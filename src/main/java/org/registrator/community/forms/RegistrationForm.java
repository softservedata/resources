package org.registrator.community.forms;

import java.util.Date;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

public class RegistrationForm{

    private static final String ONLY_LITERALS = "[а-яіїєА-ЯІЇЄa-zA-Z,\\-]+";
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserService userService;

    @NotEmpty(message = "{msg.notEmptyField}")
    @Pattern(regexp = "[a-zA-Z0-9].{5,20}",message = "{msg.registration.login}")
    private String login;

    @Pattern(regexp = "[a-zA-Z0-9].{5,20}",message = "{msg.registration.password}")
    private String password;

    @NotEmpty(message = "{msg.notEmptyField}")
    private String confirmPassword;

    @Size(min=1, max=30, message="{msg.registration.nameRestriction}")
    @Pattern(regexp = ONLY_LITERALS, message = "{msg.registration.onlyLetters}")
    private String firstName;

    @Size(min=1, max=30, message="{msg.registration.secondname}")
    @Pattern(regexp = ONLY_LITERALS, message = "{msg.registration.onlyLetters}")
    private String lastName;

    @Pattern(regexp = "[[а-яіїєА-ЯІЇЄa-zA-Z,\\-]){1,30}]*", message = "{msg.registration.restriction}")
    private String middleName;

    @NotEmpty(message = "{msg.notEmptyField}")
    @Email(message="{msg.registration.wrongemail}")
    private String email;

    @Pattern(regexp = "[[А-ЯІЇЄ]){2}]*", message = "{msg.registration.onlytwoletters}")
    private String seria;

    @Pattern(regexp = "[[0-9]){6}]*", message = "{msg.registration.only6digits}")
    private String number;

    private String publishedByData;

    @Pattern(regexp = "[[0-9])]*", message = "{msg.registration.onlydigits}")
    private String postcode;

    private String region;

    private String district;

    private String city;

    private String street;

    private String building;

    private String flat;
    
    @NotNull(message = "{msg.notEmptyField}")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateOfAccession;
    
    @Pattern (regexp = "[[0-9]){10}]*", message = "fsdfsdfdsf")
    private String phoneNumber;

    @NotEmpty(message = "{msg.notEmptyField}")
    private String territorialCommunity;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPublishedByData() {
        return publishedByData;
    }

    public void setPublishedByData(String publishedByData) {
        this.publishedByData = publishedByData;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Date getDateOfAccession() {
        return dateOfAccession;
    }

    public void setDateOfAccession(Date dateOfAccession) {
        this.dateOfAccession = dateOfAccession;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTerritorialCommunity() {
        return territorialCommunity;
    }

    public void setTerritorialCommunity(String territorialCommunity) {
        this.territorialCommunity = territorialCommunity;
    }  
    
}