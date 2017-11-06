package com.yn.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.mapper.ElecDataYearMapper;
import com.yn.model.ElecDataYear;
import com.yn.model.ElecDataYearExample;
import com.yn.model.ElecDataYearExample.Criteria;

@Service
public class ElecDataYearService {

	@Autowired
	private ElecDataYearMapper elecDataYearMapper;

	/**
	 * 
	    * @Title: findByCondition
	    * @Description: TODO(条件查询)
	    * @param @param elecDataYear
	    * @param @return    参数
	    * @return List<ElecDataYear>    返回类型
	    * @throws
	 */
	public List<ElecDataYear> findByCondition(ElecDataYear elecDataYear) {
		ElecDataYearExample example = new ElecDataYearExample();
		Criteria criteria = example.createCriteria();
		criteria.andDAddrEqualTo(elecDataYear.getdAddr());
		criteria.andAmmeterCodeEqualTo(elecDataYear.getAmmeterCode());
		criteria.andRecordTimeEqualTo(elecDataYear.getRecordTime());
		return elecDataYearMapper.selectByExample(example);
	}

	/**
	 * 
	    * @Title: updateByExampleSelective
	    * @Description: TODO(根据条件修改)
	    * @param @param elecDataYear
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	public boolean updateByExampleSelective(ElecDataYear elecDataYear) {
		ElecDataYearExample example = new ElecDataYearExample();
		Criteria criteria = example.createCriteria();
		criteria.andAmmeterCodeEqualTo(elecDataYear.getAmmeterCode());
		criteria.andRecordTimeEqualTo(elecDataYear.getRecordTime());
		int i = elecDataYearMapper.updateByExampleSelective(elecDataYear, example);
		return i > 0;
	}

	/**
	 * 
	    * @Title: saveByMapper
	    * @Description: TODO(保存)
	    * @param @param elecDataYear
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	public boolean saveByMapper(ElecDataYear elecDataYear) {
		List<ElecDataYear> dataYears = selectByExample(elecDataYear);
		ElecDataYear dataYear = null;
		if (dataYears.size()>0) {
			dataYear = dataYears.get(0);
			BigDecimal kwh2 = dataYear.getKwh()==null?BigDecimal.valueOf(0.0):dataYear.getKwh();
			BigDecimal kwh = elecDataYear.getKwh()==null?BigDecimal.valueOf(0.0):elecDataYear.getKwh();
			double totalKwh = kwh2.doubleValue()+kwh.doubleValue();
			elecDataYear.setKwh(BigDecimal.valueOf(totalKwh));
			return updateByExampleSelective(elecDataYear);
		}else{
			int insert = elecDataYearMapper.insert(elecDataYear);
			return insert > 0;
		}
	}

	public List<ElecDataYear> selectByExample(ElecDataYear edy) {
		ElecDataYearExample ex = new ElecDataYearExample();
		Criteria criteria = ex.createCriteria();
		criteria.andAmmeterCodeEqualTo(edy.getAmmeterCode());
		criteria.andTypeEqualTo(edy.getType());
		criteria.andRecordTimeEqualTo(edy.getRecordTime());
		return elecDataYearMapper.selectByExample(ex);
	}

}
