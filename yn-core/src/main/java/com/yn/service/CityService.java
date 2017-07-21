package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.CityDao;
import com.yn.model.City;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class CityService {
    @Autowired
    CityDao cityDao;

    public City findOne(Long id) {
        return cityDao.findOne(id);
    }

    public void save(City city) {
        if(city.getId()!=null){
        	City one = cityDao.findOne(city.getId());
            try {
                BeanCopy.beanCopy(city,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cityDao.save(one);
        }else {
            cityDao.save(city);
        }
    }

    public void delete(Long id) {
        cityDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		cityDao.deleteBatch(id);
	}

    public City findOne(City city) {
        Specification<City> spec = RepositoryUtil.getSpecification(city);
        City findOne = cityDao.findOne(spec);
        return findOne;
    }

    public List<City> findAll(List<Long> list) {
        return cityDao.findAll(list);
    }

    public Page<City> findAll(City city, Pageable pageable) {
        Specification<City> spec = RepositoryUtil.getSpecification(city);
        Page<City> findAll = cityDao.findAll(spec, pageable);
        return findAll;
    }

    public List<City> findAll(City city) {
        Specification<City> spec = RepositoryUtil.getSpecification(city);
        return cityDao.findAll(spec);
    }
}
