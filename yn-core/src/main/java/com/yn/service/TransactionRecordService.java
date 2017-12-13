package com.yn.service;

import java.util.List;

import com.yn.model.*;
import com.yn.utils.RepositoryUtil;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yn.dao.TransactionRecordDao;
import com.yn.dao.mapper.TransactionRecordMapper;
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
	
	public int FindBynewNum(com.yn.model.Page<TransactionRecord> page){
		
		return transactionRecordMapper.FindByNum(page);
	}
	
  public List<TransactionRecord> FindByTransactionRecord(Long userId){
		
		return transactionRecordDao.FindByTransactionRecord(userId);
		
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
	    public List<TransactionRecord> GivePage(com.yn.model.Page<TransactionRecord> page){
	    	
			return transactionRecordMapper.GivePage(page);
	    }

	public Page<TransactionRecord> findAll(TransactionRecord transactionRecord, Pageable pageable) {
		Specification<TransactionRecord> spec = RepositoryUtil.getSpecification(transactionRecord);
		Page<TransactionRecord> findAll = transactionRecordDao.findAll(spec, pageable);
		return findAll;
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
		    	transactionRecord.setServerId(billOrder.getServerId());
		    	transactionRecord.setPayWay(billOrder.getPayWay());
		    	
	    	}else if(object instanceof Recharge){
	    		Recharge recharge = (Recharge)object;
	    	
	    		transactionRecord.setRemark(recharge.getRemark());
	    		
	    		transactionRecord.setMoney(recharge.getMoney());
	    		transactionRecord.setPayWay(recharge.getPayWay());
	    		transactionRecord.setType(1);
	    		transactionRecord.setStatus(recharge.getStatus());
	    		transactionRecord.setUserId(recharge.getUserId());
	    		transactionRecord.setOrderNo(recharge.getTradeNo());
	    	}else if(object instanceof BillWithdrawals){
	    		BillWithdrawals billWithdrawals = (BillWithdrawals)object;
	    	
	    		transactionRecord.setRemark(billWithdrawals.getRemark());	
	 
	    		transactionRecord.setMoney(billWithdrawals.getMoney());
	    		transactionRecord.setPayWay(4);
	    		transactionRecord.setType(3);
	    		transactionRecord.setStatus(billWithdrawals.getStatus());
	    		transactionRecord.setOrderNo(billWithdrawals.getTradeNo());
	    		transactionRecord.setUserId(billWithdrawals.getUserId());
	    		
	    	}else if(object instanceof BillRefund){

				BillRefund billRefund =(BillRefund)object;

				transactionRecord.setRemark(billRefund.getRemark());
				transactionRecord.setMoney(billRefund.getMoney());
				transactionRecord.setStatus(billRefund.getStatus());
				transactionRecord.setOrderNo(billRefund.getTradeNo());
				transactionRecord.setUserId(billRefund.getUserId());
				transactionRecord.setServerId(billRefund.getServerId());
				transactionRecord.setPayWay(4);
				transactionRecord.setType(4);
	    	}
	    	
	    	save(transactionRecord);
	    	
	    }
	    
	 /** 充值记录*/
	    
	 /** 提现记录*/
	    
	    
	    
	    
	

}
