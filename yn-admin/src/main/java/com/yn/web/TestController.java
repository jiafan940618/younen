package com.yn.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.yn.model.Inverter;
import com.yn.model.NewServerPlan;
import com.yn.model.SolarPanel;
import com.yn.service.BrandService;
import com.yn.service.InverterService;
import com.yn.service.NewServerPlanService;
import com.yn.service.SolarPanelService;
import com.yn.utils.BeanCopy;
import com.yn.vo.InverterVo;
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
	
	/** 编辑添加逆变器*/
	@ResponseBody
    @RequestMapping(value = "/invsave")
    public Object invsave(InverterVo brandVo) {
		brandVo.setBrandId(11);
		brandVo.setBrandName("测试");
		brandVo.setModel("测试型号");

		Inverter brand = new Inverter();
        BeanCopy.copyProperties(brandVo, brand);
        inverterService.save(brand);
        return ResultVOUtil.success(brand);
    }
	
	/** 编辑添加电池板*/
	@ResponseBody
    @RequestMapping(value = "/solarsave")
    public Object save(SolarPanelVol brandVo) {
		brandVo.setBrandId(20);
		brandVo.setBrandName("测试");
		brandVo.setModel("测试型号");
		
		SolarPanel brand = new SolarPanel();
        BeanCopy.copyProperties(brandVo, brand);
        solarPanelService.save(brand);
        return ResultVOUtil.success(brand);
    }
	
	
	 @ResponseBody
	    @RequestMapping(value = "/delete")
	    public Object delete(Long id) {
		 id = 10L;
	     
	    List<NewServerPlan> list = newServerPlanService.FindBybrandId(id);
	    
	    	if(list.size() == 0){
	    		 list = newServerPlanService.FindtwobrandId01(id);
	    		 
	    		 if(list.size() == 0){
	    			 brandService.delete(id);
	    			 return  ResultVOUtil.success();
	    		 }
	    	}
	        return ResultVOUtil.newerror(777,"请先删除所有相关的方案",list);
	    }
	
	
	

}
