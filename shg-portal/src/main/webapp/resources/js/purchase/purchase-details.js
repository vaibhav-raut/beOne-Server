/**
 * 
 */
var lotArray = new Array(), vat, discount, grossTotal, netTotalCalculated;
$(document).ready(function() {
	$("#search_manufacturer_btn").click(searchManufacturerClickHandler);
	$("#search_stock_type_btn").click(loadStockTypeSearchDlg);

	$("#invoice_submit_btn").click(submitInvoiceDetails);
	$("#invoice_reset_btn").click(resetInvoiceForm);
	$("#add_lot_btn").click(addLotToInvoice);

	$("#vat_percent").change(vatDiscountAmountChangeHandler);
	$("#discount_percent").change(vatDiscountAmountChangeHandler);
	$("#other_taxes").change(vatDiscountAmountChangeHandler);
	$("#total_amt").change(vatDiscountAmountChangeHandler);
	
	$("#number_per_set").change(calculateLotTotal);
	$("#number_of_set").change(calculateLotTotal);
	$("#item_price").change(calculateLotTotal);
});
function searchManufacturerClickHandler() {
	loadManufacturerSearchDlg(PURCHASE_INVOICE);
}
function vatDiscountAmountChangeHandler() {
	var vatPercent = $("#vat_percent").val();
	if (!vatPercent)
		vatPercent = 0;
	var discountPercent = $("#discount_percent").val();
	if (!discountPercent)
		discountPercent = 0;
	var totalAmt = $("#total_amt").val();
	if (!totalAmt)
		totalAmt = 0;
	var otherTaxes = $("#other_taxes").val();
	if (!otherTaxes)
		otherTaxes = 0;
	vatPercent = new Number(vatPercent);
	discountPercent = new Number(discountPercent);
	totalAmt = new Number(totalAmt);
	otherTaxes = new Number(otherTaxes);
	
	vat = totalAmt * vatPercent / 100;
	discount = totalAmt * discountPercent / 100;
	grossTotal = totalAmt + vat + otherTaxes - discount;
	$("#vat").html("Rs. " + vat);
	$("#discount").html("Rs. " + discount);
	$("#gross_total").html("Rs. " + grossTotal);
	
	var sumOfAllLotTotals = 0;
	for (var i = 0; i < lotArray.length; i++) {
		sumOfAllLotTotals += lotArray[i].lotPriceAm;
	}
	netTotalCalculated = sumOfAllLotTotals === 0 ? 0 : (sumOfAllLotTotals + vat + otherTaxes - discount);
	$("#net_total").html("Rs. " + netTotalCalculated);
}
function calculateLotTotal() {
	var numPerSet = $("#number_per_set").val();
	if (!numPerSet)
		numPerSet = 0;
	var numOfSet = $("#number_of_set").val();
	if (!numOfSet)
		numOfSet = 0;
	var itemPrice = $("#item_price").val();
	if (!itemPrice)
		itemPrice = 0;
	numPerSet = new Number(numPerSet);
	numOfSet = new Number(numOfSet);
	itemPrice = new Number(itemPrice);
	$("#lot_total").html("Rs. " + (numPerSet * numOfSet * itemPrice));
}
function submitInvoiceDetails() {
	if (validateInvoiceForm()) {
		var numOfLot = parseInt($("#num_of_lots").val());
		var warningMsg = "Number of lot in invoice: " + numOfLot + "\nNumber of lot added: " + lotArray.length + "\nStill wants to continue?";
		if((lotArray.length !== 0 && lotArray.length === numOfLot) || confirm(warningMsg) === true) {
			var purchaseInvoice = {};
			purchaseInvoice.manufactureAcNo = $("#manufacturer_acc_num").val();
			purchaseInvoice.invoiceNo = $("#invoice_number").val();
			purchaseInvoice.invoiceStatus = $("#invoice_status").val();
			purchaseInvoice.noOfLots = $("#num_of_lots").val();
			purchaseInvoice.noOfItems = $("#num_of_items").val();
			purchaseInvoice.total = $("#total_amt").val();
			purchaseInvoice.discount = discount;
			purchaseInvoice.grossTotal = grossTotal;
			purchaseInvoice.vat = vat;
			purchaseInvoice.otherTaxes = $("#other_taxes").val();
			purchaseInvoice.netTotalCalculated = netTotalCalculated;
			purchaseInvoice.description = $("#description").val();
			purchaseInvoice.lots = lotArray;
			
			var payload = JSON.stringify(purchaseInvoice);
			ajaxCall("/mr/inventory/v1/add_purchase_invoice", "PUT", true, payload, function(data, status) {
				showMsgInvoice("Invoice submitted successfully.<br>Purchased Invoice Id: <b>" + data.purchasedInvoiceId + "</b>");
				disableInvoiceForm();
			}, function(xhr) {
				enableInvoiceForm();
				showErrorMsgInvoice("");
				handleErrorAndDisplayMsg(xhr, "invoice_err_msg");
			});
		}
	}
}
function validateInvoiceForm() {
	var manufacturerAccNum = $("#manufacturer_acc_num").val(), invoiceNum = $(
			"#invoice_number").val(), numOfLot = $("#num_of_lots").val(), numOfItem = $(
			"#num_of_items").val(), totalAmt = $("#total_amt").val();
	if (manufacturerAccNum === "") {
		showErrorMsgInvoice("Please search manufacturer.");
		$("#search_manufacturer_btn").focus();
	} else if (invoiceNum === "") {
		showErrorMsgInvoice("Please provide invoice number.");
		$("#invoice_number").focus();
	} else if (numOfLot === "") {
		showErrorMsgInvoice("Please provide number of lots.");
		$("#num_of_lots").focus();
	} else if (numOfItem === "") {
		showErrorMsgInvoice("Please provide number of items.");
		$("#num_of_items").focus();
	} else if (totalAmt === "") {
		showErrorMsgInvoice("Please provide total amount.");
		$("#total_amt").focus();
	} else {
		return true;
	}
	return false;
}
function addLotToInvoice() {
	$("#add_lot_err_msg").html("");
	if(!validateInvoiceForm()) {
		$("#add_lot_err_msg").html("Fill invoice form before adding lot to it.");
	} else {
		var stockTypeId = $("#stock_type_id").val(), numPerSet = $(
		"#number_per_set").val(), numOfSet = $("#number_of_set").val(), itemPrice = $(
		"#item_price").val();
		if (stockTypeId === "") {
			$("#add_lot_err_msg").html("Please search stock type.");
			$("#search_stock_type_btn").focus();
		} else if (numPerSet === "") {
			$("#add_lot_err_msg").html("Please provide number per set.");
			$("#number_per_set").focus();
		} else if (numOfSet === "") {
			$("#add_lot_err_msg").html("Please provide number of set.");
			$("#number_of_set").focus();
		} else if (itemPrice === "") {
			$("#add_lot_err_msg").html("Please provide item price.");
			$("#item_price").focus();
		} else {
			var lot = {};
			lot.lotId = lotArray.length + 1;
			lot.stockTypeId = new Number(stockTypeId);
			lot.noPerSet = new Number(numPerSet);
			lot.noOfSets = new Number(numOfSet);
			lot.itemPriceAm = new Number(itemPrice);
			lot.lotPriceAm = lot.noPerSet * lot.noOfSets * lot.itemPriceAm;
			lotArray[lotArray.length] = lot;
			reloadLotTable();
			$("#number_per_set").val("");
			$("#number_of_set").val("");
			$("#item_price").val("");
			$("#lot_total").html("Rs. --");
		}
	}
}
function reloadLotTable() {
	var content = "";
	for (var i = 0; i < lotArray.length; i++) {
		content += "<tr id='m_doc_row" + lotArray[i].lotId + "'><td>" + lotArray[i].lotId
				+ "</td><td>" + lotArray[i].stockTypeId
				+ "</td><td>" + lotArray[i].noPerSet
				+ "</td><td>" + lotArray[i].noOfSets
				+ "</td><td>" + lotArray[i].itemPriceAm
				+ "</td><td>" + lotArray[i].lotPriceAm
				+ "</td><td width='50'><button class='btn default' data-id='" + i + "' id='lot_del_btn" + lotArray[i].lotId + "'>Delete</button></td></tr>";
	}
	if (content != "") {
		var tableHeader = "<tr><td><strong>Lot Id</strong></td><td><strong>Stock Type</strong></td><td><strong>Number Per Set</strong></td><td><strong>Number of Set</strong></td><td><strong>Item Price</strong></td><td><strong>Total Amount</strong></td><td></td></tr>";
		$("#lot_table").html(tableHeader + content);
		for (var i = 0; i < lotArray.length; i++) {
			$("#lot_del_btn" + lotArray[i].lotId).click(deleteLot);
		}
		vatDiscountAmountChangeHandler();
	} else {
		$("#lot_table").html("<tr><td align='center'>No lots added to invoice</td></tr>");
	}
}
function deleteLot() {
	var newLotArray = new Array();
	var index = parseInt($(this).data("id"));
	for (var i = 0; i < lotArray.length; i++) {
		if (i !== index) {
			lotArray[i].lotId = newLotArray.length + 1;
			newLotArray[newLotArray.length] = lotArray[i];
		}
	}
	lotArray = newLotArray;
	reloadLotTable();
}
function showMsgInvoice(msg) {
	$("#invoice_msg").html(msg);
	$("#invoice_err_msg").hide();
	$("#invoice_msg").show();
}
function showErrorMsgInvoice(msg) {
	$("#invoice_err_msg").html(msg);
	$("#invoice_msg").hide();
	$("#invoice_err_msg").show();
}
function resetInvoiceForm() {
	showMsgInvoice("");
	$("#manufacturer_name").html("--");
	$("#manufacturer_acc_num").val("");
	$("#invoice_number").val("");
	$("#description").val("");
	$("#num_of_lots").val("");
	$("#num_of_items").val("");
	$("#total_amt").val("");
	$("#vat_percent").val("");
	$("#vat").html("Rs. --");
	$("#discount_percent").val("");
	$("#discount").html("Rs. --");
	$("#other_taxes").val("");
	$("#gross_total").html("Rs. --");
	$("#net_total").html("Rs. --");
	
	$("#add_lot_err_msg").html("");
	$("#stock_type").html("--");
	$("#stock_type_id").val("");
	$("#number_per_set").val("");
	$("#number_of_set").val("");
	$("#item_price").val("");
	$("#lot_total").html("Rs. --");
	
	lotArray = new Array();
	reloadLotTable();
	
	enableInvoiceForm();	
}
function enableInvoiceForm() {
	$("#manufacturer_acc_num").removeAttr("disabled");
	$("#invoice_number").removeAttr("disabled");
	$("#description").removeAttr("disabled");
	$("#num_of_lots").removeAttr("disabled");
	$("#num_of_items").removeAttr("disabled");
	$("#total_amt").removeAttr("disabled");
	$("#vat_percent").removeAttr("disabled");
	$("#discount_percent").removeAttr("disabled");
	$("#other_taxes").removeAttr("disabled");

	$("#stock_type_id").removeAttr("disabled");
	$("#number_per_set").removeAttr("disabled");
	$("#number_of_set").removeAttr("disabled");
	$("#item_price").removeAttr("disabled");
	
	$("#search_manufacturer_btn").removeAttr("disabled");
	$("#search_stock_type_btn").removeAttr("disabled");
	$("#invoice_submit_btn").removeAttr("disabled");
	$("#add_lot_btn").removeAttr("disabled");
}
function disableInvoiceForm() {
	$("#manufacturer_acc_num").attr("disabled", "disabled");
	$("#invoice_number").attr("disabled", "disabled");
	$("#description").attr("disabled", "disabled");
	$("#num_of_lots").attr("disabled", "disabled");
	$("#num_of_items").attr("disabled", "disabled");
	$("#total_amt").attr("disabled", "disabled");
	$("#vat_percent").attr("disabled", "disabled");
	$("#discount_percent").attr("disabled", "disabled");
	$("#other_taxes").attr("disabled", "disabled");

	$("#stock_type_id").attr("disabled", "disabled");
	$("#number_per_set").attr("disabled", "disabled");
	$("#number_of_set").attr("disabled", "disabled");
	$("#item_price").attr("disabled", "disabled");
	
	$("#search_manufacturer_btn").attr("disabled", "disabled");
	$("#search_stock_type_btn").attr("disabled", "disabled");
	$("#invoice_submit_btn").attr("disabled", "disabled");
	$("#add_lot_btn").attr("disabled", "disabled");
}