package com.yn.service;

import com.yn.dao.OrderPlanDao;
import com.yn.model.Apolegamy;
import com.yn.model.OrderPlan;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.StationVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OrderPlanService {
    @Autowired
    OrderPlanDao orderPlanDao;
    @Autowired
    ApolegamyService apolegamyService;

    public OrderPlan findOne(Long id) {
        return orderPlanDao.findOne(id);
    }

    public void save(OrderPlan orderPlan) {
        if(orderPlan.getId()!=null){
        	OrderPlan one = orderPlanDao.findOne(orderPlan.getId());
            try {
                BeanCopy.beanCopy(orderPlan,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            orderPlanDao.save(one);
        }else {
            orderPlanDao.save(orderPlan);
        }
    }
    /** 后台电站详情 orderid*/
    public Map<String,String> selectByid(Long orderid){
    	
    	Map<String,String> map = new HashMap<String,String>();
    	
    	Object list = orderPlanDao.selectByid(orderid);
			Object[] obj = (Object[]) list;
			
			String orderCode = (String)obj[0];
			String orderText = (String)obj[1];
			String batteryBoardBrand = (String)obj[2];
			String batteryBoardModel = (String)obj[3];
			String inverterBrand = (String)obj[4];
			String inverterModel = (String)obj[5];
			String apoIds = (String)obj[6];
			Integer batteryBoardWarrantyYear = (Integer)obj[7];
			Integer id = (Integer)obj[8];
			
			
			map.put("orderCode", orderCode);
			map.put("constructionStatus", orderText);
			map.put("batteryBoard", batteryBoardBrand+" "+batteryBoardModel);
			map.put("inverter", inverterBrand+" "+inverterModel);

		String apoids = "";
			if(apoIds != null ){
	        String[] ids =  apoIds.split(",");

	        List<Long> newlist = new LinkedList<Long>();
	        for (int i = 0; i < ids.length; i++) {
	        	newlist.add(Long.valueOf(ids[i]));
			}


	        List<Apolegamy>  apolist =  apolegamyService.findAll(newlist);



	        if(apolist.size() !=0){

		        for (Apolegamy apolegamy : apolist) {
		        	apoids += apolegamy.getApolegamyName()+"、";
				}
	        }
			}
	        if(apoids.equals("")){
	        	map.put("apoIds", "未选择配选项目");
	        }else{
	        	 map.put("apoIds", apoids);
	        }

	        map.put("batteryBoardWarrantyYear", batteryBoardWarrantyYear.toString());
	        map.put("plan_id", id.toString());
	        
		return map;
    }
    
    public  StationVo findByid(Long id){
    	StationVo staionVo = new StationVo();
    	
    	List<Object> list =orderPlanDao.findByid(id);
    	for (Object object : list) {
 
    		Object[] obj = (Object[]) object;
    		Integer sId = (Integer)obj[0];
    		Integer serverId = (Integer)obj[1];
    		Integer userId = (Integer)obj[2];
    		String stationCode = (String)obj[3];
    		BigDecimal capaCity = (BigDecimal) obj[4];
    		String linkMan = (String)obj[5];
    		Integer status = (Integer)obj[6];
    		String companyName = (String)obj[7];
    		Integer caddr = (Integer)obj[8];
    		Integer orderId = (Integer)obj[9];
    		String linkPhone = (String)obj[10];
    		
    		
    		staionVo.setId(Long.valueOf(sId));
    		staionVo.setServerId(Long.valueOf(serverId));
    		staionVo.setUserId(Long.valueOf(userId));
    		staionVo.setStationCode(stationCode);
    		staionVo.setCapacity(capaCity.doubleValue());
    		staionVo.setLinkMan(linkMan);
    		staionVo.setStatus(status);
    		staionVo.setCompanyName(companyName);
    		if(caddr == null){
    			staionVo.setCaddr("未绑定电表");
    		}else{
    		
    		staionVo.setCaddr(caddr.toString());
    		}
    		staionVo.setOrderId(Long.valueOf(orderId));
    		staionVo.setLinkPhone(linkPhone);
		}
    	
    	
		return staionVo;
    }
    
    
    

    public void delete(Long id) {
        orderPlanDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		orderPlanDao.deleteBatch(id);
	}

    public OrderPlan findOne(OrderPlan orderPlan) {
        Specification<OrderPlan> spec = RepositoryUtil.getSpecification(orderPlan);
        OrderPlan findOne = orderPlanDao.findOne(spec);
        return findOne;
    }

    public List<OrderPlan> findAll(List<Long> list) {
        return orderPlanDao.findAll(list);
    }

    public Page<OrderPlan> findAll(OrderPlan orderPlan, Pageable pageable) {
        Specification<OrderPlan> spec = RepositoryUtil.getSpecification(orderPlan);
        Page<OrderPlan> findAll = orderPlanDao.findAll(spec, pageable);
        return findAll;
    }

    public List<OrderPlan> findAll(OrderPlan orderPlan) {
        Specification<OrderPlan> spec = RepositoryUtil.getSpecification(orderPlan);
        return orderPlanDao.findAll(spec);
    }
}
