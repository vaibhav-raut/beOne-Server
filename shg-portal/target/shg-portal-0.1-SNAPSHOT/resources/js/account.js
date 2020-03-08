/**
 * 
 */
var addTxnAccessVal = null;
$(document).ready(function() {
	addTxnAccessVal = $("#addTxnAccessVal").val();
	loadCommonProperties();
	loadPaymentModes();
	$("#lang").val(curLang);
});
function loadPaymentModes() {
	ajaxCall("/enum/v1/enum_values/PaymentMode", "GET", true, "", function(
			data, status) {
		var paymentMode = data.enumValues;
		var content = "";
		for (var k = 0; k < paymentMode.length; k++) {
			content += " <option>" + paymentMode[k].enumValue + "</option>";
		}
		$("#approve_pay_mode").html(content);
	});
}
