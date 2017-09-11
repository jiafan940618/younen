package com.yn.utils;

import java.io.File;

/**
 * 文件/文件夹操作类
 * 
 * @author Allen
 * @version 1.0
 * @since 1.0
 */
public class FileUtil {

	/**
	 * 验证文件夹路径是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static boolean validateFolder(File file) {
		return (file.exists() && file.isDirectory());
	}

	/**
	 * 创建文件夹
	 * 
	 * @param file
	 * @return
	 */
	public static boolean makeDirs(File file) {
		return file.mkdirs();
	}
}
