package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Brand;
import com.yn.model.City;
import com.yn.service.BrandService;
import com.yn.utils.BeanCopy;
import com.yn.vo.BrandVo;
import com.yn.vo.CityVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/brand")
public class BrandController {

	@Autowired
	BrandService brandService;
	
	@ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BrandVo brandVo) {
		Brand brand = new Brand();
        BeanCopy.copyProperties(brandVo, brand);
        brandService.save(brand);
        return ResultVOUtil.success(brand);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
     
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
	
	
	
	
	
	
}
