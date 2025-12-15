package com.sollge.gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GPSDeviceApplication {

    static void main(String[] args) {
        SpringApplication.run(GPSDeviceApplication.class, args);
    }
}
