package com.yn.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yn.dao.ServerDao;
import com.yn.vo.QualificationsVo;
import com.yn.vo.SolarPanelVo;

@Service
public class SolarPanelSerice {
	
	@Autowired
	private ServerDao serverdao;
	
	
	
	  public  List<Object> findObject( Integer start, Integer limit){
	    	
			return serverdao.findObject(start, limit);
	    }

	    public List<Object> findtwoObject(String CityName ,Integer start, Integer limit){
		    	
				return serverdao.findtwoObject(CityName, start, limit);
		  }
	    
	    public   List<Object> findquatwoObject(List<Long> ids){
	    	
			return serverdao.findquatwoObject(ids);
	    	
	    }
	    
	    public  List<Object> findquaObject(List<Long> ids){
	    	
	    	return serverdao.findquaObject(ids);
	    }
	    
	    
	    /** 处理数据*/
	    public List<SolarPanelVo> getpanel(List<Object> list){
	    	
	    	List<SolarPanelVo> listsolar = new ArrayList<SolarPanelVo>();
	    	
	    	for (Object obj : list) {
	    		SolarPanelVo solarPanelVo = new SolarPanelVo();
				Object[] object = (Object[])obj;
				
				Integer s_id =(Integer)object[0];
				String companyName =(String)object[1];
				Integer type =(Integer)object[2];
				
				String typeName = type == 1 ? "单晶硅":"多晶硅";
				
				Integer conversionEfficiency =(Integer)object[3];
				//qualityAssurance
				
					BigDecimal quality =(BigDecimal)object[4];
					if(null != quality){
					Double qualityAssurance = quality.doubleValue();
					//boardYear
					BigDecimal board =(BigDecimal)object[5];
					Double boardYear = board.doubleValue();
					String companyLogo =(String)object[6];
					
					solarPanelVo.setS_id(s_id.longValue());
					solarPanelVo.setCompanyName(companyName);
					solarPanelVo.setType(type);
					solarPanelVo.setConversionEfficiency(conversionEfficiency);
					solarPanelVo.setQualityAssurance(qualityAssurance);
					solarPanelVo.setBoardYear(boardYear);
					solarPanelVo.setCompanyLogo(companyLogo);
					solarPanelVo.setTypeName(typeName);
					
					listsolar.add(solarPanelVo);
				}
			}

			return listsolar;
	    }
	
	    /** 处理资质*/
	    public List<QualificationsVo> getqua(List<Object> list){
	    	
	    	List<QualificationsVo> listqua = new ArrayList<QualificationsVo>();
	    	
	    	for (Object obj : list) {
				
	    		QualificationsVo quali = new QualificationsVo();
	    		Object[] object = (Object[])obj;
	    		
	    		Integer id =(Integer)object[0];
	    		
		    		String imgUrl =(String)object[1];
		    		if(null !=imgUrl && !imgUrl.equals("")){
		    		String text =(String)object[2];
		    		
		    		quali.setId(id.longValue());
		    		quali.setImgUrl(imgUrl);
		    		quali.setText(text);
		    		
		    		listqua.add(quali);
	    		}
			}
	    	
			return listqua;
	    }
}
