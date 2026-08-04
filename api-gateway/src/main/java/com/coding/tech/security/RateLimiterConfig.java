package com.coding.tech.security;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String username = exchange.getRequest().getHeaders().getFirst("X-Authenticated-User");

            if (username == null) {
                username = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();

            }
            System.out.println("username"+username);
            return Mono.just(username);

        };
    }
}
