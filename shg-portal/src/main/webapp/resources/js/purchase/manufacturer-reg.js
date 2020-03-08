/**
 * 
 */
$(document).ready(function() {
	$("#m_reg_tab").click(resetManufacturerRegForm);
	$("#m_submit_btn").click(submitManufacturerRegData);
	$("#m_reset_btn").click(resetManufacturerRegForm);
	$("#m_sel_district_btn").click(selectDistrictForManufacturerReg);
});
function selectDistrictForManufacturerReg() {
	loadSelectDistrictDialog("m_state", "m_district");
}
function submitManufacturerRegData() {
	var gname = $("#m_name").val().replace(/\n/g,
			" "), address = $("#m_address").val().replace(/\n/g, " "), pincode = $("#m_pin_code")
			.val(), priMob = $("#m_pri_mobile").val(), selectedDistrict = $("#m_district").val();
	if (gname == "") {
		showErrorMsgManufacturerReg("Please provide a Manufacturer Name.");
		$("#m_name").focus();
	} else if (address == "") {
		showErrorMsgManufacturerReg("Please provide Address.");
		$("#m_address").focus();
	} else if (pincode == "") {
		showErrorMsgManufacturerReg("Please provide pin code.");
		$("#m_pin_code").focus();
	} else if (priMob == "") {
		showErrorMsgManufacturerReg("Please provide Primary Mobile.");
		$("#m_pri_mobile").focus();
	} else if (selectedDistrict == "") {
		showErrorMsgManufacturerReg("Please select district.");
		$("#m_sel_district_btn").focus();
	} else {
		if (confirm("Submit application?") === true) {
			showMsgManufacturerReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
			$("#m_submit_btn").attr("disabled", "disabled");
	
			var description = $("#m_desc").val().replace(/\n/g, " ");
			var payload = "{\"groupName\":\"" + gname + "\",\"description\":\"" + description
					+ "\",\"contacts\":[{\"lang\":\"English\",\"name\":\"" + gname
					+ "\",\"description\":\"" + description + "\",\"address\":\"" + address + "\",\"village\":\""
					+ $("#m_village").val() + "\",\"grampanchayat\":\""
					+ $("#m_panchayat").val() + "\",\"taluka\":\""
					+ $("#m_tehsil").val() + "\",\"district\":\""
					+ selectedDistrict + "\",\"state\":\""
					+ $("#m_state").val() + "\",\"email\":\""
					+ $("#m_email").val() + "\",\"pinCode\":\"" + pincode
					+ "\",\"priMobile\":\"" + priMob + "\",\"phone\":\""
					+ $("#m_phone").val() + "\",\"secMobile\":\""
					+ $("#m_sec_mobile").val() + "\"}]" + ",\"mappingMemberAcNos\":[\""
					+ curMemberAcNo + "\"]}";
	
			ajaxCall("/mr/inventory/v1/add_manufacturer", "PUT", true, payload, function(data,
					status) {
				showMsgManufacturerReg("Manufacturer Added Successfully."
						+ "<br>Group Account Number: <b>"
						+ getReadableAccNumForDistrict(data.districtCode,
								data.groupAcNo) + "</b><br>Group Name: <b>"
						+ data.contacts[0].name + "</b>");
				disableManufacturerRegForm();
			}, function(xhr) {
				$("#m_submit_btn").removeAttr("disabled");
				showErrorMsgManufacturerReg("");
				handleErrorAndDisplayMsg(xhr, "m_reg_err_msg");
			});
		}
	}
}
function showMsgManufacturerReg(msg) {
	$("#m_reg_msg").html(msg);
	$("#m_reg_err_msg").hide();
	$("#m_reg_msg").show();
}
function showErrorMsgManufacturerReg(msg) {
	$("#m_reg_err_msg").html(msg);
	$("#m_reg_msg").hide();
	$("#m_reg_err_msg").show();
}
function resetManufacturerRegForm() {
	showErrorMsgManufacturerReg("");
	$("#m_submit_btn").removeAttr("disabled");
	$("#m_name").val("");
	$("#m_desc").val("");
	$("#m_address").val("");
	$("#m_village").val("");
	$("#m_panchayat").val("");
	$("#m_tehsil").val("");
	$("#m_pin_code").val("");
	$("#m_district").val("");
	$("#m_state").val("");
	$("#m_phone").val("");
	$("#m_pri_mobile").val("");
	$("#m_sec_mobile").val("");
	$("#m_email").val("");

	$("#m_name").removeAttr("disabled");
	$("#m_desc").removeAttr("disabled");
	$("#m_address").removeAttr("disabled");
	$("#m_village").removeAttr("disabled");
	$("#m_panchayat").removeAttr("disabled");
	$("#m_tehsil").removeAttr("disabled");
	$("#m_pin_code").removeAttr("disabled");
	$("#m_phone").removeAttr("disabled");
	$("#m_pri_mobile").removeAttr("disabled");
	$("#m_sec_mobile").removeAttr("disabled");
	$("#m_email").removeAttr("disabled");
}
function disableManufacturerRegForm() {
	$("#m_name").attr("disabled", "disabled");
	$("#m_desc").attr("disabled", "disabled");
	$("#m_address").attr("disabled", "disabled");
	$("#m_village").attr("disabled", "disabled");
	$("#m_panchayat").attr("disabled", "disabled");
	$("#m_tehsil").attr("disabled", "disabled");
	$("#m_pin_code").attr("disabled", "disabled");
	$("#m_phone").attr("disabled", "disabled");
	$("#m_pri_mobile").attr("disabled", "disabled");
	$("#m_sec_mobile").attr("disabled", "disabled");
	$("#m_email").attr("disabled", "disabled");
}