package com.digihealth.anesthesia.doc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasSelfPayInstrument;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.doc.formbean.SelfPayInstrumentAccedeFormbean;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentItem;
@Service
public class DocSelfPayInstrumentAccedeService extends BaseService
{
    public SelfPayInstrumentAccedeFormbean selectByRegOptId(String regOptId)
    {
        SelfPayInstrumentAccedeFormbean formbean = new SelfPayInstrumentAccedeFormbean();
        DocSelfPayInstrumentAccede docSelfPayInstrumentAccede = docSelfPayInstrumentAccedeDao.selectByRegOptId(regOptId);
        formbean.setDocSelfPayInstrumentAccede(docSelfPayInstrumentAccede);
        
        List<DocSelfPayInstrumentItem> docSelfPayInstrumentItems = new ArrayList<DocSelfPayInstrumentItem>();
        
        if (null != docSelfPayInstrumentAccede)
        {
            docSelfPayInstrumentItems = docSelfPayInstrumentItemDao.selectByAccedeId(docSelfPayInstrumentAccede.getId());
        
            if (null == docSelfPayInstrumentItems || docSelfPayInstrumentItems.size() < 1)
            {
                List<BasSelfPayInstrument> selfPayInstruments = basSelfPayInstrumentDao.selectAll(getBeid());
                if (null != selfPayInstruments && selfPayInstruments.size() > 0)
                {
                    for (BasSelfPayInstrument basSelfPayInstrument : selfPayInstruments)
                    {
                        DocSelfPayInstrumentItem item = new DocSelfPayInstrumentItem();
                        item.setAccedeId(docSelfPayInstrumentAccede.getId());
                        item.setId(GenerateSequenceUtil.generateSequenceNo());
                        item.setIsSelect(0);
                        item.setName(basSelfPayInstrument.getName());
                        item.setPrice(basSelfPayInstrument.getPrice());
                        item.setType(basSelfPayInstrument.getType());
                        docSelfPayInstrumentItemDao.insert(item);
                        docSelfPayInstrumentItems.add(item);
                    } 
                }
            }
        }
        
        formbean.setDocSelfPayInstrumentItems(docSelfPayInstrumentItems);
        return formbean;
    }
    
    @Transactional
    public void updateSelfPayInstrumentAccede(DocSelfPayInstrumentAccede docSelfPayInstrumentAccede)
    {
        docSelfPayInstrumentAccedeDao.updateByPrimaryKey(docSelfPayInstrumentAccede);
    }
    
    @Transactional
    public void updateSelfPayInstrumentItem(DocSelfPayInstrumentItem docSelfPayInstrumentItem)
    {
        docSelfPayInstrumentItemDao.updateByPrimaryKey(docSelfPayInstrumentItem);
    }
    
    @Transactional
    public SelfPayInstrumentAccedeFormbean init(String regOptId)
    {
        SelfPayInstrumentAccedeFormbean formbean = new SelfPayInstrumentAccedeFormbean();
        List<DocSelfPayInstrumentItem> docSelfPayInstrumentItems = new ArrayList<DocSelfPayInstrumentItem>();
        DocSelfPayInstrumentAccede docSelfPayInstrumentAccede = docSelfPayInstrumentAccedeDao.selectByRegOptId(regOptId);
        if (null != docSelfPayInstrumentAccede)
        {
            docSelfPayInstrumentAccede.setSelect(null);
            docSelfPayInstrumentAccedeDao.updateByPrimaryKey(docSelfPayInstrumentAccede);
            formbean.setDocSelfPayInstrumentAccede(docSelfPayInstrumentAccede);
            docSelfPayInstrumentItemDao.deleteByAccedeId(docSelfPayInstrumentAccede.getId());
            
            List<BasSelfPayInstrument> selfPayInstruments = basSelfPayInstrumentDao.selectAll(getBeid());
            if (null != selfPayInstruments && selfPayInstruments.size() > 0)
            {
                for (BasSelfPayInstrument basSelfPayInstrument : selfPayInstruments)
                {
                    DocSelfPayInstrumentItem item = new DocSelfPayInstrumentItem();
                    item.setAccedeId(docSelfPayInstrumentAccede.getId());
                    item.setId(GenerateSequenceUtil.generateSequenceNo());
                    item.setIsSelect(0);
                    item.setName(basSelfPayInstrument.getName());
                    item.setPrice(basSelfPayInstrument.getPrice());
                    item.setType(basSelfPayInstrument.getType());
                    docSelfPayInstrumentItemDao.insert(item);
                    docSelfPayInstrumentItems.add(item);
                } 
            }
            formbean.setDocSelfPayInstrumentItems(docSelfPayInstrumentItems);
        }
        return formbean;
    }
}
