package com.digihealth.anesthesia.interfacedata.po.yxrm;

import java.io.Serializable;

public class HisRegOpt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1834402316108670147L;
	private String reservenumber; 
	private String name; 
	private String age; 
	private Integer ageMon; 
	private Integer ageDay; 
	private Integer birthday; 
	private String sex; 
	private String medicalType; 
	private String credNumber;
	private String hid; 
	private String cid; 
	private Integer regionId; 
	private String regionName;
	private Integer deptId; 
	private String deptName; 
	private String bed; 
	private String diagCode; 
	private String diagName; 
	private String operCode; 
	private String operName; 
	private String surgeryDoctorId; 
	private String surgeryDoctorName; 
	private String assistantId; 
	private String assistantName; 
	private String weight; 
	private String height; 
	private String hbsag; 
	private String hcv; 
	private String hiv; 
	private String hp; 
	private String operDate; 
	private String operStartTime; 
	private String operEndTime; 
	private String dragAllergy; //药物过敏
	private String operLevel; //手术等级
	private String incisionLevel; //（Ⅰ、Ⅱ、Ⅲ、Ⅳ）
	private Integer operSource; //0:住院 1：门诊
	private Integer operType; //0为择期，1为急诊
	private String anaesType; //0为全麻，1为局麻
	private String anaesID; 
	private String anaesName; 
	private String createUser; 
	private Integer frontOperForbidTake; //术前禁食:0-否;1-是;
	private String frontOperSpecialCase;
    public String getReservenumber()
    {
        return reservenumber;
    }
    public void setReservenumber(String reservenumber)
    {
        this.reservenumber = reservenumber;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getAge()
    {
        return age;
    }
    public void setAge(String age)
    {
        this.age = age;
    }
    public Integer getAgeMon()
    {
        return ageMon;
    }
    public void setAgeMon(Integer ageMon)
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
    public Integer getBirthday()
    {
        return birthday;
    }
    public void setBirthday(Integer birthday)
    {
        this.birthday = birthday;
    }
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    public String getMedicalType()
    {
        return medicalType;
    }
    public void setMedicalType(String medicalType)
    {
        this.medicalType = medicalType;
    }
    public String getCredNumber()
    {
        return credNumber;
    }
    public void setCredNumber(String credNumber)
    {
        this.credNumber = credNumber;
    }
    public String getHid()
    {
        return hid;
    }
    public void setHid(String hid)
    {
        this.hid = hid;
    }
    public String getCid()
    {
        return cid;
    }
    public void setCid(String cid)
    {
        this.cid = cid;
    }
    public Integer getRegionId()
    {
        return regionId;
    }
    public void setRegionId(Integer regionId)
    {
        this.regionId = regionId;
    }
    public String getRegionName()
    {
        return regionName;
    }
    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }
    public Integer getDeptId()
    {
        return deptId;
    }
    public void setDeptId(Integer deptId)
    {
        this.deptId = deptId;
    }
    public String getDeptName()
    {
        return deptName;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    public String getBed()
    {
        return bed;
    }
    public void setBed(String bed)
    {
        this.bed = bed;
    }
    public String getDiagCode()
    {
        return diagCode;
    }
    public void setDiagCode(String diagCode)
    {
        this.diagCode = diagCode;
    }
    public String getDiagName()
    {
        return diagName;
    }
    public void setDiagName(String diagName)
    {
        this.diagName = diagName;
    }
    public String getOperCode()
    {
        return operCode;
    }
    public void setOperCode(String operCode)
    {
        this.operCode = operCode;
    }
    public String getOperName()
    {
        return operName;
    }
    public void setOperName(String operName)
    {
        this.operName = operName;
    }
    public String getSurgeryDoctorId()
    {
        return surgeryDoctorId;
    }
    public void setSurgeryDoctorId(String surgeryDoctorId)
    {
        this.surgeryDoctorId = surgeryDoctorId;
    }
    public String getSurgeryDoctorName()
    {
        return surgeryDoctorName;
    }
    public void setSurgeryDoctorName(String surgeryDoctorName)
    {
        this.surgeryDoctorName = surgeryDoctorName;
    }
    public String getAssistantId()
    {
        return assistantId;
    }
    public void setAssistantId(String assistantId)
    {
        this.assistantId = assistantId;
    }
    public String getAssistantName()
    {
        return assistantName;
    }
    public void setAssistantName(String assistantName)
    {
        this.assistantName = assistantName;
    }
    public String getWeight()
    {
        return weight;
    }
    public void setWeight(String weight)
    {
        this.weight = weight;
    }
    public String getHeight()
    {
        return height;
    }
    public void setHeight(String height)
    {
        this.height = height;
    }
    public String getHbsag()
    {
        return hbsag;
    }
    public void setHbsag(String hbsag)
    {
        this.hbsag = hbsag;
    }
    public String getHcv()
    {
        return hcv;
    }
    public void setHcv(String hcv)
    {
        this.hcv = hcv;
    }
    public String getHiv()
    {
        return hiv;
    }
    public void setHiv(String hiv)
    {
        this.hiv = hiv;
    }
    public String getHp()
    {
        return hp;
    }
    public void setHp(String hp)
    {
        this.hp = hp;
    }
    public String getOperDate()
    {
        return operDate;
    }
    public void setOperDate(String operDate)
    {
        this.operDate = operDate;
    }
    public String getOperStartTime()
    {
        return operStartTime;
    }
    public void setOperStartTime(String operStartTime)
    {
        this.operStartTime = operStartTime;
    }
    public String getOperEndTime()
    {
        return operEndTime;
    }
    public void setOperEndTime(String operEndTime)
    {
        this.operEndTime = operEndTime;
    }
    public String getDragAllergy()
    {
        return dragAllergy;
    }
    public void setDragAllergy(String dragAllergy)
    {
        this.dragAllergy = dragAllergy;
    }
    public String getOperLevel()
    {
        return operLevel;
    }
    public void setOperLevel(String operLevel)
    {
        this.operLevel = operLevel;
    }
    public String getIncisionLevel()
    {
        return incisionLevel;
    }
    public void setIncisionLevel(String incisionLevel)
    {
        this.incisionLevel = incisionLevel;
    }
    public Integer getOperSource()
    {
        return operSource;
    }
    public void setOperSource(Integer operSource)
    {
        this.operSource = operSource;
    }
    public Integer getOperType()
    {
        return operType;
    }
    public void setOperType(Integer operType)
    {
        this.operType = operType;
    }
    public String getAnaesType()
    {
        return anaesType;
    }
    public void setAnaesType(String anaesType)
    {
        this.anaesType = anaesType;
    }
    public String getAnaesID()
    {
        return anaesID;
    }
    public void setAnaesID(String anaesID)
    {
        this.anaesID = anaesID;
    }
    public String getAnaesName()
    {
        return anaesName;
    }
    public void setAnaesName(String anaesName)
    {
        this.anaesName = anaesName;
    }
    public String getCreateUser()
    {
        return createUser;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }
    public Integer getFrontOperForbidTake()
    {
        return frontOperForbidTake;
    }
    public void setFrontOperForbidTake(Integer frontOperForbidTake)
    {
        this.frontOperForbidTake = frontOperForbidTake;
    }
    public String getFrontOperSpecialCase()
    {
        return frontOperSpecialCase;
    }
    public void setFrontOperSpecialCase(String frontOperSpecialCase)
    {
        this.frontOperSpecialCase = frontOperSpecialCase;
    }
	
	
}
