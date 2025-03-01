package org.logisticcompany.model.binding;

import org.logisticcompany.model.enums.PackageType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class PackageAddBindingModel {
    private String address;
    private Double weight;
    private Double price;
    public PackageAddBindingModel() {
    }

    public PackageAddBindingModel(String address, Double weight, Double price) {
        this.address = address;
        this.weight = weight;
        this.price = price;
    }


    @Size(min = 3, max = 20)
    @NotBlank
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Positive
    @NotNull
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Positive
    @NotNull
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
