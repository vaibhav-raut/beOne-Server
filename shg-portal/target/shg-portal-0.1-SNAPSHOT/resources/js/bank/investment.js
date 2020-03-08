/**
 * 
 */
var investmentPlanningData = null, trackedInvtAcc = null, invtAccAttachments = new Array();
$(document).ready(function() {
	resetBankInvestmentForm();
	$("#investment_tab").click(resetBankInvestmentForm);
	$("#b_invest_amt1").change(numberFieldChangeHandler);
	$("#b_invest_amt2").change(numberFieldChangeHandler);
	$("#b_invest_roi1").change(numberFieldChangeHandler);
	$("#b_invest_roi2").change(numberFieldChangeHandler);
	$("#b_invest_ex_earning").change(numberFieldChangeHandler);
	$("#b_invest_months").change(numberFieldChangeHandler);
	$("#b_invest_proj_int_amt").change(numberFieldChangeHandler);
	$("#b_invest_oth_charges").change(numberFieldChangeHandler);
	$("#b_invest_type").change(investmentTypeChangeHandler);
	$("#b_invest_bank_search").click(bInvestmentBankSearchClickHandler);
	$("#b_invest_acc_search").click(searchInvestmentAccount);
	$("#b_invest_reset_btn").click(resetBankInvestmentForm);
	$("#b_invest_plan_btn").click(planBankInvestment);
	$("#b_invest_apply_btn").click(applyBankInvestment);
	$("#back2accTrac_btn2").click(back2AccTrackFromInvestment);
	$("#b_invest_approve_btn").click(approveBankInvtAcc);
	$("#b_invest_reject_btn").click(rejectBankInvtAcc);
	$("#b_invest_upload_file_btn").click(uploadInvtAccAttachment);
});
function investmentTypeChangeHandler() {
	var type = $("#b_invest_type option:selected").text();
	if (type == "Project Development") {
		investmentPlanningData = null;
		$("#b_invest_deposit_content").hide();
		$("#b_invest_prj_dev_content").show();
	} else {
		$("#b_invest_prj_dev_content").hide();
		$("#b_invest_deposit_content").show();
	}
}
function planBankInvestment() {
	if (investmentPlanningData != null) {
		investmentPlanningData = null;
		showMsgBankInvestment("");
		$("#b_invest_plan_btn").html("Plan Investment");

		$("#b_invest_amt2").removeAttr("disabled");
		$("#b_invest_req_date2").removeAttr("disabled");
		$("#b_invest_roi2").removeAttr("disabled");
		$("#b_invest_months").removeAttr("disabled");

		$("#b_invest_acc_name2").attr("disabled", "disabled");
		$("#b_invest_proj_int_amt").attr("disabled", "disabled");
		$("#b_invest_end_date2").attr("disabled", "disabled");
		$("#b_invest_oth_charges").attr("disabled", "disabled");
		return;
	}
	var bankProfileId = $("#b_invest_bank_id").val(), amount = $(
			"#b_invest_amt2").val(), interestRate = $("#b_invest_roi2").val(), reqDate = $(
			"#b_invest_req_date2").val(), numMonths = $("#b_invest_months")
			.val();
	if (bankProfileId == "")
		showErrorMsgBankInvestment("Please search Bank");
	else if (amount == "") {
		showErrorMsgBankInvestment("Please enter Investment Amount");
		$("#b_invest_amt2").focus();
	} else if (interestRate == "") {
		showErrorMsgBankInvestment("Please enter Rate of Interest");
		$("#b_invest_roi2").focus();
	} else if (reqDate == "") {
		showErrorMsgBankInvestment("Please select Requested Date");
		$("#b_invest_req_date2").focus();
	} else if (numMonths == "") {
		showErrorMsgBankInvestment("Please enter Investment Duration");
		$("#b_invest_months").focus();
	} else {
		var payload = "{\"investmentType\":\""
				+ $("#b_invest_type option:selected").text()
				+ "\",\"rateOfInterest\":" + interestRate + ",\"invtAm\":"
				+ amount + ",\"requestedDate\":\"" + reqDate
				+ "\",\"noOfMonths\":" + numMonths + "}";
		ajaxCall("/group_invt/v1/invt_planning", "POST", true, payload,
				function(data, status) {
					investmentPlanningData = data;
					showMsgBankInvestment("");
					$("#b_invest_plan_btn").html("Re-Plan Investment");
					$("#b_invest_amt2").attr("disabled", "disabled");
					$("#b_invest_req_date2").attr("disabled", "disabled");
					$("#b_invest_roi2").attr("disabled", "disabled");
					$("#b_invest_months").attr("disabled", "disabled");
					$("#b_invest_acc_name2").removeAttr("disabled");
					$("#b_invest_proj_int_amt").removeAttr("disabled");
//					$("#b_invest_end_date2").removeAttr("disabled");
//					$("#b_invest_oth_charges").removeAttr("disabled");

					$("#b_invest_proj_int_amt").val(data.projInterestAm);
					$("#b_invest_end_date2").val(data.maturityDate);
				}, function(xhr) {
					showErrorMsgBankInvestment("");
					handleErrorAndDisplayMsg(xhr, "b_invest_err_msg");
				});
	}
}
function applyBankInvestment() {
	var bankProfileId = $("#b_invest_bank_id").val(), investAccId = $(
			"#b_invest_b_acc_id").val();
	if (bankProfileId == "")
		showErrorMsgBankInvestment("Please search Bank");
	else if (investAccId == "")
		showErrorMsgBankInvestment("Please search Investment Account");
	else {
		var payload = "", type = $("#b_invest_type option:selected").text();
		if (type == "Project Development") {
			var accName = $("#b_invest_acc_name1").val(), amount = $(
					"#b_invest_amt1").val(), expEarning = $(
					"#b_invest_ex_earning").val(), interestRate = $(
					"#b_invest_roi1").val(), requestedDate = $(
					"#b_invest_req_date1").val(), maturityDate = $(
					"#b_invest_end_date1").val();
			if (accName == "") {
				showErrorMsgBankInvestment("Please enter Investment Account Name");
				$("#b_invest_acc_name1").focus();
			} else if (amount == "") {
				showErrorMsgBankInvestment("Please enter Investment Amount");
				$("#b_invest_amt1").focus();
			} else if (expEarning == "") {
				showErrorMsgBankInvestment("Please enter Expected Earning");
				$("#b_invest_ex_earning").focus();
			} else if (requestedDate == "") {
				showErrorMsgBankInvestment("Please select Requested Date");
				$("#b_invest_req_date1").focus();
			} else if (maturityDate == "") {
				showErrorMsgBankInvestment("Please select Expected Date of Maturity");
				$("#b_invest_end_date1").focus();
			} else if (interestRate == "") {
				showErrorMsgBankInvestment("Please enter Projected Rate of Interest");
				$("#b_invest_roi1").focus();
			} else {
				payload += "{\"groupAcNo\":" + groupAcNo
						+ ",\"approvedByMember\":" + curMemberAcNo
						+ ",\"bankGroupAcNo\":" + bankProfileId
						+ ",\"invtBankAcNo\":" + investAccId
						+ ",\"investmentNo\":" + investAccId
						+ ",\"investmentType\":\"" + type
						+ "\",\"investmentAcName\":\"" + accName
						+ "\",\"invtAm\":" + amount + ",\"projInterestAm\":"
						+ expEarning + ",\"interestRate\":" + interestRate
						+ ",\"requestedDate\":\"" + requestedDate
						+ "\",\"maturityDate\":\"" + maturityDate
						+ "\",\"developmentPlan\":\""
						+ $("#b_invest_dev_plan").val().replace("\n", " ")
						+ "\",\"investmentDesc\":\""
						+ $("#b_invest_remark").val().replace("\n", " ")
						+ "\"}";
			}
		} else {
			var accName = $("#b_invest_acc_name2").val(), amount = $(
					"#b_invest_amt2").val(), interestRate = $("#b_invest_roi2")
					.val(), requestedDate = $("#b_invest_req_date1").val(), maturityDate = $(
					"#b_invest_end_date2").val(), projectedIntAmt = $(
					"#b_invest_proj_int_amt").val();
			if (amount == "") {
				showErrorMsgBankInvestment("Please enter Investment Amount");
				$("#b_invest_amt2").focus();
			} else if (interestRate == "") {
				showErrorMsgBankInvestment("Please enter Rate of Interest");
				$("#b_invest_roi2").focus();
			} else if (requestedDate == "") {
				showErrorMsgBankInvestment("Please select Requested Date");
				$("#b_invest_req_date2").focus();
			} else if (investmentPlanningData == null) {
				showErrorMsgBankInvestment("Please do investment planning");
			} else if (accName == "") {
				showErrorMsgBankInvestment("Please enter Investment Account Name");
				$("#b_invest_acc_name2").focus();
			} else if (projectedIntAmt == "") {
				showErrorMsgBankInvestment("Please enter Projected Interest Amount");
				$("#b_invest_proj_int_amt").focus();
			} else if (maturityDate == "") {
				showErrorMsgBankInvestment("Please select Date of Maturity");
				$("#b_invest_end_date2").focus();
			} else {
				payload += "{\"groupAcNo\":" + groupAcNo
						+ ",\"approvedByMember\":" + curMemberAcNo
						+ ",\"bankGroupAcNo\":" + bankProfileId
						+ ",\"invtBankAcNo\":" + investAccId
						+ ",\"investmentNo\":" + investAccId
						+ ",\"investmentType\":\"" + type
						+ "\",\"investmentAcName\":\"" + accName
						+ "\",\"invtAm\":" + amount + ",\"projInterestAm\":"
						+ projectedIntAmt + ",\"interestRate\":" + interestRate
						+ ",\"requestedDate\":\"" + requestedDate
						+ "\",\"maturityDate\":\"" + maturityDate + "\"}";
			}
		}
		if (payload != "") {
			$("#b_invest_apply_btn").attr("disabled", "disabled");
			showMsgBankInvestment("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
			ajaxCall(
					"/group_invt/v1/group_invt_ac",
					"PUT",
					true,
					payload,
					function(data, status) {
						showMsgBankInvestment("Investment application has been submitted successfully."
								+ "<br>Investment Account Number: <b>"
								+ data.groupInvtAcNo
								+ "</b>"
								+ "<br>Account Status: <b>"
								+ data.accountStatus + "</b>");
						disableBankInvestmentForm();
						$("#b_invest_type").attr("disabled", "disabled");
						$("#b_invest_plan_btn").attr("disabled", "disabled");
						$("#b_invest_bank_search").attr("disabled", "disabled");
						$("#b_invest_acc_search").attr("disabled", "disabled");
					}, function(xhr) {
						showErrorMsgBankInvestment("");
						$("#b_invest_apply_btn").removeAttr("disabled");
						handleErrorAndDisplayMsg(xhr, "b_invest_err_msg");
					});
		}
	}
}
function resetBankInvestmentForm() {
	investmentPlanningData = null;
	$("#b_invest_type").removeAttr("disabled");
	$("#b_invest_bank_search").removeAttr("disabled");
	$("#b_invest_acc_search").removeAttr("disabled");
	$("#b_invest_plan_btn").html("Plan Investment");

	showMsgBankInvestment("");
	showInvtAccDefaultButtons(true);
	$("#b_invest_bank_id").val("");
	$("#b_invest_bank_name").html(" -- ");
	$("#b_invest_branch_name").html(" -- ");
	$("#b_invest_ifsc_code").html(" -- ");
	$("#b_invest_b_acc_id").val("");
	$("#b_invest_b_acc_num").html(" -- ");
	$("#b_invest_b_acc_name").html(" -- ");

	$("#b_invest_acc_name1").val("");
	$("#b_invest_amt1").val("");
	$("#b_invest_ex_earning").val("");
	$("#b_invest_req_date1").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#b_invest_end_date1").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#b_invest_roi1").val("");
	$("#b_invest_dev_plan").val("");
	$("#b_invest_remark").val("");

	$("#b_invest_acc_name2").val("");
	$("#b_invest_amt2").val("");
	$("#b_invest_req_date2").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#b_invest_end_date2").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#b_invest_roi2").val("");
	$("#b_invest_months").val("");
	$("#b_invest_oth_charges").val("");
	$("#b_invest_proj_int_amt").val("");
	disableBankInvestmentForm();
	$("#b_invest_attachment_div").hide();
}
function enableBankInvestmentForm() {
	$("#b_invest_acc_name1").removeAttr("disabled");
	$("#b_invest_amt1").removeAttr("disabled");
	$("#b_invest_ex_earning").removeAttr("disabled");
	$("#b_invest_req_date1").removeAttr("disabled");
	$("#b_invest_end_date1").removeAttr("disabled");
	$("#b_invest_roi1").removeAttr("disabled");
	$("#b_invest_dev_plan").removeAttr("disabled");
	$("#b_invest_remark").removeAttr("disabled");

	$("#b_invest_acc_name2").removeAttr("disabled");
	$("#b_invest_amt2").removeAttr("disabled");
	$("#b_invest_req_date2").removeAttr("disabled");
	$("#b_invest_roi2").removeAttr("disabled");
	$("#b_invest_months").removeAttr("disabled");
//	$("#b_invest_end_date2").removeAttr("disabled");
//	$("#b_invest_oth_charges").removeAttr("disabled");
	$("#b_invest_proj_int_amt").removeAttr("disabled");

	$("#b_invest_plan_btn").removeAttr("disabled");
	$("#b_invest_apply_btn").removeAttr("disabled");
}
function disableBankInvestmentForm() {
	$("#b_invest_acc_name1").attr("disabled", "disabled");
	$("#b_invest_amt1").attr("disabled", "disabled");
	$("#b_invest_ex_earning").attr("disabled", "disabled");
	$("#b_invest_req_date1").attr("disabled", "disabled");
	$("#b_invest_end_date1").attr("disabled", "disabled");
	$("#b_invest_roi1").attr("disabled", "disabled");
	$("#b_invest_dev_plan").attr("disabled", "disabled");
	$("#b_invest_remark").attr("disabled", "disabled");

	$("#b_invest_acc_name2").attr("disabled", "disabled");
	$("#b_invest_amt2").attr("disabled", "disabled");
	$("#b_invest_req_date2").attr("disabled", "disabled");
	$("#b_invest_end_date2").attr("disabled", "disabled");
	$("#b_invest_roi2").attr("disabled", "disabled");
	$("#b_invest_months").attr("disabled", "disabled");
	$("#b_invest_oth_charges").attr("disabled", "disabled");
	$("#b_invest_proj_int_amt").attr("disabled", "disabled");

	$("#b_invest_plan_btn").attr("disabled", "disabled");
	$("#b_invest_apply_btn").attr("disabled", "disabled");
}
function showMsgBankInvestment(msg) {
	$("#b_invest_msg").html(msg);
	$("#b_invest_err_msg").hide();
	$("#b_invest_msg").show();
}
function showErrorMsgBankInvestment(msg) {
	$("#b_invest_err_msg").html(msg);
	$("#b_invest_msg").hide();
	$("#b_invest_err_msg").show();
}
function bInvestmentBankSearchClickHandler() {
	bankSearchedFor = "bank-investment";
	loadBankSearchDialog();
	resetBankSearchDialog();
}
function searchInvestmentAccount() {
	$("#sel_add_acc_type").html("Investment Account");
	$("#add_acc_bank_id").val($("#b_invest_bank_id").val());
	$("#add_acc_bank_name").html($("#b_invest_bank_name").html());
	$("#add_acc_branch_name").html($("#b_invest_branch_name").html());
	$("#add_acc_ifsc_code").html($("#b_invest_ifsc_code").html());
	launchSelAddGroupAccDlg();
}
function showInvtAccDefaultButtons(isdefault) {
	$("#b_invest_activate_btn").hide();
	$("#b_invest_closure_btn").hide();
	if (isdefault) {
		$("#b_invest_approval_btns").hide();
		$("#b_invest_back_btn").hide();
		$("#b_invest_default_btns").show();
	} else {
		$("#b_invest_default_btns").hide();
		$("#b_invest_back_btn").show();
		$("#b_invest_approval_btns").show();
	}
}
function showInvtAccActivateBackButton() {
	$("#b_invest_default_btns").hide();
	$("#b_invest_approval_btns").hide();
	$("#b_invest_closure_btn").hide();
	$("#b_invest_activate_btn").show();
	$("#b_invest_back_btn").show();
}
function showInvtAccCloseBackButton() {
	$("#b_invest_default_btns").hide();
	$("#b_invest_approval_btns").hide();
	$("#b_invest_activate_btn").hide();
	$("#b_invest_closure_btn").show();
	$("#b_invest_back_btn").show();
}
function showInvtAccBackButtonOnly() {
	$("#b_invest_default_btns").hide();
	$("#b_invest_approval_btns").hide();
	$("#b_invest_activate_btn").hide();
	$("#b_invest_closure_btn").hide();
	$("#b_invest_back_btn").show();
}
function back2AccTrackFromInvestment() {
	loadBankAppTrackingTab();
	resetBankInvestmentForm();
	$("#b_invest_bank_search").removeAttr("disabled");
	$("#b_invest_acc_search").removeAttr("disabled");
	$("#b_invest_type").removeAttr("disabled");
}
function showBankInvtDetailsForTracking() {
	resetBankInvestmentForm();
	$("#b_invest_bank_search").attr("disabled", "disabled");
	$("#b_invest_acc_search").attr("disabled", "disabled");
	$("#b_invest_type").attr("disabled", "disabled");

	var index = parseInt($(this).data("id"));
	if (!isNaN(index))
		trackedInvtAcc = groupAllTrackedAcc.groupInvtAcs[index];

	var status = trackedInvtAcc.accountStatus;
	if (status == "Draft" || status == "Sent Back")
		showInvtAccDefaultButtons(true);
	else if (status == "Approved")
		showInvtAccActivateBackButton();
	else if (status == "Active" || status == "Sub Standard" || status == "Bad Debt")
		showInvtAccCloseBackButton();
	else if (status == "Submitted" || status == "Under Review" || status == "On Hold")
		showInvtAccDefaultButtons(false);
	else
		showInvtAccBackButtonOnly();

	showBankInvtAccDetails(trackedInvtAcc);
}
function approveBankInvtAcc() {
	updateBankInvtAccountStatus("Approved");
}
function rejectBankInvtAcc() {
	updateBankInvtAccountStatus("Rejected");
}
function updateBankInvtAccountStatus(status) {
	trackedInvtAcc.accountStatus = status;
	trackedInvtAcc.approvedByMember = curMemberAcNo;
	trackedInvtAcc.attachments = null;
	var payload = JSON.stringify(trackedInvtAcc);
	ajaxCall("/group_invt/v1/approve_group_invt_ac", "POST", true, payload,
			function(data, status) {
		showMsgBankInvestment("Updated Account Status: <b>" + data.accountStatus
				+ "</b>" + "<br>Account Number: <b>" + data.groupInvtAcNo + "</b>");
		if (data.accountStatus == "Approved")
			showInvtAccActivateBackButton();
		else if (data.accountStatus == "Active")
			showInvtAccCloseBackButton();
		else
			showInvtAccBackButtonOnly();
		trackedInvtAcc = data;
		trackedInvtAcc.attachments = invtAccAttachments;
		loadBankAppTrackingTab();
		$("#b_invest_approve_btn").removeAttr("disabled");
		$("#b_invest_reject_btn").removeAttr("disabled");
	}, function(xhr) {
		$("#b_invest_approve_btn").removeAttr("disabled");
		$("#b_invest_reject_btn").removeAttr("disabled");
		showErrorMsgBankInvestment("");
		handleErrorAndDisplayMsg(xhr, "b_invest_err_msg");
	});
}
function showBankInvtAccDetails(invtAcc) {
	showMsgBankInvestment("Account Status: <b>" + invtAcc.accountStatus
			+ "</b>" + "<br>Account Number: <b>" + invtAcc.groupInvtAcNo + "</b>");
	$("#b_invest_bank_id").val(invtAcc.bankProfileId);
	$("#b_invest_b_acc_id").val(invtAcc.invtBankAcNo);
	if (invtAcc.bankAccount != null) {
		$("#b_invest_bank_name").html(invtAcc.bankAccount.bankName);
		$("#b_invest_branch_name").html(invtAcc.bankAccount.bankBranchName);
		$("#b_invest_ifsc_code").html(invtAcc.bankAccount.ifcsCode);
		$("#b_invest_b_acc_num").html(invtAcc.bankAccount.accountNumber);
		$("#b_invest_b_acc_name").html(invtAcc.bankAccount.accountName);
	}

	$("#b_invest_type").val(invtAcc.investmentType);
	investmentTypeChangeHandler();
	
	if (invtAcc.investmentType == "Project Development") {
		$("#b_invest_acc_name1").val(invtAcc.investmentAcName);
		$("#b_invest_amt1").val(invtAcc.invtAm);
		$("#b_invest_ex_earning").val(invtAcc.projInterestAm);
		$("#b_invest_req_date1").val(invtAcc.requestedDate);
		$("#b_invest_end_date1").val(invtAcc.maturityDate);
		$("#b_invest_roi1").val(invtAcc.interestRate);
		$("#b_invest_dev_plan").val(invtAcc.developmentPlan);
		$("#b_invest_remark").val(invtAcc.investmentDesc);
	} else {
		$("#b_invest_acc_name2").val(invtAcc.investmentAcName);
		$("#b_invest_amt2").val(invtAcc.invtAm);
		$("#b_invest_req_date2").val(invtAcc.requestedDate);
		$("#b_invest_end_date2").val(invtAcc.maturityDate);
		$("#b_invest_roi2").val(invtAcc.interestRate);
		$("#b_invest_months").val("");
		$("#b_invest_oth_charges").val("");
		$("#b_invest_proj_int_amt").val(invtAcc.projInterestAm);
	}
	invtAccAttachments = invtAcc.attachments;
	reloadInvtAccAttachmentTable();
}
function reloadInvtAccAttachmentTable() {
	var content = "";
	for (var i = 0; i < invtAccAttachments.length; i++) {
		if(invtAccAttachments[i] != null) {
			content += "<tr id='b_invest_doc_row" + invtAccAttachments[i].docId + "'><td>" + docTypes[invtAccAttachments[i].docType.docTypeId - 1].docCategory
					+ "</td><td>" + docTypes[invtAccAttachments[i].docType.docTypeId - 1].docType
					+ "</td><td>" + invtAccAttachments[i].fileName
					+ "</td><td width='50'><button class='btn yellow aboutme' data-id='" + i + "' id='b_invest_edit_doc_btn" + invtAccAttachments[i].docId + "'>Change</button>"
					+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='b_invest_del_doc_btn" + invtAccAttachments[i].docId + "'>Delete</button></td></tr>";
		}
	}
	if (content != "") {
		var attachmentTableHeader = "<tr><td><strong>Document Type</strong></td><td><strong>Document Category</strong></td><td><strong>File Name</strong></td><td></td><td></td></tr>";
		$("#b_invest_attachments_table").html(attachmentTableHeader + content);
		for (var i = 0; i < invtAccAttachments.length; i++) {
			if(invtAccAttachments[i] != null) {
				$("#b_invest_edit_doc_btn" + invtAccAttachments[i].docId).click(editInvtAccAttachment);
				$("#b_invest_del_doc_btn" + invtAccAttachments[i].docId).click(deleteInvtAccAttachment);
			}
		}
	}
	$("#b_invest_attachment_div").show();
}
function uploadInvtAccAttachment() {
	var uri = (appBaseURL + "/group_invt/v1/attach_file/" + trackedInvtAcc.groupAcNo + "/" + trackedInvtAcc.groupInvtAcNo);
	loadDocumentUploadDlg("Loan", uri, function(attachment) {
		invtAccAttachments[invtAccAttachments.length] = attachment;
		trackedInvtAcc.attachments = invtAccAttachments;
		reloadInvtAccAttachmentTable();
	});
}
function editInvtAccAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/group_invt/v1/update_attach_file/" + trackedInvtAcc.groupAcNo + "/" + invtAccAttachments[index].docId + "/" + trackedInvtAcc.groupInvtAcNo);
	loadEditUploadedDocumentDlg("Loan", uri, invtAccAttachments[index], function(attachment) {
		invtAccAttachments[index] = attachment;
		reloadInvtAccAttachmentTable();
	});
}
function deleteInvtAccAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/group_invt/v1/delete_attach_file/" + trackedInvtAcc.groupAcNo + "/" + invtAccAttachments[index].docId + "/" + trackedInvtAcc.groupInvtAcNo,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#b_invest_doc_row" + invtAccAttachments[index].docId).remove();
			if($("#b_invest_attachments_table tbody").children().length == 1)
				$("#b_invest_attachments_table").html("");
			invtAccAttachments[index] = null;
			trackedInvtAcc.attachments = invtAccAttachments;
		}, function(xhr) {
			showErrorMsgBankInvestment("");
			handleErrorAndDisplayMsg(xhr, "b_invest_err_msg");
		});
	}
}