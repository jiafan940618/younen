package com.yn.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.OrderDao;
import com.yn.dao.mapper.OrderMapper;
import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.enums.OrderEnum;
import com.yn.model.Order;
import com.yn.model.User;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.JsonUtil;
import com.yn.utils.ObjToMap;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewUserVo;
import com.yn.vo.OrderVo;
import com.yn.vo.ResVo;
import com.yn.vo.SVo2;
import com.yn.vo.UserVo;

@Service
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	protected OrderDao orderDao;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private OrderMapper mapper;
	@Autowired
	ApolegamyOrderService apoleService;

	/** 个人中心订单状态查询*/
	public List<Order> findBystatus(com.yn.model.Page<Order> page) {

		return mapper.findBystatus(page);
	}

	public List<Order> findByiosstatus(User userVo) {

		return mapper.findByiosstatus(userVo);
	}

	public int findByNum(com.yn.model.Page<Order> page) {

		return mapper.findByNum(page);
	}

	 /** 根据订单id查找serverId*/
	public Long findByOrderId(Long id){

		return orderDao.findByOrderId(id);
	}

	/** 修改退款状态*/

	public void updateOrderbyId(Order order) {

		orderDao.updateOrderbyId(order);
	}

	public Order selectOrderSta(Order order) {

		Object object = orderDao.selectOrderSta(order);

		Object[] obj = (Object[]) object;
		Integer id = (Integer) obj[0];
		Integer status = (Integer) obj[1];
		BigDecimal hadPayPrice = (BigDecimal) obj[2];
		BigDecimal totalPrice = (BigDecimal) obj[3];

		Order order01 = new Order();
		order01.setId(id.longValue());
		order01.setHadPayPrice(hadPayPrice.doubleValue());
		order01.setStatus(status);
		order01.setTotalPrice(totalPrice.doubleValue());

		return order01;
	}

	public List<OrderVo> findByUserId(User user) {

		Order neworder = new Order();

		List<OrderVo> orderlist = new LinkedList<OrderVo>();

		List<Object> list = orderDao.findsome(user.getId());

		for (Object object : list) {
			Object[] obj = (Object[]) object;
			Integer t_id = (Integer) obj[0];
			BigDecimal capacity = (BigDecimal) obj[1];
			String serverName = (String) obj[2];
			String orderCode = (String) obj[3];
			Integer gridConnectedStepa = (Integer) obj[4];
			Integer s_id = (Integer) obj[5];
			String stationName = (String) obj[6];
			BigDecimal totalPrice = (BigDecimal) obj[7];
			BigDecimal hadPayPrice = (BigDecimal) obj[8];
			Integer status = (Integer) obj[9];

			DecimalFormat df = new DecimalFormat("#0.00");
			String speed = df.format(hadPayPrice.doubleValue() / totalPrice.doubleValue() * 100);

			OrderVo order = new OrderVo();
			order.setId(Long.valueOf(t_id));
			order.setCapacity(capacity.doubleValue());
			order.setServerName(serverName);
			order.setOrderCode(orderCode);
			order.setGridConnectedStepA(gridConnectedStepa);
			order.setUserName(user.getUserName());
			order.setStationId(s_id);
			order.setStationName(stationName);
			order.setSpeed(Double.valueOf(speed));
			// [订单状态]{0:申请中,1:施工中,2:并网发电申请中,3:并网发电,4:退款中,5:退款成功,9:全部}
			order.setStatus(status);
			if (status == 0) {
				order.setIpoMemo("申请中");
			} else if (status == 1) {
				order.setIpoMemo("施工中");
			} else if (status == 2) {
				order.setIpoMemo("并网发电申请中");
			} else if (status == 4) {
				order.setIpoMemo("退款中");
			} else if (status == 5) {
				order.setIpoMemo("退款成功");
			}

			orderlist.add(order);
		}

		return orderlist;
	}

	public Object getIosInfoOrder(Long orderId) {

		return orderDao.getIosInfoOrder(orderId);
	}

	public Order findOne(Long id) {
		return orderDao.findOne(id);
	}

	public Object getInformOrder(Long orderId) {

		return orderDao.getInfoOrder(orderId);
	}

	public boolean checkUpdateOrderStatus(Order order) {
		return mapper.updateByCondition(order) > 0 ? true : false;
	}

	public Order findstatus(Long orderId) {

		return mapper.findstatus(orderId);

	}

	public Object findOrder(Long orderId) {

		return orderDao.findOrder(orderId);
	}

	public Order finByOrderCode(String orderCode) {

		return mapper.findOrderCode(orderCode);
	}

	public void newSave(Order order) {
		orderDao.save(order);
	}

	public void save(Order order) {
		if (order.getId() != null) {
			Order one = orderDao.findOne(order.getId());
			try {
				BeanCopy.beanCopy(order, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			orderDao.save(one);
		} else {
			orderDao.save(order);
		}
	}

	public void delete(Long id) {
		orderDao.delete(id);

		// 删除未读信息
		noticeService.delete(NoticeEnum.NEW_ORDER.getCode(), id);
	}

	public Order FindByTradeNo(String tradeNo) {

		Object object = orderDao.FindByTradeNo(tradeNo);

		Object[] obj = (Object[]) object;
		Integer id = (Integer) obj[0];
		Integer status = (Integer) obj[1];
		BigDecimal hadPayPrice = (BigDecimal) obj[2];
		BigDecimal totalPrice = (BigDecimal) obj[3];

		Order order = new Order();
		order.setId(id.longValue());
		order.setHadPayPrice(hadPayPrice.doubleValue());
		order.setStatus(status);
		order.setTotalPrice(totalPrice.doubleValue());
		return order;
	}

	/** 根据现有的金额,改变订单状态 */
	public void givePrice(Order order) {

		Double num = order.getHadPayPrice() / order.getTotalPrice();

		Integer status = order.getStatus();
		if (0 < num && num < 0.3) {
			status = 0;
		} else if (0.3 <= num && num < 0.6) {
			status = 1;
		} else if (0.6 <= num && num < 1) {
			status = 2;
		} else if (num == 1) {
			status = 3;
		}
		order.setStatus(status);

		mapper.UpdateOrderStatus(order);
	}

	public void deleteBatch(List<Long> id) {
		orderDao.deleteBatch(id);
	}

	public Order findOne(Order order) {
		Specification<Order> spec = getSpecification(order);
		Order findOne = orderDao.findOne(spec);
		return findOne;
	}

	public List<Order> findAll(List<Long> list) {
		return orderDao.findAll(list);
	}

	public Page<Order> findAll(Order order, Pageable pageable) {
		Specification<Order> spec = getSpecification(order);
		Page<Order> findAll = orderDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Order> findAll(Order order) {
		Specification<Order> spec = getSpecification(order);
		return orderDao.findAll(spec);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<Order> getSpecification(Order order) {
		order.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(order);
		return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate conjunction = cb.conjunction();
			List<Expression<Boolean>> expressions = conjunction.getExpressions();
			Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm")
						&& !entry.getKey().equals("queryEndDtm")) {
					Object value = entry.getValue();
					if (value instanceof Map) {
						Iterator<Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
						while (iterator1.hasNext()) {
							Entry<String, Object> entry1 = iterator1.next();
							expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
						}
					} else {
						expressions.add(cb.equal(root.get(entry.getKey()), value));
					}
				}
			}

			// 根据订单号，联系人，联系人手机号，服务商名称
			if (!StringUtils.isEmpty(order.getQuery())) {
				Predicate[] predicates = new Predicate[5];
				predicates[0] = cb.like(root.get("orderCode"), "%" + order.getQuery() + "%");
				predicates[1] = cb.like(root.get("linkMan"), "%" + order.getQuery() + "%");
				predicates[2] = cb.like(root.get("linkPhone"), "%" + order.getQuery() + "%");
				predicates[3] = cb.like(root.get("addressText"), "%" + order.getQuery() + "%");
				predicates[4] = cb.like(root.get("server").get("companyName"), "%" + order.getQuery() + "%");
				expressions.add(cb.or(predicates));
			}

			// 根据日期筛选
			String queryStartDtm = order.getQueryStartDtm();
			String queryEndDtm = order.getQueryEndDtm();
			if (!StringUtils.isEmpty(queryStartDtm)) {
				expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"),
						DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}
			if (!StringUtils.isEmpty(queryEndDtm)) {
				expressions.add(cb.lessThan(root.get("createDtm"),
						DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}

			return conjunction;
		};
	}

	/**
	 * 账目明细
	 *
	 * @param serverId
	 * @return
	 */
	public OrderDetailAccounts detailAccounts(Long serverId) {
		Order orderR = new Order();
		orderR.setServerId(serverId);
		List<Order> findAll = findAll(orderR);

		OrderDetailAccounts oda = new OrderDetailAccounts();
		oda.setOrderNum(findAll.size());

		for (Order order : findAll) {
			Double totalPrice = order.getTotalPrice();
			oda.setPriceTol(oda.getPriceTol() + totalPrice);
			if (order.getStatus().equals(OrderEnum.STATUS_APPLY.getCode())) {
				// 申请中项目
				oda.setApplyingPriceTol(oda.getApplyingPriceTol() + totalPrice);
			} else if (order.getStatus().equals(OrderEnum.STATUS_BUILD.getCode())) {
				// 建设中项目
				oda.setBuildingPriceTol(oda.getBuildingPriceTol() + totalPrice);
			} else if (order.getStatus().equals(OrderEnum.STATUS_GRIDCONNECTED_APPLY.getCode())) {
				// 申请并网发电项目
				oda.setGridConnectedingPriceTol(oda.getGridConnectedingPriceTol() + totalPrice);
			} else if (order.getStatus().equals(OrderEnum.STATUS_GRIDCONNECTED.getCode())) {
				// 并网发电中项目
				oda.setGridConnectedPriceTol(oda.getGridConnectedPriceTol() + totalPrice);
			}
			// 优能服务费
			oda.setFactoragePriceTol((oda.getFactoragePriceTol() == null ? 0.0 : oda.getFactoragePriceTol())
					+ (order.getFactoragePrice() == null ? 0.0 : order.getFactoragePrice()));
			// 优能选配项目
			oda.setYnApolegamyPriceTol((oda.getYnApolegamyPriceTol() == null ? 0.0 : oda.getYnApolegamyPriceTol())
					+ (order.getYnApolegamyPrice() == null ? 0.0 : order.getYnApolegamyPrice()));
			// 服务商选配项目
			oda.setServerApolegamyPriceTol(
					(oda.getServerApolegamyPriceTol() == null ? 0.0 : oda.getServerApolegamyPriceTol())
							+ (order.getServerApolegamyPrice() == null ? 0.0 : order.getServerApolegamyPrice()));
			// 已支付金额
			oda.setHadPayPriceTol((oda.getHadPayPriceTol() == null ? 0.0 : oda.getHadPayPriceTol())
					+ (order.getHadPayPrice() == null ? 0.0 : order.getHadPayPrice()));
			// 未支付金额
			oda.setNotPayPriceTol((oda.getNotPayPriceTol() == null ? 0.0 : oda.getNotPayPriceTol())
					+ ((order.getTotalPrice() == null ? 0.0 : order.getTotalPrice())
							- (order.getHadPayPrice() == null ? 0.0 : order.getHadPayPrice())));
		}

		// 营业利润 = 优能服务费 + 优能选配项目金额
		oda.setProfitTol((oda.getFactoragePriceTol()==null?0.0:oda.getFactoragePriceTol()) +( oda.getYnApolegamyPriceTol()==null?0.0: oda.getYnApolegamyPriceTol()));

		return oda;
	}

	/** 根据集合拿到订单的信息 */
	public OrderVo getinfoOrder(Object object) {

		Object[] obj = (Object[]) object;

		Integer orderId = (Integer) obj[0];
		String orderCode = (String) obj[1];
		String tradeNo = (String) obj[2];
		BigDecimal capacity = (BigDecimal) obj[3];
		BigDecimal totalPrice = (BigDecimal) obj[4];
		BigDecimal payPrice = (BigDecimal) obj[5];
		String serverName = (String) obj[6];
		Integer status = (Integer) obj[7];

		Double speed = payPrice.doubleValue() / totalPrice.doubleValue();

		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		speed = Double.parseDouble(decimalFormat.format(speed)) * 100;

		OrderVo orderVo = new OrderVo();
		orderVo.setId(orderId.longValue());
		orderVo.setOrderCode(orderCode);
		if(null != tradeNo){
			orderVo.setTradeNo(tradeNo);
		}else{
			orderVo.setTradeNo("未建电站");
		}

		orderVo.setCapacity(capacity.doubleValue());
		orderVo.setTotalPrice(totalPrice.doubleValue());
		orderVo.setHadPayPrice(payPrice.doubleValue());
		orderVo.setSpeed(speed);
		orderVo.setServerName(serverName);
		orderVo.setStatus(status);
		return orderVo;
	}

	/**pc端订单详情数据*/
	public NewPlanVo getVoNewPlan(Object object) {

		NewPlanVo newPlanVo = new NewPlanVo();

		Object[] obj = (Object[]) object;

		String userName = (String) obj[0];
		String phone = (String) obj[1];
		String addressText = (String) obj[2];
		String serverName = (String) obj[3];
		String orderCode = (String) obj[4];

		String solName = (String) obj[5] + "   " + (String) obj[6];
		String InvestName = (String) obj[7] + "   " + (String) obj[8];

		String jsonText = (String) obj[9];
		BigDecimal capacity = (BigDecimal) obj[10];
		BigDecimal planPrice = (BigDecimal) obj[11];

		String ids = (String) obj[12];
		if (null == ids) {
			ids = "0";
		}

		BigDecimal price = (BigDecimal) obj[13];
		BigDecimal totalprice = (BigDecimal) obj[14];
		Integer warPeriod = (Integer) obj[15];
		Integer status = (Integer) obj[16];

		newPlanVo.setUserName(userName);
		newPlanVo.setPhone(phone);
		newPlanVo.setAddress(addressText);
		newPlanVo.setCompanyName(serverName);
		newPlanVo.setOrderCode(orderCode);
		newPlanVo.setInvstername(InvestName);
		newPlanVo.setBrandname(solName);
		newPlanVo.setMaterialJson(jsonText);
		newPlanVo.setWarPeriod(warPeriod);
		newPlanVo.setNum(capacity.doubleValue());
		newPlanVo.setAllMoney(planPrice.doubleValue());
		newPlanVo.setApoPrice(price.doubleValue());
		newPlanVo.setSerPrice(totalprice.doubleValue());
		newPlanVo.setIds(ids);
		newPlanVo.setStatus(status);

		return newPlanVo;
	}

	/** ios端的数据处理*/
	public NewPlanVo getIOsNewPlan(Object object) {

		NewPlanVo newPlanVo = new NewPlanVo();

		Object[] obj = (Object[]) object;

		String userName = (String) obj[0];
		String phone = (String) obj[1];
		String addressText = (String) obj[2];
		String serverName = (String) obj[3];
		String orderCode = (String) obj[4];

		String solName = (String) obj[5] + "   " + (String) obj[6];
		String InvestName = (String) obj[7] + "   " + (String) obj[8];

		String jsonText = (String) obj[9];
		BigDecimal capacity = (BigDecimal) obj[10];
		BigDecimal planPrice = (BigDecimal) obj[11];
		String ids = (String) obj[12];

		BigDecimal price = (BigDecimal) obj[13];
		BigDecimal totalprice = (BigDecimal) obj[14];
		Integer warPeriod = (Integer) obj[15];
		Integer status = (Integer) obj[16];

		String ipoMemo = (String) obj[17];

		Integer loanStatus = (Integer) obj[18];

		Date createDtm = (Date) obj[19];

		BigDecimal hadPayPrice = (BigDecimal) obj[20];

		DecimalFormat df = new DecimalFormat("#0.00");
		String speed = df.format(hadPayPrice.doubleValue() / totalprice.doubleValue() * 100);

		newPlanVo.setUserName(userName);
		newPlanVo.setPhone(phone);
		newPlanVo.setAddress(addressText);
		newPlanVo.setCompanyName(serverName);
		newPlanVo.setOrderCode(orderCode);
		newPlanVo.setInvstername(InvestName);
		newPlanVo.setCapacity(capacity.doubleValue());
		newPlanVo.setBrandname(solName);
		newPlanVo.setMaterialJson(jsonText);
		newPlanVo.setWarPeriod(warPeriod);
		newPlanVo.setNum(capacity.doubleValue());
		newPlanVo.setAllMoney(planPrice.doubleValue());
		newPlanVo.setApoPrice(price.doubleValue());
		newPlanVo.setSerPrice(totalprice.doubleValue());
		newPlanVo.setIds(ids);
		newPlanVo.setStatus(status);

		if (null == ipoMemo) {
			newPlanVo.setIpoMemo("暂无");
		} else {
			newPlanVo.setIpoMemo(ipoMemo);
		}

		newPlanVo.setLoanStatus(loanStatus);
		newPlanVo.setCreateDtm(createDtm);
		newPlanVo.setSpeed(speed);

		return newPlanVo;
	}

	/** */

	/** 根据订单记录号修改状态 */

	public void UpdateOrStatus(String tradeNo, Double money) {

		Order order = FindByTradeNo(tradeNo);

		logger.info("----- ------ ------修改的金额为" + money);
		order.setHadPayPrice(order.getHadPayPrice() + money);
		logger.info("----- ------ ------保存的的金额为" + (order.getHadPayPrice() + money));

		Double num = order.getHadPayPrice() / order.getTotalPrice();

		Integer status = order.getStatus();

		if (0 < num && num < 0.3) {
			status = 9;
		} else if (num >= 0.3 && num < 0.6) {
			status = 0;
		} else if (0.6 <= num && num < 1) {
			status = 1;
		} else if (num == 1) {
			status = 2;
		}
		logger.info("----- ------ ------status为" + status);
		order.setStatus(status);

		mapper.UpdateOrder(order);

		logger.info("----- ----- ---- ------ ----- --修改订单状态成功！");

	}

	/**
	 * 获取施工进度的状态
	 * 
	 * @param o
	 * @return
	 */
	public Object getConstructionStatus(Order o) {
		return findOne(o.getId()).getConstructionStatus();
	}

	private static String NOTBEGIN = "notBegin";// 未开始
	private static String MATERIALAPPROAC = "材料进场";// 材料进场
	private static String FOUNDATIONBUILDING = "基础建筑";// 基础建筑
	private static String SUPPORTINSTALLATION = "支架安装";// 支架安装
	private static String PHOTOVOLTAICPANELINSTALLATION = "光伏板安装";// 光伏板安装
	private static String DCCONNECTION = "直流接线";// 直流接线
	private static String ELECTRICBOXINVERTER = "电箱逆变器";// 电箱逆变器
	private static String BUSBOXINSTALLATION = "汇流箱安装";// 汇流箱安装
	private static String ACLINE = "交流辅线";// 交流辅线
	private static String LIGHTNINGPROTECTIONGROUNDINGTEST = "防雷接地测试";// 防雷接地测试
	private static String GRIDCONNECTEDACCEPTANCE = "并网验收";// 并网验收
	private static String SUCCESS = "success";// 并网验收成功！走完最后一步
	private static String SERVER = "server";// 服务商
	private static String ORDERCODE = "orderCode";// 订单号

	/**
	 * 修改施工状态的进度 如果现在是<材料进场>，就选择为<材料进场>,<材料进场>已经完成的话，那么就是进入到<基础建筑>,此时应该点击<基础建筑>
	 * 即进入到哪一步，就出发那个的事件。
	 * 
	 * @param o
	 * @return
	 */
	public boolean updateConstructionStatus(Order o, Integer thisStauts, ResVo resVo) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(date);
		Map<String, Object> bigMap = new HashMap<String, Object>();
		Order order = findOne(o.getId());
		List<Object> list1 = new LinkedList<Object>();
		List<Object> list = new LinkedList<Object>();
		SVo2 s = new SVo2();
		ResVo rv = new ResVo();
		s.setServerImg(order.getServer().getCompanyLogo());
		s.setServerName(order.getServerName());
		s.setOrderCode(order.getOrderCode());
		list.add(s);
		bigMap.put("Server", list);
		if (order.getConstructionStatus() == null || order.getConstructionStatus().length() < 100) {
			System.out.println("没有数据，先填充。");
			ResVo rv0 = new ResVo();
			rv0.setContent("当前正在执行");
			rv0.setTarget("materialapproac");
			rv0.setTitle(MATERIALAPPROAC);
			rv0.setIsNow(true);
			list1.add(rv0);

			ResVo rv1 = new ResVo(true);
			rv1.setContent("");
			rv1.setTarget("foundationbuilding");
			rv1.setTitle(FOUNDATIONBUILDING);
			list1.add(rv1);

			ResVo rv2 = new ResVo();
			rv2.setContent("");
			rv2.setTarget("supportinstallation");
			rv2.setTitle(SUPPORTINSTALLATION);
			list1.add(rv2);

			ResVo rv3 = new ResVo();
			rv3.setContent("");
			rv3.setTarget("photovoltaicpanelinstallation");
			rv3.setTitle(PHOTOVOLTAICPANELINSTALLATION);
			list1.add(rv3);

			ResVo rv4 = new ResVo();
			rv4.setContent("");
			rv4.setTarget("dcconnection");
			rv4.setTitle(DCCONNECTION);
			list1.add(rv4);

			ResVo rv5 = new ResVo();
			rv5.setContent("");
			rv5.setTarget("electricboxinverter");
			rv5.setTitle(ELECTRICBOXINVERTER);
			list1.add(rv5);

			ResVo rv6 = new ResVo();
			rv6.setContent("");
			rv6.setTarget("busboxinstallation");
			rv6.setTitle(BUSBOXINSTALLATION);
			list1.add(rv6);

			ResVo rv7 = new ResVo();
			rv7.setContent("");
			rv7.setTarget("acline");
			rv7.setTitle(ACLINE);
			list1.add(rv7);

			ResVo rv8 = new ResVo();
			rv8.setContent("");
			rv8.setTarget("lightningprotectiongroundingtest");
			rv8.setTitle(LIGHTNINGPROTECTIONGROUNDINGTEST);
			list1.add(rv8);

			ResVo rv9 = new ResVo();
			rv9.setContent("");
			rv9.setTarget("gridconnectedacceptance");
			rv9.setTitle(GRIDCONNECTEDACCEPTANCE);
			list1.add(rv9);

			bigMap.put("ResVo", list1);
			String obj2Json = JsonUtil.obj2Json(bigMap);
			order.setConstructionStatus(obj2Json);
			// update
			order.setBuildStepB(0);
			int stepB = mapper.updateBuildStepB(order);
			int status = mapper.updateConstructionStatus(order);
			System.out.println("stepB::" + stepB + "\tstatus" + status);
			System.out.println(obj2Json);
			return stepB > 0 && status > 0 ? true : false;
		}

		Map<String, Object> json2Obj = (Map<String, Object>) JsonUtil.json2Obj(order.getConstructionStatus());
		List<Object> l = new LinkedList<Object>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		Map<String, Object> m = new HashMap<String, Object>();
		List<Object> obl = (List<Object>) json2Obj.get("ResVo");
		int flag = -999;
		// String title = "";
		// String content = "";
		// String target = "";
		Map<String, Object> m2 = new HashMap<String, Object>();
		// int index = -999;
		for (int i = 0; i < obl.size(); i++) {
			m = (Map<String, Object>) obl.get(i);
			System.err.println(m.get("target"));
			rv = new ResVo();
			if (m.get("target").equals(resVo.getTarget())) {
				rv.setContent(format + "-已完成-" + resVo.getTitle());
				rv.setTarget(m.get("target").toString());
				rv.setTitle(m.get("title").toString());
				rv.setIsNow(false);
				flag = i;// 记录当前下标。方便修改下一条记录的content
				System.out.println("当前的flag是：" + (flag + 1));
			} else {
				rv.setContent(m.get("content") + "");
				rv.setTarget(m.get("target") + "");
				rv.setTitle(m.get("title") + "");
			}
			if (!m.get("target").equals("gridconnectedacceptance")) {
				if ((flag + 1) == i) {
					String title = m.get("title").toString();
					rv.setContent("当前正在执行");
					rv.setTarget(m.get("target").toString());
					rv.setTitle(title);
					rv.setIsNow(true);
					System.out.println(666666);
					flag = -999;
				}
			}
			l.add(rv);
		}

		order.setBuildStepB(thisStauts);
		m1.put("Server", list);
		m1.put("ResVo", l);
		String obj2Json3 = JsonUtil.obj2Json(m1);
		order.setConstructionStatus(obj2Json3);
		int stepB = mapper.updateBuildStepB(order);
		int status = mapper.updateConstructionStatus(order);
		// System.out.println("stepB::" + stepB + " \t status::" + status);
		// System.out.println(obj2Json3.length());
		// System.out.println(obj2Json3);
		return stepB > 0 && status > 0 ? true : false;

	}

	/**
	 * 修改贷款的状态
	 * 
	 * @param o
	 * @param flag
	 *            true：设置成贷款成功、false：设置为贷款失败.
	 * @return
	 */
	public boolean updateLoanStatus(Order o, boolean flag) {
		Order order = findOne(o.getId());
		if (flag) {
			order.setLoanStatus(2);// cheng gong ..
			Double totalPrice = order.getTotalPrice();
			order.setHadPayPrice(totalPrice);// 贷款成功就让订单需支付的钱=着条订单的总价-->相等于用户支付满钱了。
		} else {
			order.setHadPayPrice(null);// 不修改金额
			order.setLoanStatus(3);// shi bai ..
		}
		int status = mapper.updateLoanStatus(order);
		return status > 0 ? true : false;
	}

	public boolean updateApplyStepBImgUrl(Order order) {
		return mapper.updateApplyStepBImgUrl(order) > 0 ? true : false;
	}

	public Map<String, Object> checkSurv(Order o, Integer isOk) {
		Order o1 = findOne(o.getId());
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		if (o1.getLoanStatus() == 2) {// 看看是不是貸款成功的。
			jsonResult.put("isOk", true);
			o1.setApplyStepA(1);
			int condition = mapper.updateByCondition(o1);
			if (condition > 0) {
				jsonResult.put("isOk", true);
				jsonResult.put("loanStatus", true);
				jsonResult.put("applyIsPay", false);
			} else {
				jsonResult.put("isOk", false);
				jsonResult.put("loanStatus", true);
				jsonResult.put("applyIsPay", false);
				jsonResult.put("reason", "系统错误，请联系管理员。");
			}
			return jsonResult;
		} else {
			jsonResult.put("loanStatus", false);
		}
//		if (o1.getApplyIsPay() != 1) {
//			jsonResult.put("isOk", false);
//			jsonResult.put("applyIsPay", false);
//			jsonResult.put("reason", "当前订单状态未支付，不能进行申请预约");
//			return jsonResult;
//		} else {
//			jsonResult.put("applyIsPay", true);// 支付成功。
//		}
		if (isOk == 1) {
			if (o1.getApplyStepA() == 1) {
				jsonResult.put("isOk", false);
				jsonResult.put("reason", "已申请预约，不能重复进行");
				return jsonResult;
			} else if (o1.getApplyStepA() == 2) {
				jsonResult.put("isOk", false);
				jsonResult.put("reason", "已勘察完成，不能重复进行");
				return jsonResult;
			}
			o1.setApplyStepA(1);
			int condition = mapper.updateByCondition(o1);
			if (condition > 0) {
				jsonResult.put("isOk", true);
			} else {
				jsonResult.put("isOk", false);
				jsonResult.put("reason", "系统错误，请联系管理员。");
			}
			return jsonResult;
		}
		jsonResult.put("isOk", false);
		jsonResult.put("reason", "当前订单状态不能进行预约申请");
		return jsonResult;
	}

	public Map<String, Object> checkGrid(Order o, Integer isOk) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		Order o1 = findOne(o.getId());
		if (o1.getLoanStatus() == 2) {// 看看是不是貸款成功的。再看有没有上传图片 。
			if (o1.getApplyStepbimgUrl() == null || o1.getApplyStepbimgUrl().length() < 1) {
				jsonResult.put("reason", "请先上传报建时所需要的材料。");
				jsonResult.put("loanStatus", true);
				jsonResult.put("isOk", false);
				jsonResult.put("applyStepBImgUrl", false);
				jsonResult.put("applyIsPay", false);
			} else {
				jsonResult.put("applyStepBImgUrl", true);
				o1.setApplyStepB(1);
				int condition = mapper.updateByCondition(o1);
				if (condition > 0) {
					jsonResult.put("loanStatus", true);
					jsonResult.put("isOk", true);
					jsonResult.put("applyIsPay", false);
				} else {
					jsonResult.put("loanStatus", true);
					jsonResult.put("isOk", false);
					jsonResult.put("applyIsPay", false);
					jsonResult.put("reason", "系统错误，请联系管理员。");
				}
			}
			return jsonResult;
		} else {
			jsonResult.put("loanStatus", false);
		}
//		if (o1.getApplyIsPay() != 1) {
//			jsonResult.put("applyIsPay", false);
//			jsonResult.put("reason", "当前订单未支付,请先支付。");
//			jsonResult.put("isOk", false);
//			if (o1.getApplyStepBImgUrl() == null || o1.getApplyStepBImgUrl().length() < 1) {
//				jsonResult.put("applyStepBImgUrl", false);
//			}else{
//				jsonResult.put("applyStepBImgUrl", true);
//			}
//			return jsonResult;
//		} else {
//			jsonResult.put("applyIsPay", true);// 支付成功。
//		}
		if (isOk == 1) {
			if (o1.getApplyStepbimgUrl() == null || o1.getApplyStepbimgUrl().length() < 1) {
				jsonResult.put("reason", "请先上传报建时所需要的材料。");
				jsonResult.put("applyStepBImgUrl", false);
				jsonResult.put("isOk", false);
			} else {
				jsonResult.put("applyStepBImgUrl", true);
				if (o1.getApplyStepB() == 1) {
					jsonResult.put("reason", "已申请报建或者正在进行，不能重复申请");
					jsonResult.put("isOk", false);
					return jsonResult;
				} else if (o1.getApplyStepB() == 2) {
					jsonResult.put("reason", "申请已完成，不能重复申请");
					jsonResult.put("isOk", false);
					return jsonResult;
				}
				o1.setApplyStepB(1);
				int condition = mapper.updateByCondition(o1);
				if (condition > 0) {
					jsonResult.put("isOk", true);
				} else {
					jsonResult.put("reason", "系统错误，请联系管理员。");
					jsonResult.put("isOk", false);
				}
			}
			return jsonResult;
		}
		jsonResult.put("reason", "当前订单状态不能进行申请施工。");
		jsonResult.put("isOk", false);
		return jsonResult;
	}

	public Map<String, Object> checkApply(Order o, Integer isOk) {
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		Order o1 = findOne(o.getId());
		if (o1.getLoanStatus() == 2) {// 看看是不是貸款成功的。
			o1.setApplyStepB(1);
			int condition = mapper.updateByCondition(o1);
			if (condition > 0) {
				jsonResult.put("isOk", true);
				jsonResult.put("loanStatus", true);
				jsonResult.put("buildIsPay", false);
			} else {
				jsonResult.put("loanStatus", true);
				jsonResult.put("buildIsPay", false);
				jsonResult.put("isOk", false);
				jsonResult.put("reason", "系统错误，请联系管理员。");
			}
			return jsonResult;
		} else {
			jsonResult.put("loanStatus", false);
		}
		if (o1.getBuildIsPay() != 1 && o1.getStatus() != 2) {
			jsonResult.put("isOk", false);
			jsonResult.put("buildIsPay", false);
			jsonResult.put("reason", "当前订单状态不能进行申请施工（未支付施工费用）。");
			return jsonResult;
		} else {
			jsonResult.put("buildIsPay", true);
		}
		if (isOk == 1) {
			if (o1.getBuildStepA() == 1) {
				jsonResult.put("reason", "已申请施工，不能重复申请");
				jsonResult.put("isOk", false);
				return jsonResult;
			}
			o1.setBuildStepA(1);
			int condition = mapper.updateByCondition(o1);
			if (condition > 0) {
				jsonResult.put("isOk", true);
			} else {
				jsonResult.put("reason", "系统错误，请联系管理员。");
				jsonResult.put("isOk", false);
			}
			return jsonResult;
		}
		jsonResult.put("reason", "当前订单状态不能进行申请施工");
		jsonResult.put("isOk", false);
		return null;
	}

	public boolean updateOrderStauts43Step(Order order) {
		int step = mapper.updateOrderStauts43Step(order);
		return step > 0 ? true : false;
	}
}