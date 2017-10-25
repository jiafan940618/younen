package com.yn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.mapper.ElecDataMonthMapper;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataMonthExample;
import com.yn.model.ElecDataMonthExample.Criteria;

@Service
public class ElecDataMonthService {

	@Autowired
	private ElecDataMonthMapper elecDataMonthMapper;


	/**
	 * 
	    * @Title: findByCondition
	    * @Description: TODO(根据下面的拼接条件查询出单个对象。)
	    * @param @param elecDataMonth
	    * @param @return    参数
	    * @return ElecDataMonth    返回类型
	    * @throws
	 */
	public List<ElecDataMonth> findByCondition(ElecDataMonth elecDataMonth) {
		ElecDataMonthExample example = new ElecDataMonthExample();
		Criteria criteria = example.createCriteria();
		criteria.andDAddrEqualTo(elecDataMonth.getdAddr());
		// criteria.andDTypeEqualTo(elecDataMonth.getdType());
		// criteria.andWAddrEqualTo(elecDataMonth.getwAddr());
		// criteria.andDevConfCodeEqualTo(elecDataMonth.getDevConfCode());
		criteria.andAmmeterCodeEqualTo(elecDataMonth.getAmmeterCode());
		criteria.andRecordTimeEqualTo(elecDataMonth.getRecordTime());
		return elecDataMonthMapper.selectByExample(example);
	}

	public boolean updateByExampleSelective(ElecDataMonth elecDataMonth) {
		ElecDataMonthExample example = new ElecDataMonthExample();
		Criteria criteria = example.createCriteria();
		criteria.andAmmeterCodeEqualTo(elecDataMonth.getAmmeterCode());
		criteria.andRecordTimeEqualTo(elecDataMonth.getRecordTime());
		int i = elecDataMonthMapper.updateByExampleSelective(elecDataMonth, example);
		return i > 0;
	}

	public boolean saveByMapper(ElecDataMonth elecDataMonth) {
		int insert = elecDataMonthMapper.insert(elecDataMonth);
		return insert > 0;
	}
}
