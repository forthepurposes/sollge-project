package com.sollge.rental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RentalServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(RentalServiceApplication.class, args);
    }
}