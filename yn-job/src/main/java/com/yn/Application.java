package com.yn;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @EnableAutoConfiguration
@SpringBootApplication
// @ComponentScan
// @MapperScan("com.cj.lzyqssn.mapper")
public class Application {
	private static Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("yn-job Start Success");
	}
}