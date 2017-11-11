package com.yn.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.ElecDataDay;
import com.yn.model.ElecDataDayExample;
import com.yn.model.ElecDataHour;
@Mapper
public interface ElecDataDayMapper {
	int countByExample(ElecDataDayExample example);

	int deleteByExample(ElecDataDayExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(ElecDataDay record);

	int insertSelective(ElecDataDay record);

	List<ElecDataDay> selectByExample(ElecDataDayExample example);

	ElecDataDay selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") ElecDataDay record, @Param("example") ElecDataDayExample example);

	int updateByExample(@Param("record") ElecDataDay record, @Param("example") ElecDataDayExample example);

	int updateByPrimaryKeySelective(ElecDataDay record);

	int updateByPrimaryKey(ElecDataDay record);

	List<ElecDataDay> selectByQuery(Map<String, Object> map);

	ElecDataDay findHuanbao(Map<String, Object> map);

}