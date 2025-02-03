package org.logisticcompany.model.service;

public class OfficeServiceModel {
    private Long id;
    private String phone;
    private String address;
    private String company;

    public OfficeServiceModel() {
    }

    public OfficeServiceModel(Long id, String phone, String address, String company) {
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
