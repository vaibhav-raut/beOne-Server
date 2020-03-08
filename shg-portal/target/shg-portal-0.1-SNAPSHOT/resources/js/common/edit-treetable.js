/**
 * 
 */
var inputFieldIds = null;	// contains list of {"id":"input-field-id", "type":"data-type"}
function loadRecordTree(records, tableId, isEdit) {
	if(!isEdit)
		isEdit = false;
    inputFieldIds = new Array();

    var content = getHtmlDetails("", records, "", isEdit);
    $("#" + tableId).html(content);
    $("#" + tableId).agikiTreeTable({
        persist : true,
        persistStoreName : "files"
    });

    if(isEdit)
    	registerListeners();
}
function registerListeners() {
	for (var i = 0; i < inputFieldIds.length; i++) {
	    if (inputFieldIds[i].type === "int" || inputFieldIds[i].type === "float")
	        $("#" + inputFieldIds[i].id).change(numberFieldChangeHandler);
	}
}
var dataIndex = 1;
function getHtmlDetails(parentIdIndex, details, id, isEdit) {
    var content = "";
    if (details != null) {
        for (var k = 0; k < details.length; k++) {
            var myIdIndex = dataIndex++;

            content += getDetailAsHTMLRow(parentIdIndex, myIdIndex, details[k], id + k, isEdit);

            content += getHtmlDetails(myIdIndex, details[k].details, id + k, isEdit);
        }
    }
    return content;
}
function getDetailAsHTMLRow(parentIdIndex, myIdIndex, detail, id, isEdit) {
    var parentHasChildren = (detail.details && detail.details.length > 0);
    var style = "";
    if(parentHasChildren)
        style= "color: #098dc7; font-weight: bold";

    var content = "<tr style='" + style + "' data-tt-id=" + myIdIndex + " data-tt-parent-id="
        + parentIdIndex + "><td hidden='true'></td><td>" + detail.displayName + "</td>";

    if(!isEdit) {
	    content += "<td class='bluetext'>";
	    if(detail.type === "bool")
	        content += ((detail.value === "0") ? "No" : "Yes");
	    else
	        content += detail.value;
	    content += "</td>";
    } else {
	    content += "<td width='120'>";
	    if(detail.type !== "None") {
	        if(detail.type === "bool") {
	            content += "<select class='form-control' id='" + id + "' title='Select new value'><option value='1' "
	            		+ ((detail.value === "1") ? "selected='selected'" : "") + ">Yes</option><option value='0' "
	            		+ ((detail.value === "0") ? "selected='selected'" : "") + ">No</option></select>";
	        } else {
	            content += "<input type='text' class='form-control' id='" + id + "' title='Enter new value' value='" + detail.value + "'>";
	        }

		    // used while registering listeners
	        var idAndType = {};
	        idAndType["id"] = id;
	        idAndType["type"] = detail.type;
	        inputFieldIds[inputFieldIds.length] = idAndType;
	    }
	    content += "</td>";
    }
    content += "</tr>";

    return content;
}
function getUpdatedRecordTree(records) {
    readUpdatedRecordValue(records, "");
    return records;
}
function readUpdatedRecordValue(details, id) {
    if (details != null) {
        for (var k = 0; k < details.length; k++) {
    	    if(details[k].type !== "None") {
    	    	details[k].value = $("#" + id + k).val();
    	    }
            readUpdatedRecordValue(details[k].details, id + k);
        }
    }
}