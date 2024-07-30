package com.azo.backend.msvc.courses.msvc_courses.services;

import java.util.List;
import java.util.Optional;

import com.azo.backend.msvc.courses.msvc_courses.models.entities.Course;

//paso 3.
public interface CourseService {
  List<Course> getAll();
  Optional<Course> getById(Long id);
  Course save (Course course);
  void remove (Long id);

}
