package com.yn.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyServer;
import com.yn.model.Order;
import com.yn.model.Server;
import com.yn.model.ServerPlan;
import com.yn.model.User;
import com.yn.service.ApolegamyServerService;
import com.yn.service.ApolegamyService;
import com.yn.service.ServerPlanService;
import com.yn.utils.BeanCopy;
import com.yn.utils.ResultData;
import com.yn.vo.ServerPlanVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/serverPlan")
public class ServerPlanController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServerPlanController.class);
	
	@Autowired
    ApolegamyServerService apolegamyserService;
    @Autowired
	ApolegamyService apolegamyService;
    @Autowired
    ServerPlanService serverPlanService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        ServerPlan findOne = serverPlanService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ServerPlanVo serverPlanVo) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        serverPlanService.save(serverPlan);
        return ResultVOUtil.success(serverPlan);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        serverPlanService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ServerPlanVo serverPlanVo) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        ServerPlan findOne = serverPlanService.findOne(serverPlan);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ServerPlanVo serverPlanVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        Page<ServerPlan> findAll = serverPlanService.findAll(serverPlan, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    
   
    
    /** 方案接口*/
    @ResponseBody
    @RequestMapping(value = "/Orderplan")
    public ResultData<Object> findOrderplan(ServerPlanVo serverPlanVo) {
  
        ServerPlan serverPlan = new ServerPlan();
        serverPlan.setId(serverPlanVo.getServerId());

       List<ServerPlan>  list = serverPlanService.findAll(serverPlan);

        return ResultVOUtil.success(list);
    }
    
    /** 配选项目*/
    @ResponseBody
    @RequestMapping(value = "/apolegamy")
    public ResultData<Object> findApolegamy(ServerPlanVo serverPlanVo) {

         ApolegamyServer apolegamyServer = new ApolegamyServer();
         apolegamyServer.setServerId(serverPlanVo.getServerId());
         
         List<ApolegamyServer>  list = apolegamyserService.findAll(apolegamyServer);      
    	
        return ResultVOUtil.success(list);
    }
    
    
    
    
    
}