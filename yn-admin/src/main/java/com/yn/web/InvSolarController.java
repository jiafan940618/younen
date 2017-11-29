package com.yn.web;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
		return ResultVOUtil.error(777, "编辑失败!");
    }
	
	
	
	@ResponseBody
    @RequestMapping(value = "/solardelete", method = {RequestMethod.POST})
    public Object solardelete(@RequestBody SolarPanelVol brandVo) {
		if(brandVo.getType() == 1){
			solarPanelService.delete(brandVo.getId());
		}else if(brandVo.getType() == 3){
			inverterService.delete(brandVo.getId());
		}
		
		
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
    @RequestMapping(value = "/solarfindAll", method = {RequestMethod.POST, RequestMethod.GET})
    public Object solarfindAll(SolarPanelVol brandVo,@PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
		
		if(brandVo.getType() == 1){
			
			SolarPanel solarPanel = new SolarPanel();
			
			BeanCopy.copyProperties(brandVo, solarPanel);
			
			Page<SolarPanel> page =	solarPanelService.findAll(solarPanel, pageable);
			
			page.getContent();

			return ResultVOUtil.success(page);
			
		}else if(brandVo.getType() == 3){

			Inverter inverter = new Inverter();
			
			BeanCopy.copyProperties(brandVo, inverter);

			Page<Inverter> page =	inverterService.findAll(inverter, pageable);
			
			return ResultVOUtil.success(page);

		}

		return ResultVOUtil.error(null);
	
	}
	
	
	

}
