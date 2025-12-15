package com.sollge.gateway;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ApiGatewayApplication {

    static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}