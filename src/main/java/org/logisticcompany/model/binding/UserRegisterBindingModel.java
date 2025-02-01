package org.logisticcompany.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserRegisterBindingModel {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String email;
    private Integer age;
    private LocalDate bornOn;
    private String country;

    public UserRegisterBindingModel() {
    }

    @Size(min = 5, max = 20)
    @NotBlank
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    public UserRegisterBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getLastName() {
        return lastName;
    }

    public UserRegisterBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Email
    @NotBlank
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Positive
    @NotNull
    public Integer getAge() {
        return age;
    }

    public UserRegisterBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @NotNull
    public LocalDate getBornOn() {
        return bornOn;
    }

    public UserRegisterBindingModel setBornOn(LocalDate bornOn) {
        this.bornOn = bornOn;
        return this;
    }

    @NotNull
    @NotEmpty
    public String getCountry() {
        return country;
    }

    public UserRegisterBindingModel setCountry(String country) {
        this.country = country;
        return this;
    }
}
