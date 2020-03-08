/**
 * 
 */
function groupSearchIncompleteDataError() {
	$("#dialog_table_header").html("Group Search Error");
	$("#dialog_table_body")
			.html("<tr><td>Please provide 'District code' with 'Group Name' or 'Group Id' to search</td></tr>");
	$("#dialog_btn").html("OK");
}
function groupSearching() {
	$("#dialog_table_header").html("Searching...");
	$("#dialog_table_body")
			.html("<tr><td>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	$("#dialog_btn").html("Cancel");
}
function loadGroupSearchData(searchedGroups) {
	var tableContent = "<tr><td><strong>Account No.</strong></td><td><strong>Group Name</strong></td><td><strong>Group Type</strong></td><td><strong>Status</strong></td><td><strong>Place</strong></td></tr>";
	for (var i = 0; i < searchedGroups.length; i++) {
		tableContent += "<tr><td><a href='#' data-id='" + i
				+ "' id='group_" + i + "'>"
				+ getReadableAccNumForDistrict(searchedGroups[i].districtCode, searchedGroups[i].groupAcNo)
				+ "</a></td><td>" + searchedGroups[i].groupName + "</td><td>"
				+ searchedGroups[i].groupType + "</td><td>"
				+ searchedGroups[i].status + "</td><td>"
				+ searchedGroups[i].place + "</td></tr>";
	}
	$("#dialog_table_header").html("Select Group");
	$("#dialog_table_body").html(tableContent);
	$("#dialog_btn").html("OK");
}
function groupSearchFailed(xhr) {
	$("#dialog_table_header").html("Group Search Error");
	$("#dialog_btn").html("OK");
	handleErrorAndDisplayMsg(xhr, "dialog_table_body");
}
function hideGroupSearchDialog() {
	$("#modal_dialog").modal("hide");
	$("#from_id").val("");
	$("#dialog_table_header").html("");
	$("#dialog_table_body").html("");
}