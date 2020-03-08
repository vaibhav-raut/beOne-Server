define(['jquery'],
function() {
    var Memberdb = (function() {

        //Constructor
        function Memberdb() {

            // public function
            this.init = function() {
                $("#mdb_tab").click(loadMemberDashboardTab);
                $("#md_print").click(printTable);
                $("#md_pdf").click(printTable);
                $("#md_excel").click(genExcel);
                loadMemberDashboardTab();
            };

            // private function
            loadMemberDashboardTab = function() {
                var uri = "/dashboard/v1/member_dashboard/" + $("#lang option:selected").text() + "/" + curMemberAcNo;
                ajaxCall(uri, "GET", true, "", handleSuccess, handleError);
            };

            function handleSuccess(data, status) {
                $("#md_msg").html("");
                if(data !== null && data.headerRecords !== null && data.headerRecords.length !== 0) {
                    var tablecontent = "",
                        i = 0;
                    for (; i < data.headerRecords.length; i++) {
                        tablecontent += "<tr><td>" + data.headerRecords[i].name + "</td><td class='bluetext'>";
                        if (data.headerRecords[i].name === "Member Account Number") {
                            tablecontent += getReadableAccNumForDistrict(mdCode, data.headerRecords[i].value);
                        } else {
                            tablecontent += data.headerRecords[i].value + "</td></tr>";
                        }
                    }
                    tablecontent += "<tr><td colspan='2'></td></tr>";
                    $("#mdb_header_table").html(tablecontent);

                    tablecontent = "";
                    for (i = 0; i < data.records.length / 2; i++) {
                        tablecontent += "<tr><td>" + data.records[i].name + "</td><td class='bluetext'>" + data.records[i].value + "</td></tr>";
                    }
                    $("#md_table_1").html(tablecontent);

                    tablecontent = "";
                    for (; i < data.records.length; i++) {
                        tablecontent += "<tr><td>" + data.records[i].name + "</td><td class='bluetext'>" + data.records[i].value + "</td></tr>";
                    }
                    $("#md_table_2").html(tablecontent);
                } else {
                    $("#md_err_msg").hide();
                    $("#md_msg").show();
                    $("#md_msg").html("<tr><td>No dashboard data available.</td></tr>");
                }
            };

            function handleError(xhr) {
                $("#md_msg").hide();
                $("#md_err_msg").show();
                handleErrorAndDisplayMsg(xhr, "md_err_msg");
            };
        }
        return new Memberdb();
    }());
    return Memberdb;
});