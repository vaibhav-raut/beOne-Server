/**
 * 
 */
var searchedGroupSelGrp = null;

$(document).ready(function() {
	$("#sel_grp_btn").click(selectGroup);
	$("#sel_grp_search_btn").click(searchGroupSelGrp);
	$("#sel_grp_reset_btn").click(resetSelGrpForm);
	$("#sel_grp_district").change(districtSelGrpChangeHandler);
	$("#sel_grp_groupid").change(groupidSelGrpChangeHandler);
});
function selectGroup() {
	var district = $("#sel_grp_district").val();
	var groupid = $("#sel_grp_groupid").val();
	var password = $("#sel_grp_password").val();
	if (district == "" || groupid == "" || password == "") {
		$("#sel_grp_err_msg").html("Please enter district code, group id and password to select group.");
	} else {
		$("#sel_grp_btn").attr("disabled", "disabled");
		document.selGrpForm.action = "selectgroup";
		document.selGrpForm.submit();
	}
}
function districtSelGrpChangeHandler() {
	var district = $("#sel_grp_district").val();
	var distChars = district.split("");
	if (distChars.length != 4 || !isNaN(distChars[0]) || !isNaN(distChars[1])
			|| isNaN(distChars[2]) || isNaN(distChars[3])) {
		district = "";
		$("#sel_grp_err_msg").html("Please enter proper district code with first 2 character and last 2 numbers. eg. MH01");
		$("#sel_grp_district").focus();
	}
	$("#sel_grp_district").val(district);
}
function groupidSelGrpChangeHandler() {
	var prefix = "";
	var groupid = $("#sel_grp_groupid").val();
	if (groupid != "") {
		if (isNaN(groupid)) {
			$("#sel_grp_err_msg").html("Please enter numbers only in group id field.");
			groupid = "";
			$("#sel_grp_groupid").focus();
		} else {
			if (groupid.length > 5) {
				groupid = groupid.substring(0, 5);
			} else {
				for (var i = groupid.length; i < 5; i++)
					prefix += "0";
				groupid = prefix + groupid;
			}
		}
		$("#sel_grp_groupid").val(groupid);
	}
}
function resetSelGrpForm() {
	$("#sel_grp_err_msg").html("");
	$("#sel_grp_district1").val("");
	$("#sel_grp_groupid1").val("");
	$("#sel_grp_name").val("");
	$("#s_grp_header").hide();
	$("#s_grp_num").html("Search group to verify");
	$("#s_grp_name").html("");
	$("#s_grp_place").html("");
}
function searchGroupSelGrp() {
	var district = $("#sel_grp_district").val();
	var grpName = $("#sel_grp_name").val();
	var grpId = $("#sel_grp_groupid").val();
	if (district == "" || (grpName == "" && grpId == ""))
		groupSearchIncompleteDataError();
	else {
		groupSearching();
		var payload = "{\"memberAcNo\":" + curMemberAcNo + ",\"searchDistrictCode\":\"" + district + "\"";
		if (grpId != "") {
			payload +=  ",\"searchGroupNo\":" + new Number(grpId) + "}";
			ajaxCall("/auth/v1/search_group_no_in_district/", "POST", true, payload, groupSearchSuccessSelGrp, groupSearchFailed);
		} else if (grpName != "") {
			payload +=  ",\"searchGroupNameShort\":\"" + grpName + "\"}";
			ajaxCall("/auth/v1/search_group_by_name_in_district/", "POST", true, payload, groupSearchSuccessSelGrp, groupSearchFailed);
		}
	}
}
function groupSearchSuccessSelGrp(data, status) {
	searchedGroupSelGrp = data.foundGroups;
	loadGroupSearchData(searchedGroupSelGrp);

	for (var i = 0; i < searchedGroupSelGrp.length; i++) {
		$("#group_" + i).click(loadSelectedGroupSelGrp);
	}
}
function loadSelectedGroupSelGrp() {
	hideGroupSearchDialog();
	var index = parseInt($(this).data("id"));
	var selectedGroup = searchedGroupSelGrp[index];
	var selGroupAcNo = "" + selectedGroup.groupAcNo;
	$("#sel_grp_groupid").val(selGroupAcNo.substring(selGroupAcNo.length - 5, selGroupAcNo.length));
	
	$("#s_grp_header").show();
	$("#s_grp_num").html(getReadableAccNumForDistrict(selectedGroup.districtCode, selGroupAcNo));
	$("#s_grp_name").html(selectedGroup.groupName);
	$("#s_grp_place").html(selectedGroup.place);
	searchedGroupSelGrp = null;	
}