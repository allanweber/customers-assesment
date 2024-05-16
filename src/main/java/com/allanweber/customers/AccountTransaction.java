package com.allanweber.customers;

import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class AccountTransaction {
    @Id
    private Integer id;

    private Integer account;

    private BigDecimal balance;

    public AccountTransaction(Integer account, BigDecimal balance) {
        this.account = account;
        this.balance = balance;
    }

    public AccountTransaction(Integer id, Integer account, BigDecimal balance) {
        this.id = id;
        this.account = account;
        this.balance = balance;
    }

    public AccountTransaction() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getAccount() {
        return account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "CustomerTransaction{" +
                "id=" + id +
                ", account=" + account +
                ", balance=" + balance +
                '}';
    }
}
