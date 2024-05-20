package com.allanweber.customers.transaction;

import com.allanweber.customers.customer.CustomerAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransactionService {

    private final AccountTransactionRepository accountTransactionRepository;

    public TransactionService(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public BigDecimal balance(CustomerAccount account) {

        //Temporary to simulate some values
        simulateBalance(account);

        return accountTransactionRepository.findByAccount(account.getId())
                .stream()
                .map(AccountTransaction::getBalance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_DOWN);
    }

    //This is just to simulate some values
    private void simulateBalance(CustomerAccount account) {
        if (!accountTransactionRepository.existsByAccount(account.getId())) {
            for (int i = 0; i < 10; i++) {
                double x = (Math.random() * (100 - (-100) + 1)) + (-100);
                BigDecimal value = BigDecimal.valueOf(x).setScale(2, RoundingMode.DOWN);
                accountTransactionRepository.save(new AccountTransaction(account.getId(), value));
            }
        }
    }
}
