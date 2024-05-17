package com.allanweber.customers.api;

import com.allanweber.customers.register.CustomerSignUp;
import com.allanweber.customers.register.RegisterCustomer;
import com.allanweber.customers.register.SignUpResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final RegisterCustomer registerCustomer;

    public RegisterController(RegisterCustomer registerCustomer) {
        this.registerCustomer = registerCustomer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody CustomerSignUp signUp) {
        SignUpResponse response = registerCustomer.signUp(signUp);
        return ResponseEntity.ok().body(response);
    }
}
