package com.beone.shg.net.persistence.util;

import java.util.ArrayList;
import java.util.List;

import com.beone.shg.net.config.EnumConst;
import com.beone.shg.net.persistence.model.TxType;
import com.beone.shg.net.persistence.support.EnumCache;

public class EnumUtil {

	public static String approvalToAccountStatus(String approval) {
		String account = "";
		
		if(approval.equals(EnumConst.ApprovalStatus_Approved)) {
			account = EnumConst.AccountStatus_Active;
		} else if (approval.equals(EnumConst.ApprovalStatus_Draft)) {
			account = EnumConst.AccountStatus_Draft;
		} else if (approval.equals(EnumConst.ApprovalStatus_Submitted)) {
			account = EnumConst.AccountStatus_Submitted;
		} else if (approval.equals(EnumConst.ApprovalStatus_Rejected)) {
			account = EnumConst.AccountStatus_Rejected;
		} else if (approval.equals(EnumConst.ApprovalStatus_On_Hold)) {
			account = EnumConst.AccountStatus_On_Hold;
		} else if (approval.equals(EnumConst.ApprovalStatus_Sent_Back)) {
			account = EnumConst.AccountStatus_Sent_Back;
		} else if (approval.equals(EnumConst.ApprovalStatus_Under_Review)) {
			account = EnumConst.AccountStatus_Under_Review;
		} else {
			account = approval;
		}

		return account;
	}

	public static boolean isAccountStatusActive(String status) {
		return (status.equals(EnumConst.AccountStatus_Active) ||
				status.equals(EnumConst.AccountStatus_Sub_Standard) ||
				status.equals(EnumConst.AccountStatus_Bad_Debt));
	}

	public static boolean isAccountStatusApproveReject(String status) {
		return (status.equals(EnumConst.AccountStatus_Approved) ||
				status.equals(EnumConst.AccountStatus_Rejected));
	}

	public static boolean isAccountStatusApprovable(String status) {
		return (status.equals(EnumConst.AccountStatus_Submitted) ||
				status.equals(EnumConst.AccountStatus_On_Hold) ||
				status.equals(EnumConst.AccountStatus_Under_Review) ||
				status.equals(EnumConst.AccountStatus_Sent_Back));
	}

	public static boolean isAccountStatusToClosure(String status) {
		return (status.equals(EnumConst.AccountStatus_Active) ||
				status.equals(EnumConst.AccountStatus_Bad_Debt) ||
				status.equals(EnumConst.AccountStatus_Sub_Standard));
	}

	public static boolean isAccountStatusForClosure(String status) {
		return (status.equals(EnumConst.AccountStatus_Bad_Debt_Closed) ||
				status.equals(EnumConst.AccountStatus_Foreclosed) ||
				status.equals(EnumConst.AccountStatus_Complete));
	}

	public static boolean isApprovalStatusApprovable(String status) {
		return (status.equals(EnumConst.ApprovalStatus_Submitted) ||
				status.equals(EnumConst.ApprovalStatus_On_Hold) ||
				status.equals(EnumConst.ApprovalStatus_Under_Review) ||
				status.equals(EnumConst.ApprovalStatus_Under_Review) ||
				status.equals(EnumConst.ApprovalStatus_Sent_Back));
	}

	public static boolean isStatusActive(String status) {
		return (status.equals(EnumConst.ActiveStatus_Active) ||
				status.equals(EnumConst.ActiveStatus_Idle) ||
				status.equals(EnumConst.ActiveStatus_Inactive));
	}

	public static boolean isStatusLive(String status) {
		return (status.equals(EnumConst.ActiveStatus_Active) ||
				status.equals(EnumConst.ActiveStatus_Idle) ||
				status.equals(EnumConst.ActiveStatus_Inactive) ||
				status.equals(EnumConst.ActiveStatus_Locked));
	}	

	public static boolean isStatusUpdatable(String status) {
		return (status.equals(EnumConst.ActiveStatus_Inactive) ||
				status.equals(EnumConst.ActiveStatus_Locked) ||
				status.equals(EnumConst.ActiveStatus_Closed) ||
				status.equals(EnumConst.ActiveStatus_Junk));
	}	

	public static boolean isTodoTxStatusTodo(String status) {
		return (status.equals(EnumConst.TxTodoStatus_Previous_Missed) ||
				status.equals(EnumConst.TxTodoStatus_Missed) ||
				status.equals(EnumConst.TxTodoStatus_Over_Due) ||
				status.equals(EnumConst.TxTodoStatus_Todo) ||
				status.equals(EnumConst.TxTodoStatus_Undone));
	}	

	public static boolean isTxStatusDone(String status) {
		return (status.equals(EnumConst.TxStatus_Submitted) ||
				status.equals(EnumConst.TxStatus_Missing_Bank_Info) ||
				status.equals(EnumConst.TxStatus_Payment_Tobe_Done) ||
				status.equals(EnumConst.TxStatus_Counter_Submitted));
	}	

	public static boolean isTxStatusToUpdable(String status) {
		return (status.equals(EnumConst.TxStatus_Missing_Bank_Info) ||
				status.equals(EnumConst.TxStatus_Payment_Tobe_Done));
	}	

	public static boolean isTxStatusActive(String status) {
		return (status.equals(EnumConst.TxStatus_Submitted) ||
				status.equals(EnumConst.TxStatus_Payment_Tobe_Done) ||
				status.equals(EnumConst.TxStatus_Missing_Bank_Info) ||
				status.equals(EnumConst.TxStatus_Approved) ||
				status.equals(EnumConst.TxStatus_Auto_Approved) ||
				status.equals(EnumConst.TxStatus_Counter_Submitted) ||
				status.equals(EnumConst.TxStatus_Counter_Approved) ||
				status.equals(EnumConst.TxStatus_Complete));
	}	

	public static boolean isTxStatusToApproveReject(String status) {
		return (status.equals(EnumConst.TxStatus_Approved) ||
				status.equals(EnumConst.TxStatus_Rejected));
	}	

	public static boolean isTxStatusApproved(String status) {
		return (status.equals(EnumConst.TxStatus_Approved) ||
				status.equals(EnumConst.TxStatus_Auto_Approved) ||
				status.equals(EnumConst.TxStatus_Counter_Approved) ||
				status.equals(EnumConst.TxStatus_Complete));
	}	

	public static boolean isTxTodoStatusDoneOrApproved(String txTodoStatus) {
		return (txTodoStatus.equals(EnumConst.TxTodoStatus_Done) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Approved) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Auto_Approved));
	}	

	public static boolean isTxTodoStatusApproved(String txTodoStatus) {
		return (txTodoStatus.equals(EnumConst.TxTodoStatus_Approved) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Auto_Approved));
	}	

	public static boolean isTxTodoStatusTodo(String txTodoStatus) {
		return (txTodoStatus.equals(EnumConst.TxTodoStatus_Todo));
	}	

	public static boolean isTxTodoStatusNotDone(String txTodoStatus) {
		return (txTodoStatus.equals(EnumConst.TxTodoStatus_Todo) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Undone) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Previous_Missed));
	}	

	public static boolean isTxTodoStatusMissed(String txTodoStatus) {
		return (txTodoStatus.equals(EnumConst.TxTodoStatus_Todo) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Undone) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Over_Due) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Missed) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Previous_Missed) ||
				txTodoStatus.equals(EnumConst.TxTodoStatus_Auto_Rejected));
	}	

	public static boolean isComputeForMember(String roleCategory) {
		return roleCategory.equals(EnumConst.MRole_Cat_SHG_Member);
	}	

	public static boolean isCoreMember(String role) {
		return (role.equals(EnumConst.MRole_Core_Member) ||
				role.equals(EnumConst.MRole_Group_Secretary) ||
				role.equals(EnumConst.MRole_Group_Treasure) ||
				role.equals(EnumConst.MRole_Group_President));
	}	

	public static boolean isSpecialCoreMember(String role) {
		return (role.equals(EnumConst.MRole_Group_Secretary) ||
				role.equals(EnumConst.MRole_Group_Treasure) ||
				role.equals(EnumConst.MRole_Group_President));
	}	

	public static boolean isAssociateMember(String role) {
		return (role.equals(EnumConst.MRole_Associate_Member));
	}	

	public static boolean isSHGMember(String role) {
		return (isCoreMember(role) || isAssociateMember(role));
	}	

	public static boolean isAccountActiveToCompute(String status) {
		return (status.equals(EnumConst.AccountStatus_Active) ||
				status.equals(EnumConst.AccountStatus_Sub_Standard) ||
				status.equals(EnumConst.AccountStatus_Bad_Debt));
	}	

	public static boolean isActiveToCompute(String status) {
		return (status.equals(EnumConst.ActiveStatus_Active) ||
				status.equals(EnumConst.ActiveStatus_Idle) ||
				status.equals(EnumConst.ActiveStatus_Inactive) ||
				status.equals(EnumConst.ActiveStatus_Locked));
	}	
	public static boolean isLive(String status) {
		return (status.equals(EnumConst.ActiveStatus_Active) ||
				status.equals(EnumConst.ActiveStatus_Requested) ||
				status.equals(EnumConst.ActiveStatus_Locked) ||
				status.equals(EnumConst.ActiveStatus_Idle) ||
				status.equals(EnumConst.ActiveStatus_Inactive));
	}	
	public static boolean isClosed(String status) {
		return (status.equals(EnumConst.ActiveStatus_Closed) ||
				status.equals(EnumConst.ActiveStatus_Completed));
	}	

	public static boolean isBankAffiliatedType(String type) {
		return (type.equals(EnumConst.BankAccountType_Saving_Account) ||
				type.equals(EnumConst.BankAccountType_Current_Account));
	}	

	public static boolean isBankPaymentMode(String mode) {
		return (mode.equals(EnumConst.PaymentMode_CHEQUE) ||
				mode.equals(EnumConst.PaymentMode_CREDIT_CARD) ||
				mode.equals(EnumConst.PaymentMode_DEBIT_CARD) ||
				mode.equals(EnumConst.PaymentMode_DEMAND_DRAFT) ||
				mode.equals(EnumConst.PaymentMode_NET_BANKING));
	}	

	public static boolean isTxPenaltyType(TxType type) {
		return (type.getAmountType().equals(EnumConst.TxType_FEE) ||
				type.getAmountType().equals(EnumConst.TxType_OUTSTANDING_FEE));
	}
	
	public static boolean isTxSavingType(String status) {
		return (status.equals(EnumConst.TxType_Saving_Installment));
	}
	
	public static boolean isTxLoanType(String status) {
		return (status.equals(EnumConst.TxType_Loan_Installment) ||
				status.equals(EnumConst.TxType_Loan_Interest_Installment) ||
				status.equals(EnumConst.TxType_Loan_Prepayment) ||
				status.equals(EnumConst.TxType_Loan_Disbursement));
	}
	
	public static String getAccountToStatusDisplay() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Rejected));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	
	
	public static String getStatusToApprove() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Draft));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Submitted));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Under_Review));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_On_Hold));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Sent_Back));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Approved));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	
	
	public static String getAccountStatusToActive() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Draft));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Submitted));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Under_Review));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_On_Hold));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Sent_Back));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Approved));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	
	
	public static String getActiveAccountStatus() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Active));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Sub_Standard));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.AccountStatus, EnumConst.AccountStatus_Bad_Debt));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getActiveStatusIDs() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Active));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Idle));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Inactive));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ActiveStatus, EnumConst.ActiveStatus_Locked));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getSpecialCoreMemberIDs() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfMRole(EnumConst.MRole_Group_Secretary));
		list.add(EnumCache.getIndexOfMRole(EnumConst.MRole_Group_Treasure));
		list.add(EnumCache.getIndexOfMRole(EnumConst.MRole_Group_President));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getBankPaymentModeIDs() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_CHEQUE));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_CREDIT_CARD));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_DEBIT_CARD));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_DEMAND_DRAFT));
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.PaymentMode, EnumConst.PaymentMode_NET_BANKING));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	

	public static String getTransferTxTypeIds() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfTxType(EnumConst.TxType_Bank_Transfer));
		list.add(EnumCache.getIndexOfTxType(EnumConst.TxType_Bank_Withdrawal));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	
	
	public static String getStatusToDisplay() {
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(EnumCache.getIndexOfEnumValue(EnumConst.ApprovalStatus, EnumConst.ApprovalStatus_Rejected));
		
		return ConversionUtil.convertArrayToInString(list.toArray());
	}	
	
	public static boolean isInternalTxType(String txType) {
		return (txType.equals(EnumConst.TxType_Provisional_Interest_Earned) ||
				txType.equals(EnumConst.TxType_Compute_Cumulative_Saving) ||
				txType.equals(EnumConst.TxType_Bad_Debt_Closure) ||
				txType.equals(EnumConst.TxType_Profit_Share_Declare) ||
				txType.equals(EnumConst.TxType_Divided_Share_Declare));
	}
	
	public static boolean isOutstandingTxType(String txType) {
		return (txType.equals(EnumConst.TxType_Outstanding_Late_Fee) ||
				txType.equals(EnumConst.TxType_Outstanding_Auto_Debit_Fail) ||
				txType.equals(EnumConst.TxType_Outstanding_Cheque_Bouncing) ||
				txType.equals(EnumConst.TxType_Outstanding_Miscellaneous_Fee));
	}
	
	public static boolean isTransferTxType(String txType) {
		return (txType.equals(EnumConst.TxType_Bank_Transfer) ||
				txType.equals(EnumConst.TxType_Bank_Withdrawal));
	}
	
	public static boolean isNonSHGGroupType(String groupType) {
		return (groupType.equals(EnumConst.GroupType_NGO) ||
				groupType.equals(EnumConst.GroupType_Bank) ||
				groupType.equals(EnumConst.GroupType_GOV) ||
				groupType.equals(EnumConst.GroupType_SHG_One_Agent) ||
				groupType.equals(EnumConst.GroupType_Area_Admin) ||
				groupType.equals(EnumConst.GroupType_Super_Area_Admin) ||
				groupType.equals(EnumConst.GroupType_SHG_One_Admin));
	}
	
	public static boolean isMrGroupType(String groupType) {
		return (groupType.equals(EnumConst.GroupType_Udaan_Admin) ||
				groupType.equals(EnumConst.GroupType_Mega_HUB) ||
				groupType.equals(EnumConst.GroupType_Local_HUB) ||
				groupType.equals(EnumConst.GroupType_Manufacturer) ||
				groupType.equals(EnumConst.GroupType_Micro_Retailer) ||
				groupType.equals(EnumConst.GroupType_Sales_Executive) ||
				groupType.equals(EnumConst.GroupType_Super_Sales_Executive));
	}
}
