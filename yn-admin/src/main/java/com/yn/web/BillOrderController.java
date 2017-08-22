package com.yn.web;

import com.yn.dao.BillOrderDao;
import com.yn.dao.OrderDao;
import com.yn.model.BillOrder;
import com.yn.model.Order;
import com.yn.service.BillOrderService;
import com.yn.service.OrderService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.utils.StringUtil;
import com.yn.vo.BillOrderVo;
import com.yn.vo.re.ResultDataVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/billOrder")
public class BillOrderController {


    @Autowired
    BillOrderService billOrderService;
    @Autowired
    BillOrderDao billOrderDao;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDao orderDao;


    @RequestMapping(value = "/select", method = {RequestMethod.POST})
    @ResponseBody
    public Object findOne(Long id) {
        BillOrder findOne = billOrderService.findOne(id);
        return ResultDataVoUtil.success(findOne);
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public Object save(@RequestBody BillOrderVo billOrderVo) {
        BillOrder billOrder = new BillOrder();
        BeanCopy.copyProperties(billOrderVo, billOrder);
        billOrderService.save(billOrder);
        return ResultDataVoUtil.success(billOrder);
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public Object delete(Long id) {
        billOrderService.delete(id);
        return ResultDataVoUtil.success();
    }

    @ResponseBody
    @RequestMapping(value = "/findOne", method = {RequestMethod.POST})
    public Object findOne(BillOrderVo billOrderVo) {
        BillOrder billOrder = new BillOrder();
        BeanCopy.copyProperties(billOrderVo, billOrder);
        BillOrder findOne = billOrderService.findOne(billOrder);
        return ResultDataVoUtil.success(findOne);
    }

    @RequestMapping(value = "/findAll", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object findAll(BillOrderVo billOrderVo, String orderStatus, @PageableDefault(value = 15, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        BillOrder billOrder = new BillOrder();
        BeanCopy.copyProperties(billOrderVo, billOrder);
        Page<BillOrder> findAll = billOrderService.findAll(billOrder, orderStatus, pageable);
        return ResultDataVoUtil.success(findAll);
    }

    /**
     * 手动录入订单的交易
     *
     * @param orderId
     * @param money
     * @return
     */
    @RequestMapping(value = "/manualInput", method = {RequestMethod.POST})
    @ResponseBody
    public Object manualInput(@RequestParam(value = "orderId") Long orderId, @RequestParam(value = "money") Double money) {
        Long userId = SessionCache.instance().checkUserIsLogin();

        Order order = orderService.findOne(orderId);
        Double totalPrice = order.getTotalPrice();
        Double hadPayPrice = order.getHadPayPrice();
        Double shouldPayPrice = totalPrice - hadPayPrice;

        if (hadPayPrice.doubleValue() == totalPrice.doubleValue()) {
            return ResultDataVoUtil.error(777, "该订单已经支付完，不用继续录入");
        } else if ((hadPayPrice + money) > totalPrice) {
            return ResultDataVoUtil.error(777, "该订单的总价是" + totalPrice + "元，已经支付了" + hadPayPrice + "元， 此次录入的金额不可以超过" + shouldPayPrice + "元");
        }

        BillOrder billOrder = new BillOrder();
        billOrder.setOrderId(orderId);
        billOrder.setPayWay(0);
        billOrder.setMoney(money);
        billOrder.setRemark("手动录入");
        billOrder.setStatus(0);
        billOrder.setUserId(order.getUserId());
        billOrder.setDutyUserId(userId);
        billOrder.setTradeNo(StringUtil.getRandomTradeNo());
        billOrderDao.save(billOrder);

        order.setHadPayPrice(order.getHadPayPrice() + money);
        orderDao.save(order);

        return ResultDataVoUtil.success();
    }
}
