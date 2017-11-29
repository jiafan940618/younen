package com.yn.web;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Brand;
import com.yn.model.City;
import com.yn.model.Inverter;
import com.yn.model.NewServerPlan;
import com.yn.model.SolarPanel;
import com.yn.service.BrandService;
import com.yn.service.NewServerPlanService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BrandVo;
import com.yn.vo.CityVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/brand")
public class BrandController {

	@Autowired
	BrandService brandService;
	@Autowired 
	NewServerPlanService newServerPlanService;
	//, method = {RequestMethod.POST}@RequestBody 
	@ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BrandVo brandVo) {

		Brand brand = new Brand();
        BeanCopy.copyProperties(brandVo, brand);
        brandService.save(brand);
        return ResultVOUtil.success(brand);
    }

	//, method = {RequestMethod.POST}
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {

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
    
    @ResponseBody
    @RequestMapping(value = "/newserdelete", method = {RequestMethod.POST})
    public Object newserdelete(Long id) {
    	newServerPlanService.delete(id);

        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BrandVo brandVo) {
    	
    	Brand brand = new Brand();
    	
        BeanCopy.copyProperties(brandVo, brand);
        
        Brand findOne = brandService.findOne(brand);
        
        return ResultVOUtil.success(findOne);
    }
	
    
    /** 加载品牌与型号*/
    @ResponseBody
    @RequestMapping(value = "/findBrand")
    public Object findBrand(com.yn.model.Page page) {
    	
    	Integer count = 0;
    	List<Brand> list = new LinkedList<Brand>();
    	
    	/** 1、电池板 3、逆变器*/
    	list =	brandService.getBrand(page);
    	
    	if(page.getType() == 1){
    		for (Brand brand : list) {
    			
        		List<SolarPanel> Solarlist =	brandService.getSolarPanel(brand.getId().intValue());
        		
        		brand.setSolar(new HashSet(Solarlist));
    			}
    		
    		
    	}else if(page.getType() == 3){
    		for (Brand brand : list) {
    			
        		List<Inverter> invlist =	brandService.getInverter(brand.getId().intValue());
        		
        		brand.setInverter(new HashSet(invlist));
    			}
    	}
    	
    	count =	brandService.getCount(page);

 		page.setTotal(count);
 		
 
        return ResultVOUtil.newsuccess(page,list);
    }
    
	/** 删除品牌*/
    
	
	
	
	
}
