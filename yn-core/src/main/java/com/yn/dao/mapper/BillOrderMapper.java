package com.yn.dao.mapper;

import com.yn.model.BillOrder;
import com.yn.vo.BillOrderVo;

public interface BillOrderMapper {
	
	void UpdateBillorder(BillOrderVo billorderVo);
	
	void InsertBillorder(BillOrder billorder);
	

}
