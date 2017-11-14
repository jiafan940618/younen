package com.yn.web;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.News;
import com.yn.model.Station;
import com.yn.model.User;
import com.yn.model.VisitorStation;
import com.yn.service.StationService;
import com.yn.service.VisitorStationService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.NewsVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/visitor")
public class VisitorStationController {
	
	@Autowired
	VisitorStationService visitorStationService;
	@Autowired
	StationService stationService;
	
	@ResponseBody
    @RequestMapping(value = "/giveStation")
    public Object giveStation() {
		
		User newuserVo = SessionCache.instance().getUser();
  	  	
  	  	if(null == newuserVo){
  	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	  	}
    	
  	  	if(newuserVo.getRoleId() != 7L){
  	  	 return ResultVOUtil.error(777,"抱歉,该用户没有该权限!");
  	  	}
		
		List<Station> list =stationService.getselStation();
      
        return ResultVOUtil.success(list);
    }
	
	 /** 挑选业务员想看的电站*/
	@ResponseBody
    @RequestMapping(value = "/checkStation")
    public Object checkStation(@RequestParam("stationId") List<Long> stationId) {
		
		User newuserVo = SessionCache.instance().getUser();
  	  	
  	  	if(null == newuserVo){
  	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	  	}
    	
  	  	if(newuserVo.getRoleId() != 7L){
  	  	 return ResultVOUtil.error(777,"抱歉,该用户没有该权限!");
  	  	}
		
	
  	  VisitorStation visitorStation = new VisitorStation();
  	  
	  String ids = visitorStationService.getList(stationId);
	  
	  visitorStation.setUserId(newuserVo.getId());
	  
	  visitorStation.setStationIds(ids);
	  
	  visitorStationService.save(visitorStation);
      
        return ResultVOUtil.success();
    }
	
	
	@ResponseBody
    @RequestMapping(value = "/selStation")
    public Object selStation() {
		User newuserVo = SessionCache.instance().getUser();
  	  	
  	  	if(null == newuserVo){
  	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	  	}
  	  	
  		if(newuserVo.getRoleId() != 7L){
  			
  	  	   return ResultVOUtil.error(777,"抱歉,该用户没有该权限!");
  	  	  }
	
  	  
  	    VisitorStation visitorStation	 = visitorStationService.findVisitorStation(newuserVo.getId());
  	  
	  	String[] ids = visitorStation.getStationIds().split(",");
	  	
	  	List<Long> list = new LinkedList<Long>();
	  	
	  	for (int i = 0; i < ids.length; i++) {
	  		list.add(Long.valueOf(ids[i]));
		}
  	  
     List<Station> stalist = stationService.findAll(list);
     
     for (Station station : stalist) {
			station.setOrder(null);
			station.setAmmeter(null);
			station.setServer(null);
			station.setUser(null);
		}
		return ResultVOUtil.success(stalist);
	}
	
	/** 移除用户选择的电站id*/
	@ResponseBody
    @RequestMapping(value = "/deleteStation")
    public Object deleteStation(@RequestParam("stationId") List<Long> stationId) {
		User newuserVo = SessionCache.instance().getUser();
  	  	
  	  	if(null == newuserVo){
  	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	  	}
  	  	
  		if(newuserVo.getRoleId() != 7L){
  			
  	  	   return ResultVOUtil.error(777,"抱歉,该用户没有该权限!");
  	  	  }
  		
  		 VisitorStation visitorStation	 = visitorStationService.findVisitorStation(newuserVo.getId());
		
  		 String ids = "";

		if(0 < stationId.size()){
		
			for (Long long1 : stationId) {
				String nuo =	long1+",";

				ids = visitorStation.getStationIds().replaceAll(nuo, "");
			}
		}
			visitorStation.setStationIds(ids);
			
			visitorStationService.save(visitorStation);	
			
		
		return ResultVOUtil.success(ids);
	}
}
