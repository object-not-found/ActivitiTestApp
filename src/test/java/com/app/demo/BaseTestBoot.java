package com.app.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("test")
public class BaseTestBoot {
	public static void main(String[] args){
		SpringApplication.run(BaseTestBoot.class, args);
	}
}
