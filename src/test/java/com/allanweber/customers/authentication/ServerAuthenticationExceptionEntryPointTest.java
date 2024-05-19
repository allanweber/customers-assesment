package com.allanweber.customers.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

class ServerAuthenticationExceptionEntryPointTest {

    private final ServerAuthenticationExceptionEntryPoint authenticationEntryPoint = new ServerAuthenticationExceptionEntryPoint();

    @DisplayName("Given server request entry point must return Unauthorised")
    @Test
    public void commence() throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/path");
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException ex = new AuthenticationCredentialsNotFoundException("message");

        authenticationEntryPoint.commence(request, response, ex);
    }
}