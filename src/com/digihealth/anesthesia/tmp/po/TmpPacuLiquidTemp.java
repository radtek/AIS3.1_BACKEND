package com.digihealth.anesthesia.tmp.po;

import java.util.Date;

public class TmpPacuLiquidTemp {
    /**
     * 模板ID
     */
    private String tempId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 拼音码
     */
    private String pinyin;

    /**
     * 模板内容
     */
    private String tempContent;

    /**
     * 创建人用户名
     */
    private String createUser;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 模板类型：1，私有；2，全局
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 模板名称
     */
    private String tempName;

    /**
     * 模板类型 1、观察记录模版 2、麻醉小结模板
     */
    private Integer tempType;
    
    private String beid;

    public String getBeid()
    {
        return beid;
    }

    public void setBeid(String beid)
    {
        this.beid = beid;
    }

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getTempContent() {
        return tempContent;
    }

    public void setTempContent(String tempContent) {
        this.tempContent = tempContent;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public Integer getTempType() {
        return tempType;
    }

    public void setTempType(Integer tempType) {
        this.tempType = tempType;
    }
}