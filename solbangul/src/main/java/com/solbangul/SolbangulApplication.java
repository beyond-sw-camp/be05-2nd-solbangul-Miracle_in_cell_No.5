package com.solbangul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SolbangulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolbangulApplication.class, args);
	}

}
