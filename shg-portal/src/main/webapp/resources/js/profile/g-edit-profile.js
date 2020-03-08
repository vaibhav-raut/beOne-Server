/**
 * 
 */
$(document).ready(function() {
	$("#gp_edit_btn").click(editGroupProfile);
	$("#gedit_update_btn").click(updateGroupProfile);
	$("#g_types").change(groupTypeChangeHandler);
	$("#pin_code").change(numberFieldChangeHandler);
	$("#gedit_bank_search").click(geditBankSearchClickHandler);
});
function editGroupProfile() {
	loadEditGProfileTab();
	clearGEditForm();
	disableGEditForm();
	if (gProfile != null) {
		fillGProfileWithMsg();
		enableGEditForm();
		$("#gedit_update_btn").removeAttr("disabled");
	}
}
function loadEditGProfileTab() {
	var gTypeContent = "";
	for (var i = 0; i < groupTypes.length; i++) {
		gTypeContent += "<option>" + groupTypes[i].groupType + "</option>";
	}
	$("#g_types").html(gTypeContent);

	var accTypeContent = "";
	for (var i = 0; i < bankAccTypes.length; i++)
		accTypeContent += "<option>" + bankAccTypes[i].enumValue + "</option>";
	$("#g_acc_type").html(accTypeContent);

	var districtContent = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (districtValues[i].state != "Admin")
			districtContent += "<option>" + districtValues[i].district
					+ "</option>";
	}
	$("#g_district").html(districtContent);
}
function fillGProfileWithMsg(msg) {
	if (gProfile != null) {
		if (msg == undefined)
			msg = "";

		showMsgGEdit(msg + "Group Account Number: <b>"
				+ getReadableAccNumForDistrict(gProfile.districtCode,
						gProfile.groupAcNo) + "</b>");
		$("#g_types").val(gProfile.groupType);
		$("#g_doe").val(gProfile.dateOfEstablishment);
		if (gProfile.contacts != null && gProfile.contacts[0] != null) {
			$("#g_name").val(gProfile.contacts[0].name);
			$("#g_vision").val(gProfile.contacts[0].vision);
			$("#g_desc").val(gProfile.contacts[0].description);
			$("#g_address").val(gProfile.contacts[0].address);
			$("#g_village").val(gProfile.contacts[0].village);
			$("#g_panchayat").val(gProfile.contacts[0].grampanchayat);
			$("#g_tehsil").val(gProfile.contacts[0].taluka);
			$("#g_pin_code").val(gProfile.contacts[0].pinCode);
			$("#g_district").val(gProfile.contacts[0].district);
			var district = $("#g_district option:selected").text();
			if(district == "") {
				$("#g_district").attr("disabled", "disabled");
				$("#g_district").html("<option>" + gProfile.contacts[0].district + "</option>");
			} else {
				$("#g_district").removeAttr("disabled");
			}
			$("#g_state").val(gProfile.contacts[0].state);
			var state = $("#g_state option:selected").text();
			if(state == "") {
				$("#g_state").attr("disabled", "disabled");
				$("#g_state").html("<option>" + gProfile.contacts[0].state + "</option>");
			} else {
				$("#g_state").removeAttr("disabled");
			}
			$("#g_phone").val(gProfile.contacts[0].phone);
			$("#g_pri_mobile").val(gProfile.contacts[0].priMobile);
			$("#g_sec_mobile").val(gProfile.contacts[0].secMobile);
			$("#g_email").val(gProfile.contacts[0].email);
		}
		groupTypeChangeHandler();
		if (gProfile.groupType == "SHG" || gProfile.groupType == "Federation") {
			if (gProfile.bankAccounts != null
					&& gProfile.bankAccounts[0] != null) {
				$("#g_acc_num").val(gProfile.bankAccounts[0].accountNumber);
				$("#g_acc_name").val(gProfile.bankAccounts[0].accountName);
				$("#g_acc_type").val(gProfile.bankAccounts[0].bankAccountType);
				$("#gedit_bank_id").val(gProfile.bankAccounts[0].bankProfileId);
				$("#gedit_bank_name").html(gProfile.bankAccounts[0].bankName);
				$("#gedit_branch_name").html(
						gProfile.bankAccounts[0].bankBranchName);
				$("#gedit_ifsc_code").html(gProfile.bankAccounts[0].ifcsCode);
			}
		} else if (gProfile.groupType == "Bank" && gProfile.bankProfile != null) {
			$("#gbp_name").val(gProfile.bankProfile.bankName);
			$("#gbp_branch_name").val(gProfile.bankProfile.branchName);
			$("#gbp_ifsc_code").val(gProfile.bankProfile.ifcsCode);
			$("#gbp_loan_rate").val(gProfile.bankProfile.bankLoanIntRate);
			$("#gbp_fd_rate").val(gProfile.bankProfile.bankFdIntRate);
			$("#gbp_rating").val(gProfile.bankProfile.bankRating);
		}
	}
}
function updateGroupProfile() {
	var grpType = $("#g_types option:selected").text(), doe = $("#g_doe").val(), gname = $(
			"#g_name").val().replace(/\n/g, " "), address = $("#g_address")
			.val().replace(/\n/g, " "), pincode = $("#g_pin_code").val(), priMob = $(
			"#g_pri_mobile").val();
	if (doe == "") {
		showErrorMsgGEdit("Please provide Date of Establishment.");
		$("#g_doe").focus();
	} else if (grpType != "Bank" && gname == "") {
		showErrorMsgGEdit("Please provide a Group Name.");
		$("#g_name").focus();
	} else if (address == "") {
		showErrorMsgGEdit("Please provide Address.");
		$("#g_address").focus();
	} else if (pincode == "") {
		showErrorMsgGEdit("Please provide pin code.");
		$("#g_pin_code").focus();
	} else if (priMob == "") {
		showErrorMsgGEdit("Please provide Primary Mobile.");
		$("#g_pri_mobile").focus();
	} else {
		var selectedDistrict = $("#g_district option:selected").text();
		if (grpType == "SHG" || grpType == "Federation") {
			var bankProfileId = $("#gedit_bank_id").val();
			if (bankProfileId == "") {
				showErrorMsgGEdit("Please search bank for Bank Account Information.");
				return;
			}
			var accnum = $("#g_acc_num").val();
			if (accnum == "") {
				showErrorMsgGEdit("Please provide Bank Account Number.");
				$("#g_acc_num").focus();
				return;
			}
			var accname = $("#g_acc_name").val();
			if (accname == "") {
				showErrorMsgGEdit("Please provide Bank Account Name.");
				$("#g_acc_name").focus();
				return;
			}
			if(gProfile.bankAccounts == null)
				gProfile.bankAccounts = [];
			if(gProfile.bankAccounts[0] == null)
				gProfile.bankAccounts[0] = {};
			gProfile.bankAccounts[0].bankProfileId = bankProfileId;
			gProfile.bankAccounts[0].accountNumber = accnum;
			gProfile.bankAccounts[0].accountName = accname;
			gProfile.bankAccounts[0].bankAccountType = $("#g_acc_type option:selected").text();
		}
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
			if(gProfile.bankProfile == null)
				gProfile.bankProfile = {};
			gProfile.bankProfile.districtCode = districtCode;
			gProfile.bankProfile.bankName = bankname;
			gProfile.bankProfile.branchName = branchname;
			gProfile.bankProfile.ifcsCode = ifscode;
			gProfile.bankProfile.bankLoanIntRate = $("#gbp_loan_rate").val();
			gProfile.bankProfile.bankFdIntRate = $("#gbp_fd_rate").val();
			gProfile.bankProfile.bankRating = $("#gbp_rating").val();
		}
		showMsgGEdit("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		$("#gedit_update_btn").attr("disabled", "disabled");

		gProfile.groupType = grpType;
		gProfile.dateOfEstablishment = doe;
		gProfile.contacts[0].name = gname;
		gProfile.contacts[0].description = $("#g_desc").val().replace(/\n/g, " ");
		gProfile.contacts[0].vision = $("#g_vision").val().replace(/\n/g, " ");
		gProfile.contacts[0].address = address;
		gProfile.contacts[0].village = $("#g_village").val();
		gProfile.contacts[0].grampanchayat = $("#g_panchayat").val();
		gProfile.contacts[0].taluka = $("#g_tehsil").val();
		gProfile.contacts[0].district = selectedDistrict;
		gProfile.contacts[0].state = $("#g_state option:selected").text();
		gProfile.contacts[0].email = $("#g_email").val();
		gProfile.contacts[0].pinCode = pincode;
		gProfile.contacts[0].priMobile = priMob;
		gProfile.contacts[0].phone = $("#g_phone").val();
		gProfile.contacts[0].secMobile = $("#g_sec_mobile").val();
		
		var bankAccountDisplay = gProfile.bankAccountDisplay;
		gProfile.bankAccountDisplay = null;
		groupAttachments = gProfile.attachments;
		gProfile.attachments = null;
		var payload = JSON.stringify(gProfile);
		gProfile.attachments = groupAttachments;
		gProfile.bankAccountDisplay = bankAccountDisplay;
		ajaxCall( "/group/v1/update_group_profile", "POST", true, payload,
				function(data, status) {
					gProfile = data;
					fillGProfileWithMsg("Profile updated Successfully.<br>");
					displayGroupProfile();
				}, function(xhr) {
					$("#gedit_update_btn").removeAttr("disabled");
					enableGEditForm();
					showErrorMsgGEdit("");
					handleErrorAndDisplayMsg(xhr, "gedit_err_msg");
				});
	}
}
function groupTypeChangeHandler() {
	var grpType = $("#g_types option:selected").text();
	if (grpType != "SHG" && grpType != "Federation") {
		$("#g_bank_acc_div").hide();
		if (grpType == "Bank") {
			$("#g_bprofile_div").show();
			$("#grp_name_row").hide();
		} else {
			$("#g_bprofile_div").hide();
			$("#grp_name_row").show();
		}
	} else {
		$("#g_bank_acc_div").show();
		$("#g_bprofile_div").hide();
		$("#grp_name_row").show();
	}
}
function enableGEditForm() {
	// $("#g_types").removeAttr("disabled");
	$("#doe").removeAttr("disabled");
	$("#g_name").removeAttr("disabled");
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
	$("#acc_num").removeAttr("disabled");
	$("#acc_name").removeAttr("disabled");
	$("#acc_type").removeAttr("disabled");
	$("#gedit_bank_search").removeAttr("disabled");
}
function disableGEditForm() {
	$("#g_types").attr("disabled", "disabled");
	$("#doe").attr("disabled", "disabled");
	$("#g_name").attr("disabled", "disabled");
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
	$("#acc_num").attr("disabled", "disabled");
	$("#acc_name").attr("disabled", "disabled");
	$("#acc_type").attr("disabled", "disabled");
	$("#gedit_bank_search").attr("disabled", "disabled");
}
function clearGEditForm() {
	$("#doe").val("");
	$("#g_name").val("");
	$("#address").val("");
	$("#village").val("");
	$("#panchayat").val("");
	$("#tehsil").val("");
	$("#pin_code").val("");
	$("#phone").val("");
	$("#pri_mobile").val("");
	$("#sec_mobile").val("");
	$("#email").val("");
	$("#acc_num").val("");
	$("#acc_name").val("");
	$("#gedit_bank_id").val("");
	$("#gedit_bank_name").html(" -- ");
	$("#gedit_branch_name").html(" -- ");
	$("#gedit_ifsc_code").html(" -- ");
}
function showMsgGEdit(msg) {
	$("#gedit_msg").html(msg);
	$("#gedit_err_msg").hide();
	$("#gedit_msg").show();
}
function showErrorMsgGEdit(msg) {
	$("#gedit_err_msg").html(msg);
	$("#gedit_msg").hide();
	$("#gedit_err_msg").show();
}
function geditBankSearchClickHandler() {
	bankSearchedFor = "group-profile";
	loadBankSearchDialog();
	resetBankSearchDialog();
}