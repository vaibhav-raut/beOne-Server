define(['jquery'],
function() {
    var Groupdb = (function() {

        //Constructor
        function Groupdb() {

            // public function
            this.init = function() {
                $("#db_tab").click(loadDashboardTab);
                $("#db_print").click(printTable);
                $("#db_pdf").click(printTable);
                $("#db_excel").click(genExcel);
                loadDashboardTab();
            };

            // private function
            function loadDashboardTab() {
                var uri = "/dashboard/v1/group_dashboard/" + $("#lang option:selected").text() + "/" + groupAcNo;
                ajaxCall(uri, "GET", true, "", handleSuccess, handleError);
            };

            var dataIdIndex = 1, tablecontent = "";
            function handleSuccess(data, status) {
                $("#db_msg").html("");
                if (data !== null && data.headerRecords !== null && data.headerRecords.length !== 0) {
                    tablecontent = "";
                    var i = 0;
                    for (; i < data.headerRecords.length; i++) {
                        tablecontent += "<tr><td>" + data.headerRecords[i].name + "</td><td class='bluetext'>" + data.headerRecords[i].value + "</td></tr>";
                    }
                    tablecontent += "<tr><td colspan='2'></td></tr>";
                    $("#db_header_table").html(tablecontent);

                    tablecontent = "";
                    for (i = 0; i < data.records.length / 2; i++) {
                        var parentIdIndex = dataIdIndex++;
                        tablecontent += "<tr data-tt-id=" + parentIdIndex
                                + " style='color: #098dc7; font-weight: bold'><td hidden='true'></td><td>"
                                + data.records[i].name + "</td><td class='bluetext'>"
                                + data.records[i].value + "</td></tr>";
                        
                        getHtmlDetails(parentIdIndex, data.records[i].details);
                    }
                    $("#db_table_1").html(tablecontent);
                    $("#db_table_1").agikiTreeTable({
                        persist : true,
                        persistStoreName : "files"
                    });

                    tablecontent = "";
                    for (; i < data.records.length; i++) {
                        var parentIdIndex = dataIdIndex++;
                        tablecontent += "<tr data-tt-id=" + parentIdIndex
                                + " style='color: #098dc7; font-weight: bold'><td hidden='true'></td><td>"
                                + data.records[i].name + "</td><td class='bluetext'>"
                                + data.records[i].value + "</td></tr>";
                        
                        getHtmlDetails(parentIdIndex, data.records[i].details);
                    }
                    $("#db_table_2").html(tablecontent);
                    $("#db_table_2").agikiTreeTable({
                        persist : true,
                        persistStoreName : "files"
                    });
                } else {
                    $("#db_err_msg").hide();
                    $("#db_msg").show();
                    $("#db_msg").html("<tr><td>No dashboard data available.</td></tr>");
                }
            };

            function getHtmlDetails(parentIdIndex, details) {
                if (details != null) {
                    for (var k = 0; k < details.length; k++) {
                        var newParentIdIndex = dataIdIndex++;
                        tablecontent += "<tr data-tt-id=" + newParentIdIndex + " data-tt-parent-id="
                                + parentIdIndex + "><td hidden='true'></td><td>" + details[k].name
                                + "</td><td class='bluetext'>"
                                + details[k].value + "</td></tr>";
                        
                        getHtmlDetails(newParentIdIndex, details[k].details);
                    }
                }
            };

            function handleError(xhr) {
                $("#db_msg").hide();
                $("#db_err_msg").show();
                handleErrorAndDisplayMsg(xhr, "db_err_msg");
            };
        }
        return new Groupdb();
    }());
    return Groupdb;
});