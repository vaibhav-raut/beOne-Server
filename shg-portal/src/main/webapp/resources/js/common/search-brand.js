/**
 * 
 */
var searchedBrands = null, brandSearchedFor = "", manufacturer = {};
$(document).ready(function() {
	$("#sbrbyname_btn").click(searchBrandByNameHandler);
	$("#add_new_brand_btn").click(addNewBrandHandler);
	$("#search_existing_brand_btn").click(searchExistingBrandHandler);
});
//This function must be called from caller
function loadBrandSearchDlg(manufacturerObj, calledFor) {
	manufacturer = manufacturerObj;
	brandSearchedFor = calledFor;
	$("#search_existing_brand_btn").hide();
	$("#add_new_brand_btn").show();
	resetBrandSearchDialog();
	if (manufacturer && manufacturer.name) {
		$("#sbr_manufacturer").html(manufacturer.name);
	} else {
		$("#sbr_dialog_table_body").html("<tr><td align='center' style='color: red'>Manufacturer information is not available</td></tr>");
	}
}
function searchBrandByNameHandler() {
	if (!manufacturer || !manufacturer.manufacturerAcNo) {
		$("#sbr_dialog_table_body").html("<tr><td align='center' style='color: red'>Manufacturer information is not available</td></tr>");
		return;
	}
	var bname = $("#sbr_name").val();
	if (bname == "") {
		$("#sbr_dialog_table_body").html("<tr><td align='center' style='color: red'>Please provide some input in brand name</td></tr>");
		return;
	}
	$("#sbr_dialog_table_body").html("<tr><td align='center'>Please Wait... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>");
	ajaxCall("/mr/inventory/v1/search_brand/" + manufacturer.manufacturerAcNo + "/" + bname,
			"GET", true, "", loadBrandSearchData, brandSearchFailed);
}
function loadBrandSearchData(data, status) {
	searchedBrands = data;
	if (searchedBrands != null && searchedBrands.length != 0) {
		var tableContent = "<tr><td align='center' colspan='2'><strong>Select Brand</strong></td></tr>";
		tableContent += "<tr><td><strong>ID<strong></td><td><strong>Name<strong></td></tr>";
		for (var i = 0; i < searchedBrands.length; i++) {
			tableContent += "<tr><td>" + searchedBrands[i].brandId
				+ "</td><td><a href='#' data-id='" + i + "' id='brand_" + searchedBrands[i].brandId + "'>"
				+ searchedBrands[i].brandName + "</a></td></tr>";
		}
		$("#sbr_dialog_table_body").html(tableContent);
		for (var i = 0; i < searchedBrands.length; i++) {
			$("#brand_" + searchedBrands[i].brandId).click(loadSelectedBrand);
		}
	} else {
		$("#sbr_dialog_table_body").html("<tr><td align='center'>No brand found.</td></tr>");
	}
}
function loadSelectedBrand() {
	var index = parseInt($(this).data("id"));
	var brandId = searchedBrands[index].brandId;
	var name = searchedBrands[index].brandName;
	
	$("#st_brand_id").val(brandId);
	$("#st_brand_name").html(name);
	
	$("#search_brand_dlg").modal("hide");
	resetBrandSearchDialog();
}
function brandSearchFailed(xhr) {
	handleErrorAndDisplayMsg(xhr, "sbr_dialog_table_body");
}
function addNewBrandHandler() {
	$("#sbr_table").hide();
	$("#add_new_brand_btn").hide();
	$("#search_existing_brand_btn").show();
	$("#sbr_dialog_table_body").html("<tr><td align='center'>Please visit <b>Brand & Stock Type Registration</b> page to add new brand.</td></tr>");
}
function searchExistingBrandHandler() {
	$("#search_existing_brand_btn").hide();
	$("#add_new_brand_btn").show();
	$("#sbr_table").show();
	$("#sbr_dialog_table_body").html("");
}
function resetBrandSearchDialog() {
	$("#sbr_table").show();
	$("#sbr_manufacturer").html("--");
	$("#sbr_name").val("");
	$("#sbr_dialog_table_body").html("");
}