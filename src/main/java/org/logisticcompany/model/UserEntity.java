package org.logisticcompany.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "age")
    private Integer age;
    @Column(name = "born_on")
    private LocalDate bornOn;
    @Column(name = "country")
    private String country;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    @ManyToOne
    private LogisticCompany logisticCompany;

    public UserEntity(String username, String firstName, String lastName, String password, String email,
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
        this.roles = new HashSet<>();
        this.logisticCompany = null;
    }

    public UserEntity() {
    }

    public Set<RoleEntity> getRoles() {
        return roles;
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

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public LogisticCompany getLogisticCompany() {
        return logisticCompany;
    }

    public void setLogisticCompany(LogisticCompany logisticCompany) {
        this.logisticCompany = logisticCompany;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", balance='" + balance + '\'' +
                ", age=" + age +
                ", bornOn=" + bornOn +
                ", country='" + country + '\'' +
                ", roles=" + roles +
                '}';
    }
}