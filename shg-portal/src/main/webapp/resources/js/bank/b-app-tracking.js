/**
 * 
 */
var groupAllTrackedAcc = null;

$(document).ready(function() {
	loadBankAppTrackingTab();
	$("#bank_app_track_tab").click(loadBankAppTrackingTab);
});
function loadBankAppTrackingTab() {
	ajaxCall(
			"/group/v1/group_all_acs/" + $("#lang option:selected").text()
					+ "/" + groupAcNo + "/All",
			"GET",
			true,
			"",
			function(data, status) {
				groupAllTrackedAcc = data;
				var header = data.displayNames;
				var loanAcs = data.groupLoanAcs;
				if (loanAcs != null && loanAcs.length != 0) {
					$("#b_loan_app_table_header").html("<tr><th><strong>" + header.groupLoanAcNo + "</strong></th><th><strong>"
						+ header.loanAcName + "</strong></th><th><strong>" + header.loanType + "</strong></th><th><strong>"
						+ header.principle + "</strong></th><th><strong>" + header.rateOfInterest + "</strong></th><th><strong>"
						+ header.requestedDate + "</strong></th><th><strong>" + header.accountStatus + "</strong></th><th><strong>Action</strong></th></tr>");

					var tablecontent = "";
					for (var k = 0; k < loanAcs.length; k++) {
						tablecontent += "<tr><td>" + loanAcs[k].groupLoanAcNo + "</td>";
						tablecontent += "<td>" + loanAcs[k].loanAcName + "</td>";
						tablecontent += "<td>" + loanAcs[k].loanType + "</td>";
						tablecontent += "<td>Rs." + loanAcs[k].principle + "</td>";
						tablecontent += "<td>" + loanAcs[k].rateOfInterest + "</td>";
						tablecontent += "<td>" + loanAcs[k].requestedDate + "</td>";
						var status = loanAcs[k].accountStatus;
						tablecontent += "<td>" + status + "</td>";
						if (status == "Draft" || status == "Sent Back")
							tablecontent += "<td><a class='btn btn-sm yellow' href='#bank_loan_content' data-toggle='tab' data-id='"
									+ k + "' id='b_loan_action_" + k + "'>Apply";
						else if (status == "Approved")
							tablecontent += "<td><a class='btn btn-sm green' href='#bank_app_activation_content' data-toggle='tab' data-id='"
									+ k + "' id='b_loan_action_" + k + "'>Activate";
						else if (status == "Active" || status == "Sub Standard" || status == "Bad Debt")
							tablecontent += "<td><a class='btn btn-sm red' href='#bank_app_activation_content' data-toggle='tab' data-id='"
									+ k + "' id='b_loan_action_" + k + "'>Close";
						else if (status == "Submitted" || status == "Under Review" || status == "On Hold")
							tablecontent += "<td><a class='btn btn-sm blue' href='#bank_loan_content' data-toggle='tab' data-id='"
									+ k + "' id='b_loan_action_" + k + "'>Approve";
						else
							tablecontent += "<td><a class='btn btn-sm default' href='#bank_loan_content' data-toggle='tab' data-id='"
								+ k + "' id='b_loan_action_" + k + "'>Details";
						tablecontent += "</a></td></tr>";
					}
					$("#b_loan_app_table_body").html(tablecontent);
					for (var k = 0; k < loanAcs.length; k++) {
						var status = loanAcs[k].accountStatus;
						if (status == "Approved")
							$("#b_loan_action_" + k).click(loadLoanAccActivationTab);
						else if (status == "Active" || status == "Sub Standard" || status == "Bad Debt")
							$("#b_loan_action_" + k).click(loadLoanAccClosureTab);
						else
							$("#b_loan_action_" + k).click(showBankLoanDetailsForTracking);
					}
				} else {
					$("#b_loan_app_table_header").html("");
					$("#b_loan_app_table_body").html("<tr><td>You do not have any data to display</td></tr>");
				}
				
				var invtAcs = data.groupInvtAcs;
				if (invtAcs != null && invtAcs.length != 0) {
					$("#b_invest_app_table_header").html("<tr><th><strong>" + header.groupInvtAcNo + "</strong></th><th><strong>"
						+ header.investmentAcName + "</strong></th><th><strong>" + header.investmentType + "</strong></th><th><strong>"
						+ header.invtAm + "</strong></th><th><strong>" + header.interestRate + "</strong></th><th><strong>"
						+ header.requestedDate + "</strong></th><th><strong>" + header.accountStatus + "</strong></th><th><strong>Action</strong></th></tr>");

					var tablecontent = "";
					for (var k = 0; k < invtAcs.length; k++) {
						tablecontent += "<tr><td>" + invtAcs[k].groupInvtAcNo + "</td>";
						tablecontent += "<td>" + invtAcs[k].investmentAcName + "</td>";
						tablecontent += "<td>" + invtAcs[k].investmentType + "</td>";
						tablecontent += "<td>Rs." + invtAcs[k].invtAm + "</td>";
						tablecontent += "<td>" + invtAcs[k].interestRate + "</td>";
						tablecontent += "<td>" + invtAcs[k].requestedDate + "</td>";
						var status = invtAcs[k].accountStatus;
						tablecontent += "<td>" + status + "</td>";
						if (status == "Draft" || status == "Sent Back")
							tablecontent += "<td><a class='btn btn-sm yellow' href='#investment_content' data-toggle='tab' data-id='"
									+ k + "' id='b_invest_action_" + k + "'>Apply";
						else if (status == "Approved")
							tablecontent += "<td><a class='btn btn-sm green' href='#bank_app_activation_content' data-toggle='tab' data-id='"
									+ k + "' id='b_invest_action_" + k + "'>Activate";
						else if (status == "Active" || status == "Sub Standard" || status == "Bad Debt")
							tablecontent += "<td><a class='btn btn-sm red' href='#bank_app_activation_content' data-toggle='tab' data-id='"
									+ k + "' id='b_invest_action_" + k + "'>Close";
						else if (status == "Submitted" || status == "Under Review" || status == "On Hold")
							tablecontent += "<td><a class='btn btn-sm blue' href='#investment_content' data-toggle='tab' data-id='"
									+ k + "' id='b_invest_action_" + k + "'>Approve";
						else
							tablecontent += "<td><a class='btn btn-sm default' href='#investment_content' data-toggle='tab' data-id='"
								+ k + "' id='b_invest_action_" + k + "'>Details";
						tablecontent += "</a></td></tr>";
					}
					$("#b_invest_app_table_body").html(tablecontent);
					for (var k = 0; k < invtAcs.length; k++) {
						var status = invtAcs[k].accountStatus;
						if (status == "Approved")
							$("#b_invest_action_" + k).click(loadInvtAccActivationTab);
						else if (status == "Active" || status == "Sub Standard" || status == "Bad Debt")
							$("#b_invest_action_" + k).click(loadInvtAccClosureTab);
						else
							$("#b_invest_action_" + k).click(showBankInvtDetailsForTracking);
					}
				} else {
					$("#b_invest_app_table_header").html("");
					$("#b_invest_app_table_body").html("<tr><td>You do not have any data to display</td></tr>");
				}
			}, function(xhr) {
				$("#b_loan_app_table_header").html("");
				$("#b_invest_app_table_header").html("");
				handleErrorAndDisplayMsg(xhr, "b_loan_app_table_body");
				handleErrorAndDisplayMsg(xhr, "b_invest_app_table_body");
			});
	loadPaymentModes();
	getGroupBankInfo();
}