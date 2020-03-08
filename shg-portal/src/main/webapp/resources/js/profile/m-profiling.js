/**
 * 
 */
var mprofilingTypeCategories = new Array(), profilingPointsMap = {};
$(document).ready(function() {
	loadMProfilingType();
	$("#p_mprofiling_btn").click(loadMProfiling);
	$("#mp_update_btn").click(updateMProfiling);
	$("#mp_print").click(printTable);
	$("#mp_pdf").click(printTable);
	$("#mp_excel").click(genExcel);
});
function loadMProfilingType() {
	ajaxCall("/enum/v1/mprofiling_type_for/Member", "GET", true, "",
			function(data, status) {
				mprofilingTypeCategories = data.enumValues;
			});
}
function loadMProfiling() {
	if (mProfile === null) {
		$("#mp_update_btn").attr("disabled", "disabled");
		$("#mp_acc_num").html("--");
		$("#mp_name").html("--");
		$("#mp_role").html("--");
		$("#mprofiling_table").html("<tr><td align='center'>Profiling data is not available.</td></tr>");
	} else {
		$("#mp_acc_num").html(getReadableAccNumForDistrict(gdCode, mProfile.memberAcNo));
		$("#mp_name").html(mProfile.memberName);
		$("#mp_role").html(mProfile.mrole);
		
		var content = "<tr><td align='center'>Loading profiling data... <img src='resources/img/ajax-loader.gif' alt=''/></td></tr>";
		$("#mprofiling_table").html(content);

		ajaxCall("/profile/v1/mprofiling_data/" + mProfile.memberAcNo, "GET", true, "",
				function(data, status) {
					convertToProfilingPointsMap(data.points);
					displayMProfilingPoints();
				},
				function(xhr) {
					$("#mprofiling_table").html("<tr><td align='center'>Profiling data is not available.</td></tr>");
					showErrorMsgMProfiling("");
					handleErrorAndDisplayMsg(xhr, "mp_err_msg");
				});
	}
}
function convertToProfilingPointsMap(mprofilingPoints) {
	profilingPointsMap = {};
	for (var i = 0; i < mprofilingPoints.length; i++) {
		profilingPointsMap[mprofilingPoints[i].pointId] = mprofilingPoints[i];
	}
}
function displayMProfilingPoints() {
	var content = "<tr><td><b>Category</b></td><td><b>Point</b></td><td><b>Current Value</b></td><td><b>New Value</b></td></tr>";
	var dataIdIndex = 1;
	for (var index = 0; index < mprofilingTypeCategories.length; index++) {
		content += "<tr data-tt-id=" + dataIdIndex + "><td hidden='true'></td><td style='color: #098dc7; font-weight: bold' colspan='4'>" + mprofilingTypeCategories[index].category + "</td></tr>";
		var parentIdIndex = dataIdIndex;
		dataIdIndex++;
		for (var i = 0; i < mprofilingTypeCategories[index].enumValues.length; i++) {
			var profilingType = mprofilingTypeCategories[index].enumValues[i];
			
			content += "<tr data-tt-id=" + dataIdIndex + " data-tt-parent-id=" + parentIdIndex + "><td hidden='true'></td><td>";
			content += "</td><td>" + profilingType.point + "</td><td>";
			
			var profilingPoint = profilingPointsMap[profilingType.pointId];
			var valType = profilingType.valueType;
			if (valType === "single_option" || valType === "multi_option") {
				var inputContent = "<td width='300'><select class='form-control' title='Select new value' id='" + profilingType.pointId + "'";
				var options = profilingType.options;
				if (valType === "multi_option")
					inputContent += " multiple";
				inputContent += "><option></option>";
				
				if (options !== null) {
					var optionIds = null;
					if(profilingPoint)
						optionIds = profilingPoint.options;
					for (var j = 0; j < options.length; j++) {
						var isSelected = false;
						if(optionIds) {
							for (var k = 0; k < optionIds.length; k++) {
								if(optionIds[k] === options[j].optionId) {
									isSelected = true;
									break;
								}
							}
						}
						inputContent += "<option value='" + options[j].optionId + "'";
						if(isSelected) {
							inputContent += " selected";
							content += options[j].option + "<br>";
						}
						inputContent += ">" + options[j].option + "</option>";
					}
				}
				content += inputContent + "</select>";
			} else {
				var value = profilingPoint ? profilingPoint.value : "";
				content += value + "</td><td width='300'><input type='text' class='form-control' title='Enter new value' value='" + value + "' id='" + profilingType.pointId + "'>";
			}
			content += "</td></tr>";
			dataIdIndex++;
		}
	}
	$("#mprofiling_table").html(content);
    $("#mprofiling_table").agikiTreeTable({
        persist : true,
        persistStoreName : "files"
    });
	$("#mp_update_btn").removeAttr("disabled");
}
function updateMProfiling() {
	var mprofilingPoints = new Array();;
	for (var i = 0; i < mprofilingTypeCategories.length; i++) {
		for (var j = 0; j < mprofilingTypeCategories[i].enumValues.length; j++) {
			var profilingType = mprofilingTypeCategories[i].enumValues[j];
			var value = $("#" + profilingType.pointId).val();
			if(value && value != "") {
				var point = {};
				point["pointId"] = profilingType.pointId;
				var valType = profilingType.valueType;
				if (valType === "single_option" || valType === "multi_option") {
					point["options"] = (value + "").split(",");
				} else {
					point["value"] = value;
				}
				point["valueType"] = valType;
				
				mprofilingPoints[mprofilingPoints.length] = point;
			}
		}
	}
	var mprofilingData = {};
	mprofilingData["memberAcNo"] = mProfile.memberAcNo;
	mprofilingData["points"] = mprofilingPoints;
	ajaxCall("/profile/v1/update_mprofiling_data/",
			"POST",
			true,
			JSON.stringify(mprofilingData),
			function(data, status) {
				showMsgMProfiling("Profiling data updated successfully.");
				convertToProfilingPointsMap(data.points);
				displayMProfilingPoints();
			},
			function(xhr) {
				showErrorMsgMProfiling("");
				handleErrorAndDisplayMsg(xhr, "mp_err_msg");
			});
}
function showMsgMProfiling(msg) {
	$("#mp_msg").html(msg);
	$("#mp_err_msg").hide();
	$("#mp_msg").show();
}
function showErrorMsgMProfiling(msg) {
	$("#mp_err_msg").html(msg);
	$("#mp_msg").hide();
	$("#mp_err_msg").show();
}