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
    @Query("delete from NewServerPlan  where id = ?1")
    void delete( Long id);
    
    @Transactional
    @Modifying
    @Query("update NewServerPlan set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
	
	 @Transactional
	    @Modifying
	    @Query("select new NewServerPlan(serverId,minPurchase,unitPrice) from  NewServerPlan where id = :#{#newserverPlan.id} and del = 0 ")
	    NewServerPlan selectOne(NewServerPlanVo newserverPlan);
	 

	 @Query(value="SELECT"
		 		+ " n.id,n.server_id,n.material_json,n.min_purchase,n.unit_price,n.plan_img_url,t.brand_name t_invstername,t.model t_invstermodel,p.brand_name p_brandname"
		 		+ ",p.model p_brandmodel,n.war_period,n.plan_name "+
	     " FROM new_server_plan n LEFT JOIN inverter t ON t.id = n.inverter_id "+
	     " LEFT JOIN solar_panel p ON n.batteryboard_id = p.id  WHERE n.del=0 AND n.plan_id =0 and n.server_id =?1 ",nativeQuery=true)
		   List<Object> selectServerPlan(Long Id);
	 
	 @Query(value="SELECT * FROM new_server_plan n  LEFT JOIN solar_panel s ON n.batteryboard_id = s.id WHERE s.brand_id = ?1 and n.del=0 ",nativeQuery=true)
	List<NewServerPlan> FindBybrandId(Long Id);
	 
	 @Query(value="SELECT * FROM new_server_plan n  LEFT JOIN inverter i ON n.inverter_id = i.id WHERE i.brand_id = ?1 and n.del = 0",nativeQuery=true)
	 List<NewServerPlan> FindtwobrandId(Long Id);
	 
	 @Query(value="INSERT INTO new_server_plan  "+
     " (server_id,batteryboard_id,inverter_id,material_json,min_purchase,unit_price,plan_img_url,plan_id,war_period,faction_id,TYPE,plan_name)" 
      +" VALUES (:#{#newServerPlan.serverId},:#{#newServerPlan.batteryboardId},:#{#newServerPlan.inverterId},"
      + ":#{#newServerPlan.materialJson},:#{#newServerPlan.minPurchase},:#{#newServerPlan.unitPrice},:#{#newServerPlan.planImgUrl},"
      + ":#{#newServerPlan.planId},:#{#newServerPlan.warPeriod},:#{#newServerPlan.factionId},:#{#newServerPlan.type},:#{#newServerPlan.planName})",nativeQuery=true)
	 void insert(@Param("newServerPlan") NewServerPlan newServerPlan);
	 
	

}
