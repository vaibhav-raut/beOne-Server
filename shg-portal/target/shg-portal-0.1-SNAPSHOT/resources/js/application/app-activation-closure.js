/**
 * 
 */
var trackedMember = null, trackedMemberLoanAc = null, paymentMode = null;
var memberBankAcNos = null, txns2Activate = null, updatedTxn2Activate = new Array();

$(document).ready(function() {
	$("#m_activate_btn").click(activateMember);
	$("#l_disburse_btn").click(disburseLoan);
	$("#m_closure_btn").click(closeMemberAccount);
	$("#l_closure_btn").click(closeLoanAccount);
	$("#m2g_check_0").click(m2gTxnRowSelectionChangehandler);
	$("#m2g_check_1").click(m2gTxnRowSelectionChangehandler);
	$("#m2g_check_2").click(m2gTxnRowSelectionChangehandler);
	$("#m2g_check_3").click(m2gTxnRowSelectionChangehandler);
	$("#g2m_check_0").click(g2mTxnRowSelectionChangehandler);
	$("#g2m_check_1").click(g2mTxnRowSelectionChangehandler);
	$("#m2g_paymode_0").change(paymentModeChangeHandler);
	$("#m2g_paymode_1").change(paymentModeChangeHandler);
	$("#m2g_paymode_2").change(paymentModeChangeHandler);
	$("#m2g_paymode_3").change(paymentModeChangeHandler);
	$("#g2m_paymode_0").change(paymentModeChangeHandler);
	$("#g2m_paymode_1").change(paymentModeChangeHandler);
	$("#m2g_amt_0").change(txnAmtChangeHandler);
	$("#m2g_amt_1").change(txnAmtChangeHandler);
	$("#m2g_amt_2").change(txnAmtChangeHandler);
	$("#m2g_amt_3").change(txnAmtChangeHandler);
	$("#g2m_amt_0").change(txnAmtChangeHandler);
	$("#g2m_amt_1").change(txnAmtChangeHandler);
	$("#go2activate_btn").click(loadMemberActivationTab);
	$("#go2disburse_btn").click(loadFundDisburseTab);
	$("#back2apptrac_btn3").click(back2AppTrackFromAppActivate);
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
				$("#m2g_paymode_0").html(paymodes);
				$("#m2g_paymode_1").html(paymodes);
				$("#m2g_paymode_2").html(paymodes);
				$("#m2g_paymode_3").html(paymodes);
				$("#g2m_paymode_0").html(paymodes);
				$("#g2m_paymode_1").html(paymodes);
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
	$("#activate_msg").html("");
	$("#activate_err_msg").html("");
	$("#app_detail_body").html("");
	$("#activate_btns").hide();
	$("#disburse_btns").hide();
	resetActivateClosureTxnForm();
}
function back2AppTrackFromAppActivate() {
	resetAppActivationForm();
	loadAppTrackingTab();
	resetMemberRegForm();
	resetFullFundApplication();
}
function showActivateButton() {
	$("#disburse_btns").hide();
	$("#m_closure_btns").hide();
	$("#l_closure_btns").hide();
	$("#closure_back_btn").hide();
	$("#activate_btns").show();
	$("#activate_back_btn").show();
}
function showDisburseButton() {
	$("#m_closure_btns").hide();
	$("#l_closure_btns").hide();
	$("#activate_btns").hide();
	$("#closure_back_btn").hide();
	$("#disburse_btns").show();
	$("#activate_back_btn").show();
}
function showMemberCloseButton() {
	$("#disburse_btns").hide();
	$("#l_closure_btns").hide();
	$("#activate_btns").hide();
	$("#activate_back_btn").hide();
	$("#m_closure_btns").show();
	$("#closure_back_btn").show();
}
function showFundCloseButton() {
	$("#disburse_btns").hide();
	$("#m_closure_btns").hide();
	$("#activate_btns").hide();
	$("#activate_back_btn").hide();
	$("#l_closure_btns").show();
	$("#closure_back_btn").show();
}
function resetActivateClosureTxnForm() {
	$("#tx2activate_table").hide();
	$("#slipnum").val("");
	$("#m2g_title").hide();
	$("#g2m_title").hide();
	$("#g2m_tx_net").hide();
	for(var m2g = 0; m2g < 4; m2g++) {
		$("#m2g_tx_" + m2g).hide();
		$("#m2g_check_" + m2g).html("<i class='fa fa-times'></i>");
		$("#m2g_txtype_" + m2g).html("");
		$("#m2g_amt_" + m2g).val("0");
		$("#m2g_paymode_" + m2g).val("CASH");
		$("#m2g_cheque_" + m2g).val("");
	}
	for(var g2m = 0; g2m < 2; g2m++) {
		$("#g2m_tx_" + g2m).hide();
		$("#g2m_tx_d" + g2m).hide();
		$("#g2m_check_" + g2m).html("<i class='fa fa-times'></i>");
		$("#g2m_txtype_" + g2m).html("");
		$("#g2m_amt_" + g2m).val("0");
		$("#g2m_paymode_" + g2m).val("CASH");
		$("#g2m_cheque_" + g2m).val("");
		$("#g2m_desc_" + g2m).html("");
	}	
}
function loadMemberActivationTab() {
	if (appTrackingData.members != null && appTrackingData.members.length != 0) {
		showActivateButton();
		var index = parseInt($(this).data("id"));
		if (!isNaN(index)) {
			trackedMember = appTrackingData.members[index];
		}
		var headers = appTrackingData.displayNames;
		var tablecontent = "<tr><td>" + headers.dateOfEnroll + "</td><td>"
				+ trackedMember.dateOfEnroll
				+ "</td><td width='60'>&nbsp;</td><td>" + headers.memberAcNo
				+ "</td><td>" + getReadableAccNum(trackedMember.memberAcNo)
				+ "</td></tr><tr><td>" + headers.memberName + "</td><td>"
				+ trackedMember.memberName + "</td><td>&nbsp;</td><td>"
				+ headers.mrole + "</td><td>" + trackedMember.mrole
				+ "</td></tr><tr><td>" + headers.plannedMonthlySavingAm
				+ "</td><td>";
		
		if (trackedMember.memberSavingAc != null && trackedMember.memberSavingAc[0] != null) {
			tablecontent += "Rs." + trackedMember.memberSavingAc[0].installmentAm;
		}
		
		tablecontent += "</td><td>&nbsp;</td><td>"
				+ headers.recommendedByMemberName + "</td><td>"
				+ trackedMember.recommendedByMemberName
				+ "</td></tr><tr><td>Primary Mobile</td><td>";
		if (trackedMember.contacts != null && trackedMember.contacts[0] != null) {
			tablecontent += trackedMember.contacts[0].priMobile;
		}
		tablecontent += "</td><td>&nbsp;</td><td></td><td><a class='btn btn-sm yellow' href='#m_reg_content' data-toggle='tab' id='m_detail_"
				+ trackedMember.memberAcNo + "'>More Details</a></td></tr>";

		$("#app_detail_body").html(tablecontent);
		$("#m_detail_" + trackedMember.memberAcNo).click(
				showMemberDetailsForActivation);

		resetActivateClosureTxnForm();
		loadTxs2ActivateApp("member");
	} else {
		$("app_detail_body").html("You do not have any data to display.");
	}
}
function loadFundDisburseTab() {
	if (appTrackingData.memberLoanAcs != null
			&& appTrackingData.memberLoanAcs.length != 0) {
		showDisburseButton();
		var index = parseInt($(this).data("id"));
		if (!isNaN(index))
			trackedMemberLoanAc = appTrackingData.memberLoanAcs[index];
		var headers = appTrackingData.displayNames;
		var tablecontent = "<tr><td>"
				+ headers.memberLoanAcNo
				+ "</td><td>"
				+ trackedMemberLoanAc.memberLoanAcNo
				+ "</td><td width='60'>&nbsp;</td><td>"
				+ headers.memberAcNo
				+ "</td><td>"
				+ getReadableAccNum(trackedMemberLoanAc.memberAcNo)
				+ "</td></tr><tr><td>"
				+ headers.memberName
				+ "</td><td>"
				+ trackedMemberLoanAc.memberName
				+ "</td><td>&nbsp;</td><td>"
				+ headers.loanType
				+ "</td><td>"
				+ trackedMemberLoanAc.loanType
				+ "</td></tr><tr><td>"
				+ headers.principle
				+ "</td><td>Rs."
				+ trackedMemberLoanAc.principle
				+ "</td><td>&nbsp;</td><td>Rate of Interest</td><td>"
				+ trackedMemberLoanAc.rateOfInterest
				+ "</td></tr><tr><td>"
				+ headers.installment
				+ "</td><td>"
				+ trackedMemberLoanAc.installment
				+ "</td><td>&nbsp;</td><td>No. of EMIs</td><td>"
				+ trackedMemberLoanAc.expNoOfInst
				+ "</td></tr><tr><td>No. of Start Up EMIs</td><td>"
				+ trackedMemberLoanAc.startupNoOfInst
				+ "</td><td>&nbsp;</td><td>Start Date</td><td>"
				+ trackedMemberLoanAc.instStartDate
				+ "</td></tr><tr><td>Expected Completion Date</td><td>"
				+ trackedMemberLoanAc.expCompletionDate
				+ "</td><td>&nbsp;</td><td></td>"
				+ "<td><a class='btn btn-sm yellow' href='#m_fund_content' data-toggle='tab' id='fund_detail_"
				+ trackedMemberLoanAc.memberLoanAcNo
				+ "'>More Details</a></td></tr>";

		$("#app_detail_body").html(tablecontent);
		$("#fund_detail_" + trackedMemberLoanAc.memberLoanAcNo).click(
				showFundDetailsForActivation);

		resetActivateClosureTxnForm();
		loadTxs2ActivateApp("loan");
	} else {
		$("#app_detail_body").html("You do not have any data to display.");
	}
}
function loadTxs2ActivateApp(appName) {
	var url = "", payload = "";
	if (appName == "member" && trackedMember != null && trackedMember.approvalStatus != "Active") {
		url = "/member/v1/txs_to_activate";
		trackedMember.attachments = null;
		payload = JSON.stringify(trackedMember);
		trackedMember.attachments = memberAttachments;
		memberBankAcNos = trackedMember.memberBankAcNos;
	} else if (appName == "loan" && trackedMemberLoanAc != null && trackedMemberLoanAc.accountStatus != "Active") {
		url = "/mloan/v1/txs_to_activate";
		trackedMemberLoanAc.attachments = null;
		payload = JSON.stringify(trackedMemberLoanAc);
		trackedMemberLoanAc.attachments = mfundAttachments;
		memberBankAcNos = trackedMemberLoanAc.memberBankAcNos;
	}
	if (url != "") {
		$("#m_activate_btn").attr("disabled", "disabled");
		$("#l_disburse_btn").attr("disabled", "disabled");
		$("#tx2activate_msg").html("<div align='center'>Please wait... <img src='resources/img/ajax-loader.gif' alt=''/></div><br>");
		var mAccContent = "<option></option>";
		if (memberBankAcNos != null) {
			for (var i = 0; i < memberBankAcNos.length; i++) {
				mAccContent += "<option>" + memberBankAcNos[i].displayAccount + "</option>";
			}
			$("#mbankacc").html(gAccContent);
		}
		ajaxCall(
				url,
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != null && data != "" && data.transactions != null
							&& data.transactions.length != 0) {
						txns2Activate = data.transactions;
						$("#tx2activate_msg").html("");
						$("#pay_date").val(convertTS2DDMMYYYYDate(currentServerTime));
						$("#tx2activate_table").show();

						var m2g = 0, g2m = 0;
						for (var k = 0; k < data.transactions.length; k++) {
							if (data.transactions[k].slipType == "RECEIPT") {
								$("#m2g_title").show();
								$("#m2g_check_" + m2g).html("<i class='fa fa-check'></i>");
								$("#m2g_amt_" + m2g).removeAttr("disabled");
								$("#m2g_paymode_" + m2g).removeAttr("disabled");
								$("#m2g_txtype_" + m2g).html("<strong>" + data.transactions[k].txType + "</strong>");
								$("#m2g_amt_" + m2g).val(data.transactions[k].amount);
								$("#m2g_paymode_" + m2g).val(data.transactions[k].paymentMode);
								$("#m2g_tx_" + m2g).show();
								m2g++;
							} else if (data.transactions[k].slipType == "VOUCHER") {
								$("#g2m_title").show();
								$("#g2m_check_" + g2m).html("<i class='fa fa-check'></i>");
								$("#g2m_amt_" + g2m).removeAttr("disabled");
								$("#g2m_paymode_" + g2m).removeAttr("disabled");
								$("#g2m_txtype_" + g2m).html("<strong>" + data.transactions[k].txType + "</strong>");
								$("#g2m_amt_" + g2m).val(data.transactions[k].amount);
								$("#g2m_desc_" + g2m).html(data.transactions[k].description);
								$("#g2m_paymode_" + g2m).val(data.transactions[k].paymentMode);
								$("#g2m_tx_" + g2m).show();
								$("#g2m_tx_d" + g2m).show();
								g2m++;
							}
						}
						paymentModeChangeHandler();
					} else {
						$("#tx2activate_msg").html("No transactions found.");
					}
					$("#m_activate_btn").removeAttr("disabled");
					$("#l_disburse_btn").removeAttr("disabled");
				},
				function(xhr) {
					handleErrorAndDisplayMsg(xhr, "tx2activate_msg");
				});
	}
}
function loadMemberClosureTab() {
	if (trackedMember != null) {
		if (trackedMember.memberLoanAc.length != 0) {
			showErrorMsgAppActivate("Please close member loan account first.");
		} else {
			showMsgAppActivate("");
		}
		showMemberCloseButton();
		var headers = appTrackingData.displayNames;
		var tablecontent = "<tr><td>" + headers.dateOfEnroll + "</td><td>"
				+ trackedMember.dateOfEnroll
				+ "</td><td width='60'>&nbsp;</td><td>" + headers.memberAcNo
				+ "</td><td>" + getReadableAccNum(trackedMember.memberAcNo)
				+ "</td></tr><tr><td>" + headers.memberName + "</td><td>"
				+ trackedMember.memberName + "</td><td>&nbsp;</td><td>"
				+ headers.mrole + "</td><td>" + trackedMember.mrole
				+ "</td></tr><tr><td>" + headers.plannedMonthlySavingAm
				+ "</td><td>";
		if (trackedMember.account != null)
			tablecontent += "Rs."
					+ trackedMember.account.plannedMonthlySavingAm;
		tablecontent += "</td><td>&nbsp;</td><td>"
				+ headers.recommendedByMemberName + "</td><td>"
				+ trackedMember.recommendedByMemberName
				+ "</td></tr><tr><td>Primary Mobile</td><td>";
		if (trackedMember.contacts != null && trackedMember.contacts[0] != null)
			tablecontent += trackedMember.contacts[0].priMobile;
		tablecontent += "</td><td>&nbsp;</td><td></td><td><a class='btn btn-sm yellow' href='#m_reg_content' data-toggle='tab' id='m_close_detail_"
				+ trackedMember.memberAcNo + "'>More Details</a></td></tr>";

		$("#app_detail_body").html(tablecontent);
		$("#m_close_detail_" + trackedMember.memberAcNo).click(
				showMemberDetailsForClosure);

		resetActivateClosureTxnForm();
		loadTxs2CloseApp("member");
	} else {
		$("app_detail_body").html("You do not have any data to display.");
	}
}
function loadFundClosureTab() {
	if (trackedMember != null && trackedMember.memberLoanAc.length != 0) {
		showMsgAppActivate("");
		showFundCloseButton();
		var index = parseInt($(this).data("id"));
		trackedMemberLoanAc = trackedMember.memberLoanAc[index];
		var headers = appTrackingData.displayNames;
		var tablecontent = "<tr><td>"
				+ headers.memberLoanAcNo
				+ "</td><td>"
				+ trackedMemberLoanAc.memberLoanAcNo
				+ "</td><td width='60'>&nbsp;</td><td>"
				+ headers.memberAcNo
				+ "</td><td>"
				+ getReadableAccNum(trackedMemberLoanAc.memberAcNo)
				+ "</td></tr><tr><td>"
				+ headers.memberName
				+ "</td><td>"
				+ trackedMemberLoanAc.memberName
				+ "</td><td>&nbsp;</td><td>"
				+ headers.loanType
				+ "</td><td>"
				+ trackedMemberLoanAc.loanType
				+ "</td></tr><tr><td>"
				+ headers.principle
				+ "</td><td>Rs."
				+ trackedMemberLoanAc.principle
				+ "</td><td>&nbsp;</td><td>Rate of Interest</td><td>"
				+ trackedMemberLoanAc.rateOfInterest
				+ "</td></tr><tr><td>"
				+ headers.installment
				+ "</td><td>"
				+ trackedMemberLoanAc.installment
				+ "</td><td>&nbsp;</td><td>No. of EMIs</td><td>"
				+ trackedMemberLoanAc.expNoOfInst
				+ "</td></tr><tr><td>No. of Start Up EMIs</td><td>"
				+ trackedMemberLoanAc.startupNoOfInst
				+ "</td><td>&nbsp;</td><td>Start Date</td><td>"
				+ trackedMemberLoanAc.instStartDate
				+ "</td></tr><tr><td>Expected Completion Date</td><td>"
				+ trackedMemberLoanAc.expCompletionDate
				+ "</td><td>&nbsp;</td><td></td>"
				+ "<td><a class='btn btn-sm yellow' href='#m_fund_content' data-toggle='tab' data-id='" + index + "' id='fund_close_detail_"
				+ trackedMemberLoanAc.memberLoanAcNo
				+ "'>More Details</a></td></tr>";

		$("#app_detail_body").html(tablecontent);
		$("#fund_close_detail_" + trackedMemberLoanAc.memberLoanAcNo).click(
				showFundDetailsForClosure);

		resetActivateClosureTxnForm();
		loadTxs2CloseApp("loan");
	} else {
		$("#app_detail_body").html("You do not have any data to display.");
	}
}
function loadTxs2CloseApp(appName) {
	var url = "", payload = "";
	if (appName == "member" && trackedMember != null) {
		url = "/member/v1/txs_to_close";
		trackedMember.attachments = null;
		payload = JSON.stringify(trackedMember);
		trackedMember.attachments = memberAttachments;
		memberBankAcNos = trackedMember.memberBankAcNos;
	} else if (appName == "loan" && trackedMemberLoanAc != null) {
		url = "/mloan/v1/txs_to_close";
		trackedMemberLoanAc.attachments = null;
		payload = JSON.stringify(trackedMemberLoanAc);
		trackedMemberLoanAc.attachments = mfundAttachments;
		memberBankAcNos = trackedMemberLoanAc.memberBankAcNos;
	}
	if (url != "") {
		$("#m_closure_btn").attr("disabled", "disabled");
		$("#l_closure_btn").attr("disabled", "disabled");
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
		ajaxCall(
				url,
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != null && data != "" && data.transactions != null
							&& data.transactions.length != 0) {
						txns2Activate = data.transactions;
						$("#tx2activate_msg").html("");
						$("#pay_date").val(
								convertTS2DDMMYYYYDate(currentServerTime));
						$("#tx2activate_table").show();

						var m2g = 0, g2m = 0;
						for (var k = 0; k < data.transactions.length; k++) {
							if (data.transactions[k].slipType == "RECEIPT") {
								$("#m2g_title").show();
								$("#m2g_check_" + m2g).html("<i class='fa fa-check'></i>");
								$("#m2g_amt_" + m2g).removeAttr("disabled");
								$("#m2g_paymode_" + m2g).removeAttr("disabled");
								$("#m2g_txtype_" + m2g).html("<strong>" + data.transactions[k].txType + "</strong>");
								$("#m2g_amt_" + m2g).val(data.transactions[k].amount);
								$("#m2g_paymode_" + m2g).val(data.transactions[k].paymentMode);
								$("#m2g_tx_" + m2g).show();
								m2g++;
							} else if (data.transactions[k].slipType == "VOUCHER") {
								$("#g2m_title").show();
								$("#g2m_check_" + g2m).html("<i class='fa fa-check'></i>");
								$("#g2m_amt_" + g2m).removeAttr("disabled");
								$("#g2m_paymode_" + g2m).removeAttr("disabled");
								$("#g2m_txtype_" + g2m).html("<strong>" + data.transactions[k].txType + "</strong>");
								$("#g2m_amt_" + g2m).val(data.transactions[k].amount);
								$("#g2m_desc_" + g2m).html(data.transactions[k].description);
								$("#g2m_paymode_" + g2m).val(data.transactions[k].paymentMode);
								$("#g2m_tx_" + g2m).show();
								$("#g2m_tx_d" + g2m).show();
								g2m++;
							}
						}
						paymentModeChangeHandler();
					} else {
						$("#tx2activate_msg").html("No transactions found.");
					}
					$("#m_closure_btn").removeAttr("disabled");
					$("#l_closure_btn").removeAttr("disabled");
				},
				function(xhr) {
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
	updateGroupToMemberNetPayable();
}
function m2gTxnRowSelectionChangehandler() {
	var value = $(this).val();
	var html = $("#m2g_txtype_" + value).html();
	if ($(this).html().indexOf("fa-check") == -1) {
		$(this).html("<i class='fa fa-check'></i>");
		$("#m2g_txtype_" + value).html("<strong>" + html + "</strong>");
		$("#m2g_amt_" + value).removeAttr("disabled");
		$("#m2g_paymode_" + value).removeAttr("disabled");
		$("#m2g_cheque_" + value).removeAttr("disabled");
		paymentModeChangeHandler();
	} else {
		$(this).html("<i class='fa fa-times'></i>");
		$("#m2g_txtype_" + value).html(html.replace(/<strong>/g, "").replace(/<\/strong>/g, ""));
		$("#m2g_amt_" + value).attr("disabled", "disabled");
		$("#m2g_paymode_" + value).attr("disabled", "disabled");
		$("#m2g_cheque_" + value).attr("disabled", "disabled");
		updateGroupToMemberNetPayable();
	}
}
function g2mTxnRowSelectionChangehandler() {
	var value = $(this).val();
	var html = $("#g2m_txtype_" + value).html();
	if ($(this).html().indexOf("fa-check") == -1) {
		$(this).html("<i class='fa fa-check'></i>");
		$("#g2m_txtype_" + value).html("<strong>" + html + "</strong>");
		$("#g2m_amt_" + value).removeAttr("disabled");
		$("#g2m_paymode_" + value).removeAttr("disabled");
		$("#g2m_cheque_" + value).removeAttr("disabled");
		paymentModeChangeHandler();
	} else {
		$(this).html("<i class='fa fa-times'></i>");
		$("#g2m_txtype_" + value).html(html.replace(/<strong>/g, "").replace(/<\/strong>/g, ""));
		$("#g2m_amt_" + value).attr("disabled", "disabled");
		$("#g2m_paymode_" + value).attr("disabled", "disabled");
		$("#g2m_cheque_" + value).attr("disabled", "disabled");
		updateGroupToMemberNetPayable();
	}
}
function paymentModeChangeHandler() {
	var paymode = new Array();
	paymode[0] = $("#m2g_paymode_0 option:selected").text();
	paymode[1] = $("#m2g_paymode_1 option:selected").text();
	paymode[2] = $("#m2g_paymode_2 option:selected").text();
	paymode[3] = $("#m2g_paymode_3 option:selected").text();
	paymode[4] = $("#g2m_paymode_0 option:selected").text();
	paymode[5] = $("#g2m_paymode_1 option:selected").text();
	if (paymode[0] == "CASH" && paymode[1] == "CASH" && paymode[2] == "CASH"
			&& paymode[3] == "CASH" && paymode[4] == "CASH"
			&& paymode[5] == "CASH")
		$("#bankinfo").hide();
	else
		$("#bankinfo").show();
	if (paymode[0] == "CHEQUE" && $("#m2g_check_0").html().indexOf("fa-check") != -1)
		$("#m2g_cheque_0").removeAttr("disabled");
	else
		$("#m2g_cheque_0").attr("disabled", "disabled");
	if (paymode[1] == "CHEQUE" && $("#m2g_check_1").html().indexOf("fa-check") != -1)
		$("#m2g_cheque_1").removeAttr("disabled");
	else
		$("#m2g_cheque_1").attr("disabled", "disabled");
	if (paymode[2] == "CHEQUE" && $("#m2g_check_2").html().indexOf("fa-check") != -1)
		$("#m2g_cheque_2").removeAttr("disabled");
	else
		$("#m2g_cheque_2").attr("disabled", "disabled");
	if (paymode[3] == "CHEQUE" && $("#m2g_check_3").html().indexOf("fa-check") != -1)
		$("#m2g_cheque_3").removeAttr("disabled");
	else
		$("#m2g_cheque_3").attr("disabled", "disabled");
	if (paymode[4] == "CHEQUE" && $("#g2m_check_0").html().indexOf("fa-check") != -1)
		$("#g2m_cheque_0").removeAttr("disabled");
	else
		$("#g2m_cheque_0").attr("disabled", "disabled");
	if (paymode[5] == "CHEQUE" && $("#g2m_check_1").html().indexOf("fa-check") != -1)
		$("#g2m_cheque_1").removeAttr("disabled");
	else
		$("#g2m_cheque_1").attr("disabled", "disabled");
	
	updateGroupToMemberNetPayable();
}
function updateGroupToMemberNetPayable() {
	var m2g = 0, g2m = 0, g2mNetPayable = 0;
	for (var k = 0; k < txns2Activate.length; k++) {
		if (txns2Activate[k].slipType == "RECEIPT") {
			if ($("#m2g_check_" + m2g).html().indexOf("fa-check") != -1) {
				// read amount from the page; not from txns2Activate[k].amount
				g2mNetPayable = g2mNetPayable - new Number($("#m2g_amt_" + m2g).val());
			}
			m2g++;
		} else if (txns2Activate[k].slipType == "VOUCHER") {
			if ($("#g2m_check_" + g2m).html().indexOf("fa-check") != -1) {
				// read amount from the page; not from txns2Activate[k].amount
				g2mNetPayable = g2mNetPayable + new Number($("#g2m_amt_" + g2m).val());
			}
			g2m++;
		}
	}
	if(g2mNetPayable >= 0)
		$("#g2m_net_payable").html("Group To Member Net Payable: <strong>Rs. " + g2mNetPayable + "</strong>");
	else
		$("#g2m_net_payable").html("Member To Group Net Payable: <strong>Rs. " + (-1) * g2mNetPayable + "</strong>");
	$("#g2m_tx_net").show();
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
function activateMember() {
	if (validateActivateAppForm() && confirm("Activate member account?") === true) {
		$("#m_activate_btn").attr("disabled", "disabled");
		showMsgAppActivate("Activating member. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedMember.transactions = updatedTxn2Activate;
		trackedMember.attachments = null;
		var payload = JSON.stringify(trackedMember);
		trackedMember.attachments = memberAttachments;
		ajaxCall(
				"/member/v1/activate",
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != "") {
						showMsgAppActivate("Member account activated successfully.");
						trackedMember.approvalStatus = "Active";
					} else {
						showErrorMsgAppActivate("Activate member: No data received from the server.");
						$("#m_activate_btn").removeAttr("disabled");
					}
				}, function(xhr) {
					$("#m_activate_btn").removeAttr("disabled");
					showErrorMsgAppActivate("");
					handleErrorAndDisplayMsg(xhr, "activate_err_msg");
				});
	}
}
function disburseLoan() {
	if (validateActivateAppForm() && confirm("Disburse loan?") === true) {
		$("#l_disburse_btn").attr("disabled", "disabled");
		showMsgAppActivate("Disbursing loan. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedMemberLoanAc.transactions = updatedTxn2Activate;
		trackedMemberLoanAc.attachments = null;
		var payload = JSON.stringify(trackedMemberLoanAc);
		trackedMemberLoanAc.attachments = mfundAttachments;
		ajaxCall(
				"/mloan/v1/activate",
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != "") {
						showMsgAppActivate("Loan disbursed successfully.");
						trackedMemberLoanAc.accountStatus = "Active";
					} else {
						showErrorMsgAppActivate("Disburse loan: No data received from the server.");
						$("#l_disburse_btn").removeAttr("disabled");
					}
				}, function(xhr) {
					$("#l_disburse_btn").removeAttr("disabled");
					showErrorMsgAppActivate("");
					handleErrorAndDisplayMsg(xhr, "activate_err_msg");
				});
	}
}
function closeMemberAccount() {
	if (trackedMember.memberLoanAc.length != 0) {
		showErrorMsgAppActivate("Please close member loan account first.");
	} else if (validateActivateAppForm() && confirm("Close member account?") === true) {
		$("#m_closure_btn").attr("disabled", "disabled");
		showMsgAppActivate("Closing member account. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedMember.transactions = updatedTxn2Activate;
		trackedMember.attachments = null;
		var payload = JSON.stringify(trackedMember);
		trackedMember.attachments = memberAttachments;
		ajaxCall(
				"/member/v1/close",
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != "") {
						showMsgAppActivate("Member account closed successfully.");
						loadAndGetMemberClosureData();
					} else {
						showErrorMsgAppActivate("Closing member account: No data received from the server.");
						$("#m_closure_btn").removeAttr("disabled");
					}
				},
				function(xhr) {
					$("#m_closure_btn").removeAttr("disabled");
					showErrorMsgAppActivate("");
					handleErrorAndDisplayMsg(xhr, "activate_err_msg");
				});
	}
}
function closeLoanAccount() {
	if (validateActivateAppForm() && confirm("Close loan account?") === true) {
		$("#l_closure_btn").attr("disabled", "disabled");
		showMsgAppActivate("Closing loan account. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		trackedMemberLoanAc.transactions = updatedTxn2Activate;
		trackedMemberLoanAc.attachments = null;
		var payload = JSON.stringify(trackedMemberLoanAc);
		trackedMemberLoanAc.attachments = mfundAttachments;
		ajaxCall(
				"/mloan/v1/close",
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != "") {
						showMsgAppActivate("Loan account closed successfully.");
						loadAndGetMemberClosureData();
					} else {
						showErrorMsgAppActivate("Closing loan account: No data received from the server.");
						$("#l_closure_btn").removeAttr("disabled");
					}
				},
				function(xhr) {
					$("#l_closure_btn").removeAttr("disabled");
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
	var m2g = 0, g2m = 0, g2mChequeNum = "", g2mPaymode = "";
	for (var k = 0; k < txns2Activate.length; k++) {
		var chequeNum = 0, paymode = "", groupBankAcNo = 0, memberBankAcNo = 0;
		if (txns2Activate[k].slipType == "RECEIPT") {
			if ($("#m2g_check_" + m2g).html().indexOf("fa-check") != -1) {
				paymode = $("#m2g_paymode_" + m2g).val();
				if (paymode == "CHEQUE") {
					chequeNum = $("#m2g_cheque_" + m2g).val();
					if (chequeNum == "" || isNaN(chequeNum)) {
						showErrorMsgAppActivate("Please provide valid cheque number for "
								+ txns2Activate[k].txType + " transaction.");
						return false;
					}
					groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $("#shgbankacc option:selected").text());
					memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos, $("#mbankacc option:selected").text());
				} else if (g2mPaymode == "CHEQUE") {
					chequeNum = g2mChequeNum;
					groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $("#shgbankacc option:selected").text());
					memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos, $("#mbankacc option:selected").text());
				}
				var index = updatedTxn2Activate.length;
				updatedTxn2Activate[index] = txns2Activate[k];
				updatedTxn2Activate[index].slipNo = slipNum;
				updatedTxn2Activate[index].paymentTs = convertDDMMYYYYDate2TS(payDate);
				updatedTxn2Activate[index].amount = $("#m2g_amt_" + m2g).val();
				updatedTxn2Activate[index].paymentMode = paymode;
				updatedTxn2Activate[index].chequeNo = chequeNum;
				updatedTxn2Activate[index].groupBankAcNo = groupBankAcNo;
				updatedTxn2Activate[index].memberBankAcNo = memberBankAcNo;
				updatedTxn2Activate[index].doneByMemberAcNo = curMemberAcNo;
				updatedTxn2Activate[index].memberBankAcNos = null;
			}
			m2g++;
		}
	}
	for (var k = 0; k < txns2Activate.length; k++) {
		var groupBankAcNo = 0, memberBankAcNo = 0;
		if (txns2Activate[k].slipType == "VOUCHER") {
			if ($("#g2m_check_" + g2m).html().indexOf("fa-check") != -1) {
				g2mPaymode = $("#g2m_paymode_" + g2m).val();
				if (g2mPaymode == "CHEQUE") {
					g2mChequeNum = $("#g2m_cheque_" + g2m).val();
					if (g2mChequeNum == "" || isNaN(g2mChequeNum)) {
						showErrorMsgAppActivate("Please provide valid cheque number for "
								+ txns2Activate[k].txType + " transaction.");
						return false;
					}
					groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $("#shgbankacc option:selected").text());
					memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos, $("#mbankacc option:selected").text());
				}
				var index = updatedTxn2Activate.length;
				updatedTxn2Activate[index] = txns2Activate[k];
				updatedTxn2Activate[index].slipNo = slipNum;
				updatedTxn2Activate[index].paymentTs = convertDDMMYYYYDate2TS(payDate);
				updatedTxn2Activate[index].amount = $("#g2m_amt_" + g2m).val();
				updatedTxn2Activate[index].paymentMode = g2mPaymode;
				updatedTxn2Activate[index].chequeNo = g2mChequeNum;
				updatedTxn2Activate[index].groupBankAcNo = groupBankAcNo;
				updatedTxn2Activate[index].memberBankAcNo = memberBankAcNo;
				updatedTxn2Activate[index].doneByMemberAcNo = curMemberAcNo;
				updatedTxn2Activate[index].memberBankAcNos = null;
			}
			g2m++;
		}
	}
	return true;
}