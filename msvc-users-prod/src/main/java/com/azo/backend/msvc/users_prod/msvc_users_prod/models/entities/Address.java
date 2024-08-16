package com.azo.backend.msvc.users_prod.msvc_users_prod.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

//paso 1: entidad Address

@Entity
@Table(name = "addresses")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "El país es requerido.")
  @Column(nullable = false)
  private String country;

  @NotBlank(message = "La provincia es requerida.")
  @Column(nullable = false)
  private String province;

  @NotBlank(message = "La ciudad es requerida.")
  @Column(nullable = false)
  private String city;

  @NotBlank(message = "La calle es requerida.")
  private String street;

  private String sideStreet;
  private String postalCode;
  private String addressReference;
  private String houseNumber;
  private String geolocation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getSideStreet() {
    return sideStreet;
  }

  public void setSideStreet(String sideStreet) {
    this.sideStreet = sideStreet;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getAddressReference() {
    return addressReference;
  }

  public void setAddressReference(String addressReference) {
    this.addressReference = addressReference;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getGeolocation() {
    return geolocation;
  }

  public void setGeolocation(String geolocation) {
    this.geolocation = geolocation;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
