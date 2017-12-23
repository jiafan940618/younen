package com.yn.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
		Date date = new Date();
		String[] weeks = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}

		return weeks[week_index];
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

	/**
	 * 
	 * @Title: countDaysInMonth 
	 * @Description: TODO(返回指定月份的总天数)
	 * @param @param month 传入的月份 
	 * @param @return 参数 指定月份的天数 
	 * @return int 返回类型
	 * @throws
	 */
	public static int countDaysInMonth(int month) {
		return LocalDate.now().withMonth(month).lengthOfMonth();
	}

	/**
	 * 
	    * @Title: countNowDaysInMonth
	    * @Description: TODO(获取指定月份的当前日期的天数)
	    * @param @param month 当前月份的当前天数
	    *  eg：2017/10/10-->10 
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	public static int countNowDaysInMonth(int month) {
		return LocalDate.now().withMonth(month).now().getDayOfMonth();
	}

	/**
	 * 
	    * @Title: parseDate
	    * @Description: TODO(返回一个具体的日期)
	    *  eg：2017_06_31
	    * @param @param y 年
	    * @param @param m 月
	    * @param @param d 日
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String parseDate(int y, int m, int d) {
		String date = "";
		String day = "";
		String month = "";
		if (d <= 9) {
			day = "0" + d;
		} else {
			day = d + "";
		}
		if (m <= 9) {
			month = "0" + m;
		} else {
			month = m + "";
		}
		date = y + "_" + month + "_" + day;
		return date;
	}

	/**
	 * 
	    * @Title: getDaysByYearMonth
	    * @Description: TODO(根据 年、月 获取对应的月份 的 天数)
	    * @param @param year
	    * @param @param month
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 
	    * @Title: getDayOfWeekByDate
	    * @Description: TODO(根据日期 找到对应日期的 星期几)
	    * @param @param date
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfweek = "-1";
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = myFormatter.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String str = formatter.format(myDate);
			dayOfweek = str;
		} catch (Exception e) {
			System.out.println("错误!");
		}
		return dayOfweek;
	}

	/**
	 * 获取上周时间区间
	 * 
	 * @return
	 */
	public static Date[] getLsatWeek() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.WEEK_OF_YEAR, -1);
		now.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Date date[] = new Date[2];
		date[0] = now.getTime();
		System.out.println(date[0]);
		now.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		date[1] = now.getTime();
		System.out.println(date[1]);
		return date;
	}

	/**
	 * 获取本周时间区间
	 * 
	 * @return
	 */

	public static Date[] getTimesWeekmorning() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Date date[] = new Date[2];
		date[0] = now.getTime();
		System.out.println(date[0]);
		Date nowtime = new Date();
		date[1] = nowtime;
		System.out.println(date[1]);
		return date;
	}

	/**
	 * 获取当上一小时的时间点
	 */
	public static Date lastHour() {

		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR, -1);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}

	/**
	 * 某一年的天数
	 */
	public static int whichYear(String date) {
		int days = 0;
		if (date.equals(Calendar.getInstance().get(Calendar.YEAR))) {
			days = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("", Locale.ENGLISH);
			sdf.applyPattern("yyyy");
			Calendar calendar = new GregorianCalendar();
			try {
				calendar.setTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			days = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		}

		return days;

	}

	/**
	 * 某一月的天数
	 */
	public static int whichMonth(String date) {
		int days = 0;
		if (date.equals(
				(Calendar.getInstance().get(Calendar.YEAR) + "-" + (Calendar.getInstance().get(Calendar.MONTH) + 1))
						.toString())) {
			days = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("", Locale.ENGLISH);
			sdf.applyPattern("yyyy-MM");
			Calendar calendar = new GregorianCalendar();
			try {
				calendar.setTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		return days;
	}

	/**
	 * 
	    * @Title: daysBetween
	    * @Description: TODO(计算两个时间之的时差)
	    * @param @param s 起始时间 yyyy-MM-dd
	    * @param @param b 结束时间 yyyy-MM-dd
	    * @param @return
	    * @param @throws ParseException    参数
	    * @return int    返回类型
	    * @throws
	 */
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

	/**
	 * 
	    * @Title: getSeason
	    * @Description: TODO(获取当前季度)
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	public static int getSeason() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
		int month = calendar.get(Calendar.MONTH);
		int season = (month + 1 + 4) / 4;
		return season;
	}

	/**
	 * （按照中国的纬度）
			第一季度：3－5月（春季）
			第二季度：6－8月（夏季）
			第三季度：9－11月（秋季）
			第四季度：12－2月（冬季）
	    * @Title: getMonthBySeason
	    * @Description: TODO(根据季度获取月份集合)
	    * @param @param season
	    * @param @return    参数
	    * @return List<Integer>    返回类型
	    * @throws
	 */
	public static List<Integer> getMonthBySeason(int season) {
		if (season > 4) {
			return null;
		}
		List<Integer> months = new LinkedList<Integer>();
		switch (season) {
		case 1:
			months = Arrays.asList(3, 4, 5);
			break;
		case 2:
			months = Arrays.asList(6, 7, 8);
			break;
		case 3:
			months = Arrays.asList(9, 10, 11);
			break;
		case 4:
			months = Arrays.asList(12, 1, 2);
			break;
		default:
			break;
		}
		return months;
	}

	/**
	 * （按照中国的纬度）
			第一季度：3－5月（春季）
			第二季度：6－8月（夏季）
			第三季度：9－11月（秋季）
			第四季度：12－2月（冬季）
	    * @Title: getSeasonDate
	    * @Description: TODO(根据季度返回月份)
	    * @param @param date
	    * @param @return    参数
	    * @return Date[]    返回类型
	    * @throws
	 */
	public static Date[] getSeasonDate(Date date) {
		Date[] season = new Date[3];

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int nSeason = getSeason(date);
		if (nSeason == 1) {// 第一季度
			c.set(Calendar.MONTH, Calendar.MARCH);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.APRIL);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MAY);
			season[2] = c.getTime();
		} else if (nSeason == 2) {// 第二季度
			c.set(Calendar.MONTH, Calendar.JUNE);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JULY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.AUGUST);
			season[2] = c.getTime();
		} else if (nSeason == 3) {// 第三季度
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.NOVEMBER);
			season[2] = c.getTime();
		} else if (nSeason == 4) {// 第四季度
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JANUARY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.FEBRUARY);
			season[2] = c.getTime();
		}
		return season;
	}

	/**
	 * 
	    * @Title: getSeason
	    * @Description: TODO(1 第一季度 2 第二季度 3 第三季度 4 第四季度 )
	    * @param @param date
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	public static int getSeason(Date date) {

		int season = 0;

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			season = 1;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			season = 2;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			season = 3;
			break;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			season = 4;
			break;
		default:
			break;
		}
		return season;
	}

}
