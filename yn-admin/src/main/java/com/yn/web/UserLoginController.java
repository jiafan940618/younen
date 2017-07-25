package com.yn.web;

import com.yn.dao.UserDao;
import com.yn.model.Server;
import com.yn.model.User;
import com.yn.service.ServerService;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.CodeUtil;
import com.yn.utils.Constant;
import com.yn.utils.MD5Util;
import com.yn.utils.ObjToMap;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping(value = "/server/userLogin")
public class UserLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    ServerService serverService;


    /**
     * 登陆接口
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public Object appLogin(@RequestParam("phone")String phone, @RequestParam("password")String password, @RequestParam("code")String code) {
        String sessionCode = SessionCache.instance().getCode();
        if (sessionCode == null || !sessionCode.equals(code)) {
            return ResultDataVoUtil.error(777, Constant.CODE_ERROR);
        }

        User user = userService.findByPhone(phone);
        if (user == null) {
            return ResultDataVoUtil.error(777, Constant.NO_THIS_USER);
        } else if (!user.getPassword().equals(MD5Util.GetMD5Code(password))) {
            return ResultDataVoUtil.error(777, Constant.PASSWORD_ERROR);
        }
        if (user.getRoleId() == null || user.getRoleId() == 6) {
            return ResultDataVoUtil.error(777, "权限不足");
        }

        user.setToken(userService.getToken(user));
        userDao.save(user);

        SessionCache.instance().setUser(user);
        user.setPassword(null);

        // 返回服务商id
        Map<String, Object> objectMap = ObjToMap.getObjectMap(user);
        Server server = new Server();
        server.setUserId(user.getId());
        Server serverResult = serverService.findOne(server);
        if (serverResult != null) {
            objectMap.put("serverId", serverResult.getId());
        }

        return ResultDataVoUtil.success(objectMap);
    }

    /**
     * 图形验证码
     */
    @RequestMapping(value = "/getCode", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 生成随机字串,参数为图形验证码长度
        String code = CodeUtil.generateCode(4);

        // 存入会话session
        HttpSession httpSession = request.getSession(true);
        SessionCache.instance().setCode(code);
        // 生成图片
        int w = 200, h = 80;
        CodeUtil.outputImage(w, h, response.getOutputStream(), code);
        System.out.println(code);
    }

    @RequestMapping(value = "/getMd5", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(String code) {
        String getMD5Code = MD5Util.GetMD5Code(code);
        return ResultDataVoUtil.success(getMD5Code);
    }

}
