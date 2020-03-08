/**
 * 
 */
$(document).ready(function() {
	$("#br_search_manufacturer_btn").click(bSearchManufacturerClickHandler);
	$("#br_reset_btn").click(resetBrandRegForm);
	$("#br_add_btn").click(addNewBrand);
	
	$("#st_search_brand_btn").click(bSearchBrandClickHandler);
	$("#st_sel_prod_category_btn").click(bSelectProdCategoryClickHandler);
	$("#st_reset_btn").click(resetSTypeRegForm);
	$("#st_add_btn").click(addNewStockType);
});
function bSearchManufacturerClickHandler() {
	loadManufacturerSearchDlg(BRAND);
}
function addNewBrand() {
	var manufacturerAcNo = $("#br_manufacturer_acc_num").val(), bname = $("#br_name").val().replace(/\n/g, " ");
	if (manufacturerAcNo == "") {
		showErrorMsgBrandReg("Please search manufacturer.");
		$("#br_search_manufacturer_btn").focus();
	} else if (bname == "") {
		showErrorMsgBrandReg("Please provide a Brand Name.");
		$("#br_name").focus();
	} else {
		if (confirm("Add new brand '" + bname + "'?") === true) {
			showMsgBrandReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
			$("#br_add_btn").attr("disabled", "disabled");
	
			var payload = "{\"manufactureAcNo\":" + manufacturerAcNo + ",\"name\":\"" + bname
					+ "\",\"description\":\"" + $("#br_desc").val().replace(/\n/g, " ")
					+ "\",\"properties\":\"" + $("#br_properties").val() + "\",\"link\":\""
					+ $("#br_link").val() + "\"}";
	
			ajaxCall("/mr/inventory/v1/add_brand", "PUT", true, payload, function(data,
					status) {
				showMsgBrandReg("Brand Added Successfully."
						+ "<br>Brand ID: <b>" + data.brandId + "</b><br>Brand Name: <b>"
						+ data.name + "</b>");
				disableBrandRegForm();
			}, function(xhr) {
				$("#br_add_btn").removeAttr("disabled");
				showErrorMsgBrandReg("");
				handleErrorAndDisplayMsg(xhr, "br_reg_err_msg");
			});
		}
	}
}
function showMsgBrandReg(msg) {
	$("#br_reg_msg").html(msg);
	$("#br_reg_err_msg").hide();
	$("#br_reg_msg").show();
}
function showErrorMsgBrandReg(msg) {
	$("#br_reg_err_msg").html(msg);
	$("#br_reg_msg").hide();
	$("#br_reg_err_msg").show();
}
function resetBrandRegForm() {
	showErrorMsgBrandReg("");
//	$("#br_manufacturer_name").html("--");
//	$("#br_manufacturer_acc_num").val("");
	$("#br_name").val("");
	$("#br_desc").val("");
	$("#br_prop").val("");
	$("#br_link").val("");

	$("#br_add_btn").removeAttr("disabled");
	$("#br_name").removeAttr("disabled");
	$("#br_desc").removeAttr("disabled");
	$("#br_prop").removeAttr("disabled");
	$("#br_link").removeAttr("disabled");
}
function disableBrandRegForm() {
	$("#br_add_btn").attr("disabled", "disabled");
	$("#br_name").attr("disabled", "disabled");
	$("#br_desc").attr("disabled", "disabled");
	$("#br_prop").attr("disabled", "disabled");
	$("#br_link").attr("disabled", "disabled");
}

function bSearchBrandClickHandler() {
	var manufacturerObj = {};
	var manufacturerAcNo = $("#br_manufacturer_acc_num").val();
	if (manufacturerAcNo == "") {
		showErrorMsgSTypeReg("Please search manufacturer.");
		$("#br_search_manufacturer_btn").focus();
	} else {
		manufacturerObj.name = $("#br_manufacturer_name").html();
		manufacturerObj.manufacturerAcNo = manufacturerAcNo;
	}
	loadBrandSearchDlg(manufacturerObj);
}
function bSelectProdCategoryClickHandler() {
	loadSelectProdCategoryDlg("st_prod_category");
}
function addNewStockType() {
	var manufacturerAcNo = $("#br_manufacturer_acc_num").val(),
	    brandId = $("#st_brand_id").val(),
	    stname = $("#st_name").val().replace(/\n/g, " "),
	    prodCategory = $("#st_prod_category").val();
	if (manufacturerAcNo == "") {
		showErrorMsgSTypeReg("Please search manufacturer.");
		$("#br_search_manufacturer_btn").focus();
	} else if (brandId == "") {
		showErrorMsgSTypeReg("Please search brand.");
		$("#st_search_brand_btn").focus();
	} else if (stname == "") {
		showErrorMsgSTypeReg("Please provide a Stock Type Name.");
		$("#st_name").focus();
	} else if (prodCategory == "") {
		showErrorMsgSTypeReg("Please select a proper Product Category.");
		$("#st_sel_prod_category_btn").focus();
	} else {
		if (confirm("Add new Stock Type '" + stname + "'?") === true) {
			showMsgSTypeReg("Please wait... <img src='resources/img/ajax-loader.gif' alt=''/>");
			$("#st_add_btn").attr("disabled", "disabled");
	
			var payload = "{\"brandId\":" + brandId + ",\"name\":\"" + stname
					+ "\",\"category\":\"" + prodCategory
					+ "\",\"description\":\"" + $("#st_desc").val().replace(/\n/g, " ")
					+ "\",\"properties\":\"" + $("#st_properties").val() + "\",\"link\":\""
					+ $("#st_link").val() + "\"}";
	
			ajaxCall("/mr/inventory/v1/add_stock_type", "PUT", true, payload, function(data,
					status) {
				showMsgSTypeReg("Stock Type Added Successfully."
						+ "<br>Stock Type Name: <b>" + data.name + "</b>");
				disableSTypeRegForm();
			}, function(xhr) {
				$("#st_add_btn").removeAttr("disabled");
				showErrorMsgSTypeReg("");
				handleErrorAndDisplayMsg(xhr, "st_reg_err_msg");
			});
		}
	}
}
function showMsgSTypeReg(msg) {
	$("#st_reg_msg").html(msg);
	$("#st_reg_err_msg").hide();
	$("#st_reg_msg").show();
}
function showErrorMsgSTypeReg(msg) {
	$("#st_reg_err_msg").html(msg);
	$("#st_reg_msg").hide();
	$("#st_reg_err_msg").show();
}
function resetSTypeRegForm() {
	showErrorMsgSTypeReg("");
//	$("#st_brand_name").html("--");
//	$("#st_brand_id").val("");
	$("#st_name").val("");
	$("#st_desc").val("");
	$("#st_prop").val("");
	$("#st_link").val("");

	$("#st_add_btn").removeAttr("disabled");
	$("#st_search_brand_btn").removeAttr("disabled");
	$("#st_name").removeAttr("disabled");
	$("#st_prod_category").removeAttr("disabled");
	$("#st_desc").removeAttr("disabled");
	$("#st_prop").removeAttr("disabled");
	$("#st_link").removeAttr("disabled");
}
function disableSTypeRegForm() {
	$("#st_add_btn").attr("disabled", "disabled");
	$("#st_search_brand_btn").attr("disabled", "disabled");
	$("#st_name").attr("disabled", "disabled");
	$("#st_prod_category").attr("disabled", "disabled");
	$("#st_desc").attr("disabled", "disabled");
	$("#st_prop").attr("disabled", "disabled");
	$("#st_link").attr("disabled", "disabled");
}