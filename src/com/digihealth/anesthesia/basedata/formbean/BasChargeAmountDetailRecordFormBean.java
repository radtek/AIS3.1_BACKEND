package com.digihealth.anesthesia.basedata.formbean;


public class BasChargeAmountDetailRecordFormBean
{

    /**
     * 患者名称
     */
    private String name;

    /**
     * 科室名称
     */
    private String deptName;

    /**
     * 单位
     */
    private String unit;

    /**
     * 记账时间
     */
    private String chargeDate;

    /**
     * 计费数量
     */
    private Integer chargeAmount;
    /**
     * 规格
     * @return
     */
    private String spec;
    /**
     * 记账人
     * @return
     */
    private String receiveName;
    
    

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(String chargeDate) {
		this.chargeDate = chargeDate;
	}

	public Integer getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Integer chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
    

    
}
