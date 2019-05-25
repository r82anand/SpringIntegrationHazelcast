package com.sivadas.anand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableIntegration
@EnableScheduling
public class SpringBootHazelcastReplicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHazelcastReplicaApplication.class, args);
	}

}
