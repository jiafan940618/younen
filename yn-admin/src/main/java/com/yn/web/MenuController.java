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

import com.yn.model.Menu;
import com.yn.service.MenuService;
import com.yn.utils.BeanCopy;
import com.yn.vo.MenuVo;

@RestController
@RequestMapping("/server/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Menu findOne = menuService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

//    @ResponseBody
//    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody MenuVo menuVo) {
        Menu menu = new Menu();
        BeanCopy.copyProperties(menuVo, menu);
        menuService.save(menu);
        return ResultVOUtil.success(menu);
    }

//    @ResponseBody
//    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        menuService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(MenuVo menuVo) {
        Menu menu = new Menu();
        BeanCopy.copyProperties(menuVo, menu);
        Menu findOne = menuService.findOne(menu);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(MenuVo menuVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Menu menu = new Menu();
        BeanCopy.copyProperties(menuVo, menu);
        Page<Menu> findAll = menuService.findAll(menu, pageable);
        return ResultVOUtil.success(findAll);
    }
}
