package com.beone.shg.net.persistence.spo;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.MSavingAc;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.spo.model.GroupAcInfo;
import com.beone.shg.net.persistence.spo.model.MemberAcInfo;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.support.TxTypeFormula;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;
import com.beone.shg.net.persistence.util.GenAlgoUtil;
import com.beone.shg.net.webservice.rest.model.resp.TxTypeValue;

public class MSIComputPO extends MemberPO {
	private final static Logger LOGGER = LoggerFactory.getLogger(MSIComputPO.class);

	public MSIComputPO(GroupAcInfo groupAcInfo) {
		super(groupAcInfo);		
	}

	@Override
	public void executeMemberPO(MemberAcInfo memberAcInfo) {

		try {

			for(MSavingAc ac: memberAcInfo.getSavingAcs()) {
				
				// Fix Previous Incomplete Data
				fixIncompleteData(memberAcInfo, ac);
				
				// Check if it is Saving Account 
				if(ac.getCumulativeSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {

					// Calculate Old Interest On Saving if Not Done before
					if(calOldNotCalculatedInt(memberAcInfo, ac)) {
						// Skip Interest Computation If done for Entire Saving
						return;
					}
					
					BigDecimal savingAmountConsidered = ac.getCumulativeSavedAm();
					int dayOfPayment = 0;
					int daysInCurMonth = DateUtil.getDaysInCurrentMonth();
					boolean curInstallmentPaid = false;

					if(memberAcInfo.getAc().getOutstandingSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= DataUtil.ZERO_INTEGER) {
						// Transaction Check
						for(Tx tx: memberAcInfo.getTransactions()) {

							TxTypeValue txType = EnumCache.getTxTypeValue(tx.getTxTypeId());
							if(txType.getTxType().equals(EnumConst.TxType_Saving_Installment) &&
									EnumUtil.isTxStatusApproved(EnumCache.getNameOfEnumValue(EnumConst.TxStatus, tx.getTxStatusId()))) {

								// Condition Check Transaction Payment Date 
								dayOfPayment = DateUtil.getDayOfMonth(tx.getPaymentTs());
								if(dayOfPayment <= groupAcInfo.getGRules().getDueDayOfMonth()) {

									// Consider Current Month's Saving Installment only for days after payment done
									savingAmountConsidered = BDUtil.sub(savingAmountConsidered, BDUtil.multiply(tx.getAmount(), (((float)dayOfPayment)/(float)daysInCurMonth)));
								} 
								else {
									// Subtract Entire Current Month's Saving Installment when Late Payed
									savingAmountConsidered = BDUtil.sub(savingAmountConsidered, tx.getAmount());
								}
								curInstallmentPaid = true;
							}	
						}
					}
				
					if(curInstallmentPaid && savingAmountConsidered.compareTo(DataUtil.ZERO_BIG_DECIMAL) >= DataUtil.ZERO_INTEGER) {

						BigDecimal interestEarned = DataUtil.ZERO_BIG_DECIMAL;
						float monthlyInt = 0.0f;
						String mRole = EnumCache.getNameOfMRole(memberAcInfo.getMprofile().getMroleId());

						// Get Interest Based on Member Role
						if(EnumUtil.isAssociateMember(mRole)) {
							monthlyInt = GenAlgoUtil.getMonthlyIntFactor(groupAcInfo.getGRules().getAmIntOnSaving());
						} else if(EnumUtil.isCoreMember(mRole)) {					
							monthlyInt = GenAlgoUtil.getMonthlyIntFactor(groupAcInfo.getGRules().getCmIntOnSaving());
						}

						// Compute Interest for This Month
						interestEarned = GenAlgoUtil.roundHalfUp(GenAlgoUtil.computeInterestOnSaving(savingAmountConsidered, monthlyInt), 2);

						if(interestEarned.compareTo(DataUtil.ZERO_BIG_DECIMAL) >= DataUtil.ZERO_INTEGER) {
							addTransaction(memberAcInfo, ac, interestEarned, null);
						}
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.toString() + "; for MemberAcNo:" + memberAcInfo.getMprofile().getMemberAcNo());
		}
	}

	protected void fixIncompleteData(MemberAcInfo memberAcInfo, MSavingAc ac) {

		if((ac.getCumulativeSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0 || ac.getNoOfInstPaid() <= 0) && 
				ac.getSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) > 0) {
			
			MSavingAc mSavingAc = memberAcInfo.getDaoFactory().getMSavingAcDAO().findById(ac.getMSavingAcNo());
			
			if(ac.getCumulativeSavedAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) == 0) {
				ac.setCumulativeSavedAm(ac.getSavedAm());
				mSavingAc.setCumulativeSavedAm(ac.getSavedAm());
			}
			if(ac.getNoOfInstPaid() <= 0) {
				int paidInst = BDUtil.divide(ac.getSavedAm(), ac.getSavingInstallmentAm()).intValue();
				ac.setNoOfInstPaid(paidInst);
				mSavingAc.setNoOfInstPaid(paidInst);
			}

			memberAcInfo.getDaoFactory().getMSavingAcDAO().merge(mSavingAc);
		}
	}
	
	protected boolean calOldNotCalculatedInt(MemberAcInfo memberAcInfo, MSavingAc mSavingAc) {

		int noOfInst = mSavingAc.getNoOfInstPaid() - mSavingAc.getNoOfInsallMissed();
		
		if(mSavingAc.getTotalIntEnAm().compareTo(DataUtil.ZERO_BIG_DECIMAL) <= DataUtil.ZERO_INTEGER && noOfInst > 2) {
			
			float monthlyInt = 0.0f;
			String mRole = EnumCache.getNameOfMRole(memberAcInfo.getMprofile().getMroleId());

			// Get Interest Based on Member Role
			if(EnumUtil.isAssociateMember(mRole)) {
				monthlyInt = GenAlgoUtil.getMonthlyIntFactor(groupAcInfo.getGRules().getAmIntOnSaving());
			} else if(EnumUtil.isCoreMember(mRole)) {					
				monthlyInt = GenAlgoUtil.getMonthlyIntFactor(groupAcInfo.getGRules().getCmIntOnSaving());
			}
						
			BigDecimal oldInterestEarned = GenAlgoUtil.roundHalfUp(GenAlgoUtil.compOldIntOnSaving(mSavingAc.getSavingInstallmentAm(), noOfInst, monthlyInt), 2);
			
			addTransaction(memberAcInfo, mSavingAc, oldInterestEarned, "Adding Old Interest on Saving if Not Calculated!");

			return true;
		}
		return false;
	}
	
	protected void addTransaction(MemberAcInfo memberAcInfo, MSavingAc mSavingAc, BigDecimal interestEarned, String description) {

		// Build transaction Object & Save		
		Tx transaction = new Tx();

		int txTypeId = EnumCache.getIndexOfTxType(EnumConst.TxType_Provisional_Interest_Earned);
		transaction.setTxTypeId(txTypeId);
		transaction.setTxStatusId(EnumCache.getIndexOfEnumValue(EnumConst.TxStatus, EnumConst.TxStatus_Auto_Approved));
		transaction.setReceiptVoucherNo("");
		transaction.setChequeNo("");

		transaction.setMemberAcNo(memberAcInfo.getAc().getMAcNo());
		transaction.setGroupAcNo(this.groupAcInfo.getGroupAcNo()); 
		transaction.setDoneByMemberAcNo(EnumConst.SystemAcNo);
		transaction.setApprovedByMemberAcNo(EnumConst.SystemAcNo);
		transaction.setMemberSavingAcNo(mSavingAc.getMSavingAcNo());
		transaction.setAmount(interestEarned);
		transaction.setPaymentModeId(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_INTERNAL));
		transaction.setEntryTs(this.groupAcInfo.getComputationDate());
		transaction.setPaymentTs(this.groupAcInfo.getComputationDate());
		transaction.setApprovedTs(this.groupAcInfo.getComputationDate());

		if(description == null || description.isEmpty()) {
			transaction.setDescription(DateUtil.getMonthName(groupAcInfo.getStartTime()) + " " + DateUtil.getYear(groupAcInfo.getEndTime()) + 
					" - " + EnumConst.TxType_Provisional_Interest_Earned);
		} else {
			transaction.setDescription(description);
		}
		transaction.setEntryLocation("");

		// Save Transaction to DB
		memberAcInfo.getDaoFactory().getTxDAO().persist(transaction);

		TxTypeFormula formula = EnumCache.getTxTypeFormula(transaction.getTxTypeId());
		if(formula.getFormulaOnDone() != null) {		
			memberAcInfo.getProcessJobBuilder().pushPostProcessJob(formula.getFormulaOnDone(), transaction);
		}
	}
}