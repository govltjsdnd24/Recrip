package com.ssafy.enjoytrip.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.enjoytrip"})
public class EnjoyTripSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnjoyTripSpringApplication.class, args);
	}

}
