package com.allanweber.customers.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @DisplayName("Save a new Customer")
    @Test
    void save() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        List<CustomerAccount> accounts = singletonList(new CustomerAccount(1, "IBAN-123", "acc", "EUR"));
        Customer customer = new Customer(1, "username", "name", LocalDate.now(), "12465", "123456798", singletonList(address), accounts);

        when(customerRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);

        Customer saved = customerService.save(customer);
        assertThat(saved).isEqualTo(customer);
    }

    @DisplayName("When saving a customer with an existing username return conflict exception")
    @Test
    void usernameConflict() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        List<CustomerAccount> accounts = singletonList(new CustomerAccount(1, "IBAN-123", "acc", "EUR"));
        Customer customer = new Customer(1, "username", "name", LocalDate.now(), "12465", "123456798", singletonList(address), accounts);

        when(customerRepository.findByUsername("username")).thenReturn(Optional.of(customer));

        NotUniqueUserNameException exception = assertThrows(NotUniqueUserNameException.class, () ->
                customerService.save(customer)
        );

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(exception.getStatusText()).isEqualTo("Username already taken");

        verify(customerRepository, never()).save(any());
    }
}