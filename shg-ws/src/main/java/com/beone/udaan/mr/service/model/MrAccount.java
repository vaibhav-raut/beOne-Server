package com.beone.udaan.mr.service.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.csv.CSVDataCollector;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.model.rest.MemberContactREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;
import com.beone.udaan.mr.config.EnumConstMr;
import com.beone.udaan.mr.persistence.model.PMAc;
import com.beone.udaan.mr.persistence.support.EnumCacheMr;

public class MrAccount {

	private long memberAcNo;
	private MemberREST memberInfo;
	private String mrRole;
	private String mrStatus;
	private String mrType;
	private BigDecimal registrationPaidAm;
	private BigDecimal registrationPendingAm;
	private BigDecimal depositPaidAm;
	private BigDecimal depositPendingAm;
	private BigDecimal depositReturnAm;
	private BigDecimal creditLimitAm;
	private BigDecimal totalCollectedAm;
	private BigDecimal totalPaidCollectedAm;
	private BigDecimal currentCollectedAm;
	private BigDecimal soldPaidAm;
	private BigDecimal soldPendingAm;
	private BigDecimal paidInterestPenaltyAm;
	private BigDecimal pendingInterestPenaltyAm;
	private long lastVisitOn;
	private long interestCalculatedOn;
	private BigDecimal currentStockAm;
	private BigDecimal currentStockDiscountAm;
	private BigDecimal currentStockUnsettledAm;
	private BigDecimal currentStockToReturnAm;
	private int currentStockNo;
	private int currentStockDiscountNo;
	private int currentStockUnsettledNo;
	private int currentStockToReturnNo;
	private float performanceIndex;
	private float returnIndex;
	private float salesIndex;
	private List<MrStat> mrStats;
	private List<MrVisitM> mrVisits;

	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public MemberREST getMemberInfo() {
		return memberInfo;
	}
	public void setMemberInfo(MemberREST memberInfo) {
		this.memberInfo = memberInfo;
	}
	public String getMrRole() {
		return mrRole;
	}
	public void setMrRole(String mrRole) {
		this.mrRole = mrRole;
	}
	public String getMrStatus() {
		return mrStatus;
	}
	public void setMrStatus(String mrStatus) {
		this.mrStatus = mrStatus;
	}
	public String getMrType() {
		return mrType;
	}
	public void setMrType(String mrType) {
		this.mrType = mrType;
	}
	public BigDecimal getRegistrationPaidAm() {
		return registrationPaidAm;
	}
	public void setRegistrationPaidAm(BigDecimal registrationPaidAm) {
		this.registrationPaidAm = registrationPaidAm;
	}
	public BigDecimal getRegistrationPendingAm() {
		return registrationPendingAm;
	}
	public void setRegistrationPendingAm(BigDecimal registrationPendingAm) {
		this.registrationPendingAm = registrationPendingAm;
	}
	public BigDecimal getDepositPaidAm() {
		return depositPaidAm;
	}
	public void setDepositPaidAm(BigDecimal depositPaidAm) {
		this.depositPaidAm = depositPaidAm;
	}
	public BigDecimal getDepositPendingAm() {
		return depositPendingAm;
	}
	public void setDepositPendingAm(BigDecimal depositPendingAm) {
		this.depositPendingAm = depositPendingAm;
	}
	public BigDecimal getDepositReturnAm() {
		return depositReturnAm;
	}
	public void setDepositReturnAm(BigDecimal depositReturnAm) {
		this.depositReturnAm = depositReturnAm;
	}
	public BigDecimal getCreditLimitAm() {
		return creditLimitAm;
	}
	public void setCreditLimitAm(BigDecimal creditLimitAm) {
		this.creditLimitAm = creditLimitAm;
	}
	public BigDecimal getTotalCollectedAm() {
		return totalCollectedAm;
	}
	public void setTotalCollectedAm(BigDecimal totalCollectedAm) {
		this.totalCollectedAm = totalCollectedAm;
	}
	public BigDecimal getTotalPaidCollectedAm() {
		return totalPaidCollectedAm;
	}
	public void setTotalPaidCollectedAm(BigDecimal totalPaidCollectedAm) {
		this.totalPaidCollectedAm = totalPaidCollectedAm;
	}
	public BigDecimal getCurrentCollectedAm() {
		return currentCollectedAm;
	}
	public void setCurrentCollectedAm(BigDecimal currentCollectedAm) {
		this.currentCollectedAm = currentCollectedAm;
	}
	public BigDecimal getSoldPaidAm() {
		return soldPaidAm;
	}
	public void setSoldPaidAm(BigDecimal soldPaidAm) {
		this.soldPaidAm = soldPaidAm;
	}
	public BigDecimal getSoldPendingAm() {
		return soldPendingAm;
	}
	public void setSoldPendingAm(BigDecimal soldPendingAm) {
		this.soldPendingAm = soldPendingAm;
	}
	public BigDecimal getPaidInterestPenaltyAm() {
		return paidInterestPenaltyAm;
	}
	public void setPaidInterestPenaltyAm(BigDecimal paidInterestPenaltyAm) {
		this.paidInterestPenaltyAm = paidInterestPenaltyAm;
	}
	public BigDecimal getPendingInterestPenaltyAm() {
		return pendingInterestPenaltyAm;
	}
	public void setPendingInterestPenaltyAm(BigDecimal pendingInterestPenaltyAm) {
		this.pendingInterestPenaltyAm = pendingInterestPenaltyAm;
	}
	public long getLastVisitOn() {
		return lastVisitOn;
	}
	public void setLastVisitOn(long lastVisitOn) {
		this.lastVisitOn = lastVisitOn;
	}
	public long getInterestCalculatedOn() {
		return interestCalculatedOn;
	}
	public void setInterestCalculatedOn(long interestCalculatedOn) {
		this.interestCalculatedOn = interestCalculatedOn;
	}
	public BigDecimal getCurrentStockAm() {
		return currentStockAm;
	}
	public void setCurrentStockAm(BigDecimal currentStockAm) {
		this.currentStockAm = currentStockAm;
	}
	public BigDecimal getCurrentStockDiscountAm() {
		return currentStockDiscountAm;
	}
	public void setCurrentStockDiscountAm(BigDecimal currentStockDiscountAm) {
		this.currentStockDiscountAm = currentStockDiscountAm;
	}
	public BigDecimal getCurrentStockUnsettledAm() {
		return currentStockUnsettledAm;
	}
	public void setCurrentStockUnsettledAm(BigDecimal currentStockUnsettledAm) {
		this.currentStockUnsettledAm = currentStockUnsettledAm;
	}
	public BigDecimal getCurrentStockToReturnAm() {
		return currentStockToReturnAm;
	}
	public void setCurrentStockToReturnAm(BigDecimal currentStockToReturnAm) {
		this.currentStockToReturnAm = currentStockToReturnAm;
	}
	public int getCurrentStockNo() {
		return currentStockNo;
	}
	public void setCurrentStockNo(int currentStockNo) {
		this.currentStockNo = currentStockNo;
	}
	public int getCurrentStockDiscountNo() {
		return currentStockDiscountNo;
	}
	public void setCurrentStockDiscountNo(int currentStockDiscountNo) {
		this.currentStockDiscountNo = currentStockDiscountNo;
	}
	public int getCurrentStockUnsettledNo() {
		return currentStockUnsettledNo;
	}
	public void setCurrentStockUnsettledNo(int currentStockUnsettledNo) {
		this.currentStockUnsettledNo = currentStockUnsettledNo;
	}
	public int getCurrentStockToReturnNo() {
		return currentStockToReturnNo;
	}
	public void setCurrentStockToReturnNo(int currentStockToReturnNo) {
		this.currentStockToReturnNo = currentStockToReturnNo;
	}
	public float getPerformanceIndex() {
		return performanceIndex;
	}
	public void setPerformanceIndex(float performanceIndex) {
		this.performanceIndex = performanceIndex;
	}
	public float getReturnIndex() {
		return returnIndex;
	}
	public void setReturnIndex(float returnIndex) {
		this.returnIndex = returnIndex;
	}
	public float getSalesIndex() {
		return salesIndex;
	}
	public void setSalesIndex(float salesIndex) {
		this.salesIndex = salesIndex;
	}
	public List<MrStat> getMrStats() {
		return mrStats;
	}
	public void setMrStats(List<MrStat> mrStats) {
		this.mrStats = mrStats;
	}
	public void addMrStats(MrStat mrStat) {
		if(mrStats == null) {
			mrStats = new ArrayList<MrStat>();
		}

		this.mrStats.add(mrStat);
	}
	public List<MrVisitM> getMrVisits() {
		return mrVisits;
	}
	public void setMrVisits(List<MrVisitM> mrVisits) {
		this.mrVisits = mrVisits;
	}
	public void addMrVisits(MrVisitM mrVisit) {
		if(mrVisits == null) {
			mrVisits = new ArrayList<MrVisitM>();
		}

		this.mrVisits.add(mrVisit);
	}

	public static MrAccount getMrAccount(PMAc pmAc) {

		MrAccount mrAccount = new MrAccount();

		mrAccount.setMemberAcNo(pmAc.getMemberAcNo());
		mrAccount.setMemberInfo(MemberREST.buildMemberProfile(pmAc.getMProfile()));
		mrAccount.setMrRole(EnumCache.getNameOfMRole(pmAc.getMProfile().getMroleId()));
		mrAccount.setMrStatus(EnumCacheMr.getNameOfStatusValue(EnumConstMr.MrStatus, pmAc.getMrStatusId()));
		mrAccount.setMrType(EnumCacheMr.getNameOfMrType(pmAc.getMrTypeId()));

		mrAccount.setRegistrationPaidAm(pmAc.getRegistrationPaidAm());
		mrAccount.setRegistrationPendingAm(pmAc.getRegistrationPendingAm());
		mrAccount.setDepositPaidAm(pmAc.getDepositPaidAm());
		mrAccount.setDepositPendingAm(pmAc.getDepositPendingAm());
		mrAccount.setDepositReturnAm(pmAc.getDepositReturnAm());
		mrAccount.setCreditLimitAm(pmAc.getCreditLimitAm());
		mrAccount.setTotalCollectedAm(pmAc.getTotalCollectedAm());
		mrAccount.setTotalPaidCollectedAm(pmAc.getTotalPaidCollectedAm());
		mrAccount.setCurrentCollectedAm(pmAc.getCurrentCollectedAm());
		mrAccount.setSoldPaidAm(pmAc.getSoldPaidAm());
		mrAccount.setSoldPendingAm(pmAc.getSoldPendingAm());
		mrAccount.setPaidInterestPenaltyAm(pmAc.getPaidInterestPenaltyAm());
		mrAccount.setPendingInterestPenaltyAm(pmAc.getPendingInterestPenaltyAm());
		mrAccount.setLastVisitOn(DateUtil.convertDateToTime(pmAc.getLastVisitOn()));
		mrAccount.setInterestCalculatedOn(DateUtil.convertDateToTime(pmAc.getInterestCalculatedOn()));
		mrAccount.setCurrentStockAm(pmAc.getCurrentStockAm());
		mrAccount.setCurrentStockDiscountAm(pmAc.getCurrentStockDiscountAm());
		mrAccount.setCurrentStockUnsettledAm(pmAc.getCurrentStockUnsettledAm());
		mrAccount.setCurrentStockToReturnAm(pmAc.getCurrentStockToReturnAm());
		mrAccount.setCurrentStockNo(pmAc.getCurrentStockNo());
		mrAccount.setCurrentStockDiscountNo(pmAc.getCurrentStockDiscountNo());
		mrAccount.setCurrentStockUnsettledNo(pmAc.getCurrentStockUnsettledNo());
		mrAccount.setCurrentStockToReturnNo(pmAc.getCurrentStockToReturnNo());
		mrAccount.setPerformanceIndex(pmAc.getPerformanceIndex());
		mrAccount.setReturnIndex(pmAc.getReturnIndex());
		mrAccount.setSalesIndex(pmAc.getSalesIndex());

		{
			MrStat stat1 = new MrStat();

			stat1.setTitle(EnumConstMr.MrStat_Titel_This_Month);
			stat1.setStockAm(pmAc.getThisMonthStockAm());
			stat1.setStockReturnedAm(pmAc.getThisMonthStockReturnedAm());
			stat1.setStockDamagedAm(pmAc.getThisMonthStockDamagedAm());
			stat1.setStockSoldAm(pmAc.getThisMonthStockSoldAm());
			stat1.setStockSoldDiscountAm(pmAc.getThisMonthStockSoldDiscountAm());
			stat1.setStockMrSoldAm(pmAc.getThisMonthStockMrSoldAm());
			stat1.setStockNo(pmAc.getThisMonthStockNo());
			stat1.setStockReturnedNo(pmAc.getThisMonthStockReturnedNo());
			stat1.setStockDamagedNo(pmAc.getThisMonthStockDamagedNo());
			stat1.setStockSoldNo(pmAc.getThisMonthStockSoldNo());
			stat1.setStockSoldDiscountNo(pmAc.getThisMonthStockSoldDiscountNo());
			stat1.setVisitCounter(pmAc.getThisMonthVisitCounter());

			mrAccount.addMrStats(stat1);
		}

		{
			MrStat stat2 = new MrStat();

			stat2.setTitle(EnumConstMr.MrStat_Titel_Last_Month);
			stat2.setStockAm(pmAc.getLastMonthStockAm());
			stat2.setStockReturnedAm(pmAc.getLastMonthStockReturnedAm());
			stat2.setStockDamagedAm(pmAc.getLastMonthStockDamagedAm());
			stat2.setStockSoldAm(pmAc.getLastMonthStockSoldAm());
			stat2.setStockSoldDiscountAm(pmAc.getLastMonthStockSoldDiscountAm());
			stat2.setStockMrSoldAm(pmAc.getLastMonthStockMrSoldAm());
			stat2.setStockNo(pmAc.getLastMonthStockNo());
			stat2.setStockReturnedNo(pmAc.getLastMonthStockReturnedNo());
			stat2.setStockDamagedNo(pmAc.getLastMonthStockDamagedNo());
			stat2.setStockSoldNo(pmAc.getLastMonthStockSoldNo());
			stat2.setStockSoldDiscountNo(pmAc.getLastMonthStockSoldDiscountNo());
			stat2.setVisitCounter(pmAc.getLastMonthVisitCounter());

			mrAccount.addMrStats(stat2);
		}

		{
			MrStat stat3 = new MrStat();

			stat3.setTitle(EnumConstMr.MrStat_Titel_Total);
			stat3.setStockAm(pmAc.getTotalStockAm());
			stat3.setStockReturnedAm(pmAc.getTotalStockReturnedAm());
			stat3.setStockDamagedAm(pmAc.getTotalStockDamagedAm());
			stat3.setStockSoldAm(pmAc.getTotalStockSoldAm());
			stat3.setStockSoldDiscountAm(pmAc.getTotalStockSoldDiscountAm());
			stat3.setStockMrSoldAm(pmAc.getTotalStockMrSoldAm());
			stat3.setStockNo(pmAc.getTotalStockNo());
			stat3.setStockReturnedNo(pmAc.getTotalStockReturnedNo());
			stat3.setStockDamagedNo(pmAc.getTotalStockDamagedNo());
			stat3.setStockSoldNo(pmAc.getTotalStockSoldNo());
			stat3.setStockSoldDiscountNo(pmAc.getTotalStockSoldDiscountNo());
			stat3.setVisitCounter(pmAc.getTotalVisitCounter());

			mrAccount.addMrStats(stat3);
		}

		return mrAccount;
	}

	public static boolean isMemberCSVValid(CSVDataCollector csvData) throws BadRequestException {

		return (csvData.isColumnPresent("Name Title") &&
				csvData.isColumnPresent("First Name") &&
				csvData.isColumnPresent("Middle Name") &&
				csvData.isColumnPresent("Last Name") &&
				csvData.isColumnPresent("Active Status") &&
				csvData.isColumnPresent("Member Role"));
	}
	
	public Map<String,String> toStringInfo() {

		Map<String,String> memberInfo = new HashMap<String,String>();
		memberInfo.put("memberAcNo", Long.toString(memberAcNo));
		memberInfo.put("mrStatus", mrStatus);
		memberInfo.put("mrType", mrType);

		return memberInfo;
	}

	public static MrAccount buildMrAccount(CSVDataCollector csvData, int row) throws BadRequestException {

		MrAccount mrAccount = new MrAccount();

		mrAccount.setMemberInfo(buildMember(csvData, row));
		
		mrAccount.setMemberAcNo(DataUtil.toLong(csvData.getCellValue("Account No", row)));
		mrAccount.setMrStatus(csvData.getCellValue("mrStatus", row));
		mrAccount.setMrType(csvData.getCellValue("mrType", row));

		mrAccount.setRegistrationPaidAm(DataUtil.toBigDecimal(csvData.getCellValue("registrationPaidAm", row)));
		mrAccount.setRegistrationPendingAm(DataUtil.toBigDecimal(csvData.getCellValue("registrationPendingAm", row)));
		mrAccount.setDepositPaidAm(DataUtil.toBigDecimal(csvData.getCellValue("depositPaidAm", row)));
		mrAccount.setDepositPendingAm(DataUtil.toBigDecimal(csvData.getCellValue("depositPendingAm", row)));
		mrAccount.setDepositReturnAm(DataUtil.toBigDecimal(csvData.getCellValue("depositReturnAm", row)));
		mrAccount.setCreditLimitAm(DataUtil.toBigDecimal(csvData.getCellValue("creditLimitAm", row)));
		mrAccount.setTotalCollectedAm(DataUtil.toBigDecimal(csvData.getCellValue("totalCollectedAm", row)));
		mrAccount.setTotalPaidCollectedAm(DataUtil.toBigDecimal(csvData.getCellValue("totalPaidCollectedAm", row)));
		mrAccount.setCurrentCollectedAm(DataUtil.toBigDecimal(csvData.getCellValue("currentCollectedAm", row)));
		mrAccount.setSoldPaidAm(DataUtil.toBigDecimal(csvData.getCellValue("soldPaidAm", row)));
		mrAccount.setSoldPendingAm(DataUtil.toBigDecimal(csvData.getCellValue("soldPendingAm", row)));
		mrAccount.setPaidInterestPenaltyAm(DataUtil.toBigDecimal(csvData.getCellValue("paidInterestPenaltyAm", row)));
		mrAccount.setPendingInterestPenaltyAm(DataUtil.toBigDecimal(csvData.getCellValue("pendingInterestPenaltyAm", row)));
		mrAccount.setCurrentStockAm(DataUtil.toBigDecimal(csvData.getCellValue("currentStockAm", row)));
		mrAccount.setCurrentStockToReturnAm(DataUtil.ZERO_BIG_DECIMAL);
		mrAccount.setCurrentStockNo(DataUtil.toInteger(csvData.getCellValue("currentStockNo", row)));
		mrAccount.setCurrentStockToReturnNo(DataUtil.ZERO_INTEGER);

		{
			MrStat mrStat1 = new MrStat();

			mrStat1.setTitle(EnumConstMr.MrStat_Titel_Total);
			mrStat1.setStockAm(DataUtil.toBigDecimal(csvData.getCellValue("total-stockAm", row)));
			mrStat1.setStockReturnedAm(DataUtil.toBigDecimal(csvData.getCellValue("total-stockReturnedAm", row)));
			mrStat1.setStockDamagedAm(DataUtil.toBigDecimal(csvData.getCellValue("total-stockDamagedAm", row)));
			mrStat1.setStockSoldAm(DataUtil.toBigDecimal(csvData.getCellValue("total-stockSoldAm", row)));
			mrStat1.setStockMrSoldAm(DataUtil.toBigDecimal(csvData.getCellValue("total-stockMrSoldAm", row)));
			mrStat1.setStockNo(DataUtil.toInteger(csvData.getCellValue("total-stockNo", row)));
			mrStat1.setStockReturnedNo(DataUtil.toInteger(csvData.getCellValue("total-stockReturnedNo", row)));
			mrStat1.setStockDamagedNo(DataUtil.toInteger(csvData.getCellValue("total-stockDamagedNo", row)));
			mrStat1.setStockSoldNo(DataUtil.toInteger(csvData.getCellValue("total-stockSoldNo", row)));
			mrStat1.setVisitCounter(DataUtil.toInteger(csvData.getCellValue("total-visitCounter", row)));

			mrAccount.addMrStats(mrStat1);
		}

		{
			MrStat mrStat2 = new MrStat();

			mrStat2.setTitle(EnumConstMr.MrStat_Titel_This_Month);
			mrStat2.setStockAm(DataUtil.toBigDecimal(csvData.getCellValue("thisMonth-stockAm", row)));
			mrStat2.setStockReturnedAm(DataUtil.toBigDecimal(csvData.getCellValue("thisMonth-stockReturnedAm", row)));
			mrStat2.setStockDamagedAm(DataUtil.toBigDecimal(csvData.getCellValue("thisMonth-stockDamagedAm", row)));
			mrStat2.setStockSoldAm(DataUtil.toBigDecimal(csvData.getCellValue("thisMonth-stockSoldAm", row)));
			mrStat2.setStockMrSoldAm(DataUtil.toBigDecimal(csvData.getCellValue("thisMonth-stockMrSoldAm", row)));
			mrStat2.setStockNo(DataUtil.toInteger(csvData.getCellValue("thisMonth-stockNo", row)));
			mrStat2.setStockReturnedNo(DataUtil.toInteger(csvData.getCellValue("thisMonth-stockReturnedNo", row)));
			mrStat2.setStockDamagedNo(DataUtil.toInteger(csvData.getCellValue("thisMonth-stockDamagedNo", row)));
			mrStat2.setStockSoldNo(DataUtil.toInteger(csvData.getCellValue("thisMonth-stockSoldNo", row)));
			mrStat2.setVisitCounter(DataUtil.toInteger(csvData.getCellValue("thisMonth-visitCounter", row)));

			mrAccount.addMrStats(mrStat2);
		}

		{
			MrStat mrStat3 = new MrStat();

			mrStat3.setTitle(EnumConstMr.MrStat_Titel_Last_Month);
			mrStat3.setStockAm(DataUtil.toBigDecimal(csvData.getCellValue("lastMonth-stockAm", row)));
			mrStat3.setStockReturnedAm(DataUtil.toBigDecimal(csvData.getCellValue("lastMonth-stockReturnedAm", row)));
			mrStat3.setStockDamagedAm(DataUtil.toBigDecimal(csvData.getCellValue("lastMonth-stockDamagedAm", row)));
			mrStat3.setStockSoldAm(DataUtil.toBigDecimal(csvData.getCellValue("lastMonth-stockSoldAm", row)));
			mrStat3.setStockMrSoldAm(DataUtil.toBigDecimal(csvData.getCellValue("lastMonth-stockMrSoldAm", row)));
			mrStat3.setStockNo(DataUtil.toInteger(csvData.getCellValue("lastMonth-stockNo", row)));
			mrStat3.setStockReturnedNo(DataUtil.toInteger(csvData.getCellValue("lastMonth-stockReturnedNo", row)));
			mrStat3.setStockDamagedNo(DataUtil.toInteger(csvData.getCellValue("lastMonth-stockDamagedNo", row)));
			mrStat3.setStockSoldNo(DataUtil.toInteger(csvData.getCellValue("lastMonth-stockSoldNo", row)));
			mrStat3.setVisitCounter(DataUtil.toInteger(csvData.getCellValue("lastMonth-visitCounter", row)));

			mrAccount.addMrStats(mrStat3);
		}

		return mrAccount;
	}

	public static MemberREST buildMember(CSVDataCollector csvData, int row) throws BadRequestException {

		MemberREST member = new MemberREST();
		member.setMemberAcNo(DataUtil.toLong(csvData.getCellValue("Account No", row)));
		member.setParentGroupAcNo(DataUtil.toLong(csvData.getCellValue("groupAcNo", row)));		
		MemberContactREST contact = new MemberContactREST();

		// Add Name Info
		contact.setLang(EnumConst.Lang_English);
		contact.setNameTitle(csvData.getCellValue("Name Title", row));
		contact.setFirstName(csvData.getCellValue("First Name", row));
		contact.setMiddleName(csvData.getCellValue("Middle Name", row));
		contact.setLastName(csvData.getCellValue("Last Name", row));

		// Add Enrollment Info
		member.setApprovalStatus(EnumConst.ApprovalStatus_Approved);
		member.setActiveStatus(csvData.getCellValue("Active Status", row));
		member.setMrole(csvData.getCellValue("Member Role", row));
		member.setPasscode(csvData.getCellValue("Passcode", row));
		member.setUidaiNo(csvData.getCellValue("Adhaar Card No", row));
		member.setPanCardNo(csvData.getCellValue("PAN Card No", row));
		member.setVoterIdNo(csvData.getCellValue("Voter Id No", row));
		member.setDrivingLicenseNo(csvData.getCellValue("Ration Card No", row));
		member.setGender(csvData.getCellValue("Gender", row));
		member.setDateOfEnroll(csvData.getCellValue("Date Of Enrollment", row));
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
		contact.setState(csvData.getCellValue("State", row));
		contact.setPhone(csvData.getCellValue("Phone", row));
		contact.setPriMobile(csvData.getCellValue("Primary Mobile", row));
		contact.setSecMobile(csvData.getCellValue("Secondary Mobile", row));
		contact.setEmail(csvData.getCellValue("Email", row));
		member.addContact(contact);

		return member;
	}	


	public static class MrStat {

		private String title;
		private BigDecimal stockAm;
		private BigDecimal stockReturnedAm;
		private BigDecimal stockDamagedAm;
		private BigDecimal stockSoldAm;
		private BigDecimal stockSoldDiscountAm;
		private BigDecimal stockMrSoldAm;
		private int stockNo;
		private int stockReturnedNo;
		private int stockDamagedNo;
		private int stockSoldDiscountNo;
		private int stockSoldNo;
		private int visitCounter;

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public BigDecimal getStockAm() {
			return stockAm;
		}
		public void setStockAm(BigDecimal stockAm) {
			this.stockAm = stockAm;
		}
		public BigDecimal getStockReturnedAm() {
			return stockReturnedAm;
		}
		public void setStockReturnedAm(BigDecimal stockReturnedAm) {
			this.stockReturnedAm = stockReturnedAm;
		}
		public BigDecimal getStockDamagedAm() {
			return stockDamagedAm;
		}
		public void setStockDamagedAm(BigDecimal stockDamagedAm) {
			this.stockDamagedAm = stockDamagedAm;
		}
		public BigDecimal getStockSoldAm() {
			return stockSoldAm;
		}
		public void setStockSoldAm(BigDecimal stockSoldAm) {
			this.stockSoldAm = stockSoldAm;
		}
		public BigDecimal getStockSoldDiscountAm() {
			return stockSoldDiscountAm;
		}
		public void setStockSoldDiscountAm(BigDecimal stockSoldDiscountAm) {
			this.stockSoldDiscountAm = stockSoldDiscountAm;
		}
		public BigDecimal getStockMrSoldAm() {
			return stockMrSoldAm;
		}
		public void setStockMrSoldAm(BigDecimal stockMrSoldAm) {
			this.stockMrSoldAm = stockMrSoldAm;
		}
		public int getStockNo() {
			return stockNo;
		}
		public void setStockNo(int stockNo) {
			this.stockNo = stockNo;
		}
		public int getStockReturnedNo() {
			return stockReturnedNo;
		}
		public void setStockReturnedNo(int stockReturnedNo) {
			this.stockReturnedNo = stockReturnedNo;
		}
		public int getStockDamagedNo() {
			return stockDamagedNo;
		}
		public void setStockDamagedNo(int stockDamagedNo) {
			this.stockDamagedNo = stockDamagedNo;
		}
		public int getStockSoldNo() {
			return stockSoldNo;
		}
		public void setStockSoldNo(int stockSoldNo) {
			this.stockSoldNo = stockSoldNo;
		}
		public int getStockSoldDiscountNo() {
			return stockSoldDiscountNo;
		}
		public void setStockSoldDiscountNo(int stockSoldDiscountNo) {
			this.stockSoldDiscountNo = stockSoldDiscountNo;
		}
		public int getVisitCounter() {
			return visitCounter;
		}
		public void setVisitCounter(int visitCounter) {
			this.visitCounter = visitCounter;
		}

	}
}
