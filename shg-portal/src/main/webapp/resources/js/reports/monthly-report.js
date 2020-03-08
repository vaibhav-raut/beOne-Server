/**
 * 
 */
var reportData = null;
$(document).ready(function() {
	loadMonthlyReportTab();
	$("#load_m_report").click(loadMonthlyReport);
	$("#m_r_pdf").click(saveReportPDF);
	$("#m_r_excel").click(genExcel);
});
function loadMonthlyReportTab() {
	if (groupAcProfile != null) {
		var content = "";
		for (var k = 0; k < groupAcProfile.monthsAvaliable.length; k++)
			content += "<option>" + groupAcProfile.monthsAvaliable[k]
					+ "</option>";
		$("#m_r_till_date").html(content);
	} else {
		ajaxCall("/group/v1/group_ac/" + $("#lang option:selected").text()
				+ "/" + groupAcNo, "GET", true, "", function(data, status) {
			groupAcProfile = data;
			var content = "";
			for (var k = 0; k < groupAcProfile.monthsAvaliable.length; k++)
				content += "<option>" + groupAcProfile.monthsAvaliable[k]
						+ "</option>";
			$("#m_r_till_date").html(content);
		});
	}
}
function loadMonthlyReport() {
	var tillDate = $("#m_r_till_date").val();
	if (tillDate == "") {
		showMessage("Please select a valid date.", "m_r_till_date");
	} else {
		var url = "";
		if (tillDate == "As On Date") {
			url = "/report/v1/as_on_date_report/"
					+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
					+ $("#m_r_names").val() + "/DETAILED";
		} else {
			url = "/report/v1/monthly_report/"
					+ $("#lang option:selected").text() + "/" + groupAcNo + "/"
					+ $("#m_r_names").val() + "/" + tillDate + "/DETAILED";
		}
		ajaxCall(
				url,
				"GET",
				true,
				"",
				function(data, status) {
					reportData = data;
					var dataIdIndex = 1;
					for (var i = 0; i < data.sheets.length; i++) {
						var records = data.sheets[i].records;
						var content = "<tr><td><strong>No.</strong></td><td></td><td><strong>"
								+ data.sheets[i].topTitle
								+ "</strong></td><td align='right'><strong>Amount</strong></td></tr>";
						for (var j = 0; j < records.length; j++) {
							var parentIdIndex = dataIdIndex++;
							content += "<tr data-tt-id="
									+ parentIdIndex
									+ " style='color: #098dc7; font-weight: bold'><td>"
									+ (j + 1) + "</td><td></td><td>"
									+ records[j].name
									+ "</td><td align='right'>"
									+ records[j].value + "</td></tr>";
							var details = records[j].details;
							if (details != null) {
								for (var k = 0; k < details.length; k++) {
									content += "<tr data-tt-id="
											+ (dataIdIndex++)
											+ " data-tt-parent-id="
											+ parentIdIndex + "><td>" + (j + 1)
											+ "." + (k + 1)
											+ "</td><td></td><td>"
											+ details[k].name
											+ "</td><td align='right'>"
											+ details[k].value + "</td></tr>";
								}
							}
						}
						content += "<tr bgcolor='#ddd'><td colspan='2'></td><td><strong>"
								+ data.sheets[i].bottomTitle
								+ "</strong></td><td align='right'><strong>"
								+ data.sheets[i].totalAmount
								+ "</strong></td></tr>";
						$("#m_r_table_" + i).html(content);
						$("#m_r_table_" + i).agikiTreeTable({
							persist : true,
							persistStoreName : "files"
						});
					}
				},
				function(xhr) {
					handleErrorAndDisplayMsg(xhr, "m_r_table_0");
					$("#m_r_table_1").html("");
				});
	}
}
function saveReportPDF() {
	if(reportData && reportData.sheets.length > 0) {
		var doc = new jsPDF();
		doc.setFont("times", "normal");
		var column = 30, title = reportData.bankReportName;
		// Adding company logo
		doc.addImage(logoImgData, 'JPEG', 10, 10, 40, 10);

		// Adding image as water mark
		doc.addImage(watermarkImgData, 'JPEG', 75, 120, 60, 60);

		// adding page header/title
		doc.setFontSize(15);
		doc.text(80, column += 6, title);

		doc.setFontSize(14);
		if(groupAcProfile.groupName.length <= title.length)
			doc.text(80, column += 6, groupAcProfile.groupName);
		else
			doc.text(60, column += 6, groupAcProfile.groupName);

		doc.setFontSize(13);
		var date = reportData.date;
		if (date.search("As On Date") === -1)
			doc.text(100, column += 6, date);
		else
			doc.text(80, column += 6, date);

		// adding group details
		var y = column;
		doc.setFontSize(10);
		doc.text(10, y += 15, "Group Account Number:");
		doc.text(10, y += 6, "Date Of Establishment:");
		doc.text(10, y += 6, "Address:");
		doc.text(10, y += 6, "Affiliate Bank:");

		y = column;
		doc.setFontType("bolditalic");
		doc.text(60, y += 15, getReadableAccNum(groupAcProfile.groupAcNo));
		doc.text(60, y += 6, groupAcProfile.dateOfEstablishment);
		doc.text(60, y += 6, groupAcProfile.address ? groupAcProfile.address : "N/A");
		doc.text(60, y += 6, groupAcProfile.affiliatedBank ? groupAcProfile.affiliatedBank : "N/A");

		column = y + 10;
		var maxY = column;
		for (var i = 0; i < reportData.sheets.length; i++) {
			var row = 90 * i + 10 * (i+1);
			doc.setFontSize(9);
			doc.setFontType("bold");
			doc.line(100 * i + 10, column - 4, 100 * i + 103, column - 4);
			doc.text(row, column, "No.");
			doc.text(row + 10, column, reportData.sheets[i].topTitle);
			doc.text(row + 75, column, "Amount");
			doc.line(100 * i + 10, column + 2, 100 * i + 103, column + 2);

			y = column;
			doc.setFontType("normal");
			var records = reportData.sheets[i].records;
			for (var j = 0; j < records.length; j++) {
				y = y + 6;
				doc.setFontSize(9);
				doc.text(row, y, "" + (j + 1));
				doc.text(row + 10, y, records[j].name);
				doc.text(row + 75, y, records[j].value);

				var details = records[j].details;
				if (details != null) {
					doc.setFontSize(8);
					for (var k = 0; k < details.length; k++) {
						y = y + 6;
						doc.text(row, y, (j + 1) + "." + (k + 1));
						doc.text(row + 10, y, details[k].name);
						doc.text(row + 75, y, details[k].value);
					}
				}
			}
			if(maxY < y)
				maxY = y;
		}
		column = maxY + 6;
		doc.setFontType("bold");
		for (var i = 0; i < reportData.sheets.length; i++) {
			var row = 90 * i + 10 * (i+1);
			doc.line(100 * i + 10, column - 4, 100 * i + 103, column - 4);
			doc.text(row + 10, column, reportData.sheets[i].bottomTitle);
			doc.text(row + 75, column, reportData.sheets[i].totalAmount + "");
			doc.line(100 * i + 10, column + 2, 100 * i + 103, column + 2);
		}
		
		// add footer
		doc.setFontType("normal");
		doc.setFontSize(12);
    	doc.text(10, 250, ["Prepared By,", "", "SHG-One.net"]);
    	doc.text(170, 250, "Sanctioned By,");

		doc.save(title + ".pdf");
	}
}