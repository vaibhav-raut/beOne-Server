/**
 * 
 */
$(document).ready(function() {
	loadCommonProperties();
	$("#lang").val(curLang);
	$("#pass_reset_form_btn").click(resetChangePasswordForm);
	$("#pass_change_btn").click(changeMemberPassword);
});
function resetChangePasswordForm() {
	$("#pass_msg").html("");
	$("#pass_err_msg").html("");
	$("#pass_err_msg").hide();
	$("#pass_msg").show();
	$("#pass_old_pswd").val("");
	$("#pass_new_pswd").val("");
	$("#pass_repeat_pswd").val("");
	$("#pass_change_btn").removeAttr("disabled");
}
function showMsgChangePswd(msg) {
	$("#pass_err_msg").hide();
	$("#pass_msg").show();
	$("#pass_msg").html(msg);
}
function showErrMsgChangePswd(errMsg) {
	$("#pass_msg").hide();
	$("#pass_err_msg").show();
	$("#pass_err_msg").html(errMsg);
}
function changeMemberPassword() {
	var oldPassword = $("#pass_old_pswd").val();
	var newPassword = $("#pass_new_pswd").val();
	var repeatPassword = $("#pass_repeat_pswd").val();

	if (oldPassword == "" || newPassword == "" || repeatPassword == "")
		showErrMsgChangePswd("Please enter all details.");
	else if (newPassword.length < 6 || repeatPassword.length < 6)
		showErrMsgChangePswd("New password should be 6 to 20 character long.");
	else if (newPassword != repeatPassword)
		showErrMsgChangePswd("New passwords do not match.");
	else if (newPassword.search(" ") != -1)
		showErrMsgChangePswd("New password has space.");
	else {
		showMsgChangePswd("Please Wait... <img src='resources/img/ajax-loader.gif'/>");
		$("#pass_change_pswd_btn").attr("disabled", "disabled");
		var payload = "{\"memberAcNo\":" + curMemberAcNo
				+ ",\"oldPasscode\":\"" + oldPassword + "\",\"newPasscode\":\""
				+ newPassword + "\"}";
		ajaxCall(
				"/auth/v1/change_member_password/",
				"POST",
				true,
				payload,
				function(data, status) {
					resetChangePasswordForm();
					$("#pass_change_div").hide();
					showMsgChangePswd("<b>Password changed successfully.<b>");
					$("#pass_change_ok").show();
				}, function(xhr) {
					$("#pass_change_pswd_btn").removeAttr("disabled");
					showErrMsgChangePswd("");
					handleErrorAndDisplayMsg(xhr, "pass_err_msg");
				});
	}
}