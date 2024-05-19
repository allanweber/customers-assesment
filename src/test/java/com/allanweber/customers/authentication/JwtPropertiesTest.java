package com.allanweber.customers.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties(JwtProperties.class)
@TestPropertySource(properties = "customers.jwt.secret=secret123")
@ExtendWith(SpringExtension.class)
class JwtPropertiesTest {

    @Autowired
    JwtProperties JwtProperties;

    @DisplayName("Check the jwt secret")
    @Test
    void allowed() {
        String secret = JwtProperties.getSecret();
        assertThat(secret).isEqualTo("secret123");
    }
}