package com.allanweber.customers.customer;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    @Id
    private Integer id;

    private String username;

    private String name;

    private LocalDate dateOfBirth;

    private String documentNumber;

    private String password;

    private List<CustomerAddress> customerAddresses;

    private List<CustomerAccount> customerAccounts;

    public Customer() {
    }

    @PersistenceCreator
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

    public String getPassword() {
        return password;
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
