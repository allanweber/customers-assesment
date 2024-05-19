package com.allanweber.customers.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ApiExceptionHandlerTest {
    ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @Test
    @DisplayName("When handle HttpClientErrorException with no message, return status as message")
    void handleClientExceptionNoMessage() {
        ResponseEntity<Map<String, String>> response = apiExceptionHandler.handleClientException(
                new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThat(response.getStatusCode().value()).isEqualTo(404);
        assertThat(Objects.requireNonNull(response.getBody()).get("error")).isEqualTo("NOT_FOUND");
    }

    @Test
    @DisplayName("When handle HttpClientErrorException message, return message")
    void handleClientExceptionWithMessage() {
        ResponseEntity<Map<String, String>> response = apiExceptionHandler.handleClientException(
                new HttpClientErrorException(HttpStatus.BAD_REQUEST, "any message"));

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(Objects.requireNonNull(response.getBody()).get("error")).isEqualTo("any message");
    }

    @Test
    @DisplayName("When handle Exception, return message and status 500")
    void handleException() {
        ResponseEntity<Map<String, String>> response = apiExceptionHandler
                .handleException(new RuntimeException("any message"));
        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(Objects.requireNonNull(response.getBody()).get("error")).isEqualTo("any message");
    }

    @Test
    @DisplayName("When handle Exception with cause, return cause as message and status 500")
    void handleExceptionWithCause() {
        RuntimeException causeMessage = new RuntimeException("cause message");
        ResponseEntity<Map<String, String>> response = apiExceptionHandler
                .handleException(new RuntimeException("any message", causeMessage));
        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(Objects.requireNonNull(response.getBody()).get("error")).isEqualTo("cause message");
    }

    @Test
    @DisplayName("When handle UsernameNotFoundException return message and status 401")
    void handleUsernameNotFoundException() {
        ResponseEntity<Map<String, String>> response = apiExceptionHandler.handleException(new UsernameNotFoundException("cause message"));
        assertThat(response.getStatusCode().value()).isEqualTo(401);
        assertThat(Objects.requireNonNull(response.getBody()).get("error")).isEqualTo("cause message");
    }

    @Test
    @DisplayName("When handle MethodArgumentNotValidException, return message for FieldError")
    void handleMethodArgumentNotValidExceptionWithFieldError() {
        FieldError fieldError = mock(FieldError.class);
        when(fieldError.getField()).thenReturn("name");
        when(fieldError.getDefaultMessage()).thenReturn("default message");
        when(fieldError.getCodes()).thenReturn(new String[]{"message"});

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodParameter methodParameter = mock(MethodParameter.class);
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, List<String>>> response = apiExceptionHandler.handleMethodArgumentNotValidException(ex);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(Objects.requireNonNull(response.getBody()).get("errors").get(0)).isEqualTo("default message");
    }
}