package com.digihealth.anesthesia.doc.formbean;

import java.io.Serializable;

public class BatchOperCostFormBean implements Serializable{
	
	private String primaryKeyId; //主键ID
	private String operCostType; // 1药品、输液  2手术费用、耗材
	
	
	public String getPrimaryKeyId() {
		return primaryKeyId;
	}
	public void setPrimaryKeyId(String primaryKeyId) {
		this.primaryKeyId = primaryKeyId;
	}
	public String getOperCostType() {
		return operCostType;
	}
	public void setOperCostType(String operCostType) {
		this.operCostType = operCostType;
	}

	
}