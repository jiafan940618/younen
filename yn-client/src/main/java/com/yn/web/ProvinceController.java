package com.yn.web;

import com.yn.vo.re.ResultVOUtil;
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

import com.yn.model.Province;
import com.yn.service.ProvinceService;
import com.yn.utils.BeanCopy;
import com.yn.vo.ProvinceVo;

@RestController
@RequestMapping("/client/province")
public class ProvinceController {
    @Autowired
    ProvinceService provinceService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Province findOne = provinceService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ProvinceVo provinceVo) {
        Province province = new Province();
        BeanCopy.copyProperties(provinceVo, province);
        provinceService.save(province);
        return ResultVOUtil.success(province);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        provinceService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ProvinceVo provinceVo) {
        Province province = new Province();
        BeanCopy.copyProperties(provinceVo, province);
        Province findOne = provinceService.findOne(province);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ProvinceVo provinceVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Province province = new Province();
        BeanCopy.copyProperties(provinceVo, province);
        Page<Province> findAll = provinceService.findAll(province, pageable);
        return ResultVOUtil.success(findAll);
    }
}
