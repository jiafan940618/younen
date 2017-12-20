package com.yn.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.yn.model.City;
import com.yn.vo.CityVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yn.dao.DevideDao;
import com.yn.model.Construction;
import com.yn.model.Order;
import com.yn.service.ConstructionService;
import com.yn.service.DevideService;
import com.yn.service.OssService;
import com.yn.service.SystemConfigService;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.ResultData;
import com.yn.vo.ConstructionVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/construction")
public class ConstructionController {
	//[施工类别]{0:all,1:屋顶类,2:建筑一体化,3:公共设施}
	
    private static final Logger logger = LoggerFactory.getLogger(ConstructionController.class);
	
    @Autowired
	private OssService oss;
	@Autowired
    private UserService userService;
	@Autowired
	ConstructionService constructionService;
	@Autowired
	SystemConfigService systemConfigService;
	
	
	/** 后台添加首页施工类别*/
	@ResponseBody
    @RequestMapping(value = "/save")
    public Object construction(ConstructionVo constructionVo) throws Exception {

		System.out.println("传的id--- -- ---- ---- ---->"+constructionVo.getId());
		System.out.println("传的imgurl--- -- ---- ---- ---->"+constructionVo.getImgUrl());
		System.out.println("传的type--- -- ---- ---- ---->"+constructionVo.getType());

		Construction construction = new Construction();	
	
		String[] ImgUrls =	constructionVo.getImgUrls();

		if(constructionVo.getImgUrls() == null) {

			BeanCopy.copyProperties(constructionVo, construction);
			
			constructionService.save(construction);
			
			return ResultVOUtil.success(construction);
			
		}else {
				
			for (int i = 0; i < ImgUrls.length; i++) {
				
				String imgUrl = ImgUrls[i];
			
				BeanCopy.copyProperties(constructionVo, construction);
				construction.setImgUrl(imgUrl);
				
				constructionService.insertConstr(construction);
	
			}
			
			return ResultVOUtil.success("添加成功!");
		}

	}
	
	
	@ResponseBody
    @RequestMapping(value = "/newsave", method = {RequestMethod.POST, RequestMethod.GET})
    public Object save(ConstructionVo constructionVo) throws Exception {

		System.out.println("传的id--- -- ---- ---- ---->"+constructionVo.getId());
		System.out.println("传的imgurl--- -- ---- ---- ---->"+constructionVo.getImgUrl());
		System.out.println("传的type--- -- ---- ---- ---->"+constructionVo.getType());

		Construction construction = new Construction();	
	

			BeanCopy.copyProperties(constructionVo, construction);
			
			constructionService.save(construction);
			
			return ResultVOUtil.success(construction);
	}

	@RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object findAll(ConstructionVo constructionVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
		Construction construction = new Construction();
		BeanCopy.copyProperties(constructionVo, construction);
		Page<Construction> findAll = constructionService.findAll(construction, pageable);
		return ResultVOUtil.success(findAll);
	}

	@RequestMapping(value = "/findOne", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object findO(ConstructionVo constructionVo) {
		Construction construction = new Construction();
		BeanCopy.copyProperties(constructionVo, construction);

		construction =	constructionService.findOne(construction.getId());

		return ResultVOUtil.success(construction);
	}


	
	@ResponseBody
    @RequestMapping(value = "/getSession")
    public Object sessiontion(String propertyValue,HttpSession httpSession) throws Exception {
		
		httpSession.setAttribute("propertyValue", propertyValue);
		
		return ResultVOUtil.success();
	}
	
	@ResponseBody
    @RequestMapping(value = "/findbyType")
    public Object getconstruc(ConstructionVo constructionVo) {


	 List<Construction> list = constructionService.findbyStruction();

        return ResultVOUtil.success(list);
    }
	
	@ResponseBody
    @RequestMapping(value = "/delete")
    public Object delete(ConstructionVo constructionVo) {


	 constructionService.delete(constructionVo.getId());

        return ResultVOUtil.success(null);
    }
	
	
	
	/** 上传图片以后的处理,只能一种一种类型的多文件，不然会出现问题*/ 
	@ResponseBody
    @RequestMapping(value = "/uploadImg")
    public Object UploadImg(MultipartHttpServletRequest request,HttpSession session) throws Exception {
		  request.setCharacterEncoding("UTF-8");
		
	  String finaltime =null;
	  
	 String propertyValue =(String) session.getAttribute("propertyValue");
		  
		  String realpath = "/opt/Test";
		  /** 测试路径*/
		  String upload = systemConfigService.get("propertyValue");
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
	                	 finaltime  =  oss.upload(file, realpath,upload);

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
