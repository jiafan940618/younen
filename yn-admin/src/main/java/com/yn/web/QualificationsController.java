package com.yn.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Qualifications;
import com.yn.service.QualificationsService;
import com.yn.utils.BeanCopy;
import com.yn.vo.QualificationsVo;
import com.yn.vo.re.ResultDataVoUtil;

@RestController
@RequestMapping("/server/qualifications")
public class QualificationsController {
    @Autowired
    QualificationsService qualificationsService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Qualifications findOne = qualificationsService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody QualificationsVo qualificationsVo) {
        Qualifications qualifications = new Qualifications();
        BeanCopy.copyProperties(qualificationsVo, qualifications);
        qualificationsService.save(qualifications);
        return ResultDataVoUtil.success(qualifications);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        qualificationsService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(QualificationsVo qualificationsVo) {
        Qualifications qualifications = new Qualifications();
        BeanCopy.copyProperties(qualificationsVo, qualifications);
        Qualifications findOne = qualificationsService.findOne(qualifications);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(QualificationsVo qualificationsVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Qualifications qualifications = new Qualifications();
        BeanCopy.copyProperties(qualificationsVo, qualifications);
        Page<Qualifications> findAll = qualificationsService.findAll(qualifications, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}
