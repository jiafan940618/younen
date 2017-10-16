package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Order;

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
	
	/**
	 * 修改贷款的状态。
	 * @param order
	 * @return
	 */
	int updateLoanStatus(Order order);


	int updateApplyStepBImgUrl(Order order);


	int updateOrderStauts43Step(Order order);


}