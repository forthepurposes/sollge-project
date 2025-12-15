package com.sollge.verifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class VerifierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerifierServiceApplication.class, args);
    }
}