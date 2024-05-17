package com.allanweber.customers.customer;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    @Id
    private Integer id;

    private final String username;

    private final String name;

    private final LocalDate dateOfBirth;

    private final String documentNumber;

    private final String password;

    private final List<CustomerAddress> customerAddresses;

    private final List<CustomerAccount> customerAccounts;

    public Customer(Integer id, String username, String name, LocalDate dateOfBirth, String documentNumber, String password, List<CustomerAddress> customerAddresses, List<CustomerAccount> customerAccounts) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.password = password;
        this.customerAddresses = customerAddresses;
        this.customerAccounts = customerAccounts;
    }

    public Customer(String username, String name, LocalDate dateOfBirth, String documentNumber, String password, List<CustomerAddress> customerAddresses, List<CustomerAccount> customerAccounts) {
        this.username = username;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.password = password;
        this.customerAddresses = customerAddresses;
        this.customerAccounts = customerAccounts;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", documentNumber='" + documentNumber + '\'' +
                ", password='" + password + '\'' +
                ", customerAddresses=" + customerAddresses +
                ", customerAccounts=" + customerAccounts +
                '}';
    }
}
