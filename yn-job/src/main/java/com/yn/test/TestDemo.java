package com.yn.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import com.yn.job.RollBack4Exception;
import com.yn.utils.DateUtil;


public class TestDemo {
	static {

		int x = 5;
	}
	static int x, y;

	public static void s() {
		y = x++ + ++x;
	}

	public static void main(String[] as) throws ParseException {
		
		System.out.println( new SimpleDateFormat("yyyy_MM_dd")
						.format(new SimpleDateFormat("yyMMddHHssmm").parse("171025102400")));
		
		//System.out.println( DateUtil.formatDate(DateUtil.formatString("171025235900", "yyMMddHHssmm"), "yyyy_MM_dd"));
		System.out.println(new SimpleDateFormat("yyyy_MM_dd").format(new SimpleDateFormat("yyMMddHHssmm").parse("171025102400")));
		
		
		
		
		int inMonth = DateUtil.countDaysInMonth(8);
		System.out.println(inMonth);
		 
		System.out.println();
		int y, m, d, h, mi, s;
		Calendar cal = Calendar.getInstance();
		y = cal.get(Calendar.YEAR);
		m = cal.get(Calendar.MONTH)+1;
		d = cal.get(Calendar.DATE);
		h = cal.get(Calendar.HOUR_OF_DAY);
		mi = cal.get(Calendar.MINUTE);
		s = cal.get(Calendar.SECOND);
		System.out.println("现在时刻是" + y + "年" + m + "月" + d + "日" + h + "时" + mi + "分" + s + "秒");

		System.out.println(new Date().getDate());
		String d1 = "am1Phase17071432146541531";
		// x--;
		// s();
		// System.out.println(x+y + ++x);
		/*
		 * String date = "2017-10-01"; SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd"); Date thisHourSpace = sdf.parse(date);
		 * String startDtm = DateUtil.formatDate(thisHourSpace,
		 * DateUtil.yyMMddHHmmss).substring(0, 6); String endDtm =
		 * DateUtil.formatDate(thisHourSpace,
		 * DateUtil.yyMMddHHmmss).substring(0, 6); System.out.println(startDtm);
		 * System.out.println(endDtm);
		 */
		// AmPhaseService aps = new AmPhaseService();
		// aps.findAllAm1Phase();
		// aps.findAllAm3Phase();
		// sd();
		// **//***///***//
		/*
		 * String date = "2017-10-10"; SimpleDateFormat sdf = new
		 * SimpleDateFormat("yyyy-MM-dd"); SimpleDateFormat sdf1 = new
		 * SimpleDateFormat("yyyy_MM_dd"); Date ss = sdf.parse(date); String
		 * format = sdf1.format(ss); System.out.println(format);
		 */
		// System.out.println(countDaysInMonth(Month.MAY));
		// System.out.println(LocalDate.now().withMonth(10).now().getDayOfMonth());
		// System.out.println(nowMonth);
		// System.out.println(year);
		// System.out.println(i);
		// String parsrDate = parseDate(2017,11);
		// System.out.println(parsrDate);
		// int month = 11;
		// int days = TestDemo.countDaysInMonth(month);
		// int year = 2017;
		// String date = "";
		// String day = "";
		// String months = "";
		// if(month<=9){
		// months +="0"+month;
		// }else{
		// months = month+"";
		// }
		// for (int i = 1; i <= days; i++) {
		// if (i <= 9) {
		// day = "0" + i;
		// } else {
		// day = i + "";
		// }
		// date = year + months + day;// 20171001
		// System.out.println(date);
		// }
	}

	public static void sd() throws ParseException {
		String date = "2017-10-10";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date thisHourSpace = sdf.parse(date);
		String startDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss).substring(0, 6);
		System.out.println(startDtm);
	}

	private static String parseDate(int y, int m) {
		String date = "";
		String day = "";
		int d = countDaysInMonth(m);
		if (d <= 9) {
			day = "0" + d;
		} else {
			day = d + "";
		}
		date = y + "" + m + "" + day;
		return date;
	}

	public static int countDaysInMonth(/* Month month */int month) {
		return LocalDate.now().withMonth(/* month.getValue() */month).lengthOfMonth();
	}

}
