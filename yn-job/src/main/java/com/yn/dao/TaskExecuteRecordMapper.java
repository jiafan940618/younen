package com.yn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.TaskExecuteRecord;
import com.yn.model.TaskExecuteRecordExample;

@Mapper
public interface TaskExecuteRecordMapper {
    int countByExample(TaskExecuteRecordExample example);

    int deleteByExample(TaskExecuteRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TaskExecuteRecord record);

    int insertSelective(TaskExecuteRecord record);

    List<TaskExecuteRecord> selectByExample(TaskExecuteRecordExample example);

    TaskExecuteRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TaskExecuteRecord record, @Param("example") TaskExecuteRecordExample example);

    int updateByExample(@Param("record") TaskExecuteRecord record, @Param("example") TaskExecuteRecordExample example);

    int updateByPrimaryKeySelective(TaskExecuteRecord record);

    int updateByPrimaryKey(TaskExecuteRecord record);
    
    TaskExecuteRecord findByEndDate();
}