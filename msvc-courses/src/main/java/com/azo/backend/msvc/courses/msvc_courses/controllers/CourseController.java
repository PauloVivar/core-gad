package com.azo.backend.msvc.courses.msvc_courses.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.azo.backend.msvc.courses.msvc_courses.models.entities.Course;
import com.azo.backend.msvc.courses.msvc_courses.services.CourseService;

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

  @PostMapping("/")
  public ResponseEntity<?> create (@RequestBody Course course){
    Course courseDb = service.create(course);
    return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update (@RequestBody Course course, Long id){
    Optional<Course> o = service.getById(id);
    if(o.isPresent()){
      return ResponseEntity.ok(o.orElseThrow());
    }
    return ResponseEntity.notFound().build();
  }

}
