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
    
  /*  @Query("select * from Order where orderCode = :#(#order.orderCode)")
    Order findOrder(@Param("order") Order order);*/
    
    
    
}
