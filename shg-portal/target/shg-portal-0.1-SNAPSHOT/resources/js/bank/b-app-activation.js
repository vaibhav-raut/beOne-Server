/**
 * 
 */
var paymentMode = null, memberBankAcNos = null, txns2Activate = null, updatedTxn2Activate = new Array();

$(document).ready(function() {
	$("#loan_acc_activate_btn").click(activateLoanAccount);
	$("#invt_acc_activate_btn").click(activateInvtAccount);
	$("#loan_acc_closure_btn").click(closeLoanAccount);
	$("#invt_acc_closure_btn").click(closeInvtAccount);
	$("#g2b_paymode_0").change(paymentModeChangeHandler);
	$("#g2b_paymode_1").change(paymentModeChangeHandler);
	$("#g2b_paymode_2").change(paymentModeChangeHandler);
	$("#g2b_paymode_3").change(paymentModeChangeHandler);
	$("#b2g_paymode_0").change(paymentModeChangeHandler);
	$("#b2g_paymode_1").change(paymentModeChangeHandler);
	$("#g2b_check_0").click(g2bTxnRowSelectionChangehandler);
	$("#g2b_check_1").click(g2bTxnRowSelectionChangehandler);
	$("#g2b_check_2").click(g2bTxnRowSelectionChangehandler);
	$("#g2b_check_3").click(g2bTxnRowSelectionChangehandler);
	$("#b2g_check_0").click(b2gTxnRowSelectionChangehandler);
	$("#b2g_check_1").click(b2gTxnRowSelectionChangehandler);
	$("#g2b_amt_0").change(txnAmtChangeHandler);
	$("#g2b_amt_1").change(txnAmtChangeHandler);
	$("#g2b_amt_2").change(txnAmtChangeHandler);
	$("#g2b_amt_3").change(txnAmtChangeHandler);
	$("#b2g_amt_0").change(txnAmtChangeHandler);
	$("#b2g_amt_1").change(txnAmtChangeHandler);
	$("#go2activate_btn1").click(loadLoanAccActivationTab);
	$("#go2activate_btn2").click(loadInvtAccActivationTab);
	$("#back2accTrac_btn3").click(back2AppTrackFromAppActivate);
	$("#app_print").click(printTable);
	$("#app_pdf").click(printTable);
	$("#app_excel").click(genExcel);
});
function loadPaymentModes() {
	ajaxCall("/enum/v1/enum_values/PaymentMode", "GET", true, "", function(
			data, status) {
		if (data != "") {
			paymentMode = data.enumValues;
			var paymodes = "";
			if (paymentMode != null) {
				for (var k = 0; k < paymentMode.length; k++) {
					paymodes += " <option>" + paymentMode[k].enumValue
							+ "</option>";
				}
				$("#g2b_paymode_0").html(paymodes);
				$("#g2b_paymode_1").html(paymodes);
				$("#g2b_paymode_2").html(paymodes);
				$("#g2b_paymode_3").html(paymodes);
				$("#b2g_paymode_0").html(paymodes);
				$("#b2g_paymode_1").html(paymodes);
			}
		}
	});
}
function getGroupBankInfo() {
	ajaxCall("/group/v1/group_bank_info/" + $("#lang option:selected").text()
			+ "/" + groupAcNo, "GET", true, "", function(data, status) {
		if (data != "") {
			groupBankAcNos = data.groupBankAcNos;
			var gAccContent = "";
			if (groupBankAcNos != null) {
				for (var i = 0; i < groupBankAcNos.length; i++) {
					gAccContent += "<option>"
							+ groupBankAcNos[i].displayAccount + "</option>";
				}
				$("#shgbankacc").html(gAccContent);
			}
		}
	});
}
function resetAppActivationForm() {
	txns2Activate = null;
	trackedInvtAcc = null;
	trackedLoanAcc = null;
	$("#activate_msg").html("");
	$("#activate_err_msg").html("");
	$("#app_detail_body").html("");
	$("#loan_closure_btns").hide();
	$("#invt_closure_btns").hide();
	$("#loan_activate_btns").hide();
	$("#invt_activate_btns").hide();
	resetActivateClosureTxnForm();
}
function back2AppTrackFromAppActivate() {
	resetAppActivationForm();
	loadBankAppTrackingTab();
	resetBankLoanForm();
	resetBankInvestmentForm();
}
function showLoanAccActivateButton() {
	$("#loan_closure_btns").hide();
	$("#invt_closure_btns").hide();
	$("#invt_activate_btns").hide();
	$("#loan_activate_btns").show();
}
function showInvtAccActivateButton() {
	$("#loan_closure_btns").hide();
	$("#invt_closure_btns").hide();
	$("#loan_activate_btns").hide();
	$("#invt_activate_btns").show();
}
function showLoanAccClosureButton() {
	$("#loan_activate_btns").hide();
	$("#invt_activate_btns").hide();
	$("#invt_closure_btns").hide();
	$("#loan_closure_btns").show();
}
function showInvtAccClosureButton() {
	$("#loan_activate_btns").hide();
	$("#invt_activate_btns").hide();
	$("#loan_closure_btns").hide();
	$("#invt_closure_btns").show();
}
function resetActivateClosureTxnForm() {
	$("#tx2activate_table").hide();
	$("#slipnum").val("");
	$("#g2b_title").hide();
	$("#b2g_title").hide();
	$("#b2g_tx_net").hide();
	for(var g2b = 0; g2b < 4; g2b++) {
		$("#g2b_tx_" + g2b).hide();
		$("#g2b_check_" + g2b).html("<i class='fa fa-times'></i>");
		$("#g2b_txtype_" + g2b).html("");
		$("#g2b_amt_" + g2b).val("0");
		$("#g2b_paymode_" + g2b).val("CASH");
		$("#g2b_cheque_" + g2b).val("");
	}
	for(var b2g = 0; b2g < 2; b2g++) {
		$("#b2g_tx_" + b2g).hide();
		$("#b2g_tx_d" + b2g).hide();
		$("#b2g_check_" + b2g).html("<i class='fa fa-times'></i>");
		$("#b2g_txtype_" + b2g).html("");
		$("#b2g_amt_" + b2g).val("0");
		$("#b2g_paymode_" + b2g).val("CASH");
		$("#b2g_cheque_" + b2g).val("");
		$("#b2g_desc_" + b2g).html("");
	}	
}
function displayLoanAccInfo() {
	var tablecontent = "<tr><td>Account Number</td><td>"
			+ trackedLoanAcc.groupLoanAcNo
			+ "</td><td width='60'>&nbsp;</td><td>Account Name</td><td>"
			+ trackedLoanAcc.loanAcName
			+ "</td></tr><tr><td>Loan Amount</td><td>"
			+ trackedLoanAcc.principle
			+ "</td><td>&nbsp;</td><td>Rate of Interest</td><td>"
			+ trackedLoanAcc.rateOfInterest
			+ "</td></tr><tr><td>Loan Computation Type</td><td>"
			+ trackedLoanAcc.loanCalculation
			+ "</td><td>&nbsp;</td><td>Fixed EMI</td><td>"
			+ trackedLoanAcc.installment
			+ "</td></tr><tr><td>Requested Date</td><td>"
			+ trackedLoanAcc.requestedDate
			+ "</td><td>&nbsp;</td><td></td><td><a class='btn btn-sm yellow' href='#bank_loan_content' data-toggle='tab' id='loan_detail_"
			+ trackedLoanAcc.groupLoanAcNo + "'>More Details</a></td></tr>";

	$("#app_detail_body").html(tablecontent);
	$("#loan_detail_" + trackedLoanAcc.groupLoanAcNo).click(
			showBankLoanDetailsForTracking);
}
function displayInvtAccInfo() {
	var tablecontent = "<tr><td>Account Number</td><td>"
			+ trackedInvtAcc.groupInvtAcNo
			+ "</td><td width='60'>&nbsp;</td><td>Account Name</td><td>"
			+ trackedInvtAcc.loanAcName
			+ "</td></tr><tr><td>Investment Amount</td><td>"
			+ trackedInvtAcc.invtAm
			+ "</td><td>&nbsp;</td><td>Rate of Interest</td><td>"
			+ trackedInvtAcc.interestRate
			+ "</td></tr><tr><td>Investment Type</td><td>"
			+ trackedInvtAcc.investmentType
			+ "</td><td>&nbsp;</td><td>Maturity Date</td><td>"
			+ trackedInvtAcc.maturityDate
			+ "</td></tr><tr><td>Requested Date</td><td>"
			+ trackedInvtAcc.requestedDate
			+ "</td><td>&nbsp;</td><td></td><td><a class='btn btn-sm yellow' href='#investment_content' data-toggle='tab' id='invt_detail_"
			+ trackedInvtAcc.groupInvtAcNo + "'>More Details</a></td></tr>";

	$("#app_detail_body").html(tablecontent);
	$("#invt_detail_" + trackedInvtAcc.groupInvtAcNo).click(
			showBankInvtDetailsForTracking);
}
function loadLoanAccActivationTab() {
	if (groupAllTrackedAcc != null
			&& groupAllTrackedAcc.groupLoanAcs.length != 0) {
		var index = parseInt($(this).data("id"));
		if (!isNaN(index)) {
			trackedLoanAcc = groupAllTrackedAcc.groupLoanAcs[index];
		}
		showLoanAccActivateButton();
		displayLoanAccInfo();
		resetActivateClosureTxnForm();
		loadTxs2ActivateApp("loan");
	} else {
		$("app_detail_body").html("You do not have any data to display.");
	}
}
function loadInvtAccActivationTab() {
	if (groupAllTrackedAcc != null
			&& groupAllTrackedAcc.groupInvtAcs.length != 0) {
		var index = parseInt($(this).data("id"));
		if (!isNaN(index)) {
			trackedInvtAcc = groupAllTrackedAcc.groupInvtAcs[index];
		}
		showInvtAccActivateButton();
		displayInvtAccInfo();
		resetActivateClosureTxnForm();
		loadTxs2ActivateApp("investment");
	} else {
		$("app_detail_body").html("You do not have any data to display.");
	}
}
function loadTxs2ActivateApp(appName) {
	var url = "", payload = "";
	if (appName == "loan" && trackedLoanAcc != null
			&& trackedLoanAcc.approvalStatus != "Active") {
		url = "/gloan/v1/txs_to_activate";
		payload = JSON.stringify(trackedLoanAcc);
		memberBankAcNos = trackedLoanAcc.memberBankAcNos;
		$("#loan_acc_activate_btn").attr("disabled", "disabled");
	} else if (appName == "investment" && trackedInvtAcc != null
			&& trackedInvtAcc.accountStatus != "Active") {
		url = "/group_invt/v1/txs_to_activate";
		payload = JSON.stringify(trackedInvtAcc);
		memberBankAcNos = trackedInvtAcc.memberBankAcNos;
		$("#invt_acc_activate_btn").attr("disabled", "disabled");
	}
	if (url != "") {
		$("#tx2activate_msg")
				.html(
						"<div align='center'>Please wait... <img src='resources/img/ajax-loader.gif' alt=''/></div><br>");
		var mAccContent = "<option></option>";
		if (memberBankAcNos != null) {
			for (var i = 0; i < memberBankAcNos.length; i++) {
				mAccContent += "<option>" + memberBankAcNos[i].displayAccount
						+ "</option>";
			}
			$("#mbankacc").html(gAccContent);
		}
		ajaxCall(url, "POST", true, payload, function(data, status) {
			if (data != null && data != "" && data.transactions != null
					&& data.transactions.length != 0) {
				txns2Activate = data.transactions;
				$("#tx2activate_msg").html("");
				$("#pay_date").val(convertTS2DDMMYYYYDate(currentServerTime));
				$("#tx2activate_table").show();

				var g2b = 0, b2g = 0;
				for (var k = 0; k < data.transactions.length; k++) {
					if (data.transactions[k].slipType == "VOUCHER") {
						$("#g2b_title").show();
						$("#g2b_check_" + g2b).html("<i class='fa fa-check'></i>");
						$("#g2b_amt_" + g2b).removeAttr("disabled");
						$("#g2b_paymode_" + g2b).removeAttr("disabled");
						$("#g2b_txtype_" + g2b).html("<strong>" + data.transactions[k].txType + "</strong>");
						$("#g2b_amt_" + g2b).val(data.transactions[k].amount);
						$("#g2b_paymode_" + g2b).val(data.transactions[k].paymentMode);
						$("#g2b_tx_" + g2b).show();
						g2b++;
					} else if (data.transactions[k].slipType == "RECEIPT") {
						$("#b2g_title").show();
						$("#b2g_check_" + b2g).html("<i class='fa fa-check'></i>");
						$("#b2g_amt_" + b2g).removeAttr("disabled");
						$("#b2g_paymode_" + b2g).removeAttr("disabled");
						$("#b2g_txtype_" + b2g).html("<strong>" + data.transactions[k].txType + "</strong>");
						$("#b2g_amt_" + b2g).val(data.transactions[k].amount);
						$("#b2g_desc_" + b2g).html(data.transactions[k].description);
						$("#b2g_paymode_" + b2g).val(data.transactions[k].paymentMode);
						$("#b2g_tx_" + b2g).show();
						$("#b2g_tx_d" + b2g).show();
						b2g++;
					}
				}
				paymentModeChangeHandler();
				$("#loan_acc_activate_btn").removeAttr("disabled");
				$("#invt_acc_activate_btn").removeAttr("disabled");
			} else {
				$("#tx2activate_msg").html("No transactions found.");
			}
		}, function(xhr) {
			handleErrorAndDisplayMsg(xhr, "tx2activate_msg");
		});
	}
}
function loadLoanAccClosureTab() {
	if (groupAllTrackedAcc != null
			&& groupAllTrackedAcc.groupLoanAcs.length != 0) {
		var index = parseInt($(this).data("id"));
		if (!isNaN(index)) {
			trackedLoanAcc = groupAllTrackedAcc.groupLoanAcs[index];
		}
		showLoanAccClosureButton();
		displayLoanAccInfo();
		resetActivateClosureTxnForm();
		loadTxs2CloseApp("loan");
	} else {
		$("app_detail_body").html("You do not have any data to display.");
	}
}
function loadInvtAccClosureTab() {
	if (groupAllTrackedAcc != null
			&& groupAllTrackedAcc.groupInvtAcs.length != 0) {
		var index = parseInt($(this).data("id"));
		if (!isNaN(index)) {
			trackedInvtAcc = groupAllTrackedAcc.groupInvtAcs[index];
		}
		showInvtAccClosureButton();
		displayInvtAccInfo();
		resetActivateClosureTxnForm();
		loadTxs2CloseApp("investment");
	} else {
		$("app_detail_body").html("You do not have any data to display.");
	}
}
function loadTxs2CloseApp(appName) {
	var url = "", payload = "";
	if (appName == "loan" && trackedLoanAcc != null) {
		url = "/gloan/v1/txs_to_close";
		payload = JSON.stringify(trackedLoanAcc);
		memberBankAcNos = trackedLoanAcc.memberBankAcNos;
		$("#loan_acc_closure_btn").attr("disabled", "disabled");
	} else if (appName == "investment" && trackedInvtAcc != null) {
		url = "/group_invt/v1/txs_to_recover";
		payload = JSON.stringify(trackedInvtAcc);
		memberBankAcNos = trackedInvtAcc.memberBankAcNos;
		$("#invt_acc_closure_btn").attr("disabled", "disabled");
	}
	if (url != "") {
		$("#tx2activate_msg")
				.html(
						"<div align='center'>Please wait... <img src='resources/img/ajax-loader.gif' alt=''/></div><br>");
		var mAccContent = "<option></option>";
		if (memberBankAcNos != null) {
			for (var i = 0; i < memberBankAcNos.length; i++) {
				mAccContent += "<option>" + memberBankAcNos[i].displayAccount
						+ "</option>";
			}
			$("#mbankacc").html(gAccContent);
		}
		ajaxCall(url, "POST", true, payload, function(data, status) {
			if (data != null && data != "" && data.transactions != null
					&& data.transactions.length != 0) {
				txns2Activate = data.transactions;
				$("#tx2activate_msg").html("");
				$("#pay_date").val(convertTS2DDMMYYYYDate(currentServerTime));
				$("#tx2activate_table").show();

				var g2b = 0, b2g = 0;
				for (var k = 0; k < data.transactions.length; k++) {
					if (data.transactions[k].slipType == "VOUCHER") {
						$("#g2b_title").show();
						$("#g2b_check_" + g2b).html("<i class='fa fa-check'></i>");
						$("#g2b_amt_" + g2b).removeAttr("disabled");
						$("#g2b_paymode_" + g2b).removeAttr("disabled");
						$("#g2b_txtype_" + g2b).html("<strong>" + data.transactions[k].txType + "</strong>");
						$("#g2b_amt_" + g2b).val(data.transactions[k].amount);
						$("#g2b_paymode_" + g2b).val(data.transactions[k].paymentMode);
						$("#g2b_tx_" + g2b).show();
						g2b++;
					} else if (data.transactions[k].slipType == "RECEIPT") {
						$("#b2g_title").show();
						$("#b2g_check_" + b2g).html("<i class='fa fa-check'></i>");
						$("#b2g_amt_" + b2g).removeAttr("disabled");
						$("#b2g_paymode_" + b2g).removeAttr("disabled");
						$("#b2g_txtype_" + b2g).html("<strong>" + data.transactions[k].txType + "</strong>");
						$("#b2g_amt_" + b2g).val(data.transactions[k].amount);
						$("#b2g_desc_" + b2g).html(data.transactions[k].description);
						$("#b2g_paymode_" + b2g).val(data.transactions[k].paymentMode);
						$("#b2g_tx_" + b2g).show();
						$("#b2g_tx_d" + b2g).show();
						b2g++;
					}
				}
				paymentModeChangeHandler();
				$("#loan_acc_closure_btn").removeAttr("disabled");
				$("#invt_acc_closure_btn").removeAttr("disabled");
			} else {
				$("#tx2activate_msg").html("No transactions found.");
			}
		}, function(xhr) {
			handleErrorAndDisplayMsg(xhr, "tx2activate_msg");
		});
	}
}
function txnAmtChangeHandler() {
	var fieldVal = $(this).val();
	if (fieldVal == "") {
		fieldVal = 0;
	} else if (isNaN(fieldVal)) {
		fieldVal = 0;
		showMessage("Please enter only numbers.", $(this).attr("id"));
		$(this).focus();
	} else {
		fieldVal = new Number(fieldVal);
	}
	$(this).val(fieldVal);
	updateBankToGroupNetPayable();
}
function g2bTxnRowSelectionChangehandler() {
	var value = $(this).val();
	var html = $("#g2b_txtype_" + value).html();
	if ($(this).html().indexOf("fa-check") == -1) {
		$(this).html("<i class='fa fa-check'></i>");
		$("#g2b_txtype_" + value).html("<strong>" + html + "</strong>");
		$("#g2b_amt_" + value).removeAttr("disabled");
		$("#g2b_paymode_" + value).removeAttr("disabled");
		$("#g2b_cheque_" + value).removeAttr("disabled");
		paymentModeChangeHandler();
	} else {
		$(this).html("<i class='fa fa-times'></i>");
		$("#g2b_txtype_" + value).html(html.replace(/<strong>/g, "").replace(/<\/strong>/g, ""));
		$("#g2b_amt_" + value).attr("disabled", "disabled");
		$("#g2b_paymode_" + value).attr("disabled", "disabled");
		$("#g2b_cheque_" + value).attr("disabled", "disabled");
		updateBankToGroupNetPayable();
	}
}
function b2gTxnRowSelectionChangehandler() {
	var value = $(this).val();
	var html = $("#b2g_txtype_" + value).html();
	if ($(this).html().indexOf("fa-check") == -1) {
		$(this).html("<i class='fa fa-check'></i>");
		$("#b2g_txtype_" + value).html("<strong>" + html + "</strong>");
		$("#b2g_amt_" + value).removeAttr("disabled");
		$("#b2g_paymode_" + value).removeAttr("disabled");
		$("#b2g_cheque_" + value).removeAttr("disabled");
		paymentModeChangeHandler();
	} else {
		$(this).html("<i class='fa fa-times'></i>");
		$("#b2g_txtype_" + value).html(html.replace(/<strong>/g, "").replace(/<\/strong>/g, ""));
		$("#b2g_amt_" + value).attr("disabled", "disabled");
		$("#b2g_paymode_" + value).attr("disabled", "disabled");
		$("#b2g_cheque_" + value).attr("disabled", "disabled");
		updateBankToGroupNetPayable();
	}
}
function paymentModeChangeHandler() {
	var paymode = new Array();
	paymode[0] = $("#g2b_paymode_0 option:selected").text();
	paymode[1] = $("#g2b_paymode_1 option:selected").text();
	paymode[2] = $("#g2b_paymode_2 option:selected").text();
	paymode[3] = $("#g2b_paymode_3 option:selected").text();
	paymode[4] = $("#b2g_paymode_0 option:selected").text();
	paymode[5] = $("#b2g_paymode_1 option:selected").text();
	if (paymode[0] == "CASH" && paymode[1] == "CASH" && paymode[2] == "CASH"
			&& paymode[3] == "CASH" && paymode[4] == "CASH"
			&& paymode[5] == "CASH")
		$("#bankinfo").hide();
	else
		$("#bankinfo").show();
	if (paymode[0] == "CHEQUE")
		$("#g2b_cheque_0").removeAttr("disabled");
	else
		$("#g2b_cheque_0").attr("disabled", "disabled");
	if (paymode[1] == "CHEQUE")
		$("#g2b_cheque_1").removeAttr("disabled");
	else
		$("#g2b_cheque_1").attr("disabled", "disabled");
	if (paymode[2] == "CHEQUE")
		$("#g2b_cheque_2").removeAttr("disabled");
	else
		$("#g2b_cheque_2").attr("disabled", "disabled");
	if (paymode[3] == "CHEQUE")
		$("#g2b_cheque_3").removeAttr("disabled");
	else
		$("#g2b_cheque_3").attr("disabled", "disabled");
	if (paymode[4] == "CHEQUE")
		$("#b2g_cheque_0").removeAttr("disabled");
	else
		$("#b2g_cheque_0").attr("disabled", "disabled");
	if (paymode[5] == "CHEQUE")
		$("#b2g_cheque_1").removeAttr("disabled");
	else
		$("#b2g_cheque_1").attr("disabled", "disabled");

	updateBankToGroupNetPayable();
}
function updateBankToGroupNetPayable() {
	var g2b = 0, b2g = 0, b2gNetPayable = 0;
	for (var k = 0; k < txns2Activate.length; k++) {
		if (txns2Activate[k].slipType == "VOUCHER") {
			if ($("#g2b_check_" + g2b).html().indexOf("fa-check") != -1) {
				// read amount from the page; not from txns2Activate[k].amount
				b2gNetPayable = b2gNetPayable - new Number($("#g2b_amt_" + g2b).val());
			}
			g2b++;
		} else if (txns2Activate[k].slipType == "RECEIPT") {
			if ($("#b2g_check_" + b2g).html().indexOf("fa-check") != -1) {
				// read amount from the page; not from txns2Activate[k].amount
				b2gNetPayable = b2gNetPayable + new Number($("#b2g_amt_" + b2g).val());
			}
			b2g++;
		}
	}
	if(b2gNetPayable >= 0)
		$("#b2g_net_payable").html("Bank To Group Net Payable: <strong>Rs. " + b2gNetPayable + "</strong>");
	else
		$("#b2g_net_payable").html("Group To Bank Net Payable: <strong>Rs. " + ((-1) * b2gNetPayable) + "</strong>");
	$("#b2g_tx_net").show();
}
function showMsgAppActivate(msg) {
	$("#activate_msg").html(msg);
	$("#activate_err_msg").hide();
	$("#activate_msg").show();
}
function showErrorMsgAppActivate(msg) {
	$("#activate_err_msg").html(msg);
	$("#activate_msg").hide();
	$("#activate_err_msg").show();
}
function activateLoanAccount() {
	if (validateActivateAppForm()) {
		$("#loan_acc_activate_btn").attr("disabled", "disabled");
		showMsgAppActivate("Activating account. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedLoanAcc.transactions = updatedTxn2Activate;
		trackedLoanAcc.attachments = null;
		var payload = JSON.stringify(trackedLoanAcc);
		ajaxCall("/gloan/v1/activate", "POST", true, payload, function(data,
				status) {
			showMsgAppActivate("Loan account activated successfully.");
			trackedLoanAcc.approvalStatus = "Active";
		}, function(xhr) {
			$("#loan_acc_activate_btn").removeAttr("disabled");
			showErrorMsgAppActivate("");
			handleErrorAndDisplayMsg(xhr, "activate_err_msg");
		});
	}
}
function activateInvtAccount() {
	if (validateActivateAppForm()) {
		$("#invt_acc_activate_btn").attr("disabled", "disabled");
		showMsgAppActivate("Activating account. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedInvtAcc.transactions = updatedTxn2Activate;
		trackedInvtAcc.attachments = null;
		var payload = JSON.stringify(trackedInvtAcc);
		ajaxCall("/group_invt/v1/activate", "POST", true, payload, function(
				data, status) {
			showMsgAppActivate("Investment account activated successfully.");
			trackedInvtAcc.approvalStatus = "Active";
		}, function(xhr) {
			$("#invt_acc_activate_btn").removeAttr("disabled");
			showErrorMsgAppActivate("");
			handleErrorAndDisplayMsg(xhr, "activate_err_msg");
		});
	}
}
function closeLoanAccount() {
	if (validateActivateAppForm()) {
		$("#loan_acc_closure_btn").attr("disabled", "disabled");
		showMsgAppActivate("Closing account. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedLoanAcc.transactions = updatedTxn2Activate;
		trackedLoanAcc.attachments = null;
		var payload = JSON.stringify(trackedLoanAcc);
		ajaxCall("/gloan/v1/close", "POST", true, payload, function(data,
				status) {
			showMsgAppActivate("Loan account closed successfully.");
		}, function(xhr) {
			$("#loan_acc_closure_btn").removeAttr("disabled");
			showErrorMsgAppActivate("");
			handleErrorAndDisplayMsg(xhr, "activate_err_msg");
		});
	}
}
function closeInvtAccount() {
	if (validateActivateAppForm()) {
		$("#invt_acc_closure_btn").attr("disabled", "disabled");
		showMsgAppActivate("Closing account. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedInvtAcc.transactions = updatedTxn2Activate;
		trackedInvtAcc.attachments = null;
		var payload = JSON.stringify(trackedInvtAcc);
		ajaxCall("/group_invt/v1/close", "POST", true, payload, function(data,
				status) {
			showMsgAppActivate("Investment account closed successfully.");
		}, function(xhr) {
			$("#invt_acc_closure_btn").removeAttr("disabled");
			showErrorMsgAppActivate("");
			handleErrorAndDisplayMsg(xhr, "activate_err_msg");
		});
	}
}
function validateActivateAppForm() {
	if(txns2Activate == null) {
		// Nothing to validate. No transaction require for activation/closure.
		return true;
	}
	var slipNum = $("#slipnum").val();
	if (slipNum == "" || isNaN(slipNum)) {
		showErrorMsgAppActivate("Please provide valid slip number");
		return false;
	}
	var payDate = $("#pay_date").val();
	if (payDate == "") {
		showErrorMsgAppActivate("Please provide valid payment date");
		return false;
	}
	updatedTxn2Activate = new Array();
	var b2g = 0, b2gChequeNum = "", b2gPaymode = "";
	for (var k = 0; k < txns2Activate.length; k++) {
		var groupBankAcNo = 0, memberBankAcNo = 0;
		if (txns2Activate[k].slipType == "RECEIPT") {
			if ($("#b2g_check_" + b2g).html().indexOf("fa-check") != -1) {
				b2gPaymode = $("#b2g_paymode_" + b2g).val();
				if (b2gPaymode == "CHEQUE") {
					b2gChequeNum = $("#b2g_cheque_" + b2g).val();
					if (b2gChequeNum == "" || isNaN(b2gChequeNum)) {
						showErrorMsgAppActivate("Please provide valid cheque number for "
								+ txns2Activate[k].txType + " transaction.");
						return false;
					}
					groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $(
							"#shgbankacc option:selected").text());
					memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos, $(
							"#mbankacc option:selected").text());
				}
				var index = updatedTxn2Activate.length;
				updatedTxn2Activate[index] = txns2Activate[k];
				updatedTxn2Activate[index].slipNo = slipNum;
				updatedTxn2Activate[index].paymentTs = convertDDMMYYYYDate2TS(payDate);
				updatedTxn2Activate[index].amount = $("#b2g_amt_" + b2g).val();
				updatedTxn2Activate[index].paymentMode = b2gPaymode;
				updatedTxn2Activate[index].chequeNo = b2gChequeNum;
				updatedTxn2Activate[index].groupBankAcNo = groupBankAcNo;
				updatedTxn2Activate[index].memberBankAcNo = memberBankAcNo;
				updatedTxn2Activate[index].doneByMemberAcNo = curMemberAcNo;
			}
			b2g++;
		}
	}
	var g2b = 0;
	for (var k = 0; k < txns2Activate.length; k++) {
		var chequeNum = 0, paymode = "", groupBankAcNo = 0, memberBankAcNo = 0;
		if (txns2Activate[k].slipType == "VOUCHER") {
			if ($("#g2b_check_" + g2b).html().indexOf("fa-check") != -1) {
				paymode = $("#g2b_paymode_" + g2b).val();
				if (paymode == "CHEQUE") {
					chequeNum = $("#g2b_cheque_" + g2b).val();
					if (chequeNum == "" || isNaN(chequeNum)) {
						showErrorMsgAppActivate("Please provide valid cheque number for "
								+ txns2Activate[k].txType + " transaction.");
						return false;
					}
					groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $(
							"#shgbankacc option:selected").text());
					memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos, $(
							"#mbankacc option:selected").text());
				} else if (b2gPaymode == "CHEQUE") {
					chequeNum = b2gChequeNum;
					groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $(
							"#shgbankacc option:selected").text());
					memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos, $(
							"#mbankacc option:selected").text());
				}
				var index = updatedTxn2Activate.length;
				updatedTxn2Activate[index] = txns2Activate[k];
				updatedTxn2Activate[index].slipNo = slipNum;
				updatedTxn2Activate[index].paymentTs = convertDDMMYYYYDate2TS(payDate);
				updatedTxn2Activate[index].amount = $("#g2b_amt_" + g2b).val();
				updatedTxn2Activate[index].paymentMode = paymode;
				updatedTxn2Activate[index].chequeNo = chequeNum;
				updatedTxn2Activate[index].groupBankAcNo = groupBankAcNo;
				updatedTxn2Activate[index].memberBankAcNo = memberBankAcNo;
				updatedTxn2Activate[index].doneByMemberAcNo = curMemberAcNo;
			}
			g2b++;
		}
	}
	return true;
}