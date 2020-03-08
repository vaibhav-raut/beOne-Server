/**
 * 
 */
var trackedGroup = null;

$(document).ready(function() {
	$("#g_activate_btn").click(activateGroup);
	$("#g_go2activate_btn").click(loadGroupActivationTab);
	$("#g_back2apptrac_btn3").click(back2AppTrackFromGroupAppActivate);
	$("#g_app_print").click(printTable);
	$("#g_app_pdf").click(printTable);
	$("#g_app_excel").click(genExcel);
});
function resetGroupAppActivationForm() {
	$("#g_activate_msg").html("");
	$("#g_activate_err_msg").html("");
	$("#g_app_detail_body").html("");
	$("#g_activate_btns").hide();
}
function back2AppTrackFromGroupAppActivate() {
	resetGroupAppActivationForm();
	loadDistrictApplications();
	resetGroupRegForm();
}
function showGroupActivateButton() {
	$("#g_activate_btn").removeAttr("disabled");
	$("#g_activate_btns").show();
	$("#g_activate_back_btn").show();
}
function loadGroupActivationTab() {
	if (distApplications.groups != null && distApplications.groups.length != 0) {
		showGroupActivateButton();
		var index = parseInt($(this).data("id"));
		if (!isNaN(index))
			trackedGroup = distApplications.groups[index];
		var headers = distApplications.displayNames;
		var tablecontent = "<tr><td>" + headers.dateOfEstablishment + "</td><td>"
				+ trackedGroup.dateOfEstablishment
				+ "</td><td width='60'>&nbsp;</td><td>" + headers.groupAcNo
				+ "</td><td>" + getReadableAccNumForDistrict(trackedGroup.districtCode, trackedGroup.groupAcNo)
				+ "</td></tr><tr><td>" + headers.groupName + "</td><td>"
				+ trackedGroup.groupName + "</td><td>&nbsp;</td><td>"
				+ headers.groupType + "</td><td>" + trackedGroup.groupType
				+ "</td></tr><tr><td>Primary Mobile</td><td>";
		if (trackedGroup.contacts != null && trackedGroup.contacts[0] != null)
			tablecontent += trackedGroup.contacts[0].priMobile;
		tablecontent += "</td><td>&nbsp;</td><td></td><td><a class='btn btn-sm yellow' href='#g_reg_content' data-toggle='tab' id='g_detail_"
				+ trackedGroup.groupAcNo + "'>More Details</a></td></tr>";

		$("#g_app_detail_body").html(tablecontent);
		$("#g_detail_" + trackedGroup.groupAcNo).click(
				showGroupDetailsForActivation);
	} else {
		$("g_app_detail_body").html("No data found.");
	}
}
function showMsgGroupAppActivate(msg) {
	$("#g_activate_msg").html(msg);
	$("#g_activate_err_msg").hide();
	$("#g_activate_msg").show();
}
function showErrorMsgGroupAppActivate(msg) {
	$("#g_activate_err_msg").html(msg);
	$("#g_activate_msg").hide();
	$("#g_activate_err_msg").show();
}
function activateGroup() {
	$("#g_activate_btn").attr("disabled", "disabled");
	showMsgGroupAppActivate("Activating group. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
	var payload = JSON.stringify(trackedGroup);
	ajaxCall(
			"/group/v1/activate",
			"POST",
			true,
			payload,
			function(data, status) {
				if (data != "") {
					showMsgGroupAppActivate("Group activated successfully.");
					trackedGroup.approvalStatus = "Active";
				} else {
					showErrorMsgGroupAppActivate("Activate group: No data received from the server.");
					$("#g_activate_btn").removeAttr("disabled");
				}
			}, function(xhr) {
				$("#g_activate_btn").removeAttr("disabled");
				showErrorMsgGroupAppActivate("");
				handleErrorAndDisplayMsg(xhr, "g_activate_err_msg");
			});
}