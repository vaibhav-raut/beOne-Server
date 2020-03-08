package com.beone.shg.net.persistence.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MBankAccount;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;

public class BankAccountInfoCollector {

	private long groupAcNo;
	private Set<Long> memberAcNos;
	private Map<Long, List<MBankAccount>> memberAcToBankAccount;
	private Map<Long, List<BankAccountShort>> memberAcToBankAccountShort;
	private Map<Long, MBankAccount> memberBankAcToBankAccount;
	private Map<Long, BankAccountShort> memberBankAcToBankAccountShort;

	public BankAccountInfoCollector() {
		memberAcNos = new HashSet<Long>();
		memberAcToBankAccount = new LinkedHashMap<Long, List<MBankAccount>>();
		memberAcToBankAccountShort = new LinkedHashMap<Long, List<BankAccountShort>>();
		memberBankAcToBankAccount = new LinkedHashMap<Long, MBankAccount>();
		memberBankAcToBankAccountShort = new LinkedHashMap<Long, BankAccountShort>();
	}

	public long getGroupAcNo() {
		return groupAcNo;
	}

	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}

	public Set<Long> getMemberAcNos() {
		return memberAcNos;
	}

	public String getMemberAcNoString() {
		return ConversionUtil.convertArrayToInString(this.memberAcNos.toArray());
	}

	public void addMemberAcNo(Long memberAcNo) {
		this.memberAcNos.add(memberAcNo);
	}

	public List<MBankAccount> getMemberBankAccounts(long memberAcNo) {
		return memberAcToBankAccount.get(memberAcNo);
	}

	public boolean isMemberBankAccountShort(long memberAcNo) {
		return memberAcToBankAccountShort.containsKey(memberAcNo);
	}

	public List<BankAccountShort> getMemberBankAccountShort(long memberAcNo) {
		return memberAcToBankAccountShort.get(memberAcNo);
	}

	public MBankAccount getMemberBankAcForBankAcNo(long memberBankAcNo) {
		return memberBankAcToBankAccount.get(memberBankAcNo);
	}

	public BankAccountShort getMemberBankAcForBankAcNoShort(long memberBankAcNo) {
		return memberBankAcToBankAccountShort.get(memberBankAcNo);
	}

	public void putMemberBankAccounts(Long memberAcNo, MBankAccount bankAccount) {
		this.memberAcNos.add(memberAcNo);
		
		if(memberAcToBankAccount.get(memberAcNo) == null) {
			memberAcToBankAccount.put(memberAcNo, new ArrayList<MBankAccount>());
			memberAcToBankAccountShort.put(memberAcNo, new ArrayList<BankAccountShort>());
		}
		this.memberAcToBankAccount.get(memberAcNo).add(bankAccount);
		BankAccountShort bankAccountShort = new BankAccountShort(bankAccount.getBankAccountNo(),
				bankAccount.getBankAccount().getBankProfile().getGAcNo(),
				ConversionUtil.convertToDisplayStr(bankAccount.getBankAccount()),
				EnumCache.getNameOfEnumValue(EnumConst.BankAccountType, bankAccount.getBankAccount().getBankAccountTypeId()));
		this.memberAcToBankAccountShort.get(memberAcNo).add(bankAccountShort);
		
		this.memberBankAcToBankAccount.put(bankAccount.getBankAccountNo(), bankAccount);
		this.memberBankAcToBankAccountShort.put(bankAccount.getBankAccountNo(), bankAccountShort);
		
	}
}
