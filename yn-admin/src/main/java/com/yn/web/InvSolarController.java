package com.yn.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.yn.model.Inverter;
import com.yn.model.SolarPanel;
import com.yn.service.InverterService;
import com.yn.service.SolarPanelService;
import com.yn.utils.BeanCopy;
import com.yn.vo.InverterVo;
import com.yn.vo.SolarPanelVol;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/InvSolar")
public class InvSolarController {
	
	@Autowired
	InverterService inverterService;
	@Autowired
	SolarPanelService solarPanelService;
	
	/** 编辑添加逆变器*/
	@ResponseBody
    @RequestMapping(value = "/invsave", method = {RequestMethod.POST})
    public Object invsave(@RequestBody InverterVo brandVo) {
		Inverter brand = new Inverter();
        BeanCopy.copyProperties(brandVo, brand);
        inverterService.save(brand);
        return ResultVOUtil.success(brand);
    }
	
	/** 编辑添加电池板*/
	@ResponseBody
    @RequestMapping(value = "/solarsave", method = {RequestMethod.POST})
    public Object save(@RequestBody SolarPanelVol brandVo) {
		SolarPanel brand = new SolarPanel();
        BeanCopy.copyProperties(brandVo, brand);
        solarPanelService.save(brand);
        return ResultVOUtil.success(brand);
    }
	
	@ResponseBody
    @RequestMapping(value = "/invdelete", method = {RequestMethod.POST})
    public Object invdelete(@RequestBody InverterVo brandVo) {
		
		inverterService.delete(brandVo.getId());
		
        return ResultVOUtil.success(null);
    }
	
	@ResponseBody
    @RequestMapping(value = "/solardelete", method = {RequestMethod.POST})
    public Object solardelete(@RequestBody SolarPanelVol brandVo) {
		
		solarPanelService.delete(brandVo.getId());
		
        return ResultVOUtil.success(null);
    }
	
	@ResponseBody
    @RequestMapping(value = "/invfindAll")
    public Object invfindAll(InverterVo brandVo) {
		
		Inverter inverter = new Inverter();
		BeanCopy.copyProperties(brandVo, inverter);
		
		List<Inverter> list = inverterService.findAll(inverter);

        return ResultVOUtil.success(list);
    }
	
	@ResponseBody
    @RequestMapping(value = "/solarfindAll")
    public Object solarfindAll(SolarPanelVol brandVo) {
		
		SolarPanel solarPanel = new SolarPanel();
		BeanCopy.copyProperties(brandVo, solarPanel);
		
		List<SolarPanel> list = solarPanelService.findAll(solarPanel);
		
		return ResultVOUtil.success(list);
	
	}
	
	
	

}
