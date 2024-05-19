package com.allanweber.customers.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    @Mock
    JwtProperties jwtProperties;

    JwtProvider jwtProvider;

    @BeforeEach
    public void setUp() {
        when(jwtProperties.getSecret()).thenReturn("uFTZbRD2SJp4TjzMjzRh1VoRakqMOiwajx+2/zKfQA4=");
        this.jwtProvider = new JwtProvider(jwtProperties);
    }

    @DisplayName("Given user and return JWT token")
    @Test
    void generateAccessToken() {
        String token = jwtProvider.generate("user");

        Jws<Claims> claimsJws = jwtProvider.parse(token);

        String username = claimsJws.getPayload().getSubject();

        assertThat(username).isEqualTo("user");
    }
}