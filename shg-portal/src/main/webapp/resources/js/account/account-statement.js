/**
 * 
 */
var statementType = "", accStatementTxns = new Array();
$(document).ready(function() {
	addTxnAccessVal = $("#addTxnAccessVal").val();
	loadAccStatementTab();
	$("#accstatement_tab").click(loadAccStatementTab);
	$("#as_cash_radio").change(statementTypeRadioChangeHandler);
	$("#as_bank_radio").change(statementTypeRadioChangeHandler);
	$("#as_joint_radio").change(statementTypeRadioChangeHandler);
	$("#as_load").click(loadAccStatementData);
	$("#as_print").click(printTable);
	$("#as_pdf").click(printTable);
	$("#as_excel").click(genExcel);
});
function loadAccStatementTab() {
	var date = $("#as_start_date").val();
	if (date == "") {
		var dateSplited = convertTS2DDMMYYYYDate(currentServerTime).split("/");
		if (dateSplited.length == 3) {
			$("#as_start_date").val(
					"01/" + dateSplited[1] + "/" + dateSplited[2]);
		}
	}
	if (groupAcProfile != null) {
		var lastSelected = $("#as_end_month option:selected").text();
		var content = "";
		for (var k = 0; k < groupAcProfile.monthsAvaliable.length; k++)
			content += "<option>" + groupAcProfile.monthsAvaliable[k]
					+ "</option>";
		$("#as_end_month").html(content);
		if (lastSelected != "")
			$("#as_end_month").val(lastSelected);
	}
	var gAccContent = "<option>All</option>";
	for (var i = 0; i < groupBankAcNos.length; i++)
		gAccContent += "<option>" + groupBankAcNos[i].displayAccount
				+ "</option>";
	$("#as_bankacc_num").html(gAccContent);
	statementTypeRadioChangeHandler();
}
function showASErrorMsg(show, msg) {
	if (show) {
		$("#as_err_msg").show();
		$("#as_err_msg").html("<td colspan='6'>" + msg + "</td>");
	} else {
		$("#as_err_msg").hide();
		$("#as_err_msg").html("");
	}
}
function loadAccStatementData() {
	showASErrorMsg(false, "");
	if ($("#as_start_date").val() == "") {
		showASErrorMsg(true, "Please enter start date.");
		return;
	}
	if (statementType == "") {
		showASErrorMsg(true, "Please select statement type.");
		return;
	}
	var startTime = convertDDMMYYYYDate2TS($("#as_start_date").val());
	var groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $(
			"#as_bankacc_num").val());
	var endMonth = $("#as_end_month option:selected").text();
	var url = "/report/v1/account_book/" + $("#lang option:selected").text()
			+ "/" + groupAcNo + "/" + statementType + "/" + groupBankAcNo + "/"
			+ startTime.toString() + "/" + endMonth;
	$("#as_table_top").html("");
	$("#as_table_header").html("");
	$("#as_table_body")
			.html(
					"<tr><td>Please wait... <br>Account Statement is loading... <img src='resources/img/ajax-loader.gif'/></td></tr>");
	ajaxCall(
			url,
			"GET",
			true,
			"",
			function(data, status) {
				accStatementTxns = data.transactions;
				var tablecontent = "";
				for (var k = 0; k < accStatementTxns.length; k++) {
					tablecontent += "<tr class='odd gradeX'><td>"
							+ convertTS2DDMMYYYYDate(accStatementTxns[k].paymentTs)
							+ "</td><td>" + accStatementTxns[k].txId + "</td>";
					var txWith = accStatementTxns[k].txWith;
					if (txWith == "MEMBER")
						tablecontent += "<td>" + getReadableAccNum(accStatementTxns[k].memberAcNo) + "</td>";
					else if (txWith == "GROUP")
						tablecontent += "<td>" + getReadableAccNum(accStatementTxns[k].groupAcNo) + "</td>";
					else if (txWith == "BANK")
						tablecontent += "<td>" + getReadableAccNum(accStatementTxns[k].externalGroupAcNo) + "</td>";
					else
						tablecontent += "<td></td>";
					tablecontent += "<td>" + accStatementTxns[k].memberName
							+ "</td><td>" + accStatementTxns[k].txType + "</td>";
					if ("RECEIPT" == accStatementTxns[k].slipType)
						tablecontent += "<td>" + accStatementTxns[k].amount + "</td><td></td>";
					else
						tablecontent += "<td></td><td>" + accStatementTxns[k].amount + "</td>";
					var balance = 0;
					if (accStatementTxns[k].clearBalance != null)
						balance += accStatementTxns[k].clearBalance;
					tablecontent += "<td>" + balance + "</td><td>"
							+ accStatementTxns[k].status + "</td>";
					var txnStatus = accStatementTxns[k].status;
					if (addTxnAccessVal >= APPROVE_ACCESS_VAL) {
						if (txnStatus == "Submitted" || txnStatus == "Missing Bank Info"
								|| txnStatus == "Payment Tobe Done" || txnStatus == "Complete")
							tablecontent += "<td><a class='btn btn-sm yellow' data-toggle='modal' data-id='"
								+ k + "' id='as_approve_" + accStatementTxns[k].txId
								+ "'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></a></td>";
						else
							tablecontent += "<td></td>";
					}
					tablecontent += "</tr>";
				}
				if (tablecontent != "") {
					var headers = data.displayNames;
					$("#as_table_top").html(
							"<tr><th>" + headers.noOfTxs + "</th><th>"
									+ headers.noOfApprovedTxs + "</th><th>"
									+ headers.totalReceivedAmount + "</th><th>"
									+ headers.totalPaidAmount + "</th><th>"
									+ headers.openingClearBalance + "</th><th>"
									+ headers.closingClearBalance + "</th><th>"
									+ headers.closingBalanceSubjectedToClearing
									+ "</th></tr><tr><td>" + data.noOfTxs
									+ "</td><td>" + data.noOfApprovedTxs
									+ "</td><td>" + data.totalReceivedAmount
									+ "</td><td>" + data.totalPaidAmount
									+ "</td><td>" + data.openingClearBalance
									+ "</td><td>" + data.closingClearBalance
									+ "</td><td>"
									+ data.closingBalanceSubjectedToClearing
									+ "</td><tr>");
					var headerContent = "<tr><th><strong>Date</strong></th><th><strong>Transaction Id</strong></th><th><strong>Account No.</strong></th><th><strong>Name</strong></th><th><strong>Transaction Type</strong></th>"
									+ "<th><strong>Received Amount</strong></th><th><strong>Paid Amount</strong></th><th><strong>Balance</strong></th><th><strong>Status</strong></th>";
					if (addTxnAccessVal >= APPROVE_ACCESS_VAL)
						headerContent += "<th><strong>Approve / Reject</strong></th>";
					headerContent += "</tr>";
					$("#as_table_header").html(headerContent);
				} else
					tablecontent = "<tr><td>No data available for given selection.</td></tr>";
				$("#as_table_body").html(tablecontent);
				for (k = 0; k < accStatementTxns.length; k++)
					$("#as_approve_" + accStatementTxns[k].txId).click(
							launchASTxnApproveDialog);
			},
			function(xhr) {
				$("#as_table_body")
						.html(
								"<tr><td>("
										+ xhr.status
										+ ") Error occured while loading account statement."
										+ "</td></tr>");
				$("#as_table_body").html(
						"<tr><td>" + JSON.parse(xhr.responseText).error.message
								+ "</td></tr>");
			});
}
function launchASTxnApproveDialog() {
	var index = parseInt($(this).data("id"));
	$("#approval_req_from").val("Account Statement");
	$("#approve_dialog_name").html("Approve Transaction");
	launchApproveRejectDialog(accStatementTxns[index]);
}
function statementTypeRadioChangeHandler() {
	statementType = "";
	if ($("#as_cash_radio").is(":checked")) {
		statementType = "Cash Book";
		$("#as_bankacc_num").attr("disabled", "disabled");
	} else if ($("#as_bank_radio").is(":checked")) {
		statementType = "Bank Book";
		$("#as_bankacc_num").removeAttr("disabled");
	} else if ($("#as_joint_radio").is(":checked")) {
		statementType = "Joint Book";
		$("#as_bankacc_num").attr("disabled", "disabled");
	}
}