package com.yn.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestUtils {
	@SuppressWarnings("rawtypes")
	public static Map<String, String> getRequestMapValue(Map paramMap){
		
		Map<String, String> retMap = new HashMap<String, String>(paramMap.size());
		
		for(Iterator iter = paramMap.entrySet().iterator();iter.hasNext();){ 
	        Map.Entry element = (Map.Entry)iter.next(); 
	        String strKey = (String)element.getKey(); 
	        Object[] strObj = (Object[])element.getValue(); 
	        if(strObj != null){
	        	retMap.put(strKey, (String)strObj[0]);
	        }
		}
		
		return retMap;
	}

}
