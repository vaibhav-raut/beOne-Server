/**
 * 
 */
$(document).ready(function() {
	$("#p_edit_btn").click(editMemberProfile);
	$("#p_update_btn").click(updateMemberProfile);
	$("#m_name_search").click(searchMemberUsingName);
	$("#pri_mobile").change(numberFieldChangeHandler);
	$("#sec_mobile").change(numberFieldChangeHandler);
	$("#phone").change(numberFieldChangeHandler);
	$("#pin_code").change(numberFieldChangeHandler);
	$("#acc_num").change(numberFieldChangeHandler);
	$("#medit_bank_search").click(meditBankSearchClickHandler);
});
function editMemberProfile() {
	loadEditProfileTab();
	clearEditProfileForm();
	disableEditProfileForm();
	if (mProfile != null) {
		fillMProfileWithMsg();
		enableEditProfileForm();
		$("#p_update_btn").removeAttr("disabled");
	}
}
function loadEditProfileTab() {
	var titleContent = "";
	for (var i = 0; i < titles.length; i++)
		titleContent += "<option>" + titles[i].title + "</option>";
	$("#name_title").html(titleContent);

	var districtContent = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (districtValues[i].state != "Admin")
			districtContent += "<option>" + districtValues[i].district
					+ "</option>";
	}
	$("#district").html(districtContent);

	var accTypeContent = "";
	for (var i = 0; i < bankAccTypes.length; i++)
		accTypeContent += "<option>" + bankAccTypes[i].enumValue + "</option>";
	$("#acc_type").html(accTypeContent);
}
function fillMProfileWithMsg(msg) {
	if (mProfile != null) {
		if (msg == undefined)
			msg = "";
		showMsgEditProfile(msg + "Member Name: <b>" + mProfile.memberName
				+ "</b><br>" + "Account Number: <b>"
				+ getReadableAccNum(mProfile.memberAcNo) + "</b>");
		$("#medit_role").html(mProfile.mrole);
		$("#recommend_by_m_name").val(mProfile.recommendedByMemberName);
		$("#recommend_by_m_num").val(mProfile.recommendedByMemberAcNo);
		$("#gender").val(mProfile.gender);
		$("#doe").val(mProfile.dateOfEnroll);
		$("#dob").val(mProfile.dateOfBirth);
		$("#doa").val(mProfile.dateOfAnni);
		if (mProfile.contacts != null && mProfile.contacts[0] != null) {
			$("#name_title").val(mProfile.contacts[0].nameTitle);
			$("#first_name").val(mProfile.contacts[0].firstName);
			$("#middle_name").val(mProfile.contacts[0].middleName);
			$("#last_name").val(mProfile.contacts[0].lastName);
			$("#address").val(mProfile.contacts[0].address);
			$("#village").val(mProfile.contacts[0].village);
			$("#panchayat").val(mProfile.contacts[0].grampanchayat);
			$("#tehsil").val(mProfile.contacts[0].taluka);
			$("#pin_code").val(mProfile.contacts[0].pinCode);
			$("#district").val(mProfile.contacts[0].district);
			$("#state").val(mProfile.contacts[0].state);
			$("#phone").val(mProfile.contacts[0].phone);
			$("#pri_mobile").val(mProfile.contacts[0].priMobile);
			$("#sec_mobile").val(mProfile.contacts[0].secMobile);
			$("#email").val(mProfile.contacts[0].email);
		}
		if (getMemberRoleCategory(mrole) == "SHG Member") {
			$("#bank_info_div").show();
			if (mProfile.bankAccounts != null
					&& mProfile.bankAccounts[0] != null) {
				$("#acc_num").val(mProfile.bankAccounts[0].accountNumber);
				$("#acc_name").val(mProfile.bankAccounts[0].accountName);
				$("#acc_type").val(mProfile.bankAccounts[0].bankAccountType);
				$("#medit_bank_id").val(mProfile.bankAccounts[0].bankProfileId);
				$("#medit_bank_name").html(mProfile.bankAccounts[0].bankName);
				$("#medit_branch_name").html(
						mProfile.bankAccounts[0].bankBranchName);
				$("#medit_ifsc_code").html(mProfile.bankAccounts[0].ifcsCode);
			}
		}
		else {
			$("#bank_info_div").hide();
		}
	}
}
function updateMemberProfile() {
	var firstName = $("#first_name").val(), lastName = $("#last_name").val(), address = $(
			"#address").val().replace(/\n/g, " "), doe = $("#doe").val(), accnum = $("#acc_num").val(), accname = $(
			"#acc_name").val(), bankProfileId = $("#medit_bank_id").val(), mroleCategory = getMemberRoleCategory(mProfile.mrole);
	if (firstName == "") {
		showErrorMsgEditProfile("Please provide member's first name.");
		$("#first_name").focus();
	} else if (lastName == "") {
		showErrorMsgEditProfile("Please provide member's last name.");
		$("#last_name").focus();
	} else if (address == "") {
		showErrorMsgEditProfile("Please provide member's address.");
		$("#address").focus();
	} else if (doe == "") {
		showErrorMsgEditProfile("Please provide member's date of enrollement.");
		$("#doe").focus();
	} else if (mroleCategory == "SHG Member" && accnum != ""
			&& (accname == "" || bankProfileId == "")) {
		showErrorMsgEditProfile("Please provide complete bank information.");
	} else {
		disableEditProfileForm();
		showMsgEditProfile("Updating profile. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		$("#p_update_btn").attr("disabled", "disabled");

		mProfile.gender = $("#gender option:selected").text();
		mProfile.dateOfEnroll = doe;
		mProfile.recommendedByMemberAcNo = $("#recommend_by_m_num").val();
		mProfile.dateOfBirth = $("#dob").val();
		mProfile.dateOfAnni = $("#doa").val();
		mProfile.contacts[0].nameTitle = $("#name_title option:selected").text();
		mProfile.contacts[0].firstName = firstName;
		mProfile.contacts[0].middleName = $("#middle_name").val();
		mProfile.contacts[0].lastName = lastName;
		mProfile.contacts[0].address = address;
		mProfile.contacts[0].village = $("#village").val();
		mProfile.contacts[0].grampanchayat = $("#panchayat").val();
		mProfile.contacts[0].taluka = $("#tehsil").val();
		mProfile.contacts[0].district = $("#district option:selected").text();
		mProfile.contacts[0].state = $("#state option:selected").text();
		mProfile.contacts[0].pinCode = $("#pin_code").val();
		mProfile.contacts[0].priMobile = $("#pri_mobile").val();
		mProfile.contacts[0].secMobile = $("#sec_mobile").val();
		mProfile.contacts[0].phone= $("#phone").val();
		mProfile.contacts[0].email = $("#email").val();

		if (mroleCategory == "SHG Member" && accnum != "") {
			if(mProfile.bankAccounts == null)
				mProfile.bankAccounts = [];
			if(mProfile.bankAccounts[0] == null)
				mProfile.bankAccounts[0] = {};
			mProfile.bankAccounts[0].accountNumber = accnum;
			mProfile.bankAccounts[0].accountName = accname;
			mProfile.bankAccounts[0].bankAccountType = $("#acc_type option:selected").text();
			mProfile.bankAccounts[0].bankProfileId = bankProfileId;
		}
		memberAttachments = mProfile.attachments;
		mProfile.attachments = null;
		var payload = JSON.stringify(mProfile);
		mProfile.attachments = memberAttachments;
		ajaxCall("/member/v1/update_member", "POST", true, payload, 
				function( data, status) {
					mProfile = data;
					fillMProfileWithMsg("Profile updated Successfully.<br>");
					if(mProfile.memberAcNo === curMemberAcNo)
						displayMemberProfile(mdCode);
					else
						displayMemberProfile(gdCode);
				}, function(xhr) {
					$("#p_update_btn").removeAttr("disabled");
					enableEditProfileForm();
					showErrorMsgEditProfile("");
					handleErrorAndDisplayMsg(xhr, "medit_err_msg");
				});
	}
}
function showMsgEditProfile(msg) {
	$("#medit_msg").html(msg);
	$("#medit_err_msg").hide();
	$("#medit_msg").show();
}
function showErrorMsgEditProfile(msg) {
	$("#medit_err_msg").html(msg);
	$("#medit_msg").hide();
	$("#medit_err_msg").show();
}
function enableEditProfileForm() {
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
	$("#acc_num").removeAttr("disabled");
	$("#acc_name").removeAttr("disabled");
	$("#acc_type").removeAttr("disabled");
	$("#medit_bank_search").removeAttr("disabled");
}
function disableEditProfileForm() {
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
	$("#acc_num").attr("disabled", "disabled");
	$("#acc_name").attr("disabled", "disabled");
	$("#acc_type").attr("disabled", "disabled");
	$("#medit_bank_search").attr("disabled", "disabled");
}
function clearEditProfileForm() {
	$("#recommend_by_m_name").val("");
	$("#recommend_by_m_num").val("");
	$("#doe").val("");
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
	$("#acc_num").val("");
	$("#acc_name").val("");
	$("#medit_bank_id").val("");
	$("#medit_bank_name").html(" -- ");
	$("#medit_branch_name").html(" -- ");
	$("#medit_ifsc_code").html(" -- ");
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
function meditBankSearchClickHandler() {
	bankSearchedFor = "member-profile";
	loadBankSearchDialog();
	resetBankSearchDialog();
}