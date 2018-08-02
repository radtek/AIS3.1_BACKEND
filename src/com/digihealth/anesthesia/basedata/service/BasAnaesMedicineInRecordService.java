package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesMedicineInRecord;
import com.digihealth.anesthesia.basedata.po.BasAnaesMedicineStorage;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.google.common.base.Objects;

@Service
public class BasAnaesMedicineInRecordService extends BaseService
{

	//查询毒麻药入库信息列表
	public List<BasAnaesMedicineInRecord> queryMedicineInRecordList(SystemSearchFormBean systemSearchFormBean)
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
		
		return basAnaesMedicineInRecordDao.queryMedicineInRecordList(filters, systemSearchFormBean);
	}
	
	//查询毒麻药入库信息数量
	public int queryMedicineInRecordListTotal(SystemSearchFormBean systemSearchFormBean)
	{
		List<Filter> filters = systemSearchFormBean.getFilters();
		return basAnaesMedicineInRecordDao.queryMedicineInRecordListTotal(filters);
	}
	
	//存储毒麻药
	@Transactional
	public void saveMedicineInRecord(BasAnaesMedicineInRecord basAnaesMedicineInRecord,ResponseValue resp)
	{
		Integer id = basAnaesMedicineInRecord.getId();
		if(null == id)
		{
			basAnaesMedicineInRecord.setPinYin(PingYinUtil.getFirstSpell(basAnaesMedicineInRecord.getMedicineName()));
			basAnaesMedicineInRecord.setOperateTime(new Date());
			basAnaesMedicineInRecordDao.insertSelective(basAnaesMedicineInRecord);
		}else
		{
			//状态为已核对时，修改入库数量，需同步更新库存数据
			if(Objects.equal(1, basAnaesMedicineInRecord.getStatus())){
				Integer changeNum = basAnaesMedicineInRecord.getNumber() - basAnaesMedicineInRecord.getOldNumber();//计算修改后的差值
				
				String medicineName = basAnaesMedicineInRecord.getMedicineName();//药品名字
				String firm = basAnaesMedicineInRecord.getFirm();//厂家
				String spec = basAnaesMedicineInRecord.getSpec();//规格
				String batch = basAnaesMedicineInRecord.getBatch();//批次
				BasAnaesMedicineStorage oldBasAnaesMedicineStorage =  basAnaesMedicineStorageDao.selectMedicineByMFSB(medicineName, firm, spec, batch);
				if(null != oldBasAnaesMedicineStorage){
					Integer number = oldBasAnaesMedicineStorage.getNumber() + changeNum;
					if(number < 1){
						resp.setResultCode("30000002");
						resp.setResultMessage("该批次的药品库存不足，无法调整入库数量!!!");
					}
					oldBasAnaesMedicineStorage.setNumber(number);
					basAnaesMedicineStorageDao.updateByPrimaryKey(oldBasAnaesMedicineStorage);
				}
			}
			basAnaesMedicineInRecord.setPinYin(PingYinUtil.getFirstSpell(basAnaesMedicineInRecord.getMedicineName()));
			basAnaesMedicineInRecordDao.updateByPrimaryKeySelective(basAnaesMedicineInRecord);
		}
	}
	
	//删除毒麻药记录
	@Transactional
	public void delMedicineInRecord(Integer id)
	{
		basAnaesMedicineInRecordDao.deleteByPrimaryKey(id);
	}
	
	//审核毒麻药
	@Transactional
	public void checkMedicineInRecord(BasAnaesMedicineInRecord basAnaesMedicineInRecord)
	{
		if(null != basAnaesMedicineInRecord)
		{
			basAnaesMedicineInRecord.setCheckTime(new Date());
			basAnaesMedicineInRecord.setStatus(1);
			basAnaesMedicineInRecordDao.updateByPrimaryKeySelective(basAnaesMedicineInRecord);
		}
		
	}
	
	//通过ID查询入库记录
	public BasAnaesMedicineInRecord selectById(Integer id)
	{
		return basAnaesMedicineInRecordDao.selectByPrimaryKey(id);
	}
	
}
