/*
 * EvtAnaesModifyRecord.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-10-27 Created
 */
package com.digihealth.anesthesia.evt.po;

import java.util.Date;

public class EvtAnaesModifyRecord {
    /**
     * 术中麻醉记录历史痕迹表主键
     */
    private String id;

    /**
     * 所属模块
     */
    private String operModule;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 患者id
     */
    private String regOptId;

    /**
     * 事件表对应主键Id
     */
    private String eventId;

    /**
     * 所属表
     */
    private String modTable;

    /**
     * 对应表的属性值
     */
    private String modProperty;

    /**
     * 修改前值
     */
    private String oldValue;

    /**
     * 修改后值
     */
    private String newValue;

    /**
     * 操作员ID
     */
    private String operId;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 版本id
     */
    private String beid;

    /**
     * 备注
     */
    private String remark;
    /**
     * 住院号
     */
    private String hid;
    /**
     * 患者姓名
     */
    private String name;
    /**
     * 手术日期
     */
    private String operaDate;
    /**
     * 手术室
     */
    private String operRoomId;
    /**
     * 台次
     */
    private String pcs;
    /**
     * 
     * @return
     */
    private String operRoomName;
    
    
    
    public String getOperRoomName() {
		return operRoomName;
	}

	public void setOperRoomName(String operRoomName) {
		this.operRoomName = operRoomName;
	}

	public String getOperRoomId() {
		return operRoomId;
	}

	public void setOperRoomId(String operRoomId) {
		this.operRoomId = operRoomId;
	}

	public String getPcs() {
		return pcs;
	}

	public void setPcs(String pcs) {
		this.pcs = pcs;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperaDate() {
		return operaDate;
	}

	public void setOperaDate(String operaDate) {
		this.operaDate = operaDate;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperModule() {
        return operModule;
    }

    public void setOperModule(String operModule) {
        this.operModule = operModule;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegOptId() {
        return regOptId;
    }

    public void setRegOptId(String regOptId) {
        this.regOptId = regOptId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getModTable() {
        return modTable;
    }

    public void setModTable(String modTable) {
        this.modTable = modTable;
    }

    public String getModProperty() {
        return modProperty;
    }

    public void setModProperty(String modProperty) {
        this.modProperty = modProperty;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}