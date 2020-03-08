package com.beone.shg.net.persistence.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.bo.InitEnumBO;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.webservice.rest.model.resp.AccessCodeEnum;
import com.beone.shg.net.webservice.rest.model.resp.DistrictValue;
import com.beone.shg.net.webservice.rest.model.resp.DistrictsEnum;
import com.beone.shg.net.webservice.rest.model.resp.DocTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.FundTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.GenericEnum;
import com.beone.shg.net.webservice.rest.model.resp.GroupTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeCatEnum;
import com.beone.shg.net.webservice.rest.model.resp.MProfilingTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MRoleEnum;
import com.beone.shg.net.webservice.rest.model.resp.MessageTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.MonthlyReportsEnum;
import com.beone.shg.net.webservice.rest.model.resp.NameTitleEnum;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeEnum;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.shg.net.webservice.rest.support.InternalServerErrorException;

@Component("enumBO")
public class EnumBO extends BaseBO {

    private InitEnumBO initEnumBO;

	@Autowired
	@Qualifier("initEnumBO")
	public void setInitEnumBO(InitEnumBO initEnumBO) {
		this.initEnumBO = initEnumBO;
	}

    public void addEnumValues(GenericEnum addEnum) throws BadRequestException {
    	initEnumBO.addEnumValues(addEnum);
	}

    public void updateEnumValues(GenericEnum addEnum) throws BadRequestException {
    	initEnumBO.updateEnumValues(addEnum);
	}

    public void loadAllEnumValues() {
    	initEnumBO.loadAllEnumValues();
    }

    public GenericEnum getEnumValues(String enumName) throws BadRequestException, InternalServerErrorException {
		
		if(enumName == null || enumName.isEmpty()) {
			throw new BadRequestException("Null Or Empty Enum Name");
		}
		
		GenericEnum gEnum = EnumCache.getEnumValues(enumName);
		
		if(gEnum == null) {	
			throw new InternalServerErrorException("Invalid Enum Name");
		}
		
    	return gEnum;
    }

    public void addTxType(TxTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Tx Type Enum");
		}

		initEnumBO.addTxType(enu);
	}

    public void updateTxType(TxTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Tx Type Enum");
		}

    	initEnumBO.updateTxType(enu);
	}

    public TxTypeEnum getTxType() throws InternalServerErrorException {
    	
    	TxTypeEnum txTypeEnum = EnumCache.getTxTypeShare();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Tx Type Enum");
		}
		
    	return txTypeEnum;
	}

    public void addGroupType(GroupTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumBO.addGroupType(enu);
	}

    public void updateGroupType(GroupTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumBO.updateGroupType(enu);
	}

    public GroupTypeEnum getGroupType() throws InternalServerErrorException {
    	
    	GroupTypeEnum txTypeEnum = EnumCache.getGroupTypeShare();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}

    public void addNameTitle(NameTitleEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumBO.addNameTitle(enu);
	}

    public void updateNameTitle(NameTitleEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Name Title Enum");
		}

    	initEnumBO.updateNameTitle(enu);
	}

    public NameTitleEnum getNameTitle() throws InternalServerErrorException {
    	
    	NameTitleEnum txTypeEnum = EnumCache.getNameTitle();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Name Title Enum");
		}
		
    	return txTypeEnum;
	}

    public void addMRole(MRoleEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Member Role Enum");
		}

    	initEnumBO.addMRole(enu);
	}

    public void updateMRole(MRoleEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Member Role Enum");
		}

    	initEnumBO.updateMRole(enu);
	}

    public MRoleEnum getMRole() throws InternalServerErrorException {
    	
    	MRoleEnum txTypeEnum = EnumCache.getMRoleShare();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Member Role Enum");
		}
		
    	return txTypeEnum;
	}

    public void addFundType(FundTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Fund Type Enum");
		}

    	initEnumBO.addFundType(enu);
	}

    public void updateFundType(FundTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Fund Type Enum");
		}

    	initEnumBO.updateFundType(enu);
	}

    public FundTypeEnum getFundType() throws InternalServerErrorException {
    	
    	FundTypeEnum txTypeEnum = EnumCache.getFundType();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Fund Type Enum");
		}
		
    	return txTypeEnum;
	}

    public void addDistricts(DistrictsEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Districts");
		}

    	initEnumBO.addDistricts(enu);
	}

    public void updateDistricts(DistrictsEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Districts");
		}

    	initEnumBO.updateDistricts(enu);
	}

    public DistrictsEnum getDistricts(String lang) throws BadRequestException {
    	
    	DistrictsEnum districtsEnum = EnumCache.getDistrictsREST(lang);
		
		if(districtsEnum == null) {	
			throw new BadRequestException("Invalid Districts");
		}

    	DistrictsEnum dEnum = new DistrictsEnum();
    	dEnum.setLang(districtsEnum.getLang());
    	for(DistrictValue dInfo: districtsEnum.getDistrictValues()) {
    		if(!dInfo.getDivision().equals(EnumConst.State_Admin)) {
    			dEnum.addDistrictValue(dInfo);
    		}
    	}
    	
    	return dEnum;
	}

	public void addMonthlyReports(MonthlyReportsEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Monthly Report");
		}

    	initEnumBO.addMonthlyReports(enu);
	}

	public void updateMonthlyReports(MonthlyReportsEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Monthly Report");
		}

    	initEnumBO.updateMonthlyReports(enu);
	}

	public MonthlyReportsEnum getMonthlyReports() throws InternalServerErrorException {
		
		MonthlyReportsEnum monthlyReportEnum = EnumCache.getMonthlyReportsShare();
		
		if(monthlyReportEnum == null) {	
			throw new InternalServerErrorException("Invalid Monthly Reports");
		}
		
    	return monthlyReportEnum;
	}

    public void addDocType(DocTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Document Type");
		}

    	initEnumBO.addDocType(enu);
	}

    public void updateDocType(DocTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Document Type");
		}

    	initEnumBO.updateDocType(enu);
	}

    public DocTypeEnum getDocType() throws InternalServerErrorException {
    	
    	DocTypeEnum txTypeEnum = EnumCache.getDocType();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Document Type");
		}
		
    	return txTypeEnum;
	}

    public void addMessageType(MessageTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Message Type");
		}

    	initEnumBO.addMessageType(enu);
	}

    public void updateMessageType(MessageTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Message Type");
		}

    	initEnumBO.updateMessageType(enu);
	}

    public MessageTypeEnum getMessageType() throws InternalServerErrorException {
    	
    	MessageTypeEnum txTypeEnum = EnumCache.getMessageType();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Message Type");
		}
		
    	return txTypeEnum;
	}

    public void addMProfilingType(MProfilingTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty MProfiling Type");
		}

    	initEnumBO.addMProfilingType(enu);
	}

    public void updateMProfilingType(MProfilingTypeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty MProfiling Type");
		}

    	initEnumBO.updateMProfilingType(enu);
	}

    public MProfilingTypeEnum getMProfilingType() throws InternalServerErrorException {
    	
    	MProfilingTypeEnum mProfilingTypeEnum = EnumCache.getMProfilingType();
		
		if(mProfilingTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid MProfiling Type");
		}

    	return mProfilingTypeEnum;
	}

    public MProfilingTypeCatEnum getMProfilingType(String profileFor) throws InternalServerErrorException {
    	
    	MProfilingTypeCatEnum mProfilingTypeEnum = EnumCache.getMProfilingType(profileFor);
		
		if(mProfilingTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid MProfiling Type");
		}

    	return mProfilingTypeEnum;
	}

    public void addUiAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.addUiAccessCode(enu);
	}

    public void updateUiAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.updateUiAccessCode(enu);
	}

    public AccessCodeEnum getUiAccessCode() throws InternalServerErrorException {
    	
    	AccessCodeEnum txTypeEnum = EnumCache.getUiAccessCode();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Access Code Type");
		}
		
    	return txTypeEnum;
	}

    public void addWsAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.addWsAccessCode(enu);
	}

    public void updateWsAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.updateWsAccessCode(enu);
	}

    public AccessCodeEnum getWsAccessCode() throws InternalServerErrorException {
    	
    	AccessCodeEnum txTypeEnum = EnumCache.getWsAccessCode();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Access Code Type");
		}
		
    	return txTypeEnum;
	}

    public void addMrUiAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.addMrUiAccessCode(enu);
	}

    public void updateMrUiAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.updateMrUiAccessCode(enu);
	}

    public AccessCodeEnum getMrUiAccessCode() throws InternalServerErrorException {
    	
    	AccessCodeEnum txTypeEnum = EnumCache.getMrUiAccessCode();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Access Code Type");
		}
		
    	return txTypeEnum;
	}

    public void addMrWsAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.addMrWsAccessCode(enu);
	}

    public void updateMrWsAccessCode(AccessCodeEnum enu) throws BadRequestException {
		
		if(enu == null) {
			throw new BadRequestException("Null Or Empty Access Code Type");
		}

    	initEnumBO.updateMrWsAccessCode(enu);
	}

    public AccessCodeEnum getMrWsAccessCode() throws InternalServerErrorException {
    	
    	AccessCodeEnum txTypeEnum = EnumCache.getMrWsAccessCode();
		
		if(txTypeEnum == null) {	
			throw new InternalServerErrorException("Invalid Access Code Type");
		}
		
    	return txTypeEnum;
	}
}
