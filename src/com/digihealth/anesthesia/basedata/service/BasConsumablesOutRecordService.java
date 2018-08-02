package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digihealth.anesthesia.basedata.formbean.BasMedicineRegOptFormBean;
import com.digihealth.anesthesia.basedata.formbean.BatAddConsumablesOutRecordFormbean;
import com.digihealth.anesthesia.basedata.formbean.BatAddConsumablesRetreatFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesLoseRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesOutRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesRetreatRecord;
import com.digihealth.anesthesia.basedata.po.BasConsumablesStorage;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.exception.CustomException;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;

@Service
public class BasConsumablesOutRecordService extends BaseService
{
    //新增领用耗材记录
	@Transactional
	public void addConsumablesOutRecord(BasConsumablesOutRecord basConsumablesOutRecord,ResponseValue resp)
	{
		Integer storageId = basConsumablesOutRecord.getStorageId();
		int outNumber = basConsumablesOutRecord.getOutNumber();
		BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(storageId);
		if(null != basConsumablesStorage)
		{
			int number = basConsumablesStorage.getNumber();
			if(outNumber>number)
			{
				resp.setResultCode("10000002");
	            resp.setResultMessage("领用的数量不能大于库存的数量");
			}else
			{
				String outType = basConsumablesOutRecord.getOutType();
				String regOptId = basConsumablesOutRecord.getRegOptId();
				basConsumablesOutRecord.setActualNumber(outNumber);
				basConsumablesOutRecord.setOutTime(basConsumablesOutRecord.getOutTime()==null?new Date():basConsumablesOutRecord.getOutTime());
				basConsumablesOutRecord.setPinYin(PingYinUtil.getFirstSpell(basConsumablesOutRecord.getInstrumentName()));
				basConsumablesOutRecordDao.insertSelective(basConsumablesOutRecord);
				//如果是手术取药，更新手术信息表outMedicine字段
				if("2".equals(outType))
				{
					BasRegOpt basRegOpt = basRegOptDao.searchRegOptById(regOptId);
					basRegOpt.setOutInstrument(1);
					basRegOptDao.updateByPrimaryKeySelective(basRegOpt);
				}
				
				//更新取药记录表
				basConsumablesStorage.setNumber(number-outNumber);
				basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
			}
		}else
		{
			resp.setResultCode("10000003");
            resp.setResultMessage("传输的库存记录不存在！");
		}
	}
	
	@Transactional
	public void batAddConsumablesOutRecord(BatAddConsumablesOutRecordFormbean batRecord,ResponseValue resp)
	{
		List<BasConsumablesOutRecord> resList = batRecord.getResList();
		if(resList!=null && resList.size()>0){
			for (BasConsumablesOutRecord basConsumablesOutRecord : resList) {
				Integer id = basConsumablesOutRecord.getId();
				int outNumber = basConsumablesOutRecord.getOutNumber();
				BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(id);
				if(null != basConsumablesStorage)
				{
					int number = basConsumablesStorage.getNumber();
					if(outNumber>number)
					{
						resp.setResultCode("10000002");
			            resp.setResultMessage(basConsumablesOutRecord.getInstrumentName()+"取耗材的数量不能大于库存的数量");
			            throw new CustomException(basConsumablesOutRecord.getInstrumentName()+"取耗材的数量不能大于库存的数量");
					}else
					{
						String outType = batRecord.getOutType();
						String regOptId = basConsumablesOutRecord.getRegOptId();
						basConsumablesOutRecord.setStorageId(id);
						basConsumablesOutRecord.setId(null);
						basConsumablesOutRecord.setActualNumber(outNumber);
						basConsumablesOutRecord.setOutTime(basConsumablesOutRecord.getOutTime()==null?new Date():basConsumablesOutRecord.getOutTime());
						basConsumablesOutRecord.setReceiveName(batRecord.getReceiveName());
						basConsumablesOutRecord.setPinYin(PingYinUtil.getFirstSpell(basConsumablesOutRecord.getInstrumentName()));
						basConsumablesOutRecord.setReceiveName(batRecord.getReceiveName());
						basConsumablesOutRecord.setOperator(batRecord.getOperator());
						basConsumablesOutRecord.setOutType(outType);
						basConsumablesOutRecordDao.insertSelective(basConsumablesOutRecord);
						//如果是手术取药，更新手术信息表outMedicine字段
						if("2".equals(outType))
						{
							BasRegOpt basRegOpt = basRegOptDao.searchRegOptById(regOptId);
							basRegOpt.setOutMedicine(1);
							basRegOptDao.updateByPrimaryKeySelective(basRegOpt);
						}
						//更新取药记录表
						basConsumablesStorage.setNumber(number-outNumber);
						basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
					}
				}else
				{
					resp.setResultCode("10000003");
		            resp.setResultMessage(basConsumablesOutRecord.getInstrumentName()+"传输的库存记录不存在！");
		            throw new CustomException(basConsumablesOutRecord.getInstrumentName()+"传输的库存记录不存在！");
				}
			}
		}
	}
	
	
	
	//查询耗材取药记录列表
	public List<BasConsumablesOutRecord>  queryConsumablesOutRecordList(SystemSearchFormBean systemSearchFormBean)
	{
		if (StringUtils.isEmpty(systemSearchFormBean.getSort()))
		{
			systemSearchFormBean.setSort("id");
		}
		if (StringUtils.isEmpty(systemSearchFormBean.getOrderBy()))
		{
			systemSearchFormBean.setOrderBy("DESC");
		}
		if (StringUtils.isEmpty(systemSearchFormBean.getBeid()))
		{
			systemSearchFormBean.setBeid(getBeid());
		}
		
		List<Filter> filters = systemSearchFormBean.getFilters();

		return basConsumablesOutRecordDao.queryConsumablesOutRecordList(filters, systemSearchFormBean);
	}

	// 查询耗材领取记录数量
	public int queryConsumablesOutRecordListTotal(
			SystemSearchFormBean systemSearchFormBean)
	{
		List<Filter> filters = systemSearchFormBean.getFilters();
		if (null == filters)
		{
			filters = new ArrayList<Filter>();
		}
		Filter filter = new Filter();
		filter.setField("beid");
		filter.setValue(getBeid());
		filters.add(filter);
		return basConsumablesOutRecordDao.queryConsumablesOutRecordListTotal(filters);
	}
	
	//增加耗材退药记录
	@Transactional
	public void addConsumablesRetreatRecord(BasConsumablesRetreatRecord basConsumablesRetreatRecord,ResponseValue resp)
	{
		//取得取药记录id
		int outRecordId = basConsumablesRetreatRecord.getOutRecordId();
		int retreatNumber = basConsumablesRetreatRecord.getRetreatNumber();
		//通过取药记录id获得取药记录信息
		BasConsumablesOutRecord basConsumablesOutRecord = basConsumablesOutRecordDao.selectByPrimaryKey(outRecordId);
		if(null != basConsumablesOutRecord)
		{
			int actualNumber = basConsumablesOutRecord.getActualNumber();
			if(retreatNumber>actualNumber)
			{
				resp.setResultCode("10000002");
	            resp.setResultMessage("退货的数量不能大于领取耗材的实际数量");
			}else
			{
				//退药记录存入数据库
				basConsumablesRetreatRecord.setRetreatTime(new Date());
				basConsumablesRetreatRecordDao.insertSelective(basConsumablesRetreatRecord);
				//将取药记录进行更新。更新退药数量和实际数量
				int outReteatNum = basConsumablesOutRecord.getRetreatNumber();
				basConsumablesOutRecord.setRetreatNumber(outReteatNum + retreatNumber);
				basConsumablesOutRecord.setActualNumber(actualNumber - retreatNumber);
				basConsumablesOutRecordDao.updateByPrimaryKeySelective(basConsumablesOutRecord);
				
				int storageId = basConsumablesOutRecord.getStorageId();
				BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(storageId);
				if(null != basConsumablesStorage)
				{
					int number = basConsumablesStorage.getNumber();
					basConsumablesStorage.setNumber(number + retreatNumber);
					basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
				}
			}
		}else
		{
			resp.setResultCode("10000003");
            resp.setResultMessage("传输的取药记录不存在！");
		}
	}
	
	//增加耗材退药记录
	@Transactional
	public void batAddConsumablesRetreatRecord(BatAddConsumablesRetreatFormbean retreatRecord,ResponseValue resp)
	{
		List<BasConsumablesRetreatRecord> resList = retreatRecord.getResList();
		for (BasConsumablesRetreatRecord basConsumablesRetreatRecord : resList) {
			if(StringUtils.isBlank(retreatRecord.getRetreatType())){
				retreatRecord.setRetreatType("1");//如果为空则默认为普通退药
			}
			//取得取药记录id
			int id = basConsumablesRetreatRecord.getId();
			//int outRecordId = basConsumablesRetreatRecord.getOutRecordId();
			int retreatNumber = basConsumablesRetreatRecord.getRetreatNumber();
			//通过取药记录id获得取药记录信息
			BasConsumablesOutRecord basConsumablesOutRecord = basConsumablesOutRecordDao.selectByPrimaryKey(id);
			if(null != basConsumablesOutRecord)
			{
				int actualNumber = basConsumablesOutRecord.getActualNumber();
				if(retreatNumber>actualNumber)
				{
					resp.setResultCode("10000002");
		            resp.setResultMessage(basConsumablesRetreatRecord.getInstrumentName()+"退货的数量不能大于领取耗材的实际数量");
		            throw new CustomException(basConsumablesRetreatRecord.getInstrumentName()+"退货的数量不能大于领取耗材的实际数量"); 
				}else
				{
					//退药记录存入数据库
					basConsumablesRetreatRecord.setRetreatTime(new Date());
					basConsumablesRetreatRecord.setRetreatName(retreatRecord.getRetreatName());
					basConsumablesRetreatRecord.setRetreatreason(retreatRecord.getRetreatreason());
					basConsumablesRetreatRecord.setOperator(retreatRecord.getOperator());
					basConsumablesRetreatRecord.setOutRecordId(id);
					basConsumablesRetreatRecord.setId(null);
					basConsumablesRetreatRecord.setRetreatType(retreatRecord.getRetreatType());
					basConsumablesRetreatRecordDao.insertSelective(basConsumablesRetreatRecord);
					//将取药记录进行更新。更新退药数量和实际数量
					int outReteatNum = basConsumablesOutRecord.getRetreatNumber();
					basConsumablesOutRecord.setRetreatNumber(outReteatNum + retreatNumber);
					basConsumablesOutRecord.setActualNumber(actualNumber - retreatNumber);
					basConsumablesOutRecordDao.updateByPrimaryKeySelective(basConsumablesOutRecord);
					
					int storageId = basConsumablesOutRecord.getStorageId();
					BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(storageId);
					if(null != basConsumablesStorage)
					{
						int number = basConsumablesStorage.getNumber();
						basConsumablesStorage.setNumber(number + retreatNumber);
						basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
					}
				}
			}else
			{
				resp.setResultCode("10000003");
	            resp.setResultMessage(basConsumablesRetreatRecord.getInstrumentName()+"传输的领用记录不存在！");
	            throw new CustomException(basConsumablesRetreatRecord.getInstrumentName()+"传输的领用录不存在！"); 
			}
		}
	}
	
	
	//增加报损记录
	public void addConsumablesLoseRecord(BasConsumablesLoseRecord basConsumablesLoseRecord,ResponseValue resp)
	{
		//报损类型
		String loseType = basConsumablesLoseRecord.getLoseType();
		int loseNumber = basConsumablesLoseRecord.getLoseNumber();
		//类型为普通报损和手术报损
		if("1".equals(loseType) || "2".equals(loseType))
		{
			// 取得取药记录id
			int outRecordId = basConsumablesLoseRecord.getOutRecordId();
			// 通过取药记录id获得取药记录信息
			BasConsumablesOutRecord basConsumablesOutRecord = basConsumablesOutRecordDao.selectByPrimaryKey(outRecordId);
			if (null != basConsumablesOutRecord)
			{
				int actualNumber = basConsumablesOutRecord.getActualNumber();
				if (loseNumber > actualNumber)
				{
					resp.setResultCode("10000002");
					resp.setResultMessage("报损的数量不能大于领用的实际数量");
				} else
				{
					// 报损记录存入数据库
					basConsumablesLoseRecord.setLoseTime(new Date());
					basConsumablesLoseRecordDao.insertSelective(basConsumablesLoseRecord);
					// 将取药记录进行更新。更新退药数量和实际数量
					int outLoseNum = basConsumablesOutRecord.getLoseNumber();
					basConsumablesOutRecord.setLoseNumber(outLoseNum + loseNumber);
					basConsumablesOutRecord.setActualNumber(actualNumber - loseNumber);
					basConsumablesOutRecordDao.updateByPrimaryKeySelective(basConsumablesOutRecord);
				}
			} else
			{
				resp.setResultCode("10000003");
				resp.setResultMessage("传输的领用记录不存在！");
			}
		}else if("3".equals(loseType))//清单报损
		{
			int storageId = basConsumablesLoseRecord.getStorageId();
			BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(storageId);
			if(null != basConsumablesStorage)
			{
				//库存剩余数量
				int storageNum = basConsumablesStorage.getNumber();
				if(loseNumber > storageNum)
				{
					resp.setResultCode("10000006");
					resp.setResultMessage("清单报损，报损数量不能大于存在剩余数量！");
				}else
				{
					// 报损记录存入数据库
					basConsumablesLoseRecord.setLoseTime(new Date());
					basConsumablesLoseRecordDao.insertSelective(basConsumablesLoseRecord);
					//更新库存记录
					basConsumablesStorage.setNumber(storageNum - loseNumber );
					basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
				}
				
			}else
			{
				resp.setResultCode("10000005");
				resp.setResultMessage("清单报损，库存ID传输错误，没有这个库存记录！");
			}
		}else
		{
			resp.setResultCode("10000004");
			resp.setResultMessage("报损类型错误！");
		}
		
	}
	
	//查询手术信息
	public List<BasMedicineRegOptFormBean> selectRegOptInfoForOutRecordList(SystemSearchFormBean systemSearchFormBean)
	{
		if (StringUtils.isEmpty(systemSearchFormBean.getSort()))
		{
			systemSearchFormBean.setSort("regOptId");
		}
		if (StringUtils.isEmpty(systemSearchFormBean.getOrderBy()))
		{
			systemSearchFormBean.setOrderBy("ASC");
		}

		List<Filter> filters = systemSearchFormBean.getFilters();
		if (null == filters)
		{
			filters = new ArrayList<Filter>();
		}
		Filter filter = new Filter();
		filter.setField("beid");
		filter.setValue(getBeid());
		filters.add(filter);
		
		return basConsumablesOutRecordDao.selectRegOptInfoForOutRecordList(filters, systemSearchFormBean);
	}
	//查询查询手术信息数量
	public int selectRegOptInfoForOutRecordListTotal(SystemSearchFormBean systemSearchFormBean)
	{
		List<Filter> filters = systemSearchFormBean.getFilters();
		return basConsumablesOutRecordDao.selectRegOptInfoForOutRecordListTotal(filters);
	}
	
	//删除退货记录
	public void delConsumablesRetreatRecord(Integer id,ResponseValue resp)
	{
		BasConsumablesRetreatRecord basConsumablesRetreatRecord = basConsumablesRetreatRecordDao.selectByPrimaryKey(id);
		if(null != basConsumablesRetreatRecord)
		{
			int retreatNumber = basConsumablesRetreatRecord.getRetreatNumber();
			basConsumablesRetreatRecordDao.deleteByPrimaryKey(id);
			
			int outRecordId = basConsumablesRetreatRecord.getOutRecordId();
			BasConsumablesOutRecord basConsumablesOutRecord = basConsumablesOutRecordDao.selectByPrimaryKey(outRecordId);
			if(null != basConsumablesOutRecord)
			{
				int oldRetreatNum = basConsumablesOutRecord.getRetreatNumber();
				int oldActualNum = basConsumablesOutRecord.getActualNumber();
				basConsumablesOutRecord.setRetreatNumber(oldRetreatNum - retreatNumber);
				basConsumablesOutRecord.setActualNumber(oldActualNum + retreatNumber);
				basConsumablesOutRecordDao.updateByPrimaryKeySelective(basConsumablesOutRecord);
				
				int storageId = basConsumablesOutRecord.getStorageId();
				BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(storageId);
				if(null != basConsumablesStorage)
				{
					int number = basConsumablesStorage.getNumber();
					basConsumablesStorage.setNumber(number - retreatNumber);
					basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
				}
			}
		}else
		{
			resp.setResultCode("10000002");
			resp.setResultMessage("退货记录不存在！");
		}
	}
	
	//删除报损记录
	public void delConsumablesLoseRecord(Integer id,ResponseValue resp)
	{
		BasConsumablesLoseRecord basConsumablesLoseRecord = basConsumablesLoseRecordDao.selectByPrimaryKey(id);
		if(null != basConsumablesLoseRecord)
		{
			int loseNumber = basConsumablesLoseRecord.getLoseNumber();
			basConsumablesLoseRecordDao.deleteByPrimaryKey(id);
				
			int outRecordId = basConsumablesLoseRecord.getOutRecordId();
			BasConsumablesOutRecord basConsumablesOutRecord = basConsumablesOutRecordDao.selectByPrimaryKey(outRecordId);
			if(null != basConsumablesOutRecord)
			{
				int oldloseNum = basConsumablesOutRecord.getLoseNumber();
				int oldActualNum =basConsumablesOutRecord.getActualNumber();
				basConsumablesOutRecord.setLoseNumber(oldloseNum - loseNumber);
				basConsumablesOutRecord.setActualNumber(oldActualNum + loseNumber);
				basConsumablesOutRecordDao.updateByPrimaryKeySelective(basConsumablesOutRecord);
			}
		}
		else
		{
			resp.setResultCode("10000002");
			resp.setResultMessage("报损记录不存在！");
		}
	}
	
	//逻辑删除领取耗材记录
	public void delConsumablesOutRecord(Integer id,ResponseValue resp)
	{
		BasConsumablesOutRecord basConsumablesOutRecord = basConsumablesOutRecordDao.selectByPrimaryKey(id);
		if(null != basConsumablesOutRecord)
		{
			
			int actualNumber = basConsumablesOutRecord.getActualNumber();
			int loseNumber = basConsumablesOutRecord.getLoseNumber();
			basConsumablesOutRecord.setEnable(0);
			basConsumablesOutRecordDao.updateByPrimaryKeySelective(basConsumablesOutRecord);
			
			//存储记录恢复
			Integer storageId = basConsumablesOutRecord.getStorageId();
			BasConsumablesStorage basConsumablesStorage = basConsumablesStorageDao.selectByPrimaryKey(storageId);
			if(null != basConsumablesStorage)
			{
				int number = basConsumablesStorage.getNumber();
				basConsumablesStorage.setNumber(number + actualNumber + loseNumber);
				basConsumablesStorageDao.updateByPrimaryKeySelective(basConsumablesStorage);
			}
			
			//退货记录逻辑删除
			basConsumablesRetreatRecordDao.updateEnableByOutRecordId(id);
			//报损记录逻辑删除
			basConsumablesLoseRecordDao.updateEnableByOutRecordId(id);
			
		}else
		{
			resp.setResultCode("10000002");
			resp.setResultMessage("领取耗材记录不存在！");
		}
	}
	
}
