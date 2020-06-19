package com.pec.personalexpensescontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PecApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {SpringApplication.run(PecApplication.class, args);}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PecApplication.class);
	}
}
