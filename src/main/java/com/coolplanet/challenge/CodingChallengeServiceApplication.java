package com.coolplanet.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.coolplanet.challenge.*"})
public class CodingChallengeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingChallengeServiceApplication.class, args);
	}

}
