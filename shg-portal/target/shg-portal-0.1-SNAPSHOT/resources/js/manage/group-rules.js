/**
 * 
 */
var groupRulesTree = null;

$(document).ready(function() {
	loadGroupRulesTab();
	$("#g_rules_tab").click(loadGroupRulesTab);
	$("#grules_edit_btn").click(function() {
		loadGroupRulesDiv(true);
	});
	$("#grules_back_btn").click(function() {
		loadGroupRulesDiv(false);
	});
	$("#grules_update_btn").click(updateGroupRuleClickHandler);
	$("#grules_print").click(printTable);
	$("#grules_pdf").click(printTable);
	$("#grules_excel").click(genExcel);
});
function showMsgGroupRules(msg) {
	$("#grules_msg").html(msg);
	$("#grules_err_msg").hide();
	$("#grules_msg").show();
}
function showErrorMsgGroupRules(msg) {
	$("#grules_err_msg").html(msg);
	$("#grules_msg").hide();
	$("#grules_err_msg").show();
}
function loadGroupRulesTab() {
	ajaxCall("/rule/v1/grules_display_tree/" + "/" + groupAcNo, "GET", true,
			"", function(data, status) {
				groupRulesTree = data;
				loadGroupRulesDiv(false);
			}, function(xhr) {
				showErrorMsgGroupRules("");
				handleErrorAndDisplayMsg(xhr, "grules_err_msg");
			});
}
function loadGroupRulesDiv(isEdit) {
	showMsgGroupRules("");
	if (groupRulesTree && groupRulesTree.rules && groupRulesTree.rules.length > 0) {
		if(isEdit) {
			$("#grules_edit_btn").hide();
			$("#grules_details").hide();
			$("#grules_edit_div").show();
			loadRecordTree(groupRulesTree.rules, "grules_edit_table", true);
			$("#grules_update_btn").show();
			$("#grules_back_btn").show();
		} else {
			$("#grules_update_btn").hide();
			$("#grules_back_btn").hide();
			$("#grules_edit_div").hide();
			$("#grules_details").show();
			loadRecordTree(groupRulesTree.rules, "grules_table");
			$("#grules_edit_btn").show();
		}
	} else {
		$("#grules_update_btn").hide();
		$("#grules_back_btn").hide();
		$("#grules_edit_btn").hide();
		$("#grules_edit_div").hide();
		$("#grules_details").show();
		$("#grules_table").html("<tr><td>No Rules to display</td></tr>");
	}
}
function updateGroupRuleClickHandler() {
	groupRulesTree.rules = getUpdatedRecordTree(groupRulesTree.rules);
	ajaxCall( "/rule/v1/update_grules", "POST", true, JSON.stringify(groupRulesTree),
			function(data, status) {
				groupRulesTree = data;
				loadGroupRulesDiv(false);
				showMsgGroupRules("Group rules updated Successfully.");
			}, function(xhr) {
				showErrorMsgGroupRules("");
				handleErrorAndDisplayMsg(xhr, "grules_err_msg");
			});
}
