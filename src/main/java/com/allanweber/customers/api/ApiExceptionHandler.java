package com.allanweber.customers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String UNEXPECTED_EXCEPTION_HAPPENED = "Unexpected Exception happened";

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientException(HttpClientErrorException ex) {
        final Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", ex.getStatusText());
        return status(ex.getStatusCode()).body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return status(INTERNAL_SERVER_ERROR).body(extractMessage(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return status(BAD_REQUEST).body(getBindingResult(ex.getBindingResult()));
    }

    private Map<String, List<String>> getBindingResult(BindingResult bindingResult) {
        final List<String> errors = new ArrayList<>();
        for (final ObjectError error : bindingResult.getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        final Map<String, List<String>> errorsResponse = new ConcurrentHashMap<>();
        errorsResponse.put("errors", errors);
        return errorsResponse;
    }

    private Map<String, String> extractMessage(Exception exception) {
        final String message = Optional.ofNullable(exception.getCause()).orElse(exception).getMessage();
        final Map<String, String> errors = new ConcurrentHashMap<>();
        errors.put("error", Optional.ofNullable(message).orElse(UNEXPECTED_EXCEPTION_HAPPENED));
        return errors;
    }
}
