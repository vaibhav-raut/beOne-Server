package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.model.GBankAccount;
import com.beone.shg.net.persistence.model.MBankAccount;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;

@Repository("txMPO")
public class TxMPO extends IMProcessing {

	private static final Logger log = LoggerFactory.getLogger(TxMPO.class);

	@Override
	public void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang) {

		Tx tx = null;

		if(mJob.getRelatedObject().containsKey(Tx.class)) {
			tx = (Tx)mJob.getRelatedObject().get(Tx.class);
		}

		processMessageFormula(tx, mJob, formulaMap, lang);
	}
	
	public static void processMessageFormula(Tx tx, MessageJob mJob, Map<String,String> formulaMap, String lang) {

		if(tx != null) {
			try {

				if(formulaMap.keySet().contains("tx_id")) {
					formulaMap.put("tx_id", DataUtil.toString(tx.getTxId()));
				}

				if(formulaMap.keySet().contains("tx_type_id")) {
					formulaMap.put("tx_type_id", DataUtil.toString(EnumCache.getNameOfTxType(tx.getTxTypeId())));
				}

				if(formulaMap.keySet().contains("tx_status_id")) {
					formulaMap.put("tx_status_id", DataUtil.toString(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId())));
				}

				if(formulaMap.keySet().contains("payment_mode_id")) {
					formulaMap.put("payment_mode_id", DataUtil.toString(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, tx.getPaymentModeId())));
				}

				if(formulaMap.keySet().contains("done_by_m")) {
					formulaMap.put("done_by_m", DataUtil.toString(mJob.getDaoFactory().getMemberContactDAO().getNameOfMember(lang, tx.getDoneByMemberAcNo())));
				}

				if(formulaMap.keySet().contains("approved_by_m")) {
					formulaMap.put("approved_by_m", DataUtil.toString(mJob.getDaoFactory().getMemberContactDAO().getNameOfMember(lang, tx.getApprovedByMemberAcNo())));
				}

				if(formulaMap.keySet().contains("m_loan_ac_no")) {
					formulaMap.put("m_loan_ac_no", DataUtil.toString(tx.getMemberLoanAcNo()));
				}

				if(formulaMap.keySet().contains("reason_to_undo")) {
					formulaMap.put("reason_to_undo", DataUtil.toString(EnumCache.getNameOfEnumValue(EnumConst.ReasonToUndo, tx.getReasonToUndoId())));
				}

				if(formulaMap.keySet().contains("cheque_no")) {
					formulaMap.put("cheque_no", DataUtil.toString(tx.getChequeNo()));
				}

				if(formulaMap.keySet().contains("amount")) {
					formulaMap.put("amount", DataUtil.toString(tx.getAmount()));
				}

				if(formulaMap.keySet().contains("payment_ts")) {
					formulaMap.put("payment_ts", DateUtil.getDisplaySMSDateStr(tx.getPaymentTs()));
				}

				if(formulaMap.keySet().contains("entry_ts")) {
					formulaMap.put("entry_ts", DateUtil.getDisplaySMSDateStr(tx.getEntryTs()));
				}

				if(formulaMap.keySet().contains("approved_ts")) {
					formulaMap.put("approved_ts", DateUtil.getDisplaySMSDateStr(tx.getApprovedTs()));
				}

				if(formulaMap.keySet().contains("member_name_short")) {
					if(tx.getMemberAcNo() > 0) {
						formulaMap.put("member_name_short", mJob.getDaoFactory().getMemberContactDAO().getShortNameOfMember(lang, tx.getMemberAcNo()));
					}
				}

				if(formulaMap.keySet().contains("member_name")) {
					if(tx.getMemberAcNo() > 0) {
						formulaMap.put("member_name", mJob.getDaoFactory().getMemberContactDAO().getNameOfMember(lang, tx.getMemberAcNo()));
					}
				}

				if(formulaMap.keySet().contains("member_name_full")) {
					if(tx.getMemberAcNo() > 0) {
						formulaMap.put("member_name_full", mJob.getDaoFactory().getMemberContactDAO().getFullNameOfMember(lang, tx.getMemberAcNo()));
					}
				}

				if(formulaMap.keySet().contains("group_name")) {
					formulaMap.put("group_name", mJob.getDaoFactory().getGroupContactDAO().getNameOfGroup(lang, tx.getGroupAcNo()));
				}


				if(formulaMap.keySet().contains("bank_name")) {
					if(tx.getBankGroupAcNo() > 0) {
						formulaMap.put("bank_name", mJob.getDaoFactory().getGroupContactDAO().getNameOfGroup(lang, tx.getBankGroupAcNo()));
					}
				}

				if(formulaMap.keySet().contains("group_bank_ac_name")) {
					if(tx.getGroupBankAccount() > 0) {
						GBankAccount gBankAccount = mJob.getDaoFactory().getGBankAccountDAO().findById(tx.getGroupBankAccount());
						formulaMap.put("group_bank_ac_name", ConversionUtil.convertToDisplay(gBankAccount).getDisplayAccount());
					}
				}

				if(formulaMap.keySet().contains("member_bank_ac_name")) {
					if(tx.getMemberBankAccount() > 0) {
						MBankAccount mBankAccount = mJob.getDaoFactory().getMBankAccountDAO().findById(tx.getMemberBankAccount());
						formulaMap.put("member_bank_ac_name", ConversionUtil.convertToDisplay(mBankAccount).getDisplayAccount());
					}
				}

			} catch (Exception e) {
				log.error(e.toString() + "; for Tx: " + tx.getTxId());
			}
		}
	}
}