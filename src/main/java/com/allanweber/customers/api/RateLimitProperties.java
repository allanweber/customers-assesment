package com.allanweber.customers.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("customer.rate")
@Configuration
public class RateLimitProperties {

    private int limit;

    private Long refresh;

    public int getLimit() {
        return limit;
    }

    public Long getRefresh() {
        return refresh;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setRefresh(Long refresh) {
        this.refresh = refresh;
    }
}
