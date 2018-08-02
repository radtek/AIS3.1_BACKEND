package com.digihealth.anesthesia.research.formbean;

import java.io.Serializable;

public class SearchDeptOperatCountByAsalev implements Serializable{

	private String deptId;
	private String deptName;
	private Long fst;
	private Long sec;
	private Long thd;
	private Long fou;
	private Long fif;
	private Long six;
	private Long total;
	private String anaMedId;
	private String anaMedName;
	

	public String getAnaMedId()
    {
        return anaMedId;
    }
    public void setAnaMedId(String anaMedId)
    {
        this.anaMedId = anaMedId;
    }
    public String getAnaMedName()
    {
        return anaMedName;
    }
    public void setAnaMedName(String anaMedName)
    {
        this.anaMedName = anaMedName;
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
	public Long getFif() {
		return fif;
	}
	public void setFif(Long fif) {
		this.fif = fif;
	}
	public Long getSix() {
		return six;
	}
	public void setSix(Long six) {
		this.six = six;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}
