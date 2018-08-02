package com.digihealth.anesthesia.interfacedata.po;

import java.io.Serializable;

public class Blood implements Serializable{ 
	
	private String id;
	private String bloodId;
	private String bloodName;
	private Float bloodDosage;
	private String bloodUnit;
	private String bloodType;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	public String getBloodId() {
		return bloodId;
	}
	public void setBloodId(String bloodId) {
		this.bloodId = bloodId;
	}
	public String getBloodName() {
		return bloodName;
	}
	public void setBloodName(String bloodName) {
		this.bloodName = bloodName;
	}
	public Float getBloodDosage() {
		return bloodDosage;
	}
	public void setBloodDosage(Float bloodDosage) {
		this.bloodDosage = bloodDosage;
	}
	public String getBloodUnit() {
		return bloodUnit;
	}
	public void setBloodUnit(String bloodUnit) {
		this.bloodUnit = bloodUnit;
	}
	

	
}