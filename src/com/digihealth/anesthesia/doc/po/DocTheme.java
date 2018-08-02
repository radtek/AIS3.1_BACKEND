/*
 * DocTheme.java
 * 2017-12-07 Created
 */
package com.digihealth.anesthesia.doc.po;

import java.io.Serializable;
import java.util.Date;

public class DocTheme  implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 文书主题id
     */
    private String docThemeId;

    /**
     * 文书主题名称
     */
    private String docThemeName;

    /**
     * 文书菜单id
     */
    private String menuId;
    
    /**
     * 应用模块（文书菜单对应的父菜单）
     */
    private String menuParentId;

    /**
     * 标记删除
     */
    private Integer isDelete;

    /**
     * 文书类型：1 定制文书，2自定义文书（单表）
     */
    private Integer docType;

    /**
     * 文书时间
     */
    private Date createTime;

    /**
     * 角色权限：管理员，医生，护士等
     */
    private String roleIds;

    /**
     * 定制文书url
     */
    private String url;

    /**
     * 审核设置1.未审核2.审核中，3.审核通过4.审核不通过
     */
    private Integer themeState;

    private String themeType;

    /**
     * 局点id
     */
    private String beid;
    
    /**
     * 原生HTML元素
     */
    private String originalHtml;

    /**
     * 格式化后的元素
     */
    private String parseHtml;
    
    //角色
    private String roles;

    public String getDocThemeId() {
        return docThemeId;
    }

    public void setDocThemeId(String docThemeId) {
        this.docThemeId = docThemeId == null ? null : docThemeId.trim();
    }

    public String getDocThemeName() {
        return docThemeName;
    }

    public void setDocThemeName(String docThemeName) {
        this.docThemeName = docThemeName == null ? null : docThemeName.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }
    
    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId == null ? null : menuParentId.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds == null ? null : roleIds.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getThemeState() {
        return themeState;
    }

    public void setThemeState(Integer themeState) {
        this.themeState = themeState;
    }

    public String getThemeType() {
        return themeType;
    }

    public void setThemeType(String themeType) {
        this.themeType = themeType == null ? null : themeType.trim();
    }

    public String getOriginalHtml() {
        return originalHtml;
    }

    public void setOriginalHtml(String originalHtml) {
        this.originalHtml = originalHtml == null ? null : originalHtml.trim();
    }

    public String getParseHtml() {
        return parseHtml;
    }

    public void setParseHtml(String parseHtml) {
        this.parseHtml = parseHtml == null ? null : parseHtml.trim();
    }

	public String getRoles()
	{
		return roles;
	}

	public void setRoles(String roles)
	{
		this.roles = roles;
	}

	public String getBeid()
	{
		return beid;
	}

	public void setBeid(String beid)
	{
		this.beid = beid;
	}
    
}