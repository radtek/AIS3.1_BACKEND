
package com.digihealth.anesthesia.interfaceParameters.yxrm.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _String_QNAME = new QName("http://tempuri.org/", "string");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetRegCode }
     * 
     */
    public GetRegCode createGetRegCode() {
        return new GetRegCode();
    }

    /**
     * Create an instance of {@link GetRegCodeResponse }
     * 
     */
    public GetRegCodeResponse createGetRegCodeResponse() {
        return new GetRegCodeResponse();
    }

    /**
     * Create an instance of {@link GetKeyCode }
     * 
     */
    public GetKeyCode createGetKeyCode() {
        return new GetKeyCode();
    }

    /**
     * Create an instance of {@link GetKeyCodeResponse }
     * 
     */
    public GetKeyCodeResponse createGetKeyCodeResponse() {
        return new GetKeyCodeResponse();
    }

    /**
     * Create an instance of {@link A01 }
     * 
     */
    public A01 createA01() {
        return new A01();
    }

    /**
     * Create an instance of {@link A01Response }
     * 
     */
    public A01Response createA01Response() {
        return new A01Response();
    }

    /**
     * Create an instance of {@link CreateOperationNotice }
     * 
     */
    public CreateOperationNotice createCreateOperationNotice() {
        return new CreateOperationNotice();
    }

    /**
     * Create an instance of {@link CreateOperationNoticeResponse }
     * 
     */
    public CreateOperationNoticeResponse createCreateOperationNoticeResponse() {
        return new CreateOperationNoticeResponse();
    }

    /**
     * Create an instance of {@link UpdateOperationNotice }
     * 
     */
    public UpdateOperationNotice createUpdateOperationNotice() {
        return new UpdateOperationNotice();
    }

    /**
     * Create an instance of {@link UpdateOperationNoticeResponse }
     * 
     */
    public UpdateOperationNoticeResponse createUpdateOperationNoticeResponse() {
        return new UpdateOperationNoticeResponse();
    }

    /**
     * Create an instance of {@link UpdateScheduling }
     * 
     */
    public UpdateScheduling createUpdateScheduling() {
        return new UpdateScheduling();
    }

    /**
     * Create an instance of {@link UpdateSchedulingResponse }
     * 
     */
    public UpdateSchedulingResponse createUpdateSchedulingResponse() {
        return new UpdateSchedulingResponse();
    }

    /**
     * Create an instance of {@link UpdateState }
     * 
     */
    public UpdateState createUpdateState() {
        return new UpdateState();
    }

    /**
     * Create an instance of {@link UpdateStateResponse }
     * 
     */
    public UpdateStateResponse createUpdateStateResponse() {
        return new UpdateStateResponse();
    }

    /**
     * Create an instance of {@link UpdateOAState }
     * 
     */
    public UpdateOAState createUpdateOAState() {
        return new UpdateOAState();
    }

    /**
     * Create an instance of {@link UpdateOAStateResponse }
     * 
     */
    public UpdateOAStateResponse createUpdateOAStateResponse() {
        return new UpdateOAStateResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

}
