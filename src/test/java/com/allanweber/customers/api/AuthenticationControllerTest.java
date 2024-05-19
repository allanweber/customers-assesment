package com.allanweber.customers.api;

import com.allanweber.customers.MySqlTestContainerInitializer;
import com.allanweber.customers.authentication.CustomerSignIn;
import com.allanweber.customers.authentication.SignInResponse;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MySqlTestContainerInitializer.class)
class AuthenticationControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @DisplayName("Successfully authenticate a customer")
    @Test
    void authenticate() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        List<CustomerAccount> accounts = singletonList(new CustomerAccount(1, "IBAN-123", "acc", "EUR"));
        Customer customer = new Customer("username", "name", LocalDate.now(), "12465", "123456798", singletonList(address), accounts);
        customerRepository.save(customer);

        CustomerSignIn sign = new CustomerSignIn("username", "123456798");
        webTestClient.post()
                .uri("/logon")
                .bodyValue(sign)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SignInResponse.class)
                .value(response ->
                        assertThat(response.token()).isNotBlank()
                );
    }

    @DisplayName("Fail to authenticate a customer dues to invalid username")
    @Test
    void authenticateFailUsername() {
        CustomerSignIn sign = new CustomerSignIn("username", "12345678");
        webTestClient.post()
                .uri("/logon")
                .bodyValue(sign)
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .value(response -> {
                    assertThat(response.get("error")).isEqualTo("Invalid username or password");
                });
    }

    @DisplayName("Fail to authenticate a customer dues to invalid password")
    @Test
    void authenticateFailPassword() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        List<CustomerAccount> accounts = singletonList(new CustomerAccount(1, "IBAN-123", "acc", "EUR"));
        Customer customer = new Customer("username", "name", LocalDate.now(), "12465", "123456798", singletonList(address), accounts);
        customerRepository.save(customer);

        CustomerSignIn sign = new CustomerSignIn("username", "123456789");
        webTestClient.post()
                .uri("/logon")
                .bodyValue(sign)
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .value(response -> {
                    assertThat(response.get("error")).isEqualTo("Invalid username or password");
                });
    }
}