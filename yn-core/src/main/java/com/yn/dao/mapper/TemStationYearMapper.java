package com.yn.dao.mapper;

import com.yn.model.TemStationYear;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface TemStationYearMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(TemStationYear record);

    int insertSelective(TemStationYear record);


    TemStationYear selectByPrimaryKey(Integer id);


    TemStationYear findHuanbao(Map<String, Object> map);
    
    int updateByPrimaryKeySelective(TemStationYear record);

    int updateByPrimaryKey(TemStationYear record);
    
    List<TemStationYear> selectByQuery(TemStationYear temStationYear);
}