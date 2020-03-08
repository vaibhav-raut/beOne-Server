/**
 * 
 */
var fundTypes, loanCalcTypes, recoveryPeriods, isFundApp = true, mfundAttachments = new Array();
var mProfileSearched = null, mProfilePlanning = null, fundPlanningData = null, fundTypePlanning = null, recPeriodPlanning = null;

$(document).ready(function() {
	loadFundTypes();
	$("#m_fund_app_tab").click(showMemberFundAppTab);
	$("#fundapp_m_search").click(searchMemberForFundApp);
	$("#fund_type").change(updateRateOfInterest);
	$("#loan_calc_type").change(loanCalcTypeChangeHandler);
	$("#principle").change(principleChangeHandler);
	$("#fixed_emi").change(numberFieldChangeHandler);
	$("#num_of_emi").change(numberFieldChangeHandler);
	$("#fund_plan_btn").click(doMemberFundPlanning);
	$("#fund_display_emi_btn").click(doMemberFundPlanning);
	$("#fund_reset_btn").click(resetFullFundApplication);
	$("#fund_apply_btn").click(applyFundCalculation);
	$("#fund_approve_btn").click(approveFundApp);
	$("#fund_reject_btn").click(rejectFundApp);
	$("#fund_hold_btn").click(holdFundApp);
	$("#back2apptrac_btn2").click(back2AppTrackFromFundApp);
	$("#fund_upload_file_btn").click(uploadMemberFundAttachment);
	$("#fund_print").click(printTable);
	$("#fund_pdf").click(printTable);
	$("#fund_excel").click(genExcel);
});
function loadFundTypes() {
	ajaxCall("/enum/v1/fund_type", "GET", true, "", function(data, status) {
		fundTypes = data.enumValues;
		loadLoanCalcTypes();
	});
}
function loadLoanCalcTypes() {
	ajaxCall("/enum/v1/enum_values/LoanCalculation", "GET", true, "", function(
			data, status) {
		loanCalcTypes = data.enumValues;
		loadRecoveryPeriods();
	});
}
function loadRecoveryPeriods() {
	ajaxCall("/enum/v1/enum_values/RecoveryPeriod", "GET", true, "", function(
			data, status) {
		recoveryPeriods = data.enumValues;
		loadMemberFundAppTab();
	});
}
function loadMemberFundAppTab() {
	$("#fund_s_date").val(convertTS2DDMMYYYYDate(currentServerTime));

	var fTypeContent = "";
	for (var i = 0; i < fundTypes.length; i++) {
		if (fundTypes[i].fundCategory == "Regular Recovery") {
			fTypeContent += "<option>" + fundTypes[i].fundType + "</option>";
		}
	}
	$("#fund_type").html(fTypeContent);

	var calcTypeContent = "";
	for (var i = 0; i < loanCalcTypes.length; i++)
		calcTypeContent += "<option>" + loanCalcTypes[i].enumValue
				+ "</option>";
	$("#loan_calc_type").html(calcTypeContent);

	var recPeriodContent = "";
	for (var i = 0; i < recoveryPeriods.length; i++)
		recPeriodContent += "<option>" + recoveryPeriods[i].enumValue
				+ "</option>";
	$("#recovery_period").html(recPeriodContent);
	$("#recovery_period").val("Monthly");

	$("#fund_plan_btn").attr("disabled", "disabled");
	$("#fund_apply_btn").attr("disabled", "disabled");
}
function showMemberFundAppTab() {
	if (isFundApp == false) {
		resetFullFundApplication();
		resetAppActivationForm();
	}
}
function searchMemberForFundApp() {
	var memberName = $("#member_name").val();
	resetFundAppForm();
	if (memberName == "")
		memberSearchIncompleteDataError();
	else {
		memberSearching();
		ajaxCall(
				"/member/v1/member_search_by_name/"
						+ $("#lang option:selected").text() + "/" + groupAcNo
						+ "/" + memberName,
				"GET",
				true,
				"",
				function(data, status) {
					searchedMembers = data.memberNames;
					loadMemberSearchData(searchedMembers);

					for (var i = 0; i < data.memberNames.length; i++) {
						$("#member_" + i).click(
								loadAndGetSelectedMemberProfile);
					}
				}, memberSearchFailed);
	}
}
function loadAndGetSelectedMemberProfile() {
	hideMemberSearchDialog();
	var index = parseInt($(this).data("id"));
	var memberName = searchedMembers[index].memberName;
	var memberAcNo = searchedMembers[index].memberAcNo;
	$("#member_name").val(memberName);
	ajaxCall(
			"/member/v1/member_account/" + $("#lang option:selected").text()
					+ "/" + memberAcNo,
			"GET",
			true,
			"",
			function(data, status) {
				if (data != "") {
					var mrole = data.mrole;
					if (isMemberRoleEligibleForLoan(mrole)) {
						mProfileSearched = data;
						$("#m_role").val(mrole);
						$("#m_saving").val(mProfileSearched.account.savedAm);
						$("#m_rating").val(
								mProfileSearched.account.creditRating);
						$("#m_pending_loan").val(
								mProfileSearched.account.loanAm
										- mProfileSearched.account.recLoanAm);
						clearFundCalculation();
						enableFundAppFormFields();
						if (getMemberFundLimit() > 0)
							updateRateOfInterest();
					} else {
						showErrorMsgFundApp("Please search again.<br>Fund Calculation is applicable only for SHG Members. "
								+ $("#member_name").val()
								+ " is "
								+ mrole
								+ ".");
						$("#member_name").val("");
					}
				}
			}, function(xhr) {
				showErrorMsgFundApp("");
				handleErrorAndDisplayMsg(xhr, "fund_err_msg");
			});
}
function isMemberRoleEligibleForLoan(mrole) {
	if (mrole == "Core Member" || mrole == "Associate Member")
		return true;
	for (var i = 0; i < mRoles.length; i++) {
		if (mRoles[i].role == mrole)
			if (mRoles[i].roleCategory == "SHG Member")
				return true;
	}
	return false;
}
function getMemberFundLimit() {
	var mTotalSaving = mProfileSearched.account.savedAm;
	var pendingLoan = mProfileSearched.account.loanAm
			- mProfileSearched.account.recLoanAm;
	var curFundType = $("#fund_type option:selected").text();
	var limit = 0;
	if (curFundType == "Consumption Loan") {
		var savingX = groupRules.cmSavingXConsumptionLoan;
		var limitOnConsumption = groupRules.cmLimitOnConsumptionLoan;
		if(mProfileSearched.role == "Associate Member") {
			savingX = groupRules.amSavingXConsumptionLoan;
			limitOnConsumption = groupRules.amLimitOnConsumptionLoan;
		}
		limit = mTotalSaving * savingX - pendingLoan;
		if (limit > limitOnConsumption)
			limit = limitOnConsumption;
	} else if (curFundType == "Individual Development Fund") {
		var savingXDevLoan = groupRules.cmSavingXDevLoan;
		if(mProfileSearched.role == "Associate Member")
			savingXDevLoan = groupRules.amSavingXDevLoan;
		limit = mTotalSaving * savingXDevLoan - pendingLoan;
	} else if (curFundType == "Project Development Fund") {
		// TODO
		var savingXDevLoan = groupRules.cmSavingXDevLoan;
		if(mProfileSearched.role == "Associate Member")
			savingXDevLoan = groupRules.amSavingXDevLoan;
		limit = mTotalSaving * savingXDevLoan - pendingLoan;
	}
	if (limit <= 0) {
		limit = 0;
		showErrorMsgFundApp("You are not eligible for loan.<br>You may already availed the loan as per your eligibility or you do not have any savings.");
		$("#fund_plan_btn").focus();
	}
	return limit;
}
function loanCalcTypeChangeHandler() {
	var curCalcType = $("#loan_calc_type option:selected").text();
	if (curCalcType == "Normal EMI") {
		$("#fixed_emi").val("");
		$("#fixed_emi").attr("disabled", "disabled");
		$("#num_of_emi").removeAttr("disabled");
	} else if (curCalcType == "Fixed EMI") {
		$("#num_of_emi").val("");
		$("#num_of_emi").attr("disabled", "disabled");
		$("#fixed_emi").removeAttr("disabled");
	} else if (curCalcType == "Reducing Interest") {
		$("#num_of_emi").removeAttr("disabled");
		$("#fixed_emi").removeAttr("disabled");
	}
}
function principleChangeHandler() {
	var curPrinciple = $("#principle").val();
	if (curPrinciple != "") {
		if (isNaN(curPrinciple)) {
			showMessage("Please enter only numbers in Principle field", "principle");
			$("#principle").val("");
			$("#principle").focus();
		} else {
			var principleVal = parseInt(curPrinciple);
			var limit = getMemberFundLimit();
			if (principleVal > limit) {
				showMessage("Your maximum loan limit on selected fund type is Rs."
						+ limit + " only.", "principle");
				$("#principle").val(limit);
				$("#principle").focus();
			}
			updateRateOfInterest();
		}
	}
}
function updateRateOfInterest() {
	var roi = 0;
	var curMrole = $("#m_role").val();
	var curFundType = $("#fund_type option:selected").text();
	var curPrinciple = 0;
	if ($("#principle").val() != "")
		curPrinciple = parseInt($("#principle").val());
	var am_cm_diff = groupRules.amBaseIntOnLoan - groupRules.cmBaseIntOnLoan;
	if (am_cm_diff < 0)
		am_cm_diff = am_cm_diff * (-1);

	if (curFundType == "Consumption Loan") {
		roi = groupRules.intOnConsumptionLoan;
	} else if (curFundType == "Individual Development Fund") {
		if (curPrinciple <= groupRules.individualDevLoanRange1)
			roi = groupRules.intOnIndividualDevLoanRange1;
		else if (curPrinciple <= groupRules.individualDevLoanRange2)
			roi = groupRules.intOnIndividualDevLoanRange2;
		else if (curPrinciple <= groupRules.individualDevLoanRange3)
			roi = groupRules.intOnIndividualDevLoanRange3;
		else
			roi = groupRules.intOnIndividualDevLoanRange3;
	} else if (curFundType == "Project Development Fund") {
		if (curPrinciple <= groupRules.projectDevLoanRange1)
			roi = groupRules.intOnProjectDevLoanRange1;
		else if (curPrinciple <= groupRules.projectDevLoanRange2)
			roi = groupRules.intOnProjectDevLoanRange2;
		else if (curPrinciple <= groupRules.projectDevLoanRange3)
			roi = groupRules.intOnProjectDevLoanRange3;
		else
			roi = groupRules.intOnProjectDevLoanRange3;
	}
	// for Core Member no update is required
	if (curMrole == "Associate Member")
		roi = roi + am_cm_diff;
	$("#roi").val(roi);
}
function doMemberFundPlanning() {
	if (isFundApp == true && mProfileSearched == null) {
		showErrorMsgFundApp("Please search member first.");
		return;
	}
	var mrole = $("#m_role").val();
	var fundType = $("#fund_type option:selected").text();
	var loanCalcType = $("#loan_calc_type option:selected").text();
	var principle = $("#principle").val();
	var roi = $("#roi").val();
	var sDate = $("#fund_s_date").val();
	var fixedEMI = $("#fixed_emi").val();
	var numOfEMI = $("#num_of_emi").val();

	if (mrole == "" || fundType == ""
			|| loanCalcType == "" || principle == "" || roi == ""
			|| sDate == "" || (loanCalcType == "Fixed EMI" && fixedEMI == "")
			|| (loanCalcType == "Normal EMI" && numOfEMI == "")) {
		showErrorMsgFundApp("Please fill all the fields before submitting form.");
	} else {
		$("#fund_plan_btn").attr("disabled", "disabled");
		$("#fund_apply_btn").attr("disabled", "disabled");
		$("#fund_reset_btn").attr("disabled", "disabled");

		if (fixedEMI == "")
			fixedEMI = 0;
		if (numOfEMI == "")
			numOfEMI = 0;
		var payload = "{\"loanCalculation\":\"" + loanCalcType
				+ "\",\"rateOfInterest\":" + roi + ",\"principle\":"
				+ principle + ",\"fixedEMI\":" + fixedEMI + ",\"noOfEMIs\":"
				+ numOfEMI + ",\"startDate\":\"" + sDate + "\",\"dueDay\":"
				+ groupRules.dueDayOfMonth + "}";
		ajaxCall(
				"/mloan/v1/member_loan_planning",
				"POST",
				false,
				payload,
				function(data, status) {
					if(trackedMemberLoanAc)
						showMsgFundApp("Loan Account No: <b>" + trackedMemberLoanAc.memberLoanAcNo
								+ "</b><br>Application Status: <b>" + trackedMemberLoanAc.accountStatus + "</b>");
					else
						showMsgFundApp("");
					fundPlanningData = data;
					fundTypePlanning = $("#fund_type option:selected").text();
					recPeriodPlanning = $("#recovery_period option:selected").text();
					if (isFundApp == true) {
						mProfilePlanning = mProfileSearched;
						loadFundPlanningSheet();
					}
					loadEMIBreakdownSheet();
					$("#fund_plan_btn").removeAttr("disabled");
					$("#fund_apply_btn").removeAttr("disabled");
					$("#fund_reset_btn").removeAttr("disabled");
				}, function(xhr) {
					$("#fund_plan_btn").removeAttr("disabled");
					$("#fund_apply_btn").removeAttr("disabled");
					$("#fund_reset_btn").removeAttr("disabled");
					showErrorMsgFundApp("");
					handleErrorAndDisplayMsg(xhr, "fund_err_msg");
				});
	}
}
function loadFundPlanningSheet() {
	if (fundPlanningData != null && mProfilePlanning != null) {
		var loanProcessingFeePercent = groupRules.cmLoanProcessingFeePercent;
		if(mProfilePlanning.role == "Associate Member")
			loanProcessingFeePercent = groupRules.amLoanProcessingFeePercent;
		var planTableContent = "<tr><td width='232'>Member Name</td><td width='232'>"
				+ $("#member_name").val()
				+ "</td></tr><tr><td>Member Role</td><td>"
				+ mProfilePlanning.mrole
				+ "</td></tr><tr><td>Member Pending Loan</td><td>Rs."
				+ (mProfilePlanning.account.loanAm - mProfilePlanning.account.recLoanAm)
				+ "</td></tr><tr><td>Fund Type</td><td>"
				+ fundTypePlanning
				+ "</td></tr><tr><td>Loan Calculation Type</td><td>"
				+ fundPlanningData.loanCalculation
				+ "</td></tr><tr><td>Recovery Period</td><td>"
				+ recPeriodPlanning
				+ "</td></tr><tr><td>Principle Amount</td><td>Rs."
				+ fundPlanningData.principle
				+ "</td></tr><tr><td>Rate of Interest</td><td>"
				+ fundPlanningData.rateOfInterest
				+ "</td></tr><tr><td>EMI</td><td>Rs."
				+ fundPlanningData.fixedEMI
				+ "</td></tr><tr><td>No. of EMIs</td><td>"
				+ fundPlanningData.noOfEMIs
				+ "</td></tr><tr><td>No. of Start Up EMIs</td><td>"
				+ fundPlanningData.noOfStartUpEMI
				+ "</td></tr><tr><td>Start Date</td><td>"
				+ fundPlanningData.startDate
				+ "</td></tr><tr><td>Expected Completion Date</td><td>"
				+ fundPlanningData.expCompletionDate
				+ "</td></tr><tr><td>Due Date of Month</td><td>"
				+ fundPlanningData.dueDay
				+ "</td></tr><tr><td>Pre EMIs</td><td>"
				+ fundPlanningData.preEMI
				+ "</td></tr><tr><td>Total Interest</td><td>Rs."
				+ fundPlanningData.totalInterest
				+ "</td></tr><tr><td>Loan Processing Fee</td><td>Rs."
				+ ((fundPlanningData.principle * loanProcessingFeePercent) / 100.0)
				+ "</td></tr>";
		$("#fund_planning_msg").hide();
		$("#fund_planning_table").html(planTableContent);
	}
}
function loadEMIBreakdownSheet() {
	var emiTableContent = "<tr><td>No Data to display</td></tr>";
	if (fundPlanningData != null) {
		var loanEMIs = fundPlanningData.loanEMIs;
		if (loanEMIs != null && loanEMIs.length != 0) {
			emiTableContent = "<tr><th>Month No.</th><th>Due Date</th><th>EMI</th><th>Principle EMI</th><th>Interest EMI</th><th>Pending Principle</th></tr>";
			for (var i = 0; i < loanEMIs.length; i++) {
				var emi = loanEMIs[i];
				emiTableContent += "<tr><td>" + emi.monthNo + "</td><td>"
						+ emi.dueDate + "</td><td>Rs." + emi.emi
						+ "</td><td>Rs." + emi.principleEMI + "</td><td>Rs."
						+ emi.interestEMI + "</td><td>Rs." + emi.principle
						+ "</td></tr>";
			}
		}
	}
	$("#fund_emi_breakdown_div").show();
	$("#emi_breakdown_msg").hide();
	$("#emi_breakdown_table").html(emiTableContent);
}
function enableFundAppFormFields() {
	$("#principle").removeAttr("disabled");
	$("#fund_s_date").removeAttr("disabled");
	$("#fund_type").removeAttr("disabled");
	$("#loan_calc_type").removeAttr("disabled");
	// $("#recovery_period").removeAttr("disabled");
	loanCalcTypeChangeHandler();
	$("#fund_plan_btn").removeAttr("disabled");
}
function resetFullFundApplication() {
	isFundApp = true;
	trackedMemberLoanAc = null;
	showErrorMsgFundApp("");
	showFundDefaultButtons(true);
	resetFundAppForm();
	clearFundCalculation();
	$("#fund_emi_breakdown_div").hide();
	$("#fund_attachment_div").hide();
}
function resetFundAppForm() {
	mProfileSearched = null;
	$("#member_name").val("");
	$("#member_name").removeAttr("disabled");
	$("#fundapp_m_search").removeAttr("disabled");
	$("#m_role").val("");
	$("#m_saving").val("");
	$("#m_rating").val("");
	$("#m_pending_loan").val("");
	$("#fund_type").attr("disabled", "disabled");
	$("#loan_calc_type").attr("disabled", "disabled");
	// $("#recovery_period").attr("disabled", "disabled");
	$("#principle").val("");
	$("#principle").attr("disabled", "disabled");
	$("#roi").val("");
	$("#fund_s_date").val(convertTS2DDMMYYYYDate(currentServerTime));
	$("#fund_s_date").attr("disabled", "disabled");
	$("#fixed_emi").val("");
	$("#fixed_emi").attr("disabled", "disabled");
	$("#num_of_emi").val("");
	$("#num_of_emi").attr("disabled", "disabled");
	$("#fund_plan_btn").attr("disabled", "disabled");
}
function clearFundCalculation() {
	mProfilePlanning = null;
	fundPlanningData = null;
	fundTypePlanning = null;
	recPeriodPlanning = null;
	$("#fund_planning_table").html("");
	$("#fund_planning_msg").show();
	$("#emi_breakdown_table").html("");
	$("#emi_breakdown_msg").show();
	$("#fund_apply_btn").attr("disabled", "disabled");
}
function disableFullFundAppForm() {
	$("#member_name").attr("disabled", "disabled");
	$("#fundapp_m_search").attr("disabled", "disabled");
	$("#fund_type").attr("disabled", "disabled");
	$("#loan_calc_type").attr("disabled", "disabled");
	// $("#recovery_period").attr("disabled", "disabled");
	$("#principle").attr("disabled", "disabled");
	$("#fund_s_date").attr("disabled", "disabled");
	$("#fixed_emi").attr("disabled", "disabled");
	$("#num_of_emi").attr("disabled", "disabled");
	$("#fund_plan_btn").attr("disabled", "disabled");
	$("#fund_apply_btn").attr("disabled", "disabled");
}
function applyFundCalculation() {
	if (fundPlanningData != null && mProfilePlanning != null && confirm("Apply loan?") === true) {
		showMsgFundApp("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");

		$("#fund_plan_btn").attr("disabled", "disabled");
		$("#fund_apply_btn").attr("disabled", "disabled");

		var payload = "{\"memberAcNo\": " + mProfilePlanning.memberAcNo
				+ ",\"loanType\":\"" + fundTypePlanning
				+ "\",\"loanCalculation\":\""
				+ fundPlanningData.loanCalculation + "\",\"approvedByMember\":"
				+ curMemberAcNo + ",\"recoveryPeriod\":\"" + recPeriodPlanning
				+ "\",\"principle\":" + fundPlanningData.principle
				+ ",\"projectedInterest\":" + fundPlanningData.totalInterest
				+ ",\"installment\":" + fundPlanningData.fixedEMI
				+ ",\"rateOfInterest\":" + fundPlanningData.rateOfInterest
				+ ",\"startupNoOfInst\":" + fundPlanningData.noOfStartUpEMI
				+ ",\"expNoOfInst\":" + fundPlanningData.noOfEMIs
				+ ",\"requestedDate\":\""
				+ convertTS2DDMMYYYYDate(currentServerTime)
				+ "\",\"expNoOfInst\":" + fundPlanningData.noOfEMIs
				+ ",\"instStartDate\":\"" + fundPlanningData.startDate
				+ "\",\"expCompletionDate\":\""
				+ fundPlanningData.expCompletionDate + "\"}";

		ajaxCall(
				"/mloan/v1/member_loan_ac",
				"PUT",
				false,
				payload,
				function(data, status) {
					showMsgFundApp("Loan application has been submitted successfully."
							+ "<br>Loan Account Number: <b>"
							+ data.memberLoanAcNo
							+ "</b>"
							+ "<br>Account Status: <b>"
							+ data.accountStatus + "</b>");
					trackedMemberLoanAc = data;
					$("#fund_attachment_div").show();
					$("#fund_plan_btn").removeAttr("disabled");
					disableFullFundAppForm();
					loadAppTrackingTab();
				},
				function(xhr) {
					$("#fund_plan_btn").removeAttr("disabled");
					$("#fund_apply_btn").removeAttr("disabled");
					showErrorMsgFundApp("");
					handleErrorAndDisplayMsg(xhr, "fund_err_msg");
				});
	}
}
function showMsgFundApp(msg) {
	$("#fund_msg").html(msg);
	$("#fund_err_msg").hide();
	$("#fund_msg").show();
}
function showErrorMsgFundApp(msg) {
	$("#fund_err_msg").html(msg);
	$("#fund_msg").hide();
	$("#fund_err_msg").show();
}
function showFundDefaultButtons(isdefault) {
	$("#fund_disburse_btn").hide();
	$("#go2lclosure_btns").hide();
	$("#lclosure_back_btn").hide();
	if (isdefault) {
		$("#fund_approval_btns").hide();
		$("#fund_back_btn").hide();
		$("#fund_default_btns").show();
	} else {
		$("#fund_default_btns").hide();
		$("#fund_back_btn").show();
		$("#fund_approval_btns").show();
	}
}
function showFundDisburseBackButton() {
	$("#fund_default_btns").hide();
	$("#fund_approval_btns").hide();
	$("#go2lclosure_btns").hide();
	$("#lclosure_back_btn").hide();
	$("#fund_disburse_btn").show();
	$("#fund_back_btn").show();
}
function showFundBackButtonOnly() {
	$("#fund_default_btns").hide();
	$("#fund_approval_btns").hide();
	$("#fund_disburse_btn").hide();
	$("#go2lclosure_btns").hide();
	$("#lclosure_back_btn").hide();
	$("#fund_back_btn").show();
}
function showFundClosureButton() {
	$("#fund_default_btns").hide();
	$("#fund_approval_btns").hide();
	$("#fund_disburse_btn").hide();
	$("#fund_back_btn").hide();
	$("#go2lclosure_btns").show();
	$("#lclosure_back_btn").show();
}
function approveFundApp() {
	updateFundApplicationStatus("Approved");
}
function rejectFundApp() {
	updateFundApplicationStatus("Rejected");
}
function holdFundApp() {
	updateFundApplicationStatus("On Hold");
}
function back2AppTrackFromFundApp() {
	isFundApp = true;
	loadAppTrackingTab();
	resetFullFundApplication();
}
function updateFundApplicationStatus(status) {
	if (status != "Under Review") {
		if (confirm("Are you sure to mark application to " + status + "?") === false)
			return;
		showMsgFundApp("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
		$("#fund_approve_btn").attr("disabled", "disabled");
		$("#fund_reject_btn").attr("disabled", "disabled");
		$("#fund_hold_btn").attr("disabled", "disabled");
	}
	trackedMemberLoanAc.accountStatus = status;
	trackedMemberLoanAc.approvedByMember = curMemberAcNo;
	trackedMemberLoanAc.attachments = null;
	var payload = JSON.stringify(trackedMemberLoanAc);
	ajaxCall(
			"/mloan/v1/approve_member_loan_ac",
			"POST",
			true,
			payload,
			function(data, status) {
				if (data != "") {
					showMsgFundApp("Updated Application Status: <b>"
							+ data.accountStatus + "</b>"
							+ "<br>Fund Ac No: <b>" + data.memberLoanAcNo
							+ "</b>");
					if (data.accountStatus == "Approved")
						showFundDisburseBackButton();
					trackedMemberLoanAc = data;
					trackedMemberLoanAc.attachments = mfundAttachments;
					loadAppTrackingTab();
					$("#fund_approve_btn").removeAttr("disabled");
					$("#fund_reject_btn").removeAttr("disabled");
					$("#fund_hold_btn").removeAttr("disabled");
				}
			},
			function(xhr) {
				$("#fund_approve_btn").removeAttr("disabled");
				$("#fund_reject_btn").removeAttr("disabled");
				$("#fund_hold_btn").removeAttr("disabled");
				showErrorMsgFundApp("");
				handleErrorAndDisplayMsg(xhr, "fund_err_msg");
			});
}
function showFundDetailsForActivation() {
	isFundApp = false;
	var index = parseInt($(this).data("id"));
	if (!isNaN(index))
		trackedMemberLoanAc = appTrackingData.memberLoanAcs[index];
	if (trackedMemberLoanAc.accountStatus == "Active"
			|| trackedMemberLoanAc.accountStatus == "Rejected")
		showFundBackButtonOnly();
	else if (trackedMemberLoanAc.accountStatus == "Approved")
		showFundDisburseBackButton();
	else
		showFundDefaultButtons(false);
	showMemberFundAppDetails(trackedMemberLoanAc);
	if (trackedMemberLoanAc.accountStatus == "Submitted")
		updateFundApplicationStatus("Under Review");
	$("#fund_emi_breakdown_div").show();
}
function showFundDetailsForClosure() {
	isFundApp = false;
	var index = parseInt($(this).data("id"));
	trackedMemberLoanAc = trackedMember.memberLoanAc[index];
	showFundClosureButton();
	if(trackedMemberLoanAc.accountStatus != "Active")
		$("#go2lclosure_btns").hide();
	showMemberFundAppDetails(trackedMemberLoanAc);
	$("#fund_emi_breakdown_div").show();
}
function showMemberFundAppDetails(memberLoanAcData) {
	showMsgFundApp("Loan Account No: <b>" + memberLoanAcData.memberLoanAcNo
			+ "</b><br>Application Status: <b>" + memberLoanAcData.accountStatus + "</b>");
	disableFullFundAppForm();
	$("#member_name").val(memberLoanAcData.memberName);
	$("#m_role").val(memberLoanAcData.mrole);
	var loanProcFeePercent = groupRules.cmLoanProcessingFeePercent;
	if(memberLoanAcData.mrole == "Associate Member")
		loanProcFeePercent = groupRules.amLoanProcessingFeePercent;
	var memberAc = memberLoanAcData.memberAc;
	var pendingLoanAmt = "";
	if(memberAc != null) {
		pendingLoanAmt = (memberAc.loanAm - memberAc.recLoanAm);
		$("#m_saving").val(memberAc.plannedMonthlySavingAm);
		$("#m_rating").val(memberAc.creditRating);
		$("#m_pending_loan").val(pendingLoanAmt);
	}
	$("#fund_type").val(memberLoanAcData.loanType);
	$("#loan_calc_type").val(memberLoanAcData.loanCalculation);
	$("#recovery_period").val(memberLoanAcData.recoveryPeriod);
	$("#principle").val(memberLoanAcData.principle);
	$("#roi").val(memberLoanAcData.rateOfInterest);
	$("#fund_s_date").val(memberLoanAcData.instStartDate);
	$("#fixed_emi").val(memberLoanAcData.installment);
	$("#num_of_emi").val(memberLoanAcData.expNoOfInst);

	var planTableContent = "<tr><td width='232'>Member Name</td><td width='232'>"
			+ memberLoanAcData.memberName
			+ "</td></tr><tr><td>Member Role</td><td>"
			+ memberLoanAcData.mrole
			+ "</td></tr><tr><td>Member Pending Loan</td><td>Rs."
			+ pendingLoanAmt
			+ "</td></tr><tr><td>Fund Type</td><td>"
			+ memberLoanAcData.loanType
			+ "</td></tr><tr><td>Loan Calculation Type</td><td>"
			+ memberLoanAcData.loanCalculation
			+ "</td></tr><tr><td>Recovery Period</td><td>"
			+ memberLoanAcData.recoveryPeriod
			+ "</td></tr><tr><td>Principle Amount</td><td>Rs."
			+ memberLoanAcData.principle
			+ "</td></tr><tr><td>Rate of Interest</td><td>"
			+ memberLoanAcData.rateOfInterest
			+ "</td></tr><tr><td>EMI</td><td>Rs."
			+ memberLoanAcData.installment
			+ "</td></tr><tr><td>No. of EMIs</td><td>"
			+ memberLoanAcData.expNoOfInst
			+ "</td></tr><tr><td>No. of Start Up EMIs</td><td>"
			+ memberLoanAcData.startupNoOfInst
			+ "</td></tr><tr><td>Start Date</td><td>"
			+ memberLoanAcData.instStartDate
			+ "</td></tr><tr><td>Expected Completion Date</td><td>"
			+ memberLoanAcData.expCompletionDate
			+ "</td></tr><tr><td>Due Date of Month</td><td>"
			+ groupRules.dueDayOfMonth
			+ "</td></tr><tr><td>Pre EMIs</td><td>"
			+ memberLoanAcData.preEmiInterest
			+ "</td></tr><tr><td>Total Interest</td><td>Rs."
			+ memberLoanAcData.projectedInterest
			+ "</td></tr><tr><td>Loan Processing Fee</td><td>Rs."
			+ ((memberLoanAcData.principle * loanProcFeePercent) / 100.0)
			+ "</td></tr>";
	$("#fund_planning_msg").hide();
	$("#fund_planning_table").html(planTableContent);
	
	mfundAttachments = memberLoanAcData.attachments;
	reloadMemberFundAttachmentTable();
}
function reloadMemberFundAttachmentTable() {
	var content = "";
	for (var i = 0; i < mfundAttachments.length; i++) {
		if(mfundAttachments[i] != null) {
			content += "<tr id='fund_doc_row" + mfundAttachments[i].docId + "'><td>" + docTypes[mfundAttachments[i].docType.docTypeId - 1].docCategory
					+ "</td><td>" + docTypes[mfundAttachments[i].docType.docTypeId - 1].docType
					+ "</td><td>" + mfundAttachments[i].fileName
					+ "</td><td width='50'><button class='btn yellow aboutme' data-id='" + i + "' id='fund_edit_doc_btn" + mfundAttachments[i].docId + "'>Change</button>"
					+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='fund_del_doc_btn" + mfundAttachments[i].docId + "'>Delete</button></td></tr>";
		}
	}
	if (content != "") {
		var attachmentTableHeader = "<tr><td><strong>Document Type</strong></td><td><strong>Document Category</strong></td><td><strong>File Name</strong></td><td></td><td></td></tr>";
		$("#fund_attachments_table").html(attachmentTableHeader + content);
		for (var i = 0; i < mfundAttachments.length; i++) {
			if(mfundAttachments[i] != null) {
				$("#fund_edit_doc_btn" + mfundAttachments[i].docId).click(editMemberFundAttachment);
				$("#fund_del_doc_btn" + mfundAttachments[i].docId).click(deleteMemberFundAttachment);
			}
		}
	}
	$("#fund_attachment_div").show();
}
function uploadMemberFundAttachment() {
	if(trackedMemberLoanAc == null) {
		showDocUploadErrMsg("Search member and do fund planning first.", true);
	} else {
		var uri = (appBaseURL + "/mloan/v1/attach_file/" + trackedMemberLoanAc.memberAcNo + "/" + trackedMemberLoanAc.memberLoanAcNo);
		loadDocumentUploadDlg("Loan", uri, function(attachment) {
			mfundAttachments[mfundAttachments.length] = attachment;
			trackedMemberLoanAc.attachments = mfundAttachments;
			reloadMemberFundAttachmentTable();
		});
	}
}
function editMemberFundAttachment() {
	$("#doc_upload_dlg").modal("show");
	var index = parseInt($(this).data("id"));
	var uri = (appBaseURL + "/mloan/v1/update_attach_file/" + trackedMemberLoanAc.memberAcNo + "/" + mfundAttachments[index].docId + "/" + trackedMemberLoanAc.memberLoanAcNo);
	loadEditUploadedDocumentDlg("Loan", uri, mfundAttachments[index], function(attachment) {
		mfundAttachments[index] = attachment;
		reloadMemberFundAttachmentTable();
	});
}
function deleteMemberFundAttachment() {
	var index = parseInt($(this).data("id"));
	if (confirm("Delete attachment?") === true) {
		ajaxCall("/mloan/v1/delete_attach_file/" + trackedMemberLoanAc.memberAcNo + "/" + mfundAttachments[index].docId + "/" + trackedMemberLoanAc.memberLoanAcNo,
				"DELETE", true, null, function(data, status) {
			showMessage("Attachment deleted successfully.");
			// Delete row from table and set null for corresponding entry of attachment in array.
			$("#fund_doc_row" + mfundAttachments[index].docId).remove();
			if($("#fund_attachments_table tbody").children().length == 1)
				$("#fund_attachments_table").html("");
			mfundAttachments[index] = null;
			trackedMemberLoanAc.attachments = mfundAttachments;
		}, function(xhr) {
			showErrorMsgFundApp("");
			handleErrorAndDisplayMsg(xhr, "fund_err_msg");
		});
	}
}