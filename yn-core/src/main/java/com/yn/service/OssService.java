package com.yn.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSSClient;
import com.yn.utils.FileUtil;

@Service
public class OssService {
	
	private static final Logger logger = LoggerFactory.getLogger(OssService.class);
	
	/**
	 * 图片上传oss,不涉及后台
	 */
	public String[] uploadFiles(MultipartHttpServletRequest request) {
		List<MultipartFile> files = request.getFiles("file");
		String realpath = request.getSession().getServletContext().getRealPath("/resources/images");
		File f = new File(realpath);
		f.mkdirs();
		
		String[] saveToOsss = null;
		if (files != null && files.size() > 0) {
			saveToOsss = new String[files.size()];
			for (int i = 0; i < files.size(); i++) {
				String saveToOss = upload(files.get(i), realpath,"img/");
				saveToOsss[i] = saveToOss;
			}
		}
		return saveToOsss;
	}
	
	
	
	
	/**
	 * 图片保存oos
	 */
	public String upload(MultipartFile file, String realpath,String upload) {
		String path = null;
		String operatePath = null;
		String type = null;
		int res = 0;
		String fileName = file.getOriginalFilename();
		String operateName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."), fileName.length());
		// 判断文件类型
		type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
		if (
				(type.toLowerCase().equals(".jpeg") && file != null)
				|| (type.toLowerCase().equals(".png") && file != null)
				|| (type.toLowerCase().equals(".jpg") && file != null)
				|| (type.toLowerCase().equals(".mp4") && file != null)
				|| (type.toLowerCase().equals(".rmvb") && file != null)
				|| (type.toLowerCase().equals(".avi") && file != null)
				|| (type.toLowerCase().equals(".webm") && file != null)
				|| (type.toLowerCase().equals(".apk") && file != null)
				
				) {
			
			File localFolder = new File(realpath);
			if (!FileUtil.validateFolder(localFolder)) {
				boolean isSuccess = FileUtil.makeDirs(localFolder);
				if (isSuccess) {
					logger.info("create folder success, folder path is: " + realpath);
				} else {
					logger.info("create folder failed, folder path is: " + realpath);
				}
			}

			path = realpath + File.separator + operateName;
			operatePath = realpath + File.separator + fileName.replace(".", "1.");
			File localFile = new File(path);
			// 写文件到本地
			try {
				file.transferTo(localFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			
			res = uploadOss(upload + operateName, path);
		} else {
			return "101";
		}
		if (res == 1)
			return "http://oss.u-en.cn/" + upload + operateName;//res == 0 ? "上传失败" : "上传成功";
		return  "102";
	}
	
	
	
	public int uploadOss(String key, String localFilePath) {
		int res = 0;
		String endpoint = "oss.u-en.cn";
		String accessKeyId = "qoKG4r6HsGejsBWH";
		String accessKeySecret = "hWLfHm488gnSgu7hyh9V1EltLNv89v";

		
		OSSClient client = null;
		logger.info("--- ---- ---- ---- --- ---- 进入异常外");
		try {
			logger.info("--- ---- ---- ---- --- ---- 进入异常内");
			
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			logger.info("--- ---- ---- ---- --- ---- 第1步");
			/** 参数为bucketName,key,上传url*/
			client.putObject("uen", key, new File(localFilePath));
			logger.info("--- ---- ---- ---- --- ---- 第2步");
			res = 1;
			logger.info("--- ---- ---- ---- --- ---- "+res);
		} catch (Exception e) {
			
			 e.printStackTrace(System.out);
		        throw e;
		        
		} finally {
			try {
				client.shutdown();
			} catch (Exception e2) {
				e2.printStackTrace(System.out);
			        throw e2;
			}
		}
	
		return res;
	}

}
