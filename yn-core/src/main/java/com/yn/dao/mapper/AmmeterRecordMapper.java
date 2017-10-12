package com.yn.dao.mapper;

import com.yn.model.AmmeterRecord;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmmeterRecordMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterRecord record);

    int insertSelective(AmmeterRecord record);


    AmmeterRecord selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(AmmeterRecord record);

    int updateByPrimaryKey(AmmeterRecord record);
}