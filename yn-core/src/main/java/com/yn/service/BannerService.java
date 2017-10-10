package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.BannerDao;
import com.yn.model.Banner;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class BannerService {
    @Autowired
    BannerDao bannerDao;

    public Banner findOne(Long id) {
        return bannerDao.findOne(id);
    }

    public void save(Banner banner) {
        if(banner.getId()!=null){
        	Banner one = bannerDao.findOne(banner.getId());
            try {
                BeanCopy.beanCopy(banner,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bannerDao.save(one);
        }else {
            bannerDao.save(banner);
        }
    }

    public void delete(Long id) {
        bannerDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		bannerDao.deleteBatch(id);
	}

    public Banner findOne(Banner banner) {
        Specification<Banner> spec = RepositoryUtil.getSpecification(banner);
        Banner findOne = bannerDao.findOne(spec);
        return findOne;
    }

    public List<Banner> findAll(List<Long> list) {
        return bannerDao.findAll(list);
    }

    public Page<Banner> findAll(Banner banner, Pageable pageable) {
        Specification<Banner> spec = RepositoryUtil.getSpecification(banner);
        Page<Banner> findAll = bannerDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Banner> findAll(Banner banner) {
        Specification<Banner> spec = RepositoryUtil.getSpecification(banner);
        return bannerDao.findAll(spec);
    }
    
	 public List<Banner> selectBanner(){
	    	
			return bannerDao.selectBanner();
	    }
}
