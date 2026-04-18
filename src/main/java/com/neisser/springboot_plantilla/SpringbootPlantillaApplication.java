package com.neisser.springboot_plantilla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringbootPlantillaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootPlantillaApplication.class, args);
	}
}
