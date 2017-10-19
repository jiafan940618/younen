package com.yn.web;

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

import com.yn.model.City;
import com.yn.service.CityService;
import com.yn.utils.BeanCopy;
import com.yn.vo.CityVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/city")
public class CityController {
    @Autowired
    CityService cityService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        City findOne = cityService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody CityVo cityVo) {
        City city = new City();
        BeanCopy.copyProperties(cityVo, city);
        cityService.save(city);
        return ResultVOUtil.success(city);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        cityService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(CityVo cityVo) {
        City city = new City();
        BeanCopy.copyProperties(cityVo, city);
        City findOne = cityService.findOne(city);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(CityVo cityVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        City city = new City();
        BeanCopy.copyProperties(cityVo, city);
        Page<City> findAll = cityService.findAll(city, pageable);
        return ResultVOUtil.success(findAll);
    }
}
