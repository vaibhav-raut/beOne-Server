package com.beone.shg.net.persistence.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.beone.shg.net.config.DBConst;
import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.job.DataFeildsUpdateJob;
import com.beone.shg.net.persistence.job.JobQueueManager;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GInvtAc;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.resp.Attachment;
import com.beone.shg.net.webservice.rest.model.resp.InvtPlan;
import com.beone.shg.net.webservice.rest.model.resp.Transaction;
import com.beone.shg.net.webservice.rest.model.resp.Transactions;
import com.beone.shg.net.webservice.rest.model.rest.GroupInvtAcREST;
import com.beone.shg.net.webservice.rest.support.BadRequestException;

@Component("groupInvtAcBO")
public class GroupInvtAcBO extends BaseBO {

	@Autowired
	@Qualifier("transactionBO")
	private TransactionBO transactionBO;

	@Autowired
	@Qualifier("attachmentBO")
	private AttachmentBO attachmentBO;

	public GroupInvtAcREST add(GroupInvtAcREST accountREST) throws BadRequestException {
		return add(accountREST, false);
	}
	
	public GroupInvtAcREST add(GroupInvtAcREST accountREST, boolean oldData) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No: " + accountREST.getGroupAcNo());
		}
		if(accountREST.getBankGroupAcNo() <= 0 && 
				daoFactory.getGProfileDAO().getReferenceById(accountREST.getBankGroupAcNo()) != null) {
			throw new BadRequestException("Invalid Bank Group Ac No: " + accountREST.getBankGroupAcNo());
		}
		if(accountREST.getInvtAm() == null || accountREST.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
			throw new BadRequestException("Invalid Investment Amount");
		}
		if(accountREST.getInvestmentType() == null ||
				accountREST.getInvestmentType().isEmpty() ||
				EnumCache.getIndexOfEnumValue(EnumConst.InvestmentType, accountREST.getInvestmentType()) <= 0) {
			throw new BadRequestException("Invalid Investment Type: " + accountREST.getInvestmentType());
		}

		GInvtAc account = new GInvtAc();		
		account.setGAc(daoFactory.getGAcDAO().getReferenceById(accountREST.getGroupAcNo()));
		account.setBGroupAcNo(accountREST.getBankGroupAcNo());
		
		if(accountREST.getInvtBankAcNo() > 0 && daoFactory.getGBankAccountDAO().getReferenceById(accountREST.getInvtBankAcNo()) != null) {
			account.setInvtBankAcNo(accountREST.getInvtBankAcNo());
		}
		
		if(oldData) {
			addOldAc(account, accountREST);
		} else {
			addNewAc(account, accountREST);
		}
		
		daoFactory.getGInvtAcDAO().persist(account);
		
		// Load Account ID back
		accountREST.setGroupInvtAcNo(account.getGInvtAcNo());
		
		return accountREST;
	}
	
	protected void addNewAc(GInvtAc account, GroupInvtAcREST accountREST) throws BadRequestException {
		
		account.setInvestmentTypeId(EnumCache.getIndexOfEnumValue(EnumConst.InvestmentType, accountREST.getInvestmentType()));
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Submitted));
		accountREST.setAccountStatus(EnumConst.AccountStatus_Submitted);
		if(accountREST.getApprovedByMember() > 0 &&
				daoFactory.getMProfileDAO().getReferenceById(accountREST.getApprovedByMember()) != null) {
			account.setApprovedByMember(accountREST.getApprovedByMember());
		}
		account.setInvestmentNo(accountREST.getInvestmentNo());
		account.setInvestmentAcName(accountREST.getInvestmentAcName());
		account.setInvestmentDesc(accountREST.getInvestmentDesc());
		account.setInvtAm(accountREST.getInvtAm());
		account.setRecInvtAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setRecInterestAm(DataUtil.ZERO_BIG_DECIMAL);
		account.setProjInterestAm(accountREST.getProjInterestAm());
		account.setInterestRate(accountREST.getInterestRate());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setMaturityDate(DateUtil.convertStringToDate(accountREST.getMaturityDate()));
		account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
	}
	
	protected void addOldAc(GInvtAc account, GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getAccountStatus() == null ||
				accountREST.getAccountStatus().isEmpty() ||
				EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()) <= 0) {
			throw new BadRequestException("Invalid Account Status: " + accountREST.getAccountStatus());
		}

		account.setInvestmentTypeId(EnumCache.getIndexOfEnumValue(EnumConst.InvestmentType, accountREST.getInvestmentType()));
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
		if(accountREST.getApprovedByMember() > 0 &&
				daoFactory.getMProfileDAO().getReferenceById(accountREST.getApprovedByMember()) != null) {
			account.setApprovedByMember(accountREST.getApprovedByMember());
		}
		account.setInvestmentNo(accountREST.getInvestmentNo());
		account.setInvestmentAcName(accountREST.getInvestmentAcName());
		account.setInvestmentDesc(accountREST.getInvestmentDesc());
		account.setInvtAm(accountREST.getInvtAm());
		account.setRecInvtAm(accountREST.getRecInvtAm());
		account.setRecInterestAm(accountREST.getRecInterestAm());
		account.setProjInterestAm(accountREST.getProjInterestAm());
		account.setInterestRate(accountREST.getInterestRate());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setApprovedDate(DateUtil.convertStringToDate(accountREST.getApprovedDate()));
		account.setMaturityDate(DateUtil.convertStringToDate(accountREST.getMaturityDate()));
		account.setClosureDate(DateUtil.convertStringToDate(accountREST.getClosureDate()));
		account.setDevelopmentPlan(accountREST.getDevelopmentPlan());
	}

	public GroupInvtAcREST updateGroupInvtAcData(GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupInvtAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(accountREST.getGroupInvtAcNo());
		
		account.setInvestmentTypeId(EnumCache.getIndexOfEnumValue(EnumConst.InvestmentType, accountREST.getInvestmentType()));
		account.setAccountStatusId(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()));
		if(accountREST.getApprovedByMember() > 0 &&
				daoFactory.getMProfileDAO().getReferenceById(accountREST.getApprovedByMember()) != null) {
			account.setApprovedByMember(accountREST.getApprovedByMember());
		}
		account.setInvtAm(accountREST.getInvtAm());
		account.setRecInvtAm(accountREST.getRecInvtAm());
		account.setRecInterestAm(accountREST.getRecInterestAm());
		account.setProjInterestAm(accountREST.getProjInterestAm());
		account.setInterestRate(accountREST.getInterestRate());
		account.setRequestedDate(DateUtil.convertStringToDate(accountREST.getRequestedDate()));
		account.setApprovedDate(DateUtil.convertStringToDate(accountREST.getApprovedDate()));
		account.setMaturityDate(DateUtil.convertStringToDate(accountREST.getMaturityDate()));
		account.setClosureDate(DateUtil.convertStringToDate(accountREST.getClosureDate()));
		
		daoFactory.getGInvtAcDAO().merge(account);
		
		return accountREST;
	}

	public GroupInvtAcREST approveReject(GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupInvtAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}
		if(accountREST.getAccountStatus() == null ||
				accountREST.getAccountStatus().isEmpty() ||
				EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus()) <= 0) {
			throw new BadRequestException("Invalid Account Status: " + accountREST.getAccountStatus());
		}
		if(!EnumUtil.isAccountStatusApproveReject(accountREST.getAccountStatus())) {
			throw new BadRequestException("Invalid Account Status : " + accountREST.getAccountStatus());
		}		
		int accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, accountREST.getAccountStatus());
		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Approval Status:" + accountREST.getAccountStatus());
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(accountREST.getGroupInvtAcNo());
		
		if(!EnumUtil.isAccountStatusApprovable(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
			throw new BadRequestException("Can't Update Group Invtestment Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}
		
		account.setAccountStatusId(accountStatusId);
		account.setApprovedDate(DateUtil.convertStringToDateWithCurrentDefault(accountREST.getApprovedDate()));
		
		daoFactory.getGInvtAcDAO().merge(account);
		
		return accountREST;
	}

	public GroupInvtAcREST activate(GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupInvtAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		int accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active);

		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Approval Status:" + accountREST.getAccountStatus());
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(accountREST.getGroupInvtAcNo());

		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
			throw new BadRequestException("Can't Update Group Invtestment Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		account.setAccountStatusId(accountStatusId);
		account.setApprovedDate(DateUtil.convertStringToDateWithCurrentDefault(accountREST.getApprovedDate()));

		daoFactory.getGInvtAcDAO().merge(account);
		
		// Update Group Account
		GAc gAc = daoFactory.getGAcDAO().findById(accountREST.getGroupAcNo());
		if(gAc == null) {
			throw new BadRequestException("Invalid Group Account: " + accountREST.getGroupAcNo());
		}
		
		// Update SHG Project Development Investment
		if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development)) {
			gAc.setNoOfProject(gAc.getNoOfProject() + 1);
			gAc.setNoOfActiveProject(gAc.getNoOfActiveProject() + 1);
			gAc.setPProjIntOnLoanAm(BDUtil.add(gAc.getPProjIntOnLoanAm(), accountREST.getProjInterestAm()));
		} 
		// Update Fix Deposit Investment
		else if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit)) {
			gAc.setNoOfFixDeposit(gAc.getNoOfFixDeposit() + 1);
			gAc.setNoOfActiveFixDeposit(gAc.getNoOfActiveFixDeposit() + 1);
			gAc.setProjIntOnFixDepositAm(BDUtil.add(gAc.getProjIntOnFixDepositAm(), accountREST.getProjInterestAm()));
		}
		// Update Other Investment
		else {
			gAc.setNoOfOtherInv(gAc.getNoOfOtherInv() + 1);
			gAc.setNoOfActiveOtherInv(gAc.getNoOfActiveOtherInv() + 1);
			gAc.setProjIntOnOtherInvAm(BDUtil.add(gAc.getProjIntOnOtherInvAm(), accountREST.getProjInterestAm()));
		}
		daoFactory.getGAcDAO().merge(gAc);
		
		// Do Transactions for Activation
		if(accountREST.getTransactions() != null && !accountREST.getTransactions().isEmpty()) {
			transactionBO.addTransactions(accountREST.getTransactions());
		}

		return accountREST;
	}

	public Transactions getTxsToActivate(GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupInvtAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(accountREST.getGroupInvtAcNo());

		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Approved)) {
			throw new BadRequestException("Can't Update Group Invtestment Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		Transactions txs = new Transactions();				

		Transaction tx = new Transaction();
		
		if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development)) {
			tx.setTxType(EnumConst.TxType_Group_Project_Dev_Fund);
			tx.setTxAcType(EnumConst.TxAcType_Project_Development);
			tx.setDescription("SHG Project Devlopment Fund of = " + account.getInvtAm());
		} 
		else if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit)) {
			tx.setTxType(EnumConst.TxType_Fix_Deposit_Investment);
			tx.setTxAcType(EnumConst.TxAcType_Fix_Deposit);
			tx.setDescription("Fix Deposit Investment of = " + account.getInvtAm());
		}
		else {
			tx.setTxType(EnumConst.TxType_Other_Investment);
			tx.setTxAcType(EnumConst.TxAcType_Group_Investment);
			tx.setDescription("Other Investment of = " + account.getInvtAm());
		}
		
		tx.setSlipType(EnumConst.SlipType_VOUCHER);
		tx.setGroupInvtAcNo(account.getGInvtAcNo());
		tx.setGroupAcNo(account.getGAc().getGAcNo());
		tx.setExternalGroupAcNo(account.getBGroupAcNo());
		tx.setExternalGroupBankAcNo(account.getInvtBankAcNo());
		tx.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx.setAmount(account.getInvtAm());
		
		txs.addTransaction(tx);

		return txs;
	}

	public GroupInvtAcREST close(GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupInvtAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}
		if(accountREST.getAccountStatus() == null) {
			throw new BadRequestException("Invalid Account Status");
		}

		int accountStatusId = EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Complete);

		if(accountStatusId <= DataUtil.ZERO_INTEGER) {
			throw new BadRequestException("Invalid Approval Status:" + accountREST.getAccountStatus());
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(accountREST.getGroupInvtAcNo());

		if(!EnumUtil.isAccountStatusActive(EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()))) {
			throw new BadRequestException("Can't Update Group Invtestment Account Status: " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		account.setAccountStatusId(accountStatusId);
		account.setApprovedDate(DateUtil.convertStringToDateWithCurrentDefault(accountREST.getApprovedDate()));

		daoFactory.getGInvtAcDAO().merge(account);
		
		if(accountREST.getTransactions() != null && accountREST.getTransactions().isEmpty()) {
			transactionBO.addTransactions(accountREST.getTransactions());
		}

		return accountREST;
	}

	public Transactions getTxsToRecover(GroupInvtAcREST accountREST) throws BadRequestException {

		if(accountREST.getGroupInvtAcNo() <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}
		if(!ConversionUtil.isValidGroupAcNo(accountREST.getGroupAcNo())) {
			throw new BadRequestException("Invalid Group Account No");
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(accountREST.getGroupInvtAcNo());

		if(!EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()).equals(EnumConst.AccountStatus_Active)) {
			throw new BadRequestException("Account status Not 'Active' it is : " + 
					EnumCache.getNameOfEnumValue(EnumConst.AccountStatus, account.getAccountStatusId()));
		}

		Transactions txs = new Transactions();				

		// Transaction for Recovery of Investment Amount
		Transaction tx1 = new Transaction();				
		if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development)) {
			tx1.setTxType(EnumConst.TxType_Group_Project_Recovery);
			tx1.setTxAcType(EnumConst.TxAcType_Project_Development);
			tx1.setDescription("Recovered SHG Project Devlopment Fund!");
		} 
		else if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit)) {
			tx1.setTxType(EnumConst.TxType_Fix_Deposit_Recovery);
			tx1.setTxAcType(EnumConst.TxAcType_Fix_Deposit);
			tx1.setDescription("Recovered Fix Deposit Investment!");
		} else {
			tx1.setTxType(EnumConst.TxType_Other_Investment_Recovery);
			tx1.setTxAcType(EnumConst.TxAcType_Group_Investment);
			tx1.setDescription("Recovered Other Investment!");
		}
		tx1.setSlipType(EnumConst.SlipType_RECEIPT);
		tx1.setGroupInvtAcNo(account.getGInvtAcNo());
		tx1.setGroupAcNo(account.getGAc().getGAcNo());
		tx1.setExternalGroupAcNo(account.getBGroupAcNo());
		tx1.setExternalGroupBankAcNo(account.getInvtBankAcNo());
		tx1.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx1.setAmount(BDUtil.sub(account.getInvtAm(), account.getRecInvtAm()));
		txs.addTransaction(tx1);

		// Transaction for Recovery of Interest or Earning on Investment Amount
		Transaction tx2 = new Transaction();				
		if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Project_Development)) {
			tx2.setTxType(EnumConst.TxType_Group_Project_Earning);
			tx2.setTxAcType(EnumConst.TxAcType_Project_Development);
			tx2.setDescription("Recovered Earnings on SHG Project!");
		} 
		else if(EnumCache.getNameOfEnumValue(EnumConst.InvestmentType, account.getInvestmentTypeId()).equals(EnumConst.InvestmentType_Fix_Deposit)) {
			tx2.setTxType(EnumConst.TxType_Fix_Deposit_Interest);
			tx2.setTxAcType(EnumConst.TxAcType_Fix_Deposit);
			tx2.setDescription("Recovered Interest on Fix Deposit!");
		} else {
			tx2.setTxType(EnumConst.TxType_Other_Investment_Earning);
			tx2.setTxAcType(EnumConst.TxAcType_Group_Investment);
			tx2.setDescription("Recovered Earning on Other Investment!");
		}
		tx2.setSlipType(EnumConst.SlipType_RECEIPT);
		tx2.setGroupInvtAcNo(account.getGInvtAcNo());
		tx2.setGroupAcNo(account.getGAc().getGAcNo());
		tx2.setExternalGroupAcNo(account.getBGroupAcNo());
		tx2.setExternalGroupBankAcNo(account.getInvtBankAcNo());
		tx2.setPaymentMode(EnumConst.PaymentMode_CHEQUE);
		tx2.setAmount(account.getProjInterestAm());
		txs.addTransaction(tx2);

		return txs;
	}

	public GroupInvtAcREST getGroupInvtAc(String lang, long groupInvtAcNo) throws BadRequestException {

		if(groupInvtAcNo <= 0) {
			throw new BadRequestException("Invalid Group Invt Account No");
		}

		GInvtAc account = daoFactory.getGInvtAcDAO().findById(groupInvtAcNo);
		
		return GroupInvtAcREST.convertAccountToREST(account);
	}

	public List<GroupInvtAcREST> getGroupInvtAcs(String lang, long groupAcNo) throws BadRequestException {

		if(!ConversionUtil.isValidGroupAcNo(groupAcNo)) {
			throw new BadRequestException("Invalid Group Account No");
		}

		List<GInvtAc> accounts = daoFactory.getGInvtAcDAO().getAllAcForGroup(groupAcNo);
		
		List<GroupInvtAcREST> accountRESTList = new ArrayList<GroupInvtAcREST>();
		
		if(accounts != null && !accounts.isEmpty()) {
			for(GInvtAc account: accounts) {
				accountRESTList.add(GroupInvtAcREST.convertAccountToREST(account));
			}
		}
		
		return accountRESTList;
	}
	
	public InvtPlan getInvtPlanning(InvtPlan plan) throws BadRequestException {
		
		
		if(plan.getInvtAm() == null || plan.getInvtAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= 0) {
			throw new BadRequestException("Invalid Investment Amount!");
		}
		if(plan.getRateOfInterest() <= 0.0) {
			throw new BadRequestException("Invalid Investment Rate of Interest!");
		}
		if(plan.getNoOfMonths() <= 0) {
			throw new BadRequestException("Invalid Investment Duration!");
		}
		if(plan.getInvestmentType() == null || plan.getInvestmentType().isEmpty()) {
			throw new BadRequestException("Invalid Investment Type!");
		}

		// Compute Investment Plan 
		return GenAlgoUtil.compInvestmentPlan(plan);
	}
	
    public List<Attachment> attachFile(long groupAcNo, long groupInvtAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	
    	String attachmentInfo = attachmentBO.saveFile(groupAcNo, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_ADD, DBConst.G_INVT_AC, groupInvtAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public List<Attachment> updateAttachFile(long groupAcNo, long docId, long groupInvtAcNo, String fileName, int docTypeId, byte[] fileByte) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	GInvtAc gInvtAc = daoFactory.getGInvtAcDAO().findById(groupInvtAcNo);
    	if(!gInvtAc.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(gInvtAc.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Group Investment Account!");
    	}
    	
    	String attachmentInfo = attachmentBO.updateFile(groupAcNo, docId, fileName, docTypeId, fileByte);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, attachmentInfo);
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_UPDATE, DBConst.G_INVT_AC, groupInvtAcNo, dataFeilds));
		
    	return Attachment.buildAttachments(attachmentInfo);
    }
	
    public void deleteAttachFile(long groupAcNo, long docId, long groupInvtAcNo) throws BadRequestException, IOException {
    	if(docId <= 0) {
    		throw new BadRequestException("Invalid Doc!");
    	}
    	GInvtAc gInvtAc = daoFactory.getGInvtAcDAO().findById(groupInvtAcNo);
    	if(!gInvtAc.getAttachmentUrl().contains(DBConst.ATTACH_EXTERNAL_DILIMITER + docId + DBConst.ATTACH_INTER_DILIMITER) &&
    			(gInvtAc.getAttachmentUrl().indexOf(docId + DBConst.ATTACH_INTER_DILIMITER) != 0)) {
    		throw new BadRequestException("Invalid Doc ID for the Group Investment Account!");
    	}
    	
    	attachmentBO.deleteFile(groupAcNo, docId);
    	Map<String, String> dataFeilds = new HashMap<String, String>();
    	dataFeilds.put(DBConst.ATTACHMENT_URL, Long.toString(docId));
    	
		JobQueueManager.addToJobQueue(groupAcNo, new DataFeildsUpdateJob(daoFactory, DBConst.ATTACH_ALGO_DELETE, DBConst.G_INVT_AC, groupInvtAcNo, dataFeilds));
    }
}
