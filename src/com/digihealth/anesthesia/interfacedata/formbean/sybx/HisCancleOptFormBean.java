package com.digihealth.anesthesia.interfacedata.formbean.sybx;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class HisCancleOptFormBean implements Serializable {

	private static final long serialVersionUID = 7562638870069336973L;

	/**
	 * 住院号
	 */
	private String hid;
	private String deptId;
	private String deptName;
	/**
	 * 手术预约号
	 */
	private String preengagementnumber;
	private String state;

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPreengagementnumber() {
		return preengagementnumber;
	}

	public void setPreengagementnumber(String preengagementnumber) {
		this.preengagementnumber = preengagementnumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
