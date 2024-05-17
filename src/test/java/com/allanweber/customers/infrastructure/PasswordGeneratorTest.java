package com.allanweber.customers.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordGeneratorTest {

    PasswordGenerator passwordGenerator = new PasswordGenerator();

    @DisplayName("Generate a new password")
    @Test
    void generate() {
        String password = passwordGenerator.generate();
        assertThat(password).isNotBlank();
    }

}