package com.yn.web;


import java.util.LinkedList;
import java.util.List;

import com.yn.model.Brand;
import com.yn.service.BrandService;
import com.yn.vo.NewDev;
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
import com.yn.model.OtherInfo;
import com.yn.model.SolarPanel;
import com.yn.service.InverterService;
import com.yn.service.OtherInfoService;
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
	@Autowired
	OtherInfoService otherInfoService;
	@Autowired
	BrandService brandService;
	
	
	/** 编辑添加电池板*/
	@ResponseBody
    @RequestMapping(value = "/solarsave", method = {RequestMethod.POST})
    public Object save(@RequestBody SolarPanelVol brandVo) {
		
		System.out.println("传递的Id为：---- ---- ---- "+brandVo.getId());
		System.out.println("传递的type为：---- ---- ---- "+brandVo.getType());
		
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
		}else if(brandVo.getType() == 2){
			OtherInfo otherInfo = new OtherInfo();
	        BeanCopy.copyProperties(brandVo, otherInfo);
	        otherInfoService.save(otherInfo);
	        return ResultVOUtil.success(otherInfo);
		}
		
		
		return ResultVOUtil.error(777, "编辑失败!");
    }
	
	
	
	@ResponseBody
    @RequestMapping(value = "/solardelete", method = {RequestMethod.POST})
    public Object solardelete(SolarPanelVol brandVo) {
		
		System.out.println("传递的Id为：---- ---- ---- "+brandVo.getId());
		System.out.println("传递的type为：---- ---- ---- "+brandVo.getType());
		
		if(brandVo.getType() == 1){
			solarPanelService.delete(brandVo.getId());
		}else if(brandVo.getType() == 3){
			inverterService.delete(brandVo.getId());
		}else if(brandVo.getType() == 2){
			otherInfoService.delete(brandVo.getId());
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

		}else if(brandVo.getType() == 2){

			com.yn.model.Page newpage = new com.yn.model.Page();

			newpage.setType(2);

			List<Brand> list =	brandService.getBrand(newpage);

			List<NewDev> newlist = new LinkedList<NewDev>();

			for (Brand brand: list) {
				OtherInfo otherInfo = new OtherInfo();
				otherInfo.setBrandId(brand.getId().intValue());
				List<OtherInfo> infolist = otherInfoService.findAll(otherInfo);
				List<SolarPanel> set = new LinkedList<SolarPanel>();

				NewDev dev = new NewDev();
				dev.setId(brand.getId());
				dev.setDevideName(brand.getBrandName());

				List<NewDev> children = new LinkedList<NewDev>();

				for (OtherInfo otherInfo2 : infolist) {

					NewDev cdev = new NewDev();

					cdev.setId(otherInfo2.getId());
					cdev.setDevideName(otherInfo2.getModel());

					children.add(cdev);
				}

				dev.setChildren(children);

				newlist.add(dev);
			}

			return ResultVOUtil.success(newlist);
		}

		return ResultVOUtil.error(null);
	
	}
	
	
	

}
