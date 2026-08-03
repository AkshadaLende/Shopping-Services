package com.coding.tech.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.UUID;

@Component
public class CorrelationLoggingFilter implements GlobalFilter, Ordered {

    public static final String HEADER = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest serverHttpRequest = exchange.getRequest();

        String correlationId = serverHttpRequest.getHeaders().getFirst(HEADER);

        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
        }
        ServerHttpRequest mutatedRequest = serverHttpRequest.mutate().header(HEADER, correlationId).build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();


        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
