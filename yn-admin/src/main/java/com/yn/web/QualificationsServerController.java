package com.yn.web;

import com.yn.model.QualificationsServer;
import com.yn.service.QualificationsServerService;
import com.yn.utils.BeanCopy;
import com.yn.vo.QualificationsServerVo;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/qualificationsServer")
public class QualificationsServerController {
    @Autowired
    QualificationsServerService qualificationsServerService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        QualificationsServer findOne = qualificationsServerService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody QualificationsServerVo qualificationsServerVo) {
        QualificationsServer qualificationsServer = new QualificationsServer();
        BeanCopy.copyProperties(qualificationsServerVo, qualificationsServer);
        qualificationsServerService.save(qualificationsServer);
        return ResultDataVoUtil.success(qualificationsServer);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        qualificationsServerService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(QualificationsServerVo qualificationsServerVo) {
        QualificationsServer qualificationsServer = new QualificationsServer();
        BeanCopy.copyProperties(qualificationsServerVo, qualificationsServer);
        QualificationsServer findOne = qualificationsServerService.findOne(qualificationsServer);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(QualificationsServerVo qualificationsServerVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        QualificationsServer qualificationsServer = new QualificationsServer();
        BeanCopy.copyProperties(qualificationsServerVo, qualificationsServer);
        Page<QualificationsServer> findAll = qualificationsServerService.findAll(qualificationsServer, pageable);
        return ResultDataVoUtil.success(findAll);
    }

    @RequestMapping(value = "/saveBatch", method = {RequestMethod.POST})
    @ResponseBody
    public Object saveBatch(String qualificationsIds, Long serverId) {
        qualificationsServerService.saveBatch(qualificationsIds, serverId);
        return ResultDataVoUtil.success();
    }
}
