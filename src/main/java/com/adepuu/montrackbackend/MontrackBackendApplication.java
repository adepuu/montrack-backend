package com.adepuu.montrackbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MontrackBackendApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MontrackBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
