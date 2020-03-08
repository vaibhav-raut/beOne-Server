package com.beone.shg.net.persistence.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.beone.shg.net.persistence.model.BankProfile;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

public class BankInfoCollector {

	private int districtId;
	private String bankName;
	private Set<Long> bankProfileIds;
	private Map<Long, String> bankProfileIdToNames;
	private Map<Long, BankProfile> bankProfileIdToProfiles;
	private Map<String, Long> namesToBankProfileId;

	public BankInfoCollector() {
		bankProfileIds = new HashSet<Long>();
		bankProfileIdToNames = new LinkedHashMap<Long, String>();
		bankProfileIdToProfiles = new LinkedHashMap<Long, BankProfile>();
		namesToBankProfileId = new LinkedHashMap<String, Long>();
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Set<Long> getBankProfileIds() {
		return bankProfileIds;
	}

	public String getbankProfileIdsString() {
		return ConversionUtil.convertArrayToInString(this.bankProfileIds.toArray());
	}

	public void addbankProfileId(Long bankProfileId) {
		this.bankProfileIds.add(bankProfileId);
	}

	public String getBankName(Long bankProfileId) {
		return bankProfileIdToNames.get(bankProfileId);
	}

	public long getBankProfileId(String name) {
		return namesToBankProfileId.get(name);
	}

	public boolean isBankProfileIdPresent(Long bankProfileId) {
		return bankProfileIdToNames.containsKey(bankProfileId);
	}

	public boolean isNamePresent(String name) {
		return namesToBankProfileId.containsKey(name);
	}

	public void putBankName(Long bankProfileId, String name) {
		this.bankProfileIds.add(bankProfileId);
		this.bankProfileIdToNames.put(bankProfileId, name);
		this.namesToBankProfileId.put(name, bankProfileId);
	}

	public BankProfile getBankProfile(Long bankProfileId) {
		return bankProfileIdToProfiles.get(bankProfileId);
	}

	public void putBankProfile(Long bankProfileId, BankProfile bankProfile) {
		this.bankProfileIds.add(bankProfileId);
		this.bankProfileIdToNames.put(bankProfileId, (bankProfile.getBankName() + " " + bankProfile.getBranchName()));
		this.namesToBankProfileId.put((bankProfile.getBankName() + " " + bankProfile.getBranchName()), bankProfileId);
		this.bankProfileIdToProfiles.put(bankProfileId, bankProfile);
	}

	public List<BankProfile> searchBankName(String nameshort) throws BadRequestException {
		List<BankProfile> list = new ArrayList<BankProfile>();
		
		if(nameshort != null && !nameshort.isEmpty()) {
			for(String name: namesToBankProfileId.keySet()) {
				if(StringUtils.containsIgnoreCase(name, nameshort)) {
					list.add(bankProfileIdToProfiles.get(namesToBankProfileId.get(name)));
				}
			}
		} else {
			list.addAll(bankProfileIdToProfiles.values());
		}
		return list;
	}
}
