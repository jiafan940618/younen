package com.yn.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtil {
	
	/**
	 * 单个对象验证
	 * @param id
	 * @return
	 */
	public static List<String> validate(Object id) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> validate = validator.validate(id);
        
        Iterator<ConstraintViolation<Object>> iterator = validate.iterator();
        List<String> list =new ArrayList<String>();
        while (iterator.hasNext()) {
			ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) iterator
					.next();
			list.add(constraintViolation.getMessage());
			
//			localeMessageSourceUtil.getMessage(constraintViolation.getMessage());
		}
        return list;
	}
	
	/**
	 * 单个对象中的多属性校验
	 * @param id
	 * @param propertys
	 * @return
	 */
	public static List<String> validates(Object id,String... propertys) {
        
        Set<ConstraintViolation<Object>> validate = getProperty(id, propertys);
        
        Iterator<ConstraintViolation<Object>> iterator = validate.iterator();
        List<String> list =new ArrayList<String>();
        while (iterator.hasNext()) {
			ConstraintViolation<Object> constraintViolation = (ConstraintViolation<Object>) iterator
					.next();
			list.add(constraintViolation.getMessage());
			
//			localeMessageSourceUtil.getMessage(constraintViolation.getMessage());
		}
        return list;
	}


	/**
	 * 获取本次需要校验的属性
	 * @param id
	 * @param propertys
	 * @return
	 */
	private static Set<ConstraintViolation<Object>> getProperty(Object id,String... propertys) {
		//获取默认校验类
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> validate =new HashSet<ConstraintViolation<Object>>();
		if (propertys!=null) {
			for (int i = 0; i < propertys.length; i++) {
				Set<ConstraintViolation<Object>> validateProperty = validator.validateProperty(id, propertys[i]);
				if (validateProperty!=null) {
					validate.addAll(validateProperty);
				}
			}
		}
		return validate;
	}
}
