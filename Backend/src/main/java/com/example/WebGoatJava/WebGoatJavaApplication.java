package com.example.WebGoatJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class WebGoatJavaApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebGoatJavaApplication.class, args);
	}
}
