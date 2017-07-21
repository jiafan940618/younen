package com.yn.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DateUtil {

	public static final String yyyy_MM_dd = "yyyy-MM-dd";

	public static final String yyyy_MM_dd_HHmmss = "yyyy-MM-dd HH:mm:ss";
	
	public static final String yyMMddHHmmss = "yyMMddHHmmss";
	
	public static String formatDate(Date date, String format) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date != null) {
			result = sdf.format(date);
		}
		return result;
	}

	public static Date formatString(String str, String format) throws ParseException {
		if (StringUtil.isEmpty(str)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static Date parseString(String str, String format) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}

		Date parse = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			parse = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return parse;
	}

	public static Calendar getTodayBegin() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now;
	}

	public static Calendar getTomorrowBegin() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return now;
	}

	public static Calendar after(int hour) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.DATE) - hour);
		return now;
	}

	/**
	 * 获取昨天的时间区间
	 */
	public static Date[] getYesterdaySpace() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date[] = new Date[2];

		date[0] = now.getTime();
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	/**
	 * 获取今天的时间区间
	 */
	public static Date[] getTodaySpace() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date[] = new Date[2];

		date[0] = now.getTime();
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	/**
	 * 获取今月的时间区间
	 */
	public static Date[] getThisMonthSpace() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date[] = new Date[2];

		date[0] = now.getTime();
		now.roll(Calendar.DATE, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	/**
	 * 获取上个月的时间区间
	 */
	public static Date[] getLastMonthSpace() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, -1);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date[] = new Date[2];

		date[0] = now.getTime();
		now.roll(Calendar.DATE, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	/**
	 * 获取今年的时间区间
	 */
	public static Date[] getThisYearSpace() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MONTH, 0);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date[] = new Date[2];

		date[0] = now.getTime();
		now.set(Calendar.MONTH, 11);
		now.roll(Calendar.DATE, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	/**
	 * 获取去年的时间区间
	 */
	public static Date[] getLastYearSpace() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -1);
		now.set(Calendar.MONTH, 0);
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date[] = new Date[2];

		date[0] = now.getTime();
		now.set(Calendar.MONTH, 11);
		now.roll(Calendar.DATE, -1);
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	/**
	 * 获取当前小时的时间区间
	 */
	public static Date[] thisHourSpace() {
		Date date[] = new Date[2];
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);

		date[0] = now.getTime();
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}

	/**
	 * 获取当上一小时的时间区间
	 */
	public static Date[] lastHourSpace() {
		Date date[] = new Date[2];
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, -1);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);

		date[0] = now.getTime();
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		date[1] = now.getTime();
		return date;
	}
	
	public static String getCurrentDateStr() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	public static Date getDateFromTimeMillis(Long timeMillis) {
		Date date = new Date(timeMillis);
		return date;
	}

	/**
	 * 获取今天0点的时间戳
	 * @return
	 */
	public static Long getDayBeginTimeInMillis() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8 "));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 001);
		return cal.getTimeInMillis();
	}
	
	/**
	 * 查看今天是星期几，如果今天是星期5，则返回5
	 * @return
	 */
	public static String getDayOfWeekNum() {
    	Date date=new Date();
	    String[] weeks = {"7","1","2","3","4","5","6"};  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1; 
        if(week_index < 0) {
            week_index = 0;  
        }
    	
    	return  weeks[week_index];
    }
	
	/**
	 * 获取当前时间是 几点几分几秒
	 * @return
	 */
	public static Time getNowTime() {
		Time nowTime = new Time(System.currentTimeMillis() - getDayBeginTimeInMillis());
		return nowTime;
	}
	
	/**
	 * 毫秒转化
	 * @param ms
	 * @return
	 */
	public static String formatTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day; // 天
		String strHour = hour < 10 ? "0" + hour : "" + hour;// 小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;// 分钟
		String strSecond = second < 10 ? "0" + second : "" + second;// 秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;// 毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strMinute + " 分钟 " + strSecond + " 秒";
	}
	
	/**
	 * 判断当前时分是否在某两个时分段内
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean inTimeSpace(Time beginTime, Time endTime) {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		try {
			String format = sdf.format(new Date());
			Date now = sdf.parse(format);
			
			String beginTimeStr = sdf.format(beginTime);
			Date begin = sdf.parse(beginTimeStr);
			
			String endTimeStr = sdf.format(endTime);
			Date end = sdf.parse(endTimeStr);
			
			if (begin.before(now) && now.before(end)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return false;
    }
	
	/**
	 * 获取今天每个时刻的整点，当天0点到当天23点
	 * @return
	 */
	public static List<Date> getTodayEachHourBegin() {
		List<Date> hourList1 = new ArrayList<>();
		int hour1 = 23;
		for (int i = 0; i <= hour1; i++) {
			Calendar now = Calendar.getInstance();
			now.set(Calendar.HOUR_OF_DAY, i);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			hourList1.add(now.getTime());
		}
		return hourList1;
	}

	/**
	 * 获取今天每个时刻的整点，当天1点到第二天0点
	 * @return
	 */
	public static List<Date> getTodayEachHourBegin2() {
		List<Date> hourList1 = new ArrayList<>();
		int hour1 = 24;
		for (int i = 1; i <= hour1; i++) {
			Calendar now = Calendar.getInstance();
			now.set(Calendar.HOUR_OF_DAY, i);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);
			hourList1.add(now.getTime());
		}
		return hourList1;
	}
	
}
