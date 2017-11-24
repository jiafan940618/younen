package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.yn.model.TestKwh;

public interface TestKwhDao extends JpaRepository<TestKwh, Integer>, JpaSpecificationExecutor<TestKwh>{
	
	@Query(value="select t.kwh from testkwh as t WHERE t.time=?1",nativeQuery=true)
    double getKwh(Integer time);
}
