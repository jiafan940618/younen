package com.yn.vo;

/**
 * 
    * @ClassName: DataStatistics
    * @Description: TODO(优能光伏服务平台能源数据报表)
    * @author lzyqssn
    * @date 2017年12月23日
    *
 */
public class DataStatistics {

	// 周期内实际发电量 单位：度
	private Double totalKwh;
	// 装机容量
	private Double capacity;
	// 应发天数
	private Integer shouldWorkDays;
	// 故障天数 没发电算故障
	private Integer faultDays;
	// 本季度总天数
	private Integer thisSeasonDays;
	// 上季度总天数 --> 环比
	private Integer prevSeasonDays;
	// 实发天数 应发天数-故障天数
	private Integer actualDay;
	// 周期计划发电量 单位：度 装机容量*实际发电天数*3.06
	private Double cycleElectricityOfPlan;
	// 周期发电率 （实际/计划）*100%
	private String cycleRate;

	public Double getTotalKwh() {
		return totalKwh;
	}

	public void setTotalKwh(Double totalKwh) {
		this.totalKwh = totalKwh;
	}

	public Double getCapacity() {
		return capacity;
	}

	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}

	public Integer getShouldWorkDays() {
		return shouldWorkDays;
	}

	public void setShouldWorkDays(Integer shouldWorkDays) {
		this.shouldWorkDays = shouldWorkDays;
	}

	public Integer getFaultDays() {
		return faultDays;
	}

	public void setFaultDays(Integer faultDays) {
		this.faultDays = faultDays;
	}

	public Integer getThisSeasonDays() {
		return thisSeasonDays;
	}

	public void setThisSeasonDays(Integer thisSeasonDays) {
		this.thisSeasonDays = thisSeasonDays;
	}

	public Integer getPrevSeasonDays() {
		return prevSeasonDays;
	}

	public void setPrevSeasonDays(Integer prevSeasonDays) {
		this.prevSeasonDays = prevSeasonDays;
	}

	public Integer getActualDay() {
		return actualDay;
	}

	public void setActualDay(Integer actualDay) {
		this.actualDay = actualDay;
	}

	public Double getCycleElectricityOfPlan() {
		return cycleElectricityOfPlan;
	}

	public void setCycleElectricityOfPlan(Double cycleElectricityOfPlan) {
		this.cycleElectricityOfPlan = cycleElectricityOfPlan;
	}

	public String getCycleRate() {
		return cycleRate;
	}

	public void setCycleRate(String cycleRate) {
		this.cycleRate = cycleRate;
	}

}
