package com.yn.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yn.service.OssService;
import com.yn.vo.re.ResultVOUtil;

import java.util.HashMap;
import java.util.Map;


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
    	
    	String type = request.getParameter("type");
    	
    	System.out.println("上传的类型：--- --- "+type);
    	
    	
        String[] saveToOSSs = ossService.uploadFiles(request);
        return ResultVOUtil.success(saveToOSSs);
    }


    @RequestMapping(value = "/upload4KindEditor", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object upload4KindEditor(MultipartHttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        String[] saveToOSSs = ossService.uploadFiles(request);
        String result = saveToOSSs[0];
        if (request.equals("上传失败")) {
            map.put("error", 1);
            map.put("url", null);
            map.put("msg", "上传失败");
        } else {
            map.put("error", 0);
            map.put("url", result);
        }
        return map;
    }

}
