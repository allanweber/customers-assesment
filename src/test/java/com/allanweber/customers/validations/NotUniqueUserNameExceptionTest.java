package com.allanweber.customers.validations;

import com.allanweber.customers.validations.NotUniqueUserNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NotUniqueUserNameExceptionTest {

    @DisplayName("Default instance is 409 status and default message")
    @Test
    void defaultInstance() {
        NotUniqueUserNameException exception = assertThrows(NotUniqueUserNameException.class, () -> {
            throw new NotUniqueUserNameException();
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(exception.getStatusText()).isEqualTo("Username already taken");
    }

    @DisplayName("New custom status and message")
    @Test
    void statusMessage() {
        NotUniqueUserNameException exception = assertThrows(NotUniqueUserNameException.class, () -> {
            throw new NotUniqueUserNameException(HttpStatus.BAD_REQUEST, "message");
        });

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getStatusText()).isEqualTo("message");
    }
}