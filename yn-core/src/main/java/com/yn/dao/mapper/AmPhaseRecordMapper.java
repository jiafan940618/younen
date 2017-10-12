package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.AmPhaseRecord;

@Mapper
public interface AmPhaseRecordMapper {

	public void addAmPhaseRecord(AmPhaseRecord amPhaseRecord);
}
