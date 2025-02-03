package org.logisticcompany.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LogisticCompanyAddBindingModel {
    private String name;

    public LogisticCompanyAddBindingModel() {
    }

    public LogisticCompanyAddBindingModel(String name) {
        this.name = name;
    }

    @Size(min = 3, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
