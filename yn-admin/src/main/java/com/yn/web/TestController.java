package com.yn.web;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

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
import com.yn.model.Brand;
import com.yn.model.Devide;
import com.yn.model.Inverter;
import com.yn.model.Menu;
import com.yn.model.NewServerPlan;
import com.yn.model.OtherInfo;
import com.yn.model.Page;
import com.yn.model.Role;
import com.yn.model.Server;
import com.yn.model.SolarPanel;
import com.yn.service.BrandService;
import com.yn.service.DevideService;
import com.yn.service.InverterService;
import com.yn.service.MenuService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OtherInfoService;
import com.yn.service.RoleService;
import com.yn.service.ServerService;
import com.yn.service.SolarPanelService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.InverterVo;
import com.yn.vo.NewServerPlanVo;
import com.yn.vo.SolarPanelVol;
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
   
	
	/** 编辑添加逆变器*/
	@ResponseBody
    @RequestMapping(value = "/invsave")
    public Object invsave(Long roleId) {

		roleId = 1l;

			Role findOne = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map = new LinkedHashMap<>();
	        Set<Menu> menus = findOne.getMenu();

	        roleId = 2l;

			Role findOne02 = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map02 = new LinkedHashMap<>();
	        Set<Menu> menus02 = findOne02.getMenu();

	        roleId = 3l;

			Role findOne03 = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map03 = new LinkedHashMap<>();
	        Set<Menu> menus03 = findOne03.getMenu();

	        roleId = 4l;

			Role findOne04 = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map04 = new LinkedHashMap<>();
	        Set<Menu> menus04 = findOne04.getMenu();

	        roleId = 5l;

			Role findOne05 = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map05 = new LinkedHashMap<>();
	        Set<Menu> menus05 = findOne05.getMenu();

	        roleId = 6l;

			Role findOne06 = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map06 = new LinkedHashMap<>();
	        Set<Menu> menus06 = findOne06.getMenu();

	        roleId = 7l;

			Role findOne07 = roleService.findOne(roleId);
	        Map<Menu, List<Menu>> map07 = new LinkedHashMap<>();
	        Set<Menu> menus07 = findOne07.getMenu();

	        
        return ResultVOUtil.success();

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
