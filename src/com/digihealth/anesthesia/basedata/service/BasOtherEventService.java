package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasOtherEvent;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;

@Service
public class BasOtherEventService extends BaseService
{
    public List<BasOtherEvent> selectAllOtherEvent(SystemSearchFormBean systemSearchFormBean)
    {
        if (StringUtils.isEmpty(systemSearchFormBean.getBeid())) {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("id");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("ASC");
        }
        String filter = getSysFilter(systemSearchFormBean);
        return basOtherEventDao.selectAllOtherEvent(filter, systemSearchFormBean);
    }
    
    public int selectOtherEventTotal(SystemSearchFormBean systemSearchFormBean)
    {
        if (StringUtils.isEmpty(systemSearchFormBean.getBeid())) {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("id");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("ASC");
        }
        String filter = getSysFilter(systemSearchFormBean);
        return basOtherEventDao.selectOtherEventTotal(filter, systemSearchFormBean);
    }
    
    public String getSysFilter(SystemSearchFormBean systemSearchFormBean) {
        String filter = "";
        List<Filter> filters = systemSearchFormBean.getFilters();
        if(filters!=null&&filters.size()>0){
            for(int i = 0;i<filters.size();i++){
                if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
                    filter += " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
                }
            }
        }
        return filter;
    }
    
    public BasOtherEvent selectOtherEventById(String id)
    {
        return basOtherEventDao.selectByPrimaryKey(id);
    }
    
    @Transactional
    public void updateOtherEvent(BasOtherEvent basOtherEvent)
    {
        if (StringUtils.isBlank(basOtherEvent.getId()))
        {
            basOtherEvent.setBeid(getBeid());
            basOtherEvent.setId(GenerateSequenceUtil.generateSequenceNo());
            basOtherEventDao.insert(basOtherEvent);
        }
        else
        {
            basOtherEventDao.updateByPrimaryKeySelective(basOtherEvent);
        }
    }
    
    @Transactional
    public void deleteOtherEventById(String id)
    {
        basOtherEventDao.deleteByPrimaryKey(id);
    }
}
