define(['jquery'],
function() {
    var Memberwisedb = (function() {

        //Constructor
        function Memberwisedb() {

            // Define all class level variables/constants here.
            var memberwiseDBData = null,
                tableheader = "",
                MEMBER_INFO = "Member Information",
                MEMBER_ACC_DETAILS = "Member Account Overview",
                MEMBER_SAVING_DETAILS = "Member Saving Details",
                MEMBER_LOAN_DETAILS = "Member Loan Details",
                memberInfoRecordsId = ['0','1','2','3','22'],
                memberAccRecordsId = ['0','1','2','4','6','8','10','11','12','13','14','15','16','20','21'],
                memberSavingRecordsId = ['0','1','2','4','5','6','7','8','9','10','11','12','13'],
                memberLoanRecordsId = ['0','1','2','4','14','15','16','17','18','19'];
            
            // public function
            this.init = function() {
                $("#mwdb_tab").click(loadMemberWiseDashboardTab);
                $("#mwd_search").click(filterMemberWiseDBTable);
                $("#mwd_clear_search").click(clearFilterMemberWiseDBTable);
                $("#mwd_print_m_info").click(function() {
                    printMemberDetailsFor(MEMBER_INFO);
                });
                $("#mwd_print_m_acc_overview").click(function() {
                    printMemberDetailsFor(MEMBER_ACC_DETAILS);
                });
                $("#mwd_print_m_saving_details").click(function() {
                    printMemberDetailsFor(MEMBER_SAVING_DETAILS);
                });
                $("#mwd_print_m_loan_details").click(function() {
                    printMemberDetailsFor(MEMBER_LOAN_DETAILS);
                });
                $("#mwd_excel").click(genExcel);
                loadMemberWiseDashboardTab();
            };

            // private function
            function clearFilterMemberWiseDBTable() {
                $("#mwd_input").val("");
                filterMemberWiseDBTable();
            };

            function filterMemberWiseDBTable() {
                if (memberwiseDBData !== null) {
                    var search_input = $("#mwd_input").val(),
                        tablecontent = "";
                    for (var j = 0; j < memberwiseDBData.length; j++) {
                        if (search_input === "" || memberwiseDBData[j].records[1].value.toLowerCase().search(search_input.toLowerCase()) !== -1) {
                            tablecontent += "<tr>";
                            for (var k = 0; k < memberwiseDBData[j].records.length; k++) {
                                if (memberwiseDBData[j].records[k].name === "Member Account Number") {
                                    tablecontent += "<td>" + getReadableAccNum(memberwiseDBData[j].records[k].value) + "</td>";
                                } else {
                                    tablecontent += "<td>" + memberwiseDBData[j].records[k].value + "</td>";
                                }
                            }
                            tablecontent += "</tr>";
                        }
                        if(tablecontent === "") {
                            $("#mwd_table_header").empty();
                            $("#mwd_table_body").html("<tr><td>No data found</td></tr>");
                        } else {
                            $("#mwd_table_header").html(tableheader);
                            $("#mwd_table_body").html(tablecontent);
                        }
                        $("#mwd_filter").html(search_input.toLowerCase());
                        if (search_input === "") {
                            $("#mwd_clear_search").hide();
                        } else {
                            $("#mwd_clear_search").show();
                        }
                    }
                }
            };
                
            function loadMemberWiseDashboardTab() {
                var uri = "/dashboard/v1/member_wise_dashboard/" + $("#lang option:selected").text() + "/" + groupAcNo;
                ajaxCall(uri, "GET", true, "", handleSuccess,
                        function(xhr) {
                            handleErrorAndDisplayMsg(xhr, "mwd_table_body");
                        });
            };

            function handleSuccess(data, status) {
                memberwiseDBData = data;
                if (data.length > 0) {
                    tableheader = "<tr>";
                    for (var i = 0; i < data[0].records.length; i++) {
                        tableheader += "<th><strong>" + data[0].records[i].name + "</strong></th>";
                    }
                    tableheader += "</tr>";
                }
                filterMemberWiseDBTable();
            };
            
            function printMemberDetailsFor(title) {
                var recordsId = null;
                if(title === MEMBER_INFO)
                    recordsId = memberInfoRecordsId;
                else if(title === MEMBER_ACC_DETAILS)
                    recordsId = memberAccRecordsId;
                else if(title === MEMBER_SAVING_DETAILS)
                    recordsId = memberSavingRecordsId;
                else if(title === MEMBER_LOAN_DETAILS)
                    recordsId = memberLoanRecordsId;
                var content = '<html><head>';
                content += getHeaderForPrintPage(title);
                content += pageStyleForPrinting + "</head><body>";
                content += "<img src='resources/img/shg-one.png' alt='logo' class='img-responsive' width='150' height='50'/><br>";
                content += "<p style='font-size:15pt;'>" + title + "</p>";
                content += "<p style='font-size:14pt;'>" + groupAcProfile.groupName + "</p><br><br>";
                content += getGroupInfoTableContent() + "<br>";
                var memberDataTable = "<table><tr><th>No member data found</th></tr></table>";
                if (recordsId && memberwiseDBData && memberwiseDBData.length > 0) {
                    var headContent = "<tr>";
                    for (var i = 0; i < recordsId.length; i++) {
                        headContent += "<th>" + memberwiseDBData[0].records[recordsId[i]].name + "</th>";
                    }
                    headContent += "</tr>";
                    
                    var bodyContent = "";
                    for (var j = 0; j < memberwiseDBData.length; j++) {
                        bodyContent += "<tr>";
                        for (var i = 0; i < recordsId.length; i++) {
                               var record = memberwiseDBData[j].records[recordsId[i]];
                            if (record.name === "Member Account Number") {
                                bodyContent += "<td>" + getReadableAccNum(record.value) + "</td>";
                            } else {
                                bodyContent += "<td>" + record.value + "</td>";
                            }
                        }
                        bodyContent += "</tr>";
                    }

                    memberDataTable = "<table style='border-bottom: 1px solid black;'><table style='border-bottom: 1px solid black;'>"
                        + headContent + bodyContent + "</table>";
                }
                content += memberDataTable + "<br><br><br><br>";
                content += getFooterTableContent();
                content += '</body></html>';
                
                printContent(content);
            };
        }
        return new Memberwisedb();
    }());
    return Memberwisedb;
});