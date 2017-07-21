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

import com.yn.model.Apolegamy;
import com.yn.service.ApolegamyService;
import com.yn.utils.BeanCopy;
import com.yn.utils.ValidatorUtil;
import com.yn.vo.ApolegamyVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/client/apolegamy")
public class ApolegamyController {
    @Autowired
    ApolegamyService apolegamyService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Apolegamy findOne = apolegamyService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ApolegamyVo apolegamyVo) {
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        apolegamyService.save(apolegamy);
        return ResultDataVoUtil.success(apolegamy);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        apolegamyService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ApolegamyVo apolegamyVo) {
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        Apolegamy findOne = apolegamyService.findOne(apolegamy);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ApolegamyVo apolegamyVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        Page<Apolegamy> findAll = apolegamyService.findAll(apolegamy, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}