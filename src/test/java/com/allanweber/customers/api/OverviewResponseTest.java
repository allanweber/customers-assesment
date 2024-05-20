package com.allanweber.customers.api;

import com.allanweber.customers.customer.CustomerAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

class OverviewResponseTest {

    @DisplayName("Spread customer account and balance values to overview response")
    @Test
    void overview() {
        CustomerAccount customerAccount = new CustomerAccount("IBAN-123", "payment", "EUR");
        BigDecimal balance = BigDecimal.valueOf(123.45).setScale(2, RoundingMode.HALF_DOWN);

        OverviewResponse response = new OverviewResponse(customerAccount, balance);

        assertThat(response.getNumber()).isEqualTo("IBAN-123");
        assertThat(response.getType()).isEqualTo("payment");
        assertThat(response.getCurrency()).isEqualTo("EUR");
        assertThat(response.getBalance().toString()).isEqualTo("123.45");
    }
}