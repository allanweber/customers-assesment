package com.allanweber.customers;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public class Customer {
    @Id
    private Integer id;

    private final String username;

    private final String firstName;

    private final String lastName;

    private final LocalDate dateOfBirth;

    private final String documentNumber;

    private final String password;

    private final List<CustomerAddress> customerAddresses;

    private final List<CustomerAccount> customerAccounts;

    public Customer(Integer id, String username, String firstName, String lastName, LocalDate dateOfBirth, String documentNumber, String password, List<CustomerAddress> customerAddresses, List<CustomerAccount> customerAccounts) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.password = password;
        this.customerAddresses = customerAddresses;
        this.customerAccounts = customerAccounts;
    }

    public Customer(String username, String firstName, String lastName, LocalDate dateOfBirth, String documentNumber, String password, List<CustomerAddress> customerAddresses, List<CustomerAccount> customerAccounts) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.documentNumber = documentNumber;
        this.password = password;
        this.customerAddresses = customerAddresses;
        this.customerAccounts = customerAccounts;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public List<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    public List<CustomerAccount> getCustomerAccounts() {
        return customerAccounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", documentNumber='" + documentNumber + '\'' +
                ", customerAddresses=" + customerAddresses +
                ", customerAccounts=" + customerAccounts +
                '}';
    }
}
