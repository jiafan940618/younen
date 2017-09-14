package com.yn.web;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;



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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yn.dao.OrderDao;
import com.yn.dao.OrderPlanDao;
import com.yn.enums.OrderDetailEnum;
import com.yn.model.Order;
import com.yn.service.OrderDetailService;
import com.yn.model.Apolegamy;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Server;
import com.yn.model.User;
import com.yn.service.ApolegamyService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderDetailService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.UserService;
import com.yn.service.WalletService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.OrderVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/order")
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	ApolegamyService apolegamyService;
	@Autowired
	OrderPlanService orderPlanService;
	@Autowired
	NewServerPlanService newserverPlanService;
	@Autowired
	ServerService serverService;
	@Autowired
	UserService userservice;
	@Autowired
	OrderService orderService;
	@Autowired
	ServerPlanService serverPlanService;
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderPlanDao orderPlanDao;
	@Autowired
	WalletService walletService;
	@Autowired
	OrderDetailService orderDetailService;
	
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	private static DecimalFormat df1 = new DecimalFormat("0000");
	private static Random rd = new Random();
    private  static	SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p',
			'5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
	/** (不能与自定义进制有重复) */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;


	public static String toSerialCode(long id, int s) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			// System.out.println(num + "-->" + ind);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全 
		if (str.length() < s) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			Random rnd = new Random();
			for (int i = 1; i < s - str.length(); i++) {
				sb.append(r[rnd.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}

	@RequestMapping(value = "/select", method = { RequestMethod.POST })
	@ResponseBody
	public Object findOne(Long id) {
		Order findOne = orderService.findOne(id);
		return ResultVOUtil.success(findOne);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody OrderVo orderVo) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		orderService.save(order);
		return ResultVOUtil.success(order);
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Object delete(Long id) {
		orderService.delete(id);
		return ResultVOUtil.success();
	}

	@ResponseBody
	@RequestMapping(value = "/findOne", method = { RequestMethod.POST })
	public Object findOne(OrderVo orderVo) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		Order findOne = orderService.findOne(order);
		return ResultVOUtil.success(findOne);
	}

	@RequestMapping(value = "/findAll", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object findAll(OrderVo orderVo,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		Page<Order> findAll = orderService.findAll(order, pageable);
		return ResultVOUtil.success(findAll);
	}

	/** 线上支付,第一步 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/orderPrices") public ResultData<Object>
	 * findOrderprice(OrderVo orderVo) { Order order =
	 * orderService.findOne(orderVo.getId()); Wallet wallet =
	 * walletService.findOne(orderVo.getUserId()); return
	 * ResultVOUtil.success(null); }
	 */

	/**
	 * 点击订单详情页各个按钮出发同一个接口，但调用不同的函数处理
	 * 
	 * @param orderVo
	 * @param target
	 *            LOANAPPLICATION, APPLYPAYMENT, BUILDPAYMENT,
	 *            GRIDCONNECTEDPAYMENT, SURVEYAPPOINTMENT,
	 *            GRIDCONNECTEDAPPLICATION, BUILDAPPLICATION, STATIONRUN
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/orderDetail", method = { RequestMethod.POST })
	public Object orderDetail(OrderVo orderVo, OrderDetailEnum target) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		Order findOne = orderService.findOne(order);
		Map<String, String> result = new HashMap<>();
		switch (target) {
		case LOANAPPLICATION:
			result = orderDetailService.loanApplication(findOne);// 贷款申请
			break;
		case APPLYPAYMENT:
			result = orderDetailService.applyPayment(findOne);// 申请中线上支付
			break;
		case BUILDPAYMENT:
			result = orderDetailService.buildPayment(findOne);// 建设中线上支付
			break;
		case GRIDCONNECTEDPAYMENT:
			result = orderDetailService.gridConnectedPayment(findOne);// 并网申请线上支付
																		// -->
																		// 报建状态
			break;
		case SURVEYAPPOINTMENT:
			result = orderDetailService.surveyAppointment(findOne);// 勘察预约
			break;
		case GRIDCONNECTEDAPPLICATION:
			result = orderDetailService.gridConnectedApplication(findOne);// 并网申请
																			// -->
																			// 并网发电的线上支付
			break;
		case BUILDAPPLICATION:
			result = orderDetailService.buildApplication(findOne);// 建设中 -->
																	// 施工申请
			break;
		case STATIONRUN:
			result = orderDetailService.stationRun(findOne);// 并网发电
			break;
		default:
			break;
		}
		return ResultVOUtil.success(result);
	}

	/** 订单详情 */
	@ResponseBody
	@RequestMapping(value = "/seeOrder")
	public Object LookOrder(HttpSession session) {

		//Integer num =100;
		Integer num =(Integer)session.getAttribute("num");
		  Long userid = (Long) session.getAttribute("userid");
    	//Long userid = 3L;
	   User user=   userservice.findOne(userid);
	    List<Long>  list=	(List<Long>) session.getAttribute("list");
    	Double price = (Double)session.getAttribute("price");
   	Long	planid =	(Long) session.getAttribute("newserverplanid");
    	
    	
	   /*List<Long>  list = new LinkedList<Long>();
	    list.add(12L);
	    list.add(13L);*/
	   /* list.add(16L);*/
	    
	   /* Double price =11000.00;
    	Long planid =1L;*/
    	
    	 logger.info("num为:--- --- ---- "+num);
	        logger.info("方案的id为： ------ ------ ------"+planid);
	        logger.info("用户的id为： ------ ------ ------"+userid);
	        logger.info("总的金额为： ------ ------ ------"+price);
    	
    	 List<Apolegamy> list01 =  apolegamyService.findAll(list);
    	 
    	 Double apoPrice = 0.0;
         
         for (Apolegamy apolegamy : list01) {
        	 apoPrice += apolegamy.getPrice();
		}



		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

		NewPlanVo newPlanVo = new NewPlanVo();
		// 生成订单编号 

		String orderCode = toSerialCode(newserverPlan.getId(), 4) + format.format(System.currentTimeMillis())
				+ df1.format(rd.nextInt(9999));
		newPlanVo.setOrderCode(orderCode);

		Server server = serverService.findOne(newserverPlan.getServerId());
		/** 后面得加上该字段*/
		newPlanVo.setWarrantyYear(newserverPlan.getCapacity());
		
		newPlanVo.setSerPrice(price + apoPrice );
		newPlanVo.setApoPrice(apoPrice);
		newPlanVo.setNum(num);
		newPlanVo.setCompanyName(server.getCompanyName());
		newPlanVo.setPhone(user.getPhone());
		newPlanVo.setUserName(user.getUserName());
		newPlanVo.setAddress(user.getAddressText());
		newPlanVo.setId(newserverPlan.getId().intValue());
		newPlanVo.setServerId(newserverPlan.getServerId().intValue());
		newPlanVo.setMaterialJson(newserverPlan.getMaterialJson());
		// newPlanVo.setUnitPrice(newserverPlan);
		// newPlanVo.setImg_url(img_url);
		newPlanVo.setInvstername(
		newserverPlan.getInverter().getBrandName() + "   " + newserverPlan.getInverter().getModel());
		newPlanVo.setBrandname(
		newserverPlan.getSolarPanel().getBrandName() + "   " + newserverPlan.getSolarPanel().getModel());
		newPlanVo.setAllMoney(price);
		session.setAttribute("newPlanVo", newPlanVo);
	
		
    	//=
    	 
		return ResultVOUtil.newsuccess(newPlanVo, list01);
	}

	/** 修改电站信息 */
	@RequestMapping(value = "/updateInfo", method = { RequestMethod.POST })
	@ResponseBody
	public Object udatestation(UserVo userVo, HttpSession session) {
		
		if(null == userVo.getAddressText()){
			logger.info("--- ---- --- --- -- 地址不能为空");
			
			return ResultVOUtil.error(777, Constant.ADRESS_NULL);
		}
		if(userVo.getAddressText().length()>40){
			
			return ResultVOUtil.error(777, Constant.ADDRESS_LONG);
		}
		
		if(null == userVo.getPhone()){
			logger.info("--- ---- --- --- -- 电话不能为空");
			
			return ResultVOUtil.error(777, Constant.PHONE_NULL);
		}
		
		if(!PhoneFormatCheckUtils.isPhoneLegal(userVo.getPhone())){
			logger.info("--- ---- --- --- -- 请输入正确的电话格式");
			return ResultVOUtil.error(777, Constant.PHONE_ERROR);
		}
		
		if(null == userVo.getUserName()){
			logger.info("--- ---- --- --- -- 联系人不能为空");
			return ResultVOUtil.error(777, Constant.USERNAME_NULL);
		}
		if(userVo.getUserName().length()>12){
			logger.info("--- ---- --- --- -- 联系人字符不能超过12位");
			return ResultVOUtil.error(777, Constant.USERNAME_LONG);
		}
		
		
		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");

		User newuser =(User)session.getAttribute("user");
		
		 /** 前端页面地址的参数*/
		newuser.setFullAddressText(userVo.getAddressText());
		
		newuser.setPhone(userVo.getPhone());
		newuser.setUserName(userVo.getUserName());
		
		 Long userid = (Long) session.getAttribute("userid");
		   User user=   userservice.findOne(userid);
		   
		   user.setFullAddressText(userVo.getAddressText());
		   user.setPhone(userVo.getPhone());
		   user.setUserName(userVo.getUserName());
		   
		   userservice.save(user);
		

		List<Long>  listid=	(List<Long>) session.getAttribute("list");
		 List<Apolegamy> list =  apolegamyService.findAll(listid);
		
		session.setAttribute("User", newuser);

		plan.setAddress(userVo.getAddressText());
		plan.setPhone(userVo.getPhone());
		plan.setUserName(userVo.getUserName());

	
		session.setAttribute("newPlanVo", plan);
		session.setAttribute("result", ResultVOUtil.newsuccess(plan, list));

		return ResultVOUtil.newsuccess(plan, list);
	}

	/** 点击确定 */
	@ResponseBody
	@RequestMapping(value = "/orderPrice")
	public ResultData<Object> findOrderprice(HttpSession session) {
		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");
			
		 List<Long>  listid=	(List<Long>) session.getAttribute("list");
		 List<Apolegamy> list =  apolegamyService.findAll(listid);
		 
		Double apoPrice = 0.0;
		for (Apolegamy apolegamy : list) {
			apoPrice += apolegamy.getPrice();
		}

		Long planid =(Long) session.getAttribute("newserverplanid");
		
		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);
		User user02 = userservice.findByPhone(plan.getPhone());
		// ** 添加订单*//*

		Order order = newserverPlanService.getOrder(newserverPlan, user02, plan.getAllMoney(), apoPrice,plan.getOrderCode());
		
		// 取出订单号并添加
		NewPlanVo attribute = (NewPlanVo) session.getAttribute("newPlanVo");
		order.setOrderCode(attribute.getOrderCode());
		orderService.save(order);

		Order order02 = new Order();
		order02.setOrderCode(order.getOrderCode());

		Order neworder = orderService.findOne(order02);

		// ** 订单计划表*//*

		Long id = newserverPlan.getId();

		NewServerPlan serverPlan = newserverPlanService.findOne(id);

		OrderPlan orderPlan = newserverPlanService.giveOrderPlan(newserverPlan, neworder);

		OrderPlan orderPlan2 = new OrderPlan();
		orderPlan2.setOrderId(orderPlan.getOrderId());

		OrderPlan newOrdPlan = orderPlanService.findOne(orderPlan2);

		order.setOrderPlanId(newOrdPlan.getId());

		orderPlanService.save(orderPlan);

		neworder.setOrderPlan(newOrdPlan);

		neworder.getUser().setPassword(null);

		return ResultVOUtil.success(null);
	}

}
