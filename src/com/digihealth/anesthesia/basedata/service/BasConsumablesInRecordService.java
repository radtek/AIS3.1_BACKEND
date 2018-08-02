package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesMedicineStorage;
import com.digihealth.anesthesia.basedata.po.BasConsumablesInRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesStorage;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.google.common.base.Objects;

@Service
public class BasConsumablesInRecordService extends BaseService
{

	//查询耗材入库信息列表
	public List<BasConsumablesInRecord> queryConsumablesInRecordList(SystemSearchFormBean systemSearchFormBean)
	{
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("id");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("DESC");
		}
		
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(null == filters)
		{
			filters = new ArrayList<Filter>();
		}
		Filter filter = new Filter();
		filter.setField("beid");
		filter.setValue(getBeid());
		filters.add(filter);
		
		return basConsumablesInRecordDao.queryConsumablesInRecordList(filters, systemSearchFormBean);
	}
	
	//查询耗材入库信息数量
	public int queryConsumablesInRecordListTotal(SystemSearchFormBean systemSearchFormBean)
	{
		List<Filter> filters = systemSearchFormBean.getFilters();
		return basConsumablesInRecordDao.queryConsumablesInRecordListTotal(filters);
	}
	
	//存储耗材
	@Transactional
	public void saveConsumablesInRecord(BasConsumablesInRecord basConsumablesInRecord,ResponseValue resp)
	{
		Integer id = basConsumablesInRecord.getId();
		if(null == id)
		{
			basConsumablesInRecord.setPinYin(PingYinUtil.getFirstSpell(basConsumablesInRecord.getInstrumentName()));
			basConsumablesInRecord.setOperateTime(new Date());
			basConsumablesInRecordDao.insertSelective(basConsumablesInRecord);
		}else
		{
			//状态为已核对时，修改入库数量，需同步更新库存数据
			if(Objects.equal(1, basConsumablesInRecord.getStatus())){
				Integer changeNum = basConsumablesInRecord.getNumber() - basConsumablesInRecord.getOldNumber();//计算修改后的差值
				
				String instrumentId = basConsumablesInRecord.getInstrumentId();//耗材名字
				String batch = basConsumablesInRecord.getBatch();//批次
				
				BasConsumablesStorage oldBasConsumablesStorage = basConsumablesStorageDao.selectConsumablesByMFSB(instrumentId, batch);
				if(null != oldBasConsumablesStorage){
					Integer number = oldBasConsumablesStorage.getNumber() + changeNum;
					if(number < 1){
						resp.setResultCode("30000002");
						resp.setResultMessage("该批次的耗材库存不足，无法调整入库数量!!!");
					}
					oldBasConsumablesStorage.setNumber(number);
					basConsumablesStorageDao.updateByPrimaryKey(oldBasConsumablesStorage);
				}
			}
			
			basConsumablesInRecord.setPinYin(PingYinUtil.getFirstSpell(basConsumablesInRecord.getInstrumentName()));
			basConsumablesInRecordDao.updateByPrimaryKeySelective(basConsumablesInRecord);
		}
	}
	
	//删除耗材记录
	@Transactional
	public void delConsumablesInRecord(Integer id)
	{
		basConsumablesInRecordDao.deleteByPrimaryKey(id);
	}
	
	//审核耗材
	@Transactional
	public void checkConsumablesInRecord(BasConsumablesInRecord basConsumablesInRecord)
	{
		if(null != basConsumablesInRecord)
		{
			basConsumablesInRecord.setCheckTime(new Date());
			basConsumablesInRecord.setStatus(1);
			basConsumablesInRecordDao.updateByPrimaryKeySelective(basConsumablesInRecord);
		}
		
	}
	
	//通过ID查询入库记录
	public BasConsumablesInRecord selectById(Integer id)
	{
		return basConsumablesInRecordDao.selectByPrimaryKey(id);
	}
	
}
