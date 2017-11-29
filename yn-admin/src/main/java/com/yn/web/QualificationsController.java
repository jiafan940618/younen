package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.yn.model.Qualifications;
import com.yn.model.QualificationsServer;
import com.yn.model.Server;
import com.yn.service.QualificationsServerService;
import com.yn.service.QualificationsService;
import com.yn.service.ServerService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.QualificationsVo;

@RestController
@RequestMapping("/server/qualifications")
public class QualificationsController {
    @Autowired
    QualificationsService qualificationsService;
    @Autowired
    QualificationsServerService qualificationsServerService;
    @Autowired
    ServerService serverService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Qualifications findOne = qualificationsService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody QualificationsVo qualificationsVo,HttpSession session) {

        Qualifications qualifications = new Qualifications();
        BeanCopy.copyProperties(qualificationsVo, qualifications);
        qualificationsService.saveWithApolegamy(qualifications);

        return ResultVOUtil.success(qualifications);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        qualificationsService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(QualificationsVo qualificationsVo) {
        Qualifications qualifications = new Qualifications();
        BeanCopy.copyProperties(qualificationsVo, qualifications);
        Qualifications findOne = qualificationsService.findOne(qualifications);
        return ResultVOUtil.success(findOne);
    }
    
     /** 展示选择的配选项目*/
    @ResponseBody
    @RequestMapping(value = "/findServer", method = {RequestMethod.POST})
    public Object findByServerId(QualificationsVo qualificationsVo,HttpSession session) {
    	
    	 SessionCache server =(SessionCache) session.getAttribute("SessionCache");
      	
      	if(null == server){
      		
      		return ResultVOUtil.error(777, "抱歉你未登录!");
      	}
      	Server serverResult = serverService.findOne(server.getUserId());
      	
      	List<Qualifications>  list = 	qualificationsService.FindByServerId(serverResult.getId());
      	
        return ResultVOUtil.success(list);
    }
    
    
    
    

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(QualificationsVo qualificationsVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Qualifications qualifications = new Qualifications();
        BeanCopy.copyProperties(qualificationsVo, qualifications);
        Page<Qualifications> findAll = qualificationsService.findAll(qualifications, pageable);
        return ResultVOUtil.success(findAll);
    }
}
