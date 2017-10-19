package com.yn.dao.mapper;

import com.yn.model.Station;


public interface StationMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Station record);

    int insertSelective(Station record);


    Station selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
}