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
	    
		public AmPhaseRecord selectOneByC(AmPhaseRecord amPhaseRecord);
		
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
}
