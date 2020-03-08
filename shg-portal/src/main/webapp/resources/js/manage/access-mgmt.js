/**
 * 
 */
var searchedMembersAM = null, selectedMemberAM = null, searchedGroupAM = null, selectedGroupAM = null, memberAccessInfo = null;

$(document).ready(function() {
	$("#am_member_search").click(searchMember);
	$("#clear_search").click(clearMemberSearch);
	$("#update_member_parent_access_btn").click(updateMemberParentAccess);
	$("#dcode_am").change(districtCodeChangeHandlerAM);
	$("#groupid_am").change(groupidChangeHandlerAM);
	$("#s_grp_am_btn").click(searchGroupAM);
	$("#clear_group_search").click(clearGroupSearch);
	$("#add_member_access_to_group_btn").click(updateMemberAccessToGroup);
	$("#update_member_access_to_group_btn").click(updateMemberAccessToGroup);
	$("#remove_member_access_to_group_btn").click(removeMemberAccessToGroup);
	clearMemberSearch();
});
function searchMember() {
	var memberName = $("#member_name").val();
	clearMemberSearch();
	$("#member_name").val(memberName);
	
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
					searchedMembersAM = data.memberNames;
					loadMemberSearchData(searchedMembersAM);

					for (var i = 0; i < data.memberNames.length; i++) {
						$("#member_" + i).click(loadselectedMemberAM);
					}
				}, memberSearchFailed);
	}
}
function loadselectedMemberAM() {
	hideMemberSearchDialog();
	var index = parseInt($(this).data("id"));
	selectedMemberAM = searchedMembersAM[index];
	searchedMembersAM = null;
	$("#member_name").val(selectedMemberAM.memberName);
	$("#clear_search").show();
	$("#cur_member_name").html(selectedMemberAM.memberName);
	$("#cur_member_acc_num").html(getReadableAccNum(selectedMemberAM.memberAcNo));
	showMsgAM("Loading Member Parent Access Info... <img src='resources/img/ajax-loader.gif' alt=''/>");
	$("#member_parent_access_table").show();
	getMemberAccessInfo(selectedMemberAM);
	if(gType !== "SHG")
		$("#search_group_table").show();
}
function clearMemberSearch() {
	clearGroupSearch();
	showMsgAM("");
	$("#clear_search").hide();
	$("#member_name").val("");
	searchedMembersAM = null;
	selectedMemberAM = null;
	$("#search_group_table").hide();
	$("#member_parent_access_table").hide();
	$("#new_mrole").html("<option></option>");
	$("#new_member_status").html("<option></option>");
	$("#cur_member_name").html(" -- ");
	$("#cur_member_acc_num").html(" -- ");
	$("#cur_mrole").html(" -- ");
	$("#cur_member_status").html(" -- ");
	memberAccessInfo = null;
}
function getMemberAccessInfo(member) {
	ajaxCall(
			"/auth/v1/member_parent_access/" + member.memberAcNo,
			"GET", true, "",
			function(data, status) {
				memberAccessInfo = data;

				showMsgAM("");
				$("#cur_mrole").html(memberAccessInfo.mrole);
				$("#cur_member_status").html(memberAccessInfo.status);
				var possibleMroles = "";
				for(var i = 0; i < memberAccessInfo.possibleRoles.length; i++)
					possibleMroles += "<option>" + memberAccessInfo.possibleRoles[i] + "</option>";
				$("#new_mrole").html(possibleMroles);
				$("#new_member_status").html("<option>Active</option><option>Locked</option>");
				$("#new_mrole").val(memberAccessInfo.mrole);
				$("#new_member_status").val(memberAccessInfo.status);
			},
			function(xhr) {
				showErrorMsgAM("");
				handleErrorAndDisplayMsg(xhr, "am_err_msg");
			});
}
function updateMemberParentAccess() {
	var mAccessInfo = memberAccessInfo;
	if($("#new_mrole").val())
		mAccessInfo.mrole = $("#new_mrole").val();
	mAccessInfo.status = $("#new_member_status").val();
	showMsgAM("Updating Access Info... <img src='resources/img/ajax-loader.gif' alt=''/>");
	ajaxCall(
			"/auth/v1/update_member_parent_access/",
			"POST", true, JSON.stringify(mAccessInfo),
			function(data, status) {
				memberAccessInfo = data;
				$("#new_mrole").val(memberAccessInfo.mrole);
				$("#new_member_status").val(memberAccessInfo.status);
				$("#cur_mrole").html(memberAccessInfo.mrole);
				$("#cur_member_status").html(memberAccessInfo.status);
				showMsgAM("Member parent access updated successfully.");
			},
			function(xhr) {
				showErrorMsgAM("");
				handleErrorAndDisplayMsg(xhr, "am_err_msg");
			});
}
function searchGroupAM() {
	var district = $("#dcode_am").val();
	var grpName = $("#groupname_am").val();
	var grpId = $("#groupid_am").val();
	clearGroupSearch();
	$("#dcode_am").val(district);
	$("#groupname_am").val(grpName);
	$("#groupid_am").val(grpId);
	if (district == "" || (grpName == "" && grpId == ""))
		groupSearchIncompleteDataError();
	else {
		groupSearching();
		var payload = "{\"memberAcNo\":" + curMemberAcNo + ",\"searchDistrictCode\":\"" + district + "\"";
		if (grpId != "") {
			payload += ",\"searchGroupNo\":" + new Number(grpId) + "}";
			ajaxCall("/auth/v1/search_group_no_in_district/", "POST", true, payload, groupSearchSuccessAM, groupSearchFailed);
		} else if (grpName != "") {
			payload += ",\"searchGroupNameShort\":\"" + grpName + "\"}";
			ajaxCall("/auth/v1/search_group_by_name_in_district/", "POST", true, payload, groupSearchSuccessAM, groupSearchFailed);
		}
	}
}
function groupSearchSuccessAM(data, status) {
	searchedGroupAM = data.foundGroups;
	loadGroupSearchData(searchedGroupAM);

	for (var i = 0; i < searchedGroupAM.length; i++) {
		$("#group_" + i).click(loadselectedGroupAM);
	}
}
function loadselectedGroupAM() {
	hideGroupSearchDialog();
	var index = parseInt($(this).data("id"));	
	selectedGroupAM = searchedGroupAM[index];
	searchedGroupAM = null;
	showMsgAM("Loading Member Access To Selected Group... <img src='resources/img/ajax-loader.gif' alt=''/>");
	memberAccessInfo.groupsMappings = [];
	memberAccessInfo.groupsMappings[0] = {};
	memberAccessInfo.groupsMappings[0].groupAcNo = selectedGroupAM.groupAcNo;
	getMemberAccessToGroups(memberAccessInfo);
}
function clearGroupSearch() {
	showMsgAM("");
	$("#clear_group_search").hide();
	$("#dcode_am").val("");
	$("#groupname_am").val("");
	$("#groupid_am").val("");
	searchedGroupAM = null;
	selectedGroupAM = null;
	$("#member_access_to_group_table").hide();
	$("#cur_group_name").html(" -- ");
	$("#cur_group_acc_num").html(" -- ");
	$("#cur_group_type").html(" -- ");
	$("#cur_group_status").html(" -- ");
	if(memberAccessInfo)
		memberAccessInfo.groupsMappings = null;
}
function getMemberAccessToGroups(mAccessInfo) {
	ajaxCall(
			"/auth/v1/search_member_access_to_group/",
			"POST", true, JSON.stringify(mAccessInfo),
			function(data, status) {
				memberAccessInfo = data;
				showMsgAM("");
				$("#clear_group_search").show();
				$("#cur_group_name").html(selectedGroupAM.groupName);
				$("#cur_group_acc_num").html(getReadableAccNumForDistrict(selectedGroupAM.districtCode, selectedGroupAM.groupAcNo));
				$("#cur_group_type").html(memberAccessInfo.groupsMappings[0].groupType);
				$("#cur_group_status").html(memberAccessInfo.groupsMappings[0].groupStatus);
				displayMemberAccessToGroup(memberAccessInfo);
			},
			function(xhr) {
				searchedGroupAM = null;
				selectedGroupAM = null;
				showErrorMsgAM("");
				handleErrorAndDisplayMsg(xhr, "am_err_msg");
			});
}
function updateMemberAccessToGroup() {
	var mAccessInfo = memberAccessInfo;
	if($("#new_mrole_to_group").val())
		mAccessInfo.groupsMappings[0].mrole = $("#new_mrole_to_group").val();
	showMsgAM("Updating Access Info... <img src='resources/img/ajax-loader.gif' alt=''/>");
	ajaxCall(
			"/auth/v1/update_member_access_to_group/",
			"POST", true, JSON.stringify(mAccessInfo),
			function(data, status) {
				memberAccessInfo = data;
				showMsgAM("Member access updated successfully.");
				displayMemberAccessToGroup(memberAccessInfo);
			},
			function(xhr) {
				showErrorMsgAM("");
				handleErrorAndDisplayMsg(xhr, "am_err_msg");
			});
}
function removeMemberAccessToGroup() {
	var mAccessInfo = memberAccessInfo;
	showMsgAM("Removing Access... <img src='resources/img/ajax-loader.gif' alt=''/>");
	ajaxCall(
			"/auth/v1/remove_member_access_to_group/",
			"POST", true, JSON.stringify(mAccessInfo),
			function(data, status) {
				memberAccessInfo = data;
				showMsgAM("Member access removed successfully.");
				displayMemberAccessToGroup(memberAccessInfo);
			},
			function(xhr) {
				showErrorMsgAM("");
				handleErrorAndDisplayMsg(xhr, "am_err_msg");
			});
}
function displayMemberAccessToGroup(mAccessInfo) {
	var possibleMroles = "";
	for(var i = 0; i < mAccessInfo.possibleRoles.length; i++)
		possibleMroles += "<option>" + mAccessInfo.possibleRoles[i] + "</option>";
	$("#new_mrole_to_group").html(possibleMroles);
	var curMRole = mAccessInfo.groupsMappings[0].mrole;
	if(curMRole) {
		$("#new_mrole_to_group").val(curMRole);
		$("#cur_mrole_to_group").html(curMRole);
		$("#add_member_access_to_group_btn").attr("disabled", "disabled");
		$("#update_member_access_to_group_btn").removeAttr("disabled");
		$("#remove_member_access_to_group_btn").removeAttr("disabled");
	} else {
		$("#cur_mrole_to_group").html("No Access");
		$("#add_member_access_to_group_btn").removeAttr("disabled");
		$("#update_member_access_to_group_btn").attr("disabled", "disabled");
		$("#remove_member_access_to_group_btn").attr("disabled", "disabled");
	}
	$("#member_access_to_group_table").show();
}
function districtCodeChangeHandlerAM() {
	var district = $("#dcode_am").val();
	var distChars = district.split("");
	if (distChars.length != 4 || !isNaN(distChars[0]) || !isNaN(distChars[1])
			|| isNaN(distChars[2]) || isNaN(distChars[3])) {
		district = "";
		showMessage("Please enter proper district code with first 2 character and last 2 numbers. eg. MH01", "dcode_am");
	}
	$("#dcode_am").val(district);
}
function groupidChangeHandlerAM() {
	var prefix = "";
	var groupid = $("#groupid_am").val();
	if (groupid != "") {
		if (isNaN(groupid)) {
			showMessage("Please enter numbers only in group id field.", "groupid_am");
			groupid = "";
		} else {
			if (groupid.length > 5) {
				groupid = groupid.substring(0, 5);
			} else {
				for (var i = groupid.length; i < 5; i++)
					prefix += "0";
				groupid = prefix + groupid;
			}
		}
		$("#groupid_am").val(groupid);
	}
}
function showMsgAM(msg) {
	$("#am_err_msg").empty().hide();
	$("#am_msg").html(msg).show();
}
function showErrorMsgAM(msg) {
	$("#am_msg").empty().hide();
	$("#am_err_msg").html(msg).show();
}