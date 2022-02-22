package com.example.fortunaball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FortunaBallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FortunaBallApplication.class, args);
    }

}
