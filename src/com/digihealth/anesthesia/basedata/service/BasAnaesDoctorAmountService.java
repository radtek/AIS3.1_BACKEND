package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesDoctorAmount;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.StringUtils;

@Service
public class BasAnaesDoctorAmountService extends BaseService
{
    public List<BasAnaesDoctorAmount> selectAllAnaesDoctorAmount(SystemSearchFormBean systemSearchFormBean)
    {
        if (StringUtils.isEmpty(systemSearchFormBean.getBeid())) {
            systemSearchFormBean.setBeid(getBeid());
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
            systemSearchFormBean.setSort("recordMonth");
        }
        if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
            systemSearchFormBean.setOrderBy("DESC");
        }
        return basAnaesDoctorAmountDao.selectAllAnaesDoctorAmount(systemSearchFormBean);
    }
    
    public int selectAnaesDoctorAmountTotal(SystemSearchFormBean systemSearchFormBean)
    {
        if (StringUtils.isEmpty(systemSearchFormBean.getBeid())) {
            systemSearchFormBean.setBeid(getBeid());
        }
        return basAnaesDoctorAmountDao.selectAnaesDoctorAmountTotal(systemSearchFormBean);
    }
    
    @Transactional
    public void updateAnaesDoctorAmount(BasAnaesDoctorAmount anaesDoctorAmount)
    {
        String beid = getBeid();
        BasAnaesDoctorAmount oldAnaesDoctorAmount = basAnaesDoctorAmountDao.selectByPrimaryKey(anaesDoctorAmount.getRecordMonth(), beid);
        if (null == oldAnaesDoctorAmount)
        {
            anaesDoctorAmount.setBeid(beid);
            basAnaesDoctorAmountDao.insert(anaesDoctorAmount);
        }
        else
        {
            basAnaesDoctorAmountDao.updateByPrimaryKey(anaesDoctorAmount);
        }
    }
    
    @Transactional
    public void deleteAnaesDoctorAmount(BasAnaesDoctorAmount anaesDoctorAmount)
    {
        basAnaesDoctorAmountDao.deleteByPrimaryKey(anaesDoctorAmount.getRecordMonth(), getBeid());
    }
    
    public BasAnaesDoctorAmount selectByPrimaryKey(String recordMonth, String beid)
    {
        return basAnaesDoctorAmountDao.selectByPrimaryKey(recordMonth, beid);
    }
}
