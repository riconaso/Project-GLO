package com.corso.ProjectGLO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProjectGloApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectGloApplication.class, args);
	}

}
