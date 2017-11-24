package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.OrderPlan;

public interface OrderPlanDao extends JpaRepository<OrderPlan, Long>, JpaSpecificationExecutor<OrderPlan> {
    @Modifying
    @Query("update OrderPlan set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update OrderPlan set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    @Query(value = "SELECT o.order_code,o.construction_status ,p.battery_board_brand,p.battery_board_model,p.inverter_brand,p.inverter_model,a.apo_ids,o.war_period FROM t_order o" 
    		+" LEFT JOIN order_plan p ON o.id = p.order_id"
    		+" LEFT JOIN  apolegamy_order a ON a.order_id = o.id"
    		+" WHERE o.id = ?1 AND o.del = 0",nativeQuery=true)
   	Object selectByid(Long id);
    
    @Query(value = "SELECT s.id,s.server_id,s.user_id,s.station_code,s.capacity,s.link_man,s.status,r.company_name,"
    		+ " a.c_addr,s.order_id,s.link_phone FROM station s LEFT JOIN server r ON  s.server_id = r.id "
    		+ " LEFT JOIN  ammeter a ON a.station_id = s.id WHERE s.id = ?1 AND s.del =0",nativeQuery=true)
   	List<Object> findByid(Long id);
    
    
}
