package com.yn.web;

import java.math.BigDecimal;
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

import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyServer;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.ProductionDetail;
import com.yn.model.Qualifications;
import com.yn.model.QualificationsServer;
import com.yn.model.ServerPlan;
import com.yn.model.User;
import com.yn.service.ApolegamyServerService;
import com.yn.service.ApolegamyService;
import com.yn.service.NewServerPlanService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ProductionService;
import com.yn.service.QualificationsServerService;
import com.yn.service.ServerPlanService;
import com.yn.service.UserService;
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewServerPlanVo;
import com.yn.vo.PriceVo;
import com.yn.vo.ServerPlanVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;


@RestController
@RequestMapping("/client/serverPlan")
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

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        serverPlanService.delete(id);
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
 
    /** 处理金额*/
    @ResponseBody
    @RequestMapping(value = "/findPlan")
    public  ResultData<Object> findServerPlan(NewServerPlanVo newserverPlanVo,@RequestParam("ids") List<Long> ids,String price,UserVo userVo,HttpSession session) {

    	 Integer minpur =	newserverPlanVo.getMinPurchase();
    	 
    	
    	 NewServerPlan newserverPlan = new NewServerPlan();
        BeanCopy.copyProperties(newserverPlanVo, newserverPlan);
        
        
        NewServerPlan findOne = newserverPlanService.findOne(newserverPlanVo.getId());
        
        if(null == minpur || minpur < findOne.getMinPurchase() ){
        	logger.info("------ ---- -- -- -- -- - -- - - 千瓦时不能为空且小于 "+findOne.getMinPurchase());
        	
        	return  ResultVOUtil.error(777, Constant.PUR_NULL+findOne.getMinPurchase());
  	  	}
        
        Double utilprice =  findOne.getUnitPrice();
      
        Integer minpurchase =  newserverPlanVo.getMinPurchase();
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

     User newuser=   userservice.findOne(userVo.getId());
       /** 处理订单的信息*/
       
       session.setAttribute("list", list);
       session.setAttribute("newserverPlan", findOne);
       session.setAttribute("user", newuser);
       session.setAttribute("price", AllMoney);
       
      // session.setAttribute("order", neworder);
       
        return ResultVOUtil.success(null);
    }
    
    
   
    
    /** 方案接口*/
    @ResponseBody
    @RequestMapping(value = "/Orderplan")
    public ResultData<Object> findOrderplan() {
    //	ServerPlanVo serverPlanVo
    	
        NewServerPlan serverPlan = new NewServerPlan();
        serverPlan.setServerId(1L);

        List<NewPlanVo> list = new ArrayList<NewPlanVo>();

       List<Object>  list01 = newserverPlanService.selectServerPlan(serverPlan.getServerId());
       

        for (Object obj : list01) {
			NewPlanVo newPlanVo  = new NewPlanVo();
        	
        	Object[] object = (Object[])obj;
        	Integer id = (Integer) object[0];
        	Integer serverid =(Integer) object[1];
        	String materialJson =(String) object[2];
        	Integer minPurchase =(Integer)object[3];
        	BigDecimal unitPrice =(BigDecimal)object[4];
        	String img_url = (String)object[5];
        	String invstername = (String)object[6] +"   " +(String)object[7];
        	String brandname =(String)object[8] +"   " +(String)object[9];
        	BigDecimal  allMoney = unitPrice.multiply(new BigDecimal(minPurchase));
        	
        	newPlanVo.setId(id);
        	newPlanVo.setServerId(serverid);
        	newPlanVo.setMaterialJson(materialJson);
        	newPlanVo.setUnitPrice(unitPrice);
        	newPlanVo.setImg_url(img_url);
        	newPlanVo.setInvstername(invstername);
        	newPlanVo.setBrandname(brandname);
        	newPlanVo.setAllMoney(allMoney.doubleValue());
        	
        	list.add(newPlanVo);
		}

        return ResultVOUtil.success(list);
    }
    
    /** 配选项目,与资质*/
    @ResponseBody
    @RequestMapping(value = "/apolegamy")
    public ResultData<Object> findApolegamy() {
    	 /** 资质*/
    	QualificationsServer qualificationsServer = new QualificationsServer();
    	qualificationsServer.setServerId(1L);
    	
    	List<QualificationsServer>	listqua =	qualificationsServerService.findAll(qualificationsServer);
    	List<Qualifications>  newlist = new ArrayList<Qualifications>();
    	for (QualificationsServer qualificationsServer2 : listqua) {
    		
    		Qualifications qualifications =	qualificationsServer2.getQualifications();
    		newlist.add(qualifications);
		}
    	
        /** 配选项目*/
         ApolegamyServer apolegamyServer = new ApolegamyServer();
         apolegamyServer.setServerId(1L);
         
         List<ApolegamyServer>  list = apolegamyserService.findAll(apolegamyServer);  
         
         List<Apolegamy> listapo = new ArrayList<Apolegamy>();
         
         for (ApolegamyServer apolegamyServer2 : list) {
        	 Apolegamy Apolegamy =	 apolegamyServer2.getApolegamy();
        	 
        	 listapo.add(Apolegamy);
		}
    	
        return ResultVOUtil.newsuccess(listapo,newlist);
    }
    
    /** 服务详情  Service details*/
    @ResponseBody
    @RequestMapping(value = "/detail")
    public ResultData<Object> finddetail() {
    	
    	ProductionDetail production = new ProductionDetail();
    	production.setServerId(1);
    	
    	 production = productionService.findOne(production);
    	
        return ResultVOUtil.success(production);
    }
    
     /** 备份的方案*/
   /* @ResponseBody
    @RequestMapping(value = "/planAll")
    public ResultData<Object> finddall() {
    	  NewServerPlan serverPlan = new NewServerPlan();
          serverPlan.setServerId(1L);

         List<NewServerPlan>  list = newserverPlanService.findAll(serverPlan);
         
        List<PriceVo> listprice = new ArrayList<PriceVo>();
         for (NewServerPlan newServerPlan : list) {
      	   PriceVo newprice = new PriceVo();
      	   Double allMoney = newServerPlan.getMinPurchase() * newServerPlan.getUnitPrice();
      	   Long id = newServerPlan.getId();
      	   
      	   newprice.setAllmoney(allMoney);
      	   newprice.setId(id);
      	   
      	   listprice.add(newprice);
      	
         }
         ApolegamyServer apolegamyServer = new ApolegamyServer();
         apolegamyServer.setServerId(1L);
         
         List<ApolegamyServer>  listap = apolegamyserService.findAll(apolegamyServer);  
         
         List<Apolegamy> listapo = new ArrayList<Apolegamy>();
         
         for (ApolegamyServer apolegamyServer2 : listap) {
        	 Apolegamy Apolegamy =	 apolegamyServer2.getApolegamy();
        	 
        	 listapo.add(Apolegamy);
		}
         ProductionDetail production = new ProductionDetail();
     	production.setServerId(1);
     	
     	 production = productionService.findOne(production);
    	
    	
    	
        return ResultVOUtil.aginsuccess(list, listprice, listapo, production);
    }*/
    
    
}