package com.yn.dao.mapper;

import com.yn.model.TemStationYear;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface TemStationYearMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(TemStationYear record);

    int insertSelective(TemStationYear record);


    TemStationYear selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(TemStationYear record);

    int updateByPrimaryKey(TemStationYear record);
}