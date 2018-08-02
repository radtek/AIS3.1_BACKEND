/*
 * DocPostFollowRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "麻醉术后随访记录单对象")
public class DocPostFollowRecord {
	@ApiModelProperty(value = "主键id")
    private String postFollowId;

	@ApiModelProperty(value = "患者id")
    private String regOptId;

    /**
     * 血压
     */
	@ApiModelProperty(value = "血压")
    private String bloodPressure;

    /**
     * 脉搏
     */
	@ApiModelProperty(value = "脉搏")
    private String pulse;

    /**
     * 全麻术后其他特殊情况处理
     */
	@ApiModelProperty(value = "全麻术后其他特殊情况处理")
    private String generalExceptCase;

    /**
     * 椎管内术后其他特殊情况处理
     */
	@ApiModelProperty(value = "椎管内术后其他特殊情况处理")
    private String spinalExceptCase;

    /**
     * 术后镇痛其他特殊情况说明
     */
	@ApiModelProperty(value = "术后镇痛其他特殊情况说明")
    private String analgesicExceptCase;

    /**
     * 麻醉医生
     */
	@ApiModelProperty(value = "麻醉医生")
    private String anesthetistId;

    /**
     * 二次插管
     */
	@ApiModelProperty(value = "二次插管")
    private Integer secondIntubat;

    /**
     * 插管时间
     */
	@ApiModelProperty(value = "插管时间")
    private Date intubatTime;

    /**
     * 完成状态:END,NO_END
     */
	@ApiModelProperty(value = "完成状态:END,NO_END")
    private String processState;

    /**
     * 是否死亡 1是 0否
     */
	@ApiModelProperty(value = "是否死亡 1是 0否")
    private Integer isDeath;

    /**
     * 死亡时间
     */
	@ApiModelProperty(value = "死亡时间")
    private Date deathTime;
	
	 /**
     * 呼吸
     */
	@ApiModelProperty(value = "呼吸")
    private String breath;
	
	 /**
     * SPO2
     */
	@ApiModelProperty(value = "SPO2")
    private String spo2;
	
	 /**
     * 情况处理后1
     */
	@ApiModelProperty(value = "情况处理后横线1")
    private String line1;
	
	 /**
     * 情况处理后2
     */
	@ApiModelProperty(value = "情况处理后横线2")
    private String line2;

	 /**
     * 体温
     */
    private String temp;

    /**
     * 气管导管拔出
     */
    private Integer tracheaTubeOut;

    /**
     * 术中知晓
     */
    private Integer intraoperAware;

    /**
     * 意识清醒
     */
    private Integer awareness;

    /**
     * 声音沙哑
     */
    private Integer hoarseness;

    /**
     * 喉咙痛
     */
    private Integer throatSore;

    /**
     * 循环稳定
     */
    private Integer cyclingStability;

    /**
     * 认知障碍
     */
    private Integer cognitiveDisorders;

    /**
     * 下肢肌力恢复
     */
    private Integer muscleRecovery;

    /**
     * 感觉异常
     */
    private Integer feelingAbnormal;

    /**
     * 脊麻后头痛
     */
    private Integer spinalAnaesHeadache;

    /**
     * 穿刺点压痛
     */
    private Integer punctureTenderness;

    /**
     * 尿潴留
     */
    private Integer uroschesis;

    /**
     * 尿管保留
     */
    private Integer urineTubeRetain;

    /**
     * 使用镇痛泵
     */
    private Integer analgesicPump;

    /**
     * 静息时疼痛评分
     */
    private Integer vasResting;

    /**
     * 活动时疼痛评分
     */
    private Integer vasActivity;

    /**
     * 呼吸抑制
     */
    private Integer breatheInhibi;

    /**
     * 镇静评分
     */
    private Integer ramsay;

    /**
     * 恶心评分
     */
    private Integer nauseaScore;

    /**
     * 呕吐评分
     */
    private Integer vomitScore;

    /**
     * 瘙痒评分
     */
    private Integer itchScore;

    /**
     * 备注
     */
    private String remark;

    /**
     * 日期
     */
    private String docDate;
	
	
    public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getBreath() {
		return breath;
	}

	public void setBreath(String breath) {
		this.breath = breath;
	}


	public String getSpo2() {
		return spo2;
	}

	public void setSpo2(String spo2) {
		this.spo2 = spo2;
	}

	public String getPostFollowId() {
        return postFollowId;
    }

    public void setPostFollowId(String postFollowId) {
        this.postFollowId = postFollowId == null ? null : postFollowId.trim();
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId == null ? null : regOptId.trim();
    }

    public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse == null ? null : pulse.trim();
    }

    public String getGeneralExceptCase() {
        return generalExceptCase;
    }

    public void setGeneralExceptCase(String generalExceptCase) {
        this.generalExceptCase = generalExceptCase == null ? null : generalExceptCase.trim();
    }

    public String getSpinalExceptCase() {
        return spinalExceptCase;
    }

    public void setSpinalExceptCase(String spinalExceptCase) {
        this.spinalExceptCase = spinalExceptCase == null ? null : spinalExceptCase.trim();
    }

    public String getAnalgesicExceptCase() {
        return analgesicExceptCase;
    }

    public void setAnalgesicExceptCase(String analgesicExceptCase) {
        this.analgesicExceptCase = analgesicExceptCase == null ? null : analgesicExceptCase.trim();
    }

    public String getAnesthetistId() {
        return anesthetistId;
    }

    public void setAnesthetistId(String anesthetistId) {
        this.anesthetistId = anesthetistId == null ? null : anesthetistId.trim();
    }

    public Integer getSecondIntubat() {
        return secondIntubat;
    }

    public void setSecondIntubat(Integer secondIntubat) {
        this.secondIntubat = secondIntubat;
    }

    public Date getIntubatTime() {
        return intubatTime;
    }

    public void setIntubatTime(Date intubatTime) {
        this.intubatTime = intubatTime;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState == null ? null : processState.trim();
    }

    public Integer getIsDeath() {
        return isDeath;
    }

    public void setIsDeath(Integer isDeath) {
        this.isDeath = isDeath;
    }

    public Date getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Date deathTime) {
        this.deathTime = deathTime;
    }

    public String getTemp()
    {
        return temp;
    }

    public void setTemp(String temp)
    {
        this.temp = temp;
    }

    public Integer getTracheaTubeOut()
    {
        return tracheaTubeOut;
    }

    public void setTracheaTubeOut(Integer tracheaTubeOut)
    {
        this.tracheaTubeOut = tracheaTubeOut;
    }

    public Integer getIntraoperAware()
    {
        return intraoperAware;
    }

    public void setIntraoperAware(Integer intraoperAware)
    {
        this.intraoperAware = intraoperAware;
    }

    public Integer getAwareness()
    {
        return awareness;
    }

    public void setAwareness(Integer awareness)
    {
        this.awareness = awareness;
    }

    public Integer getHoarseness()
    {
        return hoarseness;
    }

    public void setHoarseness(Integer hoarseness)
    {
        this.hoarseness = hoarseness;
    }

    public Integer getThroatSore()
    {
        return throatSore;
    }

    public void setThroatSore(Integer throatSore)
    {
        this.throatSore = throatSore;
    }

    public Integer getCyclingStability()
    {
        return cyclingStability;
    }

    public void setCyclingStability(Integer cyclingStability)
    {
        this.cyclingStability = cyclingStability;
    }

    public Integer getCognitiveDisorders()
    {
        return cognitiveDisorders;
    }

    public void setCognitiveDisorders(Integer cognitiveDisorders)
    {
        this.cognitiveDisorders = cognitiveDisorders;
    }

    public Integer getMuscleRecovery()
    {
        return muscleRecovery;
    }

    public void setMuscleRecovery(Integer muscleRecovery)
    {
        this.muscleRecovery = muscleRecovery;
    }

    public Integer getFeelingAbnormal()
    {
        return feelingAbnormal;
    }

    public void setFeelingAbnormal(Integer feelingAbnormal)
    {
        this.feelingAbnormal = feelingAbnormal;
    }

    public Integer getSpinalAnaesHeadache()
    {
        return spinalAnaesHeadache;
    }

    public void setSpinalAnaesHeadache(Integer spinalAnaesHeadache)
    {
        this.spinalAnaesHeadache = spinalAnaesHeadache;
    }

    public Integer getPunctureTenderness()
    {
        return punctureTenderness;
    }

    public void setPunctureTenderness(Integer punctureTenderness)
    {
        this.punctureTenderness = punctureTenderness;
    }

    public Integer getUroschesis()
    {
        return uroschesis;
    }

    public void setUroschesis(Integer uroschesis)
    {
        this.uroschesis = uroschesis;
    }

    public Integer getUrineTubeRetain()
    {
        return urineTubeRetain;
    }

    public void setUrineTubeRetain(Integer urineTubeRetain)
    {
        this.urineTubeRetain = urineTubeRetain;
    }

    public Integer getAnalgesicPump()
    {
        return analgesicPump;
    }

    public void setAnalgesicPump(Integer analgesicPump)
    {
        this.analgesicPump = analgesicPump;
    }

    public Integer getVasResting()
    {
        return vasResting;
    }

    public void setVasResting(Integer vasResting)
    {
        this.vasResting = vasResting;
    }

    public Integer getVasActivity()
    {
        return vasActivity;
    }

    public void setVasActivity(Integer vasActivity)
    {
        this.vasActivity = vasActivity;
    }

    public Integer getBreatheInhibi()
    {
        return breatheInhibi;
    }

    public void setBreatheInhibi(Integer breatheInhibi)
    {
        this.breatheInhibi = breatheInhibi;
    }

    public Integer getRamsay()
    {
        return ramsay;
    }

    public void setRamsay(Integer ramsay)
    {
        this.ramsay = ramsay;
    }

    public Integer getNauseaScore()
    {
        return nauseaScore;
    }

    public void setNauseaScore(Integer nauseaScore)
    {
        this.nauseaScore = nauseaScore;
    }

    public Integer getVomitScore()
    {
        return vomitScore;
    }

    public void setVomitScore(Integer vomitScore)
    {
        this.vomitScore = vomitScore;
    }

    public Integer getItchScore()
    {
        return itchScore;
    }

    public void setItchScore(Integer itchScore)
    {
        this.itchScore = itchScore;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getDocDate()
    {
        return docDate;
    }

    public void setDocDate(String docDate)
    {
        this.docDate = docDate;
    }
}