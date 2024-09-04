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
import com.azo.backend.msvc.users_prod.msvc_users_prod.clients.ProcedureClientRest;
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

  @Autowired
  private ProcedureClientRest client;
  
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
    
    // Verificar si el contribuyente ya está asociado a un usuario
    Optional<Contribuyente> existingContribuyente = contribuyenteRepository.findByCi(userRegistration.getCi());
    if (existingContribuyente.isPresent() && existingContribuyente.get().getUser() != null) {
        throw new RuntimeException("El contribuyente ya está asociado a un usuario existente.");
    }

    User newUser = createNewUser(userRegistration);
    Contribuyente contribuyente;
    if (existingContribuyente.isPresent()) {
        contribuyente = existingContribuyente.get();
    } else {
        contribuyente = createNewContribuyente(userRegistration);
    }

    associateUserAndContribuyente(newUser, contribuyente);
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
    Optional<User> uo = repository.findById(id);
    if (uo.isPresent()) {
      User user = uo.get();
      if (user.getContribuyente() != null) {
          Contribuyente contribuyente = user.getContribuyente();
          contribuyente.setUser(null);
          contribuyenteRepository.save(contribuyente);
      }
      
      // Eliminar las aceptaciones de términos asociadas
      termsAcceptanceRepository.deleteByUserId(id);
      
      // Finalmente, eliminar el usuario
      repository.delete(user);

      // Elimina user de trámite de msvc_avaluos
      client.removeProcedureUserById(id);
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

  // Métodos de validación llamados desde el controlador
  //validar campos unique
  @Override
  public boolean existsByUsername(String username) {
      return repository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
      return repository.existsByEmail(email);
  }

  @Override
  public boolean isContribuyenteAssociated(String ci) {
      Optional<Contribuyente> contribuyente = contribuyenteRepository.findByCi(ci);
      return contribuyente.isPresent() && contribuyente.get().getUser() != null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> listByIds(Iterable<Long> ids) {
    return (List<User>) repository.findAllById(ids);
  }


  //METODOS AUXILIARES
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

  //Logica utils para saveRegistration
  private User createNewUser(UserRegistrationDTO userRegistration) {
    User newUser = new User();
    newUser.setUsername(userRegistration.getUsername());
    newUser.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
    newUser.setEmail(userRegistration.getEmail());

    List<Role> roles = new ArrayList<>();
    roleRepository.findByName("ROLE_USER").ifPresent(roles::add);
    newUser.setRoles(roles);

    return repository.save(newUser);
  }

  private Contribuyente createNewContribuyente(UserRegistrationDTO userRegistration) {
    Contribuyente contribuyente = new Contribuyente();
    contribuyente.setCi(userRegistration.getCi());
    
    // Solo establecer los campos si están presentes
    if (userRegistration.getFullName() != null) contribuyente.setFullName(userRegistration.getFullName());
    if (userRegistration.getAddress() != null) contribuyente.setAddress(userRegistration.getAddress());
    if (userRegistration.getPhone() != null) contribuyente.setPhone(userRegistration.getPhone());
    if (userRegistration.getTaxpayerType() != null) contribuyente.setTaxpayerType(userRegistration.getTaxpayerType());
    if (userRegistration.getLegalPerson() != null) contribuyente.setLegalPerson(userRegistration.getLegalPerson());
    if (userRegistration.getIdentificationType() != null) contribuyente.setIdentificationType(userRegistration.getIdentificationType());
    if (userRegistration.getBirthdate() != null) contribuyente.setBirthdate(userRegistration.getBirthdate());
    if (userRegistration.getTaxpayerCity() != null) contribuyente.setTaxpayerCity(userRegistration.getTaxpayerCity());
    if (userRegistration.getHouseNumber() != null) contribuyente.setHouseNumber(userRegistration.getHouseNumber());
    if (userRegistration.getMaritalStatus() != null) contribuyente.setMaritalStatus(userRegistration.getMaritalStatus());

    return contribuyenteRepository.save(contribuyente);
  }

  private void associateUserAndContribuyente(User user, Contribuyente contribuyente) {
    contribuyente.setUser(user);
    user.setContribuyente(contribuyente);
    repository.save(user);
    contribuyenteRepository.save(contribuyente);
  }

}
