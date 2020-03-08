package com.beone.shg.net.persistence.bo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.webservice.rest.model.resp.GroupDataMapper;
import com.beone.shg.net.webservice.rest.model.resp.TodoTransaction;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.rest.BankAccountREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupAllDataREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupContactREST;
import com.beone.shg.net.webservice.rest.model.rest.GroupREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberContactREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberLoanAcREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberSavingAcREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("groupDataBO")
public class GroupDataBO extends BaseBO {

	@Autowired
	@Qualifier("groupBO")
	private GroupBO groupBO;

	@Autowired
	@Qualifier("memberBO")
	private MemberBO memberBO;

	@Autowired
	@Qualifier("memberSavingAcBO")
	private MemberSavingAcBO memberSavingAcBO;

	@Autowired
	@Qualifier("memberLoanAcBO")
	private MemberLoanAcBO memberLoanAcBO;

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	public GroupAllDataREST exportGroupData(long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GroupAllDataREST groupAllData = new GroupAllDataREST();
		
		groupAllData.setGroupAcNo(groupAcNo);

		groupAllData.setGroup(groupBO.getGroupAllAccounts(EnumConst.Lang_English, groupAcNo, "All"));
		
		groupAllData.setMembers(memberBO.getAllGroupMembers(groupAcNo));
		
		groupAllData.setMemberSavingAc(memberSavingAcBO.getMemberSavingAcsForGroup(EnumConst.Lang_English, groupAcNo));
		
		groupAllData.setMemberLoanAc(memberLoanAcBO.getMemberLoanAcsForGroup(EnumConst.Lang_English, groupAcNo));
		
		groupAllData.setTodoTransactions(transactionBO.getGroupTodoTxs(EnumConst.Lang_English, groupAcNo, DataUtil.LONG_TIME_MIN, DataUtil.LONG_TIME_MAX, ""));

		groupAllData.setTransactions(transactionBO.getGroupTxs(EnumConst.Lang_English, groupAcNo, DataUtil.LONG_TIME_MIN, DataUtil.LONG_TIME_MAX, ""));
		
		return groupAllData;
	}

	public void importGroupData(GroupAllDataREST groupData) throws BadRequestException {

		if(groupData == null) {
			throw new BadRequestException("Invalid Import Data");
		}
		
		GroupDataMapper mapper = new GroupDataMapper(groupData.getMembers().size());
		
		addGroup(groupData, mapper);
		
		addMembers(groupData, mapper);
		
		addMemberSavingAc(groupData, mapper);
		
		addMemberLoanAc(groupData, mapper);
		
		addTodoTransactions(groupData, mapper);
		
		addTransactions(groupData, mapper);
		
	}
	
	protected void addGroup(GroupAllDataREST groupData, GroupDataMapper mapper) throws BadRequestException {

		// Save Old IDs for Mapping
		long oldId = groupData.getGroup().getGroupAcNo();
		List<Long> contactIds = new ArrayList<Long>();
		for(GroupContactREST contact: groupData.getGroup().getContacts()) {
			contactIds.add(contact.getContactId());
		}
		List<Long> bankAccountIds = new ArrayList<Long>();
		for(BankAccountREST bankAc: groupData.getGroup().getBankAccounts()) {
			bankAccountIds.add(bankAc.getBankAccountId());
		}

		// Add Group
		GroupREST newGroup = groupBO.addGroup(groupData.getGroup(), true);
		
		// Map Old IDs with New
		mapper.putGroupAc(oldId, newGroup.getGroupAcNo());
		for(int i = 0; i < newGroup.getContacts().size(); i++) {
			mapper.putContactAc(contactIds.get(i), newGroup.getContacts().get(i).getContactId());
		}
		for(int i = 0; i < newGroup.getBankAccounts().size(); i++) {
			mapper.putBankAcountAc(bankAccountIds.get(i), newGroup.getBankAccounts().get(i).getBankAccountId());
		}
	}
	
	protected void addMembers(GroupAllDataREST groupData, GroupDataMapper mapper) throws BadRequestException {
		long newGroupAcNo = groupData.getGroup().getGroupAcNo();
		
		for(MemberREST member: groupData.getMembers()) {
			member.setParentGroupAcNo(newGroupAcNo);
			
			// Save Old IDs for Mapping
			long oldId = member.getMemberAcNo();
			List<Long> contactIds = new ArrayList<Long>();
			for(MemberContactREST contact: member.getContacts()) {
				contactIds.add(contact.getContactId());
			}
			List<Long> bankAccountIds = new ArrayList<Long>();
			for(BankAccountREST bankAc: member.getBankAccounts()) {
				bankAccountIds.add(bankAc.getBankAccountId());
			}
			
			// Add member 
			MemberREST newMember = memberBO.importMember(member);
			
			// Map Old IDs with New
			mapper.putMemberAc(oldId, newMember.getMemberAcNo());
			for(int i = 0; i < newMember.getContacts().size(); i++) {
				mapper.putContactAc(contactIds.get(i), newMember.getContacts().get(i).getContactId());
			}
			for(int i = 0; i < newMember.getBankAccounts().size(); i++) {
				mapper.putBankAcountAc(bankAccountIds.get(i), newMember.getBankAccounts().get(i).getBankAccountId());
			}
		}
	}
	
	protected void addMemberSavingAc(GroupAllDataREST groupData, GroupDataMapper mapper) throws BadRequestException {
		for(MemberSavingAcREST saving: groupData.getMemberSavingAc()) {			

			// Update Ids for new Mapping
			saving.setMemberAc(mapper.getMemberAc(saving.getMemberAc()));
			
			// Save Old IDs for Mapping
			long oldId = saving.getMemberSavingAcNo();

			// Add Saving Account
			MemberSavingAcREST newSaving = memberSavingAcBO.add(saving, true);

			// Map Old IDs with New
			mapper.putSavingAc(oldId, newSaving.getMemberSavingAcNo());
		}
	}
	
	protected void addMemberLoanAc(GroupAllDataREST groupData, GroupDataMapper mapper) throws BadRequestException {
		for(MemberLoanAcREST loan: groupData.getMemberLoanAc()) {
			
			// Update Ids for new Mapping
			loan.setMemberAcNo(mapper.getMemberAc(loan.getMemberAcNo()));
			if(loan.getApprovedByMember() > DataUtil.ZERO_LONG) {
				loan.setApprovedByMember(mapper.getMemberAc(loan.getApprovedByMember()));
			}
			if(loan.getMultiMToLoanAcs() != null & !loan.getMultiMToLoanAcs().isEmpty()) {
				Set<Long> newList = new HashSet<Long>();
				for(Long oldId: loan.getMultiMToLoanAcs()) {
					newList.add(mapper.getMemberAc(oldId));
				}
				loan.setMultiMToLoanAcs(newList);
			}

			// Save Old IDs for Mapping
			long oldId = loan.getMemberLoanAcNo();

			// Add Loan Account
			MemberLoanAcREST newLoan = memberLoanAcBO.add(loan, true);

			// Map Old IDs with New
			mapper.putLoanAc(oldId, newLoan.getMemberLoanAcNo());
		}
	}
	
	protected void addTodoTransactions(GroupAllDataREST groupData, GroupDataMapper mapper) throws BadRequestException {
		for(TodoTransaction tx: groupData.getTodoTransactions().getTodoTransactions()) {			
			tx.setMemberAcNo(mapper.getMemberAc(tx.getMemberAcNo()));
			tx.setGroupAcNo(mapper.getGroupAc(tx.getGroupAcNo()));
			if(tx.getInstAcNo() > DataUtil.ZERO_LONG) {
				if(tx.getTxAcType().equals(EnumConst.TxAcType_Member_Saving)) {
					tx.setInstAcNo(mapper.getSavingAc(tx.getInstAcNo()));
				} else if(tx.getTxAcType().equals(EnumConst.TxAcType_Member_Loan)) {
					tx.setInstAcNo(mapper.getLoanAc(tx.getInstAcNo()));
				}
			}
			
			// Save Old IDs for Mapping
			long oldId = tx.getTodoTxId();

			TodoTransaction newTx = transactionBO.addTodoTransaction(tx);

			// Map Old IDs with New
			mapper.putTodoTx(oldId, newTx.getTodoTxId());
		}
	}
	
	protected void addTransactions(GroupAllDataREST groupData, GroupDataMapper mapper) throws BadRequestException {
		for(Transaction tx: groupData.getTransactions().getTransactions()) {			
			tx.setMemberAcNo(mapper.getMemberAc(tx.getMemberAcNo()));
			tx.setGroupAcNo(mapper.getGroupAc(tx.getGroupAcNo()));
			tx.setDoneByMemberAcNo(mapper.getMemberAc(tx.getDoneByMemberAcNo()));
			
			if(tx.getApprovedByMemberAcNo() > DataUtil.ZERO_LONG) {
				tx.setApprovedByMemberAcNo(mapper.getMemberAc(tx.getApprovedByMemberAcNo()));
			}
			if(tx.getSavingAcNo() > DataUtil.ZERO_LONG) {
				tx.setSavingAcNo(mapper.getSavingAc(tx.getSavingAcNo()));
			}
			if(tx.getMemberLoanAcNo() > DataUtil.ZERO_LONG) {
				tx.setMemberLoanAcNo(mapper.getLoanAc(tx.getMemberLoanAcNo()));
			}
			if(tx.getGroupBankAcNo() > DataUtil.ZERO_LONG) {
				tx.setGroupBankAcNo(mapper.getBankAcountAc(tx.getGroupBankAcNo()));
			}
			if(tx.getMemberBankAcNo() > DataUtil.ZERO_LONG) {
				tx.setMemberBankAcNo(mapper.getBankAcountAc(tx.getMemberBankAcNo()));
			}
 
			// Save Old IDs for Mapping
			long oldId = tx.getTxId();

			Transaction newTx = transactionBO.importTransaction(tx);

			// Map Old IDs with New
			mapper.putTx(oldId, newTx.getTxId());
		}
	}
}
