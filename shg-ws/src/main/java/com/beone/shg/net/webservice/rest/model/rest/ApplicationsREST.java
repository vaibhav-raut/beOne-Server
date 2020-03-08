package com.beone.shg.net.webservice.rest.model.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.GroupInfoCollector;
import com.beone.shg.net.persistence.util.DataUtil;

@JsonSerialize(include = Inclusion.NON_NULL)
public class ApplicationsREST {
	
	private long groupAcNo;
	private List<MemberREST> members;	
	private List<MemberLoanAcREST> memberLoanAcs;
	private Map<String, String> displayNames;

	public ApplicationsREST(long groupAcNo) {
		super();
		this.groupAcNo = groupAcNo;
	}
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public List<MemberREST> getMembers() {
		return members;
	}
	public void setMembers(List<MemberREST> members) {
		this.members = members;
	}
	public void addMember(MemberREST member) {
		if(this.members == null) {
			this.members = new ArrayList<MemberREST>();
		}
		this.members.add(member);
	}
	public List<MemberLoanAcREST> getMemberLoanAcs() {
		return memberLoanAcs;
	}
	public void setMemberLoanAcs(List<MemberLoanAcREST> memberLoanAcs) {
		this.memberLoanAcs = memberLoanAcs;
	}	
	public void addMemberLoanAc(MemberLoanAcREST memberLoanAc) {
		if(this.memberLoanAcs == null) {
			this.memberLoanAcs = new ArrayList<MemberLoanAcREST>();
		}
		this.memberLoanAcs.add(memberLoanAc);
	}
	
	public Map<String, String> getDisplayNames() {
		return displayNames;
	}
	public void setDisplayNames(Map<String, String> displayNames) {
		this.displayNames = displayNames;
	}
	public static void loadMemberApplications(List<MProfile> mProfiles, GroupInfoCollector collector, ApplicationsREST applications) {
		for(MProfile mProfile: mProfiles) {
			MemberREST member = new MemberREST();
			MemberREST.loadMemberProfile(mProfile, member);
			MemberREST.loadMemberAccount(mProfile, member);
			MemberREST.loadMemberContacts(mProfile, member);
			
			member.setMemberName(collector.getMemberName(mProfile.getMemberAcNo()));
			if(mProfile.getRecommendedByMember() > 0) {
				member.setRecommendedByMemberName(collector.getMemberName(mProfile.getRecommendedByMember()));
			} else {
				member.setRecommendedByMemberName(DataUtil.EMPTY_STRING);
			}
			if(mProfile.getApprovedByMember() > 0) {
				member.setApprovedByMemberName(collector.getMemberName(mProfile.getApprovedByMember()));
			} else {
				member.setApprovedByMemberName(DataUtil.EMPTY_STRING);
			}

			applications.addMember(member);
		}
	}	
	
	public static void loadMemberLoanApplications(List<MLoanAc> mLoanAcs, GroupInfoCollector collector, ApplicationsREST applications) {
		for(MLoanAc mLoanAc: mLoanAcs) {
			MemberLoanAcREST loanAc = MemberLoanAcREST.convertAccountToREST(mLoanAc);
			
			loanAc.setMemberName(collector.getMemberName(loanAc.getMemberAcNo()));
			if(loanAc.getApprovedByMember() > DataUtil.ZERO_LONG) {
				loanAc.setApprovedByMemberName(collector.getMemberName(loanAc.getApprovedByMember()));
			} else {
				loanAc.setApprovedByMemberName(DataUtil.EMPTY_STRING);
			}
			
			for(MAc mAc: collector.getMemberProfile(loanAc.getMemberAcNo()).getMAcs()) {
				loanAc.setMemberAc(MemberAcREST.convertMemberAccount(mAc));
				break;
			}
			
			loanAc.setMrole(EnumCache.getNameOfMRole(collector.getMemberProfile(loanAc.getMemberAcNo()).getMroleId()));
			
			applications.addMemberLoanAc(loanAc);
		}
	}	
}
