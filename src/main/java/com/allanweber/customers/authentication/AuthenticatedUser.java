package com.allanweber.customers.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
