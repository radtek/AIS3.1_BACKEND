
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
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.4
 * 2018-03-30T15:46:46.213+08:00
 * Generated source version: 3.1.4
 * 
 */

@javax.jws.WebService(
                      serviceName = "clsIntegrationPlatfrom",
                      portName = "clsIntegrationPlatfromHttpPost",
                      targetNamespace = "ZLSoft",
                      wsdlLocation = "http://192.128.0.120:8087/IntegrationPlatfrom.asmx?wsdl",
                      endpointInterface = "zlsoft.ClsIntegrationPlatfromHttpPost")
                      
public class ClsIntegrationPlatfromHttpPostImpl implements ClsIntegrationPlatfromHttpPost {

    private static final Logger LOG = Logger.getLogger(ClsIntegrationPlatfromHttpPostImpl.class.getName());

    /* (non-Javadoc)
     * @see zlsoft.ClsIntegrationPlatfromHttpPost#integrationPlatfrom(java.lang.String  strXml )*
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
