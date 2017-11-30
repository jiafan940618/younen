package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

import com.yn.model.Apolegamy;
import com.yn.model.Brand;
import com.yn.model.Devide;
import com.yn.model.Inverter;
import com.yn.model.OtherInfo;
import com.yn.model.SolarPanel;
import com.yn.service.BrandService;
import com.yn.service.DevideService;
import com.yn.service.InverterService;
import com.yn.service.OtherInfoService;
import com.yn.service.SolarPanelService;
import com.yn.utils.BeanCopy;
import com.yn.vo.DevideVo;

@RestController
@RequestMapping("/server/devide")
public class DevideController {
    @Autowired
    DevideService devideService;
    @Autowired
    BrandService brandService;
    @Autowired
    SolarPanelService solarPanelService;
    @Autowired
    OtherInfoService otherInfoService;
    @Autowired
    InverterService inverterService;
    

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Devide findOne = devideService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody DevideVo devideVo) {
        Devide devide = new Devide();
        BeanCopy.copyProperties(devideVo, devide);
        devideService.save(devide);
        return ResultVOUtil.success(devide);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        devideService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(DevideVo devideVo) {
        Devide devide = new Devide();
        BeanCopy.copyProperties(devideVo, devide);
        Devide findOne = devideService.findOne(devide);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(DevideVo devideVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        Devide devide = new Devide();
        BeanCopy.copyProperties(devideVo, devide);
        Page<Devide> findAll = devideService.findAll(devide, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    /** 加载品牌与型号*/
    @ResponseBody
    @RequestMapping(value = "/findBrand")
    public Object findBrand(com.yn.model.Page page) {
/*    	page.setType(1);
    	page.setId(1L);*/
    	Integer count = 0;
    	List<Brand> list = new LinkedList<Brand>();
    	
    	/** 1、电池板 3、逆变器*/
    	list =	brandService.getBrand(page);
    	
    	if(page.getType() == 1){
    		for (Brand brand : list) {
    			
        		List<SolarPanel> Solarlist =brandService.getSolarPanel(brand.getId().intValue());
        		
        		brand.setSolar(Solarlist);
    			}
    		
    		
    	}else if(page.getType() == 3){
    		for (Brand brand : list) {
    			
        		List<Inverter> invlist = brandService.getInverter(brand.getId().intValue());
        		
        		List<SolarPanel> set = new LinkedList<SolarPanel>();
        		
        		for (Inverter solarPanel : invlist) {
        			
        			SolarPanel sol = new SolarPanel();
        			sol.setId(solarPanel.getId());
        			sol.setBrandId(solarPanel.getBrandId());
        			sol.setBrandName(solarPanel.getModel());
        			sol.setModel(solarPanel.getBrandName());
					
        			set.add(sol);
				}
        		brand.setSolar(set);
    			}
    	}else if(page.getType() == 2){
    		for(Brand brand : list){
    			
    			OtherInfo otherInfo = new OtherInfo();
    			otherInfo.setBrandId(brand.getId().intValue());
    			List<OtherInfo> infolist = otherInfoService.findAll(otherInfo);
    			List<SolarPanel> set = new LinkedList<SolarPanel>();
    			
    			for (OtherInfo otherInfo2 : infolist) {
    				
    				SolarPanel sol = new SolarPanel();
        			sol.setId(otherInfo2.getId());
        			sol.setBrandId(otherInfo2.getBrandId());
        			sol.setBrandName(otherInfo2.getBrandName());
        			sol.setModel(otherInfo2.getModel());
        			set.add(sol);
    				
				}
 
    			brand.setSolar(set);
    		}
    	}

    	count =	brandService.getCount(page);

 		page.setTotal(count);
 
        return ResultVOUtil.newsuccess(page,list);
    }
    
    /** 新增逆变器品牌型号*/
    
    
     /** 新增电池板品牌型号*/
    
    
}
