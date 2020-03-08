/**
 * 
 */
var txwith = "", memberBankAcNos, groupInvtAccList = new Array(), groupLoanAccList = new Array(), curTxnId = null;
var searchedMembersATxn, selectedMemberATxn = null, memberAccDataATxn = null, txnAttachments = new Array();

$(document).ready(function() {
	loadAddTransactionTab();
	$("#txn_amt").change(numberFieldChangeHandler);
	$("#txn_slipnum").change(numberFieldChangeHandler);
	$("#txn_paymode").change(paymentModeChangeHandler);
	$("#add_txn_tab").click(loadAddTransactionTab);
	$("#addTxBtn").click(addTransaction);
	$("#m_name_search").click(searchMemberUsingNameAddTxn);
	$("#txwith_member").change(txwithRadioChangeHandler);
	$("#txwith_group").change(txwithRadioChangeHandler);
	$("#txwith_bank").change(txwithRadioChangeHandler);
	$("#txn_category").change(txCategoryChangeHandler);
	$("#txn_type").change(txTypeChangeHandler);
	$("#resetAddTxBtn").click(resetAddTransactionForm);
	$("#txn_upload_file_btn").click(uploadTxnAttachment);
});
function loadAddTransactionTab() {
	showMsgAddTxn("");
	$("#txn_paydate").val(convertTS2DDMMYYYYDate(currentServerTime));
	// load group loan and investment accounts
	ajaxCall("/group/v1/group_all_acs/" + $("#lang option:selected").text()
			+ "/" + groupAcNo + "/Active", "GET", true, "", function(data,
			status) {
		groupLoanAccList = data.groupLoanAcs;
		groupInvtAccList = data.groupInvtAcs;
	});
}
function addTransaction() {
	if (txwith == "") {
		showErrorMsgAddTxn("Please select transaction with.");
		return;
	}
	var txCategory = $("#txn_category option:selected").text();
	if (txCategory == "") {
		showErrorMsgAddTxn("Please select transaction category.");
		$("#txn_category").focus();
		return;
	}
	var txType = $("#txn_type option:selected").text();
	if (txType == "") {
		showErrorMsgAddTxn("Please select transaction type.");
		$("#txn_type").focus();
		return;
	}
	var memberAcNo = 0, memberName = "";
	if ((txwith == "MEMBER" || (txwith == "GROUP" && txCategory != "PROJECT LOAN"))
			&& selectedMemberATxn == null) {
		showErrorMsgAddTxn("Please search member.");
		return;
	} else if (selectedMemberATxn != null) {
		memberAcNo = selectedMemberATxn.memberAcNo;
		memberName = selectedMemberATxn.memberName;
	}
	var account = $("#accounts option:selected").text();
	if ((txCategory == "MEMBER SAVING" || txCategory == "MEMBER LOAN"
			|| txCategory == "PROJECT LOAN" || txCategory == "BANK LOAN"
			|| txCategory == "INVESTMENT" || (txwith == "BANK" && txCategory == "EXPENSE"))
			&& account == "") {
		showErrorMsgAddTxn("Please select Saving/Loan/Investment account.");
		$("#accounts").focus();
		return;
	}
	var amount = $("#txn_amt").val();
	if (amount == "") {
		showErrorMsgAddTxn("Please provide amount.");
		$("#txn_amt").focus();
		return;
	}
	var paymentMode = $("#txn_paymode option:selected").text();
	if (paymentMode == "") {
		showErrorMsgAddTxn("Please select payment mode.");
		$("#txn_paymode").focus();
		return;
	}
	var chequeNum = "";
	if (paymentMode == "CHEQUE") {
		chequeNum = $("#txn_chequenum").val();
		if (chequeNum == "") {
			showErrorMsgAddTxn("Please enter cheque number.");
			$("#txn_chequenum").focus();
			return;
		}
	}
	var slipNo = $("#txn_slipnum").val();
	if (slipNo == "") {
		showErrorMsgAddTxn("Please provide slip no.");
		$("#txn_slipnum").focus();
		return;
	}
	var date = $("#txn_paydate").val();
	if (date == "") {
		showErrorMsgAddTxn("Please select payment date.");
		$("#txn_paydate").focus();
		return;
	}
	var isBankType = isBankTypePaymentMode(paymentMode);
	var gBankAcName = $("#txn_g_bank_acc option:selected").text();
	if (isBankType && gBankAcName == "") {
		showErrorMsgAddTxn("Please select SHG Bank Ac No.");
		$("#txn_g_bank_acc").focus();
		return;
	}
	$("#addTxBtn").attr("disabled", "disabled");
	showMsgAddTxn("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");

	var savingAcNo = 0, memberLoanAcNo = 0, groupBankAcNo = null, groupInvtAc = null, groupLoanAc = null;
	if (txwith == "MEMBER") {
		if (txCategory == "MEMBER SAVING")
			savingAcNo = account;
		else if (txCategory == "MEMBER LOAN")
			memberLoanAcNo = account;
	} else if (txwith == "GROUP" && txCategory == "PROJECT LOAN") {
		for (var i = 0; i < groupInvtAccList.length; i++) {
			if (account == groupInvtAccList[i].investmentAcName) {
				groupInvtAc = groupInvtAccList[i];
				break;
			}
		}
	} else if (txwith == "BANK" && txCategory == "BANK LOAN") {
		for (var i = 0; i < groupLoanAccList.length; i++) {
			if (account == groupLoanAccList[i].loanAcName) {
				groupLoanAc = groupLoanAccList[i];
				break;
			}
		}
	} else if (txwith == "BANK" && txCategory == "INVESTMENT") {
		if (txType == "Bank Interest Earned") {
			for (var i = 0; i < groupBankAcNos.length; i++) {
				if (account == groupBankAcNos[i].displayAccount) {
					groupBankAcNo = groupBankAcNos[i];
					break;
				}
			}
		} else {
			for (var i = 0; i < groupInvtAccList.length; i++) {
				if (account == groupInvtAccList[i].investmentAcName) {
					groupInvtAc = groupInvtAccList[i];
					break;
				}
			}
		}
	} else if (txwith == "BANK" && txCategory == "EXPENSE") {
		var found = false;
		for (var i = 0; i < groupLoanAccList.length; i++) {
			if (account == groupLoanAccList[i].loanAcName) {
				groupLoanAc = groupLoanAccList[i];
				found = true;
				break;
			}
		}
		if (!found) {
			for (var i = 0; i < groupInvtAccList.length; i++) {
				if (account == groupInvtAccList[i].investmentAcName) {
					groupInvtAc = groupInvtAccList[i];
					break;
				}
			}
		}
	}
	var accStrForPayload = ",\"savingAcNo\":" + savingAcNo
			+ ",\"memberLoanAcNo\":" + memberLoanAcNo;
	if (groupBankAcNo != null) {
		accStrForPayload += ",\"externalGroupAcNo\":"
				+ groupBankAcNo.bankGroupAcNo;
	} else if (groupInvtAc != null) {
		accStrForPayload += ",\"groupInvtAcNo\":" + groupInvtAc.groupInvtAcNo
				+ ",\"externalGroupAcNo\":" + groupInvtAc.bankGroupAcNo
				+ ",\"externalGroupBankAcNo\":" + groupInvtAc.invtBankAcNo;
	} else if (groupLoanAc != null) {
		accStrForPayload += ",\"groupLoanAcNo\":" + groupLoanAc.groupLoanAcNo
				+ ",\"externalGroupAcNo\":" + groupLoanAc.bankGroupAcNo
				+ ",\"externalGroupBankAcNo\":" + groupLoanAc.loanBankAcNo;
	}
	if (isBankType) {
		var groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos,
				gBankAcName);
		accStrForPayload += ",\"groupBankAcNo\": " + groupBankAcNo;
		var mBankAcName = $("#txn_m_bank_acc option:selected").text();
		if (mBankAcName != "") {
			var memberBankAcNo = getBankAccFromDisplayName(memberBankAcNos,
					mBankAcName);
			accStrForPayload += ",\"memberBankAcNo\": " + memberBankAcNo;
		}
	}
	var dateSplited = date.split("/");
	var paymentTs = (new Date(dateSplited[2], dateSplited[1] - 1,
			dateSplited[0])).getTime();
	var payload = "{\"groupAcNo\": " + groupAcNo + ",\"doneByMemberAcNo\": "
			+ curMemberAcNo + ",\"memberAcNo\": " + memberAcNo
			+ ",\"memberName\": \"" + memberName + "\",\"txType\": \"" + txType
			+ "\",\"amount\": " + amount + ",\"paymentMode\": \"" + paymentMode
			+ "\",\"chequeNo\":\"" + chequeNum + "\",\"slipNo\": " + slipNo
			+ ",\"paymentTs\": " + paymentTs + accStrForPayload
			+ ",\"description\": \"" + $("#txn_desc").val().replace("\n", " ")
			+ "\"}";
	ajaxCall("/transaction/v1/transaction", "PUT", false, payload, function(
			data, status) {
		curTxnId = data.txId;
		showMsgAddTxn("Add Transaction Succeeded. Transaction Id - <b>" + curTxnId 
				+ "</b><br>Please add required attachment for this transaction.");
		disableAddTransactionForm();
		$("#txn_attachment_div").show();
	}, function(xhr) {
		$("#addTxBtn").removeAttr("disabled");
		showErrorMsgAddTxn("");
		handleErrorAndDisplayMsg(xhr, "add_txn_err_msg");
	});
}
function disableAddTransactionForm() {
	$("#addTxBtn").attr("disabled", "disabled");
	$("#txwith_member").attr("disabled", "disabled");
	$("#txwith_group").attr("disabled", "disabled");
	$("#txwith_bank").attr("disabled", "disabled");
	$("#txn_category").attr("disabled", "disabled");
	$("#txn_type").attr("disabled", "disabled");
	$("#m_name_search").attr("disabled", "disabled");
	$("#m_name").attr("disabled", "disabled");
	$("#accounts").attr("disabled", "disabled");
	$("#txn_amt").attr("disabled", "disabled");
	$("#txn_paymode").attr("disabled", "disabled");
	$("#txn_chequenum").attr("disabled", "disabled");
	$("#txn_slipnum").attr("disabled", "disabled");
	$("#txn_paydate").attr("disabled", "disabled");
	$("#txn_m_bank_acc").attr("disabled", "disabled");
	$("#txn_g_bank_acc").attr("disabled", "disabled");
}
function resetAddTransactionForm() {
	curTxnId = null;
	txwith = "";
	selectedMemberATxn = null;
	memberAccDataATxn = null;
	txnAttachments = new Array();
	showMsgAddTxn("");
	$("#m_acc_num").html("");
	$("#m_name").val("");
	$("#txn_amt").val("");
	$("#txn_chequenum").val("");
	$("#txn_slipnum").val("");
	$("#txn_paydate").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#txn_desc").val("");
	$("#txn_attachment_div").hide();
	$("#addTxBtn").removeAttr("disabled");
	$("#txwith_member").removeAttr("disabled");
	$("#txwith_group").removeAttr("disabled");
	$("#txwith_bank").removeAttr("disabled");
	$("#txn_category").removeAttr("disabled");
	$("#txn_type").removeAttr("disabled");
	$("#m_name_search").removeAttr("disabled");
	$("#m_name").removeAttr("disabled");
	$("#accounts").removeAttr("disabled");
	$("#txn_amt").removeAttr("disabled");
	$("#txn_paymode").removeAttr("disabled");
	$("#txn_chequenum").removeAttr("disabled");
	$("#txn_slipnum").removeAttr("disabled");
	$("#txn_paydate").removeAttr("disabled");
	$("#txn_m_bank_acc").removeAttr("disabled");
	$("#txn_g_bank_acc").removeAttr("disabled");
	txwithRadioChangeHandler();
	txCategoryChangeHandler();
}
function showMsgAddTxn(msg) {
	$("#add_txn_msg").html(msg);
	$("#add_txn_err_msg").hide();
	$("#add_txn_msg").show();
}
function showErrorMsgAddTxn(msg) {
	$("#add_txn_err_msg").html(msg);
	$("#add_txn_msg").hide();
	$("#add_txn_err_msg").show();
}
function txwithRadioChangeHandler() {
	if ($("#txwith_member").is(":checked"))
		txwith = "MEMBER";
	else if ($("#txwith_group").is(":checked"))
		txwith = "GROUP";
	else if ($("#txwith_bank").is(":checked"))
		txwith = "BANK";

	var content = "";
	var arr = new Array();
	for (var i = 0; i < txTypes.length; i++) {
		if (txTypes[i].txWith == txwith) {
			var found = false;
			for (var j = 0; j < arr.length; j++) {
				found = (txTypes[i].txCategory == arr[j]);
				if (found)
					break;
			}
			if (!found) {
				arr[arr.length] = txTypes[i].txCategory;
				content += "<option>" + txTypes[i].txCategory + "</option>";
			}
		}
	}
	$("#txn_category").html(content);

	txCategoryChangeHandler();
}
function txCategoryChangeHandler() {
	var txCategory = $("#txn_category option:selected").text();
	var content = "";
	for (var i = 0; i < txTypes.length; i++) {
		if (txTypes[i].txWith == txwith && txTypes[i].txCategory == txCategory) {
			content += "<option>" + txTypes[i].txType + "</option>";
		}
	}
	$("#txn_type").html(content);
	txTypeChangeHandler();
	enableMemberSearch();
}
function txTypeChangeHandler() {
	var txCategory = $("#txn_category option:selected").text();
	var txType = $("#txn_type option:selected").text();
	if(txType == "Provisional Interest Earned"
		|| txType == "Compute Cumulative Saving"
		|| txType == "Bad Debt Closure"
		|| txType == "Divided Share Declare"
		|| txType == "Profit Share Declare") {
		$("#txn_paymode").html("<option>INTERNAL</option>");
		$("#txn_paymode").attr("disabled", "disabled");
	} else if(txType == "Outstanding Late Fee"
		|| txType == "Outstanding Cheque Bouncing"
		|| txType == "Outstanding Auto Debit Fail"
		|| txType == "Outstanding Miscellaneous Fee") {
		$("#txn_paymode").html("<option>OUTSTANDING</option>");
		$("#txn_paymode").attr("disabled", "disabled");
	} else if(txCategory == "TRANSFER") {
		$("#txn_paymode").html("<option>CHEQUE</option>");
		$("#txn_paymode").attr("disabled", "disabled");
	} else if(txCategory == "OUTSTANDING FEE") {
		$("#txn_paymode").html("<option>OUTSTANDING</option>");
		$("#txn_paymode").attr("disabled", "disabled");
	} else {
		$("#txn_paymode").html(payModeHTMLForAddTxn);
		$("#txn_paymode").removeAttr("disabled");
	}
	paymentModeChangeHandler();
	populateAccounts();
}
function enableMemberSearch() {
	if (txwith == "MEMBER"
			|| (txwith == "GROUP" && $("#txn_category").val() != "PROJECT LOAN")) {
		$("#m_name").removeAttr("disabled");
		$("#m_name_search").removeAttr("disabled");
	} else {
		selectedMemberATxn = null;
		memberAccDataATxn = null;
		$("#m_name").val("");
		$("#m_acc_num").html("");
		$("#m_name").attr("disabled", "disabled");
		$("#m_name_search").attr("disabled", "disabled");
	}
}
function populateAccounts() {
	var txCategory = $("#txn_category option:selected").text();
	var txType = $("#txn_type option:selected").text();
	$("#accounts").removeAttr("disabled");
	var content = "<option></option>";
	if (txwith == "MEMBER" && txCategory == "MEMBER SAVING"
			&& memberAccDataATxn != null) {
		var accList = memberAccDataATxn.memberSavingAc;
		if (accList != null) {
			for (var i = 0; i < accList.length; i++) {
				content += "<option>" + accList[i].memberSavingAcNo
						+ "</option>";
			}
		}
	} else if (txwith == "MEMBER" && txCategory == "MEMBER LOAN"
			&& memberAccDataATxn != null) {
		var accList = memberAccDataATxn.memberLoanAc;
		if (accList != null) {
			for (var i = 0; i < accList.length; i++) {
				content += "<option>" + accList[i].memberLoanAcNo + "</option>";
			}
		}
	} else if (txwith == "GROUP" && txCategory == "PROJECT LOAN") {
		for (var i = 0; i < groupInvtAccList.length; i++) {
			if (groupInvtAccList[i].investmentType == "Project Development") {
				content += "<option>" + groupInvtAccList[i].investmentAcName
						+ "</option>";
			}
		}
	} else if (txwith == "BANK" && txCategory == "BANK LOAN") {
		for (var i = 0; i < groupLoanAccList.length; i++) {
			content += "<option>" + groupLoanAccList[i].loanAcName
					+ "</option>";
		}
	} else if (txwith == "BANK" && txCategory == "INVESTMENT") {
		if (txType == "Bank Interest Earned") {
			for (var i = 0; i < groupBankAcNos.length; i++) {
				var bType = groupBankAcNos[i].bankAccountType;
				if (bType == "Saving Account" || bType == "Current Account") {
					content += "<option>" + groupBankAcNos[i].displayAccount
							+ "</option>";
				}
			}
		} else if (txType == "Fix Deposit Recovery"
				|| txType == "Fix Deposit Interest") {
			for (var i = 0; i < groupInvtAccList.length; i++) {
				if (groupInvtAccList[i].investmentType == "Fix Deposit")
					content += "<option>"
							+ groupInvtAccList[i].investmentAcName
							+ "</option>";
			}
		} else if (txType == "Other Investment Recovery"
				|| txType == "Other Investment Interest") {
			for (var i = 0; i < groupInvtAccList.length; i++) {
				if (groupInvtAccList[i].investmentType != "Project Development"
						|| groupInvtAccList[i].investmentType != "Fix Deposit")
					content += "<option>"
							+ groupInvtAccList[i].investmentAcName
							+ "</option>";
			}
		}
	} else if (txwith == "BANK" && txCategory == "EXPENSE") {
		for (var i = 0; i < groupLoanAccList.length; i++) {
			content += "<option>" + groupLoanAccList[i].loanAcName
					+ "</option>";
		}
		for (var i = 0; i < groupInvtAccList.length; i++) {
			content += "<option>" + groupInvtAccList[i].investmentAcName
					+ "</option>";
		}
	} else
		$("#accounts").attr("disabled", "disabled");
	$("#accounts").html(content);
}
function paymentModeChangeHandler() {
	var curPayMode = $("#txn_paymode option:selected").text();
	if (!isBankTypePaymentMode(curPayMode)) {
		$("#txn_g_bank_acc").val("");
		$("#txn_m_bank_acc").val("");
		$("#txn_chequenum").val("");
		$("#txn_g_bank_acc").attr("disabled", "disabled");
		$("#txn_m_bank_acc").attr("disabled", "disabled");
		$("#txn_chequenum").attr("disabled", "disabled");
	} else {
		if (curPayMode == "CHEQUE")
			$("#txn_chequenum").removeAttr("disabled");
		else {
			$("#txn_chequenum").val("");
			$("#txn_chequenum").attr("disabled", "disabled");
		}
		$("#txn_g_bank_acc").removeAttr("disabled");
		$("#txn_m_bank_acc").removeAttr("disabled");
		var gAccContent = "<option></option>";
		for (var i = 0; i < groupBankAcNos.length; i++) {
			var bType = groupBankAcNos[i].bankAccountType;
			if (bType == "Saving Account" || bType == "Current Account") {
				gAccContent += "<option>" + groupBankAcNos[i].displayAccount
						+ "</option>";
			}
		}
		$("#txn_g_bank_acc").html(gAccContent);
	}
}
function searchMemberUsingNameAddTxn() {
	var memberName = $("#m_name").val();
	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching();
		ajaxCall("/member/v1/member_search_by_name/"
				+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
				+ memberName, "GET", true, "", function(data, status) {
					searchedMembersATxn = data.memberNames;
					loadMemberSearchData(searchedMembersATxn);

					for (var i = 0; i < data.memberNames.length; i++) {
						$("#member_" + i).click(loadSelectedMemberAddTxn);
					}

				}, memberSearchFailed);
	}
}
function loadSelectedMemberAddTxn() {
	hideMemberSearchDialog();
	var index = parseInt($(this).data("id"));
	selectedMemberATxn = searchedMembersATxn[index];
	var memberName = selectedMemberATxn.memberName;
	var memberAcNo = selectedMemberATxn.memberAcNo;
	var memberAcNoStr = getReadableAccNum(memberAcNo);
	$("#m_acc_num").html(memberAcNoStr);
	$("#m_name").val(memberName);

	memberBankAcNos = selectedMemberATxn.memberBankAcNos;
	var mAccContent = "<option></option>";
	if (memberBankAcNos != null) {
		for (var i = 0; i < memberBankAcNos.length; i++)
			mAccContent += "<option>" + memberBankAcNos[i].displayAccount
					+ "</option>";
	}
	$("#txn_m_bank_acc").html(mAccContent);

	ajaxCall("/member/v1/member_accounts_data/"
			+ $("#lang option:selected").text() + "/" + memberAcNo + "/Active",
			"GET", true, "", function(data, status) {
				memberAccDataATxn = data;
				populateAccounts();
			});
}
function reloadTxnAttachmentTable() {
	var content = "";
	for (var i = 0; i < txnAttachments.length; i++) {
		if(txnAttachments[i] != null) {
			content += "<tr id='txn_doc_row" + txnAttachments[i].docId + "'><td>" + docTypes[txnAttachments[i].docType.docTypeId - 1].docCategory
					+ "</td><td>" + docTypes[txnAttachments[i].docType.docTypeId - 1].docType
					+ "</td><td>" + txnAttachments[i].fileName
					+ "</td><td width='50'><button class='btn yellow aboutme' data-id='" + i + "' id='txn_edit_doc_btn" + txnAttachments[i].docId + "'>Change</button>"
					+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='txn_del_doc_btn" + txnAttachments[i].docId + "'>Delete</button></td></tr>";
		}
	}
	if (content != "") {
		var attachmentTableHeader = "<tr><td><strong>Document Type</strong></td><td><strong>Document Category</strong></td><td><strong>File Name</strong></td><td></td><td></td></tr>";
		$("#txn_attachments_table").html(attachmentTableHeader + content);
		for (var i = 0; i < txnAttachments.length; i++) {
			if(txnAttachments[i] != null) {
				$("#txn_edit_doc_btn" + txnAttachments[i].docId).click(editTxnAttachment);
				$("#txn_del_doc_btn" + txnAttachments[i].docId).click(deleteTxnAttachment);
			}
		}
	}
	$("#txn_attachment_div").show();
}
function uploadTxnAttachment() {
	if (curTxnId == null) {
		showMessage("Please add transaction first.");
		return;
	}
	var uri = (appBaseURL + "/transaction/v1/tx_info_file/" + groupAcNo + "/" + curTxnId);
	loadDocumentUploadDlg("Transaction", uri, function(attachment) {
		txnAttachments[txnAttachments.length] = attachment;
		reloadTxnAttachmentTable();
	});
}
function editTxnAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/transaction/v1/update_tx_info_file/" + groupAcNo + "/" + txnAttachments[index].docId + "/" + curTxnId);
	loadEditUploadedDocumentDlg("Transaction", uri, txnAttachments[index], function(attachment) {
		txnAttachments[index] = attachment;
		reloadTxnAttachmentTable();
	});
}
function deleteTxnAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/transaction/v1/delete_tx_info_file/" + groupAcNo + "/" + txnAttachments[index].docId + "/" + curTxnId,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#m_doc_row" + txnAttachments[index].docId).remove();
			if($("#m_attachments_table tbody").children().length == 1)
				$("#m_attachments_table").html("");
			txnAttachments[index] = null;
		}, function(xhr) {
			showMessage("Unable to delete attachment.");
			showErrorMsgAddTxn("");
			handleErrorAndDisplayMsg(xhr, "add_txn_err_msg");
		});
	}
}