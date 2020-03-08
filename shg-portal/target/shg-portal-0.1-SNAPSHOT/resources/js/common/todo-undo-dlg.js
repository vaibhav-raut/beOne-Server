/**
 * 
 */
var doneTodoTxn = null;
$(document).ready(function() {
	$("#done_principle").change(donePrincipleChangeHandler);
	$("#done_interest").change(doneInterestChangeHandler);
	$("#done_date").change(donePayDateChangeHandler);
	$("#done_pay_mode").change(donePaymentModeChangeHandler);
	$("#done_penalty_check").click(doneApplyPenaltyChangeHandler);
	$("#done_penalty_type").change(donePenaltyTypeChangeHandler);
	$("#done_paid").click(donePaidPendingChangeHandler);
	$("#done_pending").click(donePaidPendingChangeHandler);
	$("#undo_penalty_type").change(undoPenaltyTypeChangeHandler);
	$("#undo_reason").change(undoReasonChangeHandler);
	$("#done_btn").click(doneTodoTransaction);
	$("#undo_btn").click(undoTodoTransaction);
});
function launchDoneDialog(todoTxn) {
	doneTodoTxn = todoTxn;
	$("#done_form").show();
	$("#done_btn").show();
	$("#done_cancel").html("Cancel");
	$("#done_msg").html("");
	$("#done_err_msg").html("");
	$("#done_m_name").html(getReadableAccNum(todoTxn.memberAcNo) + "<br>" + todoTxn.memberName);
	$("#done_loan_principle_interest_row").hide();
	if(	$("#done_tx_type").val() === "Loan Installment") {
		$("#done_loan_principle_interest_row").show();
		$("#done_principle").val(todoTxn.amount - todoTxn.interestComponent);
		$("#done_interest").val(todoTxn.interestComponent);
	}
	$("#done_amt").val(todoTxn.amount);
	$("#done_slip_num").val("");
	var date = new Date();
	$("#done_date").val(date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
	date = new Date(todoTxn.dueDateTs);
	$("#done_due_date").val(date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
	$("#done_pay_mode").val(todoTxn.expectedPaymentMode);
	$("#done_desc").val(todoTxn.description);
	var mAccContent = "";
	if (todoTxn.memberBankAcNos != null) {
		for (var i = 0; i < todoTxn.memberBankAcNos.length; i++)
			mAccContent += "<option>" + todoTxn.memberBankAcNos[i].displayAccount + "</option>";
	}
	$("#done_m_bank_acc").html(mAccContent);
	var gAccContent = "";
	for (var i = 0; i < groupBankAcNos.length; i++)
		gAccContent += "<option>" + groupBankAcNos[i].displayAccount + "</option>";
	$("#done_g_bank_acc").html(gAccContent);
	$("#done_cheque_num").val("");

	donePayDateChangeHandler();

	donePaymentModeChangeHandler();
	$("#done_btn").removeAttr("disabled");
	$("#done").modal("show");
}
function donePayDateChangeHandler() {
	if (doneTodoTxn == null)
		return;
	var dateSplited = $("#done_date").val().split("/");
	if (dateSplited.length < 3)
		return;
	var curDate = (new Date(dateSplited[2], dateSplited[1] - 1, dateSplited[0]));
	var dueDate = new Date(doneTodoTxn.dueDateTs);
	var isDueDateMissed = (curDate.getFullYear() > dueDate.getFullYear()
			|| curDate.getMonth() > dueDate.getMonth()
			|| curDate.getDate() > dueDate.getDate());
	if (doneTodoTxn.txTodoStatus == "Previous Missed" || isDueDateMissed) {
		var interestPenalty = 0, lateFee = 0;
		var desc = doneTodoTxn.description;
		if(isDueDateMissed) {
			var type = ($("#done_tx_type").val() == "Loan Installment") ? "Loan" : "Saving";
			var noOfLatePaidDays = curDate.getDate() - dueDate.getDate();
			if (doneTodoTxn.mRole == "Associate Member") {
				lateFee = ruleAmounts["AM " + type + " Late Fee"];
				if ("Loan" === type)
					interestPenalty = Math.round(doneTodoTxn.amount * (groupRules.amBaseIntOnLoan /36500 ) * noOfLatePaidDays);
			} else {
				lateFee = ruleAmounts["CM " + type + " Late Fee"];
				if ("Loan" === type)
					interestPenalty = Math.round(doneTodoTxn.amount * (groupRules.cmBaseIntOnLoan /36500 ) * noOfLatePaidDays);
			}
			// Updating description
			if(desc.search(", Fee=") === -1)
				desc = (desc + ", Fee=");
			if(interestPenalty > 0)
				desc = (desc + " IP:" + interestPenalty);
			desc = (desc + " LP:" + lateFee);
		}
		$("#done_penalty_check").removeAttr("disabled");
		$("#done_penalty_type").removeAttr("disabled");
		$("#done_paid").removeAttr("disabled");
		$("#done_pending").removeAttr("disabled");
		$("#done_penalty_check").html("<i class='fa fa-check'></i>");
		$("#done_paid").html("<i class='fa fa-check'></i>");
		$("#done_pending").html("<i class='fa fa-times'></i>");
		$("#done_penalty_type").val("Late Fee");
		$("#done_penalty").val(doneTodoTxn.penaltyAm + interestPenalty + lateFee);
		$("#done_desc").val(desc);
	} else {
		$("#done_penalty_check").html("<i class='fa fa-times'></i>");
		$("#done_paid").html("<i class='fa fa-times'></i>");
		$("#done_pending").html("<i class='fa fa-times'></i>");
		$("#done_penalty").val("");
		$("#done_penalty_type").val("");
		$("#done_penalty_check").attr("disabled", "disabled");
		$("#done_penalty_type").attr("disabled", "disabled");
		$("#done_paid").attr("disabled", "disabled");
		$("#done_pending").attr("disabled", "disabled");
		$("#done_desc").val(doneTodoTxn.description);
	}
}
function launchUndoDialog(todoTxn) {
	doneTodoTxn = null;
	$("#undo_form").show();
	$("#undo_btn").show();
	$("#undo_cancel").html("Cancel");
	$("#undo_msg").html("");
	$("#undo_err_msg").html("");
	$("#undo_m_name").html(getReadableAccNum(todoTxn.memberAcNo) + "<br>" + todoTxn.memberName);
	$("#undo_loan_principle_interest_row").hide();
	if(	$("#undo_tx_type").val() === "Loan Installment") {
		$("#undo_loan_principle_interest_row").show();
		$("#undo_principle").val(todoTxn.amount - todoTxn.interestComponent);
		$("#undo_interest").val(todoTxn.interestComponent);
	}
	$("#undo_amt").val(todoTxn.amount);
	$("#undo_slip_num").val(todoTxn.transactions[0].slipNo);
	var date = new Date(todoTxn.transactions[0].paymentTs);
	$("#undo_date").val(date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
	date = new Date(todoTxn.dueDateTs);
	$("#undo_due_date").val(date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear());
	$("#undo_pay_mode").val(todoTxn.expectedPaymentMode);
	$("#undo_desc").val(todoTxn.description);
	// $("#undo_penalty").val(todoTxn.penaltyAm);
	var mAccContent = "";
	if (todoTxn.memberBankAcNos != null) {
		for (var i = 0; i < todoTxn.memberBankAcNos.length; i++)
			mAccContent += "<option>" + todoTxn.memberBankAcNos[i].displayAccount + "</option>";
	}
	$("#undo_m_bank_acc").html(mAccContent);
	var gAccContent = "";
	for (var i = 0; i < groupBankAcNos.length; i++)
		gAccContent += "<option>" + groupBankAcNos[i].displayAccount + "</option>";
	$("#undo_g_bank_acc").html(gAccContent);
	var penaltyTypes = "";
	var penaltyReason = "<option>Entry Mistake</option>";
	if (todoTxn.expectedPaymentMode == "CASH") {
		$("#undo_g_bank_acc").attr("disabled", "disabled");
		$("#undo_m_bank_acc").attr("disabled", "disabled");
	} else {
		$("#undo_g_bank_acc").removeAttr("disabled");
		$("#undo_m_bank_acc").removeAttr("disabled");
		if (todoTxn.transactions[0].groupBankAcDisplay != null) {
			$("#undo_g_bank_acc").val(todoTxn.transactions[0].groupBankAcDisplay.displayAccount);
		}
		if (todoTxn.transactions[0].memberBankAcDisplay != null) {
			$("#undo_m_bank_acc").val(todoTxn.transactions[0].memberBankAcDisplay.displayAccount);
		}
		if (todoTxn.expectedPaymentMode == "CHEQUE") {
			$("#undo_cheque_num").val(todoTxn.transactions[0].chequeNo);
			penaltyTypes += "<option>Cheque Bouncing Penalty</option>";
			penaltyReason += "<option>Cheque Bounced</option>";
		} else if (todoTxn.expectedPaymentMode == "AUTO DEBIT") {
			$("#undo_cheque_num").val("");
			penaltyTypes += "<option>Auto Debit Failure Penalty</option>";
			penaltyReason += "<option>Auto Debit Failure</option>";
		}
	}
	$("#undo_penalty_type").html(penaltyTypes);
	$("#undo_reason").html(penaltyReason);
	$("#undo_btn").removeAttr("disabled");
	$("#undo").modal("show");
}
function donePrincipleChangeHandler() {
	var fieldVal = $("#done_principle").val();
	if (fieldVal == "") {
		fieldVal = 0;
	} else if (isNaN(fieldVal) || 0 > new Number(fieldVal)) {
		fieldVal = 0;
		$("#done_err_msg").html("Please provide valid Principle Component.");
		$("#done_principle").focus();
	} else {
		fieldVal = new Number(fieldVal);
	}
	$("#done_principle").val(fieldVal);
	$("#done_amt").val(new Number($("#done_interest").val()) + fieldVal);
}
function doneInterestChangeHandler() {
	var fieldVal = $("#done_interest").val();
	if (fieldVal == "") {
		fieldVal = 0;
	} else if (isNaN(fieldVal) || 0 > new Number(fieldVal)) {
		fieldVal = 0;
		$("#done_err_msg").html("Please provide valid Interest Component.");
		$("#done_interest").focus();
	} else {
		fieldVal = new Number(fieldVal);
	}
	$("#done_interest").val(fieldVal);
	$("#done_amt").val(new Number($("#done_principle").val()) + fieldVal);
}
function donePaymentModeChangeHandler() {
	var curPayMode = $("#done_pay_mode option:selected").text();
	if (curPayMode == "CASH") {
		$("#done_g_bank_acc").val("");
		$("#done_m_bank_acc").val("");
		$("#done_g_bank_acc").attr("disabled", "disabled");
		$("#done_m_bank_acc").attr("disabled", "disabled");
		$("#done_cheque_num").attr("disabled", "disabled");
	} else {
		$("#done_g_bank_acc").removeAttr("disabled");
		$("#done_m_bank_acc").removeAttr("disabled");
		if (curPayMode == "CHEQUE")
			$("#done_cheque_num").removeAttr("disabled");
		else
			$("#done_cheque_num").attr("disabled", "disabled");
	}
}
function doneApplyPenaltyChangeHandler() {
	if (doneTodoTxn == null)
		return;
	var dateSplited = $("#done_date").val().split("/");
	if (dateSplited.length < 3)
		return;
	if ($("#done_penalty_check").html().indexOf("fa-check") == -1) {

		var dueDate = new Date(doneTodoTxn.dueDateTs);
		var curDate = (new Date(dateSplited[2], dateSplited[1] - 1, dateSplited[0]));
		var isDueDateMissed = (curDate.getFullYear() > dueDate.getFullYear()
				|| curDate.getMonth() > dueDate.getMonth()
				|| curDate.getDate() > dueDate.getDate());
		var interestPenalty = 0, lateFee = 0;
		var desc = doneTodoTxn.description;
		if(isDueDateMissed) {
			var type = ($("#done_tx_type").val() == "Loan Installment") ? "Loan" : "Saving";
			var noOfLatePaidDays = curDate.getDate() - dueDate.getDate();
			if (doneTodoTxn.mRole == "Associate Member") {
				lateFee = ruleAmounts["AM " + type + " Late Fee"];
				if ("Loan" === type)
					interestPenalty = Math.round(doneTodoTxn.amount * (groupRules.amBaseIntOnLoan /36500 ) * noOfLatePaidDays);
			} else {
				lateFee = ruleAmounts["CM " + type + " Late Fee"];
				if ("Loan" === type)
					interestPenalty = Math.round(doneTodoTxn.amount * (groupRules.cmBaseIntOnLoan /36500 ) * noOfLatePaidDays);
			}
			// Updating description
			if(desc.search(", Fee=") === -1)
				desc = (desc + ", Fee=");
			if(interestPenalty > 0)
				desc = (desc + " IP:" + interestPenalty);
			desc = (desc + " LP:" + lateFee);
		}
		$("#done_penalty_check").html("<i class='fa fa-check'></i>");
		$("#done_penalty_type").removeAttr("disabled");
		$("#done_paid").removeAttr("disabled");
		$("#done_pending").removeAttr("disabled");
		$("#done_paid").html("<i class='fa fa-check'></i>");
		$("#done_pending").html("<i class='fa fa-times'></i>");
		$("#done_penalty_type").val("Late Fee");
		$("#done_penalty").val(doneTodoTxn.penaltyAm + interestPenalty + lateFee);
		$("#done_desc").val(desc);
	} else {
		$("#done_penalty_check").html("<i class='fa fa-times'></i>");
		$("#done_paid").html("<i class='fa fa-times'></i>");
		$("#done_pending").html("<i class='fa fa-times'></i>");
		$("#done_penalty_type").val("");
		$("#done_penalty_type").attr("disabled", "disabled");
		$("#done_paid").attr("disabled", "disabled");
		$("#done_pending").attr("disabled", "disabled");
		$("#done_penalty").val("");
		$("#done_desc").val(doneTodoTxn.description + ", Fee= Not Applied");
	}
}
function donePaidPendingChangeHandler() {
	if ($("#done_paid").html().indexOf("fa-check") == -1) {
		$("#done_paid").html("<i class='fa fa-check'></i>");
		$("#done_pending").html("<i class='fa fa-times'></i>");
	} else {
		$("#done_paid").html("<i class='fa fa-times'></i>");
		$("#done_pending").html("<i class='fa fa-check'></i>");
	}
}
function donePenaltyTypeChangeHandler() {
	var donePenalty = $("#done_penalty_type option:selected").text();
	$("#done_penalty").val(doneTodoTxn.penaltyAm + ruleAmounts[donePenalty]);
}
function undoPenaltyTypeChangeHandler() {
	var undoPenalty = $("#undo_penalty_type option:selected").text();
	if (undoPenalty == "Cheque Bouncing Penalty")
		$("#undo_reason").val("Cheque Bounced");
	else if (undoPenalty == "Auto Debit Failure Penalty")
		$("#undo_reason").val("Auto Debit Failed");
	else
		$("#undo_reason").val("Entry Mistake");
	$("#undo_penalty").val(ruleAmounts[undoPenalty]);
}
function undoReasonChangeHandler() {
	var undoReason = $("#undo_reason option:selected").text();
	if (undoReason == "Cheque Bounced") {
		$("#undo_penalty_type").val("Cheque Bouncing Penalty");
		$("#undo_penalty").val(ruleAmounts["Cheque Bouncing Penalty"]);
	} else if (undoReason == "Auto Debit Failed") {
		$("#undo_penalty_type").val("Auto Debit Failure Penalty");
		$("#undo_penalty").val(ruleAmounts["Auto Debit Failure Penalty"]);
	} else {
		$("#undo_penalty_type").val("");
		$("#undo_penalty").val("");
	}
}
function doneTodoTransaction() {
	var slipNo = $("#done_slip_num").val();
	if (slipNo == "" || isNaN(slipNo)) {
		$("#done_err_msg").html("Please provide a valid Slip Number");
		$("#done_slip_num").focus();
		return;
	}
	var date = $("#done_date").val();
	if (date == "") {
		$("#done_err_msg").html("Please provide Payment Date");
		$("#done_date").focus();
		return;
	}
	var paymentMode = $("#done_pay_mode option:selected").text();
	var groupBankAcName = $("#done_g_bank_acc option:selected").text();
	if (groupBankAcName == "" && paymentMode != "CASH") {
		$("#done_err_msg").html("Please select SHG Bank Account Number");
		$("#done_g_bank_acc").focus();
		return;
	}
	var chequeNum = $("#done_cheque_num").val();
	if (paymentMode == "CHEQUE" && (chequeNum == "" || isNaN(chequeNum))) {
		$("#done_err_msg").html("Please provide a valid Cheque Number");
		$("#done_cheque_num").focus();
		return;
	}
	var isPenalty = ($("#done_penalty_check").html().indexOf("fa-check") != -1);
	var penaltyType = $("#done_penalty_type").val();
	if (penaltyType == "" && isPenalty) {
		$("#done_err_msg").html("Please select Penalty Type");
		$("#done_penalty_type").focus();
		return;
	}
	var memberIndex = $("#done_member_index").val();
	var txType = $("#done_tx_type").val();
	var todoTxn = null, amount = 0;
	if (txType == "Saving Installment") {
		todoTxn = savingTodoTxns[memberIndex];
		amount = todoTxn.amount;
	} else if (txType == "Loan Installment") {
		todoTxn = loanTodoTxns[memberIndex];
		amount = $("#done_principle").val();
	}
	if (todoTxn != null) {
		$("#done_btn").attr("disabled", "disabled");
		var accStrForPayload = "";
		if (paymentMode != "CASH") {
			var groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos,
					groupBankAcName);
			accStrForPayload = ",\"groupBankAcNo\": " + groupBankAcNo;
			if (memberBankAcName != "") {
				var memberBankAcName = $("#done_m_bank_acc option:selected")
						.text();
				var memberBankAcNo = getBankAccFromDisplayName(
						todoTxn.memberBankAcNos, memberBankAcName);
				accStrForPayload += ",\"memberBankAcNo\": " + memberBankAcNo;
			}
		}
		var dateSplited = date.split("/");
		var paymentTs = (new Date(dateSplited[2], dateSplited[1] - 1,
				dateSplited[0])).getTime();
		var payload = "[{\"relatedTxTodoId\": " + todoTxn.todoTxId
				+ ",\"txType\": \"" + todoTxn.txType + "\",\"slipNo\": "
				+ parseInt(slipNo) + ",\"memberAcNo\": " + todoTxn.memberAcNo
				+ ",\"groupAcNo\": " + groupAcNo;
		if (chequeNum != "")
			payload += ",\"chequeNo\": " + parseInt(chequeNum);
		payload += ",\"doneByMemberAcNo\": " + curMemberAcNo + ",\"amount\": "
				+ amount + ",\"paymentMode\": \"" + paymentMode
				+ "\",\"paymentTs\": " + paymentTs + accStrForPayload
				+ ",\"description\": \"" + $("#done_desc").val().replace("\n", " ") + "\"}";

		if (txType == "Loan Installment") {
			payload += ", {\"relatedTxTodoId\": " + todoTxn.todoTxId
					+ ",\"txType\": \"Loan Interest Installment"
					+ "\",\"slipNo\": " + parseInt(slipNo)
					+ ",\"memberAcNo\": " + todoTxn.memberAcNo
					+ ",\"groupAcNo\": " + groupAcNo;
			if (chequeNum != "")
				payload += ",\"chequeNo\": " + parseInt(chequeNum);
			payload += ",\"doneByMemberAcNo\": " + curMemberAcNo
					+ ",\"amount\": " + $("#done_interest").val()
					+ ",\"paymentMode\": \"" + paymentMode
					+ "\",\"paymentTs\": " + paymentTs + accStrForPayload
					+ ",\"description\": \"" + $("#done_desc").val().replace("\n", " ") + "\"}";
		}
		if (isPenalty) {
			if ($("#done_pending").html().indexOf("fa-check") != -1) {
				penaltyType = "Outstanding " + penaltyType;
			}

			payload += ", {\"relatedTxTodoId\": " + todoTxn.todoTxId
					+ ",\"txType\": \"" + penaltyType + "\",\"slipNo\": "
					+ parseInt(slipNo) + ",\"memberAcNo\": "
					+ todoTxn.memberAcNo + ",\"groupAcNo\": " + groupAcNo;
			if (chequeNum != "")
				payload += ",\"chequeNo\": " + parseInt(chequeNum);
			payload += ",\"doneByMemberAcNo\": " + curMemberAcNo
					+ ",\"amount\": " + parseFloat($("#done_penalty").val());
			if ($("#done_pending").html().indexOf("fa-check") != -1) {
				payload += ",\"paymentMode\": \"OUTSTANDING";
			} else {
				payload += ",\"paymentMode\": \"" + paymentMode;
			}
			payload += "\",\"paymentTs\": " + paymentTs + accStrForPayload
					+ ",\"description\": \"" + $("#done_desc").val().replace("\n", " ") + "\"}";
		}
		payload += "]";
		ajaxCall("/transaction/v1/transactions", "PUT", false, payload,
				function(data, status) {
					if (data[0].txType == "Saving Installment")
						loadSavingCollectionTab();
					else if (data[0].txType == "Loan Installment")
						loadLoanCollectionTab();
					$("#done_form").hide();
					$("#done_btn").hide();
					$("#done_cancel").html("OK");
					$("#done_msg").html(
							"Transaction Marked Done Successfully:\n\nTransaction Id - "
									+ data[0].txId);
				}, function(xhr) {
					$("#done_btn").removeAttr("disabled");
					handleErrorAndDisplayMsg(xhr, "done_err_msg");
				});
	}
}
function undoTodoTransaction() {
	var memberIndex = $("#undo_member_index").val();
	var txType = $("#undo_tx_type").val();
	var todoTxn = null;
	if (txType == "Saving Installment")
		todoTxn = savingTodoTxns[memberIndex];
	else if (txType == "Loan Installment")
		todoTxn = loanTodoTxns[memberIndex];

	if (todoTxn != null) {
		$("#undo_btn").attr("disabled", "disabled");
		var undoPenalty = $("#undo_penalty").val();

		if (undoPenalty != "" && !isNaN(undoPenalty)) {
			var penaltyTxType = $("#undo_penalty_type").val();
			if (penaltyTxType == "Cheque Bouncing Penalty")
				penaltyTxType = "Outstanding Cheque Bouncing";
			else if (penaltyTxType == "Auto Debit Failure Penalty")
				penaltyTxType = "Outstanding Auto Debit Fail";

			var pload = "[{\"relatedTxTodoId\": " + todoTxn.todoTxId
					+ ",\"txType\": \"" + penaltyTxType + "\",\"memberAcNo\": "
					+ todoTxn.memberAcNo + ",\"groupAcNo\": " + groupAcNo
					+ ",\"doneByMemberAcNo\": " + curMemberAcNo
					+ ",\"amount\": " + parseFloat($("#undo_penalty").val())
					+ ",\"paymentMode\": \"OUTSTANDING\"}]";
			ajaxCall("/transaction/v1/transactions", "PUT", false, pload,
					function(data, status) {
						// $("#undo_msg").html("Transaction for Penalty, Added
						// Successfully:\n\nTransaction Id - " + data[0].txId);
					}, function(xhr) {
						handleErrorAndDisplayMsg(xhr, "undo_err_msg");
					});
		}
		var payload = "[";
		for (var i = 0; i < todoTxn.transactions.length; i++) {
			if (i != 0)
				payload += ",";
			var txId = todoTxn.transactions[i].txId;
			var description = $("#undo_reason").val() + ": Transaction Id - "
					+ txId + " Undone";
			payload += "{\"txId\": " + txId + ",\"relatedTxTodoId\": "
					+ todoTxn.todoTxId + ",\"txType\": \""
					+ todoTxn.transactions[i].txType + "\",\"memberAcNo\": "
					+ todoTxn.memberAcNo + ",\"groupAcNo\": " + groupAcNo
					+ ",\"amount\": " + todoTxn.amount
					+ ",\"doneByMemberAcNo\": " + curMemberAcNo
					+ ",\"description\": \"" + description
					+ "\",\"reasonToUndo\": \"" + $("#undo_reason").val()
					+ "\"}";
		}
		payload += "]";
		ajaxCall("/transaction/v1/undo_transactions", "POST", false, payload,
				function(data, status) {
					if (data[0].txType == "Saving Installment")
						loadSavingCollectionTab();
					else if (data[0].txType == "Loan Installment")
						loadLoanCollectionTab();
					$("#undo_form").hide();
					$("#undo_btn").hide();
					$("#undo_cancel").html("OK");
					$("#undo_msg").html(
							"Transaction Undone Completed for Transaction Id - "
									+ data[0].txId);
				}, function(xhr) {
					$("#undo_btn").removeAttr("disabled");
					handleErrorAndDisplayMsg(xhr, "undo_err_msg");
				});
	}
}