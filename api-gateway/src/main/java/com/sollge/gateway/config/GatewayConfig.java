package com.sollge.gateway.config;

import lombok.RequiredArgsConstructor;
import com.sollge.gateway.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", predicate -> predicate
                        .path("/api/v1/users/**")
                        .uri("lb://user-service"))
                .route("rental-service", predicate -> predicate
                        .path("/api/v1/rentals/**")
                        .filters(filter -> filter.filters(authFilter))
                        .uri("lb://rental-service"))
                .build();
    }
}

