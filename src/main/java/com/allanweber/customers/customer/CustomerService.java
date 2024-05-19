package com.allanweber.customers.customer;

import com.allanweber.customers.validations.NotUniqueUserNameException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
