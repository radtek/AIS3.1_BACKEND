package com.digihealth.anesthesia.basedata.formbean;

import java.util.List;

import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;


public class SearchLiquidTempFormBean
{
    private Integer pageNo;
    private Integer pageSize;
    private String sort;
    private String orderBy;
    private String createUser;
    private Integer type;
    private String executiveLevel;
    private List<Filter> filters;
    private String beid;
    private String roleType;
    public String getRoleType()
    {
        return roleType;
    }
    public void setRoleType(String roleType)
    {
        this.roleType = roleType;
    }
    public String getBeid()
    {
        return beid;
    }
    public void setBeid(String beid)
    {
        this.beid = beid;
    }
    public Integer getPageNo() {
        if(pageNo!=null){
            return (pageNo - 1) * pageSize;
        }
        return pageNo;
    }
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    public Integer getPageSize()
    {
        return pageSize;
    }
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    public String getOrderBy() {
        return StringUtils.sqlValidate(orderBy);
    }
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    public String getCreateUser()
    {
        return createUser;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }
    public Integer getType()
    {
        return type;
    }
    public void setType(Integer type)
    {
        this.type = type;
    }
    public String getExecutiveLevel()
    {
        return executiveLevel;
    }
    public void setExecutiveLevel(String executiveLevel)
    {
        this.executiveLevel = executiveLevel;
    }
    public List<Filter> getFilters()
    {
        return filters;
    }
    public void setFilters(List<Filter> filters)
    {
        this.filters = filters;
    }
}
