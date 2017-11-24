package com.yn.dao.mapper;


import com.yn.model.AmmeterRecord;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AmmeterRecordMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterRecord record);

    int insertSelective(AmmeterRecord record);


    AmmeterRecord selectByPrimaryKey(Integer id);

    List<AmmeterRecord> selectByQuery(Map<String, Object> map);

    int updateByPrimaryKeySelective(AmmeterRecord record);

    int updateByPrimaryKey(AmmeterRecord record);

	AmmeterRecord select(AmmeterRecord ammeterRecord);
}