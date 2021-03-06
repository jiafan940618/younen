package com.yn.web;

import com.yn.model.*;
import com.yn.vo.re.ResultVOUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.yn.service.ApolegamyServerService;
import com.yn.service.ApolegamyService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ProductionService;
import com.yn.service.QualificationsServerService;
import com.yn.service.ServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.UserService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewServerPlanVo;
import com.yn.vo.ServerPlanVo;
import com.yn.vo.UserVo;

@RestController
@RequestMapping("/server/serverPlan")
public class ServerPlanController {
	private static final Logger logger = LoggerFactory.getLogger(ServerPlanController.class);
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderPlanService orderPlanService;
	@Autowired
	UserService userservice;
	@Autowired
    ApolegamyServerService apolegamyserService;
    @Autowired
	ApolegamyService apolegamyService;
    @Autowired
    ServerPlanService serverPlanService;
    @Autowired
    ProductionService productionService;
    @Autowired
    QualificationsServerService qualificationsServerService;
    @Autowired
    NewServerPlanService plan;
    @Autowired
    ServerService serverService;
    @Autowired
    NewServerPlanService newserverPlanService;
    

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        ServerPlan findOne = serverPlanService.findOne(id);
        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody ServerPlanVo serverPlanVo) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        serverPlanService.save(serverPlan);
        return ResultVOUtil.success(serverPlan);
    }

     /** 添加后台服务商方案*/
    @ResponseBody
    @RequestMapping(value = "/newsave", method = {RequestMethod.POST})
    public Object newsave(@RequestBody NewServerPlanVo serverPlanVo,HttpSession session) {

        logger.info("传递的serverId为：----- ---- ---- --- ---"+serverPlanVo.getServerId());
        logger.info("传递SolarPanel的Id为：----- ---- ---- --- ---"+serverPlanVo.getSolarPanel().getId());
        logger.info("传递SolarPanel的QualityAssurance为：----- ---- ---- --- ---"+serverPlanVo.getSolarPanel().getQualityAssurance());
        logger.info("传递SolarPanel的BoardYear为：----- ---- ---- --- ---"+serverPlanVo.getSolarPanel().getBoardYear());

        logger.info("传递Inverter的Id为：----- ---- ---- --- ---"+serverPlanVo.getInverter().getId());
        logger.info("传递Inverter的QualityAssurance为：----- ---- ---- --- ---"+serverPlanVo.getInverter().getQualityAssurance());
        logger.info("传递Inverter的BoardYear为：----- ---- ---- --- ---"+serverPlanVo.getInverter().getBoardYear());
        Server serverResult = serverService.findOne(serverPlanVo.getServerId());
    	
    	NewServerPlan serverPlan = new NewServerPlan();

    	serverPlan.setServerId(serverResult.getId());
    	
    	List<NewServerPlan> list = newserverPlanService.findAll(serverPlan);
    	
    	serverPlanVo.setServerId(serverResult.getId());
    	
    	serverPlanVo.setConversionEfficiency(serverPlanVo.getSolarPanel().getConversionEfficiency());

        serverPlanVo.setType(1);

        return newserverPlanService.insertServerPlan(list,serverPlan,serverPlanVo);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        serverPlanService.delete(id);
        return ResultVOUtil.success();
    }
    
    @ResponseBody
    @RequestMapping(value = "/newdelete", method = {RequestMethod.POST})
    public Object newdelete(Long id) {
    	
    	newserverPlanService.delete(id);

        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(ServerPlanVo serverPlanVo) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        ServerPlan findOne = serverPlanService.findOne(serverPlan);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(ServerPlanVo serverPlanVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        ServerPlan serverPlan = new ServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);
        Page<ServerPlan> findAll = serverPlanService.findAll(serverPlan, pageable);
        return ResultVOUtil.success(findAll);
    }
    
    @RequestMapping(value = "/newfindAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object newfindAll(NewServerPlanVo serverPlanVo,HttpSession session) {

        Long userId = SessionCache.instance().getUserId();

        Server server = new Server();
        server.setUserId(userId);

    	Server serverResult = serverService.findOne(server);
    	
    	serverPlanVo.setServerId(serverResult.getId());
    	
        NewServerPlan serverPlan = new NewServerPlan();
        BeanCopy.copyProperties(serverPlanVo, serverPlan);

       List<NewServerPlan> findAll = newserverPlanService.findAll(serverPlan);
        return ResultVOUtil.success(findAll);
    }
    
 
    /** 处理金额*/
    @ResponseBody
    @RequestMapping(value = "/findPlan")
    public  ResultData<Object> findServerPlan(NewServerPlanVo newserverPlanVo,@RequestParam("checkedId") List<Long> ids,@RequestParam("moneyTotal") String price,UserVo userVo,HttpSession session) {
    	

    	 NewServerPlan newserverPlan = new NewServerPlan();
        BeanCopy.copyProperties(newserverPlanVo, newserverPlan);
        
        
        NewServerPlan findOne = newserverPlanService.findOne(newserverPlanVo.getId());
        
        Double minpur =	findOne.getMinPurchase().doubleValue();
        
        if(null == minpur || minpur < newserverPlanVo.getCapacity().intValue() ){
        	logger.info("------ ---- -- -- -- -- - -- - - 千瓦时不能为空且小于 "+findOne.getMinPurchase());
        	
        	return  ResultVOUtil.error(777, Constant.PUR_NULL+findOne.getMinPurchase());
  	  	}
        
        Double utilprice =  findOne.getUnitPrice();
      
        Integer minpurchase =  newserverPlanVo.getCapacity().intValue();
         /** 计算总价格*/
        Double AllMoney = utilprice * minpurchase;
         /*** 计算备选项目价格*/
        List<Apolegamy> list =  apolegamyService.findAll(ids);
        
       Double apoPrice = 0.0;
        
         for (Apolegamy apolegamy : list) {
        	 apoPrice += apolegamy.getPrice();
		}
        
        if((AllMoney+apoPrice) != Double.valueOf(price) ){
        	 /** 价格不对 */
        	logger.info("------ ---- -- -- -- -- - -- - - ：金额错误 ");
        	return  ResultVOUtil.error(777, Constant.PRICE_ERROR); 
        }

   //  User newuser=   userservice.findOne(userVo.getId());
       /** 处理订单的信息*/
       
       session.setAttribute("list", ids);
       session.setAttribute("newserverplanid", newserverPlanVo.getId());
       session.setAttribute("userid", userVo.getUserid());
       session.setAttribute("price", AllMoney);
       
      // session.setAttribute("order", neworder);
       
        return ResultVOUtil.success(null);
    }

    
    
   
    
    /** 方案接口*/
    @ResponseBody
    @RequestMapping(value = "/Orderplan")
    public ResultData<Object> findOrderplan(ServerPlanVo serverPlanVo) {
    
    		serverPlanVo.setServerId(1L);
    	
	    	ServerPlan serverPlan = new ServerPlan();
	        BeanCopy.copyProperties(serverPlanVo, serverPlan);

            List<NewPlanVo> list = new ArrayList<NewPlanVo>();

           List<Object>  list01 = newserverPlanService.selectServerPlan(serverPlan.getServerId());
         
            for (Object obj : list01) {
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
            	String conent = (String)object[10];
            	String  planName =(String)object[11];

            	BigDecimal  allMoney = unitPrice.multiply(minPurchase);
            	
            	newPlanVo.setId(id);
            	newPlanVo.setServerId(serverid);
            	newPlanVo.setMaterialJson(materialJson);
            	newPlanVo.setUnitPrice(unitPrice);
            	newPlanVo.setImg_url(img_url);
            	newPlanVo.setInvstername(invstername);
            	newPlanVo.setBrandname(brandname);
            	newPlanVo.setAllMoney(allMoney.doubleValue());
            	newPlanVo.setConent(conent);
            	newPlanVo.setPlanName(planName);
            	newPlanVo.setMinPurchase(minPurchase.doubleValue());
            	
            	list.add(newPlanVo);
    		}
            
            List<Object> newlist =	 plan.getPlan(serverPlan.getServerId());
            
            List<Object> newlist02 =  plan.getPlanTH(serverPlan.getServerId());
            
            System.out.println(serverPlan.getServerId());
            ProductionDetail production = new ProductionDetail();
        	production.setServerId(serverPlan.getServerId().intValue());
        	
        	 production = productionService.findOne(production);

            return ResultVOUtil.newsuccess(list, newlist,newlist02,production);
        }
    
}