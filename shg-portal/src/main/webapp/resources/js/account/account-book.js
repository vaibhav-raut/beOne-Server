/**
 * 
 */
var bookType = "", accBookTxns = new Array();
$(document).ready(function() {
	addTxnAccessVal = $("#addTxnAccessVal").val();
	loadAccountBookTab();
	$("#accbook_tab").click(loadAccountBookTab);
	$("#ab_cash_radio").change(bookTypeRadioChangeHandler);
	$("#ab_bank_radio").change(bookTypeRadioChangeHandler);
	$("#ab_joint_radio").change(bookTypeRadioChangeHandler);
	$("#ab_load").click(loadAccountBookData);
	$("#ab_print").click(printTable);
	$("#ab_pdf").click(printTable);
	$("#ab_excel").click(genExcel);
});
function loadAccountBookTab() {
	var date = $("#ab_start_date").val();
	if (date == "") {
		var dateSplited = convertTS2DDMMYYYYDate(currentServerTime).split("/");
		if (dateSplited.length == 3) {
			$("#ab_start_date").val(
					"01/" + dateSplited[1] + "/" + dateSplited[2]);
		}
	}
	if (groupAcProfile != null) {
		var lastSelected = $("#ab_end_month option:selected").text();
		var content = "";
		for (var k = 0; k < groupAcProfile.monthsAvaliable.length; k++)
			content += "<option>" + groupAcProfile.monthsAvaliable[k]
					+ "</option>";
		$("#ab_end_month").html(content);
		if (lastSelected != "")
			$("#ab_end_month").val(lastSelected);
	}
	var gAccContent = "<option>All</option>";
	for (var i = 0; i < groupBankAcNos.length; i++) {
		var bType = groupBankAcNos[i].bankAccountType;
		if (bType == "Saving Account" || bType == "Current Account") {
			gAccContent += "<option>" + groupBankAcNos[i].displayAccount
					+ "</option>";
		}
	}
	$("#ab_bankacc_num").html(gAccContent);
	bookTypeRadioChangeHandler();
}
function showAccBookErrorMsg(show, msg) {
	if (show) {
		$("#ab_err_msg").show();
		$("#ab_err_msg").html("<td colspan='6'>" + msg + "</td>");
	} else {
		$("#ab_err_msg").hide();
		$("#ab_err_msg").html("");
	}
}
function loadAccountBookData() {
	showAccBookErrorMsg(false, "");
	if ($("#ab_start_date").val() == "") {
		showAccBookErrorMsg(true, "Please enter start date.");
		return;
	}
	if (bookType == "") {
		showAccBookErrorMsg(true, "Please select book type.");
		return;
	}
	var startTime = convertDDMMYYYYDate2TS($("#ab_start_date").val());
	var groupBankAcNo = getBankAccFromDisplayName(groupBankAcNos, $(
			"#ab_bankacc_num").val());
	var endMonth = $("#ab_end_month option:selected").text();
	var url = "/report/v1/ac_book/" + $("#lang option:selected").text() + "/"
			+ groupAcNo + "/" + bookType + "/" + groupBankAcNo + "/"
			+ startTime.toString() + "/" + endMonth;
	$("#ab_table")
			.html(
					"<tr><td>Please wait... <br>Account book is loading... <img src='resources/img/ajax-loader.gif'/></td></tr>");
	ajaxCall(
			url,
			"GET",
			true,
			"",
			function(data, status) {
				var dates = data.dates;
				if (dates != null && dates.length > 0) {
					var tablecontent = "", dataIdIndex = 1;
					for (var i = 0; i < dates.length; i++) {
						var acRecords = data.book[dates[i]];
						for (var j = 0; j < acRecords.length; j++) {
							var parentIdIndex = dataIdIndex++;
							tablecontent += "<tr data-tt-id="
									+ parentIdIndex
									+ " style='color: #098dc7; font-weight: bold'><td>"
									+ acRecords[j].date + "</td><td>"
									+ acRecords[j].slipNo + "</td><td>"
									+ acRecords[j].txNo + "</td><td>"
									+ acRecords[j].particular + "</td><td>"
									+ acRecords[j].amount + "</td><td>"
									+ acRecords[j].txStatus + "</td><td>"
									+ acRecords[j].receivedAm + "</td><td>"
									+ acRecords[j].paidAm + "</td><td>"
									+ acRecords[j].balance + "</td></tr>";

							var records = acRecords[j].records;
							if (records != null) {
								for (var k = 0; k < records.length; k++) {
									tablecontent += "<tr data-tt-id="
											+ (dataIdIndex++)
											+ " data-tt-parent-id="
											+ parentIdIndex + "><td>"
											+ records[k].date + "</td><td>"
											+ records[k].slipNo + "</td><td>"
											+ records[k].txNo + "</td><td>"
											+ records[k].particular
											+ "</td><td>" + records[k].amount
											+ "</td><td>" + records[k].txStatus
											+ "</td>";
									// Displaying approve/reject button instead
									// of records[k].receivedAm field.
									if (addTxnAccessVal >= APPROVE_ACCESS_VAL && records[k].txStatus == "Submitted") {
										var txn = records[k].transaction;
										tablecontent += "<td><a class='btn btn-sm yellow' title='Click to approve' data-toggle='modal' data-id='"
												+ accBookTxns.length
												+ "' id='ab_approve_"
												+ txn.txId
												+ "'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></a></td>";
										accBookTxns[accBookTxns.length] = txn;
									}
									tablecontent += "<td>" + records[k].paidAm
											+ "</td><td>" + records[k].balance
											+ "</td></tr>";
								}
							}
						}
						tablecontent += "<tr bgcolor='#ddd'><td colspan='9'></td></tr>";
					}
					var header = data.displayNames;
					var headerContent = "<tr><td width='100'><strong>"
							+ header.date + "</strong></td><td><strong>"
							+ header.slipNo + "</strong></td><td><strong>"
							+ header.txNo + "</strong></td><td><strong>"
							+ header.particular + "</strong></td><td><strong>"
							+ header.amount + "</strong></td><td><strong>"
							+ header.txStatus + "</strong></td><td><strong>"
							+ header.receivedAm + "</strong></td><td><strong>"
							+ header.paidAm + "</strong></td><td><strong>"
							+ header.balance + "</strong></td></tr>";
					$("#ab_table").html(headerContent + tablecontent);
					$("#ab_table").agikiTreeTable({
						persist : true,
						persistStoreName : "files"
					});
					for (k = 0; k < accBookTxns.length; k++)
						$("#ab_approve_" + accBookTxns[k].txId).click(
								launchABTxnApproveDialog);
				} else {
					$("#ab_table")
							.html(
									"<tr><td>No data available for given selection</td></tr>");
				}
			}, function(xhr) {
				handleErrorAndDisplayMsg(xhr, "ab_table");
			});
}
function launchABTxnApproveDialog() {
	var index = parseInt($(this).data("id"));
	$("#approval_req_from").val("Account Book");
	$("#approve_dialog_name").html("Approve Transaction");
	launchApproveRejectDialog(accBookTxns[index]);
}
function bookTypeRadioChangeHandler() {
	bookType = "";
	if ($("#ab_cash_radio").is(":checked")) {
		bookType = "Cash Book";
		$("#ab_bankacc_num").attr("disabled", "disabled");
	} else if ($("#ab_bank_radio").is(":checked")) {
		bookType = "Bank Book";
		$("#ab_bankacc_num").removeAttr("disabled");
	} else if ($("#ab_joint_radio").is(":checked")) {
		bookType = "Joint Book";
		$("#ab_bankacc_num").attr("disabled", "disabled");
	}
}