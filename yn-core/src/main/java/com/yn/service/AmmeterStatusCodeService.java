package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.AmmeterStatusCodeDao;
import com.yn.model.AmmeterStatusCode;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class AmmeterStatusCodeService {
    @Autowired
    AmmeterStatusCodeDao ammeterStatusCodeDao;

    public AmmeterStatusCode findOne(Long id) {
        return ammeterStatusCodeDao.findOne(id);
    }

    public void save(AmmeterStatusCode ammeterStatusCode) {
        if(ammeterStatusCode.getId()!=null){
        	AmmeterStatusCode one = ammeterStatusCodeDao.findOne(ammeterStatusCode.getId());
            try {
                BeanCopy.beanCopy(ammeterStatusCode,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ammeterStatusCodeDao.save(one);
        }else {
            ammeterStatusCodeDao.save(ammeterStatusCode);
        }
    }

    public void delete(Long id) {
        ammeterStatusCodeDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		ammeterStatusCodeDao.deleteBatch(id);
	}

    public AmmeterStatusCode findOne(AmmeterStatusCode ammeterStatusCode) {
        Specification<AmmeterStatusCode> spec = RepositoryUtil.getSpecification(ammeterStatusCode);
        AmmeterStatusCode findOne = ammeterStatusCodeDao.findOne(spec);
        return findOne;
    }

    public List<AmmeterStatusCode> findAll(List<Long> list) {
        return ammeterStatusCodeDao.findAll(list);
    }

    public Page<AmmeterStatusCode> findAll(AmmeterStatusCode ammeterStatusCode, Pageable pageable) {
        Specification<AmmeterStatusCode> spec = RepositoryUtil.getSpecification(ammeterStatusCode);
        Page<AmmeterStatusCode> findAll = ammeterStatusCodeDao.findAll(spec, pageable);
        return findAll;
    }

    public List<AmmeterStatusCode> findAll(AmmeterStatusCode ammeterStatusCode) {
        Specification<AmmeterStatusCode> spec = RepositoryUtil.getSpecification(ammeterStatusCode);
        return ammeterStatusCodeDao.findAll(spec);
    }
    
    public AmmeterStatusCode findByStatusCode(String statusCode) {
    	AmmeterStatusCode ammeterStatusCode = new AmmeterStatusCode();
    	ammeterStatusCode.setStatusCode(statusCode);
    	ammeterStatusCode.setDel(0);
    	AmmeterStatusCode findOne = ammeterStatusCodeDao.findOne(Example.of(ammeterStatusCode));
        return findOne;
    }
}
