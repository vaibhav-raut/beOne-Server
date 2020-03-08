/**
 * Common Java Script for all pages
 */
var groupAcProfile = null,
	groupBankAcNos = new Array(),
	groupRules = null,
	states = new Array(),
	districtValues = new Array(),
	docTypes = new Array();
var READ_ACCESS_VAL = 1, UPDATE_ACCESS_VAL = 2, APPROVE_ACCESS_VAL = 3; 

var maxPDFWidth = 210, maxPDFHeight = 300;
var logoUrl = "resources/img/shg-one.jpg", logoImgData = null;
var watermarkUrl = "resources/img/shg-one-logo-watermark-560x560.jpg", watermarkImgData = null;

$(document).ready(function() {
	loadImageData(logoUrl, function(imgData) {
		logoImgData = imgData;
	});
	loadImageData(watermarkUrl, function(imgData) {
		watermarkImgData = imgData;
	});
	$("#dialog_btn").click(hideMessage);
});
var appBaseURL = "/shg-ws";
function ajaxCall(url, type, async, data, successHandler, errorHandler) {
	if (!async)
		async = true;
	$.ajax({
		url : (appBaseURL + url),
		headers : authReqHeader,
		type : type,
		data : data,
		contentType : "application/json",
		dataType : "json",
		async : async,
		success : successHandler,
		error : errorHandler
	});
}
// This function will logout an user for any error other then 400 and
// display proper error message at given html tag.
function handleErrorAndDisplayMsg(xhr, errMsgHtmlTag) {
	if(xhr.status != 400) {
       window.location.href = "/shg-portal/500";
	} else {
		var errMsg = "";
		try {
			// Error message is of "<Exception-type>: <Actual-message-string>" type.
			errMsg = JSON.parse(xhr.responseText).error.message;
			errMsg = errMsg.substring(errMsg.indexOf(":") + 2, errMsg.length);
		} catch (e) {
			errMsg = xhr.statusText;
		}
		if(errMsgHtmlTag.search("dialog_table_body") != -1)
			errMsg = "<tr><td align='center' style='color: red'>" + errMsg + "</td></tr>";
		$("#" + errMsgHtmlTag).html(errMsg);
	}
}

var currentServerTime, curMemberAcNo, mdCode, mdId, groupAcNo, gType, gdCode, gdId, curLang, mrole, roleCategory, authToken, authReqHeader;
function loadCommonProperties() {
	currentServerTime = parseInt($("#currentServerTime").val());
	curMemberAcNo = $("#memberAcNo").val();
	mdCode = $("#mdCode").val();
	mdId = $("#mdId").val();
	groupAcNo = $("#groupAcNo").val();
	gType = $("#gType").val();
	gdCode = $("#gdCode").val();
	gdId = $("#gdId").val();
	curLang = $("#curLang").val();
	mrole = $("#mRole").val();
	roleCategory = $("#roleCategory").val();
	authToken = $("#authToken").val();
	authReqHeader = JSON.parse("{\"AUTH-Token\":\"" + authToken
			+ "\",\"MEM-Ac-No\":" + curMemberAcNo + ",\"SEL-GRP-Ac-No\":"
			+ groupAcNo + "}");
	if (gType == "SHG") {
		loadDBStats();
		// load Group Rules
		ajaxCall("/group/v1/group_rules/" + $("#lang option:selected").text()
				+ "/" + groupAcNo, "GET", true, "", function(data, status) {
			groupRules = data;
		});
	} else {
		$("#memberCount").html("-");
		$("#monthlySaving").html("- <i class='fa fa-inr' style='size:50px'>");
		$("#currentBalance").html("- <i class='fa fa-inr' style='size:50px'>");
		$("#totalSaving").html("- <i class='fa fa-inr' style='size:50px'>");
	}
	if (districtValues.length == 0)
		loadDistricts();
	if (groupAcProfile == null)
		getGroupAccountProfile();
	loadDocTypes();
}
function loadDBStats() {
	ajaxCall(
			"/dashboard/v1/group_dashboard/"
					+ $("#lang option:selected").text() + "/" + groupAcNo,
			"GET",
			true,
			"",
			function(data, status) {
				if (data != "") {
					$("#memberCount").html(data.memberCount);
					$("#monthlySaving")
							.html(
									data.monthlySaving
											+ " <i class='fa fa-inr' style='size:50px'>");
					$("#currentBalance")
							.html(
									data.currentBalance
											+ " <i class='fa fa-inr' style='size:50px'>");
					$("#totalSaving")
							.html(
									data.totalSaving
											+ " <i class='fa fa-inr' style='size:50px'>");
				}
			}, function(xhr, errLocation) {
				$("#memberCount").html("-");
				$("#monthlySaving").html(
						"- <i class='fa fa-inr' style='size:50px'>");
				$("#currentBalance").html(
						"- <i class='fa fa-inr' style='size:50px'>");
				$("#totalSaving").html(
						"- <i class='fa fa-inr' style='size:50px'>");
			});
}
function getBankAccFromDisplayName(bankAcNos, displayName) {
	if (bankAcNos != null) {
		for (var i = 0; i < bankAcNos.length; i++) {
			if (bankAcNos[i].displayAccount == displayName)
				return bankAcNos[i].bankAcNo;
		}
	}
	return 0;
}
function getAlphabet(index) {
	var alphabet = [ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
			"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
			"y", "z" ];
	return alphabet[index - 1];
}
function isDateDDMMYYYYFormatted(dateString) {
	var dateSplited = dateString.split("/");
	if (dateSplited.length != 3)
		return false;
	if (dateSplited[0] < 0 || dateSplited[0] > 31)
		return false;
	if (dateSplited[1] < 0 || dateSplited[1] > 12)
		return false;
	return true;
}
function convertMonth2TS(monthYrString) {
	var month = ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"];
	return 0;
}
function convertDDMMYYYYDate2TS(dateString) {
	var dateSplited = dateString.split("/");
	return (new Date(dateSplited[2], dateSplited[1] - 1, dateSplited[0]))
			.getTime();
}
function convertTS2DDMMYYYYDate(dateTS) {
	var dateString = "";
	if(dateTS != 0) {
		var date = new Date(dateTS);
		if (date.getDate() < 10)
			dateString += "0";
		dateString += date.getDate() + "/";
		if (date.getMonth() < 9)
			dateString += "0";
		dateString += (date.getMonth() + 1) + "/" + date.getFullYear();
	}
	return dateString;
}
function getReadableAccNumForDistrict(gdistCode, accNum) {
	var formattedAccNum = gdistCode + "-";
	var accStr = "" + accNum;
	var size = accStr.length;
	if (size < 9) {
		formattedAccNum += accStr.substring(size - 5, size);
	} else {
		formattedAccNum += accStr.substring(size - 9, size - 4);
		formattedAccNum += "-";
		formattedAccNum += accStr.substring(size - 4, size);
	}
	return formattedAccNum;
}
function getReadableAccNum(accNum) {
	return getReadableAccNumForDistrict(gdCode, accNum);
}
function getAccNumFromFormattedAcc(formattedAccNum) {
	var accNoSplitted = formattedAccNum.split("-");
	if (accNoSplitted.length == 3) {
		var accNum = gdId + accNoSplitted[1] + accNoSplitted[2];
		if (!isNaN(accNum))
			return parseInt(accNum);
	}
	return null;
}
function showMessage(msg, calledFromId) {
	$("#from_id").val(calledFromId);
	$("#dialog_table_header").html("Message");
	$("#dialog_table_body").html("<tr><td align='center'>" + msg + "</td></tr>");
	$("#dialog_btn").html("OK");
	$("#modal_dialog").modal("show");
}
function hideMessage() {
	$("#modal_dialog").modal("hide");
	$("#dialog_table_body").html("");
	$("#dialog_btn").html("Cancel");
	var calledFromId = $("#from_id").val();
	$("#from_id").val("");
	$("#" + calledFromId).focus();
}
function numberFieldChangeHandler() {
	var fieldVal = $(this).val();
	if (fieldVal != "") {
		if (isNaN(fieldVal)) {
			showMessage("Please enter only numbers.", $(this).attr("id"));
			$(this).val("");
			$(this).focus();
		} else {
			$(this).val(new Number(fieldVal));
		}
	}
}
function generateexcel(tableId, title) {
	var blob = new Blob(
			[ document.getElementById(tableId).outerHTML ],
			{
				type : "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
			});
	saveAs(blob, title + ".xls");
};
function saveAs(blob, filename) {
	var a = document.createElement('a');
	a.href = window.URL.createObjectURL(blob);
	a.download = filename;
	a.click();
}

var pageStyleForPrinting = "<style>body {font-family:times, serif; background-image:url(resources/img/shg-one-logo-watermark-380x380.jpg); background-repeat: no-repeat; background-position: center;} " +
				"p {text-align:center;} table {font-size:10pt; width: 100%;} th {border-top: 1px solid black; border-bottom: 1px solid black;} th, td {padding: 8px;}</style>";
function getHeaderForPrintPage(title) {
	return document.head.innerHTML.replace("<title>SHG-One.net</title>", "<title>" + title + "</title>");
}
function getGroupInfoTableContent() {
    var groupInfoTableContent = "<table><tr><td>Group Account Number:"
        + "</td><td style='font-style:italic'><b>"
        + getReadableAccNum(groupAcProfile.groupAcNo)
        + "</b></td></tr><tr><td>Date Of Establishment:"
        + "</td><td style='font-style:italic'><b>"
        + groupAcProfile.dateOfEstablishment
        + "</b></td></tr><tr><td>Address:"
        + "</td><td style='font-style:italic'><b>"
        + groupAcProfile.address
        + "</b></td></tr></table>";
    return groupInfoTableContent;
}
function getFooterTableContent() {
    var footerTable = "<table><tr><td>Prepared By,</td><td align='right'>Sanctioned By,</td></tr><tr></tr><tr><td>SHG-One.net</td><td></td></tr></table>";
    return footerTable;
}
function printContent(content) {
    var tmpwindow = window.open();
    tmpwindow.document.write(content);
    tmpwindow.onload = setTimeout(function() {
        tmpwindow.print();
        tmpwindow.close();
    }, 500);
};
function printDiv(divName, title, heading, addGroupDetail) {
	var header = document.head.innerHTML.replace("<title>SHG-One.net</title>",
			"<title>" + title + "</title>");
	var mywindow = window.open();
	mywindow.document.write('<html><head>');
	mywindow.document.write(header);
	mywindow.document.write('</head><body>');
	mywindow.document
			.write("<img src='resources/img/shg-one.png' alt='logo' class='img-responsive' width='150' height='50'/><br>");
	if (heading && heading != "")
		mywindow.document.write("<h3 align='center'>" + heading + "</h3><br>");

	if (addGroupDetail) {
		var groupDataTable = "<div class='col-md-12'><table class='table'><tr><td>Group Account Number"
				+ "</td><td align='right'>"
				+ getReadableAccNum(groupAcProfile.groupAcNo)
				+ "</td></tr><tr><td>Name of Self Help Group"
				+ "</td><td align='right'>"
				+ groupAcProfile.groupName
				+ "</td></tr><tr><td>Date Of Establishment"
				+ "</td><td align='right'>"
				+ groupAcProfile.dateOfEstablishment
				+ "</td></tr><tr><td>Address"
				+ "</td><td align='right'>"
				+ groupAcProfile.contacts[0].address
				+ "</td></tr></table></div>";
		mywindow.document.write(groupDataTable);
	}
	mywindow.document.write(document.getElementById(divName).innerHTML);
	mywindow.document.write('</body></html>');
	mywindow.onload = setTimeout(function() {
		mywindow.print();
		mywindow.close();
	}, 500);
}
function printTable() {
	var tableId = "", title = "";
	var addGroupDetail = true;
	var id = $(this).prop("id");
	if (id == "g_reg_print" || id == "g_reg_pdf") {
		tableId = "g_reg_details";
		title = "Group Registration";
	} else if (id == "db_print" || id == "db_pdf") {
		tableId = "shg_db_details";
		title = "SHG Dashboard";
	} else if (id == "md_print" || id == "md_pdf") {
		tableId = "m_db_details";
		title = "Member Dashboard";
	} else if (id == "sc_print" || id == "sc_pdf") {
		tableId = "sc_table";
		title = "Saving Collection";
	} else if (id == "loan_print" || id == "loan_pdf") {
		tableId = "loan_table";
		title = "Fund Collection";
	} else if (id == "txn_print" || id == "txn_pdf") {
		tableId = "txn_table";
		title = "Trasactions";
	} else if (id == "m_acc_print" || id == "m_acc_pdf") {
		tableId = "m_acc_table";
		title = "Member Account";
	} else if (id == "m_reg_print" || id == "m_reg_pdf") {
		tableId = "m_reg_details";
		title = "Member Registration";
	} else if (id == "fund_print" || id == "fund_pdf") {
		tableId = "fund_details";
		title = "Fund Planning";
	} else if (id == "app_print" || id == "app_pdf") {
		tableId = "app_details";
		title = "Application";
	} else if (id == "g_app_print" || id == "g_app_pdf") {
		tableId = "g_app_details";
		title = "Application";
	} else if (id == "p_print" || id == "p_pdf") {
		tableId = "p_details";
		title = "Member Profile";
	} else if (id == "mp_print" || id == "mp_pdf") {
		tableId = "mprofiling_details";
		title = "Member Profiling Data";
	} else if (id == "gp_print" || id == "gp_pdf") {
		tableId = "gp_details";
		title = "Group Profile";
	} else if (id == "m_csv_print" || id == "m_csv_pdf") {
		tableId = "m_csv_details";
		title = "Import Group Data";
	} else if (id == "grules_print" || id == "grules_pdf") {
		tableId = "grules_details";
		title = "Group Rules";
	} else if (id == "ab_print" || id == "ab_pdf") {
		tableId = "ab_detail";
		title = "Account Book";
	} else if (id == "as_print" || id == "as_pdf") {
		tableId = "as_detail";
		title = "Account Statement";
	} else if (id == "item_tag_print" || id == "item_tag_pdf") {
		tableId = "item_tag_detail";
		addGroupDetail = false;
	}
	printDiv(tableId, title, title, addGroupDetail);
}
function genExcel() {
	var tableId = "", title = "";
	var id = $(this).prop("id");
	if (id == "g_reg_excel") {
		tableId = "g_reg_details";
		title = "Group Registration";
	} else if (id == "db_excel") {
		tableId = "shg_db_details";
		title = "SHG Dashboard";
	} else if (id == "md_excel") {
		tableId = "m_db_details";
		title = "Member Dashboard";
	} else if (id == "mwd_excel") {
		tableId = "mwd_table";
		title = "Member Dashboard";
	} else if (id == "sc_excel") {
		tableId = "sc_table";
		title = "Saving Collection";
	} else if (id == "loan_excel") {
		tableId = "loan_table";
		title = "Fund Collection";
	} else if (id == "txn_excel") {
		tableId = "txn_table";
		title = "Trasactions";
	} else if (id == "m_acc_excel") {
		tableId = "m_acc_table";
		title = "Member Account";
	} else if (id == "m_r_excel") {
		tableId = "m_r_table";
		title = $("#m_r_names option:selected").text() + " Report";
	} else if (id == "m_reg_excel") {
		tableId = "m_reg_details";
		title = "Member Registration";
	} else if (id == "fund_excel") {
		tableId = "fund_details";
		title = "Fund Planning";
	} else if (id == "app_excel") {
		tableId = "app_details";
		title = "Application";
	} else if (id == "g_app_excel") {
		tableId = "g_app_details";
		title = "Application";
	} else if (id == "p_excel") {
		tableId = "p_details";
		title = "Member Profile";
	} else if (id == "mp_excel") {
		tableId = "mprofiling_details";
		title = "Member Profiling Data";
	} else if (id == "gp_excel") {
		tableId = "gp_details";
		title = "Group Profile";
	} else if (id == "m_csv_excel") {
		tableId = "m_csv_details";
		title = "Import Group Data";
	} else if (id == "grules_excel") {
		tableId = "grules_details";
		title = "Group Rules";
	} else if (id == "ab_excel") {
		tableId = "ab_detail";
		title = "Account Book";
	} else if (id == "as_excel") {
		tableId = "as_detail";
		title = "Account Statement";
	}
	generateexcel(tableId, title);
}
function getGroupAccountProfile() {
	ajaxCall("/group/v1/group_ac/" + $("#lang option:selected").text() + "/"
			+ groupAcNo, "GET", true, "", function(data, status) {
		groupAcProfile = data;
		dispatchEvent(new Event("groupAcProfile"));
		var bankAccounts = groupAcProfile.bankAccountDisplay;
		for (var i = 0; i < bankAccounts.length; i++) {
			var bType = bankAccounts[i].bankAccountType;
			if (bType == "Saving Account" || bType == "Current Account") {
				groupBankAcNos[groupBankAcNos.length] = bankAccounts[i];
			}
		}
	});
}
function loadDistricts() {
	ajaxCall("/enum/v1/districts/English", "GET", true, "", function(data,
			status) {
		districtValues = data.districtValues;
		for (var i = 0; i < districtValues.length; i++) {
			var state = districtValues[i].state;
			var found = false;
			for (var j = 0; j < states.length; j++) {
				if(states[j] == state) {
					found = true;
					break;
				}
			}
			if(!found)
				states[states.length] =  state;
		}
		if(states[0] == "Admin") {
			for (var i = 0; i < states.length - 1; i++) {
				states[i] = states[i + 1];
			}
			states[states.length - 1] = "Admin";
		}
	});
}
function loadDocTypes() {
	ajaxCall("/enum/v1/doc_type", "GET", true, "", function(data, status) {
		docTypes = data.enumValues;
	});
}
function loadImageData(imgUrl, callback) {
	var img = new Image, imgData;
	
	img.onError = function() {
		throw new Error('Cannot load image: "'+imgUrl+'"');
	};
	img.onload = function() {
		var canvas = document.createElement('canvas');
		document.body.appendChild(canvas);
		canvas.width = img.width;
		canvas.height = img.height;

		var ctx = canvas.getContext('2d');
		ctx.drawImage(img, 0, 0);
		// Grab the image as a jpeg encoded in base64, but only the data
		imgData = canvas.toDataURL('image/jpeg').slice('data:image/jpeg;base64,'.length);
		// Convert the data to binary form
		imgData = atob(imgData);
		document.body.removeChild(canvas);
		if (typeof callback === 'function') {
			callback(imgData);
		}
	};
	img.src = imgUrl;
}