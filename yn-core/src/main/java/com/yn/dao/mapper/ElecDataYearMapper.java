package com.yn.dao.mapper;

import com.yn.model.ElecDataYear;
import com.yn.model.ElecDataYearExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ElecDataYearMapper {
    int countByExample(ElecDataYearExample example);

    int deleteByExample(ElecDataYearExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ElecDataYear record);

    int insertSelective(ElecDataYear record);

    List<ElecDataYear> selectByExample(ElecDataYearExample example);

    ElecDataYear selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ElecDataYear record, @Param("example") ElecDataYearExample example);

    int updateByExample(@Param("record") ElecDataYear record, @Param("example") ElecDataYearExample example);

    int updateByPrimaryKeySelective(ElecDataYear record);

    int updateByPrimaryKey(ElecDataYear record);
}