package org.logisticcompany.model.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Double balance;
    private Integer age;
    private LocalDate bornOn;
    private String country;
    private Set<RoleDto> roles;

    public UserDto(String username, String firstName, String lastName, String password, String email,
                   Double balance, Integer age, LocalDate bornOn, String country) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.balance = balance;
        this.age = age;
        this.bornOn = bornOn;
        this.country = country;
        roles = new HashSet<>();
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getBornOn() {
        return bornOn;
    }

    public void setBornOn(LocalDate bornOn) {
        this.bornOn = bornOn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" + "\n" +
                "username='" + username + '\'' + "\n" +
                "firstName='" + firstName + '\'' + "\n" +
                "lastName='" + lastName + '\'' + "\n" +
                "email='" + email + '\'' + "\n" +
                "balance='" + balance + '\'' + "\n" +
                "age=" + age + "\n" +
                "bornOn=" + bornOn + "\n" +
                "country='" + country + '\'' + "\n" +
                "roles=" + roles + "\n" +
                "}\n";
    }

}

