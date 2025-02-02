package org.logisticcompany.model.service;

import org.logisticcompany.model.enums.PackageType;

import java.time.LocalDate;

public class PackageServiceModel {
    private Long id;
    private String address;
    private Double weight;
    private Double price;
    private PackageType type;

    public PackageServiceModel(String address, Double weight, Double price,
                               PackageType type) {
        this.address = address;
        this.weight = weight;
        this.price = price;
        this.type = type;
    }

    public PackageServiceModel() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
