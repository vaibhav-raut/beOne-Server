/**
 * 
 */
var fundTypes = null, loanCalcTypes = null, loanPlanningData = null, trackedLoanAcc = null, loanAccAttachments = new Array();
$(document).ready(function() {
	loadFundTypes();
	$("#bank_loan_tab").click(loadBankLoanTab);
	$("#b_loan_amt").change(numberFieldChangeHandler);
	$("#b_loan_roi").change(numberFieldChangeHandler);
	$("#b_loan_proc_fee").change(numberFieldChangeHandler);
	$("#b_loan_oth_charges").change(numberFieldChangeHandler);
	$("#b_loan_num_of_emi").change(numberFieldChangeHandler);
	$("#b_loan_fixed_emi").change(numberFieldChangeHandler);
	$("#b_loan_calc_type").change(bLoanCalcTypeChangeHandler);
	$("#b_loan_bank_search").click(bLoanBankSearchClickHandler);
	$("#b_loan_acc_search").click(searchLoanAccount);
	$("#b_loan_reset_btn").click(resetBankLoanForm);
	$("#b_loan_plan_btn").click(planBankLoan);
	$("#b_loan_apply_btn").click(applyBankLoan);
	$("#back2accTrac_btn1").click(back2AccTrackFromLoan);
	$("#b_loan_approve_btn").click(approveBankLoanAcc);
	$("#b_loan_reject_btn").click(rejectBankLoanAcc);
	$("#b_loan_upload_file_btn").click(uploadLoanAccAttachment);
});
function loadFundTypes() {
	ajaxCall("/enum/v1/fund_type", "GET", true, "", function(data, status) {
		fundTypes = data.enumValues;
		loadLoanCalcTypes();
	});
}
function loadLoanCalcTypes() {
	ajaxCall("/enum/v1/enum_values/LoanCalculation", "GET", true, "", function(
			data, status) {
		loanCalcTypes = data.enumValues;
		loadBankLoanTab();
	});
}
function loadBankLoanTab() {
	var fTypeContent = "";
	for (var i = 0; i < fundTypes.length; i++)
		fTypeContent += "<option>" + fundTypes[i].fundType + "</option>";
	$("#b_loan_fund_type").html(fTypeContent);

	var calcTypeContent = "";
	for (var i = 0; i < loanCalcTypes.length; i++)
		calcTypeContent += "<option>" + loanCalcTypes[i].enumValue
				+ "</option>";
	$("#b_loan_calc_type").html(calcTypeContent);

	resetBankLoanForm();
}
function planBankLoan() {
	if (loanPlanningData != null) {
		loanPlanningData = null;
		showMsgBankLoan("");
		$("#b_loan_plan_btn").html("Plan Loan");
		$("#emi_plan_msg").show();
		$("#emi_plan_table").html("");
		disableBankLoanApplyForm();
		enableBankLoanPlanningForm();
		return;
	}
	var bankProfileId = $("#b_loan_bank_id").val(), amount = $("#b_loan_amt")
			.val(), interestRate = $("#b_loan_roi").val(), calcType = $(
			"#b_loan_calc_type option:selected").text(), procFeePercent = $(
			"#b_loan_proc_fee_percent").val(), numEMI = $("#b_loan_num_of_emi")
			.val(), fixedEMI = $("#b_loan_fixed_emi").val(), startDate = $(
			"#b_loan_s_date").val(), reqDate = $("#b_loan_req_date").val();

	if (bankProfileId == "")
		showErrorMsgBankLoan("Please search Bank");
	else if (amount == "") {
		showErrorMsgBankLoan("Please enter Loan Amount");
		$("#b_loan_amt").focus();
	} else if (interestRate == "") {
		showErrorMsgBankLoan("Please enter Rate of Interest");
		$("#b_loan_roi").focus();
	} else if (calcType == "") {
		showErrorMsgBankLoan("Please select Loan Computation Type");
		$("#b_loan_calc_type").focus();
	} else if (numEMI == "" && fixedEMI == "") {
		showErrorMsgBankLoan("Please enter No. of EMIs or Fixed EMI");
		$("#b_loan_num_of_emi").focus();
		$("#b_loan_fixed_emi").focus();
	} else if (procFeePercent == "") {
		showErrorMsgBankLoan("Please enter Loan Processing Fee Percent");
		$("#b_loan_proc_fee_percent").focus();
	} else if (reqDate == "") {
		showErrorMsgBankLoan("Please select Requested Date");
		$("#b_loan_req_date").focus();
	} else if (startDate == "") {
		showErrorMsgBankLoan("Please select EMI Start Date");
		$("#b_loan_s_date").focus();
	} else {
		$("#b_loan_plan_btn").attr("disabled", "disabled");
		showMsgBankLoan("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		if (numEMI == "")
			numEMI = 0;
		if (fixedEMI == "")
			fixedEMI = 0;
		var payload = "{\"loanCalculation\":\"" + calcType
				+ "\",\"rateOfInterest\":" + interestRate + ",\"principle\":"
				+ amount + ",\"fixedEMI\":" + fixedEMI + ",\"noOfEMIs\":"
				+ numEMI + ",\"requestedDate\":\"" + reqDate
				+ "\",\"startDate\":\"" + startDate
				+ "\",\"loanProcessingFeePercent\":" + procFeePercent + "}";
		ajaxCall(
				"/gloan/v1/loan_planning",
				"POST",
				true,
				payload,
				function(data, status) {
					loanPlanningData = data;
					showMsgBankLoan("Loan planning done successfully.<br> You can see Monthly EMI Breakdown in the below section.");
					disableBankLoanPlanningForm();
					$("#b_loan_plan_btn").html("Re-Plan Loan");
					$("#b_loan_plan_btn").removeAttr("disabled");
					$("#b_loan_proc_fee").val(
							loanPlanningData.loanProcessingFee);
					enableBankLoanApplyForm();

					loadLoanEMIPlan(loanPlanningData.loanEMIs);
				}, function(xhr) {
					showErrorMsgBankLoan("");
					$("#b_loan_plan_btn").removeAttr("disabled");
					handleErrorAndDisplayMsg(xhr, "b_loan_err_msg");
				});
	}
}
function loadLoanEMIPlan(loanEMIs) {
	var emiTableContent = "<tr><td>No Data to display</td></tr>";
	if (loanEMIs != null && loanEMIs.length != 0) {
		emiTableContent = "<tr><th>Month No.</th><th>Due Date</th><th>EMI</th><th>Principle EMI</th><th>Interest EMI</th><th>Pending Principle</th></tr>";
		for (var i = 0; i < loanEMIs.length; i++) {
			var emi = loanEMIs[i];
			emiTableContent += "<tr><td>" + emi.monthNo + "</td><td>"
					+ emi.dueDate + "</td><td>Rs." + emi.emi + "</td><td>Rs."
					+ emi.principleEMI + "</td><td>Rs." + emi.interestEMI
					+ "</td><td>Rs." + emi.principle + "</td></tr>";
		}
	}
	$("#emi_plan_msg").hide();
	$("#emi_plan_table").html(emiTableContent);
}
function applyBankLoan() {
	var bankProfileId = $("#b_loan_bank_id").val(), loanAccId = $(
			"#b_loan_b_acc_id").val(), accName = $("#b_loan_acc_name").val(), fundType = $(
			"#b_loan_fund_type option:selected").text(), procFee = $(
			"#b_loan_proc_fee").val();

	if (bankProfileId == "")
		showErrorMsgBankLoan("Please search Bank");
	else if (loanPlanningData == null)
		showErrorMsgBankLoan("Please do loan planning");
	else if (loanAccId == "")
		showErrorMsgBankLoan("Please search Loan Account");
	else if (accName == "") {
		showErrorMsgBankLoan("Please enter Loan Account Name");
		$("#b_loan_acc_name").focus();
	} else if (fundType == "") {
		showErrorMsgBankLoan("Please select Loan Type");
		$("#b_loan_fund_type").focus();
	} else if (procFee == "") {
		showErrorMsgBankLoan("Please enter Processing Fee");
		$("#b_loan_proc_fee").focus();
	} else {
		$("#b_loan_apply_btn").attr("disabled", "disabled");
		showMsgBankLoan("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		var otherFee = $("#b_loan_oth_charges").val();
		if (otherFee == "")
			otherFee = 0;
		var payload = "{\"groupAcNo\":"
				+ groupAcNo
				+ ",\"approvedByMember\":"
				+ curMemberAcNo
				+ ",\"bankGroupAcNo\":"
				+ bankProfileId
				+ ",\"loanBankAcNo\":"
				+ loanAccId
				+ ",\"loanAcName\":\""
				+ accName
				+ "\",\"principle\":"
				+ loanPlanningData.principle
				+ ",\"rateOfInterest\":"
				+ loanPlanningData.rateOfInterest
				+ ",\"loanType\":\""
				+ fundType
				+ "\",\"loanCalculation\":\""
				+ loanPlanningData.loanCalculation
				+ "\",\"projectedInterest\":"
				+ loanPlanningData.totalInterest
				+ ",\"installment\":"
				+ loanPlanningData.fixedEMI
				+ ",\"preEmiInterest\":"
				+ loanPlanningData.preEMI
				+ ",\"startupNoOfInst\":"
				+ loanPlanningData.noOfStartUpEMI
				+ ",\"expNoOfInst\":"
				+ loanPlanningData.noOfEMIs
				+ ",\"requestedDate\":\""
				+ loanPlanningData.requestedDate
				+ "\",\"instStartDate\":\""
				+ loanPlanningData.startDate
				+ "\",\"expCompletionDate\":\""
				+ loanPlanningData.expCompletionDate
				+ "\",\"loanProcessingFee\":"
				+ procFee
				+ ",\"otherFee\":"
				+ otherFee
				+ ",\"developmentPlan\":\""
				+ $("#b_loan_dev_plan").val().replace("\n", " ")
				+ "\",\"statusMessage\":\""
				+ $("#b_loan_remark").val().replace("\n", " ")
				+ "\",\"paidInterest\":0,\"pendingInterestDue\":0,\"loanSource\":\"BANK\",\"recoveryPeriod\":\"Monthly\"}";

		ajaxCall("/gloan/v1/group_loan_ac", "PUT", true, payload, function(
				data, status) {
			showMsgBankLoan("Loan application has been submitted successfully."
					+ "<br>Loan Account Number: <b>" + data.groupLoanAcNo
					+ "</b>" + "<br>Account Status: <b>" + data.accountStatus
					+ "</b>");
			disableBankLoanPlanningForm();
			disableBankLoanApplyForm();
			$("#b_loan_plan_btn").attr("disabled", "disabled");
			$("#b_loan_bank_search").attr("disabled", "disabled");
			$("#b_loan_acc_search").attr("disabled", "disabled");
		}, function(xhr) {
			showErrorMsgBankLoan("");
			$("#b_loan_apply_btn").removeAttr("disabled");
			handleErrorAndDisplayMsg(xhr, "b_loan_err_msg");
		});
	}
}
function bLoanCalcTypeChangeHandler() {
	var curCalcType = $("#b_loan_calc_type option:selected").text();
	if (curCalcType == "Normal EMI") {
		$("#b_loan_fixed_emi").val("");
		$("#b_loan_fixed_emi").attr("disabled", "disabled");
		$("#b_loan_num_of_emi").removeAttr("disabled");
	} else if (curCalcType == "Fixed EMI") {
		$("#b_loan_num_of_emi").val("");
		$("#b_loan_num_of_emi").attr("disabled", "disabled");
		$("#b_loan_fixed_emi").removeAttr("disabled");
	} else if (curCalcType == "Reducing Interest") {
		$("#b_loan_num_of_emi").removeAttr("disabled");
		$("#b_loan_fixed_emi").removeAttr("disabled");
	}
}
function resetBankLoanForm() {
	loanPlanningData = null;
	showMsgBankLoan("");
	showLoanAccDefaultButtons(true);
	$("#b_loan_bank_search").removeAttr("disabled");
	$("#b_loan_acc_search").removeAttr("disabled");

	$("#b_loan_bank_id").val("");
	$("#b_loan_bank_name").html(" -- ");
	$("#b_loan_branch_name").html(" -- ");
	$("#b_loan_ifsc_code").html(" -- ");
	$("#b_loan_b_acc_id").val("");
	$("#b_loan_b_acc_num").html(" -- ");
	$("#b_loan_b_acc_name").html(" -- ");

	$("#b_loan_acc_name").val("");
	$("#b_loan_amt").val("");
	$("#b_loan_roi").val("");
	$("#b_loan_proc_fee_percent").val("");
	$("#b_loan_proc_fee").val("");
	$("#b_loan_oth_charges").val("");
	$("#b_loan_num_of_emi").val("");
	$("#b_loan_fixed_emi").val("");
	$("#b_loan_req_date").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#b_loan_s_date").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#b_loan_dev_plan").val("");
	$("#b_loan_remark").val("");
	$("#b_loan_plan_btn").html("Plan Loan");

	$("#emi_plan_msg").show();
	$("#emi_plan_table").html("");

	disableBankLoanPlanningForm();
	disableBankLoanApplyForm();
	$("#b_loan_attachment_div").hide();
}
function enableBankLoanPlanningForm() {
	$("#b_loan_amt").removeAttr("disabled");
	$("#b_loan_roi").removeAttr("disabled");
	$("#b_loan_calc_type").removeAttr("disabled");
	$("#b_loan_num_of_emi").removeAttr("disabled");
	$("#b_loan_fixed_emi").removeAttr("disabled");
	$("#b_loan_proc_fee_percent").removeAttr("disabled");
	$("#b_loan_req_date").removeAttr("disabled");
	$("#b_loan_s_date").removeAttr("disabled");
	$("#b_loan_plan_btn").removeAttr("disabled");
	bLoanCalcTypeChangeHandler();
}
function disableBankLoanPlanningForm() {
	$("#b_loan_amt").attr("disabled", "disabled");
	$("#b_loan_roi").attr("disabled", "disabled");
	$("#b_loan_calc_type").attr("disabled", "disabled");
	$("#b_loan_num_of_emi").attr("disabled", "disabled");
	$("#b_loan_fixed_emi").attr("disabled", "disabled");
	$("#b_loan_proc_fee_percent").attr("disabled", "disabled");
	$("#b_loan_req_date").attr("disabled", "disabled");
	$("#b_loan_s_date").attr("disabled", "disabled");
	$("#b_loan_plan_btn").attr("disabled", "disabled");
}
function enableBankLoanApplyForm() {
	$("#b_loan_acc_name").removeAttr("disabled");
	$("#b_loan_fund_type").removeAttr("disabled");
	$("#b_loan_proc_fee").removeAttr("disabled");
	$("#b_loan_oth_charges").removeAttr("disabled");
	$("#b_loan_dev_plan").removeAttr("disabled");
	$("#b_loan_remark").removeAttr("disabled");
	$("#b_loan_apply_btn").removeAttr("disabled");
}
function disableBankLoanApplyForm() {
	$("#b_loan_acc_name").attr("disabled", "disabled");
	$("#b_loan_fund_type").attr("disabled", "disabled");
	$("#b_loan_calc_type").attr("disabled", "disabled");
	$("#b_loan_proc_fee").attr("disabled", "disabled");
	$("#b_loan_oth_charges").attr("disabled", "disabled");
	$("#b_loan_dev_plan").attr("disabled", "disabled");
	$("#b_loan_remark").attr("disabled", "disabled");
	$("#b_loan_apply_btn").attr("disabled", "disabled");
}
function showMsgBankLoan(msg) {
	$("#b_loan_msg").html(msg);
	$("#b_loan_err_msg").hide();
	$("#b_loan_msg").show();
}
function showErrorMsgBankLoan(msg) {
	$("#b_loan_err_msg").html(msg);
	$("#b_loan_msg").hide();
	$("#b_loan_err_msg").show();
}
function bLoanBankSearchClickHandler() {
	bankSearchedFor = "bank-loan";
	loadBankSearchDialog();
	resetBankSearchDialog();
}
function searchLoanAccount() {
	$("#sel_add_acc_type").html("Loan Account");
	$("#add_acc_bank_id").val($("#b_loan_bank_id").val());
	$("#add_acc_bank_name").html($("#b_loan_bank_name").html());
	$("#add_acc_branch_name").html($("#b_loan_branch_name").html());
	$("#add_acc_ifsc_code").html($("#b_loan_ifsc_code").html());
	launchSelAddGroupAccDlg();
}
function showLoanAccDefaultButtons(isdefault) {
	$("#b_loan_activate_btn").hide();
	$("#b_loan_closure_btn").hide();
	if (isdefault) {
		$("#b_loan_approval_btns").hide();
		$("#b_loan_back_btn").hide();
		$("#b_loan_default_btns").show();
	} else {
		$("#b_loan_default_btns").hide();
		$("#b_loan_back_btn").show();
		$("#b_loan_approval_btns").show();
	}
}
function showLoanAccActivateBackButton() {
	$("#b_loan_default_btns").hide();
	$("#b_loan_approval_btns").hide();
	$("#b_loan_closure_btn").hide();
	$("#b_loan_activate_btn").show();
	$("#b_loan_back_btn").show();
}
function showLoanAccCloseBackButton() {
	$("#b_loan_default_btns").hide();
	$("#b_loan_approval_btns").hide();
	$("#b_loan_activate_btn").hide();
	$("#b_loan_closure_btn").show();
	$("#b_loan_back_btn").show();
}
function showLoanAccBackButtonOnly() {
	$("#b_loan_default_btns").hide();
	$("#b_loan_approval_btns").hide();
	$("#b_loan_activate_btn").hide();
	$("#b_loan_closure_btn").hide();
	$("#b_loan_back_btn").show();
}
function back2AccTrackFromLoan() {
	loadBankAppTrackingTab();
	resetBankLoanForm();
	$("#b_loan_bank_search").removeAttr("disabled");
	$("#b_loan_acc_search").removeAttr("disabled");
}
function showBankLoanDetailsForTracking() {
	resetBankLoanForm();
	$("#b_loan_bank_search").attr("disabled", "disabled");
	$("#b_loan_acc_search").attr("disabled", "disabled");

	var index = parseInt($(this).data("id"));
	if (!isNaN(index))
		trackedLoanAcc = groupAllTrackedAcc.groupLoanAcs[index];

	var status = trackedLoanAcc.accountStatus;
	if (status == "Draft" || status == "Sent Back")
		showLoanAccDefaultButtons(true);
	else if (status == "Approved")
		showLoanAccActivateBackButton();
	else if (status == "Active" || status == "Sub Standard" || status == "Bad Debt")
		showLoanAccCloseBackButton();
	else if (status == "Submitted" || status == "Under Review" || status == "On Hold")
		showLoanAccDefaultButtons(false);
	else
		showLoanAccBackButtonOnly();

	showBankLoanAccDetails(trackedLoanAcc);
}
function approveBankLoanAcc() {
	updateBankLoanAccountStatus("Approved");
}
function rejectBankLoanAcc() {
	updateBankLoanAccountStatus("Rejected");
}
function updateBankLoanAccountStatus(status) {
	trackedLoanAcc.accountStatus = status;
	trackedLoanAcc.approvedByMember = curMemberAcNo;
	trackedLoanAcc.attachments = null;
	var payload = JSON.stringify(trackedLoanAcc);
	ajaxCall("/gloan/v1/approve_group_loan_ac", "POST", true, payload,
			function(data, status) {
				showMsgBankLoan("Updated Account Status: <b>"
						+ data.accountStatus + "</b>"
						+ "<br>Account Number: <b>" + data.groupLoanAcNo
						+ "</b>");
				if (data.accountStatus == "Approved")
					showLoanAccActivateBackButton();
				else if (data.accountStatus == "Active")
					showLoanAccCloseBackButton();
				else
					showLoanAccBackButtonOnly();
				trackedLoanAcc = data;
				trackedLoanAcc.attachment = loanAccAttachments;
				loadBankAppTrackingTab();
				$("#b_loan_approve_btn").removeAttr("disabled");
				$("#b_loan_reject_btn").removeAttr("disabled");
			}, function(xhr) {
				$("#b_loan_approve_btn").removeAttr("disabled");
				$("#b_loan_reject_btn").removeAttr("disabled");
				showErrorMsgBankLoan("");
				handleErrorAndDisplayMsg(xhr, "b_loan_err_msg");
			});
}
function showBankLoanAccDetails(loanAcc) {
	showMsgBankLoan("Account Status: <b>" + loanAcc.accountStatus + "</b>"
			+ "<br>Account Number: <b>" + loanAcc.groupLoanAcNo + "</b>");

	$("#b_loan_bank_id").val(loanAcc.bankGroupAcNo);
	$("#b_loan_b_acc_id").val(loanAcc.loanBankAcNo);
	if (loanAcc.bankAccount != null) {
		$("#b_loan_bank_name").html(loanAcc.bankAccount.bankName);
		$("#b_loan_branch_name").html(loanAcc.bankAccount.bankBranchName);
		$("#b_loan_ifsc_code").html(loanAcc.bankAccount.ifcsCode);
		$("#b_loan_b_acc_num").html(loanAcc.bankAccount.accountNumber);
		$("#b_loan_b_acc_name").html(loanAcc.bankAccount.accountName);
	}
	$("#b_loan_acc_name").val(loanAcc.loanAcName);
	$("#b_loan_amt").val(loanAcc.principle);
	$("#b_loan_roi").val(loanAcc.rateOfInterest);
	$("#b_loan_calc_type").val(loanAcc.loanCalculation);
	$("#b_loan_proc_fee_percent").val((loanAcc.loanProcessingFee/loanAcc.principle) * 100);
	$("#b_loan_proc_fee").val(loanAcc.loanProcessingFee);
	$("#b_loan_oth_charges").val(loanAcc.otherFee);
	$("#b_loan_num_of_emi").val(loanAcc.expNoOfInst);
	$("#b_loan_fixed_emi").val(loanAcc.installment);
	$("#b_loan_req_date").val(loanAcc.requestedDate);
	$("#b_loan_s_date").val(loanAcc.instStartDate);
	$("#b_loan_fund_type").val(loanAcc.loanType);
	$("#b_loan_dev_plan").val(loanAcc.developmentPlan);
	$("#b_loan_remark").val(loanAcc.statusMessage);
	$("#b_loan_plan_btn").html("Plan Loan");

	$("#emi_plan_msg").hide();
	$("#emi_plan_table").html("");

	loanAccAttachments = loanAcc.attachments;
	reloadLoanAccAttachmentTable();
}
function reloadLoanAccAttachmentTable() {
	var content = "";
	for (var i = 0; i < loanAccAttachments.length; i++) {
		if(loanAccAttachments[i] != null) {
			content += "<tr id='b_loan_doc_row" + loanAccAttachments[i].docId + "'><td>" + docTypes[loanAccAttachments[i].docType.docTypeId - 1].docCategory
					+ "</td><td>" + docTypes[loanAccAttachments[i].docType.docTypeId - 1].docType
					+ "</td><td>" + loanAccAttachments[i].fileName
					+ "</td><td width='50'><button class='btn yellow aboutme' data-id='" + i + "' id='b_loan_edit_doc_btn" + loanAccAttachments[i].docId + "'>Change</button>"
					+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='b_loan_del_doc_btn" + loanAccAttachments[i].docId + "'>Delete</button></td></tr>";
		}
	}
	if (content != "") {
		var attachmentTableHeader = "<tr><td><strong>Document Type</strong></td><td><strong>Document Category</strong></td><td><strong>File Name</strong></td><td></td><td></td></tr>";
		$("#b_loan_attachments_table").html(attachmentTableHeader + content);
		for (var i = 0; i < loanAccAttachments.length; i++) {
			if(loanAccAttachments[i] != null) {
				$("#b_loan_edit_doc_btn" + loanAccAttachments[i].docId).click(editLoanAccAttachment);
				$("#b_loan_del_doc_btn" + loanAccAttachments[i].docId).click(deleteLoanAccAttachment);
			}
		}
	}
	$("#b_loan_attachment_div").show();
}
function uploadLoanAccAttachment() {
	var uri = (appBaseURL + "/gloan/v1/attach_file/" + trackedLoanAcc.groupAcNo + "/" + trackedLoanAcc.groupLoanAcNo);
	loadDocumentUploadDlg("Loan", uri, function(attachment) {
		loanAccAttachments[loanAccAttachments.length] = attachment;
		trackedLoanAcc.attachments = loanAccAttachments;
		reloadLoanAccAttachmentTable();
	});
}
function editLoanAccAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/gloan/v1/update_attach_file/" + trackedLoanAcc.groupAcNo + "/" + loanAccAttachments[index].docId + "/" + trackedLoanAcc.groupLoanAcNo);
	loadEditUploadedDocumentDlg("Loan", uri, loanAccAttachments[index], function(attachment) {
		loanAccAttachments[index] = attachment;
		reloadLoanAccAttachmentTable();
	});
}
function deleteLoanAccAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/gloan/v1/delete_attach_file/" + trackedLoanAcc.groupAcNo + "/" + loanAccAttachments[index].docId + "/" + trackedLoanAcc.groupLoanAcNo,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#b_loan_doc_row" + loanAccAttachments[index].docId).remove();
			if($("#b_loan_attachments_table tbody").children().length == 1)
				$("#b_loan_attachments_table").html("");
			loanAccAttachments[index] = null;
			trackedLoanAcc.attachments = loanAccAttachments;
		}, function(xhr) {
			showErrorMsgBankLoan("");
			handleErrorAndDisplayMsg(xhr, "b_loan_err_msg");
		});
	}
}