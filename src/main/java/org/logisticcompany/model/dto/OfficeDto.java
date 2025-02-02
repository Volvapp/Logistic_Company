package org.logisticcompany.model.dto;

import java.util.ArrayList;
import java.util.List;

public class OfficeDto {
    private String address;
    private String phone;

    private LogisticCompanyDto logisticCompanyDto;
    private List<PackageDto> packages;
    private List<UserDto> userEntities;

    public OfficeDto(String address, String phone, LogisticCompanyDto logisticCompanyDto) {
        this.address = address;
        this.phone = phone;
        this.logisticCompanyDto = logisticCompanyDto;
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

    public LogisticCompanyDto getLogisticCompanyDto() {
        return logisticCompanyDto;
    }

    public void setLogisticCompanyDto(LogisticCompanyDto logisticCompanyDto) {
        this.logisticCompanyDto = logisticCompanyDto;
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

