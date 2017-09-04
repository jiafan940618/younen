package com.yn.web;

import com.yn.vo.re.ResultVOUtil;
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

import com.yn.model.User;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.vo.UserVo;

@RestController
@RequestMapping("/client/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        User findOne = userService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody UserVo userVo) {
        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        userService.save(user);
        return ResultVOUtil.success(user);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        userService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(UserVo userVo) {
        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        User findOne = userService.findOne(user);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(UserVo userVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        User user = new User();
        BeanCopy.copyProperties(userVo, user);
        Page<User> findAll = userService.findAll(user, pageable);
        return ResultVOUtil.success(findAll);
    }
    
}