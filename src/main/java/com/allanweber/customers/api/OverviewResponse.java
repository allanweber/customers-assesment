package com.allanweber.customers.api;

import com.allanweber.customers.customer.CustomerAccount;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

import static java.util.Optional.ofNullable;

public class OverviewResponse {

    private final String number;
    private final String type;
    private final String currency;
    @NumberFormat(pattern = "#0.00")
    private final BigDecimal balance;

    public OverviewResponse(CustomerAccount customerAccount, BigDecimal balance) {
        this.number = ofNullable(customerAccount).map(CustomerAccount::getIban).orElse(null);
        this.type = ofNullable(customerAccount).map(CustomerAccount::getType).orElse(null);
        this.currency = ofNullable(customerAccount).map(CustomerAccount::getCurrency).orElse(null);
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
