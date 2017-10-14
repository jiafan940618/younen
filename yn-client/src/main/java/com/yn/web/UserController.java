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
    
    /** 查询出用户的资料*/
    @ResponseBody
    @RequestMapping(value = "/findUser", method = {RequestMethod.POST})
    public Object findByOne(UserVo userVo) {
       
    	//userVo
    	User user = userService.findIdByuser(userVo.getId());

    	
        return ResultVOUtil.success(user);
    }
    
    /** 修改用户资料*/
    @ResponseBody
    @RequestMapping(value = "/updateSome")
    public Object updateUser(UserVo userVo) {
       
    	userVo.setId(8L);
    	userVo.setFullAddressText("测试地址");
    	userVo.setEmail("974426563@163.com");
    	userVo.setHeadImgUrl("http://oss.u-en.cn/img/d0b9fdc2-e45c-4fe2-970e-13fbdde03d15.png");
    	userVo.setPhone("13530895662");
    	
    	User user = new User();
        BeanCopy.copyProperties(userVo, user);

        userService.updateNewUser(user);
	
        return ResultVOUtil.success("修改成功!");
    }
    
}