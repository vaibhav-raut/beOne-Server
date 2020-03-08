/**
 * 
 */
var searchedMembersForAcc;

$(document).ready(function() {
	loadMemberAccountTab();
	$("#m_name_search").click(searchMemberUsingName);
	$("#m_acc_load_data").click(loadMemberAccountBook);
	$("#m_acc_tab").click(loadMemberAccountTab);
	$("#m_acc_print").click(printTable);
	$("#m_acc_pdf").click(printTable);
	$("#m_acc_excel").click(genExcel);
});
function loadMemberAccountTab() {
	ajaxCall("/group/v1/group_ac/" + $("#lang option:selected").text() + "/"
			+ groupAcNo, "GET", true, "", function(data, status) {
		var months = data.monthsAvaliable;
		var content = "", revContent = "";
		for (var k = 0; k < months.length; k++) {
			content += "<option>" + months[k] + "</option>";
			revContent += "<option>" + months[months.length - k - 1]
					+ "</option>";
		}
		$("#m_acc_start_month").html(revContent);
		$("#m_acc_end_month").html(content);
	});
}
function searchMemberUsingName() {
	$("#member_num").val("");
	$("#member_num_txt").html("N/A");
	$("#m_acc_table_header").html("");
	$("#m_acc_table_body").html("");

	var memberName = $("#member_name").val();
	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching();
		ajaxCall(
				"/member/v1/member_search_by_name/"
						+ $("#lang option:selected").text() + "/" + groupAcNo
						+ "/" + memberName,
				"GET",
				true,
				"",
				function(data, status) {
					searchedMembersForAcc = data.memberNames;
					loadMemberSearchData(searchedMembersForAcc);

					for (var i = 0; i < data.memberNames.length; i++) {
						$("#member_" + i).click(loadSelectedMember);
					}
				}, memberSearchFailed);
	}
}
function loadSelectedMember() {
	hideMemberSearchDialog();
	var index = parseInt($(this).data("id"));
	var memberName = searchedMembersForAcc[index].memberName;
	var memberAcNo = searchedMembersForAcc[index].memberAcNo;
	$("#member_name").val(memberName);
	$("#member_num").val(memberAcNo);
	$("#member_num_txt").html(getReadableAccNum(memberAcNo));
//	loadMemberAccountBook();
}
function loadMemberAccountBook() {
	var memberNum = $("#member_num").val();
	var sMonth = $("#m_acc_start_month option:selected").text();
	var eMonth = $("#m_acc_end_month option:selected").text();
	if (memberNum == "")
		showMessage("Please search and select member first.", "member_name");
	else if (sMonth == "")
		showMessage("Please select start month/date", "m_acc_start_month");
	else if (eMonth == "")
		showMessage("Please select end month/date", "m_acc_end_month");
	else {
		$("#m_acc_table_header").html("");
		$("#m_acc_table_body")
				.html(
						"<tr><td>Please wait... <br>Data is loading... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
		var url = "/report/v1/member_ac_book/"
				+ $("#lang option:selected").text() + "/" + memberNum + "/"
				+ sMonth + "/" + eMonth;
		ajaxCall(url, "GET", true, "", function(data, status) {
			if (data != "" && data.monthlyAcs != null
					&& data.monthlyAcs.length != 0) {
				var headers = data.displayNames;
				$("#m_acc_table_header").html(
						"<tr><th><strong>" + headers.month
								+ "</strong></th><th><strong>"
								+ headers.plannedMonthlySavingAm
								+ "</strong></th><th><strong>"
								+ headers.savedAm
								+ "</strong></th><th><strong>"
								+ headers.provIntEnAm
								+ "</strong></th><th><strong>"
								+ headers.prevMonthIntEnAm
								+ "</strong></th><th><strong>" + headers.loanAm
								+ "</strong></th><th><strong>"
								+ headers.outstandingLoanAm
								+ "</strong></th><th><strong>"
								+ headers.recIntOnLoanAm
								+ "</strong></th><th><strong>"
								+ headers.returnedSavedAm
								+ "</strong></th><th><strong>"
								+ headers.returnedIntEnAm
								+ "</strong></th><th><strong>"
								+ headers.dividedProfitPaidAm
								+ "</strong></th><th><strong>"
								+ headers.dividedProfitDeclaredAm
								+ "</strong></th><th><strong>"
								+ headers.recPenaltyAm
								+ "</strong></th><th><strong>"
								+ headers.pendingPenaltyAm
								+ "</strong></th></tr>");

				var tablecontent = "";
				for (var k = 0; k < data.monthlyAcs.length; k++) {
					var acc = data.monthlyAcs[k];
					tablecontent += "<tr><td>" + acc.month + "</td><td>"
							+ acc.plannedMonthlySavingAm + "</td><td>"
							+ acc.savedAm + "</td><td>" + acc.provIntEnAm
							+ "</td><td>" + acc.prevMonthIntEnAm + "</td><td>"
							+ acc.loanAm + "</td><td>" + acc.outstandingLoanAm
							+ "</td><td>" + acc.recIntOnLoanAm + "</td><td>"
							+ acc.returnedSavedAm + "</td><td>"
							+ acc.returnedIntEnAm + "</td><td>"
							+ acc.dividedProfitPaidAm + "</td><td>"
							+ acc.dividedProfitDeclaredAm + "</td><td>"
							+ acc.recPenaltyAm + "</td><td>"
							+ acc.pendingPenaltyAm + "</td></tr>";
				}
				$("#m_acc_table_body").html(tablecontent);
			} else {
				$("#m_acc_table_body").html(
						"<tr><td>No account detail found.</td></tr>");
			}
		}, function(xhr) {
			handleErrorAndDisplayMsg(xhr, "m_acc_table_body");
		});
	}
}