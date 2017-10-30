
package com.yn.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Order;

public interface OrderDao extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
	@Modifying
	@Query("update Order set del=1,delDtm=(now()) where id = :id")
	void delete(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query("update Order set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query("SELECT COUNT(*) FROM Order u WHERE u.createDtm>=?1 AND u.createDtm<?2 AND u.del=0")
    long countNum(Date startDtm, Date endDtm);
    
    @Query("SELECT COUNT(*) FROM Order u WHERE u.createDtm>=?1 AND u.createDtm<?2 AND u.serverId=?3 AND u.del=0")
    long countNum(Date startDtm, Date endDtm, Long serverId);
    
    @Query("select COALESCE(sum(s.capacity),0) from Order s WHERE s.del=0")
    double sumCapacity();
    
    @Query("select COALESCE(sum(s.capacity),0) from Order s WHERE s.serverId=?1 AND s.del=0")
    double sumCapacity(Long serverId);

    @Query("select o.userId from Order o where o.del=0 and o.serverId=?1")
    Set<Long> findUserId(Long serverId);
    
    /** 根据订单号查找订单信息*/
     //,o.loan_status,o.apply_is_pay,o.apply_stepa,o.apply_stepb
    @Query(value="SELECT o.id,o.order_code,s.station_code,o.capacity,"+
	 "o.total_price,o.had_pay_price,o.server_name,o.status FROM t_order o LEFT JOIN station s ON s.order_id = o.id  WHERE o.id =:orderId",nativeQuery=true)
    Object findOrder(@Param("orderId") Long orderId);
    
  @Query(value="SELECT o.link_man,o.link_phone,o.address_text,o.server_name,o.order_code, "+
	" p.battery_board_brand,p.battery_board_model,p.inverter_brand,p.inverter_model,"
	+ "p.other_material_json_text,o.capacity,o.plan_price,d.apo_ids,d.price,o.total_price,o.war_period,o.status"+
		"  FROM t_order o LEFT JOIN order_plan p ON o.id = p.order_id"+ 
		 "  LEFT JOIN apolegamy_order d ON d.order_id = o.id WHERE o.id =:orderId",nativeQuery=true)
   
   Object getInfoOrder(@Param("orderId") Long orderId);
  
   /** ios端的订单详情*/
  @Query(value="SELECT o.link_man,o.link_phone,o.address_text,o.server_name,o.order_code, "+
			" p.battery_board_brand,p.battery_board_model,p.inverter_brand,p.inverter_model,"
			+ "p.other_material_json_text,o.capacity,o.plan_price,d.apo_ids,d.price,o.total_price,o.war_period,o.status,o.ipo_memo,o.loan_status,o.create_dtm,o.had_pay_price "+
				"  FROM t_order o LEFT JOIN order_plan p ON o.id = p.order_id"+ 
				 "  LEFT JOIN apolegamy_order d ON d.order_id = o.id WHERE o.id =:orderId",nativeQuery=true)
		   
		   Object getIosInfoOrder(@Param("orderId") Long orderId);
  
    
  	@Query(value="SELECT o.id,o.status,o.had_pay_price,o.total_price FROM `bill_order`"
  			+ " b LEFT JOIN t_order o ON b.order_id = o.id WHERE b.trade_no = :tradeNo ;",nativeQuery=true)
  	Object FindByTradeNo(@Param("tradeNo") String tradeNo);
  	
  	
  	@Transactional
	@Modifying
	@Query("update Order set status =:#{#order.status} where id = :#{#order.id}")
	void updateOrderbyId(@Param("order") Order order);


  	@Query(value=" SELECT id,status,had_pay_price,total_price from t_order where  id = :#{#order.id} and del =0 ",nativeQuery=true)
  	Object selectOrderSta(@Param("order") Order order);
  
  
  	@Query("select o.orderCode from Order o where o.del=0 and o.id=?1")
    String findByStationId(Long orderId);
  	
  	@Query(value ="SELECT t.`id`,t.`capacity`,t.`server_name`,t.`order_code`,t.grid_connected_stepa FROM t_order t "
			+ "  WHERE t.grid_connected_stepa = 1  AND t.`status` <> 3 AND t.`del` = 0 AND user_id = :userId", nativeQuery = true)
	List<Object> findsome(@Param("userId") Long userId);

}
