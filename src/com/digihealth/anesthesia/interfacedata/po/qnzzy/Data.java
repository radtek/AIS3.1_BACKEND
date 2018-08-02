package com.digihealth.anesthesia.interfacedata.po.qnzzy;

import javax.xml.bind.annotation.XmlElement;

public class Data
{
    private Rows rows;

    @XmlElement(name = "Rows")
    public Rows getRows()
    {
        return rows;
    }

    public void setRows(Rows rows)
    {
        this.rows = rows;
    }
}
