/**
 * 
 */
var searchedMembers = null;

$(document).ready(function() {
	$("#m_name_search1").click(searchMemberUsingName);
	$("#m_name_search2").click(searchMemberUsingName);
	$("#m_name_search3").click(searchMemberUsingName);
	$("#m_name1").change(searchMemberChangeHandler);
	$("#m_name2").change(searchMemberChangeHandler);
	$("#m_name3").change(searchMemberChangeHandler);
	$("#track_txn_tab").click(loadTxnTrackingTab);
	$("#searchTxBtn").click(searchTransactions);
	$("#txn_print").click(printTable);
	$("#txn_pdf").click(printTable);
	$("#txn_excel").click(genExcel);
});
function searchMemberChangeHandler() {
	var calledFromId = $(this).prop("id");
	if (calledFromId == "m_name1")
		$("#m_num1").val("");
	else if (calledFromId == "m_name2")
		$("#m_num2").val("");
	else if (calledFromId == "m_name3")
		$("#m_num3").val("");
}
function searchMemberUsingName() {
	var memberName = "";
	var calledFromId = $(this).prop("id");
	if (calledFromId == "m_name_search1")
		memberName = $("#m_name1").val();
	else if (calledFromId == "m_name_search2")
		memberName = $("#m_name2").val();
	else if (calledFromId == "m_name_search3")
		memberName = $("#m_name3").val();

	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching(calledFromId);
		ajaxCall("/member/v1/member_search_by_name/"
				+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
				+ memberName, "GET", true, "", function(data, status) {
					searchedMembers = data.memberNames;
					// TODO Do we need following??
					// groupBankAcNos = searchedMembers.groupBankAcNos;
					
					loadMemberSearchData(searchedMembers);
					
					for (var i = 0; i < searchedMembers.length; i++) {
						$("#member_" + i).click(loadSelectedMember);
					}
				}, memberSearchFailed);
	}
}
function loadSelectedMember() {
	var index = parseInt($(this).data("id"));
	var memberName = searchedMembers[index].memberName;
	var memberAcNo = searchedMembers[index].memberAcNo;

	var calledFromId = hideMemberSearchDialog();
	if (calledFromId == "m_name_search1") {
		$("#m_name1").val(memberName);
		$("#m_num1").val(memberAcNo);
	} else if (calledFromId == "m_name_search2") {
		$("#m_name2").val(memberName);
		$("#m_num2").val(memberAcNo);
	} else if (calledFromId == "m_name_search3") {
		$("#m_name3").val(memberName);
		$("#m_num3").val(memberAcNo);
	}
}
function loadTxnTrackingTab() {
	var curDate = new Date(currentServerTime);
	var year = curDate.getFullYear();
	var month = curDate.getMonth();
	var startDate = new Date(year, month, 1);
	var endDate = new Date(year, month + 1, 0);

	$("#track_sdate").val(convertTS2DDMMYYYYDate(startDate.getTime()));
	$("#track_edate").val(convertTS2DDMMYYYYDate(endDate.getTime()));
	$("#searchTxBtn").removeAttr("disabled");
}
function searchTransactions() {
	var sDate = $("#track_sdate").val();
	var eDate = $("#track_edate").val();
	if (sDate == "")
		showMessage("Please select a valid start date", "track_sdate");
	else if (eDate == "")
		showMessage("Please select a valid end date", "track_edate");
	else {
		$("#txn_table_header").html("");
		$("#txn_table_body")
				.html(
						"<tr><td>Please wait... <br>We are searching for transactions... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
		$("#searchTxBtn").attr("disabled", "disabled");

		var memberAcNo = 0, doneByMember = 0, approvedByMember = 0;
		if ($("#m_num1").val() != "")
			memberAcNo = $("#m_num1").val();
		if ($("#m_num2").val() != "")
			doneByMember = $("#m_num2").val();
		if ($("#m_num3").val() != "")
			approvedByMember = $("#m_num3").val();
		if ($("#m_name1").val() != "" && memberAcNo == 0)
			$("#m_name1").val("");
		if ($("#m_name2").val() != "" && doneByMember == 0)
			$("#m_name2").val("");
		if ($("#m_name3").val() != "" && approvedByMember == 0)
			$("#m_name3").val("");
		var startTime = convertDDMMYYYYDate2TS(sDate);
		var endTime = convertDDMMYYYYDate2TS(eDate) + 86399000; // 86399000
																// added to get
																// end time of
																// the day
		var txtype = $("#track_txtype option:selected").text();
		var txstatus = $("#track_txstatus option:selected").text();
		var paymode = $("#track_paymode option:selected").text();
		if (txtype == "All")
			txtype = "";
		if (txstatus == "All")
			txstatus = "";
		if (paymode == "All")
			paymode = "";

		var payload = "{\"lang\": \"" + $("#lang option:selected").text()
				+ "\",\"groupAcNo\": " + groupAcNo + ",\"txType\": \"" + txtype
				+ "\",\"txStatus\": \"" + txstatus + "\",\"paymentMode\": \""
				+ paymode + "\",\"startTime\": " + startTime + ",\"endTime\": "
				+ endTime + ",\"memberAcNo\": " + memberAcNo
				+ ",\"doneByMember\": " + doneByMember
				+ ",\"approvedByMember\": " + approvedByMember + "}";
		ajaxCall(
				"/transaction/v1/track_transactions",
				"POST",
				true,
				payload,
				function(data, status) {
					if (data != null && data != "" && data.transactions != null
							&& data.transactions.length != 0) {
						var headers = data.displayNames;
						$("#txn_table_header")
								.html(
										"<tr><th><strong>"
												+ headers.entryTs
												+ "</strong></th><th><strong>"
												+ headers.txId
												+ "</strong></th><th><strong>"
												+ headers.txType
												+ "</strong></th><th><strong>"
												+ headers.slipType
												+ "</strong></th><th><strong>"
												+ headers.slipNo
												+ "</strong></th><th><strong>"
												+ headers.savingAcNo
												+ "/"
												+ headers.memberLoanAcNo
												+ "</strong></th><th><strong>"
												+ headers.amount
												+ "</strong></th><th><strong>"
												+ headers.paymentMode
												+ "</strong></th><th><strong>"
												+ headers.chequeNo
												+ "</strong></th><th><strong>"
												+ headers.doneByMemberName
												+ "</strong></th><th><strong>"
												+ headers.approvedByMemberName
												+ "</strong></th><th><strong>"
												+ headers.memberBankAcDisplay
												+ "</strong></th><th><strong>"
												+ headers.status
												+ "</strong></th><th><strong>"
												+ headers.reasonToUndo
												+ "</strong></th><th><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
												+ headers.description
												+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong></th><th><strong>"
												+ headers.paymentTs
												+ "</strong></th><th><strong>"
												+ headers.approvedTs
												+ "</strong></th></tr>");

						var tablecontent = "";
						for (var k = 0; k < data.transactions.length; k++) {
							var txn = data.transactions[k];
							var acNo = txn.savingAcNo;
							if (txn.memberLoanAcNo != 0)
								acNo = txn.memberLoanAcNo;
							var bankAcc = "";
							if (txn.memberBankAcDisplay != null)
								bankAcc = txn.memberBankAcDisplay;

							tablecontent += "<tr><td>"
									+ convertTS2DDMMYYYYDate(txn.entryTs)
									+ "</td><td>" + txn.txId + "</td><td>"
									+ txn.txType + "</td><td>" + txn.slipType
									+ "</td><td>" + txn.slipNo + "</td><td>"
									+ acNo + "</td><td>" + txn.amount
									+ "</td><td>" + txn.paymentMode
									+ "</td><td>" + txn.chequeNo + "</td><td>"
									+ txn.doneByMemberName + "</td><td>"
									+ txn.approvedByMemberName + "</td><td>"
									+ bankAcc + "</td><td>" + txn.status
									+ "</td><td>" + txn.reasonToUndo
									+ "</td><td width='250'>" + txn.description
									+ "</td><td>"
									+ convertTS2DDMMYYYYDate(txn.paymentTs)
									+ "</td><td>"
									+ convertTS2DDMMYYYYDate(txn.approvedTs)
									+ "</td></tr>";
						}
						$("#txn_table_body").html(tablecontent);
					} else {
						$("#txn_table_body")
								.html(
										"<tr><td>No transactions found for given search criteria.</td></tr>");
					}
					$("#searchTxBtn").removeAttr("disabled");
				},
				function(xhr) {
					$("#searchTxBtn").removeAttr("disabled");
					handleErrorAndDisplayMsg(xhr, "txn_table_body");
				});
	}
}