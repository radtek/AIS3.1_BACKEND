package com.digihealth.anesthesia.sysMng.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.formbean.SearchViewFormBean;
import com.digihealth.anesthesia.sysMng.po.BasEntity;

@Service
public class BasViewService extends BaseService
{
    public List<Map<String, Object>> selectAllView()
    {
        return basViewDao.selectAllView();
    }
    
    public List<BasEntity> selectAllColumnsOfView(String viewName)
    {
        return basViewDao.selectAllColumnsOfView(viewName);
    }
    
    public List<Map<String,Object>> selectByColumns(SearchViewFormBean formBean)
    {
        if (StringUtils.isEmpty(formBean.getBeid())) {
            formBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(formBean.getSort())){
            formBean.setSort(formBean.getColumnList().get(0));
        }
        if(StringUtils.isEmpty(formBean.getOrderBy())){
            formBean.setOrderBy("ASC");
        }
        
        formBean.setColumn(StringUtils.getStringByList(formBean.getColumnList()));
        String filter = getSysFilter(formBean);
        
        return basViewDao.selectByColumns(filter, formBean);
    }
    
    public int selectByColumnsTotal(SearchViewFormBean formBean)
    {
        if (StringUtils.isEmpty(formBean.getBeid())) {
            formBean.setBeid(getBeid());
        }
        String filter = getSysFilter(formBean);
        
        return basViewDao.selectByColumnsTotal(filter, formBean);
    }
    
    public String getSysFilter(SearchViewFormBean formBean) {
        String filter = "";
        List<Filter> filters = formBean.getFilters();
        if(filters!=null&&filters.size()>0){
            for(int i = 0;i<filters.size();i++){
                if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
                    filter += " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
                }
            }
        }
        return filter;
    }
}
