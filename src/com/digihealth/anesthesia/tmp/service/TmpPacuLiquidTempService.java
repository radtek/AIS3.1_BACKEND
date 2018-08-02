package com.digihealth.anesthesia.tmp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SearchLiquidTempFormBean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.po.BasUser;
import com.digihealth.anesthesia.tmp.po.TmpPacuLiquidTemp;

@Service
public class TmpPacuLiquidTempService extends BaseService
{
    public List<TmpPacuLiquidTemp> queryLiquidTempList(SearchLiquidTempFormBean searchLiquidTempFormBean)
    {
        List<TmpPacuLiquidTemp> patLiquidTempList = new ArrayList<TmpPacuLiquidTemp>();
        
        if (StringUtils.isBlank(searchLiquidTempFormBean.getRoleType()))
        {
            BasUser user = basUserDao.getByLoginName(searchLiquidTempFormBean.getCreateUser(), getBeid());
            searchLiquidTempFormBean.setRoleType(user.getRoleType());
        }
        if(StringUtils.isEmpty(searchLiquidTempFormBean.getSort())){
            searchLiquidTempFormBean.setSort("tempId");
        }
        if(StringUtils.isEmpty(searchLiquidTempFormBean.getOrderBy())){
            searchLiquidTempFormBean.setOrderBy("ASC");
        }
        if(StringUtils.isEmpty(searchLiquidTempFormBean.getBeid())){
            searchLiquidTempFormBean.setBeid(getBeid());
        }
        List<Filter> filters = searchLiquidTempFormBean.getFilters();
        
        patLiquidTempList = tmpPacuLiquidTempDao.selectLiquidTempByFormBean(filters, searchLiquidTempFormBean);
        
        return patLiquidTempList;
    }
    
    
    public Integer queryLiquidTempTotal(SearchLiquidTempFormBean searchLiquidTempFormBean)
    {
        if (StringUtils.isBlank(searchLiquidTempFormBean.getRoleType()))
        {
            BasUser user = basUserDao.getByLoginName(searchLiquidTempFormBean.getCreateUser(), getBeid());
            searchLiquidTempFormBean.setRoleType(user.getRoleType());
        }
        if(StringUtils.isEmpty(searchLiquidTempFormBean.getBeid())){
            searchLiquidTempFormBean.setBeid(getBeid());
        }
        
        List<Filter> filters = searchLiquidTempFormBean.getFilters();
        return tmpPacuLiquidTempDao.selectLiquidTempTotalByFormBean(filters, searchLiquidTempFormBean);
    }
    
    @Transactional
    public void updateLiquidTemp(TmpPacuLiquidTemp pacuLiquidTemp)
    {
        if (StringUtils.isBlank(pacuLiquidTemp.getPinyin()))
        {
        	pacuLiquidTemp.setPinyin(PingYinUtil.getFirstSpell(pacuLiquidTemp.getTempContent()));
        }
        
        if (StringUtils.isBlank(pacuLiquidTemp.getBeid()))
        {
            pacuLiquidTemp.setBeid(getBeid());
        }
        
        if (StringUtils.isBlank(pacuLiquidTemp.getTempId()))
        {
            pacuLiquidTemp.setTempId(GenerateSequenceUtil.generateSequenceNo());
        	tmpPacuLiquidTempDao.insert(pacuLiquidTemp);
        }
        else
        {
        	tmpPacuLiquidTempDao.updateByPrimaryKeySelective(pacuLiquidTemp);
        }
    }
    
    @Transactional
    public void deleteLiquidTemp(TmpPacuLiquidTemp pacuLiquidTemp)
    {
    	tmpPacuLiquidTempDao.deleteByPrimaryKey(pacuLiquidTemp.getTempId());
    }
}
