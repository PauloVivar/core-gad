package com.azo.backend.msvc.courses.msvc_courses.repositories;

import org.springframework.data.repository.CrudRepository;

import com.azo.backend.msvc.courses.msvc_courses.models.entities.Course;

//paso 2
public interface CourseRepository extends CrudRepository<Course, Long> {

}
