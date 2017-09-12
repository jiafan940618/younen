package com.yn.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.mapper.ServerMapper;
import com.yn.model.Apolegamy;
import com.yn.model.NewServerPlan;
import com.yn.model.Server;
import com.yn.model.newPage;
import com.yn.service.ApolegamyService;
import com.yn.service.DevideService;
import com.yn.service.NewServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.SolarPanelSerice;
import com.yn.service.SystemConfigService;
import com.yn.utils.ResultData;
import com.yn.vo.QualificationsVo;
import com.yn.vo.SolarPanelVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/test")
public class TestController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	SolarPanelSerice solarService;
	@Autowired
	ServerMapper serverMapper;
	
	
	
	 @RequestMapping(value = "/dotest")
	 @ResponseBody
	    public ResultData<Object> newTest() {
		
		String cityName="深圳市";
		 
		 List<Server> list =	serverMapper.find(cityName);
		 
		 

			return ResultVOUtil.success(list);
	    }
	
	 @RequestMapping(value = "/dotest02")
	 @ResponseBody
	    public ResultData<Object> someTest() {
		 
		 String cityName ="";
		 
			List<SolarPanelVo> solar = null;
			List<QualificationsVo> quali =null;
			List<Object> list = null;
			
			newPage<Server> page = new newPage<Server>();
			page.setIndex(1);
			page.setLimit(3);
			
			if(null == cityName || cityName.equals("")){
				
			 list =  solarService.findObject(page.getStart(), page.getLimit());

				
			}else{
				
				 list =  solarService.findtwoObject(cityName,page.getStart(), page.getLimit());

			}
				solar  =solarService.getpanel(list);
				
				List<Long> ids = new LinkedList<Long>();
				 for (SolarPanelVo solarPanelVo : solar) {
					 if(ids.size()!=0){
						 ids.remove(0);
					 }
					logger.info("服务商信息为："+solarPanelVo.getS_id() +" -- -- "+solarPanelVo.getCompanyName()+" -- "+solarPanelVo.getCompanyLogo());
					ids.add(solarPanelVo.getS_id());
					
					List<Object> list02 = solarService.findquatwoObject(ids);
					 
					  quali = solarService.getqua(list02);
					 for (QualificationsVo qualificationsVo : quali) {
						 logger.info("资质为："+qualificationsVo.getId()+" -- "+qualificationsVo.getImgUrl());
					}
					 solarPanelVo.setList(quali);
				}

			return ResultVOUtil.success(solar);
	    }
	 
	 /* Server server = new Server();
	 server.setServerCityText("深圳市");
	 server.setServerCityIds("199");
	 
	 Pageable pageable = new PageRequest(0, 20, Direction.DESC, "id");
	
Page<Server> page =	serverService.findAll(server, pageable);

System.out.println(page.getTotalPages());

List<Server> list = page.getContent();
Set<NewServerPlan> doset =  new  HashSet<NewServerPlan>();

for (Server server2 : list) {
	
	Set<NewServerPlan> set = server2.getNewServerPlan();
	

	int i=0;
	 for (NewServerPlan newServerPlan : set) {
		 if(i==0){
			 doset.add(newServerPlan);
			 server2.setNewServerPlan(null);
			 server2.setNewServerPlan(doset);
			 System.out.println(newServerPlan.getId()+" -- -- "+newServerPlan.getInverterId());
			 break;
		 }
	}

}*/

	 
	
	

}
