package com.yn.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.yn.model.Wallet;

public interface WalletDao extends JpaRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet> {
    @Modifying
    @Query("update Wallet set del=1,delDtm=(now()) where id = :id")
    void delete(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query("update Wallet set del=1,delDtm=(now()) where id in (:ids)")
	void deleteBatch(@Param("ids") List<Long> ids);


	Wallet findByUserId(@Param("userid") Long userid);
	
	@Transactional
	@Modifying
	@Query("update Wallet set money=:#{#wallet.money},updateDtm=(now()) where id = :#{#wallet.id} and del=0")
	void updatePrice(@Param("wallet") Wallet wallet);
	
	@Transactional
	@Modifying
	@Query(value="update wallet as w set w.integral=?1,w.update_dtm=(now()) where w.user_id=?2 and w.del=0",nativeQuery=true)
	void updateIntegral(double integral,Long userId );

}
