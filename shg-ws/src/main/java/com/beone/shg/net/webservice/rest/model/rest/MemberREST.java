package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MBankAccount;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.MemberContact;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.persistence.util.LoanData;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.LoanEMIPlan;
import com.beone.shg.net.webservice.rest.model.resp.MRoleValue;
import com.beone.shg.net.webservice.rest.model.resp.MemberOtherAcInfo;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberREST {

	private long memberAcNo;
	private long parentGroupAcNo;
	private List<Long> mappingGroupAcNos;
	private long recommendedByMemberAcNo;
	private long approvedByMemberAcNo;
	private String memberName;
	private String displayAddress;
	private String recommendedByMemberName;
	private String approvedByMemberName;
	private String approvalStatus;
	private String activeStatus;
	private String mrole;
	private String roleCategory;
	private String passcode;
	private byte passSet;
	private String gender;
	private Float percentageProfileComplete;
	private String uidaiNo;
	private String panCardNo;
	private String voterIdNo;
	private String drivingLicenseNo;
	private String photoUrl;
	private String statusMessage;
	private String location;
	private String dateOfEnroll;
	private String dateOfBirth;
	private String dateOfAnni;
	private String dateOfClosure;
	private BigDecimal monthlySaving;
	private int noPlannedSavingInst;
	private String errorMessages;
	private long lastLoggedInTs;
	private MemberAcREST account;
	private MMessageREST mmessage;
	private List<Attachment> attachments;
	private List<MemberContactREST> contacts;
	private List<BankAccountREST> bankAccounts;
	private List<MemberSavingAcREST> memberSavingAc;
	private List<MemberLoanAcREST> memberLoanAc;
	private MemberOtherAcInfo memberOtherAcInfo;
	private List<Transaction> transactions;
	private Map<String, String> displayNames;

	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNos) {
		this.memberAcNo = memberAcNos;
	}
	public long getParentGroupAcNo() {
		return parentGroupAcNo;
	}
	public void setParentGroupAcNo(long parentGroupAcNo) {
		this.parentGroupAcNo = parentGroupAcNo;
	}
	public List<Long> getMappingGroupAcNos() {
		return mappingGroupAcNos;
	}
	public void setMappingGroupAcNos(List<Long> mappingGroupAcNos) {
		this.mappingGroupAcNos = mappingGroupAcNos;
	}
	public void addMappingGroupAcNo(Long mappingGroupAcNo) {
		if(this.mappingGroupAcNos == null) {
			this.mappingGroupAcNos = new ArrayList<Long>();
		}
		this.mappingGroupAcNos.add(mappingGroupAcNo);
	}
	public long getRecommendedByMemberAcNo() {
		return recommendedByMemberAcNo;
	}
	public void setRecommendedByMemberAcNo(long recommendedByMemberAcNo) {
		this.recommendedByMemberAcNo = recommendedByMemberAcNo;
	}
	public long getApprovedByMemberAcNo() {
		return approvedByMemberAcNo;
	}
	public void setApprovedByMemberAcNo(long approvedByMemberAcNo) {
		this.approvedByMemberAcNo = approvedByMemberAcNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getDisplayAddress() {
		return displayAddress;
	}
	public void setDisplayAddress(String displayAddress) {
		this.displayAddress = displayAddress;
	}
	public String getRecommendedByMemberName() {
		return recommendedByMemberName;
	}
	public void setRecommendedByMemberName(String recommendedByMemberName) {
		this.recommendedByMemberName = recommendedByMemberName;
	}
	public String getApprovedByMemberName() {
		return approvedByMemberName;
	}
	public void setApprovedByMemberName(String approvedByMemberName) {
		this.approvedByMemberName = approvedByMemberName;
	}
	public String getApprovalStatus() {
		return approvalStatus;
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
	public String getMrole() {
		return mrole;
	}
	public void setMrole(String mrole) {
		this.mrole = mrole;
	}
	public String getRoleCategory() {
		return roleCategory;
	}
	public void setRoleCategory(String roleCategory) {
		this.roleCategory = roleCategory;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public byte getPassSet() {
		return passSet;
	}
	public void setPassSet(byte passSet) {
		this.passSet = passSet;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Float getPercentageProfileComplete() {
		return percentageProfileComplete;
	}
	public void setPercentageProfileComplete(Float percentageProfileComplete) {
		this.percentageProfileComplete = percentageProfileComplete;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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
	public String getUidaiNo() {
		return uidaiNo;
	}
	public void setUidaiNo(String uidaiNo) {
		this.uidaiNo = uidaiNo;
	}
	public String getPanCardNo() {
		return panCardNo;
	}
	public void setPanCardNo(String panCardNo) {
		this.panCardNo = panCardNo;
	}
	public String getVoterIdNo() {
		return voterIdNo;
	}
	public void setVoterIdNo(String voterIdNo) {
		this.voterIdNo = voterIdNo;
	}
	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}
	public String getDateOfEnroll() {
		return dateOfEnroll;
	}
	public void setDateOfEnroll(String dateOfEnroll) {
		this.dateOfEnroll = dateOfEnroll;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfAnni() {
		return dateOfAnni;
	}
	public void setDateOfAnni(String dateOfAnni) {
		this.dateOfAnni = dateOfAnni;
	}
	public String getDateOfClosure() {
		return dateOfClosure;
	}
	public void setDateOfClosure(String dateOfClosure) {
		this.dateOfClosure = dateOfClosure;
	}
	public BigDecimal getMonthlySaving() {
		return monthlySaving;
	}
	public void setMonthlySaving(BigDecimal monthlySaving) {
		this.monthlySaving = monthlySaving;
	}
	public int getNoPlannedSavingInst() {
		return noPlannedSavingInst;
	}
	public void setNoPlannedSavingInst(int noPlannedSavingInst) {
		this.noPlannedSavingInst = noPlannedSavingInst;
	}
	public String getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}
	public long getLastLoggedInTs() {
		return lastLoggedInTs;
	}
	public void setLastLoggedInTs(long lastLoggedInTs) {
		this.lastLoggedInTs = lastLoggedInTs;
	}
	public MemberAcREST getAccount() {
		return account;
	}
	public void setAccount(MemberAcREST account) {
		this.account = account;
	}
	public MMessageREST getMmessage() {
		return mmessage;
	}
	public void setMmessage(MMessageREST mmessage) {
		this.mmessage = mmessage;
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
	public List<MemberContactREST> getContacts() {
		return contacts;
	}
	public void setContacts(List<MemberContactREST> contacts) {
		this.contacts = contacts;
	}
	public void addContact(MemberContactREST contact) {
		if(this.contacts == null) {
			this.contacts = new ArrayList<MemberContactREST>();
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
	public List<MemberSavingAcREST> getMemberSavingAc() {
		return memberSavingAc;
	}
	public void setMemberSavingAc(List<MemberSavingAcREST> memberSavingAc) {
		this.memberSavingAc = memberSavingAc;
	}
	public void addMemberSavingAc(MemberSavingAcREST memberSavingAc) {
		if(this.memberSavingAc == null) {
			this.memberSavingAc = new ArrayList<MemberSavingAcREST>();
		}
		this.memberSavingAc.add(memberSavingAc);
	}	

	public List<MemberLoanAcREST> getMemberLoanAc() {
		return memberLoanAc;
	}
	public void setMemberLoanAc(List<MemberLoanAcREST> memberLoanAc) {
		this.memberLoanAc = memberLoanAc;
	}
	public void addMemberLoanAc(MemberLoanAcREST memberLoanAc) {
		if(this.memberLoanAc == null) {
			this.memberLoanAc = new ArrayList<MemberLoanAcREST>();
		}
		this.memberLoanAc.add(memberLoanAc);
	}	

	public MemberOtherAcInfo getMemberOtherAcInfo() {
		return memberOtherAcInfo;
	}
	public void setMemberOtherAcInfo(MemberOtherAcInfo memberOtherAcInfo) {
		this.memberOtherAcInfo = memberOtherAcInfo;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addTransaction(Transaction transaction) {
		if(transactions == null) {
			transactions = new ArrayList<Transaction>();
		}
		this.transactions.add(transaction);
	}

	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
	public Map<String,String> toStringInfo() {

		Map<String,String> memberInfo = new HashMap<String,String>();
		memberInfo.put("memberAcNo", Long.toString(memberAcNo));
		memberInfo.put("name", contacts.get(0).getNameTitle() + " " + contacts.get(0).getFirstName() + " " + contacts.get(0).getLastName());
		memberInfo.put("activeStatus", activeStatus);
		memberInfo.put("mrole", mrole);
		memberInfo.put("password", passcode);

		return memberInfo;
	}
	
	public static boolean isMemberCSVValid(CSVDataCollector csvData) throws BadRequestException {
		
		return (csvData.isColumnPresent("Name Title") &&
				csvData.isColumnPresent("First Name") &&
				csvData.isColumnPresent("Middle Name") &&
				csvData.isColumnPresent("Last Name") &&
				csvData.isColumnPresent("Active Status") &&
				csvData.isColumnPresent("Member Role"));
	}

	public static MemberREST buildMember(long groupAcNo, CSVDataCollector csvData, int row, GAc gAc) throws BadRequestException {

		MemberREST member = new MemberREST();
		member.setMemberAcNo(DataUtil.toLong(csvData.getCellValue("memberAcNo", row)));
		member.setParentGroupAcNo(groupAcNo);		
		MemberContactREST contact = new MemberContactREST();

		// Add Name Info
		contact.setLang(EnumConst.Lang_English);
		contact.setNameTitle(csvData.getCellValue("Name Title", row));
		contact.setFirstName(csvData.getCellValue("First Name", row));
		contact.setMiddleName(csvData.getCellValue("Middle Name", row));
		contact.setLastName(csvData.getCellValue("Last Name", row));

		//		member.setRecommendedByMemberAcNo(new Long(csvData.getCellValue("", row)));
		//		member.setPhotoUrl(csvData.getCellValue("", row));

		String role = csvData.getCellValue("Member Role", row);
		String status = csvData.getCellValue("Active Status", row);
		boolean isCoreMember = EnumUtil.isCoreMember(role);
		boolean isAssociateMember = EnumUtil.isAssociateMember(role);
		boolean isActive = (status.equals(EnumConst.ActiveStatus_Active));
		
		// Add Enrollment Info
		member.setApprovalStatus(EnumConst.ApprovalStatus_Approved);
		member.setActiveStatus(status);
		member.setMrole(role);
		member.setPasscode(csvData.getCellValue("Passcode", row));
		member.setUidaiNo(csvData.getCellValue("Adhaar Card No", row));
		member.setPanCardNo(csvData.getCellValue("PAN Card No", row));
		member.setVoterIdNo(csvData.getCellValue("Voter Id No", row));
		member.setDrivingLicenseNo(csvData.getCellValue("Ration Card No", row));
		member.setGender(csvData.getCellValue("Gender", row));
		member.setDateOfEnroll(csvData.getCellValue("Date Of Enrollment", row));
		String approvedDate = csvData.getCellValue("Approved Date", row);
		member.setDateOfBirth(csvData.getCellValue("Date Of Birth", row));
		member.setDateOfAnni(csvData.getCellValue("Date Of Anniversary", row));
		member.setDateOfClosure(csvData.getCellValue("Date Of Closure", row));

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
		member.addContact(contact);

		// Add Bank Info
		if(!csvData.getCellValue("Bank Account Number", row).isEmpty()) {
			BankAccountREST account = new BankAccountREST();
			account.setAccountNumber(csvData.getCellValue("Bank Account Number", row));
			account.setAccountName(csvData.getCellValue("Account Name", row));
			account.setBankAccountType(csvData.getCellValue("Bank Account Type", row));
			account.setBankName(csvData.getCellValue("Bank Name", row));
			account.setBankBranchName(csvData.getCellValue("Bank Branch Name", row));
			account.setIfcsCode(csvData.getCellValue("IFCS Code", row));
			member.addBankAccount(account);
		} 

		// Member Account 
		{
			BigDecimal plannedMonthlySaving = DataUtil.toBigDecimal(csvData.getCellValue("Planned Monthly Saving Amount", row));
			BigDecimal savedAm = DataUtil.toBigDecimal(csvData.getCellValue("Saved Amount", row));
			BigDecimal totalIntEnAm = DataUtil.toBigDecimal(csvData.getCellValue("Provisional Interest Earned", row));
			BigDecimal returnedSavedAm = DataUtil.toBigDecimal(csvData.getCellValue("Returned Saved Amount", row));
			BigDecimal returnedIntEnAm = DataUtil.toBigDecimal(csvData.getCellValue("Returned Interest on Saving", row));
			int noOfMonths = DataUtil.toInteger(csvData.getCellValue("No Saving Installments Paid", row));
			if(isActive && noOfMonths <= 0) {
				noOfMonths = DateUtil.getNoOfMonthsPassed(DateUtil.convertStringToDate(member.getDateOfEnroll()));
			}
			if(isActive && savedAm.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0 &&
					plannedMonthlySaving.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0 && 
					noOfMonths > 0) {
				savedAm = BDUtil.multiply(plannedMonthlySaving, noOfMonths);

				if(isCoreMember) {
					gAc.setCMSavedAm(BDUtil.add(gAc.getCMSavedAm(), savedAm));
				} else if(isAssociateMember) {
					gAc.setAMSavedAm(BDUtil.add(gAc.getAMSavedAm(), savedAm));
				}
			}
			if(returnedSavedAm.compareTo(savedAm) > DataUtil.ZERO_INTEGER) {				
				if(isCoreMember) {
					gAc.setCMSavedAm(BDUtil.add(gAc.getCMSavedAm(), BDUtil.sub(returnedSavedAm, savedAm)));
				} else if(isAssociateMember) {
					gAc.setAMSavedAm(BDUtil.add(gAc.getAMSavedAm(), BDUtil.sub(returnedSavedAm, savedAm)));
				}

				savedAm = returnedSavedAm;
			}
			if(returnedIntEnAm.compareTo(totalIntEnAm) > DataUtil.ZERO_INTEGER) {				
				if(isCoreMember) {
					gAc.setCMProvIntEnAm(BDUtil.add(gAc.getCMProvIntEnAm(), BDUtil.sub(returnedIntEnAm, totalIntEnAm)));
				} else if(isAssociateMember) {
					gAc.setAMProvIntEnAm(BDUtil.add(gAc.getAMProvIntEnAm(), BDUtil.sub(returnedIntEnAm, totalIntEnAm)));
				}
				totalIntEnAm = returnedIntEnAm;
			}

			{
				MemberAcREST memberAc = new MemberAcREST();
				memberAc.setCreditRating(DataUtil.ZERO_FLOAT);
				memberAc.setMeetingAttendance(DataUtil.ZERO_FLOAT);
				memberAc.setPlannedMonthlySavingAm(plannedMonthlySaving);
				memberAc.setSavedAm(savedAm);
				memberAc.setOutstandingSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("Outstanding Saving", row)));
				memberAc.setProvIntEnAm(totalIntEnAm);
				memberAc.setPrevMonthIntEnAm(DataUtil.toBigDecimal(csvData.getCellValue("Last Month Interest Earned", row)));
				memberAc.setReturnedSavedAm(returnedSavedAm);
				memberAc.setReturnedIntEnAm(returnedIntEnAm);
				memberAc.setDividedProfitDeclaredAm(DataUtil.toBigDecimal(csvData.getCellValue("Divided Profit Declared", row)));		
				memberAc.setDividedProfitPaidAm(DataUtil.toBigDecimal(csvData.getCellValue("Divided Profit Paid", row)));		
				memberAc.setNoOfLoans(DataUtil.toInteger(csvData.getCellValue("Total No of Loans", row)));
				memberAc.setNoOfActiveLoans(DataUtil.toInteger(csvData.getCellValue("Active No of Loans", row)));
				memberAc.setLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Total Loan Amount", row)));
				memberAc.setRecLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Total Recovered Loan Amount", row)));
				memberAc.setRecIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Total Recovered Interest on Loan", row)));
				memberAc.setProjIntOnLoanAm(DataUtil.toBigDecimal(csvData.getCellValue("Projected Interest on Loan", row)));
				memberAc.setBadDeptClosedAm(DataUtil.ZERO_BIG_DECIMAL);
				memberAc.setRecPenaltyAm(DataUtil.toBigDecimal(csvData.getCellValue("Total Recovered Penalty", row)));
				memberAc.setPendingPenaltyAm(DataUtil.ZERO_BIG_DECIMAL);
				memberAc.setMeetingAttended(DataUtil.ZERO_INTEGER);
				memberAc.setMeetingMissed(DataUtil.ZERO_INTEGER);
				memberAc.setLastUpdatedTs(DateUtil.getCurrentTimeDate());

				member.setAccount(memberAc);
			}
			{
				MemberSavingAcREST memberSavingAc = new MemberSavingAcREST();
				memberSavingAc.setAccountStatus(EnumConst.AccountStatus_Active);
				memberSavingAc.setRecoveryPeriod(EnumConst.RecoveryPeriod_Monthly);
				memberSavingAc.setInstallmentAm(plannedMonthlySaving);
				memberSavingAc.setSavedAm(savedAm);
				memberSavingAc.setCumulativeSavedAm(DataUtil.toBigDecimal(csvData.getCellValue("Saved Amount", row)));
				memberSavingAc.setTotalIntEnAm(totalIntEnAm);
				memberSavingAc.setPrevMonthIntAm(DataUtil.toBigDecimal(csvData.getCellValue("Last Month Interest Earned", row)));
				memberSavingAc.setCurrentFyIntEnAm(DataUtil.toBigDecimal(csvData.getCellValue("Current FY Interest Earned", row)));
				memberSavingAc.setReturnedSavedAm(returnedSavedAm);
				memberSavingAc.setReturnedIntEnAm(returnedIntEnAm);
				memberSavingAc.setIntReturnedAm(DataUtil.toBigDecimal(csvData.getCellValue("Returned Interest on Saving", row)));
				memberSavingAc.setExpectedNoOfInst(9999);
				memberSavingAc.setNoOfInstPaid(noOfMonths);

				memberSavingAc.setRequestedDate(member.getDateOfEnroll());
				memberSavingAc.setApprovedDate(approvedDate);
				memberSavingAc.setActualStartDate(approvedDate);
				memberSavingAc.setInstStartDate(approvedDate);
				//		memberSavingAc.setExpCompletionDate(expCompletionDate);
				memberSavingAc.setClosureDate(member.getDateOfClosure());

				member.addMemberSavingAc(memberSavingAc);
			}
		}

		{
			BigDecimal principle = DataUtil.toBigDecimal(csvData.getCellValue("Active Loan Amount", row));
			if(principle.compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
				BigDecimal recPrinciple = DataUtil.toBigDecimal(csvData.getCellValue("Active Recovered Loan Amount", row));
				BigDecimal pendingPrinciple = BDUtil.sub(principle, recPrinciple);
				BigDecimal recInterest = DataUtil.toBigDecimal(csvData.getCellValue("Active Recovered Interest on Loan", row));
				BigDecimal projInterest = DataUtil.toBigDecimal(csvData.getCellValue("Projected Interest on Loan", row));
				BigDecimal installment = DataUtil.toBigDecimal(csvData.getCellValue("Loan Installment", row));
				int paidLoanInstallments = DataUtil.toInteger(csvData.getCellValue("No Loan Installments Paid", row));
				int pendingLoanInstallments = DataUtil.toInteger(csvData.getCellValue("Pending Loan Installments", row));
				float rateOfInterest = DataUtil.toFloat(csvData.getCellValue("Rate of Interest", row));
				String disbursementDate = csvData.getCellValue("Loan Disbursement Date", row);
				String expLoanCompletionDate = csvData.getCellValue("Expected Loan Completion Date", row);

				if(rateOfInterest == DataUtil.ZERO_FLOAT) {
					rateOfInterest = 24.0f;
				}
				if(disbursementDate == DataUtil.EMPTY_STRING || disbursementDate.isEmpty()) {
					disbursementDate = DateUtil.convertDateToString(DateUtil.getEndOfMonthLastMonth());

					if(pendingLoanInstallments <= 0) {
						pendingLoanInstallments = BDUtil.divide(pendingPrinciple, installment).intValue();
					}
				}
				else {
					int maxLoanInstallments = BDUtil.divide(pendingPrinciple, installment).intValue();
					if(pendingLoanInstallments <= 0) {
						int paidInstemp = DateUtil.getNoOfMonthsPassed(DateUtil.convertStringToDate(disbursementDate));
						if(paidInstemp < 0 || paidInstemp > 60) {
							paidInstemp = 0;
						} else {
							paidLoanInstallments = paidInstemp;
						}
						pendingLoanInstallments = (maxLoanInstallments - paidLoanInstallments);
					}
					if(paidLoanInstallments > 0) {
						recPrinciple = BDUtil.multiply(installment, paidLoanInstallments);
						pendingPrinciple = BDUtil.sub(principle, recPrinciple);
					}
				}
				if(recInterest.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0 || 
						projInterest.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
					LoanEMIPlan plan = new LoanEMIPlan();
					plan.setLoanCalculation(EnumConst.LoanCalculation_Reducing_Interest);
					plan.setPrinciple(principle);
					plan.setRateOfInterest(rateOfInterest);
					plan.setStartDate(disbursementDate);
					plan.setNoOfEMIs(paidLoanInstallments + pendingLoanInstallments);
					plan.setDueDay(10);
					LoanData data = GenAlgoUtil.getLoanPlanningAggregate(plan, paidLoanInstallments);
					
					if(recInterest.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
						recInterest = data.getRecInterest();
						
						if(isCoreMember) {
							gAc.setCMRecIntOnLoanAm(BDUtil.add(gAc.getCMRecIntOnLoanAm(), recInterest));
						} else if(isAssociateMember) {
							gAc.setAMRecIntOnLoanAm(BDUtil.add(gAc.getAMRecIntOnLoanAm(), recInterest));
						}
					}
					if(projInterest.compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
						projInterest = data.getProjInterest();
						if(isCoreMember) {
							gAc.setCMProjIntOnLoanAm(BDUtil.add(gAc.getCMProjIntOnLoanAm(), projInterest));
						} else if(isAssociateMember) {
							gAc.setAMProjIntOnLoanAm(BDUtil.add(gAc.getAMProjIntOnLoanAm(), projInterest));
						}
					}
				}
				if(member.getAccount().getLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
					member.getAccount().setLoanAm(principle);				
					if(isCoreMember) {
						gAc.setCMLoanAm(BDUtil.add(gAc.getCMLoanAm(), principle));
					} else if(isAssociateMember) {
						gAc.setAMLoanAm(BDUtil.add(gAc.getAMLoanAm(), principle));
					}
				}
				if(member.getAccount().getRecLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
					member.getAccount().setRecLoanAm(recPrinciple);				
				}
				if(member.getAccount().getRecIntOnLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
					member.getAccount().setRecIntOnLoanAm(recInterest);				
				}
				if(member.getAccount().getProjIntOnLoanAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
					member.getAccount().setProjIntOnLoanAm(projInterest);				
				}
				if(member.getAccount().getNoOfLoans() <= 0) {
					member.getAccount().setNoOfLoans(1);				
					if(isCoreMember) {
						gAc.setCMNoOfLoans(gAc.getCMNoOfLoans() + 1);
					} else if(isAssociateMember) {
						gAc.setAMNoOfLoans(gAc.getAMNoOfLoans() + 1);
					}
				}
				if(member.getAccount().getNoOfActiveLoans() <= 0) {
					member.getAccount().setNoOfActiveLoans(1);				
					if(isCoreMember) {
						gAc.setCMNoOfActiveLoans(gAc.getCMNoOfActiveLoans() + 1);
					} else if(isAssociateMember) {
						gAc.setAMNoOfActiveLoans(gAc.getAMNoOfActiveLoans() + 1);
					}
				}

				if(expLoanCompletionDate == DataUtil.EMPTY_STRING || expLoanCompletionDate.isEmpty()) {
					Date dueDate = DateUtil.getDueDate(DateUtil.convertStringToDate(disbursementDate), 10, pendingLoanInstallments);
					expLoanCompletionDate = DateUtil.convertDateToString(dueDate);
				}

				MemberLoanAcREST memberLoanAc = new MemberLoanAcREST();

				memberLoanAc.setLoanType(EnumConst.FundType_Consumption_Loan);
				memberLoanAc.setLoanCalculation(EnumConst.LoanCalculation_Reducing_Interest);
				if(csvData.getCellValue("Loan Closure Date", row).isEmpty()) {
					memberLoanAc.setAccountStatus(EnumConst.AccountStatus_Active);
				} else {
					memberLoanAc.setAccountStatus(EnumConst.AccountStatus_Complete);
				}
				memberLoanAc.setRecoveryPeriod(EnumConst.RecoveryPeriod_Monthly);
				memberLoanAc.setLoanSource(EnumConst.LoanSource_SHG);

				memberLoanAc.setPrinciple(principle);
				memberLoanAc.setPendingPrinciple(pendingPrinciple);
				memberLoanAc.setRecoveredInterest(recInterest);
				memberLoanAc.setProjectedInterest(projInterest);
				memberLoanAc.setInstallment(installment);
				memberLoanAc.setPreEmiInterest(DataUtil.ZERO_BIG_DECIMAL);
				memberLoanAc.setPendingInterestDue(DataUtil.ZERO_BIG_DECIMAL);
				memberLoanAc.setRateOfInterest(rateOfInterest);
				memberLoanAc.setStartupNoOfInst(DataUtil.ZERO_INTEGER);
				memberLoanAc.setExpNoOfInst(pendingLoanInstallments);
				memberLoanAc.setNoOfInstPaid(paidLoanInstallments);
				memberLoanAc.setNoOfInsallLate(DataUtil.ZERO_INTEGER);
				memberLoanAc.setNoOfInsallMissed(DataUtil.ZERO_INTEGER);
				memberLoanAc.setRequestedDate(disbursementDate);
				memberLoanAc.setApprovedDate(disbursementDate);
				memberLoanAc.setDisbursementDate(disbursementDate);
				memberLoanAc.setInstStartDate(disbursementDate);
				memberLoanAc.setExpCompletionDate(expLoanCompletionDate);
				memberLoanAc.setClosureDate(csvData.getCellValue("Loan Closure Date", row));

				member.addMemberLoanAc(memberLoanAc);
			}
		}

		{
			MemberOtherAcInfo otherAcInfo = new MemberOtherAcInfo();

			List<MemberOtherAcInfo.PenaltyDetail> penaltyDetails = new ArrayList<MemberOtherAcInfo.PenaltyDetail>();

			BigDecimal lateFee = DataUtil.toBigDecimal(csvData.getCellValue("Late Fee", row));
			BigDecimal registrationFee = DataUtil.toBigDecimal(csvData.getCellValue("Registration Fee", row));
			BigDecimal loanProcessingFee = DataUtil.toBigDecimal(csvData.getCellValue("Loan Processing Fee", row));
			BigDecimal chequeBouncingPenalty = DataUtil.toBigDecimal(csvData.getCellValue("Cheque Bouncing Penalty", row));

			if(lateFee != DataUtil.ZERO_BIG_DECIMAL) {
				penaltyDetails.add(new MemberOtherAcInfo.PenaltyDetail(EnumConst.TxType_Late_Fee, lateFee));
			}
			if(registrationFee != DataUtil.ZERO_BIG_DECIMAL) {
				penaltyDetails.add(new MemberOtherAcInfo.PenaltyDetail(EnumConst.TxType_Registration_Fee, registrationFee));
			}
			if(loanProcessingFee != DataUtil.ZERO_BIG_DECIMAL) {
				penaltyDetails.add(new MemberOtherAcInfo.PenaltyDetail(EnumConst.TxType_Loan_Processing_Fee, loanProcessingFee));
			}
			if(chequeBouncingPenalty != DataUtil.ZERO_BIG_DECIMAL) {
				penaltyDetails.add(new MemberOtherAcInfo.PenaltyDetail(EnumConst.TxType_Cheque_Bouncing_Penalty, chequeBouncingPenalty));
			}

			otherAcInfo.setPenaltyDetail(penaltyDetails);

			member.setMemberOtherAcInfo(otherAcInfo);
		}

		return member;
	}	

	public static MemberREST buildMemberProfile(MProfile mProfile) {
		MemberREST member = new MemberREST();
		
		loadMemberProfile(mProfile, member);
		loadMemberContacts(mProfile, member);
		
		return member;
	}
	
	public static void loadMemberProfile(MProfile mProfile, MemberREST member) {

		member.setMemberAcNo(mProfile.getMemberAcNo());
		member.setParentGroupAcNo(ConversionUtil.getGroupAcFromMember(mProfile.getMemberAcNo()));
		member.setApprovalStatus(EnumCache.getNameOfEnumValue(EnumConst.ApprovalStatus, mProfile.getApprovalStatusId()));
		member.setActiveStatus(EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId()));
		MRoleValue mRole = EnumCache.getMRoleValue(mProfile.getMroleId());
		member.setMrole(mRole.getRole());
		member.setPassSet(mProfile.getPassSet());
		member.setRoleCategory(mRole.getRoleCategory());
		member.setPercentageProfileComplete(mProfile.getPercentageProfileComplete());

		if(mProfile.getApprovedByMember() > 0) {
			member.setApprovedByMemberAcNo(mProfile.getApprovedByMember());
		}
		if(mProfile.getRecommendedByMember() > 0) {
			member.setRecommendedByMemberAcNo(mProfile.getRecommendedByMember());
		}
		if(mProfile.getDateOfEnroll() != null) {
			member.setDateOfEnroll(DateUtil.convertDateToString(mProfile.getDateOfEnroll()));
		}
		if(mProfile.getDateOfBirth() != null) {
			member.setDateOfBirth(DateUtil.convertDateToString(mProfile.getDateOfBirth()));
		}
		if(mProfile.getDateOfAnni() != null) {
			member.setDateOfAnni(DateUtil.convertDateToString(mProfile.getDateOfAnni()));
		}
		if(mProfile.getLastLoggedInTs() != null) {
			member.setLastLoggedInTs(mProfile.getLastLoggedInTs().getTime());
		}
		if(mProfile.getDateOfClosure() != null) {
			member.setDateOfClosure(DateUtil.convertDateToString(mProfile.getDateOfClosure()));
		}
		if(mProfile.getGender() != null) {
			member.setGender(mProfile.getGender());
		}
		if(mProfile.getUidaiNo() != null) {
			member.setUidaiNo(mProfile.getUidaiNo());
		}
		if(mProfile.getPanCardNo() != null) {
			member.setPanCardNo(mProfile.getPanCardNo());
		}
		if(mProfile.getVoterIdNo() != null) {
			member.setVoterIdNo(mProfile.getVoterIdNo());
		}
		if(mProfile.getDrivingLicenseNo() != null) {
			member.setDrivingLicenseNo(mProfile.getDrivingLicenseNo());
		}
		if(mProfile.getPhotoUrl() != null) {
			member.setPhotoUrl(mProfile.getPhotoUrl());
		}
		if(mProfile.getDescription() != null) {
			member.setStatusMessage(mProfile.getDescription());
		}
		if(mProfile.getLocation() != null) {
			member.setLocation(mProfile.getLocation());
		}
		if(mProfile.getErrorMessages() != null) {
			member.setErrorMessages(mProfile.getErrorMessages());
		}
		member.setAttachments(Attachment.buildAttachments(mProfile.getAttachmentUrl()));
	}

	public static void loadMemberAccount(MProfile mProfile, MemberREST member) {

		MemberAcREST memberAc = null;

		for(MAc mAc: mProfile.getMAcs()) {
			memberAc = MemberAcREST.convertMemberAccount(mAc);
			break;
		}

		member.setAccount(memberAc);	
	}

	public static void loadMemberBankAccounts(MProfile mProfile, MemberREST member) {

		List<BankAccountREST> bankAccounts = new ArrayList<BankAccountREST>();

		for(MBankAccount mBankAccount : mProfile.getMBankAccounts()) {
			BankAccountREST bankAccount = new BankAccountREST();

			bankAccount.setBankAccountId(mBankAccount.getBankAccountNo());
			bankAccount.setAccountNumber(mBankAccount.getBankAccount().getAccountNumber());
			bankAccount.setAccountName(mBankAccount.getBankAccount().getAccountName());
			bankAccount.setBankAccountType(EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, mBankAccount.getBankAccount().getBankAccountTypeId()));
			bankAccount.setBankName(mBankAccount.getBankAccount().getBankProfile().getBankName());
			bankAccount.setBankBranchName(mBankAccount.getBankAccount().getBankProfile().getBranchName());
			bankAccount.setIfcsCode(mBankAccount.getBankAccount().getBankProfile().getIfcsCode());
			bankAccount.setAttachments(Attachment.buildAttachments(mBankAccount.getBankAccount().getAttachmentUrl()));

			bankAccounts.add(bankAccount);
		}

		member.setBankAccounts(bankAccounts);	
	}

	public static void loadMemberContacts(MProfile mProfile, MemberREST member) {

		List<MemberContactREST> contacts = new ArrayList<MemberContactREST>();

		for(MemberContact mContact : mProfile.getMemberContacts()) {
			MemberContactREST contact = new MemberContactREST();

			contact.setContactId(mContact.getContactId());
			contact.setLang(mContact.getLang().getLang());
			contact.setNameTitle(mContact.getNameTitle().getTitle());
			contact.setFirstName(mContact.getFirstName());
			contact.setMiddleName(mContact.getMiddleName());
			contact.setLastName(mContact.getLastName());
			contact.setAddress(mContact.getContact().getAddress());
			contact.setVillage(mContact.getContact().getVillage());
			contact.setGrampanchayat(mContact.getContact().getGrampanchayat());
			contact.setTaluka(mContact.getContact().getTaluka());
			contact.setPinCode(mContact.getContact().getPinCode());
			contact.setDistrict(mContact.getContact().getDistrict().getDistrict());
			contact.setDistrictId(mContact.getContact().getDistrict().getDistrictId());
			contact.setDistrictCode(mContact.getContact().getDistrict().getDistrictCode());
			contact.setDivision(mContact.getContact().getDistrict().getDivision());
			contact.setState(mContact.getContact().getDistrict().getState());
			contact.setPhone(mContact.getContact().getPhone());
			contact.setPriMobile(mContact.getContact().getPriMobile());
			contact.setSecMobile(mContact.getContact().getSecMobile());
			contact.setEmail(mContact.getContact().getEmail());

			contacts.add(contact);
			
			if(mContact.getLang().getLang().equals(EnumConst.Lang_English)) {
				member.setMemberName(contact.getNameTitle() + " " + contact.getFirstName() + " " + contact.getLastName());
				member.setDisplayAddress(contact.getAddress() + ", " + ConversionUtil.getContactPlace(mContact.getContact()));
			}
		}

		member.setContacts(contacts);
	}
}
