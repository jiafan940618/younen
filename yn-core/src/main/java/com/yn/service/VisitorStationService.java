package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.VisitorStationDao;
import com.yn.model.VisitorStation;
import com.yn.utils.BeanCopy;

@Service
public class VisitorStationService {
	
	 @Autowired
	 VisitorStationDao visitorStationDao;
	
	 
	 public VisitorStation findOne(Long id) {
	        return visitorStationDao.findOne(id);
	    }
	 
	/* public VisitorStation findVisitorStation(Long userId){
		 
		 
		return visitorStationDao.findVisitorStation(userId); 
	 }*/

	    public void save(VisitorStation news) {
	        if(news.getId()!=null){
	        	VisitorStation one = visitorStationDao.findOne(news.getId());
	            try {
	                BeanCopy.beanCopy(news,one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            visitorStationDao.save(one);
	        }else {
	        	visitorStationDao.save(news);
	        }
	    }
	    
	    /** 叠加电站id*/
	   public String getList(List<Long> list){
		   
		  String ids = new String();
		   
		   if(0 < list.size()){

			   for (Long long1 : list) {
				ids += long1+",";
			}   
		   }
		   
		 return ids;
	   } 
	
	
	

}
