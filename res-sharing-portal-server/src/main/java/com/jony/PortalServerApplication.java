package com.jony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PortalServerApplication extends SpringBootServletInitializer {

	// http://localhost:8877/doc.html
	public static void main(String[] args) {
		SpringApplication.run(PortalServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PortalServerApplication.class);
	}
}