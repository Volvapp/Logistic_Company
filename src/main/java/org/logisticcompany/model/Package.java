package org.logisticcompany.model;

import jakarta.persistence.*;
import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;

@Entity
@Table(name = "packages")
public class Package extends BaseEntity{
    @ManyToOne
    private UserEntity sender;
    @ManyToOne
    private UserEntity receiver;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "weight", nullable = false)
    private Double weight;
    @Column(name = "price")
    private Double price;
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PackageType type;

    public Package(UserEntity sender, UserEntity receiver, String address, Double weight, Double price, State state, PackageType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.address = address;
        this.weight = weight;
        this.price = price;
        this.state = state;
        this.type = type;
    }

    public Package() {

    }

    public UserEntity getSender() {
        return sender;
    }

    public void setSender(UserEntity sender) {
        this.sender = sender;
    }

    public UserEntity getReceiver() {
        return receiver;
    }

    public void setReceiver(UserEntity receiver) {
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
}
