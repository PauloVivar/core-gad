package com.viving.springcloud.msvc.users.msvc_users.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viving.springcloud.msvc.users.msvc_users.models.entities.User;
import com.viving.springcloud.msvc.users.msvc_users.services.UserService;


//5. Se icrea el UserController

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping
  public List<User> list(){
    return service.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show (@PathVariable Long id){
    Optional<User> userOptional = service.findById(id);
    if(userOptional.isPresent()){
      return ResponseEntity.ok(userOptional.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create (@RequestBody User user){
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
  }


  @PutMapping("/{id}")
  public ResponseEntity<?> update (@RequestBody User user, @PathVariable Long id){
    Optional<User> o = service.findById(id);
    if(o.isPresent()){
      User userDb = o.orElseThrow();
      userDb.setUsername(user.getUsername());
      userDb.setPassword(user.getPassword());
      userDb.setEmail(user.getEmail());
      userDb.setCellphone(user.getCellphone());
      userDb.setPhone(user.getPhone());
      userDb.setAvatar(user.getAvatar());
      userDb.setcreatedAt(user.getcreatedAt());
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<User> o = service.findById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

}
