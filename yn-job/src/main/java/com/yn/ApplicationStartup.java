package com.yn;

import java.text.ParseException;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.yn.job.RollBack4Exception;
import com.yn.threadJob.ReadDataSource;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// ReadDataSource readDataSource =
		// event.getApplicationContext().getBean(ReadDataSource.class);
		// readDataSource.start();
		RollBack4Exception back4Exception = event.getApplicationContext().getBean(RollBack4Exception.class);
		try {
			back4Exception.checkException();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("跑。");
	}
}