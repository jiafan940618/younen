package com.yn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.yn.dao.mapper")
@PropertySource("application.properties")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
