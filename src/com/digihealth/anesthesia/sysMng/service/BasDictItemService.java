package com.digihealth.anesthesia.sysMng.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.CacheUtils;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;



/**
 * 
     * Title: BasDictItemService.java    
     * Description: 数据字典
     * @author liukui       
     * @created 2016年4月13日 上午9:08:26
 */
@Service
public class BasDictItemService extends BaseService{
	public static final String SYS_CODE_CACHE = "sysCodeCache";
	public static final String SYS_CODE_CACHE_ID = "id_";
	
	//通过groupId查询dictItem列表
	public List<BasDictItem> getBasDictItemsByGroupId(SystemSearchFormBean systemSearchFormBean){
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("`order`");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		List<Filter> filters = systemSearchFormBean.getFilters();
		
		String userName = getUserName();
		if(!"administrator".equals(userName))
		{
			Filter filter = new Filter();
			filter.setField("beid");
			filter.setValue(systemSearchFormBean.getBeid());
			filters.add(filter);
		}
			
		return basDictItemDao.getDictItemsByGroupId(filters, systemSearchFormBean);
	}
	
	// 通过groupId查询dictItem列表
	public Integer getBasDictItemsNumByGroupId(SystemSearchFormBean systemSearchFormBean)
	{
		
		List<Filter> filters = systemSearchFormBean.getFilters();
		return basDictItemDao.getDictItemsNumByGroupId(filters);
	}
	
		
	//通过groupId 和 codeValue 查询具体字典值
	public List<BasDictItem> getListByGroupId(String groupId,String codeValue,String beid)
	{
		if(null == beid || "".equals(beid))
		{
			beid = getBeid();
		}
		return basDictItemDao.getListByGroupId(groupId,codeValue,beid);
	}
	
	//通过groupId 和 codeName 查询具体字典值
	public BasDictItem getListByGroupIdandCodeName(String groupId,String codeName,String beid)
	{
		if(null == beid || "".equals(beid))
		{
			beid = getBeid();
		}
		return basDictItemDao.getListByGroupIdandCodeName(groupId, codeName,beid);
	}
	
	//添加字典值
	@Transactional
	public void addBasDictItem(BasDictItem dictItem)
	{
		SystemSearchFormBean systemSearchFormBean =new  SystemSearchFormBean();
		systemSearchFormBean.setSort("`order`");
		systemSearchFormBean.setOrderBy("ASC");
		
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = new Filter();
		filter.setField("beid");
		filter.setValue(getBeid());
		filters.add(filter);
		Filter filter2 = new Filter();
		filter2.setField("groupId");
		filter2.setValue(dictItem.getGroupId());
		filters.add(filter2);
	
		//查询这个组下有几个值，如果只有一个就update,否则，一直往后加
		List<BasDictItem> dictList = basDictItemDao.getDictItemsByGroupId(filters,systemSearchFormBean);
		Integer num = 0;
		if(null != dictList && dictList.size()>0 )
		{
			num = dictList.size();
		}
		
		dictItem.setOrder(num+1);
		basDictItemDao.insertSelective(dictItem);
		
		List<SysCodeFormbean> list = basDictItemDao.searchSysCodeByGroupId(StringUtils.zhuanData(dictItem.getGroupId()),getBeid());
		CacheUtils.put(SYS_CODE_CACHE, SYS_CODE_CACHE_ID +dictItem.getGroupId()+getBeid(), list);
		
	}
	
	//修改字典值
	@Transactional
	public void upBasDictItem(BasDictItem dictItem)
	{
		int oldOrder = 0;
		int order = 1;
		if(null != dictItem.getOrder())
		{
			order = dictItem.getOrder().intValue();
		}
		BasDictItem oldBasDictItem = basDictItemDao.selectByPrimaryKey(dictItem.getId());
		if(null != oldBasDictItem)
		{
			oldOrder =  oldBasDictItem.getOrder();
		}
		
		if(oldOrder != order)
		{
			SystemSearchFormBean systemSearchFormBean =new  SystemSearchFormBean();
			systemSearchFormBean.setSort("`order`");
			systemSearchFormBean.setOrderBy("ASC");
			
			List<Filter> filters = new ArrayList<Filter>();
			Filter filter = new Filter();
			filter.setField("beid");
			filter.setValue(getBeid());
			filters.add(filter);
			Filter filter2 = new Filter();
			filter2.setField("groupId");
			filter2.setValue(dictItem.getGroupId());
			filters.add(filter2);
			
			List<BasDictItem> dictList = basDictItemDao.getDictItemsByGroupId(filters, systemSearchFormBean);
			if(null != dictList && dictList.size()>0)
			{
				for(int i=0;i<dictList.size();i++)
				{
					//老值往前移动  找到从什么地方开始就都往后摞1
					if(oldOrder > order && i+1 >= order && i+1 < oldOrder)
					{
						BasDictItem di = dictList.get(i);
						di.setOrder(i+2);
						basDictItemDao.updateByPrimaryKeySelective(di);
					}
					
					//老值向后移动  找到从什么地方开始就都往前摞1
					if(oldOrder < order && i+1 > oldOrder && i+1 <= order)
					{
						BasDictItem di = dictList.get(i);
						di.setOrder(i);
						basDictItemDao.updateByPrimaryKeySelective(di);
					}
				}
			}
		}
		basDictItemDao.updateByPrimaryKeySelective(dictItem);
		
		List<SysCodeFormbean> list = basDictItemDao.searchSysCodeByGroupId(StringUtils.zhuanData(dictItem.getGroupId()),getBeid());
		CacheUtils.put(SYS_CODE_CACHE, SYS_CODE_CACHE_ID +dictItem.getGroupId()+getBeid(), list);
	}
	
	//删除字典值
	@Transactional
	public void deleteBasDictItem(Integer id)
	{
		BasDictItem oldBasDictItem = basDictItemDao.selectByPrimaryKey(id);
		if(null != oldBasDictItem)
		{
			int order =  oldBasDictItem.getOrder();
			String groupId = oldBasDictItem.getGroupId();
			String beid = oldBasDictItem.getBeid();
			
			//找到组，把顺序从新排序
			SystemSearchFormBean systemSearchFormBean =new  SystemSearchFormBean();
			systemSearchFormBean.setSort("`order`");
			systemSearchFormBean.setOrderBy("ASC");
			
			List<Filter> filters = new ArrayList<Filter>();
			Filter filter = new Filter();
			filter.setField("beid");
			filter.setValue(beid);
			filters.add(filter);
			Filter filter2 = new Filter();
			filter2.setField("groupId");
			filter2.setValue(groupId);
			filters.add(filter2);
			
			List<BasDictItem> dictList = basDictItemDao.getDictItemsByGroupId(filters, systemSearchFormBean);
			if(null != dictList && dictList.size()>0)
			{
				for (int i = 0; i < dictList.size(); i++)
				{
					// 找到从什么地方开始就都往前摞1
					if (i + 1 > order && order != dictList.size())
					{
						BasDictItem di = dictList.get(i);
						di.setOrder(i);
						basDictItemDao.updateByPrimaryKeySelective(di);
					}
				}
			}
			
			basDictItemDao.deleteByPrimaryKey(id);
			
			List<SysCodeFormbean> list = basDictItemDao.searchSysCodeByGroupId(StringUtils.zhuanData(oldBasDictItem.getGroupId()),getBeid());
			CacheUtils.put(SYS_CODE_CACHE, SYS_CODE_CACHE_ID +oldBasDictItem.getGroupId()+getBeid(), list);
		}
	}
}
