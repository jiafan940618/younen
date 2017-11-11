package com.yn.dao.mapper;

import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataMonthExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ElecDataMonthMapper {
    int countByExample(ElecDataMonthExample example);

    int deleteByExample(ElecDataMonthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ElecDataMonth record);

    int insertSelective(ElecDataMonth record);

    List<ElecDataMonth> selectByExample(ElecDataMonthExample example);

    ElecDataMonth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ElecDataMonth record, @Param("example") ElecDataMonthExample example);

    int updateByExample(@Param("record") ElecDataMonth record, @Param("example") ElecDataMonthExample example);

    int updateByPrimaryKeySelective(ElecDataMonth record);

    int updateByPrimaryKey(ElecDataMonth record);
}