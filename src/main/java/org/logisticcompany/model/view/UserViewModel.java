package org.logisticcompany.model.view;

import java.time.LocalDate;

public class UserViewModel {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private LocalDate bornOn;
    private String country;

    public UserViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserViewModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public LocalDate getBornOn() {
        return bornOn;
    }

    public UserViewModel setBornOn(LocalDate bornOn) {
        this.bornOn = bornOn;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserViewModel setCountry(String country) {
        this.country = country;
        return this;
    }
}
