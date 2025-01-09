package com.saif.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EntityScan(basePackages = "com.saif.project.Model")
@SpringBootApplication
@EntityScan(basePackages = "com.saif.project")
public class ProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
}
