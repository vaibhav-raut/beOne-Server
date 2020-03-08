/**
 * 
 */
var addTxnAccessVal, txTypes, paymentMode, txStatus, payModeHTMLForAddTxn = "";

$(document).ready(function() {
	addTxnAccessVal = $("#addTxnAccessVal").val();
	loadCommonProperties();
	loadPaymentModes();
	loadTxStatus();
	loadTxTypes();
	$("#lang").val(curLang);
});
function loadTxTypes() {
	ajaxCall("/enum/v1/tx_type", "GET", true, "", function(data, status) {
		if (data != "") {
			txTypes = new Array();
			var content = "<option>All</option>";
			for (var k = 0; k < data.enumValues.length; k++) {
				var txType = data.enumValues[k].txType;
				if (txType != "Compute Cumulative Saving"
						&& txType != "Loan Disbursement"
						&& txType != "Group Project Dev Fund"
						&& txType != "Bank Loan Disbursement"
						&& txType != "Fix Deposit Investment"
						&& txType != "Other Investment") {
					txTypes[txTypes.length] = data.enumValues[k];
					content += " <option>" + txType + "</option>";
				}
			}
			$("#track_txtype").html(content);
		}
	});
}
function loadPaymentModes() {
	ajaxCall("/enum/v1/enum_values/PaymentMode", "GET", true, "",
			function(data, status) {
				if (data != "") {
					paymentMode = data.enumValues;
					var content = "";
					for (var k = 0; k < paymentMode.length; k++) {
						var value = paymentMode[k].enumValue;
						content += "<option>" + value + "</option>";
						if(value != "OUTSTANDING" && value != "INTERNAL")
							payModeHTMLForAddTxn += "<option>" + value + "</option>";
					}
					$("#txn_paymode").html(payModeHTMLForAddTxn);
					$("#done_pay_mode").html(content);
					$("#undo_pay_mode").html(content);
					$("#approve_pay_mode").html(content);
					$("#track_paymode").html("<option>All</option>" + content);
				}
			});
}
function loadTxStatus() {
	ajaxCall("/enum/v1/enum_values/TxStatus", "GET", true, "", function(data,
			status) {
		if (data != "") {
			txStatus = data.enumValues;
			var content = "<option>All</option>";
			for (var k = 0; k < txStatus.length; k++) {
				content += " <option>" + txStatus[k].enumValue + "</option>";
			}
			$("#track_txstatus").html(content);
		}
	});
}
function isBankTypePaymentMode(payMode) {
	for (var k = 0; k < paymentMode.length; k++) {
		if(payMode == paymentMode[k].enumValue){
			return (paymentMode[k].description == "Bank");
		}
	}
	return false;
}