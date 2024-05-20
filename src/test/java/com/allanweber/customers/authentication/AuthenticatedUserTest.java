package com.allanweber.customers.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticatedUserTest {

    @DisplayName("Get logged user")
    @Test
    void user() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("username", null));

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        String userName = authenticatedUser.getUserName();
        assertThat(userName).isEqualTo("username");
    }
}