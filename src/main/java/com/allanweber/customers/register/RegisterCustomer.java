package com.allanweber.customers.register;

import com.allanweber.customers.customer.Customer;
import com.allanweber.customers.customer.CustomerAccount;
import com.allanweber.customers.customer.CustomerAddress;
import com.allanweber.customers.customer.CustomerService;
import com.allanweber.customers.infrastructure.IbanGenerator;
import com.allanweber.customers.infrastructure.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.singletonList;

@Service
public class RegisterCustomer {

    private final CustomerService customerService;

    private final PasswordGenerator passwordGenerator;

    private final IbanGenerator ibanGenerator;

    public RegisterCustomer(CustomerService customerService, PasswordGenerator passwordGenerator, IbanGenerator ibanGenerator) {
        this.customerService = customerService;
        this.passwordGenerator = passwordGenerator;
        this.ibanGenerator = ibanGenerator;
    }

    public SignUpResponse signUp(CustomerSignUp customerSignUp) {
        String iban = ibanGenerator.generate(customerSignUp.address().country());
        String password = passwordGenerator.generate();

        Customer customer = mapToCustomer(customerSignUp, iban, password);
        customerService.save(customer);

        return new SignUpResponse(customer.getUsername(), password);
    }

    private Customer mapToCustomer(CustomerSignUp signUp, String iban, String password) {
        List<CustomerAddress> addresses = singletonList(new CustomerAddress(signUp.address().country(), signUp.address().postalCode(), signUp.address().houseNumber()));
        List<CustomerAccount> accounts = singletonList(new CustomerAccount(iban, "payment", "EUR"));
        return new Customer(
                signUp.username(),
                signUp.name(),
                signUp.dateOfBirth(),
                signUp.documentNumber(),
                password,
                addresses,
                accounts
        );
    }
}
