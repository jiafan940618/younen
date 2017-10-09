package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.RoleDao;
import com.yn.model.Role;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;

    public Role findOne(Long id) {
        return roleDao.findOne(id);
    }

    public void save(Role role) {
        if(role.getId()!=null){
        	Role one = roleDao.findOne(role.getId());
            try {
                BeanCopy.beanCopy(role,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            roleDao.save(one);
        }else {
            roleDao.save(role);
        }
    }

    public void delete(Long id) {
        roleDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		roleDao.deleteBatch(id);
	}

    public Role findOne(Role role) {
        Specification<Role> spec = RepositoryUtil.getSpecification(role);
        Role findOne = roleDao.findOne(spec);
        return findOne;
    }

    public List<Role> findAll(List<Long> list) {
        return roleDao.findAll(list);
    }

    public Page<Role> findAll(Role role, Pageable pageable) {
        Specification<Role> spec = RepositoryUtil.getSpecification(role);
        Page<Role> findAll = roleDao.findAll(spec, pageable);
        return findAll;
    }

    public List<Role> findAll(Role role) {
        Specification<Role> spec = RepositoryUtil.getSpecification(role);
        return roleDao.findAll(spec);
    }
}
