package com.yn.dao.mapper;

import com.yn.model.Station;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StationMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Station record);

    int insertSelective(Station record);


    Station selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
}