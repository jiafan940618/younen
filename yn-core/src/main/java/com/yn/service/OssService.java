package com.yn.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSSClient;

@Service
public class OssService {
	
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
				String saveToOss = upload(files.get(i), realpath);
				saveToOsss[i] = saveToOss;
			}
		}
		return saveToOsss;
	}
	
	/**
	 * 图片保存oos
	 */
	private String upload(MultipartFile file, String realpath) {
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

			path = realpath + File.separator + operateName;
			operatePath = realpath + File.separator + fileName.replace(".", "1.");
			File localFile = new File(path);
			// 写文件到本地
			try {
				file.transferTo(localFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			/*try {
				BufferedImage bufferedImage = ImageIO.read(localFile);
				int width = bufferedImage.getWidth();
				int height = bufferedImage.getHeight();
				int a = width > height ? height : width;
				if(a > 500) {
					Thumbnails.of(localFile).sourceRegion(Positions.CENTER, a, a).size(500, 500).keepAspectRatio(false).toFile(operatePath);
				} else {
					Thumbnails.of(localFile).sourceRegion(Positions.CENTER, a, a).size(a, a).keepAspectRatio(false).toFile(operatePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
//			res = uploadOss("img/" + operateName, operatePath);
			res = uploadOss("img/" + operateName, path);
		} else {
			return "上传失败：上传的文件类型不符合要求,请上传PNG或者JPG格式的图片";
		}
		if (res == 1)
			return "http://oss.u-en.cn" + "/img/" + operateName;//res == 0 ? "上传失败" : "上传成功";
		return  "上传失败";
	}
	
	
	
	public int uploadOss(String key, String localFilePath) {
		int res = 0;
		String endpoint = "oss.u-en.cn";
		String accessKeyId = "qoKG4r6HsGejsBWH";
		String accessKeySecret = "hWLfHm488gnSgu7hyh9V1EltLNv89v";

		OSSClient client = null;
		try {
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			client.putObject("uen", key, new File(localFilePath));
			res = 1;
		} catch (Exception e) {
			System.out.println();
		} finally {
			try {
				client.shutdown();
			} catch (Exception e2) {
				System.out.println();
			}
		}
		System.out.println("done");
		return res;
	}

}
