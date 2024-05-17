package com.allanweber.customers.infrastructure;

import com.allanweber.customers.customer.CustomerAccount;
import com.allanweber.customers.customer.CustomerAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IbanGeneratorTest {

    @Mock
    CustomerAccountRepository customerAccountRepository;

    @InjectMocks
    IbanGenerator ibanGenerator;

    @DisplayName("Generate an Iban for NL")
    @Test
    void generate() {
        when(customerAccountRepository.findByIban(anyString())).thenReturn(Optional.empty());
        String iban = ibanGenerator.generate("NL");
        assertThat(iban).isNotBlank();

        verify(customerAccountRepository).findByIban(anyString());
    }

    @DisplayName("When generates an Iban for NL if the Iban is already taken, generate a new one")
    @Test
    void generateTwice() {
        when(customerAccountRepository.findByIban(anyString()))
                .thenReturn(Optional.of(new CustomerAccount()))
                .thenReturn(Optional.empty());

        String iban = ibanGenerator.generate("NL");
        assertThat(iban).isNotBlank();

        verify(customerAccountRepository, times(2)).findByIban(anyString());
    }

    @DisplayName("When generates an Iban for NL if the Iban is already taken, generate a until not found")
    @Test
    void generateFourTimes() {
        when(customerAccountRepository.findByIban(anyString()))
                .thenReturn(Optional.of(new CustomerAccount()))
                .thenReturn(Optional.of(new CustomerAccount()))
                .thenReturn(Optional.of(new CustomerAccount()))
                .thenReturn(Optional.empty());

        String iban = ibanGenerator.generate("NL");
        assertThat(iban).isNotBlank();

        verify(customerAccountRepository, times(4)).findByIban(anyString());
    }
}