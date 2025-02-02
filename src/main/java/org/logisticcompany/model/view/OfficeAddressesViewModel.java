package org.logisticcompany.model.view;

public class OfficeAddressesViewModel {
    private String address;

    public OfficeAddressesViewModel() {
    }

    public OfficeAddressesViewModel(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
