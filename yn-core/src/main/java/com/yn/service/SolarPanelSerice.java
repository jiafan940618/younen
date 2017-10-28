package com.yn.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.yn.dao.ServerDao;
import com.yn.dao.SolarPanelDao;
import com.yn.model.Server;
import com.yn.model.SolarPanel;
import com.yn.vo.QualificationsVo;
import com.yn.vo.SolarPanelVo;

@Service
public class SolarPanelSerice {
	
	@Autowired
	private ServerDao serverdao;
	
	@Autowired
	private SolarPanelDao solarPanelDao;
	
	  public  List<Object> findObject(com.yn.model.Page<Server> page){
	    	
			return serverdao.findObject(page);
	    }

	    public List<Object> findtwoObject(com.yn.model.Page<Server> page){
		    	
				return serverdao.findtwoObject(page);
		  }
	    
	    public   List<Object> findquatwoObject(List<Long> ids){
	    	
			return serverdao.findquatwoObject(ids);
	    	
	    }
	    
	    public  List<Object> findquaObject(String cityName,List<Long> ids){
	    	
	    	return serverdao.findquaObject(cityName,ids);
	    }
	    
	    public List<SolarPanel> getsolar(){
	    	
	    	List<SolarPanel> solarList = new LinkedList<SolarPanel>();
	    	
	    	List<Object> list =solarPanelDao.getsolar();
	    	
	    	for (Object object : list) {
	    		SolarPanel solarPanel = new SolarPanel();
				Object[] obj =(Object[])object;
				
				Integer brandId =(Integer)obj[0];
				String brandName =(String)obj[0];
				
				solarPanel.setBrandId(brandId);
				solarPanel.setBrandName(brandName);
				solarList.add(solarPanel);
			}
	    	
	    	return	solarList;
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

					BigDecimal quality =(BigDecimal)object[4];

					if(null != quality){
					Double qualityAssurance = quality.doubleValue();
					//boardYear
					BigDecimal board =(BigDecimal)object[5];
					Double boardYear = board.doubleValue();
					String companyLogo =(String)object[6];
					
					Integer p_id = (Integer)object[7];
					
					solarPanelVo.setS_id(s_id.longValue());
					solarPanelVo.setCompanyName(companyName);
					solarPanelVo.setType(type);
					solarPanelVo.setConversionEfficiency(conversionEfficiency);
					solarPanelVo.setQualityAssurance(qualityAssurance);
					solarPanelVo.setBoardYear(boardYear);
					solarPanelVo.setCompanyLogo(companyLogo);
					solarPanelVo.setTypeName(typeName);
					solarPanelVo.setId(p_id);
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
	 
	    
	    public  List<SolarPanel> selectSolarPanel(Long brandId){
	    	
	    	int brand_id = brandId.intValue();
	    	
			return solarPanelDao.selectSolarPanel(brand_id);
	    }
   
	    
	    
}
