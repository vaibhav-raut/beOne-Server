/**
 * 
 */

$(document).ready(function() {
	$("#grules_edit_btn").click(editGroupRules);
	$("#grules_update_btn").click(updateGroupRules);
	$("#grules_done_btn").click(doneEditing);
});
function editGroupRules() {
	$("#tools_btn").hide();
	$("#grules_details").hide();
	$("#grules_edit_btn").hide();
	$("#grules_done_btn").show();
	$("#grules_update_btn").show();
	$("#grules_edit_div").show();
}
function updateGroupRules() {
	showMessage("NOT IMPLEMENTED");
}