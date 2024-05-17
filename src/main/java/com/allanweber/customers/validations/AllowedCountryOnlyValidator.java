package com.allanweber.customers.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AllowedCountryOnlyValidator implements ConstraintValidator<AllowedCountryOnly, String> {

    private final AvailableCountries availableCountries;
    private final String countries;

    public AllowedCountryOnlyValidator(AvailableCountries availableCountries) {
        this.availableCountries = availableCountries;
        countries = String.join(" or ", availableCountries.getAvailable());
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext context) {
        boolean isValid = availableCountries.getAvailable().contains(country.toUpperCase(Locale.getDefault()));
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("Only customer from %s are allowed", countries)).addConstraintViolation();
        }
        return isValid;
    }
}
