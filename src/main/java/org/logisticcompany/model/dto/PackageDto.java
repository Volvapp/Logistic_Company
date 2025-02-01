package org.logisticcompany.model.dto;

import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;

import java.time.LocalDate;

public class PackageDto {
    private UserDto sender;
    private UserDto receiver;
    private String address;
    private Double weight;
    private Double price;
    private LocalDate registrationDate;
    private LocalDate arrivalDate;
    private State state;
    private PackageType type;

    public PackageDto(UserDto sender, UserDto receiver, String address, Double weight, Double price, LocalDate registrationDate,
                      LocalDate arrivalDate, State state, PackageType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.address = address;
        this.weight = weight;
        this.price = price;
        this.registrationDate = registrationDate;
        this.arrivalDate = arrivalDate;
        this.state = state;
        this.type = type;
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

    @Override
    public String toString() {
        return "PackageDto{" + "\n" +
                "sender=" + sender + "\n" +
                "receiver=" + receiver + "\n" +
                "address='" + address + '\'' + "\n" +
                "weight=" + weight + "\n" +
                "price=" + price + "\n" +
                "registration date=" + registrationDate + "\n" +
                "arrival date=" + arrivalDate + "\n" +
                "state=" + state + "\n" +
                "type=" + type + "\n" +
                "}\n";
    }

}
