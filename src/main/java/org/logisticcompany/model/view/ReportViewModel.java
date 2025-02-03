package org.logisticcompany.model.view;

public class ReportViewModel {
    private String data;


    public ReportViewModel() {
    }

    public ReportViewModel(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
