package com.yn;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.yn.threadJob.ReadDataSource;

// @EnableAutoConfiguration
@SpringBootApplication
// @ComponentScan
// @MapperScan("com.cj.dao.mapper")
//@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		//springApplication.addListeners(new ApplicationStartup());
		springApplication.run(args);
		// SpringApplication.run(Application.class, args);
		System.out.println("yn-job Start Success");

	}
}