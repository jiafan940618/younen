package com.yn.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {
	
	public static double getNum(Double double1) {
		if (double1==null) {
			return 0;
		}
		return double1.doubleValue();
	}
	
	public static double addDouble(Double double1,Double double2) {
		return getNum(double1)+getNum(double2);
	}
	
	public static int getNum(Integer integer) {
		if (integer==null) {
			return 0;
		}
		return integer.intValue();
	}
	
	/**
	 * 保留两位小数
	 * @param decimal
	 * @return
	 */
	public static double accurateToSecondDecimal(Double decimal) {
		double num = (double)Math.round(decimal*100)/100;
		return num;
	}
	
	/**
	 * 保留两位小数
	 * @param decimal
	 * @return
	 */
	public static double accurateToTwoDecimal(Double decimal) {
		DecimalFormat df = new DecimalFormat("#.00");
		String format = df.format(decimal);
		double dd = Double.valueOf(format);
		return dd;
	}
	
	public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
	
	
}
