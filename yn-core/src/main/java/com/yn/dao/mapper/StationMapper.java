package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Station;

@Mapper
public interface StationMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Station record);

    int insertSelective(Station record);


    Station selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
    
    Station FindById(Long id);
}