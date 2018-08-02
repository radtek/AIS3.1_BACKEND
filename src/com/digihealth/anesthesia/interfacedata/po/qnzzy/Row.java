package com.digihealth.anesthesia.interfacedata.po.qnzzy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Row")
public class Row
{
    private String SerialNo;
    
    private String DeptCode;
    
    private String DeptName;
    
    private String InputCode;
    
    private String OperationCode;
    
    private String OperationName;
    
    private String StdIndicator;
    
    private String ApprovedIndicator;
    
    private String CreateDate;
    
    private String DiseaseId;
    
    private String DiseaseCode;
    
    private String DiseaseName;
    
    private String DiseaseType;
    
    private String TestNo;
    
    private String PriorityIndicator;
    
    private String PatientId;
    
    private String VisitId;
    
    private String PatientName;
    
    private String WorkingId;
    
    private String TestCause;
    
    private String RelevantClinicDiag;
    
    private String Specimen;
    
    private String SpcmReceivedDateTime;
    
    private String OrderingDept;
    
    private String OrderingProvider;
    
    private String PerformedBy;
    
    private String ResultStatus;
    
    private String ResultsRptDateTime;
    
    private String Transcriptionist;
    
    private String VerifiedBy;
    
    private String ReportItemCode;
    
    private String ReportItemName;
    
    private String AbnormalIndicator;
    
    private String Result;
    
    private String Units;
    
    private String ResultDateTime;
    
    private String ReferenceResult;
    
    private String ScheduleId;
    
    private String DeptID;
    
    private String ScheduledDateTime;
    
    private String OperRoom;
    
    private String OperRoomNo;
    
    private String Sequence;
    
    private String DiagBeforeOperation;
    
    private String AnesMethod;
    
    private String Surgeon;
    
    private String FirstOperAssistant;
    
    private String SecondOperAssistant;
    
    private String AnesDoctor;
    
    private String FirstAnesAssistant;
    
    private String SecondAnesAssistant;
    
    private String FirstOperNurse;
    
    private String SecondOperNurse;
    
    private String FirstSupplyNurse;
    
    private String SecondSupplyNurse;
    
    private String WardID;
    
    private String UserId;
    
    private String UserCode;
    
    private String UserName;
    
    private String UserDept;
    
    private String UserDeptName;
    
    private String UserJob;
    
    private String DeptStayed;
    
    private String BedNo;
    
    private String OperatingRoom;
    
    private String OperatingRoomNo;
    
    private String OperationNo;
    
    private String PatientCondition;
    
    private String IncisionLevel;
    
    private String OperationScale;
    
    private String EmergencyIndicator;
    
    private String IsolationIndicator;
    
    private String OperatingDept;
    
    private String FirstAssistant;
    
    private String SecondAssistant;
    
    private String ThirdAssistant;
    
    private String FourthAssistant;
    
    private String AnesthesiaMethod;
    
    private String AnesthesiaDoctor;
    
    private String SecondAnesthesiaDoctor;
    
    private String ThirdAnesthesiaDoctor;
    
    private String AnesthesiaAssistant;
    
    private String SecondAnesthesiaAssistant;
    
    private String ThirdAnesthesiaAssistant;
    
    private String FourthAnesthesiaAssistant;
    
    private String BloodTranDoctor;
    
    private String FirstOperationNurse;
    
    private String SecondOperationNurse;
    
    private String ThirdOperationNurse;
    
    private String ThirdSupplyNurse;
    
    private String NotesOnOperation;
    
    private String ReqDateTime;
    
    private String EnteredBy;
    
    private String Status;
    
    private String OperationPosition;
    
    private String SpecialEquipment;
    
    private String SpecialInfect;
    
    private String Operation;
    
    private String InPatientNo;
    
    private String NamePhonetic;
    
    private String Sex;
    
    private String DateOfBirth;
    
    private String BirthPlace;
    
    private String Citizenship;
    
    private String Nation;
    
    private String IdCardNo;
    
    private String Identity;
    
    private String ChargeType;
    
    private String UnitInContract;
    
    private String MailingAddress;
    
    private String ZipCode;
    
    private String PhoneNumberHome;
    
    private String PhoneNumberBusiness;
    
    private String NextOfKin;
    
    private String Relationship;
    
    private String NextOfKinAddr;
    
    private String NextOfKinZipCode;
    
    private String NextOfKinPhone;
    
    private String LastVisitDate;
    
    private String VipIndicator;
    
    private String Operator;
    
    private String SMXTID;
    
    private String SSSJ;
    
    private String SSKSSJ;
    
    private String SSJSSJ;
    
    private String LXSS;
    
    private String SSCZID;
    
    private String ZLXMID;
    
    private String YXSS;
    
    private String ZDYS;
    
    private String DYZS;
    
    private String DEZS;
    
    private String MZLX;
    
    private String MZYS;
    
    private String QK;
    
    private String YH;
    
    private String JLR;
    
    private String SSQK;
    
    private String ASAFJ;
    
    private String NNISFJ;
    
    private String SSJB;
    
    private String ZCSS;
    
    private String ID;
    
    private String Name;
    
    private String Type;
    
    private String Unit;
    
    private String Price;
    
    private String Spec;
    
    private String PackageDosageAmount;
    
    private String DosageUnit;
    
    private String Firm;
    
    @XmlElement(name = "Spec")
    public String getSpec()
    {
        return Spec;
    }
    
    public void setSpec(String spec)
    {
        Spec = spec;
    }
    
    @XmlElement(name = "PackageDosageAmount")
    public String getPackageDosageAmount()
    {
        return PackageDosageAmount;
    }
    
    public void setPackageDosageAmount(String packageDosageAmount)
    {
        PackageDosageAmount = packageDosageAmount;
    }
    
    @XmlElement(name = "DosageUnit")
    public String getDosageUnit()
    {
        return DosageUnit;
    }
    
    public void setDosageUnit(String dosageUnit)
    {
        DosageUnit = dosageUnit;
    }
    
    @XmlElement(name = "Firm")
    public String getFirm()
    {
        return Firm;
    }
    
    public void setFirm(String firm)
    {
        Firm = firm;
    }
    
    @XmlElement(name = "Unit")
    public String getUnit()
    {
        return Unit;
    }
    
    public void setUnit(String unit)
    {
        Unit = unit;
    }
    
    @XmlElement(name = "Price")
    public String getPrice()
    {
        return Price;
    }
    
    public void setPrice(String price)
    {
        Price = price;
    }
    
    @XmlElement(name = "ID")
    public String getID()
    {
        return ID;
    }
    
    public void setID(String iD)
    {
        ID = iD;
    }
    
    @XmlElement(name = "Name")
    public String getName()
    {
        return Name;
    }
    
    public void setName(String name)
    {
        Name = name;
    }
    
    @XmlElement(name = "Type")
    public String getType()
    {
        return Type;
    }
    
    public void setType(String type)
    {
        Type = type;
    }
    
    @XmlElement(name = "SMXTID")
    public String getSMXTID()
    {
        return SMXTID;
    }
    
    public void setSMXTID(String sMXTID)
    {
        SMXTID = sMXTID;
    }
    
    @XmlElement(name = "SSSJ")
    public String getSSSJ()
    {
        return SSSJ;
    }
    
    public void setSSSJ(String sSSJ)
    {
        SSSJ = sSSJ;
    }
    
    @XmlElement(name = "SSKSSJ")
    public String getSSKSSJ()
    {
        return SSKSSJ;
    }
    
    public void setSSKSSJ(String sSKSSJ)
    {
        SSKSSJ = sSKSSJ;
    }
    
    @XmlElement(name = "SSJSSJ")
    public String getSSJSSJ()
    {
        return SSJSSJ;
    }
    
    public void setSSJSSJ(String sSJSSJ)
    {
        SSJSSJ = sSJSSJ;
    }
    
    @XmlElement(name = "LXSS")
    public String getLXSS()
    {
        return LXSS;
    }
    
    public void setLXSS(String lXSS)
    {
        LXSS = lXSS;
    }
    
    @XmlElement(name = "SSCZID")
    public String getSSCZID()
    {
        return SSCZID;
    }
    
    public void setSSCZID(String sSCZID)
    {
        SSCZID = sSCZID;
    }
    
    @XmlElement(name = "ZLXMID")
    public String getZLXMID()
    {
        return ZLXMID;
    }
    
    public void setZLXMID(String zLXMID)
    {
        ZLXMID = zLXMID;
    }
    
    @XmlElement(name = "YXSS")
    public String getYXSS()
    {
        return YXSS;
    }
    
    public void setYXSS(String yXSS)
    {
        YXSS = yXSS;
    }
    
    @XmlElement(name = "ZDYS")
    public String getZDYS()
    {
        return ZDYS;
    }
    
    public void setZDYS(String zDYS)
    {
        ZDYS = zDYS;
    }
    
    @XmlElement(name = "DYZS")
    public String getDYZS()
    {
        return DYZS;
    }
    
    public void setDYZS(String dYZS)
    {
        DYZS = dYZS;
    }
    
    @XmlElement(name = "DEZS")
    public String getDEZS()
    {
        return DEZS;
    }
    
    public void setDEZS(String dEZS)
    {
        DEZS = dEZS;
    }
    
    @XmlElement(name = "MZLX")
    public String getMZLX()
    {
        return MZLX;
    }
    
    public void setMZLX(String mZLX)
    {
        MZLX = mZLX;
    }
    
    @XmlElement(name = "MZYS")
    public String getMZYS()
    {
        return MZYS;
    }
    
    public void setMZYS(String mZYS)
    {
        MZYS = mZYS;
    }
    
    @XmlElement(name = "QK")
    public String getQK()
    {
        return QK;
    }
    
    public void setQK(String qK)
    {
        QK = qK;
    }
    
    @XmlElement(name = "YH")
    public String getYH()
    {
        return YH;
    }
    
    public void setYH(String yH)
    {
        YH = yH;
    }
    
    @XmlElement(name = "JLR")
    public String getJLR()
    {
        return JLR;
    }
    
    public void setJLR(String jLR)
    {
        JLR = jLR;
    }
    
    @XmlElement(name = "SSQK")
    public String getSSQK()
    {
        return SSQK;
    }
    
    public void setSSQK(String sSQK)
    {
        SSQK = sSQK;
    }
    
    @XmlElement(name = "ASAFJ")
    public String getASAFJ()
    {
        return ASAFJ;
    }
    
    public void setASAFJ(String aSAFJ)
    {
        ASAFJ = aSAFJ;
    }
    
    @XmlElement(name = "NNISFJ")
    public String getNNISFJ()
    {
        return NNISFJ;
    }
    
    public void setNNISFJ(String nNISFJ)
    {
        NNISFJ = nNISFJ;
    }
    
    @XmlElement(name = "SSJB")
    public String getSSJB()
    {
        return SSJB;
    }
    
    public void setSSJB(String sSJB)
    {
        SSJB = sSJB;
    }
    
    @XmlElement(name = "ZCSS")
    public String getZCSS()
    {
        return ZCSS;
    }
    
    public void setZCSS(String zCSS)
    {
        ZCSS = zCSS;
    }
    
    @XmlElement(name = "InPatientNo")
    public String getInPatientNo()
    {
        return InPatientNo;
    }
    
    public void setInPatientNo(String inPatientNo)
    {
        InPatientNo = inPatientNo;
    }
    
    @XmlElement(name = "NamePhonetic")
    public String getNamePhonetic()
    {
        return NamePhonetic;
    }
    
    public void setNamePhonetic(String namePhonetic)
    {
        NamePhonetic = namePhonetic;
    }
    
    @XmlElement(name = "Sex")
    public String getSex()
    {
        return Sex;
    }
    
    public void setSex(String sex)
    {
        Sex = sex;
    }
    
    @XmlElement(name = "DateOfBirth")
    public String getDateOfBirth()
    {
        return DateOfBirth;
    }
    
    public void setDateOfBirth(String dateOfBirth)
    {
        DateOfBirth = dateOfBirth;
    }
    
    @XmlElement(name = "BirthPlace")
    public String getBirthPlace()
    {
        return BirthPlace;
    }
    
    public void setBirthPlace(String birthPlace)
    {
        BirthPlace = birthPlace;
    }
    
    @XmlElement(name = "Citizenship")
    public String getCitizenship()
    {
        return Citizenship;
    }
    
    public void setCitizenship(String citizenship)
    {
        Citizenship = citizenship;
    }
    
    @XmlElement(name = "Nation")
    public String getNation()
    {
        return Nation;
    }
    
    public void setNation(String nation)
    {
        Nation = nation;
    }
    
    @XmlElement(name = "IdCardNo")
    public String getIdCardNo()
    {
        return IdCardNo;
    }
    
    public void setIdCardNo(String idCardNo)
    {
        IdCardNo = idCardNo;
    }
    
    @XmlElement(name = "Identity")
    public String getIdentity()
    {
        return Identity;
    }
    
    public void setIdentity(String identity)
    {
        Identity = identity;
    }
    
    @XmlElement(name = "ChargeType")
    public String getChargeType()
    {
        return ChargeType;
    }
    
    public void setChargeType(String chargeType)
    {
        ChargeType = chargeType;
    }
    
    @XmlElement(name = "UnitInContract")
    public String getUnitInContract()
    {
        return UnitInContract;
    }
    
    public void setUnitInContract(String unitInContract)
    {
        UnitInContract = unitInContract;
    }
    
    @XmlElement(name = "MailingAddress")
    public String getMailingAddress()
    {
        return MailingAddress;
    }
    
    public void setMailingAddress(String mailingAddress)
    {
        MailingAddress = mailingAddress;
    }
    
    @XmlElement(name = "ZipCode")
    public String getZipCode()
    {
        return ZipCode;
    }
    
    public void setZipCode(String zipCode)
    {
        ZipCode = zipCode;
    }
    
    @XmlElement(name = "PhoneNumberHome")
    public String getPhoneNumberHome()
    {
        return PhoneNumberHome;
    }
    
    public void setPhoneNumberHome(String phoneNumberHome)
    {
        PhoneNumberHome = phoneNumberHome;
    }
    
    @XmlElement(name = "PhoneNumberBusiness")
    public String getPhoneNumberBusiness()
    {
        return PhoneNumberBusiness;
    }
    
    public void setPhoneNumberBusiness(String phoneNumberBusiness)
    {
        PhoneNumberBusiness = phoneNumberBusiness;
    }
    
    @XmlElement(name = "NextOfKin")
    public String getNextOfKin()
    {
        return NextOfKin;
    }
    
    public void setNextOfKin(String nextOfKin)
    {
        NextOfKin = nextOfKin;
    }
    
    @XmlElement(name = "Relationship")
    public String getRelationship()
    {
        return Relationship;
    }
    
    public void setRelationship(String relationship)
    {
        Relationship = relationship;
    }
    
    @XmlElement(name = "NextOfKinAddr")
    public String getNextOfKinAddr()
    {
        return NextOfKinAddr;
    }
    
    public void setNextOfKinAddr(String nextOfKinAddr)
    {
        NextOfKinAddr = nextOfKinAddr;
    }
    
    @XmlElement(name = "NextOfKinZipCode")
    public String getNextOfKinZipCode()
    {
        return NextOfKinZipCode;
    }
    
    public void setNextOfKinZipCode(String nextOfKinZipCode)
    {
        NextOfKinZipCode = nextOfKinZipCode;
    }
    
    @XmlElement(name = "NextOfKinPhone")
    public String getNextOfKinPhone()
    {
        return NextOfKinPhone;
    }
    
    public void setNextOfKinPhone(String nextOfKinPhone)
    {
        NextOfKinPhone = nextOfKinPhone;
    }
    
    @XmlElement(name = "LastVisitDate")
    public String getLastVisitDate()
    {
        return LastVisitDate;
    }
    
    public void setLastVisitDate(String lastVisitDate)
    {
        LastVisitDate = lastVisitDate;
    }
    
    @XmlElement(name = "VipIndicator")
    public String getVipIndicator()
    {
        return VipIndicator;
    }
    
    public void setVipIndicator(String vipIndicator)
    {
        VipIndicator = vipIndicator;
    }
    
    @XmlElement(name = "Operator")
    public String getOperator()
    {
        return Operator;
    }
    
    public void setOperator(String operator)
    {
        Operator = operator;
    }
    
    @XmlElement(name = "DeptStayed")
    public String getDeptStayed()
    {
        return DeptStayed;
    }
    
    public void setDeptStayed(String deptStayed)
    {
        DeptStayed = deptStayed;
    }
    
    @XmlElement(name = "BedNo")
    public String getBedNo()
    {
        return BedNo;
    }
    
    public void setBedNo(String bedNo)
    {
        BedNo = bedNo;
    }
    
    @XmlElement(name = "OperatingRoom")
    public String getOperatingRoom()
    {
        return OperatingRoom;
    }
    
    public void setOperatingRoom(String operatingRoom)
    {
        OperatingRoom = operatingRoom;
    }
    
    @XmlElement(name = "OperatingRoomNo")
    public String getOperatingRoomNo()
    {
        return OperatingRoomNo;
    }
    
    public void setOperatingRoomNo(String operatingRoomNo)
    {
        OperatingRoomNo = operatingRoomNo;
    }
    
    @XmlElement(name = "OperationNo")
    public String getOperationNo()
    {
        return OperationNo;
    }
    
    public void setOperationNo(String operationNo)
    {
        OperationNo = operationNo;
    }
    
    @XmlElement(name = "PatientCondition")
    public String getPatientCondition()
    {
        return PatientCondition;
    }
    
    public void setPatientCondition(String patientCondition)
    {
        PatientCondition = patientCondition;
    }
    
    @XmlElement(name = "IncisionLevel")
    public String getIncisionLevel()
    {
        return IncisionLevel;
    }
    
    public void setIncisionLevel(String incisionLevel)
    {
        IncisionLevel = incisionLevel;
    }
    
    @XmlElement(name = "OperationScale")
    public String getOperationScale()
    {
        return OperationScale;
    }
    
    public void setOperationScale(String operationScale)
    {
        OperationScale = operationScale;
    }
    
    @XmlElement(name = "EmergencyIndicator")
    public String getEmergencyIndicator()
    {
        return EmergencyIndicator;
    }
    
    public void setEmergencyIndicator(String emergencyIndicator)
    {
        EmergencyIndicator = emergencyIndicator;
    }
    
    @XmlElement(name = "IsolationIndicator")
    public String getIsolationIndicator()
    {
        return IsolationIndicator;
    }
    
    public void setIsolationIndicator(String isolationIndicator)
    {
        IsolationIndicator = isolationIndicator;
    }
    
    @XmlElement(name = "OperatingDept")
    public String getOperatingDept()
    {
        return OperatingDept;
    }
    
    public void setOperatingDept(String operatingDept)
    {
        OperatingDept = operatingDept;
    }
    
    @XmlElement(name = "FirstAssistant")
    public String getFirstAssistant()
    {
        return FirstAssistant;
    }
    
    public void setFirstAssistant(String firstAssistant)
    {
        FirstAssistant = firstAssistant;
    }
    
    @XmlElement(name = "SecondAssistant")
    public String getSecondAssistant()
    {
        return SecondAssistant;
    }
    
    public void setSecondAssistant(String secondAssistant)
    {
        SecondAssistant = secondAssistant;
    }
    
    @XmlElement(name = "ThirdAssistant")
    public String getThirdAssistant()
    {
        return ThirdAssistant;
    }
    
    public void setThirdAssistant(String thirdAssistant)
    {
        ThirdAssistant = thirdAssistant;
    }
    
    @XmlElement(name = "FourthAssistant")
    public String getFourthAssistant()
    {
        return FourthAssistant;
    }
    
    public void setFourthAssistant(String fourthAssistant)
    {
        FourthAssistant = fourthAssistant;
    }
    
    @XmlElement(name = "AnesthesiaMethod")
    public String getAnesthesiaMethod()
    {
        return AnesthesiaMethod;
    }
    
    public void setAnesthesiaMethod(String anesthesiaMethod)
    {
        AnesthesiaMethod = anesthesiaMethod;
    }
    
    @XmlElement(name = "AnesthesiaDoctor")
    public String getAnesthesiaDoctor()
    {
        return AnesthesiaDoctor;
    }
    
    public void setAnesthesiaDoctor(String anesthesiaDoctor)
    {
        AnesthesiaDoctor = anesthesiaDoctor;
    }
    
    @XmlElement(name = "SecondAnesthesiaDoctor")
    public String getSecondAnesthesiaDoctor()
    {
        return SecondAnesthesiaDoctor;
    }
    
    public void setSecondAnesthesiaDoctor(String secondAnesthesiaDoctor)
    {
        SecondAnesthesiaDoctor = secondAnesthesiaDoctor;
    }
    
    @XmlElement(name = "ThirdAnesthesiaDoctor")
    public String getThirdAnesthesiaDoctor()
    {
        return ThirdAnesthesiaDoctor;
    }
    
    public void setThirdAnesthesiaDoctor(String thirdAnesthesiaDoctor)
    {
        ThirdAnesthesiaDoctor = thirdAnesthesiaDoctor;
    }
    
    @XmlElement(name = "AnesthesiaAssistant")
    public String getAnesthesiaAssistant()
    {
        return AnesthesiaAssistant;
    }
    
    public void setAnesthesiaAssistant(String anesthesiaAssistant)
    {
        AnesthesiaAssistant = anesthesiaAssistant;
    }
    
    @XmlElement(name = "SecondAnesthesiaAssistant")
    public String getSecondAnesthesiaAssistant()
    {
        return SecondAnesthesiaAssistant;
    }
    
    public void setSecondAnesthesiaAssistant(String secondAnesthesiaAssistant)
    {
        SecondAnesthesiaAssistant = secondAnesthesiaAssistant;
    }
    
    @XmlElement(name = "ThirdAnesthesiaAssistant")
    public String getThirdAnesthesiaAssistant()
    {
        return ThirdAnesthesiaAssistant;
    }
    
    public void setThirdAnesthesiaAssistant(String thirdAnesthesiaAssistant)
    {
        ThirdAnesthesiaAssistant = thirdAnesthesiaAssistant;
    }
    
    @XmlElement(name = "FourthAnesthesiaAssistant")
    public String getFourthAnesthesiaAssistant()
    {
        return FourthAnesthesiaAssistant;
    }
    
    public void setFourthAnesthesiaAssistant(String fourthAnesthesiaAssistant)
    {
        FourthAnesthesiaAssistant = fourthAnesthesiaAssistant;
    }
    
    @XmlElement(name = "BloodTranDoctor")
    public String getBloodTranDoctor()
    {
        return BloodTranDoctor;
    }
    
    public void setBloodTranDoctor(String bloodTranDoctor)
    {
        BloodTranDoctor = bloodTranDoctor;
    }
    
    @XmlElement(name = "FirstOperationNurse")
    public String getFirstOperationNurse()
    {
        return FirstOperationNurse;
    }
    
    public void setFirstOperationNurse(String firstOperationNurse)
    {
        FirstOperationNurse = firstOperationNurse;
    }
    
    @XmlElement(name = "SecondOperationNurse")
    public String getSecondOperationNurse()
    {
        return SecondOperationNurse;
    }
    
    public void setSecondOperationNurse(String secondOperationNurse)
    {
        SecondOperationNurse = secondOperationNurse;
    }
    
    @XmlElement(name = "ThirdOperationNurse")
    public String getThirdOperationNurse()
    {
        return ThirdOperationNurse;
    }
    
    public void setThirdOperationNurse(String thirdOperationNurse)
    {
        ThirdOperationNurse = thirdOperationNurse;
    }
    
    @XmlElement(name = "ThirdSupplyNurse")
    public String getThirdSupplyNurse()
    {
        return ThirdSupplyNurse;
    }
    
    public void setThirdSupplyNurse(String thirdSupplyNurse)
    {
        ThirdSupplyNurse = thirdSupplyNurse;
    }
    
    @XmlElement(name = "NotesOnOperation")
    public String getNotesOnOperation()
    {
        return NotesOnOperation;
    }
    
    public void setNotesOnOperation(String notesOnOperation)
    {
        NotesOnOperation = notesOnOperation;
    }
    
    @XmlElement(name = "ReqDateTime")
    public String getReqDateTime()
    {
        return ReqDateTime;
    }
    
    public void setReqDateTime(String reqDateTime)
    {
        ReqDateTime = reqDateTime;
    }
    
    @XmlElement(name = "EnteredBy")
    public String getEnteredBy()
    {
        return EnteredBy;
    }
    
    public void setEnteredBy(String enteredBy)
    {
        EnteredBy = enteredBy;
    }
    
    @XmlElement(name = "Status")
    public String getStatus()
    {
        return Status;
    }
    
    public void setStatus(String status)
    {
        Status = status;
    }
    
    @XmlElement(name = "OperationPosition")
    public String getOperationPosition()
    {
        return OperationPosition;
    }
    
    public void setOperationPosition(String operationPosition)
    {
        OperationPosition = operationPosition;
    }
    
    @XmlElement(name = "SpecialEquipment")
    public String getSpecialEquipment()
    {
        return SpecialEquipment;
    }
    
    public void setSpecialEquipment(String specialEquipment)
    {
        SpecialEquipment = specialEquipment;
    }
    
    @XmlElement(name = "SpecialInfect")
    public String getSpecialInfect()
    {
        return SpecialInfect;
    }
    
    public void setSpecialInfect(String specialInfect)
    {
        SpecialInfect = specialInfect;
    }
    
    @XmlElement(name = "Operation")
    public String getOperation()
    {
        return Operation;
    }
    
    public void setOperation(String operation)
    {
        Operation = operation;
    }
    
    @XmlElement(name = "UserId")
    public String getUserId()
    {
        return UserId;
    }
    
    public void setUserId(String userId)
    {
        UserId = userId;
    }
    
    @XmlElement(name = "UserCode")
    public String getUserCode()
    {
        return UserCode;
    }
    
    public void setUserCode(String userCode)
    {
        UserCode = userCode;
    }
    
    @XmlElement(name = "UserName")
    public String getUserName()
    {
        return UserName;
    }
    
    public void setUserName(String userName)
    {
        UserName = userName;
    }
    
    @XmlElement(name = "UserDept")
    public String getUserDept()
    {
        return UserDept;
    }
    
    public void setUserDept(String userDept)
    {
        UserDept = userDept;
    }
    
    @XmlElement(name = "UserDeptName")
    public String getUserDeptName()
    {
        return UserDeptName;
    }
    
    public void setUserDeptName(String userDeptName)
    {
        UserDeptName = userDeptName;
    }
    
    @XmlElement(name = "UserJob")
    public String getUserJob()
    {
        return UserJob;
    }
    
    public void setUserJob(String userJob)
    {
        UserJob = userJob;
    }
    
    @XmlElement(name = "ScheduleId")
    public String getScheduleId()
    {
        return ScheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        ScheduleId = scheduleId;
    }
    
    @XmlElement(name = "DeptID")
    public String getDeptID()
    {
        return DeptID;
    }
    
    public void setDeptID(String deptID)
    {
        DeptID = deptID;
    }
    
    @XmlElement(name = "ScheduledDateTime")
    public String getScheduledDateTime()
    {
        return ScheduledDateTime;
    }
    
    public void setScheduledDateTime(String scheduledDateTime)
    {
        ScheduledDateTime = scheduledDateTime;
    }
    
    @XmlElement(name = "OperRoom")
    public String getOperRoom()
    {
        return OperRoom;
    }
    
    public void setOperRoom(String operRoom)
    {
        OperRoom = operRoom;
    }
    
    @XmlElement(name = "OperRoomNo")
    public String getOperRoomNo()
    {
        return OperRoomNo;
    }
    
    public void setOperRoomNo(String operRoomNo)
    {
        OperRoomNo = operRoomNo;
    }
    
    @XmlElement(name = "Sequence")
    public String getSequence()
    {
        return Sequence;
    }
    
    public void setSequence(String sequence)
    {
        Sequence = sequence;
    }
    
    @XmlElement(name = "DiagBeforeOperation")
    public String getDiagBeforeOperation()
    {
        return DiagBeforeOperation;
    }
    
    public void setDiagBeforeOperation(String diagBeforeOperation)
    {
        DiagBeforeOperation = diagBeforeOperation;
    }
    
    @XmlElement(name = "AnesMethod")
    public String getAnesMethod()
    {
        return AnesMethod;
    }
    
    public void setAnesMethod(String anesMethod)
    {
        AnesMethod = anesMethod;
    }
    
    @XmlElement(name = "Surgeon")
    public String getSurgeon()
    {
        return Surgeon;
    }
    
    public void setSurgeon(String surgeon)
    {
        Surgeon = surgeon;
    }
    
    @XmlElement(name = "FirstOperAssistant")
    public String getFirstOperAssistant()
    {
        return FirstOperAssistant;
    }
    
    public void setFirstOperAssistant(String firstOperAssistant)
    {
        FirstOperAssistant = firstOperAssistant;
    }
    
    @XmlElement(name = "SecondOperAssistant")
    public String getSecondOperAssistant()
    {
        return SecondOperAssistant;
    }
    
    public void setSecondOperAssistant(String secondOperAssistant)
    {
        SecondOperAssistant = secondOperAssistant;
    }
    
    @XmlElement(name = "AnesDoctor")
    public String getAnesDoctor()
    {
        return AnesDoctor;
    }
    
    public void setAnesDoctor(String anesDoctor)
    {
        AnesDoctor = anesDoctor;
    }
    
    @XmlElement(name = "FirstAnesAssistant")
    public String getFirstAnesAssistant()
    {
        return FirstAnesAssistant;
    }
    
    public void setFirstAnesAssistant(String firstAnesAssistant)
    {
        FirstAnesAssistant = firstAnesAssistant;
    }
    
    @XmlElement(name = "SecondAnesAssistant")
    public String getSecondAnesAssistant()
    {
        return SecondAnesAssistant;
    }
    
    public void setSecondAnesAssistant(String secondAnesAssistant)
    {
        SecondAnesAssistant = secondAnesAssistant;
    }
    
    @XmlElement(name = "FirstOperNurse")
    public String getFirstOperNurse()
    {
        return FirstOperNurse;
    }
    
    public void setFirstOperNurse(String firstOperNurse)
    {
        FirstOperNurse = firstOperNurse;
    }
    
    @XmlElement(name = "SecondOperNurse")
    public String getSecondOperNurse()
    {
        return SecondOperNurse;
    }
    
    public void setSecondOperNurse(String secondOperNurse)
    {
        SecondOperNurse = secondOperNurse;
    }
    
    @XmlElement(name = "FirstSupplyNurse")
    public String getFirstSupplyNurse()
    {
        return FirstSupplyNurse;
    }
    
    public void setFirstSupplyNurse(String firstSupplyNurse)
    {
        FirstSupplyNurse = firstSupplyNurse;
    }
    
    @XmlElement(name = "SecondSupplyNurse")
    public String getSecondSupplyNurse()
    {
        return SecondSupplyNurse;
    }
    
    public void setSecondSupplyNurse(String secondSupplyNurse)
    {
        SecondSupplyNurse = secondSupplyNurse;
    }
    
    @XmlElement(name = "WardID")
    public String getWardID()
    {
        return WardID;
    }
    
    public void setWardID(String wardID)
    {
        WardID = wardID;
    }
    
    @XmlElement(name = "ReportItemCode")
    public String getReportItemCode()
    {
        return ReportItemCode;
    }
    
    public void setReportItemCode(String reportItemCode)
    {
        ReportItemCode = reportItemCode;
    }
    
    @XmlElement(name = "ReportItemName")
    public String getReportItemName()
    {
        return ReportItemName;
    }
    
    public void setReportItemName(String reportItemName)
    {
        ReportItemName = reportItemName;
    }
    
    @XmlElement(name = "AbnormalIndicator")
    public String getAbnormalIndicator()
    {
        return AbnormalIndicator;
    }
    
    public void setAbnormalIndicator(String abnormalIndicator)
    {
        AbnormalIndicator = abnormalIndicator;
    }
    
    @XmlElement(name = "Result")
    public String getResult()
    {
        return Result;
    }
    
    public void setResult(String result)
    {
        Result = result;
    }
    
    @XmlElement(name = "Units")
    public String getUnits()
    {
        return Units;
    }
    
    public void setUnits(String units)
    {
        Units = units;
    }
    
    @XmlElement(name = "ResultDateTime")
    public String getResultDateTime()
    {
        return ResultDateTime;
    }
    
    public void setResultDateTime(String resultDateTime)
    {
        ResultDateTime = resultDateTime;
    }
    
    @XmlElement(name = "Referenceresult")
    public String getReferenceResult()
    {
        return ReferenceResult;
    }
    
    public void setReferenceResult(String referenceResult)
    {
        ReferenceResult = referenceResult;
    }
    
    @XmlElement(name = "TestNo")
    public String getTestNo()
    {
        return TestNo;
    }
    
    public void setTestNo(String testNo)
    {
        TestNo = testNo;
    }
    
    @XmlElement(name = "PriorityIndicator")
    public String getPriorityIndicator()
    {
        return PriorityIndicator;
    }
    
    public void setPriorityIndicator(String priorityIndicator)
    {
        PriorityIndicator = priorityIndicator;
    }
    
    @XmlElement(name = "PatientId")
    public String getPatientId()
    {
        return PatientId;
    }
    
    public void setPatientId(String patientId)
    {
        PatientId = patientId;
    }
    
    @XmlElement(name = "VisitId")
    public String getVisitId()
    {
        return VisitId;
    }
    
    public void setVisitId(String visitId)
    {
        VisitId = visitId;
    }
    
    @XmlElement(name = "PatientName")
    public String getPatientName()
    {
        return PatientName;
    }
    
    public void setPatientName(String patientName)
    {
        PatientName = patientName;
    }
    
    @XmlElement(name = "WorkingId")
    public String getWorkingId()
    {
        return WorkingId;
    }
    
    public void setWorkingId(String workingId)
    {
        WorkingId = workingId;
    }
    
    @XmlElement(name = "TestCause")
    public String getTestCause()
    {
        return TestCause;
    }
    
    public void setTestCause(String testCause)
    {
        TestCause = testCause;
    }
    
    @XmlElement(name = "RelevantClinicDiag")
    public String getRelevantClinicDiag()
    {
        return RelevantClinicDiag;
    }
    
    public void setRelevantClinicDiag(String relevantClinicDiag)
    {
        RelevantClinicDiag = relevantClinicDiag;
    }
    
    @XmlElement(name = "Specimen")
    public String getSpecimen()
    {
        return Specimen;
    }
    
    public void setSpecimen(String specimen)
    {
        Specimen = specimen;
    }
    
    @XmlElement(name = "SpcmReceivedDateTime")
    public String getSpcmReceivedDateTime()
    {
        return SpcmReceivedDateTime;
    }
    
    public void setSpcmReceivedDateTime(String spcmReceivedDateTime)
    {
        SpcmReceivedDateTime = spcmReceivedDateTime;
    }
    
    @XmlElement(name = "OrderingDept")
    public String getOrderingDept()
    {
        return OrderingDept;
    }
    
    public void setOrderingDept(String orderingDept)
    {
        OrderingDept = orderingDept;
    }
    
    @XmlElement(name = "OrderingProvider")
    public String getOrderingProvider()
    {
        return OrderingProvider;
    }
    
    public void setOrderingProvider(String orderingProvider)
    {
        OrderingProvider = orderingProvider;
    }
    
    @XmlElement(name = "PerformedBy")
    public String getPerformedBy()
    {
        return PerformedBy;
    }
    
    public void setPerformedBy(String performedBy)
    {
        PerformedBy = performedBy;
    }
    
    @XmlElement(name = "ResultStatus")
    public String getResultStatus()
    {
        return ResultStatus;
    }
    
    public void setResultStatus(String resultStatus)
    {
        ResultStatus = resultStatus;
    }
    
    @XmlElement(name = "ResultsRptDateTime")
    public String getResultsRptDateTime()
    {
        return ResultsRptDateTime;
    }
    
    public void setResultsRptDateTime(String resultsRptDateTime)
    {
        ResultsRptDateTime = resultsRptDateTime;
    }
    
    @XmlElement(name = "Transcriptionist")
    public String getTranscriptionist()
    {
        return Transcriptionist;
    }
    
    public void setTranscriptionist(String transcriptionist)
    {
        Transcriptionist = transcriptionist;
    }
    
    @XmlElement(name = "VerifiedBy")
    public String getVerifiedBy()
    {
        return VerifiedBy;
    }
    
    public void setVerifiedBy(String verifiedBy)
    {
        VerifiedBy = verifiedBy;
    }
    
    @XmlElement(name = "DiseaseId")
    public String getDiseaseId()
    {
        return DiseaseId;
    }
    
    public void setDiseaseId(String diseaseId)
    {
        DiseaseId = diseaseId;
    }
    
    @XmlElement(name = "DiseaseCode")
    public String getDiseaseCode()
    {
        return DiseaseCode;
    }
    
    public void setDiseaseCode(String diseaseCode)
    {
        DiseaseCode = diseaseCode;
    }
    
    @XmlElement(name = "DiseaseName")
    public String getDiseaseName()
    {
        return DiseaseName;
    }
    
    public void setDiseaseName(String diseaseName)
    {
        DiseaseName = diseaseName;
    }
    
    @XmlElement(name = "DiseaseType")
    public String getDiseaseType()
    {
        return DiseaseType;
    }
    
    public void setDiseaseType(String diseaseType)
    {
        DiseaseType = diseaseType;
    }
    
    @XmlElement(name = "OperationCode")
    public String getOperationCode()
    {
        return OperationCode;
    }
    
    public void setOperationCode(String operationCode)
    {
        OperationCode = operationCode;
    }
    
    @XmlElement(name = "OperationName")
    public String getOperationName()
    {
        return OperationName;
    }
    
    public void setOperationName(String operationName)
    {
        OperationName = operationName;
    }
    
    @XmlElement(name = "StdIndicator")
    public String getStdIndicator()
    {
        return StdIndicator;
    }
    
    public void setStdIndicator(String stdIndicator)
    {
        StdIndicator = stdIndicator;
    }
    
    @XmlElement(name = "ApprovedIndicator")
    public String getApprovedIndicator()
    {
        return ApprovedIndicator;
    }
    
    public void setApprovedIndicator(String approvedIndicator)
    {
        ApprovedIndicator = approvedIndicator;
    }
    
    @XmlElement(name = "CreateDate")
    public String getCreateDate()
    {
        return CreateDate;
    }
    
    public void setCreateDate(String createDate)
    {
        CreateDate = createDate;
    }
    
    @XmlElement(name = "SerialNo")
    public String getSerialNo()
    {
        return SerialNo;
    }
    
    public void setSerialNo(String serialNo)
    {
        SerialNo = serialNo;
    }
    
    @XmlElement(name = "DeptCode")
    public String getDeptCode()
    {
        return DeptCode;
    }
    
    public void setDeptCode(String deptCode)
    {
        DeptCode = deptCode;
    }
    
    @XmlElement(name = "DeptName")
    public String getDeptName()
    {
        return DeptName;
    }
    
    public void setDeptName(String deptName)
    {
        DeptName = deptName;
    }
    
    @XmlElement(name = "InputCode")
    public String getInputCode()
    {
        return InputCode;
    }
    
    public void setInputCode(String inputCode)
    {
        InputCode = inputCode;
    }
}
