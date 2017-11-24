package com.yn.web;

import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyServer;
import com.yn.model.Server;
import com.yn.service.ApolegamyServerService;
import com.yn.service.ApolegamyService;
import com.yn.service.NewServerPlanService;
import com.yn.service.ServerService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.ApolegamyVo;
import com.yn.vo.ServerVo;
import com.yn.vo.re.ResultVOUtil;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/apolegamy")
public class ApolegamyController {
	
	private static final Logger logger = LoggerFactory.getLogger(ApolegamyController.class);
	
    @Autowired
    ApolegamyService apolegamyService;
    @Autowired
    NewServerPlanService newServerPlanService;
    @Autowired
    ServerService serverService;
    @Autowired
    ApolegamyServerService apolegamyServerService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Apolegamy findOne = apolegamyService.findOne(id);
        return ResultVOUtil.success(findOne);
    }
   /** 修改信息*/
    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ApolegamyVo apolegamyVo,HttpSession httpSession) {
    	
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        apolegamyService.save(apolegamy);
       
        return ResultVOUtil.success(apolegamy);
    }
    /** 保存信息*/
    @ResponseBody
    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    public Object insert(@RequestBody ApolegamyVo apolegamyVo,HttpSession httpSession) {
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        apolegamyService.save(apolegamy);
        
        ApolegamyServer apolegamyServer = new ApolegamyServer();
        apolegamyServer.setApolegamyId(apolegamy.getId());
        
        Server server =(Server) httpSession.getAttribute("server");
        
        apolegamyServer.setServerId(server.getId()); 
        
        apolegamyServerService.save(apolegamyServer);
        
        return ResultVOUtil.success(apolegamy);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        apolegamyService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ApolegamyVo apolegamyVo) {
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        Apolegamy findOne = apolegamyService.findOne(apolegamy);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ApolegamyVo apolegamyVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Apolegamy apolegamy = new Apolegamy();
        BeanCopy.copyProperties(apolegamyVo, apolegamy);
        Page<Apolegamy> findAll = apolegamyService.findAll(apolegamy, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    
    
    @RequestMapping(value = "/findApo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findServerAll(com.yn.model.Page<Apolegamy> page,HttpSession httpSession) {
    	
    	Server server =(Server) httpSession.getAttribute("server");

    	page.setId(server.getId());
    	
    	List<Apolegamy> list = apolegamyService.getPage(page);
    	
    	Integer count = apolegamyService.getCount(page);

    	page.setTotal(count);
 	
 
        return ResultVOUtil.newsuccess(page,list);
    }
    
}