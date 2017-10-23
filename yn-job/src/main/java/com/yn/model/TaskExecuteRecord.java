package com.yn.model;

import java.io.Serializable;
import java.util.Date;

public class TaskExecuteRecord implements Serializable{
    
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 5041436540174416661L;

	private Long id;

    private Date endDate;

    private String status;

    private String jobName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

	@Override
	public String toString() {
		return "TaskExecuteRecord [id=" + id + ", endDate=" + endDate + ", status=" + status + ", jobName=" + jobName
				+ "]";
	}

	public TaskExecuteRecord(Long id, Date endDate, String status, String jobName) {
		this.id = id;
		this.endDate = endDate;
		this.status = status;
		this.jobName = jobName;
	}

	public TaskExecuteRecord() {
	}
    
    
}