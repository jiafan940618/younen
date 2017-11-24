package com.yn.service;

import java.util.List;

import com.yn.model.ApolegamyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.ApolegamyDao;
import com.yn.dao.mapper.ApolegamyMapper;
import com.yn.model.Apolegamy;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;

@Service
public class ApolegamyService {
	@Autowired
	ApolegamyDao apolegamyDao;
	@Autowired
    ApolegamyServerService apolegamyServerService;
	@Autowired
	ApolegamyMapper apolegamyMapper;

	public Apolegamy findOne(Long id) {
		return apolegamyDao.findOne(id);
	}
	
	/** 根据服务商id显示分页数据*/
	public	List<Apolegamy> getPage(com.yn.model.Page<Apolegamy> page){

		return apolegamyMapper.getPage(page);
	}

	/** 找到查询数量*/
	public int getCount(com.yn.model.Page<Apolegamy> page){
		
		
		return apolegamyMapper.getCount(page);	
	}
	

	public void save(Apolegamy apolegamy) {
		if (apolegamy.getId() != null) {
			Apolegamy one = apolegamyDao.findOne(apolegamy.getId());
			try {
				BeanCopy.beanCopy(apolegamy, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			apolegamyDao.save(one);
		} else {
			apolegamyDao.save(apolegamy);
		}
		
	}

	public void delete(Long id) {
	    // 删除优能选配项目
		apolegamyDao.delete(id);

		// 删除服务商的选配项目
        Apolegamy apolegamy = apolegamyDao.findOne(id);
        if (apolegamy.getDel().equals(1)) {
            ApolegamyServer apolegamyServer = new ApolegamyServer();
            apolegamyServer.setApolegamyId(id);
            List<ApolegamyServer> apolegamyServerList = apolegamyServerService.findAll(apolegamyServer);
            for (ApolegamyServer apolegamyServer1 : apolegamyServerList) {
                apolegamyServerService.delete(apolegamyServer1.getId());
            }
        }
    }

	public void deleteBatch(List<Long> id) {
		apolegamyDao.deleteBatch(id);
	}

	public Apolegamy findOne(Apolegamy apolegamy) {
		Specification<Apolegamy> spec = RepositoryUtil.getSpecification(apolegamy);
		Apolegamy findOne = apolegamyDao.findOne(spec);
		return findOne;
	}

	public List<Apolegamy> findAll(List<Long> list) {
		return apolegamyDao.findAll(list);
	}

	public Page<Apolegamy> findAll(Apolegamy apolegamy, Pageable pageable) {
		Specification<Apolegamy> spec = RepositoryUtil.getSpecification(apolegamy);
		Page<Apolegamy> findAll = apolegamyDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Apolegamy> findAll(Apolegamy apolegamy) {
		Specification<Apolegamy> spec = RepositoryUtil.getSpecification(apolegamy);
		return apolegamyDao.findAll(spec);
	} 
	
	public	List<Object> selectApo(Long serverid){
		
		return apolegamyDao.selectApo(serverid);
	}
	
	
	
}
