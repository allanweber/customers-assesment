package com.allanweber.customers.api;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class RateLimitRequestFilter extends OncePerRequestFilter {
    private static final Map<String, Rate> RATE_MAP = new ConcurrentHashMap<>();

    private final RateLimitProperties rateLimitProperties;

    public RateLimitRequestFilter(RateLimitProperties rateLimitProperties) {
        super();
        this.rateLimitProperties = rateLimitProperties;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String ipAddress = request.getRemoteAddr();
        Rate clientRate = RATE_MAP.computeIfAbsent(ipAddress, k -> new Rate());
        long now = System.currentTimeMillis();

        if ((now - clientRate.time) > rateLimitProperties.getRefresh()) {
            clientRate.reset();
        }

        if (clientRate.count > rateLimitProperties.getLimit()) {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("{ \"error\":\"Rate limit exceeded\"}");
        } else {
            clientRate.countOne();
            filterChain.doFilter(request, response);
        }
    }

    private static class Rate {
        private int count;
        private Long time;

        public Rate() {
            count = 1;
            time = System.currentTimeMillis();
        }

        public void reset() {
            count = 1;
            this.time = System.currentTimeMillis();
        }

        public void countOne() {
            this.count++;
        }
    }
}
