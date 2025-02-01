package org.logisticcompany.model.dto;

import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;

public class PackageDto {
    private UserDto sender;
    private UserDto receiver;
    private String address;
    private Double weight;
    private Double price;
    private State state; // enum
    private PackageType type; // enum

    public PackageDto(UserDto sender, UserDto receiver, String address, Double weight, Double price, State state, PackageType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.address = address;
        this.weight = weight;
        this.price = price;
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
                "state=" + state + "\n" +
                "type=" + type + "\n" +
                "}\n";
    }

}
