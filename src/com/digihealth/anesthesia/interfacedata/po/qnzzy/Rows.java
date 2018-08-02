package com.digihealth.anesthesia.interfacedata.po.qnzzy;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Rows
{
    private List<Row> rowList;

    @XmlElement(name = "Row")
    public List<Row> getRowList()
    {
        return rowList;
    }

    public void setRowList(List<Row> rowList)
    {
        this.rowList = rowList;
    }
}
