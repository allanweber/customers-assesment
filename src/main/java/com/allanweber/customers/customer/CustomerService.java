package com.allanweber.customers.customer;

import org.springframework.stereotype.Service;

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

    public boolean isUniqueUserName(String username) {
        return customerRepository.findByUsername(username).isEmpty();
    }
}
