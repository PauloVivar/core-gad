package com.azo.backend.msvc.courses.msvc_courses.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.azo.backend.msvc.courses.msvc_courses.models.User;
import com.azo.backend.msvc.courses.msvc_courses.models.entities.Course;
import com.azo.backend.msvc.courses.msvc_courses.repositories.CourseRepository;

//paso 4.
@Service
public class CourseServiceImpl implements CourseService {

  @Autowired
  private CourseRepository repository;
  
  @Override
  @Transactional(readOnly = true)
  public List<Course> getAll() {
    return (List<Course>) repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Course> getById(Long id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Course save(Course course) {
    return repository.save(course);
  }

  @Override
  @Transactional
  public void remove(Long id) {
    repository.deleteById(id);
  }

  @Override
  public Optional<User> assignUser(User user, Long courseId) {
    throw new UnsupportedOperationException("Unimplemented method 'assignUser'");
  }

  @Override
  public Optional<User> createUser(User user, Long courseId) {
    throw new UnsupportedOperationException("Unimplemented method 'createUser'");
  }

  @Override
  public Optional<User> deleteUser(User user, Long courseId) {
    throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
  }

}
