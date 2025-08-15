package com.example.UniversityManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication(scanBasePackages = "com.example.UniversityManagementSystem")
@EntityScan("com.example.UniversityManagementSystem.model")
@EnableJpaAuditing(auditorAwareRef="auditAwareImp")
public class UniversityManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniversityManagementSystemApplication.class, args);
	}

}
