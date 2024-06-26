package com.allanweber.customers.validations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties("customers.countries")
@Configuration
public class AvailableCountriesProperties {

    private List<String> available;

    public List<String> getAvailable() {
        return available;
    }

    public void setAvailable(List<String> available) {
        this.available = available;
    }
}
