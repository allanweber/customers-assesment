package com.allanweber.customers.api;

import com.allanweber.customers.authentication.CustomerSignIn;
import com.allanweber.customers.authentication.JwtProvider;
import com.allanweber.customers.authentication.SignInResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logon")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SignInResponse> sign(@Valid @RequestBody CustomerSignIn signin) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signin.username(), signin.password()));
        String username = (String) authenticate.getPrincipal();
        String token = jwtProvider.generate(username);
        return ResponseEntity.ok().body(new SignInResponse(token));
    }
}
