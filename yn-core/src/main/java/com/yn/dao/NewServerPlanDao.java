package com.yn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.NewServerPlan;
import com.yn.model.ServerPlan;
import com.yn.model.User;
import com.yn.vo.NewServerPlanVo;

public interface NewServerPlanDao  extends JpaRepository<NewServerPlan, Long>, JpaSpecificationExecutor<NewServerPlan>{
	
	
	 @Transactional
	    @Modifying
	    @Query("select new NewServerPlan(serverId,minPurchase,unitPrice) from  NewServerPlan where id = :#{#newserverPlan.id} ")
	    NewServerPlan selectOne(NewServerPlanVo newserverPlan);
	 

	 @Query(value="SELECT"
		 		+ " n.id,n.server_id,n.material_json,n.min_purchase,n.unit_price,n.plan_img_url,t.brand_name t_invstername,t.model t_invstermodel,p.brand_name p_brandname"
		 		+ ",p.model p_brandmodel,d.content,n.capacity"+
	     " FROM new_server_plan n LEFT JOIN inverter t ON t.id = n.inverter_id "+
	    "LEFT JOIN solar_panel p ON n.batteryboard_id = p.id LEFT JOIN production_detail d ON d.server_id = n.`server_id` WHERE n.del=0 AND n.server_id =?1 ",nativeQuery=true)
		   List<Object> selectServerPlan( Long serverId);
	

}
