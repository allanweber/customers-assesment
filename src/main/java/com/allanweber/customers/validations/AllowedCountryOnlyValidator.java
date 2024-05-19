package com.allanweber.customers.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AllowedCountryOnlyValidator implements ConstraintValidator<AllowedCountryOnly, String> {

    private final AvailableCountriesProperties availableCountriesProperties;
    private final String countries;

    public AllowedCountryOnlyValidator(AvailableCountriesProperties availableCountriesProperties) {
        this.availableCountriesProperties = availableCountriesProperties;
        countries = String.join(" or ", availableCountriesProperties.getAvailable());
    }

    @Override
    public boolean isValid(String country, ConstraintValidatorContext context) {
        boolean isValid = availableCountriesProperties.getAvailable().contains(country.toUpperCase(Locale.getDefault()));
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(String.format("Only customer from %s are allowed", countries)).addConstraintViolation();
        }
        return isValid;
    }
}
