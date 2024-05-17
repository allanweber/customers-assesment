package com.allanweber.customers.customer;

import com.allanweber.customers.validations.AllowedCountryOnly;
import jakarta.validation.constraints.NotBlank;

public record CustomerAddress(
        @NotBlank(message = "Country is required") @AllowedCountryOnly String country,
        @NotBlank(message = "Postal code is required") String postalCode,
        @NotBlank(message = "House number is required") String houseNumber) {
}
