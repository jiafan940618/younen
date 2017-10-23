package com.yn.dao.mapper;

import com.yn.model.ElecDataHour;
import com.yn.model.ElecDataHourExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ElecDataHourMapper {
	int countByExample(ElecDataHourExample example);

	int deleteByExample(ElecDataHourExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(ElecDataHour record);

	int insertSelective(ElecDataHour record);

	List<ElecDataHour> selectByExample(ElecDataHourExample example);

	ElecDataHour selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") ElecDataHour record, @Param("example") ElecDataHourExample example);

	int updateByExample(@Param("record") ElecDataHour record, @Param("example") ElecDataHourExample example);

	int updateByPrimaryKeySelective(ElecDataHour record);

	int updateByPrimaryKey(ElecDataHour record);

	List<ElecDataHour> selectByQuery(ElecDataHour elecDataHour);

}