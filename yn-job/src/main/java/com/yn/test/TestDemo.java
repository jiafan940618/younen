package com.yn.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.yn.model.Pzw;
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

	public static int daysBetween(String s, String b) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date smdate = sdf.parse(s);
		Date bdate = sdf.parse(b);
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static void s(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date d1 = new Date();
		   Date d2 = df.parse("2017-07-05 18:13:49");
		   long diff = d1.getTime() - d2.getTime();
		   long days = diff / (1000 * 60 * 60 * 24);
		  System.out.println(days);
		}
		catch (Exception e)
		{
		}
	}
	
	public static void main(String[] args) throws ParseException {
		Date date = DateUtil.formatString("2017-12-01", "yyyy-MM-dd");
		System.out.println(DateUtil.getSeason(date));
		Date[] date2 = DateUtil.getSeasonDate(date);
		for (Date date3 : date2) {
			System.out.println(DateUtil.formatDate(date3, "MM"));
			System.out.println(DateUtil.formatDate(date3, "yyyy"));
		}
		List<Integer> season = DateUtil.getMonthBySeason(4);
//		season.forEach(System.out::println);
//		System.out.println((1+3-1)%4+1);// 上季度计算公式
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
