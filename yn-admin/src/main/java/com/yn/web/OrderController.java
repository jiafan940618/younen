package com.yn.web;

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

import com.yn.dao.OrderDao;
import com.yn.dao.OrderPlanDao;
import com.yn.dao.ServerDao;
import com.yn.dao.UserDao;
import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.enums.OrderDetailEnum;
import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyOrder;
import com.yn.model.BillOrder;
import com.yn.model.Comment;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Server;
import com.yn.model.Station;
import com.yn.model.User;
import com.yn.model.Wallet;
import com.yn.service.ApolegamyOrderService;
import com.yn.service.ApolegamyService;
import com.yn.service.BillOrderService;
import com.yn.service.NewServerPlanService;
import com.yn.service.NoticeService;
import com.yn.service.OrderDetailService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.StationService;
import com.yn.service.SystemConfigService;
import com.yn.service.UserService;
import com.yn.service.WalletService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.PhoneFormatCheckUtils;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewUserVo;
import com.yn.vo.OrderVo;
import com.yn.vo.ResVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;

@RestController
@RequestMapping("/server/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	ApolegamyService apolegamyService;
	@Autowired
	NoticeService noticeService;
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
	OrderService ordService;
	@Autowired
	ApolegamyOrderService APOservice;
	@Autowired
	BillOrderService billOrderService;
	@Autowired
	StationService stationService;
	@Autowired
	UserDao userDao;
	@Autowired
	ServerDao serverDao;
	@Autowired
	SystemConfigService systemConfigService;

	@RequestMapping(value = "/select")
	@ResponseBody
	public Object findOne(Long id) {

		Order findOne = orderService.findOne(id);

		// 更新记录为已读
		if (findOne != null) {
			Long userId = SessionCache.instance().getUserId();
			if (userId != null) {
				noticeService.update2Read(NoticeEnum.NEW_ORDER.getCode(), id, userId);
			}
		}
		String imgUrl = findOne.getApplyStepbimgUrl();
		
		List list = new LinkedList();
		
		if(null != imgUrl && !imgUrl.equals("")){
			
		String[] img = imgUrl.split(",");

			for (int i = 0; i < img.length; i++) {
				list.add(img[i]);
			}
		
		}

		return ResultVOUtil.newsuccess(findOne,list);
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public Object save(@RequestBody OrderVo orderVo) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		// 修改订单的状态为施工中。
		Integer applyStepA = 0;
		if(order.getApplyStepA()!=null){
			applyStepA = order.getApplyStepA();
		}
		Integer applyStepB =0;
		if( order.getApplyStepB()!=null){
			applyStepB = order.getApplyStepB();
		}
		
		if (applyStepA == 2 && applyStepB == 2) {
			order.setStatus(1);
		}
		Integer buildStepB = 0;
		if(order.getBuildStepB()!=null){
			buildStepB =order.getBuildStepB();
		}
		if(order.getStatus()!=null){
			if(order.getStatus()==1){
				/** 添加电站 */
				Station station = new Station();
				station.setOrder(order);
				station.setOrderId(order.getId());
				Station findOne = stationService.findOne(station);
				if(findOne==null){
					stationService.insertStation(order);
				}
				//绑定电表
				//修改施工状态。
				if(buildStepB==0){
					//初始化操作
					orderService.updateConstructionStatus(order, null,null);
				}else{
					ResVo rv = new ResVo();
					if(buildStepB==1){
						rv.setTarget("materialapproac");
						rv.setTitle("材料进场");
					}else if(buildStepB==2){
						rv.setTarget("foundationbuilding");
						rv.setTitle("基础建筑");
					}else if(buildStepB==3){
						rv.setTarget("supportinstallation");
						rv.setTitle("支架安装");
					}else if(buildStepB==4){
						rv.setTarget("photovoltaicpanelinstallation");
						rv.setTitle("光伏板安装");
					}else if(buildStepB==5){
						rv.setTarget("dcconnection");
						rv.setTitle("直流接线");
					}else if(buildStepB==6){
						rv.setTarget("electricboxinverter");
						rv.setTitle("电箱逆变器");
					}else if(buildStepB==7){
						rv.setTarget("busboxinstallation");
						rv.setTitle("汇流箱安装");
					}else if(buildStepB==8){
						rv.setTarget("acline");
						rv.setTitle("交流辅线");
					}else if(buildStepB==8){
						rv.setTarget("lightningprotectiongroundingtest");
						rv.setTitle("防雷接地测试");
					}else if(buildStepB==10){
						rv.setTarget("gridconnectedacceptance");
						rv.setTitle("材料并网验收场");
					}
					orderService.updateConstructionStatus(order, buildStepB, rv);
				}
			}
		}
		Integer gridConnectedStepA =0; 
		if(order.getGridConnectedStepA()!=null){
			gridConnectedStepA = 	order.getGridConnectedStepA();
		}
		//并网申请中
		if (buildStepB == 10 && gridConnectedStepA == 1) {
			order.setStatus(2);
		}
		//并网发电。
		if (gridConnectedStepA == 2) {
			order.setStatus(3);
		}
		orderService.save(order);
		return ResultVOUtil.success(order);
	}


	/** 删除订单*/
	@ResponseBody
	@RequestMapping(value = "/delete", method ={RequestMethod.POST})
	public Object delete(Long id) {
		
		
		Station station = stationService.FindByStationCode(id);
		
		if(null != station){
			
			return ResultVOUtil.error(777, "该订单已绑定电站,不能删除!");
		}

		orderService.delete(id);
		
		ApolegamyOrder apo = new ApolegamyOrder();
		
		apo.setOrderId(id);
		
		OrderPlan orderPlan = new OrderPlan();
		
		orderPlan.setOrderId(id);
		
		apo = APOservice.findOne(apo);
		
		orderPlan =	orderPlanService.findOne(orderPlan);
		
		APOservice.delete(apo.getId());
		
		orderPlanService.delete(orderPlan.getId());

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
	public Object findAll(OrderVo orderVo,Long managerId,
			@PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
		Order order = new Order();
		BeanCopy.copyProperties(orderVo, order);
		if(userDao.findOne(managerId).getRoleId()==Long.parseLong(systemConfigService.get("server_role_id"))){
			Long serverId=serverDao.findByUserid(managerId);
			order.setServerId(serverId);
		}
          Page<Order> findAll = orderService.findAll(order, pageable);

		// 判断是否已读
		Long userId = SessionCache.instance().getUserId();
		if (userId != null) {
			List<Order> content = findAll.getContent();
			for (Order one : content) {
				Boolean isNew = noticeService.findIsNew(NoticeEnum.NEW_ORDER.getCode(), one.getId(), userId);
				if (isNew) {
					one.setIsRead(NoticeEnum.UN_READ.getCode());
				}
			}
		}

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
	 * 账目明细
	 *
	 * @return
	 */
	@RequestMapping(value = "/detailAccounts", method = { RequestMethod.POST })
	@ResponseBody
	public Object detailAccounts(Long serverId) {
		OrderDetailAccounts detailAccounts = orderService.detailAccounts(serverId);
		return ResultVOUtil.success(detailAccounts);
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
	public Object LookOrder(HttpSession session) {

		// Long userid = (Long) session.getAttribute("userid");
		Long userid = 3L;
		User user = userservice.findOne(userid);
		// List<Long> list= (List<Long>) session.getAttribute("list");
		// Double price = (Double)session.getAttribute("price");
		// Long planid = (Long) session.getAttribute("newserverplanid");
		List<Long> list = new LinkedList<Long>();
		list.add(12L);
		list.add(13L);
		list.add(16L);

		Double price = 5000.00;
		Long planid = 1L;

		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);

		NewPlanVo newPlanVo = new NewPlanVo();

		Server server = serverService.findOne(newserverPlan.getServerId());

		newPlanVo.setCompanyName(server.getCompanyName());
		newPlanVo.setPhone(user.getPhone());
		newPlanVo.setUserName(user.getUserName());
		newPlanVo.setAddress(user.getAddressText());
		newPlanVo.setId(newserverPlan.getId().intValue());
		newPlanVo.setServerId(newserverPlan.getServerId().intValue());
		newPlanVo.setMaterialJson(newserverPlan.getMaterialJson());
		newPlanVo.setInvstername(
				newserverPlan.getInverter().getBrandName() + "   " + newserverPlan.getInverter().getModel());
		newPlanVo.setBrandname(
				newserverPlan.getSolarPanel().getBrandName() + "   " + newserverPlan.getSolarPanel().getModel());
		newPlanVo.setAllMoney(price);

		session.setAttribute("newPlanVo", newPlanVo);

		return ResultVOUtil.success(newPlanVo);
	}

	/** 订单详情状态 */
	@ResponseBody
	@RequestMapping(value = "/ingorder")
	public Object LookOrder1(HttpSession session, @RequestParam("orderId") Long OrderId) {

		Object object = ordService.getInformOrder(OrderId);

		NewPlanVo newPlanVo = ordService.getVoNewPlan(object);

		String ids = newPlanVo.getIds();

		List<Long> listids = APOservice.Transformation(ids);

		List<Apolegamy> list = apolegamyService.findAll(listids);

		return ResultVOUtil.newsuccess(newPlanVo, list);
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

		NewUserVo newuser = (NewUserVo) session.getAttribute("user");

		/** 前端页面地址的参数 */
		newuser.setFullAddressText(user01.getAddressText());

		newuser.setPhone(user01.getPhone());
		newuser.setUserName(user01.getUserName());
		Long userid = (Long) session.getAttribute("userid");
		User user = userservice.findOne(userid);

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
		NewUserVo newuser = (NewUserVo) session.getAttribute("newuser");
		// Integer type = (Integer)session.getAttribute("type");

		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");

		List<Long> listid = (List<Long>) session.getAttribute("list");
		List<Apolegamy> list = apolegamyService.findAll(listid);

		Double apoPrice = 0.0;

		for (Apolegamy apolegamy : list) {
			apoPrice += apolegamy.getPrice();
		}

		Long planid = (Long) session.getAttribute("newserverplanid");

		NewServerPlan newserverPlan = newserverPlanService.findOne(planid);
		newserverPlan.setMinPurchase(plan.getNum());

		User user02 = userservice.findByPhone(plan.getPhone());
		// ** 添加订单*//*

		Order order = newserverPlanService.getOrder(newserverPlan, user02, plan.getAllMoney(), apoPrice,
				plan.getOrderCode(), null,0);

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

		/** 添加电站 */
		stationService.insertStation(neworder);

		APOservice.getapole(neworder, listid);

		neworder.getUser().setPassword(null);

		Map<String, Long> map = new HashMap<String, Long>();
		map.put("orderId", neworder.getId());

		/** 传出电站的id */
		return ResultVOUtil.success(map);
	}

	@ResponseBody
	@RequestMapping(value = "/looking")
	public ResultData<Object> findOrderprice1(HttpSession session) {

		NewPlanVo plan = (NewPlanVo) session.getAttribute("newPlanVo");

		// required=false

		return null;
	}

	/** 购买完成以后显示订单支付情况 */
	/** @RequestParam("orderId") Long orderId */
	@ResponseBody
	@RequestMapping(value = "/priceorder")
	public Object giveorder(@RequestParam("orderId") Long orderId) {

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

		return ResultVOUtil.success(order);
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
	 * 设置贷款成功/失败
	 * 
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
