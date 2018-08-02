/*
 * MedChargeTemp.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-05-11 Created
 */
package com.digihealth.anesthesia.tmp.po;

public class TmpMedPayTemp {
    /**
     * 收费模版主键
     */
    private String medPayTempId;
    /**
     * 剂量
     */
    private Float valamt;

    private String medicineId;
    
    private String firmId;

    /**
     * 模板ID
     */
    private String chargeMedTempId;
    
    /**
     * 收费类型
     * 1麻醉药  2输液
     */
    private String chargedType;
    
    private String name;
    private Float priceMinPackage;
    private String dosageUnit;
    private String spec;
    private Float packageTotalAmount;
    private String code;
    private String beid;


	public String getBeid()
    {
        return beid;
    }

    public void setBeid(String beid)
    {
        this.beid = beid;
    }

    public String getMedPayTempId() {
		return medPayTempId;
	}

	public void setMedPayTempId(String medPayTempId) {
		this.medPayTempId = medPayTempId;
	}

	public String getChargeMedTempId() {
		return chargeMedTempId;
	}

	public void setChargeMedTempId(String chargeMedTempId) {
		this.chargeMedTempId = chargeMedTempId;
	}

	public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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

	public Float getPackageTotalAmount() {
		return packageTotalAmount;
	}

	public void setPackageTotalAmount(Float packageTotalAmount) {
		this.packageTotalAmount = packageTotalAmount;
	}

	public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getChargedType() {
		return chargedType;
	}

	public void setChargedType(String chargedType) {
		this.chargedType = chargedType;
	}

	public String getFirmId() {
		return firmId;
	}

	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

    public Float getValamt() {
		return valamt;
	}

	public void setValamt(Float valamt) {
		this.valamt = valamt;
	}

	public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

}