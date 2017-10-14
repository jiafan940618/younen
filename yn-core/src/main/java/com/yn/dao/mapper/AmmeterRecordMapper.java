package com.yn.dao.mapper;

import com.yn.model.AmmeterRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AmmeterRecordMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterRecord record);

    int insertSelective(AmmeterRecord record);


    AmmeterRecord selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(AmmeterRecord record);

    int updateByPrimaryKey(AmmeterRecord record);
}