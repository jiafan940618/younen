package com.yn.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yn.model.Page;
import com.yn.model.SolarPanel;

@Mapper
public interface SolarPanelMapper {
	
	List<SolarPanel> getSolarPanel(Page<SolarPanel> page);
	
	List<SolarPanel> getCount(Page<SolarPanel> page);

}
