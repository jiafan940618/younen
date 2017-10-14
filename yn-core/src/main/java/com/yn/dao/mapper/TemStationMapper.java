package com.yn.dao.mapper;

import com.yn.model.TemStation;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TemStationMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(TemStation record);

    int insertSelective(TemStation record);


    TemStation selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(TemStation record);

    int updateByPrimaryKey(TemStation record);
}