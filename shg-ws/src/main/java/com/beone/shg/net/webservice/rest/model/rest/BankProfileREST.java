package com.beone.shg.net.webservice.rest.model.rest;

import org.apache.commons.lang3.text.WordUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.support.EnumCache;

@JsonSerialize(include = Inclusion.NON_NULL)
public class BankProfileREST {
	
	private long bankProfileId;
	private String districtCode;
	private String ifcsCode;
	private String bankName;
	private String branchName;
	private Float bankLoanIntRate;
	private Float bankFdIntRate;
	private Float bankRating;
	private Integer loanProcessingFee;
	private Integer loanApplicationCharges;
	private Integer loanPrepaymentCharges;
	private Integer latePaymentCharges;
	private Integer missedPaymentCharges;

	public long getBankProfileId() {
		return bankProfileId;
	}

	public void setBankProfileId(long bankProfileId) {
		this.bankProfileId = bankProfileId;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getIfcsCode() {
		return ifcsCode;
	}

	public void setIfcsCode(String ifcsCode) {
		this.ifcsCode = ifcsCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Float getBankLoanIntRate() {
		return bankLoanIntRate;
	}

	public void setBankLoanIntRate(Float bankLoanIntRate) {
		this.bankLoanIntRate = bankLoanIntRate;
	}

	public Float getBankFdIntRate() {
		return bankFdIntRate;
	}

	public void setBankFdIntRate(Float bankFdIntRate) {
		this.bankFdIntRate = bankFdIntRate;
	}

	public Float getBankRating() {
		return bankRating;
	}

	public void setBankRating(Float bankRating) {
		this.bankRating = bankRating;
	}

	public Integer getLoanProcessingFee() {
		return loanProcessingFee;
	}

	public void setLoanProcessingFee(Integer loanProcessingFee) {
		this.loanProcessingFee = loanProcessingFee;
	}

	public Integer getLoanApplicationCharges() {
		return loanApplicationCharges;
	}

	public void setLoanApplicationCharges(Integer loanApplicationCharges) {
		this.loanApplicationCharges = loanApplicationCharges;
	}

	public Integer getLoanPrepaymentCharges() {
		return loanPrepaymentCharges;
	}

	public void setLoanPrepaymentCharges(Integer loanPrepaymentCharges) {
		this.loanPrepaymentCharges = loanPrepaymentCharges;
	}

	public Integer getLatePaymentCharges() {
		return latePaymentCharges;
	}

	public void setLatePaymentCharges(Integer latePaymentCharges) {
		this.latePaymentCharges = latePaymentCharges;
	}

	public Integer getMissedPaymentCharges() {
		return missedPaymentCharges;
	}

	public void setMissedPaymentCharges(Integer missedPaymentCharges) {
		this.missedPaymentCharges = missedPaymentCharges;
	}

	public static void loadBankProfile(BankProfile bProfile, BankProfileREST bankProfile) {
		
		long bankProfileId = bProfile.getGProfile().getGAcNo();
		bankProfile.setBankProfileId(bankProfileId);
		bankProfile.setDistrictCode(EnumCache.getDistrictCodeForGroup(bankProfileId));
		bankProfile.setIfcsCode(bProfile.getIfcsCode());
		bankProfile.setBankName(bProfile.getBankName());
		bankProfile.setBranchName(bProfile.getBranchName());
		bankProfile.setBankLoanIntRate(bProfile.getBankLoanIntRate());
		bankProfile.setBankFdIntRate(bProfile.getBankFdIntRate());
		bankProfile.setBankRating(bProfile.getBankRating());
		bankProfile.setLoanProcessingFee(bProfile.getLoanProcessingFee());
		bankProfile.setLoanApplicationCharges(bProfile.getLoanApplicationCharges());
		bankProfile.setLoanPrepaymentCharges(bProfile.getLoanPrepaymentCharges());
		bankProfile.setLatePaymentCharges(bProfile.getLatePaymentCharges());
		bankProfile.setMissedPaymentCharges(bProfile.getMissedPaymentCharges());
	}

	public static void updateBankProfile(BankProfileREST bankProfile, BankProfile bProfile) {
		
		bProfile.setIfcsCode(bankProfile.getIfcsCode().toUpperCase());
		bProfile.setBankName(WordUtils.capitalize(bankProfile.getBankName()));
		bProfile.setBranchName(WordUtils.capitalizeFully(bankProfile.getBranchName()));
		bProfile.setBankLoanIntRate(bankProfile.getBankLoanIntRate());
		bProfile.setBankFdIntRate(bankProfile.getBankFdIntRate());
		bProfile.setBankRating(bankProfile.getBankRating());
		bProfile.setLoanProcessingFee(bankProfile.getLoanProcessingFee());
		bProfile.setLoanApplicationCharges(bankProfile.getLoanApplicationCharges());
		bProfile.setLoanPrepaymentCharges(bankProfile.getLoanPrepaymentCharges());
		bProfile.setLatePaymentCharges(bankProfile.getLatePaymentCharges());
		bProfile.setMissedPaymentCharges(bankProfile.getMissedPaymentCharges());

		// Just push back any updates done by WordUtils.capitalizeFully
		bankProfile.setBankName(bProfile.getBankName());
		bankProfile.setBranchName(bProfile.getBranchName());
	}
}
