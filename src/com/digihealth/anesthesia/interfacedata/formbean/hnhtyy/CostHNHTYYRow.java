package com.digihealth.anesthesia.interfacedata.formbean.hnhtyy;



public class CostHNHTYYRow
{

    private String chargeItemCode;
    private String chargeItemName;
    private String firm;
    private String type; //1药品 2项目
    private Float number;//数量
    private String pkItId;
    private Float jl;//剂量
    
    
	public String getChargeItemCode() {
		return chargeItemCode;
	}
	public void setChargeItemCode(String chargeItemCode) {
		this.chargeItemCode = chargeItemCode;
	}
	public String getChargeItemName() {
		return chargeItemName;
	}
	public void setChargeItemName(String chargeItemName) {
		this.chargeItemName = chargeItemName;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Float getNumber() {
		return number;
	}
	public void setNumber(Float number) {
		this.number = number;
	}
	public String getPkItId() {
		return pkItId;
	}
	public void setPkItId(String pkItId) {
		this.pkItId = pkItId;
	}
	public Float getJl() {
		return jl;
	}
	public void setJl(Float jl) {
		this.jl = jl;
	}
    
	
	
}
