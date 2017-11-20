package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.yn.model.WxInfomation;

public interface WxInfomationDao extends JpaRepository<WxInfomation, Long>, JpaSpecificationExecutor<WxInfomation> {

}
