package com.azo.backend.msvc.users_prod.msvc_users_prod.services;

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

import com.azo.backend.msvc.users_prod.msvc_users_prod.auth.TokenJwtConfig;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.IUser;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDetailDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserDto;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.UserRegistrationDTO;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.dto.mapper.DtoMapperUser;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Contribuyente;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.PasswordResetCode;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.Role;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities.User;
import com.azo.backend.msvc.users_prod.msvc_users_prod.models.request.UserRequest;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.ContribuyenteRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.PasswordResetCodeRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.RoleRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.TermsAcceptanceRepository;
import com.azo.backend.msvc.users_prod.msvc_users_prod.repositories.UserRepository;

import io.jsonwebtoken.Jwts;

//4. Cuarto Implementación de UserService -> volver realidad el CRUD

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ContribuyenteRepository contribuyenteRepository;

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

    // Guardar el User primero
    newUser = repository.save(newUser);

    // Buscar si existe un Contribuyente con la cédula proporcionada
    Optional<Contribuyente> existingContribuyente = contribuyenteRepository.findByCi(userRegistration.getCi());

    // ***test Crear y asociar Contribuyente

    Contribuyente contribuyente;
    if (existingContribuyente.isPresent()) {
      // Si existe, asociarlo al nuevo usuario
      contribuyente = existingContribuyente.get();

      // Actualizar los campos del contribuyente
      updateContribuyente(contribuyente, userRegistration);

    } else {
      // Si no existe, crear un nuevo Contribuyente
      contribuyente = createNewContribuyente(userRegistration);
    }

    contribuyente.setUser(newUser);
    newUser.setContribuyente(contribuyente);

    // Guardar el Contribuyente
    contribuyenteRepository.save(contribuyente);

    // Actualizar el User con la referencia al Contribuyente
    newUser = repository.save(newUser);

    // Registrar la aceptación de términos
    termsService.recordTermsInteraction(newUser.getId(), true, ipAddress);

    return DtoMapperUser.build(newUser);
    //return DtoMapperUser.builder().setUser(savedUser).build();

  }

  //actualizar user
  //se utiliza UserRequest por que no se pasa el password por seguridad
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
  
  //eliminar user sin eliminar contribuyente
  @Override
  @Transactional
  public void remove(Long id) {
    Optional<User> userOpt = repository.findById(id);
    if (userOpt.isPresent()) {
      User user = userOpt.get();
      if (user.getContribuyente() != null) {
          Contribuyente contribuyente = user.getContribuyente();
          contribuyente.setUser(null);
          contribuyenteRepository.save(contribuyente);
      }
      
      // Eliminar las aceptaciones de términos asociadas
      termsAcceptanceRepository.deleteByUserId(id);
      
      // Finalmente, eliminar el usuario
      repository.delete(user);
    }
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

  private void updateContribuyente(Contribuyente contribuyente, UserRegistrationDTO userRegistration) {
    contribuyente.setFullName(userRegistration.getFullName());
    contribuyente.setAddress(userRegistration.getAddress());
    contribuyente.setPhone(userRegistration.getPhone());

    contribuyente.setIndicatorExoneration(userRegistration.getIndicatorExoneration());  //por defecto
    contribuyente.setReasonExoneration(userRegistration.getReasonExoneration());        //por defecto
    contribuyente.setTaxpayerStatus(userRegistration.getTaxpayerStatus());              //por defecto
    contribuyente.setTaxpayerCity(userRegistration.getTaxpayerCity());
    contribuyente.setHouseNumber(userRegistration.getHouseNumber());
    contribuyente.setTaxpayerType(userRegistration.getTaxpayerType());                  //por defecto


    contribuyente.setLegalPerson(userRegistration.getLegalPerson());
    contribuyente.setIdentificationType(userRegistration.getIdentificationType());
    contribuyente.setBirthdate(userRegistration.getBirthdate());
    contribuyente.setDisabilityPercentage(userRegistration.getDisabilityPercentage());
    contribuyente.setMaritalStatus(userRegistration.getMaritalStatus());
  }

  private Contribuyente createNewContribuyente(UserRegistrationDTO userRegistration) {
    Contribuyente contribuyente = new Contribuyente();
    contribuyente.setCi(userRegistration.getCi());
    updateContribuyente(contribuyente, userRegistration);
    return contribuyente;
  }

}
