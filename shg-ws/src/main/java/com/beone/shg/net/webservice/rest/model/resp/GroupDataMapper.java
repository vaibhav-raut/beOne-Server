package com.beone.shg.net.webservice.rest.model.resp;

import java.util.HashMap;
import java.util.Map;

public class GroupDataMapper {
	Map<Long, Long> groupAc;
	Map<Long, Long> memberAc;
	Map<Long, Long> bankAcountAc;
	Map<Long, Long> contactAc;
	Map<Long, Long> savingAc;
	Map<Long, Long> loanAc;
	Map<Long, Long> tx;
	Map<Long, Long> todoTx;
	
	public GroupDataMapper(int memNo) {
		memNo = (int)(memNo * 1.4f);
		groupAc = new HashMap<Long, Long>(5);
		memberAc = new HashMap<Long, Long>(memNo);
		bankAcountAc = new HashMap<Long, Long>(memNo);
		contactAc = new HashMap<Long, Long>(memNo);
		savingAc = new HashMap<Long, Long>(memNo);
		loanAc = new HashMap<Long, Long>(memNo);
		tx = new HashMap<Long, Long>(memNo * 10);
		todoTx = new HashMap<Long, Long>(memNo * 10);
	}
	public Long getGroupAc(Long oldId) {
		return groupAc.get(oldId);
	}
	public void putGroupAc(Long oldId, Long newId) {
		this.groupAc.put(oldId, newId);
	}

	public Long getMemberAc(Long oldId) {
		return memberAc.get(oldId);
	}
	public void putMemberAc(Long oldId, Long newId) {
		this.memberAc.put(oldId, newId);
	}

	public Long getBankAcountAc(Long oldId) {
		return bankAcountAc.get(oldId);
	}
	public void putBankAcountAc(Long oldId, Long newId) {
		this.bankAcountAc.put(oldId, newId);
	}

	public Long getContactAc(Long oldId) {
		return contactAc.get(oldId);
	}
	public void putContactAc(Long oldId, Long newId) {
		this.contactAc.put(oldId, newId);
	}

	public Long getSavingAc(Long oldId) {
		return savingAc.get(oldId);
	}
	public void putSavingAc(Long oldId, Long newId) {
		this.savingAc.put(oldId, newId);
	}

	public Long getLoanAc(Long oldId) {
		return loanAc.get(oldId);
	}
	public void putLoanAc(Long oldId, Long newId) {
		this.loanAc.put(oldId, newId);
	}

	public Long getTx(Long oldId) {
		return tx.get(oldId);
	}
	public void putTx(Long oldId, Long newId) {
		this.tx.put(oldId, newId);
	}

	public Long getTodoTx(Long oldId) {
		return todoTx.get(oldId);
	}
	public void putTodoTx(Long oldId, Long newId) {
		this.todoTx.put(oldId, newId);
	}

}
