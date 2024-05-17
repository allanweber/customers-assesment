package com.allanweber.customers;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MySQLContainer;

import static java.lang.String.format;

public class MySqlTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    protected static final MySQLContainer<?> container;

    static {
        container = new MySQLContainer<>("mysql:5.7");
        container.withUsername("user");
        container.withPassword("password");
        container.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext,
                format("spring.datasource.url=jdbc:mysql://%s:%s/test", container.getHost(),  container.getMappedPort(3306)));
    }
}