package com.yn.web;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.model.Order;
import com.yn.model.Station;
import com.yn.model.User;
import com.yn.service.OrderService;
import com.yn.service.StationService;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.vo.OrderVo;
import com.yn.vo.UserVo;
import com.yn.vo.WalletVo;

@RestController
@RequestMapping("/client/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    StationService stationService;
    @Autowired
	OrderService orderService;
    
    
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
    	/*userVo.setId(8L);
    	userVo.setFullAddressText("测试地址");
    	userVo.setEmail("974426563@163.com");
    	userVo.setHeadImgUrl("http://oss.u-en.cn/img/d0b9fdc2-e45c-4fe2-970e-13fbdde03d15.png");
    	userVo.setPhone("13530895662");*/
    	
    	User user = new User();
        BeanCopy.copyProperties(userVo, user);

        userService.updateNewUser(user);
	
        return ResultVOUtil.success("修改成功!");
    }
    
    /** */
    @ResponseBody
    @RequestMapping(value = "/findSomeUs")
    public Object findSomeUs(UserVo userVo) {
    	
    	userVo.setId(3L);
    	logger.info("-- --- --- --- ---- ---- ---- ---- ---- 传递的用户Id:"+userVo.getId());
    	/** 电站信息*/
    List<Station> list = stationService.getstation(userVo.getId());
     
    	/** 个人资料*/
    WalletVo walletVo =  userService.findUserPrice(userVo.getId());
    
    	
    	 return ResultVOUtil.newsuccess(walletVo, list);
    }
    
    /** 个人中心订单管理*/
    @ResponseBody
    @RequestMapping(value = "/findQueryOrder")
    public Object findOrder(com.yn.model.Page<Order> page) {
    	
    	logger.info("需要传递的参数为: ---- ----- ----- index->"+page.getIndex());
    	logger.info("需要传递的参数为: ---- ----- ----- userId->"+page.getUserId());
    	logger.info("需要传递的参数为: ---- ----- ----- status->"+page.getStatus());
    	
    List<Order>	list = orderService.findBystatus(page);
    	
    	
		return ResultVOUtil.newsuccess(page, list);
    }
    
    /** 点击贷款修改订单状态*/
    @ResponseBody
    @RequestMapping(value = "/updateQueOrder")
    public Object updateOrder(OrderVo orderVo) {
    	
    	logger.info("传递的参数： ---- --- ---- ---> "+orderVo.getId());
    	orderVo.setStatus(4);
    	
    	Order order = new Order();
    	BeanCopy.copyProperties(orderVo, order);
    	
    	orderService.updateOrderbyId(order);
    	
	 return ResultVOUtil.success(null);   	
    }
    
     /** 取消退款*/
    @ResponseBody
    @RequestMapping(value = "/updateOrder")
    public Object deleOrder(OrderVo orderVo) {
    	
    	logger.info("传递的参数： ---- --- ---- ---> "+orderVo.getId());
    	
    	Order order = new Order();
    	BeanCopy.copyProperties(orderVo, order);
    	
    	Order order2 = orderService.selectOrderSta(order);
    	if(null != order2){
    		
    		Double num =order2.getHadPayPrice()/order2.getTotalPrice();
    		
    		
    		Integer status = order2.getStatus();
    		if(0< num && num < 0.3){
    			status =0;
    		}else if(0.3<= num && num < 0.6){
    			status= 1;
    		}else if(0.6<= num && num < 1){
    			status= 2;
    		}else if(num == 1){
    			status= 3;
    		}
    		order2.setStatus(status);
    		
    		orderService.updateOrderbyId(order2);
    		
    		return ResultVOUtil.success(null); 
    	}else{
    		
    		logger.info("---- ---- ----- ----- 抱歉,该订单不存在!");
    		
    		return ResultVOUtil.error(777, "抱歉,该订单不存在!"); 
    	}

    }
    /**
	 * 用户退出登录
	 */
	@ResponseBody
	@RequestMapping(value = "/logout")
	public Object logout(@RequestParam("countType") Integer countType, HttpSession httpSession) {

		if(countType == 1){
			httpSession.removeAttribute("user");
		} else if(countType == 2) {
			httpSession.removeAttribute("server");
		} else if(countType == 3) {
			httpSession.removeAttribute("admin");
		}
		
		return ResultVOUtil.success(null);
	}
    
    
}