package com.beone.shg.net.persistence.mpo.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.support.EnumCache;

public class TxMessageUtil {
	
	public final static String SAVING_DONE = "Saving Done";
	public final static String SAVING_UNDONE = "Saving Undone";
	public final static String SAVING_REJECTED = "Saving Rejected";

	public final static String SAVING_DONE_LF_PAID = "Saving Done LF Paid";
	public final static String SAVING_UNDONE_LF_PAID = "Saving Undone LF Paid";
	public final static String SAVING_REJECTED_LF_PAID = "Saving Rejected LF Paid";

	public final static String SAVING_DONE_LF_OUT = "Saving Done LF Out";
	public final static String SAVING_UNDONE_LF_OUT = "Saving Undone LF Out";
	public final static String SAVING_REJECTED_LF_OUT = "Saving Rejected LF Out";

	public final static String LOAN_PRINCIPLE_DONE = "Loan Principle Done";
	public final static String LOAN_PRINCIPLE_UNDONE = "Loan Principle Undone";
	public final static String LOAN_PRINCIPLE_REJECTED = "Loan Principle Rejected";

	public final static String LOAN_INTEREST_DONE = "Loan Interest Done";
	public final static String LOAN_INTEREST_UNDONE = "Loan Interest Undone";
	public final static String LOAN_INTEREST_REJECTED = "Loan Interest Rejected";

	public final static String LOAN_DONE = "Loan Done";
	public final static String LOAN_UNDONE = "Loan Undone";
	public final static String LOAN_REJECTED = "Loan Rejected";

	public final static String LOAN_DONE_LF_PAID = "Loan Done LF Paid";
	public final static String LOAN_UNDONE_LF_PAID = "Loan Undone LF Paid";
	public final static String LOAN_REJECTED_LF_PAID = "Loan Rejected LF Paid";

	public final static String LOAN_DONE_LF_OUT = "Loan Done LF Out";
	public final static String LOAN_UNDONE_LF_OUT = "Loan Undone LF Out";
	public final static String LOAN_REJECTED_LF_OUT = "Loan Rejected LF Out";

	public final static String LOAN_IC_DONE_LF_PAID = "Loan IC Done LF Paid";
	public final static String LOAN_IC_UNDONE_LF_PAID = "Loan IC Undone LF Paid";
	public final static String LOAN_IC_REJECTED_LF_PAID = "Loan IC Rejected LF Paid";

	public final static String LOAN_IC_DONE_LF_OUT = "Loan IC Done LF Out";
	public final static String LOAN_IC_UNDONE_LF_OUT = "Loan IC Undone LF Out";
	public final static String LOAN_IC_REJECTED_LF_OUT = "Loan IC Rejected LF Out";

	public final static String BANK_TRANSFER_DONE = "Bank Transfer Done";
	public final static String BANK_TRANSFER_UNDONE = "Bank Transfer Undone";
	public final static String BANK_TRANSFER_REJECTED = "Bank Transfer Rejected";

	public final static String BANK_WITHDRAWAL_DONE = "Bank Withdrawal Done";
	public final static String BANK_WITHDRAWAL_UNDONE = "Bank Withdrawal Undone";
	public final static String BANK_WITHDRAWAL_REJECTED = "Bank Withdrawal Rejected";

	public final static String LOAN_DISBURSEMENT_DONE = "Loan Disbursement Done";
	public final static String LOAN_DISBURSEMENT_UNDONE = "Loan Disbursement Undone";
	public final static String LOAN_DISBURSEMENT_REJECTED = "Loan Disbursement Rejected";

	public final static String LOAN_DISBURSEMENT_LP_DONE = "Loan Disbursement LP Done";
	public final static String LOAN_DISBURSEMENT_LP_UNDONE = "Loan Disbursement LP Undone";
	public final static String LOAN_DISBURSEMENT_LP_REJECTED = "Loan Disbursement LP Rejected";

	public final static String LOAN_DISBURSEMENT_LP_PII_DONE = "Loan Disbursement LP PII Done";
	public final static String LOAN_DISBURSEMENT_LP_PII_UNDONE = "Loan Disbursement LP PII Undone";
	public final static String LOAN_DISBURSEMENT_LP_PII_REJECTED = "Loan Disbursement LP PII Rejected";

	public final static String LOAN_DISBURSEMENT_PII_DONE = "Loan Disbursement PII Done";
	public final static String LOAN_DISBURSEMENT_PII_UNDONE = "Loan Disbursement PII Undone";
	public final static String LOAN_DISBURSEMENT_PII_REJECTED = "Loan Disbursement PII Rejected";

	public final static String REGISTRATION_DONE = "Registration Done";
	public final static String REGISTRATION_UNDONE = "Registration Undone";
	public final static String REGISTRATION_REJECTED = "Registration Rejected";

	public final static String REGISTRATION_SI_DONE = "Registration SI Done";
	public final static String REGISTRATION_SI_UNDONE = "Registration SI Undone";
	public final static String REGISTRATION_SI_REJECTED = "Registration SI Rejected";

	public final static String BANK_LOAN_DISB_DONE = "Bank Loan Disb Done";
	public final static String BANK_LOAN_DISB_UNDONE = "Bank Loan Disb Undone";
	public final static String BANK_LOAN_DISB_REJECTED = "Bank Loan Disb Rejected";

	public final static String BANK_LOAN_DISB_BC_DONE = "Bank Loan Disb BC Done";
	public final static String BANK_LOAN_DISB_BC_UNDONE = "Bank Loan Disb BC Undone";
	public final static String BANK_LOAN_DISB_BC_REJECTED = "Bank Loan Disb BC Rejected";

	public final static String BANK_LOAN_DISB_BC_PII_DONE = "Bank Loan Disb BC PII Done";
	public final static String BANK_LOAN_DISB_BC_PII_UNDONE = "Bank Loan Disb BC PII Undone";
	public final static String BANK_LOAN_DISB_BC_PII_REJECTED = "Bank Loan Disb BC PII Rejected";

	public final static String BANK_LOAN_DISB_PII_DONE = "Bank Loan Disb PII Done";
	public final static String BANK_LOAN_DISB_PII_UNDONE = "Bank Loan Disb PII Undone";
	public final static String BANK_LOAN_DISB_PII_REJECTED = "Bank Loan Disb PII Rejected";

	public final static String FD_INVT_DONE = "FD Invt Done";
	public final static String FD_INVT_UNDONE = "FD Invt Undone";
	public final static String FD_INVT_REJECTED = "FD Invt Rejected";

	public final static String FD_INVT_BC_DONE = "FD Invt BC Done";
	public final static String FD_INVT_BC_UNDONE = "FD Invt BC Undone";
	public final static String FD_INVT_BC_REJECTED = "FD Invt BC Rejected";

	public final static String OTHER_INVT_DONE = "Other Invt Done";
	public final static String OTHER_INVT_UNDONE = "Other Invt Undone";
	public final static String OTHER_INVT_REJECTED = "Other Invt Rejected";

	public final static String OTHER_INVT_BC_DONE = "Other Invt Done";
	public final static String OTHER_INVT_BC_UNDONE = "Other Invt Undone";
	public final static String OTHER_INVT_BC_REJECTED = "Other Invt Rejected";

	public static String getMessageForTxType(String txType, String action) {
		
		switch(txType + action) {

		case (EnumConst.TxType_Saving_Installment + EnumConst.TxStatus_Submitted):
			return SAVING_DONE;
			
		case (EnumConst.TxType_Saving_Installment + EnumConst.TxStatus_Undone):
			return SAVING_UNDONE;
			
		case (EnumConst.TxType_Saving_Installment + EnumConst.TxStatus_Rejected):
			return SAVING_REJECTED;

		
		case (EnumConst.TxType_Saving_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Submitted):
			return SAVING_DONE_LF_PAID;

		case (EnumConst.TxType_Saving_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Undone):
			return SAVING_UNDONE_LF_PAID;

		case (EnumConst.TxType_Saving_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Rejected):
			return SAVING_REJECTED_LF_PAID;


		case (EnumConst.TxType_Saving_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Submitted):
			return SAVING_DONE_LF_OUT;
			
		case (EnumConst.TxType_Saving_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Undone):
			return SAVING_UNDONE_LF_OUT;
			
		case (EnumConst.TxType_Saving_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Rejected):
			return SAVING_REJECTED_LF_OUT;
			

		case (EnumConst.TxType_Loan_Installment + EnumConst.TxStatus_Submitted):
			return LOAN_PRINCIPLE_DONE;
			
		case (EnumConst.TxType_Loan_Installment + EnumConst.TxStatus_Undone):
			return LOAN_PRINCIPLE_UNDONE;
			
		case (EnumConst.TxType_Loan_Installment + EnumConst.TxStatus_Rejected):
			return LOAN_PRINCIPLE_REJECTED;
			

		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxStatus_Submitted):
			return LOAN_INTEREST_DONE;
			
		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxStatus_Undone):
			return LOAN_INTEREST_UNDONE;
			
		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxStatus_Rejected):
			return LOAN_INTEREST_REJECTED;
			

		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxStatus_Submitted):
			return LOAN_DONE;
			
		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxStatus_Undone):
			return LOAN_UNDONE;
			
		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxStatus_Rejected):
			return LOAN_REJECTED;
		

		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Submitted):
			return LOAN_DONE_LF_PAID;

		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Undone):
			return LOAN_UNDONE_LF_PAID;

		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Rejected):
			return LOAN_REJECTED_LF_PAID;


		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Submitted):
			return LOAN_DONE_LF_OUT;
		
		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Undone):
			return LOAN_UNDONE_LF_OUT;
		
		case (EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Rejected):
			return LOAN_REJECTED_LF_OUT;
		

		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Submitted):
			return LOAN_IC_DONE_LF_PAID;

		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Undone):
			return LOAN_IC_UNDONE_LF_PAID;

		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee + EnumConst.TxStatus_Rejected):
			return LOAN_IC_REJECTED_LF_PAID;


		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Submitted):
			return LOAN_IC_DONE_LF_OUT;
			
		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Undone):
			return LOAN_IC_UNDONE_LF_OUT;
			
		case (EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee + EnumConst.TxStatus_Rejected):
			return LOAN_IC_REJECTED_LF_OUT;
			

		case (EnumConst.TxType_Bank_Transfer + EnumConst.TxStatus_Submitted):
			return BANK_TRANSFER_DONE;

		case (EnumConst.TxType_Bank_Transfer + EnumConst.TxStatus_Undone):
			return BANK_TRANSFER_UNDONE;

		case (EnumConst.TxType_Bank_Transfer + EnumConst.TxStatus_Rejected):
			return BANK_TRANSFER_REJECTED;


		case (EnumConst.TxType_Bank_Withdrawal + EnumConst.TxStatus_Submitted):
			return BANK_WITHDRAWAL_DONE;

		case (EnumConst.TxType_Bank_Withdrawal + EnumConst.TxStatus_Undone):
			return BANK_WITHDRAWAL_UNDONE;

		case (EnumConst.TxType_Bank_Withdrawal + EnumConst.TxStatus_Rejected):
			return BANK_WITHDRAWAL_REJECTED;


		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxStatus_Submitted):
			return LOAN_DISBURSEMENT_DONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxStatus_Undone):
			return LOAN_DISBURSEMENT_UNDONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxStatus_Rejected):
			return LOAN_DISBURSEMENT_REJECTED;


		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxStatus_Submitted):
			return LOAN_DISBURSEMENT_LP_DONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxStatus_Undone):
			return LOAN_DISBURSEMENT_LP_UNDONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxStatus_Rejected):
			return LOAN_DISBURSEMENT_LP_REJECTED;


		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxType_Pre_Interest_Installment + EnumConst.TxStatus_Submitted):
			return LOAN_DISBURSEMENT_LP_PII_DONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxType_Pre_Interest_Installment + EnumConst.TxStatus_Undone):
			return LOAN_DISBURSEMENT_LP_PII_UNDONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxType_Pre_Interest_Installment + EnumConst.TxStatus_Rejected):
			return LOAN_DISBURSEMENT_LP_PII_REJECTED;

		
		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Pre_Interest_Installment + EnumConst.TxStatus_Submitted):
			return LOAN_DISBURSEMENT_PII_DONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Pre_Interest_Installment + EnumConst.TxStatus_Undone):
			return LOAN_DISBURSEMENT_PII_UNDONE;

		case (EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Pre_Interest_Installment + EnumConst.TxStatus_Rejected):
			return LOAN_DISBURSEMENT_PII_REJECTED;


		case (EnumConst.TxType_Registration_Fee + EnumConst.TxStatus_Submitted):
			return REGISTRATION_DONE;
		
		case (EnumConst.TxType_Registration_Fee + EnumConst.TxStatus_Undone):
			return REGISTRATION_UNDONE;
		
		case (EnumConst.TxType_Registration_Fee + EnumConst.TxStatus_Rejected):
			return REGISTRATION_REJECTED;
		

		case (EnumConst.TxType_Registration_Fee + EnumConst.TxType_Saving_Installment + EnumConst.TxStatus_Submitted):
			return REGISTRATION_SI_DONE;

		case (EnumConst.TxType_Registration_Fee + EnumConst.TxType_Saving_Installment + EnumConst.TxStatus_Undone):
			return REGISTRATION_SI_UNDONE;

		case (EnumConst.TxType_Registration_Fee + EnumConst.TxType_Saving_Installment + EnumConst.TxStatus_Rejected):
			return REGISTRATION_SI_REJECTED;


		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxStatus_Submitted):
			return BANK_LOAN_DISB_DONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxStatus_Undone):
			return BANK_LOAN_DISB_UNDONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxStatus_Rejected):
			return BANK_LOAN_DISB_REJECTED;


		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Submitted):
			return BANK_LOAN_DISB_BC_DONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Undone):
			return BANK_LOAN_DISB_BC_UNDONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Rejected):
			return BANK_LOAN_DISB_BC_REJECTED;


		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxType_Bank_Pre_Interest_Installment + EnumConst.TxStatus_Submitted):
			return BANK_LOAN_DISB_BC_PII_DONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxType_Bank_Pre_Interest_Installment + EnumConst.TxStatus_Undone):
			return BANK_LOAN_DISB_BC_PII_UNDONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxType_Bank_Pre_Interest_Installment + EnumConst.TxStatus_Rejected):
			return BANK_LOAN_DISB_BC_PII_REJECTED;


		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Pre_Interest_Installment + EnumConst.TxStatus_Submitted):
			return BANK_LOAN_DISB_PII_DONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Pre_Interest_Installment + EnumConst.TxStatus_Undone):
			return BANK_LOAN_DISB_PII_UNDONE;

		case (EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Pre_Interest_Installment + EnumConst.TxStatus_Rejected):
			return BANK_LOAN_DISB_PII_REJECTED;


		case (EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxStatus_Submitted):
			return FD_INVT_DONE;

		case (EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxStatus_Undone):
			return FD_INVT_UNDONE;

		case (EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxStatus_Rejected):
			return FD_INVT_REJECTED;


		case (EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Submitted):
			return FD_INVT_BC_DONE;

		case (EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Undone):
			return FD_INVT_BC_UNDONE;

		case (EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Rejected):
			return FD_INVT_BC_REJECTED;


		case (EnumConst.TxType_Other_Investment + EnumConst.TxStatus_Submitted):
			return OTHER_INVT_DONE;

		case (EnumConst.TxType_Other_Investment + EnumConst.TxStatus_Undone):
			return OTHER_INVT_UNDONE;

		case (EnumConst.TxType_Other_Investment + EnumConst.TxStatus_Rejected):
			return OTHER_INVT_REJECTED;


		case (EnumConst.TxType_Other_Investment + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Submitted):
			return OTHER_INVT_BC_DONE;

		case (EnumConst.TxType_Other_Investment + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Undone):
			return OTHER_INVT_BC_UNDONE;

		case (EnumConst.TxType_Other_Investment + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxStatus_Rejected):
			return OTHER_INVT_BC_REJECTED;


		}

		return null;
	}
	public static String getMessageForTxs(List<Tx> txs) {
		
		int noOfTxs = txs.size();
		if(noOfTxs < 2) {
			return null;
		}
		
		Set<String> types = new HashSet<String>();
		String status = EnumCache.getNameOfEnumValue(EnumConst.TxStatus, txs.get(0).getTxStatusId());
		
		for(Tx tx: txs) {
			types.add(EnumCache.getNameOfTxType(tx.getTxTypeId()));
		}
		
		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Saving_Installment) && types.contains(EnumConst.TxType_Late_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Saving_Installment + EnumConst.TxType_Late_Fee), status);
		}
		
		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Saving_Installment) && types.contains(EnumConst.TxType_Outstanding_Late_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Saving_Installment + EnumConst.TxType_Outstanding_Late_Fee), status);
		}
		
		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Loan_Installment) && types.contains(EnumConst.TxType_Loan_Interest_Installment)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment), status);
		}
		
		if(noOfTxs == 3 && types.contains(EnumConst.TxType_Loan_Installment) && types.contains(EnumConst.TxType_Loan_Interest_Installment) && types.contains(EnumConst.TxType_Late_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee), status);
		}
		
		if(noOfTxs == 3 && types.contains(EnumConst.TxType_Loan_Installment) && types.contains(EnumConst.TxType_Loan_Interest_Installment) && types.contains(EnumConst.TxType_Outstanding_Late_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Installment + EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee), status);
		}
		
		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Loan_Interest_Installment) && types.contains(EnumConst.TxType_Late_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Late_Fee), status);
		}
		
		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Loan_Interest_Installment) && types.contains(EnumConst.TxType_Outstanding_Late_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Interest_Installment + EnumConst.TxType_Outstanding_Late_Fee), status);
		}
			
		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Loan_Disbursement) && types.contains(EnumConst.TxType_Loan_Processing_Fee)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee), status);
		}

		if(noOfTxs == 3 && types.contains(EnumConst.TxType_Loan_Disbursement) && types.contains(EnumConst.TxType_Loan_Processing_Fee) && types.contains(EnumConst.TxType_Pre_Interest_Installment)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Loan_Processing_Fee + EnumConst.TxType_Pre_Interest_Installment), status);
		}

		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Loan_Disbursement) && types.contains(EnumConst.TxType_Pre_Interest_Installment)) {
			return getMessageForTxType((EnumConst.TxType_Loan_Disbursement + EnumConst.TxType_Pre_Interest_Installment), status);
		}

		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Registration_Fee) && types.contains(EnumConst.TxType_Saving_Installment)) {
			return getMessageForTxType((EnumConst.TxType_Registration_Fee + EnumConst.TxType_Saving_Installment), status);
		}

		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Bank_Loan_Disbursement) && types.contains(EnumConst.TxType_Bank_Charges_Expense)) {
			return getMessageForTxType((EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense), status);
		}

		if(noOfTxs == 3 && types.contains(EnumConst.TxType_Bank_Loan_Disbursement) && types.contains(EnumConst.TxType_Bank_Charges_Expense) && types.contains(EnumConst.TxType_Bank_Pre_Interest_Installment)) {
			return getMessageForTxType((EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Charges_Expense + EnumConst.TxType_Bank_Pre_Interest_Installment), status);
		}

		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Bank_Loan_Disbursement) && types.contains(EnumConst.TxType_Bank_Pre_Interest_Installment)) {
			return getMessageForTxType((EnumConst.TxType_Bank_Loan_Disbursement + EnumConst.TxType_Bank_Pre_Interest_Installment), status);
		}

		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Fix_Deposit_Investment) && types.contains(EnumConst.TxType_Bank_Charges_Expense)) {
			return getMessageForTxType((EnumConst.TxType_Fix_Deposit_Investment + EnumConst.TxType_Bank_Charges_Expense), status);
		}

		if(noOfTxs == 2 && types.contains(EnumConst.TxType_Other_Investment) && types.contains(EnumConst.TxType_Bank_Charges_Expense)) {
			return getMessageForTxType((EnumConst.TxType_Other_Investment + EnumConst.TxType_Bank_Charges_Expense), status);
		}

		return null;
	}
}
