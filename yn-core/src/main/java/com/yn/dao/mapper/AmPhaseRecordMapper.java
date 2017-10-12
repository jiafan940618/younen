package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.AmPhaseRecord;
import com.yn.vo.AmPhaseRecordExample;

@Mapper
public interface AmPhaseRecordMapper {

	public void addAmPhaseRecord(AmPhaseRecord amPhaseRecord);
	 int countByExample(AmPhaseRecordExample example);

	    int deleteByExample(AmPhaseRecordExample example);

	    int deleteByPrimaryKey(String amPhaseRecordId);

	    int insert(AmPhaseRecord record);

	    int insertSelective(AmPhaseRecord record);

	    List<AmPhaseRecord> selectByExample(AmPhaseRecordExample example);

	    AmPhaseRecord selectByPrimaryKey(String amPhaseRecordId);

	    int updateByExampleSelective(@Param("record") AmPhaseRecord record, @Param("example") AmPhaseRecordExample example);

	    int updateByExample(@Param("record") AmPhaseRecord record, @Param("example") AmPhaseRecordExample example);

	    int updateByPrimaryKeySelective(AmPhaseRecord record);

	    int updateByPrimaryKey(AmPhaseRecord record);
}
