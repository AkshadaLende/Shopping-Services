package com.coding.tech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilters implements GlobalFilter, Ordered {

    private static final Logger logger =  LoggerFactory.getLogger(LoggerFactory.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       logger.info("Incoming request");
       logger.info("Method : {}", exchange.getRequest().getMethod());
       logger.info("Path : {}", exchange.getRequest().getURI());
       return chain.filter(exchange).then(
               Mono.fromRunnable(() -> logger.info("Response status : {}", exchange.getResponse().getStatusCode()))
       );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

/*Logging Filter
↓
Print request
↓
Gateway Routing Filter
↓
Product Service
↓
Gateway receives response
↓
Logging Filter
↓
Print response
↓
Return to client*/
