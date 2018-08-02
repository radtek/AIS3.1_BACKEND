/*
 * DocTitle.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-12-06 Created
 */
package com.digihealth.anesthesia.doc.po;

import com.digihealth.anesthesia.doc.formbean.DynamicSqlFormBean;

public class DocTitle {
    /**
     * 文书题目id
     */
    private String docTitleId;

    /**
     * 文书主题id
     */
    private String docThemeId;

    /**
     * 题目名称
     */
    private String title;

    /**
     * 对应的表名
     */
    private String tableName;

    /**
     * 对应的字段名
     */
    private String fieldName;

    /**
     * 对应的字段类型
     */
    private String fieldType;

    /**
     * 输入值上限
     */
    private Float maxdoc;

    /**
     * 输入值下限
     */
    private Float mindoc;

    /**
     * 是否必填
     */
    private Integer ismast;

    /**
     * 其他数据
     */
    private String other;

    /**
     * 插件
     */
    private String plugins;
    
    /**
     * 最大字符长度
     */
    private Integer maxlength;

    /**
     * 控件组名称
     */
    private String groupName;

    /**
     * 控件的html
     */
    private String conHtml;

    /**
     * 数据源
     */
    private String dataUrl;

    /**
     * url参数
     */
    private String dataParam;

    /**
     * 键值对
     */
    private String keyValue;
    
    /**
     * 是否动态sql
     */
    private Integer isDynamicSql;
    
    /**
     * 动态sql
     */
    private String dynamicSql;
    
    //自定义动态sql产生的对象
    private DynamicSqlFormBean dynamicSqlFormBean;

    public String getDocTitleId() {
        return docTitleId;
    }

    public void setDocTitleId(String docTitleId) {
        this.docTitleId = docTitleId == null ? null : docTitleId.trim();
    }

    public String getDocThemeId() {
        return docThemeId;
    }

    public void setDocThemeId(String docThemeId) {
        this.docThemeId = docThemeId == null ? null : docThemeId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType == null ? null : fieldType.trim();
    }

    public Float getMaxdoc() {
        return maxdoc;
    }

    public void setMaxdoc(Float maxdoc) {
        this.maxdoc = maxdoc;
    }

    public Float getMindoc() {
        return mindoc;
    }

    public void setMindoc(Float mindoc) {
        this.mindoc = mindoc;
    }

    public Integer getIsmast() {
        return ismast;
    }

    public void setIsmast(Integer ismast) {
        this.ismast = ismast;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other == null ? null : other.trim();
    }

    public String getPlugins() {
        return plugins;
    }

    public void setPlugins(String plugins) {
        this.plugins = plugins == null ? null : plugins.trim();
    }
    
    public Integer getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(Integer maxlength) {
        this.maxlength = maxlength;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getConHtml() {
        return conHtml;
    }

    public void setConHtml(String conHtml) {
        this.conHtml = conHtml == null ? null : conHtml.trim();
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl == null ? null : dataUrl.trim();
    }

    public String getDataParam() {
        return dataParam;
    }

    public void setDataParam(String dataParam) {
        this.dataParam = dataParam == null ? null : dataParam.trim();
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

	public Integer getIsDynamicSql()
	{
		return isDynamicSql;
	}

	public void setIsDynamicSql(Integer isDynamicSql)
	{
		this.isDynamicSql = isDynamicSql;
	}

	public String getDynamicSql()
	{
		return dynamicSql;
	}

	public void setDynamicSql(String dynamicSql)
	{
		this.dynamicSql = dynamicSql;
	}

	public DynamicSqlFormBean getDynamicSqlFormBean()
	{
		return dynamicSqlFormBean;
	}

	public void setDynamicSqlFormBean(DynamicSqlFormBean dynamicSqlFormBean)
	{
		this.dynamicSqlFormBean = dynamicSqlFormBean;
	}
}