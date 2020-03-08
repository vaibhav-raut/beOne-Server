package com.beone.shg.net.persistence.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.model.rest.MemberName;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

public class GroupInfoCollector {

	private long groupAcNo;
	private Set<Long> memberAcNos;
	private Map<Long, String> memberAcToNames;
	private Map<Long, MProfile> memberAcToProfile;
	private Map<Long, MemberName> memberAcToMemberNames;
	private Map<String, Long> namesToMemberAc;
	private Map<String, Long> bankAcNumberToNo;

	public GroupInfoCollector() {
		memberAcNos = new HashSet<Long>();
		memberAcToNames = new LinkedHashMap<Long, String>();
		memberAcToProfile = new LinkedHashMap<Long, MProfile>();
		memberAcToMemberNames = new LinkedHashMap<Long, MemberName>();
		namesToMemberAc = new LinkedHashMap<String, Long>();
		bankAcNumberToNo = new LinkedHashMap<String, Long>();
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

	public String getMemberName(Long memberAcNo) {
		return memberAcToNames.get(memberAcNo);
	}

	public long getMemberAc(String name) {
		return namesToMemberAc.get(name);
	}

	public long getBankAcNo(String bankAc) {
		return bankAcNumberToNo.get(bankAc);
	}

	public boolean isMemberAcPresent(Long memberAcNo) {
		return memberAcToNames.containsKey(memberAcNo);
	}

	public boolean isNamePresent(String name) {
		return namesToMemberAc.containsKey(name);
	}

	public void putMamberName(Long memberAcNo, String name) {
		this.memberAcNos.add(memberAcNo);
		this.memberAcToNames.put(memberAcNo, name);
		this.namesToMemberAc.put(name, memberAcNo);
	}

	public void putBankAcNumber(String bankAc, Long bankAcNumber) {
		this.bankAcNumberToNo.put(bankAc, bankAcNumber);
	}

	public MProfile getMemberProfile(Long memberAcNo) {
		return memberAcToProfile.get(memberAcNo);
	}

	public void putMamberProfile(Long memberAcNo, MProfile mProfile) {
		this.memberAcNos.add(memberAcNo);
		this.memberAcToProfile.put(memberAcNo, mProfile);
	}

	public void putMamberProfile(Long memberAcNo, MProfile mProfile, String name) {
		this.memberAcNos.add(memberAcNo);
		this.memberAcToNames.put(memberAcNo, name);
		this.memberAcToProfile.put(memberAcNo, mProfile);
		this.namesToMemberAc.put(name, memberAcNo);
		this.memberAcToMemberNames.put(memberAcNo, 
				new MemberName(memberAcNo, groupAcNo, name, 
				EnumCache.getNameOfMRole(mProfile.getMroleId()),
				EnumCache.getNameOfEnumValue(EnumConst.ActiveStatus, mProfile.getActiveStatusId())));
	}

	public List<MemberName> searchMemberName(String nameshort) throws BadRequestException {
		List<MemberName> list = new ArrayList<MemberName>();
		
		for(String name: namesToMemberAc.keySet()) {
			if(StringUtils.containsIgnoreCase(name, nameshort)) {
				list.add(this.memberAcToMemberNames.get(this.namesToMemberAc.get(name)));
			}
		}
		return list;
	}

	public List<MemberName> searchMemberName() throws BadRequestException {
		List<MemberName> list = new ArrayList<MemberName>();
		
		for(String name: namesToMemberAc.keySet()) {
			list.add(this.memberAcToMemberNames.get(this.namesToMemberAc.get(name)));
		}

		return list;
	}

	public List<MemberName> searchMemberName(String nameshort, BankAccountInfoCollector bankAccountCol) throws BadRequestException {
		List<MemberName> list = new ArrayList<MemberName>();
		long memberAcNo;
		for(String name: namesToMemberAc.keySet()) {
			if(StringUtils.containsIgnoreCase(name, nameshort)) {
				memberAcNo = namesToMemberAc.get(name);
				MemberName memberName = this.memberAcToMemberNames.get(this.namesToMemberAc.get(name));
				if(bankAccountCol.isMemberBankAccountShort(memberAcNo)) {
					memberName.setMemberBankAcNos(bankAccountCol.getMemberBankAccountShort(memberAcNo));
				}
				list.add(memberName);
			}
		}
		return list;
	}
}
