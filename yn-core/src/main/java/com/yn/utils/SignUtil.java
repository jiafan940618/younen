package com.yn.utils;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.StaticApplicationContext;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;


public class SignUtil {
	private Logger logger = Logger.getLogger(SignUtil.class); 
    
	   /*****该方法是清理map里空的数据****/
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();

		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		DecimalFormat formater = new DecimalFormat("###0.00");
		for (String key : sArray.keySet()) {
			String finalValue = null;
			Object value = sArray.get(key);
			
			if(value instanceof BigDecimal){
				finalValue = formater.format(value);
			}else {
				finalValue = (String) value;
			}
			 
			if (value == null || value.equals("")
					|| key.equalsIgnoreCase("sign")) {
				continue;
			}
			
			result.put(key, finalValue);
		}

		return result;
	}

	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}
	
public static String createSignPlainText(final Object dataInfo, final boolean isWithEmpty, final String... filters) {
  if (dataInfo instanceof String) {
      return (String) dataInfo;
  }
  if (dataInfo instanceof Map) {
      Map<String, Object> dataMap = (Map<String, Object>) dataInfo;
      return createMapSignPlainText(dataMap, isWithEmpty, filters);
  }
  String signJsonPlainText = JSON.toJSONString(dataInfo);
  JSONObject jsonObject = JSON.parseObject(signJsonPlainText);
  return createMapSignPlainText(jsonObject, isWithEmpty, filters);
}

private static String createMapSignPlainText(Map<String, Object> dataMap, final boolean isWithEmpty, final String... filters) {
  final StringBuilder sb = new StringBuilder();
  final Set<String> filterSet = Sets.newHashSet(filters);
  List<String> list = new ArrayList<String>();
  for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
      Object value = entry.getValue();
      if (!isWithEmpty) {
          if (value == null) {
              continue;
          }
          if (value instanceof String && StringUtils.isBlank((String) value)) {
              continue;
          }
      }
      String keyName = entry.getKey();
      if (filterSet.contains(keyName)) {
          continue;
      }
      if ("sign".equalsIgnoreCase(keyName.toLowerCase())) {
          continue;
      }
      list.add(keyName + "=" + entry.getValue());
  }
  int size = list.size();
  String[] arrayToSort = list.toArray(new String[size]);
  Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
  for (int i = 0; i < size; i++) {
      sb.append(arrayToSort[i]);
      if (i < size - 1) {
          sb.append("&");
      }
  }
  return sb.toString();
}
 
 //MD5对拼接好的String进行加密
	public static String genSign(String key,String str){
		return md5(str+"&key="+key).toUpperCase();
	}
	
public static String md5(String plainText) {
  try {
      return Encodes.encodeHex(Digests.md5(new ByteArrayInputStream(plainText.getBytes("utf-8"))));
  } catch (Exception ex) {
      return "";   
  }
}

public static boolean validSign(Map<String, String> map,String key){
	String oldSign = map.get("sign");
	String sign = genSign(key, createLinkString(paraFilter(map)));
	return sign.equalsIgnoreCase(oldSign);
}

public static void main(String[] args) {
	String sign = genSign("c52d1b4bd27c4511a954c0cf1668ed8b","1");
	}
}

