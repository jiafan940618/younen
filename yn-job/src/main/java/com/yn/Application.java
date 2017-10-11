package com.yn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableAutoConfiguration
@SpringBootApplication
// @ComponentScan
// @MapperScan("com.cj.lzyqssn.mapper")
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("yn-job Start Success");
	}
}