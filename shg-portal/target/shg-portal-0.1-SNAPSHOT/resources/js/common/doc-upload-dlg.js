/**
 * 
 */
$(document).ready(function() {
	$("#doc_upload_category").change(docCategoryChangeHandler);
	$("#doc_upload_btn").click(uploadDocument);
	$("#doc_upload_btn1").click(updateUploadedDocument);
	$("#doc_upload_dlg_cancel_btn").click(resetDocUploadDlgData);
});
function getHTMLForDocCategory(docFor) {
	var html = "";
	for (var i = 0; i < docTypes.length; i++) {
		if (docTypes[i].docFor.search(docFor) != -1) {
			 var docCat = docTypes[i].docCategory.split(":");
			 for (var j = 0; j < docCat.length; j++) {
				if(html.search(docCat[j]) == -1)
					html += "<option>" + docCat[j] + "</option>";
			}
		}
	}
	return html;
}
function getHTMLForDocType(docFor, docCategory) {
	var html = "";
	for (var i = 0; i < docTypes.length; i++) {
		if (docTypes[i].docFor.search(docFor) != -1
				&& docTypes[i].docCategory.search(docCategory) != -1
				&& html.search(docTypes[i].docType) == -1) {
			html += "<option value = " + docTypes[i].docTypeId + ">" + docTypes[i].docType + "</option>";
		}
	}
	return html;
}
function showDocUploadMsg(msg) {
	$("#doc_upload_errmsg").html("");
	$("#doc_upload_errmsg_row").hide();
	if(msg == "") {
		$("#doc_upload_msg_row").hide();
	} else {
		$("#doc_upload_msg").html(msg);
		$("#doc_upload_msg_row").show();
	}
}
function showDocUploadErrMsg(errmsg, hideContent) {
	if (hideContent)
		resetDocUploadDlgData();
	$("#doc_upload_msg").html("");
	$("#doc_upload_msg_row").hide();
	if(errmsg == "") {
		$("#doc_upload_errmsg_row").hide();
	} else {
		$("#doc_upload_errmsg").html(errmsg);
		$("#doc_upload_errmsg_row").show();
	}
}
var uploadURI, successHandler;
function loadDocumentUploadDlg(docFor, uri, handler) {
	uploadURI = uri;
	successHandler = handler;
	$("#doc_upload_dlg_content").show();
	$("#doc_upload_dlg_header").html("Upload New Document");
	$("#doc_upload_file").val("");
	$("#upload_doc_for").val(docFor);
	$("#doc_upload_category").html(getHTMLForDocCategory(docFor));
	docCategoryChangeHandler();
	showDocUploadMsg("");
	$("#doc_upload_btn1").hide();
	$("#doc_upload_btn").show();
}
function loadEditUploadedDocumentDlg(docFor, uri, attachment, handler) {
	uploadURI = uri;
	successHandler = handler;
	$("#doc_upload_dlg_content").show();
	$("#doc_upload_dlg_header").html("Update Document");
	$("#doc_upload_file").val("");
	$("#upload_doc_for").val(docFor);
	$("#doc_upload_category").html(getHTMLForDocCategory(docFor));
	$("#doc_upload_category").val(attachment.docType.docCategory);
	docCategoryChangeHandler();
	$("#doc_upload_type").val(attachment.docType.docTypeId);
	showDocUploadMsg("Please upload another file to replace " + attachment.fileName);
	$("#doc_upload_btn").hide();
	$("#doc_upload_btn1").show();
}
function docCategoryChangeHandler() {
	var docCategory = $("#doc_upload_category option:selected").text();
	$("#doc_upload_type").html(getHTMLForDocType($("#upload_doc_for").val(), docCategory));
}
function uploadDocument() {
	var docType = $("#doc_upload_type").val();
	var file = $("#doc_upload_file")[0].files[0];
	if (docType == "") {
		showDocUploadErrMsg("Please select document type to upload.");
		$("#doc_upload_type").focus();
	} else if (file == null) {
		showDocUploadErrMsg("Please choose a file to upload.");
		$("#doc_upload_file").focus();
	} else {
		$("#doc_upload_btn").attr("disabled","disabled");
		showDocUploadMsg("<img src='resources/img/ajax-loader.gif' alt=''/> Uploading file...");

	    var fd = new FormData();
	    fd.append("file", file);
		$.ajax({
			url : uploadURI + "/" + docType,
			headers : authReqHeader,
			type : "PUT",
			data : fd,
			processData: false,
			contentType: false,
	        success: function(data, status) {
	        	showDocUploadMsg(data[0].fileName + " uploaded successfully.");
	    		$("#doc_upload_btn").removeAttr("disabled");
	    		successHandler(data[0]);
	        },
	        error: function(xhr) {
	    		$("#doc_upload_btn").removeAttr("disabled");
	    		showDocUploadErrMsg("Error occured.");
	    		handleErrorAndDisplayMsg(xhr, "doc_upload_errmsg");
	        }
	     });
	}
}
function updateUploadedDocument() {
	var file = $("#doc_upload_file")[0].files[0];
	if(file == null) {
		showDocUploadErrMsg("Please choose a file to upload.");
		$("#doc_upload_file").focus();
	} else {
		$("#doc_upload_btn1").attr("disabled","disabled");
		showDocUploadMsg("<img src='resources/img/ajax-loader.gif' alt=''/> Uploading file...");

	    var fd = new FormData();
	    fd.append("file", file);
		$.ajax({
			url : uploadURI + "/" + $("#doc_upload_type").val(),
			headers : authReqHeader,
			type : "POST",
			data : fd,
			processData: false,
			contentType: false,
	        success: function(data, status) {
	        	showDocUploadMsg(data[0].fileName + " uploaded successfully.");
	    		$("#doc_upload_btn1").removeAttr("disabled");
	    		successHandler(data[0]);
	        },
	        error: function(xhr) {
	    		$("#doc_upload_btn1").removeAttr("disabled");
	    		showDocUploadErrMsg("Error occured.");
	    		handleErrorAndDisplayMsg(xhr, "doc_upload_errmsg");
	        }
	     });
	}
}
function resetDocUploadDlgData() {
	uploadURI = null;
	successHandler = null;
	$("#doc_upload_dlg_content").hide();
	$("#doc_upload_file").val("");
	$("#upload_doc_for").val("");
	$("#doc_upload_category").html("");
	$("#doc_upload_type").html("");
	$("#doc_upload_btn").hide();
	$("#doc_upload_btn1").hide();
}