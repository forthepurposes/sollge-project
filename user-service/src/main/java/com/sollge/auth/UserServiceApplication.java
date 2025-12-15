package com.sollge.auth;

import com.sollge.auth.token.JsonTokenProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JsonTokenProperties.class)
public class UserServiceApplication {

    static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}