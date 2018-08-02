package com.digihealth.anesthesia.doc.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocAnaesQualityControl;

@Service
public class DocAnaesQualityControlService extends BaseService
{
    public DocAnaesQualityControl selectAnaesQualityControlByRegOptId(String regOptId)
    {
        DocAnaesQualityControl anaesQualityControl = docAnaesQualityControlDao.selectAnaesQualityControlByRegOptId(regOptId);
        anaesQualityControl.setAllergicDetailList(convertStrToList(anaesQualityControl.getAllergicDetail()));
        anaesQualityControl.setComplicationDetailList(convertStrToList(anaesQualityControl.getComplicationDetail()));
        anaesQualityControl.setVenipuncComplicaDetailList(convertStrToList(anaesQualityControl.getVenipuncComplicaDetail()));
        return anaesQualityControl;
    }
    
    
    private List<SysCodeFormbean> convertStrToList(String s)
    {
        List<SysCodeFormbean> resultList = new ArrayList<SysCodeFormbean>();
        if (StringUtils.isNotBlank(s))
        {
            String[] ary1 = s.split(",");
            if (null != ary1 && ary1.length > 0)
            {
                for (String str : ary1)
                {
                    SysCodeFormbean formbean = new SysCodeFormbean();
                    formbean.setCodeName(str);
                    resultList.add(formbean);
                }
            }
        }
        return resultList;
    }
    
    private String convertListToStr(List<SysCodeFormbean> formbean)
    {
        String s = "";
        if (null != formbean && formbean.size() > 0)
        {
            for (SysCodeFormbean codeFormbean : formbean)
            {
                if ("".equals(s))
                {
                    s = codeFormbean.getCodeName();
                }
                else
                {
                    s = s + "," + codeFormbean.getCodeName();
                }
            }
        }
        
        return s;
    }
    
    @Transactional
    public void updateAnaesQualityControl(DocAnaesQualityControl anaesQualityControl)
    {
        anaesQualityControl.setAllergicDetail(convertListToStr(anaesQualityControl.getAllergicDetailList()));
        anaesQualityControl.setComplicationDetail(convertListToStr(anaesQualityControl.getComplicationDetailList()));
        anaesQualityControl.setVenipuncComplicaDetail(convertListToStr(anaesQualityControl.getVenipuncComplicaDetailList()));
        
        docAnaesQualityControlDao.updateByPrimaryKey(anaesQualityControl);
    }
}
