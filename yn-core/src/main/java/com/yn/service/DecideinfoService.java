package com.yn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.DecideinfoDao;
import com.yn.model.Decideinfo;
import com.yn.utils.BeanCopy;

@Service
public class DecideinfoService {
	
	@Autowired
	DecideinfoDao decideinfoDao;
	
	  public Decideinfo findOne(Long id) {
	        return decideinfoDao.findOne(id);
	    }
	
	 public void save(Decideinfo decideinfo) {
	        if(decideinfo.getId()!=null){
	        	Decideinfo one = decideinfoDao.findOne(decideinfo.getId());
	            try {
	                BeanCopy.beanCopy(decideinfo,one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            decideinfoDao.save(one);
	        }else {
	        	decideinfoDao.save(decideinfo);
	        }
	    }
	
	

}
