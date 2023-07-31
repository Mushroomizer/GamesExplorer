package com.cuan.gamesexplorer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ResponseEntity<?> httpSuccessMessage() {
        return ResponseEntity.ok("Success! The request was processed successfully.");
    }
}
