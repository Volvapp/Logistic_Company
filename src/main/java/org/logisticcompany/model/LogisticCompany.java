package org.logisticcompany.model;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "logistic_companies")
public class LogisticCompany extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "revenue")
    private Double revenue;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Office> offices;
    @OneToMany
    private List<UserEntity> userEntities;

    public LogisticCompany(String name, Double revenue) {
        this.name = name;
        this.revenue = revenue;
        this.offices = new ArrayList<>();
        this.userEntities = new ArrayList<>();
    }

    public LogisticCompany() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
