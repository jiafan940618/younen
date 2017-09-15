package com.yn.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyOrder;

public interface ApolegamyOrderDao   extends JpaRepository<ApolegamyOrder, Long>, JpaSpecificationExecutor<ApolegamyOrder>{
       
}

