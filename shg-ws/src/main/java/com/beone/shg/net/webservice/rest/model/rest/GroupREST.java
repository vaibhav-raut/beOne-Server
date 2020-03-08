package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GAcByTxtype;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.GProfile;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.GroupContact;
import com.beone.shg.net.persistence.model.MonthlyGAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupREST {
	
	private long groupAcNo;
	private String districtCode;
	private int districtId;
	private String groupName;
	private String vision;
	private String description;
	private String groupType;
	private String approvalStatus;
	private String activeStatus;
	private Float percentageProfileComplete;
	private Integer activeChildGroups;
	private int activeCoreMembers;
	private int activeAssociateMembers;
	private String dateOfEstablishment;
	private String address;
	private String affiliatedBank;
	private long lastLoggedInTs;
	private long approvedByMember;
	private String registrationNo;
	private String approvalDate;
	private String registrationDate;
	private String logoUrl;
	private String statusMessage;
	private String location;
	private String errorMessages;
	private List<Attachment> attachments;
	private List<GroupContactREST> contacts;
	private List<BankAccountREST> bankAccounts;
	private List<Long> mappingMemberAcNos;
	private List<BankAccountShort> bankAccountDisplay;
	private GroupAc groupAc;
	private GroupRules groupRules;
	private List<GAcByTxtypeREST> gacByTxtypes;
	private List<String> monthsAvaliable;
	private BankProfileREST bankProfile;
	private List<GroupInvtAcREST> groupInvtAcs;
	private List<GroupLoanAcREST> groupLoanAcs;
	private List<BankProfileREST> relatedBanks;
	private Map<String, String> displayNames;
	
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNos) {
		this.groupAcNo = groupAcNos;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getVision() {
		return vision;
	}
	public void setVision(String vision) {
		this.vision = vision;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Float getPercentageProfileComplete() {
		return percentageProfileComplete;
	}
	public void setPercentageProfileComplete(Float percentageProfileComplete) {
		this.percentageProfileComplete = percentageProfileComplete;
	}
	public Integer getActiveChildGroups() {
		return activeChildGroups;
	}
	public void setActiveChildGroups(Integer activeChildGroups) {
		this.activeChildGroups = activeChildGroups;
	}
	public int getActiveCoreMembers() {
		return activeCoreMembers;
	}
	public void setActiveCoreMembers(int activeCoreMembers) {
		this.activeCoreMembers = activeCoreMembers;
	}
	public int getActiveAssociateMembers() {
		return activeAssociateMembers;
	}
	public void setActiveAssociateMembers(int activeAssociateMembers) {
		this.activeAssociateMembers = activeAssociateMembers;
	}
	public String getDateOfEstablishment() {
		return dateOfEstablishment;
	}
	public void setDateOfEstablishment(String dateOfEstablishment) {
		this.dateOfEstablishment = dateOfEstablishment;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAffiliatedBank() {
		return affiliatedBank;
	}
	public void setAffiliatedBank(String affiliatedBank) {
		this.affiliatedBank = affiliatedBank;
	}
	public long getLastLoggedInTs() {
		return lastLoggedInTs;
	}
	public void setLastLoggedInTs(long lastLoggedInTs) {
		this.lastLoggedInTs = lastLoggedInTs;
	}
	public long getApprovedByMember() {
		return approvedByMember;
	}
	public void setApprovedByMember(long approvedByMember) {
		this.approvedByMember = approvedByMember;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}
	public List<GroupContactREST> getContacts() {
		return contacts;
	}
	public void setContacts(List<GroupContactREST> contacts) {
		this.contacts = contacts;
	}
	public void addContact(GroupContactREST contact) {
		if(this.contacts == null) {
			this.contacts = new ArrayList<GroupContactREST>();
		}
		this.contacts.add(contact);
	}
	public List<BankAccountREST> getBankAccounts() {
		return bankAccounts;
	}
	public void setBankAccounts(List<BankAccountREST> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	public void addBankAccount(BankAccountREST bankAccount) {
		if(this.bankAccounts == null) {
			this.bankAccounts = new ArrayList<BankAccountREST>();
		}
		this.bankAccounts.add(bankAccount);
	}
	public List<Long> getMappingMemberAcNos() {
		return mappingMemberAcNos;
	}
	public void setMappingMemberAcNos(List<Long> mappingMemberAcNos) {
		this.mappingMemberAcNos = mappingMemberAcNos;
	}
	public List<BankAccountShort> getBankAccountDisplay() {
		return bankAccountDisplay;
	}
	public void setBankAccountDisplay(List<BankAccountShort> bankAccountDisplay) {
		this.bankAccountDisplay = bankAccountDisplay;
	}
	public GroupAc getGroupAc() {
		return groupAc;
	}
	public void setGroupAc(GroupAc groupAc) {
		this.groupAc = groupAc;
	}

	public GroupRules getGroupRules() {
		return groupRules;
	}
	public void setGroupRules(GroupRules groupRules) {
		this.groupRules = groupRules;
	}
	public List<GAcByTxtypeREST> getGAcByTxtypes() {
		return gacByTxtypes;
	}
	public void setGAcByTxtypes(List<GAcByTxtypeREST> gacByTxtypes) {
		this.gacByTxtypes = gacByTxtypes;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	public void addAttachment(Attachment attachment) {
		if(this.attachments == null) {
			this.attachments = new ArrayList<Attachment>();
		}
		this.attachments.add(attachment);
	}
	public List<String> getMonthsAvaliable() {
		return monthsAvaliable;
	}
	public void setMonthsAvaliable(List<String> monthsAvaliable) {
		this.monthsAvaliable = monthsAvaliable;
	}
	public BankProfileREST getBankProfile() {
		return bankProfile;
	}
	public void setBankProfile(BankProfileREST bankProfile) {
		this.bankProfile = bankProfile;
	}
	public List<GroupInvtAcREST> getGroupInvtAcs() {
		return groupInvtAcs;
	}
	public void setGroupInvtAcs(List<GroupInvtAcREST> groupInvtAcs) {
		this.groupInvtAcs = groupInvtAcs;
	}
	public void addGroupInvtAc(GroupInvtAcREST groupInvtAc) {
		if(groupInvtAcs == null) {
			groupInvtAcs = new ArrayList<GroupInvtAcREST>();
		}
		this.groupInvtAcs.add(groupInvtAc);
	}
	public List<GroupLoanAcREST> getGroupLoanAcs() {
		return groupLoanAcs;
	}
	public void setGroupLoanAcs(List<GroupLoanAcREST> groupLoanAcs) {
		this.groupLoanAcs = groupLoanAcs;
	}
	public void addGroupLoanAc(GroupLoanAcREST groupLoanAc) {
		if(groupLoanAcs == null) {
			groupLoanAcs = new ArrayList<GroupLoanAcREST>();
		}
		this.groupLoanAcs.add(groupLoanAc);
	}
	public List<BankProfileREST> getRelatedBanks() {
		return relatedBanks;
	}
	public void setRelatedBanks(List<BankProfileREST> relatedBanks) {
		this.relatedBanks = relatedBanks;
	}
	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
	public void addRelatedBank(BankProfileREST relatedBank) {
		if(relatedBanks == null) {
			relatedBanks = new ArrayList<BankProfileREST>();
		}
		this.relatedBanks.add(relatedBank);
	}

	public static void loadGroupProfile(GProfile gProfile, GroupREST group) {
		
		group.setGroupAcNo(gProfile.getGAcNo());
		group.setGroupType(EnumCache.getNameOfGroupType(gProfile.getGroupTypeId()));
		group.setApprovalStatus(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, gProfile.getApprovalStatusId()));
		group.setActiveStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, gProfile.getActiveStatusId()));
		group.setPercentageProfileComplete(gProfile.getPercentageProfileComplete());
		group.setActiveChildGroups(gProfile.getActiveChildGroups());
		group.setActiveCoreMembers(gProfile.getActiveCoreMembers());
		group.setActiveAssociateMembers(gProfile.getActiveAssociateMembers());
		group.setDateOfEstablishment(DateUtil.convertDateToString(gProfile.getEstablishmentDate()));
		group.setLastLoggedInTs(DateUtil.convertDateToTime(gProfile.getLastLoggedInTs()));
		if(gProfile.getApprovedByMember() > 0) {
			group.setApprovedByMember(gProfile.getApprovedByMember());
		}
		group.setRegistrationNo(gProfile.getRegistrationNo());
		group.setApprovalDate(DateUtil.convertDateToString(gProfile.getApprovalDate()));
		group.setRegistrationDate(DateUtil.convertDateToString(gProfile.getRegistrationDate()));
		group.setLogoUrl(gProfile.getLogoUrl());
		group.setStatusMessage(gProfile.getDescription());
		group.setLocation(gProfile.getLocation());		
		group.setAttachments(Attachment.buildAttachments(gProfile.getAttachmentUrl()));
		
		for(GroupContact gContact : gProfile.getGroupContacts()) {
			if(gContact.getLang().getLang().equals(EnumConst.Lang_English)) {
				group.setAddress(ConversionUtil.getGroupAddress(gContact));
				group.setDistrictCode(gContact.getContact().getDistrict().getDistrictCode());
				group.setDistrictId(gContact.getContact().getDistrict().getDistrictId());
				group.setGroupName(gContact.getName());
				group.setVision(gContact.getVision());
				group.setDescription(gContact.getDescription());
			}
		}
	}

	public static void loadGroupContacts(GProfile gProfile, GroupREST group) {
		
		List<GroupContactREST> contacts = new ArrayList<GroupContactREST>();
		
		for(GroupContact gContact : gProfile.getGroupContacts()) {
			GroupContactREST contact = new GroupContactREST();
			
			contact.setContactId(gContact.getContactId());
			contact.setLang(gContact.getLang().getLang());
			contact.setName(gContact.getName());
			contact.setVision(gContact.getVision());
			contact.setDescription(gContact.getDescription());
			contact.setAddress(gContact.getContact().getAddress());
			contact.setVillage(gContact.getContact().getVillage());
			contact.setGrampanchayat(gContact.getContact().getGrampanchayat());
			contact.setTaluka(gContact.getContact().getTaluka());
			contact.setPinCode(gContact.getContact().getPinCode());
			contact.setDistrict(gContact.getContact().getDistrict().getDistrict());
			contact.setDistrictCode(gContact.getContact().getDistrict().getDistrictCode());
			contact.setDivision(gContact.getContact().getDistrict().getDivision());
			contact.setState(gContact.getContact().getDistrict().getState());
			contact.setPhone(gContact.getContact().getPhone());
			contact.setPriMobile(gContact.getContact().getPriMobile());
			contact.setSecMobile(gContact.getContact().getSecMobile());
			contact.setEmail(gContact.getContact().getEmail());
			
			contacts.add(contact);
		}
		
		group.setContacts(contacts);		
	}

	public static void loadGroupBankAccounts(GProfile gProfile, GroupREST group) {
		
		List<BankAccountREST> bankAccounts = new ArrayList<BankAccountREST>();
		
		for(GBankAccount gBankAccount : gProfile.getGBankAccounts()) {
			BankAccountREST bankAccount = new BankAccountREST();
			
			bankAccount.setBankAccountId(gBankAccount.getBankAccountNo());
			bankAccount.setAccountNumber(gBankAccount.getBankAccount().getAccountNumber());
			bankAccount.setAccountName(gBankAccount.getBankAccount().getAccountName());
			String type = EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, gBankAccount.getBankAccount().getBankAccountTypeId());
			bankAccount.setBankAccountType(type);
			bankAccount.setBankName(gBankAccount.getBankAccount().getBankProfile().getBankName());
			bankAccount.setBankBranchName(gBankAccount.getBankAccount().getBankProfile().getBranchName());
			bankAccount.setIfcsCode(gBankAccount.getBankAccount().getBankProfile().getIfcsCode());
			bankAccount.setAttachments(Attachment.buildAttachments(gBankAccount.getBankAccount().getAttachmentUrl()));
			
			if(EnumUtil.isBankAffiliatedType(type)) {
				group.setAffiliatedBank(gBankAccount.getBankAccount().getBankProfile().getBankName() + ", Branch - " +
						gBankAccount.getBankAccount().getBankProfile().getBranchName());
			}
			
			bankAccounts.add(bankAccount);
		}
		
		group.setBankAccounts(bankAccounts);
		
	}

	public static void loadGroupAc(GAc gAc, GroupREST group) {
		
		GroupAc groupAc = new GroupAc();
		
		groupAc.setCreditRating(gAc.getCreditRating());
		groupAc.setApprovalRating(gAc.getApprovalRating());
		groupAc.setMeetingAttendance(gAc.getMeetingAttendance());
		groupAc.setCMSavedAm(gAc.getCMSavedAm());
		groupAc.setCMOutstandingSavedAm(gAc.getCMOutstandingSavedAm());
		groupAc.setCMReturnedSavedAm(gAc.getCMReturnedSavedAm());
		groupAc.setCMReturnedIntEnAm(gAc.getCMReturnedIntEnAm());
		groupAc.setCMProvIntEnAm(gAc.getCMProvIntEnAm());
		groupAc.setCMProfitShareDeclaredAm(gAc.getCMProfitShareDeclaredAm());
		groupAc.setCMProfitSharePaidAm(gAc.getCMProfitSharePaidAm());
		groupAc.setCMLoanAm(gAc.getCMLoanAm());
		groupAc.setCMRecLoanAm(gAc.getCMRecLoanAm());
		groupAc.setCMRecIntOnLoanAm(gAc.getCMRecIntOnLoanAm());
		groupAc.setCMProjIntOnLoanAm(gAc.getCMProjIntOnLoanAm());
		groupAc.setCMPlannedMonthlySaving(gAc.getCMPlannedMonthlySaving());
		groupAc.setCMNoOfLoans(gAc.getCMNoOfLoans());
		groupAc.setCMNoOfActiveLoans(gAc.getCMNoOfActiveLoans());
		groupAc.setCMSubStdDeptAm(gAc.getCMSubStdDeptAm());
		groupAc.setCMNoOfSubStdDept(gAc.getCMNoOfSubStdDept());
		groupAc.setCMBadDeptExpAm(gAc.getCMBadDeptExpAm());
		groupAc.setCMNoOfBadDeptExp(gAc.getCMNoOfBadDeptExp());
		groupAc.setCMBadDeptClosedAm(gAc.getCMBadDeptClosedAm());
		groupAc.setCMNoOfBadDeptClosed(gAc.getCMNoOfBadDeptClosed());

		groupAc.setAMSavedAm(gAc.getAMSavedAm());
		groupAc.setAMOutstandingSavedAm(gAc.getAMOutstandingSavedAm());
		groupAc.setAMProvIntEnAm(gAc.getAMProvIntEnAm());
		groupAc.setAMDividedDeclaredAm(gAc.getAMDividedDeclaredAm());
		groupAc.setAMDividedPaidAm(gAc.getAMDividedPaidAm());
		groupAc.setAMReturnedSavedAm(gAc.getAMReturnedSavedAm());
		groupAc.setAMReturnedIntEnAm(gAc.getAMReturnedIntEnAm());
		groupAc.setAMLoanAm(gAc.getAMLoanAm());
		groupAc.setAMRecLoanAm(gAc.getAMRecLoanAm());
		groupAc.setAMRecIntOnLoanAm(gAc.getAMRecIntOnLoanAm());
		groupAc.setAMProjIntOnLoanAm(gAc.getAMProjIntOnLoanAm());
		groupAc.setAMPlannedMonthlySaving(gAc.getAMPlannedMonthlySaving());
		groupAc.setAMNoOfLoans(gAc.getAMNoOfLoans());
		groupAc.setAMNoOfActiveLoans(gAc.getAMNoOfActiveLoans());
		groupAc.setAMSubStdDeptAm(gAc.getAMSubStdDeptAm());
		groupAc.setAMNoOfSubStdDept(gAc.getAMNoOfSubStdDept());
		groupAc.setAMBadDeptExpAm(gAc.getAMBadDeptExpAm());
		groupAc.setAMNoOfBadDeptExp(gAc.getAMNoOfBadDeptExp());
		groupAc.setAMBadDeptClosedAm(gAc.getAMBadDeptClosedAm());
		groupAc.setAMNoOfBadDeptClosed(gAc.getAMNoOfBadDeptClosed());
		
		groupAc.setPLoanAm(gAc.getPLoanAm());
		groupAc.setPRecLoanAm(gAc.getPRecLoanAm());
		groupAc.setPRecIntOnLoanAm(gAc.getPRecIntOnLoanAm());
		groupAc.setPProjIntOnLoanAm(gAc.getPProjIntOnLoanAm());
		groupAc.setNoOfProject(gAc.getNoOfProject());
		groupAc.setNoOfActiveProject(gAc.getNoOfActiveProject());
		
		groupAc.setFixDepositInvAm(gAc.getFixDepositInvAm());
		groupAc.setRecFixDepositAm(gAc.getRecFixDepositAm());
		groupAc.setRecIntOnFixDepositAm(gAc.getRecIntOnFixDepositAm());
		groupAc.setProjIntOnFixDepositAm(gAc.getProjIntOnFixDepositAm());
		groupAc.setNoOfFixDeposit(gAc.getNoOfFixDeposit());
		groupAc.setNoOfActiveFixDeposit(gAc.getNoOfActiveFixDeposit());
		
		groupAc.setOtherInvAm(gAc.getOtherInvAm());
		groupAc.setRecOtherInvAm(gAc.getRecOtherInvAm());
		groupAc.setRecIntOnOtherInvAm(gAc.getRecIntOnOtherInvAm());
		groupAc.setProjIntOnOtherInvAm(gAc.getProjIntOnOtherInvAm());
		groupAc.setNoOfOtherInv(gAc.getNoOfOtherInv());
		groupAc.setNoOfActiveOtherInv(gAc.getNoOfActiveOtherInv());
		
		groupAc.setIntEnOnSavingAcAm(gAc.getIntEnOnSavingAcAm());
		groupAc.setBankBalanceAm(gAc.getClearBankBalanceAm());
		groupAc.setCashInHandAm(gAc.getClearCashInHandAm());
		groupAc.setSubjClearingBankBalanceAm(gAc.getSubjClearingBankBalanceAm());
		groupAc.setSubjClearingCashInHandAm(gAc.getSubjClearingCashInHandAm());
		groupAc.setNetProfitAm(gAc.getNetProfitAm());
		groupAc.setNetProfitProjAm(gAc.getNetProfitProjAm());
		groupAc.setExpensesAm(gAc.getExpensesAm());
		groupAc.setOutstandingExpensesAm(gAc.getOutstandingExpensesAm());
		groupAc.setPenaltyAm(gAc.getRecPenaltyAm());
		
		groupAc.setBorrowedLoanAm(gAc.getBorrowedLoanAm());
		groupAc.setPaidBorrowedLoanAm(gAc.getPaidBorrowedLoanAm());
		groupAc.setPaidIntOnBorrowedLoanAm(gAc.getPaidIntOnBorrowedLoanAm());
		groupAc.setProjIntOnBorrowedLoanAm(gAc.getProjIntOnBorrowedLoanAm());		
		groupAc.setNoOfBankLoan(gAc.getNoOfBankLoan());
		groupAc.setNoOfActiveBankLoan(gAc.getNoOfActiveBankLoan());
		groupAc.setBankSubStdDeptAm(gAc.getBankSubStdDeptAm());
		groupAc.setBankNoOfSubStdDept(gAc.getBankNoOfSubStdDept());
		groupAc.setBankBadDeptExpAm(gAc.getBankBadDeptExpAm());
		groupAc.setBankNoOfBadDeptExp(gAc.getBankNoOfBadDeptExp());
		groupAc.setBankBadDeptClosedAm(gAc.getBankBadDeptClosedAm());
		groupAc.setBankNoOfBadDeptClosed(gAc.getBankNoOfBadDeptClosed());

		groupAc.setGovRevolvingFundAm(gAc.getGovRevolvingFundAm());
		groupAc.setGovRevolvingFundReturnedAm(gAc.getGovRevolvingFundReturnedAm());
		groupAc.setNoOfGovRevolvingFund(gAc.getNoOfGovRevolvingFund());
		groupAc.setNoOfActiveGovRevolvingFund(gAc.getNoOfActiveGovRevolvingFund());
		groupAc.setGovDevelopmentFundAm(gAc.getGovDevelopmentFundAm());
		groupAc.setNoOfGovDevelopmentFund(gAc.getNoOfGovDevelopmentFund());

		groupAc.setPenShgOneMemRegFee(gAc.getPenShgOneMemRegFee());		
		groupAc.setPenShgOneServiceCharges(gAc.getPenShgOneServiceCharges());		

		groupAc.setNoOfTxsMonthlyExp(gAc.getNoOfTxsMonthlyExp());
		groupAc.setNoOfTxsMonthlyDone(gAc.getNoOfTxsMonthlyDone());
		groupAc.setNoOfTxsMonthlyApproved(gAc.getNoOfTxsMonthlyApproved());

		group.setGroupAc(groupAc);	
	}
	
	public static void updateGAc(GroupAc groupAc, GAc gAc) {
		
		gAc.setCreditRating(groupAc.getCreditRating());
		gAc.setApprovalRating(groupAc.getApprovalRating());
		gAc.setMeetingAttendance(groupAc.getMeetingAttendance());
		gAc.setCMSavedAm(groupAc.getCMSavedAm());
		gAc.setCMOutstandingSavedAm(groupAc.getCMOutstandingSavedAm());
		gAc.setCMReturnedSavedAm(groupAc.getCMReturnedSavedAm());
		gAc.setCMReturnedIntEnAm(groupAc.getCMReturnedIntEnAm());
		gAc.setCMProvIntEnAm(groupAc.getCMProvIntEnAm());
		gAc.setCMProfitShareDeclaredAm(groupAc.getCMProfitShareDeclaredAm());
		gAc.setCMProfitSharePaidAm(groupAc.getCMProfitSharePaidAm());
		gAc.setCMLoanAm(groupAc.getCMLoanAm());
		gAc.setCMRecLoanAm(groupAc.getCMRecLoanAm());
		gAc.setCMRecIntOnLoanAm(groupAc.getCMRecIntOnLoanAm());
		gAc.setCMProjIntOnLoanAm(groupAc.getCMProjIntOnLoanAm());
		gAc.setCMPlannedMonthlySaving(groupAc.getCMPlannedMonthlySaving());
		gAc.setCMNoOfLoans(groupAc.getCMNoOfLoans());
		gAc.setCMNoOfActiveLoans(groupAc.getCMNoOfActiveLoans());
		gAc.setCMSubStdDeptAm(groupAc.getCMSubStdDeptAm());
		gAc.setCMNoOfSubStdDept(groupAc.getCMNoOfSubStdDept());
		gAc.setCMBadDeptExpAm(groupAc.getCMBadDeptExpAm());
		gAc.setCMNoOfBadDeptExp(groupAc.getCMNoOfBadDeptExp());
		gAc.setCMBadDeptClosedAm(groupAc.getCMBadDeptClosedAm());
		gAc.setCMNoOfBadDeptClosed(groupAc.getCMNoOfBadDeptClosed());
		
		gAc.setAMSavedAm(groupAc.getAMSavedAm());
		gAc.setAMOutstandingSavedAm(groupAc.getAMOutstandingSavedAm());
		gAc.setAMProvIntEnAm(groupAc.getAMProvIntEnAm());
		gAc.setAMDividedDeclaredAm(groupAc.getAMDividedDeclaredAm());
		gAc.setAMDividedPaidAm(groupAc.getAMDividedPaidAm());
		gAc.setAMReturnedSavedAm(groupAc.getAMReturnedSavedAm());
		gAc.setAMReturnedIntEnAm(groupAc.getAMReturnedIntEnAm());
		gAc.setAMLoanAm(groupAc.getAMLoanAm());
		gAc.setAMRecLoanAm(groupAc.getAMRecLoanAm());
		gAc.setAMRecIntOnLoanAm(groupAc.getAMRecIntOnLoanAm());
		gAc.setAMProjIntOnLoanAm(groupAc.getAMProjIntOnLoanAm());
		gAc.setAMPlannedMonthlySaving(groupAc.getAMPlannedMonthlySaving());
		gAc.setAMNoOfLoans(groupAc.getAMNoOfLoans());
		gAc.setAMNoOfActiveLoans(groupAc.getAMNoOfActiveLoans());
		gAc.setAMSubStdDeptAm(groupAc.getAMSubStdDeptAm());
		gAc.setAMNoOfSubStdDept(groupAc.getAMNoOfSubStdDept());
		gAc.setAMBadDeptExpAm(groupAc.getAMBadDeptExpAm());
		gAc.setAMNoOfBadDeptExp(groupAc.getAMNoOfBadDeptExp());
		gAc.setAMBadDeptClosedAm(groupAc.getAMBadDeptClosedAm());
		gAc.setAMNoOfBadDeptClosed(groupAc.getAMNoOfBadDeptClosed());
		
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
		
		gAc.setIntEnOnSavingAcAm(groupAc.getIntEnOnSavingAcAm());
		gAc.setClearBankBalanceAm(groupAc.getBankBalanceAm());
		gAc.setClearCashInHandAm(groupAc.getCashInHandAm());
		gAc.setSubjClearingBankBalanceAm(groupAc.getSubjClearingBankBalanceAm());
		gAc.setSubjClearingCashInHandAm(groupAc.getSubjClearingCashInHandAm());
		gAc.setNetProfitProjAm(groupAc.getNetProfitProjAm());
		gAc.setExpensesAm(groupAc.getExpensesAm());
		gAc.setOutstandingExpensesAm(groupAc.getOutstandingExpensesAm());
		gAc.setRecPenaltyAm(groupAc.getPenaltyAm());
		
		gAc.setBorrowedLoanAm(groupAc.getBorrowedLoanAm());
		gAc.setPaidBorrowedLoanAm(groupAc.getPaidBorrowedLoanAm());
		gAc.setPaidIntOnBorrowedLoanAm(groupAc.getPaidIntOnBorrowedLoanAm());
		gAc.setProjIntOnBorrowedLoanAm(groupAc.getProjIntOnBorrowedLoanAm());		
		gAc.setBankSubStdDeptAm(groupAc.getBankSubStdDeptAm());
		gAc.setBankNoOfSubStdDept(groupAc.getBankNoOfSubStdDept());
		gAc.setBankBadDeptExpAm(groupAc.getBankBadDeptExpAm());
		gAc.setBankNoOfBadDeptExp(groupAc.getBankNoOfBadDeptExp());
		gAc.setBankBadDeptClosedAm(groupAc.getBankBadDeptClosedAm());
		gAc.setBankNoOfBadDeptClosed(groupAc.getBankNoOfBadDeptClosed());
		gAc.setNoOfBankLoan(groupAc.getNoOfBankLoan());
		gAc.setNoOfActiveBankLoan(groupAc.getNoOfActiveBankLoan());

		gAc.setGovRevolvingFundAm(groupAc.getGovRevolvingFundAm());
		gAc.setGovRevolvingFundReturnedAm(groupAc.getGovRevolvingFundReturnedAm());
		gAc.setNoOfGovRevolvingFund(groupAc.getNoOfGovRevolvingFund());
		gAc.setNoOfActiveGovRevolvingFund(groupAc.getNoOfActiveGovRevolvingFund());
		gAc.setGovDevelopmentFundAm(groupAc.getGovDevelopmentFundAm());
		gAc.setNoOfGovDevelopmentFund(groupAc.getNoOfGovDevelopmentFund());

		gAc.setPenShgOneMemRegFee(groupAc.getPenShgOneMemRegFee());		
		gAc.setPenShgOneServiceCharges(groupAc.getPenShgOneServiceCharges());		

		gAc.setNoOfTxsMonthlyExp(groupAc.getNoOfTxsMonthlyExp());
		gAc.setNoOfTxsMonthlyDone(groupAc.getNoOfTxsMonthlyDone());
		gAc.setNoOfTxsMonthlyApproved(groupAc.getNoOfTxsMonthlyApproved());
	}
	
	public static void loadGAcByTxtype(List<GAcByTxtype> gacByTxtypes, GroupREST group) {
		List<GAcByTxtypeREST> acList = new ArrayList<GAcByTxtypeREST>();
		
		for(GAcByTxtype txAx: gacByTxtypes) {
			acList.add(new GAcByTxtypeREST(txAx.getId().getGroupAcNo(), 
					EnumCache.getNameOfTxType(txAx.getTxTypeId()), 
					txAx.getAmount()));
		}
		
		group.setGAcByTxtypes(acList);
	}
	
	public static void loadMonthsAvailable(List<MonthlyGAc> monthlyGAcs, List<String> monthsAvaliable) {
		monthsAvaliable.add(EnumConst.AsOnDate);
		
		if(monthlyGAcs != null) {
			for(MonthlyGAc mGAc: monthlyGAcs) {
				monthsAvaliable.add(mGAc.getMonth());
			}
		}
		
	}
	
	public static void loadGroupRules(GRules gRules, GroupREST group) {
		GroupRules groupRules = new GroupRules();
		
		GroupRules.loadGroupRules(gRules, groupRules);
		
		group.setGroupRules(groupRules);
	}
	
	public Map<String,String> toStringInfo() {
		
		Map<String,String> groupInfo = new HashMap<String,String>();
		groupInfo.put("groupAcNo", Long.toString(groupAcNo));
		groupInfo.put("groupName", contacts.get(0).getName());
		groupInfo.put("districtCode", districtCode);
		groupInfo.put("districtId", Integer.toString(districtId));

		return groupInfo;
	}
	
	public static boolean isGroupCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("Name") &&
				csvData.isColumnPresent("Vision") &&
				csvData.isColumnPresent("Description") &&
				csvData.isColumnPresent("Date Of Establishment"));
	}
	
	public static boolean isManufacturerCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("Name") &&
				csvData.isColumnPresent("Group Type") &&
				csvData.isColumnPresent("Date Of Establishment"));
	}
	
	public static GroupREST buildGroup(String district, CSVDataCollector csvData, int row) throws BadRequestException {
		
		GroupREST group = new GroupREST();
		GroupContactREST contact = new GroupContactREST();
		
		// Add Name Info
		contact.setLang(EnumConst.Lang_English);
		group.setGroupAcNo(DataUtil.toLong(csvData.getCellValue("Group Ac No", row)));
		if(csvData.isColumnPresent("Group Type")) {
			group.setGroupType(csvData.getCellValue("Group Type", row));
		} else {
			group.setGroupType(EnumConst.GroupType_SHG);
		}
		group.setApprovalStatus(EnumConst.ApprovalStatus_Approved);
		group.setActiveStatus(EnumConst.ActiveStatus_Active);
		contact.setName(csvData.getCellValue("Name", row));
		contact.setVision(csvData.getCellValue("Vision", row));
		contact.setDescription(csvData.getCellValue("Description", row));
		group.setDateOfEstablishment(csvData.getCellValue("Date Of Establishment", row));

		// Add Contact Info
		contact.setAddress(csvData.getCellValue("Address", row));
		contact.setVillage(csvData.getCellValue("Village", row));
		contact.setGrampanchayat(csvData.getCellValue("Grampanchayat", row));
		contact.setTaluka(csvData.getCellValue("Tehsil", row));
		contact.setPinCode(csvData.getCellValue("Pin Code", row));
		if(district == null) {
			contact.setDistrict(csvData.getCellValue("District", row));
		} else {
			contact.setDistrict(district);
		}
		contact.setState(csvData.getCellValue("State", row));
		contact.setPhone(csvData.getCellValue("Phone", row));
		contact.setPriMobile(csvData.getCellValue("Primary Mobile", row));
		contact.setSecMobile(csvData.getCellValue("Secondary Mobile", row));
		contact.setEmail(csvData.getCellValue("Email", row));
		group.addContact(contact);
		
		// Add Bank Info
		if(!csvData.getCellValue("Bank Account Number", row).isEmpty()) {
			BankAccountREST account = new BankAccountREST();
			account.setAccountNumber(csvData.getCellValue("Bank Account Number", row));
			account.setAccountName(csvData.getCellValue("Account Name", row));
			account.setBankAccountType(csvData.getCellValue("Bank Account Type", row));
			account.setBankName(csvData.getCellValue("Bank Name", row));
			account.setBankBranchName(csvData.getCellValue("Bank Branch Name", row));
			account.setIfcsCode(csvData.getCellValue("IFSC Code", row));
			group.addBankAccount(account);
		}
		
		GroupAc groupAc = new GroupAc();		
		groupAc.setCreditRating(DataUtil.toFloat(csvData.getCellValue("Credit Rating", row)));
		groupAc.setApprovalRating(DataUtil.toFloat(csvData.getCellValue("Approval Rating", row)));
		groupAc.setMeetingAttendance(DataUtil.toFloat(csvData.getCellValue("Meeting Attendance", row)));	
		groupAc.setCMSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Saved Amount", row)));
		groupAc.setCMOutstandingSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Outstanding Saving", row)));
		groupAc.setCMProvIntEnAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Provisional Interest Earned", row)));
		groupAc.setCMReturnedSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Returned Saved Amount", row)));
		groupAc.setCMReturnedIntEnAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Returned Interest on Saving", row)));
		groupAc.setCMProfitShareDeclaredAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Profit Share Declared", row)));
		groupAc.setCMProfitSharePaidAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Profit Share Paid", row)));
		groupAc.setCMLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Loan Amount", row)));
		groupAc.setCMRecLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Recovered Loan Amount", row)));
		groupAc.setCMRecIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Recovered Interest on Loan", row)));
		groupAc.setCMProjIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("CM Projected Interest on Loan", row)));
		groupAc.setCMPlannedMonthlySaving(DataUtil.toBigDecimal(csvData.getCellValue("CM Planned Monthly Saving", row)));
		groupAc.setCMNoOfLoans(DataUtil.toInteger(csvData.getCellValue("CM No of Loans", row)));
		groupAc.setCMNoOfActiveLoans(DataUtil.toInteger(csvData.getCellValue("CM No of Active Loans", row)));
		
		groupAc.setAMSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Saved Amount", row)));
		groupAc.setAMOutstandingSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Outstanding Saving", row)));
		groupAc.setAMProvIntEnAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Provisional Interest Earned", row)));
		groupAc.setAMReturnedSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Returned Saved Amount", row)));
		groupAc.setAMReturnedIntEnAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Returned Interest on Saving", row)));
		groupAc.setAMDividedDeclaredAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Divided Declared", row)));
		groupAc.setAMDividedPaidAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Divided Paid", row)));
		groupAc.setAMLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Loan Amount", row)));
		groupAc.setAMRecLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Recovered Loan Amount", row)));
		groupAc.setAMRecIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Recovered Interest on Loan", row)));
		groupAc.setAMProjIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("AM Projected Interest on Loan", row)));
		groupAc.setAMPlannedMonthlySaving(DataUtil.toBigDecimal(csvData.getCellValue("AM Planned Monthly Saving", row)));
		groupAc.setAMNoOfLoans(DataUtil.toInteger(csvData.getCellValue("AM No of Loans", row)));
		groupAc.setAMNoOfActiveLoans(DataUtil.toInteger(csvData.getCellValue("AM No of Active Loans", row)));
		
		groupAc.setNoOfProject(DataUtil.toInteger(csvData.getCellValue("Total No Of Project", row)));
		groupAc.setNoOfActiveProject(DataUtil.toInteger(csvData.getCellValue("Active No Of Project", row)));
		groupAc.setPLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Project Loan Amount", row)));
		groupAc.setPRecLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Project Recovered Loan Amount", row)));
		groupAc.setPRecIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Project Recovered Interest on Loan", row)));
		groupAc.setPProjIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Project Projected Interest on Loan", row)));
		
		groupAc.setNoOfFixDeposit(DataUtil.toInteger(csvData.getCellValue("Total No Of Fix Deposit", row)));
		groupAc.setNoOfActiveFixDeposit(DataUtil.toInteger(csvData.getCellValue("Active No Of Fix Deposit", row)));
		groupAc.setFixDepositInvAm(DataUtil.toBigDecimal(csvData.getCellValue("Fix Deposit Investment", row)));
		groupAc.setRecFixDepositAm(DataUtil.toBigDecimal(csvData.getCellValue("Recovered Fix Deposit Investment", row)));
		groupAc.setRecIntOnFixDepositAm(DataUtil.toBigDecimal(csvData.getCellValue("Recovered Intrest on Fix Deposit", row)));
		groupAc.setProjIntOnFixDepositAm(DataUtil.toBigDecimal(csvData.getCellValue("Projected Interest on Fix Deposit", row)));
		
		groupAc.setNoOfOtherInv(DataUtil.toInteger(csvData.getCellValue("Total No Of Other Investment", row)));
		groupAc.setNoOfActiveOtherInv(DataUtil.toInteger(csvData.getCellValue("Active No Of Other Investment", row)));
		groupAc.setOtherInvAm(DataUtil.toBigDecimal(csvData.getCellValue("Other Investment", row)));
		groupAc.setRecOtherInvAm(DataUtil.toBigDecimal(csvData.getCellValue("Recovered Other Investment", row)));
		groupAc.setRecIntOnOtherInvAm(DataUtil.toBigDecimal(csvData.getCellValue("Recovered Intrest on Other Investment", row)));
		groupAc.setProjIntOnOtherInvAm(DataUtil.toBigDecimal(csvData.getCellValue("Projected Interest on Other Investment", row)));
		
		groupAc.setNoOfBankLoan(DataUtil.toInteger(csvData.getCellValue("Total No Of Bank Loan", row)));
		groupAc.setNoOfActiveBankLoan(DataUtil.toInteger(csvData.getCellValue("Active No Of Bank Loan", row)));
		groupAc.setBorrowedLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Bank Loan Amount", row)));
		groupAc.setPaidBorrowedLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Paid Bank Loan Amount", row)));
		groupAc.setPaidIntOnBorrowedLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Paid Interest on Bank Loan", row)));
		groupAc.setProjIntOnBorrowedLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Projected Interest on Bank Loan", row)));
		
		groupAc.setIntEnOnSavingAcAm(DataUtil.toBigDecimal(csvData.getCellValue("Interest Eaned on Bank Saving", row)));
		groupAc.setBankBalanceAm(DataUtil.toBigDecimal(csvData.getCellValue("Bank Balance", row)));
		groupAc.setCashInHandAm(DataUtil.toBigDecimal(csvData.getCellValue("Cash in Hand", row)));
		groupAc.setNetProfitAm(DataUtil.toBigDecimal(csvData.getCellValue("Net Profit", row)));
		groupAc.setNetProfitProjAm(DataUtil.toBigDecimal(csvData.getCellValue("Net Profit", row)));
		groupAc.setExpensesAm(DataUtil.toBigDecimal(csvData.getCellValue("Expenses", row)));
		groupAc.setPenaltyAm(DataUtil.toBigDecimal(csvData.getCellValue("Recovered Penalty", row)));
		group.setGroupAc(groupAc);
		
		return group;
	}	
	
	public static boolean isBankProfileCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("Name") &&
				csvData.isColumnPresent("Vision") &&
				csvData.isColumnPresent("Description") &&
				csvData.isColumnPresent("IFSC Code") &&
				csvData.isColumnPresent("Bank Name") &&
				csvData.isColumnPresent("Branch") &&
				csvData.isColumnPresent("Bank Loan Interest Rate") &&
				csvData.isColumnPresent("Bank FD Interest Rate") &&
				csvData.isColumnPresent("Bank Rating"));
	}
	
	public static GroupREST buildBankProfile(CSVDataCollector csvData, int row) throws BadRequestException {
		
		GroupREST group = new GroupREST();
		GroupContactREST contact = new GroupContactREST();
		
		// Add Name Info
		contact.setLang(EnumConst.Lang_English);
		group.setGroupType(EnumConst.GroupType_Bank);
		group.setApprovalStatus(EnumConst.ApprovalStatus_Approved);
		group.setActiveStatus(EnumConst.ActiveStatus_Active);
		contact.setName(csvData.getCellValue("Name", row));
		contact.setVision(csvData.getCellValue("Vision", row));
		contact.setDescription(csvData.getCellValue("Description", row));
		group.setDateOfEstablishment(csvData.getCellValue("Date Of Establishment", row));

		// Add Contact Info
		contact.setAddress(csvData.getCellValue("Address", row));
		contact.setVillage(csvData.getCellValue("Village", row));
		contact.setGrampanchayat(csvData.getCellValue("Grampanchayat", row));
		contact.setTaluka(csvData.getCellValue("Tehsil", row));
		contact.setPinCode(csvData.getCellValue("Pin Code", row));
		contact.setDistrict(csvData.getCellValue("District", row));
		contact.setPhone(csvData.getCellValue("Phone", row));
		contact.setPriMobile(csvData.getCellValue("Primary Mobile", row));
		contact.setSecMobile(csvData.getCellValue("Secondary Mobile", row));
		contact.setEmail(csvData.getCellValue("Email", row));
		group.addContact(contact);
		
		// Add Bank Info
		BankProfileREST bankProfile = new BankProfileREST();
		bankProfile.setIfcsCode(csvData.getCellValue("IFSC Code", row));
		bankProfile.setBankName(csvData.getCellValue("Bank Name", row));
		bankProfile.setBranchName(csvData.getCellValue("Branch", row));
		bankProfile.setBankLoanIntRate(DataUtil.toFloat(csvData.getCellValue("Bank Loan Interest Rate", row)));
		bankProfile.setBankFdIntRate(DataUtil.toFloat(csvData.getCellValue("Bank FD Interest Rate", row)));
		bankProfile.setBankRating(DataUtil.toFloat(csvData.getCellValue("Bank Rating", row)));
		bankProfile.setLoanProcessingFee(DataUtil.toInteger(csvData.getCellValue("Loan Processing Fee", row)));
		bankProfile.setLoanApplicationCharges(DataUtil.toInteger(csvData.getCellValue("Loan Application Charges", row)));
		bankProfile.setLoanPrepaymentCharges(DataUtil.toInteger(csvData.getCellValue("Loan Prepayment Charges", row)));
		bankProfile.setLatePaymentCharges(DataUtil.toInteger(csvData.getCellValue("Late Payment Charges", row)));
		bankProfile.setMissedPaymentCharges(DataUtil.toInteger(csvData.getCellValue("Missed Payment Charges", row)));
		group.setBankProfile(bankProfile);
		
		return group;
	}	
	
	public static boolean isGroupBankAccountCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("Bank Account Number") &&
				csvData.isColumnPresent("Account Name") &&
				csvData.isColumnPresent("Bank Account Type") &&
				csvData.isColumnPresent("IFSC Code"));
	}
	
	public static GBankAccountREST buildGroupBankAccount(CSVDataCollector csvData, int row) throws BadRequestException {
		GBankAccountREST account = new GBankAccountREST();
		account.setBankAccountId(DataUtil.toLong(csvData.getCellValue("bankAccountNo", row)));
		account.setAccountNumber(csvData.getCellValue("Bank Account Number", row));
		account.setAccountName(csvData.getCellValue("Account Name", row));
		account.setBankAccountType(csvData.getCellValue("Bank Account Type", row));
		account.setBankAccountType(csvData.getCellValue("Bank Account Type", row));
		account.setBankName(csvData.getCellValue("Bank Name", row));
		account.setBankBranchName(csvData.getCellValue("Branch", row));
		account.setIfcsCode(csvData.getCellValue("IFSC Code", row));
		account.setClearBalanceAm(DataUtil.toBigDecimal(csvData.getCellValue("Balance", row)));
		return account;
	}	
}
