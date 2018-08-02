package com.digihealth.anesthesia.sysMng.formbean;

import java.util.List;

import com.digihealth.anesthesia.evt.formbean.Filter;
import com.wordnik.swagger.annotations.ApiModelProperty;

public class SearchViewFormBean
{
    private String viewName;
    private String column;
    private List<String> columnList;
    
    @ApiModelProperty(value = "第几页")
    private Integer pageNo;

    @ApiModelProperty(value = "一页展示的数量")
    private Integer pageSize;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序 asc 升序     desc 降序")
    private String orderBy;

    @ApiModelProperty(value = "其他过滤条件")
    private List<Filter> filters;

    @ApiModelProperty(value = "局点id")
    private String beid;
    public String getColumn()
    {
        return column;
    }
    public void setColumn(String column)
    {
        this.column = column;
    }
    public Integer getPageNo()
    {
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
    public String getSort()
    {
        return sort;
    }
    public void setSort(String sort)
    {
        this.sort = sort;
    }
    public String getOrderBy()
    {
        return orderBy;
    }
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }
    public List<Filter> getFilters()
    {
        return filters;
    }
    public void setFilters(List<Filter> filters)
    {
        this.filters = filters;
    }
    public String getBeid()
    {
        return beid;
    }
    public void setBeid(String beid)
    {
        this.beid = beid;
    }
    public String getViewName()
    {
        return viewName;
    }
    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }
    public List<String> getColumnList()
    {
        return columnList;
    }
    public void setColumnList(List<String> columnList)
    {
        this.columnList = columnList;
    }
}
