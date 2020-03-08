/**
 * 
 */
var isGroupReg = true, groupAttachments = new Array();
$(document).ready(function() {
	loadGroupTypes();
	$("#g_reg_tab").click(showGroupRegTab);
	$("#g_submit_btn").click(submitGroupRegData);
	$("#g_reset_btn").click(resetGroupRegForm);
	$("#g_reg_approve_btn").click(approveGroupReg);
	$("#g_reg_reject_btn").click(rejectGroupReg);
	$("#g_reg_hold_btn").click(holdGroupReg);
	$("#g_back2apptrac_btn1").click(back2AppTrackFromGroupReg);
	$("#group_types").change(groupTypeChangeHandler);
	$("#g_pin_code").change(numberFieldChangeHandler);
	$("#plan_m_saving").change(numberFieldChangeHandler);
	$("#s_interest").change(numberFieldChangeHandler);
	$("#l_interest").change(numberFieldChangeHandler);
	$("#l_fee_percent").change(numberFieldChangeHandler);
	$("#reg_fee").change(numberFieldChangeHandler);
	$("#late_fee").change(numberFieldChangeHandler);
	$("#cheque_b_fee").change(numberFieldChangeHandler);
	$("#app_fee").change(numberFieldChangeHandler);
	$("#g_upload_file_btn").click(uploadGroupRegAttachment);
	$("#g_reg_print").click(printTable);
	$("#g_reg_pdf").click(printTable);
	$("#g_reg_excel").click(genExcel);
	$("#greg_bank_search").click(gregBankSearchClickHandler);
	$("#g_sel_district_btn").click(selectDistrictForGroupReg);
});
function loadGroupTypes() {
	ajaxCall("/enum/v1/group_type", "GET", true, "", function(data, status) {
		var groupTypes = data.enumValues;
		var gTypeContent = "";
		for (var i = 0; i < groupTypes.length; i++) {
			if (groupTypes[i].category != "Admin") {
				gTypeContent += "<option>" + groupTypes[i].groupType
						+ "</option>";
			}
		}
		$("#group_types").html(gTypeContent);
		groupTypeChangeHandler();
		loadBankAccTypes();
	});
}
function loadBankAccTypes() {
	ajaxCall("/enum/v1/enum_values/BankAccountType", "GET", true, "", function(
			data, status) {
		var bankAccTypes = data.enumValues;
		var accTypeContent = "";
		for (var i = 0; i < bankAccTypes.length; i++)
			accTypeContent += "<option>" + bankAccTypes[i].enumValue
					+ "</option>";
		$("#g_acc_type").html(accTypeContent);
		loadGroupRegTab();
	});
}
function loadGroupRegTab() {
	$("#plan_m_saving").val("100");
	$("#s_interest").val("12");
	$("#l_interest").val("24");
	$("#l_fee_percent").val("1.0");
	$("#reg_fee").val("100");
	$("#late_fee").val("20");
	$("#cheque_b_fee").val("500");
	$("#app_fee").val("0");
}
function selectDistrictForGroupReg() {
	loadSelectDistrictDialog("g_state", "g_district");
}
function showGroupRegTab() {
	if (isGroupReg == false) {
		resetGroupRegForm();
		// resetDistrictAppActivationForm();
	}
}
function submitGroupRegData() {
	var doe = $("#g_doe").val(), gname = $("#group_name").val().replace(/\n/g,
			" "), grpType = $("#group_types option:selected").text(), address = $(
			"#g_address").val().replace(/\n/g, " "), pincode = $("#g_pin_code")
			.val(), priMob = $("#g_pri_mobile").val(), grpRulesJSON = "", bankAccJSON = "";
	if (doe == "") {
		showErrorMsgGroupReg("Please provide Date of Establishment.");
		$("#g_doe").focus();
	} else if (grpType != "Bank" && gname == "") {
		showErrorMsgGroupReg("Please provide a Group Name.");
		$("#group_name").focus();
	} else if (address == "") {
		showErrorMsgGroupReg("Please provide Address.");
		$("#g_address").focus();
	} else if (pincode == "") {
		showErrorMsgGroupReg("Please provide pin code.");
		$("#g_pin_code").focus();
	} else if (priMob == "") {
		showErrorMsgGroupReg("Please provide Primary Mobile.");
		$("#g_pri_mobile").focus();
	} else {
		if (grpType == "SHG" || grpType == "Federation") {
			var mSaving = $("#plan_m_saving").val(), sInerest = $("#s_interest")
					.val(), lInerest = $("#l_interest").val(), lFeePercent = $(
					"#l_fee_percent").val(), regFee = $("#reg_fee").val(), lateFee = $(
					"#late_fee").val(), chequeBFee = $("#cheque_b_fee").val(), appFee = $(
					"#app_fee").val(), allowAssociate = $("#allow_associate")
					.val();
			if (mSaving == "" || sInerest == "" || lInerest == ""
					|| lFeePercent == "" || regFee == "" || lateFee == ""
					|| chequeBFee == "" || appFee == "") {
				showErrorMsgGroupReg("Please fill Group Rules fields.");
				return;
			}
			var bankProfileId = $("#greg_bank_id").val();
			if (bankProfileId == "") {
				showErrorMsgGroupReg("Please search bank for Bank Account Information.");
				return;
			}
			var accnum = $("#g_acc_num").val();
			if (accnum == "") {
				showErrorMsgGroupReg("Please provide Bank Account Number.");
				$("#g_acc_num").focus();
				return;
			}
			var accname = $("#g_acc_name").val();
			if (accname == "") {
				showErrorMsgGroupReg("Please provide Bank Account Name.");
				$("#g_acc_name").focus();
				return;
			}
			grpRulesJSON = ",\"groupRules\":{\"cmMonthlySaving\":" + mSaving
					+ ",\"allowAssociateMembers\":" + allowAssociate
					+ ",\"cmIntOnSaving\":" + sInerest
					+ ",\"cmBaseIntOnLoan\":" + lInerest
					+ ",\"cmLoanProcessingFeePercent\":" + lFeePercent
					+ ",\"cmRegistrationFee\":" + regFee
					+ ",\"amIntOnSaving\":" + sInerest
					+ ",\"amBaseIntOnLoan\":" + lInerest
					+ ",\"amLoanProcessingFeePercent\":" + lFeePercent
					+ ",\"amRegistrationFee\":" + regFee
					+ ",\"cmSavingLateFee\":" + lateFee
					+ ",\"amSavingLateFee\":" + lateFee + ",\"amLoanLateFee\":"
					+ lateFee + ",\"cmLoanLateFee\":" + lateFee
					+ ",\"chequeBouncingPenalty\":" + chequeBFee
					+ ",\"cmApplicationFee\":" + appFee
					+ ",\"amApplicationFee\":" + appFee + "}";
			bankAccJSON = ",\"bankAccounts\":[{\"bankProfileId\":"
					+ bankProfileId + ",\"accountNumber\":\"" + accnum
					+ "\",\"accountName\":\"" + accname
					+ "\",\"bankAccountType\":\""
					+ $("#g_acc_type option:selected").text() + "\"}]";
		}
		var selectedDistrict = $("#g_district").val();
		var bankProfileJSON = "";
		if (grpType == "Bank") {
			var bankname = $("#gbp_name").val();
			if (bankname == "") {
				showErrorMsgGroupReg("Please provide Bank Name.");
				$("#gbp_name").focus();
				return;
			}
			var branchname = $("#gbp_branch_name").val();
			if (branchname == "") {
				showErrorMsgGroupReg("Please provide Branch Name.");
				$("#gbp_branch_name").focus();
				return;
			}
			var ifscode = $("#gbp_ifsc_code").val();
			if (ifscode == "") {
				showErrorMsgGroupReg("Please provide IFSC Code.");
				$("#gbp_ifsc_code").focus();
				return;
			}
			var districtCode = "";
			for (var i = 0; i < districtValues.length; i++) {
				if (selectedDistrict == districtValues[i].district) {
					districtCode = districtValues[i].districtCode;
					break;
				}
			}
			gname = branchname + " - " + bankname;
			bankProfileJSON = ",\"bankProfile\":{\"districtCode\":\""
					+ districtCode + "\",\"bankName\":\"" + bankname
					+ "\",\"branchName\":\"" + branchname
					+ "\",\"ifcsCode\":\"" + ifscode
					+ "\",\"bankLoanIntRate\":\"" + $("#gbp_loan_rate").val()
					+ "\",\"bankFdIntRate\":\"" + $("#gbp_fd_rate").val()
					+ "\",\"bankRating\":\"" + $("#gbp_rating").val() + "\"}";
		}
		if (confirm("Submit application?") === true) {
			showMsgGroupReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
			$("#g_submit_btn").attr("disabled", "disabled");
	
			var vision = $("#group_vision").val().replace(/\n/g, " ");
			var description = $("#group_desc").val().replace(/\n/g, " ");
			var payload = "{\"groupName\":\"" + gname + "\",\"vision\":\"" + vision
					+ "\",\"description\":\"" + description + "\",\"groupType\":\""
					+ grpType + "\",\"dateOfEstablishment\":\"" + doe
					+ "\",\"contacts\":[{\"lang\":\"English\",\"name\":\"" + gname
					+ "\",\"description\":\"" + description + "\",\"vision\":\""
					+ vision + "\",\"address\":\"" + address + "\",\"village\":\""
					+ $("#g_village").val() + "\",\"grampanchayat\":\""
					+ $("#g_panchayat").val() + "\",\"taluka\":\""
					+ $("#g_tehsil").val() + "\",\"district\":\""
					+ selectedDistrict + "\",\"state\":\""
					+ $("#g_state").val() + "\",\"email\":\""
					+ $("#g_email").val() + "\",\"pinCode\":\"" + pincode
					+ "\",\"priMobile\":\"" + priMob + "\",\"phone\":\""
					+ $("#g_phone").val() + "\",\"secMobile\":\""
					+ $("#g_sec_mobile").val() + "\"}]" + grpRulesJSON
					+ bankAccJSON + bankProfileJSON + ",\"mappingMemberAcNos\":[\""
					+ curMemberAcNo + "\"]}";
	
			ajaxCall("/group/v1/add_group", "PUT", true, payload, function(data,
					status) {
				showMsgGroupReg("Group Added Successfully."
						+ "<br>Group Account Number: <b>"
						+ getReadableAccNumForDistrict(data.districtCode,
								data.groupAcNo) + "</b><br>Group Name: <b>"
						+ data.contacts[0].name + "</b>");
				disableGroupRegForm();
				trackedGroup = data;
				$("#g_attachment_div").show();
			}, function(xhr) {
				$("#g_submit_btn").removeAttr("disabled");
				showErrorMsgGroupReg("");
				handleErrorAndDisplayMsg(xhr, "g_reg_err_msg");
			});
		}
	}
}
function showMsgGroupReg(msg) {
	$("#g_reg_msg").html(msg);
	$("#g_reg_err_msg").hide();
	$("#g_reg_msg").show();
}
function showErrorMsgGroupReg(msg) {
	$("#g_reg_err_msg").html(msg);
	$("#g_reg_msg").hide();
	$("#g_reg_err_msg").show();
}
function resetGroupRegForm() {
	isGroupReg = true;
	trackedGroup = null;
	showGRegDefaultButtons(true);
	showErrorMsgGroupReg("");
	$("#g_submit_btn").removeAttr("disabled");
	$("#g_doe").val("");
	$("#group_name").val("");
	$("#group_vision").val("");
	$("#group_desc").val("");
	$("#g_address").val("");
	$("#g_village").val("");
	$("#g_panchayat").val("");
	$("#g_tehsil").val("");
	$("#g_pin_code").val("");
	$("#g_district").val("");
	$("#g_state").val("");
	$("#g_phone").val("");
	$("#g_pri_mobile").val("");
	$("#g_sec_mobile").val("");
	$("#g_email").val("");
	$("#g_acc_num").val("");
	$("#g_acc_name").val("");
	$("#greg_bank_id").val("");
	$("#g_bank_name").html(" -- ");
	$("#g_branch_name").html(" -- ");
	$("#g_ifsc_code").html(" -- ");
	$("#gbp_name").val("");
	$("#gbp_branch_name").val("");
	$("#gbp_ifsc_code").val("");
	$("#gbp_loan_rate").val("");
	$("#gbp_fd_rate").val("");
	$("#gbp_rating").val("");

	$("#group_types").removeAttr("disabled");
	$("#g_doe").removeAttr("disabled");
	$("#group_name").removeAttr("disabled");
	$("#group_vision").removeAttr("disabled");
	$("#group_desc").removeAttr("disabled");
	$("#g_address").removeAttr("disabled");
	$("#g_village").removeAttr("disabled");
	$("#g_panchayat").removeAttr("disabled");
	$("#g_tehsil").removeAttr("disabled");
	$("#g_pin_code").removeAttr("disabled");
	$("#g_phone").removeAttr("disabled");
	$("#g_pri_mobile").removeAttr("disabled");
	$("#g_sec_mobile").removeAttr("disabled");
	$("#g_email").removeAttr("disabled");
	$("#allow_associate").removeAttr("disabled");
	$("#plan_m_saving").removeAttr("disabled");
	$("#s_interest").removeAttr("disabled");
	$("#l_interest").removeAttr("disabled");
	$("#l_fee_percent").removeAttr("disabled");
	$("#reg_fee").removeAttr("disabled");
	$("#late_fee").removeAttr("disabled");
	$("#cheque_b_fee").removeAttr("disabled");
	$("#app_fee").removeAttr("disabled");
	$("#g_acc_num").removeAttr("disabled");
	$("#g_acc_name").removeAttr("disabled");
	$("#g_acc_type").removeAttr("disabled");
	$("#g_sel_district_btn").removeAttr("disabled");
	$("#greg_bank_search").removeAttr("disabled");
	$("#gbp_name").removeAttr("disabled");
	$("#gbp_branch_name").removeAttr("disabled");
	$("#gbp_ifsc_code").removeAttr("disabled");
	$("#gbp_loan_rate").removeAttr("disabled");
	$("#gbp_fd_rate").removeAttr("disabled");
	$("#gbp_rating").removeAttr("disabled");
	loadGroupRegTab();
	
	$("#g_attachment_div").hide();
	$("#g_attachments_table").empty();
}
function disableGroupRegForm() {
	$("#group_types").attr("disabled", "disabled");
	$("#g_doe").attr("disabled", "disabled");
	$("#group_name").attr("disabled", "disabled");
	$("#group_vision").attr("disabled", "disabled");
	$("#group_desc").attr("disabled", "disabled");
	$("#g_address").attr("disabled", "disabled");
	$("#g_village").attr("disabled", "disabled");
	$("#g_panchayat").attr("disabled", "disabled");
	$("#g_tehsil").attr("disabled", "disabled");
	$("#g_pin_code").attr("disabled", "disabled");
	$("#g_phone").attr("disabled", "disabled");
	$("#g_pri_mobile").attr("disabled", "disabled");
	$("#g_sec_mobile").attr("disabled", "disabled");
	$("#g_email").attr("disabled", "disabled");
	$("#allow_associate").attr("disabled", "disabled");
	$("#plan_m_saving").attr("disabled", "disabled");
	$("#s_interest").attr("disabled", "disabled");
	$("#l_interest").attr("disabled", "disabled");
	$("#l_fee_percent").attr("disabled", "disabled");
	$("#reg_fee").attr("disabled", "disabled");
	$("#late_fee").attr("disabled", "disabled");
	$("#cheque_b_fee").attr("disabled", "disabled");
	$("#app_fee").attr("disabled", "disabled");
	$("#g_acc_num").attr("disabled", "disabled");
	$("#g_acc_name").attr("disabled", "disabled");
	$("#g_acc_type").attr("disabled", "disabled");
	$("#g_sel_district_btn").attr("disabled", "disabled");
	$("#greg_bank_search").attr("disabled", "disabled");
	$("#gbp_name").attr("disabled", "disabled");
	$("#gbp_branch_name").attr("disabled", "disabled");
	$("#gbp_ifsc_code").attr("disabled", "disabled");
	$("#gbp_loan_rate").attr("disabled", "disabled");
	$("#gbp_fd_rate").attr("disabled", "disabled");
	$("#gbp_rating").attr("disabled", "disabled");
}
function groupTypeChangeHandler() {
	var grpType = $("#group_types option:selected").text();
	if (grpType != "SHG" && grpType != "Federation") {
		$("#group_rules_div").hide();
		$("#g_bank_acc_div").hide();
		if (grpType == "Bank") {
			$("#g_bprofile_div").show();
			$("#grp_name_row").hide();
		} else {
			$("#g_bprofile_div").hide();
			$("#grp_name_row").show();
		}
	} else {
		$("#group_rules_div").show();
		$("#g_bank_acc_div").show();
		$("#g_bprofile_div").hide();
		$("#grp_name_row").show();
	}
}
function gregBankSearchClickHandler() {
	bankSearchedFor = "group-reg";
	loadBankSearchDialog();
	resetBankSearchDialog();
}
function showGRegDefaultButtons(isdefault) {
	$("#g_reg_activate_btn").hide();
	if (isdefault) {
		$("#g_reg_approval_btns").hide();
		$("#g_reg_back_btn").hide();
		$("#g_reg_default_btns").show();
	} else {
		$("#g_reg_default_btns").hide();
		$("#g_reg_back_btn").show();
		$("#g_reg_approval_btns").show();
	}
}
function showGRegActivateBackButton() {
	$("#g_reg_default_btns").hide();
	$("#g_reg_approval_btns").hide();
	$("#g_reg_activate_btn").show();
	$("#g_reg_back_btn").show();
}
function showGRegBackButtonOnly() {
	$("#g_reg_default_btns").hide();
	$("#g_reg_approval_btns").hide();
	$("#g_reg_activate_btn").hide();
	$("#g_reg_back_btn").show();
}
function approveGroupReg() {
	updateGroupRegApplicationStatus("Approved");
}
function rejectGroupReg() {
	updateGroupRegApplicationStatus("Rejected");
}
function holdGroupReg() {
	updateGroupRegApplicationStatus("On Hold");
}
function back2AppTrackFromGroupReg() {
	isGroupReg = true;
	loadDistrictApplications();
	resetGroupRegForm();
}
function updateGroupRegApplicationStatus(status) {
	if (status != "Under Review") {
		if (confirm("Are you sure to mark application to " + status + "?") === false)
			return;
		showMsgGroupReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		$("#g_reg_approve_btn").attr("disabled", "disabled");
		$("#g_reg_reject_btn").attr("disabled", "disabled");
		$("#g_reg_hold_btn").attr("disabled", "disabled");
	}
	trackedGroup.approvalStatus = status;
	trackedGroup.approvedByMember = curMemberAcNo;
	trackedGroup.attachments = null;
	var payload = JSON.stringify(trackedGroup);
	ajaxCall("/group/v1/approve_reject", "POST", true, payload, function(data,
			status) {
		showMsgGroupReg("Updated Application Status: <b>" + data.approvalStatus
				+ "</b>" + "<br>Group Ac No: <b>"
				+ getReadableAccNumForDistrict(data.districtCode, data.groupAcNo) + "</b>");
		if (data.approvalStatus == "Approved")
			showGRegActivateBackButton();
		trackedGroup = data;
		trackedGroup.attachments = groupAttachments;
		loadDistrictApplications();
		$("#g_reg_approve_btn").removeAttr("disabled");
		$("#g_reg_reject_btn").removeAttr("disabled");
		$("#g_reg_hold_btn").removeAttr("disabled");
	}, function(xhr) {
		$("#g_reg_approve_btn").removeAttr("disabled");
		$("#g_reg_reject_btn").removeAttr("disabled");
		$("#g_reg_hold_btn").removeAttr("disabled");
		showErrorMsgGroupReg("");
		handleErrorAndDisplayMsg(xhr, "g_reg_err_msg");
	});
}
function showGroupDetailsForActivation() {
	isGroupReg = false;
	var index = parseInt($(this).data("id"));
	if (!isNaN(index))
		trackedGroup = distApplications.groups[index];
	if (trackedGroup.approvalStatus == "Active"
			|| trackedGroup.approvalStatus == "Rejected")
		showGRegBackButtonOnly();
	else if (trackedGroup.approvalStatus == "Approved")
		showGRegActivateBackButton();
	else
		showGRegDefaultButtons(false);

	showMsgGroupReg("Application Status: <b>" + trackedGroup.approvalStatus
			+ "</b>" + "<br>Group Ac No: <b>"
			+ getReadableAccNumForDistrict(trackedGroup.districtCode, trackedGroup.groupAcNo) + "</b>");
	disableGroupRegForm();

	$("#group_types").val(trackedGroup.groupType);
	$("#g_doe").val(trackedGroup.dateOfEstablishment);
	$("#group_name").val(trackedGroup.groupName);
	$("#group_vision").val(trackedGroup.vision);
	$("#group_desc").val(trackedGroup.description);
	if (trackedGroup.contacts != null && trackedGroup.contacts[0] != null) {
		$("#g_address").val(trackedGroup.contacts[0].address);
		$("#g_village").val(trackedGroup.contacts[0].village);
		$("#g_panchayat").val(trackedGroup.contacts[0].grampanchayat);
		$("#g_tehsil").val(trackedGroup.contacts[0].taluka);
		$("#g_pin_code").val(trackedGroup.contacts[0].pinCode);
		$("#g_district").val(trackedGroup.contacts[0].district);
		$("#g_state").val(trackedGroup.contacts[0].state);
		$("#g_phone").val(trackedGroup.contacts[0].phone);
		$("#g_pri_mobile").val(trackedGroup.contacts[0].priMobile);
		$("#g_sec_mobile").val(trackedGroup.contacts[0].secMobile);
		$("#g_email").val(trackedGroup.contacts[0].email);
	}

	if (trackedGroup.groupRules != null) {
		if (trackedGroup.groupRules.allowAssociateMembers == 1)
			$("#allow_associate").val("Yes");
		else
			$("#allow_associate").val("No");
		$("#plan_m_saving").val(trackedGroup.groupRules.cmMonthlySaving);
		$("#s_interest").val(trackedGroup.groupRules.cmIntOnSaving);
		$("#l_interest").val(trackedGroup.groupRules.cmBaseIntOnLoan);
		$("#l_fee_percent").val(
				trackedGroup.groupRules.cmLoanProcessingFeePercent);
		$("#reg_fee").val(trackedGroup.groupRules.cmRegistrationFee);
		$("#late_fee").val(trackedGroup.groupRules.cmSavingLateFee);
		$("#cheque_b_fee").val(trackedGroup.groupRules.chequeBouncingPenalty);
		$("#app_fee").val(trackedGroup.groupRules.cmApplicationFee);
	}
	if (trackedGroup.bankAccounts != null
			&& trackedGroup.bankAccounts[0] != null) {
		$("#g_acc_num").val(trackedGroup.bankAccounts[0].accountNumber);
		$("#g_acc_name").val(trackedGroup.bankAccounts[0].accountName);
		$("#g_acc_type").val(trackedGroup.bankAccounts[0].bankAccountType);
		$("#g_bank_name").html(trackedGroup.bankAccounts[0].bankName);
		$("#g_branch_name")
				.html(trackedGroup.bankAccounts[0].bankBranchName);
		$("#g_ifsc_code").html(trackedGroup.bankAccounts[0].ifcsCode);
	}
	if (trackedGroup.approvalStatus == "Submitted")
		updateGroupRegApplicationStatus("Under Review");

	groupAttachments = trackedGroup.attachments;
	reloadGroupAttachmentTable();
}
function reloadGroupAttachmentTable() {
	var content = "";
	for (var i = 0; i < groupAttachments.length; i++) {
		if(groupAttachments[i] != null) {
			content += "<tr id='g_doc_row" + groupAttachments[i].docId + "'><td>" + docTypes[groupAttachments[i].docType.docTypeId - 1].docCategory
					+ "</td><td>" + docTypes[groupAttachments[i].docType.docTypeId - 1].docType
					+ "</td><td>" + groupAttachments[i].fileName
					+ "</td><td width='50'><button class='btn yellow aboutme' data-id='" + i + "' id='g_edit_doc_btn" + groupAttachments[i].docId + "'>Change</button>"
					+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='g_del_doc_btn" + groupAttachments[i].docId + "'>Delete</button></td></tr>";
		}
	}
	if (content != "") {
		var attachmentTableHeader = "<tr><td><strong>Document Type</strong></td><td><strong>Document Category</strong></td><td><strong>File Name</strong></td><td></td><td></td></tr>";
		$("#g_attachments_table").html(attachmentTableHeader + content);
		for (var i = 0; i < groupAttachments.length; i++) {
			if(groupAttachments[i] != null) {
				$("#g_edit_doc_btn" + groupAttachments[i].docId).click(editGroupRegAttachment);
				$("#g_del_doc_btn" + groupAttachments[i].docId).click(deleteGroupRegAttachment);
			}
		}
	}
	$("#g_attachment_div").show();
}
function uploadGroupRegAttachment() {
	var uri = (appBaseURL + "/group/v1/attach_file/" + trackedGroup.groupAcNo);
	loadDocumentUploadDlg("Group", uri, function(attachment) {
		groupAttachments[groupAttachments.length] = attachment;
		trackedGroup.attachments = groupAttachments;
		reloadGroupAttachmentTable();
	});
}
function editGroupRegAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/group/v1/update_attach_file/" + trackedGroup.groupAcNo + "/" + groupAttachments[index].docId);
	loadEditUploadedDocumentDlg("Group", uri, groupAttachments[index], function(attachment) {
		groupAttachments[index] = attachment;
		reloadGroupAttachmentTable();
	});
}
function deleteGroupRegAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/group/v1/delete_attach_file/" + trackedGroup.groupAcNo + "/" + groupAttachments[index].docId,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#g_doc_row" + groupAttachments[index].docId).remove();
			if($("#g_attachments_table tbody").children().length == 1)
				$("#g_attachments_table").html("");
			groupAttachments[index] = null;
			trackedGroup.attachments = groupAttachments;
		}, function(xhr) {
			showErrorMsgGroupReg("");
			handleErrorAndDisplayMsg(xhr, "g_reg_err_msg");
		});
	}
}