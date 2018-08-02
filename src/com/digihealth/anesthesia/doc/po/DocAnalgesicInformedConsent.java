package com.digihealth.anesthesia.doc.po;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "术后（术前）镇疼知情同意书")
public class DocAnalgesicInformedConsent {
    /**
     * 编号
     */
	@ApiModelProperty(value = "编号")
    private String analgesicId;

    /**
     * 患者Id
     */
	@ApiModelProperty(value = "患者Id")
    private String regOptId;

    /**
     * 患者静脉自控镇痛
     */
	@ApiModelProperty(value = "患者静脉自控镇痛")
    private Integer pcia;

    /**
     * 患者硬膜外自控镇痛
     */
	@ApiModelProperty(value = "患者硬膜外自控镇痛")
    private Integer pcea;

    /**
     * 自动镇痛泵单价
     */
	@ApiModelProperty(value = "自动镇痛泵单价")
    private Float pumpPrice;

    /**
     * 电子自动镇痛泵单价
     */
	@ApiModelProperty(value = "电子自动镇痛泵单价")
    private Float electronicPumpPrice;

    /**
     * 麻醉医生签名
     */
	@ApiModelProperty(value = "麻醉医生签名")
    private String signName;

    /**
     * 签名时间
     */
	@ApiModelProperty(value = "签名时间")
    private Date signTime;

    /**
     * 患者选择的镇痛方式
     */
	@ApiModelProperty(value = "患者选择的镇痛方式")
    private String analgesicType;

    /**
     * 局点编号
     */
	@ApiModelProperty(value = "局点编号")
    private String beid;
	
    /**
     * 文档完成状态
     */
	@ApiModelProperty(value = "文档完成状态")
    private String processState;

    public String getAnalgesicId() {
        return analgesicId;
    }

    public void setAnalgesicId(String analgesicId) {
        this.analgesicId = analgesicId == null ? null : analgesicId.trim();
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

    public Integer getPcia() {
        return pcia;
    }

    public void setPcia(Integer pcia) {
        this.pcia = pcia;
    }

    public Integer getPcea() {
        return pcea;
    }

    public void setPcea(Integer pcea) {
        this.pcea = pcea;
    }

    public Float getPumpPrice() {
        return pumpPrice;
    }

    public void setPumpPrice(Float pumpPrice) {
        this.pumpPrice = pumpPrice;
    }

    public Float getElectronicPumpPrice() {
        return electronicPumpPrice;
    }

    public void setElectronicPumpPrice(Float electronicPumpPrice) {
        this.electronicPumpPrice = electronicPumpPrice;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getAnalgesicType() {
        return analgesicType;
    }

    public void setAnalgesicType(String analgesicType) {
        this.analgesicType = analgesicType == null ? null : analgesicType.trim();
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid == null ? null : beid.trim();
    }

	public String getProcessState()
	{
		return processState;
	}

	public void setProcessState(String processState)
	{
		this.processState = processState;
	}
}