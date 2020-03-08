/**
 * 
 */
var stateItemIdSD = "", districtItemIdSD = "";

$(document).ready(function() {
	$("#sd_state").change(selDistrictStateChangeHandler);
	$("#select_district_btn").click(selectDistrictAndCheckAccess);
});
function loadSelectDistrictDialog(stateItemId, districtItemId) {
	var curState = "", curDistrict = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (districtValues[i].districtCode == gdCode) {
			curDistrict = districtValues[i].district;
			curState = districtValues[i].state;
			break;
		}
	}
	var stateContent = "";
	for (var i = 0; i < states.length; i++) {
		stateContent += "<option>" + states[i] + "</option>";
	}
	$("#sd_state").html(stateContent);
	if (curState != "")
		$("#sd_state").val(curState);

	selDistrictStateChangeHandler();

	if (curDistrict != "")
		$("#sd_district").val(curDistrict);

	showMsgSD("");
	$("#sd_district").removeAttr("disabled");
	$("#sd_state").removeAttr("disabled");

	stateItemIdSD = stateItemId;
	districtItemIdSD = districtItemId;
}

function selDistrictStateChangeHandler() {
	var curState = $("#sd_state").val();
	var districtContent = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (curState == districtValues[i].state) {
			districtContent += "<option value=" + i + ">" + districtValues[i].district + "</option>";
		}
	}
	$("#sd_district").html(districtContent);
}
function selectDistrictAndCheckAccess() {
	var selectedDistrict = districtValues[$("#sd_district").val()];
	var districtSearchInfo = {};
	districtSearchInfo.memberAcNo = curMemberAcNo;
	districtSearchInfo.districtId = selectedDistrict.districtId;
	districtSearchInfo.districtCode = selectedDistrict.districtCode;
	$("#sd_district").attr("disabled", "disabled");
	$("#sd_state").attr("disabled", "disabled");
	showMsgSD("Checking member access to district... <img src='resources/img/ajax-loader.gif' alt=''/>");
	ajaxCall(
			"/auth/v1/access_to_district/",
			"POST", true, JSON.stringify(districtSearchInfo),
			function(data, status) {
				$("#" + stateItemIdSD).val($("#sd_state option:selected").text());
				$("#" + districtItemIdSD).val($("#sd_district option:selected").text());
				showMsgSD("");
				$("#select_district_dlg").modal("hide");
			},
			function(xhr) {
				$("#sd_district").removeAttr("disabled");
				$("#sd_state").removeAttr("disabled");
				showErrorMsgSD("");
				handleErrorAndDisplayMsg(xhr, "sd_err_msg");
			});
}
function showMsgSD(msg) {
	$("#sd_err_msg").empty().hide();
	$("#sd_msg").html(msg).show();
}
function showErrorMsgSD(msg) {
	$("#sd_msg").empty().hide();
	$("#sd_err_msg").html(msg).show();
}