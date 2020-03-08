/**
 * 
 */
var trackedMemberForClosure = null;
$(document).ready(function() {
	$("#closure_tab").click(resetClosureDataTables);
	$("#m_search_closure").click(searchMemberForClosure);
});
function searchMemberForClosure() {
	var memberName = $("#member_name_closure").val();
	trackedMemberForClosure = null;
	resetClosureDataTables();
	$("#member_name_closure").val(memberName);
	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching();
		ajaxCall("/member/v1/all_member_search_by_name/"
				+ $("#lang option:selected").text() + "/" + groupAcNo + "/" + memberName,
				"GET", true, "",
				function(data, status) {
					searchedMembers = data.memberNames;
					loadMemberSearchData(searchedMembers, true);

					for (var i = 0; i < data.memberNames.length; i++) {
						$("#member_" + i).click(loadAndGetMemberClosureData);
					}
				}, memberSearchFailed);
	}
}
function loadAndGetMemberClosureData() {
	hideMemberSearchDialog();
	$("#m_closure_tbody").html("<tr><td>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	$("#loan_closure_tbody").html("<tr><td>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	var index = parseInt($(this).data("id"));
	if (!isNaN(index))
		trackedMemberForClosure = searchedMembers[index];
	var memberName = trackedMemberForClosure.memberName;
	var memberAcNo = trackedMemberForClosure.memberAcNo;
	$("#member_name_closure").val(memberName);
	ajaxCall("/member/v1/member_accounts_data/"
			+ $("#lang option:selected").text() + "/" + memberAcNo + "/Active",
			"GET", true, "",
			function(data, status) {
				trackedMember = data;
				var headers = appTrackingData.displayNames;
				$("#m_closure_thead").html("<tr><th><strong>"
										+ headers.dateOfEnroll
										+ "</strong></th><th><strong>"
										+ headers.memberAcNo
										+ "</strong></th><th><strong>"
										+ headers.memberName
										+ "</strong></th><th><strong>"
										+ headers.mrole
										+ "</strong></th><th><strong>"
										+ headers.recommendedByMemberName
										+ "</strong></th><th><strong>"
										+ headers.activeStatus
										+ "</strong></th><th><strong>Action</strong></th></tr>");
				var tablecontent = "";
				tablecontent += "<tr><td>" + data.dateOfEnroll + "</td>";
				tablecontent += "<td>" + getReadableAccNum(data.memberAcNo) + "</td>";
				tablecontent += "<td>" + data.memberName + "</td>";
				tablecontent += "<td>" + data.mrole + "</td>";
				tablecontent += "<td>" + data.recommendedByMemberName + "</td>";
				tablecontent += "<td>" + data.activeStatus + "</td>";
				if(data.activeStatus == "Active")
					tablecontent += "<td><a class='btn btn-sm red' href='#app_activation_content' data-toggle='tab' id='m_closure_action'>Close Account</a>";
				else
					tablecontent += "<td><a class='btn btn-sm yellow' href='#m_reg_content' data-toggle='tab' id='m_closure_details'>Details</a>";
				tablecontent += "</td></tr>";
				$("#m_closure_tbody").html(tablecontent);
				$("#m_closure_action").click(loadMemberClosureTab);
				$("#m_closure_details").click(showMemberDetailsForClosure);

				if (data.memberLoanAc != null && data.memberLoanAc.length != 0) {
					$("#loan_closure_thead").html("<tr><th><strong>"
											+ headers.requestedDate
											+ "</strong></th><th><strong>"
											+ headers.memberLoanAcNo
											+ "</strong></th><th><strong>"
											+ headers.memberAcNo
											+ "</strong></th><th><strong>"
											+ headers.memberName
											+ "</strong></th><th><strong>"
											+ headers.loanType
											+ "</strong></th><th><strong>"
											+ headers.principle
											+ "</strong></th><th><strong>"
											+ headers.installment
											+ "</strong></th><th><strong>"
											+ headers.accountStatus
											+ "</strong></th><th><strong>Action</strong></th></tr>");
					var mLoanAcs = data.memberLoanAc;
					var tablecontent = "";
					for (var k = 0; k < mLoanAcs.length; k++) {
						tablecontent += "<tr>";
						tablecontent += "<td>" + mLoanAcs[k].requestedDate + "</td>";
						tablecontent += "<td>" + mLoanAcs[k].memberLoanAcNo + "</td>";
						tablecontent += "<td>" + getReadableAccNum(mLoanAcs[k].memberAcNo) + "</td>";
						tablecontent += "<td>" + data.memberName + "</td>";
						tablecontent += "<td>" + mLoanAcs[k].loanType + "</td>";
						tablecontent += "<td>Rs." + mLoanAcs[k].principle + "</td>";
						tablecontent += "<td>Rs." + mLoanAcs[k].installment + "</td>";
						tablecontent += "<td>" + mLoanAcs[k].accountStatus + "</td>";
						if(mLoanAcs[k].accountStatus == "Active")
							tablecontent += "<td><a class='btn btn-sm red' href='#app_activation_content' data-toggle='tab' data-id='" + k + "' id='fund_closure_action_" + k + "'>Close Account</a>";
						else
							tablecontent += "<td><a class='btn btn-sm yellow' href='#m_fund_content' data-toggle='tab' data-id='" + k + "' id='fund_closure_details_" + k  + "'>Details</a>";
						tablecontent += "</td></tr>";
					}
					$("#loan_closure_tbody").html(tablecontent);
					for (var k = 0; k < mLoanAcs.length; k++) {
						if(mLoanAcs[k].accountStatus == "Active")
							$("#fund_closure_action_" + k).click(loadFundClosureTab);
						else
							$("#fund_closure_action_" + k).click(showFundDetailsForClosure);
					}
				} else {
					$("#loan_closure_thead").html("");
					$("#loan_closure_tbody").html("<tr><td>No loan account found.</td></tr>");
				}
			}, function(xhr) {
				showErrorMsgFundApp("");
				handleErrorAndDisplayMsg(xhr, "fund_err_msg");
			});
}
function resetClosureDataTables() {
	resetAppActivationForm();
	trackedMember = null;
	trackedMemberLoanAc = null;
	$("#member_name_closure").val("");
	$("#m_closure_thead").html("");
	$("#loan_closure_thead").html("");
	$("#m_closure_tbody").html("<tr><td>Search to load member registration data.</td></tr>");
	$("#loan_closure_tbody").html("<tr><td>Search to load member loan accounts.</td></tr>");
	if(trackedMemberForClosure != null) {
		loadAndGetMemberClosureData();
	}
}