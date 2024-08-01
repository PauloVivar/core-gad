package com.azo.backend.msvc.courses.msvc_courses.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.azo.backend.msvc.courses.msvc_courses.models.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;

//paso 1

@Entity
@Table(name = "courses")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  //un curso puede tener muchos usuarios, creacion y eliminacion en cascada
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "course_id")
  private List<CourseUser> courseUsers;

  //no va a ser mapada en la db
  @Transient
  private List<User> users;

  //constructor
  public Course() {
    courseUsers = new ArrayList<>();
    users = new ArrayList<>();
  }

  //getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  //metodos custom
  public void addCourseUser (CourseUser courseUser){
    courseUsers.add(courseUser);
  }

  //este metodo se sobreescribe en CourseUser
  public void removeCourseUser (CourseUser courseUser){
    courseUsers.remove(courseUser);
  }

  public List<CourseUser> getCourseUsers() {
    return courseUsers;
  }

  public void setCourseUsers(List<CourseUser> courseUsers) {
    this.courseUsers = courseUsers;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

}
