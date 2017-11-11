package com.yn.web;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.yn.dao.OrderDao;
import com.yn.dao.OrderPlanDao;
import com.yn.dao.mapper.OrderMapper;
import com.yn.enums.OrderDetailEnum;
import com.yn.enums.ResultEnum;
import com.yn.model.Apolegamy;
import com.yn.model.BillOrder;
import com.yn.model.Comment;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.UploadPhoto;
import com.yn.model.User;
import com.yn.model.Wallet;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.ApolegamyService;
import com.yn.service.BillOrderService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderDetailService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.OssService;
import com.yn.service.ServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.StationService;
import com.yn.service.UploadPhotoService;
import com.yn.service.UserService;
import com.yn.service.WalletService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.JsonUtil;
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
	private UserService userService;
	@Autowired
	private OssService oss;
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
	UploadPhotoService uploadPhotoService;
	@Autowired
	OrderService ordService;
	@Autowired
	ApolegamyOrderService APOservice;
	@Autowired
	ApolegamyService apolegamyService;
	@Autowired
	private OrderMapper orderMapper;

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
		if (nextId == null || nextId <= 0 || nextId >= 4 || orderVo == null)
			return ResultVOUtil.error(ResultEnum.PARAMS_ERROR);
		if (orderVo != null) {
			if (orderVo.getId() == null) {
				return ResultVOUtil.error(ResultEnum.PARAMS_ERROR);
			}
		}
		Order order = new Order();
		// BeanCopy.copyProperties(orderVo, order);
		Order findOne = orderService.findOne(orderVo.getId());
		Map<String, Object> result = new HashMap<>();
		Double flag4Money = 1d;// 支付的钱
		System.err.println(order.getApplyStepA());
		// flag4ApplyStepA; //完成屋顶勘察预约的
		// flag4ApplyStepB; //完成申请保健的
		// flag4BuildStepA; //完成施工申请的
		// 申请中的下一步
		if (nextId == 1) {
			flag4Money = orderDetailService.calculatedNeedToPayMoney(findOne, 0.3d);
			result.put("flag4ApplyStepA", findOne.getApplyStepA() == 2 ? 1 : 0);
			result.put("applyStepBImgUrl",
					findOne.getApplyStepBImgUrl() == null || findOne.getApplyStepBImgUrl().length() < 1 ? 0 : 1);
			result.put("flag4ApplyStepB", findOne.getApplyStepB() == 2 ? 1 : 0);
			result.put("applyIsPay", findOne.getApplyIsPay() == 1 ? 1 : 0);
			if(flag4Money==0){
				//findOne.setStatus(1);
				boolean step = orderService.updateOrderStauts43Step(findOne);
				result.put("checkUpdate", step);
			}
		} else if (nextId == 2) { // 施工中的下一步
			flag4Money = orderDetailService.calculatedNeedToPayMoney(findOne, 0.6d);
			result.put("buildIsPay", findOne.getBuildIsPay() == 1 ? 1 : 0);
			result.put("flag4BuildStepA", findOne.getBuildStepA() == 1 ? 1 : 0);
			result.put("flag4BuildStepB", findOne.getBuildStepB() == 10 ? 1 : 0);
			if(flag4Money==0){
				//findOne.setStatus(2);
				boolean step = orderService.updateOrderStauts43Step(findOne);
				result.put("checkUpdate", step);
			}
		} else {// 进入并网发电、ios端
			flag4Money = orderDetailService.calculatedNeedToPayMoney(findOne, 0.6d);
			result.put("gridConnectedIsPay", findOne.getGridConnectedIsPay());// 并网发电支付状态
			result.put("gridConnectedStepA", findOne.getGridConnectedStepA());// 并网发电并网状态
		}
		result.put("flag4Money", flag4Money == 0 ? 1 : 0);
		result.put("loanStatus", findOne.getLoanStatus());// 贷款状态
		result.put("status", findOne.getStatus());// 订单状态
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
		Long orderId = comment.getOrderId();
		System.out.println(orderId + "-->orderId");
		Order order = orderService.findOne(orderId);
		// 支付过的或者贷款的。
		if (order.getLoanStatus() == 2 || order.getGridConnectedIsPay() == 2) {
			// 并网完成的。
			if (order.getGridConnectedStepA() == 2) {
				boolean stationRun = orderDetailService.pushComment(comment);
				return ResultVOUtil.success(stationRun);
			} else {
				return ResultVOUtil.error(-1, "并网尚未完成。");
			}
		}
		return ResultVOUtil.error(-1, "操作异常，请联系客服。");
	}

	/** 订单详情 */
	@ResponseBody
	@RequestMapping(value = "/seeOrder")
	public Object LookOrder(HttpSession session, Integer OrderCode) {

		/*
		 * Integer num =6; Long userid = 2L; List<Long> list = new
		 * LinkedList<Long>(); list.add(11L); list.add(13L);
		 * 
		 * Double price =7700.00; Long planid =1L;
		 */

		Double num = (Double) session.getAttribute("num");
		Long userid = (Long) session.getAttribute("userid");

		List<Long> list = (List<Long>) session.getAttribute("list");

		Double price = (Double) session.getAttribute("price");
		Long planid = (Long) session.getAttribute("newserverplanid");

		User user = userservice.findOne(userid);
		logger.info("num为:--- --- ---- ------------" + num);
		logger.info("方案的id为： ------ ------ ----" + planid);
		logger.info("用户的id为：  ------ ------ ----" + userid);
		logger.info("方案项目的金额为： ------ ------ ------" + price);

		List<Apolegamy> list01 = apolegamyService.findAll(list);

		Double apoPrice = 0.0;

		for (Apolegamy apolegamy : list01) {
			apoPrice += apolegamy.getPrice();
		}

		Double serPrice = price + apoPrice;

		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

		NewPlanVo newPlanVo = serverService.getPlan(newserverPlan, user, num, serPrice, apoPrice, price);

		String orderCode = (String) session.getAttribute("orderCode");

		newPlanVo.setOrderCode(orderCode);

		session.setAttribute("newPlanVo", newPlanVo);

		session.setAttribute("orderCode", newPlanVo.getOrderCode());

		return ResultVOUtil.newsuccess(newPlanVo, list01);
	}

	//, @RequestParam("orderId") Long OrderId
	/** 订单详情状态 */
	@ResponseBody
	@RequestMapping(value = "/ingorder")
	public Object LookOrder1(HttpSession session, @RequestParam("orderId") Long OrderId) {

		Object object = ordService.getInformOrder(OrderId);

		NewPlanVo newPlanVo = ordService.getVoNewPlan(object);

		String ids = newPlanVo.getIds();

		List<Long> listids = APOservice.Transformation(ids);

		List<Apolegamy> list = apolegamyService.findAll(listids);
		
		session.setAttribute("orderCode", newPlanVo.getOrderCode());

		return ResultVOUtil.newsuccess(newPlanVo, list);
	}
	
	/** ios 订单详情状态*/
	//, @RequestParam("orderId") Long OrderId
	@ResponseBody
	@RequestMapping(value = "/IosIngorder")
	public Object LookIosOrder(HttpSession session,@RequestParam("orderId") Long OrderId) {

		//Long OrderId=1L;
		
		logger.info("OrderId为：----------- " + OrderId);
		
		Object object = ordService.getIosInfoOrder(OrderId);

		NewPlanVo newPlanVo = ordService.getIOsNewPlan(object);
		
		
		List<BillOrder> billlist = billOrderService.findByOrderId(OrderId);

		List<String> newlist = billOrderService.getSay(billlist);
		
		String ids = newPlanVo.getIds();

		List<Long> listids = APOservice.Transformation(ids);

		List<Apolegamy> list = apolegamyService.findAll(listids);
		
		session.setAttribute("orderCode", newPlanVo.getOrderCode());
		
		return ResultVOUtil.Thirdsuccess(newPlanVo, list, newlist);
	}
	

	/** 修改电站信息 */
	@RequestMapping(value = "/updateInfo")
	@ResponseBody
	public Object udatestation(UserVo userVo, HttpSession session) {
		
		User user01 = new User();

		BeanCopy.copyProperties(userVo, user01);

		logger.info("地址为：----------- " + user01.getAddressText());
		logger.info("电话为：----------- " + user01.getPhone());
		logger.info("用户名为：----------- " + user01.getUserName());

		if (null == user01.getAddressText()) {
			logger.info("--- ---- --- --- -- 地址不能为空");

			return ResultVOUtil.error(777, Constant.ADRESS_NULL);
		}
		if (user01.getAddressText().length() > 40) {

			return ResultVOUtil.error(777, Constant.ADDRESS_LONG);
		}

		if (null == user01.getPhone()) {
			logger.info("--- ---- --- --- -- 电话不能为空");

			return ResultVOUtil.error(777, Constant.PHONE_NULL);
		}

		if (!PhoneFormatCheckUtils.isPhoneLegal(user01.getPhone())) {
			logger.info("--- ---- --- --- -- 请输入正确的电话格式");
			return ResultVOUtil.error(777, Constant.PHONE_ERROR);
		}

		if (null == user01.getUserName()) {
			logger.info("--- ---- --- --- -- 联系人不能为空");
			return ResultVOUtil.error(777, Constant.USERNAME_NULL);
		}
		if (user01.getUserName().length() > 12) {
			logger.info("--- ---- --- --- -- 联系人字符不能超过12位");
			return ResultVOUtil.error(777, Constant.USERNAME_LONG);
		}

		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");


		Long userid = (Long) session.getAttribute("userid");


		User user = userservice.findOne(userid);

		user.setFullAddressText(user01.getAddressText());
		
		user.setPhone(user01.getPhone());
		user.setUserName(user01.getUserName());

		userservice.save(user);

		plan.setAddress(user01.getAddressText());
		plan.setPhone(user01.getPhone());
		plan.setUserName(user01.getUserName());
		User newuser = userservice.findOne(userid);
		
		SessionCache.instance().setUser(newuser);
		session.setAttribute("newPlanVo", plan);

		return ResultVOUtil.success(user);
	}

	/** 点击确定 , 居民状态的放在session中 */
	@ResponseBody
	@RequestMapping(value = "/orderPrice")
	public ResultData<Object> findOrderprice(HttpSession session) {
		// NewUserVo newuser = (NewUserVo)session.getAttribute("newuser");
		 Integer type = (Integer)session.getAttribute("type");

		String orderCode = (String) session.getAttribute("orderCode");

		logger.info("---- ---- ---- ------ ----- 保存的订单号为：" + orderCode);
		Order orderSize = orderService.finByOrderCode(orderCode);

		Map<String, Long> map = new HashMap<String, Long>();
		if (null == orderSize) {
			logger.info("---- ---- ---- ------ ----- 开始生成订单");
			NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");

			List<Long> listid = (List<Long>) session.getAttribute("list");
			List<Apolegamy> list = apolegamyService.findAll(listid);

			Double apoPrice = 0.0;

			for (Apolegamy apolegamy : list) {
				apoPrice += apolegamy.getPrice();
			}

			Long planid = (Long) session.getAttribute("newserverplanid");

			NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

			newserverPlan.setMinPurchase(plan.getNum().doubleValue());

			User user02 = userservice.findByPhone(plan.getPhone());
			/** 添加订单*/
			Order order = newserverPlanService.getOrder(newserverPlan, user02, plan.getAllMoney(), apoPrice,
					plan.getOrderCode(), null,type);

			/** 取出订单号并添加*/
			order.setOrderCode(plan.getOrderCode());
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

			logger.info("---- ---- ------ ----- ----- 开始添加记录表");
			APOservice.getapole(neworder, listid);
			logger.info("---- ---- ------ ----- ----- 添加结束！");
			neworder.getUser().setPassword(null);

			map.put("orderId", neworder.getId());

			logger.info("---- ---- ---- ------ ----- 生成订单成功！");
			/** 传出电站的id */
			return ResultVOUtil.success(map);
		}

		logger.info("---- ---- ---- ------ ----- 查询的订单号为：" + orderSize.getOrderCode());
		logger.info("---- ---- ---- ------ ----- 订单已有,跳过添加！");

		map.put("orderId", orderSize.getId());

		return ResultVOUtil.success(map);

	}

	/** Ios点击确定的接口,没有传订单类型，后面在修改 */

	@ResponseBody
	@RequestMapping(value = "/iosorderPrice")
	public ResultData<Object> findIocordprice(HttpSession session, @RequestParam("capacity") Integer capacity) {
		//NewUserVo newuser = (NewUserVo) session.getAttribute("user");
		User newuser = SessionCache.instance().getUser();
		logger.info("传递的装机容量 ： ----- ---- ----- ----- " + capacity);
		String orderCode = (String) session.getAttribute("orderCode");

		logger.info("---- ---- ---- ------ ----- 保存的订单号为：" + orderCode);
		Order orderSize = orderService.finByOrderCode(orderCode);

		Map<String, Long> map = new HashMap<String, Long>();
		if (null == orderSize) {
			logger.info("---- ---- ---- ------ ----- 开始生成订单");
			NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");
			Double price = plan.getUnitPrice().doubleValue() * capacity;
			Double allMoney = 0.0;

			List<Long> listid = (List<Long>) session.getAttribute("list");
			List<Apolegamy> list = apolegamyService.findAll(listid);

			Double apoPrice = 0.0;

			for (Apolegamy apolegamy : list) {
				apoPrice += apolegamy.getPrice();
			}
			allMoney = price + apoPrice;
			logger.info("---- ---- ---- ------ ----- 金额为" + allMoney);
			Long planid = (Long) session.getAttribute("newserverplanid");

			NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

			newserverPlan.setMinPurchase(capacity.doubleValue());

			User user02 = userservice.findByPhone(plan.getPhone());
			/** 添加订单*/
			Order order = newserverPlanService.getOrder(newserverPlan, user02, allMoney, apoPrice, plan.getOrderCode(),
					newuser.getIpoMemo(),0);

			/** 取出订单号并添加*/
			order.setOrderCode(plan.getOrderCode());
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


			logger.info("---- ---- ------ ----- ----- 开始添加记录表");
			APOservice.getapole(neworder, listid);
			logger.info("---- ---- ------ ----- ----- 添加结束！");
			neworder.getUser().setPassword(null);

			map.put("orderId", neworder.getId());

			logger.info("---- ---- ---- ------ ----- 生成订单成功！");
			/** 传出电站的id */
			return ResultVOUtil.success(map);
		}

		logger.info("---- ---- ---- ------ ----- 查询的订单号为：" + orderSize.getOrderCode());
		logger.info("---- ---- ---- ------ ----- 订单已有,跳过添加！");

		map.put("orderId", orderSize.getId());

		return ResultVOUtil.success(map);
	}

	/** 购买完成以后显示订单支付情况 */
	/** @RequestParam("orderId") Long orderId */

	@ResponseBody
	@RequestMapping(value = "/priceorder")
	public Object giveorder(@RequestParam("orderId") Long orderId) {
		//Long orderId = 862L;

		Object order = orderService.findOrder(orderId);

		OrderVo order2 = orderService.getinfoOrder(order);

		List<BillOrder> billOrder = billOrderService.findByOrderId(orderId);

		List<String> list = billOrderService.getSay(billOrder);

		return ResultVOUtil.newsuccess(order2, list);

	}

	/** 显示购买的状态 */
	@ResponseBody
	@RequestMapping(value = "/OrderStatus")
	public Object giveStatus(@RequestParam("orderId") Long orderId) {
		Order order = orderService.findstatus(orderId);
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("loanStatus", order.getLoanStatus());
		Order findOne = orderService.findOne(orderId);
		// 进度条
		Double a = findOne.getTotalPrice(), b = findOne.getHadPayPrice();
		if (a == null) {
			a = 0d;
		}
		if (b == null) {
			b = 0d;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		if (df.format(Double.valueOf((b / a)) * 100).equals(".00")) {
			jsonResult.put("progressBar", 0.00);
		} else {
			jsonResult.put("progressBar", Double.parseDouble(df.format(Double.valueOf((b / a)) * 100)));
		}
		jsonResult.put("status", order.getStatus());
		jsonResult.put("applyIsPay", order.getApplyIsPay());
		jsonResult.put("applyStepA", order.getApplyStepA());
		jsonResult.put("applyStepB", order.getApplyStepB());
		jsonResult.put("applyStepBImgUrl",

		findOne.getApplyStepBImgUrl() != null && findOne.getApplyStepBImgUrl().length() > 0 ? 1 : 0);

		jsonResult.put("buildIsPay", order.getBuildIsPay());
		jsonResult.put("buildStepA", order.getBuildIsPay());
		jsonResult.put("buildStepB", order.getBuildStepB());
		jsonResult.put("gridConnectedIsPay", order.getGridConnectedIsPay());
		jsonResult.put("gridConnectedStepA", order.getGridConnectedStepA());
		jsonResult.put("orderCode", order.getOrderCode());
		return ResultVOUtil.success(jsonResult);
	}

	// public ResultData<Object> order_detail(@RequestParam("file_data")
	// MultipartFile[] file_data) throws UnsupportedEncodingException{
	/** 服务商上传营业执照等 */
	@RequestMapping(value = "/paydetail")
	@ResponseBody
	public ResultData<Object> order_detail(MultipartHttpServletRequest request, HttpSession session)
			throws UnsupportedEncodingException {

		logger.info("-- --- ---- ---- ------ -进入上传图片的方法");
		
		request.setCharacterEncoding("UTF-8");
		String finaltime = null;

		String realpath = "/opt/UpaloadImg";
		/** 测试路径 */
		// String realpath ="D://Software//huo";
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());

				ResultData<Object> data = userService.getresult(file);

				if (data.getCode() == 200) {
					finaltime = oss.upload(file, realpath);

					/** 取得文件以后得把文件保存在本地路径 */

					if (finaltime.equals("101")) {
						return ResultVOUtil.error(777, Constant.FILE_ERROR);
					}
					if (finaltime.equals("102")) {
						return ResultVOUtil.error(777, Constant.FILE_NULL);
					}
					// 记录上传该文件后的时间
					int finaltime01 = (int) System.currentTimeMillis();
				}
				if (data.getCode() == 777) {
					return data;
				}
			}
		}
		logger.info("添加图片为：-- --- --- ----- --- --- ----" + finaltime);

		User newuser = SessionCache.instance().getUser();

		UploadPhoto uploadPhoto = new UploadPhoto();
		uploadPhoto.setLoadImg(finaltime);
		uploadPhoto.setUserId(newuser.getId());
		String orderCode = (String) session.getAttribute("orderCode");
		Order order = orderMapper.findOrderCode(orderCode);
		order.setApplyStepBImgUrl(finaltime);
		orderService.updateApplyStepBImgUrl(order);

		logger.info("添加用户的id为：-- --- --- ----- --- --- ----" + newuser.getId());

		uploadPhotoService.save(uploadPhoto);
		return ResultVOUtil.success(finaltime);
	}

	/**
	 * 申请中
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/inApplication")
	public Object inApplication(OrderVo orderVo) {
		Order order = orderService.findOne(orderVo.getId());
		Map<String, Object> jsonResult = new HashMap<>();
		// 取到相同的东西
		jsonResult = wcnmlgbd(order);
		// 独有的东西
		// 预约状态 屋顶勘察
		jsonResult.put("applyStepA", order.getApplyStepA());
		// 申请报建状态
		jsonResult.put("applyStepB", order.getApplyStepB());
		jsonResult.put("applyStepBImgUrl", order.getApplyStepBImgUrl() != null ? 1 : 0);
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * 施工中
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/inConstruction")
	public Object inConstruction(OrderVo orderVo) {
		Map<String, Object> jsonResult = new HashMap<>();
		Order order = orderService.findOne(orderVo.getId());
		// 取到相同的东西
		jsonResult = wcnmlgbd(order);
		// 独有的东西
		// 施工中-施工申请状态
		jsonResult.put("buildStepA", order.getBuildStepA());
		// 施工中-支付状态
		jsonResult.put("buildIsPay", order.getBuildIsPay());
		// 施工中-施工状态
		Integer buildStepB = order.getBuildStepB();
		if (buildStepB == null || buildStepB < 1 || buildStepB == 0) {
			jsonResult.put("buildStepB", "未开始");
		} else if (buildStepB == 1) {
			jsonResult.put("buildStepB", "材料进场");
		} else if (buildStepB == 2) {
			jsonResult.put("buildStepB", "基础建筑");
		} else if (buildStepB == 3) {
			jsonResult.put("buildStepB", "支架安装");
		} else if (buildStepB == 4) {
			jsonResult.put("buildStepB", "光伏板安装");
		} else if (buildStepB == 5) {
			jsonResult.put("buildStepB", "直流接线");
		} else if (buildStepB == 6) {
			jsonResult.put("buildStepB", "电箱逆变器");
		} else if (buildStepB == 7) {
			jsonResult.put("buildStepB", "汇流箱安装");
		} else if (buildStepB == 8) {
			jsonResult.put("buildStepB", "交流辅线");
		} else if (buildStepB == 9) {
			jsonResult.put("buildStepB", "防雷接地测试");
		} else if (buildStepB == 10) {
			jsonResult.put("buildStepB", "并网验收");
		}
		jsonResult.put("buildStepB4Num", buildStepB);

		// jsonResult.put("buildStepB", order.getBuildStepB());
		/* --=>方案设计=--> */
		// 资质照片地址
		// jsonResult.put("qualificationsImgUrl",
		// order.getServer().getQualificationsImgUrl());
		// 其他三种方案设计的图片
		// --=> ? 暂无
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * >>>>>>> acff7fe167a97e3e4633487ebef19c2ade6de0e9 并网发电
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/inGridConnectedGeneration")
	public Object inGridConnectedGeneration(OrderVo orderVo) {
		Map<String, Object> jsonResult = new HashMap<>();
		Order order = orderService.findOne(orderVo.getId());
		// 取到相同的东西
		jsonResult = wcnmlgbd(order);
		// 独有的东西
		// 暂无
		return ResultVOUtil.success(jsonResult);
	}

	private Map<String, Object> wcnmlgbd(Order order) {
		Map<String, Object> jsonResult = new HashMap<>();
		List<BillOrder> billOrder = billOrderService.findByOrderId(order.getId());
		List<String> say = billOrderService.getSay(billOrder);
		jsonResult.put("payCount", say);
		// 贷款进度
		jsonResult.put("loanStatus", order.getLoanStatus());
		// 计算进度条
		// order.getHadPayPrice()))/10;
		Double a = order.getTotalPrice(), b = order.getHadPayPrice();
		DecimalFormat df = new DecimalFormat("#.00");
		if (df.format(Double.valueOf((b / a)) * 100).equals(".00")) {
			jsonResult.put("progressBar", 0.00);
		} else {
			jsonResult.put("progressBar", Double.parseDouble(df.format(Double.valueOf((b / a)) * 100)));
		}
		// jsonResult.put("progressBar", (int) ((b / a) * 10));

		// 支付状态
		jsonResult.put("applyIsPay", order.getApplyIsPay());
		// 订单状态
		jsonResult.put("status", order.getStatus());
		return jsonResult;
	}

	/**
	 * 修改施工中的状态
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateConstructionStatus")
	public Object updateConstructionStatus(Order o, Integer thisStauts, com.yn.vo.ResVo resVo) {
		Order order = orderService.findOne(o.getId());
		System.err.println(order.getConstructionStatus());
		boolean falg = orderService.updateConstructionStatus(order, thisStauts, resVo);
		if (falg) {
			return ResultVOUtil.success();
		} else {
			return ResultVOUtil.error(-1, "修改失败");
		}

	}

	/**
	 * 获取施工中的状态
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getConstructionStatus")
	public Object getConstructionStatus(Order o) {
		Order order = orderService.findOne(o.getId());
		if (order.getConstructionStatus() == null || order.getConstructionStatus().length() < 1) {
			orderService.updateConstructionStatus(order, 0, null);
		}
		Map<String, String> jsonResult = (Map<String, String>) JsonUtil.json2Obj(order.getConstructionStatus());
		jsonResult.put("serverImg", order.getServer().getCompanyLogo());
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * 修改预约状态-->申请预约
	 * 
	 * @param o
	 * @param isOk
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateSurveyappointment")
	public Object updateSurveyappointment(Order o, Integer isOk) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult = orderService.checkSurv(o,isOk);
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * 修改报建状态 -->申请报建
	 * 
	 * @param o
	 * @param isOk
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateGridConnectedPayment")
	public Object updateGridConnectedPayment(Order o, Integer isOk) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult = orderService.checkGrid(o, isOk);
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * 修改施工状态 -->申请施工
	 * 
	 * @param o
	 * @param isOk
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/applyBuild")
	public Object applyBuild(Order o, Integer isOk) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult = orderService.checkApply(o,isOk);
		return ResultVOUtil.success(jsonResult);
	}

	/**
	 * 设置贷款成功/失败
	 * @param o 
	 * @param flag
	 *            true：成功、false：失败
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setLoanStatusSuccessOrFaild")
	public Object setLoanStatusSuccessOrFaild(Order o, Boolean flag) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		Order order = null;
		if (o != null && flag != null)
			order = orderService.findOne(o.getId());
		else
			return ResultVOUtil.error(-1, " Params can not be null ! ");
		// 看看订单是不是在贷款申请中
		if (order.getLoanStatus() == 0)
			return ResultVOUtil.error(0, " The order did not apply for a loan ! ");
		// 再看是不是已经贷款成功

		if (order.getLoanStatus() == 2)
			return ResultVOUtil.error(-2, " The order has been successfully made and can not be duplicated ! ");
		// 或者说是失败的。
		if (order.getLoanStatus() == 3)

			return ResultVOUtil.error(-3, " The order failed and the loan could not be renewed ! ");
		if (flag) {
			boolean isOk = orderService.updateLoanStatus(order, true);
			jsonResult.put("setLoanStatus", true);// 修改成功！
			jsonResult.put("updateLoanStatus", isOk);// 贷款成功！
		} else {
			boolean isOk = orderService.updateLoanStatus(order, false);
			if (isOk)
				jsonResult.put("setLoanStatus", true);// 修改成功！
			else
				jsonResult.put("setLoanStatus", false);// 贷款失败！
		}
		return ResultVOUtil.success(jsonResult);
	}

}
