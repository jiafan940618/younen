package com.yn.test;

import org.springframework.scheduling.annotation.Scheduled;

//@Component
public class TestScheduled {
	@Scheduled(fixedDelay = 10 * 1000) 
	public void myTest() {
		System.out.println("myTest");
	}
}
