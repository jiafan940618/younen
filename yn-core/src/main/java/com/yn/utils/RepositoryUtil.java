package com.yn.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.yn.domain.IQuery;
import com.yn.domain.ISuperModel;


public class RepositoryUtil {

	public static <T> Specification<T> getSpecification(Object t, String queryText) {
		// t.setDel(0);
		if (t instanceof ISuperModel) {
			ISuperModel new_name = (ISuperModel) t;
			new_name.setDel(0);
		}
		Map<String, Object> objectMap = ObjToMap.getObjectMap(t);
		Specification specification = new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate conjunction = cb.conjunction();
				List<Expression<Boolean>> expressions = conjunction.getExpressions();
				Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();
				if (query != null && !query.equals("")) {
					List<String> queryList = ObjToMap.getQueryList(t);
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						if (!StringUtils.isEmpty(entry.getValue())) {

							if (queryList.size() > 0) {
								Predicate[] predicates = new Predicate[queryList.size()];
								for (int i = 0; i < queryList.size(); i++) {
									predicates[i] = cb.like(root.get(queryList.get(i)), "%" + queryText + "%");
								}
								expressions.add(cb.or(predicates));
							}
						}
					}
				} else {
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						expressions.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
					}
				}
				return conjunction;
			}
		};
		return specification;
	}
	/**
	 * 获取子对象的模糊查询
	 * @param t
	 * @return
	 */
	public static <T> Specification<T> getSpecification(ISuperModel t) {
        t.setDel(0);
        Map<String, Object> objectMap = ObjToMap.getObjectMap(t);
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate conjunction = cb.conjunction();
            List<Expression<Boolean>> expressions = conjunction.getExpressions();
            Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if(entry.getKey().equals("query")){
                    if(!StringUtils.isEmpty(entry.getValue())){
                        List<String> queryList = ObjToMap.getQueryList(t);
                        if(queryList.size()>0) {
                            Predicate[] predicates = new Predicate[queryList.size()];
                            for (int i = 0; i < queryList.size(); i++) {
                                predicates[i] = cb.like(root.get(queryList.get(i)), "%" + entry.getValue() + "%");
                            }
                            expressions.add(cb.or(predicates));
                        }
                    }
                }else {
                    Object value = entry.getValue();
                    if (value instanceof Map) {
                        Iterator<Entry<String, Object>> iterator1 = ((Map) value).entrySet().iterator();
                        while (iterator1.hasNext()) {
                            Entry<String, Object> entry1 = iterator1.next();
                            expressions.add(cb.equal(root.get(entry.getKey()).get(entry1.getKey()), entry1.getValue()));
                        }
                    }else {
                      expressions.add(cb.equal(root.get(entry.getKey()), value));
                    }
                }
            }
            return conjunction;
        };
    }

	/**
	 * 获取查询
	 * @param t
	 * @return
	 */
	public static <T> Specification<T> getSpecification(Object t) {
		// t.setDel(0);
		if (t instanceof ISuperModel) {
			ISuperModel new_name = (ISuperModel) t;
			new_name.setDel(0);
		}
		
		Map<String, Object> objectMap = ObjToMap.getObjectMap(t);
		Specification specification = new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				String queryText=null;
				if (t instanceof IQuery) {
					IQuery new_name = (ISuperModel) t;
					queryText = new_name.getQuery();
				}
				Predicate conjunction = cb.conjunction();
				List<Expression<Boolean>> expressions = conjunction.getExpressions();
				Iterator<Entry<String, Object>> iterator = objectMap.entrySet().iterator();
				
				if (!StringUtils.isEmpty(queryText)) {
					//获取搜索字段
					List<String> queryList = ObjToMap.getQueryList(t);
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						if (!StringUtils.isEmpty(entry.getValue())) {
							if (queryList.size() > 0) {
								Predicate[] predicates = new Predicate[queryList.size()];
								for (int i = 0; i < queryList.size(); i++) {
									predicates[i] = cb.like(root.get(queryList.get(i)), "%" + queryText + "%");
								}
								expressions.add(cb.or(predicates));
							}
						}
					}
				} else {
					while (iterator.hasNext()) {
						Entry<String, Object> entry = iterator.next();
						expressions.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
					}
				}

				return conjunction;
			}
		};
		return specification;
	}
}
