package com.yn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yn.dao.WxInfomationDao;
import com.yn.model.WxInfomation;
import com.yn.utils.BeanCopy;

@Service
public class WxInfomationService {
	
	@Autowired
	WxInfomationDao wxInfomationDao;

    public WxInfomation findOne(Long id) {
        return wxInfomationDao.findOne(id);
    }

    public void save(WxInfomation news) {
        if(news.getId()!=null){
        	WxInfomation one = wxInfomationDao.findOne(news.getId());
            try {
                BeanCopy.beanCopy(news,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            wxInfomationDao.save(one);
        }else {
        	wxInfomationDao.save(news);
        }
    }
	
	

}
