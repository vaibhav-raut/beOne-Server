package com.beone.shg.net.persistence.mpo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.beone.shg.net.persistence.job.MessageJob;
import com.beone.shg.net.persistence.model.GAc;
import com.beone.shg.net.persistence.model.MAc;
import com.beone.shg.net.persistence.model.MProfile;
import com.beone.shg.net.persistence.model.Tx;
import com.beone.shg.net.persistence.support.EnumCache;
import com.beone.shg.net.persistence.util.BDUtil;
import com.beone.shg.net.persistence.util.ConversionUtil;
import com.beone.shg.net.persistence.util.DataUtil;
import com.beone.shg.net.persistence.util.DateUtil;
import com.beone.shg.net.persistence.util.EnumUtil;

@Repository("gAcMPO")
public class GAcMPO extends IMProcessing {

	private static final Logger log = LoggerFactory.getLogger(GAcMPO.class);

	@Override
	public void processMessageFormula(MessageJob mJob, Map<String,String> formulaMap, String lang) {

		GAc gAccount = null;
		MProfile mProfile = null;
		String role = null;

		if(mJob.getRelatedObject().containsKey(Tx.class)) {
			Tx tx = (Tx)mJob.getRelatedObject().get(Tx.class);
			gAccount = mJob.getDaoFactory().getGAcDAO().findById(tx.getGroupAcNo());
			mProfile = mJob.getDaoFactory().getMProfileDAO().findById(tx.getMemberAcNo());
			role = EnumCache.getNameOfMRole(mProfile.getMroleId());
		}
		if(mJob.getRelatedObject().containsKey(MAc.class)) {
			MAc mAc = (MAc)mJob.getRelatedObject().get(MAc.class);
			gAccount = mJob.getDaoFactory().getGAcDAO().findById(ConversionUtil.getGroupAcFromMember(mAc.getMAcNo()));
			mProfile = mJob.getDaoFactory().getMProfileDAO().findById(mAc.getMAcNo());
			role = EnumCache.getNameOfMRole(mProfile.getMroleId());
		}
		if(mJob.getRelatedObject().containsKey(MProfile.class)) {
			mProfile = (MProfile)mJob.getRelatedObject().get(MProfile.class);
			gAccount = mJob.getDaoFactory().getGAcDAO().findById(ConversionUtil.getGroupAcFromMember(mProfile.getMemberAcNo()));
			role = EnumCache.getNameOfMRole(mProfile.getMroleId());
		}

		if(gAccount != null) {

			try {
				
				if(mProfile != null) {
					if(EnumUtil.isCoreMember(role)) {

						if(formulaMap.keySet().contains("c_m_planned_monthly_saving")) {
							formulaMap.put("c_m_planned_monthly_saving", DataUtil.toString(gAccount.getCMPlannedMonthlySaving()));
						}

						if(formulaMap.keySet().contains("c_m_saved_am")) {
							formulaMap.put("c_m_saved_am", DataUtil.toString(gAccount.getCMSavedAm()));
						}

						if(formulaMap.keySet().contains("c_m_outstanding_saving_am")) {
							formulaMap.put("c_m_outstanding_saving_am", DataUtil.toString(gAccount.getCMOutstandingSavedAm()));
						}

						if(formulaMap.keySet().contains("c_m_prov_int_en_am")) {
							formulaMap.put("c_m_prov_int_en_am", DataUtil.toString(gAccount.getCMProvIntEnAm()));
						}

						if(formulaMap.keySet().contains("c_m_returned_saved_am")) {
							formulaMap.put("c_m_returned_saved_am", DataUtil.toString(gAccount.getCMReturnedSavedAm()));
						}

						if(formulaMap.keySet().contains("c_m_returned_int_en_am")) {
							formulaMap.put("c_m_returned_int_en_am", DataUtil.toString(gAccount.getCMReturnedIntEnAm()));
						}

						if(formulaMap.keySet().contains("c_m_profit_share_declared_am")) {
							formulaMap.put("c_m_profit_share_declared_am", DataUtil.toString(gAccount.getCMProfitShareDeclaredAm()));
						}

						if(formulaMap.keySet().contains("c_m_profit_share_paid_am")) {
							formulaMap.put("c_m_profit_share_paid_am", DataUtil.toString(gAccount.getCMProfitSharePaidAm()));
						}

						if(formulaMap.keySet().contains("c_m_no_of_loans")) {
							formulaMap.put("c_m_no_of_loans", DataUtil.toString(gAccount.getCMNoOfLoans()));
						}

						if(formulaMap.keySet().contains("c_m_no_of_active_loans")) {
							formulaMap.put("c_m_no_of_active_loans", DataUtil.toString(gAccount.getCMNoOfActiveLoans()));
						}

						if(formulaMap.keySet().contains("c_m_loan_am")) {
							formulaMap.put("c_m_loan_am", DataUtil.toString(gAccount.getCMLoanAm()));
						}

						if(formulaMap.keySet().contains("c_m_rec_loan_am")) {
							formulaMap.put("c_m_rec_loan_am", DataUtil.toString(gAccount.getCMRecLoanAm()));
						}

						if(formulaMap.keySet().contains("c_m_rec_int_on_loan_am")) {
							formulaMap.put("c_m_rec_int_on_loan_am", DataUtil.toString(gAccount.getCMRecIntOnLoanAm()));
						}

						if(formulaMap.keySet().contains("c_m_proj_int_on_loan_am")) {
							formulaMap.put("c_m_proj_int_on_loan_am", DataUtil.toString(gAccount.getCMProjIntOnLoanAm()));
						}

						if(formulaMap.keySet().contains("c_m_sub_std_dept_am")) {
							formulaMap.put("c_m_sub_std_dept_am", DataUtil.toString(gAccount.getCMSubStdDeptAm()));
						}

						if(formulaMap.keySet().contains("c_m_no_of_sub_std_dept")) {
							formulaMap.put("c_m_no_of_sub_std_dept", DataUtil.toString(gAccount.getCMNoOfSubStdDept()));
						}

						if(formulaMap.keySet().contains("c_m_bad_dept_exp_am")) {
							formulaMap.put("c_m_bad_dept_exp_am", DataUtil.toString(gAccount.getCMBadDeptExpAm()));
						}

						if(formulaMap.keySet().contains("c_m_no_of_bad_dept_exp")) {
							formulaMap.put("c_m_no_of_bad_dept_exp", DataUtil.toString(gAccount.getCMNoOfBadDeptExp()));
						}

						if(formulaMap.keySet().contains("c_m_bad_dept_closed_am")) {
							formulaMap.put("c_m_bad_dept_closed_am", DataUtil.toString(gAccount.getCMBadDeptClosedAm()));
						}

						if(formulaMap.keySet().contains("c_m_no_of_bad_dept_closed")) {
							formulaMap.put("c_m_no_of_bad_dept_closed", DataUtil.toString(gAccount.getCMNoOfBadDeptClosed()));
						}

					} else if(EnumUtil.isAssociateMember(role)) {

						if(formulaMap.keySet().contains("a_m_planned_monthly_saving")) {
							formulaMap.put("a_m_planned_monthly_saving", DataUtil.toString(gAccount.getAMPlannedMonthlySaving()));
						}

						if(formulaMap.keySet().contains("a_m_saved_am")) {
							formulaMap.put("a_m_saved_am", DataUtil.toString(gAccount.getAMSavedAm()));
						}

						if(formulaMap.keySet().contains("a_m_outstanding_saving_am")) {
							formulaMap.put("a_m_outstanding_saving_am", DataUtil.toString(gAccount.getAMOutstandingSavedAm()));
						}

						if(formulaMap.keySet().contains("a_m_prov_int_en_am")) {
							formulaMap.put("a_m_prov_int_en_am", DataUtil.toString(gAccount.getAMProvIntEnAm()));
						}

						if(formulaMap.keySet().contains("a_m_returned_saved_am")) {
							formulaMap.put("a_m_returned_saved_am", DataUtil.toString(gAccount.getAMReturnedSavedAm()));
						}

						if(formulaMap.keySet().contains("a_m_returned_int_en_am")) {
							formulaMap.put("a_m_returned_int_en_am", DataUtil.toString(gAccount.getAMReturnedIntEnAm()));
						}

						if(formulaMap.keySet().contains("a_m_divided_declared_am")) {
							formulaMap.put("a_m_divided_declared_am", DataUtil.toString(gAccount.getAMDividedDeclaredAm()));
						}

						if(formulaMap.keySet().contains("a_m_divided_paid_am")) {
							formulaMap.put("a_m_divided_paid_am", DataUtil.toString(gAccount.getAMDividedPaidAm()));
						}

						if(formulaMap.keySet().contains("a_m_no_of_loans")) {
							formulaMap.put("a_m_no_of_loans", DataUtil.toString(gAccount.getAMNoOfLoans()));
						}

						if(formulaMap.keySet().contains("a_m_no_of_active_loans")) {
							formulaMap.put("a_m_no_of_active_loans", DataUtil.toString(gAccount.getAMNoOfActiveLoans()));
						}

						if(formulaMap.keySet().contains("a_m_loan_am")) {
							formulaMap.put("a_m_loan_am", DataUtil.toString(gAccount.getAMLoanAm()));
						}

						if(formulaMap.keySet().contains("a_m_rec_loan_am")) {
							formulaMap.put("a_m_rec_loan_am", DataUtil.toString(gAccount.getAMRecLoanAm()));
						}

						if(formulaMap.keySet().contains("a_m_rec_int_on_loan_am")) {
							formulaMap.put("a_m_rec_int_on_loan_am", DataUtil.toString(gAccount.getAMRecIntOnLoanAm()));
						}

						if(formulaMap.keySet().contains("a_m_proj_int_on_loan_am")) {
							formulaMap.put("a_m_proj_int_on_loan_am", DataUtil.toString(gAccount.getAMProjIntOnLoanAm()));
						}

						if(formulaMap.keySet().contains("a_m_sub_std_dept_am")) {
							formulaMap.put("a_m_sub_std_dept_am", DataUtil.toString(gAccount.getAMSubStdDeptAm()));
						}

						if(formulaMap.keySet().contains("a_m_no_of_sub_std_dept")) {
							formulaMap.put("a_m_no_of_sub_std_dept", DataUtil.toString(gAccount.getAMNoOfSubStdDept()));
						}

						if(formulaMap.keySet().contains("a_m_bad_dept_exp_am")) {
							formulaMap.put("a_m_bad_dept_exp_am", DataUtil.toString(gAccount.getAMBadDeptExpAm()));
						}

						if(formulaMap.keySet().contains("a_m_no_of_bad_dept_exp")) {
							formulaMap.put("a_m_no_of_bad_dept_exp", DataUtil.toString(gAccount.getAMNoOfBadDeptExp()));
						}

						if(formulaMap.keySet().contains("a_m_bad_dept_closed_am")) {
							formulaMap.put("a_m_bad_dept_closed_am", DataUtil.toString(gAccount.getAMBadDeptClosedAm()));
						}

						if(formulaMap.keySet().contains("a_m_no_of_bad_dept_closed")) {
							formulaMap.put("a_m_no_of_bad_dept_closed", DataUtil.toString(gAccount.getAMNoOfBadDeptClosed()));
						}
					}
				}

				if(formulaMap.keySet().contains("m_planned_monthly_saving")) {
					formulaMap.put("m_planned_monthly_saving", DataUtil.toString(BDUtil.add(gAccount.getCMPlannedMonthlySaving(), gAccount.getAMPlannedMonthlySaving())));
				}

				if(formulaMap.keySet().contains("m_saved_am")) {
					formulaMap.put("m_saved_am",DataUtil.toString(BDUtil.add(gAccount.getCMSavedAm(), gAccount.getAMSavedAm())));
				}

				if(formulaMap.keySet().contains("m_outstanding_saving_am")) {
					formulaMap.put("m_outstanding_saving_am",DataUtil.toString(BDUtil.add(gAccount.getCMOutstandingSavedAm(), gAccount.getAMOutstandingSavedAm())));
				}

				if(formulaMap.keySet().contains("m_prov_int_en_am")) {
					formulaMap.put("m_prov_int_en_am",DataUtil.toString(BDUtil.add(gAccount.getCMProvIntEnAm(), gAccount.getAMProvIntEnAm())));
				}

				if(formulaMap.keySet().contains("m_returned_saved_am")) {
					formulaMap.put("m_returned_saved_am",DataUtil.toString(BDUtil.add(gAccount.getCMReturnedSavedAm(), gAccount.getAMReturnedSavedAm())));
				}

				if(formulaMap.keySet().contains("m_returned_int_en_am")) {
					formulaMap.put("m_returned_int_en_am",DataUtil.toString(BDUtil.add(gAccount.getCMReturnedIntEnAm(), gAccount.getAMReturnedIntEnAm())));
				}

				if(formulaMap.keySet().contains("m_profit_share_declared_am")) {
					formulaMap.put("m_profit_share_declared_am",DataUtil.toString(BDUtil.add(gAccount.getCMProfitShareDeclaredAm(), gAccount.getAMDividedDeclaredAm())));
				}

				if(formulaMap.keySet().contains("m_profit_share_paid_am")) {
					formulaMap.put("m_profit_share_paid_am",DataUtil.toString(BDUtil.add(gAccount.getCMProfitSharePaidAm(), gAccount.getAMDividedPaidAm())));
				}

				if(formulaMap.keySet().contains("m_no_of_loans")) {
					formulaMap.put("m_no_of_loans",DataUtil.toString(gAccount.getCMNoOfLoans() + gAccount.getAMNoOfLoans()));
				}

				if(formulaMap.keySet().contains("m_no_of_active_loans")) {
					formulaMap.put("m_no_of_active_loans",DataUtil.toString(gAccount.getCMNoOfActiveLoans() + gAccount.getAMNoOfActiveLoans()));
				}

				if(formulaMap.keySet().contains("m_loan_am")) {
					formulaMap.put("m_loan_am",DataUtil.toString(BDUtil.add(gAccount.getCMLoanAm(), gAccount.getAMLoanAm())));
				}

				if(formulaMap.keySet().contains("m_rec_loan_am")) {
					formulaMap.put("m_rec_loan_am",DataUtil.toString(BDUtil.add(gAccount.getCMRecLoanAm(), gAccount.getAMRecLoanAm())));
				}

				if(formulaMap.keySet().contains("m_rec_int_on_loan_am")) {
					formulaMap.put("m_rec_int_on_loan_am",DataUtil.toString(BDUtil.add(gAccount.getCMRecIntOnLoanAm(), gAccount.getAMRecIntOnLoanAm())));
				}

				if(formulaMap.keySet().contains("m_proj_int_on_loan_am")) {
					formulaMap.put("m_proj_int_on_loan_am",DataUtil.toString(BDUtil.add(gAccount.getCMProjIntOnLoanAm(), gAccount.getAMProjIntOnLoanAm())));
				}

				if(formulaMap.keySet().contains("m_sub_std_dept_am")) {
					formulaMap.put("m_sub_std_dept_am",DataUtil.toString(BDUtil.add(gAccount.getCMSubStdDeptAm(), gAccount.getAMSubStdDeptAm())));
				}

				if(formulaMap.keySet().contains("m_no_of_sub_std_dept")) {
					formulaMap.put("m_no_of_sub_std_dept",DataUtil.toString(gAccount.getCMNoOfSubStdDept() + gAccount.getAMNoOfSubStdDept()));
				}

				if(formulaMap.keySet().contains("m_bad_dept_exp_am")) {
					formulaMap.put("m_bad_dept_exp_am",DataUtil.toString(BDUtil.add(gAccount.getCMBadDeptExpAm(), gAccount.getAMBadDeptExpAm())));
				}

				if(formulaMap.keySet().contains("m_no_of_bad_dept_exp")) {
					formulaMap.put("m_no_of_bad_dept_exp",DataUtil.toString(gAccount.getCMNoOfBadDeptExp() + gAccount.getAMNoOfBadDeptExp()));
				}

				if(formulaMap.keySet().contains("m_bad_dept_closed_am")) {
					formulaMap.put("m_bad_dept_closed_am",DataUtil.toString(BDUtil.add(gAccount.getCMBadDeptClosedAm(), gAccount.getAMBadDeptClosedAm())));
				}

				if(formulaMap.keySet().contains("m_no_of_bad_dept_closed")) {
					formulaMap.put("m_no_of_bad_dept_closed",DataUtil.toString(gAccount.getCMNoOfBadDeptClosed() + gAccount.getAMNoOfBadDeptClosed()));
				}
				
				if(formulaMap.keySet().contains("p_loan_am")) {
					formulaMap.put("p_loan_am", DataUtil.toString(gAccount.getPLoanAm()));
				}

				if(formulaMap.keySet().contains("p_rec_loan_am")) {
					formulaMap.put("p_rec_loan_am", DataUtil.toString(gAccount.getPRecLoanAm()));
				}

				if(formulaMap.keySet().contains("p_rec_int_on_loan_am")) {
					formulaMap.put("p_rec_int_on_loan_am", DataUtil.toString(gAccount.getPRecIntOnLoanAm()));
				}

				if(formulaMap.keySet().contains("p_proj_int_on_loan_am")) {
					formulaMap.put("p_proj_int_on_loan_am", DataUtil.toString(gAccount.getPProjIntOnLoanAm()));
				}

				if(formulaMap.keySet().contains("fix_deposit_inv_am")) {
					formulaMap.put("fix_deposit_inv_am", DataUtil.toString(gAccount.getFixDepositInvAm()));
				}

				if(formulaMap.keySet().contains("rec_fix_deposit_am")) {
					formulaMap.put("rec_fix_deposit_am", DataUtil.toString(gAccount.getRecFixDepositAm()));
				}

				if(formulaMap.keySet().contains("rec_int_on_fix_deposit_am")) {
					formulaMap.put("rec_int_on_fix_deposit_am", DataUtil.toString(gAccount.getRecIntOnFixDepositAm()));
				}

				if(formulaMap.keySet().contains("proj_int_on_fix_deposit_am")) {
					formulaMap.put("proj_int_on_fix_deposit_am", DataUtil.toString(gAccount.getProjIntOnFixDepositAm()));
				}

				if(formulaMap.keySet().contains("other_inv_am")) {
					formulaMap.put("other_inv_am", DataUtil.toString(gAccount.getOtherInvAm()));
				}

				if(formulaMap.keySet().contains("rec_other_inv_am")) {
					formulaMap.put("rec_other_inv_am", DataUtil.toString(gAccount.getRecOtherInvAm()));
				}

				if(formulaMap.keySet().contains("rec_int_on_other_inv_am")) {
					formulaMap.put("rec_int_on_other_inv_am", DataUtil.toString(gAccount.getRecIntOnOtherInvAm()));
				}

				if(formulaMap.keySet().contains("proj_int_on_other_inv_am")) {
					formulaMap.put("proj_int_on_other_inv_am", DataUtil.toString(gAccount.getProjIntOnOtherInvAm()));
				}

				if(formulaMap.keySet().contains("int_en_on_saving_ac_am")) {
					formulaMap.put("int_en_on_saving_ac_am", DataUtil.toString(gAccount.getIntEnOnSavingAcAm()));
				}

				if(formulaMap.keySet().contains("net_profit_am")) {
					formulaMap.put("net_profit_am", DataUtil.toString(gAccount.getNetProfitAm()));
				}

				if(formulaMap.keySet().contains("net_profit_proj_am")) {
					formulaMap.put("net_profit_proj_am", DataUtil.toString(gAccount.getNetProfitProjAm()));
				}

				if(formulaMap.keySet().contains("expenses_am")) {
					formulaMap.put("expenses_am", DataUtil.toString(gAccount.getExpensesAm()));
				}

				if(formulaMap.keySet().contains("outstanding_expenses_am")) {
					formulaMap.put("outstanding_expenses_am", DataUtil.toString(gAccount.getOutstandingExpensesAm()));
				}

				if(formulaMap.keySet().contains("rec_penalty_am")) {
					formulaMap.put("rec_penalty_am", DataUtil.toString(gAccount.getRecPenaltyAm()));
				}

				if(formulaMap.keySet().contains("pending_penalty_am")) {
					formulaMap.put("pending_penalty_am", DataUtil.toString(gAccount.getPendingPenaltyAm()));
				}

				if(formulaMap.keySet().contains("borrowed_loan_am")) {
					formulaMap.put("borrowed_loan_am", DataUtil.toString(gAccount.getBorrowedLoanAm()));
				}

				if(formulaMap.keySet().contains("paid_borrowed_loan_am")) {
					formulaMap.put("paid_borrowed_loan_am", DataUtil.toString(gAccount.getPaidBorrowedLoanAm()));
				}

				if(formulaMap.keySet().contains("paid_int_on_borrowed_loan_am")) {
					formulaMap.put("paid_int_on_borrowed_loan_am", DataUtil.toString(gAccount.getPaidIntOnBorrowedLoanAm()));
				}

				if(formulaMap.keySet().contains("proj_int_on_borrowed_loan_am")) {
					formulaMap.put("proj_int_on_borrowed_loan_am", DataUtil.toString(gAccount.getProjIntOnBorrowedLoanAm()));
				}

				if(formulaMap.keySet().contains("bank_sub_std_dept_am")) {
					formulaMap.put("bank_sub_std_dept_am", DataUtil.toString(gAccount.getBankSubStdDeptAm()));
				}

				if(formulaMap.keySet().contains("bank_no_of_sub_std_dept")) {
					formulaMap.put("bank_no_of_sub_std_dept", DataUtil.toString(gAccount.getBankNoOfSubStdDept()));
				}

				if(formulaMap.keySet().contains("bank_bad_dept_exp_am")) {
					formulaMap.put("bank_bad_dept_exp_am", DataUtil.toString(gAccount.getBankBadDeptExpAm()));
				}

				if(formulaMap.keySet().contains("bank_no_of_bad_dept_exp")) {
					formulaMap.put("bank_no_of_bad_dept_exp", DataUtil.toString(gAccount.getBankNoOfBadDeptExp()));
				}

				if(formulaMap.keySet().contains("bank_bad_dept_closed_am")) {
					formulaMap.put("bank_bad_dept_closed_am", DataUtil.toString(gAccount.getBankBadDeptClosedAm()));
				}

				if(formulaMap.keySet().contains("bank_no_of_bad_dept_closed")) {
					formulaMap.put("bank_no_of_bad_dept_closed", DataUtil.toString(gAccount.getBankNoOfBadDeptClosed()));
				}

				if(formulaMap.keySet().contains("gov_revolving_fund_am")) {
					formulaMap.put("gov_revolving_fund_am", DataUtil.toString(gAccount.getGovRevolvingFundAm()));
				}

				if(formulaMap.keySet().contains("gov_revolving_fund_returned_am")) {
					formulaMap.put("gov_revolving_fund_returned_am", DataUtil.toString(gAccount.getGovRevolvingFundReturnedAm()));
				}

				if(formulaMap.keySet().contains("no_of_gov_revolving_fund")) {
					formulaMap.put("no_of_gov_revolving_fund", DataUtil.toString(gAccount.getNoOfGovRevolvingFund()));
				}

				if(formulaMap.keySet().contains("no_of_active_gov_revolving_fund")) {
					formulaMap.put("no_of_active_gov_revolving_fund", DataUtil.toString(gAccount.getNoOfActiveGovRevolvingFund()));
				}

				if(formulaMap.keySet().contains("gov_development_fund_am")) {
					formulaMap.put("gov_development_fund_am", DataUtil.toString(gAccount.getGovDevelopmentFundAm()));
				}

				if(formulaMap.keySet().contains("no_of_gov_development_fund")) {
					formulaMap.put("no_of_gov_development_fund", DataUtil.toString(gAccount.getNoOfGovDevelopmentFund()));
				}

				if(formulaMap.keySet().contains("last_updated_ts")) {
					formulaMap.put("last_updated_ts", DateUtil.getDisplaySMSDateStr(gAccount.getLastUpdatedTs()));
				}

				if(formulaMap.keySet().contains("subj_clearing_cash_in_hand_am")) {
					formulaMap.put("subj_clearing_cash_in_hand_am", DataUtil.toString(gAccount.getSubjClearingCashInHandAm()));
				}

				if(formulaMap.keySet().contains("subj_clearing_bank_balance_am")) {
					formulaMap.put("subj_clearing_bank_balance_am", DataUtil.toString(gAccount.getSubjClearingBankBalanceAm()));
				}

				if(formulaMap.keySet().contains("clear_cash_in_hand_am")) {
					formulaMap.put("clear_cash_in_hand_am", DataUtil.toString(gAccount.getClearCashInHandAm()));
				}

				if(formulaMap.keySet().contains("clear_bank_balance_am")) {
					formulaMap.put("clear_bank_balance_am", DataUtil.toString(gAccount.getClearBankBalanceAm()));
				}

				if(formulaMap.keySet().contains("no_of_txs_monthly_exp")) {
					formulaMap.put("no_of_txs_monthly_exp", DataUtil.toString(gAccount.getNoOfTxsMonthlyExp()));
				}

				if(formulaMap.keySet().contains("no_of_txs_monthly_done")) {
					formulaMap.put("no_of_txs_monthly_done", DataUtil.toString(gAccount.getNoOfTxsMonthlyDone()));
				}

				if(formulaMap.keySet().contains("no_of_txs_monthly_approved")) {
					formulaMap.put("no_of_txs_monthly_approved", DataUtil.toString(gAccount.getNoOfTxsMonthlyApproved()));
				}

				if(formulaMap.keySet().contains("cash_in_hand_am")) {
					formulaMap.put("cash_in_hand_am", DataUtil.toString(BDUtil.add(gAccount.getSubjClearingCashInHandAm(), gAccount.getClearCashInHandAm())));
				}

				if(formulaMap.keySet().contains("bank_balance_am")) {
					formulaMap.put("bank_balance_am", DataUtil.toString(BDUtil.add(gAccount.getSubjClearingBankBalanceAm(), gAccount.getClearBankBalanceAm())));
				}

				if(formulaMap.keySet().contains("current_balance_am")) {
					formulaMap.put("current_balance_am", DataUtil.toString(BDUtil.add(BDUtil.add(gAccount.getSubjClearingCashInHandAm(), gAccount.getClearCashInHandAm()),
							BDUtil.add(gAccount.getSubjClearingBankBalanceAm(), gAccount.getClearBankBalanceAm()))));
				}

			} catch (Exception e) {
				log.error(e.toString() + "; for GroupAcNo: " + gAccount.getGAcNo());
			}
		}
	}
}