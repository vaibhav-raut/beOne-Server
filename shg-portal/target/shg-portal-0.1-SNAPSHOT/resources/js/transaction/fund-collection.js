/**
 * 
 */
var loanTodoTxns = null, loanTableHeader = "";

$(document).ready(function() {
	addTxnAccessVal = $("#addTxnAccessVal").val();
	loadLoanCollectionTab();
	$("#loan_tab").click(loadLoanCollectionTab);
	$("#loan_search").click(filterLoanColTable);
	$("#loan_clear_search").click(clearFilterLoanColTable);
	$("#loan_status").change(filterLoanColTable);
	$("#loan_print").click(printTable);
	$("#loan_pdf").click(printTable);
	$("#loan_excel").click(genExcel);
});
function clearFilterLoanColTable() {
	$("#loan_input").val("");
	filterLoanColTable();
}
function filterLoanColTable() {
	if (loanTodoTxns != null) {
		var i = 0;
		var indexOfFilteredTxns = new Array();
		var statusOfFilteredTxns = new Array();
		var search_input = $("#loan_input").val();
		var status_input = $("#loan_status").val();

		var tablecontent = "";
		for (var k = 0; k < loanTodoTxns.length; k++) {
			var mName = loanTodoTxns[k].memberName;
			var txnStatus = loanTodoTxns[k].txTodoStatus;

			if ((search_input == "" || mName.toLowerCase().search(search_input.toLowerCase()) != -1)
					&& (status_input == "All" || txnStatus.search(status_input) != -1)) {
				indexOfFilteredTxns[i] = k;
				statusOfFilteredTxns[i] = txnStatus;
				i++;

				var paidAmount = 0;
				
				if (txnStatus !== "Todo" && txnStatus !== "Undone"
						&& txnStatus !== "Over Due"
						&& txnStatus !== "Previous Missed"
						&& txnStatus !== "Rejected")
					paidAmount = loanTodoTxns[k].amount;

				tablecontent += "<tr>";
				tablecontent += "<td>" + getReadableAccNum(loanTodoTxns[k].memberAcNo) + "</td>";
				tablecontent += "<td>" + mName + "</td>";
				tablecontent += "<td>" + (loanTodoTxns[k].amount - loanTodoTxns[k].interestComponent) + "</td>";
				tablecontent += "<td>" + loanTodoTxns[k].interestComponent + "</td>";
				tablecontent += "<td>" + convertTS2DDMMYYYYDate(loanTodoTxns[k].dueDateTs) + "</td>";
				tablecontent += "<td>" + paidAmount + "</td>";
				tablecontent += "<td>" + txnStatus + "</td>";

				if ((txnStatus === "Todo" || txnStatus === "Undone"
					|| txnStatus === "Over Due" || txnStatus === "Previous Missed")
					&& addTxnAccessVal >= UPDATE_ACCESS_VAL) {
					tablecontent += "<td align='center'><a class='btn btn-sm green' data-toggle='modal' data-id='"
							+ k + "' id='loan_done_" + k + "'><i class='fa fa-check'></i> Done</a></td>";
					if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
						tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></button></td>";
				} else if (txnStatus == "Done") {
					tablecontent += "<td align='center'><a class='btn btn-sm blue' data-toggle='modal' data-id='"
							+ k + "' id='loan_undo_" + k + "'><i class='fa fa-undo'></i> Undo</a></td>";
					if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
						tablecontent += "<td align='center'><a class='btn btn-sm yellow' data-toggle='modal' data-id='"
							+ k + "' id='loan_approve_" + k + "'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></a></td>";
				} else if ((txnStatus == "Approved" || txnStatus == "Auto Approved")
						&& addTxnAccessVal >= APPROVE_ACCESS_VAL) {
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-undo'></i> Undo</button></td>";
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></button></td>";
				} else if ((txnStatus == "Rejected" || txnStatus == "Auto Rejected")
						&& addTxnAccessVal >= APPROVE_ACCESS_VAL) {
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> Done</button></td>";
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></button></td>";
				}
				tablecontent += "</tr>";
			}
		}
		if (tablecontent == "")
			$("#loan_table_header").html("");
		else
			$("#loan_table_header").html(loanTableHeader);
		$("#loan_table_body").html(tablecontent);
		$("#loan_tx_count").html(statusOfFilteredTxns.length);
		$("#loan_filter").html(search_input.toLowerCase());
		if (search_input == "")
			$("#loan_clear_search").hide();
		else
			$("#loan_clear_search").show();

		for (k = 0; k < statusOfFilteredTxns.length; k++) {
			var index = indexOfFilteredTxns[k];
			var txnStatus = statusOfFilteredTxns[k];
			if ((txnStatus == "Todo" || txnStatus == "Undone"
				|| txnStatus == "Over Due" || txnStatus == "Previous Missed")
				&& addTxnAccessVal >= UPDATE_ACCESS_VAL) {
				$("#loan_done_" + index).click(launchLoanDoneDialog);
			} else if (txnStatus == "Done") {
				if(addTxnAccessVal >= UPDATE_ACCESS_VAL)
					$("#loan_undo_" + index).click(launchLoanUndoDialog);
				if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
					$("#loan_approve_" + index).click(launchLoanApproveDialog);
			}
		}
	}
}
function loadLoanCollectionTab() {
	var curDate = new Date();
	var year = curDate.getFullYear();
	var month = curDate.getMonth();
	var startTime = new Date(year, month, 1);
	var endTime = new Date(year, month + 1, 0, 23, 59, 59, 999);
	var url = "/transaction/v1/group_todo_transactions/"
			+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
			+ startTime.getTime().toString() + "/"
			+ endTime.getTime().toString() + "/Loan Installment";
	ajaxCall(url, "GET", true, "",
			function(data, status) {
				if (data != null && data != "") {
					// storing in global objects
					loanTodoTxns = data.todoTransactions;
					// TODO Do we need following??
					// groupBankAcNos = data.groupBankAcNos;
					ruleAmounts = data.ruleAmounts;

					var isSelectionExist = false;
					var oldSelection = $("#loan_status").val();
					var content = "<option>All</option>";
					for (var k = 0; k < data.txStatus.length; k++) {
						content += " <option>" + data.txStatus[k] + "</option>";
						if(oldSelection == data.txStatus[k])
							isSelectionExist = true;
					}
					$("#loan_status").html(content);
					if(isSelectionExist) $("#loan_status").val(oldSelection);
					
					var headers = data.displayNames;
					$("#loan_top_table_data").html(
							"<tr><th>" + headers.toPayMembersNo + "</th><th>"
									+ headers.paidMembersNo + "</th><th>"
									+ headers.pendingAmount + "</th><th>"
									+ headers.paidAmount + "</th></tr>"
									+ "<tr><td>" + data.toPayMembersNo
									+ "</td><td>" + data.paidMembersNo
									+ "</td><td>" + data.pendingAmount
									+ "</td><td>" + data.paidAmount
									+ "</td><tr>");

					loanTableHeader = "<tr><th><strong>"
							+ headers.memberAcNo
							+ "</strong></th><th><strong>"
							+ headers.memberName
							+ "</strong></th><th><strong>"
							+ headers.principleComponent
							+ "</strong></th><th><strong>"
							+ headers.interestComponent
							+ "</strong></th><th><strong>"
							+ headers.dueDateTs
							+ "</strong></th><th><strong>"
							+ headers.paidAmount
							+ "</strong></th><th><strong>"
							+ headers.txTodoStatus
							+ "</strong></th>";
					if(addTxnAccessVal >= UPDATE_ACCESS_VAL)
						loanTableHeader += "<th align='center'><strong>Done / Undo</strong></th>";
					if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
						loanTableHeader += "<th align='center'><strong>Approve / Reject</strong></th>";
					loanTableHeader += "</tr>";

					filterLoanColTable();
				} else {
					$("#loan_table_body").html("<tr><td>You do not have any data to display.</td></tr>");
				}
			}, function(xhr) {
				handleErrorAndDisplayMsg(xhr, "loan_table_body");
			});
}
function launchLoanDoneDialog() {
	var index = parseInt($(this).data("id"));
	$("#done_member_index").val(index);
	$("#done_tx_type").val("Loan Installment");
	$("#done_dialog_name").html("Loan Installment");
	launchDoneDialog(loanTodoTxns[index]);
}
function launchLoanUndoDialog() {
	var index = parseInt($(this).data("id"));
	$("#undo_member_index").val(index);
	$("#undo_tx_type").val("Loan Installment");
	$("#undo_dialog_name").html("Loan Installment");
	launchUndoDialog(loanTodoTxns[index]);
}
function launchLoanApproveDialog() {
	var index = parseInt($(this).data("id"));
	$("#approval_req_from").val("Loan Collection");
	$("#approve_dialog_name").html("Loan Installment");
	launchApproveRejectDialog(loanTodoTxns[index]);
}