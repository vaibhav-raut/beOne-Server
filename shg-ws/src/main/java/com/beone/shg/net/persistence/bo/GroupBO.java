package com.beone.shg.net.persistence.bo;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.bo.util.RestDisplayTitle;
import com.beone.shg.net.persistence.job.DataFeildsUpdateJob;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.model.BankAcUpload;
import com.beone.shg.net.persistence.model.BankAccount;
import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.model.Contact;
import com.beone.shg.net.persistence.model.District;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GAcByTxtype;
import com.beone.shg.net.persistence.model.GAcByTxtypeId;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.GG;
import com.beone.shg.net.persistence.model.GGId;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.model.GLoanAc;
import com.beone.shg.net.persistence.model.GM;
import com.beone.shg.net.persistence.model.GMId;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.GroupContact;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MonthlyGAc;
import com.beone.shg.net.persistence.support.BankInfoCollector;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.MemberOtherAcInfo.PenaltyDetail;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeEnum;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;
import com.beone.shg.net.webservice.rest.model.rest.ApplicationsREST;
import com.beone.shg.net.webservice.rest.model.rest.BankAccountREST;
import com.beone.shg.net.webservice.rest.model.rest.BankProfileREST;
import com.beone.shg.net.webservice.rest.model.rest.BankSearchInfo;
import com.beone.shg.net.webservice.rest.model.rest.GAcByTxtypeREST;
import com.beone.shg.net.webservice.rest.model.rest.GBankAccountREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupAc;
import com.beone.shg.net.webservice.rest.model.rest.GroupContactREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupInvtAcREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupLoanAcREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupRules;
import com.beone.shg.net.webservice.rest.model.rest.MemberSearchInfo;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.bo.MrAccountBO;

@Component("groupBO")
public class GroupBO extends BaseBO {
	private final static Logger log = LoggerFactory.getLogger(GroupBO.class);

	@Autowired
	@Qualifier("memberBO")
	private MemberBO memberBO;

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	@Autowired
	@Qualifier("mrAccountBO")
	private MrAccountBO mrAccountBO;

	public GroupREST addGroup(GroupREST group, boolean oldData, District district) throws BadRequestException {

		if(group.getContacts() == null || group.getContacts().isEmpty()) {
			throw new BadRequestException("Null Or Empty Group Contact");
		}		
		if(group.getGroupType() == null || group.getGroupType().isEmpty()) {
			group.setGroupType(EnumConst.GroupType_SHG);
		}
		int groupId = EnumCache.getIndexOfGroupType(group.getGroupType());
		if(groupId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Group Type");
		}		
		if(group.getGroupType().equals(EnumConst.GroupType_Bank)) {
			if (group.getBankProfile() == null || 
					group.getBankProfile().getBankName() == null ||
					group.getBankProfile().getBankName().isEmpty() || 
					group.getBankProfile().getBranchName() == null ||
					group.getBankProfile().getBranchName().isEmpty() || 
					group.getBankProfile().getIfcsCode() == null ||
					group.getBankProfile().getIfcsCode().isEmpty()) {

				throw new BadRequestException("Null Or Empty Bank Profile Info");
			}
		}
		
		GProfile gProfile = new GProfile();
		
		// Get District ID & Group Counter for the District
		for(GroupContactREST contactREST : group.getContacts()) {
			if(daoFactory.getLangDAO().findByValue(contactREST.getLang()).getLang().equalsIgnoreCase(EnumConst.Lang_English)) {

				if(district == null) {
					if(contactREST.getDistrict() == null || contactREST.getDistrict().isEmpty()) {
						throw new BadRequestException("Invalid District");
					}			
					district = daoFactory.getDistrictDAO().getDistrictFromName(contactREST.getState(), contactREST.getDistrict());
				}
				if(district == null) {
					throw new BadRequestException("Invalid District for Creating Group : '" + contactREST.getDistrict() + "'");
				}
				if(district.getGroupCounter() >= DataUtil.MAX_GROUP_PER_DISTRICT) {
					throw new BadRequestException("Congratulation! '" + district.getDistrict() + "' has crossed Max Group Limit per District, please contact Admin for help!");
				}

				gProfile.setHomeDistrictId(district.getDistrictId());
				gProfile.setDistrictGroupCounter(district.getGroupCounter());
				
				group.setDistrictCode(district.getDistrictCode());
				group.setDistrictId(district.getDistrictId());
				break;
			}
		}

		gProfile.setGroupTypeId(groupId);

		if(oldData) {
			if(group.getApprovalStatus() == null || group.getApprovalStatus().isEmpty()) {
				throw new BadRequestException("Invalid Approval Status");
			}			
			int approvalStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, group.getApprovalStatus());
			if(approvalStatusId <= DataUtil.ZERO_INTEGER) {
				throw new BadRequestException("Invalid Approval Status: " + group.getApprovalStatus());
			}			
			gProfile.setApprovalStatusId(approvalStatusId);
			group.setApprovalStatus(group.getApprovalStatus());
			
			if(group.getActiveStatus() == null || group.getActiveStatus().isEmpty()) {
				throw new BadRequestException("Invalid Active Status");
			}			
			int activeStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, group.getActiveStatus());
			if(approvalStatusId <= DataUtil.ZERO_INTEGER) {
				throw new BadRequestException("Invalid Active Status: " + group.getActiveStatus());
			}			
			gProfile.setActiveStatusId(activeStatusId);
			group.setActiveStatus(group.getActiveStatus());
		} else {
			gProfile.setApprovalStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Submitted));
			group.setApprovalStatus(EnumConst.ApprovalStatus_Submitted);
			gProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Requested));
			group.setActiveStatus(EnumConst.ActiveStatus_Requested);
		}
		gProfile.setPercentageProfileComplete(0.0f);
		gProfile.setActiveChildGroups(0);
		gProfile.setMemberCounter(0);
		gProfile.setActiveCoreMembers(0);
		gProfile.setActiveAssociateMembers(0);
		gProfile.setNoOfTopGroups(0);
		gProfile.setNoOfDownGroups(0);
		
		gProfile.setEstablishmentDate(DateUtil.convertStringToDateWithCurrentDefault(group.getDateOfEstablishment()));
		gProfile.setLastLoggedInTs(new Date(System.currentTimeMillis()));
		gProfile.setLogoUrl(group.getLogoUrl());
		gProfile.setDescription(group.getStatusMessage());
		gProfile.setLocation(group.getLocation());
		
		// Save the Group
		daoFactory.getGProfileDAO().persist(gProfile);

		// Load Group Ac No 
		group.setGroupAcNo(gProfile.getGAcNo());
		
		// Increment group counter for the District 
		district.setGroupCounter(district.getGroupCounter() + 1);
		daoFactory.getDistrictDAO().merge(district);

		// Add Contact
		addGroupContacts(group);
		
		// Add Bank Accounts
		addGroupBankAccounts(group);
		
		// Add Group Mapping
		if(group.getMappingMemberAcNos() != null) {
			mapGroupToMembersInternal(gProfile, group.getMappingMemberAcNos());
		}
		
		// Add Accounts details Only for SHG Type Account
		if(group.getGroupType().equals(EnumConst.GroupType_SHG)) {
			
			// Create Group Account for the Added GroupProfile
			if(oldData) {
				addOldGroupAccount(gProfile, group.getGroupAc());
			} else {
				addNewGroupAccount(gProfile);
			}
			
			if(oldData && group.getGAcByTxtypes() != null) {
				// Add Old Account per Transaction Type
				addOldAcByTxtype(gProfile, group.getGAcByTxtypes());
			} else {
				// Create Group Account per Transaction Type
				addAcByTxtype(gProfile);
			}
			
			if(oldData && group.getGroupRules() != null) {
				// Old Rules for Group
				addOldGroupRules(gProfile, group.getGroupRules());
			} else {
				int associateAllowed = DataUtil.ZERO_INTEGER;
				if(group.getGroupRules() != null) {
					associateAllowed = group.getGroupRules().getAllowAssociateMembers();
				}
				else if(group.getGroupAc() != null &&
						group.getGroupAc().getAMPlannedMonthlySaving().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
					associateAllowed = 1;
				}
				// Create Default Rules for Group
				addNewGroupRules(gProfile, group.getGroupRules(), associateAllowed);
			}
		} 
		else if(group.getGroupType().equals(EnumConst.GroupType_Bank)) {

			BankProfile bProfile = new BankProfile();
			
			bProfile.setGProfile(gProfile);
			BankProfileREST.updateBankProfile(group.getBankProfile(), bProfile);
			
			daoFactory.getBankProfileDAO().persist(bProfile);
		}
		
		//Add Mega Hub Account 
		if(group.getGroupType().equals(EnumConst.GroupType_Mega_HUB)) {
			mrAccountBO.createPHubAccount(gProfile.getGAcNo());
		}
		
		return group;
	}
	
	public GroupREST addGroup(GroupREST group) throws BadRequestException {
		return addGroup(group, false, null);
	}
	
	public GroupREST addGroup(GroupREST group, District district) throws BadRequestException {
		return addGroup(group, false, district);
	}
	
	public GroupREST addGroup(GroupREST group, boolean oldData) throws BadRequestException {
		return addGroup(group, oldData, null);
	}
	
	public List<Map<String,String>> addGroupsCSV(String stateStr, String districtStr, List<String[]> rawGroups) throws BadRequestException {
		
		if(districtStr == null || districtStr.isEmpty()) {
			throw new BadRequestException("Invalid District");
		}			
		District district = daoFactory.getDistrictDAO().getDistrictFromName(stateStr, districtStr);
		if(district == null) {
			throw new BadRequestException("Invalid District for Creating Group : '" + districtStr + "'");
		}

		List<GroupREST> groups = new ArrayList<GroupREST>(rawGroups.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawGroups);

		if(!GroupREST.isGroupCSVValid(csvData)) {
			throw new BadRequestException("Invalid Group CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			groups.add(GroupREST.buildGroup(districtStr, csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(groups.size());

		for(GroupREST group: groups) { 
			try {
				returnList.add(addGroup(group, true).toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException(e.getMessage() + " - caused for - " + group.toStringInfo());				
			}
		}

		return returnList;
	}
	
	public List<String> getCSVUpdateTypes() {
		List<String> types = new ArrayList<String>();
		types.add(EnumConst.Update_Type_Add_Members);
		types.add(EnumConst.Update_Type_Update_Group_Account);
		types.add(EnumConst.Update_Type_Update_Members_Accounts);
		types.add(EnumConst.Update_Type_Add_Active_Transactions);
		types.add(EnumConst.Update_Type_Add_Dead_Transactions);
		types.add(EnumConst.Update_Type_Add_Group_Bank_Accounts);
		types.add(EnumConst.Update_Type_Update_Group_Bank_Accounts);
		types.add(EnumConst.Update_Type_Add_Group_Loans);
		types.add(EnumConst.Update_Type_Update_Group_Loans);
		types.add(EnumConst.Update_Type_Add_Group_Investments);
		types.add(EnumConst.Update_Type_Update_Group_Investments);
		types.add(EnumConst.Update_Type_Add_Bank_Profiles);
		return types;
	}
	
	public List<Map<String,String>> importGroupCSVData(long groupAcNo, String updateType, List<String[]> csvData) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		
		switch(updateType) {
		
		case EnumConst.Update_Type_Add_Members:
		{
			return memberBO.addMembersCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Update_Group_Account:
		{
			return updateGroupsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Update_Members_Accounts:
		{
			return memberBO.updateMembersCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Add_Active_Transactions:
		{
			return transactionBO.addActiveTransactionsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Add_Dead_Transactions:
		{
			return transactionBO.addDeadTransactionsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Add_Group_Bank_Accounts:
		{
			return addGroupBankAccontsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Update_Group_Bank_Accounts:
		{
//			return transactionBO.addDeadTransactionsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Add_Group_Loans:
		{
//			return transactionBO.addDeadTransactionsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Update_Group_Loans:
		{
//			return transactionBO.addDeadTransactionsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Add_Group_Investments:
		{
//			return transactionBO.addDeadTransactionsCSV(groupAcNo, csvData);
		}
		
		case EnumConst.Update_Type_Update_Group_Investments:
		{
//			return transactionBO.addDeadTransactionsCSV(groupAcNo, csvData);
		}
				
		case EnumConst.Update_Type_Add_Bank_Profiles:
		{
			return addBankProfilesCSV(csvData);
		}
		
		}
		
		return null;
	}
	
	protected List<Map<String,String>> updateGroupsCSV(long groupAcNo, List<String[]> rawGroups) throws BadRequestException {

		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No: " + groupAcNo);
		}
		
		List<GroupREST> groups = new ArrayList<GroupREST>(rawGroups.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawGroups);

		if(!GroupREST.isGroupCSVValid(csvData)) {
			throw new BadRequestException("Invalid Group CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			groups.add(GroupREST.buildGroup(DataUtil.EMPTY_STRING, csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(groups.size());

		for(GroupREST group: groups) {
			try {				
				// Create Group Account for the Added GroupProfile
				addOldGroupAccount(gProfile, group.getGroupAc());

				// Add Old Account per Transaction Type
				addOldAcByTxtype(gProfile, group.getGAcByTxtypes());

				// Old Rules for Group
				addOldGroupRules(gProfile, group.getGroupRules());

				returnList.add(group.toStringInfo());
			} catch (Exception e) {
				throw new BadRequestException(e.getMessage() + " - caused for - " + group.toStringInfo());				
			}
		}

		return returnList;
	}
	
	protected List<Map<String,String>> addBankProfilesCSV(List<String[]> rawCSVData) throws BadRequestException {
		
		List<GroupREST> groups = new ArrayList<GroupREST>(rawCSVData.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawCSVData);

		if(!GroupREST.isBankProfileCSVValid(csvData)) {
			throw new BadRequestException("Invalid Bank Profile CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			groups.add(GroupREST.buildBankProfile(csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(groups.size());

		for(GroupREST group: groups) {
			try {
				returnList.add(addGroup(group, true).toStringInfo());
			} catch(Exception e) {
				log.error(e.toString() + "; Bank: " + group.getBankProfile().getBankName() + 
						"; Branch: " + group.getBankProfile().getBranchName() + "; IFSC Code: " + group.getBankProfile().getIfcsCode());
			}
		}

		return returnList;
	}
	
	protected List<Map<String,String>> addGroupBankAccontsCSV(long groupAcNo, List<String[]> rawCSVData) throws BadRequestException {
		
		List<GBankAccountREST> bankAccounts = new ArrayList<GBankAccountREST>(rawCSVData.size());
		
		CSVDataCollector csvData = new CSVDataCollector(rawCSVData);

		if(!GroupREST.isGroupBankAccountCSVValid(csvData)) {
			throw new BadRequestException("Invalid Group Bank Acconts CSV Data!");
		}
		for(int row = 0; row < csvData.getNoOfRows(); row++) {
			bankAccounts.add(GroupREST.buildGroupBankAccount(csvData, row));		
		}

		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>(bankAccounts.size());

		for(GBankAccountREST bankAccount: bankAccounts) {
			try {
				returnList.add(addGroupBankAccount(groupAcNo, bankAccount).toStringInfo());
			} catch(Exception e) {
				throw new BadRequestException(e.getMessage() + 
						" - caused for - " + "; Account Number: " + bankAccount.getAccountNumber() + 
						"; Account Name: " + bankAccount.getAccountName() + 
						"; IFSC Code: " + bankAccount.getIfcsCode());				
			}
		}

		return returnList;
	}
	
	protected void addOldGroupAccount(GProfile gProfile, GroupAc groupAc) {
		
		boolean newObject = false;
		GAc gAc = daoFactory.getGAcDAO().findById(gProfile.getGAcNo());
		
		if(gAc == null) {
			gAc = new GAc();
			gAc.setGProfile(gProfile);
			newObject = true;
		}
			
		gAc.setCreditRating(groupAc.getCreditRating());
		gAc.setApprovalRating(groupAc.getApprovalRating());
		gAc.setMeetingAttendance(groupAc.getMeetingAttendance());
		
		gAc.setCMPlannedMonthlySaving(groupAc.getCMPlannedMonthlySaving());	
		gAc.setCMSavedAm(groupAc.getCMSavedAm());
		gAc.setCMOutstandingSavedAm(groupAc.getCMOutstandingSavedAm());
		gAc.setCMReturnedSavedAm(groupAc.getCMReturnedSavedAm());
		gAc.setCMReturnedIntEnAm(groupAc.getCMReturnedIntEnAm());
		gAc.setCMProvIntEnAm(groupAc.getCMProvIntEnAm());
		gAc.setCMProfitShareDeclaredAm(groupAc.getCMProfitShareDeclaredAm());
		gAc.setCMProfitSharePaidAm(groupAc.getCMProfitSharePaidAm());
		gAc.setCMLoanAm(groupAc.getCMLoanAm());
		gAc.setCMNoOfLoans(groupAc.getCMNoOfLoans());
		gAc.setCMNoOfActiveLoans(groupAc.getCMNoOfActiveLoans());
		gAc.setCMRecLoanAm(groupAc.getCMRecLoanAm());
		gAc.setCMRecIntOnLoanAm(groupAc.getCMRecIntOnLoanAm());
		gAc.setCMProjIntOnLoanAm(groupAc.getCMProjIntOnLoanAm());
		
		gAc.setAMPlannedMonthlySaving(groupAc.getAMPlannedMonthlySaving());
		gAc.setAMSavedAm(groupAc.getAMSavedAm());
		gAc.setAMOutstandingSavedAm(groupAc.getAMOutstandingSavedAm());
		gAc.setAMProvIntEnAm(groupAc.getAMProvIntEnAm());
		gAc.setAMDividedDeclaredAm(groupAc.getAMDividedDeclaredAm());
		gAc.setAMDividedPaidAm(groupAc.getAMDividedPaidAm());
		gAc.setAMReturnedSavedAm(groupAc.getAMReturnedSavedAm());
		gAc.setAMReturnedIntEnAm(groupAc.getAMReturnedIntEnAm());
		gAc.setAMLoanAm(groupAc.getAMLoanAm());
		gAc.setAMNoOfLoans(groupAc.getAMNoOfLoans());
		gAc.setAMNoOfActiveLoans(groupAc.getAMNoOfActiveLoans());
		gAc.setAMRecLoanAm(groupAc.getAMRecLoanAm());
		gAc.setAMRecIntOnLoanAm(groupAc.getAMRecIntOnLoanAm());
		gAc.setAMProjIntOnLoanAm(groupAc.getAMProjIntOnLoanAm());
		
		gAc.setPLoanAm(groupAc.getPLoanAm());
		gAc.setPRecLoanAm(groupAc.getPRecLoanAm());
		gAc.setPRecIntOnLoanAm(groupAc.getPRecIntOnLoanAm());
		gAc.setPProjIntOnLoanAm(groupAc.getPProjIntOnLoanAm());
		gAc.setNoOfProject(groupAc.getNoOfProject());
		gAc.setNoOfActiveProject(groupAc.getNoOfActiveProject());
		
		gAc.setFixDepositInvAm(groupAc.getFixDepositInvAm());
		gAc.setRecFixDepositAm(groupAc.getRecFixDepositAm());
		gAc.setRecIntOnFixDepositAm(groupAc.getRecIntOnFixDepositAm());
		gAc.setProjIntOnFixDepositAm(groupAc.getProjIntOnFixDepositAm());
		gAc.setNoOfFixDeposit(groupAc.getNoOfFixDeposit());
		gAc.setNoOfActiveFixDeposit(groupAc.getNoOfActiveFixDeposit());
		
		gAc.setOtherInvAm(groupAc.getOtherInvAm());
		gAc.setRecOtherInvAm(groupAc.getRecOtherInvAm());
		gAc.setRecIntOnOtherInvAm(groupAc.getRecIntOnOtherInvAm());
		gAc.setProjIntOnOtherInvAm(groupAc.getProjIntOnOtherInvAm());
		gAc.setNoOfOtherInv(groupAc.getNoOfOtherInv());
		gAc.setNoOfActiveOtherInv(groupAc.getNoOfActiveOtherInv());
		
		gAc.setBorrowedLoanAm(groupAc.getBorrowedLoanAm());
		gAc.setPaidBorrowedLoanAm(groupAc.getPaidBorrowedLoanAm());
		gAc.setPaidIntOnBorrowedLoanAm(groupAc.getPaidIntOnBorrowedLoanAm());
		gAc.setProjIntOnBorrowedLoanAm(groupAc.getProjIntOnBorrowedLoanAm());
		gAc.setNoOfBankLoan(groupAc.getNoOfBankLoan());
		gAc.setNoOfActiveBankLoan(groupAc.getNoOfActiveBankLoan());
		
		gAc.setIntEnOnSavingAcAm(groupAc.getIntEnOnSavingAcAm());
		gAc.setClearBankBalanceAm(groupAc.getBankBalanceAm());
		gAc.setSubjClearingBankBalanceAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setClearCashInHandAm(groupAc.getCashInHandAm());
		gAc.setNetProfitAm(groupAc.getNetProfitAm());
		gAc.setNetProfitProjAm(groupAc.getNetProfitProjAm());
		gAc.setExpensesAm(groupAc.getExpensesAm());
		gAc.setOutstandingExpensesAm(groupAc.getOutstandingExpensesAm());
		gAc.setRecPenaltyAm(groupAc.getPenaltyAm());
		gAc.setPendingPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setSubjClearingCashInHandAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setLastUpdatedTs(new Date(System.currentTimeMillis()));
		
		if(newObject) {
			gAc.setPenShgOneMemRegFee(DataUtil.ZERO_BIG_DECIMAL);		
			gAc.setPenShgOneServiceCharges(DataUtil.ZERO_BIG_DECIMAL);
		}

		if(newObject) {
			daoFactory.getGAcDAO().persist(gAc);
		} else {
			daoFactory.getGAcDAO().merge(gAc);
		}
	}
	
	protected void addNewGroupAccount(GProfile gProfile) {
		
		GAc gAc = new GAc();
		gAc.setGProfile(gProfile);
		
		gAc.setCreditRating(DataUtil.ZERO_FLOAT);
		gAc.setApprovalRating(DataUtil.ZERO_FLOAT);
		gAc.setMeetingAttendance(DataUtil.ZERO_FLOAT);
		
		gAc.setCMPlannedMonthlySaving(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMOutstandingSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMReturnedSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMReturnedIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMProvIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMProfitShareDeclaredAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMProfitSharePaidAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMNoOfLoans(DataUtil.ZERO_INTEGER);
		gAc.setCMNoOfActiveLoans(DataUtil.ZERO_INTEGER);
		gAc.setCMRecLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMRecIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMProjIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMSubStdDeptAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMNoOfSubStdDept(DataUtil.ZERO_INTEGER);
		gAc.setCMBadDeptExpAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMNoOfBadDeptExp(DataUtil.ZERO_INTEGER);
		gAc.setCMBadDeptClosedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setCMNoOfBadDeptClosed(DataUtil.ZERO_INTEGER);
		
		gAc.setAMPlannedMonthlySaving(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMOutstandingSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMProvIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMDividedDeclaredAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMDividedPaidAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMReturnedSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMReturnedIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMNoOfLoans(DataUtil.ZERO_INTEGER);
		gAc.setAMNoOfActiveLoans(DataUtil.ZERO_INTEGER);
		gAc.setAMRecLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMRecIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMProjIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMSubStdDeptAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMNoOfSubStdDept(DataUtil.ZERO_INTEGER);
		gAc.setAMBadDeptExpAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMNoOfBadDeptExp(DataUtil.ZERO_INTEGER);
		gAc.setAMBadDeptClosedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setAMNoOfBadDeptClosed(DataUtil.ZERO_INTEGER);
		
		gAc.setPLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setPRecLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setPRecIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setPProjIntOnLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNoOfProject(DataUtil.ZERO_INTEGER);
		gAc.setNoOfActiveProject(DataUtil.ZERO_INTEGER);
		
		gAc.setFixDepositInvAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setRecFixDepositAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setRecIntOnFixDepositAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setProjIntOnFixDepositAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNoOfFixDeposit(DataUtil.ZERO_INTEGER);
		gAc.setNoOfActiveFixDeposit(DataUtil.ZERO_INTEGER);
		
		gAc.setOtherInvAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setRecOtherInvAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setRecIntOnOtherInvAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setProjIntOnOtherInvAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNoOfOtherInv(DataUtil.ZERO_INTEGER);
		gAc.setNoOfActiveOtherInv(DataUtil.ZERO_INTEGER);
		
		gAc.setBorrowedLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setPaidBorrowedLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setPaidIntOnBorrowedLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setProjIntOnBorrowedLoanAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setBankSubStdDeptAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setBankNoOfSubStdDept(DataUtil.ZERO_INTEGER);
		gAc.setBankBadDeptExpAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setBankNoOfBadDeptExp(DataUtil.ZERO_INTEGER);
		gAc.setBankBadDeptClosedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setBankNoOfBadDeptClosed(DataUtil.ZERO_INTEGER);
		gAc.setNoOfBankLoan(DataUtil.ZERO_INTEGER);
		gAc.setNoOfActiveProject(DataUtil.ZERO_INTEGER);

		gAc.setGovRevolvingFundAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setGovRevolvingFundReturnedAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNoOfGovRevolvingFund(DataUtil.ZERO_INTEGER);
		gAc.setNoOfActiveGovRevolvingFund(DataUtil.ZERO_INTEGER);
		gAc.setGovDevelopmentFundAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNoOfGovDevelopmentFund(DataUtil.ZERO_INTEGER);

		gAc.setIntEnOnSavingAcAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setClearBankBalanceAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setSubjClearingBankBalanceAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setClearCashInHandAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNetProfitAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setNetProfitProjAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setExpensesAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setOutstandingExpensesAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setRecPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setPendingPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setSubjClearingCashInHandAm(DataUtil.ZERO_BIG_DECIMAL);
		gAc.setLastUpdatedTs(new Date(System.currentTimeMillis()));
		gAc.setPenShgOneMemRegFee(DataUtil.ZERO_BIG_DECIMAL);		
		gAc.setPenShgOneServiceCharges(DataUtil.ZERO_BIG_DECIMAL);		

		gAc.setNoOfTxsMonthlyExp(DataUtil.ZERO_INTEGER);
		gAc.setNoOfTxsMonthlyDone(DataUtil.ZERO_INTEGER);
		gAc.setNoOfTxsMonthlyApproved(DataUtil.ZERO_INTEGER);

		daoFactory.getGAcDAO().persist(gAc);
	}
	
	protected void addOldGroupRules(GProfile gProfile, GroupRules groupRules) {
		
		if(groupRules == null) {
			return;
		}
		
		boolean newObject = false;
		GRules gRules = daoFactory.getGRulesDAO().findById(gProfile.getGAcNo());
		
		if(gRules == null) {
			gRules = new GRules();
			gRules.setGProfile(gProfile);
			newObject = true;
		}

		GroupRules.loadGRules(groupRules, gRules);
		
		if(newObject) {
			daoFactory.getGRulesDAO().persist(gRules);
		} else {
			daoFactory.getGRulesDAO().merge(gRules);
		}
	}
	
	protected void addNewGroupRules(GProfile gProfile, GroupRules groupRules, int associateAllowed) {
		
		GRules gRules = new GRules();
		gRules.setGProfile(gProfile);
		
		if(groupRules != null && groupRules.getMaxNoOfCoreMembers() > DataUtil.ZERO_INTEGER) {
			gRules.setMaxNoOfCoreMembers(groupRules.getMaxNoOfCoreMembers());
		} else {
			gRules.setMaxNoOfCoreMembers((short)20);
		}

		if(groupRules != null && groupRules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
			gRules.setAllowAssociateMembers(groupRules.getAllowAssociateMembers());
		} else {
			gRules.setAllowAssociateMembers((short)associateAllowed);
		}

		if(groupRules != null && groupRules.getCmMonthlySaving() > DataUtil.ZERO_INTEGER) {
			gRules.setCmMonthlySaving(groupRules.getCmMonthlySaving());
		} else {
			gRules.setCmMonthlySaving(100);
		}
		gRules.setAmMinMonthlySaving(100);
		gRules.setAmMaxMonthlySaving(100);

		if(groupRules != null && groupRules.getCmRegistrationFee() > DataUtil.ZERO_INTEGER) {
			gRules.setCmRegistrationFee(groupRules.getCmRegistrationFee());
		} else {
			gRules.setCmRegistrationFee((short)100);
		}
		
		if(groupRules != null && groupRules.getAmRegistrationFee() > DataUtil.ZERO_INTEGER) {
			gRules.setAmRegistrationFee(groupRules.getAmRegistrationFee());
		} else {
			gRules.setAmRegistrationFee((short)100);
		}

		if(groupRules != null && groupRules.getCmSavingLateFee() > DataUtil.ZERO_INTEGER) {
			gRules.setCmSavingLateFee(groupRules.getCmSavingLateFee());
		} else {
			gRules.setCmSavingLateFee((short)20);
		}

		if(groupRules != null && groupRules.getAmSavingLateFee() > DataUtil.ZERO_INTEGER) {
			gRules.setAmSavingLateFee(groupRules.getAmSavingLateFee());
		} else {
			gRules.setAmSavingLateFee((short)20);
		}

		if(groupRules != null && groupRules.getCmLoanLateFee() > DataUtil.ZERO_INTEGER) {
			gRules.setCmLoanLateFee(groupRules.getCmLoanLateFee());
		} else {
			gRules.setCmLoanLateFee((short)20);
		}

		if(groupRules != null && groupRules.getAmLoanLateFee() > DataUtil.ZERO_INTEGER) {
			gRules.setAmLoanLateFee(groupRules.getAmLoanLateFee());
		} else {
			gRules.setAmLoanLateFee((short)20);
		}

		if(groupRules != null && groupRules.getChequeBouncingPenalty() > DataUtil.ZERO_INTEGER) {
			gRules.setChequeBouncingPenalty(groupRules.getChequeBouncingPenalty());
		} else {
			gRules.setChequeBouncingPenalty((short)500);
		}

		if(groupRules != null && groupRules.getAutoDebitFailurePenalty() > DataUtil.ZERO_INTEGER) {
			gRules.setAutoDebitFailurePenalty(groupRules.getAutoDebitFailurePenalty());
		} else {
			gRules.setAutoDebitFailurePenalty((short)500);
		}

		if(groupRules != null && groupRules.getCmApplicationFee() > DataUtil.ZERO_INTEGER) {
			gRules.setCmApplicationFee(groupRules.getCmApplicationFee());
		} else {
			gRules.setCmApplicationFee((short)0);
		}

		if(groupRules != null && groupRules.getAmApplicationFee() > DataUtil.ZERO_INTEGER) {
			gRules.setAmApplicationFee(groupRules.getAmApplicationFee());
		} else {
			gRules.setAmApplicationFee((short)0);
		}

		if(groupRules != null && groupRules.getCmLoanProcessingFeePercent() > DataUtil.ZERO_FLOAT) {
			gRules.setCmLoanProcessingFeePercent(groupRules.getCmLoanProcessingFeePercent());
		} else {
			gRules.setCmLoanProcessingFeePercent(1.0f);
		}
		
		if(groupRules != null && groupRules.getAmLoanProcessingFeePercent() > DataUtil.ZERO_FLOAT) {
			gRules.setAmLoanProcessingFeePercent(groupRules.getAmLoanProcessingFeePercent());
		} else {
			gRules.setAmLoanProcessingFeePercent(1.0f);
		}
		
		if(groupRules != null && groupRules.getCmIntOnSaving() > DataUtil.ZERO_FLOAT) {
			gRules.setCmIntOnSaving(groupRules.getCmIntOnSaving());
		} else {
			gRules.setCmIntOnSaving(12.0f);
		}
		
		if(groupRules != null && groupRules.getAmIntOnSaving() > DataUtil.ZERO_FLOAT) {
			gRules.setAmIntOnSaving(groupRules.getAmIntOnSaving());
		} else {
			gRules.setAmIntOnSaving(12.0f);
		}
		
		if(groupRules != null && groupRules.getCmBaseIntOnLoan() > DataUtil.ZERO_FLOAT) {
			gRules.setCmBaseIntOnLoan(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnConsumptionLoan(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnIndividualDevLoanRange1(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnIndividualDevLoanRange2(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnIndividualDevLoanRange3(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnIndividualDevLoanRange4(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnProjectDevLoanRange1(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnProjectDevLoanRange2(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnProjectDevLoanRange3(groupRules.getCmBaseIntOnLoan());
			gRules.setIntOnProjectDevLoanRange4(groupRules.getCmBaseIntOnLoan());
		} else {
			gRules.setCmBaseIntOnLoan(24.0f);
			gRules.setIntOnConsumptionLoan(24.0f);
			gRules.setIntOnIndividualDevLoanRange1(24.0f);
			gRules.setIntOnIndividualDevLoanRange2(24.0f);
			gRules.setIntOnIndividualDevLoanRange3(24.0f);
			gRules.setIntOnIndividualDevLoanRange4(24.0f);
			gRules.setIntOnProjectDevLoanRange1(24.0f);
			gRules.setIntOnProjectDevLoanRange2(24.0f);
			gRules.setIntOnProjectDevLoanRange3(24.0f);
			gRules.setIntOnProjectDevLoanRange4(24.0f);
		}
		
		if(groupRules != null && groupRules.getAmBaseIntOnLoan() > DataUtil.ZERO_FLOAT) {
			gRules.setAmBaseIntOnLoan(groupRules.getAmBaseIntOnLoan());
		} else {
			gRules.setAmBaseIntOnLoan(24.0f);
		}
		
		gRules.setAddIntToSavingAfterMonths((short)12);
		gRules.setIndividualDevLoanRange1(5000);
		gRules.setIndividualDevLoanRange2(20000);
		gRules.setIndividualDevLoanRange3(50000);
		gRules.setProjectDevLoanRange1(10000);
		gRules.setProjectDevLoanRange2(50000);
		gRules.setProjectDevLoanRange3(100000);
		gRules.setCmSavingXConsumptionLoan(5.0f);
		gRules.setAmSavingXConsumptionLoan(5.0f);
		gRules.setCmMinMonthsToConLoan(3);
		gRules.setAmMinMonthsToConLoan(3);
		gRules.setCmLimitOnConsumptionLoan(10000);
		gRules.setAmLimitOnConsumptionLoan(10000);
		gRules.setCmSavingXDevLoan(10.0f);
		gRules.setAmSavingXDevLoan(10.0f);
		gRules.setCmMinMonthsToDevLoan(6);
		gRules.setAmMinMonthsToDevLoan(6);
		gRules.setCmMinMonthsIntOnLoan(3);
		gRules.setAmMinMonthsIntOnLoan(3);
		gRules.setCreditRatingLoanPassAbove(95.0f);
		gRules.setCreditRatingLoanCausionAbove(85.0f);
		gRules.setCreditRatingLoanDengerousAbove(70.0f);
		gRules.setComputeDayOfMonth(1);
		gRules.setDueDayOfMonth(10);
		gRules.setSmsSubKey(0);
		gRules.setMailSubKey(0);
		gRules.setReportPrintingService(1);
		gRules.setSmsServiceLang(EnumCache.getIndexOfEnumValue(EnumConst.Lang, EnumConst.Lang_English));
		gRules.setMailServiceLang(EnumCache.getIndexOfEnumValue(EnumConst.Lang, EnumConst.Lang_English));
		gRules.setReportPrintingServiceLang(EnumCache.getIndexOfEnumValue(EnumConst.Lang, EnumConst.Lang_English));
		gRules.setActivationFlags(DataUtil.ZERO_LONG);
		gRules.setShgOneCMRegCharge((short)50);
		gRules.setShgOneAMRegCharge((short)50);
		gRules.setShgOneCMLoanProFeePercent(0.6f);
		gRules.setShgOneAMLoanProFeePercent(0.6f);
		gRules.setShgOneMinAnnualServiceCharge((short)1200);
		gRules.setShgOneMaxLoanProFeeOff((short)3000);
		gRules.setShgOneBillingCycleInMonths(3);

		daoFactory.getGRulesDAO().persist(gRules);
	}
	
	protected void addOldAcByTxtype(GProfile gProfile, List<GAcByTxtypeREST> gAcByTxtypes) {
		GAcByTxtype ac = null;
		int txTypeId = 0;

		if(gAcByTxtypes != null && !gAcByTxtypes.isEmpty()) {
			for(GAcByTxtypeREST gAcByTxtyp: gAcByTxtypes) {
				GAcByTxtype object = daoFactory.getGAcByTxtypeDAO().findById(gProfile.getGAcNo(), txTypeId);

				if(object == null) {

					ac = new GAcByTxtype();
					txTypeId = EnumCache.getIndexOfTxType(gAcByTxtyp.getTxType());
					ac.setId(new GAcByTxtypeId(gProfile.getGAcNo(), txTypeId));
					ac.setGroupAcNo(gProfile.getGAcNo());
					ac.setTxTypeId(txTypeId);
					ac.setAmount(gAcByTxtyp.getAmount());

					daoFactory.getGAcByTxtypeDAO().persist(ac);
				} else {

					object.setAmount(gAcByTxtyp.getAmount());
					daoFactory.getGAcByTxtypeDAO().merge(object);
				}
			}
		}
	}

	protected void addAcByTxtype(GProfile gProfile) {
		TxTypeEnum txTypeEnum = EnumCache.getTxType();
		GAcByTxtype ac = null;
		
		for(TxTypeValue value: txTypeEnum.getEnumValues()) {
			ac = new GAcByTxtype();
			ac.setAmount(DataUtil.ZERO_BIG_DECIMAL);
			ac.setId(new GAcByTxtypeId(gProfile.getGAcNo(), value.getTxTypeId()));
			ac.setGroupAcNo(gProfile.getGAcNo());
			ac.setTxTypeId(value.getTxTypeId());
			
			daoFactory.getGAcByTxtypeDAO().persist(ac);
		}
	}
	
	public void updateGroupAcByTxtype(long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No:" + groupAcNo);
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No:" + groupAcNo);
		}

		List<GAcByTxtype> gAcByTxtypes = daoFactory.getGAcByTxtypeDAO().getAllForGroup(groupAcNo);

		Set<Integer> gAcSet = new HashSet<Integer>();
		for(GAcByTxtype ac: gAcByTxtypes) {
			gAcSet.add(ac.getTxTypeId());
		}

		TxTypeEnum txTypeEnum = EnumCache.getTxType();
		GAcByTxtype ac = null;

		for(TxTypeValue value: txTypeEnum.getEnumValues()) {
			if(!gAcSet.contains(value.getTxTypeId())) {
				
				ac = new GAcByTxtype();
				ac.setAmount(DataUtil.ZERO_BIG_DECIMAL);
				ac.setId(new GAcByTxtypeId(gProfile.getGAcNo(), value.getTxTypeId()));
				ac.setGroupAcNo(gProfile.getGAcNo());
				ac.setTxTypeId(value.getTxTypeId());

				daoFactory.getGAcByTxtypeDAO().persist(ac);
			}
		}
	}
	
	public void updateAcByTxtype(long groupAcNo, List<PenaltyDetail> penaltyDetails) {

		for(PenaltyDetail penaltyDetail: penaltyDetails) {
			
			GAcByTxtype gAcByTxtype = daoFactory.getGAcByTxtypeDAO().findById(groupAcNo, EnumCache.getIndexOfTxType(penaltyDetail.getTxType()));
		
			gAcByTxtype.setAmount(gAcByTxtype.getAmount().add(penaltyDetail.getAmount()));
			
			daoFactory.getGAcByTxtypeDAO().merge(gAcByTxtype);
		}
	}
	
	public GroupREST approveReject(GroupREST group) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}

		if(group.getApprovalStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}
		int approvalStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, group.getApprovalStatus());
		if(approvalStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Active Status:" + group.getApprovalStatus());
		}		
		if(!EnumUtil.isApprovalStatusApprovable(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()))) {
			throw new BadRequestException("Can't Update Current Member Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()));
		}

		gProfile.setApprovalStatusId(approvalStatusId);
		gProfile.setApprovedByMember(group.getApprovedByMember());

		if(group.getDateOfEstablishment() != null && !group.getDateOfEstablishment().isEmpty()) {
			gProfile.setEstablishmentDate(DateUtil.convertStringToDate(group.getDateOfEstablishment()));
		}
		if(group.getLogoUrl() != null && !group.getLogoUrl().isEmpty()) {
			gProfile.setLogoUrl(group.getLogoUrl());
		}
		if(group.getStatusMessage() != null && !group.getStatusMessage().isEmpty()) {
			gProfile.setDescription(group.getStatusMessage());
		}

		daoFactory.getGProfileDAO().merge(gProfile);

		return group;
	}
	
	public GroupREST activate(GroupREST group) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		if(!EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()).equals(EnumConst.ApprovalStatus_Approved)) {
			throw new BadRequestException("Member status Not 'Approved' it is : " + 
					EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()));
		}

		gProfile.setActiveStatusId(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Active));
		group.setActiveStatus(EnumConst.ActiveStatus_Active);

		if(group.getDateOfEstablishment() != null && !group.getDateOfEstablishment().isEmpty()) {
			gProfile.setEstablishmentDate(DateUtil.convertStringToDate(group.getDateOfEstablishment()));
		}
		if(group.getLogoUrl() != null && !group.getLogoUrl().isEmpty()) {
			gProfile.setLogoUrl(group.getLogoUrl());
		}
		if(group.getStatusMessage() != null && !group.getStatusMessage().isEmpty()) {
			gProfile.setDescription(group.getStatusMessage());
		}

		daoFactory.getGProfileDAO().merge(gProfile);

		return group;
	}
	
	public GroupREST updateGroupProfile(GroupREST group) throws BadRequestException {

		group.setDistrictId((int)ConversionUtil.getDistrictFromGroupAc(group.getGroupAcNo()));
		group.setDistrictCode(EnumCache.getDistrictCodeForGroup(group.getGroupAcNo()));
		
		updateGroup(group);
		
		if(group.getContacts() != null && !group.getContacts().isEmpty()) {
			updateGroupContacts(group);
		}
		
		if(group.getBankAccounts() != null && !group.getBankAccounts().isEmpty()) {
			updateGroupBankAccounts(group);
		}
		
		if(group.getGroupType().equals(EnumConst.GroupType_Bank) && 
				group.getBankProfile() != null) {
			updateBankProfile(group);
		}

		return group;
	}
	
	public GroupREST updateGroup(GroupREST group) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}

		if(group.getDateOfEstablishment() != null && !group.getDateOfEstablishment().isEmpty()) {
			gProfile.setEstablishmentDate(DateUtil.convertStringToDate(group.getDateOfEstablishment()));
		}
		if(group.getLogoUrl() != null && !group.getLogoUrl().isEmpty()) {
			gProfile.setLogoUrl(group.getLogoUrl());
		}
		if(group.getStatusMessage() != null && !group.getStatusMessage().isEmpty()) {
			gProfile.setDescription(group.getStatusMessage());
		}

		daoFactory.getGProfileDAO().merge(gProfile);

		return group;
	}
	
	public GroupREST updateGroupStatus(GroupREST group) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		if(!EnumUtil.isStatusLive(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()))) {
			throw new BadRequestException("Group Not Alive for Status Update!");
		}

		if(group.getActiveStatus() == null || group.getActiveStatus().isEmpty()) {
			throw new BadRequestException("Invalid Given Status!");
		}
		int activeStatusId = EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, group.getActiveStatus());
		if(activeStatusId < 0) {
			throw new BadRequestException("Invalid Given Status : " + group.getActiveStatus() + "!");
		}
		if(!EnumUtil.isStatusUpdatable(group.getActiveStatus())) {
			throw new BadRequestException("Invalid Given Status : " + group.getActiveStatus() + "!");
		}

		gProfile.setActiveStatusId(activeStatusId);
		
		daoFactory.getGProfileDAO().merge(gProfile);

		return group;
	}
	
	public GroupREST updateGroupAc(GroupREST group) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}
		if(group.getGroupAc() == null) {
			throw new BadRequestException("Invalid Group Account Data!");
		}
		GAc gAc = daoFactory.getGAcDAO().findById(group.getGroupAcNo());
		if(gAc == null) {
			throw new BadRequestException("Invalid Group Account No: " + group.getGroupAcNo());
		}

		GroupREST.updateGAc(group.getGroupAc(), gAc);

		daoFactory.getGAcDAO().merge(gAc);

		return group;
	}

	public GroupREST addGroupContacts(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().getReferenceById(group.getGroupAcNo());

		for(GroupContactREST contactREST : group.getContacts()) {

			GroupContact mContact = addGroupContact(contactREST);
			mContact.setGProfile(gProfile);
			daoFactory.getGroupContactDAO().persist(mContact);
			
			contactREST.setContactId(mContact.getContactId());
		}

		return group;
	}

	protected GroupContact addGroupContact(GroupContactREST groupContact) throws BadRequestException {
		
		if(groupContact.getDistrict() == null || groupContact.getDistrict().isEmpty()) {
			throw new BadRequestException("Invalid District");
		}
		if(groupContact.getLang() == null || groupContact.getLang().isEmpty()) {
			throw new BadRequestException("Invalid Contact Langauge");
		}

		// Create & Save Contact details
		Contact contact = new Contact();

		contact.setDistrict(daoFactory.getDistrictDAO().getDistrictFromName(groupContact.getState(), groupContact.getDistrict()));
		if(groupContact.getLang().equals(EnumConst.Lang_English)) {
			contact.setAddress(WordUtils.capitalizeFully(groupContact.getAddress()));
			contact.setVillage(WordUtils.capitalizeFully(groupContact.getVillage()));
			contact.setGrampanchayat(WordUtils.capitalizeFully(groupContact.getGrampanchayat()));
			contact.setTaluka(WordUtils.capitalizeFully(groupContact.getTaluka()));
			
			// Just push back any updates done by WordUtils.capitalizeFully
			groupContact.setAddress(contact.getAddress());
			groupContact.setVillage(contact.getVillage());
			groupContact.setGrampanchayat(contact.getGrampanchayat());
			groupContact.setTaluka(contact.getTaluka());
		} else {
			contact.setAddress(groupContact.getAddress());
			contact.setVillage(groupContact.getVillage());
			contact.setGrampanchayat(groupContact.getGrampanchayat());
			contact.setTaluka(groupContact.getTaluka());
		}
		contact.setPinCode(groupContact.getPinCode());
		contact.setPhone(groupContact.getPhone());
		contact.setPriMobile(groupContact.getPriMobile());
		contact.setSecMobile(groupContact.getSecMobile());
		contact.setEmail(groupContact.getEmail());

		daoFactory.getContactDAO().persist(contact);

		// Created & Save Group Contact details
		GroupContact mContact = new GroupContact();

		mContact.setContact(contact);
		mContact.setLang(daoFactory.getLangDAO().getReferenceByValue(groupContact.getLang()));
		if(groupContact.getLang().equals(EnumConst.Lang_English)) {
			mContact.setName(WordUtils.capitalize(groupContact.getName()));
			
			// Just push back any updates done by WordUtils.capitalizeFully
			groupContact.setName(mContact.getName());
		} else {
			mContact.setName(groupContact.getName());
		}
		mContact.setVision(groupContact.getVision());
		mContact.setDescription(groupContact.getDescription());
		// Load Data Back for return 
		groupContact.setContactId(contact.getContactId());
		
		return mContact;
	}

	public GroupREST updateGroupContacts(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());

		Set<GroupContact> contacts = gProfile.getGroupContacts();
		Set<Long> contactIds = new HashSet<Long>();

		for(GroupContact contact : contacts) {
			contactIds.add(contact.getContactId());
		}

		for(GroupContactREST contactREST : group.getContacts()) {

			if(contactIds.contains(contactREST.getContactId())) {

				GroupContact mContact = updateGroupContact(contactREST);
				daoFactory.getGroupContactDAO().merge(mContact);
				contacts.add(mContact);
			}
		}

		return group;
	}

	protected GroupContact updateGroupContact(GroupContactREST groupContact) throws BadRequestException {

		if(groupContact.getContactId() <= 0) {
			throw new BadRequestException("Invalid Group Contact ID");
		}

		// Load Group Contact details
		GroupContact mContact = daoFactory.getGroupContactDAO().findById(groupContact.getContactId());

		// Load Contact details
		Contact contact = mContact.getContact();

		if(groupContact.getLang().equals(EnumConst.Lang_English)) {
			contact.setAddress(WordUtils.capitalizeFully(groupContact.getAddress()));
			contact.setVillage(WordUtils.capitalizeFully(groupContact.getVillage()));
			contact.setGrampanchayat(WordUtils.capitalizeFully(groupContact.getGrampanchayat()));
			contact.setTaluka(WordUtils.capitalizeFully(groupContact.getTaluka()));
			
			// Just push back any updates done by WordUtils.capitalizeFully
			groupContact.setAddress(contact.getAddress());
			groupContact.setVillage(contact.getVillage());
			groupContact.setGrampanchayat(contact.getGrampanchayat());
			groupContact.setTaluka(contact.getTaluka());
		} else {
			contact.setAddress(groupContact.getAddress());
			contact.setVillage(groupContact.getVillage());
			contact.setGrampanchayat(groupContact.getGrampanchayat());
			contact.setTaluka(groupContact.getTaluka());
		}
		contact.setPinCode(groupContact.getPinCode());
		contact.setPhone(groupContact.getPhone());
		contact.setPriMobile(groupContact.getPriMobile());
		contact.setSecMobile(groupContact.getSecMobile());
		contact.setEmail(groupContact.getEmail());

		daoFactory.getContactDAO().merge(contact);

		mContact.setContact(contact);
		mContact.setLang(daoFactory.getLangDAO().getReferenceByValue(groupContact.getLang()));
		if(groupContact.getLang().equals(EnumConst.Lang_English)) {
			mContact.setName(WordUtils.capitalize(groupContact.getName()));
			
			// Just push back any updates done by WordUtils.capitalizeFully
			groupContact.setName(mContact.getName());
		} else {
			mContact.setName(groupContact.getName());
		}
		mContact.setVision(groupContact.getVision());
		mContact.setDescription(groupContact.getDescription());

		return mContact;
	}

	public void removeGroupContacts(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());

		for(GroupContact contact : gProfile.getGroupContacts()) {
			daoFactory.getGroupContactDAO().remove(contact);
		}
	}

	public GroupREST addGroupBankAccounts(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		if(group.getBankAccounts() != null) {
			GProfile gProfile = daoFactory.getGProfileDAO().getReferenceById(group.getGroupAcNo());

			for(BankAccountREST bankAccontREST : group.getBankAccounts()) {
				GBankAccount gBankAccount = addGroupBankAccount(bankAccontREST);
				gBankAccount.setGProfile(gProfile);
				gBankAccount.setSubjClearingBalanceAm(DataUtil.ZERO_BIG_DECIMAL);

				if(group.getGroupAc() != null && group.getGroupAc().getBankBalanceAm() != null) {
					gBankAccount.setClearBalanceAm(group.getGroupAc().getBankBalanceAm());
				} else {
					gBankAccount.setClearBalanceAm(DataUtil.ZERO_BIG_DECIMAL);
				}

				daoFactory.getGBankAccountDAO().persist(gBankAccount);

				bankAccontREST.setBankAccountId(gBankAccount.getBankAccountNo());
			}
		}
		return group;
	}

	protected GBankAccount addGroupBankAccount(BankAccountREST groupBankAccount) throws BadRequestException {

		if(groupBankAccount.getBankProfileId() <= 0 && 
				(groupBankAccount.getIfcsCode() == null || groupBankAccount.getIfcsCode().isEmpty() ||
				groupBankAccount.getBankBranchName() == null || groupBankAccount.getBankBranchName().isEmpty())) {			
			throw new BadRequestException("Invalid Bank Profile Id & Invalid IFCS Code");
		}
		
		BankProfile bankProfile = null;
		if(groupBankAccount.getBankProfileId() > 0) {
			bankProfile = daoFactory.getBankProfileDAO().getReferenceById(groupBankAccount.getBankProfileId());
		} else {
			bankProfile = daoFactory.getBankProfileDAO().getBankForIFCSCode(groupBankAccount.getIfcsCode(), groupBankAccount.getBankBranchName());
		}
		
		if(bankProfile == null) {
			throw new BadRequestException("Invalid Bank Profile Id : " + groupBankAccount.getBankProfileId());
		}
		
		// Create & Save Banks Account details
		BankAccount bankAccount = new BankAccount();

		bankAccount.setAccountNumber(groupBankAccount.getAccountNumber());
		bankAccount.setAccountName(groupBankAccount.getAccountName());
		bankAccount.setBankAccountTypeId(EnumCache.getIndexOfEnumValue(EnumConst.BankAccountType, groupBankAccount.getBankAccountType()));
		bankAccount.setBankProfile(bankProfile);

		daoFactory.getBankAccountDAO().persist(bankAccount);

		// Created & Save Group Bank Account details
		GBankAccount gBankAccount = new GBankAccount();

		gBankAccount.setBankAccount(bankAccount);
		
		//Load Bank Account Id back
		groupBankAccount.setBankAccountId(bankAccount.getBankAccountNo());

		return gBankAccount;
	}

	protected GBankAccountREST addGroupBankAccount(long groupAcNo, GBankAccountREST groupBankAccount) throws BadRequestException {

		if(groupBankAccount.getBankProfileId() <= 0 && 
				(groupBankAccount.getIfcsCode() == null || groupBankAccount.getIfcsCode().isEmpty() ||
				groupBankAccount.getBankBranchName() == null || groupBankAccount.getBankBranchName().isEmpty())) {			
			throw new BadRequestException("Invalid Bank Profile Id & Invalid IFCS Code");
		}
		
		BankProfile bankProfile = null;
		if(groupBankAccount.getBankProfileId() > 0) {
			bankProfile = daoFactory.getBankProfileDAO().getReferenceById(groupBankAccount.getBankProfileId());
		} else {
			bankProfile = daoFactory.getBankProfileDAO().getBankForIFCSCode(groupBankAccount.getIfcsCode(), groupBankAccount.getBankBranchName());
		}
		
		if(bankProfile == null) {
			throw new BadRequestException("Invalid Bank Profile Id : " + groupBankAccount.getBankProfileId());
		}
		
		// Create & Save Banks Account details
		BankAccount bankAccount = new BankAccount();

		bankAccount.setAccountNumber(groupBankAccount.getAccountNumber());
		bankAccount.setAccountName(WordUtils.capitalize(groupBankAccount.getAccountName()));
		bankAccount.setBankAccountTypeId(EnumCache.getIndexOfEnumValue(EnumConst.BankAccountType, groupBankAccount.getBankAccountType()));
		bankAccount.setBankProfile(bankProfile);

		daoFactory.getBankAccountDAO().persist(bankAccount);

		groupBankAccount.setAccountName(bankAccount.getAccountName());
		
		// Created & Save Group Bank Account details
		GBankAccount gBankAccount = new GBankAccount();

		gBankAccount.setBankAccount(bankAccount);
		gBankAccount.setClearBalanceAm(groupBankAccount.getClearBalanceAm());
		gBankAccount.setGProfile(daoFactory.getGProfileDAO().getReferenceById(groupAcNo));
		
		daoFactory.getGBankAccountDAO().persist(gBankAccount);
		
		//Load Bank Account Id back
		groupBankAccount.setBankAccountId(bankAccount.getBankAccountNo());

		return groupBankAccount;
	}

	public GroupREST updateGroupBankAccounts(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());

		if(gProfile.getGBankAccounts() != null) {
			Set<Long> bankAccountIds = new HashSet<Long>();

			for(GBankAccount gBankAccount : gProfile.getGBankAccounts()) {
				bankAccountIds.add(gBankAccount.getBankAccountNo());
			}

			for(BankAccountREST bankAccountREST : group.getBankAccounts()) {

				if(bankAccountIds.contains(bankAccountREST.getBankAccountId())) {

					GBankAccount gBankAccount = updateGroupBankAccount(bankAccountREST);
					daoFactory.getGBankAccountDAO().merge(gBankAccount);
					gProfile.getGBankAccounts().add(gBankAccount);
				}
			}
		}

		return group;
	}

	public GroupREST updateBankProfile(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}
		if(group.getBankProfile() == null || 
				group.getBankProfile().getBankName() == null ||
				group.getBankProfile().getBankName().isEmpty()) {
			throw new BadRequestException("Null Or Empty Bank Profile");
		}

		BankProfile bProfile = daoFactory.getBankProfileDAO().findById(group.getGroupAcNo());

		BankProfileREST.updateBankProfile(group.getBankProfile(), bProfile);
		
		daoFactory.getBankProfileDAO().merge(bProfile);
		
		return group;
	}

	protected GBankAccount updateGroupBankAccount(BankAccountREST groupBankAccount) throws BadRequestException {

		if(groupBankAccount.getBankAccountId() <= 0) {
			throw new BadRequestException("Invalid Group Bank Account ID");
		}

		// Load Group Contact details
		GBankAccount gBankAccount = daoFactory.getGBankAccountDAO().findById(groupBankAccount.getBankAccountId());

		// Create & Save Banks Account details
		BankAccount bankAccount = gBankAccount.getBankAccount();

		bankAccount.setAccountNumber(groupBankAccount.getAccountNumber());
		bankAccount.setAccountName(WordUtils.capitalize(groupBankAccount.getAccountName()));
		bankAccount.setBankAccountTypeId(EnumCache.getIndexOfEnumValue(EnumConst.BankAccountType, groupBankAccount.getBankAccountType()));

		daoFactory.getBankAccountDAO().merge(bankAccount);

		groupBankAccount.setAccountName(bankAccount.getAccountName());

		gBankAccount.setBankAccount(bankAccount);

		return gBankAccount;
	}

	public void removeGroupBankAccounts(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());

		for(GBankAccount gBankAccount : gProfile.getGBankAccounts()) {
			daoFactory.getGBankAccountDAO().remove(gBankAccount);
		}
	}

	public void mapGroupToMembers(GroupREST group) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(group.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}
		if(group.getMappingMemberAcNos() == null || group.getMappingMemberAcNos().isEmpty()) {
			throw new BadRequestException("Invalid Members Accounts No");
		}

		GProfile gProfile = daoFactory.getGProfileDAO().findById(group.getGroupAcNo());
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account");
		}

		// Get References of all Mapping Groups
		for(Long memberAcNo : group.getMappingMemberAcNos()) {
			
			MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
			if(mProfile == null) {
				throw new BadRequestException("Invalid Member Account");
			}

			mapGroupToMember(mProfile, gProfile);
		}

	}

	protected void mapGroupToMembersInternal(GProfile gProfile, List<Long> memberAcNos) {

		// Get References of all Mapping Groups
		for(Long memberAcNo : memberAcNos) {
			if(!ConversionUtil.isAdminMemberAcNo(memberAcNo)) {
				MProfile mProfile = daoFactory.getMProfileDAO().findById(memberAcNo);
				mapGroupToMember(mProfile, gProfile);
			}
		}
	}
	
	protected void mapGroupToMember(MProfile mProfile, GProfile gProfile) {

		GMId groupMemberId = new GMId(gProfile.getGAcNo(), mProfile.getMemberAcNo());
		GM groupMember = new GM(groupMemberId, gProfile.getGAcNo(), mProfile.getMemberAcNo());
		daoFactory.getGMDAO().persist(groupMember);		
	}

	public void mapGroupToGroup(long topGroupAcNo, long downGroupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(topGroupAcNo)) {
			throw new BadRequestException("Invalid Top Group Account No : " + topGroupAcNo);
		}
		if(!ConversionUtil.isValidGroupAcNo(downGroupAcNo)) {
			throw new BadRequestException("Invalid Down Group Account No : " + downGroupAcNo);
		}
		if(ConversionUtil.isAdminGroupAcNo(topGroupAcNo)) {
			throw new BadRequestException("Top Group is Admin and Mapping for Admin Group is Not Required!");
		}
		if(ConversionUtil.isAdminGroupAcNo(downGroupAcNo)) {
			throw new BadRequestException("Down Group is Admin and Mapping to Admin Group is Not Allowed!");
		}

		GProfile topGProfile = daoFactory.getGProfileDAO().findById(topGroupAcNo);
		if(topGProfile == null) {
			throw new BadRequestException("Invalid Top Group Account No : " + topGroupAcNo);
		}

		GProfile downGProfile = daoFactory.getGProfileDAO().findById(downGroupAcNo);
		if(downGProfile == null) {
			throw new BadRequestException("Invalid Down Group Account No : " + downGroupAcNo);
		}

		mapGroupToGroup(topGProfile, downGProfile);

	}
	
	protected void mapGroupToGroup(GProfile topGProfile, GProfile downGProfile) {

		GGId groupGroupId = new GGId(topGProfile.getGAcNo(), downGProfile.getGAcNo());
		GG groupGroup = new GG();
		groupGroup.setId(groupGroupId);
		groupGroup.setTopGroupAcNo(topGProfile.getGAcNo());
		groupGroup.setDownGroupAcNo(downGProfile.getGAcNo());
//		groupGroup.setGroupRelationId();
//		groupGroup.setUiAccessRights(uiAccessRights);
//		groupGroup.setWsAccessRights(wsAccessRights);
		
		daoFactory.getGGDAO().persist(groupGroup);		
	}

	public GroupREST getGroupProfile(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GroupREST group = new GroupREST();
		
		GroupREST.loadGroupProfile(gProfile, group);
		
		GroupREST.loadGroupContacts(gProfile, group);
		
		GroupREST.loadGroupBankAccounts(gProfile, group);
		
		if(group.getGroupType().equals(EnumConst.GroupType_Bank)) {
			BankProfile bProfile = daoFactory.getBankProfileDAO().findById(groupAcNo);
			BankProfileREST bankProfile = new BankProfileREST();
			BankProfileREST.loadBankProfile(bProfile, bankProfile);
			
			group.setBankProfile(bankProfile);
		}
		
		group.setBankAccountDisplay(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
		
		return group;
	}

	public GroupREST getGroupAc(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GAc gAc = daoFactory.getGAcDAO().findById(groupAcNo);
		GProfile gProfile = null;
		List<MonthlyGAc> monthlyGAcs = null;
		
		if(gAc == null) {
			gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		} else {
			gProfile = gAc.getGProfile();
			monthlyGAcs = daoFactory.getMonthlyGAcDAO().getAllForGroup(groupAcNo);
		}
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GroupREST group = new GroupREST();
		
		GroupREST.loadGroupProfile(gProfile, group);
		
		GroupREST.loadGroupContacts(gProfile, group);
		
		GroupREST.loadGroupBankAccounts(gProfile, group);
		
		if(gAc != null) {
			GroupREST.loadGroupAc(gAc, group);
		}

		{
			List<String> monthsAvaliable = new ArrayList<String>();
			if(gAc != null) {
				GroupREST.loadMonthsAvailable(monthlyGAcs, monthsAvaliable);
			}
			group.setMonthsAvaliable(monthsAvaliable);
		}

		group.setBankAccountDisplay(daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
		
		return group;
	}

	public GroupREST getGroupBankAccounts(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GroupREST group = new GroupREST();
		
		GroupREST.loadGroupProfile(gProfile, group);
		
		GroupREST.loadGroupBankAccounts(gProfile, group);
		
		return group;
	}

	public GroupREST getGroupContacts(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GroupREST group = new GroupREST();
		
		GroupREST.loadGroupProfile(gProfile, group);
		
		GroupREST.loadGroupContacts(gProfile, group);
		
		return group;
	}

	public MemberSearchInfo getGroupBankInfo(String lang, long groupAcNo) throws BadRequestException {
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		return new MemberSearchInfo(groupAcNo, daoFactory.getGBankAccountDAO().getGroupBankAccountsDisplay(groupAcNo));
	}
	
	public GroupREST getGroupAllAccounts(String lang, long groupAcNo, String status) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
		if(gProfile == null) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GAc gAc = daoFactory.getGAcDAO().findById(groupAcNo);
		GRules gRules = daoFactory.getGRulesDAO().findById(groupAcNo);
		List<GAcByTxtype> gAcByTxtypes = daoFactory.getGAcByTxtypeDAO().getAllForGroup(groupAcNo);

		GroupREST group = new GroupREST();
		
		GroupREST.loadGroupProfile(gProfile, group);
		
		GroupREST.loadGroupContacts(gProfile, group);
		
		GroupREST.loadGroupBankAccounts(gProfile, group);
		
		if(gAc != null) {
			GroupREST.loadGroupAc(gAc, group);
			Set<Long> bankProfileIds = new HashSet<Long>();
			
			group.setGroupInvtAcs(new ArrayList<GroupInvtAcREST>());
			group.setGroupLoanAcs(new ArrayList<GroupLoanAcREST>());
			
			if(gAc.getGInvtAcs() != null && !gAc.getGInvtAcs().isEmpty()) {
				List<GInvtAc> gInvtAcsList = new ArrayList<GInvtAc>(gAc.getGInvtAcs());
				Collections.sort(gInvtAcsList);
				
				for(GInvtAc gInvtAc: gInvtAcsList) {
					if(!status.equalsIgnoreCase("Active") ||
							EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gInvtAc.getAccountStatusId()))) {

						GroupInvtAcREST groupInvtAcREST = GroupInvtAcREST.convertAccountToREST(gInvtAc);
						// Load Bank Account
						GBankAccount gBankAccount = daoFactory.getGBankAccountDAO().findById(gInvtAc.getInvtBankAcNo());
						groupInvtAcREST.setBankAccount(GBankAccountREST.convertAccountToREST(gBankAccount));
						bankProfileIds.add(groupInvtAcREST.getBankGroupAcNo());
						
						group.addGroupInvtAc(groupInvtAcREST);
					}		
				}
			}
			
			if(gAc.getGLoanAcs() != null && !gAc.getGLoanAcs().isEmpty()) {
				List<GLoanAc> gLoanAcsList = new ArrayList<GLoanAc>(gAc.getGLoanAcs());
				Collections.sort(gLoanAcsList);
				
				for(GLoanAc gLoanAc: gLoanAcsList) {
					if(!status.equalsIgnoreCase(EnumConst.ActiveStatus_Active) ||
							EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, gLoanAc.getAccountStatusId()))) {

						GroupLoanAcREST groupLoanAcREST = GroupLoanAcREST.convertAccountToREST(gLoanAc);
						// Load Bank Account
						GBankAccount gBankAccount = daoFactory.getGBankAccountDAO().findById(gLoanAc.getLoanBankAcNo());
						groupLoanAcREST.setBankAccount(GBankAccountREST.convertAccountToREST(gBankAccount));
						bankProfileIds.add(groupLoanAcREST.getBankGroupAcNo());
						
						group.addGroupLoanAc(groupLoanAcREST);
					}
				}
			}
			
			for(Long bankProfileId : bankProfileIds) {
				BankProfile bankProfile = daoFactory.getBankProfileDAO().findById(bankProfileId);
				BankProfileREST bankProfileREST = new BankProfileREST();
				BankProfileREST.loadBankProfile(bankProfile, bankProfileREST);
				
				group.addRelatedBank(bankProfileREST);
			}
		}

		if(gAcByTxtypes != null) {
			GroupREST.loadGAcByTxtype(gAcByTxtypes, group);
		}

		if(gRules != null) {
			GroupREST.loadGroupRules(gRules, group);
		}
		
		group.setDisplayNames(RestDisplayTitle.getGroupAcRDT());
		
		return group;
	}
	
	public GroupRules getGroupRules(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GRules gRules = daoFactory.getGRulesDAO().findById(groupAcNo);
		if(gRules == null) {
			throw new BadRequestException("Invalid Group Rules No");
		}

		GroupREST group = new GroupREST();
		
		GroupREST.loadGroupRules(gRules, group);
		
		return group.getGroupRules();
	}
	
	public GroupRules getGroupRulesForDisplay(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}		
		GroupRules rules = getGroupRules(lang, groupAcNo);
		
		if(rules.getAllowAssociateMembers() > DataUtil.ZERO_INTEGER) {
			rules.setDisplayNames(RestDisplayTitle.getGroupRulesAllRDT());
		} else {
			rules.setDisplayNames(RestDisplayTitle.getGroupRulesCMRDT());
		}
		
		return rules;
	}
	
	public void updateGroupRules(GroupRules groupRules) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupRules.getGAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		GRules gRules = daoFactory.getGRulesDAO().findById(groupRules.getGAcNo());
		if(gRules == null) {
			throw new BadRequestException("Invalid Group Rules No");
		}
		
		GroupRules.loadGRules(groupRules, gRules);
		
		daoFactory.getGRulesDAO().merge(gRules);
	}
	
	public ApplicationsREST getApplications(String lang, long groupAcNo) throws BadRequestException {
		
		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}
		
		ApplicationsREST applications = new ApplicationsREST(groupAcNo);
		
		// Member Loan Account
		List<MLoanAc> mLoanAcs = daoFactory.getMLoanAcDAO().getLoanApplications(groupAcNo);
		// Member Account
		List<MProfile> mProfiles = daoFactory.getMProfileDAO().getMemberApplications(groupAcNo);


		GroupInfoCollector collector = new GroupInfoCollector();

		if(mLoanAcs != null && !mLoanAcs.isEmpty()) {
			// Load Member Names
			for(MLoanAc mLoanAc : mLoanAcs) {
				collector.addMemberAcNo(mLoanAc.getMAc().getMAcNo());
			}

			// Load Member Profile
			daoFactory.getMProfileDAO().loadMembersProfile(collector);

			// Load Member Names
			for(MLoanAc mLoanAc : mLoanAcs) {
				if(mLoanAc.getApprovedByMember() > 0) {
					collector.addMemberAcNo(mLoanAc.getApprovedByMember());
				}
			}
		}

		if(mProfiles != null && !mProfiles.isEmpty()) {
			// Load Member Names
			for(MProfile mProfile : mProfiles) {
				collector.addMemberAcNo(mProfile.getMemberAcNo());
				if(mProfile.getRecommendedByMember() > 0) {
					collector.addMemberAcNo(mProfile.getRecommendedByMember());
				}
				if(mProfile.getApprovedByMember() > 0) {
					collector.addMemberAcNo(mProfile.getApprovedByMember());
				}
			}
		}
		
		// Load Member Names
		daoFactory.getMemberContactDAO().loadMemberNames(lang, collector);

		if(mLoanAcs != null && !mLoanAcs.isEmpty()) {
			ApplicationsREST.loadMemberLoanApplications(mLoanAcs, collector, applications);
		}
		
		if(mProfiles != null && !mProfiles.isEmpty()) {
			ApplicationsREST.loadMemberApplications(mProfiles, collector, applications);
		}

		applications.setDisplayNames(RestDisplayTitle.getApplicationRDT());

		return applications;
	}
	
	public BankSearchInfo searchBankByNameInDistrict(BankSearchInfo request) throws BadRequestException {
		if(request.getDistrictCode() == null || request.getDistrictCode().length() != 4) {
			throw new BadRequestException("Invalid District Code: " + request.getDistrictCode());
		}
		District district = daoFactory.getDistrictDAO().getDistrictFromCode(request.getDistrictCode());
		if(district == null) {
			throw new BadRequestException("Invalid District Code: " + request.getDistrictCode());
		}
		if(request.getBankName() == null) {
			throw new BadRequestException("Invalid Bank Name");
		}

		BankInfoCollector collector = new BankInfoCollector();
		collector.setDistrictId(district.getDistrictId());
		collector.setBankName(request.getBankName());
		
		daoFactory.getBankProfileDAO().loadBankProfile(collector);
		List<BankProfile> banks = collector.searchBankName(request.getBranchName());
		
		List<BankProfileREST> bankRESTs = new ArrayList<BankProfileREST>();
		
		for(BankProfile bank: banks) {
			BankProfileREST bankREST = new BankProfileREST();
			BankProfileREST.loadBankProfile(bank, bankREST);
			bankRESTs.add(bankREST);
		}
		
		request.setBankProfiles(bankRESTs);
		request.setDistrictId(district.getDistrictId());
		request.setDistrictName(district.getDistrict());
		
		return request;
	}
	
	public BankSearchInfo searchBankByIFCSCode(String ifcsCode) throws BadRequestException {
		if(ifcsCode == null || ifcsCode.length() < 8) {
			throw new BadRequestException("Invalid Bank IFCS Code");
		}

		List<BankProfile> banks = daoFactory.getBankProfileDAO().getBankForIFCSCode(ifcsCode);
		
		List<BankProfileREST> bankRESTs = new ArrayList<BankProfileREST>();
		
		if(banks != null) {
			for(BankProfile bank: banks) {
				BankProfileREST bankREST = new BankProfileREST();
				BankProfileREST.loadBankProfile(bank, bankREST);
				bankRESTs.add(bankREST);
			}
		}
		
		BankSearchInfo responce = new BankSearchInfo();
		responce.setBankProfiles(bankRESTs);
		
		return responce;
	}
	
    public List<Attachment> attachFile(long groupAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	
    	String attachmentInfo = attachmentBO.saveFile(groupAcNo, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.G_PROFILE, groupAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> attachBankFile(long groupAcNo, long bankAccountNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	
    	String attachmentInfo = attachmentBO.saveFile(groupAcNo, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.BANK_ACCOUNT, bankAccountNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> updateAttachFile(long groupAcNo, long docId, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
    	if(!gProfile.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(gProfile.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Group Profile!");
    	}
  	
    	String attachmentInfo = attachmentBO.updateFile(groupAcNo, docId, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.G_PROFILE, groupAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> updateAttachBankFile(long groupAcNo, long docId, long bankAccountNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(bankAccountNo);
    	if(!bankAccount.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(bankAccount.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Bank Account!");
    	}
    	
    	String attachmentInfo = attachmentBO.updateFile(groupAcNo, docId, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.BANK_ACCOUNT, bankAccountNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public void deleteAttachFile(long groupAcNo, long docId) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
		GProfile gProfile = daoFactory.getGProfileDAO().findById(groupAcNo);
    	if(!gProfile.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(gProfile.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Group Profile!");
    	}
  	
    	attachmentBO.deleteFile(groupAcNo, docId);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.G_PROFILE, groupAcNo, dataFeilds));
    }
	
    public void deleteAttachBankFile(long groupAcNo, long docId, long bankAccountNo) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	BankAccount bankAccount = daoFactory.getBankAccountDAO().findById(bankAccountNo);
    	if(!bankAccount.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(bankAccount.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Bank Account!");
    	}
    	
    	attachmentBO.deleteFile(groupAcNo, docId);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.BANK_ACCOUNT, bankAccountNo, dataFeilds));
    }

    public void bankAcUpload(long groupAcNo, long bankAccountNo, BigDecimal amount, File file) throws BadRequestException, IOException {

    	if(bankAccountNo <= 0) {
    		throw new BadRequestException("Invalid Bank Account No!");
    	}
    	GBankAccount gBankAccount = daoFactory.getGBankAccountDAO().findById(bankAccountNo);
    	if(gBankAccount == null) {
    		throw new BadRequestException("Invalid Bank Account No: " + bankAccountNo + "!");
    	}
    	if(groupAcNo != gBankAccount.getGProfile().getGAcNo()) {
    		throw new BadRequestException("Invalid Bank Account No for the given Group!");
    	}

    	BankAcUpload bankAcUpload = new BankAcUpload();
    	bankAcUpload.setBankAccountNo(bankAccountNo);
    	bankAcUpload.setAmount(amount);
    	bankAcUpload.setFile(FileUtils.readFileToByteArray(file));
    	bankAcUpload.setUploadDate(DateUtil.getCurrentTimeDate());

    	daoFactory.getBankAcUploadDAO().persist(bankAcUpload);

    	gBankAccount.setVerifiedBalanceAm(amount);
    	daoFactory.getGBankAccountDAO().merge(gBankAccount);
    }
}
