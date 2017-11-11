package com.yn.dao;

import com.yn.model.PzwDataSource;
import com.yn.model.PzwDataSourceExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PzwDataSourceMapper {
    int countByExample(PzwDataSourceExample example);

    int deleteByExample(PzwDataSourceExample example);

    int deleteByPrimaryKey(String amPhaseRecordId);

    int insert(PzwDataSource record);

    int insertSelective(PzwDataSource record);

    List<PzwDataSource> selectByExample(PzwDataSourceExample example);

    PzwDataSource selectByPrimaryKey(String amPhaseRecordId);

    int updateByExampleSelective(@Param("record") PzwDataSource record, @Param("example") PzwDataSourceExample example);

    int updateByExample(@Param("record") PzwDataSource record, @Param("example") PzwDataSourceExample example);

    int updateByPrimaryKeySelective(PzwDataSource record);

    int updateByPrimaryKey(PzwDataSource record);

	PzwDataSource selectAllByDate(@Param("selectDate") String selectDate,@Param("orderCause") String orderCause);
}