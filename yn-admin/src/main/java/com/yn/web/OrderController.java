package com.yn.web;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.yn.dao.OrderDao;
import com.yn.dao.OrderPlanDao;
import com.yn.enums.OrderDetailEnum;
import com.yn.enums.ResultEnum;
import com.yn.model.Order;
import com.yn.service.OrderDetailService;
import com.yn.model.Apolegamy;
import com.yn.model.BillOrder;
import com.yn.model.Comment;
import com.yn.model.NewServerPlan;
import com.yn.model.OrderPlan;
import com.yn.model.User;
import com.yn.model.Wallet;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.ApolegamyService;
import com.yn.service.BillOrderService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.StationService;
import com.yn.service.UserService;
import com.yn.service.WalletService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewUserVo;
import com.yn.vo.OrderVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/client/order")
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

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
	@Autowired
	StationService stationService;
	@Autowired
	BillOrderService billOrderService;

	@Autowired
	OrderService ordService;
	@Autowired
	ApolegamyOrderService APOservice;
	 @Autowired
	ApolegamyService apolegamyService;
	
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
	@RequestMapping(value = "/orderDetail"/*
											 * , method = { RequestMethod.POST }
											 */)
	public Object orderDetail(OrderVo orderVo, OrderDetailEnum target) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		Order findOne = orderService.findOne(orderVo.getId());
		Map<String, Object> result = new HashMap<>();
		Wallet wallet = walletService.findWalletByUser(findOne.getUserId());
		User findOne2 = userservice.findOne(findOne.getUserId());
		System.err.println(findOne2.getNickName());
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
		result.put("status", findOne.getStatus());
		result.put("userBalance", wallet.getMoney());
		result.put("nickName", findOne2.getNickName());
		return ResultVOUtil.success(result);
	}

	
	/**
	 * 订单详情页面的两个下一步
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/nextStep", method = { RequestMethod.POST })
	public Object nextStep(Integer nextId, OrderVo orderVo) {
		// 判断参数是否异常
		if (nextId == null || nextId <= 0 || nextId >= 3 || orderVo == null)
			return ResultVOUtil.error(ResultEnum.PARAMS_ERROR);
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		Order findOne = orderService.findOne(order.getId());
		Map<String, String> result = new HashMap<>();
		Double flag4Money = 0d;// 支付的钱
		// flag4ApplyStepA; //完成屋顶勘察预约的
		// flag4ApplyStepB; //完成申请保健的
		// flag4BuildStepA; //完成施工申请的
		// 申请中的下一步
		if (nextId == 1) {
			flag4Money = orderDetailService.calculatedNeedToPayMoney(findOne, 0.3d);
			result.put("flag4ApplyStepA", order.getApplyStepA() > 0 ? true + "" : false + "");
			result.put("flag4ApplyStepB", order.getApplyStepB() > 0 ? true + "" : false + "");
		} else { // 施工中的下一步
			flag4Money = orderDetailService.calculatedNeedToPayMoney(findOne, 0.6d);
			result.put("flag4BuildStepA", order.getBuildStepA() > 0 ? true + "" : false + "");
		}
		result.put("flag4Money", flag4Money < 0 ? true + "" : false + "");
		return ResultVOUtil.success(result);
	}

	/**
	 * 根据用户id查询余额
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBalance", method = { RequestMethod.POST })
	public Object getBalance(User user) {
		Wallet wallet = walletService.findWalletByUser(user.getId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("userBalance", wallet.getMoney().toString());
		return ResultVOUtil.success(map);
	}

	/**
	 * 并网发电 发布评论
	 * 
	 * @param comment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pushComment")
	public Object pushComment(Comment comment) {
		boolean stationRun = orderDetailService.pushComment(comment);
		return ResultVOUtil.success(stationRun);
	}

	/** 订单详情 */
	@ResponseBody
	@RequestMapping(value = "/seeOrder")
	public Object LookOrder(HttpSession session,Integer OrderCode) {

		/*Integer num =100;
		Long userid = 3L;
		  List<Long>  list = new LinkedList<Long>();
	    list.add(12L);
	    list.add(13L);
	    list.add(16L);
	    Double price =11000.00;
    	Long planid =1L;
		*/
	Integer num =(Integer)session.getAttribute("num");
	Long userid = (Long) session.getAttribute("userid");

	List<Long> list=(List<Long>) session.getAttribute("list");

    Double price = (Double)session.getAttribute("price");
   	Long planid =(Long) session.getAttribute("newserverplanid");
    	
    	User user= userservice.findOne(userid);
    	 	logger.info("num为:--- --- ---- ------------"+num);
	        logger.info("方案的id为： ------ ------ ----"+planid);
	        logger.info("用户的id为：  ------ ------ ----"+userid);
	        logger.info("总的金额为： ------ ------ ------"+price);
    	
    	 List<Apolegamy> list01 =  apolegamyService.findAll(list);
    	 
    	 Double apoPrice = 0.0;

	         for (Apolegamy apolegamy : list01) {
	        	 apoPrice += apolegamy.getPrice();
			 }

        Double serPrice = price + apoPrice;

		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

		NewPlanVo newPlanVo = serverService.getPlan(newserverPlan, user, num, serPrice, apoPrice, price);
		
		session.setAttribute("newPlanVo", newPlanVo);
		/*session.setAttribute("list", list);
		session.setAttribute("newserverplanid", planid);*/
 
		return ResultVOUtil.newsuccess(newPlanVo, list01);
	}
	
	/** 订单详情状态 */
	@ResponseBody
	@RequestMapping(value = "/ingorder")
	public Object LookOrder1(HttpSession session,@RequestParam("orderId") Long OrderId) {
		
		Object object = ordService.getInformOrder(OrderId);
		 
		NewPlanVo newPlanVo = ordService.getVoNewPlan(object);
		
		String ids = newPlanVo.getIds();
		
		List<Long> listids =APOservice.Transformation(ids);
		
		List<Apolegamy> list = apolegamyService.findAll(listids);
		
			return ResultVOUtil.newsuccess(newPlanVo, list);
	}
	
	
	/** 修改电站信息 */
	@RequestMapping(value = "/updateInfo")
	@ResponseBody
	public Object udatestation(UserVo userVo, HttpSession session) {
		 User user01 = new User();
		 
	        BeanCopy.copyProperties(userVo, user01);

	        logger.info("地址为：----------- "+user01.getAddressText());
	        logger.info("电话为：----------- "+user01.getPhone());
	        logger.info("用户名为：----------- "+user01.getUserName());
		
		if(null == user01.getAddressText()){
			logger.info("--- ---- --- --- -- 地址不能为空");
			
			return ResultVOUtil.error(777, Constant.ADRESS_NULL);
		}
		if(user01.getAddressText().length()>40){
			
			return ResultVOUtil.error(777, Constant.ADDRESS_LONG);
		}
		
		if(null == user01.getPhone()){
			logger.info("--- ---- --- --- -- 电话不能为空");
			
			return ResultVOUtil.error(777, Constant.PHONE_NULL);
		}
		
		if(!PhoneFormatCheckUtils.isPhoneLegal(user01.getPhone())){
			logger.info("--- ---- --- --- -- 请输入正确的电话格式");
			return ResultVOUtil.error(777, Constant.PHONE_ERROR);
		}
		
		if(null == user01.getUserName()){
			logger.info("--- ---- --- --- -- 联系人不能为空");
			return ResultVOUtil.error(777, Constant.USERNAME_NULL);
		}
		if(user01.getUserName().length()>12){
			logger.info("--- ---- --- --- -- 联系人字符不能超过12位");
			return ResultVOUtil.error(777, Constant.USERNAME_LONG);
		}
		
		
		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");

		NewUserVo newuser = (NewUserVo)session.getAttribute("user");
		
		 /** 前端页面地址的参数*/
		newuser.setFullAddressText(user01.getAddressText());
		
		newuser.setPhone(user01.getPhone());
		newuser.setUserName(user01.getUserName());
		Long userid =(Long) session.getAttribute("userid");
		User user=   userservice.findOne(userid);
		   
		   user.setFullAddressText(user01.getAddressText());
		   user.setPhone(user01.getPhone());
		   user.setUserName(user01.getUserName());
		   
		   userservice.updateUser(user);
		
		

		plan.setAddress(user01.getAddressText());
		plan.setPhone(user01.getPhone());
		plan.setUserName(user01.getUserName());

		session.setAttribute("User", newuser);
		session.setAttribute("newPlanVo", plan);

		return ResultVOUtil.success(newuser);
	}

	/** 点击确定 , 居民状态的放在session中 */
	@ResponseBody
	@RequestMapping(value = "/orderPrice")
	public ResultData<Object> findOrderprice(HttpSession session) {
		NewUserVo newuser = (NewUserVo)session.getAttribute("newuser");
	//	Integer type = (Integer)session.getAttribute("type");
		
		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");
			
		 List<Long>  listid=	(List<Long>) session.getAttribute("list");
		 List<Apolegamy> list =  apolegamyService.findAll(listid);
		 
		Double apoPrice = 0.0;

		for (Apolegamy apolegamy : list) {
			apoPrice += apolegamy.getPrice();
		}

		Long planid =(Long) session.getAttribute("newserverplanid");
		
		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);
		newserverPlan.setMinPurchase(plan.getNum());
		
		User user02 = userservice.findByPhone(plan.getPhone());
		// ** 添加订单*//*

		Order order = newserverPlanService.getOrder(newserverPlan, user02, plan.getAllMoney(), apoPrice,plan.getOrderCode(),null);
		
		// 取出订单号并添加
		order.setOrderCode(plan.getOrderCode());
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

		 /** 添加电站*/
		stationService.insertStation(neworder);
		
		APOservice.getapole(neworder, listid);

		neworder.getUser().setPassword(null);
		
		Map<String, Long> map = new HashMap<String,Long>();
		map.put("orderId", neworder.getId());
		
		 /** 传出电站的id*/ 
		return ResultVOUtil.success(map);
	}
	
	/** 购买完成以后显示订单支付情况 */ /** @RequestParam("orderId") Long orderId*/
	@ResponseBody
	@RequestMapping(value = "/priceorder")
	public Object giveorder(@RequestParam("orderId") Long orderId){

		Object order = orderService.findOrder(orderId);
		
		OrderVo order2 = orderService.getinfoOrder(order);
		   
	List<BillOrder> billOrder =	billOrderService.findByOrderId(orderId);
		
   List<String> list =	billOrderService.getSay(billOrder);
  
			return ResultVOUtil.newsuccess(order2, list);

	}
	
	 /** 显示购买的状态*/
	@ResponseBody
	@RequestMapping(value="/OrderStatus")
	public Object  giveStatus(@RequestParam("orderId") Long orderId){
		
	Order order =	orderService.findstatus(orderId);
		
		
		return ResultVOUtil.success(order);
	}
	
	
	

}
