package com.allanweber.customers.register;

import com.allanweber.customers.customer.CustomerService;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniqueUserNameValidatorTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    UniqueUserNameValidator uniqueUserNameValidator;

    ConstraintValidatorContext constraintContext;

    @BeforeEach
    public void setUp() {
        constraintContext = mock(ConstraintValidatorContext.class);
        ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder = mock(
                ConstraintValidatorContext.ConstraintViolationBuilder.class);
        lenient().when(constraintContext.buildConstraintViolationWithTemplate(anyString()))
                .thenReturn(constraintViolationBuilder);
    }

    @DisplayName("User name is unique")
    @Test
    void unique() {
        String username = "username";
        when(customerService.isUniqueUserName(username)).thenReturn(true);
        boolean valid = uniqueUserNameValidator.isValid(username, constraintContext);
        assertThat(valid).isTrue();
    }

    @DisplayName("User name is not unique")
    @Test
    void notUnique() {
        String username = "username";
        when(customerService.isUniqueUserName(username)).thenReturn(false);
        boolean valid = uniqueUserNameValidator.isValid(username, constraintContext);
        assertThat(valid).isFalse();
    }
}