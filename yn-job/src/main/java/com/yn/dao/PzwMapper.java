package com.yn.dao;

import com.yn.model.Pzw;
import com.yn.model.PzwExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PzwMapper {
    int countByExample(PzwExample example);

    int deleteByExample(PzwExample example);

    int insert(Pzw record);

    int insertSelective(Pzw record);

    List<Pzw> selectByExample(PzwExample example);

    int updateByExampleSelective(@Param("record") Pzw record, @Param("example") PzwExample example);

    int updateByExample(@Param("record") Pzw record, @Param("example") PzwExample example);

	int update(@Param("pzw") Pzw pzw, @Param("selectDate") String selectDate);
	
	Double findSumKwh(@Param("record") Pzw record ,@Param("selectDate") String selectDate);
}