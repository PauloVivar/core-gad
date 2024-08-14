package com.azo.backend.msvc.users_bck.msvc_users_bck.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.users_bck.msvc_users_bck.auth.TokenJwtConfig;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.IUser;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.UserDto;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.UserRegistrationDTO;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto.mapper.DtoMapperUser;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Customer;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.PasswordResetCode;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.Role;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.entities.User;
import com.azo.backend.msvc.users_bck.msvc_users_bck.models.request.UserRequest;
import com.azo.backend.msvc.users_bck.msvc_users_bck.repositories.PasswordResetCodeRepository;
import com.azo.backend.msvc.users_bck.msvc_users_bck.repositories.RoleRepository;
import com.azo.backend.msvc.users_bck.msvc_users_bck.repositories.TermsAcceptanceRepository;
import com.azo.backend.msvc.users_bck.msvc_users_bck.repositories.UserRepository;

import io.jsonwebtoken.Jwts;

//4. Cuarto Implementación de UserService -> volver realidad el CRUD

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private TermsAcceptanceRepository termsAcceptanceRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TermsService termsService;

  //test
  @Autowired
    private PasswordResetCodeRepository passwordResetCodeRepository;

  @Autowired
  private EmailService emailService;

  
  //listar todos los users
  @Override
  @Transactional(readOnly = true)
  public List<UserDto> findAll() {
    List<User> users = (List<User>) repository.findAll();

    return users
      .stream()
      .map( u -> DtoMapperUser.builder().setUser(u).build())
      .collect(Collectors.toList());

    //return (List<User>) repository.findAll();
  }

  //listar todos los users con paginación
  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> findAll(Pageable pageable) {
    Page<User> usersPage = repository.findAll(pageable);
    return usersPage.map(u -> DtoMapperUser.builder().setUser(u).build());
  }

  //buscar users por id
  @Override
  @Transactional(readOnly = true)
  public Optional<UserDetailDto> findById(Long id) {
    return repository.findById(id).map(u -> DtoMapperUser
      .builder()
      .setUser(u)
      .buildDetail());
    
    //return repository.findById(id);
  }

  //guardar user
  //Transactional ya no es readOnly ya que save guarda y actualiza
  @Override
  @Transactional
  public UserDto save(User user) {
    String passwordBCrypt = passwordEncoder.encode(user.getPassword());
    user.setPassword(passwordBCrypt);

    //obtener roles
    List<Role> roles = getRoles(user);

    user.setRoles(roles);
    return DtoMapperUser.builder().setUser(repository.save(user)).build();
    //return repository.save(user);
  }

  //guardar user registration
  @Override
  @Transactional
  public UserDto saveRegistration(UserRegistrationDTO userRegistration, String ipAddress) {
    if (existsByUsername(userRegistration.getUsername())) {
        throw new RuntimeException("El nombre de usuario ya existe");
    }
    if (existsByEmail(userRegistration.getEmail())) {
        throw new RuntimeException("El email ya está registrado");
    }

    User newUser = new User();
    newUser.setUsername(userRegistration.getUsername());
    newUser.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
    newUser.setEmail(userRegistration.getEmail());

    // Asignar rol de usuario por defecto
    List<Role> roles = new ArrayList<>();
    roleRepository.findByName("ROLE_USER").ifPresent(roles::add);
    newUser.setRoles(roles);

    // ***test Crear y asociar Customer
    Customer newCustomer = new Customer();
    newCustomer.setFirstname(userRegistration.getFirstname());
    newCustomer.setLastname(userRegistration.getLastname());
    newCustomer.setTypeDocumentId(userRegistration.getTypeDocumentId());
    newCustomer.setDocumentId(userRegistration.getDocumentId());
    newCustomer.setUser(newUser);
    newUser.setCustomer(newCustomer);

    User savedUser = repository.save(newUser);

    // Registrar la aceptación de términos
    termsService.recordTermsInteraction(savedUser.getId(), true, ipAddress);

    return DtoMapperUser.build(savedUser); // test
    //return DtoMapperUser.builder().setUser(savedUser).build();
  }

  //actualizar user
  //se utiliza UserRequest ya que no se pasa el password por seguridad
  @Override
  @Transactional
  public Optional<UserDto> update(UserRequest user, Long id) {
    Optional<User> o = repository.findById(id);
    User userOptional = null;
    if(o.isPresent()){

      //obtener roles
      List<Role> roles = getRoles(user);

      User userDb = o.orElseThrow();
      userDb.setRoles(roles);
      userDb.setUsername(user.getUsername());
      userDb.setEmail(user.getEmail());
      userDb.setStatus(user.getStatus());
      userDb.setAvatar(user.getAvatar());
      
      userOptional = repository.save(userDb);
    }
    return Optional.ofNullable(DtoMapperUser.builder().setUser(userOptional).build());
    //return Optional.ofNullable(userOptional);
  }
  
  //eliminar user
  @Override
  @Transactional
  public void remove(Long id) {
    // Primero, eliminamos todas las aceptaciones de términos asociadas
    termsAcceptanceRepository.deleteByUserId(id);
    // Luego, eliminamos el usuario
    repository.deleteById(id);
  }

  //inicio reset password
  @Override
  @Transactional(readOnly = true)
  public Optional<User> findByEmail(String email) {
      return repository.findByEmail(email);
  }

  @Override
  @Transactional
  public String createPasswordResetCodeForUser(User user) {
    // Generar código numérico de 6 dígitos
    String code = generateShortCode();

    // Generar token JWT
    String token = generateJwtToken(user);

    // Crear y guardar PasswordResetCode
    PasswordResetCode resetCode = new PasswordResetCode();
    resetCode.setCode(code);
    resetCode.setToken(token);
    resetCode.setUser(user);
    resetCode.setExpiryDate(LocalDateTime.now().plusHours(1)); // Expira en 1 hora
    passwordResetCodeRepository.save(resetCode);

    // Enviar código por email
    emailService.sendPasswordResetEmail(user.getEmail(), code);

    return code;
  }

  @Override
  @Transactional(readOnly = true)
  public String validatePasswordResetCode(String code) {
      Optional<PasswordResetCode> resetCodeOpt = passwordResetCodeRepository.findByCode(code);
      if (resetCodeOpt.isEmpty()) {
          return "invalidCode";
      }
      PasswordResetCode resetCode = resetCodeOpt.get();
      if (resetCode.getExpiryDate().isBefore(LocalDateTime.now())) {
          return "expired";
      }
      return null; // Código válido
  }

  @Override
  @Transactional(readOnly = true)
  public User getUserByPasswordResetCode(String code) {
    return passwordResetCodeRepository.findByCode(code)
            .map(PasswordResetCode::getUser)
            .orElse(null);
  }

  private String generateShortCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000); // Genera un número entre 100000 y 999999
    return String.valueOf(code);
  }

  private String generateJwtToken(User user) {
    long expirationTime = 3600000; // 1 hora en milisegundos
    Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

    return Jwts.builder()
            .subject(user.getUsername())
            .claim("userId", user.getId())
            .claim("purpose", "password_reset")
            .issuedAt(new Date())
            .expiration(expirationDate)
            .signWith(TokenJwtConfig.SECRET_KEY)
            .compact();
  }

  // Método para cambiar la contraseña
  @Override
  @Transactional
  public void changeUserPassword(User user, String newPassword) {
      user.setPassword(passwordEncoder.encode(newPassword));
      repository.save(user);
      // Eliminar todos los códigos de restablecimiento para este usuario
      passwordResetCodeRepository.deleteByUser(user);
  }
  //fin reset password


  //validar campos unique
  @Override
  public boolean existsByUsername(String username) {
    return repository.toString().equals(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return repository.toString().equals(email);
  }

  // @Override
  // public UserDto activateUser(Long id) {
  //   User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  //   user.setStatus(User.STATUS_ACTIVE);
  //   return repository.save(user);
  // }

  // @Override
  // public UserDto deactivateUser(Long id) {
  //   User user = repository.findById(id)
  //       .orElseThrow(() -> new UserNotFoundException(id));
  //   user.setStatus(User.STATUS_INACTIVE);
  //   return repository.save(user);
  // }

  //Logica utils para asignar o eliminar un usuario como role admin
  private List<Role> getRoles(IUser user){
    Optional<Role> ou = roleRepository.findByName("ROLE_USER");
    List<Role> roles = new ArrayList<>();
    if(ou.isPresent()){
      roles.add(ou.orElseThrow());
    }

    if(user.isAdmin()){
      Optional<Role> oa = roleRepository.findByName("ROLE_ADMIN");
      if(oa.isPresent()){
        roles.add(oa.orElseThrow());
      }
    }
    return roles;
  }

}
