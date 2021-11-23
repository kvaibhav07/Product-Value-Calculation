package com.derivatemeasure.portal.DerivateMeasures;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.derivatemeasure.portal.*")
@OpenAPIDefinition(info = @Info(title = "Derivate Measures", version = "3.0", description = "Derivate Measures Microservice"))
public class DerivateMeasuresApplication {

	public static void main(String[] args) {
		SpringApplication.run(DerivateMeasuresApplication.class, args);
	}
}
