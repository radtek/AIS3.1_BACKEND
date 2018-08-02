/*
 * ChargePayTemp.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-05-11 Created
 */
package com.digihealth.anesthesia.tmp.po;

public class TmpChargePayTemp {
    /**
     * 收费模版主键
     */
    private String chargePayTempId;

    /**
     * 剂量
     */
    private Float valamt;

    private String chargeItemId;

    /**
     * 模板ID
     */
    private String chargeMedTempId;
    
    /**
     * 收费类型
     * 1麻醉费用  2耗材费用 3麻醉操作费用
     */
    private String chargedType;
    
    //收费项目名称
    private String name;
    //单价
    private Float priceMinPackage;
    
    private Float chargeAmount;

    private String beid;
    
    private Integer enable; //用来标注收费项目的有效标志
    
    private String spec;//规格

    
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getBeid()
    {
        return beid;
    }

    public void setBeid(String beid)
    {
        this.beid = beid;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPriceMinPackage() {
		return priceMinPackage;
	}

	public void setPriceMinPackage(Float priceMinPackage) {
		this.priceMinPackage = priceMinPackage;
	}

	public Float getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Float chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargedType() {
		return chargedType;
	}

	public void setChargedType(String chargedType) {
		this.chargedType = chargedType;
	}

    public Float getValamt() {
		return valamt;
	}

	public void setValamt(Float valamt) {
		this.valamt = valamt;
	}

	public String getChargeItemId() {
        return chargeItemId;
    }

    public void setChargeItemId(String chargeItemId) {
        this.chargeItemId = chargeItemId;
    }

	public String getChargePayTempId() {
		return chargePayTempId;
	}

	public void setChargePayTempId(String chargePayTempId) {
		this.chargePayTempId = chargePayTempId;
	}

	public String getChargeMedTempId() {
		return chargeMedTempId;
	}

	public void setChargeMedTempId(String chargeMedTempId) {
		this.chargeMedTempId = chargeMedTempId;
	}

}