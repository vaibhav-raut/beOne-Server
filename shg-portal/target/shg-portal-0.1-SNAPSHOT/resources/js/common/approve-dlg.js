/**
 * 
 */
var approvalReqFrom, curTxnTobeApproved = null;

$(document).ready(function() {
	$("#approve_btn").click(approveTodoTransaction);
	$("#reject_btn").click(rejectTodoTransaction);
});
function launchApproveRejectDialog(txn) {
	curTxnTobeApproved = txn;
	var slipNo = txn.slipNo;
	var paymentTs = txn.paymentTs;
	var groupBankAcDisplay = txn.groupBankAcDisplay;
	var memberBankAcDisplay = txn.memberBankAcDisplay;
	var paymentMode = txn.paymentMode;
	var chequeNo = txn.chequeNo;
	var approvalReqFrom = $("#approval_req_from").val();
	if (approvalReqFrom === "Loan Collection" || approvalReqFrom === "Saving Collection") {
		// if current txn is TodoTransaction type
		slipNo = txn.transactions[0].slipNo;
		paymentTs = txn.transactions[0].paymentTs;
		groupBankAcDisplay = txn.transactions[0].groupBankAcDisplay;
		memberBankAcDisplay = txn.transactions[0].memberBankAcDisplay;
		paymentMode = txn.expectedPaymentMode;
		chequeNo = txn.transactions[0].chequeNo;
	}
	$("#approve_form").show();
	$("#approve_btn").show();
	$("#reject_btn").show();
	$("#approve_cancel").html("Cancel");
	$("#approve_msg").html("");
	$("#approve_err_msg").html("");
	$("#approve_m_name").html(getReadableAccNum(txn.memberAcNo) + "<br>" + txn.memberName);
	$("#approve_loan_principle_interest_row").hide();
	if(	approvalReqFrom === "Loan Collection") {
		$("#approve_loan_principle_interest_row").show();
		$("#approve_principle").val(txn.amount - txn.interestComponent);
		$("#approve_interest").val(txn.interestComponent);
	}
	$("#approve_amt").val(txn.amount);
	$("#approve_slip_num").val(slipNo);
	var date = new Date(paymentTs);
	$("#approve_date").val(date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
	var dueDateStr = "N/A", dueDateTs = txn.dueDateTs;
	if (dueDateTs) {
		date = new Date(dueDateTs);
		dueDateStr = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear();
	}
	$("#approve_due_date").val(dueDateStr);
	$("#approve_pay_mode").val(paymentMode);
	$("#approve_desc").val(txn.description);
	if (txn.penaltyPaidAm > 0) {
		$("#approve_penalty_check").html("<i class='fa fa-check'></i>");
		$("#approve_penalty").val(txn.penaltyPaidAm);
	} else {
		$("#approve_penalty_check").html("<i class='fa fa-times'></i>");
		$("#approve_penalty").val("");
		$("#approve_penalty_type").val("");
	}
	var mAccContent = "";
	if (txn.memberBankAcNos != null) {
		for (var i = 0; i < txn.memberBankAcNos.length; i++)
			mAccContent += "<option>" + txn.memberBankAcNos[i].displayAccount + "</option>";
	}
	$("#approve_m_bank_acc").html(mAccContent);
	var gAccContent = "";
	for (var i = 0; i < groupBankAcNos.length; i++)
		gAccContent += "<option>" + groupBankAcNos[i].displayAccount + "</option>";
	$("#approve_g_bank_acc").html(gAccContent);
	if (paymentMode == "CASH") {
		$("#approve_g_bank_acc").attr("disabled", "disabled");
		$("#approve_m_bank_acc").attr("disabled", "disabled");
	} else {
		$("#approve_g_bank_acc").removeAttr("disabled");
		$("#approve_m_bank_acc").removeAttr("disabled");
		if (groupBankAcDisplay != null) {
			$("#approve_g_bank_acc").val(groupBankAcDisplay.displayAccount);
		}
		if (memberBankAcDisplay != null) {
			$("#approve_m_bank_acc").val(memberBankAcDisplay.displayAccount);
		}
		if (paymentMode == "CHEQUE")
			$("#approve_cheque_num").val(chequeNo);
		else
			$("#approve_cheque_num").val("");
	}
	$("#approve_btn").removeAttr("disabled");
	$("#reject_btn").removeAttr("disabled");
	$("#approve").modal("show");
}
function approveTodoTransaction() {
	approveRejectTodoTransaction("Approved");
	curTxnTobeApproved = null;
}
function rejectTodoTransaction() {
	approveRejectTodoTransaction("Rejected");
	curTxnTobeApproved = null;
}
function approveRejectTodoTransaction(status) {
	var date = $("#approve_date").val();
	if (date == "") {
		$("#approve_err_msg").html("Please provide Payment Date");
		$("#approve_date").focus();
		return;
	}
	var paymentMode = $("#approve_pay_mode option:selected").text();
	var groupBankAcName = $("#approve_g_bank_acc option:selected").text();
	if (groupBankAcName == "" && paymentMode != "CASH") {
		$("#approve_err_msg").html("Please select SHG Bank Account Number");
	} else {
		approvalReqFrom = $("#approval_req_from").val();
		txn = curTxnTobeApproved;
		if (txn != null) {
			$("#approve_btn").attr("disabled", "disabled");
			$("#reject_btn").attr("disabled", "disabled");
			var accStrForPayload = "";
			if (paymentMode != "CASH") {
				var groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, groupBankAcName);
				accStrForPayload = ",\"groupBankAcNo\": " + groupBankAcNo;
				var memberBankAcName = $("#approve_m_bank_acc option:selected").text();
				if (memberBankAcName != "") {
					var memberBankAcNo = getBankAccFromDisplayName(txn.memberBankAcNos, memberBankAcName);
					accStrForPayload += ",\"memberBankAcNo\": " + memberBankAcNo;
				}
			}
			var dateSplited = date.split("/");
			var paymentTs = (new Date(dateSplited[2], dateSplited[1] - 1, dateSplited[0])).getTime();
			var payload = "[";
			var approvalReqFrom = $("#approval_req_from").val();
			if (approvalReqFrom === "Loan Collection" || approvalReqFrom === "Saving Collection") {
				for (var i = 0; i < txn.transactions.length; i++) {
					if (i != 0)
						payload += ",";
					payload += "{\"txId\": " + txn.transactions[i].txId
							+ ",\"relatedTxTodoId\": " + txn.todoTxId
							+ ",\"txType\": \"" + txn.transactions[i].txType
							+ "\",\"memberAcNo\": " + txn.memberAcNo
							+ ",\"paymentTs\": " + paymentTs
							+ ",\"groupAcNo\": " + groupAcNo + ",\"amount\": "
							+ txn.amount + ",\"status\": \"" + status
							+ "\",\"approvedByMemberAcNo\": " + curMemberAcNo
							+ accStrForPayload + ",\"description\": \""
							+ $("#approve_desc").val().replace("\n", " ") + "\"}";
				}
			} else {
				payload += "{\"txId\": " + txn.txId
						+ ",\"relatedTxTodoId\": " + txn.relatedTxTodoId
						+ ",\"txType\": \"" + txn.txType
						+ "\",\"memberAcNo\": " + txn.memberAcNo
						+ ",\"paymentTs\": " + paymentTs
						+ ",\"groupAcNo\": " + groupAcNo + ",\"amount\": "
						+ txn.amount + ",\"status\": \"" + status
						+ "\",\"approvedByMemberAcNo\": " + curMemberAcNo
						+ accStrForPayload + ",\"description\": \""
						+ $("#approve_desc").val().replace("\n", " ") + "\"}";
			}
			payload += "]";
			ajaxCall("/transaction/v1/approve_reject_transactions", "POST",
					false, payload, function(data, status) {
						if (approvalReqFrom === "Saving Collection")
							loadSavingCollectionTab();
						else if (approvalReqFrom === "Loan Collection")
							loadLoanCollectionTab();
						else if (approvalReqFrom === "Account Statement" || approvalReqFrom === "Account Book") {
							// Transaction are shown at both places, reloading both.
							loadAccStatementData();
							loadAccountBookData();
						}
						$("#approve_form").hide();
						$("#approve_btn").hide();
						$("#reject_btn").hide();
						$("#approve_cancel").html("OK");
						$("#approve_msg").html("Transaction " + data[0].status + " for Transaction Id - " + data[0].txId);
					}, function(xhr) {
						$("#approve_btn").removeAttr("disabled");
						$("#reject_btn").removeAttr("disabled");
						handleErrorAndDisplayMsg(xhr, "approve_err_msg");
					});
		}
	}
}