package com.yn.web;

import com.yn.domain.OrderDetailAccounts;
import com.yn.enums.NoticeEnum;
import com.yn.model.Order;
import com.yn.service.NoticeService;
import com.yn.service.OrderService;
import com.yn.session.SessionCache;
import com.yn.utils.BeanCopy;
import com.yn.vo.OrderVo;
import com.yn.vo.re.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/order")
public class OrderController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private NoticeService noticeService;


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

}
