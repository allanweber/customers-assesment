package com.allanweber.customers.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowedCountryOnlyValidator.class)
public @interface AllowedCountryOnly {
    String message() default "Country not allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
