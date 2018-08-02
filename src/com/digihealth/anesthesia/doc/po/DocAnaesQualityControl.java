package com.digihealth.anesthesia.doc.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;

public class DocAnaesQualityControl {
    private String id;

    private String regOptId;

    /**
     * ASA分级
     */
    private Integer asa;

    /**
     * 手术类型
     */
    private Integer surgeryType;

    /**
     * 麻醉方式
     */
    private Integer anesType;

    /**
     * 是否有椎管内麻醉后严重神经并发症
     */
    private Integer spinalAnaesComplication;

    /**
     * 并发症发生时间
     */
    private Date complicationTime;

    /**
     * 具体并发症
     */
    private String complicationDetail;

    /**
     * 麻醉开始后取消手术
     */
    private Integer surgeryCancle;

    /**
     * 入pacu时长
     */
    private Integer pacuDuration;

    /**
     * pacu入室体温
     */
    private Float pacuTemp;

    /**
     * 非计划入pacu
     */
    private Integer unplanToPacu;

    /**
     * 非计划二次气管插管
     */
    private Integer unplanSecondIntuba;

    /**
     * 接受400ml及以上输血
     */
    private Integer transBlood400;

    /**
     * 麻醉中接受400ml及以上自体血
     */
    private Integer transSelfBlood400;

    /**
     * 麻醉开始后死亡
     */
    private Integer anaesBeginDeath;

    /**
     * 死亡时间
     */
    private Date deathTime;

    /**
     * 麻醉开始后心脏骤停
     */
    private Integer anaesBeginSCA;

    /**
     * 心脏骤停时间
     */
    private Date scaTime;

    /**
     * 麻醉期间发生严重过敏反应
     */
    private Integer anaesAllergic;

    /**
     * 过敏反应发生时间
     */
    private Date allergicTime;

    /**
     * 过敏反应
     */
    private String allergicDetail;

    /**
     * 对患者进行中心静脉穿刺治疗
     */
    private Integer venipuncTreat;

    /**
     * 是否有中心静脉穿刺并发症
     */
    private Integer venipuncComplica;

    /**
     * 中心静脉穿刺并发症发生时间
     */
    private Date venipuncComplicaTime;

    /**
     * 中心静脉穿刺并发症详情
     */
    private String venipuncComplicaDetail;

    /**
     * 全麻气管插管拔管后声音嘶哑
     */
    private Integer genAnesHoarse;

    /**
     * 麻醉后新发昏迷
     */
    private Integer anesComa;
    
    /**
     * 具体并发症
     */
    private List<SysCodeFormbean> complicationDetailList;
    
    /**
     * 过敏反应
     */
    private List<SysCodeFormbean> allergicDetailList;
    
    /**
     * 中心静脉穿刺并发症详情
     */
    private List<SysCodeFormbean> venipuncComplicaDetailList;


    public List<SysCodeFormbean> getComplicationDetailList()
    {
        return null == complicationDetailList ? new ArrayList<SysCodeFormbean>() : complicationDetailList;
    }

    public void setComplicationDetailList(List<SysCodeFormbean> complicationDetailList)
    {
        this.complicationDetailList = complicationDetailList;
    }

    public List<SysCodeFormbean> getAllergicDetailList()
    {
        return null == allergicDetailList ? new ArrayList<SysCodeFormbean>() : allergicDetailList;
    }

    public void setAllergicDetailList(List<SysCodeFormbean> allergicDetailList)
    {
        this.allergicDetailList = allergicDetailList;
    }

    public List<SysCodeFormbean> getVenipuncComplicaDetailList()
    {
        return null == venipuncComplicaDetailList ? new ArrayList<SysCodeFormbean>() : venipuncComplicaDetailList;
    }

    public void setVenipuncComplicaDetailList(List<SysCodeFormbean> venipuncComplicaDetailList)
    {
        this.venipuncComplicaDetailList = venipuncComplicaDetailList;
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

    public Integer getAsa() {
        return asa;
    }

    public void setAsa(Integer asa) {
        this.asa = asa;
    }

    public Integer getSurgeryType() {
        return surgeryType;
    }

    public void setSurgeryType(Integer surgeryType) {
        this.surgeryType = surgeryType;
    }

    public Integer getAnesType() {
        return anesType;
    }

    public void setAnesType(Integer anesType) {
        this.anesType = anesType;
    }

    public Integer getSpinalAnaesComplication() {
        return spinalAnaesComplication;
    }

    public void setSpinalAnaesComplication(Integer spinalAnaesComplication) {
        this.spinalAnaesComplication = spinalAnaesComplication;
    }

    public Date getComplicationTime()
    {
        return complicationTime;
    }

    public void setComplicationTime(Date complicationTime)
    {
        this.complicationTime = complicationTime;
    }

    public String getComplicationDetail() {
        return complicationDetail;
    }

    public void setComplicationDetail(String complicationDetail) {
        this.complicationDetail = complicationDetail;
    }

    public Integer getSurgeryCancle() {
        return surgeryCancle;
    }

    public void setSurgeryCancle(Integer surgeryCancle) {
        this.surgeryCancle = surgeryCancle;
    }

    public Integer getPacuDuration() {
        return pacuDuration;
    }

    public void setPacuDuration(Integer pacuDuration) {
        this.pacuDuration = pacuDuration;
    }

    public Float getPacuTemp() {
        return pacuTemp;
    }

    public void setPacuTemp(Float pacuTemp) {
        this.pacuTemp = pacuTemp;
    }

    public Integer getUnplanToPacu() {
        return unplanToPacu;
    }

    public void setUnplanToPacu(Integer unplanToPacu) {
        this.unplanToPacu = unplanToPacu;
    }

    public Integer getUnplanSecondIntuba() {
        return unplanSecondIntuba;
    }

    public void setUnplanSecondIntuba(Integer unplanSecondIntuba) {
        this.unplanSecondIntuba = unplanSecondIntuba;
    }

    public Integer getTransBlood400() {
        return transBlood400;
    }

    public void setTransBlood400(Integer transBlood400) {
        this.transBlood400 = transBlood400;
    }

    public Integer getTransSelfBlood400() {
        return transSelfBlood400;
    }

    public void setTransSelfBlood400(Integer transSelfBlood400) {
        this.transSelfBlood400 = transSelfBlood400;
    }

    public Integer getAnaesBeginDeath() {
        return anaesBeginDeath;
    }

    public void setAnaesBeginDeath(Integer anaesBeginDeath) {
        this.anaesBeginDeath = anaesBeginDeath;
    }

    public Date getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Date deathTime) {
        this.deathTime = deathTime;
    }

    public Integer getAnaesBeginSCA() {
        return anaesBeginSCA;
    }

    public void setAnaesBeginSCA(Integer anaesBeginSCA) {
        this.anaesBeginSCA = anaesBeginSCA;
    }

    public Date getScaTime() {
        return scaTime;
    }

    public void setScaTime(Date scaTime) {
        this.scaTime = scaTime;
    }

    public Integer getAnaesAllergic() {
        return anaesAllergic;
    }

    public void setAnaesAllergic(Integer anaesAllergic) {
        this.anaesAllergic = anaesAllergic;
    }

    public Date getAllergicTime() {
        return allergicTime;
    }

    public void setAllergicTime(Date allergicTime) {
        this.allergicTime = allergicTime;
    }

    public String getAllergicDetail() {
        return allergicDetail;
    }

    public void setAllergicDetail(String allergicDetail) {
        this.allergicDetail = allergicDetail;
    }

    public Integer getVenipuncTreat() {
        return venipuncTreat;
    }

    public void setVenipuncTreat(Integer venipuncTreat) {
        this.venipuncTreat = venipuncTreat;
    }

    public Integer getVenipuncComplica() {
        return venipuncComplica;
    }

    public void setVenipuncComplica(Integer venipuncComplica) {
        this.venipuncComplica = venipuncComplica;
    }

    public Date getVenipuncComplicaTime() {
        return venipuncComplicaTime;
    }

    public void setVenipuncComplicaTime(Date venipuncComplicaTime) {
        this.venipuncComplicaTime = venipuncComplicaTime;
    }

    public String getVenipuncComplicaDetail() {
        return venipuncComplicaDetail;
    }

    public void setVenipuncComplicaDetail(String venipuncComplicaDetail) {
        this.venipuncComplicaDetail = venipuncComplicaDetail;
    }

    public Integer getGenAnesHoarse() {
        return genAnesHoarse;
    }

    public void setGenAnesHoarse(Integer genAnesHoarse) {
        this.genAnesHoarse = genAnesHoarse;
    }

    public Integer getAnesComa() {
        return anesComa;
    }

    public void setAnesComa(Integer anesComa) {
        this.anesComa = anesComa;
    }
}