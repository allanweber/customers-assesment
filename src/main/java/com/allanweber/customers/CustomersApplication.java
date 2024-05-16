package com.allanweber.customers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(CustomerRepository repository, AccountTransactionRepository accountTransactionRepository) {
        return args -> {

            List<CustomerAddress> customerAddresses = new ArrayList<>();
            customerAddresses.add(new CustomerAddress("NL", "3452CA", "13"));

            List<CustomerAccount> customerAccounts = new ArrayList<>();
            customerAccounts.add(new CustomerAccount("IBAN-123", "acc", "EUR"));

            String password = "aadasdasdasd";

            Customer allan = repository.save(new Customer("allanweber", "Allan", "Weber", LocalDate.of(1984, 2, 22), "123456", password, customerAddresses, customerAccounts));

            System.out.println(allan);

            Integer account = allan.getCustomerAccounts().get(0).getId();
            for (int i = 0; i < 10; i++) {
                double x = (Math.random() * ((100 - (-100)) + 1)) + (-100);
                BigDecimal value = BigDecimal.valueOf(x).setScale(2, RoundingMode.DOWN);

                AccountTransaction transaction = accountTransactionRepository.save(new AccountTransaction(account, value));
                System.out.println(transaction);
            }

            List<AccountTransaction> transactions = accountTransactionRepository.findByAccount(account);
            System.out.println("Transactions:");
            System.out.println(transactions);
        };
    }
}
