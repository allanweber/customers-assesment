package com.allanweber.customers.authentication;

import com.allanweber.customers.api.RateLimitRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.stream.Stream;

@Configuration
public class SecurityConfiguration {

    private final ServerAuthenticationExceptionEntryPoint serverAuthenticationExceptionEntryPoint;
    private final JwtProvider jwtProvider;
    private final UserPasswordAuthenticationManager authenticationManager;
    private final RateLimitRequestFilter rateLimitRequestFilter;

    public SecurityConfiguration(ServerAuthenticationExceptionEntryPoint serverAuthenticationExceptionEntryPoint, JwtProvider jwtProvider, UserPasswordAuthenticationManager authenticationManager, RateLimitRequestFilter rateLimitRequestFilter) {
        this.serverAuthenticationExceptionEntryPoint = serverAuthenticationExceptionEntryPoint;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
        this.rateLimitRequestFilter = rateLimitRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling(custom -> custom.authenticationEntryPoint(serverAuthenticationExceptionEntryPoint))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers(getPublicPath()).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(rateLimitRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return httpSecurity.authenticationManager(authenticationManager).build();
    }

    private String[] getPublicPath() {
        String[] monitoring = {"/health/**"};
        String[] authentication = {"/register", "/logon"};
        return Stream.of(monitoring, authentication).flatMap(Stream::of).toArray(String[]::new);
    }
}
