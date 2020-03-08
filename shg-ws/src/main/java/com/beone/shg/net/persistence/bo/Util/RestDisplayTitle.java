package com.beone.shg.net.persistence.bo.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.beone.shg.net.persistence.model.GRules;

public class RestDisplayTitle {

	private static Map<String, String> todoTransactionRDT = new HashMap<String, String>();
	private static Map<String, String> transactionRDT = new HashMap<String, String>();
	private static Map<String, String> accountBookRDT = new HashMap<String, String>();
	private static Map<String, String> applicationRDT = new HashMap<String, String>();
	private static Map<String, String> groupRulesAllRDT = new HashMap<String, String>();
	private static Map<String, String> groupRulesCMRDT = new HashMap<String, String>();
	private static Map<String, String> acBookRDT = new HashMap<String, String>();
	private static Map<String, String> memberAcBookRDT = new HashMap<String, String>();
	private static Map<String, String> groupAcRDT = new HashMap<String, String>();

	static {

		todoTransactionRDT.put("startTime", "Start Time");
		todoTransactionRDT.put("endTime", "End Time");
		todoTransactionRDT.put("toPayMembersNo", "Still to Pay Members");
		todoTransactionRDT.put("paidMembersNo", "Paid Members");
		todoTransactionRDT.put("pendingAmount", "Pending Amount");
		todoTransactionRDT.put("paidAmount", "Paid Amount");
		todoTransactionRDT.put("appliedPenalty", "Applied Penalty");
		todoTransactionRDT.put("todoTransactions", "Todo Transactions");
		todoTransactionRDT.put("todoTxId", "Todo Transaction Id");
		todoTransactionRDT.put("relatedTxId", "Transaction Id");
		todoTransactionRDT.put("txType", "Transaction Type");
		todoTransactionRDT.put("slipType", "Slip Type");
		todoTransactionRDT.put("memberAcNo", "Account No");
		todoTransactionRDT.put("groupAcNo", "Group Ac No");
		todoTransactionRDT.put("memberInstAcNo", "Member Installment Ac No");
		todoTransactionRDT.put("txAcType", "Installment Account Type");
		todoTransactionRDT.put("memberName", "Name");
		todoTransactionRDT.put("amount", "Amount");
		todoTransactionRDT.put("principleComponent", "Principle Component");
		todoTransactionRDT.put("interestComponent", "Interest Component");
		todoTransactionRDT.put("expectedPaymentMode", "Expected Payment Mode");
		todoTransactionRDT.put("txTodoStatus", "Status");
		todoTransactionRDT.put("penaltyAm", "Penalty");
		todoTransactionRDT.put("dueDateTs", "Due Date");
		todoTransactionRDT.put("createdOnTs", "Created On");
		todoTransactionRDT.put("description", "Description");
		todoTransactionRDT.put("transaction", "Transaction");
		todoTransactionRDT.put("txId", "Transaction Id");
		todoTransactionRDT.put("relatedTxTodoId", "Todo Transaction Id");
		todoTransactionRDT.put("slipNo", "Slip No");
		todoTransactionRDT.put("doneByMemberAcNo", "Done By Member Ac No");
		todoTransactionRDT.put("approvedByMemberAcNo", "Approved By Member Ac No");
		todoTransactionRDT.put("doneByMemberName", "Done By Member Name");
		todoTransactionRDT.put("approvedByMemberName", "Approved By Member Name");
		todoTransactionRDT.put("paymentMode", "Payment Mode");
		todoTransactionRDT.put("groupBankAcNo", "Group Bank Ac");
		todoTransactionRDT.put("memberBankAcNo", "Member Bank Ac");
		todoTransactionRDT.put("groupBankAcDisplay", "Group Bank Ac");
		todoTransactionRDT.put("memberBankAcDisplay", "Member Bank Ac");
		todoTransactionRDT.put("status", "Status");
		todoTransactionRDT.put("location", "Location");
		todoTransactionRDT.put("paymentTs", "Payment Date");
		todoTransactionRDT.put("entryTs", "Entry Date");
		todoTransactionRDT.put("approvedTs", "Approved Date");
		todoTransactionRDT.put("prevClearBalanceAm", "Last Clear Balance");
		todoTransactionRDT.put("prevSubjClearingBalanceAm", "Subjected to Clearing Balance");
		todoTransactionRDT.put("memberBankAcNos", "Member Bank Ac No");
		todoTransactionRDT.put("bankAcNo", "Bank Ac");
		todoTransactionRDT.put("displayAccount", "Display Account");
		todoTransactionRDT.put("displayNames", "Display Names");
		todoTransactionRDT.put("groupBankAcNos", "Group Bank Acs");
		
		transactionRDT.put("startTime", "Start Time");
		transactionRDT.put("endTime", "End Time");
		transactionRDT.put("txId", "Transaction Id");
		transactionRDT.put("relatedTxTodoId", "Todo Transaction Id");
		transactionRDT.put("txType", "Transaction Type");
		transactionRDT.put("slipType", "Slip Type");
		transactionRDT.put("slipNo", "Slip No");
		transactionRDT.put("memberAcNo", "Account No");
		transactionRDT.put("groupAcNo", "Group Ac No");
		transactionRDT.put("savingAcNo", "Saving Ac No");
		transactionRDT.put("loanAcNo", "Loan Ac No");
		transactionRDT.put("memberInstAcType", "Installment Ac Type");
		transactionRDT.put("memberName", "Name");
		transactionRDT.put("amount", "Amount");
		transactionRDT.put("paymentMode", "Payment Mode");
		transactionRDT.put("doneByMemberAcNo", "Done By Member Ac No");
		transactionRDT.put("approvedByMemberAcNo", "Approved By Member Ac No");
		transactionRDT.put("doneByMemberName", "Done By Member Name");
		transactionRDT.put("approvedByMemberName", "Approved By Member Name");
		transactionRDT.put("paymentMode", "Payment Mode");
		transactionRDT.put("groupBankAcNo", "Group Bank Ac No");
		transactionRDT.put("memberBankAcNo", "Member Bank Ac No");
		transactionRDT.put("groupBankAcDisplay", "Group Bank Ac No");
		transactionRDT.put("memberBankAcDisplay", "Member Bank Ac No");
		transactionRDT.put("status", "Status");
		transactionRDT.put("location", "Location");
		transactionRDT.put("paymentTs", "Payment Date");
		transactionRDT.put("entryTs", "Entry Date");
		transactionRDT.put("approvedTs", "Approved Date");
		transactionRDT.put("prevClearBalanceAm", "Last Clear Balance");
		transactionRDT.put("prevSubjClearingBalanceAm", "Subjected to Clearing Balance");
		transactionRDT.put("memberBankAcNos", "Member Bank Ac No");
		transactionRDT.put("bankAcNo", "Bank Ac");
		transactionRDT.put("displayAccount", "Display Account");
		transactionRDT.put("displayNames", "Display Names");
		transactionRDT.put("groupBankAcNos", "Group Bank Acs");		
		transactionRDT.put("chequeNo", "Cheque No");
		transactionRDT.put("reasonToUndo", "Reason To Undo");	
		transactionRDT.put("description", "Description");

		accountBookRDT.put("type", "Book Type");
		accountBookRDT.put("acNo", "Bank Account No");
		accountBookRDT.put("startTime", "Start Date");
		accountBookRDT.put("endTime", "End Date");
		accountBookRDT.put("noOfTxs", "No of Transactions");
		accountBookRDT.put("noOfApprovedTxs", "No of Approved Transactions");
		accountBookRDT.put("totalReceivedAmount", "Total Received Amount");
		accountBookRDT.put("totalPaidAmount", "Total Paid Amount");
		accountBookRDT.put("openingClearBalance", "Opening Balance");
		accountBookRDT.put("openingBalanceSubjectedToClearing", "Opening Balance Subjected To Clearing");
		accountBookRDT.put("closingClearBalance", "Closing Balance");
		accountBookRDT.put("closingBalanceSubjectedToClearing", "Balance Subjected To Clearing");

		applicationRDT.put("memberAcNo", "Member Ac No");
		applicationRDT.put("name", "Name");
		applicationRDT.put("recommendedByMemberAcNo", "Recommended By Member Ac No");
		applicationRDT.put("memberName", "Member Name");
		applicationRDT.put("recommendedByMemberName", "Recommended By Member");
		applicationRDT.put("approvedByMemberName", "Approved By Member");
		applicationRDT.put("mrole", "Member Role");
		applicationRDT.put("gender", "Gender");
		applicationRDT.put("plannedMonthlySavingAm", "Monthly Saving");
		applicationRDT.put("approvalStatus", "Status");
		applicationRDT.put("dateOfEnroll", "Date Of Enrollment");
		applicationRDT.put("creditRating", "Credit Rating");
		applicationRDT.put("savedAm", "Saved Amount");
		applicationRDT.put("memberLoanAcNo", "Fund Ac No");
		applicationRDT.put("pendingLoan", "Pending Loan");
		applicationRDT.put("loanAm", "Loan Amount");
		applicationRDT.put("recLoanAm", "Recovered Loan Amount");
		applicationRDT.put("loanType", "Fund Type");
		applicationRDT.put("loanCalculation", "Fund Calculation");
		applicationRDT.put("accountStatus", "Status");
		applicationRDT.put("recoveryPeriod", "Recovery Period");
		applicationRDT.put("principle", "Fund Amount");
		applicationRDT.put("installment", "EMI");
		applicationRDT.put("preEmiInterest", "Pre EMI Interest");
		applicationRDT.put("projectedInterest", "Projected Interest");
		applicationRDT.put("rateOfInterest", "Rate Of Interest");
		applicationRDT.put("expNoOfInst", "No Of EMIs");
		applicationRDT.put("requestedDate", "Requested Date");
		applicationRDT.put("groupAcNo", "Group Account No");
		applicationRDT.put("groupName", "Name");
		applicationRDT.put("vision", "Vision");
		applicationRDT.put("description", "Description");
		applicationRDT.put("groupType", "Group Type");
		applicationRDT.put("approvalStatus", "Approval Status");
		applicationRDT.put("activeStatus", "Status");
		applicationRDT.put("percentageProfileComplete", "Percentage Profile Complete");
		applicationRDT.put("activeCoreMembers", "Active Core Members");
		applicationRDT.put("activeAssociateMembers", "Active Associate Members");
		applicationRDT.put("dateOfEstablishment", "Date Of Establishment");
		applicationRDT.put("approvedByMember", "Approved By Member");
		applicationRDT.put("registrationNo", "Registration No");
		applicationRDT.put("approvalDate", "Approval Date");
		applicationRDT.put("registrationDate", "Registration Date");

		groupRulesAllRDT.put("computeDayOfMonth", "Compute Day Of Month");
		groupRulesAllRDT.put("dueDayOfMonth", "Due Day Of Month");
		groupRulesAllRDT.put("allowAssociateMembers", "Allow Associate Members");
		groupRulesAllRDT.put("cmMonthlySaving", "CM Monthly Saving");
		groupRulesAllRDT.put("amMinMonthlySaving", "AM Min Monthly Saving");
		groupRulesAllRDT.put("amMaxMonthlySaving", "AM Max Monthly Saving");
		groupRulesAllRDT.put("cmIntOnSaving", "CM Interest On Saving");
		groupRulesAllRDT.put("amIntOnSaving", "AM Interest On Saving");
		groupRulesAllRDT.put("addIntToSavingAfterMonths", "Add Interest To Saving After Months");
		groupRulesAllRDT.put("cmRegistrationFee", "CM Registration Fee");
		groupRulesAllRDT.put("amRegistrationFee", "AM Registration Fee");
		groupRulesAllRDT.put("lateFee", "Late Fee");
		groupRulesAllRDT.put("chequeBouncingPenalty", "Cheque Bouncing Penalty");
		groupRulesAllRDT.put("autoDebitFailurePenalty", "Auto Debit Failure Penalty");
		groupRulesAllRDT.put("cmApplicationFee", "CM Application Fee");
		groupRulesAllRDT.put("amApplicationFee", "AM Application Fee");
		groupRulesAllRDT.put("cmLoanProcessingFeePercent", "CM Loan Processing Fee");
		groupRulesAllRDT.put("amLoanProcessingFeePercent", "AM Loan Processing Fee");
		groupRulesAllRDT.put("cmBaseIntOnLoan", "CM Base Interest On Loan");
		groupRulesAllRDT.put("amBaseIntOnLoan", "AM Base Interest On Loan");
		groupRulesAllRDT.put("cmMinMonthsIntOnLoan", "CM Min Months Interest On Loan");
		groupRulesAllRDT.put("amMinMonthsIntOnLoan", "AM Min Months Interest On Loan");
		groupRulesAllRDT.put("intOnConsumptionLoan", "Interest On Consumption Loan");
		groupRulesAllRDT.put("cmSavingXConsumptionLoan", "CM Saving X Consumption Loan");
		groupRulesAllRDT.put("amSavingXConsumptionLoan", "AM Saving X Consumption Loan");
		groupRulesAllRDT.put("cmLimitOnConsumptionLoan", "CM Limit On Consumption Loan");
		groupRulesAllRDT.put("amLimitOnConsumptionLoan", "AM Limit On Consumption Loan");
		groupRulesAllRDT.put("cmMinMonthsToConLoan", "CM Min Months To Consumption Loan");
		groupRulesAllRDT.put("amMinMonthsToConLoan", "AM Min Months To Consumption Loan");
		groupRulesAllRDT.put("individualDevLoanRange1", "Loan Range 1");
		groupRulesAllRDT.put("individualDevLoanRange2", "Loan Range 2");
		groupRulesAllRDT.put("individualDevLoanRange3", "Loan Range 3");
		groupRulesAllRDT.put("intOnIndividualDevLoanRange1", "Interest On Range 1");
		groupRulesAllRDT.put("intOnIndividualDevLoanRange2", "Interest On Range 2");
		groupRulesAllRDT.put("intOnIndividualDevLoanRange3", "Interest On Range 3");
		groupRulesAllRDT.put("intOnIndividualDevLoanRange4", "Interest On Range 4");
		groupRulesAllRDT.put("projectDevLoanRange1", "Loan Range 1");
		groupRulesAllRDT.put("projectDevLoanRange2", "Loan Range 2");
		groupRulesAllRDT.put("projectDevLoanRange3", "Loan Range 3");
		groupRulesAllRDT.put("intOnProjectDevLoanRange1", "Interest On Range 1");
		groupRulesAllRDT.put("intOnProjectDevLoanRange2", "Interest On Range 2");
		groupRulesAllRDT.put("intOnProjectDevLoanRange3", "Interest On Range 3");
		groupRulesAllRDT.put("intOnProjectDevLoanRange4", "Interest On Range 4");
		groupRulesAllRDT.put("cmSavingXDevLoan", "CM Saving X Development Loan");
		groupRulesAllRDT.put("amSavingXDevLoan", "AM Saving X Development Loan");
		groupRulesAllRDT.put("cmMinMonthsToDevLoan", "CM Min Months To Development Loan");
		groupRulesAllRDT.put("amMinMonthsToDevLoan", "AM Min Months To Development Loan");

		groupRulesCMRDT.put("computeDayOfMonth", "Compute Day Of Month");
		groupRulesCMRDT.put("dueDayOfMonth", "Due Day Of Month");
		groupRulesCMRDT.put("allowAssociateMembers", "Allow Associate Members");
		groupRulesCMRDT.put("cmMonthlySaving", "Monthly Saving");
		groupRulesCMRDT.put("cmIntOnSaving", "Interest On Saving");
		groupRulesCMRDT.put("addIntToSavingAfterMonths", "Add Interest To Saving After Months");
		groupRulesCMRDT.put("cmRegistrationFee", "Registration Fee");
		groupRulesCMRDT.put("lateFee", "Late Fee");
		groupRulesCMRDT.put("chequeBouncingPenalty", "Cheque Bouncing Penalty");
		groupRulesCMRDT.put("autoDebitFailurePenalty", "Auto Debit Failure Penalty");
		groupRulesCMRDT.put("cmApplicationFee", "Application Fee");
		groupRulesCMRDT.put("cmLoanProcessingFeePercent", "Loan Processing Fee");
		groupRulesCMRDT.put("cmBaseIntOnLoan", "Base Interest On Loan");
		groupRulesCMRDT.put("cmMinMonthsIntOnLoan", "Min Months Interest On Loan");
		groupRulesCMRDT.put("intOnConsumptionLoan", "Interest On Consumption Loan");
		groupRulesCMRDT.put("cmSavingXConsumptionLoan", "Saving X Consumption Loan");
		groupRulesCMRDT.put("cmLimitOnConsumptionLoan", "Limit On Consumption Loan");
		groupRulesCMRDT.put("cmMinMonthsToConLoan", "Min Months To Consumption Loan");
		groupRulesCMRDT.put("individualDevLoanRange1", "Loan Range 1");
		groupRulesCMRDT.put("individualDevLoanRange2", "Loan Range 2");
		groupRulesCMRDT.put("individualDevLoanRange3", "Loan Range 3");
		groupRulesCMRDT.put("intOnIndividualDevLoanRange1", "Interest On Range 1");
		groupRulesCMRDT.put("intOnIndividualDevLoanRange2", "Interest On Range 2");
		groupRulesCMRDT.put("intOnIndividualDevLoanRange3", "Interest On Range 3");
		groupRulesCMRDT.put("intOnIndividualDevLoanRange4", "Interest On Range 4");
		groupRulesCMRDT.put("projectDevLoanRange1", "Loan Range 1");
		groupRulesCMRDT.put("projectDevLoanRange2", "Loan Range 2");
		groupRulesCMRDT.put("projectDevLoanRange3", "Loan Range 3");
		groupRulesCMRDT.put("intOnProjectDevLoanRange1", "Interest On Range 1");
		groupRulesCMRDT.put("intOnProjectDevLoanRange2", "Interest On Range 2");
		groupRulesCMRDT.put("intOnProjectDevLoanRange3", "Interest On Range 3");
		groupRulesCMRDT.put("intOnProjectDevLoanRange4", "Interest On Range 4");
		groupRulesCMRDT.put("cmSavingXDevLoan", "Saving X Development Loan");
		groupRulesCMRDT.put("cmMinMonthsToDevLoan", "Min Months To Development Loan");

		acBookRDT.put("date", "Date");
		acBookRDT.put("slipNo", "Recp. No");
		acBookRDT.put("txNo", "Tx. No");
		acBookRDT.put("particular", "Particular");
		acBookRDT.put("amount", "Amount");
		acBookRDT.put("receivedAm", "Received");
		acBookRDT.put("paidAm", "Paid");
		acBookRDT.put("balance", "Balance");
		acBookRDT.put("txStatus", "Status");

		memberAcBookRDT.put("month", "Month");
		memberAcBookRDT.put("creditRating", "Credit Rating");
		memberAcBookRDT.put("meetingAttendance", "Meeting Attendance");
		memberAcBookRDT.put("plannedMonthlySavingAm", "Planned Monthly Saving");
		memberAcBookRDT.put("savedAm", "Total Saving");
		memberAcBookRDT.put("outstandingSavedAm", "Outstanding Saving");
		memberAcBookRDT.put("provIntEnAm", "Prov Interest Earned");
		memberAcBookRDT.put("prevMonthIntEnAm", "Monthly Interest Earned");
		memberAcBookRDT.put("dividedProfitDeclaredAm", "Divided Profit Declared");
		memberAcBookRDT.put("dividedProfitPaidAm", "Divided Profit Paid");
		memberAcBookRDT.put("returnedSavedAm", "Returned Saving");
		memberAcBookRDT.put("returnedIntEnAm", "Returned Interest Earned");
		memberAcBookRDT.put("noOfLoans", "No Of Loans");
		memberAcBookRDT.put("noOfActiveLoans", "No Of Active Loans");
		memberAcBookRDT.put("loanAm", "Total Loan");
		memberAcBookRDT.put("recLoanAm", "Recovered Loan");
		memberAcBookRDT.put("outstandingLoanAm", "Outstanding Loan");
		memberAcBookRDT.put("recIntOnLoanAm", "Recovered Interest On Loan");
		memberAcBookRDT.put("projIntOnLoanAm", "Projected Interest On Loan");
		memberAcBookRDT.put("badDeptClosedAm", "Bad Dept Closed");
		memberAcBookRDT.put("recPenaltyAm", "Penalty");
		memberAcBookRDT.put("pendingPenaltyAm", "Outstanding Penalty");
		memberAcBookRDT.put("meetingAttended", "No Of Meetings Attended");
		memberAcBookRDT.put("meetingMissed", "No Of Meetings Missed");

		groupAcRDT.put("accountStatus", "Status");
		groupAcRDT.put("approvedByMember", "Approved By");
		groupAcRDT.put("approvedByMemberName", "Approved By");
		groupAcRDT.put("approvedDate", "Approved Date");
		groupAcRDT.put("bankGroupAcNo", "Bank Group Ac No");
		groupAcRDT.put("closureDate", "Closure Date");
		groupAcRDT.put("developmentPlan", "Development Plan");
		groupAcRDT.put("disbursementDate", "Disbursement Date");
		groupAcRDT.put("expCompletionDate", "Expected Completion Date");
		groupAcRDT.put("expNoOfInst", "Expected No Of Installments");
		groupAcRDT.put("groupAcNo", "Group Ac No");
		groupAcRDT.put("groupInvtAcNo", "Investment Ac No");
		groupAcRDT.put("groupLoanAcNo", "Loan Ac No");
		groupAcRDT.put("installment", "Installment");
		groupAcRDT.put("instStartDate", "Installment Start Date");
		groupAcRDT.put("interestRate", "Interest Rate");
		groupAcRDT.put("investmentAcName", "Investment Ac Name");
		groupAcRDT.put("investmentDesc", "Investment Desc");
		groupAcRDT.put("investmentNo", "Investment No");
		groupAcRDT.put("investmentType", "Investment Type");
		groupAcRDT.put("invtAm", "Investment Amount");
		groupAcRDT.put("invtBankAcNo", "Investment Bank Ac No");
		groupAcRDT.put("invtDuration", "Investment Duration");
		groupAcRDT.put("loanAcName", "Account Name");
		groupAcRDT.put("loanBankAcNo", "Loan Bank Ac No");
		groupAcRDT.put("loanCalculation", "Loan Calculation");
		groupAcRDT.put("loanProcessingFee", "Loan Processing Fee");
		groupAcRDT.put("loanSource", "Loan Source");
		groupAcRDT.put("loanType", "Loan Type");
		groupAcRDT.put("maturityDate", "Maturity Date");
		groupAcRDT.put("noOfInsallLate", "No Of Late Installments");
		groupAcRDT.put("noOfInsallMissed", "No Of Missed Installments");
		groupAcRDT.put("noOfInstPaid", "No Of Paid Installments");
		groupAcRDT.put("otherFee", "Other Fee");
		groupAcRDT.put("paidInterest", "Paid Interest");
		groupAcRDT.put("pendingInterestDue", "Outstanding Interest Due");
		groupAcRDT.put("pendingPrinciple", "Outstanding Principle");
		groupAcRDT.put("preEmiInterest", "Pre Emi Interest");
		groupAcRDT.put("principle", "Principle");
		groupAcRDT.put("projectedInterest", "Projected Interest");
		groupAcRDT.put("projInterestAm", "Projected Interest");
		groupAcRDT.put("rateOfInterest", "Interest Rate");
		groupAcRDT.put("recInterestAm", "Recovered Interest");
		groupAcRDT.put("recInvtAm", "Recovered Investment");
		groupAcRDT.put("recoveryPeriod", "Recovery Period");
		groupAcRDT.put("requestedDate", "Requested Date");
		groupAcRDT.put("startupNoOfInst", "Startup No Of Installments");
		groupAcRDT.put("statusMessage", "Status Message");
	}
	public static Map<String, String> getTodoTransactionRDT() {

		return todoTransactionRDT;
	}	

	public static Map<String, String> getTransactionRDT() {

		return transactionRDT;
	}	

	public static Map<String, String> getAccountBookRDT() {

		return accountBookRDT;
	}	

	public static Map<String, String> getApplicationRDT() {

		return applicationRDT;
	}	

	public static Map<String, BigDecimal> getRuleAmounts(GRules gRules) {

		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();

		map.put("CM Registration Fee", new BigDecimal(gRules.getCmRegistrationFee()));
		map.put("AM Registration Fee", new BigDecimal(gRules.getAmRegistrationFee()));
		map.put("Late Fee", new BigDecimal(gRules.getCmSavingLateFee()));
		map.put("CM Saving Late Fee", new BigDecimal(gRules.getCmSavingLateFee()));
		map.put("AM Saving Late Fee", new BigDecimal(gRules.getAmSavingLateFee()));
		map.put("CM Loan Late Fee", new BigDecimal(gRules.getCmLoanLateFee()));
		map.put("AM Loan Late Fee", new BigDecimal(gRules.getAmLoanLateFee()));
		map.put("Cheque Bouncing Penalty", new BigDecimal(gRules.getChequeBouncingPenalty()));
		map.put("Auto Debit Failure Penalty", new BigDecimal(gRules.getAutoDebitFailurePenalty()));

		return map;
	}	

	public static Map<String, String> getMemberRDT(Map<String, String> mapIn) {
		Map<String, String> map;
		if(mapIn == null) {
			map = new HashMap<String, String>();
		} else {
			map = mapIn;
		}

		map.put("memberAcNo", "Member Ac No");
		map.put("parentGroupAcNo", "Group Ac No");
		map.put("mappingGroupAcNos", "Mapping Group Ac Nos");
		map.put("recommendedByMemberAcNo", "Recommended By Member");
		map.put("approvedByMemberAcNo", "Approved By Member");
		map.put("memberName", "Member Name");
		map.put("recommendedByMemberName", "Recommended By Member");
		map.put("approvedByMemberName", "Approved By Member");
		map.put("approvalStatus", "Approval Status");
		map.put("activeStatus", "Active Status");
		map.put("mrole", "Member Role");
		map.put("gender", "Gender");
		map.put("uidaiNo", "UIDAI No");
		map.put("panCardNo", "PAN Card No");
		map.put("voterIdNo", "Voter Id No");
		map.put("drivingLicenseNo", "Driving License No");
		map.put("statusMessage", "Status Message");
		map.put("dateOfEnroll", "Date Of Enrollment");
		map.put("dateOfBirth", "Date Of Birth");
		map.put("dateOfAnni", "Date Of Anni");
		map.put("dateOfClosure", "Date Of Closure");
		map.put("monthlySaving", "Monthly Saving");
		map.put("noPlannedSavingInst", "No of Planned Saving Installment");

		return map;
	}	

	public static Map<String, String> getMemberLoanAcRDT(Map<String, String> mapIn) {
		Map<String, String> map;
		if(mapIn == null) {
			map = new HashMap<String, String>();
		} else {
			map = mapIn;
		}

		map.put("memberLoanAcNo", "Member Loan Ac No");
		map.put("memberAcNo", "Member Ac No");
		map.put("loanType", "Fund Type");
		map.put("loanCalculation", "Loan Calculation");
		map.put("accountStatus", "Account Status");
		map.put("recoveryPeriod", "Recovery Period");
		map.put("loanSource", "Fund Source");
		map.put("approvedByMember", "Approved By Member");
		map.put("mrole", "Member Role");
		map.put("memberName", "Member Name");
		map.put("approvedByMemberName", "Approved By Member");
		map.put("principle", "Principle");
		map.put("pendingPrinciple", "Pending Principle");
		map.put("recoveredInterest", "Recovered Interest");
		map.put("projectedInterest", "Projected Interest");
		map.put("installment", "Installment");
		map.put("preEmiInterest", "Pre EMI Interest");
		map.put("pendingInterestDue", "Pending Interest Due");
		map.put("rateOfInterest", "Rate Of Interest");
		map.put("startupNoOfInst", "Startup No Of Installments");
		map.put("expNoOfInst", "Expected No Of Installments");
		map.put("noOfInstPaid", "No Of Installments Paid");
		map.put("noOfInsallLate", "No Of Installments Late");
		map.put("noOfInsallMissed", "No Of Installments Missed");
		map.put("requestedDate", "Requested Date");
		map.put("approvedDate", "Approved Date");
		map.put("disbursementDate", "Disbursement Date");
		map.put("instStartDate", "Installment Start Date");
		map.put("expCompletionDate", "Expected Completion Date");
		map.put("closureDate", "Closure Date");
		map.put("developmentPlan", "Development Plan");
		map.put("statusMessage", "Status Message");

		return map;
	}	

	public static Map<String, String> getMemberSavingAcRDT(Map<String, String> mapIn) {
		Map<String, String> map;
		if(mapIn == null) {
			map = new HashMap<String, String>();
		} else {
			map = mapIn;
		}

		map.put("memberSavingAcNo", "Member Saving Ac No");
		map.put("memberAc", "Member Ac No");
		map.put("accountStatus", "Account Status");
		map.put("recoveryPeriod", "Recovery Period");
		map.put("savedAm", "Saved Amount");
		map.put("cumulativeSavedAm", "Cumulative Saved Amount");
		map.put("installmentAm", "Installment");
		map.put("totalIntEnAm", "Total Interest Earned");
		map.put("currentFyIntEnAm", "Cur FY Interest Earned");
		map.put("prevMonthIntAm", "Prev Month Interest Earned");
		map.put("returnedSavedAm", "Returned Saved Amount");
		map.put("returnedIntEnAm", "Returned Interest Earned");
		map.put("expectedNoOfInst", "Expected No Of Installments");
		map.put("noOfInstPaid", "No Of Installments Paid");
		map.put("noOfInsallLate", "No Of Installments Late");
		map.put("noOfInsallMissed", "No Of Installments Missed");
		map.put("requestedDate", "Requested Date");
		map.put("approvedDate", "Approved Date");
		map.put("actualStartDate", "Actual Start Date");
		map.put("instStartDate", "Installment Start Date");
		map.put("expCompletionDate", "Expected Completion Date");
		map.put("closureDate", "Closure Date");

		return map;
	}	

	public static Map<String, String> getGroupRulesAllRDT() {

		return groupRulesAllRDT;
	}	

	public static Map<String, String> getGroupRulesCMRDT() {

		return groupRulesCMRDT;
	}	

	public static Map<String, String> getAcBookRDT() {

		return acBookRDT;
	}	

	public static Map<String, String> getMemberAcBookRDT() {

		return memberAcBookRDT;
	}	

	public static Map<String, String> getGroupAcRDT() {

		return groupAcRDT;
	}	
}
