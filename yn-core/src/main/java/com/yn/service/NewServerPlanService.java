package com.yn.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yn.model.QualificationsServer;
import com.yn.model.Server;
import com.yn.model.ServerPlan;
import com.yn.model.User;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.ApolegamyVo;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewServerPlanVo;
import com.yn.vo.NewUserVo;
import com.yn.vo.QualificationsVo;
import com.yn.vo.ServerPlanVo;

@Service
public class NewServerPlanService {
	private static final Logger logger = LoggerFactory.getLogger(NewServerPlanService.class);

	@Autowired
    QualificationsServerService qualificationsServerService;
    @Autowired
    ApolegamyService apolegamyService;
    @Autowired
    OrderPlanService orderPlanService;
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
	/** (不能与自定义进制有重复)  */
	private static final char b = 'o';
	/** 进制长度 */
	private static final int binLen = r.length;
	
	public void delete(Long id){
		
		planDao.delete(id);
	}
	
	public void deleteBatch(List<Long> ids){
		
		planDao.deleteBatch(ids);
	}
	
	public List<NewServerPlan> FindBybrandId(Long Id){
		
		return planDao.FindBybrandId(Id);	
	}
	
	public void insert(NewServerPlan newServerPlan){
		
		planDao.insert(newServerPlan);
	}
	
	public List<NewServerPlan> FindtwobrandId01(Long Id){
		
		return planDao.FindtwobrandId(Id);	
	}
	

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
	    /** 生成方案的数据*/
	    
	    public List<NewPlanVo> getnewServerPlan(List<Object> list){
	    	  List<NewPlanVo> list01 = new ArrayList<NewPlanVo>();

	    	 for (Object obj : list) {
	    			NewPlanVo newPlanVo  = new NewPlanVo();
	            	
	            	Object[] object = (Object[])obj;
	            	Integer id = (Integer) object[0];
	            	Integer serverid =(Integer) object[1];
	            	String materialJson =(String) object[2];
	            	BigDecimal minPurchase =(BigDecimal)object[3];
	            	BigDecimal unitPrice =(BigDecimal)object[4];
	            	String img_url = (String)object[5];
	            	String invstername = (String)object[6] +"   " +(String)object[7];
	            	String brandname =(String)object[8] +"   " +(String)object[9];
	            	BigDecimal warPer =(BigDecimal)object[10];
	            	String  planName =(String)object[11];
	            	
	            	Integer warPeriod =	warPer.intValue();
	            	
	            	
	            	BigDecimal  allMoney = unitPrice.multiply(minPurchase);
	            	
	            	newPlanVo.setId(id);
	            	newPlanVo.setServerId(serverid);
	            	newPlanVo.setMaterialJson(materialJson);
	            	newPlanVo.setUnitPrice(unitPrice);
	            	newPlanVo.setImg_url(img_url);
	            	newPlanVo.setInvstername(invstername);
	            	newPlanVo.setBrandname(brandname);
	            	newPlanVo.setAllMoney(allMoney.doubleValue());
	            	newPlanVo.setWarPeriod(warPeriod);
	            	newPlanVo.setMinPurchase(minPurchase.doubleValue());
	            	newPlanVo.setPlanName(planName);
	            	
	            	list01.add(newPlanVo);
	    		}
			return list01;
	    	
	    }
	    
	    
	    
	    /** 根据用户和服务商生成一个订单 */
	    
	    public Order getOrder(NewServerPlan newserverPlan ,User user,Double price,Double apoPrice,String orderCode,String IpoMemo,Integer type){
	    	
	    	Order order = new Order();
	    	 /** 保存订单*/
	    //	String orderCode = toSerialCode(newserverPlan.getId(), 4) + format.format(System.currentTimeMillis()) + df1.format(rd.nextInt(9999));
	      //  user.setPassword(null);
	        user.getAddressText();
	        
	         /** 找到服务费率*/
	        Long serverid = newserverPlan.getServerId();
	        Server server = serverDao.findServer(serverid);
	        Double factorage =   server.getFactorage();
	         
	        /** 订单id*/
	        Long order_planid = newserverPlan.getId();
	         /** 保修期*/
	        order.setWarPeriod(newserverPlan.getWarPeriod().intValue());
	       // order.setCapacity(newserverPlan.getCapacity());
	        order.setProvinceId(user.getProvinceId());
	        order.setProvinceText(user.getProvinceText());
	        order.setServerName(server.getCompanyName());
	        /**  转移数据*/
	        order.setAddressText(user.getFullAddressText());
	       
	        logger.info("----------------------addressText："+order.getAddressText());

	        order.setCityId(user.getCityId());
	        order.setCityText(user.getCityText());
	        order.setLinkMan(user.getUserName());
	        if(null != IpoMemo && !IpoMemo.equals("")){
	        	 order.setIpoMemo(IpoMemo);
	        }
	        
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
	        order.setTotalPrice(price+apoPrice);
	         /** 已付金额*/
	        order.setHadPayPrice(0.0);
	         /** 服务费*/
	        order.setFactoragePrice(price * factorage );
	         /** 装机容量*/
	        order.setCapacity(newserverPlan.getCapacity());
	        /** 状态*/
	        order.setStatus(0);
	        order.setGridConnectedIsPay(0);
	        order.setGridConnectedStepA(1);
	        order.setApplyIsPay(0);
	        order.setApplyStepA(0);
	        order.setApplyStepB(0);
	        order.setLoanStatus(0);
	        order.setBuildIsPay(0);
	        order.setBuildStepA(0);
	        order.setBuildStepB(0);
	        /** 安装类型 默认为 1：居民*/
	        order.setType(type);
	       
	        
	        order.setUser(user);
	        
			return order;
	    }
	
	   /** 生成订单计划表*/ 
	
	    public OrderPlan giveOrderPlan(NewServerPlan  serverPlan,Order order){
	    	  OrderPlan orderPlan = new OrderPlan();
	    	/** [电池板id]*/
	    	  orderPlan.setBatteryBoardId(serverPlan.getBatteryboardId());
	    	  /** [电池板品牌]*/
	    	  orderPlan.setBatteryBoardBrand(serverPlan.getSolarPanel().getBrandName());
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

	    	  orderPlanService.save(orderPlan);

			return orderPlan;
	    }
	    
	    
	    public  List<Object> selectServerPlan(Long Id){
			  
			return planDao.selectServerPlan(Id);	
		    }

		  /** 处理服务方案*/
		    public List<Object> getPlan(Long serverId){
		    	
		    	QualificationsServer qualificationsServer = new QualificationsServer();
		    	qualificationsServer.setServerId(serverId);
		    	
		    	List<Object> newList = new LinkedList<Object>();
		    	/** 资质*/
		    	 //q.img_url,q.text
		    	List<Object>	listqua =	qualificationsServerService.selectQualif(serverId);
		    	
		    	for (Object obj : listqua) {
		    		Object[] object = (Object[])obj;
		    	
		    		QualificationsVo quVo = new QualificationsVo();
		    		
		    		String quaimg_url = (String)object[0];
		    		String text =(String)object[1];
		    		
		    		quVo.setImgUrl(quaimg_url);
		    		quVo.setText(text);
		    		
		    		newList.add(quVo);
				}

		       
				return newList;
		    }
		    
		    public List<Object> getPlanTH(Long serverId){
		    	List<Object> newList = new LinkedList<Object>();
		    	 /** 配选项目*/
		          //m.`id`,m.`apolegamy_name`,m.`img_url`,m.`price`,m.`unit`,m.`TYPE`
		         List<Object>  list = apolegamyService.selectApo(serverId);  
		         
		         for (Object obj01 : list) {
		        	 Object[] object01 = (Object[])obj01;
		        	 ApolegamyVo vol = new ApolegamyVo();
		        	 
		        	 Integer id =(Integer) object01[0];
		        	 String apolegamyName =(String)object01[1];
		        	 String apoimgUrl =(String) object01[2];
		        	 BigDecimal apoprice  = (BigDecimal)object01[3];
		        	 String unit = (String) object01[4];
		        	 Integer type = (Integer) object01[5];
		        	 String iconUrl=(String) object01[6];
		        	 
		        	 vol.setId(Long.valueOf(id));
		        	 vol.setApolegamyName(apolegamyName);
		        	 vol.setImgUrl(apoimgUrl);
		        	 vol.setUnit(unit);
		        	 vol.setType(type);
		        	 vol.setPrice(apoprice.doubleValue());
		        	 vol.setIconUrl(iconUrl);
		        	 newList.add(vol);
				}
				return newList;

		    } 

		    
		/** 拿到方案的数据*/
		    
	
	/** 根据用户和服务商生成一个订单 */

	public Order getOrder(NewServerPlan newserverPlan, User user, Double price, Double apoPrice) {

		Order order = new Order();
		/** 保存订单 */
		// String orderCode = toSerialCode(newserverPlan.getId(), 4) +
		// format.format(System.currentTimeMillis()) +
		// df1.format(rd.nextInt(9999));
		// user.setPassword(null);
		user.getAddressText();

		/** 找到服务费率 */
		Long serverid = newserverPlan.getServerId();
		Server server = serverDao.findServer(serverid);
		Double factorage = server.getFactorage();

		/** 订单id */
		Long order_planid = newserverPlan.getId();

		order.setProvinceId(user.getProvinceId());
		order.setProvinceText(user.getProvinceText());
		order.setServerName(server.getCompanyName());
		/** 转移数据 */
		order.setAddressText(user.getAddressText());
		order.setCityId(user.getCityId());
		order.setCityText(user.getCityText());
		order.setLinkMan(user.getNickName());

		order.setLinkPhone(user.getPhone());
		order.setPlanPrice(price);
		order.setServerName(server.getCompanyName());
		// order.setOrderCode(orderCode);
		order.setUserId(user.getId());
		order.setServerId(newserverPlan.getServerId());
		/** 优惠码 */
		order.setPrivilegeCode(null);
		/** 优能的选配项目价格 */
		order.setYnApolegamyPrice(apoPrice);
		/** 服务商选配项目价格 */
		order.setServerApolegamyPrice(apoPrice);
		/** 总价格 */
		order.setTotalPrice(price);
		/** 已付金额 */
		order.setHadPayPrice(0.0);
		/** 服务费 */
		order.setFactoragePrice(price * factorage);
		/** 装机容量 */
		order.setCapacity(Double.valueOf(newserverPlan.getMinPurchase().toString()));
		/** 状态 */
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
	
	   public Order getnewOrder(NewServerPlan newserverPlan ,User user,Double price,Double apoPrice,String orderCode,Double IpoMemo,Integer type){
	    	
	    	Order order = new Order();
	    	 /** 保存订单*/
	        user.getAddressText();
	        
	         /** 找到服务费率*/
	        Long serverid = newserverPlan.getServerId();
	        Server server = serverDao.findServer(serverid);
	        Double factorage =   server.getFactorage();
	         
	        /** 订单id*/
	        Long order_planid = newserverPlan.getId();
	         /** 保修期*/
	        order.setWarPeriod(newserverPlan.getWarPeriod().intValue());
	        order.setProvinceId(user.getProvinceId());
	        order.setProvinceText(user.getProvinceText());
	        order.setServerName(server.getCompanyName());
	        /**  转移数据*/
	        order.setAddressText(user.getFullAddressText());
	       
	        logger.info("----------------------addressText："+order.getAddressText());

	        order.setCityId(user.getCityId());
	        order.setCityText(user.getCityText());
	        order.setLinkMan(user.getUserName());
	        if(null != IpoMemo && !IpoMemo.equals("")){
	        	 order.setIpoMemo("");
	        }
	        
	        order.setLinkPhone(user.getPhone());
	        order.setPlanPrice(price);
	        order.setServerName(server.getCompanyName());
	        order.setOrderCode(orderCode);
	        order.setUserId(user.getId());
	        order.setServerId(newserverPlan.getServerId());
	        /** 优惠码*/
	        order.setPrivilegeCode(null);
	        /** 优能的选配项目价格*/
	        order.setYnApolegamyPrice(0.0);
	        /** 服务商选配项目价格*/
	        order.setServerApolegamyPrice(0.0);
	        /** 总价格*/
	        order.setTotalPrice(price+apoPrice);
	         /** 已付金额*/
	        order.setHadPayPrice(price+apoPrice);
	         /** 服务费*/
	        order.setFactoragePrice(0.0);
	         /** 装机容量*/
	       order.setCapacity(IpoMemo);
	        /** 状态*/
	        order.setStatus(3);
	        order.setGridConnectedIsPay(1);
	        order.setGridConnectedStepA(2);
	        order.setApplyIsPay(1);
	        order.setApplyStepA(2);
	        order.setApplyStepB(2);
	        order.setLoanStatus(0);
	        order.setBuildIsPay(1);
	        order.setBuildStepA(1);
	        order.setBuildStepB(10);
	        /** 安装类型 默认为 0：居民*/
	        order.setType(type);
	       
	        
	        order.setUser(user);
	        
			return order;
	    }

	
}
