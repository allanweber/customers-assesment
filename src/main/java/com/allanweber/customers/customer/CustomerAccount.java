package com.allanweber.customers.customer;

import org.springframework.data.annotation.Id;

public class CustomerAccount {

    @Id
    private Integer id;
    private final String iban;

    private final String type;

    private final String currency;

    public CustomerAccount(String iban, String type, String currency) {
        this.iban = iban;
        this.type = type;
        this.currency = currency;
    }

    public CustomerAccount(Integer id, String iban, String type, String currency) {
        this.id = id;
        this.iban = iban;
        this.type = type;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "id='" + id + '\'' +
                ", iban='" + iban + '\'' +
                ", type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
