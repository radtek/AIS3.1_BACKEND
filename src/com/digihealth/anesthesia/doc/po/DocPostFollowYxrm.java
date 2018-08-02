package com.digihealth.anesthesia.doc.po;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "麻醉后访视记录")
public class DocPostFollowYxrm {
	
	@ApiModelProperty(value = "编号")
    private String postFollowYxrmId;

	@ApiModelProperty(value = "访视记录Id")
    private String postFollowId;

    /**
     * 血压
     */
	@ApiModelProperty(value = "血压")
    private String beforeBloodPre;

    /**
     * 心率
     */
	@ApiModelProperty(value = "心率")
    private Integer beforeHeartrate;

    /**
     * 呼吸
     */
	@ApiModelProperty(value = "呼吸")
    private Integer beforeBreath;

    /**
     * 血氧饱和度
     */
	@ApiModelProperty(value = "血氧饱和度")
    private Integer beforeSpo2;

    /**
     * 神经系统
     */
	@ApiModelProperty(value = "神经系统")
    private String beforeMentalState;

    /**
     * 感觉
     */
	@ApiModelProperty(value = "感觉")
    private String beforeFeel;

    /**
     * 运动功能
     */
	@ApiModelProperty(value = "运动功能")
    private String beforeMotorFunction;

    /**
     * 麻醉有关导管
     */
	@ApiModelProperty(value = "麻醉有关导管")
    private String beforeAnaesPipe;

    /**
     * 拔除时间
     */
	@ApiModelProperty(value = "拔除时间")
    private Date beforeRemovalTime;

	@ApiModelProperty(value = "术前其他")
    private String beforeOther;

    /**
     * 心率
     */
	@ApiModelProperty(value = "心率")
    private Integer afterHeartrate;

    /**
     * 呼吸
     */
	@ApiModelProperty(value = "呼吸")
    private Integer afterBreath;

    /**
     * 血压
     */
	@ApiModelProperty(value = "血压")
    private String afterBloodPre;
    
    /**
     * 血氧饱和度
     */
	@ApiModelProperty(value = "血氧饱和度")
    private Integer afterSpo2;

    /**
     * 神经系统
     */
	@ApiModelProperty(value = "神经系统")
    private String afterMentalState;

    /**
     * 感觉
     */
	@ApiModelProperty(value = "感觉")
    private String afterFeel;

    /**
     * 运动功能
     */
	@ApiModelProperty(value = "运动功能")
    private String afterMotorFunction;

    /**
     * 麻醉有关导管
     */
	@ApiModelProperty(value = "麻醉有关导管")
    private String afterAnaesPipe;

    /**
     * 其他
     */
	@ApiModelProperty(value = "术后其他")
    private String afterOther;

	@ApiModelProperty(value = "签名时间")
    private Date signTime;

	@ApiModelProperty(value = "签名")
    private String signName;

    public String getPostFollowYxrmId() {
        return postFollowYxrmId;
    }

    public void setPostFollowYxrmId(String postFollowYxrmId) {
        this.postFollowYxrmId = postFollowYxrmId == null ? null : postFollowYxrmId.trim();
    }

    public String getPostFollowId() {
        return postFollowId;
    }

    public void setPostFollowId(String postFollowId) {
        this.postFollowId = postFollowId == null ? null : postFollowId.trim();
    }

    public String getBeforeBloodPre() {
        return beforeBloodPre;
    }

    public void setBeforeBloodPre(String beforeBloodPre) {
        this.beforeBloodPre = beforeBloodPre == null ? null : beforeBloodPre.trim();
    }

    public Integer getBeforeHeartrate() {
        return beforeHeartrate;
    }

    public void setBeforeHeartrate(Integer beforeHeartrate) {
        this.beforeHeartrate = beforeHeartrate;
    }

    public Integer getBeforeBreath() {
        return beforeBreath;
    }

    public void setBeforeBreath(Integer beforeBreath) {
        this.beforeBreath = beforeBreath;
    }

    public Integer getBeforeSpo2() {
        return beforeSpo2;
    }

    public void setBeforeSpo2(Integer beforeSpo2) {
        this.beforeSpo2 = beforeSpo2;
    }

    public String getBeforeMentalState() {
        return beforeMentalState;
    }

    public void setBeforeMentalState(String beforeMentalState) {
        this.beforeMentalState = beforeMentalState == null ? null : beforeMentalState.trim();
    }

    public String getBeforeFeel() {
        return beforeFeel;
    }

    public void setBeforeFeel(String beforeFeel) {
        this.beforeFeel = beforeFeel == null ? null : beforeFeel.trim();
    }

    public String getBeforeMotorFunction() {
        return beforeMotorFunction;
    }

    public void setBeforeMotorFunction(String beforeMotorFunction) {
        this.beforeMotorFunction = beforeMotorFunction == null ? null : beforeMotorFunction.trim();
    }

    public String getBeforeAnaesPipe() {
        return beforeAnaesPipe;
    }

    public void setBeforeAnaesPipe(String beforeAnaesPipe) {
        this.beforeAnaesPipe = beforeAnaesPipe == null ? null : beforeAnaesPipe.trim();
    }

    public Date getBeforeRemovalTime()
	{
		return beforeRemovalTime;
	}

	public void setBeforeRemovalTime(Date beforeRemovalTime)
	{
		this.beforeRemovalTime = beforeRemovalTime;
	}

	public String getBeforeOther() {
        return beforeOther;
    }

    public void setBeforeOther(String beforeOther) {
        this.beforeOther = beforeOther == null ? null : beforeOther.trim();
    }

    public Integer getAfterHeartrate() {
        return afterHeartrate;
    }

    public void setAfterHeartrate(Integer afterHeartrate) {
        this.afterHeartrate = afterHeartrate;
    }

    public Integer getAfterBreath() {
        return afterBreath;
    }

    public void setAfterBreath(Integer afterBreath) {
        this.afterBreath = afterBreath;
    }

    public String getAfterBloodPre() {
        return afterBloodPre;
    }

    public void setAfterBloodPre(String afterBloodPre) {
        this.afterBloodPre = afterBloodPre == null ? null : afterBloodPre.trim();
    }

    public String getAfterMentalState() {
        return afterMentalState;
    }

    public void setAfterMentalState(String afterMentalState) {
        this.afterMentalState = afterMentalState == null ? null : afterMentalState.trim();
    }

    public String getAfterFeel() {
        return afterFeel;
    }

    public void setAfterFeel(String afterFeel) {
        this.afterFeel = afterFeel == null ? null : afterFeel.trim();
    }

    public String getAfterMotorFunction() {
        return afterMotorFunction;
    }

    public void setAfterMotorFunction(String afterMotorFunction) {
        this.afterMotorFunction = afterMotorFunction == null ? null : afterMotorFunction.trim();
    }

    public String getAfterAnaesPipe() {
        return afterAnaesPipe;
    }

    public void setAfterAnaesPipe(String afterAnaesPipe) {
        this.afterAnaesPipe = afterAnaesPipe == null ? null : afterAnaesPipe.trim();
    }

    public String getAfterOther() {
        return afterOther;
    }

    public void setAfterOther(String afterOther) {
        this.afterOther = afterOther == null ? null : afterOther.trim();
    }

    public Date getSignTime()
	{
		return signTime;
	}

	public void setSignTime(Date signTime)
	{
		this.signTime = signTime;
	}

	public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

	public Integer getAfterSpo2()
	{
		return afterSpo2;
	}

	public void setAfterSpo2(Integer afterSpo2)
	{
		this.afterSpo2 = afterSpo2;
	}
}
