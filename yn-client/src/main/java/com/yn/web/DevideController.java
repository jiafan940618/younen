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

import com.yn.model.Devide;
import com.yn.service.DevideService;
import com.yn.utils.BeanCopy;
import com.yn.vo.DevideVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/devide")
public class DevideController {
    @Autowired
    DevideService devideService;

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
    
}
