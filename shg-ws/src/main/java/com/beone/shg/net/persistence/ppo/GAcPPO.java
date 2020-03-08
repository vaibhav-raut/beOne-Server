package com.beone.shg.net.persistence.ppo;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.job.PostProcessJob;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.GRules;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.ppo.util.PPOAlgoUtil;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

@Repository("gAcPPO")
public class GAcPPO extends IProcessing {

	private static final Logger log = LoggerFactory.getLogger(GAcPPO.class);

	@Override
	public void processUpdateFormula(PostProcessJob pJob, Map<String,String> formulaMap) {

		if(pJob.getRelatedObject().containsKey(Tx.class)) {

			Tx tx = (Tx)pJob.getRelatedObject().get(Tx.class);

			try {
				BigDecimal amount = tx.getAmount();
				GAc gAccount = pJob.getDaoFactory().getGAcDAO().findById(tx.getGroupAcNo());
				MProfile mProfile = pJob.getDaoFactory().getMProfileDAO().findById(tx.getMemberAcNo());
				GRules gRules = pJob.getDaoFactory().getGRulesDAO().findById(tx.getGroupAcNo());
				String role = EnumCache.getNameOfMRole(mProfile.getMroleId());
				
				if(mProfile != null) {
					if(EnumUtil.isCoreMember(role)) {

						if(formulaMap.keySet().contains("c_m_planned_monthly_saving")) {
							gAccount.setCMPlannedMonthlySaving(PPOAlgoUtil.compute(gAccount.getCMPlannedMonthlySaving(), formulaMap.get("c_m_planned_monthly_saving"), amount));
						}

						if(formulaMap.keySet().contains("c_m_saved_am")) {
							gAccount.setCMSavedAm(PPOAlgoUtil.compute(gAccount.getCMSavedAm(), formulaMap.get("c_m_saved_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_outstanding_saving_am")) {
							gAccount.setCMOutstandingSavedAm(PPOAlgoUtil.compute(gAccount.getCMOutstandingSavedAm(), formulaMap.get("c_m_outstanding_saving_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_prov_int_en_am")) {
							gAccount.setCMProvIntEnAm(PPOAlgoUtil.compute(gAccount.getCMProvIntEnAm(), formulaMap.get("c_m_prov_int_en_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_returned_saved_am")) {
							gAccount.setCMReturnedSavedAm(PPOAlgoUtil.compute(gAccount.getCMReturnedSavedAm(), formulaMap.get("c_m_returned_saved_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_returned_int_en_am")) {
							gAccount.setCMReturnedIntEnAm(PPOAlgoUtil.compute(gAccount.getCMReturnedIntEnAm(), formulaMap.get("c_m_returned_int_en_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_profit_share_declared_am")) {
							gAccount.setCMProfitShareDeclaredAm(PPOAlgoUtil.compute(gAccount.getCMProfitShareDeclaredAm(), formulaMap.get("c_m_profit_share_declared_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_profit_share_paid_am")) {
							gAccount.setCMProfitSharePaidAm(PPOAlgoUtil.compute(gAccount.getCMProfitSharePaidAm(), formulaMap.get("c_m_profit_share_paid_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_no_of_loans")) {
							gAccount.setCMNoOfLoans(PPOAlgoUtil.compute(gAccount.getCMNoOfLoans(), formulaMap.get("c_m_no_of_loans"), 1));
						}

						if(formulaMap.keySet().contains("c_m_no_of_active_loans")) {
							gAccount.setCMNoOfActiveLoans(PPOAlgoUtil.compute(gAccount.getCMNoOfActiveLoans(), formulaMap.get("c_m_no_of_active_loans"), 1));
						}

						if(formulaMap.keySet().contains("c_m_loan_am")) {
							gAccount.setCMLoanAm(PPOAlgoUtil.compute(gAccount.getCMLoanAm(), formulaMap.get("c_m_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_rec_loan_am")) {
							gAccount.setCMRecLoanAm(PPOAlgoUtil.compute(gAccount.getCMRecLoanAm(), formulaMap.get("c_m_rec_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_rec_int_on_loan_am")) {
							gAccount.setCMRecIntOnLoanAm(PPOAlgoUtil.compute(gAccount.getCMRecIntOnLoanAm(), formulaMap.get("c_m_rec_int_on_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_proj_int_on_loan_am")) {
							gAccount.setCMProjIntOnLoanAm(PPOAlgoUtil.compute(gAccount.getCMProjIntOnLoanAm(), formulaMap.get("c_m_proj_int_on_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_sub_std_dept_am")) {
							gAccount.setCMSubStdDeptAm(PPOAlgoUtil.compute(gAccount.getCMSubStdDeptAm(), formulaMap.get("c_m_sub_std_dept_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_no_of_sub_std_dept")) {
							gAccount.setCMNoOfSubStdDept(PPOAlgoUtil.compute(gAccount.getCMNoOfSubStdDept(), formulaMap.get("c_m_no_of_sub_std_dept"), 1));
						}

						if(formulaMap.keySet().contains("c_m_bad_dept_exp_am")) {
							gAccount.setCMBadDeptExpAm(PPOAlgoUtil.compute(gAccount.getCMBadDeptExpAm(), formulaMap.get("c_m_bad_dept_exp_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_no_of_bad_dept_exp")) {
							gAccount.setCMNoOfBadDeptExp(PPOAlgoUtil.compute(gAccount.getCMNoOfBadDeptExp(), formulaMap.get("c_m_no_of_bad_dept_exp"), 1));
						}

						if(formulaMap.keySet().contains("c_m_bad_dept_closed_am")) {
							gAccount.setCMBadDeptClosedAm(PPOAlgoUtil.compute(gAccount.getCMBadDeptClosedAm(), formulaMap.get("c_m_bad_dept_closed_am"), amount));
						}

						if(formulaMap.keySet().contains("c_m_no_of_bad_dept_closed")) {
							gAccount.setCMNoOfBadDeptClosed(PPOAlgoUtil.compute(gAccount.getCMNoOfBadDeptClosed(), formulaMap.get("c_m_no_of_bad_dept_closed"), 1));
						}

						if(formulaMap.keySet().contains("pen_shg_one_mem_reg_fee")) {
							gAccount.setPenShgOneMemRegFee(PPOAlgoUtil.compute(gAccount.getPenShgOneMemRegFee(), formulaMap.get("pen_shg_one_mem_reg_fee"), new BigDecimal(gRules.getShgOneCMRegCharge())));
						}

						if(formulaMap.keySet().contains("pen_shg_one_service_charges")) {
							gAccount.setPenShgOneServiceCharges(PPOAlgoUtil.compute(gAccount.getPenShgOneServiceCharges(), formulaMap.get("pen_shg_one_service_charges"), 
									(BDUtil.multiply(amount, new BigDecimal(gRules.getShgOneCMLoanProFeePercent() / gRules.getCmLoanProcessingFeePercent())))));
						}

					} else if(EnumUtil.isAssociateMember(role)) {

						if(formulaMap.keySet().contains("a_m_planned_monthly_saving")) {
							gAccount.setAMPlannedMonthlySaving(PPOAlgoUtil.compute(gAccount.getAMPlannedMonthlySaving(), formulaMap.get("a_m_planned_monthly_saving"), amount));
						}

						if(formulaMap.keySet().contains("a_m_saved_am")) {
							gAccount.setAMSavedAm(PPOAlgoUtil.compute(gAccount.getAMSavedAm(), formulaMap.get("a_m_saved_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_outstanding_saving_am")) {
							gAccount.setAMOutstandingSavedAm(PPOAlgoUtil.compute(gAccount.getAMOutstandingSavedAm(), formulaMap.get("a_m_outstanding_saving_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_prov_int_en_am")) {
							gAccount.setAMProvIntEnAm(PPOAlgoUtil.compute(gAccount.getAMProvIntEnAm(), formulaMap.get("a_m_prov_int_en_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_returned_saved_am")) {
							gAccount.setAMReturnedSavedAm(PPOAlgoUtil.compute(gAccount.getAMReturnedSavedAm(), formulaMap.get("a_m_returned_saved_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_returned_int_en_am")) {
							gAccount.setAMReturnedIntEnAm(PPOAlgoUtil.compute(gAccount.getAMReturnedIntEnAm(), formulaMap.get("a_m_returned_int_en_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_divided_declared_am")) {
							gAccount.setAMDividedDeclaredAm(PPOAlgoUtil.compute(gAccount.getAMDividedDeclaredAm(), formulaMap.get("a_m_divided_declared_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_divided_paid_am")) {
							gAccount.setAMDividedPaidAm(PPOAlgoUtil.compute(gAccount.getAMDividedPaidAm(), formulaMap.get("a_m_divided_paid_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_no_of_loans")) {
							gAccount.setAMNoOfLoans(PPOAlgoUtil.compute(gAccount.getAMNoOfLoans(), formulaMap.get("a_m_no_of_loans"), 1));
						}

						if(formulaMap.keySet().contains("a_m_no_of_active_loans")) {
							gAccount.setAMNoOfActiveLoans(PPOAlgoUtil.compute(gAccount.getAMNoOfActiveLoans(), formulaMap.get("a_m_no_of_active_loans"), 1));
						}

						if(formulaMap.keySet().contains("a_m_loan_am")) {
							gAccount.setAMLoanAm(PPOAlgoUtil.compute(gAccount.getAMLoanAm(), formulaMap.get("a_m_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_rec_loan_am")) {
							gAccount.setAMRecLoanAm(PPOAlgoUtil.compute(gAccount.getAMRecLoanAm(), formulaMap.get("a_m_rec_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_rec_int_on_loan_am")) {
							gAccount.setAMRecIntOnLoanAm(PPOAlgoUtil.compute(gAccount.getAMRecIntOnLoanAm(), formulaMap.get("a_m_rec_int_on_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_proj_int_on_loan_am")) {
							gAccount.setAMProjIntOnLoanAm(PPOAlgoUtil.compute(gAccount.getAMProjIntOnLoanAm(), formulaMap.get("a_m_proj_int_on_loan_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_sub_std_dept_am")) {
							gAccount.setAMSubStdDeptAm(PPOAlgoUtil.compute(gAccount.getAMSubStdDeptAm(), formulaMap.get("a_m_sub_std_dept_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_no_of_sub_std_dept")) {
							gAccount.setAMNoOfSubStdDept(PPOAlgoUtil.compute(gAccount.getAMNoOfSubStdDept(), formulaMap.get("a_m_no_of_sub_std_dept"), 1));
						}

						if(formulaMap.keySet().contains("a_m_bad_dept_exp_am")) {
							gAccount.setAMBadDeptExpAm(PPOAlgoUtil.compute(gAccount.getAMBadDeptExpAm(), formulaMap.get("a_m_bad_dept_exp_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_no_of_bad_dept_exp")) {
							gAccount.setAMNoOfBadDeptExp(PPOAlgoUtil.compute(gAccount.getAMNoOfBadDeptExp(), formulaMap.get("a_m_no_of_bad_dept_exp"), 1));
						}

						if(formulaMap.keySet().contains("a_m_bad_dept_closed_am")) {
							gAccount.setAMBadDeptClosedAm(PPOAlgoUtil.compute(gAccount.getAMBadDeptClosedAm(), formulaMap.get("a_m_bad_dept_closed_am"), amount));
						}

						if(formulaMap.keySet().contains("a_m_no_of_bad_dept_closed")) {
							gAccount.setAMNoOfBadDeptClosed(PPOAlgoUtil.compute(gAccount.getAMNoOfBadDeptClosed(), formulaMap.get("a_m_no_of_bad_dept_closed"), 1));
						}

						if(formulaMap.keySet().contains("pen_shg_one_mem_reg_fee")) {
							gAccount.setPenShgOneMemRegFee(PPOAlgoUtil.compute(gAccount.getPenShgOneMemRegFee(), formulaMap.get("pen_shg_one_mem_reg_fee"), new BigDecimal(gRules.getShgOneAMRegCharge())));
						}

						if(formulaMap.keySet().contains("pen_shg_one_service_charges")) {
							gAccount.setPenShgOneServiceCharges(PPOAlgoUtil.compute(gAccount.getPenShgOneServiceCharges(), formulaMap.get("pen_shg_one_service_charges"), 
									(BDUtil.multiply(amount, new BigDecimal(gRules.getShgOneAMLoanProFeePercent() / gRules.getAmLoanProcessingFeePercent())))));
						}
					}
				}

				if(formulaMap.keySet().contains("p_loan_am")) {
					gAccount.setPLoanAm(PPOAlgoUtil.compute(gAccount.getPLoanAm(), formulaMap.get("p_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("p_rec_loan_am")) {
					gAccount.setPRecLoanAm(PPOAlgoUtil.compute(gAccount.getPRecLoanAm(), formulaMap.get("p_rec_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("p_rec_int_on_loan_am")) {
					gAccount.setPRecIntOnLoanAm(PPOAlgoUtil.compute(gAccount.getPRecIntOnLoanAm(), formulaMap.get("p_rec_int_on_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("p_proj_int_on_loan_am")) {
					gAccount.setPProjIntOnLoanAm(PPOAlgoUtil.compute(gAccount.getPProjIntOnLoanAm(), formulaMap.get("p_proj_int_on_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("fix_deposit_inv_am")) {
					gAccount.setFixDepositInvAm(PPOAlgoUtil.compute(gAccount.getFixDepositInvAm(), formulaMap.get("fix_deposit_inv_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_fix_deposit_am")) {
					gAccount.setRecFixDepositAm(PPOAlgoUtil.compute(gAccount.getRecFixDepositAm(), formulaMap.get("rec_fix_deposit_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_int_on_fix_deposit_am")) {
					gAccount.setRecIntOnFixDepositAm(PPOAlgoUtil.compute(gAccount.getRecIntOnFixDepositAm(), formulaMap.get("rec_int_on_fix_deposit_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_int_on_fix_deposit_am")) {
					gAccount.setProjIntOnFixDepositAm(PPOAlgoUtil.compute(gAccount.getProjIntOnFixDepositAm(), formulaMap.get("proj_int_on_fix_deposit_am"), amount));
				}

				if(formulaMap.keySet().contains("other_inv_am")) {
					gAccount.setOtherInvAm(PPOAlgoUtil.compute(gAccount.getOtherInvAm(), formulaMap.get("other_inv_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_other_inv_am")) {
					gAccount.setRecOtherInvAm(PPOAlgoUtil.compute(gAccount.getRecOtherInvAm(), formulaMap.get("rec_other_inv_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_int_on_other_inv_am")) {
					gAccount.setRecIntOnOtherInvAm(PPOAlgoUtil.compute(gAccount.getRecIntOnOtherInvAm(), formulaMap.get("rec_int_on_other_inv_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_int_on_other_inv_am")) {
					gAccount.setProjIntOnOtherInvAm(PPOAlgoUtil.compute(gAccount.getProjIntOnOtherInvAm(), formulaMap.get("proj_int_on_other_inv_am"), amount));
				}

				if(formulaMap.keySet().contains("int_en_on_saving_ac_am")) {
					gAccount.setIntEnOnSavingAcAm(PPOAlgoUtil.compute(gAccount.getIntEnOnSavingAcAm(), formulaMap.get("int_en_on_saving_ac_am"), amount));
				}

				if(formulaMap.keySet().contains("net_profit_am")) {
					gAccount.setNetProfitAm(PPOAlgoUtil.compute(gAccount.getNetProfitAm(), formulaMap.get("net_profit_am"), amount));
				}

				if(formulaMap.keySet().contains("net_profit_proj_am")) {
					gAccount.setNetProfitProjAm(PPOAlgoUtil.compute(gAccount.getNetProfitProjAm(), formulaMap.get("net_profit_proj_am"), amount));
				}

				if(formulaMap.keySet().contains("expenses_am")) {
					gAccount.setExpensesAm(PPOAlgoUtil.compute(gAccount.getExpensesAm(), formulaMap.get("expenses_am"), amount));
				}

				if(formulaMap.keySet().contains("outstanding_expenses_am")) {
					gAccount.setOutstandingExpensesAm(PPOAlgoUtil.compute(gAccount.getOutstandingExpensesAm(), formulaMap.get("outstanding_expenses_am"), amount));
				}

				if(formulaMap.keySet().contains("rec_penalty_am")) {
					gAccount.setRecPenaltyAm(PPOAlgoUtil.compute(gAccount.getRecPenaltyAm(), formulaMap.get("rec_penalty_am"), amount));
				}

				if(formulaMap.keySet().contains("pending_penalty_am")) {
					gAccount.setPendingPenaltyAm(PPOAlgoUtil.compute(gAccount.getPendingPenaltyAm(), formulaMap.get("pending_penalty_am"), amount));
				}

				if(formulaMap.keySet().contains("borrowed_loan_am")) {
					gAccount.setBorrowedLoanAm(PPOAlgoUtil.compute(gAccount.getBorrowedLoanAm(), formulaMap.get("borrowed_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("paid_borrowed_loan_am")) {
					gAccount.setPaidBorrowedLoanAm(PPOAlgoUtil.compute(gAccount.getPaidBorrowedLoanAm(), formulaMap.get("paid_borrowed_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("paid_int_on_borrowed_loan_am")) {
					gAccount.setPaidIntOnBorrowedLoanAm(PPOAlgoUtil.compute(gAccount.getPaidIntOnBorrowedLoanAm(), formulaMap.get("paid_int_on_borrowed_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("proj_int_on_borrowed_loan_am")) {
					gAccount.setProjIntOnBorrowedLoanAm(PPOAlgoUtil.compute(gAccount.getProjIntOnBorrowedLoanAm(), formulaMap.get("proj_int_on_borrowed_loan_am"), amount));
				}

				if(formulaMap.keySet().contains("bank_sub_std_dept_am")) {
					gAccount.setBankSubStdDeptAm(PPOAlgoUtil.compute(gAccount.getBankSubStdDeptAm(), formulaMap.get("bank_sub_std_dept_am"), amount));
				}

				if(formulaMap.keySet().contains("bank_no_of_sub_std_dept")) {
					gAccount.setBankNoOfSubStdDept(PPOAlgoUtil.compute(gAccount.getBankNoOfSubStdDept(), formulaMap.get("bank_no_of_sub_std_dept"), 1));
				}

				if(formulaMap.keySet().contains("bank_bad_dept_exp_am")) {
					gAccount.setBankBadDeptExpAm(PPOAlgoUtil.compute(gAccount.getBankBadDeptExpAm(), formulaMap.get("bank_bad_dept_exp_am"), amount));
				}

				if(formulaMap.keySet().contains("bank_no_of_bad_dept_exp")) {
					gAccount.setBankNoOfBadDeptExp(PPOAlgoUtil.compute(gAccount.getBankNoOfBadDeptExp(), formulaMap.get("bank_no_of_bad_dept_exp"), 1));
				}

				if(formulaMap.keySet().contains("bank_bad_dept_closed_am")) {
					gAccount.setBankBadDeptClosedAm(PPOAlgoUtil.compute(gAccount.getBankBadDeptClosedAm(), formulaMap.get("bank_bad_dept_closed_am"), amount));
				}

				if(formulaMap.keySet().contains("bank_no_of_bad_dept_closed")) {
					gAccount.setBankNoOfBadDeptClosed(PPOAlgoUtil.compute(gAccount.getBankNoOfBadDeptClosed(), formulaMap.get("bank_no_of_bad_dept_closed"), 1));
				}

				if(formulaMap.keySet().contains("gov_revolving_fund_am")) {
					gAccount.setGovRevolvingFundAm(PPOAlgoUtil.compute(gAccount.getGovRevolvingFundAm(), formulaMap.get("gov_revolving_fund_am"), amount));
				}

				if(formulaMap.keySet().contains("gov_revolving_fund_returned_am")) {
					gAccount.setGovRevolvingFundReturnedAm(PPOAlgoUtil.compute(gAccount.getGovRevolvingFundReturnedAm(), formulaMap.get("gov_revolving_fund_returned_am"), amount));
				}

				if(formulaMap.keySet().contains("no_of_gov_revolving_fund")) {
					gAccount.setNoOfGovRevolvingFund(PPOAlgoUtil.compute(gAccount.getNoOfGovRevolvingFund(), formulaMap.get("no_of_gov_revolving_fund"), 1));
				}

				if(formulaMap.keySet().contains("no_of_active_gov_revolving_fund")) {
					gAccount.setNoOfActiveGovRevolvingFund(PPOAlgoUtil.compute(gAccount.getNoOfActiveGovRevolvingFund(), formulaMap.get("no_of_active_gov_revolving_fund"), 1));
				}

				if(formulaMap.keySet().contains("gov_development_fund_am")) {
					gAccount.setGovDevelopmentFundAm(PPOAlgoUtil.compute(gAccount.getGovDevelopmentFundAm(), formulaMap.get("gov_development_fund_am"), amount));
				}

				if(formulaMap.keySet().contains("no_of_gov_development_fund")) {
					gAccount.setNoOfGovDevelopmentFund(PPOAlgoUtil.compute(gAccount.getNoOfGovDevelopmentFund(), formulaMap.get("no_of_gov_development_fund"), 1));
				}

				if(formulaMap.keySet().contains("last_updated_ts")) {
					gAccount.setLastUpdatedTs(PPOAlgoUtil.compute(gAccount.getLastUpdatedTs(), formulaMap.get("last_updated_ts"), null));
				}

				if(formulaMap.keySet().contains("subj_clearing_cash_in_hand_am")) {
					gAccount.setSubjClearingCashInHandAm(PPOAlgoUtil.compute(gAccount.getSubjClearingCashInHandAm(), formulaMap.get("subj_clearing_cash_in_hand_am"), amount));
				}

				if(formulaMap.keySet().contains("subj_clearing_bank_balance_am")) {
					gAccount.setSubjClearingBankBalanceAm(PPOAlgoUtil.compute(gAccount.getSubjClearingBankBalanceAm(), formulaMap.get("subj_clearing_bank_balance_am"), amount));
				}

				if(formulaMap.keySet().contains("clear_cash_in_hand_am")) {
					gAccount.setClearCashInHandAm(PPOAlgoUtil.compute(gAccount.getClearCashInHandAm(), formulaMap.get("clear_cash_in_hand_am"), amount));
				}

				if(formulaMap.keySet().contains("clear_bank_balance_am")) {
					gAccount.setClearBankBalanceAm(PPOAlgoUtil.compute(gAccount.getClearBankBalanceAm(), formulaMap.get("clear_bank_balance_am"), amount));
				}

				if(formulaMap.keySet().contains("no_of_txs_monthly_exp")) {
					gAccount.setNoOfTxsMonthlyExp(PPOAlgoUtil.compute(gAccount.getNoOfTxsMonthlyExp(), formulaMap.get("no_of_txs_monthly_exp"), 1));
				}

				if(formulaMap.keySet().contains("no_of_txs_monthly_done")) {
					gAccount.setNoOfTxsMonthlyDone(PPOAlgoUtil.compute(gAccount.getNoOfTxsMonthlyDone(), formulaMap.get("no_of_txs_monthly_done"), 1));
				}

				if(formulaMap.keySet().contains("no_of_txs_monthly_approved")) {
					gAccount.setNoOfTxsMonthlyApproved(PPOAlgoUtil.compute(gAccount.getNoOfTxsMonthlyApproved(), formulaMap.get("no_of_txs_monthly_approved"), 1));
				}

				
				// Balance Update
				if(formulaMap.keySet().contains("balance_update")) {

					// Update Balance in Transaction for Cash Payment
					if(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, tx.getPaymentModeId()).equals(EnumConst.PaymentMode_CASH)) {
						gAccount.setSubjClearingCashInHandAm(PPOAlgoUtil.compute(gAccount.getSubjClearingCashInHandAm(), formulaMap.get("balance_update"), amount));
					} 
					// Update Balance in Transaction for Bank Payment
					else {
						gAccount.setSubjClearingBankBalanceAm(PPOAlgoUtil.compute(gAccount.getSubjClearingBankBalanceAm(), formulaMap.get("balance_update"), amount));
					}
				}			

				// Balance Approve
				if(formulaMap.keySet().contains("balance_approve")) {

					// Update Balance in Transaction for Cash Payment
					if(EnumCache.getNameOfEnumValue(EnumConst.PaymentMode, tx.getPaymentModeId()).equals(EnumConst.PaymentMode_CASH)) {
						gAccount.setClearCashInHandAm(PPOAlgoUtil.compute(gAccount.getClearCashInHandAm(), formulaMap.get("balance_approve"), amount));

						gAccount.setSubjClearingCashInHandAm(PPOAlgoUtil.compute(gAccount.getSubjClearingCashInHandAm(), PPOAlgoUtil.inverseAlgo(formulaMap.get("balance_approve")), amount));
					} 
					// Update Balance in Transaction for Bank Payment
					else {
						gAccount.setClearBankBalanceAm(PPOAlgoUtil.compute(gAccount.getClearBankBalanceAm(), formulaMap.get("balance_approve"), amount));

						gAccount.setSubjClearingBankBalanceAm(PPOAlgoUtil.compute(gAccount.getSubjClearingBankBalanceAm(), PPOAlgoUtil.inverseAlgo(formulaMap.get("balance_approve")), amount));
					}
				}			

				pJob.getDaoFactory().getGAcDAO().merge(gAccount);

			} catch (Exception e) {
				log.error(e.toString() + "; for GroupAcNo: " + tx.getGroupAcNo() + "; for Tx Id: " + tx.getTxId() + "; for TxType: " + EnumCache.getNameOfTxType(tx.getTxTypeId()));
			}
		}
	}

}