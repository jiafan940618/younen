package com.yn.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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

import com.yn.dao.CityDao;
import com.yn.dao.ProvinceDao;
import com.yn.dao.SubsidyDao;
import com.yn.model.City;
import com.yn.model.Province;
import com.yn.model.Subsidy;
import com.yn.service.SubsidyService;
import com.yn.utils.BeanCopy;
import com.yn.utils.ValidatorUtil;
import com.yn.vo.SubsidyVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/subsidy")
public class SubsidyController {
    @Autowired
    SubsidyService subsidyService;
    @Autowired
    SubsidyDao subsidyDao;
    @Autowired
    ProvinceDao provinceDao;
    @Autowired
    CityDao cityDao;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Subsidy findOne = subsidyService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody SubsidyVo subsidyVo) {
        Subsidy subsidy = new Subsidy();
        BeanCopy.copyProperties(subsidyVo, subsidy);
        subsidyService.save(subsidy);
        return ResultDataVoUtil.success(subsidy);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        subsidyService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(SubsidyVo subsidyVo) {
        Subsidy subsidy = new Subsidy();
        BeanCopy.copyProperties(subsidyVo, subsidy);
        Subsidy findOne = subsidyService.findOne(subsidy);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(SubsidyVo subsidyVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Subsidy subsidy = new Subsidy();
        BeanCopy.copyProperties(subsidyVo, subsidy);
        Page<Subsidy> findAll = subsidyService.findAll(subsidy, pageable);
        return ResultDataVoUtil.success(findAll);
    }
    
}