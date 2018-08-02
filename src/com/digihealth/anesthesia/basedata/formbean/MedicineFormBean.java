package com.digihealth.anesthesia.basedata.formbean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: MedicineFormBean.java Description: 描述
 * 
 * @author chengwang
 * @created 2015年11月9日 下午2:53:17
 */
@ApiModel(value = "药品参数对象")
public class MedicineFormBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键id")
	private String medicineId;

	@ApiModelProperty(value = "药品名称")
	private String name;

	@ApiModelProperty(value = "规格")
	private String spec;

	@ApiModelProperty(value = "剂量单位")
	private String dosageUnit;

	@ApiModelProperty(value = "firm")
	private String firm;

	@ApiModelProperty(value = "firmId")
	private String firmId;
	
	@ApiModelProperty(value = "最小包装单位")
	private String minPackageUnit;

	@ApiModelProperty(value = "价格id")
	private String priceId;

	@ApiModelProperty(value = "每最小包装单位中所含有的总剂量")
	private BigDecimal packageDosageAmount;

	@ApiModelProperty(value = "")
	private String pinYin;

	@ApiModelProperty(value = "药品价格")
	private Float priceMinPackage;
	
	@ApiModelProperty(value = "药品code")
	private String code;
	
	@ApiModelProperty(value = "有效标志")
	private String enable;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getFirmId()
    {
        return firmId;
    }

    public void setFirmId(String firmId)
    {
        this.firmId = firmId;
    }

    public Float getPriceMinPackage()
    {
        return priceMinPackage;
    }

    public void setPriceMinPackage(Float priceMinPackage)
    {
        this.priceMinPackage = priceMinPackage;
    }

    public String getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(String medicineId) {
		this.medicineId = medicineId;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public BigDecimal getPackageDosageAmount() {
		return packageDosageAmount;
	}

	public void setPackageDosageAmount(BigDecimal packageDosageAmount) {
		this.packageDosageAmount = packageDosageAmount;
	}

	public String getPriceId() {
		return priceId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
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

	public String getDosageUnit() {
		return dosageUnit;
	}

	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}

	public String getFirm() {
		return firm;
	}

	public void setFirm(String firm) {
		this.firm = firm;
	}

	public String getMinPackageUnit() {
		return minPackageUnit;
	}

	public void setMinPackageUnit(String minPackageUnit) {
		this.minPackageUnit = minPackageUnit;
	}

}
