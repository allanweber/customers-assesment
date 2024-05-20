package com.allanweber.customers.api;

import com.allanweber.customers.authentication.AuthenticatedUser;
import com.allanweber.customers.customer.CustomerAccount;
import com.allanweber.customers.customer.CustomerService;
import com.allanweber.customers.transaction.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {

    private final AuthenticatedUser authenticatedUser;
    private final CustomerService customerService;
    private final TransactionService transactionService;

    public AccountController(AuthenticatedUser authenticatedUser, CustomerService customerService, TransactionService transactionService) {
        this.authenticatedUser = authenticatedUser;
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    @GetMapping("/overview")
    public ResponseEntity<List<OverviewResponse>> overview() {
        List<CustomerAccount> accounts = customerService.retrieveAccounts(authenticatedUser.getUserName());
        List<OverviewResponse> overview = accounts.stream()
                .map(account -> {
                    BigDecimal balance = transactionService.balance(account);
                    return new OverviewResponse(account, balance);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(overview);
    }
}
