package org.logisticcompany.model.view;

public class UserBalanceViewModel {
    private Double balance;

    public UserBalanceViewModel() {
    }

    public Double getBalance() {
        return balance;
    }

    public UserBalanceViewModel setBalance(Double balance) {
        this.balance = balance;
        return this;
    }
}
