package com.digihealth.anesthesia.doc.po;

import java.util.Date;

public class DocDocordRecord {
    /**
     * 医嘱表主键
     */
    private String docRecordId;

    /**
     * 病人ID
     */
    private String regOptId;

    /**
     * 住院号
     */
    private String zyhm;

    /**
     * 分组号
     */
    private String groupId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 医嘱类型
     */
    private String type;

    /**
     * 子类型
     */
    private String subType;

    /**
     * 药品Id
     */
    private Integer medId;

    /**
     * 医嘱名称
     */
    private String name;

    /**
     * 给药方式
     */
    private String method;

    /**
     * 医嘱内容
     */
    private String doctorContent;

    /**
     * 用法
     */
    private String freq;

    /**
     * 数量
     */
    private Float number1;

    /**
     * 次数
     */
    private Integer times;

    /**
     * 剂量
     */
    private Float dosage;

    /**
     * 单位
     */
    private String unit;

    /**
     * 流速
     */
    private String speed;

    private String doctor;

    /**
     * 医嘱下达时间
     */
    private Date doctorTime;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 配药者
     */
    private String dispenser;

    /**
     * 执行者
     */
    private String excutor;

    private String excutorId;

    /**
     * 皮试备注
     */
    private String skinTestRem;

    /**
     * 医嘱核对人
     */
    private String checkor;

    /**
     * 医嘱执行时间
     */
    private Date excuteTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 医嘱执行状态：0，初始准备；1，审核完成；2，正在执行；3，执行完成；4，暂停；5，取消；6：删除
     */
    private Integer state;

    /**
     * his临时医嘱明细ID
     */
    private String jlxh;

    public String getDocRecordId() {
        return docRecordId;
    }

    public void setDocRecordId(String docRecordId) {
        this.docRecordId = docRecordId;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public String getZyhm() {
        return zyhm;
    }

    public void setZyhm(String zyhm) {
        this.zyhm = zyhm;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Integer getMedId() {
        return medId;
    }

    public void setMedId(Integer medId) {
        this.medId = medId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDoctorContent() {
        return doctorContent;
    }

    public void setDoctorContent(String doctorContent) {
        this.doctorContent = doctorContent;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public Float getNumber1() {
        return number1;
    }

    public void setNumber1(Float number1) {
        this.number1 = number1;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Float getDosage() {
        return dosage;
    }

    public void setDosage(Float dosage) {
        this.dosage = dosage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Date getDoctorTime() {
        return doctorTime;
    }

    public void setDoctorTime(Date doctorTime) {
        this.doctorTime = doctorTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDispenser() {
        return dispenser;
    }

    public void setDispenser(String dispenser) {
        this.dispenser = dispenser;
    }

    public String getExcutor() {
        return excutor;
    }

    public void setExcutor(String excutor) {
        this.excutor = excutor;
    }

    public String getExcutorId() {
        return excutorId;
    }

    public void setExcutorId(String excutorId) {
        this.excutorId = excutorId;
    }

    public String getSkinTestRem() {
        return skinTestRem;
    }

    public void setSkinTestRem(String skinTestRem) {
        this.skinTestRem = skinTestRem;
    }

    public String getCheckor() {
        return checkor;
    }

    public void setCheckor(String checkor) {
        this.checkor = checkor;
    }

    public Date getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(Date excuteTime) {
        this.excuteTime = excuteTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getJlxh() {
        return jlxh;
    }

    public void setJlxh(String jlxh) {
        this.jlxh = jlxh;
    }
}