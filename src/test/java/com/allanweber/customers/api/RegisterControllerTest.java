package com.allanweber.customers.api;

import com.allanweber.customers.MySqlTestContainerInitializer;
import com.allanweber.customers.customer.CustomerAddress;
import com.allanweber.customers.register.CustomerSignUp;
import com.allanweber.customers.register.SignUpResponse;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MySqlTestContainerInitializer.class)
class RegisterControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Register a new Customer")
    @Test
    void register() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        CustomerSignUp signUp = new CustomerSignUp("username", "name", LocalDate.now().minusYears(20), "123456", address);

        webTestClient.post()
                .uri("/register")
                .bodyValue(signUp)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SignUpResponse.class)
                .value(response -> {
                    assertThat(response.username()).isEqualTo("username");
                    assertThat(response.password()).isNotBlank();
                });
    }

    @DisplayName("Try to register a new customer with invalid data return error messages")
    @Test
    void invalidRegister() {
        CustomerSignUp signUp = new CustomerSignUp(null, null, null, null, null);

        webTestClient.post()
                .uri("/register")
                .bodyValue(signUp)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(new ParameterizedTypeReference<Map<String, List<String>>>() {
                })
                .value(response -> {
                    assertThat(response.get("errors")).hasSize(5);
                    assertThat(response.get("errors")).containsExactlyInAnyOrder(
                            "Date is required",
                            "Name is required",
                            "Username is required",
                            "Document is required",
                            "Address is required"
                    );
                });
    }
}