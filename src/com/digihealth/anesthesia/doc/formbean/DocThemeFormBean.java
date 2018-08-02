package com.digihealth.anesthesia.doc.formbean;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

public class DocThemeFormBean
{

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
     * 原生HTML元素
     */
    private String originalHtml;

    /**
     * 格式化后的元素
     */
    private String parseHtml;
    
    /**
     * 局点id
     */
    private String beid;
    
    //角色名称
    private String roleNames;
    
    //应用模块（菜单对应的父节点名字）
    private String menuParterNames;
    
    /**
     * 主题对应的标题列表
     */
    private List<DocTitleFormBean> docTitleFormbean;
    
    //对应新建的表的数据记录
    Record record;
    
    //动态sql查询出来的下拉值
    Map<String,List<Map<String,Object>>> pullDownMap;
    
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

	public List<DocTitleFormBean> getDocTitleFormbean()
	{
		return docTitleFormbean;
	}

	public void setDocTitleFormbean(List<DocTitleFormBean> docTitleFormbean)
	{
		this.docTitleFormbean = docTitleFormbean;
	}

	public String getBeid()
	{
		return beid;
	}

	public void setBeid(String beid)
	{
		this.beid = beid;
	}

	public String getRoleNames()
	{
		return roleNames;
	}

	public void setRoleNames(String roleNames)
	{
		this.roleNames = roleNames;
	}

	public String getMenuParterNames()
	{
		return menuParterNames;
	}

	public void setMenuParterNames(String menuParterNames)
	{
		this.menuParterNames = menuParterNames;
	}

	public Record getRecord()
	{
		return record;
	}

	public void setRecord(Record record)
	{
		this.record = record;
	}

	public Map<String, List<Map<String, Object>>> getPullDownMap()
	{
		return pullDownMap;
	}

	public void setPullDownMap(Map<String, List<Map<String, Object>>> pullDownMap)
	{
		this.pullDownMap = pullDownMap;
	}
}
