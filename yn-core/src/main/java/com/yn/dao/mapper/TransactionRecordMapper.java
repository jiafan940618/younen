package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.Page;
import com.yn.model.TransactionRecord;

@Mapper
public interface TransactionRecordMapper {
	
	/** 根据条件查询*/
	
	
	List<TransactionRecord> GivePage(Page<TransactionRecord> page);
	
	int FindByNum(Long userId);

}
