package org.logisticcompany.model.view;

public class LogisticCompanyViewModel {
    private String name;

    public LogisticCompanyViewModel() {
    }

    public LogisticCompanyViewModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
