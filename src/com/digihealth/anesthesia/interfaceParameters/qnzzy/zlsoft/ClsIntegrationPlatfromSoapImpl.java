
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.digihealth.anesthesia.interfaceParameters.qnzzy.zlsoft;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.4
 * 2018-03-30T15:46:46.223+08:00
 * Generated source version: 3.1.4
 * 
 */

@javax.jws.WebService(
                      serviceName = "clsIntegrationPlatfrom",
                      portName = "clsIntegrationPlatfromSoap",
                      targetNamespace = "ZLSoft",
                      wsdlLocation = "http://192.128.0.120:8087/IntegrationPlatfrom.asmx?wsdl",
                      endpointInterface = "zlsoft.ClsIntegrationPlatfromSoap")
                      
public class ClsIntegrationPlatfromSoapImpl implements ClsIntegrationPlatfromSoap {

    private static final Logger LOG = Logger.getLogger(ClsIntegrationPlatfromSoapImpl.class.getName());

    /* (non-Javadoc)
     * @see zlsoft.ClsIntegrationPlatfromSoap#integrationPlatfrom(java.lang.String  strXml )*
     */
    public java.lang.String integrationPlatfrom(java.lang.String strXml) { 
        LOG.info("Executing operation integrationPlatfrom");
        System.out.println(strXml);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
