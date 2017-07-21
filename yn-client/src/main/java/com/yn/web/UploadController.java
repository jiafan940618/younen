package com.yn.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yn.service.OssService;
import com.yn.vo.re.ResultDataVoUtil;



@Controller
@RequestMapping("/server/upload")
public class UploadController {
	@Resource
	OssService ossService;
	
	/**
	 * 批量上传文件上传图片
	 */
	@RequestMapping(value = "/upload", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object upload(MultipartHttpServletRequest request) {
		String[] saveToOSSs = ossService.uploadFiles(request);
		return ResultDataVoUtil.success(saveToOSSs);
	}
	
}
