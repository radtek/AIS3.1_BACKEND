package com.digihealth.anesthesia.doc.formbean;

import java.util.Date;

public class PostOperRegardFormBean {
	/**
	 * 术后回视主键
	 */
	private String id;

	/**
	 * 手术id
	 */
	private String regOptId;

	/**
	 * 访视护士解释工作是否到位
	 */
	private Integer explain;

	/**
	 * 术前的特殊手术体位指导是否有用
	 */
	private Integer preOperOptbody;

	/**
	 * 患者遮盖和保暖措施
	 */
	private Integer coverWarm;

	/**
	 * 术中手术体位摆放是否舒适
	 */
	private Integer operingOptbodyComfort;

	/**
	 * 术后伤口恢复情况
	 */
	private Integer postOperRestore;

	/**
	 * 通过访视对你有哪些帮助
	 */
	private Integer byInterviewHelp;

	/**
	 * 对手术室护士工作的满意度
	 */
	private Integer satisfacation;

	/**
	 * 建议
	 */
	private String sugest;

	/**
	 * 访视者签名
	 */
	private String interviewUser;

	private String interviewUsername;

	/**
	 * 家属签名
	 */
	private String interviewRelation;

	/**
	 * 访视时间
	 */
	private Date interviewTime;

	/**
	 * END NO_END
	 */
	private String processState;

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

	public Integer getExplain() {
		return explain;
	}

	public void setExplain(Integer explain) {
		this.explain = explain;
	}

	public Integer getPreOperOptbody() {
		return preOperOptbody;
	}

	public void setPreOperOptbody(Integer preOperOptbody) {
		this.preOperOptbody = preOperOptbody;
	}

	public Integer getCoverWarm() {
		return coverWarm;
	}

	public void setCoverWarm(Integer coverWarm) {
		this.coverWarm = coverWarm;
	}

	public Integer getOperingOptbodyComfort() {
		return operingOptbodyComfort;
	}

	public void setOperingOptbodyComfort(Integer operingOptbodyComfort) {
		this.operingOptbodyComfort = operingOptbodyComfort;
	}

	public Integer getPostOperRestore() {
		return postOperRestore;
	}

	public void setPostOperRestore(Integer postOperRestore) {
		this.postOperRestore = postOperRestore;
	}

	public Integer getByInterviewHelp() {
		return byInterviewHelp;
	}

	public void setByInterviewHelp(Integer byInterviewHelp) {
		this.byInterviewHelp = byInterviewHelp;
	}

	public Integer getSatisfacation() {
		return satisfacation;
	}

	public void setSatisfacation(Integer satisfacation) {
		this.satisfacation = satisfacation;
	}

	public String getSugest() {
		return sugest;
	}

	public void setSugest(String sugest) {
		this.sugest = sugest;
	}

	public String getInterviewUser() {
		return interviewUser;
	}

	public void setInterviewUser(String interviewUser) {
		this.interviewUser = interviewUser;
	}

	public String getInterviewUsername() {
		return interviewUsername;
	}

	public void setInterviewUsername(String interviewUsername) {
		this.interviewUsername = interviewUsername;
	}

	public String getInterviewRelation() {
		return interviewRelation;
	}

	public void setInterviewRelation(String interviewRelation) {
		this.interviewRelation = interviewRelation;
	}

	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

}
