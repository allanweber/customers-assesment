package com.allanweber.customers.register;

import com.allanweber.customers.customer.CustomerAddress;
import com.allanweber.customers.validations.AdultOnly;
import com.allanweber.customers.validations.UniqueUserName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CustomerSignUp(
        @NotBlank(message = "Username is required") @UniqueUserName String username,
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "Date is required")
        @JsonFormat(pattern = "dd-MM-yyyy")
        @AdultOnly
        LocalDate dateOfBirth,
        @NotBlank(message = "Document is required") String documentNumber,
        @NotNull(message = "Address is required") @Valid CustomerAddress address
) {
}
