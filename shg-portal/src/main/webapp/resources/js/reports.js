/**
 * 
 */
$(document).ready(function() {
	loadCommonProperties();
	loadReportEnum();
	$("#lang").val(curLang);
});
function loadReportEnum() {
	ajaxCall("/enum/v1/monthly_reports/" + $("#lang option:selected").text(),
			"GET", true, "", function(data, status) {
				if (data != "") {
					var mReportEnum = data.monthlyReports;
					var content = "";
					for (var i = 0; i < mReportEnum.length; i++) {
						content += "<option value='" +  mReportEnum[i].reportName + "'>"
							+ mReportEnum[i].reportDisplayName + "</option>";
					}
					$("#m_r_names").html(content);
				}
			});
}