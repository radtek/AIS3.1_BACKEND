package com.digihealth.anesthesia.doc.formbean;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class OptForZdyDocFormBean implements Serializable
{

	/** 描述 (@author: chengwang) */

	private static final long serialVersionUID = 1L;
	// 手术ID
	@ApiModelProperty(value = "患者id")
	private String regOptId;
	// 姓名
	@ApiModelProperty(value = "患者姓名")
	private String name;
	// 住院号
	@ApiModelProperty(value = "住院号")
	private String hid;
	// 性别
	@ApiModelProperty(value = "性别")
	private String sex;
	// 出生
	@ApiModelProperty(value = "出生")
	private String birthday;
	// 年龄
	@ApiModelProperty(value = "年龄")
	private String age;

	@ApiModelProperty(value = "年龄月")
	private String ageMon;

	@ApiModelProperty(value = "年龄天")
	private Integer ageDay;
	// 床号
	@ApiModelProperty(value = "床号")
	private String bed;
	// 病区名称
	@ApiModelProperty(value = "病区名称")
	private String regionName;
	// 科室名称
	@ApiModelProperty(value = "科室名称")
	private String deptName;
	// 拟施手术名称
	@ApiModelProperty(value = "拟施手术名称")
	private String designedOptName;
	// 术前诊断名称
	@ApiModelProperty(value = "术前诊断名称")
	private String diagnosisName;
	// 手术日期
	@ApiModelProperty(value = "手术日期")
	private String operaDate;
	@ApiModelProperty(value = "开始时间")
	private String startTime;
	@ApiModelProperty(value = "结束时间")
	private String endTime;
	// 手术室名称
	@ApiModelProperty(value = "手术室名称")
	private String operRoomName;
	@ApiModelProperty(value = "卫生护士名字")
	private String healthNurseName;
	// 器械护士1
	@ApiModelProperty(value = "器械护士1名字")
	private String instrnurseName1;
	// 器械护士2
	@ApiModelProperty(value = "器械护士2名字")
	private String instrnurseName2;
	// 巡回护士1
	@ApiModelProperty(value = "巡回护士1名字")
	private String circunurseName1;
	// 巡回护士2
	@ApiModelProperty(value = "巡回护士2名字")
	private String circunurseName2;
	// 麻醉医生
	@ApiModelProperty(value = "麻醉医生名字")
	private String anesthetistName;
	// 体重
	@ApiModelProperty(value = "体重")
	private Float weight;
	// 身高
	@ApiModelProperty(value = "身高")
	private Float height;
	// 药物过敏
	@ApiModelProperty(value = "药物过敏")
	private String hypersusceptibility;
	// 手术等级
	@ApiModelProperty(value = "手术等级")
	private String optLevel;
	// 拟施麻醉方法名称
	@ApiModelProperty(value = "拟施麻醉方法名称")
	private String designedAnaesMethodName;
	// 手术医生NAME
	@ApiModelProperty(value = "手术医生名字")
	private String operatorName;
	// 灌注医生
	@ApiModelProperty(value = "灌注医生名字")
	private String perfusiondoctorName;
	@ApiModelProperty(value = "助手医生名字")
	private String assistantName;
	@ApiModelProperty(value = "手术停止原因")
	private String reasons;
	@ApiModelProperty(value = "切口等级")
	private Integer cutLevel;
	@ApiModelProperty(value = "台次")
	private String pcs;// 台次
	@ApiModelProperty(value = "证件号")
    private String identityNo;//证件号
	public String getRegOptId()
	{
		return regOptId;
	}
	public void setRegOptId(String regOptId)
	{
		this.regOptId = regOptId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getHid()
	{
		return hid;
	}
	public void setHid(String hid)
	{
		this.hid = hid;
	}
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	public String getBirthday()
	{
		return birthday;
	}
	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}
	public String getAge()
	{
		return age;
	}
	public void setAge(String age)
	{
		this.age = age;
	}
	public String getAgeMon()
	{
		return ageMon;
	}
	public void setAgeMon(String ageMon)
	{
		this.ageMon = ageMon;
	}
	public Integer getAgeDay()
	{
		return ageDay;
	}
	public void setAgeDay(Integer ageDay)
	{
		this.ageDay = ageDay;
	}
	public String getBed()
	{
		return bed;
	}
	public void setBed(String bed)
	{
		this.bed = bed;
	}
	public String getRegionName()
	{
		return regionName;
	}
	public void setRegionName(String regionName)
	{
		this.regionName = regionName;
	}
	public String getDeptName()
	{
		return deptName;
	}
	public void setDeptName(String deptName)
	{
		this.deptName = deptName;
	}
	public String getDesignedOptName()
	{
		return designedOptName;
	}
	public void setDesignedOptName(String designedOptName)
	{
		this.designedOptName = designedOptName;
	}
	public String getDiagnosisName()
	{
		return diagnosisName;
	}
	public void setDiagnosisName(String diagnosisName)
	{
		this.diagnosisName = diagnosisName;
	}
	public String getOperaDate()
	{
		return operaDate;
	}
	public void setOperaDate(String operaDate)
	{
		this.operaDate = operaDate;
	}
	public String getStartTime()
	{
		return startTime;
	}
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public String getOperRoomName()
	{
		return operRoomName;
	}
	public void setOperRoomName(String operRoomName)
	{
		this.operRoomName = operRoomName;
	}
	public String getHealthNurseName()
	{
		return healthNurseName;
	}
	public void setHealthNurseName(String healthNurseName)
	{
		this.healthNurseName = healthNurseName;
	}
	public String getInstrnurseName1()
	{
		return instrnurseName1;
	}
	public void setInstrnurseName1(String instrnurseName1)
	{
		this.instrnurseName1 = instrnurseName1;
	}
	public String getInstrnurseName2()
	{
		return instrnurseName2;
	}
	public void setInstrnurseName2(String instrnurseName2)
	{
		this.instrnurseName2 = instrnurseName2;
	}
	public String getCircunurseName1()
	{
		return circunurseName1;
	}
	public void setCircunurseName1(String circunurseName1)
	{
		this.circunurseName1 = circunurseName1;
	}
	public String getCircunurseName2()
	{
		return circunurseName2;
	}
	public void setCircunurseName2(String circunurseName2)
	{
		this.circunurseName2 = circunurseName2;
	}
	public String getAnesthetistName()
	{
		return anesthetistName;
	}
	public void setAnesthetistName(String anesthetistName)
	{
		this.anesthetistName = anesthetistName;
	}
	public Float getWeight()
	{
		return weight;
	}
	public void setWeight(Float weight)
	{
		this.weight = weight;
	}
	public Float getHeight()
	{
		return height;
	}
	public void setHeight(Float height)
	{
		this.height = height;
	}
	public String getHypersusceptibility()
	{
		return hypersusceptibility;
	}
	public void setHypersusceptibility(String hypersusceptibility)
	{
		this.hypersusceptibility = hypersusceptibility;
	}
	public String getOptLevel()
	{
		return optLevel;
	}
	public void setOptLevel(String optLevel)
	{
		this.optLevel = optLevel;
	}
	public String getDesignedAnaesMethodName()
	{
		return designedAnaesMethodName;
	}
	public void setDesignedAnaesMethodName(String designedAnaesMethodName)
	{
		this.designedAnaesMethodName = designedAnaesMethodName;
	}
	public String getOperatorName()
	{
		return operatorName;
	}
	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
	public String getPerfusiondoctorName()
	{
		return perfusiondoctorName;
	}
	public void setPerfusiondoctorName(String perfusiondoctorName)
	{
		this.perfusiondoctorName = perfusiondoctorName;
	}
	public String getAssistantName()
	{
		return assistantName;
	}
	public void setAssistantName(String assistantName)
	{
		this.assistantName = assistantName;
	}
	public String getReasons()
	{
		return reasons;
	}
	public void setReasons(String reasons)
	{
		this.reasons = reasons;
	}
	public Integer getCutLevel()
	{
		return cutLevel;
	}
	public void setCutLevel(Integer cutLevel)
	{
		this.cutLevel = cutLevel;
	}
	public String getPcs()
	{
		return pcs;
	}
	public void setPcs(String pcs)
	{
		this.pcs = pcs;
	}
	public String getIdentityNo()
	{
		return identityNo;
	}
	public void setIdentityNo(String identityNo)
	{
		this.identityNo = identityNo;
	}
	
}
