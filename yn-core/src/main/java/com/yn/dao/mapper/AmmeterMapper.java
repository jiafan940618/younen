package com.yn.dao.mapper;

import com.yn.model.Ammeter;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AmmeterMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Ammeter record);

    int insertSelective(Ammeter record);


    Ammeter selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(Ammeter record);

    int updateByPrimaryKey(Ammeter record);
}