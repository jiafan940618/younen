package com.yn;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.yn.threadJob.ReadDataSource;


public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ReadDataSource readDataSource = event.getApplicationContext().getBean(ReadDataSource.class);
		System.out.println("跑。");
		readDataSource.start();
	}
}