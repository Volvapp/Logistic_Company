package org.logisticcompany.model.dto;

import org.logisticcompany.model.enums.PackagePaidStatus;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;

import java.time.LocalDate;

public class PackageDto {
    private UserDto sender;
    private UserDto receiver;
    private UserDto courier;
    private String address;
    private Double weight;
    private Double price;
    private LocalDate registrationDate;
    private LocalDate arrivalDate;
    private State state;
    private PackageType type;
    private PackagePaidStatus packagePaidStatus;

    public PackageDto(UserDto sender, UserDto receiver, UserDto courier, String address, Double weight, Double price, LocalDate registrationDate,
                      LocalDate arrivalDate, State state, PackageType type, PackagePaidStatus packagePaidStatus) {
        this.sender = sender;
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

    public PackageDto() {
    }

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public UserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDto receiver) {
        this.receiver = receiver;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public UserDto getCourier() {
        return courier;
    }

    public void setCourier(UserDto courier) {
        this.courier = courier;
    }

    public PackagePaidStatus getPackagePaidStatus() {
        return packagePaidStatus;
    }

    public void setPackagePaidStatus(PackagePaidStatus packagePaidStatus) {
        this.packagePaidStatus = packagePaidStatus;
    }

    @Override
    public String toString() {
        return "Package{" + "\n" +
                "address='" + address + '\'' + "\n" +
                "weight=" + weight + "\n" +
                "price=" + price + "\n" +
                "registration date=" + registrationDate + "\n" +
                "arrival date=" + arrivalDate + "\n" +
                "state=" + state + "\n" +
                "type=" + type + "\n" +
                "package paid status=" + packagePaidStatus + "\n" +
                "}\n";
    }

}
