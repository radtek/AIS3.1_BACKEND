package com.digihealth.anesthesia.evt.formbean;

import java.io.Serializable;

public class ChangeValueFormbean implements Serializable{
    /**
     * 修改前值
     */
    private String oldValue;

    /**
     * 修改后值
     */
    private String newValue;
    /**
     * 对应表的属性值
     */
    private String modProperty;
    
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getModProperty() {
		return modProperty;
	}
	public void setModProperty(String modProperty) {
		this.modProperty = modProperty;
	}

	
}
