package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.BankCard;

public interface BankCardDao extends JpaRepository<BankCard, Long>, JpaSpecificationExecutor<BankCard> {
    @Modifying
    @Query("update BankCard set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update BankCard set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);
    
    
    /** 根据用户id查询出银行卡的编号*/
    @Query("select b from BankCard b where b.userId = :userId  and b.del=0")
   List<BankCard>  selectBank(@Param("userId") Long userId);
    
    
    @Query(value="SELECT b.real_name,b.bank_card_num,b.treaty_id ,c.bank_no  "
    		+ " FROM bank_card b LEFT JOIN bank_code c ON b.bank_id =c.id WHERE b.treaty_id =:treatyId AND b.del =0",nativeQuery=true)
    Object findByTreatyId (@Param("treatyId") String treatyId);
    
    @Query("select new BankCard(id,treatyId,orderNo) from BankCard  where treatyId =:treatyId  and del=0")
    BankCard  findByTreatyId02(@Param("treatyId") String treatyId);
    
}
