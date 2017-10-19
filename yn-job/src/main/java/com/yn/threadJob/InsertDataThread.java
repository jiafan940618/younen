package com.yn.threadJob;

import java.sql.SQLException;

/**
 * 
 * @ClassName: InsertDataThread
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lzyqssn
 * @date 2017年10月17日
 *
 */
public class InsertDataThread {

	

	public static void main(String[] args) throws SQLException {
		for (int i = 1; i <= 5; i++) {
			//RunClass rc = new RunClass();
			new RunClass().start();
		}
		///////// ************///////////

		/*
		 * ExecutorService threadPool = Executors.newCachedThreadPool();
		 * CompletionService<Integer> cs = new
		 * ExecutorCompletionService<Integer>(threadPool); for (int i = 1; i <
		 * 5; i++) { final int taskID = i; cs.submit(new Callable<Integer>() {
		 * public Integer call() throws Exception { return taskID; } }); } //
		 * 可能做一些事情 for (int i = 1; i < 5; i++) { try {
		 * System.out.println(cs.take().get()); } catch (InterruptedException e)
		 * { e.printStackTrace(); } catch (ExecutionException e) {
		 * e.printStackTrace(); } }
		 */
	}

}
