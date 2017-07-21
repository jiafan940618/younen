package com.yn.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.ServerDao;
import com.yn.model.Server;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.utils.RepositoryUtil;

@Service
public class ServerService {
	@Autowired
	ServerDao serverDao;

	public Server findOne(Long id) {
		return serverDao.findOne(id);
	}

	public void save(Server server) {
		if (server.getId() != null) {
			Server one = serverDao.findOne(server.getId());
			try {
				BeanCopy.beanCopy(server, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			serverDao.save(one);
		} else {
			serverDao.save(server);
		}
		System.out.println();
	}

	public void delete(Long id) {
		serverDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		serverDao.deleteBatch(id);
	}

	public Server findOne(Server server) {
		Specification<Server> spec = getSpecification(server);
		Server findOne = serverDao.findOne(spec);
		return findOne;
	}

	public List<Server> findAll(List<Long> list) {
		return serverDao.findAll(list);
	}

	public Page<Server> findAll(Server server, Pageable pageable) {
		Specification<Server> spec = getSpecification(server);
		Page<Server> findAll = serverDao.findAll(spec, pageable);
		return findAll;
	}

	public List<Server> findAll(Server server) {
		Specification<Server> spec = RepositoryUtil.getSpecification(server);
		return serverDao.findAll(spec);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<Server> getSpecification(Server server) {
		server.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(server);
		return (Root<Server> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

			Predicate conjunction = cb.conjunction();
			List<Expression<Boolean>> expressions = conjunction.getExpressions();
			Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				if (!entry.getKey().equals("query") && !entry.getKey().equals("queryStartDtm") && !entry.getKey().equals("queryEndDtm")) {
					Object value = entry.getValue();
					if (value instanceof Map) {
						Iterator<Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
						while (iterator1.hasNext()) {
							Entry<String, Object> entry1 = iterator1.next();
							expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
						}
					} else {
						expressions.add(cb.equal(root.get(entry.getKey()), value));
					}
				}
			}

			// 根据【项目负责人/业务员电话/服务商名称】搜索
			String queryStr = server.getQuery();
			if (!StringUtils.isEmpty(queryStr)) {
				Predicate[] predicates = new Predicate[3];
				predicates[0] = cb.like(root.get("companyName"), "%" + queryStr + "%");
				predicates[1] = cb.like(root.get("personInCharge"), "%" + queryStr + "%");
				predicates[2] = cb.like(root.get("salesmanPhone"), "%" + queryStr + "%");
				expressions.add(cb.or(predicates));
			}
			
			// 根据日期筛选
			String queryStartDtm = server.getQueryStartDtm();
			String queryEndDtm = server.getQueryEndDtm();
			if (!StringUtils.isEmpty(queryStartDtm)) {
				expressions.add(cb.greaterThanOrEqualTo(root.get("createDtm"), DateUtil.parseString(queryStartDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}
			if (!StringUtils.isEmpty(queryEndDtm)) {
                expressions.add(cb.lessThan(root.get("createDtm"), DateUtil.parseString(queryEndDtm, DateUtil.yyyy_MM_dd_HHmmss)));
			}

			return conjunction;
		};
	}
	
}
