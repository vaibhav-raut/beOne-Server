/**
 * 
 */
$(document).ready(function() {
	addEventListener("groupAcProfile", loadUpdateGroupTab);
	$("#u_group_status_btn").click(updateGroupStatus);
});
function loadUpdateGroupTab() {
	$("#u_status_row").hide();
    if(groupAcProfile && groupAcProfile.activeStatus) {
    	$("#cur_grp_status").html(groupAcProfile.activeStatus);
    	if(groupAcProfile.activeStatus.search(/Active|Inactive|Idle|Locked/i) === -1) {
    		showMsgGroupStatus("Status update is not allowed for this Group.");
	    } else {
	    	if(groupAcProfile.activeStatus === "Locked") {
		    	$("#new_grp_status").html("<option value='Inactive'>Unlock</option><option value='Closed'>Close</option><option value='Junk'>Mark Junk</option>");
	    	} else {
		    	$("#new_grp_status").html("<option value='Locked'>Lock</option><option value='Closed'>Close</option><option value='Junk'>Mark Junk</option>");
	    	}
	    	$("#u_status_row").show();
	    }
    } else {
    	showErrMsgGroupStatus("Problem while loading current group status.");
    }
}
function updateGroupStatus() {
	if (confirm("Are you sure to update group status?")) {
		var curStatus = groupAcProfile.activeStatus;
		groupAcProfile.activeStatus = $("#new_grp_status").val();
		var bankAccountDisplay = groupAcProfile.bankAccountDisplay;
		groupAcProfile.bankAccountDisplay = null;
		var payload = JSON.stringify(groupAcProfile);
		groupAcProfile.activeStatus = curStatus;
		groupAcProfile.bankAccountDisplay = bankAccountDisplay;
		
		$("#new_grp_status").attr("disabled", "disabled");
		$("#u_group_status_btn").attr("disabled", "disabled");
		ajaxCall("/group/v1/update_group_status", "POST", false, payload,
				function(data, status) {
					groupAcProfile = data;
					dispatchEvent(new Event("groupAcProfile"));
					showMsgGroupStatus("Group status updated successfully.");
					$("#new_grp_status").removeAttr("disabled");
					$("#u_group_status_btn").removeAttr("disabled");
				}, function(xhr) {
					$("#new_grp_status").removeAttr("disabled");
					$("#u_group_status_btn").removeAttr("disabled");
					showErrMsgGroupStatus("");
					handleErrorAndDisplayMsg(xhr, "u_status_err_msg");
				});
	}
}
function showMsgGroupStatus(msg) {
	$("#u_status_err_msg").empty();
	$("#u_status_err_msg").hide();
	$("#u_status_msg").html(msg);
	$("#u_status_msg").show();
}
function showErrMsgGroupStatus(msg) {
	$("#u_status_msg").empty();
	$("#u_status_msg").hide();
	$("#u_status_err_msg").html(msg);
	$("#u_status_err_msg").show();
}