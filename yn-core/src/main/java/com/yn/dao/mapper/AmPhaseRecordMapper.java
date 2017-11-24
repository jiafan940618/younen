package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.AmPhaseRecord;
import com.yn.vo.AmPhaseRecordExample;

@Mapper
public interface AmPhaseRecordMapper {

	public void addAmPhaseRecord(AmPhaseRecord apr);
	 int countByExample(AmPhaseRecordExample example);

	    int deleteByExample(AmPhaseRecordExample example);

	    int deleteByPrimaryKey(String amPhaseRecordId);

	    int insert(AmPhaseRecord record);

	    int insertSelective(AmPhaseRecord record);

	    List<AmPhaseRecord> selectByExample(AmPhaseRecordExample example);

	    AmPhaseRecord selectByPrimaryKey(AmPhaseRecord amPhaseRecord);

	    int updateByExampleSelective(@Param("record") AmPhaseRecord record, @Param("example") AmPhaseRecordExample example);

	    int updateByExample(@Param("record") AmPhaseRecord record, @Param("example") AmPhaseRecordExample example);

	    int updateByPrimaryKeySelective(AmPhaseRecord record);

	    int updateByPrimaryKey(AmPhaseRecord record);
	    
		public List<AmPhaseRecord> selectOneByC(AmPhaseRecord amPhaseRecord);
		
		/**
		 * 
		    * @Title: createTmpTable
		    * @Description: TODO(根据时间创建临时表)
		    * @param @param amPhaseRecord    参数 
		    * @return void    返回类型
		    * @throws
		 */
	    void createTmpTable(AmPhaseRecord amPhaseRecord);
	    
	    /**
	     * 
	        * @Title: dropTmpTable
	        * @Description: TODO(这里用一句话描述这个方法的作用)
	        * @param @param amPhaseRecord    参数
	        * @return void    返回类型
	        * @throws
	     */
	    void dropTmpTable(AmPhaseRecord amPhaseRecord);
	    
		public AmPhaseRecord findByMapper4InitKwh(AmPhaseRecord apr);
		
		public List<AmPhaseRecord> findAll4DateCaddr(AmPhaseRecord amPhaseRecord);
		
		public List<AmPhaseRecord> selectOneBySort(AmPhaseRecord amPhaseRecord);
		
		/**
		 * 
		    * @Title: findMaxData
		    * @Description: TODO(查询当天时间最大的一条数据)
		    * @param @param aprR
		    * @param @return    参数
		    * @return AmPhaseRecord    返回类型
		    * @throws
		 */
		public AmPhaseRecord findMaxData(AmPhaseRecord aprR);
		
		/**
		 * 
		    * @Title: findMinData
		    * @Description: TODO(当天时间最小的数据)
		    * @param @param aprR
		    * @param @return    参数
		    * @return AmPhaseRecord    返回类型
		    * @throws
		 */
		public AmPhaseRecord findMinData(AmPhaseRecord aprR);
		
		public AmPhaseRecord find4Daddr(AmPhaseRecord aprR);
}
