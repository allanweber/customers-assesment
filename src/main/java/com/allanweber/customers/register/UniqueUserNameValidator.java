package com.allanweber.customers.register;

import com.allanweber.customers.customer.CustomerService;
import com.allanweber.customers.customer.NotUniqueUserNameException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueUserNameValidator implements ConstraintValidator<UniqueUserName, String> {

    private final CustomerService customerService;

    public UniqueUserNameValidator(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (!customerService.isUniqueUserName(username)) {
            isValid = false;
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(NotUniqueUserNameException.USERNAME_ALREADY_TAKEN).addConstraintViolation();
        }
        return isValid;
    }
}
