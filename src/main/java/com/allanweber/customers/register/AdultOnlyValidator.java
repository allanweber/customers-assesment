package com.allanweber.customers.register;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class AdultOnlyValidator implements ConstraintValidator<AdultOnly, LocalDate> {

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (Objects.nonNull(dateOfBirth)) {
            Period period = Period.between(dateOfBirth, LocalDate.now());
            isValid = period.getYears() >= 18;
        }
        return isValid;
    }
}
