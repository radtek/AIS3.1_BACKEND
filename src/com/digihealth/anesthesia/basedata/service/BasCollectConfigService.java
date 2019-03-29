package com.digihealth.anesthesia.basedata.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasCollectConfig;
import com.digihealth.anesthesia.common.service.BaseService;

@Service
public class BasCollectConfigService extends BaseService
{
    public BasCollectConfig selectByRoomId(String roomId)
    {
        return basCollectConfigDao.selectByPrimaryKey(roomId, getBeid());
    }
    
    @Transactional
    public void update(BasCollectConfig basCollectConfig)
    {
        basCollectConfigDao.updateByPrimaryKey(basCollectConfig);
    }
}
