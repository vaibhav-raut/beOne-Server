package com.beone.shg.net.webservice.rest.model.rest;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberSavingAcREST {

	private long memberSavingAcNo;
	private long memberAc;
	private String accountStatus;
	private String recoveryPeriod;
	private BigDecimal savedAm;
	private BigDecimal intReturnedAm;
	private BigDecimal cumulativeSavedAm;
	private BigDecimal installmentAm;
	private BigDecimal totalIntEnAm;
	private BigDecimal currentFyIntEnAm;
	private BigDecimal prevMonthIntAm;
	private BigDecimal returnedSavedAm;
	private BigDecimal returnedIntEnAm;
	private int expectedNoOfInst;
	private int noOfInstPaid;
	private int noOfInsallLate;
	private int noOfInsallMissed;
	private String requestedDate;
	private String approvedDate;
	private String actualStartDate;
	private String instStartDate;
	private String expCompletionDate;
	private String closureDate;
		
	public MemberSavingAcREST() {
		super();
	}

	public MemberSavingAcREST(long memberAc, BigDecimal installmentAm,
			int expectedNoOfInst) {
		super();
		this.memberAc = memberAc;
		this.installmentAm = installmentAm;
		if(expectedNoOfInst > 0) {
			this.expectedNoOfInst = expectedNoOfInst;
		} else {
			this.expectedNoOfInst = 9999;
		}
		this.savedAm = DataUtil.ZERO_BIG_DECIMAL;
		this.intReturnedAm = DataUtil.ZERO_BIG_DECIMAL;
		this.cumulativeSavedAm = DataUtil.ZERO_BIG_DECIMAL;
		this.totalIntEnAm = DataUtil.ZERO_BIG_DECIMAL;
		this.currentFyIntEnAm = DataUtil.ZERO_BIG_DECIMAL;
		this.prevMonthIntAm = DataUtil.ZERO_BIG_DECIMAL;		
	}

	public long getMemberSavingAcNo() {
		return memberSavingAcNo;
	}

	public void setMemberSavingAcNo(long memberSavingAcNo) {
		this.memberSavingAcNo = memberSavingAcNo;
	}

	public long getMemberAc() {
		return memberAc;
	}

	public void setMemberAc(long memberAc) {
		this.memberAc = memberAc;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getRecoveryPeriod() {
		return recoveryPeriod;
	}

	public void setRecoveryPeriod(String recoveryPeriod) {
		this.recoveryPeriod = recoveryPeriod;
	}

	public BigDecimal getSavedAm() {
		return savedAm;
	}

	public void setSavedAm(BigDecimal savedAm) {
		this.savedAm = savedAm;
	}

	public BigDecimal getIntReturnedAm() {
		return intReturnedAm;
	}

	public void setIntReturnedAm(BigDecimal intReturnedAm) {
		this.intReturnedAm = intReturnedAm;
	}

	public BigDecimal getCumulativeSavedAm() {
		return cumulativeSavedAm;
	}

	public void setCumulativeSavedAm(BigDecimal cumulativeSavedAm) {
		this.cumulativeSavedAm = cumulativeSavedAm;
	}

	public BigDecimal getInstallmentAm() {
		return installmentAm;
	}

	public void setInstallmentAm(BigDecimal installmentAm) {
		this.installmentAm = installmentAm;
	}

	public BigDecimal getTotalIntEnAm() {
		return totalIntEnAm;
	}

	public void setTotalIntEnAm(BigDecimal totalIntEnAm) {
		this.totalIntEnAm = totalIntEnAm;
	}

	public BigDecimal getCurrentFyIntEnAm() {
		return currentFyIntEnAm;
	}

	public void setCurrentFyIntEnAm(BigDecimal currentFyIntEnAm) {
		this.currentFyIntEnAm = currentFyIntEnAm;
	}

	public BigDecimal getPrevMonthIntAm() {
		return prevMonthIntAm;
	}

	public void setPrevMonthIntAm(BigDecimal prevMonthIntAm) {
		this.prevMonthIntAm = prevMonthIntAm;
	}

	public BigDecimal getReturnedSavedAm() {
		return returnedSavedAm;
	}

	public void setReturnedSavedAm(BigDecimal returnedSavedAm) {
		this.returnedSavedAm = returnedSavedAm;
	}

	public BigDecimal getReturnedIntEnAm() {
		return returnedIntEnAm;
	}

	public void setReturnedIntEnAm(BigDecimal returnedIntEnAm) {
		this.returnedIntEnAm = returnedIntEnAm;
	}

	public int getExpectedNoOfInst() {
		return expectedNoOfInst;
	}

	public void setExpectedNoOfInst(int expectedNoOfInst) {
		this.expectedNoOfInst = expectedNoOfInst;
	}

	public int getNoOfInstPaid() {
		return noOfInstPaid;
	}

	public void setNoOfInstPaid(int noOfInstPaid) {
		this.noOfInstPaid = noOfInstPaid;
	}

	public int getNoOfInsallLate() {
		return noOfInsallLate;
	}

	public void setNoOfInsallLate(int noOfInsallLate) {
		this.noOfInsallLate = noOfInsallLate;
	}

	public int getNoOfInsallMissed() {
		return noOfInsallMissed;
	}

	public void setNoOfInsallMissed(int noOfInsallMissed) {
		this.noOfInsallMissed = noOfInsallMissed;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getInstStartDate() {
		return instStartDate;
	}

	public void setInstStartDate(String instStartDate) {
		this.instStartDate = instStartDate;
	}

	public String getExpCompletionDate() {
		return expCompletionDate;
	}

	public void setExpCompletionDate(String expCompletionDate) {
		this.expCompletionDate = expCompletionDate;
	}

	public String getClosureDate() {
		return closureDate;
	}

	public void setClosureDate(String closureDate) {
		this.closureDate = closureDate;
	}

	public String[] toStringArray() {
		
		String[] memberRaw = {
				new Long(memberSavingAcNo).toString(),
				new Long(memberAc).toString()
		};
		return memberRaw;
	}
	
	public static MemberSavingAcREST buildMemberSavingAc(String[] rawMember) throws BadRequestException {
		
		MemberSavingAcREST account = new MemberSavingAcREST();
		int index = DataUtil.ZERO_INTEGER;
		
		if(rawMember.length == DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid CSV Data:" + rawMember);
		}
		
		account.setMemberAc(new Long(rawMember[index++].trim()));
		account.setAccountStatus(rawMember[index++].trim());
		account.setRecoveryPeriod(rawMember[index++].trim());
		account.setInstallmentAm(new BigDecimal(rawMember[index++].trim()));
		account.setExpectedNoOfInst(new Integer(rawMember[index++].trim()));
		account.setRequestedDate(rawMember[index++].trim());
		account.setApprovedDate(rawMember[index++].trim());
		account.setActualStartDate(rawMember[index++].trim());
		account.setInstStartDate(rawMember[index++].trim());
		account.setClosureDate(rawMember[index++].trim());

		return account;
	}	

	public static MemberSavingAcREST convertAccountToREST(MSavingAc account) {
		MemberSavingAcREST accountREST = new MemberSavingAcREST();

		accountREST.setMemberSavingAcNo(account.getMSavingAcNo());
		accountREST.setMemberAc(account.getMAc().getMAcNo());
		accountREST.setAccountStatus(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		accountREST.setRecoveryPeriod(EnumCache.getNameOfEnumValue(EnumConst.RecoveryPeriod, account.getRecoveryPeriodId()));
		accountREST.setSavedAm(account.getSavedAm());
		accountREST.setCumulativeSavedAm(account.getCumulativeSavedAm());
		accountREST.setInstallmentAm(account.getSavingInstallmentAm());
		accountREST.setTotalIntEnAm(account.getTotalIntEnAm());
		accountREST.setCurrentFyIntEnAm(account.getCurrentFyIntEnAm());
		accountREST.setPrevMonthIntAm(account.getPrevMonthIntAm());
		accountREST.setExpectedNoOfInst(account.getExpNoOfInst());
		accountREST.setNoOfInstPaid(account.getNoOfInstPaid());
		accountREST.setNoOfInsallLate(account.getNoOfInsallLate());
		accountREST.setNoOfInsallMissed(account.getNoOfInsallMissed());
		accountREST.setRequestedDate(DateUtil.convertDateToString(account.getRequestedDate()));
		accountREST.setApprovedDate(DateUtil.convertDateToString(account.getApprovedDate()));
		accountREST.setActualStartDate(DateUtil.convertDateToString(account.getActualStartDate()));
		accountREST.setInstStartDate(DateUtil.convertDateToString(account.getInstStartDate()));
		accountREST.setExpCompletionDate(DateUtil.convertDateToString(account.getExpCompletionDate()));
		accountREST.setClosureDate(DateUtil.convertDateToString(account.getClosureDate()));
		
		return accountREST;
	}
}
