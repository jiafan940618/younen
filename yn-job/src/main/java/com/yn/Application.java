package com.yn;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// @EnableAutoConfiguration
@SpringBootApplication
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
// @ComponentScan
// @MapperScan("com.cj.dao.mapper")
//@EnableScheduling
public class Application {
 
	public static void main(String[] args) throws IOException { 
		SpringApplication springApplication = new SpringApplication(Application.class);
//		springApplication.addListeners(new ApplicationStartup());
		springApplication.run(args);
		System.out.println("yn-job Start Success");
	}
} 