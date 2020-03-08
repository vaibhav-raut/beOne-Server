/**
 * 
 */
var titles, bankAccTypes, searchedMembers, isMemberReg = true, memberAttachments = new Array();

$(document).ready(function() {
	loadNameTitles();
	$("#m_reg_tab").click(showMemberRegTab);
	$("#submit_btn").click(submitMemberRegData);
	$("#reset_btn").click(resetMemberRegForm);
	$("#reg_approve_btn").click(approveMemberReg);
	$("#reg_reject_btn").click(rejectMemberReg);
	$("#reg_hold_btn").click(holdMemberReg);
	$("#back2apptrac_btn1").click(back2AppTrackFromMemberReg);
	$("#roles").change(memberRoleChangeHandler);
	$("#m_name_search").click(searchMemberUsingName);
	$("#pri_mobile").change(numberFieldChangeHandler);
	$("#sec_mobile").change(numberFieldChangeHandler);
	$("#phone").change(numberFieldChangeHandler);
	$("#monthly_saving").change(numberFieldChangeHandler);
	$("#planned_inst_num").change(numberFieldChangeHandler);
	$("#pin_code").change(numberFieldChangeHandler);
	$("#m_upload_file_btn").click(uploadMemberRegAttachment);
	$("#m_reg_print").click(printTable);
	$("#m_reg_pdf").click(printTable);
	$("#m_reg_excel").click(genExcel);
	$("#mreg_bank_search").click(mregBankSearchClickHandler);
});
function loadNameTitles() {
	ajaxCall("/enum/v1/name_title", "GET", true, "", function(data, status) {
		titles = data.enumValues;
		loadMemberBankAccTypes();
	});
}
function loadMemberBankAccTypes() {
	ajaxCall("/enum/v1//enum_values/BankAccountType", "GET", true, "",
			function(data, status) {
				bankAccTypes = data.enumValues;
				loadMemberRegTab();
			});
}
function loadMemberRegTab() {
	$("#doe").val(convertTS2DDMMYYYYDate(currentServerTime));

	var rolesContent = "";
	// GroupType should be = Roles.belongTo
	for (var i = 0; i < mRoles.length; i++) {
		if (gType == mRoles[i].belongTo) {
			if ((mRoles[i].role == "Associate Member"
					&& (gType == "SHG" || gType == "Federation") && groupRules.allowAssociateMembers == 0)
					|| mRoles[i].role == "Group Secretary"
					|| mRoles[i].role == "Group Treasure"
					|| mRoles[i].role == "Group President")
				rolesContent += "";
			else
				rolesContent += "<option>" + mRoles[i].role + "</option>";
		}
	}
	$("#roles").html(rolesContent);
	memberRoleChangeHandler();

	var titleContent = "";
	for (var i = 0; i < titles.length; i++)
		titleContent += "<option>" + titles[i].title + "</option>";
	$("#name_title").html(titleContent);

	var districtContent = "", curDistrict = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (districtValues[i].state != "Admin") {
			districtContent += "<option>" + districtValues[i].district
					+ "</option>";
			if (districtValues[i].districtCode == gdCode)
				curDistrict = districtValues[i].district;
		}
	}
	$("#district").html(districtContent);
	if (curDistrict != "")
		$("#district").val(curDistrict);

	var accTypeContent = "";
	for (var i = 0; i < bankAccTypes.length; i++)
		accTypeContent += "<option>" + bankAccTypes[i].enumValue + "</option>";
	$("#acc_type").html(accTypeContent);
}
function showMemberRegTab() {
	if (isMemberReg == false) {
		resetMemberRegForm();
		resetAppActivationForm();
	}
}
function submitMemberRegData() {
	var fname = $("#first_name").val(), lname = $("#last_name").val(), address = $(
			"#address").val().replace(/\n/g, " "), doe = $("#doe").val(), mSaving = $(
			"#monthly_saving").val(), accnum = $("#acc_num").val(), accname = $(
			"#acc_name").val(), bankid = $("#mreg_bank_id").val(), mrole = $(
			"#roles option:selected").text(), mroleCategory = getMemberRoleCategory(mrole);
	if (fname == "") {
		showErrorMsgMemberReg("Please provide member's first name.");
		$("#first_name").focus();
	} else if (lname == "") {
		showErrorMsgMemberReg("Please provide member's last name.");
		$("#last_name").focus();
	} else if (address == "") {
		showErrorMsgMemberReg("Please provide member's address.");
		$("#address").focus();
	} else if (doe == "") {
		showErrorMsgMemberReg("Please provide member's date of enrollement.");
		$("#doe").focus();
	} else if (mroleCategory == "SHG Member" && mSaving == "") {
		showErrorMsgMemberReg("Please provide member's monthly saving.");
		$("#monthly_saving").focus();
	} else if (mroleCategory == "SHG Member" && accnum != "" && (accname == "" || bankid == "")) {
		showErrorMsgMemberReg("Please provide complete bank information.");
	} else if (confirm("Submit application?") === true) {
		showMsgMemberReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		$("#submit_btn").attr("disabled", "disabled");

		var dob = $("#dob").val(), doa = $("#doa").val(), savingInstNum = $(
				"#planned_inst_num").val(), priMob = $("#pri_mobile").val(), secMob = $(
				"#sec_mobile").val(), phone = $("#phone").val(), pincode = $(
				"#pin_code").val(), recomBy = $("#recommend_by_m_num").val();
		var payload = "{\"parentGroupAcNo\":" + groupAcNo + ",\"mrole\":\""
				+ mrole + "\",\"gender\":\""
				+ $("#gender option:selected").text()
				+ "\",\"dateOfEnroll\":\"" + doe + "\"";
		if (recomBy != "")
			payload += ",\"recommendedByMemberAcNo\":" + recomBy;
		if (dob != "")
			payload += ",\"dateOfBirth\":\"" + dob + "\"";
		if (doa != "")
			payload += ",\"dateOfAnni\":\"" + doa + "\"";
		if (mSaving != "")
			payload += ",\"monthlySaving\":" + mSaving;
		if (savingInstNum != "")
			payload += ",\"noPlannedSavingInst\":" + savingInstNum;

		payload += ",\"contacts\":[{\"lang\":\"English\",\"nameTitle\":\""
				+ $("#name_title option:selected").text()
				+ "\",\"firstName\":\"" + fname + "\",\"middleName\":\""
				+ $("#middle_name").val() + "\",\"lastName\":\"" + lname
				+ "\",\"address\":\"" + address + "\",\"village\":\""
				+ $("#village").val() + "\",\"grampanchayat\":\""
				+ $("#panchayat").val() + "\",\"taluka\":\""
				+ $("#tehsil").val() + "\",\"district\":\""
				+ $("#district option:selected").text() + "\",\"state\":\""
				+ $("#state option:selected").text() + "\",\"email\":\""
				+ $("#email").val() + "\"";
		if (pincode != "")
			payload += ",\"pinCode\":" + pincode;
		if (phone != "")
			payload += ",\"phone\":" + phone;
		if (priMob != "")
			payload += ",\"priMobile\":" + priMob;
		if (secMob != "")
			payload += ",\"secMobile\":" + secMob;
		payload += "}]";

		if (mroleCategory == "SHG Member" && accnum != "") {
			payload += ",\"bankAccounts\":[{\"bankProfileId\":" + bankid
					+ ",\"accountNumber\":\"" + accnum
					+ "\",\"accountName\":\"" + accname
					+ "\",\"bankAccountType\":\""
					+ $("#acc_type option:selected").text() + "\"}]";
		}
		payload += "}";

		ajaxCall("/member/v1/add_member", "PUT", true, payload, function(data,
				status) {
			showMsgMemberReg("Member Added Successfully."
					+ "<br>Member Name: <b>" + data.memberName + "</b>"
					+ "<br>Account Number: <b>"
					+ getReadableAccNum(data.memberAcNo) + "</b>");
			trackedMember = data;
			$("#m_attachment_div").show();
			disableMemberRegForm();
			loadAppTrackingTab();
		}, function(xhr) {
			$("#submit_btn").removeAttr("disabled");
			showErrorMsgMemberReg("");
			handleErrorAndDisplayMsg(xhr, "reg_err_msg");
		});
	}
}
function resetMemberRegForm() {
	isMemberReg = true;
	trackedMember = null;
	showErrorMsgMemberReg("");
	showRegDefaultButtons(true);
	$("#submit_btn").removeAttr("disabled");
	$("#recommend_by_m_name").val("");
	$("#recommend_by_m_num").val("");
	$("#doe").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#dob").val("");
	$("#doa").val("");
	$("#first_name").val("");
	$("#middle_name").val("");
	$("#last_name").val("");
	$("#address").val("");
	$("#village").val("");
	$("#panchayat").val("");
	$("#tehsil").val("");
	$("#pin_code").val("");
	$("#phone").val("");
	$("#pri_mobile").val("");
	$("#sec_mobile").val("");
	$("#email").val("");
	$("#monthly_saving").val("");
	$("#planned_inst_num").val("");
	$("#acc_num").val("");
	$("#acc_name").val("");
	$("#greg_bank_id").val("");
	$("#greg_bank_name").html(" -- ");
	$("#greg_branch_name").html(" -- ");
	$("#greg_ifsc_code").html(" -- ");

	$("#roles").removeAttr("disabled");
	$("#recommend_by_m_name").removeAttr("disabled");
	$("#m_name_search").removeAttr("disabled");
	$("#gender").removeAttr("disabled");
	$("#doe").removeAttr("disabled");
	$("#dob").removeAttr("disabled");
	$("#doa").removeAttr("disabled");
	$("#name_title").removeAttr("disabled");
	$("#first_name").removeAttr("disabled");
	$("#middle_name").removeAttr("disabled");
	$("#last_name").removeAttr("disabled");
	$("#address").removeAttr("disabled");
	$("#village").removeAttr("disabled");
	$("#panchayat").removeAttr("disabled");
	$("#tehsil").removeAttr("disabled");
	$("#pin_code").removeAttr("disabled");
	$("#district").removeAttr("disabled");
	$("#state").removeAttr("disabled");
	$("#phone").removeAttr("disabled");
	$("#pri_mobile").removeAttr("disabled");
	$("#sec_mobile").removeAttr("disabled");
	$("#email").removeAttr("disabled");
	$("#monthly_saving").removeAttr("disabled");
	$("#planned_inst_num").removeAttr("disabled");
	$("#acc_num").removeAttr("disabled");
	$("#acc_name").removeAttr("disabled");
	$("#acc_type").removeAttr("disabled");
	$("#mreg_bank_search").removeAttr("disabled");
}
function disableMemberRegForm() {
	$("#roles").attr("disabled", "disabled");
	$("#recommend_by_m_name").attr("disabled", "disabled");
	$("#m_name_search").attr("disabled", "disabled");
	$("#gender").attr("disabled", "disabled");
	$("#doe").attr("disabled", "disabled");
	$("#dob").attr("disabled", "disabled");
	$("#doa").attr("disabled", "disabled");
	$("#name_title").attr("disabled", "disabled");
	$("#first_name").attr("disabled", "disabled");
	$("#middle_name").attr("disabled", "disabled");
	$("#last_name").attr("disabled", "disabled");
	$("#address").attr("disabled", "disabled");
	$("#village").attr("disabled", "disabled");
	$("#panchayat").attr("disabled", "disabled");
	$("#tehsil").attr("disabled", "disabled");
	$("#pin_code").attr("disabled", "disabled");
	$("#district").attr("disabled", "disabled");
	$("#state").attr("disabled", "disabled");
	$("#phone").attr("disabled", "disabled");
	$("#pri_mobile").attr("disabled", "disabled");
	$("#sec_mobile").attr("disabled", "disabled");
	$("#email").attr("disabled", "disabled");
	$("#monthly_saving").attr("disabled", "disabled");
	$("#planned_inst_num").attr("disabled", "disabled");
	$("#acc_num").attr("disabled", "disabled");
	$("#acc_name").attr("disabled", "disabled");
	$("#acc_type").attr("disabled", "disabled");
	$("#mreg_bank_search").attr("disabled", "disabled");
}
function memberRoleChangeHandler() {
	var mrole = $("#roles option:selected").text();
	if ((gType == "SHG" || gType == "Federation") && getMemberRoleCategory(mrole) == "SHG Member") {
		$("#saving_info_div").show();
		$("#bank_info_div").show();
	} else {
		$("#saving_info_div").hide();
		$("#bank_info_div").hide();
		$("#monthly_saving").val("");
		$("#planned_inst_num").val("");
	}
}
function searchMemberUsingName() {
	$("#recommend_by_m_num").val("");
	var memberName = $("#recommend_by_m_name").val();
	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching();
		ajaxCall("/member/v1/member_search_by_name/"
				+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
				+ memberName, "GET", true, "", function(data, status) {
			searchedMembers = data.memberNames;
			loadMemberSearchData(searchedMembers);

			for (var i = 0; i < data.memberNames.length; i++) {
				$("#member_" + i).click(loadSelectedMember);
			}
		}, memberSearchFailed);
	}
}
function loadSelectedMember() {
	hideMemberSearchDialog();
	var index = parseInt($(this).data("id"));
	var memberName = searchedMembers[index].memberName;
	var memberAcNo = searchedMembers[index].memberAcNo;
	$("#recommend_by_m_name").val(memberName);
	$("#recommend_by_m_num").val(memberAcNo);
}
function mregBankSearchClickHandler() {
	bankSearchedFor = "member-reg";
	loadBankSearchDialog();
	resetBankSearchDialog();
}
function showMsgMemberReg(msg) {
	$("#reg_msg").html(msg);
	$("#reg_err_msg").hide();
	$("#reg_msg").show();
}
function showErrorMsgMemberReg(msg) {
	$("#reg_err_msg").html(msg);
	$("#reg_msg").hide();
	$("#reg_err_msg").show();
}
function showRegDefaultButtons(isdefault) {
	$("#reg_activate_btn").hide();
	$("#go2mclosure_btns").hide();
	$("#mclosure_back_btn").hide();
	if (isdefault) {
		$("#reg_approval_btns").hide();
		$("#reg_back_btn").hide();
		$("#reg_default_btns").show();
	} else {
		$("#reg_default_btns").hide();
		$("#reg_back_btn").show();
		$("#reg_approval_btns").show();
	}
}
function showRegActivateBackButton() {
	$("#reg_default_btns").hide();
	$("#reg_approval_btns").hide();
	$("#go2mclosure_btns").hide();
	$("#mclosure_back_btn").hide();
	$("#reg_activate_btn").show();
	$("#reg_back_btn").show();
}
function showRegBackButtonOnly() {
	$("#reg_default_btns").hide();
	$("#reg_approval_btns").hide();
	$("#reg_activate_btn").hide();
	$("#go2mclosure_btns").hide();
	$("#mclosure_back_btn").hide();
	$("#reg_back_btn").show();
}
function showMemberClosureButton() {
	$("#reg_default_btns").hide();
	$("#reg_approval_btns").hide();
	$("#reg_activate_btn").hide();
	$("#reg_back_btn").hide();
	$("#go2mclosure_btns").show();
	$("#mclosure_back_btn").show();
}
function approveMemberReg() {
	updateMemberRegApplicationStatus("Approved");
}
function rejectMemberReg() {
	updateMemberRegApplicationStatus("Rejected");
}
function holdMemberReg() {
	updateMemberRegApplicationStatus("On Hold");
}
function back2AppTrackFromMemberReg() {
	isMemberReg = true;
	loadAppTrackingTab();
	resetMemberRegForm();
}
function updateMemberRegApplicationStatus(status) {
	if (status != "Under Review") {
		if (confirm("Are you sure to mark application to " + status + "?") === false)
			return;
		showMsgMemberReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		$("#reg_approve_btn").attr("disabled", "disabled");
		$("#reg_reject_btn").attr("disabled", "disabled");
		$("#reg_hold_btn").attr("disabled", "disabled");
	}
	trackedMember.approvalStatus = status;
	trackedMember.approvedByMemberAcNo = curMemberAcNo;
	trackedMember.attachments = null;
	var payload = JSON.stringify(trackedMember);
	ajaxCall("/member/v1/approve_member", "POST", true, payload, function(data,
			status) {
		if (data != "") {
			showMsgMemberReg("Updated Application Status: <b>"
					+ data.approvalStatus + "</b>" + "<br>Member Ac No: <b>"
					+ getReadableAccNum(data.memberAcNo) + "</b>");
			if (data.approvalStatus == "Approved")
				showRegActivateBackButton();
			trackedMember = data;
			trackedMember.attachments = memberAttachments;
			loadAppTrackingTab();
			$("#reg_approve_btn").removeAttr("disabled");
			$("#reg_reject_btn").removeAttr("disabled");
			$("#reg_hold_btn").removeAttr("disabled");
		}
	}, function(xhr) {
		$("#reg_approve_btn").removeAttr("disabled");
		$("#reg_reject_btn").removeAttr("disabled");
		$("#reg_hold_btn").removeAttr("disabled");
		showErrorMsgMemberReg("");
		handleErrorAndDisplayMsg(xhr, "reg_err_msg");
	});
}
function showMemberDetailsForActivation() {
	isMemberReg = false;
	var index = parseInt($(this).data("id"));
	if (!isNaN(index))
		trackedMember = appTrackingData.members[index];
	if (trackedMember.approvalStatus == "Active"
			|| trackedMember.approvalStatus == "Rejected")
		showRegBackButtonOnly();
	else if (trackedMember.approvalStatus == "Approved")
		showRegActivateBackButton();
	else
		showRegDefaultButtons(false);
	showMemberRegDetails(trackedMember);
	if (trackedMember.approvalStatus == "Submitted")
		updateMemberRegApplicationStatus("Under Review");
}
function showMemberDetailsForClosure() {
	isMemberReg = false;
	showMemberClosureButton();
	if(trackedMember.activeStatus != "Active")
		$("#go2mclosure_btns").hide();
	showMemberRegDetails(trackedMember);
}
function showMemberRegDetails(memberData) {
	showMsgMemberReg("Member Ac No: <b>" + getReadableAccNum(memberData.memberAcNo)
	        + "</b><br>Application Status: <b>" + memberData.approvalStatus
			+ "</b> | Active Status: <b>" + memberData.activeStatus + "</b>");
	disableMemberRegForm();
	$("#roles").val(memberData.mrole);
	memberRoleChangeHandler();
	$("#recommend_by_m_name").val(memberData.recommendedByMemberName);
	$("#recommend_by_m_num").val(memberData.recommendedByMemberAcNo);
	$("#gender").val(memberData.gender);
	$("#doe").val(memberData.dateOfEnroll);
	$("#dob").val(memberData.dateOfBirth);
	$("#doa").val(memberData.dateOfAnni);
	if (memberData.contacts != null && memberData.contacts[0] != null) {
		$("#name_title").val(memberData.contacts[0].nameTitle);
		$("#first_name").val(memberData.contacts[0].firstName);
		$("#middle_name").val(memberData.contacts[0].middleName);
		$("#last_name").val(memberData.contacts[0].lastName);
		$("#address").val(memberData.contacts[0].address);
		$("#village").val(memberData.contacts[0].village);
		$("#panchayat").val(memberData.contacts[0].grampanchayat);
		$("#tehsil").val(memberData.contacts[0].taluka);
		$("#pin_code").val(memberData.contacts[0].pinCode);
		$("#district").val(memberData.contacts[0].district);
		$("#state").val(memberData.contacts[0].state);
		$("#phone").val(memberData.contacts[0].phone);
		$("#pri_mobile").val(memberData.contacts[0].priMobile);
		$("#sec_mobile").val(memberData.contacts[0].secMobile);
		$("#email").val(memberData.contacts[0].email);
	}
	if (memberData.account != null)
		$("#monthly_saving").val(memberData.account.plannedMonthlySavingAm);
	$("#planned_inst_num").val(memberData.noPlannedSavingInst);
	if (memberData.bankAccounts != null && memberData.bankAccounts[0] != null) {
		$("#acc_num").val(memberData.bankAccounts[0].accountNumber);
		$("#acc_name").val(memberData.bankAccounts[0].accountName);
		$("#acc_type").val(memberData.bankAccounts[0].bankAccountType);
		$("#mreg_bank_name").html(memberData.bankAccounts[0].bankName);
		$("#mreg_branch_name").html(memberData.bankAccounts[0].bankBranchName);
		$("#mreg_ifsc_code").html(memberData.bankAccounts[0].ifcsCode);
	}
	memberAttachments = memberData.attachments;
	reloadMemberAttachmentTable();
}
function reloadMemberAttachmentTable() {
	var content = "";
	for (var i = 0; i < memberAttachments.length; i++) {
		if(memberAttachments[i] != null) {
			content += "<tr id='m_doc_row" + memberAttachments[i].docId + "'><td>" + docTypes[memberAttachments[i].docType.docTypeId - 1].docCategory
					+ "</td><td>" + docTypes[memberAttachments[i].docType.docTypeId - 1].docType
					+ "</td><td>" + memberAttachments[i].fileName
					+ "</td><td width='50'><button class='btn yellow aboutme' data-id='" + i + "' id='m_edit_doc_btn" + memberAttachments[i].docId + "'>Change</button>"
					+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='m_del_doc_btn" + memberAttachments[i].docId + "'>Delete</button></td></tr>";
		}
	}
	if (content != "") {
		var attachmentTableHeader = "<tr><td><strong>Document Type</strong></td><td><strong>Document Category</strong></td><td><strong>File Name</strong></td><td></td><td></td></tr>";
		$("#m_attachments_table").html(attachmentTableHeader + content);
		for (var i = 0; i < memberAttachments.length; i++) {
			if(memberAttachments[i] != null) {
				$("#m_edit_doc_btn" + memberAttachments[i].docId).click(editMemberRegAttachment);
				$("#m_del_doc_btn" + memberAttachments[i].docId).click(deleteMemberRegAttachment);
			}
		}
	}
	$("#m_attachment_div").show();
}
function uploadMemberRegAttachment() {
	var uri = (appBaseURL + "/member/v1/attach_file/" + trackedMember.memberAcNo);
	loadDocumentUploadDlg("Member", uri, function(attachment) {
		memberAttachments[memberAttachments.length] = attachment;
		trackedMember.attachments = memberAttachments;
		reloadMemberAttachmentTable();
	});
}
function editMemberRegAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/member/v1/update_attach_file/" + trackedMember.memberAcNo + "/" + memberAttachments[index].docId);
	loadEditUploadedDocumentDlg("Member", uri, memberAttachments[index], function(attachment) {
		memberAttachments[index] = attachment;
		reloadMemberAttachmentTable();
	});
}
function deleteMemberRegAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/member/v1/delete_attach_file/" + trackedMember.memberAcNo + "/" + memberAttachments[index].docId,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#m_doc_row" + memberAttachments[index].docId).remove();
			if($("#m_attachments_table tbody").children().length == 1)
				$("#m_attachments_table").html("");
			memberAttachments[index] = null;
			trackedMember.attachments = memberAttachments;
		}, function(xhr) {
			showErrorMsgMemberReg("");
			handleErrorAndDisplayMsg(xhr, "m_reg_err_msg");
		});
	}
}