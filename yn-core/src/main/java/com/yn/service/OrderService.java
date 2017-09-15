package com.yn.service;

import com.yn.dao.OrderDao;
import com.yn.dao.mapper.OrderMapper;
import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.enums.OrderEnum;
import com.yn.model.Order;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.vo.NewPlanVo;
import com.yn.vo.OrderVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class OrderService {

	
    @Autowired
    protected OrderDao orderDao;
    @Autowired
    private NoticeService noticeService;
    @Resource
	private OrderMapper mapper;
    @Autowired
    ApolegamyOrderService  apoleService;

    public Order findOne(Long id) {
        return orderDao.findOne(id);
    }
    
    public  Object getInformOrder(Long orderId){
    	
		return orderDao.getInfoOrder(orderId);	
    }
    
    
    public boolean checkUpdateOrderStatus(Order order){
		return mapper.updateByCondition(order)>0?true:false;
	}
    
    public Order findstatus(Long orderId){
    	
		return mapper.findstatus(orderId);
    }
    
   public Object findOrder(Long orderId){
	   
	return orderDao.findOrder(orderId);  
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Specification<Order> getSpecification(Order order) {
        order.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(order);
        return (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
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
                expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
            }
            if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
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
    public OrderVo getinfoOrder(Object object){
    	
    	Object[] obj = (Object[])object;
    	
    	Integer orderId = (Integer) obj[0];
    	String orderCode =(String)obj[1];
    	String tradeNo =(String)obj[2];
    	BigDecimal capacity = (BigDecimal) obj[3];
    	BigDecimal totalPrice = (BigDecimal) obj[4];
    	BigDecimal payPrice = (BigDecimal) obj[5];
    	String serverName =(String)obj[6];
    
    	Double speed = payPrice.doubleValue()/totalPrice.doubleValue();
    	
    	DecimalFormat decimalFormat = new DecimalFormat("0.0000");
    	speed = Double.parseDouble(decimalFormat.format(speed))*100;
    	
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
    
    public NewPlanVo getVoNewPlan(Object object){
    	
    	NewPlanVo newPlanVo = new NewPlanVo();
    	
    	Object[] obj =(Object[])object;
    	
    	String userName  =(String) obj[0];
    	String phone  =(String) obj[1];
    	String addressText  =(String) obj[2];
    	String serverName  =(String) obj[3];
    	String orderCode  =(String) obj[4];
    	
    	String solName = (String) obj[5]+"   "+(String)obj[6];
    	String InvestName = (String) obj[7]+"   "+(String)obj[8];
    	
    	String jsonText =(String)obj[9];
    	BigDecimal capacity = (BigDecimal) obj[10];
    	BigDecimal planPrice = (BigDecimal) obj[11];
    	String ids  =(String) obj[12];
    	
    	
    	BigDecimal price = (BigDecimal) obj[13];
    	BigDecimal totalprice = (BigDecimal) obj[14];
    	Integer warPeriod =(Integer)obj[15];
    	Integer status =(Integer)obj[16];
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
    

}
