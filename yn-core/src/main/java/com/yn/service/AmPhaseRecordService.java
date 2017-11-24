package com.yn.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
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
	public void dropTmpTable(String date) {
		AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
		amPhaseRecord.setDate(date);
		amPhaseRecordMapper.dropTmpTable(amPhaseRecord);
	}

	public void saveByMapper(AmPhaseRecord apr) {
		createTmpTable(apr);
		amPhaseRecordMapper.addAmPhaseRecord(apr);
	}

	public void createTmpTable(AmPhaseRecord apr) {
		amPhaseRecordMapper.createTmpTable(apr);
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
		// RowId cAddr iAddr dAddr dType wAddr MeterTime
		amPhaseRecordMapper.createTmpTable(amPhaseRecord);
		// AmPhaseRecord amPhaseRecord1 = new AmPhaseRecord();
		List<AmPhaseRecord> findOne = amPhaseRecordMapper.selectOneByC(amPhaseRecord);
		// for (AmPhaseRecord amPhaseRecord2 : findOne) {
		// BeanUtils.copyProperties(amPhaseRecord2, amPhaseRecord1);
		//
		// }
		return findOne.size() == 0 ? null : findOne.get(findOne.size() - 1);
	}

	public AmPhaseRecord findOneByMapper4Daddr(AmPhaseRecord amPhaseRecord) {
		amPhaseRecord.setMeterState(new SimpleDateFormat("yyyy_MM_dd").format(new Date()));
		AmPhaseRecord findOne = amPhaseRecordMapper.find4Daddr(amPhaseRecord);
		return findOne;
	}

	public AmPhaseRecord findOneByMapperAndSort(AmPhaseRecord amPhaseRecord) {
		// RowId cAddr iAddr dAddr dType wAddr MeterTime
		amPhaseRecordMapper.createTmpTable(amPhaseRecord);
		// AmPhaseRecord amPhaseRecord1 = new AmPhaseRecord();
		List<AmPhaseRecord> findOne = amPhaseRecordMapper.selectOneBySort(amPhaseRecord);
		// for (AmPhaseRecord amPhaseRecord2 : findOne) {
		// BeanUtils.copyProperties(amPhaseRecord2, amPhaseRecord1);
		//
		// }
		return findOne.size() == 0 ? null : findOne.get(findOne.size() - 1);
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

	public List<AmPhaseRecord> findAllByMapperAndCurrenttime(AmPhaseRecord amPhaseRecord) {
		AmPhaseRecordExample example = new AmPhaseRecordExample();
		Criteria criteria = example.createCriteria();
		criteria.andDealtEqualTo(amPhaseRecord.getDealt());
		criteria.andIAddrEqualTo(amPhaseRecord.getiAddr());
		criteria.andDTypeEqualTo(amPhaseRecord.getdType());
		criteria.andCAddrEqualTo(amPhaseRecord.getcAddr());
		criteria.andWAddrEqualTo(amPhaseRecord.getwAddr());
		if (amPhaseRecord.getdAddr() != null) {
			criteria.andDAddrEqualTo(amPhaseRecord.getdAddr());
		}
		example.setDate(amPhaseRecord.getDate());
		Date[] thisHourSpace = DateUtil.thisHourSpace();
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.HOUR_OF_DAY, -3);// 3小时前
		Date date = calendar.getTime();
		Long startDtm = Long.valueOf(DateUtil.formatDate(date, DateUtil.yyMMddHHmmss));
		Long endDtm = Long.valueOf(DateUtil.formatDate(thisHourSpace[1], DateUtil.yyMMddHHmmss));
		criteria.andMeterTimeGreaterThanOrEqualTo(startDtm);
		criteria.andMeterTimeLessThanOrEqualTo(endDtm);
		List<AmPhaseRecord> byExample = amPhaseRecordMapper.selectByExample(example);
		return byExample;
	}

	public List<AmPhaseRecord> findAllByMapper(AmPhaseRecord amPhaseRecord) {
		AmPhaseRecordExample example = new AmPhaseRecordExample();
		Criteria criteria = example.createCriteria();
		if (amPhaseRecord.getDealt() != null) {
			criteria.andDealtEqualTo(amPhaseRecord.getDealt());
		}
		if (amPhaseRecord.getiAddr() != null) {
			criteria.andIAddrEqualTo(amPhaseRecord.getiAddr());
		}
		criteria.andDTypeEqualTo(amPhaseRecord.getdType());
		criteria.andCAddrEqualTo(amPhaseRecord.getcAddr());
		criteria.andWAddrEqualTo(amPhaseRecord.getwAddr());
		if (amPhaseRecord.getdAddr() != null) {
			criteria.andDAddrEqualTo(amPhaseRecord.getdAddr());
		}
		example.setDate(amPhaseRecord.getDate());
		if (amPhaseRecord.getMeterTime() != null) {
			criteria.andMeterTimeEqualTo(amPhaseRecord.getMeterTime());
		}
		if (amPhaseRecord.getdAddr() != null && amPhaseRecord.getdAddr() == 6) {
			criteria.andDAddrIn(Arrays.asList(1L, 11L));
		}
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
		if (amPhaseRecord.getdAddr() != null) {
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

	public AmPhaseRecord findByMapper4InitKwh(AmPhaseRecord apr) {
		return amPhaseRecordMapper.findByMapper4InitKwh(apr);
	}

	public List<AmPhaseRecord> findAll(String date, Integer cAddr, Integer type) {
		AmPhaseRecord amPhaseRecord = new AmPhaseRecord();
		amPhaseRecord.setDate(date);
		amPhaseRecord.setcAddr(cAddr);
		amPhaseRecord.setdAddr(Long.valueOf(type));
		// amPhaseRecord.setwAddr(0);xml已固定为0。
		return amPhaseRecordMapper.findAll4DateCaddr(amPhaseRecord);
	}

	public AmPhaseRecord findMaxData(AmPhaseRecord aprR) {
		return amPhaseRecordMapper.findMaxData(aprR);
	}

	public AmPhaseRecord findMinData(AmPhaseRecord aprR) {
		return amPhaseRecordMapper.findMinData(aprR);
	}

}
