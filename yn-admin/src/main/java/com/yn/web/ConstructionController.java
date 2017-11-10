package com.yn.web;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yn.dao.DevideDao;
import com.yn.model.Construction;
import com.yn.service.ConstructionService;
import com.yn.service.DevideService;
import com.yn.service.OssService;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.ResultData;
import com.yn.vo.ConstructionVo;
import com.yn.vo.re.ResultVOUtil;

@Controller 
@RequestMapping("/client/construction")
public class ConstructionController {
	//[施工类别]{0:all,1:屋顶类,2:建筑一体化,3:公共设施}
	
    private static final Logger logger = LoggerFactory.getLogger(ConstructionController.class);
	
    @Autowired
	private OssService oss;
	@Autowired
    private UserService userService;
	@Autowired
	ConstructionService constructionService;
	
	
	/** 后台添加首页施工类别*/
	@ResponseBody
    @RequestMapping(value = "/findConstruc")
    public Object construction(ConstructionVo constructionVo) throws Exception {
		 Construction construction = new  Construction();
		 
		if(null != constructionVo.getId()){
			 construction.setId(constructionVo.getId());
			 construction.setImgUrl(constructionVo.getImgUrl());
			 construction.setType(constructionVo.getType());
			 construction.setIdentification(constructionVo.getIdentification());
		}else{
		
		 construction.setImgUrl(constructionVo.getImgUrl());
		 construction.setType(constructionVo.getType());
		 construction.setIdentification(constructionVo.getIdentification());
		} 
		 constructionService.save(construction);
		
		 return ResultVOUtil.success();
	}
	
	
	
	/** 上传图片以后的处理,只能一种一种类型的多文件，不然会出现问题*/ 
	@ResponseBody
    @RequestMapping(value = "/uploadImg")
    public Object UploadImg(MultipartHttpServletRequest request,HttpSession session) throws Exception {
		  request.setCharacterEncoding("UTF-8");
		
	  String finaltime =null;
		  
		  String realpath = "/opt/Test";
		  /** 测试路径*/
		//创建一个通用的多部分解析器  
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //记录上传过程起始时的时间，用来计算上传时间  
	                int pre = (int) System.currentTimeMillis();  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());  
	                
	                ResultData<Object>  data =  userService.getresult(file);
	                
	                if(data.getCode() == 200){
	                	 finaltime  =  oss.upload(file, realpath);

	 	                /** 取得文件以后得把文件保存在本地路径*/
	 	              
		 	               if(finaltime.equals("101") ){
		 	            	   return   ResultVOUtil.error(777, Constant.FILE_ERROR);
		 	                }
		 	               if(finaltime.equals("102") ){
		 	            	   return   ResultVOUtil.error(777, Constant.FILE_NULL);
		 	                }
		 	                //记录上传该文件后的时间  
		 	                int finaltime01 = (int) System.currentTimeMillis();  
	                }
	                if(data.getCode() ==777){
	                	return data;
	                }
	            }	
	        }
	        
	        logger.info("---- ---- ----- ------- --- finaltime："+finaltime);
		
		return ResultVOUtil.success(finaltime);
	
	}
	
	

}