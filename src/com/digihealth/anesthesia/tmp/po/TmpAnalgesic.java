package com.digihealth.anesthesia.tmp.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value="镇痛方式模版对象")
public class TmpAnalgesic {
    /**
     * 模板ID
     */
	@ApiModelProperty(value="模板ID")
    private String analTmpId;

    /**
     * 镇痛类型
     */
	@ApiModelProperty(value="镇痛类型")
    private String analgesicType;

    /**
     * 药品编号
     */
	@ApiModelProperty(value="药品编号")
    private String medicineId;

    /**
     * 药品名称
     */
    @ApiModelProperty(value="药品名称")
    private String name;

    /**
     * 规格
     */
    @ApiModelProperty(value="规格")
    private String spec;

    /**
     * 剂量
     */
    @ApiModelProperty(value="剂量")
    private String dosage;
    
    /**
     * 剂量单位
     */
    @ApiModelProperty(value="剂量单位")
    private String dosageUnit;

    /**
     * 厂家
     */
    @ApiModelProperty(value="厂家")
    private String firm;
    
    /**
     * bas_medical_take_way的主键id
     */
    @ApiModelProperty(value="用药方式")
    private String medTakeWayId;

    /**
     * 药品价格id,bas_price表主键id
     */
    @ApiModelProperty(value="药品价格id")
    private String priceId;

    /**
     * 基线id
     */
    @ApiModelProperty(value="基线id")
    private String beid;

    public String getAnalTmpId() {
        return analTmpId;
    }

    public void setAnalTmpId(String analTmpId) {
        this.analTmpId = analTmpId == null ? null : analTmpId.trim();
    }

    public String getAnalgesicType() {
        return analgesicType;
    }

    public void setAnalgesicType(String analgesicType) {
        this.analgesicType = analgesicType == null ? null : analgesicType.trim();
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId == null ? null : medicineId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage == null ? null : dosage.trim();
    }

    public String getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(String dosageUnit) {
        this.dosageUnit = dosageUnit == null ? null : dosageUnit.trim();
    }
    
    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm == null ? null : firm.trim();
    }
    
    public String getMedTakeWayId() {
        return medTakeWayId;
    }

    public void setMedTakeWayId(String medTakeWayId) {
        this.medTakeWayId = medTakeWayId == null ? null : medTakeWayId.trim();
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId == null ? null : priceId.trim();
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid == null ? null : beid.trim();
    }
}
