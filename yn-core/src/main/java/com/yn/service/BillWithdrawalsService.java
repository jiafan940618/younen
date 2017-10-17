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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yn.dao.BillWithdrawalsDao;
import com.yn.model.BillWithdrawals;
import com.yn.utils.BeanCopy;
import com.yn.utils.DateUtil;
import com.yn.utils.ObjToMap;
import com.yn.vo.BillWithdrawalsVo;

@Service
public class BillWithdrawalsService {
	@Autowired
	BillWithdrawalsDao billWithdrawalsDao;

	public BillWithdrawals findOne(Long id) {
		return billWithdrawalsDao.findOne(id);
	}

	public void save(BillWithdrawals billWithdrawals) {
		if (billWithdrawals.getId() != null) {
			BillWithdrawals one = billWithdrawalsDao.findOne(billWithdrawals.getId());
			try {
				BeanCopy.beanCopy(billWithdrawals, one);
			} catch (Exception e) {
				e.printStackTrace();
			}
			billWithdrawalsDao.save(one);
		} else {
			billWithdrawalsDao.save(billWithdrawals);
		}
		System.out.println();
	}
	
	public BillWithdrawals findByOrderNo(String orderNo){
		
		return billWithdrawalsDao.findByOrderNo(orderNo);	
	}
	
	 //b.phone,b.real_name,b.bank_card_num,b.user_id,c.bank_no,c.bank_name,w.id w_id
	public BillWithdrawalsVo selWithdrawal(String treatyId){
		
	Object[] object	= (Object[])billWithdrawalsDao.selWithdrawal(treatyId);
	
	String phone = (String) object[0];
	String realName = (String) object[1];
	String bankCardNum = (String) object[2];
	Integer userId = (Integer) object[3];
	String bankNo = (String) object[4];
	String bankName = (String) object[5];
	Integer walletId = (Integer) object[6];
	
	BillWithdrawalsVo billWithdrawalsVo = new BillWithdrawalsVo();
	billWithdrawalsVo.setPhone(phone);
	billWithdrawalsVo.setRealName(realName);
	billWithdrawalsVo.setBankCardNum(bankCardNum);
	billWithdrawalsVo.setUserId(userId.longValue());
	billWithdrawalsVo.setBankName(bankName);
	billWithdrawalsVo.setWalletId(walletId.longValue());
	billWithdrawalsVo.setBankNo(bankNo);
		return billWithdrawalsVo;	
	}
	
	

	public void delete(Long id) {
		billWithdrawalsDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		billWithdrawalsDao.deleteBatch(id);
	}

	public BillWithdrawals findOne(BillWithdrawals billWithdrawals) {
		Specification<BillWithdrawals> spec = getSpecification(billWithdrawals);
		BillWithdrawals findOne = billWithdrawalsDao.findOne(spec);
		return findOne;
	}

	public List<BillWithdrawals> findAll(List<Long> list) {
		return billWithdrawalsDao.findAll(list);
	}

	public Page<BillWithdrawals> findAll(BillWithdrawals billWithdrawals, Pageable pageable) {
		Specification<BillWithdrawals> spec = getSpecification(billWithdrawals);
		Page<BillWithdrawals> findAll = billWithdrawalsDao.findAll(spec, pageable);
		return findAll;
	}

	public List<BillWithdrawals> findAll(BillWithdrawals billWithdrawals) {
		Specification<BillWithdrawals> spec = getSpecification(billWithdrawals);
		return billWithdrawalsDao.findAll(spec);
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Specification<BillWithdrawals> getSpecification(BillWithdrawals billWithdrawals) {
		billWithdrawals.setDel(0);
		Map<String, Object> objectMap = ObjToMap.getObjectMap(billWithdrawals);
		return (Root<BillWithdrawals> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

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

			// 根据交易单号，用户
			String queryStr = billWithdrawals.getQuery();
			if (!StringUtils.isEmpty(queryStr)) {
				Predicate[] predicates = new Predicate[2];
				predicates[0] = cb.like(root.get("tradeNo"), "%" + queryStr + "%");
				predicates[1] = cb.like(root.get("user").get("userName"), "%" + queryStr + "%");
				expressions.add(cb.or(predicates));
			}
			
			// 根据日期筛选
			String queryStartDtm = billWithdrawals.getQueryStartDtm();
			String queryEndDtm = billWithdrawals.getQueryEndDtm();
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
