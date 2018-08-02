/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-27 下午2:42:59    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.formbean;

import java.io.Serializable;
import java.util.Date;

import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * Title: SafeCheckFormBean.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-27 下午2:42:59
 */

public class AnaesPacuRecFormBean implements Serializable {
	private String id;
	private String regOptId;
	private String name;
	private String sex;
	private String age;
	private String ageMon;
	private Integer ageDay;
	private String hid; // 住院号
	private String operaDate;
	private String bed;
	private String regionName; // 病区
	private String optLatterDiag; // 诊断名称
	private String optRealOper; // 手术名称
	private String anaesMethod;// 实际麻醉方法
	private String roomName;// 复苏室名称
	private String roomId;
	private String bedName;
	private String operatLevelName;// 手术等级
	private String asaLevelName;// asa分级
	private String asaLevel;// asa分级
	private Float weight;// 体重
	private String anesthetistName;// 麻醉医生
	private String deptName;
	private String operRoomName;//手术室
	private String circunurseName1;//巡回护士
	
	@ApiModelProperty(value = "气管插管  1是 0否")
	private String trachealIntub;

	@ApiModelProperty(value = "气管导管  1已拔 0未拔")
	private String trachealCatheter;
	
	@ApiModelProperty(value = "意识  1未清醒 2会意 3睁眼 4哭啼")
	private String conscious;
	
	@ApiModelProperty(value = "上呼吸机时间")
    private Date ventilatorTime;
	
	@ApiModelProperty(value = "拔气管导管 1未拔")
    private String trachealCatheterExt;
	
	@ApiModelProperty(value = "拔气管导管时间")
    private Date trachealExtTime;
	
	@ApiModelProperty(value = "切口敷料  1干净 2渗血 3脱落")
	private String cutDressings;
	
	@ApiModelProperty(value = "吸痰 1有 0无")
	private String sputumSuction;
	
	@ApiModelProperty(value = "呕吐 1有 0无")
	private String vomit;
	
	@ApiModelProperty(value = "神志")
	private String sense;
	
	@ApiModelProperty(value = "静脉通畅 1通畅 0不通畅")
	private String venousPatency;
	
	@ApiModelProperty(value = "steward评分")
	private String stewardSco;
	
	private String operEndTime;

	
	public String getCircunurseName1() {
		return circunurseName1;
	}

	public void setCircunurseName1(String circunurseName1) {
		this.circunurseName1 = circunurseName1;
	}

	public String getOperEndTime() {
		return operEndTime;
	}

	public void setOperEndTime(String operEndTime) {
		this.operEndTime = operEndTime;
	}

	public String getOperRoomName() {
		return operRoomName;
	}

	public void setOperRoomName(String operRoomName) {
		this.operRoomName = operRoomName;
	}

	public String getTrachealIntub() {
		return trachealIntub;
	}

	public void setTrachealIntub(String trachealIntub) {
		this.trachealIntub = trachealIntub;
	}

	public String getTrachealCatheter() {
		return trachealCatheter;
	}

	public void setTrachealCatheter(String trachealCatheter) {
		this.trachealCatheter = trachealCatheter;
	}

	public String getConscious() {
		return conscious;
	}

	public void setConscious(String conscious) {
		this.conscious = conscious;
	}

	public Date getVentilatorTime() {
		return ventilatorTime;
	}

	public void setVentilatorTime(Date ventilatorTime) {
		this.ventilatorTime = ventilatorTime;
	}

	public String getTrachealCatheterExt() {
		return trachealCatheterExt;
	}

	public void setTrachealCatheterExt(String trachealCatheterExt) {
		this.trachealCatheterExt = trachealCatheterExt;
	}

	public Date getTrachealExtTime() {
		return trachealExtTime;
	}

	public void setTrachealExtTime(Date trachealExtTime) {
		this.trachealExtTime = trachealExtTime;
	}

	public String getCutDressings() {
		return cutDressings;
	}

	public void setCutDressings(String cutDressings) {
		this.cutDressings = cutDressings;
	}

	public String getSputumSuction() {
		return sputumSuction;
	}

	public void setSputumSuction(String sputumSuction) {
		this.sputumSuction = sputumSuction;
	}

	public String getVomit() {
		return vomit;
	}

	public void setVomit(String vomit) {
		this.vomit = vomit;
	}

	public String getSense() {
		return sense;
	}

	public void setSense(String sense) {
		this.sense = sense;
	}

	public String getVenousPatency() {
		return venousPatency;
	}

	public void setVenousPatency(String venousPatency) {
		this.venousPatency = venousPatency;
	}

	public String getStewardSco() {
		return stewardSco;
	}

	public void setStewardSco(String stewardSco) {
		this.stewardSco = stewardSco;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAnesthetistName() {
		return anesthetistName;
	}

	public void setAnesthetistName(String anesthetistName) {
		this.anesthetistName = anesthetistName;
	}

	private String bedId;

	/**
	 * PACU房间Id
	 */
	private Integer pacuRoomId;

	/**
	 * 当前患者复苏状态状态：0，未开始；1，开始复苏；2，复苏完成
	 */
	private String processState;

	/**
	 * 入室时间
	 */
	private Date enterTime;

	/**
	 * 出室时间
	 */
	private Date exitTime;

	/**
	 * 完成时间
	 */
	private Date finishTime;

	/**
	 * 出室去向 1=回病房 2=ICU 3=离院 4=死亡
	 */
	private String leaveTo;

	/**
	 * 随身管道
	 */
	private String portablePipe;

	/**
	 * 麻醉方式
	 */
	private String anaesType;

	/**
	 * 随身物品，1，影像；2，衣服；3：裤子；4：被子；5：鞋子；6：药物
	 */
	private String portableRes;

	/**
	 * 其他随身物品
	 */
	private String portableResOther;

	/**
	 * 医生签名
	 */
	private String docSign;

	/**
	 * 护士签名
	 */
	private String nurseSign;

	public String getAsaLevel() {
		return asaLevel;
	}

	public void setAsaLevel(String asaLevel) {
		this.asaLevel = asaLevel;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getAsaLevelName() {
		return asaLevelName;
	}

	public void setAsaLevelName(String asaLevelName) {
		this.asaLevelName = asaLevelName;
	}

	public Float getWeight() {
		return weight;
	}

	public String getOperatLevelName() {
		return operatLevelName;
	}

	public void setOperatLevelName(String operatLevelName) {
		this.operatLevelName = operatLevelName;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return UserUtils.getAge(age, ageMon, ageDay);
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAgeMon() {
		return ageMon;
	}

	public void setAgeMon(String ageMon) {
		this.ageMon = ageMon;
	}

	public Integer getAgeDay() {
		return ageDay;
	}

	public void setAgeDay(Integer ageDay) {
		this.ageDay = ageDay;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getOperaDate() {
		return operaDate;
	}

	public void setOperaDate(String operaDate) {
		this.operaDate = operaDate;
	}

	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getOptLatterDiag() {
		return optLatterDiag;
	}

	public void setOptLatterDiag(String optLatterDiag) {
		this.optLatterDiag = optLatterDiag;
	}

	public String getOptRealOper() {
		return optRealOper;
	}

	public void setOptRealOper(String optRealOper) {
		this.optRealOper = optRealOper;
	}

	public String getAnaesMethod() {
		return anaesMethod;
	}

	public void setAnaesMethod(String anaesMethod) {
		this.anaesMethod = anaesMethod;
	}

	public Integer getPacuRoomId() {
		return pacuRoomId;
	}

	public void setPacuRoomId(Integer pacuRoomId) {
		this.pacuRoomId = pacuRoomId;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(String leaveTo) {
		this.leaveTo = leaveTo;
	}

	public String getPortablePipe() {
		return portablePipe;
	}

	public void setPortablePipe(String portablePipe) {
		this.portablePipe = portablePipe;
	}

	public String getAnaesType() {
		return anaesType;
	}

	public void setAnaesType(String anaesType) {
		this.anaesType = anaesType;
	}

	public String getPortableRes() {
		return portableRes;
	}

	public void setPortableRes(String portableRes) {
		this.portableRes = portableRes;
	}

	public String getPortableResOther() {
		return portableResOther;
	}

	public void setPortableResOther(String portableResOther) {
		this.portableResOther = portableResOther;
	}

	public String getDocSign() {
		return docSign;
	}

	public void setDocSign(String docSign) {
		this.docSign = docSign;
	}

	public String getNurseSign() {
		return nurseSign;
	}

	public void setNurseSign(String nurseSign) {
		this.nurseSign = nurseSign;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getBedId() {
		return bedId;
	}

	public void setBedId(String bedId) {
		this.bedId = bedId;
	}

}
