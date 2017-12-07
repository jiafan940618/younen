package com.yn.web;

import com.yn.vo.re.ResultVOUtil;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import com.yn.model.Ammeter;
import com.yn.model.Order;
import com.yn.model.Station;
import com.yn.model.TransactionRecord;
import com.yn.model.User;
import com.yn.model.VisitorStation;
import com.yn.service.AmmeterService;
import com.yn.service.OrderService;
import com.yn.service.StationService;
import com.yn.service.SystemConfigService;
import com.yn.service.TransactionRecordService;
import com.yn.service.UserService;
import com.yn.service.VisitorStationService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.vo.NewUserVo;
import com.yn.vo.OrderVo;
import com.yn.vo.StationVo;
import com.yn.vo.TransactionRecordVo;
import com.yn.vo.UserVo;
import com.yn.vo.WalletVo;

@RestController
@RequestMapping("/client/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	AmmeterService ammeterService;
	@Autowired
	SystemConfigService systemConfigService;
    @Autowired
    UserService userService;
    @Autowired
    StationService stationService;
    @Autowired
	OrderService orderService;
    @Autowired
	TransactionRecordService transactionRecordService;
    @Autowired
    VisitorStationService visitorStationService;
    
    
    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        User findOne = userService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody UserVo userVo) {
    	
    	/*addressText = "松山湖";
        email = "nicb@engrossing.cn";
        phone = 13450699433;
        realName = "刘";
        userName = "刘先生";*/
    	
    	
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
    public Object findByOne(UserVo userVo,HttpSession httpSession) {
       
    	 User newuserVo = SessionCache.instance().getUser();
    	
    	if(null == newuserVo){
    		return ResultVOUtil.error(5003, "抱歉,您未登录!");
    	}
    	
    	WalletVo walletVo =  userService.findUserPrice(newuserVo.getId());

    	
        return ResultVOUtil.success(walletVo);
    }
    
    /** 修改用户资料*/
    @ResponseBody
    @RequestMapping(value = "/updateSome")
    public Object updateUser(UserVo userVo ,String code4register,HttpSession httpSession) {
    	/*userVo.setId(8L);
    	userVo.setFullAddressText("测试地址");
    	userVo.setEmail("974426563@163.com");
    	userVo.setHeadImgUrl("http://oss.u-en.cn/img/d0b9fdc2-e45c-4fe2-970e-13fbdde03d15.png");
    	userVo.setPhone("13530895662");*/
    	logger.info("--- ---- ---- ------- --FullAddressText:"+userVo.getFullAddressText());
    	logger.info("--- ---- ---- ------- --Email:"+userVo.getEmail());
    	logger.info("--- ---- ---- ------- --HeadImgUrl:"+userVo.getHeadImgUrl());
    	logger.info("--- ---- ---- ------- --Phone:"+userVo.getPhone());
    	
    	User newuserVo = SessionCache.instance().getUser();
    	 

    	if(null == newuserVo){
    		return ResultVOUtil.error(5003, "抱歉,您未登录!");
    	}
    	
    	if(!PhoneFormatCheckUtils.isPhoneLegal(userVo.getPhone())){
			
			return ResultVOUtil.error(777, "抱歉,您的手机号有误!");
		}

    	if(null != userVo.getEmail() || !userVo.getEmail().equals("")){
    		
    		if(!userVo.getEmail().equals(newuserVo.getEmail()) ){

    		User user02 = userService.findByEamil(userVo.getEmail()); 
	    	if(null != user02){
	    		
	    		return ResultVOUtil.error(777, "抱歉,该Email已注册!");
	    	}
    		}	
    	}
    	
    	if(!newuserVo.getPhone().equals(userVo.getPhone())){
    		 User newuser =	userService.findByPhone(userVo.getPhone());
    		    if(null != newuser ){
    		    	return ResultVOUtil.error(777, "该电话号码已注册");
    		    }
    	
    	
    	Long code4registerTime = (Long)httpSession.getAttribute("codeUserTime");
		if (code4registerTime==null) {
			logger.info(" -- -- --- 短信验证码不能为空！");
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		// 如果短信验证码超过了5分钟,获得短信验证码的时间
		Long spaceTime = System.currentTimeMillis() - code4registerTime;
		if(spaceTime > 300000){
			logger.info(" -- -- --- 短信验证码已失效！");
			return ResultVOUtil.error(777, Constant.CODE_AGAIN);
		}
		
		String attribute2 = (String)httpSession.getAttribute("codeUser");
		if (attribute2 == null) {
			logger.info(" -- -- --- 短信验证码不能为空！");
			
			return ResultVOUtil.error(777, Constant.CODE_NULL);
		}
		/** 比较俩次的验证码是否一样 或者 俩次电话不一样都返回一个验证码错误*/
		if (!code4register.equals(attribute2) || !userVo.getPhone().equals(httpSession.getAttribute("user_phone"))) {
			
			logger.info(" -- -- --- 验证码错误");
			
			return ResultVOUtil.error(777, Constant.CODE_ERROR);
		}
    	
    	} 
    	 User user = new User();
         BeanCopy.copyProperties(userVo, user);
         user.setId(newuserVo.getId());
         userService.save(user);
        
        NewUserVo userVo01 = new NewUserVo(); 
        userVo01.setEmail(userVo.getEmail());
        userVo01.setFullAddressText(userVo.getFullAddressText());
        userVo01.setId(userVo.getId());
        userVo01.setNickName(userVo.getNickName());
        userVo01.setUserName(newuserVo.getUserName());
        userVo01.setPhone(userVo.getPhone());
        userVo01.setHeadImgUrl(userVo.getHeadImgUrl());
        

        return ResultVOUtil.success(userVo01);
    }
   
    /** PC端*/
    @ResponseBody
    @RequestMapping(value = "/findSomeUs")
    public Object findSomeUs(UserVo userVo,HttpSession httpSession) {
    	
    	User newuserVo = SessionCache.instance().getUser();
    	
    	if(null == newuserVo){
    		return ResultVOUtil.error(5003, "抱歉,您未登录!");
    	}
    
    	
	    logger.info("-- --- --- --- ---- ---- ---- ---- ---- 传递的用户Id:"+userVo.getId());
	    	/** 电站信息*/
	    List<StationVo> list = stationService.getnewstation(newuserVo.getId());
	     
	    	/** 个人资料*/
	    WalletVo walletVo =  userService.findUserPrice(userVo.getId());
    

    	 return ResultVOUtil.newsuccess(walletVo, list);
    }
    
/** ios端的个人中心*/
  @ResponseBody
  @RequestMapping(value = "/iosFindSomeUs")
  public Object iosfindSomeUs(UserVo userVo,HttpSession httpSession) {
	  User newuserVo = SessionCache.instance().getUser();
  	
  	if(null == newuserVo){
  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	}
	  
  	
    Map<String, String> newmap = new HashMap<String, String>();
  	//userVo.setId(3L);
    logger.info("-- --- --- --- ---- ---- ---- ---- ---- 传递的用户Id:"+userVo.getId());
  	/** 通过userId找到总的发电量*/
  
  	Double power = ammeterService.findByUserId(userVo);
 
    Map<String, String> map = systemConfigService.getlist(); 
	/**植树参数*/
	Double plant_trees_prm = Double.valueOf(map.get("plant_trees_prm"));
	/**co2减排参数*/
	Double CO2_prm = Double.valueOf(map.get("CO2_prm"));
	
	DecimalFormat df = new DecimalFormat("#0.00");
	 /** co2排放量*/
	newmap.put("CO2_prm", df.format(power * CO2_prm));
	 /** 植树参数*/
	newmap.put("plant_trees_prm", df.format(power * plant_trees_prm));
	  
  	/** 个人资料*/
   WalletVo walletVo =  userService.findUserPrice(userVo.getId());
  
   String num = transactionRecordService.FindByNum(userVo.getId())+"";

   newmap.put("num",num);
   newmap.put("Integral",walletVo.getIntegral().toString() );
  	
  	 return ResultVOUtil.newhsuccess(walletVo, newmap);
  }
  
    
    
    
   /** pc端 0.0 后面版本要改为分页的形式*/
    @ResponseBody
    @RequestMapping(value = "/findStationUs")
    public Object findnewStation(HttpSession httpSession) {
	   User newuserVo = SessionCache.instance().getUser();
	  	
	  	if(null == newuserVo){
	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
	  	}

    	 List<StationVo> list = stationService.getnewstation(newuserVo.getId());
    	
    	return ResultVOUtil.success(list);
    }
   
   /** 移动端,显示我的电站*/
    @ResponseBody
    @RequestMapping(value = "/findiosStationUs")
    public Object findioaStation(HttpSession httpSession) {
	  User newuserVo = SessionCache.instance().getUser();

	  	if(null == newuserVo){
	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
	  	}

	  	Map<String, String> map = systemConfigService.getlist(); 

	  	List<StationVo> volist = new LinkedList<StationVo>();
  	  	
  		if(newuserVo.getRoleId() == 7L){

  	    VisitorStation visitorStation	 = visitorStationService.findOne(newuserVo.getVisitorId());
  	  
	  	String[] ids = visitorStation.getStationIds().split(",");
	  	
	  	List<Long> newlist = new LinkedList<Long>();
	  	
	  	for (int i = 0; i < ids.length; i++) {
	  		newlist.add(Long.valueOf(ids[i]));
		}
  	  
	  	volist = stationService.findByList(newlist, map);

  		 } else if(newuserVo.getRoleId() == 6L){
	
		  	volist = stationService.findByUserIdS(newuserVo.getId(), map); 
  		 } else if (newuserVo.getRoleId() == 1L){
  			
  			 if(newuserVo.getVisitorId() == 1){
  				List<Long> ids = stationService.FindByStationId();
  				
  				volist = stationService.findByList(ids, map);
  			 }
  			 
  		 }
    	
    	return ResultVOUtil.success(volist);
    }
    /** 移动端,建设中的电站*/
    @ResponseBody
    @RequestMapping(value = "/buildStationUs")
    public Object buildStation(HttpSession httpSession) {
    	User newuserVo = SessionCache.instance().getUser();
	  	
	  	if(null == newuserVo){
	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
	  	}
	 
    	List<OrderVo> list = orderService.findByUserId(newuserVo);
    	
		return ResultVOUtil.success(list);	
    }
    
    /** 修改移动端电站名*/
    @ResponseBody
	@RequestMapping(value = "/updateUs")
    public Object updateStation(HttpSession httpSession,StationVo stationVo) {
    
    	
    	logger.info("修改的id为 -- ---- --- --- --- "+stationVo.getId());
    	logger.info("修改的电站名为 -- ---- --- --- --- "+stationVo.getStationName());

    	Station station = new Station();
        BeanCopy.copyProperties(stationVo, station);
         
        stationService.save(station);

		return ResultVOUtil.success(null);
    }
   
   /** 移动端的订单管理*/
  @ResponseBody
  @RequestMapping(value = "/findiosQueryOrder")
  public Object findStation(HttpSession httpSession) {
	  User newuserVo = SessionCache.instance().getUser();
	  	
	  	if(null == newuserVo){
	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
	  	}

	  	List<OrderVo> listVo = new LinkedList<OrderVo>();
	  	
	  logger.info("-- --- --- --- ---- ---- ---- ---- ---- 传递的用户Id:"+newuserVo.getId());
  	
	  List<Order>	list = orderService.findByiosstatus(newuserVo);
	  
	  for (Order order : list) {
		  OrderVo orderVo = new OrderVo();  
		  Double   memo =0.0;
			// 进度条
			Double a = order.getTotalPrice(), b = order.getHadPayPrice();

			if(b ==null || b==0.0 ){
				order.setIpoMemo("已支付总工程款的0%");
			}else{
				DecimalFormat df = new DecimalFormat("#.00");
				memo =	Double.parseDouble(df.format(Double.valueOf((b / a)) * 100));
				order.setIpoMemo("已支付总工程款的"+memo+"%");
			}
			
			BeanCopy.copyProperties(order, orderVo);
			
		Station station = stationService.FindByStationCode(orderVo.getId());
		if(null != station){
			orderVo.setStationCode(station.getStationCode());
		}else{
			orderVo.setStationCode("该订单未施工");
		}

		listVo.add(orderVo);
	}
  	return ResultVOUtil.success(listVo);
  }
    
    
    /** 个人中心订单管理*/
    @ResponseBody
    @RequestMapping(value = "/findQueryOrder")
    public Object findOrder(com.yn.model.Page<Order> page,HttpSession httpSession) {
    	//page.setUserId(3l);
    	User newuserVo = SessionCache.instance().getUser();
 	  	
 	  	if(null == newuserVo){
 	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
 	  	}
    	
    	if(null == page.getStatus()){
    		page.setStatus(9);
    	}
    	page.setUserId(newuserVo.getId());
    	
    	logger.info("需要传递的参数为: ---- ----- ----- index->"+page.getIndex());
    	logger.info("需要传递的参数为: ---- ----- ----- userId->"+page.getUserId());
    	logger.info("需要传递的参数为: ---- ----- ----- status->"+page.getStatus());
    	
    List<Order>	list = orderService.findBystatus(page); 
     
    int num = orderService.findByNum(page);
    if(num <=  0){
			page.setTotal(1);
		}else{
			page.setTotal( num%page.getLimit() == 0 ? num/page.getLimit() : (num-num%page.getLimit())/page.getLimit()+1);	
		}
    
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
    
    /** 查询交易记录*/
    @RequestMapping("/userTransactionRecord") 
    @ResponseBody
    public Object helloJsp01(com.yn.model.Page<TransactionRecord>  page,HttpSession httpSession){
    	/*page.setUserId(2L);
    	page.setTime_to("2017-10-20");*/
    	//page.setTime_from("2017-10-03");
    	User newuserVo = SessionCache.instance().getUser();
  	  	
  	  	if(null == newuserVo){
  	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	  	}
    	
  	    page.setUserId(2l);
  	    if(null != page.getTime_to()){
  	    	page.setTime_to(page.getTime_to()+" "+"23:59:59");
  	    }
    	
    	
    	logger.info("传递参数 ---- ----- ----- userId："+page.getUserId());
    	logger.info("传递参数 ---- ----- ----- index："+page.getIndex());
    	logger.info("传递参数 ---- ----- ----- time_from："+page.getTime_from());
    	logger.info("传递参数 ---- ----- ----- time_to："+page.getTime_to());
    	logger.info("传递参数 ---- ----- ----- type："+page.getType());
    	logger.info("传递参数 ---- ----- ----- status："+page.getStatus());
    	logger.info("传递参数 ---- ----- ----- payWay："+page.getPayWay());
    	
    	int total =	transactionRecordService.FindBynewNum(page);
    	
    	 List<TransactionRecord> list = transactionRecordService.GivePage(page);
    	 for (TransactionRecord transactionRecord : list) {
    		 
    		 if(transactionRecord.getPayWay()== 1){
    			 transactionRecord.setRemark("余额支付");
    		 }else if(transactionRecord.getPayWay() == 2){
    			 transactionRecord.setRemark("微信");
    		 }else if(transactionRecord.getPayWay() == 3){
    			 transactionRecord.setRemark("支付宝");
    		 }else if(transactionRecord.getPayWay() == 4){
    			 transactionRecord.setRemark("网银支付");
    		 }else if(transactionRecord.getPayWay() == 5){
    			 transactionRecord.setRemark("快付通");
    		 }
    	 }
    	 /** 总页数*/
    	 if(total <= 0){
 			page.setTotal(1);
 		}else{
 			page.setTotal(total%page.getLimit() == 0 ? total/page.getLimit() : (total-total%page.getLimit())/page.getLimit()+1);
 		}

		return ResultVOUtil.newsuccess(page, list);  
    }
    
    /** ios端接口*/
    
    @RequestMapping("/iosTransactionRecord") 
    @ResponseBody
    public Object ioshelloJs(TransactionRecordVo transactionRecordVo,HttpSession httpSession){
    	
    	User newuserVo = SessionCache.instance().getUser();
  	  	
  	  	if(null == newuserVo){
  	  		return ResultVOUtil.error(5003, "抱歉,您未登录!");
  	  	}
    	
  	  transactionRecordVo.setUserId(newuserVo.getId());
    	
    	//transactionRecordVo.setUserId(3L);
    	logger.info("传递参数 ---- ----- ----- userId："+transactionRecordVo.getUserId());

    //	1、余额支付 2、微信 3、支付宝  4、银联  5 快付通   9、全部*
    	
    	 List<TransactionRecord> list = transactionRecordService.FindByTransactionRecord(transactionRecordVo.getUserId());
    	 
    	 for (TransactionRecord transactionRecord : list) {
    		 
    		 if(transactionRecord.getType() == 1){
    			 transactionRecord.setRemark("余额支付");
    		 }else if(transactionRecord.getType() == 2){
    			 transactionRecord.setRemark("微信");
    		 }else if(transactionRecord.getType() == 3){
    			 transactionRecord.setRemark("支付宝");
    		 }else if(transactionRecord.getType() == 4){
    			 transactionRecord.setRemark("网银支付");
    		 }else if(transactionRecord.getType() == 5){
    			 transactionRecord.setRemark("快付通");
    		 }
			
		}
    	 
		return ResultVOUtil.success(list);
    }
    
    
    /**
	 * 用户退出登录
	 */
	@ResponseBody
	@RequestMapping(value = "/logout")
	public Object logout(@RequestParam("countType") Integer countType, HttpSession httpSession) {
		
		User user = SessionCache.instance().getUser();
		if(null == user){
			
			return ResultVOUtil.error(5003, "抱歉,您未登录!");
		}

		if(countType == 1){

			 user.setToken(null);
		     userService.updateTokenBeforeLogout(user);

		     httpSession.removeAttribute("SessionCache");

		} else if(countType == 2) {
			httpSession.removeAttribute("server");
		} else if(countType == 3) {
			httpSession.removeAttribute("admin");
		}
		
		return ResultVOUtil.success(null);
	}
    
    
}