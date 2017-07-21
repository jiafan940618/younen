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

import com.yn.model.TemStationYear;
import com.yn.service.TemStationYearService;
import com.yn.utils.BeanCopy;
import com.yn.vo.TemStationYearVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/server/temStationYear")
public class TemStationYearController {
    @Autowired
    TemStationYearService temStationYearService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        TemStationYear findOne = temStationYearService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody TemStationYearVo temStationYearVo) {
        TemStationYear temStationYear = new TemStationYear();
        BeanCopy.copyProperties(temStationYearVo, temStationYear);
        temStationYearService.save(temStationYear);
        return ResultDataVoUtil.success(temStationYear);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        temStationYearService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(TemStationYearVo temStationYearVo) {
        TemStationYear temStationYear = new TemStationYear();
        BeanCopy.copyProperties(temStationYearVo, temStationYear);
        TemStationYear findOne = temStationYearService.findOne(temStationYear);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(TemStationYearVo temStationYearVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        TemStationYear temStationYear = new TemStationYear();
        BeanCopy.copyProperties(temStationYearVo, temStationYear);
        Page<TemStationYear> findAll = temStationYearService.findAll(temStationYear, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}