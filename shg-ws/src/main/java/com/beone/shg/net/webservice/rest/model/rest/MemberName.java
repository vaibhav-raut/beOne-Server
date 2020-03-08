package com.beone.shg.net.webservice.rest.model.rest;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;


@JsonSerialize(include = Inclusion.NON_NULL)
public class MemberName {

	private long memberAcNo;
	private long groupAcNo;
	private String memberName;
	private String mRole;
	private String status;
	private List<BankAccountShort> memberBankAcNos;
	
	public MemberName(long memberAcNo, long groupAcNo, String memberName, String mRole, String status) {
		super();
		this.memberAcNo = memberAcNo;
		this.groupAcNo = groupAcNo;
		this.memberName = memberName;
		this.mRole = mRole;
		this.status = status;
	}
	public MemberName(long memberAcNo, long groupAcNo, String memberName,
			String mRole, String status, List<BankAccountShort> memberBankAcNos) {
		super();
		this.memberAcNo = memberAcNo;
		this.groupAcNo = groupAcNo;
		this.memberName = memberName;
		this.mRole = mRole;
		this.status = status;
		this.memberBankAcNos = memberBankAcNos;
	}
	public long getMemberAcNo() {
		return memberAcNo;
	}
	public void setMemberAcNo(long memberAcNo) {
		this.memberAcNo = memberAcNo;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMRole() {
		return mRole;
	}
	public void setMRole(String mRole) {
		this.mRole = mRole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<BankAccountShort> getMemberBankAcNos() {
		return memberBankAcNos;
	}
	public void setMemberBankAcNos(List<BankAccountShort> memberBankAcNos) {
		this.memberBankAcNos = memberBankAcNos;
	}
}
