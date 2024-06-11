package com.adepuu.montrackbackend;

import com.adepuu.montrackbackend.config.RsaKeyConfigProperties;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@SpringBootApplication
@Log
public class MontrackBackendApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(MontrackBackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Montrack Backend Application started successfully");
  }
}
