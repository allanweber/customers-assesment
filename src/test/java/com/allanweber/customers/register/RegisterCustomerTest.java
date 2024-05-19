package com.allanweber.customers.register;

import com.allanweber.customers.customer.*;
import com.allanweber.customers.customer.IbanGenerator;
import com.allanweber.customers.authentication.PasswordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterCustomerTest {

    @Mock
    CustomerService customerService;

    @Mock
    PasswordGenerator passwordGenerator;

    @Mock
    IbanGenerator ibanGenerator;

    @InjectMocks
    RegisterCustomer registerCustomer;

    @DisplayName("Register a new customer")
    @Test
    void register() {
        CustomerAddress address = new CustomerAddress("NL", "1234", "1");
        CustomerSignUp signUp = new CustomerSignUp("username", "name", LocalDate.now().minusYears(20), "123456", address);

        when(ibanGenerator.generate("NL")).thenReturn("NL29ABNA3667086008");
        when(passwordGenerator.generate()).thenReturn("123456798");

        List<CustomerAccount> accounts = singletonList(new CustomerAccount(1,"IBAN-123", "acc", "EUR"));
        Customer customer = new Customer(1,signUp.username(), signUp.name(), signUp.dateOfBirth(), signUp.documentNumber(), "123456798", singletonList(address), accounts);
        when(customerService.save(any())).thenReturn(customer);

        SignUpResponse response = registerCustomer.signUp(signUp);

        assertThat(response.username()).isEqualTo("username");
        assertThat(response.password()).isEqualTo("123456798");
    }
}