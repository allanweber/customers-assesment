package com.allanweber.customers.customer;

import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CustomerAccountRepository extends Repository<CustomerAccount, Integer> {
    Optional<CustomerAccount> findByIban(String iban);
}
