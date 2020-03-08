/**
 * 
 */
var gProfile = null, groupAttachments = new Array();

$(document).ready(function() {
	$("#g_profile_tab").click(loadGroupProfileTab);
	$("#g_upload_file_btn").click(uploadGProfileAttachment);
	$("#gp_print").click(printTable);
	$("#gp_pdf").click(printTable);
	$("#gp_excel").click(genExcel);
});
function loadGroupProfileTab() {
	ajaxCall("/group/v1/group_profile/"
			+ $("#lang option:selected").text() + "/"
			+ groupAcNo, "GET", true, "",
			function(data, status) {
				if (data != null && data != "") {
					gProfile = data;
					
					$("#gp_edit_btn").removeAttr("disabled");
					displayGroupProfile();
				}
			}, function(xhr) {
				showErrorMsgGProfile("");
				handleErrorAndDisplayMsg(xhr, "gp_err_msg");
			});
}
function displayGroupProfile() {
	showMsgGProfile("Group Account Number: <b>"
			+ getReadableAccNumForDistrict(gProfile.districtCode, gProfile.groupAcNo) + "</b>");
	$("#gp_group_type").html(gProfile.groupType);
	$("#gp_doe").html(gProfile.dateOfEstablishment);
	if (gProfile.contacts != null && gProfile.contacts[0] != null) {
		$("#gp_group_name").html(gProfile.contacts[0].name);
		$("#gp_group_vision").html(gProfile.contacts[0].vision);
		$("#gp_group_desc").html(gProfile.contacts[0].description);
		$("#gp_address").html(gProfile.contacts[0].address);
		$("#gp_village").html(gProfile.contacts[0].village);
		$("#gp_panchayat").html(gProfile.contacts[0].grampanchayat);
		$("#gp_tehsil").html(gProfile.contacts[0].taluka);
		$("#gp_pin_code").html(gProfile.contacts[0].pinCode);
		$("#gp_district").html(gProfile.contacts[0].district);
		$("#gp_state").html(gProfile.contacts[0].state);
		$("#gp_phone").html(gProfile.contacts[0].phone);
		$("#gp_pri_mobile").html(gProfile.contacts[0].priMobile);
		$("#gp_sec_mobile").html(gProfile.contacts[0].secMobile);
		$("#gp_email").html(gProfile.contacts[0].email);
	}
	if (gProfile.groupType != "SHG" && gProfile.groupType != "Federation") {
		$("#gp_bank_acc_div").hide();
		$("#gp_bprofile_div").hide();
		if (gProfile.groupType == "Bank") {
			$("#gp_bprofile_div").show();
			if(gProfile.bankProfile != null) {
				$("#gp_bp_name").html(gProfile.bankProfile.bankName);
				$("#gp_bp_branch_name").html(gProfile.bankProfile.branchName);
				$("#gp_bp_ifsc_code").html(gProfile.bankProfile.ifcsCode);
				$("#gp_bp_loan_rate").html(gProfile.bankProfile.bankLoanIntRate);
				$("#gp_bp_fd_rate").html(gProfile.bankProfile.bankFdIntRate);
				$("#gp_bp_rating").html(gProfile.bankProfile.bankRating);
			}
		}
	} else {
		$("#gp_bank_acc_div").show();
		$("#gp_bprofile_div").hide();
		if (gProfile.bankAccounts != null && gProfile.bankAccounts[0] != null) {
			$("#gp_acc_num").html(gProfile.bankAccounts[0].accountNumber);
			$("#gp_acc_name").html(gProfile.bankAccounts[0].accountName);
			$("#gp_acc_type").html(gProfile.bankAccounts[0].bankAccountType);
			$("#gp_bank_name").html(gProfile.bankAccounts[0].bankName);
			$("#gp_branch_name").html(gProfile.bankAccounts[0].bankBranchName);
			$("#gp_ifsc_code").html(gProfile.bankAccounts[0].ifcsCode);
		}
	}
	$("#gp_details").show();

	groupAttachments = gProfile.attachments;
	reloadGroupAttachmentTable();
}
function showMsgGProfile(msg) {
	$("#gp_msg").html(msg);
	$("#gp_err_msg").hide();
	$("#gp_msg").show();
}
function showErrorMsgGProfile(msg) {
	$("#gp_err_msg").html(msg);
	$("#gp_msg").hide();
	$("#gp_err_msg").show();
}
function clearGroupProfile() {
	$("#gp_details").hide();
	$("#gp_err_msg").hide();
	$("#gp_err_msg").html("");
	$("#gp_msg").show();
	$("#gp_msg").html("");
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
				$("#g_edit_doc_btn" + groupAttachments[i].docId).click(editGProfileAttachment);
				$("#g_del_doc_btn" + groupAttachments[i].docId).click(deleteGProfileAttachment);
			}
		}
	}
	$("#g_attachment_div").show();
}
function uploadGProfileAttachment() {
	var uri = (appBaseURL + "/group/v1/attach_file/" + gProfile.groupAcNo);
	loadDocumentUploadDlg("Group", uri, function(attachment) {
		groupAttachments[groupAttachments.length] = attachment;
		gProfile.attachments = groupAttachments;
		reloadGroupAttachmentTable();
	});
}
function editGProfileAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/group/v1/update_attach_file/" + gProfile.groupAcNo + "/" + groupAttachments[index].docId);
	loadEditUploadedDocumentDlg("Group", uri, groupAttachments[index], function(attachment) {
		groupAttachments[index] = attachment;
		reloadGroupAttachmentTable();
	});
}
function deleteGProfileAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/group/v1/delete_attach_file/" + gProfile.groupAcNo + "/" + groupAttachments[index].docId,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#g_doc_row" + groupAttachments[index].docId).remove();
			if($("#g_attachments_table tbody").children().length == 1)
				$("#g_attachments_table").html("");
			groupAttachments[index] = null;
			gProfile.attachments = groupAttachments;
		}, function(xhr) {
			showErrorMsgGProfile("");
			handleErrorAndDisplayMsg(xhr, "gp_err_msg");
		});
	}
}