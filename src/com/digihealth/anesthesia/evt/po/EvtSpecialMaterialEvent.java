package com.digihealth.anesthesia.evt.po;

import java.util.Date;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "麻醉记录单特殊用药事件对象")
public class EvtSpecialMaterialEvent
{
    /**
     * 特殊材料事件主键
     */
    private String specialMaterialEventId;

    /**
     * 文书id
     */
    private String docId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 特殊材料名称
     */
    private String specialItemName;

    /**
     * 其他事件定义ID
     */
    private String specialItemId;

    public String getSpecialMaterialEventId() {
        return specialMaterialEventId;
    }

    public void setSpecialMaterialEventId(String specialMaterialEventId) {
        this.specialMaterialEventId = specialMaterialEventId == null ? null : specialMaterialEventId.trim();
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId == null ? null : docId.trim();
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

    public String getSpecialItemName() {
        return specialItemName;
    }

    public void setSpecialItemName(String specialItemName) {
        this.specialItemName = specialItemName == null ? null : specialItemName.trim();
    }

    public String getSpecialItemId() {
        return specialItemId;
    }

    public void setSpecialItemId(String specialItemId) {
        this.specialItemId = specialItemId == null ? null : specialItemId.trim();
    }
}
