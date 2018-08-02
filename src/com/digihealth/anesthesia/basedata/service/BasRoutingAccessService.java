package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import net.sf.ehcache.Cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasRoutingAccessControl;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.listener.ConstantHolder;
import com.digihealth.anesthesia.common.listener.PathAccessThread;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.CacheUtils;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;

@Service
public class BasRoutingAccessService extends BaseService
{

	//查询局点下接口路由信息
	public List<BasRoutingAccessControl> getBasRoutingAccessByBeid(String beid)
	{
		if(StringUtils.isBlank(beid))
		{
			beid = getBeid();
		}
		
		return basRoutingAccessControlDao.getBasRoutingAccessByBeid(beid);
	}
	
	//保存接口路由信息  传了id为修改，没有传id为新增
	@Transactional
	public void saveBasRoutingAccess(BasRoutingAccessControl basRoutingAccessControl,ResponseValue resp)
	{
		if(null != basRoutingAccessControl)
		{
			//aliasName 是唯一索引
			String aliasName = basRoutingAccessControl.getAliasName();
			List<BasRoutingAccessControl> aliasNameList = basRoutingAccessControlDao.getBasRoutingAccessByAliasName(aliasName);
			
			//`beid`,`uri`是唯一索引 
			String uri=basRoutingAccessControl.getUri(); 
			String beid = getBeid();
			List<BasRoutingAccessControl> uriList = basRoutingAccessControlDao.getBasRoutingAccessByUrl(uri, beid);
			
			//判断是新增还是修改
			String id = basRoutingAccessControl.getId();
			if(StringUtils.isBlank(id))
			{
				if(null != aliasNameList && aliasNameList.size()>0)
				{
					resp.setResultCode("10000001");
		            resp.setResultMessage("aliasName 是唯一索引，不可重复");
		            return ;
				}
				
				if(null != uriList && uriList.size()>0)
				{
					resp.setResultCode("10000001");
					resp.setResultMessage("uri 和  beid 是唯一索引，不可重复");
		            return ;
				}
				
				id = GenerateSequenceUtil.generateSequenceNo();
				basRoutingAccessControl.setId(id);
				basRoutingAccessControlDao.insertSelective(basRoutingAccessControl);
			}else
			{
				if(null != aliasNameList && aliasNameList.size()>0)
				{
					for(BasRoutingAccessControl routing : aliasNameList)
					{
						String routingId = routing.getId();
						if(!id.equals(routingId))
						{
							resp.setResultCode("10000001");
				            resp.setResultMessage("aliasName 是唯一索引，不可重复");
				            return ;
						}
					}
				}
				
				if(null != uriList && uriList.size()>0)
				{
					for(BasRoutingAccessControl routing : uriList)
					{
						String routingId = routing.getId();
						if(!id.equals(routingId))
						{
							resp.setResultCode("10000001");
							resp.setResultMessage("uri 和  beid 是唯一索引，不可重复");
				            return ;
						}
					}
				}
				
				basRoutingAccessControlDao.updateByPrimaryKeySelective(basRoutingAccessControl);
			}
			
			
			List<BasRoutingAccessControl> racList = PathAccessThread.getAllRacList();
	    	//先清空缓存中的路径数据
			Cache cache = CacheUtils.getCacheByCacheName(ConstantHolder.ROUTING_ACCESS_CACHE);
			if(null != cache){
				List<String> keys = cache.getKeys();
				if(null != keys && keys.size()>0){
					String key = keys.get(0);
					if(key.contains(ConstantHolder.ROUTING_ACCESS_PREFIX)){
						cache.remove(key);//清除key相应的对象
					}
				}
			}
			
	    	if(null != racList && racList.size()>0){
	    		for (int i = 0; i < racList.size(); i++) {
	    			BasRoutingAccessControl sysRoutingAccessControl = racList.get(i);
	    			String sysUri = sysRoutingAccessControl.getUri();
	    			String sysBeid = sysRoutingAccessControl.getBeid();
	    			String key = ConstantHolder.ROUTING_ACCESS_PREFIX + sysBeid + ConstantHolder.ROUTING_ACCESS_LINK + sysUri;
	    			logger.info("put to routingAccessCache:"+key+",sysRoutingAccessControl="+sysRoutingAccessControl.toString());
					CacheUtils.put(ConstantHolder.ROUTING_ACCESS_CACHE , key, sysRoutingAccessControl);
				}
	    	}
		}
	}
}
