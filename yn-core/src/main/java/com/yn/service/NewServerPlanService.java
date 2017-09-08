package com.yn.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.NewServerPlanDao;
import com.yn.dao.ServerDao;
import com.yn.model.NewServerPlan;
import com.yn.model.News;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Server;
import com.yn.model.ServerPlan;
import com.yn.model.User;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.NewServerPlanVo;
import com.yn.vo.ServerPlanVo;
@Service
public class NewServerPlanService {
	
	@Autowired
	NewServerPlanDao planDao;
	
	@Autowired
	ServerDao serverDao;
	
	static DecimalFormat df = new DecimalFormat("0.00");
	static DecimalFormat df1 = new DecimalFormat("0000");
	static Random rd = new Random();
	SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
	
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
	
	 public void save(NewServerPlan news) {
	        if(news.getId()!=null){
	        	NewServerPlan one = planDao.findOne(news.getId());
	            try {
	                BeanCopy.beanCopy(news,one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            planDao.save(one);
	        }else {
	        	planDao.save(news);
	        }
	    }
	
	 public NewServerPlan findOne(Long id) {
	        return planDao.findOne(id);
	    }
	
	 public NewServerPlan findOne(NewServerPlan serverPlan) {
	        Specification<NewServerPlan> spec =RepositoryUtil.getSpecification(serverPlan);
	        NewServerPlan findOne = planDao.findOne(spec);
	        return findOne;
	    }

	    public List<NewServerPlan> findAll(List<Long> list) {
	        return planDao.findAll(list);
	    }

	    public Page<NewServerPlan> findAll(NewServerPlan serverPlan, Pageable pageable) {
	        Specification<NewServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
	        Page<NewServerPlan> findAll = planDao.findAll(spec, pageable);
	        return findAll;
	    }

	    public List<NewServerPlan> findAll(NewServerPlan serverPlan) {
	        Specification<NewServerPlan> spec = RepositoryUtil.getSpecification(serverPlan);
	        return planDao.findAll(spec);
	    }
	
	    public NewServerPlan selectOne(NewServerPlanVo newserverPlan) {
	    	
	    	return	planDao.selectOne(newserverPlan);
	    }
	    
	    /** 根据用户和服务商生成一个订单 */
	    
	    public Order getOrder(NewServerPlan newserverPlan ,User user,Double price,Double apoPrice){
	    	
	    	Order order = new Order();
	    	 /** 保存订单*/
	    	String orderCode = toSerialCode(newserverPlan.getId(), 4) + format.format(System.currentTimeMillis()) + df1.format(rd.nextInt(9999));
	      //  user.setPassword(null);
	        user.getAddressText();
	        
	         /** 找到服务费率*/
	        Long serverid = newserverPlan.getServerId();
	        Server server = serverDao.findServer(serverid);
	        Double factorage =   server.getFactorage();
	         
	        /** 订单id*/
	        Long order_planid = newserverPlan.getId();
	        
	        order.setProvinceId(user.getProvinceId());
	        order.setProvinceText(user.getProvinceText());
	        order.setServerName(server.getCompanyName());
	        /**  转移数据*/
	        order.setAddressText(user.getAddressText());
	        order.setCityId(user.getCityId());
	        order.setCityText(user.getCityText());
	        order.setLinkMan(user.getNickName());
	        
	        order.setLinkPhone(user.getPhone());
	        order.setPlanPrice(price);
	        order.setServerName(server.getCompanyName());
	        order.setOrderCode(orderCode);
	        order.setUserId(user.getId());
	        order.setServerId(newserverPlan.getServerId());
	        /** 优惠码*/
	        order.setPrivilegeCode(null);
	        /** 优能的选配项目价格*/
	        order.setYnApolegamyPrice(apoPrice);
	        /** 服务商选配项目价格*/
	        order.setServerApolegamyPrice(apoPrice);
	        /** 总价格*/
	        order.setTotalPrice(price);
	         /** 已付金额*/
	        order.setHadPayPrice(0.0);
	         /** 服务费*/
	        order.setFactoragePrice(price * factorage );
	         /** 装机容量*/
	        order.setCapacity(Double.valueOf(newserverPlan.getMinPurchase().toString()));
	        /** 状态*/
	        order.setStatus(0);
	        order.setGridConnectedIsPay(0);
	        order.setGridConnectedStepA(0);
	        order.setApplyIsPay(0);
	        order.setApplyStepA(0);
	        order.setApplyStepB(0);
	        order.setLoanStatus(0);
	        
	        Date date = new Date();
	        order.setCreateDtm(date);
	        
	        order.setUser(user);
	        
			return order;
	    }
	
	   /** 生成订单计划表*/ 
	
	    public OrderPlan giveOrderPlan(NewServerPlan  serverPlan,Order order){
	    	  OrderPlan orderPlan = new OrderPlan();
	    	/** [电池板id]*/
	    	  orderPlan.setBatteryBoardId(serverPlan.getBatteryboardId());
	    	  /** [电池板品牌]*/
	    	  orderPlan.setBatteryBoardName(serverPlan.getSolarPanel().getBrandName());
	    	  /** [电池板型号]*/
	    	  orderPlan.setBatteryBoardModel(serverPlan.getSolarPanel().getModel()); 
	    	  /** [电池板质保期 年]*/
	    	  orderPlan.setBatteryBoardShelfLife(serverPlan.getSolarPanel().getQualityAssurance());
	    	   /** [电池板质保修 年]*/
	    	  orderPlan.setBatteryBoardWarrantyYear(serverPlan.getSolarPanel().getBoardYear());
	    	  
	    	  /** 逆变器id*/
	    	  orderPlan.setInverterId(serverPlan.getInverterId());
	    	  /** [逆变器品牌]*/
	    	  orderPlan.setInverterBrand(serverPlan.getInverter().getBrandName());
	    	  /** [逆变器型号]*/
	    	  orderPlan.setInverterModel(serverPlan.getInverter().getModel());
	    	  /** 保期 年 */
	    	  orderPlan.setInverterShelfLife(serverPlan.getInverter().getQualityAssurance());
	    	   /** 保修 年*/
	    	  orderPlan.setInverterWarrantyYear(serverPlan.getInverter().getBoardYear());
	    	  
	    	  orderPlan.setOtherMaterialJsonText(serverPlan.getMaterialJson());
	    	  
	    	  orderPlan.setUnitPrice(serverPlan.getUnitPrice());
	    	  orderPlan.setDel(0);
	    	  
	    	  orderPlan.setOrderId(order.getId());
	    	  
	    	  Date date = new Date();
	    	  orderPlan.setCreateDtm(date);
	    	  
	    	  

			return orderPlan;
	    }
	    
	    
	  public  List<Object> selectServerPlan(Long serverId){
		  
		return planDao.selectServerPlan(serverId);	
	    }

}
