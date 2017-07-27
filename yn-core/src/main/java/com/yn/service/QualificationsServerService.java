package com.yn.service;

import com.yn.dao.QualificationsServerDao;
import com.yn.enums.DeleteEnum;
import com.yn.model.Apolegamy;
import com.yn.model.ApolegamyServer;
import com.yn.model.QualificationsServer;
import com.yn.utils.BeanCopy;
import com.yn.utils.RepositoryUtil;
import com.yn.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QualificationsServerService {
    @Autowired
    QualificationsServerDao qualificationsServerDao;
    @Autowired
    ApolegamyServerService apolegamyServerService;
    @Autowired
    ApolegamyService apolegamyService;

    public QualificationsServer findOne(Long id) {
        return qualificationsServerDao.findOne(id);
    }

    public void save(QualificationsServer qualificationsServer) {
        if(qualificationsServer.getId()!=null){
        	QualificationsServer one = qualificationsServerDao.findOne(qualificationsServer.getId());
            try {
                BeanCopy.beanCopy(qualificationsServer,one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            qualificationsServerDao.save(one);
        }else {
            qualificationsServerDao.save(qualificationsServer);
        }
    }

    public void delete(Long id) {
        // 1.删除服务商的资质
        qualificationsServerDao.delete(id);

        QualificationsServer one = qualificationsServerDao.findOne(id);
        // 2.查找资质的选配项目（apolegamy）
        Apolegamy apolegamyE = new Apolegamy();
        apolegamyE.setQualificationsId(one.getQualificationsId());
        List<Apolegamy> apolegamyList = apolegamyService.findAll(apolegamyE);
        // 3.删除务商的资质的选配项目
        for (Apolegamy apolegamy : apolegamyList) {
            ApolegamyServer apolegamyServerE = new ApolegamyServer();
            apolegamyServerE.setApolegamyId(apolegamy.getId());
            List<ApolegamyServer> apolegamyServerList = apolegamyServerService.findAll(apolegamyServerE);
            for (ApolegamyServer apolegamyServer : apolegamyServerList) {
                apolegamyServerService.delete(apolegamyServer.getId());
            }
        }
    }

    public void deleteBatch(List<Long> id) {
		qualificationsServerDao.deleteBatch(id);
	}

    public QualificationsServer findOne(QualificationsServer qualificationsServer) {
        Specification<QualificationsServer> spec = RepositoryUtil.getSpecification(qualificationsServer);
        QualificationsServer findOne = qualificationsServerDao.findOne(spec);
        return findOne;
    }

    public List<QualificationsServer> findAll(List<Long> list) {
        return qualificationsServerDao.findAll(list);
    }

    public Page<QualificationsServer> findAll(QualificationsServer qualificationsServer, Pageable pageable) {
        Specification<QualificationsServer> spec = RepositoryUtil.getSpecification(qualificationsServer);
        Page<QualificationsServer> findAll = qualificationsServerDao.findAll(spec, pageable);
        return findAll;
    }

    public List<QualificationsServer> findAll(QualificationsServer qualificationsServer) {
        Specification<QualificationsServer> spec = RepositoryUtil.getSpecification(qualificationsServer);
        return qualificationsServerDao.findAll(spec);
    }

    @Transactional
    public void saveBatch(String qualificationsIds, Long serverId) {
        if (!StringUtil.isEmpty(qualificationsIds)) {
            // 有资质id
            String[] split = qualificationsIds.split(",");
            List<Long> qualificationsIdList = Arrays.stream(split).map(e -> Long.valueOf(e)).collect(Collectors.toList());

            // 删除不需要的资质
            QualificationsServer qualificationsServerE = new QualificationsServer();
            qualificationsServerE.setDel(0);
            qualificationsServerE.setServerId(serverId);
            List<QualificationsServer> qualificationsServerList = qualificationsServerDao.findAll(Example.of(qualificationsServerE));
            for (QualificationsServer qualificationsServer : qualificationsServerList) {
                if (!qualificationsIdList.contains(qualificationsServer.getQualificationsId())) {
                    delete(qualificationsServer.getId());
                }
            }

            // 添加服务商资质
            for (Long qulificationsId : qualificationsIdList) {
                QualificationsServer qualificationsServerE1 = new QualificationsServer();
                qualificationsServerE1.setQualificationsId(qulificationsId);
                qualificationsServerE1.setServerId(serverId);
                QualificationsServer qualificationsServer = findOne(qualificationsServerE1);
                if (qualificationsServer == null) {
                    QualificationsServer qualificationsServer1 = new QualificationsServer();
                    qualificationsServer1.setQualificationsId(qulificationsId);
                    qualificationsServer1.setServerId(serverId);
                    qualificationsServerDao.save(qualificationsServer1);
                }
            }
        } else {
            // 没有资质id
            QualificationsServer qualificationsServerE = new QualificationsServer();
            qualificationsServerE.setServerId(serverId);
            qualificationsServerE.setDel(0);
            List<QualificationsServer> qualificationsServerList = qualificationsServerDao.findAll(Example.of(qualificationsServerE));
            for (QualificationsServer qualificationsServer : qualificationsServerList) {
                delete(qualificationsServer.getId());
            }
        }
    }
}
