package com.yn.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.mail.iap.Literal;
import com.yn.model.Construction;
import com.yn.service.ConstructionService;
import com.yn.vo.ConstructionVo;
import com.yn.vo.re.ResultVOUtil;


@Controller 
@RequestMapping("/client/construction")
public class ConstructionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ConstructionController.class);
	
	@Autowired
	ConstructionService constructionService;
	
	
	/** 根据施工项目的类型，查出相应的数据*/
		@ResponseBody
	    @RequestMapping(value = "/findbyType")
	    public Object construction(ConstructionVo constructionVo) {

		 logger.info("-- -- --- --- --- ---- --- ---- 传递的类型是:"+constructionVo.getType());
		
		 Construction construction = new Construction();
		 
		 List<Construction> list = new ArrayList<Construction>();
		 
		 if(constructionVo.getType() == 0){
			 list = constructionService.findAll(construction);
		 }else{
			 list = constructionService.findbyType(constructionVo.getType());
		 }

	        return ResultVOUtil.success(list);
	    }
	
	
	
	
	
	
	

}
