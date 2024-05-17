package com.allanweber.customers.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.io.Serial;

public class NotUniqueUserNameException extends HttpClientErrorException {

    public static final String USERNAME_ALREADY_TAKEN = "Username already taken";
    @Serial
    private static final long serialVersionUID = 8783475675134784819L;

    public NotUniqueUserNameException() {
        this(HttpStatus.CONFLICT, USERNAME_ALREADY_TAKEN);
    }


    public NotUniqueUserNameException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
