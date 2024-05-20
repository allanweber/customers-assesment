package com.allanweber.customers.api;

import com.allanweber.customers.MySqlTestContainerInitializer;
import com.allanweber.customers.authentication.JwtProvider;
import com.allanweber.customers.customer.Customer;
import com.allanweber.customers.customer.CustomerAccount;
import com.allanweber.customers.customer.CustomerAddress;
import com.allanweber.customers.customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MySqlTestContainerInitializer.class)
class AccountControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @AfterEach
    void cleanup() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @DisplayName("Retrieve customer account overview")
    @Test
    void overview() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        List<CustomerAccount> accounts = singletonList(new CustomerAccount(1, "IBAN-123", "acc", "EUR"));
        Customer customer = new Customer("username", "name", LocalDate.now(), "12465", "123456798", singletonList(address), accounts);
        customerRepository.save(customer);
        String token = jwtProvider.generate("username");

        webTestClient.get()
                .uri("/overview")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ".concat(token))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<List<OverviewResponse>>() {
                })
                .value(response -> {
                    assertThat(response).hasSize(1);
                    assertThat(response).extracting(OverviewResponse::getNumber).containsExactly("IBAN-123");
                    assertThat(response).extracting(OverviewResponse::getType).containsExactly("acc");
                    assertThat(response).extracting(OverviewResponse::getCurrency).containsExactly("EUR");
                    assertThat(response).extracting(OverviewResponse::getBalance).isNotNull();
                });


        customerRepository.deleteAll();
    }
}