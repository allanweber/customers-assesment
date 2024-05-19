package com.allanweber.customers.validations;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AllowedCountryOnlyValidatorTest {

    @Mock
    AvailableCountriesProperties availableCountriesProperties;

    @InjectMocks
    AllowedCountryOnlyValidator allowedCountryOnlyValidator;

    ConstraintValidatorContext constraintContext;

    @BeforeEach
    void setup() {
        constraintContext = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder = mock(
                ConstraintValidatorContext.ConstraintViolationBuilder.class);
        lenient().when(constraintContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);

        when(availableCountriesProperties.getAvailable()).thenReturn(asList("NL", "BE"));
    }

    @DisplayName("Given valid countries return true")
    @ParameterizedTest
    @ValueSource(strings = {"NL", "BE", "nl", "be"})
    void valid(String country) {
        boolean valid = allowedCountryOnlyValidator.isValid(country, constraintContext);
        assertThat(valid).isTrue();
    }

    @DisplayName("Given invalid countries return true")
    @ParameterizedTest
    @ValueSource(strings = {"PT", "GB"})
    void invalid(String country) {
        boolean valid = allowedCountryOnlyValidator.isValid(country, constraintContext);
        assertThat(valid).isFalse();
    }
}