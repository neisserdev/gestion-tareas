package com.tareas.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ApiTareasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTareasApplication.class, args);
	}
}
