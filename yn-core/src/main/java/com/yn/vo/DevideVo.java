package com.yn.vo;



/**
 * 设备
 */
public class DevideVo {
	
	private Long id;
	private String devideName;
	private String devideBrand;
	private String devideModel;
	private Integer type;
	private Long parentId;
	private Integer zlevel;
	
	//电池板的品牌型号 SolarPanel
	
	private Integer solbrandId;
	private String solbrandName;
	private String solmodel;
	private Double solqualityAssurance; //质保年限
	private String solconversionEff;
	private String solpowerGeneration;
	private String solremark;
	private Double solsupplyPrice;
	//逆变器的品牌与型号 Inverter
	private Integer invbrandId;
	private String invbrandName;
	private String invmodel;
	private Double invqualityAssurance; //质保年限
	private String invremark;
	private Double invsupplyPrice;
	
	
	
	
	
	public Double getSolsupplyPrice() {
		return solsupplyPrice;
	}
	public void setSolsupplyPrice(Double solsupplyPrice) {
		this.solsupplyPrice = solsupplyPrice;
	}
	public Double getInvsupplyPrice() {
		return invsupplyPrice;
	}
	public void setInvsupplyPrice(Double invsupplyPrice) {
		this.invsupplyPrice = invsupplyPrice;
	}
	public Integer getSolbrandId() {
		return solbrandId;
	}
	public void setSolbrandId(Integer solbrandId) {
		this.solbrandId = solbrandId;
	}
	public String getSolbrandName() {
		return solbrandName;
	}
	public void setSolbrandName(String solbrandName) {
		this.solbrandName = solbrandName;
	}
	public String getSolmodel() {
		return solmodel;
	}
	public void setSolmodel(String solmodel) {
		this.solmodel = solmodel;
	}
	public Double getSolqualityAssurance() {
		return solqualityAssurance;
	}
	public void setSolqualityAssurance(Double solqualityAssurance) {
		this.solqualityAssurance = solqualityAssurance;
	}
	public String getSolconversionEff() {
		return solconversionEff;
	}
	public void setSolconversionEff(String solconversionEff) {
		this.solconversionEff = solconversionEff;
	}
	public String getSolpowerGeneration() {
		return solpowerGeneration;
	}
	public void setSolpowerGeneration(String solpowerGeneration) {
		this.solpowerGeneration = solpowerGeneration;
	}
	public String getSolremark() {
		return solremark;
	}
	public void setSolremark(String solremark) {
		this.solremark = solremark;
	}
	
	public Integer getInvbrandId() {
		return invbrandId;
	}
	public void setInvbrandId(Integer invbrandId) {
		this.invbrandId = invbrandId;
	}
	public String getInvbrandName() {
		return invbrandName;
	}
	public void setInvbrandName(String invbrandName) {
		this.invbrandName = invbrandName;
	}
	public String getInvmodel() {
		return invmodel;
	}
	public void setInvmodel(String invmodel) {
		this.invmodel = invmodel;
	}
	public Double getInvqualityAssurance() {
		return invqualityAssurance;
	}
	public void setInvqualityAssurance(Double invqualityAssurance) {
		this.invqualityAssurance = invqualityAssurance;
	}
	public String getInvremark() {
		return invremark;
	}
	public void setInvremark(String invremark) {
		this.invremark = invremark;
	}
	
	public String getDevideName() {
		return devideName;
	}
	public void setDevideName(String devideName) {
		this.devideName = devideName;
	}
	public String getDevideBrand() {
		return devideBrand;
	}
	public void setDevideBrand(String devideBrand) {
		this.devideBrand = devideBrand;
	}
	public String getDevideModel() {
		return devideModel;
	}
	public void setDevideModel(String devideModel) {
		this.devideModel = devideModel;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getZlevel() {
		return zlevel;
	}
	public void setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
	}
}
