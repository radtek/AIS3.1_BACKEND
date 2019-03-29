package com.digihealth.anesthesia.evt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.OperDefFormBean;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;

@Service
public class EvtOptRealOperService extends BaseService {

	public List<OperDefFormBean> getSelectOptRealOperList(SearchFormBean searchBean) {
		if (StringUtils.isBlank(searchBean.getBeid())) {
			searchBean.setBeid(getBeid());
		}
		return evtOptRealOperDao.getSelectOptRealOperList(searchBean);
	}

	public List<EvtOptRealOper> searchOptRealOperList(SearchFormBean searchBean) {
		if (StringUtils.isBlank(searchBean.getBeid())) {
			searchBean.setBeid(getBeid());
		}
		return evtOptRealOperDao.searchOptRealOperList(searchBean);
	}

	/**
	 * 实施手术
	 * 
	 * @param OptRealOper
	 */
	@Transactional
    public void saveOptRealOper(List<EvtOptRealOper> optRealOperList)
    {
        if (null != optRealOperList && optRealOperList.size() > 0)
        {
            String docId = optRealOperList.get(0).getDocId();
            evtOptRealOperDao.deleteByDocId(docId);
            for (EvtOptRealOper optRealOper : optRealOperList)
            {
                if (StringUtils.isBlank(optRealOper.getOperDefId()) && StringUtils.isBlank(optRealOper.getName()))
                {
                    continue;
                }
                
                if (StringUtils.isBlank(optRealOper.getOptRealOperId()))
                {
                    optRealOper.setOptRealOperId(GenerateSequenceUtil.generateSequenceNo()); 
                }
                
                if (StringUtils.isBlank(optRealOper.getOperDefId()))
                {
                    String operDefId = GenerateSequenceUtil.generateSequenceNo();
                    optRealOper.setOperDefId(operDefId);
                    
                    BasOperdef operdef = new BasOperdef();
                    operdef.setOperdefId(operDefId);
                    operdef.setName(optRealOper.getName());
                    operdef.setPinYin(PingYinUtil.getFirstSpell(optRealOper.getName()));
                    operdef.setEnable(1);
                    operdef.setBeid(getBeid());
                    basOperdefDao.insert(operdef);
                }
                evtOptRealOperDao.insert(optRealOper);
            }
        }
        
        String regOptId = "";
        if (optRealOperList.size() > 0)
        {
            DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(optRealOperList.get(0).getDocId());
            regOptId = anaesRecord.getRegOptId();
        }
        
        LogUtils.saveOperateLog(regOptId,
            LogUtils.OPT_TYPE_INFO_SAVE,
            LogUtils.OPT_MODULE_OPER_RECORD,
            "实施手术保存",
            JsonType.jsonType(optRealOperList),
            UserUtils.getUserCache(),
            getBeid());
    }
	
	/**
	 * 保存实施手术（永兴定制）
	 * 
	 * @param OptRealOper
	 */
	@Transactional
    public void saveOptRealOperYXRM(List<EvtOptRealOper> optRealOperList)
    {
		int maxoptLevel = 1;
        int maxCutLevel = 1;
        Map<String,Integer> optLevelMap = new HashMap<String,Integer>();
        optLevelMap.put("一级",1);
        optLevelMap.put("二级",2);
        optLevelMap.put("三级",3);
        optLevelMap.put("四级",4);
        
        Map<Integer,String> optLevelMapTwo = new HashMap<Integer,String>();
        optLevelMapTwo.put(1,"一级");
        optLevelMapTwo.put(2,"二级");
        optLevelMapTwo.put(3,"三级");
        optLevelMapTwo.put(4,"四级");
        
        if (null != optRealOperList && optRealOperList.size() > 0)
        {
            String docId = optRealOperList.get(0).getDocId();
            evtOptRealOperDao.deleteByDocId(docId);
            
            for (EvtOptRealOper optRealOper : optRealOperList)
            {
                if (StringUtils.isBlank(optRealOper.getOperDefId()) && StringUtils.isBlank(optRealOper.getName()))
                {
                    continue;
                }
                
                if (StringUtils.isBlank(optRealOper.getOptRealOperId()))
                {
                    optRealOper.setOptRealOperId(GenerateSequenceUtil.generateSequenceNo()); 
                }
                
                String operDefId = optRealOper.getOperDefId();
                if (StringUtils.isNotBlank(operDefId))
                {
                	BasOperdef basOperdef = basOperdefDao.queryOperdefById(operDefId);
                	String optLevel = basOperdef.getOptLevel();
                	Integer cutLevel = basOperdef.getCutLevel();
                	if(null != cutLevel && cutLevel.intValue() >maxCutLevel )
                	{
                		maxCutLevel = cutLevel.intValue();
                	}
                	if(StringUtils.isNotBlank(optLevel))
                	{
                		int level = optLevelMap.get(optLevel);
                		if(level > maxoptLevel)
                		{
                			maxoptLevel = level;
                		}
                	}
                }
                evtOptRealOperDao.insert(optRealOper);
            }
        }
        
        String regOptId = "";
        DocAnaesRecord anaesRecord = null;
        if (optRealOperList.size() > 0)
        {
            anaesRecord = docAnaesRecordDao.searchAnaesRecordById(optRealOperList.get(0).getDocId());
            if(null != anaesRecord)
            {
            	regOptId = anaesRecord.getRegOptId();
            }
            
            
        }
        
        Boolean flag = false;
        BasRegOpt basRegOpt = basRegOptDao.searchRegOptById(regOptId);
        if(null != basRegOpt)
        {
        	String optLevel = basRegOpt.getOptLevel();
        	Integer cutLevel = basRegOpt.getCutLevel();
        	if(null != cutLevel)
        	{
        		if(cutLevel.intValue() != maxCutLevel)
        		{
        			flag = true;
        			basRegOpt.setCutLevel(maxCutLevel);
        		}
        	}else
        	{
        		flag = true;
        		basRegOpt.setCutLevel(maxCutLevel);
        	}
        	
        	if(StringUtils.isNotBlank(optLevel))
        	{
        		int level = optLevelMap.get(optLevel);
        		if(level != maxoptLevel)
        		{
        			flag = true;
        			basRegOpt.setOptLevel(optLevelMapTwo.get(maxoptLevel));
        		}
        	}else
        	{
        		flag = true;
        		basRegOpt.setOptLevel(optLevelMapTwo.get(maxoptLevel));
        	}
        }
        
        //最高切口等级和手术等级改变时，修改手术基础信息对应字段
        if(flag)
        {
        	basRegOptDao.updateByPrimaryKey(basRegOpt);
        	if(null != anaesRecord)
            {
        		anaesRecord.setOptLevel(optLevelMapTwo.get(maxoptLevel));
        		docAnaesRecordDao.updateByPrimaryKey(anaesRecord);
            }
        }
        
        LogUtils.saveOperateLog(regOptId,
            LogUtils.OPT_TYPE_INFO_SAVE,
            LogUtils.OPT_MODULE_OPER_RECORD,
            "实施手术保存",
            JsonType.jsonType(optRealOperList),
            UserUtils.getUserCache(),
            getBeid());
    }
	

	/**
	 * 检验事件
	 * 
	 * @param OptRealOper
	 */
	@Transactional
	public void insertOptRealOper(EvtOptRealOper optRealOper) {
		optRealOper.setOptRealOperId(GenerateSequenceUtil.generateSequenceNo());
		evtOptRealOperDao.insert(optRealOper);
	}

	/**
	 * 检验事件
	 * 
	 * @param OptRealOper
	 */
	@Transactional
	public void updateOptRealOper(EvtOptRealOper optRealOper) {
		evtOptRealOperDao.updateByPrimaryKeySelective(optRealOper);
	}

	/**
	 * 删除
	 * 
	 * @param optRealOper
	 */
	@Transactional
	public void deleteOptRealOper(EvtOptRealOper optRealOper) {
		evtOptRealOperDao.deleteByPrimaryKey(optRealOper.getOptRealOperId());
	}
}
