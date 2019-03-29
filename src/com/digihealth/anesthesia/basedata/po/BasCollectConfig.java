package com.digihealth.anesthesia.basedata.po;

public class BasCollectConfig {
    private String roomId;

    /**
     * 基线id
     */
    private String beid;

    private String ip;

    /**
     * 手术Id
     */
    private String patientId;

    /**
     * 设备协议，多个用逗号隔开
     */
    private String devicesUsed;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBeid() {
        return beid;
    }

    public void setBeid(String beid) {
        this.beid = beid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDevicesUsed() {
        return devicesUsed;
    }

    public void setDevicesUsed(String devicesUsed) {
        this.devicesUsed = devicesUsed;
    }
}