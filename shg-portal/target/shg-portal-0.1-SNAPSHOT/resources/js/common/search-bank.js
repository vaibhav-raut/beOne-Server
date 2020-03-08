/**
 * 
 */
var searchedBankProfiles = null, bankSearchedFor = "";
$(document).ready(function() {
	$("#bsbyname_btn").click(bankSearchByNameHandler);
	$("#bsbyifsc_btn").click(bankSearchByIFSCHandler);
	$("#bs_state").click(bankSearchStateChangeHandler);
});
function loadBankSearchDialog() {
	var curState = "", curDistrict = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (districtValues[i].districtCode == gdCode) {
			curDistrict = districtValues[i].district;
			curState = districtValues[i].state;
			break;
		}
	}
	var stateContent = "";
	for (var i = 0; i < states.length; i++) {
		stateContent += "<option>" + states[i] + "</option>";
	}
	$("#bs_state").html(stateContent);
	if (curState != "")
		$("#bs_state").val(curState);
	
	bankSearchStateChangeHandler();
	
	if (curDistrict != "")
		$("#bs_district").val(curDistrict);
}
function bankSearchStateChangeHandler() {
	var curState = $("#bs_state").val();
	var districtContent = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (curState == districtValues[i].state) {
			districtContent += "<option>" + districtValues[i].district + "</option>";
		}
	}
	$("#bs_district").html(districtContent);
}
function bankSearchByNameHandler() {
	var bname = $("#bs_name").val();
	if (bname == "") {
		$("#sb_dialog_table_body").html("<tr><td align='center' style='color: red'>Please provide some input in bank name</td></tr>");
		return;
	}
	var selectedDistrict = $("#bs_district option:selected").text();
	var districtCode = "";
	for (var i = 0; i < districtValues.length; i++) {
		if (selectedDistrict == districtValues[i].district) {
			districtCode = districtValues[i].districtCode;
			break;
		}
	}
	$("#sb_dialog_table_body").html("<tr><td align='center'>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	var payload = "{\"districtCode\":\"" + districtCode + "\",\"bankName\":\""
			+ bname + "\",\"branchName\":\"" + $("#bs_branch").val() + "\"}";
	ajaxCall("/group/v1/search_bank_by_name_in_district",
			"POST", true, payload, loadBankSearchData, bankSearchFailed);
}
function bankSearchByIFSCHandler() {
	var ifsc = $("#bs_ifsc").val();
	if (ifsc == "") {
		$("#sb_dialog_table_body").html("<tr><td align='center' style='color: red'>Please provide IFSC code</td></tr>");
		return;
	}
	$("#sb_dialog_table_body").html("<tr><td align='center'>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	ajaxCall("/group/v1/search_bank_by_ifcscode/" + ifsc,
			"GET", true, "", loadBankSearchData, bankSearchFailed);
}
function loadBankSearchData(data, status) {
	searchedBankProfiles = data.bankProfiles;
	if (searchedBankProfiles != null && searchedBankProfiles.length != 0) {
		var tableContent = "<tr><td colspan='3' align='center'><strong>Select Bank</strong></td><td>";
		tableContent += "<tr><td><strong>IFSC<strong></td><td><strong>Bank</strong></td><td><strong>Branch</strong></td></tr>";
		for (var i = 0; i < searchedBankProfiles.length; i++) {
			tableContent += "<tr><td><a href='#' data-id='" + i
					+ "' id='bank_"
					+ searchedBankProfiles[i].bankProfileId + "'>"
					+ searchedBankProfiles[i].ifcsCode + "</a></td><td>"
					+ searchedBankProfiles[i].bankName + "</td><td>"
					+ searchedBankProfiles[i].branchName
					+ "</td></tr>";
		}
		$("#sb_dialog_table_body").html(tableContent);
		for (var i = 0; i < searchedBankProfiles.length; i++) {
			$("#bank_" + searchedBankProfiles[i].bankProfileId)
					.click(loadSelectedBank);
		}
	} else {
		$("#sb_dialog_table_body").html("<tr><td align='center'>No bank found.</td></tr>");
	}
}
function bankSearchFailed(xhr) {
	handleErrorAndDisplayMsg(xhr, "sb_dialog_table_body");
}
function loadSelectedBank() {
	var index = parseInt($(this).data("id"));
	var bankProfileId = searchedBankProfiles[index].bankProfileId;
	var bank = searchedBankProfiles[index].bankName;
	var branch = searchedBankProfiles[index].branchName;
	var ifsc = searchedBankProfiles[index].ifcsCode;

	if (bankSearchedFor == "group-reg") {
		$("#greg_bank_id").val(bankProfileId);
		$("#g_bank_name").html(bank);
		$("#g_branch_name").html(branch);
		$("#g_ifsc_code").html(ifsc);
	} else if (bankSearchedFor == "member-reg") {
		$("#mreg_bank_id").val(bankProfileId);
		$("#mreg_bank_name").html(bank);
		$("#mreg_branch_name").html(branch);
		$("#mreg_ifsc_code").html(ifsc);
	} else if (bankSearchedFor == "member-profile") {
		$("#medit_bank_id").val(bankProfileId);
		$("#medit_bank_name").html(bank);
		$("#medit_branch_name").html(branch);
		$("#medit_ifsc_code").html(ifsc);
	} else if (bankSearchedFor == "group-profile") {
		$("#gedit_bank_id").val(bankProfileId);
		$("#gedit_bank_name").html(bank);
		$("#gedit_branch_name").html(branch);
		$("#gedit_ifsc_code").html(ifsc);
	} else if (bankSearchedFor == "bank-loan") {
		$("#b_loan_bank_id").val(bankProfileId);
		$("#b_loan_bank_name").html(bank);
		$("#b_loan_branch_name").html(branch);
		$("#b_loan_ifsc_code").html(ifsc);
		$("#b_loan_roi").val(searchedBankProfiles[index].bankLoanIntRate);
		$("#b_loan_proc_fee").val(searchedBankProfiles[index].loanProcessingFee);
		enableBankLoanPlanningForm();
	} else if (bankSearchedFor == "bank-investment") {
		$("#b_invest_bank_id").val(bankProfileId);
		$("#b_invest_bank_name").html(bank);
		$("#b_invest_branch_name").html(branch);
		$("#b_invest_ifsc_code").html(ifsc);
		$("#b_invest_roi2").val(searchedBankProfiles[index].bankFdIntRate);
		enableBankInvestmentForm();
	}
	$("#search_bank_dialog").modal("hide");
	resetBankSearchDialog();
}
function resetBankSearchDialog() {
	$("#bs_table").show();
	$("#bs_name").val("");
	$("#bs_ifsc").val("");
	$("#sb_dialog_table_body").html("");
}