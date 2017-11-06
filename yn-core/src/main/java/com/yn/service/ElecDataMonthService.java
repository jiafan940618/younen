package com.yn.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.mapper.ElecDataMonthMapper;
import com.yn.model.ElecDataMonth;
import com.yn.model.ElecDataMonthExample;
import com.yn.model.ElecDataYear;
import com.yn.model.ElecDataYearExample;
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
		List<ElecDataMonth> list = selectByExample(elecDataMonth);
		ElecDataMonth edm = null;
		if(list.size()>0){
			edm = list.get(0);
			BigDecimal kwh2 = edm.getKwh()==null?BigDecimal.valueOf(0.0):edm.getKwh();
			BigDecimal kwh = elecDataMonth.getKwh()==null?BigDecimal.valueOf(0.0):elecDataMonth.getKwh();
			double totalKwh = kwh2.doubleValue()+kwh.doubleValue();
			edm.setKwh(BigDecimal.valueOf(totalKwh));
			return updateByExampleSelective(edm);
		}else{
			int insert = elecDataMonthMapper.insert(elecDataMonth);
			return insert > 0;
		}
	}
	
	public List<ElecDataMonth> selectByExample(ElecDataMonth edm) {
		ElecDataMonthExample ex = new ElecDataMonthExample();
		Criteria criteria = ex.createCriteria();
		criteria.andAmmeterCodeEqualTo(edm.getAmmeterCode());
		criteria.andTypeEqualTo(edm.getType());
		criteria.andRecordTimeEqualTo(edm.getRecordTime());
		return elecDataMonthMapper.selectByExample(ex);
	}

}
