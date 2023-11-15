package com.ssafy.recrip.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.recrip"})
public class EnjoyTripSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnjoyTripSpringApplication.class, args);
	}

}
