package org.logisticcompany.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

@Table(name = "offices")
public class Office extends BaseEntity{
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "address", nullable = false)
    private String address;
    @ManyToOne
    private LogisticCompany logisticCompany;
    @OneToMany
    private List<Package> packages;
    @OneToMany
    private List<UserEntity> userEntities;


    public Office(String phone, String address, LogisticCompany logisticCompany) {
        this.phone = phone;
        this.address = address;
        this.logisticCompany = logisticCompany;
        this.packages = new ArrayList<>();
        this.userEntities = new ArrayList<>();
    }

    public Office() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LogisticCompany getLogisticCompany() {
        return logisticCompany;
    }

    public void setLogisticCompany(LogisticCompany logisticCompany) {
        this.logisticCompany = logisticCompany;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
