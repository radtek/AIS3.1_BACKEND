package com.digihealth.anesthesia.research.formbean;

import java.io.Serializable;

public class SearchDeptOperatCountByOptlev implements Serializable{

	private String deptId;
	private String deptName;
	private Long fst;
	private Long sec;
	private Long thd;
	private Long fou;
	private Long total;
	private Long incTotal;
	

	
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
	public Long getIncTotal() {
		return incTotal;
	}
	public void setIncTotal(Long incTotal) {
		this.incTotal = incTotal;
	}
	public Long getFst() {
		return fst;
	}
	public void setFst(Long fst) {
		this.fst = fst;
	}
	public Long getSec() {
		return sec;
	}
	public void setSec(Long sec) {
		this.sec = sec;
	}
	public Long getThd() {
		return thd;
	}
	public void setThd(Long thd) {
		this.thd = thd;
	}
	public Long getFou() {
		return fou;
	}
	public void setFou(Long fou) {
		this.fou = fou;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}
