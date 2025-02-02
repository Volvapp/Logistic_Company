package org.logisticcompany.model.view;

import java.time.LocalDate;

public class ClientPackageDetailsView {
    private Long id;
    private String receiver;
    private String courier;
    private String address;
    private Double weight;
    private Double price;
    private LocalDate registrationDate;
    private LocalDate arrivalDate;
    private String state;
    private String type;
    private String packagePaidStatus;

    public ClientPackageDetailsView() {
    }

    public ClientPackageDetailsView(Long id, String receiver, String courier, String address, Double weight, Double price, LocalDate registrationDate, LocalDate arrivalDate, String state, String type, String packagePaidStatus) {
        this.id = id;
        this.receiver = receiver;
        this.courier = courier;
        this.address = address;
        this.weight = weight;
        this.price = price;
        this.registrationDate = registrationDate;
        this.arrivalDate = arrivalDate;
        this.state = state;
        this.type = type;
        this.packagePaidStatus = packagePaidStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
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

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPackagePaidStatus() {
        return packagePaidStatus;
    }

    public void setPackagePaidStatus(String packagePaidStatus) {
        this.packagePaidStatus = packagePaidStatus;
    }
}
