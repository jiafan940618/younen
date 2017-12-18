package com.yn.web;

import com.yn.dao.UserDao;
import com.yn.enums.ResultEnum;
import com.yn.enums.RoleEnum;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Server;
import com.yn.model.User;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerService;
import com.yn.service.StationService;
import com.yn.service.SystemConfigService;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.*;
import com.yn.vo.NewUserVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/server/userLogin")
public class UserLoginController {

	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    ServerService serverService;
    
    @Autowired
    NewServerPlanService newserverPlanService;
    
    @Autowired
    OrderService orderService;
    @Autowired
    OrderPlanService orderPlanService;
    @Autowired
    StationService stationService;
    @Autowired
	ApolegamyOrderService APOservice;
    @Autowired
    UserDao userDao;
    @Autowired
    SystemConfigService systemConfigService;

    /**
     * 登入
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    @ResponseBody
    public Object appLogin(@RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("code") String code) {


        // 校验图形验证码
        String sessionCode = SessionCache.instance().getCode();
        if (sessionCode == null || !sessionCode.equals(code)) {
            return ResultVOUtil.error(ResultEnum.CODE_ERROR);
        }


        // 根据 phone 和 account 查找用户
        User user = userService.findByPhoneOrAccountOrEamil(phone);


        // 校验用户权限
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.NO_THIS_USER);
        } else if (user.getRoleId() == null || user.getRoleId().equals(RoleEnum.ORDINARY_MEMBER.getRoleId())) {
            return ResultVOUtil.error(ResultEnum.NO_PERMISSION);
        }


        // 校验密码
        if (!user.getPassword().equals(MD5Util.GetMD5Code(password))) {
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }


        // 更新token
        userService.updateToken(user);


        // 保存用户到session，不返回密码给前端
        SessionCache.instance().setUserId(user.getId());
        user.setPassword(null);

        
        // 返回服务商id
        Map<String, Object> objectMap = ObjToMap.getObjectMap(user);
        if(userDao.findOne(user.getId()).getRoleId()!=Long.parseLong(systemConfigService.get("admin_role_id"))){
        	Server server = new Server();
            server.setUserId(user.getId());
            Server serverResult = serverService.findOne(server);
            if (serverResult != null) {
                objectMap.put("serverId", serverResult.getId());
            }
            System.out.println("用户的id："+serverResult.getUser().getId()+",用户角色："+serverResult.getUser().getRoleId());
        }
        
        return ResultVOUtil.success(objectMap);
    }

    /** 前端跳转后台登陆
     * @throws IOException */
    @RequestMapping(value = "/JumpLogin")
    public void JumpLogin(UserVo userVo,HttpServletResponse response,HttpServletRequest request) throws IOException {

    	response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true");

     //  response.setHeader("Access-Control-Allow-Origin", "http://mt.u-en.cn/");
    	
        User user = userService.findByPhone(userVo.getPhone());

        if (user == null) {
            logger.info("----- --- ----- 该用户不存在！");
            
            JSONArray jsonArray = JSONArray.fromObject(ResultVOUtil.error(777, Constant.NO_THIS_USER));
            String result = jsonArray.toString();

             //前端传过来的回调函数名称
            String callback = request.getParameter("callback");
               //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            result = callback + "(" + result + ")";

            response.getWriter().write(result);

           
        } else if (!user.getPassword().equals(MD5Util.GetMD5Code(userVo.getPassword()))) {
            logger.info("----- --- ----- 密码错误！");
            
            JSONArray jsonArray = JSONArray.fromObject(ResultVOUtil.error(777, Constant.PASSWORD_ERROR));
            String result = jsonArray.toString();

             //前端传过来的回调函数名称
            String callback = request.getParameter("callback");
               //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            result = callback + "(" + result + ")";

            response.getWriter().write(result);

        }else if(user.getPassword().equals(MD5Util.GetMD5Code(userVo.getPassword()))){
        	 
         
        Server server = new Server();
        server.setUserId(user.getId());

        server = serverService.findOne(server);
        if(null == server){
            logger.info("----- --- ----- 抱歉,用户未注册服务商的身份");
            
            JSONArray jsonArray = JSONArray.fromObject(ResultVOUtil.error(777, Constant.PASSWORD_ERROR));
            String result = jsonArray.toString();

             //前端传过来的回调函数名称
               String callback = request.getParameter("callback");
               //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
               result = callback + "(" + result + ")";

               response.getWriter().write(result);
           
        }

        user.setToken(userService.getToken(user));

        userService.updateToken(user);

        SessionCache.instance().setUserId(user.getId());

        user.setPassword(null);
        

        
        JSONArray jsonArray = JSONArray.fromObject(user);
        String result = jsonArray.toString();

         //前端传过来的回调函数名称
           String callback = request.getParameter("callback");
           //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
           result = callback + "(" + result + ")";

           response.getWriter().write(result);

        logger.info("---- ---- --- --- - --- - --- ----结束");
         }
    }

    /** 获得Token实现跨域登陆*/
    @RequestMapping(value = "/logIn")
    @ResponseBody
    public Object logIn(UserVo userVo) {
        User userR = new User();
        BeanCopy.copyProperties(userVo, userR);


        logger.info("拿到Token----- ---- ---- --->"+userR.getToken());

        User user = userService.findOne(userR);
        user.setPassword(null);
        
        Map<String, Object> objectMap = ObjToMap.getObjectMap(user);
        if(userDao.findOne(user.getId()).getRoleId()!=Long.parseLong(systemConfigService.get("admin_role_id"))){
        	Server server = new Server();
            server.setUserId(user.getId());
            Server serverResult = serverService.findOne(server);
            if (serverResult != null) {
                objectMap.put("serverId", serverResult.getId());
            }
            System.out.println("用户的id："+serverResult.getUser().getId()+",用户角色："+serverResult.getUser().getRoleId());
        }


        return ResultVOUtil.success(objectMap);
    }


    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logOut", method = {RequestMethod.POST})
    @ResponseBody
    public Object logOut() {
        SessionCache.clean();
        return ResultVOUtil.success();
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
        return ResultVOUtil.success(getMD5Code);
    }
    
    /** 给用户一键添加订单,电站,绑定电表*/
    @RequestMapping(value = "/produce")
	 @ResponseBody
	    public Object someTest(UserVo userVo,HttpSession session,User user) {

    			userVo.setPhone("13902601226");
    			userVo.setCapacity(10.08);
    			userVo.setPrice(110880.00);
    			userVo.setPlanId(5L);
//    			user.setAddressText("东莞市万江区永泰街徐家尾一巷8号	");
//    			user.setUserName("招杰伟");
//    			
//    			   user.setPhone(userVo.getPhone());
//    			   /** 添加用户 */
//    			   userService.saveQuickly(user);
//    			   logger.info("---- ---- ------ ----- ----- 用户添加成功！");
				   Integer type = userVo.getType();

					logger.info("---- ---- ---- ------ ----- 开始生成订单");

					List<Long> listid = new ArrayList<Long>();

					Double apoPrice = userVo.getPrice();

					Long planid = userVo.getPlanId();

					NewServerPlan newserverPlan = newserverPlanService.findOne(planid);
					User user02 =	userService.findByPhone(userVo.getPhone());
					
				    String orderCode =	serverService.getnewOrderCode(newserverPlan.getServerId(), user02.getProvinceId());
					
					/** 添加订单*/
					Order order = newserverPlanService.getnewOrder(newserverPlan, user02,0.0, apoPrice,
							orderCode, userVo.getCapacity(),type);

					/** 取出订单号并添加*/
					order.setOrderCode(orderCode);
					orderService.newSave(order);

					Order order02 = new Order();
					order02.setOrderCode(order.getOrderCode());

					Order neworder = orderService.findOne(order02);

					/** 订单计划表*/
					Long id = newserverPlan.getId();

					NewServerPlan serverPlan = newserverPlanService.findOne(id);

					OrderPlan orderPlan = newserverPlanService.giveOrderPlan(newserverPlan, neworder);

					OrderPlan orderPlan2 = new OrderPlan();
					orderPlan2.setOrderId(orderPlan.getOrderId());

					OrderPlan newOrdPlan = orderPlanService.findOne(orderPlan2);

					order.setOrderPlanId(newOrdPlan.getId());

					orderPlanService.save(orderPlan);

					neworder.setOrderPlan(newOrdPlan);

					/** 添加电站 */
					stationService.insertSta(neworder);

					logger.info("---- ---- ------ ----- ----- 开始添加记录表");
					APOservice.getapole(neworder, listid);
					logger.info("---- ---- ------ ----- ----- 添加结束！");
					neworder.getUser().setPassword(null);

				
					return ResultVOUtil.success();
				}
    
    
}
