package com.allanweber.customers.validations;

import com.allanweber.customers.validations.AvailableCountries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@EnableConfigurationProperties(AvailableCountries.class)
@TestPropertySource(properties = "customers.countries.available=GB,PT")
@ExtendWith(SpringExtension.class)
class AvailableCountriesTest {

    @Autowired
    AvailableCountries availableCountries;

    @DisplayName("Check the available countries")
    @Test
    void allowed() {
        List<String> allowed = availableCountries.getAvailable();
        assertThat(allowed).containsExactly("GB", "PT");
    }
}