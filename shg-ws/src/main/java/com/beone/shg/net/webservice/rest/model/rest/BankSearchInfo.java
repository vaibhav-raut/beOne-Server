package com.beone.shg.net.webservice.rest.model.rest;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class BankSearchInfo {

	private String districtCode;
	private int districtId;
	private String districtName;
	private String bankName;
	private String branchName;
	private List<BankProfileREST> bankProfiles;
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
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
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
	public List<BankProfileREST> getBankProfiles() {
		return bankProfiles;
	}
	public void setBankProfiles(List<BankProfileREST> bankProfiles) {
		this.bankProfiles = bankProfiles;
	}
	
}
