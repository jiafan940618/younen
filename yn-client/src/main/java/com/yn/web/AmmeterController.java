package com.yn.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.AmmeterDao;
import com.yn.model.Ammeter;
import com.yn.service.AmmeterService;
import com.yn.utils.BeanCopy;
import com.yn.vo.AmmeterVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/ammeter")
public class AmmeterController {
    @Autowired
    AmmeterService ammeterService;
    @Autowired
    AmmeterDao ammeterDao;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Ammeter findOne = ammeterService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody AmmeterVo ammeterVo) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        ammeterService.save(ammeter);
        return ResultDataVoUtil.success(ammeter);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        ammeterService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(AmmeterVo ammeterVo) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        Ammeter findOne = ammeterService.findOne(ammeter);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(AmmeterVo ammeterVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Ammeter ammeter = new Ammeter();
        BeanCopy.copyProperties(ammeterVo, ammeter);
        Page<Ammeter> findAll = ammeterService.findAll(ammeter, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}
