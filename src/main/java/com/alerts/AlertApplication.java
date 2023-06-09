package com.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.alerts" })
public class AlertApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertApplication.class, args);
	}
}
