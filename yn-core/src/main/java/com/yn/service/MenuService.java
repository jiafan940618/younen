package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.MenuDao;
import com.yn.model.Menu;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class MenuService {
    @Autowired
    MenuDao menuDao;

    public Menu findOne(Long id) {
        return menuDao.findOne(id);
    }

    public void save(Menu menu) {
        if(menu.getId()!=null){
        	Menu one = menuDao.findOne(menu.getId());
            try {
                BeanCopy.beanCopy(menu,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuDao.save(one);
        }else {
            menuDao.save(menu);
        }
    }

    public void delete(Long id) {
        menuDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		menuDao.deleteBatch(id);
	}

    public Menu findOne(Menu menu) {
        Specification<Menu> spec = RepositoryUtil.getSpecification(menu);
        Menu findOne = menuDao.findOne(spec);
        return findOne;
    }

    public List<Menu> findAll(List<Long> list) {
        return menuDao.findAll(list);
    }

    public Page<Menu> findAll(Menu menu, Pageable pageable) {
        Specification<Menu> spec = RepositoryUtil.getSpecification(menu);
        Page<Menu> findAll = menuDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Menu> findAll(Menu menu) {
        Specification<Menu> spec = RepositoryUtil.getSpecification(menu);
        return menuDao.findAll(spec);
    }
    
    public List<Menu> findAll2(Menu menu) {
//    	menuDao.findAll(Example.of(menu));
        Specification<Menu> spec = RepositoryUtil.getSpecification(menu);
        return menuDao.findAll(spec);
    }
}
