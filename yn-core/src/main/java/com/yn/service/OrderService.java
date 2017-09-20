package com.yn.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.OrderDao;
import com.yn.dao.mapper.OrderMapper;
import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.enums.OrderEnum;
import com.yn.model.Order;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.JsonUtil;
import com.yn.utils.ObjToMap;
import com.yn.vo.NewPlanVo;
import com.yn.vo.OrderVo;

@Service
public class OrderService {

	@Autowired
	protected OrderDao orderDao;
	@Autowired
	private NoticeService noticeService;
	@Resource
	private OrderMapper mapper;
	@Autowired
	ApolegamyOrderService apoleService;

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
			oda.setFactoragePriceTol(oda.getFactoragePriceTol() + order.getFactoragePrice());
			// 优能选配项目
			oda.setYnApolegamyPriceTol(oda.getYnApolegamyPriceTol() + order.getYnApolegamyPrice());
			// 服务商选配项目
			oda.setServerApolegamyPriceTol(oda.getServerApolegamyPriceTol() + order.getServerApolegamyPrice());
			// 已支付金额
			oda.setHadPayPriceTol(oda.getHadPayPriceTol() + order.getHadPayPrice());
			// 未支付金额
			oda.setNotPayPriceTol(oda.getNotPayPriceTol() + (order.getTotalPrice() - order.getHadPayPrice()));
		}

		// 营业利润 = 优能服务费 + 优能选配项目金额
		oda.setProfitTol(oda.getFactoragePriceTol() + oda.getYnApolegamyPriceTol());

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

		Double speed = payPrice.doubleValue() / totalPrice.doubleValue();

		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		speed = Double.parseDouble(decimalFormat.format(speed)) * 100;

		OrderVo orderVo = new OrderVo();
		orderVo.setId(orderId.longValue());
		orderVo.setOrderCode(orderCode);
		orderVo.setTradeNo(tradeNo);
		orderVo.setCapacity(capacity.doubleValue());
		orderVo.setTotalPrice(totalPrice.doubleValue());
		orderVo.setHadPayPrice(payPrice.doubleValue());
		orderVo.setSpeed(speed);
		orderVo.setServerName(serverName);

		return orderVo;
	}

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
		newPlanVo.setNum(capacity.intValue());
		newPlanVo.setAllMoney(planPrice.doubleValue());
		newPlanVo.setApoPrice(price.doubleValue());
		newPlanVo.setSerPrice(totalprice.doubleValue());
		newPlanVo.setIds(ids);
		newPlanVo.setStatus(status);
		return newPlanVo;
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

	private String NOTBEGIN = "notBegin";// 未开始
	private String MATERIALAPPROAC = "materialApproac";// 材料进场
	private String FOUNDATIONBUILDING = "foundationBuilding";// 基础建筑
	private String SUPPORTINSTALLATION = "supportInstallation";// 支架安装
	private String PHOTOVOLTAICPANELINSTALLATION = "photovoltaicPanelInstallation";// 光伏板安装
	private String DCCONNECTION = "DCConnection";// 直流接线
	private String ELECTRICBOXINVERTER = "electricBoxInverter";// 电箱逆变器
	private String BUSBOXINSTALLATION = "busBoxInstallation";// 汇流箱安装
	private String ACLINE = "ACLine";// 交流辅线
	private String LIGHTNINGPROTECTIONGROUNDINGTEST = "lightningProtectionGroundingTest";// 防雷接地测试
	private String GRIDCONNECTEDACCEPTANCE = "gridConnectedAcceptance";// 并网验收
	private String SUCCESS = "success";// 并网验收成功！走完最后一步
	private String SERVER = "server";// 服务商
	private String ORDERCODE = "orderCode";// 订单号

	/**
	 * 修改施工状态的进度 如果现在是<材料进场>，就选择为<材料进场>,<材料进场>已经完成的话，那么就是进入到<基础建筑>,此时应该点击<基础建筑>
	 * 即进入到哪一步，就出发那个的事件。
	 * 
	 * @param o
	 * @return
	 */
	public boolean updateConstructionStatus(Order o, String target) {
		Map<String, Object> map = new HashMap<String, Object>();
		Order order = findOne(o.getId());
		String constructionStatus = order.getConstructionStatus();
		if (constructionStatus == null || constructionStatus.length() < 279) {// 针对没有这个状态的订单的操作
			Map<String, Object> csMap = new HashMap<String, Object>();
			csMap.put(NOTBEGIN, "未开始");
			csMap.put(MATERIALAPPROAC, " ");
			csMap.put(FOUNDATIONBUILDING, " ");
			csMap.put(SUPPORTINSTALLATION, " ");
			csMap.put(PHOTOVOLTAICPANELINSTALLATION, " ");
			csMap.put(DCCONNECTION, " ");
			csMap.put(ELECTRICBOXINVERTER, " ");
			csMap.put(BUSBOXINSTALLATION, " ");
			csMap.put(ACLINE, " ");
			csMap.put(LIGHTNINGPROTECTIONGROUNDINGTEST, " ");
			csMap.put(GRIDCONNECTEDACCEPTANCE, " ");
			csMap.put(SUCCESS, " ");
			csMap.put(SERVER, order.getServer().getCompanyName());
			csMap.put(ORDERCODE, order.getOrderCode());
			constructionStatus = JsonUtil.obj2Json(csMap);
			System.out.println(constructionStatus);
			System.out.println("constructionStatus.length::" + constructionStatus.length());
			order.setConstructionStatus(constructionStatus);
			int status = mapper.updateConstructionStatus(order);
			return status > 0 ? true : false;
		}
		//
		if (constructionStatus != null && constructionStatus.length() > 278) {// 不是上面那个空的东西
			System.out.println("开始修改状态！");
			map = JsonUtil.parseJSON2Map(constructionStatus);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String format = sdf.format(date);
			// 0:未开始,1:材料进场,2:基础建筑,3:支架安装,4:光伏板安装,5:直流接线,6:电箱逆变器,7:汇流箱安装,8:交流辅线,9:防雷接地测试,10:并网验收
			for (String key : map.keySet()) {
				System.out.println("target::" + target + "\tkey::" + key);
				if (key.equals(target)) {
					// Object value = map.get(key);
					if (target.equals(MATERIALAPPROAC)) {// 材料进场
						// 获取支付、施工状态
						if (order.getBuildIsPay() != 1 || order.getBuildStepA() != 1) {
							return false;
						}
						order.setBuildStepB(0);
						map.replace(NOTBEGIN, "正在施工");
						map.replace(MATERIALAPPROAC, "当前正在进行");
						break;
					} else if (target.equals(FOUNDATIONBUILDING)) {// 基础建筑
						System.out.println(map.get(MATERIALAPPROAC));
						if (!map.get(MATERIALAPPROAC).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(1);
						map.replace(MATERIALAPPROAC, format + "已完成材料进场");
						map.replace(FOUNDATIONBUILDING, "当前正在进行");
						break;
					} else if (target.equals(SUPPORTINSTALLATION)) {// 支架安装
						System.out.println(map.get(FOUNDATIONBUILDING));
						if (!map.get(FOUNDATIONBUILDING) .equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(2);
						map.replace(FOUNDATIONBUILDING, format + "已完成基础建筑");
						map.replace(SUPPORTINSTALLATION, "当前正在进行");
						break;
					} else if (target.equals(PHOTOVOLTAICPANELINSTALLATION)) {// 光伏板安装
						System.out.println(map.get(SUPPORTINSTALLATION));
						if (!map.get(SUPPORTINSTALLATION).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(3);
						map.replace(SUPPORTINSTALLATION, format + "已完成支架安装");
						map.replace(PHOTOVOLTAICPANELINSTALLATION, "当前正在进行");
						break;
					} else if (target.equals(DCCONNECTION)) {// 直流接线
						System.out.println(map.get(PHOTOVOLTAICPANELINSTALLATION));
						if (!map.get(PHOTOVOLTAICPANELINSTALLATION).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(4);
						map.replace(PHOTOVOLTAICPANELINSTALLATION, format + "已完成光伏板安装");
						map.replace(DCCONNECTION, "当前正在进行");
					} else if (target.equals(ELECTRICBOXINVERTER)) {// 电箱逆变器
						System.out.println(map.get(PHOTOVOLTAICPANELINSTALLATION));
						if (!map.get(PHOTOVOLTAICPANELINSTALLATION).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(5);
						map.replace(DCCONNECTION, format + "已完成直流接线");
						map.replace(ELECTRICBOXINVERTER, "当前正在进行");
						break;
					} else if (target.equals(BUSBOXINSTALLATION)) {// 汇流箱安装
						System.out.println(map.get(ELECTRICBOXINVERTER));
						if (!map.get(ELECTRICBOXINVERTER).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(6);
						map.replace(ELECTRICBOXINVERTER, format + "已完成电箱逆变器");
						map.replace(BUSBOXINSTALLATION, "当前正在进行");
					} else if (target.equals(ACLINE)) {// 交流辅线
						System.out.println(map.get(BUSBOXINSTALLATION));
						if (!map.get(BUSBOXINSTALLATION).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(7);
						map.replace(BUSBOXINSTALLATION, format + "已完成汇流箱安装");
						map.replace(ACLINE, "当前正在进行");
						break;
					} else if (target.equals(LIGHTNINGPROTECTIONGROUNDINGTEST)) {// 防雷接地测试
						System.out.println(map.get(ACLINE));
						if (!map.get(ACLINE).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(8);
						map.replace(ACLINE, format + "已完成交流辅线");
						map.replace(LIGHTNINGPROTECTIONGROUNDINGTEST, "当前正在进行");
						break;
					} else if (target.equals(GRIDCONNECTEDACCEPTANCE)) {// 并网验收
						System.out.println(map.get(LIGHTNINGPROTECTIONGROUNDINGTEST));
						if (!map.get(LIGHTNINGPROTECTIONGROUNDINGTEST).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(9);
						map.replace(LIGHTNINGPROTECTIONGROUNDINGTEST, format + "已完成防雷接地测试");
						map.replace(GRIDCONNECTEDACCEPTANCE, "当前正在进行");
					} else if (target.equals(SUCCESS)) {
						System.out.println(map.get(GRIDCONNECTEDACCEPTANCE));
						if (!map.get(GRIDCONNECTEDACCEPTANCE).equals("当前正在进行")) {
							return false;
						}
						order.setBuildStepB(10);
						map.replace(GRIDCONNECTEDACCEPTANCE, format + "已完成并网验收");
						break;
					}
				}
			}
		}
		constructionStatus = JsonUtil.obj2Json(map);
		System.out.println(constructionStatus);
		order.setConstructionStatus(constructionStatus);
		// 更新操作。。。。。
		int status = mapper.updateConstructionStatus(order);
		int b = mapper.updateBuildStepB(order);
		System.out.println("修改状态结束！" + status + "\t" + b);
		return status > 0 && b > 0 ? true : false;
	}

}
