package com.yn.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil extends org.springframework.util.StringUtils {

	public static boolean isEmpty(Object str) {

		if (str instanceof String) {
			return str == null || "".equals(str);
		} else if (str instanceof Collection) {
			Collection collection = (Collection) str;
			return collection.isEmpty();
		}
		return str == null;
	}

	public static List<Integer> parse2IntList(String str) {
		List<Integer> list = new ArrayList<>();
		if (!isEmpty(str)) {
			String[] split = str.split(",");
			if (split.length > 0) {
				for (int i = 0; i < split.length; i++) {
					String string = split[i];
					list.add(Integer.parseInt(string));
				}
			}
		}

		return list;
	}

	/**
	 * 当前时间 + 随机五位数
	 * @return
	 */
	public static String getRandomTradeNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = sdf.format(date);
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		return str + rannum; 
	}
	
	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
	 * 17+除9的任意数 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 合法E-mail地址： 1. 必须包含一个并且只有一个符号“@” 2. 第一个字符不得是“@”或者“.” 3. 不允许出现“@.”或者.@ 4.
	 * 结尾不得是字符“@”或者“.” 5. 允许“@”前的字符中出现“＋” 6. 不允许“＋”在最前面，或者“＋@”
	 */
	public static boolean isEmail(String str) throws PatternSyntaxException {
		String regExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
