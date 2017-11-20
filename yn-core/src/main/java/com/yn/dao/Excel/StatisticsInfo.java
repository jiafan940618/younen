package com.yn.dao.Excel;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class StatisticsInfo {
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
    private long id;  
	
    private BigDecimal money;
    
    private String description;  
    
    private Date currentdate; 
    
    public long getId() {  
        return id;  
    }  
    public void setId(long id) {  
        this.id = id;  
    }  
    public BigDecimal getMoney() {  
        return money;  
    }  
    public void setMoney(BigDecimal money) {  
        this.money = money;  
    }  
    public String getDescription() {  
        return description;  
    }  
    public void setDescription(String description) {  
        this.description = description;  
    }  
    public Date getCurrentdate() {  
        return currentdate;  
    }  
    public void setCurrentdate(Date currentdate) {  
        this.currentdate = currentdate;  
    }  
}  