package com.allanweber.customers.validations;

import com.allanweber.customers.validations.AdultOnlyValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AdultOnlyValidatorTest {

    AdultOnlyValidator adultOnlyValidator = new AdultOnlyValidator();

    @DisplayName("A 18 years old is valid")
    @Test
    void valid18() {
        LocalDate dateOfBirth = LocalDate.now().minusYears(18);

        boolean valid = adultOnlyValidator.isValid(dateOfBirth, null);

        assertThat(valid).isTrue();
    }

    @DisplayName("A 17 years old is invalid")
    @Test
    void invalid17() {
        LocalDate dateOfBirth = LocalDate.now().minusYears(17);

        boolean valid = adultOnlyValidator.isValid(dateOfBirth, null);

        assertThat(valid).isFalse();
    }

    @DisplayName("Null value is valid")
    @Test
    void validNull() {
        boolean valid = adultOnlyValidator.isValid(null, null);

        assertThat(valid).isTrue();
    }
}