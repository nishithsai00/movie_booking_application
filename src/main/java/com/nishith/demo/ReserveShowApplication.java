package com.nishith.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReserveShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReserveShowApplication.class, args);
	}

}
