package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesEvent;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;

@Service
public class BasAnaesEventService extends BaseService
{
    public List<BasAnaesEvent> selectAllAnaesEvent(SystemSearchFormBean systemSearchFormBean)
    {
        if (StringUtils.isEmpty(systemSearchFormBean.getBeid())) {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("eventValue");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("ASC");
        }
        String filter = getSysFilter(systemSearchFormBean);
        return basAnaesEventDao.selectAllAnaesEvent(filter, systemSearchFormBean);
    }
    
    public int selectAnaesEventTotal(SystemSearchFormBean systemSearchFormBean)
    {
        if (StringUtils.isEmpty(systemSearchFormBean.getBeid())) {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("eventValue");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("ASC");
        }
        String filter = getSysFilter(systemSearchFormBean);
        return basAnaesEventDao.selectAnaesEventTotal(filter, systemSearchFormBean);
    }
    
    public String getSysFilter(SystemSearchFormBean systemSearchFormBean) {
        String filter = "";
        List<Filter> filters = systemSearchFormBean.getFilters();
        if(filters!=null&&filters.size()>0){
            for(int i = 0;i<filters.size();i++){
                String value = filters.get(i).getValue();
                String field = filters.get(i).getField();
                if(!StringUtils.isEmpty(value)){
                    if ("docType".equals(field))
                    {
                        if ("1".equals(value))
                        {
                            filter += " AND eventValue NOT IN('1001','1002')";
                        }
                        else if ("2".equals(value))
                        {
                            filter += " AND eventValue NOT IN('1','2','4','5','8','9')";
                        }
                    }
                    else
                    {
                        filter += " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
                    }
                }
            }
        }
        return filter;
    }
    
    public BasAnaesEvent selectAnaesEventById(String id)
    {
        return basAnaesEventDao.selectByPrimaryKey(id);
    }
    
    @Transactional
    public void updateAnaesEvent(BasAnaesEvent basAnaesEvent, ResponseValue resp)
    {
        if (StringUtils.isBlank(basAnaesEvent.getId()))
        {
            String beid = getBeid();
            Integer maxEventVlaue = basAnaesEventDao.selectMaxEventValue(beid);
            if (null == maxEventVlaue)
            {
                basAnaesEvent.setEventValue(1);
            }
            else
            {
                basAnaesEvent.setEventValue(maxEventVlaue + 1);
            }
            basAnaesEvent.setBeid(beid);
            basAnaesEvent.setPinyin(PingYinUtil.getFirstSpell(basAnaesEvent.getName()));
            basAnaesEvent.setId(GenerateSequenceUtil.generateSequenceNo());
            basAnaesEventDao.insert(basAnaesEvent);
        }
        else
        {
            if (StringUtils.isBlank(basAnaesEvent.getPinyin()))
            {
                basAnaesEvent.setPinyin(PingYinUtil.getFirstSpell(basAnaesEvent.getName()));
            }
            basAnaesEventDao.updateByPrimaryKeySelective(basAnaesEvent);
        }
    }
    
    @Transactional
    public void deleteAnaesEventById(String id)
    {
        basAnaesEventDao.deleteByPrimaryKey(id);
    }
}
