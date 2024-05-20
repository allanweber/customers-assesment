package com.allanweber.customers.transaction;

import com.allanweber.customers.customer.CustomerAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    AccountTransactionRepository accountTransactionRepository;

    @InjectMocks
    TransactionService transactionService;

    @DisplayName("Calculate the current balance for existing account transactions")
    @Test
    void balance() {
        CustomerAccount customerAccount = new CustomerAccount(1, "aaaaa", "cc", "EUR");

        List<AccountTransaction> transactions = asList(
                new AccountTransaction(1, BigDecimal.valueOf(10.5)),
                new AccountTransaction(1, BigDecimal.valueOf(-5)),
                new AccountTransaction(1, BigDecimal.valueOf(10))
        );
        when(accountTransactionRepository.findByAccount(1)).thenReturn(transactions);

        BigDecimal balance = transactionService.balance(customerAccount);

        assertThat(balance).isEqualTo(BigDecimal.valueOf(15.50).setScale(2, RoundingMode.HALF_DOWN));
    }

    @DisplayName("Calculate the current balance for empty transactions")
    @Test
    void balanceZero() {
        CustomerAccount customerAccount = new CustomerAccount(1, "aaaaa", "cc", "EUR");

        when(accountTransactionRepository.findByAccount(1)).thenReturn(emptyList());

        BigDecimal balance = transactionService.balance(customerAccount);

        assertThat(balance).isEqualTo(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_DOWN));
    }

    @DisplayName("Calculate the negative balance for existing account transactions")
    @Test
    void negativeBalance() {
        CustomerAccount customerAccount = new CustomerAccount(1, "aaaaa", "cc", "EUR");

        List<AccountTransaction> transactions = asList(
                new AccountTransaction(1, BigDecimal.valueOf(1.5)),
                new AccountTransaction(1, BigDecimal.valueOf(-5)),
                new AccountTransaction(1, BigDecimal.valueOf(1))
        );
        when(accountTransactionRepository.findByAccount(1)).thenReturn(transactions);

        BigDecimal balance = transactionService.balance(customerAccount);

        assertThat(balance).isEqualTo(BigDecimal.valueOf(-2.50).setScale(2, RoundingMode.HALF_DOWN));
    }
}