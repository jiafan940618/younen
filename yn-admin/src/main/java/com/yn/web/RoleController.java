package com.yn.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.vo.re.ResultVOUtil;
import net.sf.json.JSONArray;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/server/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuService menuService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        Role findOne = roleService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody RoleVo roleVo) {
        Role role = new Role();
        BeanCopy.copyProperties(roleVo, role);
        roleService.save(role);
        return ResultVOUtil.success(role);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        roleService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(RoleVo roleVo) {
        Role role = new Role();
        BeanCopy.copyProperties(roleVo, role);
        Role findOne = roleService.findOne(role);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST})
    @ResponseBody
    public Object findAll(RoleVo roleVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Role role = new Role();
        BeanCopy.copyProperties(roleVo, role);
        Page<Role> findAll = roleService.findAll(role, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    @RequestMapping(value = "/find",method = {RequestMethod.POST})
    @ResponseBody
    public Object find(Long roleId) {
        Role findOne = roleService.findOne(roleId);

        Map<Menu, List<Menu>> map = new LinkedHashMap<>();
        Set<Menu> menus = findOne.getMenu();
        for (Menu menu : menus) {
            if (menu.getZlevel() == 1) {
                map.put(menu, new ArrayList<>());
            }
        }

        Set<Menu> keySet = map.keySet();
        for (Menu parent : keySet) {
            List<Menu> children = map.get(parent);
            for (Menu menu : menus) {
                if (menu.getZlevel() != 1) {
                    if (menu.getParentId().intValue() == parent.getId().intValue()) {
                        children.add(menu);
                    }
                }
            }
            parent.setChildren(children);
        }

        return ResultVOUtil.success(keySet);
    }

    @RequestMapping(value = "/newFind")
    public void newfind(Long roleId, HttpServletResponse response, HttpServletRequest request) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
       response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");
        

      //  response.setHeader("Access-Control-Allow-Origin", "http://mt.u-en.cn/");

       Long userid = SessionCache.instance().getUserId();

        System.out.println("传递的roleId为:--- --- -- --- --->"+roleId+"userId--- ----"+userid);

        SessionCache.instance().setUserId(1L);

        Role findOne = roleService.findOne(roleId);

        Map<Menu, List<Menu>> map = new LinkedHashMap<>();
        Set<Menu> menus = findOne.getMenu();
        for (Menu menu : menus) {
            if (menu.getZlevel() == 1) {
                map.put(menu, new ArrayList<>());
            }
        }

        Set<Menu> keySet = map.keySet();
        for (Menu parent : keySet) {
            List<Menu> children = map.get(parent);
            for (Menu menu : menus) {
                if (menu.getZlevel() != 1) {
                    if (menu.getParentId().intValue() == parent.getId().intValue()) {
                        children.add(menu);
                    }
                }
            }
            parent.setChildren(children);
        }


        JSONArray jsonArray = JSONArray.fromObject(keySet);
         String result = jsonArray.toString();

          //前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            result = callback + "(" + result + ")";

            response.getWriter().write(result);

    }


    
    /**
     * 权限分配
     * @return
     */
    @RequestMapping(value = "/assign", method = {RequestMethod.POST})
    @ResponseBody
    public Object assign(@RequestParam(value="roleId" ,required=true)Long roleId, @RequestParam(value="menuIds" ,required=true)String menuIds) {
        Set<Menu> menuList = new HashSet<>();
        String[] split = menuIds.split(",");
        for (String idStr : split) {
			Long menuId = Long.valueOf(idStr);
			Menu menu = menuService.findOne(menuId);
			menuList.add(menu);
		}
        
        Role role = roleService.findOne(roleId);
        role.setMenu(menuList);
        roleDao.save(role);
        
        return ResultVOUtil.success();
    }
    
}
