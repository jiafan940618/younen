package com.yn.vo;


public class InverterVo {
	

	    private Long id;
	
		private Integer brandId;
	
		private String brandName;
	
		private String model;

		private int phases;

		private int voltage;
	
		private int frequency;
	
		private Double qualityAssurance;

		private Double boardYear;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Integer getBrandId() {
			return brandId;
		}

		public void setBrandId(Integer brandId) {
			this.brandId = brandId;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public int getPhases() {
			return phases;
		}

		public void setPhases(int phases) {
			this.phases = phases;
		}

		public int getVoltage() {
			return voltage;
		}

		public void setVoltage(int voltage) {
			this.voltage = voltage;
		}

		public int getFrequency() {
			return frequency;
		}

		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		public Double getQualityAssurance() {
			return qualityAssurance;
		}

		public void setQualityAssurance(Double qualityAssurance) {
			this.qualityAssurance = qualityAssurance;
		}

		public Double getBoardYear() {
			return boardYear;
		}

		public void setBoardYear(Double boardYear) {
			this.boardYear = boardYear;
		}

}
