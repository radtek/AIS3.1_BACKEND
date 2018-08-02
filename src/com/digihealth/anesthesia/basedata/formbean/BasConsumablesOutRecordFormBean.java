package com.digihealth.anesthesia.basedata.formbean;

import java.util.Date;

public class BasConsumablesOutRecordFormBean
{
    /**
     * 耗材库存id
     */
    private Integer storageId;

    /**
     * 耗材编号
     */
    private String instrumentId;

    /**
     * 耗材名称
     */
    private String instrumentName;

    /**
     * 厂家
     */
    private String firm;

    /**
     * 规格
     */
    private String spec;

    /**
     * 批次
     */
    private String batch;

    /**
     * 有效日期
     */
    private Date effectiveTime;
    
    /**
     * 时间段内总取出数量
     */
    private Integer allOutNumber = 0;

    
    /**
     * 时间段内总退药数量
     */
    private Integer allRetreatNumber = 0;

    
    /**
     * 时间段内总报损数量
     */
    private Integer allLoseNumber = 0;

    
    /**
     * 时间段内总实际数量
     */
    private Integer allActualNumber = 0;

    /**
     * 取药日期
     */
    private Date outTime;

    /**
     * 经办人
     */
    private String operator;

    /**
     * 领用人
     */
    private String receiveName;

    /**
     * 取药类型：1 普通取药，2 手术取药
     */
    private String outType;

    /**
     * 手术id
     */
    private String regOptId;

    /**
     * 局点id
     */
    private String beid;

    /**
     * 计费数量
     */
    private Integer chargeAmount;
    
    private String receive;
    /**
     * 单位
     */
    private String unit;

    
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
    
	public Integer getStorageId()
	{
		return storageId;
	}

	public void setStorageId(Integer storageId)
	{
		this.storageId = storageId;
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public String getFirm()
	{
		return firm;
	}

	public void setFirm(String firm)
	{
		this.firm = firm;
	}

	public String getSpec()
	{
		return spec;
	}

	public void setSpec(String spec)
	{
		this.spec = spec;
	}

	public String getBatch()
	{
		return batch;
	}

	public void setBatch(String batch)
	{
		this.batch = batch;
	}

	public Date getEffectiveTime()
	{
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime)
	{
		this.effectiveTime = effectiveTime;
	}

	public Integer getAllOutNumber()
	{
		return allOutNumber;
	}

	public void setAllOutNumber(Integer allOutNumber)
	{
		this.allOutNumber = allOutNumber;
	}

	public Integer getAllRetreatNumber()
	{
		return allRetreatNumber;
	}

	public void setAllRetreatNumber(Integer allRetreatNumber)
	{
		this.allRetreatNumber = allRetreatNumber;
	}

	public Integer getAllLoseNumber()
	{
		return allLoseNumber;
	}

	public void setAllLoseNumber(Integer allLoseNumber)
	{
		this.allLoseNumber = allLoseNumber;
	}

	public Integer getAllActualNumber()
	{
		return allActualNumber;
	}

	public void setAllActualNumber(Integer allActualNumber)
	{
		this.allActualNumber = allActualNumber;
	}



	public Date getOutTime()
	{
		return outTime;
	}

	public void setOutTime(Date outTime)
	{
		this.outTime = outTime;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getReceiveName()
	{
		return receiveName;
	}

	public void setReceiveName(String receiveName)
	{
		this.receiveName = receiveName;
	}

	public String getOutType()
	{
		return outType;
	}

	public void setOutType(String outType)
	{
		this.outType = outType;
	}

	public String getRegOptId()
	{
		return regOptId;
	}

	public void setRegOptId(String regOptId)
	{
		this.regOptId = regOptId;
	}

	public String getBeid()
	{
		return beid;
	}

	public void setBeid(String beid)
	{
		this.beid = beid;
	}
    
}
