package com.allanweber.customers.authentication;

import jakarta.validation.constraints.NotBlank;

public record CustomerSignIn(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password
) {
}
