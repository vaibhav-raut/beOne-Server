/**
 * 
 */
var searchedStockTypes = null;
$(document).ready(function() {
	$("#stbyname_btn").click(searchStockTypeByNameHandler);
	$("#add_new_stype_btn").click(addNewStockTypeHandler);
	$("#search_existing_stype_btn").click(searchExistingStockTypeHandler);
});
//This function should be called from caller
function loadStockTypeSearchDlg() {
	$("#search_existing_stype_btn").hide();
	$("#add_new_stype_btn").show();
	resetStockTypeSearchDialog();
}
function searchStockTypeByNameHandler() {
	var stname = $("#sst_name").val();
	if (stname == "") {
		$("#sst_dialog_table_body").html("<tr><td align='center' style='color: red'>Please provide some input in Stock Type</td></tr>");
		return;
	}
	$("#sst_dialog_table_body").html("<tr><td align='center'>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	ajaxCall("/mr/inventory/v1/search_stock_type/" + stname,
			"GET", true, "", loadStockTypeSearchData, stockTypeSearchFailed);
}
function loadStockTypeSearchData(data, status) {
	searchedStockTypes = data;
	if (searchedStockTypes != null && searchedStockTypes.length != 0) {
		var tableContent = "<tr><td colspan='2' align='center'><strong>Select Stock Type</strong></td></tr>";
		tableContent += "<tr><td><strong>Stock Type<strong></td><td><strong>Brand</strong></td></tr>";
		for (var i = 0; i < searchedStockTypes.length; i++) {
			tableContent += "<tr><td><a href='#' data-id='" + i + "' id='stockType_" + searchedStockTypes[i].stockTypeId + "'>"
					+ searchedStockTypes[i].stockTypeName + "</a></td><td>"
					+ searchedStockTypes[i].brandName + "</td></tr>";
		}
		$("#sst_dialog_table_body").html(tableContent);
		for (var i = 0; i < searchedStockTypes.length; i++) {
			$("#stockType_" + searchedStockTypes[i].stockTypeId).click(loadSelectedStockType);
		}
	} else {
		$("#sst_dialog_table_body").html("<tr><td align='center'>No Stock Type found.</td></tr>");
	}
}
function loadSelectedStockType() {
	var index = parseInt($(this).data("id"));
	var stockTypeId = searchedStockTypes[index].stockTypeId;
	var stockTypeName = searchedStockTypes[index].stockTypeName;
	var brandName = searchedStockTypes[index].brandName;
	$("#stock_type_id").val(stockTypeId);
	$("#stock_type").html(stockTypeName + " - " + brandName);
	$("#search_stock_type_dlg").modal("hide");
	resetStockTypeSearchDialog();
}
function stockTypeSearchFailed(xhr) {
	handleErrorAndDisplayMsg(xhr, "sst_dialog_table_body");
}
function addNewStockTypeHandler() {
	$("#sst_table").hide();
	$("#add_new_stype_btn").hide();
	$("#search_existing_stype_btn").show();
	$("#sst_dialog_table_body").html("<tr><td align='center'>Please visit <b>Brand & Stock Type Registration</b> page to add new brand and stock type.</td></tr>");
}
function searchExistingStockTypeHandler() {
	$("#search_existing_stype_btn").hide();
	$("#add_new_stype_btn").show();
	$("#sst_table").show();
	$("#sst_dialog_table_body").html("");
}
function resetStockTypeSearchDialog() {
	$("#sst_table").show();
	$("#sst_name").val("");
	$("#sst_dialog_table_body").html("");
}