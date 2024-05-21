package com.allanweber.customers;

import com.allanweber.customers.api.RateLimitProperties;
import com.allanweber.customers.authentication.JwtProperties;
import com.allanweber.customers.validations.AvailableCountriesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({AvailableCountriesProperties.class, JwtProperties.class, RateLimitProperties.class})
@SpringBootApplication
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }
}
