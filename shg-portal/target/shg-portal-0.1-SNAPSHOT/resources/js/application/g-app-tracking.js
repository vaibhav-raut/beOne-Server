/**
 * 
 */
var distApplications = null;

$(document).ready(function() {
	$("#district_app_div").show();
	$("#g_app_sel_district_btn").click(selectDistrictForDistrictApp);
	$("#g_app_search_btn").click(loadDistrictApplications);
});
function selectDistrictForDistrictApp() {
	loadSelectDistrictDialog("g_app_state", "g_app_district");
}
function loadDistrictApplications() {
	var selectedDistrict = $("#g_app_district").val();
	if(selectedDistrict == "") {
		showMessage("Please select a district.", "g_app_sel_district_btn");
		return;
	}
	$("#g_app_table_header").html("");
	$("#g_app_table_body")
			.html(
					"<tr><td>Please wait... <br>Applications are loading... <img src='resources/img/ajax-loader.gif'/></td></tr>");
	var selectedDistrictId = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (selectedDistrict == districtValues[i].district)
			selectedDistrictId = districtValues[i].districtId;
	}
	ajaxCall(
			"/district/v1/district_applications/"
					+ $("#lang option:selected").text() + "/"
					+ selectedDistrictId,
			"GET",
			true,
			"",
			function(data, status) {
				distApplications = data;
				var headers = data.displayNames;
				if (data.groups != null && data.groups.length != 0) {
					$("#g_app_table_header")
							.html(
									"<tr><th><strong>"
											+ headers.dateOfEstablishment
											+ "</strong></th><th><strong>"
											+ headers.groupAcNo
											+ "</strong></th><th><strong>"
											+ headers.groupName
											+ "</strong></th><th><strong>"
											+ headers.groupType
											+ "</strong></th><th><strong>"
											+ headers.approvalStatus
											+ "</strong></th><th><strong>Action</strong></th></tr>");

					var gData = data.groups;
					var tablecontent = "";
					for (var k = 0; k < gData.length; k++) {
						tablecontent += "<tr><td>" + gData[k].dateOfEstablishment + "</td>";
						tablecontent += "<td>" + getReadableAccNumForDistrict(gData[k].districtCode, gData[k].groupAcNo) + "</td>";
						tablecontent += "<td>" + gData[k].groupName + "</td>";
						tablecontent += "<td>" + gData[k].groupType + "</td>";
						tablecontent += "<td>" + gData[k].approvalStatus + "</td>";
						if (gData[k].approvalStatus == "Active"
								|| gData[k].approvalStatus == "Rejected")
							tablecontent += "<td><a class='btn btn-sm yellow' href='#g_reg_content' data-toggle='tab' data-id='" + k
									+ "' id='g_reg_action_" + k + "'>Details";
						else if (gData[k].approvalStatus == "Approved")
							tablecontent += "<td><a class='btn btn-sm green' href='#g_app_activation_content' data-toggle='tab' data-id='" + k
									+ "' id='g_reg_action_" + k + "'>Activate";
						else
							tablecontent += "<td><a class='btn btn-sm blue' href='#g_reg_content' data-toggle='tab' data-id='" + k
									+ "' id='g_reg_action_" + k + "'>Approve";
						tablecontent += "</a></td></tr>";
					}
					$("#g_app_table_body").html(tablecontent);
					for (var k = 0; k < data.groups.length; k++) {
						if (gData[k].approvalStatus == "Approved")
							$("#g_reg_action_" + k).click(
									loadGroupActivationTab);
						else
							$("#g_reg_action_" + k).click(
									showGroupDetailsForActivation);
					}
				} else {
					$("#g_app_table_header").html("");
					$("#g_app_table_body")
							.html(
									"<tr><td>No application for selected district.</td></tr>");
				}
			}, function(xhr) {
				$("#g_app_table_header").html("");
				handleErrorAndDisplayMsg(xhr, "g_app_table_body");
			});
}