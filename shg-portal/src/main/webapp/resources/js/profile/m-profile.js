/**
 * 
 */
var mProfile = null, memberAttachments = new Array(), searchedMembers = null, selectedMember = null;

$(document).ready(function() {
	$("#m_profile_tab").click(loadMemberProfileTab);
	$("#m_profile_search").click(searchMember);
	$("#clear_search").click(clearMemberSearch);
	$("#p_reset_pswd_btn").click(resetMemberPassword);
	$("#m_upload_file_btn").click(uploadMProfileAttachment);
	$("#p_print").click(printTable);
	$("#p_pdf").click(printTable);
	$("#p_excel").click(genExcel);
});
function loadMemberProfileTab() {
	$("#clear_search").hide();
	$("#p_change_pswd_btn").hide();
	$("#p_reset_pswd_btn").show();
	$("#member_name").show();
	$("#m_profile_search").show();
	$("#p_edit_btn").attr("disabled", "disabled");
	$("#p_reset_pswd_btn").attr("disabled", "disabled");
	clearMemberProfile();
}
function searchMember() {
	$("#clear_search").hide();
	clearMProfileData();
	var memberName = $("#member_name").val();
	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching();
		ajaxCall(
				"/member/v1/member_search_by_name/"
						+ $("#lang option:selected").text() + "/" + groupAcNo
						+ "/" + memberName,
				"GET", true, "",
 				function(data, status) {
					searchedMembers = data.memberNames;
					loadMemberSearchData(searchedMembers);
		
					for (var i = 0; i < data.memberNames.length; i++) {
						$("#member_" + i).click(loadAndGetSelectedMProfile);
					}
				}, memberSearchFailed);
	}
}
function loadAndGetSelectedMProfile() {
	hideMemberSearchDialog();
	var index = parseInt($(this).data("id"));
	selectedMember = searchedMembers[index];
	searchedMembers = null;
	$("#member_name").val(selectedMember.memberName);
	$("#clear_search").show();
	showMsgMProfile("Loading profile of <b>"
			+ selectedMember.memberName
			+ "</b>. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");

	ajaxCall("/member/v1/member_complete_data/"
			+ $("#lang option:selected").text() + "/"
			+ selectedMember.memberAcNo, "GET", true, "",
			function(data, status) {
				if (data != null && data != "") {
					mProfile = data;
					$("#p_reset_pswd_btn").removeAttr("disabled");
					$("#p_edit_btn").removeAttr("disabled");
					showMsgMProfile("");
					displayMemberProfile(gdCode);
				}
			}, function(xhr) {
				showErrorMsgMProfile("");
				handleErrorAndDisplayMsg(xhr, "p_err_msg");
			});
}
function displayMemberProfile(dCode) {
	$("#p_member_info").html("Member Name: <b>" + mProfile.memberName
			+ "</b><br>" + "Account Number: <b>"
			+ getReadableAccNumForDistrict(dCode, mProfile.memberAcNo) + "</b>");
	$("#p_role").html(mProfile.mrole);
	$("#p_r_by_m_name").html(mProfile.recommendedByMemberName);
	$("#p_gender").html(mProfile.gender);
	$("#p_doe").html(mProfile.dateOfEnroll);
	$("#p_dob").html(mProfile.dateOfBirth);
	$("#p_doa").html(mProfile.dateOfAnni);
	if (mProfile.contacts != null && mProfile.contacts[0] != null) {
		$("#p_name_title").html(mProfile.contacts[0].nameTitle);
		$("#p_first_name").html(mProfile.contacts[0].firstName);
		$("#p_middle_name").html(mProfile.contacts[0].middleName);
		$("#p_last_name").html(mProfile.contacts[0].lastName);
		$("#p_address").html(mProfile.contacts[0].address);
		$("#p_village").html(mProfile.contacts[0].village);
		$("#p_panchayat").html(mProfile.contacts[0].grampanchayat);
		$("#p_tehsil").html(mProfile.contacts[0].taluka);
		$("#p_pin_code").html(mProfile.contacts[0].pinCode);
		$("#p_district").html(mProfile.contacts[0].district);
		$("#p_state").html(mProfile.contacts[0].state);
		$("#p_phone").html(mProfile.contacts[0].phone);
		$("#p_pri_mobile").html(mProfile.contacts[0].priMobile);
		$("#p_sec_mobile").html(mProfile.contacts[0].secMobile);
		$("#p_email").html(mProfile.contacts[0].email);
	}
	if (getMemberRoleCategory(mProfile.mrole) == "SHG Member") {
		$("#p_saving_info_div").show();
		$("#p_bank_info_div").show();
		if (mProfile.account != null)
			$("#p_monthly_saving").html(mProfile.account.plannedMonthlySavingAm);
		else
			$("#p_monthly_saving").html("");
		$("#p_planned_inst_num").html(mProfile.noPlannedSavingInst);
		if (mProfile.bankAccounts != null && mProfile.bankAccounts[0] != null) {
			$("#p_acc_num").html(mProfile.bankAccounts[0].accountNumber);
			$("#p_acc_name").html(mProfile.bankAccounts[0].accountName);
			$("#p_acc_type").html(mProfile.bankAccounts[0].bankAccountType);
			$("#p_bank_name").html(mProfile.bankAccounts[0].bankName);
			$("#p_branch_name").html(mProfile.bankAccounts[0].bankBranchName);
			$("#p_ifsc_code").html(mProfile.bankAccounts[0].ifcsCode);
		}
	}
	else {
		$("#p_saving_info_div").hide();
		$("#p_bank_info_div").hide();
	}
	$("#p_details").show();
	$("#p_mprofiling_btn").removeAttr("disabled");

	memberAttachments = mProfile.attachments;
	if(memberAttachments == null)
		memberAttachments = new Array();
	reloadMemberAttachmentTable();
}
function clearMemberSearch() {
	$("#member_name").val("");
	$("#clear_search").hide();
	clearMProfileData();
}
function clearMProfileData() {
	$("#p_reset_pswd_btn").attr("disabled", "disabled");
	$("#p_edit_btn").attr("disabled", "disabled");
	showErrorMsgMProfile("");
	showMsgMProfile("");
	selectedMember = null;
	clearMemberProfile();
}
function resetMemberPassword() {
	var payload = "{\"memberAcNo\":" + mProfile.memberAcNo + "}";
	ajaxCall(
			"/auth/v1/reset_member_password/",
			"POST",
			true,
			payload,
			function(data, status) {
				if (data != null && data != "") {
					showMsgMProfile("Password reset successfully. New password is <b>"
							+ data.newPasscode + "</b>");
					$("#p_member_info").html("Member Name: <b>" + mProfile.memberName
							+ "</b><br>" + "Account Number: <b>"
							+ getReadableAccNumForDistrict(gdCode, mProfile.memberAcNo) + "</b>");
				}
			},
			function(xhr) {
				showErrorMsgMProfile("");
				handleErrorAndDisplayMsg(xhr, "p_err_msg");
			});
}
function showMsgMProfile(msg) {
	$("#p_member_info").empty();
	$("#p_err_msg").empty().hide();
	$("#p_msg").html(msg).show();
}
function showErrorMsgMProfile(msg) {
	$("#p_msg").empty().hide();
	$("#p_err_msg").html(msg).show();
}
function clearMemberProfile() {
	mProfile = null;
	showMsgMProfile("");
	$("#p_mprofiling_btn").attr("disabled", "disabled");
	$("#p_details").hide();
	$("#p_role").html("");
	$("#p_r_by_m_name").html("");
	$("#p_gender").html("");
	$("#p_doe").html("");
	$("#p_dob").html("");
	$("#p_doa").html("");
	$("#p_name_title").html("");
	$("#p_first_name").html("");
	$("#p_middle_name").html("");
	$("#p_last_name").html("");
	$("#p_address").html("");
	$("#p_village").html("");
	$("#p_panchayat").html("");
	$("#p_tehsil").html("");
	$("#p_pin_code").html("");
	$("#p_district").html("");
	$("#p_state").html("");
	$("#p_phone").html("");
	$("#p_pri_mobile").html("");
	$("#p_sec_mobile").html("");
	$("#p_email").html("");
	$("#p_monthly_saving").html("");
	$("#p_planned_inst_num").html("");
	$("#p_acc_num").html("");
	$("#p_acc_name").html("");
	$("#p_acc_type").html("");
	$("#p_bank_name").html("");
	$("#p_branch_name").html("");
	$("#p_ifsc_code").html("");
	$("#m_attachments_table").empty();
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
				$("#m_edit_doc_btn" + memberAttachments[i].docId).click(editMProfileAttachment);
				$("#m_del_doc_btn" + memberAttachments[i].docId).click(deleteMProfileAttachment);
			}
		}
	} else
		$("#m_attachments_table").empty();
	$("#m_attachment_div").show();
}
function uploadMProfileAttachment() {
	var uri = (appBaseURL + "/member/v1/attach_file/" + mProfile.memberAcNo);
	loadDocumentUploadDlg("Member", uri, function(attachment) {
		memberAttachments[memberAttachments.length] = attachment;
		mProfile.attachments = memberAttachments;
		reloadMemberAttachmentTable();
	});
}
function editMProfileAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/member/v1/update_attach_file/" + mProfile.memberAcNo + "/" + memberAttachments[index].docId);
	loadEditUploadedDocumentDlg("Member", uri, memberAttachments[index], function(attachment) {
		memberAttachments[index] = attachment;
		reloadMemberAttachmentTable();
	});
}
function deleteMProfileAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/member/v1/delete_attach_file/" + mProfile.memberAcNo + "/" + memberAttachments[index].docId,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#m_doc_row" + memberAttachments[index].docId).remove();
			if($("#m_attachments_table tbody").children().length == 1)
				$("#m_attachments_table").empty();
			memberAttachments[index] = null;
			mProfile.attachments = memberAttachments;
		}, function(xhr) {
			showErrorMsgMProfile("");
			handleErrorAndDisplayMsg(xhr, "p_err_msg");
		});
	}
}