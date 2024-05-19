package com.allanweber.customers.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtProvider {

    private static final String HEADER_TYP = "typ";
    private static final String TOKEN_TYPE = "JWT";
    private static final String TOKEN_ISSUER = "customers-api";
    private static final long TOKEN_DURATION_SECONDS = TimeUnit.HOURS.toSeconds(1);

    private final SecretKey secretKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generate(String user) {
        return Jwts.builder()
                .signWith(secretKey, Jwts.SIG.HS256)
                .header().add(HEADER_TYP, TOKEN_TYPE)
                .and()
                .issuer(TOKEN_ISSUER)
                .audience().add(TOKEN_ISSUER)
                .and().subject(user)
                .issuedAt(new Date())
                .expiration(Date.from(LocalDateTime.now().plusSeconds(TOKEN_DURATION_SECONDS).atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    public Jws<Claims> parse(String token) {
        String cleanToken = token.replace(JwtTokenAuthenticationFilter.TOKEN_PREFIX, "");
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(cleanToken);
    }


}
