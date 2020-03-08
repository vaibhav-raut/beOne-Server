package com.beone.shg.net.webservice.rest.model.rest;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.beone.shg.net.webservice.rest.model.resp.TodoTransactions;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;

@JsonSerialize(include = Inclusion.NON_NULL)
public class GroupAllDataREST {
	
	private long groupAcNo;
	private GroupREST group;
	private List<MemberREST> members;
	List<MemberSavingAcREST> memberSavingAc;
	List<MemberLoanAcREST> memberLoanAc;
	Transactions transactions;
	TodoTransactions todoTransactions;
	
	public long getGroupAcNo() {
		return groupAcNo;
	}
	public void setGroupAcNo(long groupAcNo) {
		this.groupAcNo = groupAcNo;
	}
	public GroupREST getGroup() {
		return group;
	}
	public void setGroup(GroupREST group) {
		this.group = group;
	}
	public List<MemberREST> getMembers() {
		return members;
	}
	public void setMembers(List<MemberREST> members) {
		this.members = members;
	}
	public List<MemberSavingAcREST> getMemberSavingAc() {
		return memberSavingAc;
	}
	public void setMemberSavingAc(List<MemberSavingAcREST> memberSavingAc) {
		this.memberSavingAc = memberSavingAc;
	}
	public List<MemberLoanAcREST> getMemberLoanAc() {
		return memberLoanAc;
	}
	public void setMemberLoanAc(List<MemberLoanAcREST> memberLoanAc) {
		this.memberLoanAc = memberLoanAc;
	}
	public Transactions getTransactions() {
		return transactions;
	}
	public void setTransactions(Transactions transactions) {
		this.transactions = transactions;
	}
	public TodoTransactions getTodoTransactions() {
		return todoTransactions;
	}
	public void setTodoTransactions(TodoTransactions todoTransactions) {
		this.todoTransactions = todoTransactions;
	}
}
