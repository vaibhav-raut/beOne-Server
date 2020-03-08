package com.beone.shg.net.persistence.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.webservice.rest.model.resp.BankAccountShort;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.rest.MemberREST;
import com.beone.shg.net.webservice.rest.model.rest.MemberSavingAcREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("memberSavingAcBO")
public class MemberSavingAcBO extends BaseBO {

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	public MemberSavingAcREST add(MemberSavingAcREST accountREST) throws BadRequestException {
		return add(accountREST, false);
	}
	
	public MemberSavingAcREST add(MemberSavingAcREST accountREST, boolean oldData) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAc())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(accountREST.getInstallmentAm() == null || accountREST.getInstallmentAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
			throw new BadRequestException("Invalid Installment Amount");
		}

		boolean newObject = false;
		MSavingAc account = null;
		
		if(accountREST.getMemberSavingAcNo() > 0) {
			account = daoFactory.getMSavingAcDAO().findById(accountREST.getMemberSavingAcNo());
		}
		
		if(account == null) {
			account = new MSavingAc();		
			account.setMAc(daoFactory.getMAcDAO().getReferenceById(accountREST.getMemberAc()));
			newObject = true;
		}
		
		if(accountREST.getRecoveryPeriod() != null && !accountREST.getRecoveryPeriod().isEmpty()) {
			account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod()));
		} else {
			account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, EnumConst.RecoveryPeriod_Monthly));
		}
		
		if(oldData) {
			addOldAc(account, accountREST);
		} else {
			addNewAc(account, accountREST);
		}
		
		if(newObject) {
			daoFactory.getMSavingAcDAO().persist(account);
		} else {
			daoFactory.getMSavingAcDAO().merge(account);
		}
		
		// Load Account ID back
		accountREST.setMemberSavingAcNo(account.getMSavingAcNo());
		
		return accountREST;
	}
	
	protected void addNewAc(MSavingAc account, MemberSavingAcREST accountREST) throws BadRequestException {
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Submitted));
		account.setSavedAm(DataUtil.ZERO_BIG_DECIMAL);			
		account.setCumulativeSavedAm(DataUtil.ZERO_BIG_DECIMAL);			
		account.setSavingInstallmentAm(accountREST.getInstallmentAm());
		account.setTotalIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setCurrentFyIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setReturnedSavedAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setReturnedIntEnAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setExpNoOfInst(accountREST.getExpectedNoOfInst());
		account.setNoOfInstPaid(DataUtil.ZERO_INTEGER);
		account.setNoOfInsallLate(DataUtil.ZERO_INTEGER);
		account.setNoOfInsallMissed(DataUtil.ZERO_INTEGER);
		account.setPrevMonthIntAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setRequestedDate(DateUtil.getCurrentTimeDate());
	}
	
	protected void addOldAc(MSavingAc account, MemberSavingAcREST accountREST) throws BadRequestException {

		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
		account.setSavedAm(accountREST.getSavedAm());
		if(accountREST.getCumulativeSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {
			account.setCumulativeSavedAm(accountREST.getSavedAm());
		} else {
			account.setCumulativeSavedAm(accountREST.getCumulativeSavedAm());
		}
		account.setSavingInstallmentAm(accountREST.getInstallmentAm());
		account.setTotalIntEnAm(accountREST.getTotalIntEnAm());
		account.setCurrentFyIntEnAm(accountREST.getCurrentFyIntEnAm());
		account.setReturnedSavedAm(accountREST.getReturnedSavedAm());
		account.setReturnedIntEnAm(accountREST.getReturnedIntEnAm());
		account.setExpNoOfInst(accountREST.getExpectedNoOfInst());
		account.setNoOfInstPaid(accountREST.getNoOfInstPaid());
		account.setNoOfInsallLate(accountREST.getNoOfInsallLate());
		account.setNoOfInsallMissed(accountREST.getNoOfInsallMissed());
		account.setPrevMonthIntAm(accountREST.getPrevMonthIntAm());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setApprovedDate(DateUtil.convertStringToDate(accountREST.getApprovedDate()));
		account.setActualStartDate(DateUtil.convertStringToDate(accountREST.getActualStartDate()));
		account.setInstStartDate(DateUtil.convertStringToDate(accountREST.getInstStartDate()));
		account.setExpCompletionDate(DateUtil.getExpectedDate(DateUtil.convertStringToLong(accountREST.getRequestedDate()), 
				accountREST.getExpectedNoOfInst()));
		account.setClosureDate(DateUtil.convertStringToDate(accountREST.getClosureDate()));
	}
	
	public List<String[]> addAcs(List<String[]> rawMemberSavingAcs) throws BadRequestException {
		
		List<MemberSavingAcREST> memberSavingAcs = new ArrayList<MemberSavingAcREST>(rawMemberSavingAcs.size());
		
		for(String[] rawMemberSavingAc: rawMemberSavingAcs) {
			if(rawMemberSavingAc.length > 1) {
				memberSavingAcs.add(MemberSavingAcREST.buildMemberSavingAc(rawMemberSavingAc));
			}
		}
		
		List<String[]> returnList = new ArrayList<String[]>(memberSavingAcs.size());
		
		for(MemberSavingAcREST memberSavingAc: memberSavingAcs) {
			returnList.add(add(memberSavingAc).toStringArray());
		}
		
		return returnList;
	}

	public MemberSavingAcREST updateMemberSavingAc(MemberSavingAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberSavingAcNo() <= 0) {
			throw new BadRequestException("Invalid Member Installment Account No");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAc())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		boolean updated = false;
		MSavingAc account = daoFactory.getMSavingAcDAO().findById(accountREST.getMemberSavingAcNo());
				
		if(accountREST.getRecoveryPeriod() != null && !accountREST.getRecoveryPeriod().isEmpty() &&
				!accountREST.getRecoveryPeriod().equals(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getRecoveryPeriodId()))) {
			
			account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getRecoveryPeriod()));
			updated = true;
		}
		if(accountREST.getInstallmentAm() != null &&
				accountREST.getInstallmentAm().compareTo(account.getSavingInstallmentAm()) != DataUtil.ZERO_INTEGER) {
			
			account.setSavingInstallmentAm(accountREST.getInstallmentAm());
			updated = true;
		}
		if(accountREST.getExpectedNoOfInst() > 0 &&
				accountREST.getExpectedNoOfInst() != account.getExpNoOfInst()) {
			account.setExpNoOfInst(accountREST.getExpectedNoOfInst());
			updated = true;
		}
		
		if(updated) {
			daoFactory.getMSavingAcDAO().merge(account);
		} else {
			throw new BadRequestException("No Saving Data for Updation");
		}
		
		return accountREST;
	}

	public MemberSavingAcREST updateMemberSavingAcData(MemberSavingAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberSavingAcNo() <= 0) {
			throw new BadRequestException("Invalid Member Installment Account No");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAc())) {
			throw new BadRequestException("Invalid Member Account No");
		}

		MSavingAc account = daoFactory.getMSavingAcDAO().findById(accountREST.getMemberSavingAcNo());
		
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
		account.setRecoveryPeriodId(EnumCache.getIndexOfEnumValue(EnumConst.RecoveryPeriod, accountREST.getRecoveryPeriod()));
		account.setSavedAm(accountREST.getSavedAm());
		account.setCumulativeSavedAm(accountREST.getCumulativeSavedAm());
		account.setSavingInstallmentAm(accountREST.getInstallmentAm());
		account.setTotalIntEnAm(accountREST.getTotalIntEnAm());
		account.setCurrentFyIntEnAm(accountREST.getCurrentFyIntEnAm());
		account.setPrevMonthIntAm(accountREST.getPrevMonthIntAm());
		account.setExpNoOfInst(accountREST.getExpectedNoOfInst());
		account.setNoOfInstPaid(accountREST.getNoOfInstPaid());
		account.setNoOfInsallLate(accountREST.getNoOfInsallLate());
		account.setNoOfInsallMissed(accountREST.getNoOfInsallMissed());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setApprovedDate(DateUtil.convertStringToDate(accountREST.getApprovedDate()));
		account.setActualStartDate(DateUtil.convertStringToDate(accountREST.getActualStartDate()));
		account.setInstStartDate(DateUtil.convertStringToDate(accountREST.getInstStartDate()));
		account.setExpCompletionDate(DateUtil.convertStringToDate(accountREST.getExpCompletionDate()));
		account.setClosureDate(DateUtil.convertStringToDate(accountREST.getClosureDate()));		
		
		daoFactory.getMSavingAcDAO().merge(account);
		
		return accountREST;
	}

	public MemberSavingAcREST approveReject(MemberSavingAcREST accountREST) throws BadRequestException {

		if(accountREST.getMemberSavingAcNo() <= DataUtil.ZERO_LONG) {
			throw new BadRequestException("Invalid Saving Type");
		}
		if(!ConversionUtil.isValidMemberAcNo(accountREST.getMemberAc())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(accountREST.getAccountStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}
		
		int accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus());

		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Account Status:" + accountREST.getAccountStatus());
		}

		MSavingAc account = daoFactory.getMSavingAcDAO().findById(accountREST.getMemberSavingAcNo());
		
		account.setAccountStatusId(accountStatusId);
		account.setApprovedDate(DateUtil.convertStringToDateWithCurrentDefault(accountREST.getApprovedDate()));
		
		daoFactory.getMSavingAcDAO().merge(account);
		
		return accountREST;
	}

	public void approveReject(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(member.getApprovalStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}
		
		int accountStatusId = DataUtil.ZERO_INTEGER;

		accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, member.getApprovalStatus());
		
		MAc mAc = daoFactory.getMAcDAO().findById(member.getMemberAcNo());
		
		if(mAc.getMSavingAcs() != null) {
			for(MSavingAc savingAc : mAc.getMSavingAcs()) {
				
				MSavingAc account = daoFactory.getMSavingAcDAO().findById(savingAc.getMSavingAcNo());
				
				account.setAccountStatusId(accountStatusId);
				account.setApprovedDate(DateUtil.getCurrentTimeDate());
				
				if(member.getDateOfEnroll() != null && !member.getDateOfEnroll().isEmpty()) {
					account.setApprovedDate(DateUtil.convertStringToDate(member.getDateOfEnroll()));
				}
				
				daoFactory.getMSavingAcDAO().merge(account);
			}
		}
	}

	public void activate(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		
		MAc mAc = daoFactory.getMAcDAO().findById(member.getMemberAcNo());
		
		if(mAc.getMSavingAcs() != null) {
			for(MSavingAc savingAc : mAc.getMSavingAcs()) {
				
				MSavingAc account = daoFactory.getMSavingAcDAO().findById(savingAc.getMSavingAcNo());
				
				if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
					throw new BadRequestException("Current Account Status Not 'Approved' : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
				}
				
				account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active));
				
				daoFactory.getMSavingAcDAO().merge(account);
			}
		}
	}

	public Transaction getTxToActivate(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}
		
		MAc mAc = daoFactory.getMAcDAO().findById(member.getMemberAcNo());
		Transaction tx = null;
		
		if(mAc.getMSavingAcs() != null) {
			for(MSavingAc savingAc : mAc.getMSavingAcs()) {
				
				MSavingAc account = daoFactory.getMSavingAcDAO().findById(savingAc.getMSavingAcNo());
				
				if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
					throw new BadRequestException("Account status Not 'Approved' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
				}

				tx = new Transaction();				
				tx.setTxType(EnumConst.TxType_Saving_Installment);
				tx.setTxWith(EnumConst.TxType_MEMBER);
				tx.setSlipType(EnumConst.SlipType_RECEIPT);
				tx.setMemberAcNo(member.getMemberAcNo());
				tx.setSavingAcNo(savingAc.getMSavingAcNo());
				tx.setGroupAcNo(member.getParentGroupAcNo());
				tx.setPaymentMode(EnumConst.PaymentMode_CASH);
				tx.setDescription("First Saving Installment after Member enrollment");
				tx.setAmount(savingAc.getSavingInstallmentAm());
				break;
			}
		}

		return tx;
	}

	public void close(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		
		MAc mAc = daoFactory.getMAcDAO().findById(member.getMemberAcNo());
		
		if(mAc.getMSavingAcs() != null) {
			for(MSavingAc savingAc : mAc.getMSavingAcs()) {
				
				MSavingAc account = daoFactory.getMSavingAcDAO().findById(savingAc.getMSavingAcNo());
				
				if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Active)) {
					throw new BadRequestException("Current Account Status Not 'Active' : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
				}
				
				account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Complete));
				
				daoFactory.getMSavingAcDAO().merge(account);
			}
		}
	}

	public List<Transaction> getTxToClose(MemberREST member) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(member.getMemberAcNo())) {
			throw new BadRequestException("Invalid Member Account");
		}
		if(!ConversionUtil.isValidGroupAcNo(member.getParentGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account");
		}
		
		MAc mAc = daoFactory.getMAcDAO().findById(member.getMemberAcNo());
		List<Transaction> txs = new ArrayList<Transaction>();
		List<BankAccountShort> memberBankAcNos = daoFactory.getMBankAccountDAO().getMemberBankAccountsDisplay(member.getMemberAcNo());

		if(mAc.getMSavingAcs() != null) {
			for(MSavingAc savingAc : mAc.getMSavingAcs()) {
				
				MSavingAc account = daoFactory.getMSavingAcDAO().findById(savingAc.getMSavingAcNo());
				
				if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Active)) {
					throw new BadRequestException("Account status Not 'Active' it is : " + EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
				}

				Transaction tx1 = new Transaction();				
				tx1.setTxType(EnumConst.TxType_Saving_Returned);
				tx1.setTxWith(EnumConst.TxType_MEMBER);
				tx1.setSlipType(EnumConst.SlipType_VOUCHER);
				tx1.setMemberAcNo(member.getMemberAcNo());
				tx1.setSavingAcNo(savingAc.getMSavingAcNo());
				tx1.setGroupAcNo(member.getParentGroupAcNo());
				tx1.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
				tx1.setDescription("Returned - Total Saving after Member Account closure");
				tx1.setAmount(savingAc.getSavedAm());
				tx1.setMemberBankAcNos(memberBankAcNos);
				txs.add(tx1);

				Transaction tx2 = new Transaction();				
				tx2.setTxType(EnumConst.TxType_Saving_Interest_Returned);
				tx2.setTxWith(EnumConst.TxType_MEMBER);
				tx2.setSlipType(EnumConst.SlipType_VOUCHER);
				tx2.setMemberAcNo(member.getMemberAcNo());
				tx2.setSavingAcNo(savingAc.getMSavingAcNo());
				tx2.setGroupAcNo(member.getParentGroupAcNo());
				tx2.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
				tx2.setDescription("Returned - Interest Earned after Member Account closure");
				tx2.setAmount(savingAc.getTotalIntEnAm());
				tx2.setMemberBankAcNos(memberBankAcNos);
				txs.add(tx2);
			}
		}

		return txs;
	}

	public MemberSavingAcREST getMemberSavingAc(String lang, long memberSavingAcNo) throws BadRequestException {

		if(memberSavingAcNo <= 0) {
			throw new BadRequestException("Invalid Member Installment Account No");
		}

		MSavingAc account = daoFactory.getMSavingAcDAO().findById(memberSavingAcNo);
		
		return MemberSavingAcREST.convertAccountToREST(account);
	}

	public List<MemberSavingAcREST> getMemberSavingAcsForMember(String lang, long memberAcNo, String status) throws BadRequestException {

		if(!ConversionUtil.isValidMemberAcNo(memberAcNo)) {
			throw new BadRequestException("Invalid Member Account No");
		}

		List<MSavingAc> accounts = daoFactory.getMSavingAcDAO().getAllAcForMember(memberAcNo);
		
		List<MemberSavingAcREST> accountRESTList = new ArrayList<MemberSavingAcREST>();
		
		if(accounts != null && !accounts.isEmpty()) {
			for(MSavingAc account: accounts) {
				if(!status.equalsIgnoreCase("Active") ||
						EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
					accountRESTList.add(MemberSavingAcREST.convertAccountToREST(account));
				}
			}
		}
		
		return accountRESTList;
	}

	public List<MemberSavingAcREST> getMemberSavingAcsForGroup(String lang, long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		List<MSavingAc> accounts = daoFactory.getMSavingAcDAO().getAllAcForGroup(groupAcNo);
		
		List<MemberSavingAcREST> accountRESTList = new ArrayList<MemberSavingAcREST>();
		
		if(accounts != null && !accounts.isEmpty()) {
			for(MSavingAc account: accounts) {
				accountRESTList.add(MemberSavingAcREST.convertAccountToREST(account));
			}
		}
		
		return accountRESTList;
	}
}
