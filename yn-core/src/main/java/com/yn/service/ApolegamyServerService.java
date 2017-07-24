package com.yn.service;

import com.yn.dao.ApolegamyServerDao;
import com.yn.model.ApolegamyServer;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApolegamyServerService {
    @Autowired
    ApolegamyServerDao apolegamyServerDao;

    public ApolegamyServer findOne(Long id) {
        return apolegamyServerDao.findOne(id);
    }

    public void save(ApolegamyServer apolegamyServer) {
        if(apolegamyServer.getId()!=null){
        	ApolegamyServer one = apolegamyServerDao.findOne(apolegamyServer.getId());
            try {
                BeanCopy.beanCopy(apolegamyServer,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            apolegamyServerDao.save(one);
        }else {
            apolegamyServerDao.save(apolegamyServer);
        }
    }

    public void delete(Long id) {
        apolegamyServerDao.delete(id);
    }
    
    public void deleteBatch(List<Long> id) {
		apolegamyServerDao.deleteBatch(id);
	}

    public ApolegamyServer findOne(ApolegamyServer apolegamyServer) {
        Specification<ApolegamyServer> spec = RepositoryUtil.getSpecification(apolegamyServer);
        ApolegamyServer findOne = apolegamyServerDao.findOne(spec);
        return findOne;
    }

    public List<ApolegamyServer> findAll(List<Long> list) {
        return apolegamyServerDao.findAll(list);
    }

    public Page<ApolegamyServer> findAll(ApolegamyServer apolegamyServer, Pageable pageable) {
        Specification<ApolegamyServer> spec = RepositoryUtil.getSpecification(apolegamyServer);
        Page<ApolegamyServer> findAll = apolegamyServerDao.findAll(spec, pageable);
        return findAll;
    }

    public List<ApolegamyServer> findAll(ApolegamyServer apolegamyServer) {
        Specification<ApolegamyServer> spec = RepositoryUtil.getSpecification(apolegamyServer);
        return apolegamyServerDao.findAll(spec);
    }
}
