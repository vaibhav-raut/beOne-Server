/**
 * 
 */
var PURCHASE_INVOICE = "purchase_invoice", BRAND = "brand";
var searchedManufacturers = null, manufacturerSearchedFor = "";
$(document).ready(function() {
	$("#svbyname_btn").click(searchManufacturerByNameHandler);
	$("#add_new_manufacturer_btn").click(addNewManufacturerHandler);
	$("#search_existing_manufacturer_btn").click(searchExistingManufacturerHandler);
});
//This function must be called from caller
function loadManufacturerSearchDlg(calledFor) {
	manufacturerSearchedFor = calledFor;
	$("#search_existing_manufacturer_btn").hide();
	$("#add_new_manufacturer_btn").show();
	resetManufacturerSearchDialog();
}
function searchManufacturerByNameHandler() {
	var vname = $("#sm_name").val();
	if (vname == "") {
		$("#sm_dialog_table_body").html("<tr><td align='center' style='color: red'>Please provide some input in manufacturer name</td></tr>");
		return;
	}
	$("#sm_dialog_table_body").html("<tr><td align='center'>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	ajaxCall("/mr/inventory/v1/search_manufacturer/" + vname,
			"GET", true, "", loadManufacturerSearchData, manufacturerSearchFailed);
}
function loadManufacturerSearchData(data, status) {
	searchedManufacturers = data;
	if (searchedManufacturers != null && searchedManufacturers.length != 0) {
		var tableContent = "<tr><td colspan='2' align='center'><strong>Select Manufacturer</strong></td></tr>";
		tableContent += "<tr><td><strong>Name<strong></td><td><strong>Place</strong></td></tr>";
		for (var i = 0; i < searchedManufacturers.length; i++) {
			tableContent += "<tr><td><a href='#' data-id='" + i + "' id='manufacturer_" + searchedManufacturers[i].groupAcNo + "'>"
					+ searchedManufacturers[i].groupName + "</a></td><td>"
					+ searchedManufacturers[i].place + "</td></tr>";
		}
		$("#sm_dialog_table_body").html(tableContent);
		for (var i = 0; i < searchedManufacturers.length; i++) {
			$("#manufacturer_" + searchedManufacturers[i].groupAcNo).click(loadSelectedManufacturer);
		}
	} else {
		$("#sm_dialog_table_body").html("<tr><td align='center'>No manufacturer found.</td></tr>");
	}
}
function loadSelectedManufacturer() {
	var index = parseInt($(this).data("id"));
	var manufacturerGrAcNo = searchedManufacturers[index].groupAcNo;
	var name = searchedManufacturers[index].groupName;
	var place = searchedManufacturers[index].place;
	
	if (manufacturerSearchedFor === PURCHASE_INVOICE) {
		$("#manufacturer_acc_num").val(manufacturerGrAcNo);
		$("#manufacturer_name").html(name + " - " + place);
	} else if (manufacturerSearchedFor === BRAND) {
		$("#br_manufacturer_acc_num").val(manufacturerGrAcNo);
		$("#br_manufacturer_name").html(name + " - " + place);
	}
	
	$("#search_manufacturer_dlg").modal("hide");
	resetManufacturerSearchDialog();
}
function manufacturerSearchFailed(xhr) {
	handleErrorAndDisplayMsg(xhr, "sm_dialog_table_body");
}
function addNewManufacturerHandler() {
	$("#sm_table").hide();
	$("#add_new_manufacturer_btn").hide();
	$("#search_existing_manufacturer_btn").show();
	$("#sm_dialog_table_body").html("<tr><td align='center'>Please visit <b>Manufacturer Registration</b> page to add new manufacturer.</td></tr>");
}
function searchExistingManufacturerHandler() {
	$("#search_existing_manufacturer_btn").hide();
	$("#add_new_manufacturer_btn").show();
	$("#sm_table").show();
	$("#sm_dialog_table_body").html("");
}
function resetManufacturerSearchDialog() {
	$("#sm_table").show();
	$("#sm_name").val("");
	$("#sm_dialog_table_body").html("");
}