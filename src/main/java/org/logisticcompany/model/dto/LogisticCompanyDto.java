package org.logisticcompany.model.dto;

import java.util.ArrayList;
import java.util.List;

public class LogisticCompanyDto {
    private String name;
    private Double revenue;
    private List<OfficeDto> offices;
    private List<UserDto> userEntities;

    public LogisticCompanyDto(String name, Double revenue) {
        this.name = name;
        this.revenue = revenue;
        this.offices = new ArrayList<>();
        this.userEntities = new ArrayList<>();
    }

    public LogisticCompanyDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public List<OfficeDto> getOffices() {
        return offices;
    }

    public void setOffices(List<OfficeDto> offices) {
        this.offices = offices;
    }

    public List<UserDto> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserDto> userEntities) {
        this.userEntities = userEntities;
    }
}
