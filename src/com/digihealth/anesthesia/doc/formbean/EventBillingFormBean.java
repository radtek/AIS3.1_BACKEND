package com.digihealth.anesthesia.doc.formbean;

import java.io.Serializable;

public class EventBillingFormBean implements Serializable{

    private String ebId;
	private String regOptId;
	private String regName;
	private String code;
	private String name;
	private String spec;
	private String firm;
	private String firmId;
	private float packageDosageAmount;
	private float dosageTotalAmount;
	private float packageTotalAmount;
	private String minPackageUnit;
	private float priceMinPackage;
	private float discount;
	private float shouldCost;
	private float realCost;
	private String isCharged;
	private String chargedType;
	private String userType;
	private String totalAmountUnit;
	private String state;
    private String dosageUnit;
	private String operaDate;
	private String hid;
	private String deptName;
	private String medName;
	private String costType;//费用类型 1麻醉科收费清单 2手术核算单
	private String anesthetistName;
	
	
	public String getOperaDate() {
		return operaDate;
	}
	public void setOperaDate(String operaDate) {
		this.operaDate = operaDate;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getMedName() {
		return medName;
	}
	public void setMedName(String medName) {
		this.medName = medName;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public String getAnesthetistName() {
		return anesthetistName;
	}
	public void setAnesthetistName(String anesthetistName) {
		this.anesthetistName = anesthetistName;
	}
	public String getEbId()
    {
        return ebId;
    }
    public void setEbId(String ebId)
    {
        this.ebId = ebId;
    }
    public String getFirmId()
    {
        return firmId;
    }
    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }
    public String getState()
    {
        return state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    public String getDosageUnit()
    {
        return dosageUnit;
    }
    public void setDosageUnit(String dosageUnit)
    {
        this.dosageUnit = dosageUnit;
    }
    public String getRegOptId() {
		return regOptId;
	}
	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public float getPackageDosageAmount() {
		return packageDosageAmount;
	}
	public void setPackageDosageAmount(float packageDosageAmount) {
		this.packageDosageAmount = packageDosageAmount;
	}
	public float getDosageTotalAmount() {
		return dosageTotalAmount;
	}
	public void setDosageTotalAmount(float dosageTotalAmount) {
		this.dosageTotalAmount = dosageTotalAmount;
	}
	public float getPackageTotalAmount() {
		return packageTotalAmount;
	}
	public void setPackageTotalAmount(float packageTotalAmount) {
		this.packageTotalAmount = packageTotalAmount;
	}
	public String getMinPackageUnit() {
		return minPackageUnit;
	}
	public void setMinPackageUnit(String minPackageUnit) {
		this.minPackageUnit = minPackageUnit;
	}
	public float getPriceMinPackage() {
		return priceMinPackage;
	}
	public void setPriceMinPackage(float priceMinPackage) {
		this.priceMinPackage = priceMinPackage;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getShouldCost() {
		return shouldCost;
	}
	public void setShouldCost(float shouldCost) {
		this.shouldCost = shouldCost;
	}
	public float getRealCost() {
		return realCost;
	}
	public void setRealCost(float realCost) {
		this.realCost = realCost;
	}
	public String getIsCharged() {
		return isCharged;
	}
	public void setIsCharged(String isCharged) {
		this.isCharged = isCharged;
	}
	public String getChargedType() {
		return chargedType;
	}
	public void setChargedType(String chargedType) {
		this.chargedType = chargedType;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getTotalAmountUnit() {
		return totalAmountUnit;
	}
	public void setTotalAmountUnit(String totalAmountUnit) {
		this.totalAmountUnit = totalAmountUnit;
	}
	
	
	
	
}