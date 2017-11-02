package com.yn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yn.dao.AmPhaseRecordDao;
import com.yn.dao.mapper.AmPhaseRecordMapper;
import com.yn.model.AmPhaseRecord;
import com.yn.utils.DateUtil;
import com.yn.utils.RepositoryUtil;
import com.yn.vo.AmPhaseRecordExample;
import com.yn.vo.AmPhaseRecordExample.Criteria;

@Service
public class AmPhaseRecordService {

	@Autowired
	AmPhaseRecordDao amPhaseRecordDao;

	@Autowired
	AmPhaseRecordMapper amPhaseRecordMapper;

	public AmPhaseRecord findOne(Long id) {
		return amPhaseRecordDao.findOne(id);
	}

	public AmPhaseRecord save(AmPhaseRecord amPhaseRecord) {
		AmPhaseRecord save = amPhaseRecordDao.save(amPhaseRecord);
		return save;
	}
	
	/**
	 * 
	    * @Title: dropTmpTable
	    * @Description: TODO(删除表)
	    * @param @param date    参数
	    * @return void    返回类型
	    * @throws
	 */
	public void dropTmpTable(String date){
		AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
		amPhaseRecord.setDate(date);
		amPhaseRecordMapper.dropTmpTable(amPhaseRecord);
	}

	public void saveByMapper(AmPhaseRecord apr) {
		amPhaseRecordMapper.createTmpTable(apr);
		amPhaseRecordMapper.addAmPhaseRecord(apr);
	}

	@Transactional
	public int updateByPrimaryKeySelective(AmPhaseRecord record) {
		amPhaseRecordMapper.createTmpTable(record);
		return amPhaseRecordMapper.updateByPrimaryKeySelective(record);
	}
	@Transactional
	public int updateByPrimaryKey(AmPhaseRecord record) {
		return amPhaseRecordMapper.updateByPrimaryKey(record);
	}

	public AmPhaseRecord selectByPrimaryKey(AmPhaseRecord record) {
		return amPhaseRecordMapper.selectByPrimaryKey(record);
	}

	public void delete(Long id) {
		amPhaseRecordDao.delete(id);
	}

	public void deleteBatch(List<Long> id) {
		amPhaseRecordDao.deleteBatch(id);
	}

	public AmPhaseRecord findOne(AmPhaseRecord amPhaseRecord) {
		Specification<AmPhaseRecord> spec = RepositoryUtil.getSpecification(amPhaseRecord);
		AmPhaseRecord findOne = amPhaseRecordDao.findOne(spec);
		return findOne;
	}
	public AmPhaseRecord findOneByMapper(AmPhaseRecord amPhaseRecord) {
		//RowId cAddr iAddr dAddr dType wAddr MeterTime
		amPhaseRecordMapper.createTmpTable(amPhaseRecord);
		AmPhaseRecord findOne = amPhaseRecordMapper.selectOneByC(amPhaseRecord);
		return findOne;
	}

	public List<AmPhaseRecord> findAll(List<Long> list) {
		return amPhaseRecordDao.findAll(list);
	}

	public Page<AmPhaseRecord> findAll(AmPhaseRecord amPhaseRecord, Pageable pageable) {
		Specification<AmPhaseRecord> spec = RepositoryUtil.getSpecification(amPhaseRecord);
		Page<AmPhaseRecord> findAll = amPhaseRecordDao.findAll(spec, pageable);
		return findAll;
	}

	public List<AmPhaseRecord> findAll(AmPhaseRecord amPhaseRecord) {
		Specification<AmPhaseRecord> spec = RepositoryUtil.getSpecification(amPhaseRecord);
		return amPhaseRecordDao.findAll(spec);
	}

	public List<AmPhaseRecord> findAllByMapper(AmPhaseRecord amPhaseRecord) {
		AmPhaseRecordExample example = new AmPhaseRecordExample();
		Criteria criteria = example.createCriteria();
		// Dealt iAddr dType cAddr dAddr
		criteria.andDealtEqualTo(amPhaseRecord.getDealt());
		criteria.andIAddrEqualTo(amPhaseRecord.getiAddr());
		criteria.andDTypeEqualTo(amPhaseRecord.getdType());
		criteria.andCAddrEqualTo(amPhaseRecord.getcAddr());
		if(amPhaseRecord.getdAddr()!=null){
			criteria.andDAddrEqualTo(amPhaseRecord.getdAddr());
		}
		example.setDate(amPhaseRecord.getDate());
		List<AmPhaseRecord> byExample = amPhaseRecordMapper.selectByExample(example);
		return byExample;
	}
	
	public List<AmPhaseRecord> findAllByMapper2(AmPhaseRecord amPhaseRecord) throws ParseException {
		AmPhaseRecordExample example = new AmPhaseRecordExample();
		Criteria criteria = example.createCriteria();
		// Dealt iAddr dType cAddr dAddr
		criteria.andCAddrEqualTo(amPhaseRecord.getcAddr());
		criteria.andDTypeEqualTo(amPhaseRecord.getdType());
		criteria.andIAddrEqualTo(amPhaseRecord.getiAddr());
		criteria.andDealtEqualTo(amPhaseRecord.getDealt());
		if(amPhaseRecord.getdAddr()!=null){
			criteria.andDAddrEqualTo(amPhaseRecord.getdAddr());
		}
		example.setDate(amPhaseRecord.getDate());
		List<AmPhaseRecord> byExample = amPhaseRecordMapper.selectByExample(example);
		return byExample;
	}


	/**
	 * 删除记录表某一天的数据，。
	 * 
	 * @param date
	 * @throws ParseException
	 */
	public boolean deleteAmPhaseRecordById(String date) {
		try {
			date = parseDate(date);
			String sql = "delete FROM  am_phase_record WHERE am_phase_record_id LIKE '%" + date + "%' ";
			System.out.println(sql);
			amPhaseRecordDao.deleteAmPhaseRecordByIdLike(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 格式如下：
	 * 
	 * @param 2017-10-10
	 * @return 171010
	 * @throws ParseException
	 */
	private String parseDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date thisHourSpace = sdf.parse(date);
		String startDtm = DateUtil.formatDate(thisHourSpace, DateUtil.yyMMddHHmmss).substring(0, 6);
		return startDtm;
	}

}
