/**
 * 
 */
function memberSearchIncompleteDataError() {
	$("#dialog_table_header").html("Member Search Error");
	$("#dialog_table_body")
			.html("<tr><td align='center'>Please provide 'Member Name' to search</td></tr>");
	$("#dialog_btn").html("OK");
}
function memberSearching(calledFromId) {
	$("#from_id").val(calledFromId);
	$("#dialog_table_header").html("Searching...");
	$("#dialog_table_body")
			.html("<tr><td>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	$("#dialog_btn").html("Cancel");
}
function loadMemberSearchData(searchedMembers, showMemberStatus) {
	var tableContent = "<tr><td><strong>Account No.</strong></td><td><strong>Member Name</strong></td><td><strong>Member Role</strong></td>";
	if(showMemberStatus)
		tableContent += "<td><strong>Status</strong></td>";
	tableContent += "</tr>";
	for (var i = 0; i < searchedMembers.length; i++) {
		tableContent += "<tr><td width='150'><a href='#' data-id='" + i
				+ "' id='member_" + i + "'>"
				+ getReadableAccNum(searchedMembers[i].memberAcNo)
				+ "</a></td><td>" + searchedMembers[i].memberName
				+ "</td><td>"+ searchedMembers[i].mrole + "</td>";
		if(showMemberStatus)
			tableContent += "<td>" + searchedMembers[i].status + "</td>";
		tableContent += "</tr>";
	}
	$("#dialog_table_header").html("Select Member");
	$("#dialog_table_body").html(tableContent);
	$("#dialog_btn").html("OK");
}
function memberSearchFailed(xhr) {
	$("#from_id").val("");
	$("#dialog_table_header").html("Member Search Error");
	$("#dialog_btn").html("OK");
	handleErrorAndDisplayMsg(xhr, "dialog_table_body");
}
function hideMemberSearchDialog() {
	var calledFromId = $("#from_id").val();
	$("#modal_dialog").modal("hide");
	$("#from_id").val("");
	$("#dialog_table_header").html("");
	$("#dialog_table_body").html("");
	return calledFromId;
}