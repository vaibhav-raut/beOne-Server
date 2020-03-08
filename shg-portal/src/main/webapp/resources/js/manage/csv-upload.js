/**
 * 
 */
var selectedGroupCSV = null;
var searchedGroupCSV = null;

$(document).ready(function() {
	loadCSVUploadTab();
	$("#csv_upload_tab").click(loadCSVUploadTab);
	$("#s_grp_csv_btn").click(searchGroupCSV);
	$("#upload_grp_csv_btn").click(uploadGroupCSV);
	$("#reset_grp_csv_btn").click(resetGroupCSV);
	$("#upload_member_csv_btn").click(uploadMemberCSV);
	$("#reset_member_csv_btn").click(resetMemberCSV);
	$("#csv_sel_district_btn").click(selectDistrictForCSVUpload);
	$("#m_csv_print").click(printTable);
	$("#m_csv_pdf").click(printTable);
	$("#m_csv_excel").click(genExcel);
});
function loadCSVUploadTab() {
	ajaxCall("/group/v1/csv_update_types", "GET", true, "", function(data, status) {
		var content = "";
		for (var i = 0; i < data.length; i++) {
			content += "<option>" + data[i] + "</option>";
		}
		$("#csv_update_type").html(content);
	});
}
function uploadGroupCSV() {
	var districtName = $("#district_name").val();
	if(districtName == "") {
		$("#g_csv_msg").html("Please select a district.");
		return;
	}
	var file = $("#group_csv_file")[0].files[0];
	if(file == null) {
		$("#g_csv_msg").html("Please choose a group CSV file.");
	} else if(file.name.substring(file.name.length - 4, file.name.length) != ".csv") {
		$("#g_csv_msg").html("Please choose a correct CSV file with .csv extension.");
	} else {
		$("#upload_grp_csv_btn").attr("disabled","disabled");
		$("#reset_grp_csv_btn").attr("disabled","disabled");
		$("#g_csv_msg").html("<img src='resources/img/ajax-loader.gif' alt=''/> Please wait, uploading data...");
		
	    var fd = new FormData();
	    fd.append("file", file);
	    
		$.ajax({
			url : (appBaseURL + "/group/v1/add_groups_csv/" + districtName),
			headers : authReqHeader,
			type : "PUT",
			data : fd,
			processData: false,
			contentType: false,
	        success: function(data, status) {
	    		$("#g_csv_msg").html("Group CSV file uploaded succesfully");

	    		var tablecontent = "<tr><td><strong>District Code</strong></td><td><strong>Group Name</strong></td><td><strong>Account Number</strong></td></tr>";
				tablecontent += "<tr><td>" + data[0].districtCode + "</td>";
				tablecontent += "<td>" + data[0].groupName + "</td>";
				tablecontent += "<td>" + getReadableAccNumForDistrict(data[0].districtCode, data[0].groupAcNo) + "</td></tr>";

				$("#grp_upload_result").html(tablecontent);
	    		$("#upload_grp_csv_btn").removeAttr("disabled");
	    		$("#reset_grp_csv_btn").removeAttr("disabled");
	        },
	        error: function(xhr) {
	    		$("#upload_grp_csv_btn").removeAttr("disabled");
	    		$("#reset_grp_csv_btn").removeAttr("disabled");
	    		handleErrorAndDisplayMsg(xhr, "g_csv_msg");
	        }
	     });
	}
}
function resetGroupCSV() {
	$("#group_csv_file").val("");
	$("#g_csv_msg").html("Please select district and upload group CSV file to create group.");
	$("#grp_upload_result").html("");
	$("#upload_grp_csv_btn").removeAttr("disabled");
}
function selectDistrictForCSVUpload() {
	loadSelectDistrictDialog("state_name", "district_name");
}
function searchGroupCSV() {
	var district = $("#dcode_csv").val();
	var grpName = $("#groupname_csv").val();
	var grpId = $("#groupid_csv").val();
	if (district == "" || (grpName == "" && grpId == ""))
		groupSearchIncompleteDataError();
	else {
		groupSearching();
		var payload = "{\"memberAcNo\":" + curMemberAcNo + ",\"searchDistrictCode\":\"" + district + "\"";
		if (grpId != "") {
			payload +=  ",\"searchGroupNo\":" + grpId + "}";
			ajaxCall("/auth/v1/search_group_no_in_district/", "POST", true, payload, groupSearchSuccessCSV, groupSearchFailed);
		} else if (grpName != "") {
			payload +=  ",\"searchGroupNameShort\":\"" + grpName + "\"}";
			ajaxCall("/auth/v1/search_group_by_name_in_district/", "POST", true, payload, groupSearchSuccessCSV, groupSearchFailed);
		}
	}
}
function groupSearchSuccessCSV(data, status) {
	searchedGroupCSV = data.foundGroups;
	loadGroupSearchData(searchedGroupCSV);

	for (var i = 0; i < searchedGroupCSV.length; i++) {
		$("#group_" + i).click(loadselectedGroupCSV);
	}
}
function loadselectedGroupCSV() {
	hideGroupSearchDialog();
	var index = parseInt($(this).data("id"));
	selectedGroupCSV = searchedGroupCSV[index];
	$("#group_detail_csv").html(selectedGroupCSV.groupName + " ("
			+ getReadableAccNumForDistrict(selectedGroupCSV.districtCode, selectedGroupCSV.groupAcNo) + ")");
	searchedGroupCSV = null;
}
function uploadMemberCSV() {
	if(selectedGroupCSV == null) {
		$("#m_csv_msg").html("Please search group first.");
		return;
	}
	var file = $('#member_csv_file')[0].files[0];
	if(file == null) {
		$("#m_csv_msg").html("Please choose a member CSV file.");
	} else if(file.name.substring(file.name.length - 4, file.name.length) != ".csv") {
		$("#m_csv_msg").html("Please choose a correct CSV file with .csv extension.");
	} else {
		$("#upload_member_csv_btn").attr("disabled","disabled");
		$("#reset_member_csv_btn").attr("disabled","disabled");
		$("#s_grp_csv_btn").attr("disabled","disabled");
		$("#m_csv_msg").html("<img src='resources/img/ajax-loader.gif' alt=''/> Please wait, uploading data... It may take few minutes.");

		var csvUpdateType = $("#csv_update_type option:selected").text();
	    var fd = new FormData();
	    fd.append("file", file);
	    
		$.ajax({
			url : (appBaseURL + "/group/v1/import_group_csv_data/" + selectedGroupCSV.groupAcNo + "/" + csvUpdateType),
			headers : authReqHeader,
			type : "PUT",
			data : fd,
			processData: false,
			contentType: false,
	        success: function(data, status) {
	        	$("#m_csv_msg").html("CSV file uploaded successfully");
	        	
				var csvUpdateType = $("#csv_update_type option:selected").text();
	        	var tablecontent = "<tr><td colspan='5' align='center'><strong>" + selectedGroupCSV.groupName;
				tablecontent += " (" + getReadableAccNumForDistrict(selectedGroupCSV.districtCode, selectedGroupCSV.groupAcNo) + ")</strong></td></tr>";
				tablecontent += "<tr><td colspan='5' align='center'>" + data.length + " entries added for " + csvUpdateType + "</td></tr>";
				
				if(data.length > 0 && csvUpdateType == "Add Members") {
					tablecontent += "<tr><td><strong>Account Number</strong></td><td><strong>Member Name</strong></td><td><strong>Status</strong></td><td><strong>Role</strong></td><td><strong>Password</strong></td></tr>";
					for (var i = 0; i < data.length; i++) {
						tablecontent += "<tr><td>" + getReadableAccNumForDistrict(selectedGroupCSV.districtCode, data[i].memberAcNo) + "</td>";
						tablecontent += "<td>" + data[i].name + "</td>";
						tablecontent += "<td>" + data[i].activeStatus + "</td>";
						tablecontent += "<td>" + data[i].mrole + "</td>";
						tablecontent += "<td>" + data[i].password + "</td></tr>";
					}
				}
				$("#member_upload_result").html(tablecontent);
	    		$("#upload_member_csv_btn").removeAttr("disabled");
	    		$("#reset_member_csv_btn").removeAttr("disabled");
	    		$("#s_grp_csv_btn").removeAttr("disabled");
	        },
	        error: function(xhr) {
	    		$("#upload_member_csv_btn").removeAttr("disabled");
	    		$("#reset_member_csv_btn").removeAttr("disabled");
	    		$("#s_grp_csv_btn").removeAttr("disabled");
	    		handleErrorAndDisplayMsg(xhr, "m_csv_msg");
	        }
	     });
	}
}
function resetMemberCSV() {
	selectedGroupCSV = null;
	$("#dcode_csv").val("");
	$("#groupname_csv").val("");
	$("#groupid_csv").val("");
	$("#member_csv_file").val("");
	$("#group_detail_csv").html("No group selected");
	$("#m_csv_msg").html("Please search a group and upload member CSV file to create members within that group.");
	$("#member_upload_result").html("");
	$("#upload_member_csv_btn").removeAttr("disabled");
	$("#s_grp_csv_btn").removeAttr("disabled");
}
