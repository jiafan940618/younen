package com.yn.dao.mapper;

import com.yn.model.Ammeter;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AmmeterMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Ammeter record);

    int insertSelective(Ammeter record);


    Ammeter selectByPrimaryKey(Integer id);



    int updateByPrimaryKeySelective(Ammeter record);

    int updateByPrimaryKey(Ammeter record);
}