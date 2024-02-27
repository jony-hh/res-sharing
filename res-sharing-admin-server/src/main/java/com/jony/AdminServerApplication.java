package com.jony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AdminServerApplication extends SpringBootServletInitializer {

	// http://localhost:8866/api/doc.html
	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminServerApplication.class);
	}
}