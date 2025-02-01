package org.logisticcompany.model;

import org.logisticcompany.model.enums.PackageType;
import org.logisticcompany.model.enums.State;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "packages")
public class Package extends BaseEntity {
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
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;
    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;
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
        this.registrationDate = LocalDate.now();
        this.arrivalDate = LocalDate.now().plusDays(5);
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
        return "Package{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", address='" + address + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", registration date=" + registrationDate +
                ", arrival date=" + arrivalDate +
                ", state=" + state +
                ", type=" + type +
                '}';
    }

}