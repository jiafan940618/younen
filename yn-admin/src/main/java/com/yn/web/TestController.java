package com.yn.web;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.yn.model.*;
import com.yn.service.*;
import com.yn.vo.*;
import net.sf.json.JSONArray;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.RoleDao;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.JsonUtil;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/test")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	BrandService brandService;
	@Autowired 
	NewServerPlanService newServerPlanService;
	@Autowired
	InverterService inverterService;
	@Autowired
	SolarPanelService solarPanelService;
	@Autowired
	DevideService devideService;
	@Autowired
	OtherInfoService otherInfoService;
	@Autowired
    ServerService serverService;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuService menuService;

	@Autowired
	ApolegamyService apolegamyService;
   
	
	/** 编辑添加逆变器*/
	@ResponseBody
    @RequestMapping(value = "/invsave")
    public Object invsave(com.yn.model.Page<Apolegamy> page) {

		Server serverResult = serverService.findOne(2L);

		page.setId(serverResult.getId());

		List<Apolegamy> list = apolegamyService.getPage(page);

		Integer count = apolegamyService.getCount(page);

		page.setTotal(count);
	        
        return ResultVOUtil.newsuccess(page,list);

    }
	
	@ResponseBody
    @RequestMapping(value = "/newdelete")
    public Object newdelete(Long id,HttpSession session) {
    	
		id = 12L;
    	
    	Server serverResult = serverService.findOne(1l);
    	
    	NewServerPlan newserverPlan = new NewServerPlan();
    	
    	newserverPlan.setServerId(serverResult.getId());

        NewServerPlan newServerPlan =  newServerPlanService.findOne(id);
        
        if(newServerPlan.getPlanId() != 1 ){
        	newServerPlanService.delete(id);
        }else{
        	newServerPlanService.delete(id);
        	
        	List<NewServerPlan> list =	newServerPlanService.findAll(newserverPlan);
        	
        	NewServerPlan ServerPlan = list.get(0);
        	ServerPlan.setPlanId(1);
        	
        	newServerPlanService.save(ServerPlan);
        	
        }

        return ResultVOUtil.success();
    }
	
	
	/** 编辑添加电池板*/
	@ResponseBody
    @RequestMapping(value = "/solarsave")
    public Object save(SolarPanelVol brandVo) {
		brandVo.setBrandId(2);
		brandVo.setBrandName("测试1246");
		brandVo.setModel("sadsafa");
		brandVo.setType(3);
		
		if(brandVo.getType() == 1){
			SolarPanel brand = new SolarPanel();
	        BeanCopy.copyProperties(brandVo, brand);
	        solarPanelService.save(brand);
	        
	        return ResultVOUtil.success(brand);
		}else if(brandVo.getType() == 3){
			Inverter brand = new Inverter();
	        BeanCopy.copyProperties(brandVo, brand);
	        inverterService.save(brand);
	        return ResultVOUtil.success(brand);
		}
		
        return ResultVOUtil.error(null);
    }
	
	
	 @ResponseBody
	    @RequestMapping(value = "/saveser")
	    public Object delete(NewServerPlanVo serverPlanVo) {
		 serverPlanVo.setBatteryBoardId(2L);
		 serverPlanVo.setInverterId(3L);
		 serverPlanVo.setUnitPrice(51.00);
		 serverPlanVo.setWarPeriod(new BigDecimal(5));
		 serverPlanVo.setMinPurchase(8.00);
		 serverPlanVo.setMaterialJson("sadasdfafsaf");
		 serverPlanVo.setPlanImgUrl("dffsfdsfds");
		 serverPlanVo.setDel(1);
		 
		 Server serverResult = serverService.findOne(1l);

	    	serverPlanVo.setServerId(serverResult.getId());

	    	NewServerPlan serverPlan = new NewServerPlan();
	        BeanCopy.copyProperties(serverPlanVo, serverPlan);
	        serverPlan.setBatteryboardId(2L);
	        
	        newServerPlanService.insert(serverPlan);
		 
		
	        return ResultVOUtil.success();
	    }
	
	
	

}
