package com.allanweber.customers.customer;

import com.allanweber.customers.validations.NotUniqueUserNameException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        if (isUniqueUserName(customer.getUsername())) {
            return customerRepository.save(customer);
        }
        throw new NotUniqueUserNameException();
    }

    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public boolean isUniqueUserName(String username) {
        return findByUsername(username).isEmpty();
    }

    public List<CustomerAccount> retrieveAccounts(String username) {
        Customer customer = findByUsername(username)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Customer not found"));
        return ofNullable(customer.getCustomerAccounts()).orElse(emptyList());
    }
}
