package com.yn.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.util.ResourceUtils;

public class TemplateFileUtil {
	
	public static FileInputStream getTemplates(String tempName) throws FileNotFoundException {
		
        return new FileInputStream(ResourceUtils.getFile("classpath:"+tempName));
        
    }
	
}
