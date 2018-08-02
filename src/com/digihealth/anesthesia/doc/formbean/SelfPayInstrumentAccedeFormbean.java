package com.digihealth.anesthesia.doc.formbean;

import java.util.List;

import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentItem;

public class SelfPayInstrumentAccedeFormbean
{
    private DocSelfPayInstrumentAccede docSelfPayInstrumentAccede;
    
    private List<DocSelfPayInstrumentItem> docSelfPayInstrumentItems;

    public DocSelfPayInstrumentAccede getDocSelfPayInstrumentAccede()
    {
        return docSelfPayInstrumentAccede;
    }

    public void setDocSelfPayInstrumentAccede(DocSelfPayInstrumentAccede docSelfPayInstrumentAccede)
    {
        this.docSelfPayInstrumentAccede = docSelfPayInstrumentAccede;
    }

    public List<DocSelfPayInstrumentItem> getDocSelfPayInstrumentItems()
    {
        return docSelfPayInstrumentItems;
    }

    public void setDocSelfPayInstrumentItems(List<DocSelfPayInstrumentItem> docSelfPayInstrumentItems)
    {
        this.docSelfPayInstrumentItems = docSelfPayInstrumentItems;
    }
}
