package com.allanweber.customers.api;

import com.allanweber.customers.MySqlTestContainerInitializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = MySqlTestContainerInitializer.class)
@TestPropertySource(properties = {"customer.rate.limit=2", "customer.rate.refresh=1000"})
class RateLimitRequestFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("Successfully request twice within the rate limit but the third time fails. After a while is successful again")
    @Test
    void limiting() throws InterruptedException {
        webTestClient.get()
                .uri("/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .value(response -> assertThat(response.get("status")).isEqualTo("UP"));

        webTestClient.get()
                .uri("/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .value(response -> assertThat(response.get("status")).isEqualTo("UP"));

        webTestClient.get()
                .uri("/health")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.TOO_MANY_REQUESTS)
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .value(response -> assertThat(response.get("error")).isEqualTo("Rate limit exceeded"));

        Thread.sleep(1000);

        webTestClient.get()
                .uri("/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, String>>() {
                })
                .value(response -> assertThat(response.get("status")).isEqualTo("UP"));
    }
}