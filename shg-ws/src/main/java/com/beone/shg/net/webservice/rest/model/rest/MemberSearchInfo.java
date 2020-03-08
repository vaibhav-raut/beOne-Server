package com.beone.shg.net.webservice.rest.model.rest;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;


@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberSearchInfo {

	private long groupAcNo;
	private List<MemberName> memberNames;
	private List<BankAccountShort> groupBankAcNos;
	
	public MemberSearchInfo() {
	}
	public MemberSearchInfo(long groupAcNo, List<BankAccountShort> groupBankAcNos) {
		super();
		this.groupAcNo = groupAcNo;
		this.groupBankAcNos = groupBankAcNos;
	}
	public MemberSearchInfo(long groupAcNo, List<MemberName> memberNames,
			List<BankAccountShort> groupBankAcNos) {
		super();
		this.groupAcNo = groupAcNo;
		this.memberNames = memberNames;
		this.groupBankAcNos = groupBankAcNos;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public List<MemberName> getMemberNames() {
		return memberNames;
	}
	public void setMemberNames(List<MemberName> memberNames) {
		this.memberNames = memberNames;
	}
	public List<BankAccountShort> getGroupBankAcNos() {
		return groupBankAcNos;
	}
	public void setGroupBankAcNos(List<BankAccountShort> groupBankAcNos) {
		this.groupBankAcNos = groupBankAcNos;
	}
	
}
