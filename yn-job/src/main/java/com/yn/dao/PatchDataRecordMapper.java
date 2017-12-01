package com.yn.dao;

import com.yn.model.PatchDataRecord;
import com.yn.model.PatchDataRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface PatchDataRecordMapper {
	int countByExample(PatchDataRecordExample example);

	int deleteByExample(PatchDataRecordExample example);

	int deleteByPrimaryKey(String amPhaseRecordId);

	int insert(PatchDataRecord record);

	int insertSelective(PatchDataRecord record);

	List<PatchDataRecord> selectByExample(PatchDataRecordExample example);

	PatchDataRecord selectByPrimaryKey(String amPhaseRecordId);

	int updateByExampleSelective(@Param("record") PatchDataRecord record,
			@Param("example") PatchDataRecordExample example);

	int updateByExample(@Param("record") PatchDataRecord record, @Param("example") PatchDataRecordExample example);

	int updateByPrimaryKeySelective(PatchDataRecord record);

	int updateByPrimaryKey(PatchDataRecord record);

	int truncateTable();
	
	PatchDataRecord find4Daddr(PatchDataRecord record);
}