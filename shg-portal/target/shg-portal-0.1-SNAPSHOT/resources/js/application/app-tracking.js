/**
 * 
 */
var appTrackingData = null;

$(document).ready(function() {
	loadAppTrackingTab();
	$("#app_track_tab").click(loadAppTrackingTab);
});
function loadAppTrackingTab() {
	ajaxCall(
			"/group/v1/group_applications/" + $("#lang option:selected").text()
					+ "/" + groupAcNo,
			"GET",
			true,
			"",
			function(data, status) {
				if (data != "") {
					appTrackingData = data;
					var headers = data.displayNames;
					if (data.members != null && data.members.length != 0) {
						$("#m_app_table_header").html("<tr><th><strong>" + headers.dateOfEnroll
									+ "</strong></th><th><strong>" + headers.memberAcNo
									+ "</strong></th><th><strong>" + headers.memberName
									+ "</strong></th><th><strong>" + headers.mrole
									+ "</strong></th><th><strong>" + headers.recommendedByMemberName
									+ "</strong></th><th><strong>" + headers.approvalStatus
									+ "</strong></th><th><strong>Action</strong></th></tr>");

						var mData = data.members;
						var tablecontent = "";
						for (var k = 0; k < mData.length; k++) {
							tablecontent += "<tr><td>" + mData[k].dateOfEnroll + "</td>";
							tablecontent += "<td>" + getReadableAccNum(mData[k].memberAcNo) + "</td>";
							tablecontent += "<td>" + mData[k].memberName + "</td>";
							tablecontent += "<td>" + mData[k].mrole + "</td>";
							tablecontent += "<td>" + mData[k].recommendedByMemberName + "</td>";
							tablecontent += "<td>" + mData[k].approvalStatus + "</td>";
							if (mData[k].approvalStatus == "Active" || mData[k].approvalStatus == "Rejected") {
								tablecontent += "<td><a class='btn btn-sm yellow' href='#m_reg_content' data-toggle='tab' data-id='"
										+ k + "' id='m_reg_action_" + k + "'>Details";
							}
							else if (mData[k].approvalStatus == "Approved") {
								tablecontent += "<td><a class='btn btn-sm green' href='#app_activation_content' data-toggle='tab' data-id='"
										+ k + "' id='m_reg_action_" + k + "'>Activate";
							}
							else {
								tablecontent += "<td><a class='btn btn-sm blue' href='#m_reg_content' data-toggle='tab' data-id='"
										+ k + "' id='m_reg_action_" + k + "'>Approve";
							}
							tablecontent += "</a></td></tr>";
						}
						$("#m_app_table_body").html(tablecontent);
						for (var k = 0; k < data.members.length; k++) {
							if (mData[k].approvalStatus == "Approved")
								$("#m_reg_action_" + k).click(loadMemberActivationTab);
							else
								$("#m_reg_action_" + k).click(showMemberDetailsForActivation);
						}
					} else {
						$("#m_app_table_header").html("");
						$("#m_app_table_body").html("<tr><td>No applications found.</td></tr>");
					}
					if (data.memberLoanAcs != null
							&& data.memberLoanAcs.length != 0) {
						$("#m_loan_app_div").show();
						$("#loan_app_table_header").html("<tr><th><strong>" + headers.requestedDate
										+ "</strong></th><th><strong>" + headers.memberLoanAcNo
										+ "</strong></th><th><strong>" + headers.memberAcNo
										+ "</strong></th><th><strong>" + headers.memberName
										+ "</strong></th><th><strong>" + headers.loanType
										+ "</strong></th><th><strong>" + headers.principle
										+ "</strong></th><th><strong>" + headers.installment
										+ "</strong></th><th><strong>" + headers.accountStatus
										+ "</strong></th><th><strong>Action</strong></th></tr>");

						var mLoanAcs = data.memberLoanAcs;
						var tablecontent = "";
						for (var k = 0; k < mLoanAcs.length; k++) {
							tablecontent += "<tr>";
							tablecontent += "<td>" + mLoanAcs[k].requestedDate + "</td>";
							tablecontent += "<td>" + mLoanAcs[k].memberLoanAcNo + "</td>";
							tablecontent += "<td>" + getReadableAccNum(mLoanAcs[k].memberAcNo) + "</td>";
							tablecontent += "<td>" + mLoanAcs[k].memberName + "</td>";
							tablecontent += "<td>" + mLoanAcs[k].loanType + "</td>";
							tablecontent += "<td>Rs." + mLoanAcs[k].principle + "</td>";
							tablecontent += "<td>Rs." + mLoanAcs[k].installment + "</td>";
							tablecontent += "<td>" + mLoanAcs[k].accountStatus + "</td>";
							if (mLoanAcs[k].accountStatus == "Active" || mLoanAcs[k].accountStatus == "Rejected") {
								tablecontent += "<td><a class='btn btn-sm yellow' href='#m_fund_content' data-toggle='tab' data-id='"
										+ k + "' id='fund_app_action_" + k + "'>Details";
							}
							else if (mLoanAcs[k].accountStatus == "Approved") {
								tablecontent += "<td><a class='btn btn-sm green' href='#app_activation_content' data-toggle='tab' data-id='"
										+ k + "' id='fund_app_action_" + k + "'>Activate";
							}
							else {
								tablecontent += "<td><a class='btn btn-sm blue' href='#m_fund_content' data-toggle='tab' data-id='"
										+ k + "' id='fund_app_action_" + k + "'>Approve";
							}
							tablecontent += "</a></td></tr>";
						}
						$("#loan_app_table_body").html(tablecontent);
						for (var k = 0; k < mLoanAcs.length; k++) {
							if (mLoanAcs[k].accountStatus == "Approved")
								$("#fund_app_action_" + k).click(loadFundDisburseTab);
							else
								$("#fund_app_action_" + k).click(showFundDetailsForActivation);
						}
					} else {
						$("#loan_app_table_header").html("");
						$("#loan_app_table_body").html("<tr><td>No applications found.</td></tr>");
					}
				} else {
					$("#m_app_table_header").html("");
					$("#loan_app_table_header").html("");
					$("#m_app_table_body").html("<tr><td>No applications found.</td></tr>");
					$("#loan_app_table_body").html("<tr><td>No applications found.</td></tr>");
				}
			}, function(xhr) {
				$("#m_app_table_header").html("");
				$("#loan_app_table_header").html("");
				handleErrorAndDisplayMsg(xhr, "m_app_table_body");
				handleErrorAndDisplayMsg(xhr, "loan_app_table_body");
			});
	loadPaymentModes();
	getGroupBankInfo();
}