package org.logisticcompany.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class EmployeeAddBindingModel {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String employeeType;
    private String email;
    private Integer age;
    private LocalDate bornOn;
    private String country;

    private String company;

    public EmployeeAddBindingModel() {
    }

    @Size(min = 5, max = 20)
    @NotBlank
    public String getUsername() {
        return username;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getFirstName() {
        return firstName;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getLastName() {
        return lastName;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getPassword() {
        return password;
    }

    @NotNull
    public String getEmployeeType() {
        return employeeType;
    }

    @Email
    @NotBlank
    public String getEmail() {
        return email;
    }

    @Positive
    @NotNull
    public Integer getAge() {
        return age;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    @NotNull
    public LocalDate getBornOn() {
        return bornOn;
    }

    @NotNull
    @NotEmpty
    public String getCountry() {
        return country;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    @NotNull
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String employeeType) {
        this.employeeType = employeeType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setBornOn(LocalDate bornOn) {
        this.bornOn = bornOn;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
