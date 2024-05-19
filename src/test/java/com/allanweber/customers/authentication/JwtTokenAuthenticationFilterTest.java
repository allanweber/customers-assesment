package com.allanweber.customers.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenAuthenticationFilterTest {

    @Mock
    JwtProperties jwtProperties;

    JwtProvider jwtProvider;
    JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    @BeforeEach
    public void setup() {
        when(jwtProperties.getSecret()).thenReturn("uFTZbRD2SJp4TjzMjzRh1VoRakqMOiwajx+2/zKfQA4=");
        jwtProvider = new JwtProvider(jwtProperties);
        jwtTokenAuthenticationFilter = new JwtTokenAuthenticationFilter(jwtProvider);
    }

    @DisplayName("Given request with token process request successfully")
    @Test
    void serverRequestSuccess() throws ServletException, IOException {
        String token = jwtProvider.generate("username");

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockFilterChain chain = new MockFilterChain();

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer ".concat(token));

        jwtTokenAuthenticationFilter.doFilterInternal(request, response, chain);
    }

    @Test
    @DisplayName("Given request without token process request successfully")
    void serverRequestSuccessWithoutToken() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        MockFilterChain chain = new MockFilterChain();

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
        jwtTokenAuthenticationFilter.doFilterInternal(request, response, chain);
    }
}