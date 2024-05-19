package com.allanweber.customers.authentication;

import com.allanweber.customers.customer.Customer;
import com.allanweber.customers.customer.CustomerService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
public class UserPasswordAuthenticationManager implements AuthenticationManager {

    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";
    private final CustomerService customerService;

    public UserPasswordAuthenticationManager(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        Customer customer = customerService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(INVALID_USERNAME_OR_PASSWORD));

        String password = (String) authentication.getCredentials();
        if (!password.equals(customer.getPassword())) {
            throw new UsernameNotFoundException(INVALID_USERNAME_OR_PASSWORD);
        }
        return new UsernamePasswordAuthenticationToken(username, null, singletonList(new SimpleGrantedAuthority("USER")));
    }
}
