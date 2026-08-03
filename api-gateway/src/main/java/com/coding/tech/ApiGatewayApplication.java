package com.coding.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
/*
                        Internet
                            │
                                    ▼
                  Spring Cloud Gateway
                            │
        ┌─────────────────────────────────────────────┐
        │                                             │
        │ 1. Correlation ID Filter                    │
        │ 2. Request Logging                          │
        │ 3. JWT Authentication                       │
        │ 4. Authorization                            │
        │ 5. Rate Limiting                            │
        │ 6. Request Validation                       │
        │ 7. Request Transformation                   │
        │ 8. Circuit Breaker                          │
        │ 9. Metrics                                  │
        │10. Distributed Tracing                      │
        └─────────────────────────────────────────────┘
                           │
        ┌──────────────────┼──────────────────┐
        ▼                  ▼                  ▼
Product Service     Order Service    Inventory Service*/
