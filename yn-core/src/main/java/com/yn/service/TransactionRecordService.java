package com.yn.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yn.dao.TransactionRecordDao;
import com.yn.dao.mapper.TransactionRecordMapper;
import com.yn.model.BillOrder;
import com.yn.model.BillWithdrawals;
import com.yn.model.Page;
import com.yn.model.Recharge;
import com.yn.model.TransactionRecord;
import com.yn.utils.BeanCopy;

@Service
public class TransactionRecordService {
	
	@Autowired
	TransactionRecordDao transactionRecordDao;
	@Autowired
	TransactionRecordMapper transactionRecordMapper;
	
	 public TransactionRecord findOne(Long id) {
	        return transactionRecordDao.findOne(id);
	    }
	 
	public int FindByNum(Long userId){
		
		return transactionRecordDao.FindByNum(userId); 
	 }

	    public TransactionRecord save(TransactionRecord transactionRecord) {
	        if (transactionRecord.getId() != null) {
	        	TransactionRecord one = transactionRecordDao.findOne(transactionRecord.getId());
	            try {
	                BeanCopy.beanCopy(transactionRecord, one);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return transactionRecordDao.save(one);
	        } else {
	            return transactionRecordDao.save(transactionRecord);
	        }
	    }
	    public List<TransactionRecord> GivePage(Page<TransactionRecord> page){
	    	
			return transactionRecordMapper.GivePage(page);
	    }
	  
	
	 /** 将3种记录保存在一个表中*/
	    /** 支付记录*/
	    public void InsertBillAll(Object object){
	    	
	    	TransactionRecord transactionRecord = new TransactionRecord();
	    	
	    	if(object instanceof BillOrder){
	    		BillOrder billOrder = (BillOrder)object;
	    		
	    		transactionRecord.setRemark(billOrder.getRemark());
	    		
	    		transactionRecord.setRemark("无");
		    	transactionRecord.setMoney(billOrder.getMoney());
		    	transactionRecord.setOrderNo(billOrder.getTradeNo());
		    	transactionRecord.setUserId(billOrder.getUserId());
		    	transactionRecord.setType(2);
		    	transactionRecord.setStatus(billOrder.getStatus());
		    	transactionRecord.setPayWay(billOrder.getPayWay());
		    	
	    	}else if(object instanceof Recharge){
	    		Recharge recharge = (Recharge)object;
	    	
	    		transactionRecord.setRemark(recharge.getRemark());
	    		
	    		transactionRecord.setMoney(recharge.getMoney());
	    		transactionRecord.setPayWay(recharge.getPayWay());
	    		transactionRecord.setType(1);
	    		transactionRecord.setStatus(recharge.getStatus());
	    		transactionRecord.setUserId(recharge.getUserId());
	    		transactionRecord.setOrderNo(recharge.getRechargeCode());
	    	}else if(object instanceof BillWithdrawals){
	    		BillWithdrawals billWithdrawals = (BillWithdrawals)object;
	    	
	    		transactionRecord.setRemark(billWithdrawals.getRemark());	
	 
	    		transactionRecord.setMoney(billWithdrawals.getMoney());
	    		transactionRecord.setPayWay(9);
	    		transactionRecord.setType(3);
	    		transactionRecord.setStatus(billWithdrawals.getStatus());
	    		transactionRecord.setOrderNo(billWithdrawals.getTradeNo());
	    		transactionRecord.setUserId(billWithdrawals.getUserId());
	    		
	    	}
	    	
	    	save(transactionRecord);
	    	
	    }
	    
	 /** 充值记录*/
	    
	 /** 提现记录*/
	    
	    
	    
	    
	

}
