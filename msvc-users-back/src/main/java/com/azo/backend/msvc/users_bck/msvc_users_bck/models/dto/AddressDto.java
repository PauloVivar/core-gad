package com.azo.backend.msvc.users_bck.msvc_users_bck.models.dto;

public class AddressDto {

  private Long id;
  private String country;
  private String province;
  private String city;
  private String street;
  private String postalCode;

  public AddressDto() {
  }

  public AddressDto(Long id, String country, String province, String city, String street, String postalCode) {
    this.id = id;
    this.country = country;
    this.province = province;
    this.city = city;
    this.street = street;
    this.postalCode = postalCode;
  }

  // Getters and setters

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

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

}
