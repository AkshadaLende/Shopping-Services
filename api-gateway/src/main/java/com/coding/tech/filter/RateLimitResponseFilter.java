package com.coding.tech.filter;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class RateLimitResponseFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Inside ratelimitresponse");
        return chain.filter(exchange)
                .then(Mono.defer(() -> {

                    if (exchange.getResponse().getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {

                        String response = """
                                 {
                                   "status":429,
                                   "message":"Rate limit exceeded. Please try again later."
                                }
                                """;

                        exchange.getResponse().getHeaders()
                                .setContentType(MediaType.APPLICATION_JSON);

                        return exchange.getResponse().writeWith(
                                Mono.just(exchange.getResponse()
                                        .bufferFactory()
                                        .wrap(response.getBytes(StandardCharsets.UTF_8)))
                        );
                    }

                    return Mono.empty();
                }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
