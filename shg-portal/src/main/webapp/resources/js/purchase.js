/**
 * 
 */
var productCategoryTree = 
$(document).ready(function() {
	loadCommonProperties();
	$("#lang").val(curLang);
	loadProductCategory();
});
function loadProductCategory() {
	ajaxCall("/mr/enum/v1/product_category_tree", "GET", true, "", function(data,
			status) {
			productCategoryTree = data;
	});
}