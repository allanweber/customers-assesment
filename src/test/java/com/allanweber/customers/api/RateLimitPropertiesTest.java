package com.allanweber.customers.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties(RateLimitProperties.class)
@TestPropertySource(properties = {"customer.rate.limit=5", "customer.rate.refresh=3000"})
@ExtendWith(SpringExtension.class)
class RateLimitPropertiesTest {

    @Autowired
    RateLimitProperties rateLimitProperties;

    @DisplayName("Check the rate limits")
    @Test
    void allowed() {
        assertThat(rateLimitProperties.getLimit()).isEqualTo(5);
        assertThat(rateLimitProperties.getRefresh()).isEqualTo(3000L);
    }
}