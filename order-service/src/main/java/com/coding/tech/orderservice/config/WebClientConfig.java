package com.coding.tech.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webclientBuilder(){
        return WebClient.builder();
    }
// creating WebClient.Builder bean and remove build method and annotated with loadBalanced annotation and what i will do
// add the load balancing capabilities at client side to web client builder
// whenever we create the webclient builder it will automatically create the client side load balancer and use client side load balancing to call inventory service
// although order service finds multiple instances of inventory service it won't get confused
}
