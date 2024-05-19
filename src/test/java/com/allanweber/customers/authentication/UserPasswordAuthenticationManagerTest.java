package com.allanweber.customers.authentication;

import com.allanweber.customers.customer.Customer;
import com.allanweber.customers.customer.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserPasswordAuthenticationManagerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    UserPasswordAuthenticationManager userPasswordAuthenticationManager;

    @DisplayName("Customer successful authenticated")
    @Test
    void authenticate() {
        when(customerService.findByUsername("username")).thenReturn(
                Optional.of(new Customer("username", "name", LocalDate.now(), "12465", "password", null,null))
        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("username", "password");
        Authentication authenticate = userPasswordAuthenticationManager.authenticate(authentication);

        assertThat(authenticate.isAuthenticated()).isTrue();
        assertThat(authenticate.getPrincipal()).isEqualTo("username");
        assertThat(authenticate.getCredentials()).isNull();
    }

    @DisplayName("Customer not authenticated due to invalid username")
    @Test
    void invalidUsername() {
        when(customerService.findByUsername("username")).thenReturn(                Optional.empty()        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("username", "password");
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userPasswordAuthenticationManager.authenticate(authentication));

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
    }

    @DisplayName("Customer not authenticated due to invalid password")
    @Test
    void invalidPassword() {
        when(customerService.findByUsername("username")).thenReturn(
                Optional.of(new Customer("username", "name", LocalDate.now(), "12465", "password1", null,null))
        );

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("username", "password");
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userPasswordAuthenticationManager.authenticate(authentication));

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
    }
}