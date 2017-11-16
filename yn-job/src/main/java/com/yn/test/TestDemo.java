package com.yn.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yn.utils.DateUtil;

public class TestDemo {

	private static PrintStream mytxt;
	private static PrintStream out;

	TestDemo() {
		try {
			mytxt = new PrintStream("./log.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtil.getDaysByYearMonth(2017, 11));
	}

	public void test1() {
		out = System.out;
		System.setOut(mytxt);
		System.out.println("文档执行的日期是：" + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E").format(new Date()));
		System.setOut(out);
		System.out.println("日期保存完毕。");
	}

	public static void test2() {
		try {
			PrintStream mytxt = new PrintStream("/opt/springbootproject/ynJob/log1.txt");
			PrintStream out = System.out;
			System.setOut(mytxt);
			System.out.println("文档执行的日期是：" + new Date());
			System.setOut(out);
			System.out.println("日期保存完毕。");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
