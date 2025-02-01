package org.logisticcompany.model.dto;

import java.util.ArrayList;
import java.util.List;

public class OfficeDto {
    private String address;
    private String phone;
    private List<PackageDto> packages;
    private List<UserDto> userEntities;

    public OfficeDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
        this.packages = new ArrayList<>();
        this.userEntities = new ArrayList<>();
    }

    public OfficeDto() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<PackageDto> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageDto> packages) {
        this.packages = packages;
    }

    public List<UserDto> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserDto> userEntities) {
        this.userEntities = userEntities;
    }
}

