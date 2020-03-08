/**
 * 
 */
$(document).ready(function() {
	loadMrCSVUploadTab();
	$("#csv_upload_tab").click(loadMrCSVUploadTab);
	$("#upload_csv_btn").click(uploadCSV);
	$("#reset_csv_btn").click(resetCSV);
	$("#csv_print").click(printTable);
	$("#csv_pdf").click(printTable);
	$("#csv_excel").click(genExcel);
});
function loadMrCSVUploadTab() {
	ajaxCall("/mr/manage_data/v1/get_mr_csv_types", "GET", true, "", function(data, status) {
		var content = "";
		for (var i = 0; i < data.length; i++) {
			content += "<option>" + data[i] + "</option>";
		}
		$("#csv_type").html(content);
	});
}
function uploadCSV() {
	var file = $('#csv_file')[0].files[0];
	if(file == null) {
		$("#csv_upload_msg").html("Please choose a CSV file.");
	} else if(file.name.substring(file.name.length - 4, file.name.length) != ".csv") {
		$("#csv_upload_msg").html("Please choose a correct CSV file with .csv extension.");
	} else {
		$("#upload_csv_btn").attr("disabled","disabled");
		$("#reset_csv_btn").attr("disabled","disabled");
		$("#csv_upload_msg").html("<img src='resources/img/ajax-loader.gif' alt=''/> Please wait, uploading data... It may take few minutes.");

		var csvUpdateType = $("#csv_type option:selected").text();
	    var fd = new FormData();
	    fd.append("file", file);
	    
		$.ajax({
			url : (appBaseURL + "/mr/manage_data/v1/add_mr_csv_for_type/" + csvUpdateType),
			headers : authReqHeader,
			type : "PUT",
			data : fd,
			processData: false,
			contentType: false,
	        success: function(data, status) {
	        	$("#csv_upload_msg").html("CSV file uploaded successfully");
	        	
	    		$("#upload_csv_btn").removeAttr("disabled");
	    		$("#reset_csv_btn").removeAttr("disabled");
	        },
	        error: function(xhr) {
	    		$("#upload_csv_btn").removeAttr("disabled");
	    		$("#reset_csv_btn").removeAttr("disabled");
	    		handleErrorAndDisplayMsg(xhr, "csv_upload_msg");
	        }
	     });
	}
}
function resetCSV() {
	selectedGroupCSV = null;
	$("#csv_file").val("");
	$("#csv_upload_msg").html("");
	$("#csv_upload_result").html("");
	$("#upload_csv_btn").removeAttr("disabled");
}
