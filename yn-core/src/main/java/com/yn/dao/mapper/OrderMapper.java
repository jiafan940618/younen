package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Order;
import com.yn.model.Page;
import com.yn.vo.NewUserVo;
import com.yn.vo.UserVo;

@Mapper
public interface OrderMapper {
	
//	@Transactional
//	@Modifying
	int updateByCondition(Order order);
	
	
	/** 根据订单号查询出订单详情*/
	
	Order findstatus(Long orderId);
	
	Order findOrderCode(String orderCode);
	
    /** 根据传入状态修改相应订单状态*/
	void UpdateOrder(Order order);

	int updateConstructionStatus(Order order);
	
	int updateBuildStepB(Order order);
	
	/** 修改订单的进度  [订单状态]{0:申请中,1:施工中,2:并网发电申请中,3:并网发电}'")*/
	void UpdateOrderStatus(Order order);
	
	/** ios端无分页 */
	List<Order> findByiosstatus(NewUserVo userVo);
	
	/** [订单状态]{0:申请中,1:施工中,2:并网发电申请中,3:并网发电,4:退款中,5:退款成功,9:全部}*/
	List<Order> findBystatus(Page<Order> page);
	
	/** 总记录数*/
	int findByNum(Page<Order> page);
	
	/**
	 * 修改贷款的状态。
	 * @param order
	 * @return
	 */
	int updateLoanStatus(Order order);


	int updateApplyStepBImgUrl(Order order);


	int updateOrderStauts43Step(Order order);


}