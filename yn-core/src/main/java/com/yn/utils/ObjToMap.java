package com.yn.utils;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjToMap {

	public static Map<String, Object> getObjectMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		if (obj == null) {
			return map;
		}
		// 获取f对象对应类中的所有属性域
		getField(obj, obj.getClass(), map, true);
		getField(obj, obj.getClass().getSuperclass(), map, true);
		return map;
	}

	private static void getField(Object obj, Class<?> objClass, Map<String, Object> map, Boolean withSubclass) {
		Field[] fields = objClass.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				Object o = fields[i].get(obj);
				if (o != null && !fields[i].getType().toString().contains("java.util.List")) {
					if (withSubclass && isDomain(fields[i])) {
						Map<String, Object> domainMap = new HashMap<>();
						getField(o, o.getClass(), domainMap, false);
						map.put(varName, domainMap);
					} else {
						map.put(varName, o);
					}
				}
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static boolean isDomain(Field field) {
		Annotation[] annotations = field.getAnnotations();
		for (int j = 0; j < annotations.length; j++) {
			if (annotations[j] instanceof ManyToOne || annotations[j] instanceof OneToOne) {
				return true;
			}
		}
		return false;
	}

	public static List<String> getQueryList(Object obj) {
		List<String> list = new ArrayList<>();
		if (obj == null) {
			return list;
		}
		// 获取f对象对应类中的所有的搜索字段
		getQuery(obj.getClass(), list);
		getQuery(obj.getClass().getSuperclass(), list);
		return list;
	}

	private static void getQuery(Class<?> objClass, List<String> list) {
		Field[] fields = objClass.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			Annotation[] annotations = fields[i].getAnnotations();
			if (isQuery(annotations)) {
				list.add(fields[i].getName());
			}
		}
	}

	private static boolean isQuery(Annotation[] annotations) {
		if (annotations != null && annotations.length > 0) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof XField) {
					return ((XField) annotation).query();
				}
			}
		}
		return false;
	}
}
