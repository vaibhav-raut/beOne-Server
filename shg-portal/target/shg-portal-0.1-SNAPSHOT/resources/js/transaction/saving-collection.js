/**
 * 
 */
var savingTodoTxns = null, scTableHeader = "", ruleAmounts;

$(document).ready(function() {
	addTxnAccessVal = $("#addTxnAccessVal").val();
	loadSavingCollectionTab();
	$("#sc_tab").click(loadSavingCollectionTab);
	$("#sc_search").click(filterSavingColTable);
	$("#sc_clear_search").click(clearFilterSavingColTable);
	$("#sc_status").change(filterSavingColTable);
	$("#sc_print").click(printTable);
	$("#sc_pdf").click(printTable);
	$("#sc_excel").click(genExcel);
});
function clearFilterSavingColTable() {
	$("#sc_input").val("");
	filterSavingColTable();
}
function filterSavingColTable() {
	if (savingTodoTxns != null) {
		var i = 0;
		var indexOfFilteredTxns = new Array();
		var statusOfFilteredTxns = new Array();
		var search_input = $("#sc_input").val();
		var status_input = $("#sc_status").val();

		var tablecontent = "";
		for (var k = 0; k < savingTodoTxns.length; k++) {
			var mName = savingTodoTxns[k].memberName;
			var txnStatus = savingTodoTxns[k].txTodoStatus;

			if ((search_input == "" || mName.toLowerCase().search(search_input.toLowerCase()) != -1)
					&& (status_input == "All" || txnStatus.search(status_input) != -1)) {
				indexOfFilteredTxns[i] = k;
				statusOfFilteredTxns[i] = txnStatus;
				i++;

				var paidAmount = 0;
				if (txnStatus == "Todo" || txnStatus == "Undone"
						|| txnStatus == "Over Due"
						|| txnStatus == "Previous Missed"
						|| txnStatus == "Rejected")
					paidAmount = savingTodoTxns[k].amount;
				var pendingAmount = savingTodoTxns[k].amount - paidAmount;

				tablecontent += "<tr>";
				tablecontent += "<td>" + getReadableAccNum(savingTodoTxns[k].memberAcNo) + "</td>";
				tablecontent += "<td>" + mName + "</td>";
				tablecontent += "<td>" + paidAmount + "</td>";
				tablecontent += "<td>" + convertTS2DDMMYYYYDate(savingTodoTxns[k].dueDateTs) + "</td>";
				tablecontent += "<td>" + pendingAmount + "</td>";
				tablecontent += "<td>" + txnStatus + "</td>";

				if ((txnStatus === "Todo" || txnStatus === "Undone"
					|| txnStatus === "Over Due" || txnStatus === "Previous Missed")
					&& addTxnAccessVal >= UPDATE_ACCESS_VAL) {
					tablecontent += "<td align='center'><a class='btn btn-sm green' data-toggle='modal' data-id='"
							+ k + "' id='saving_done_" + k + "'><i class='fa fa-check'></i> Done</a></td>";
					if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
						tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></button></td>";
				} else if (txnStatus == "Done" && addTxnAccessVal >= UPDATE_ACCESS_VAL) {
					tablecontent += "<td align='center'><a class='btn btn-sm blue' data-toggle='modal' data-id='"
							+ k + "' id='saving_undo_" + k + "'><i class='fa fa-undo'></i> Undo</a></td>";
					if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
						tablecontent += "<td align='center'><a class='btn btn-sm yellow' data-toggle='modal' data-id='"
							+ k + "' id='saving_approve_" + k + "'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></a></td>";
				} else if ((txnStatus == "Approved" || txnStatus == "Auto Approved")
						&& addTxnAccessVal >= APPROVE_ACCESS_VAL) {
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-undo'></i>Undo</button></td>";
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></button></td>";
				} else if ((txnStatus == "Rejected" || txnStatus == "Auto Rejected")
						&& addTxnAccessVal >= APPROVE_ACCESS_VAL) {
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i>Done</button></td>";
					tablecontent += "<td align='center'><button disabled='disabled'><i class='fa fa-check'></i> / <i class='fa fa-times'></i></button></td>";
				}
				tablecontent += "</tr>";
			}
		}
		if (tablecontent == "")
			$("#sc_table_header").html("");
		else
			$("#sc_table_header").html(scTableHeader);
		$("#sc_table_body").html(tablecontent);
		$("#sc_tx_count").html(statusOfFilteredTxns.length);
		$("#sc_filter").html(search_input.toLowerCase());
		if (search_input == "")
			$("#sc_clear_search").hide();
		else
			$("#sc_clear_search").show();

		for (k = 0; k < statusOfFilteredTxns.length; k++) {
			var index = indexOfFilteredTxns[k];
			var txnStatus = statusOfFilteredTxns[k];
			if ((txnStatus === "Todo" || txnStatus === "Undone"
				|| txnStatus === "Over Due" || txnStatus === "Previous Missed")
				&& addTxnAccessVal >= UPDATE_ACCESS_VAL) {
				$("#saving_done_" + index).click(launchSavingDoneDialog);
			} else if (txnStatus === "Done") {
				if(addTxnAccessVal >= UPDATE_ACCESS_VAL)
					$("#saving_undo_" + index).click(launchSavingUndoDialog);
				if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
					$("#saving_approve_" + index).click(launchSavingApproveDialog);
			}
		}
	}
}
function loadSavingCollectionTab() {
	var curDate = new Date(currentServerTime);
	var year = curDate.getFullYear();
	var month = curDate.getMonth();
	var startTime = new Date(year, month, 1);
	var endTime = new Date(year, month + 1, 0, 23, 59, 59, 999);
	var url = "/transaction/v1/group_todo_transactions/"
			+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
			+ startTime.getTime().toString() + "/"
			+ endTime.getTime().toString() + "/Saving Installment";
	ajaxCall(url, "GET", true, "",
			function(data, status) {
				if (data != null && data != "") {
					// storing in global objects
					savingTodoTxns = data.todoTransactions;
					// TODO Do we need following??
					// groupBankAcNos = data.groupBankAcNos;
					ruleAmounts = data.ruleAmounts;

					var isSelectionExist = false;
					var oldSelection = $("#sc_status").val();
					var content = "<option>All</option>";
					for (var k = 0; k < data.txStatus.length; k++) {
						content += " <option>" + data.txStatus[k] + "</option>";
						if(oldSelection == data.txStatus[k])
							isSelectionExist = true;
					}
					$("#sc_status").html(content);
					if(isSelectionExist) $("#sc_status").val(oldSelection);

					var headers = data.displayNames;
					$("#sc_top_table_data").html(
							"<tr><th>" + headers.toPayMembersNo + "</th><th>"
									+ headers.paidMembersNo + "</th><th>"
									+ headers.pendingAmount + "</th><th>"
									+ headers.paidAmount + "</th></tr>"
									+ "<tr><td>" + data.toPayMembersNo
									+ "</td><td>" + data.paidMembersNo
									+ "</td><td>" + data.pendingAmount
									+ "</td><td>" + data.paidAmount
									+ "</td><tr>");

					scTableHeader = "<tr><th><strong>"
							+ headers.memberAcNo
							+ "</strong></th><th><strong>"
							+ headers.memberName
							+ "</strong></th><th><strong>"
							+ headers.pendingAmount
							+ "</strong></th><th><strong>"
							+ headers.dueDateTs
							+ "</strong></th><th><strong>"
							+ headers.paidAmount
							+ "</strong></th><th><strong>"
							+ headers.txTodoStatus
							+ "</strong></th>";
					if(addTxnAccessVal >= UPDATE_ACCESS_VAL)
						scTableHeader += "<th align='center'><strong>Done / Undo</strong></th>";
					if(addTxnAccessVal >= APPROVE_ACCESS_VAL)
						scTableHeader += "<th align='center'><strong>Approve / Reject</strong></th>";
					scTableHeader += "</tr>";

					filterSavingColTable();
				} else {
					$("#sc_table_body").html("<tr><td>You do not have any data to display.</td></tr>");
				}
			}, function(xhr) {
				handleErrorAndDisplayMsg(xhr, "sc_table_body");
			});
}
function launchSavingDoneDialog() {
	var index = parseInt($(this).data("id"));
	$("#done_member_index").val(index);
	$("#done_tx_type").val("Saving Installment");
	$("#done_dialog_name").html("Saving Installment");
	launchDoneDialog(savingTodoTxns[index]);
}
function launchSavingUndoDialog() {
	var index = parseInt($(this).data("id"));
	$("#undo_member_index").val(index);
	$("#undo_tx_type").val("Saving Installment");
	$("#undo_dialog_name").html("Saving Installment");
	launchUndoDialog(savingTodoTxns[index]);
}
function launchSavingApproveDialog() {
	var index = parseInt($(this).data("id"));
	$("#approval_req_from").val("Saving Collection");
	$("#approve_dialog_name").html("Saving Installment");
	launchApproveRejectDialog(savingTodoTxns[index]);
}