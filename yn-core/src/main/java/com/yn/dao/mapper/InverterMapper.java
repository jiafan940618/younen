package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.yn.model.Inverter;
import com.yn.model.Page;

@Mapper
public interface InverterMapper {
	
	List<Inverter> getInverter(Page<Inverter> page);
	
	List<Inverter> getCount(Page<Inverter> page);

}
