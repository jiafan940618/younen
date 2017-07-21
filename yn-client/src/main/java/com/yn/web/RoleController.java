package com.yn.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.yn.dao.RoleDao;
import com.yn.model.Menu;
import com.yn.model.Role;
import com.yn.service.MenuService;
import com.yn.service.RoleService;
import com.yn.utils.BeanCopy;
import com.yn.vo.RoleVo;
import com.yn.vo.re.ResultDataVoUtil;


@RestController
@RequestMapping("/client/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Role findOne = roleService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody RoleVo roleVo) {
        Role role = new Role();
        BeanCopy.copyProperties(roleVo, role);
        roleService.save(role);
        return ResultDataVoUtil.success(role);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        roleService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(RoleVo roleVo) {
        Role role = new Role();
        BeanCopy.copyProperties(roleVo, role);
        Role findOne = roleService.findOne(role);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST})
    @ResponseBody
    public Object findAll(RoleVo roleVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Role role = new Role();
        BeanCopy.copyProperties(roleVo, role);
        Page<Role> findAll = roleService.findAll(role, pageable);
        return ResultDataVoUtil.success(findAll);
    }
}
