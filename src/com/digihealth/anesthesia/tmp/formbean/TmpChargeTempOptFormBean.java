package com.digihealth.anesthesia.tmp.formbean;

import java.io.Serializable;

/**
 * 麻醉收费单模板表
 * @author dell
 *
 */
public class TmpChargeTempOptFormBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 115613461651398879L;
	
	private String costType; // 1药品 2收费项目
	
	private String fromMedId; //原药品Id
	private String fromFirmId;//原厂家Id
	private String toMedId;//替换后药品Id
	private String toFirmId;//替换后厂家Id
	private String fromChargeItemId;//原收费项目Id
	private String toChargeItemId;//替换后收费项目Id
	
	private String medicineId;
	private String firmId;
	private String chargeItemId;
	
	private String chargeItemCode;//替换后的收费项目code
	private String chargeItemName;
	private String spec;
	private String pinyin;
	private String unit;
	private Float price;
	private String type;
	private String enable;
	private Float basicUnitAmount;
	private Float basicUnitPrice;
	private String chargeType;
	private Integer chargeItemAmount;
	
	
	
	
	public String getChargeItemName() {
		return chargeItemName;
	}
	public void setChargeItemName(String chargeItemName) {
		this.chargeItemName = chargeItemName;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public Float getBasicUnitAmount() {
		return basicUnitAmount;
	}
	public void setBasicUnitAmount(Float basicUnitAmount) {
		this.basicUnitAmount = basicUnitAmount;
	}
	public Float getBasicUnitPrice() {
		return basicUnitPrice;
	}
	public void setBasicUnitPrice(Float basicUnitPrice) {
		this.basicUnitPrice = basicUnitPrice;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public Integer getChargeItemAmount() {
		return chargeItemAmount;
	}
	public void setChargeItemAmount(Integer chargeItemAmount) {
		this.chargeItemAmount = chargeItemAmount;
	}
	public String getChargeItemCode() {
		return chargeItemCode;
	}
	public void setChargeItemCode(String chargeItemCode) {
		this.chargeItemCode = chargeItemCode;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}

	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getFromFirmId() {
		return fromFirmId;
	}
	public void setFromFirmId(String fromFirmId) {
		this.fromFirmId = fromFirmId;
	}

	public String getToFirmId() {
		return toFirmId;
	}
	public void setToFirmId(String toFirmId) {
		this.toFirmId = toFirmId;
	}
	public String getFromMedId() {
		return fromMedId;
	}
	public void setFromMedId(String fromMedId) {
		this.fromMedId = fromMedId;
	}
	public String getToMedId() {
		return toMedId;
	}
	public void setToMedId(String toMedId) {
		this.toMedId = toMedId;
	}
	public String getFromChargeItemId() {
		return fromChargeItemId;
	}
	public void setFromChargeItemId(String fromChargeItemId) {
		this.fromChargeItemId = fromChargeItemId;
	}
	public String getToChargeItemId() {
		return toChargeItemId;
	}
	public void setToChargeItemId(String toChargeItemId) {
		this.toChargeItemId = toChargeItemId;
	}
	public String getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}
	public String getChargeItemId() {
		return chargeItemId;
	}
	public void setChargeItemId(String chargeItemId) {
		this.chargeItemId = chargeItemId;
	}
	
}
