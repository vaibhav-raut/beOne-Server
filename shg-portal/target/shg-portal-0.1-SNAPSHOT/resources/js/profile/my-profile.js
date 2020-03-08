/**
 * 
 */
$(document).ready(function() {
	$("#my_profile_tab").click(loadMyProfileTab);
	$("#p_change_pswd_btn").click(resetPasswordForm);
	$("#reset_pswd_form_btn").click(resetPasswordForm);
	$("#change_pswd_btn").click(changeMemberPassword);
});
function loadMyProfileTab() {
	$("#member_name").val("");
	$("#clear_search").hide();
	$("#member_name").hide();
	$("#m_profile_search").hide();
	$("#p_reset_pswd_btn").hide();
	$("#p_change_pswd_btn").show();
	$("#p_edit_btn").attr("disabled", "disabled");
	clearMemberProfile();
	loadMyProfile(curMemberAcNo);
}
function loadMyProfile(memberAcNo) {
	ajaxCall("/member/v1/my_profile/"
			+ $("#lang option:selected").text() + "/"
			+ memberAcNo, "GET", true, "",
			function(data, status) {
				if (data != null && data != "") {
					mProfile = data;
					$("#p_reset_pswd_btn").removeAttr("disabled");
					$("#p_edit_btn").removeAttr("disabled");
					displayMemberProfile(mdCode);
				}
			}, function(xhr) {
				showErrorMsgMProfile("");
				handleErrorAndDisplayMsg(xhr, "p_err_msg");
			});
}
function resetPasswordForm() {
	$("#msg").html("");
	$("#err_msg").html("");
	$("#err_msg").hide();
	$("#msg").show();
	$("#old_pswd").val("");
	$("#new_pswd").val("");
	$("#repeat_pswd").val("");
	$("#pswd_dialog_body").show();
	$("#change_pswd_btn").show();
	$("#reset_pswd_form_btn").show();
	$("#okcancel_pswd_btn").html("Cancel");
	$("#change_pswd_btn").removeAttr("disabled");
}
function showMsgPswdDlg(msg) {
	$("#err_msg").hide();
	$("#msg").show();
	$("#msg").html(msg);
}
function showErrMsgPswdDlg(errMsg) {
	$("#msg").hide();
	$("#err_msg").show();
	$("#err_msg").html(errMsg);
}
function changeMemberPassword() {
	showMsgPswdDlg("Please Wait...");
	var oldPassword = $("#old_pswd").val();
	var newPassword = $("#new_pswd").val();
	var repeatPassword = $("#repeat_pswd").val();
	
	if(oldPassword == "" || newPassword == "" || repeatPassword == "")
		showErrMsgPswdDlg("Please enter all details.");
	else if(newPassword.length < 6 || repeatPassword.length < 6)
		showErrMsgPswdDlg("New password should be 6 to 20 character long.");
	else if(newPassword != repeatPassword)
		showErrMsgPswdDlg("New passwords do not match.");
	else if(newPassword.search(" ") != -1)
		showErrMsgPswdDlg("New password has space.");
	else {
	var payload = "{\"memberAcNo\":" + mProfile.memberAcNo
			+ ",\"oldPasscode\":\"" + oldPassword
			+ "\",\"newPasscode\":\"" + newPassword + "\"}";
	$("#change_pswd_btn").attr("disabled", "disabled");
	ajaxCall(
			"/auth/v1/change_member_password/",
			"POST",
			true,
			payload,
			function(data, status) {
				showMsgPswdDlg("<b>Password changed successfully.<b>");
				$("#pswd_dialog_body").hide();
				$("#change_pswd_btn").hide();
				$("#reset_pswd_form_btn").hide();
				$("#okcancel_pswd_btn").html("Ok");
			},
			function(xhr) {
				$("#change_pswd_btn").removeAttr("disabled");
				showErrMsgPswdDlg("");
				handleErrorAndDisplayMsg(xhr, "err_msg");
			});
	}
}