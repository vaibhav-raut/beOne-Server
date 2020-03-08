package com.beone.shg.net.persistence.spo;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MLoanAc;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.model.TxTodo;
import com.beone.shg.net.persistence.spo.MemberPO;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.TxTypeFormula;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;

public class MBuildTodoTxsPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MBuildTodoTxsPO.class);
	private final static String LATE_FEE = "LF";
	private final static String INTEREST_PENALTY = "IP";
	
	public MBuildTodoTxsPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {

			for(MSavingAc ac:memberAcInfo.getSavingAcs()) {
				
				// Verify If Todo Tx is already build 
				if(verifyAlreadyBuild(memberAcInfo, ac)) {

					// Build Todo Tx for this month
					buildTodoTransaction(memberAcInfo, ac);
				}

				// Updated Previous Missed Todo Tx
				updateMissedTx(memberAcInfo, ac);

				// Build Outstanding Saving
				buildOutstandingSaving(memberAcInfo, ac);
			}

			for(MLoanAc ac:memberAcInfo.getLoanAcs()) {

				if(ac.getPendingPrincipleAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > DataUtil.ZERO_INTEGER) {

					// Verify If Todo Tx is already build 
					if(verifyAlreadyBuild(memberAcInfo, ac)) {

						// Build Todo Tx for this month
						buildTodoTransaction(memberAcInfo, ac);
					}

					// Updated Previous Missed Todo Tx
					updateMissedTx(memberAcInfo, ac);
				}
			}			

		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}

	protected boolean verifyAlreadyBuild(MemberAcInfo memberAcInfo, MSavingAc savingAc) {

		boolean txPresent = false;

		if(BDUtil.add(memberAcInfo.getAc().getPlannedMonthlySavingAm(), memberAcInfo.getAc().getOutstandingSavedAm()).compareTo(DataUtil.ZERO_BIG_DECIMAL) <= DataUtil.ZERO_INTEGER) {
			return false;
		}
		
		for(TxTodo tx: memberAcInfo.getNextTodoTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Saving_Installment) &&
					tx.getMemberSavingAcNo() > 0 && 
					tx.getMemberSavingAcNo() == savingAc.getMSavingAcNo() &&
					EnumUtil.isTxTodoStatusTodo(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

				if(txPresent) {
					TxTodo txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tx.getTxTodoId());
					txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Auto_Rejected));
					txTodo.setDueDate(DateUtil.INVALID_DATE);
					memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);	

				} else {
					// Return False to not build new Todo Tx
					txPresent = true;

					// Check if the Installment Amount is Correct
					if(tx.getAmount().compareTo(savingAc.getSavingInstallmentAm()) != DataUtil.ZERO_INTEGER) {

						TxTodo txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tx.getTxTodoId());
						txTodo.setAmount(savingAc.getSavingInstallmentAm());
						memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);					
					}
				}
			}			
		}

		return !txPresent;
	}

	protected void buildTodoTransaction(MemberAcInfo memberAcInfo, MSavingAc savingAc) {

		TxTodo txTodo = new TxTodo();

		txTodo.setMemberAcNo(savingAc.getMAc().getMAcNo());
		txTodo.setMemberSavingAcNo(savingAc.getMSavingAcNo());
		txTodo.setAmount(savingAc.getSavingInstallmentAm());

		txTodo.setGroupAcNo(groupAcInfo.getGroupAcNo());
		txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Todo));
		txTodo.setDueDate(DateUtil.getDayOfMonth(groupAcInfo.getEndTime(), groupAcInfo.getGRules().getDueDayOfMonth()));
		txTodo.setCreationOnTs(DateUtil.getDayOfMonth(groupAcInfo.getEndTime(), groupAcInfo.getGRules().getComputeDayOfMonth()));

		txTodo.setTxTypeId(EnumCache.getIndexOfTxType(EnumConst.TxType_Saving_Installment));
		txTodo.setDescription("For " + DateUtil.getMonthName(groupAcInfo.getEndTime()) + " " + DateUtil.getYear(groupAcInfo.getEndTime()));

		int pModeId = EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_CASH);

		// Transaction Check
		for(Tx tx: memberAcInfo.getTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Saving_Installment)) {
				pModeId = tx.getPaymentModeId();
			}
		}

		txTodo.setExpectedPaymentModeId(pModeId);

		// Create Todo Transaction for Saving Account 
		memberAcInfo.getDaoFactory().getTxTodoDAO().persist(txTodo);
	}

	protected void updateMissedTx(MemberAcInfo memberAcInfo, MSavingAc savingAc) {

		// Carry Forward Previous Missed Transactions
		for(TxTodo tx: memberAcInfo.getTodoTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Saving_Installment) &&
					tx.getMemberSavingAcNo() > 0 && 
					tx.getMemberSavingAcNo() == savingAc.getMSavingAcNo() &&
					EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

				TxTodo txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tx.getTxTodoId());

				txTodo.setDueDate(DateUtil.getDayOfMonth(groupAcInfo.getEndTime(), groupAcInfo.getGRules().getDueDayOfMonth()));
				txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Previous_Missed));
				
				BigDecimal penalty = DataUtil.ZERO_BIG_DECIMAL;
				if(EnumCache.getNameOfMRole(memberAcInfo.getMprofile().getMroleId()).equals(EnumConst.MRole_Associate_Member)) {
					penalty = BDUtil.add(penalty, groupAcInfo.getGRules().getAmSavingLateFee());
					txTodo.setDescription(addFeeDescription(txTodo.getDescription(), LATE_FEE, groupAcInfo.getGRules().getAmSavingLateFee()));
				} else {
					penalty = BDUtil.add(penalty, groupAcInfo.getGRules().getCmSavingLateFee());
					txTodo.setDescription(addFeeDescription(txTodo.getDescription(), LATE_FEE, groupAcInfo.getGRules().getCmSavingLateFee()));
				}
				txTodo.setPenaltyAm(GenAlgoUtil.roundHalfUp(BDUtil.add(penalty, txTodo.getPenaltyAm()), 0));

				memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);					
			}
		}
	}

	protected void buildOutstandingSaving(MemberAcInfo memberAcInfo, MSavingAc savingAc) {

		if(memberAcInfo.getAc().getLastUpdatedTs().getTime() < this.groupAcInfo.getComputationDate().getTime()) {

			// Build transaction Object & Save		
			Tx transaction = new Tx();

			int txTypeId = EnumCache.getIndexOfTxType(EnumConst.TxType_Outstanding_Saving_Inst);
			transaction.setTxTypeId(txTypeId);
			transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Auto_Approved));
			transaction.setReceiptVoucherNo("");
			transaction.setChequeNo("");

			transaction.setMemberAcNo(memberAcInfo.getAc().getMAcNo());
			transaction.setGroupAcNo(this.groupAcInfo.getGroupAcNo());
			transaction.setDoneByMemberAcNo(EnumConst.SystemAcNo);
			transaction.setApprovedByMemberAcNo(EnumConst.SystemAcNo);
			transaction.setMemberSavingAcNo(savingAc.getMSavingAcNo());
			transaction.setAmount(savingAc.getSavingInstallmentAm());
			transaction.setPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_INTERNAL));
			transaction.setEntryTs(this.groupAcInfo.getComputationDate());
			transaction.setPaymentTs(this.groupAcInfo.getComputationDate());
			transaction.setApprovedTs(this.groupAcInfo.getComputationDate());

			transaction.setDescription(DateUtil.getMonthName(groupAcInfo.getStartTime()) + " " + DateUtil.getYear(groupAcInfo.getEndTime()) + 
					" - " + EnumConst.TxType_Outstanding_Saving_Inst);
			transaction.setEntryLocation("");

			// Save Transaction to DB
			memberAcInfo.getDaoFactory().getTxDAO().persist(transaction);

			TxTypeFormula formula = EnumCache.getTxTypeFormula(transaction.getTxTypeId());
			if(formula.getFormulaOnDone() != null) {		
				memberAcInfo.getProcessJobBuilder().pushPostProcessJob(formula.getFormulaOnDone(), transaction);
			}
			
			MAc mAc = memberAcInfo.getDaoFactory().getMAcDAO().findById(memberAcInfo.getAc().getMAcNo());
			mAc.setLastUpdatedTs(this.groupAcInfo.getComputationDate());
			memberAcInfo.getDaoFactory().getMAcDAO().merge(mAc);
		}

	}

	protected boolean verifyAlreadyBuild(MemberAcInfo memberAcInfo, MLoanAc loanAc) {

		boolean txPresent = false;

		for(TxTodo tx: memberAcInfo.getNextTodoTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Loan_Installment) &&
					tx.getMemberLoanAcNo() > 0 && 
					tx.getMemberLoanAcNo() == loanAc.getMLoanAcNo() &&
					EnumUtil.isTxTodoStatusTodo(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

				if(txPresent) {
					TxTodo txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tx.getTxTodoId());
					txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Auto_Rejected));
					txTodo.setDueDate(DateUtil.INVALID_DATE);
					memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);	

				} else {
					// Return False to not build new Todo Tx
					txPresent = true;

					// Check if the Installment Amount is Correct
					if(tx.getAmount().compareTo(loanAc.getInstallmentAm()) != DataUtil.ZERO_INTEGER) {

						TxTodo txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tx.getTxTodoId());
						txTodo.setAmount(loanAc.getInstallmentAm());
						memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);					
					}
				}
			}			
		}

		return !txPresent;
	}

	protected void buildTodoTransaction(MemberAcInfo memberAcInfo, MLoanAc loanAc) {

		TxTodo txTodo = new TxTodo();

		txTodo.setMemberAcNo(loanAc.getMAc().getMAcNo());
		txTodo.setMemberLoanAcNo(loanAc.getMLoanAcNo());
		
		BigDecimal pricipleInTodoTx = DataUtil.ZERO_BIG_DECIMAL;
		
		for(TxTodo tx: memberAcInfo.getTodoTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Loan_Installment) &&
					tx.getMemberLoanAcNo() > 0 && 
					tx.getMemberLoanAcNo() == loanAc.getMLoanAcNo() &&
					EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

				pricipleInTodoTx = pricipleInTodoTx.add(tx.getAmount());
			}
		}
		
		BigDecimal actualPendingPrin = loanAc.getPendingPrincipleAm().subtract(pricipleInTodoTx);
		BigDecimal interestComponent = GenAlgoUtil.roundHalfUp(GenAlgoUtil.computeInterestOnLoan(loanAc.getPendingPrincipleAm(), GenAlgoUtil.getMonthlyIntFactor(loanAc.getRateOfInterest())), 0);

		switch(EnumCache.getNameOfEnumValue(EnumConst.LoanCalculation, loanAc.getLoanCalculationId())) {
		
		case EnumConst.LoanCalculation_Reducing_Interest:
		{
			if(actualPendingPrin.compareTo(loanAc.getInstallmentAm()) >= DataUtil.ZERO_INTEGER) {
				txTodo.setAmount(GenAlgoUtil.roundHalfUp(BDUtil.add(loanAc.getInstallmentAm(), interestComponent), 0));
			} else {
				txTodo.setAmount(GenAlgoUtil.roundHalfUp(BDUtil.add(actualPendingPrin, interestComponent), 0));
			}
			txTodo.setInterestComponent(interestComponent);
			
			break;
		}
		
		case EnumConst.LoanCalculation_Normal_EMI:
		case EnumConst.LoanCalculation_Fixed_EMI:
		{
			if(actualPendingPrin.compareTo(BDUtil.sub(loanAc.getInstallmentAm(), interestComponent)) >= DataUtil.ZERO_INTEGER) {
				txTodo.setAmount(GenAlgoUtil.roundHalfUp(loanAc.getInstallmentAm(), 0));
			} else {
				txTodo.setAmount(GenAlgoUtil.roundHalfUp(BDUtil.add(actualPendingPrin, interestComponent), 0));
			}
			txTodo.setInterestComponent(interestComponent);
			
			break;
		}
		
		}

		txTodo.setGroupAcNo(groupAcInfo.getGroupAcNo());
		txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Todo));
		txTodo.setDueDate(DateUtil.getDayOfMonth(groupAcInfo.getEndTime(), groupAcInfo.getGRules().getDueDayOfMonth()));
		txTodo.setCreationOnTs(DateUtil.getDayOfMonth(groupAcInfo.getEndTime(), groupAcInfo.getGRules().getComputeDayOfMonth()));

		txTodo.setTxTypeId(EnumCache.getIndexOfTxType(EnumConst.TxType_Loan_Installment));
		txTodo.setDescription("For " + DateUtil.getMonthName(groupAcInfo.getEndTime()) + " " + DateUtil.getYear(groupAcInfo.getEndTime()));

		int pModeId = EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_CASH);
		for(Tx tx: memberAcInfo.getTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Loan_Installment)) {
				pModeId = tx.getPaymentModeId();
			}
		}

		txTodo.setExpectedPaymentModeId(pModeId);

		memberAcInfo.getDaoFactory().getTxTodoDAO().persist(txTodo);

	}

	protected void updateMissedTx(MemberAcInfo memberAcInfo, MLoanAc loanAc) {

		// Carry Forward Previous Missed Transactions
		for(TxTodo tx: memberAcInfo.getTodoTransactions()) {
			if(EnumCache.getNameOfTxType(tx.getTxTypeId()).equals(EnumConst.TxType_Loan_Installment) &&
					tx.getMemberLoanAcNo() > 0 && 
					tx.getMemberLoanAcNo() == loanAc.getMLoanAcNo() &&
					EnumUtil.isTxTodoStatusMissed(EnumCache.getNameOfEnumValue(EnumConst.TxTodoStatus, tx.getTxTodoStatusId()))) {

				TxTodo txTodo = memberAcInfo.getDaoFactory().getTxTodoDAO().findById(tx.getTxTodoId());

				txTodo.setDueDate(DateUtil.getDayOfMonth(groupAcInfo.getEndTime(), groupAcInfo.getGRules().getDueDayOfMonth()));
				txTodo.setTxTodoStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxTodoStatus, EnumConst.TxTodoStatus_Previous_Missed));
				
				// Penalty Calculation 
				// 1 - Interest on pending principle
				// 2 - Late fee
				BigDecimal ipOnAmount = BDUtil.add(txTodo.getAmount(), txTodo.getInterestComponent());
				BigDecimal penalty = GenAlgoUtil.roundHalfUp(GenAlgoUtil.computeInterestOnLoan(ipOnAmount, 
						GenAlgoUtil.getMonthlyIntFactor(loanAc.getRateOfInterest())), 0);
						
				txTodo.setDescription(addFeeDescription(txTodo.getDescription(), INTEREST_PENALTY, penalty.intValue()));
				
				if(EnumCache.getNameOfMRole(memberAcInfo.getMprofile().getMroleId()).equals(EnumConst.MRole_Associate_Member)) {
					penalty = BDUtil.add(penalty, groupAcInfo.getGRules().getAmLoanLateFee());
					txTodo.setDescription(addFeeDescription(txTodo.getDescription(), LATE_FEE, groupAcInfo.getGRules().getAmLoanLateFee()));
				} else {
					penalty = BDUtil.add(penalty, groupAcInfo.getGRules().getCmLoanLateFee());
					txTodo.setDescription(addFeeDescription(txTodo.getDescription(), LATE_FEE, groupAcInfo.getGRules().getCmLoanLateFee()));
				}
				
				if(txTodo.getPenaltyAm() != null) {
					penalty = BDUtil.add(penalty, txTodo.getPenaltyAm());
				}
				txTodo.setPenaltyAm(GenAlgoUtil.roundHalfUp(penalty, 0));

				memberAcInfo.getDaoFactory().getTxTodoDAO().merge(txTodo);					
			}
		}
	}

	protected String addFeeDescription(String curDes, String type, int fee) {
		
		StringBuilder sb = new StringBuilder(curDes);
		
		if(!curDes.contains(", Fee=")) {
			sb.append(", Fee=");
		}
		sb.append(" " + type + ":" + fee);
		
		return sb.toString();
	}
}
