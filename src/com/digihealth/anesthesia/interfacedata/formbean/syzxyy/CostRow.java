package com.digihealth.anesthesia.interfacedata.formbean.syzxyy;

import javax.xml.bind.annotation.XmlElement;


public class CostRow
{
	//private String blh;//住院号
	private String operId;//手麻的收费项目ID
	private String actDept;//执行科室  药品的传 24010121         收费项目传 21050211
	private String recorder;//操作帐号
	//private String actTime;//计费日期
	private String priceId;//药品代码或收费代码
	private Float  amount;//数量
	//private String hisPriceid;//传固定 0
	private String xmdj;//项目单价 price priceMinPackage
	private String ypdw;//项目单位 medicine packageDosageAmount
	private String dwxs;//单位系数 price minPackageUnit
	private String xmlb; //1是项目  2是药品
	private String operjzxh;//pkItId
	
	
	public String getOperjzxh() {
		return operjzxh;
	}
	public void setOperjzxh(String operjzxh) {
		this.operjzxh = operjzxh;
	}
	public String getXmlb() {
		return xmlb;
	}
	public void setXmlb(String xmlb) {
		this.xmlb = xmlb;
	}
	public String getXmdj() {
		return xmdj;
	}
	public void setXmdj(String xmdj) {
		this.xmdj = xmdj;
	}
	public String getYpdw() {
		return ypdw;
	}
	public void setYpdw(String ypdw) {
		this.ypdw = ypdw;
	}
	public String getDwxs() {
		return dwxs;
	}
	public void setDwxs(String dwxs) {
		this.dwxs = dwxs;
	}
//	public String getBlh() {
//		return blh;
//	}
	@XmlElement(name = "oper_id")
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	@XmlElement(name = "act_dept")
	public String getActDept() {
		return actDept;
	}
	public void setActDept(String actDept) {
		this.actDept = actDept;
	}
//	@XmlElement(name = "act_time")
//	public String getActTime() {
//		return actTime;
//	}
//	public void setActTime(String actTime) {
//		this.actTime = actTime;
//	}
	@XmlElement(name = "price_id")
	public String getPriceId() {
		return priceId;
	}
	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}
//	@XmlElement(name = "his_priceid")
//	public String getHisPriceid() {
//		return hisPriceid;
//	}
//	public void setHisPriceid(String hisPriceid) {
//		this.hisPriceid = hisPriceid;
//	}
//	public void setBlh(String blh) {
//		this.blh = blh;
//	}
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
//	public String getIdm() {
//		return idm;
//	}
//	public void setIdm(String idm) {
//		this.idm = idm;
//	}
    
}
