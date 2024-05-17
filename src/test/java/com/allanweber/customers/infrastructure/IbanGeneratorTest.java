package com.allanweber.customers.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IbanGeneratorTest {

    IbanGenerator ibanGenerator = new IbanGenerator();

    @DisplayName("Generate an Iban")
    @Test
    void generate() {
        String iban = ibanGenerator.generate();
        assertThat(iban).isNotBlank();
    }

}