package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasSelfPayInstrument;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.evt.formbean.Filter;

@Service
public class BasSelfPayInstrumentService extends BaseService
{
    public List<BasSelfPayInstrument> searchSelfPayInstrument(BaseInfoQuery baseQuery)
    {
        if (StringUtils.isBlank(baseQuery.getBeid()))
        {
            baseQuery.setBeid(getBeid());
        }
        return basSelfPayInstrumentDao.searchSelfPayInstrument(baseQuery);
    }
    
    public List<BasSelfPayInstrument> querySelfPayInstrumentList(SystemSearchFormBean systemSearchFormBean){
        if (StringUtils.isBlank(systemSearchFormBean.getBeid()))
        {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("id");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("ASC");
        }
        String filter = "";
        List<Filter> filters = systemSearchFormBean.getFilters();
        if(filters!=null&&filters.size()>0){
            for(int i = 0;i<filters.size();i++){
                if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
                    filter = filter + " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
                }
            }
        }
        return basSelfPayInstrumentDao.querySelfPayInstrumentList(filter, systemSearchFormBean);
    }
    
    public int querySelfPayInstrumentTotal(SystemSearchFormBean systemSearchFormBean){
        if (StringUtils.isBlank(systemSearchFormBean.getBeid()))
        {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("id");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("ASC");
        }
        String filter = "";
        List<Filter> filters = systemSearchFormBean.getFilters();
        if(filters!=null&&filters.size()>0){
            for(int i = 0;i<filters.size();i++){
                if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
                    filter = filter + " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
                }
            }
        }
        return basSelfPayInstrumentDao.querySelfPayInstrumentTotal(filter, systemSearchFormBean);
    }
    
    public BasSelfPayInstrument searchSelfPayInstrumentById(String id){
        return basSelfPayInstrumentDao.searchSelfPayInstrumentById(id);
    }
    
    @Transactional
    public String updateInstrument(BasSelfPayInstrument selfPayInstrument){
        if (StringUtils.isBlank(selfPayInstrument.getBeid()))
        {
            selfPayInstrument.setBeid(getBeid());
        }
        
        selfPayInstrument.setPinYin(PingYinUtil.getFirstSpell(selfPayInstrument.getName()));
        if (StringUtils.isNotBlank(selfPayInstrument.getId())) {
            basSelfPayInstrumentDao.updateByPrimaryKey(selfPayInstrument);
            return "修改自费耗材成功";
        } else {
            selfPayInstrument.setId(GenerateSequenceUtil.generateSequenceNo());
            basSelfPayInstrumentDao.insert(selfPayInstrument);
            return "创建自费耗材成功";
        }
    }
    
    @Transactional
    public void deleteSelfPayInstrument(BasSelfPayInstrument selfPayInstrument){
    	basSelfPayInstrumentDao.deleteByPrimaryKey(selfPayInstrument.getId());
    }
}
