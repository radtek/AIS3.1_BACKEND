package com.digihealth.anesthesia.interfacedata.po;

import java.io.Serializable;

public class OperCost implements Serializable{ 
	
	private Integer number;
	private String chargeItemCode;
	private String firm;
	private String pkItId;
	
	
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public String getPkItId() {
		return pkItId;
	}
	public void setPkItId(String pkItId) {
		this.pkItId = pkItId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getChargeItemCode() {
		return chargeItemCode;
	}
	public void setChargeItemCode(String chargeItemCode) {
		this.chargeItemCode = chargeItemCode;
	}
	
}