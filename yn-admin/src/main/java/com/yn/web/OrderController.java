package com.yn.web;

import com.yn.dao.OrderDao;
import com.yn.dao.OrderPlanDao;
import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.model.Apolegamy;
import com.yn.model.NewServerPlan;
import com.yn.model.Order;
import com.yn.model.OrderPlan;
import com.yn.model.Server;
import com.yn.model.User;
import com.yn.service.ApolegamyService;
import com.yn.service.NewServerPlanService;
import com.yn.service.NoticeService;
import com.yn.service.OrderPlanService;
import com.yn.service.OrderService;
import com.yn.service.ServerPlanService;
import com.yn.service.ServerService;
import com.yn.service.UserService;
import com.yn.service.WalletService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.ResultData;
import com.yn.vo.NewPlanVo;
import com.yn.vo.OrderVo;
import com.yn.vo.UserVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/server/order")
public class OrderController {
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

    @RequestMapping(value = "/select", method = {RequestMethod.POST})
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


        return ResultVOUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody OrderVo orderVo) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        orderService.save(order);
        return ResultVOUtil.success(order);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        orderService.delete(id);
        return ResultVOUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(OrderVo orderVo) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
        Order findOne = orderService.findOne(order);
        return ResultVOUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(OrderVo orderVo, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Order order = new Order();
        BeanCopy.copyProperties(orderVo, order);
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
     * 账目明细
     *
     * @return
     */
    @RequestMapping(value = "/detailAccounts", method = {RequestMethod.POST})
    @ResponseBody
    public Object detailAccounts(Long serverId) {
        OrderDetailAccounts detailAccounts = orderService.detailAccounts(serverId);
        return ResultVOUtil.success(detailAccounts);
    }
    
    /** 订单详情*/
    @ResponseBody
    @RequestMapping(value = "/seeOrder")
    public Object LookOrder(HttpSession session) {
    	
	  //  Long userid = (Long) session.getAttribute("userid");
    	Long userid = 3L;
	    User user=   userservice.findOne(userid);
	   // List<Long>  list=	(List<Long>) session.getAttribute("list");
    	//Double price = (Double)session.getAttribute("price");
    	//Long	planid =	(Long) session.getAttribute("newserverplanid");
	    List<Long>  list = new LinkedList<Long>();
	    list.add(12L);
	    list.add(13L);
	    list.add(16L);
	    
	    Double price =5000.00;
    	Long planid =1L;
    	
    	
    	 NewServerPlan newserverPlan = newserverPlanService.findOne(planid);
    	
    	NewPlanVo newPlanVo  = new NewPlanVo();
    	
    	Server server =	serverService.findOne(newserverPlan.getServerId());

    	newPlanVo.setCompanyName(server.getCompanyName());
    	newPlanVo.setPhone(user.getPhone());
    	newPlanVo.setUserName(user.getUserName());
    	newPlanVo.setAddress(user.getAddressText());
    	newPlanVo.setId(newserverPlan.getId().intValue());
    	newPlanVo.setServerId(newserverPlan.getServerId().intValue());
    	newPlanVo.setMaterialJson(newserverPlan.getMaterialJson());
    	newPlanVo.setInvstername(newserverPlan.getInverter().getBrandName()+"   "+newserverPlan.getInverter().getModel());
    	newPlanVo.setBrandname(newserverPlan.getSolarPanel().getBrandName()+"   "+newserverPlan.getSolarPanel().getModel());
    	newPlanVo.setAllMoney(price);
    	
    	session.setAttribute("newPlanVo", newPlanVo);
    	
        return ResultVOUtil.success(newPlanVo);
    }
    
    /** 修改电站信息*/
    @RequestMapping(value = "/updateInfo", method = {RequestMethod.POST})
	@ResponseBody
	public Object udatestation(UserVo userVo,HttpSession session) {
    	
    		NewPlanVo plan =(NewPlanVo)	session.getAttribute("newPlanVo");
    		plan.setAddress(userVo.getAddressText());
    		plan.setPhone(userVo.getPhone());
    		plan.setUserName(userVo.getUserName());
    		
    		List<Long> ids =( List<Long>)session.getAttribute("list");
      	  List<Apolegamy> list =  apolegamyService.findAll(ids);
    
    		session.setAttribute("newPlanVo", plan);
    		session.setAttribute("result", ResultVOUtil.newsuccess(plan, list,null,null));

		return ResultVOUtil.newsuccess(plan, list,null,null);
	}
    
    
     /** 点击确定*/
    @ResponseBody
    @RequestMapping(value = "/orderPrice")
    public ResultData<Object> findOrderprice(HttpSession session) {
    	NewPlanVo plan =(NewPlanVo)	session.getAttribute("newPlanVo");
    	
    	List<Long> ids =( List<Long>)session.getAttribute("list");
  	  List<Apolegamy> list =  apolegamyService.findAll(ids);

    	 Double apoPrice = 0.0;
         
         for (Apolegamy apolegamy : list) {
        	 apoPrice += apolegamy.getPrice();
		}
    	
         NewServerPlan	newserverPlan =	(NewServerPlan) session.getAttribute("newserverPlan");
    	  User user02 =  userservice.findByPhone(plan.getPhone());
         //** 添加订单*//* 
         Order order  = newserverPlanService. getOrder(newserverPlan,user02,plan.getAllMoney(),apoPrice);
         
         orderService.save(order);
         
         Order order02 = new Order();
         order02.setOrderCode(order.getOrderCode());
         
         Order neworder =   orderService.findOne(order02);
         
         //** 订单计划表*//*
         
         Long id =  newserverPlan.getId();
      
         NewServerPlan  serverPlan = newserverPlanService.findOne(id);
         
        OrderPlan orderPlan = newserverPlanService.giveOrderPlan(newserverPlan,neworder);
        OrderPlan orderPlan2 = new OrderPlan();
        orderPlan2.setOrderId(orderPlan.getOrderId());
        
        OrderPlan newOrdPlan =  orderPlanService.findOne(orderPlan2);
        
        order.setOrderPlanId(newOrdPlan.getId());
        
        orderPlanService.save(orderPlan);
        
        neworder.setOrderPlan(newOrdPlan);
        
        neworder.getUser().setPassword(null);
    	
        return ResultVOUtil.success(null);
    }
    
    @ResponseBody
    @RequestMapping(value = "/looking")
    public ResultData<Object> findOrderprice1(HttpSession session) {
		
    	NewPlanVo plan =(NewPlanVo)	session.getAttribute("newPlanVo");
    	
    	
  //  required=false
  
    	return null;
    }  
    

}
