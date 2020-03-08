/**
 * 
 */
$(document).ready(function() {
	$("#show_add_acc_content").click(showAddNewBankAccContent);
	$("#show_sel_acc_content").click(showSelectBankAccContent);
	$("#sel_acc_btn").click(selectBankAccount);
	$("#add_acc_btn").click(addNewBankAccount);
});
function launchSelAddGroupAccDlg() {
	$("#add_acc_num").val("");
	$("#add_acc_name").val("");
	var bankGroupAccNo = $("#add_acc_bank_id").val();
	if (bankGroupAccNo == "") {
		$("#sel_acc_btn").hide();
		$("#sel_acc_content").hide();
		$("#show_sel_acc_content").hide();
		$("#sel_acc_btn").attr("disabled", "disabled");
		$("#add_acc_content").hide();
		$("#add_acc_btn").hide();
		$("#show_add_acc_content").hide();
		$("#add_acc_btn").attr("disabled", "disabled");
		$("#sel_acc_dialog_table_body").html("<tr><td align='center' style='color: red'>Please search Bank first.</td></tr>");
		$("#sel_add_ok_btn").html("OK");
	} else {
		var accType = $("#sel_add_acc_type").html(), content = "";
		var bankAccounts = groupAcProfile.bankAccountDisplay;
		for (var i = 0; i < bankAccounts.length; i++) {
			if (accType == bankAccounts[i].bankAccountType && bankAccounts[i].bankGroupAcNo == bankGroupAccNo) {
				content += "<option value='" + bankAccounts[i].bankAcNo + "'>"
						+ bankAccounts[i].displayAccount + "</option>";
			}
		}
		$("#sel_acc_account").html(content);
		if(content != "")
			showSelectBankAccContent();
		else
			showAddNewBankAccContent();
	}
}
function showSelectBankAccContent() {
	$("#add_acc_content").hide();
	$("#sel_acc_content").show();
	$("#add_acc_btn").hide();
	$("#show_sel_acc_content").hide();
	$("#sel_acc_btn").show();
	$("#show_add_acc_content").show();
	$("#sel_acc_btn").removeAttr("disabled");
	$("#sel_acc_dlg_msg_header").html("");
	$("#sel_acc_dialog_table_body").html("");
	$("#sel_add_ok_btn").html("Cancel");
}
function showAddNewBankAccContent() {
	$("#sel_acc_content").hide();
	$("#add_acc_content").show();
	$("#sel_acc_btn").hide();
	$("#show_add_acc_content").hide();
	$("#add_acc_btn").show();
	$("#show_sel_acc_content").show();
	$("#add_acc_btn").removeAttr("disabled");
	$("#sel_acc_dlg_msg_header").html("");
	$("#sel_acc_dialog_table_body").html("");
	$("#sel_add_ok_btn").html("Cancel");
	if ($("#add_acc_bank_id").val() == "") {
		$("#add_acc_btn").attr("disabled", "disabled");
		$("#sel_acc_dialog_table_body").html(
			"<tr><td align='center' style='color: red'>You cannot add new account.<br>Please search Bank first.</td></tr>");
	}
}
function selectBankAccount() {
	var accDisplayName = $("#sel_acc_account option:selected").text();
	if (accDisplayName == "") {
		$("#sel_acc_dialog_table_body")
				.html(
						"<tr><td align='center' style='color: red'>Please select an Account</td></tr>");
	} else {
		var bankAccId = $("#sel_acc_account").val();
		var accType = $("#sel_add_acc_type").html();
		for (var i = 0; i < groupAcProfile.bankAccounts.length; i++) {
			var account = groupAcProfile.bankAccounts[i];
			if (bankAccId == account.bankAccountId
					&& accType == account.bankAccountType) {
				if (accType == "Loan Account") {
					$("#b_loan_b_acc_id").val(bankAccId);
					$("#b_loan_b_acc_num").html(account.accountNumber);
					$("#b_loan_b_acc_name").html(account.accountName);
				} else if (accType == "Investment Account") {
					$("#b_invest_b_acc_id").val(bankAccId);
					$("#b_invest_b_acc_num").html(account.accountNumber);
					$("#b_invest_b_acc_name").html(account.accountName);
				}
				break;
			}
		}
		$("#sel_add_acc_dlg").modal("hide");
	}
}
function addNewBankAccount() {
	var errMsg = "", accnum = $("#add_acc_num").val(), accname = $(
			"#add_acc_name").val(), acctype = $("#sel_add_acc_type").html(), bankProfileId = $(
			"#add_acc_bank_id").val();
	if (accnum == "")
		errMsg = "Please enter Account Number";
	else if (accname == "")
		errMsg = "Please enter Account Name";
	else if (acctype == "")
		errMsg = "Please select Account Type";
	else if (bankProfileId == "")
		errMsg = "Please search Bank first";

	if (errMsg != "") {
		$("#sel_acc_dialog_table_body").html(
				"<tr><td align='center' style='color: red'>" + errMsg
						+ "</td></tr>");
	} else {
		var payload = "{\"groupAcNo\":" + groupAcNo
				+ ",\"bankAccounts\":[{\"accountNumber\":\"" + accnum
				+ "\",\"accountName\":\"" + accname
				+ "\",\"bankAccountType\":\"" + acctype
				+ "\",\"bankProfileId\":" + bankProfileId + "}]}";
		ajaxCall(
				"/group/v1/add_group_bank_accounts",
				"PUT",
				true,
				payload,
				function(data, status) {
					showSelectBankAccContent();
					$("#sel_acc_dialog_table_body")
							.html(
									"<tr><td align='center'>Account added successfully.<br>Refreshing the list. Please wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
					ajaxCall("/group/v1/group_ac/"
							+ $("#lang option:selected").text() + "/"
							+ groupAcNo, "GET", true, "",
							function(data, status) {
								groupAcProfile = data;
								launchSelAddGroupAccDlg();
							});
				}, function(xhr) {
					handleErrorAndDisplayMsg(xhr, "sel_acc_dialog_table_body");
				});
	}
}
