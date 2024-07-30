package com.azo.backend.msvc.courses.msvc_courses.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azo.backend.msvc.courses.msvc_courses.models.entities.Course;
import com.azo.backend.msvc.courses.msvc_courses.services.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

  @Autowired
  private CourseService service;

  @GetMapping
  public ResponseEntity<List<Course>> list () {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> detail(@PathVariable Long id) {
    Optional<Course> o = service.getById(id);
    if(o.isPresent()) {
      return ResponseEntity.ok(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<?> create (@Valid @RequestBody Course course, BindingResult result){
    if(result.hasErrors()){
      return validate(result);
    }
    Course courseDb = service.save(course);
    return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update (@Valid @RequestBody Course course, BindingResult result, Long id){
    if(result.hasErrors()){
      return validate(result);
    }
    Optional<Course> o = service.getById(id);
    if(o.isPresent()){
      Course courseDb = o.orElseThrow();
      courseDb.setName(course.getName());
      return ResponseEntity.status(HttpStatus.CREATED).body(service.save(courseDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> remove (@PathVariable Long id){
    Optional<Course> o = service.getById(id);
    if(o.isPresent()){
      service.remove(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  //metodos utilitario para validar que un error si se repite el user o email
  private ResponseEntity<Map<String, String>> validate (BindingResult result){
    Map<String, String> errors = new HashMap<>();
    result.getFieldErrors().forEach( err -> {
      errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errors);
  }

}
