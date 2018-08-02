package com.digihealth.anesthesia.common.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.operProceed.dao.BasObserveDataDao;
import com.digihealth.anesthesia.operProceed.po.BasObserveData;

/**
 * 移除一天前的采集数据（秒表），保证表的数据不至于太大
 */
public class DelDatMonitorDateTask
{
	private final static Logger logger = Logger.getLogger(DelDatMonitorDateTask.class);
	private int taskTotal = 0;
	private List<String> idList = new ArrayList<String>();

	public void job()
	{
		getObseverDateForDel();
	}

	private void getObseverDateForDel()
	{
		logger.info("DelDatMonitorDateTask getObseverDateForDel is start.");
		
		String deleteDay = DateUtils.DateToString(DateUtils.addDays(new Date(), -1));
		//查询出待删除的数据集
		BasRegOptDao basRegOptDao = SpringContextHolder.getBean(BasRegOptDao.class);
		List<BasRegOpt> regOptList = basRegOptDao.getRegOptListByOperTime(deleteDay);
		
		List<String> regOptIds = new ArrayList<String>();
		if(null!= regOptList && regOptList.size()>0){
			for (BasRegOpt basRegOpt : regOptList) {
				regOptIds.add(basRegOpt.getRegOptId());
			}
		}
		if(null!=regOptIds && regOptIds.size()>0){
			goDataByRegOptId(regOptIds);
		}else{
			logger.info("DelDatMonitorDateTask getObseverDateForDel data is null.");
		}
        this.taskTotal = 0;
		logger.info("DelDatMonitorDateTask getObseverDateForDel is end.");
	}

	
	private void goDataByRegOptId(List<String> regOptIds)
	{
		boolean flag = false;
		//从bas_observe_data 查询出500条数据
		idList = selectDatMonitorDateList(regOptIds);
		if(null != idList && idList.size()>0)
		{
			//将这些数据从bas_observe_data表删除
			deleteMonitorData(idList);
			flag = true;
			idList.clear();
		}
		
		taskTotal ++;
		//还能从数据库里查到记录并且循环次数不能超过100万,超过了停下来，起到保护作用。
		if(flag && taskTotal<1000000)
		{
			goDataByRegOptId(regOptIds);
		}
	}
	
	/**
	 * 根据时间查询1000条采集数据
	 */
	private List<String> selectDatMonitorDateList(List<String> regOptIds) {
		List<String> mdidList = new ArrayList<String>();
		BasObserveDataDao basObserveDataDao = SpringContextHolder.getBean(BasObserveDataDao.class);
		List<BasObserveData> obsList = basObserveDataDao.searchObsListByRegOptId(regOptIds);
		if(null != obsList && obsList.size()>0)
		{
			 for (BasObserveData basObserveData : obsList) {
                 mdidList.add(basObserveData.getId());
             }
		}
		return mdidList;
	}
	
	/**
	 * 根据传入的Id集合删除数据
	 * @param observeIds
	 * @return
	 */
	@SuppressWarnings("unused")
	private int deleteMonitorData(List<String> observeIds) {
		BasObserveDataDao basObserveDataDao = SpringContextHolder.getBean(BasObserveDataDao.class);
		return basObserveDataDao.deleteObsByIds(observeIds);
	}
	
}
