/**
 * 
 */
var prodCategoryItemIdSPC = "", selProdCategory;
var catLevel1Array = new Array(), catLevel2Array = new Array(), catLevel3Array = new Array(), catLevel4Array = new Array();
var categories = new Array();
$(document).ready(function() {
	$("#spc_category_1").change(firstCategoryChangeHandler);
	$("#spc_category_2").change(secondCategoryChangeHandler);
	$("#spc_category_3").change(thirdCategoryChangeHandler);
	$("#spc_category_4").change(fourthCategoryChangeHandler);
	$("#spc_ok_btn").click(selProdCategoryOkHandler);
});
//This function must be called from caller
function loadSelectProdCategoryDlg(prodCategoryItemId) {
	populateCategoryDropdowns();
	prodCategoryItemIdSPC = prodCategoryItemId;
}
function firstCategoryChangeHandler() {
    var firstLevelCategory = $("#spc_category_1 option:selected").text();
    populateCategoryDropdowns(firstLevelCategory);
}
function secondCategoryChangeHandler() {
    var firstLevelCategory = $("#spc_category_1 option:selected").text(),
        secondLevelCategory = $("#spc_category_2 option:selected").text();
    populateCategoryDropdowns(firstLevelCategory, secondLevelCategory);
}
function thirdCategoryChangeHandler() {
    var firstLevelCategory = $("#spc_category_1 option:selected").text(),
    	secondLevelCategory = $("#spc_category_2 option:selected").text(),
    	thirdLevelCategory = $("#spc_category_3 option:selected").text();
    populateCategoryDropdowns(firstLevelCategory, secondLevelCategory, thirdLevelCategory);
}
function fourthCategoryChangeHandler() {
	selProdCategory = $("#spc_category_4").val();
	$("#spc_product_category").html(selProdCategory);
}
function populateCategoryDropdowns(firstLevelCategory, secondLevelCategory, thirdLevelCategory, fourthLevelCategory) {
	var options1 = "", options2 = "", options3 = "", options4 = "",
		catTreeValues1 = productCategoryTree.catTreeValues;
	for(var i = 0; i < catTreeValues1.length; i++) {
		var selected1 = "";
		if (!firstLevelCategory)
			firstLevelCategory = catTreeValues1[0].catLevelName;
		if (catTreeValues1[i].catLevelName === firstLevelCategory) {
			selected1 = " selected";
			var catTreeValues2 = catTreeValues1[i].catTreeValues;
			for(var j = 0; j < catTreeValues2.length; j++) {
				var selected2 = "";
				if (!secondLevelCategory)
					secondLevelCategory = catTreeValues2[0].catLevelName;
				if (catTreeValues2[j].catLevelName === secondLevelCategory) {
					selected2 = " selected";
					var catTreeValues3 = catTreeValues2[j].catTreeValues;
					for(var k = 0;k < catTreeValues3.length; k++) {
						var selected3 = "";
						if (!thirdLevelCategory)
							thirdLevelCategory = catTreeValues3[0].catLevelName;
						if (catTreeValues3[k].catLevelName === thirdLevelCategory) {
							selected3 = " selected";
							var catTreeValues4 = catTreeValues3[k].catTreeValues;
							for(var l = 0; l < catTreeValues4.length; l++) {
								var prodCategory = catTreeValues4[l].productCategory;
								options4 += "<option value='" + prodCategory + "'>" + catTreeValues4[l].catLevelName + "</option>";
							}
						}
						options3 += "<option" + selected3 + ">" + catTreeValues3[k].catLevelName + "</option>";
					}
				}
				options2 += "<option" + selected2 + ">" + catTreeValues2[j].catLevelName + "</option>";
			}
		}
		options1 += "<option" + selected1 + ">" + catTreeValues1[i].catLevelName + "</option>";
	}
	$("#spc_category_1").html(options1);
	$("#spc_category_2").html(options2);
	$("#spc_category_3").html(options3);
	$("#spc_category_4").html(options4);
	
	fourthCategoryChangeHandler();
}
function insertIntoArrayIfNotExist(catlevelArray, catlevel) {
	for (var i = 0; i < catlevelArray.length; i++) {
		if (catlevelArray[i] === catlevel) {
			return catlevelArray;
		}
	}
	catlevelArray[catlevelArray.length] = catlevel;
	return catlevelArray;
}
function getHTMLContent(catlevelArray) {
	var content = "";
	for (var k = 0; k < catlevelArray.length; k++) {
	    content += " <option>" + catlevelArray[k] + "</option>";
	}
	return content;
}
function selProdCategoryOkHandler() {
	$("#" + prodCategoryItemIdSPC).val(selProdCategory);
}