package org.logisticcompany.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OfficeAddBindingModel {
    private String phone;
    private String address;
    private String company;

    public OfficeAddBindingModel() {
    }

    public OfficeAddBindingModel(String phone, String address, String company) {
        this.phone = phone;
        this.address = address;
        this.company = company;
    }

    @Size(min = 10, max = 20)
    @NotBlank
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
