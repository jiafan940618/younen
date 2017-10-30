package com.yn.web;

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

import com.yn.model.Apolegamy;
import com.yn.model.NewServerPlan;
import com.yn.model.ProductionDetail;
import com.yn.model.ServerPlan;
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
import com.yn.utils.BeanCopy;
import com.yn.utils.Constant;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.NewServerPlanVo;
import com.yn.vo.ServerPlanVo;
import com.yn.vo.ServerVo;
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
    //@RequestParam("checkedId")   @RequestParam("moneyTotal")
    /** 处理金额*/
    @ResponseBody
    @RequestMapping(value = "/findPlan")
    public  ResultData<Object> findServerPlan(NewServerPlanVo newserverPlanVo,@RequestParam("checkedId") List<Long> checkedId,@RequestParam("moneyTotal") String price,UserVo userVo,HttpSession session) {
    	
    	for (Long long1 : checkedId) {
			logger.info("id串为：-------- ----- ----- ---- "+long1);
		}
    	
    	NewServerPlan newserverPlan = new NewServerPlan();
        BeanCopy.copyProperties(newserverPlanVo, newserverPlan);

        NewServerPlan findOne = newserverPlanService.findOne(newserverPlanVo.getId());
        
        Double minpur =	findOne.getMinPurchase().doubleValue();
        
        logger.info("------ ---- -- -- -- -- - -- - - 购买量为： "+newserverPlanVo.getCapacity().doubleValue());
        
	        if(null == minpur || minpur > newserverPlanVo.getCapacity().doubleValue() ){
	        	logger.info("------ ---- -- -- -- -- - -- - - 千瓦时不能为空且小于 "+findOne.getMinPurchase());
	        	
	        	return  ResultVOUtil.error(777, Constant.PUR_NULL+findOne.getMinPurchase());
	  	  	}
        
        Double utilprice =  findOne.getUnitPrice();
      
        Double minpurchase =  newserverPlanVo.getCapacity().doubleValue();
         /** 计算总价格*/
        Double AllMoney = utilprice * minpurchase;
         /*** 计算备选项目价格*/
        
        List<Apolegamy> list =  apolegamyService.findAll(checkedId);
        
        Double apoPrice = 0.0;
 
        	 for (Apolegamy apolegamy : list) {
	        	 apoPrice += apolegamy.getPrice();
			}
    
  
	        if((AllMoney+apoPrice) != Double.valueOf(price) ){
	        	 /** 价格不对 */
	        	logger.info("------ ---- -- -- -- -- - -- - - ：金额错误 ");
	        	return  ResultVOUtil.error(777, Constant.PRICE_ERROR); 
	        }

       /** 处理订单的信息*/
        
        logger.info("num为:--- --- ---- "+newserverPlanVo.getCapacity().intValue());
        logger.info("方案的id为： ------ ------ ------"+newserverPlanVo.getId());
        logger.info("用户的id为： ------ ------ ------"+userVo.getUserid());
        logger.info("总的金额为： ------ ------ ------"+(AllMoney+apoPrice));
        if(checkedId.size() !=0 ){
	        for (Long id : checkedId) {
	        	 logger.info("集合为： ------ ------ ------"+id);
			}
        }
        
        String orderCode = serverService.getOrderCode(findOne.getServerId());
        
       session.setAttribute("orderCode", orderCode);
       session.setAttribute("num", newserverPlanVo.getCapacity().doubleValue());
       session.setAttribute("list", checkedId);
       session.setAttribute("newserverplanid", newserverPlanVo.getId());
       session.setAttribute("userid", userVo.getUserid());
       session.setAttribute("price", AllMoney);
       
        return ResultVOUtil.success(null);
    }
 //git commit -m "更新电站的分页动态条件查询，修改支付不能小数的BUG"
    
    
 //@RequestParam("serverId") Long serverId
    /** 方案接口*/
    @ResponseBody
    @RequestMapping(value = "/Orderplan")
    public ResultData<Object> findOrderplan(ServerVo serverVo) {
    	/*serverVo.setId(1L);
    	
    	serverVo.setServerId("8");*/
        //	ServerPlanVo serverPlanVo
        logger.info("传过来的serverId为：-- ---- --- ---- "+serverVo.getId());
            NewServerPlan serverPlan = new NewServerPlan();
            serverPlan.setServerId(Long.valueOf(serverVo.getServerId()));

           List<Object>  list01 = newserverPlanService.selectServerPlan(serverVo.getId());
           
           List<NewPlanVo> list = newserverPlanService.getnewServerPlan(list01);

            List<Object> newlist =	 plan.getPlan(serverPlan.getServerId());
            
            List<Object> newlist02 =  plan.getPlanTH(serverPlan.getServerId());
            
            System.out.println(serverPlan.getServerId());
            ProductionDetail production = new ProductionDetail();
        	production.setServerId(serverPlan.getServerId().intValue());
        	
        	 production = productionService.findOne(production);

            return ResultVOUtil.newsuccess(list, newlist,newlist02,production);
        }
    
    
    /** 点击我要建站保存类型*/
    @ResponseBody
    @RequestMapping(value = "/saveType")
    public ResultData<Object> saveType(ServerVo ServerVo,HttpSession httpSession) {
    	
    	logger.info(" ---- ---- ---- --- --- 传过来的类型:"+ServerVo.getType());
    	
    	httpSession.setAttribute("type", ServerVo.getType());
    	
		return ResultVOUtil.success();
    }
    
    
    
}