package com.yn.web;

import com.yn.model.ApolegamyServer;
import com.yn.service.ApolegamyServerService;
import com.yn.utils.BeanCopy;
import com.yn.vo.ApolegamyServerVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/apolegamyServer")
public class ApolegamyServerController {
    @Autowired
    ApolegamyServerService apolegamyServerService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        ApolegamyServer findOne = apolegamyServerService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ApolegamyServerVo apolegamyServerVo) {
        ApolegamyServer apolegamyServer = new ApolegamyServer();
        BeanCopy.copyProperties(apolegamyServerVo, apolegamyServer);
        apolegamyServerService.save(apolegamyServer);
        return ResultVOUtil.success(apolegamyServer);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        apolegamyServerService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ApolegamyServerVo apolegamyServerVo) {
        ApolegamyServer apolegamyServer = new ApolegamyServer();
        BeanCopy.copyProperties(apolegamyServerVo, apolegamyServer);
        ApolegamyServer findOne = apolegamyServerService.findOne(apolegamyServer);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ApolegamyServerVo apolegamyServerVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ApolegamyServer apolegamyServer = new ApolegamyServer();
        BeanCopy.copyProperties(apolegamyServerVo, apolegamyServer);
        Page<ApolegamyServer> findAll = apolegamyServerService.findAll(apolegamyServer, pageable);
        return ResultVOUtil.success(findAll);
    }
}
