package com.digihealth.anesthesia.interfacedata.po.hnhtyy;

import org.apache.commons.lang3.StringUtils;

import com.wordnik.swagger.annotations.ApiModelProperty;

public class Row
{
    private String code;
    private String name;
    private String pinyin;
    private String enable;
    
    private String type;
    private String briefName;
    private String packageDosageAmount;
    private String dosageUnit;
    private String spec;
    private String chargeType;
    private String firm;
    private String firmId;
    private String minPackageUnit;
    private String priceMinPackage;
    private String batch;
    
    private String reservenumber;
    private String age;
    private String ageMon;
    private String ageDay;
    private String birthday;
    private String sex;
    private String medicalType;
    private String credNumber;
    private String hid;
    private String cid;
    private String regionId;
    private String regionName;
    private String deptId;
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
    private String dragAllergy;
    private String operLevel;
    private String incisionLevel;
    private String opeSource;
    private String operType;
    private String anaesType;
    private String anaesID;
    private String anaesName;
    private String createUser;
    private String frontOperForbidTake;
    private String frontOperSpecialCase;
    private String timetemp;
    private String basicUnitAmout;
    private String price;
    private String unit;
    private String chargeItemCode;
    private String chargeItemName;
    private String timestamp;//his回传时间戳值
    private String remark;
    private String operatorId;
    private String operatorName;
    
    
    //his计费返回参数
    private String ret;//F为失败  T为成功
    private String oper_id;//手麻收费项目ID
    private String idm;//类型  1为项目  2为药品
    
    private String state;//手术状态
    
    private String onlynumber;
    private String visitid;
    
    private String feename;//费用类型 
    
    
    private String resultCode;
    private String resultMessage;
    
    private String pkItId;
    
    
	public String getOperatorId()
    {
        return operatorId;
    }
    public void setOperatorId(String operatorId)
    {
        this.operatorId = operatorId;
    }
    public String getOperatorName()
    {
        return operatorName;
    }
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
    public String getPkItId() {
		return pkItId;
	}
	public void setPkItId(String pkItId) {
		this.pkItId = pkItId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
    
    
	public String getFeename() {
		return feename;
	}
	public void setFeename(String feename) {
		this.feename = feename;
	}
	public String getOnlynumber() {
		return onlynumber;
	}
	public void setOnlynumber(String onlynumber) {
		this.onlynumber = onlynumber;
	}
	public String getVisitid() {
		return visitid;
	}
	public void setVisitid(String visitid) {
		this.visitid = visitid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRet() {
		return ret;
	}
	public void setRet(String ret) {
		this.ret = ret;
	}
	public String getOper_id() {
		return oper_id;
	}
	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}
	public String getIdm() {
		return idm;
	}
	public void setIdm(String idm) {
		this.idm = idm;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getChargeItemCode() {
		return StringUtils.isEmpty(chargeItemCode)?"":chargeItemCode.trim();
	}
	public void setChargeItemCode(String chargeItemCode) {
		this.chargeItemCode = chargeItemCode;
	}
	public String getChargeItemName() {
		return StringUtils.isEmpty(chargeItemName)?"":chargeItemName.trim();
	}
	public void setChargeItemName(String chargeItemName) {
		this.chargeItemName = chargeItemName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getBasicUnitAmout() {
		return basicUnitAmout;
	}
	public void setBasicUnitAmout(String basicUnitAmout) {
		this.basicUnitAmout = basicUnitAmout;
	}
	public String getReservenumber() {
		return StringUtils.isEmpty(reservenumber)?"":reservenumber.trim();
	}
	public void setReservenumber(String reservenumber) {
		this.reservenumber = reservenumber;
	}
	public String getAge() {
		return age;
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
	public String getAgeDay() {
		return ageDay;
	}
	public void setAgeDay(String ageDay) {
		this.ageDay = ageDay;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return StringUtils.isEmpty(sex)?"":sex.trim();
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMedicalType() {
		return StringUtils.isEmpty(medicalType)?"":medicalType.trim();
	}
	public void setMedicalType(String medicalType) {
		this.medicalType = medicalType;
	}
	public String getCredNumber() {
		return credNumber;
	}
	public void setCredNumber(String credNumber) {
		this.credNumber = credNumber;
	}
	public String getHid() {
		return StringUtils.isEmpty(hid)?"":hid.trim();
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getCid() {
		return StringUtils.isEmpty(cid)?"":cid.trim();
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getRegionId() {
		return StringUtils.isEmpty(regionId)?"":regionId.trim();
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return StringUtils.isEmpty(regionName)?"":regionName.trim();
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getDeptId() {
		return StringUtils.isEmpty(deptId)?"":deptId.trim();
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return StringUtils.isEmpty(deptName)?"":deptName.trim();
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getBed() {
		return StringUtils.isEmpty(bed)?"":bed.trim();
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getDiagCode() {
		return StringUtils.isEmpty(diagCode)?"":diagCode.trim();
	}
	public void setDiagCode(String diagCode) {
		this.diagCode = diagCode;
	}
	public String getDiagName() {
		return StringUtils.isEmpty(diagName)?"":diagName.trim();
	}
	public void setDiagName(String diagName) {
		this.diagName = diagName;
	}
	public String getOperCode() {
		return StringUtils.isEmpty(operCode)?"":operCode.trim();
	}
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
	public String getOperName() {
		return StringUtils.isEmpty(operName)?"":operName.trim();
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getSurgeryDoctorId() {
		return StringUtils.isEmpty(surgeryDoctorId)?"":surgeryDoctorId.trim();
	}
	public void setSurgeryDoctorId(String surgeryDoctorId) {
		this.surgeryDoctorId = surgeryDoctorId;
	}
	public String getSurgeryDoctorName() {
		return StringUtils.isEmpty(surgeryDoctorName)?"":surgeryDoctorName.trim();
	}
	public void setSurgeryDoctorName(String surgeryDoctorName) {
		this.surgeryDoctorName = surgeryDoctorName;
	}
	public String getAssistantId() {
		return StringUtils.isEmpty(assistantId)?"":assistantId.trim();
	}
	public void setAssistantId(String assistantId) {
		this.assistantId = assistantId;
	}
	public String getAssistantName() {
		return StringUtils.isEmpty(assistantName)?"":assistantName.trim();
	}
	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getHbsag() {
		return hbsag;
	}
	public void setHbsag(String hbsag) {
		this.hbsag = hbsag;
	}
	public String getHcv() {
		return hcv;
	}
	public void setHcv(String hcv) {
		this.hcv = hcv;
	}
	public String getHiv() {
		return hiv;
	}
	public void setHiv(String hiv) {
		this.hiv = hiv;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getOperDate() {
		return operDate;
	}
	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}
	public String getOperStartTime() {
		return operStartTime;
	}
	public void setOperStartTime(String operStartTime) {
		this.operStartTime = operStartTime;
	}
	public String getOperEndTime() {
		return operEndTime;
	}
	public void setOperEndTime(String operEndTime) {
		this.operEndTime = operEndTime;
	}
	public String getDragAllergy() {
		return dragAllergy;
	}
	public void setDragAllergy(String dragAllergy) {
		this.dragAllergy = dragAllergy;
	}
	public String getOperLevel() {
		return StringUtils.isEmpty(operLevel)?"":operLevel.trim();
	}
	public void setOperLevel(String operLevel) {
		this.operLevel = operLevel;
	}
	public String getIncisionLevel() {
		return StringUtils.isEmpty(incisionLevel)?"":incisionLevel.trim();
	}
	public void setIncisionLevel(String incisionLevel) {
		this.incisionLevel = incisionLevel;
	}
	public String getOpeSource() {
		return opeSource;
	}
	public void setOpeSource(String opeSource) {
		this.opeSource = opeSource;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getAnaesType() {
		return anaesType;
	}
	public void setAnaesType(String anaesType) {
		this.anaesType = anaesType;
	}
	public String getAnaesID() {
		return StringUtils.isEmpty(anaesID)?"":anaesID.trim();
	}
	public void setAnaesID(String anaesID) {
		this.anaesID = anaesID;
	}
	public String getAnaesName() {
		return StringUtils.isEmpty(anaesName)?"":anaesName.trim();
	}
	public void setAnaesName(String anaesName) {
		this.anaesName = anaesName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getFrontOperForbidTake() {
		return frontOperForbidTake;
	}
	public void setFrontOperForbidTake(String frontOperForbidTake) {
		this.frontOperForbidTake = frontOperForbidTake;
	}
	public String getFrontOperSpecialCase() {
		return frontOperSpecialCase;
	}
	public void setFrontOperSpecialCase(String frontOperSpecialCase) {
		this.frontOperSpecialCase = frontOperSpecialCase;
	}
	public String getTimetemp() {
		return timetemp;
	}
	public void setTimetemp(String timetemp) {
		this.timetemp = timetemp;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public String getFirmId() {
		return firmId;
	}
	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}
	public String getMinPackageUnit() {
		return minPackageUnit;
	}
	public void setMinPackageUnit(String minPackageUnit) {
		this.minPackageUnit = minPackageUnit;
	}
	public String getPriceMinPackage() {
		return priceMinPackage;
	}
	public void setPriceMinPackage(String priceMinPackage) {
		this.priceMinPackage = priceMinPackage;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBriefName() {
		return briefName;
	}
	public void setBriefName(String briefName) {
		this.briefName = briefName;
	}
	public String getPackageDosageAmount() {
		return packageDosageAmount;
	}
	public void setPackageDosageAmount(String packageDosageAmount) {
		this.packageDosageAmount = packageDosageAmount;
	}
	public String getDosageUnit() {
		return dosageUnit;
	}
	public void setDosageUnit(String dosageUnit) {
		this.dosageUnit = dosageUnit;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getCode() {
		return StringUtils.isEmpty(code)?"":code.trim();
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return StringUtils.isEmpty(name)?"":name.trim();
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	@Override
	public String toString() {
		return "Row [code=" + code + ", name=" + name + ", pinyin=" + pinyin
				+ ", enable=" + enable + ", type=" + type + ", briefName="
				+ briefName + ", packageDosageAmount=" + packageDosageAmount
				+ ", dosageUnit=" + dosageUnit + ", spec=" + spec
				+ ", chargeType=" + chargeType + ", firm=" + firm + ", firmId="
				+ firmId + ", minPackageUnit=" + minPackageUnit
				+ ", priceMinPackage=" + priceMinPackage + ", batch=" + batch
				+ ", reservenumber=" + reservenumber + ", age=" + age
				+ ", ageMon=" + ageMon + ", ageDay=" + ageDay + ", birthday="
				+ birthday + ", sex=" + sex + ", medicalType=" + medicalType
				+ ", credNumber=" + credNumber + ", hid=" + hid + ", cid="
				+ cid + ", regionId=" + regionId + ", regionName=" + regionName
				+ ", deptId=" + deptId + ", deptName=" + deptName + ", bed="
				+ bed + ", diagCode=" + diagCode + ", diagName=" + diagName
				+ ", operCode=" + operCode + ", operName=" + operName
				+ ", surgeryDoctorId=" + surgeryDoctorId
				+ ", surgeryDoctorName=" + surgeryDoctorName + ", assistantId="
				+ assistantId + ", assistantName=" + assistantName
				+ ", weight=" + weight + ", height=" + height + ", hbsag="
				+ hbsag + ", hcv=" + hcv + ", hiv=" + hiv + ", hp=" + hp
				+ ", operDate=" + operDate + ", operStartTime=" + operStartTime
				+ ", operEndTime=" + operEndTime + ", dragAllergy="
				+ dragAllergy + ", operLevel=" + operLevel + ", incisionLevel="
				+ incisionLevel + ", opeSource=" + opeSource + ", operType="
				+ operType + ", anaesType=" + anaesType + ", anaesID="
				+ anaesID + ", anaesName=" + anaesName + ", createUser="
				+ createUser + ", frontOperForbidTake=" + frontOperForbidTake
				+ ", frontOperSpecialCase=" + frontOperSpecialCase
				+ ", timetemp=" + timetemp + ", basicUnitAmout="
				+ basicUnitAmout + ", price=" + price + ", unit=" + unit
				+ ", chargeItemCode=" + chargeItemCode + ", chargeItemName="
				+ chargeItemName + ", timestamp=" + timestamp
				+ ", getTimestamp()=" + getTimestamp()
				+ ", getChargeItemCode()=" + getChargeItemCode()
				+ ", getChargeItemName()=" + getChargeItemName()
				+ ", getPrice()=" + getPrice() + ", getUnit()=" + getUnit()
				+ ", getChargeType()=" + getChargeType()
				+ ", getBasicUnitAmout()=" + getBasicUnitAmout()
				+ ", getReservenumber()=" + getReservenumber() + ", getAge()="
				+ getAge() + ", getAgeMon()=" + getAgeMon() + ", getAgeDay()="
				+ getAgeDay() + ", getBirthday()=" + getBirthday()
				+ ", getSex()=" + getSex() + ", getMedicalType()="
				+ getMedicalType() + ", getCredNumber()=" + getCredNumber()
				+ ", getHid()=" + getHid() + ", getCid()=" + getCid()
				+ ", getRegionId()=" + getRegionId() + ", getRegionName()="
				+ getRegionName() + ", getDeptId()=" + getDeptId()
				+ ", getDeptName()=" + getDeptName() + ", getBed()=" + getBed()
				+ ", getDiagCode()=" + getDiagCode() + ", getDiagName()="
				+ getDiagName() + ", getOperCode()=" + getOperCode()
				+ ", getOperName()=" + getOperName()
				+ ", getSurgeryDoctorId()=" + getSurgeryDoctorId()
				+ ", getSurgeryDoctorName()=" + getSurgeryDoctorName()
				+ ", getAssistantId()=" + getAssistantId()
				+ ", getAssistantName()=" + getAssistantName()
				+ ", getWeight()=" + getWeight() + ", getHeight()="
				+ getHeight() + ", getHbsag()=" + getHbsag() + ", getHcv()="
				+ getHcv() + ", getHiv()=" + getHiv() + ", getHp()=" + getHp()
				+ ", getOperDate()=" + getOperDate() + ", getOperStartTime()="
				+ getOperStartTime() + ", getOperEndTime()=" + getOperEndTime()
				+ ", getDragAllergy()=" + getDragAllergy()
				+ ", getOperLevel()=" + getOperLevel()
				+ ", getIncisionLevel()=" + getIncisionLevel()
				+ ", getOpeSource()=" + getOpeSource() + ", getOperType()="
				+ getOperType() + ", getAnaesType()=" + getAnaesType()
				+ ", getAnaesID()=" + getAnaesID() + ", getAnaesName()="
				+ getAnaesName() + ", getCreateUser()=" + getCreateUser()
				+ ", getFrontOperForbidTake()=" + getFrontOperForbidTake()
				+ ", getFrontOperSpecialCase()=" + getFrontOperSpecialCase()
				+ ", getTimetemp()=" + getTimetemp() + ", getFirm()="
				+ getFirm() + ", getFirmId()=" + getFirmId()
				+ ", getMinPackageUnit()=" + getMinPackageUnit()
				+ ", getPriceMinPackage()=" + getPriceMinPackage()
				+ ", getBatch()=" + getBatch() + ", getType()=" + getType()
				+ ", getBriefName()=" + getBriefName()
				+ ", getPackageDosageAmount()=" + getPackageDosageAmount()
				+ ", getDosageUnit()=" + getDosageUnit() + ", getSpec()="
				+ getSpec() + ", getCode()=" + getCode() + ", getName()="
				+ getName() + ", getPinyin()=" + getPinyin() + ", getEnable()="
				+ getEnable() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    
    
}
