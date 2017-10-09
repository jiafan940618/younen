package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.BillOrder;
import com.yn.vo.BillOrderVo;

@Mapper
public interface BillOrderMapper {

	void UpdateBillorder(BillOrderVo billorderVo);

	void InsertBillorder(BillOrder billorder);

}
