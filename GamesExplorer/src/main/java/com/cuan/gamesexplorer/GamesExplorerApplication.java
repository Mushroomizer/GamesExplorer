package com.cuan.gamesexplorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GamesExplorerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamesExplorerApplication.class, args);
	}

}
