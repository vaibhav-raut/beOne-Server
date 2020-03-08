/**
 * 
 */
// IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip
// TODO: Remove plugins from following which are not actually required.
require(["jquery",
         "../../plugins/jquery-migrate-1.2.1.min",
         "../../plugins/jquery-ui/jquery-ui-1.10.3.custom.min",
         "../../plugins/bootstrap/js/bootstrap.min",
         "../../plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min",
         "../../plugins/jquery-slimscroll/jquery.slimscroll.min",
         "../../plugins/jquery.blockui.min",
         "../../plugins/jquery.cokie.min",
         "../../plugins/back-to-top",
         "../../plugins/uniform/jquery.uniform.min",
         "../../plugins/select2/select2.min",
         "../../plugins/data-tables/jquery.dataTables",
         "../../plugins/data-tables/DT_bootstrap",
         "../../plugins/bootstrap-datepicker/js/bootstrap-datepicker",
         "../../plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min",
         "../../plugins/jquery.treetable-ajax-persist",
         "../../plugins/jquery.treetable-3.0.0",
         "../../plugins/persist-min"],
function() {
    $(function() {
        loadCommonProperties();
        $("#lang").val(curLang);

        if ($("#gdbAccess").val() === "true") {
            require(["Groupdb"],
                    function(groupdb){
                        groupdb.init();
                    }
                );
        }
        if ($("#mdbAccess").val() === "true") {
            require(["Memberdb"],
                    function(memberdb){
                        memberdb.init();
                    }
                );
        }
        if ($("#mwdbAccess").val() === "true") {
            require(["Memberwisedb"],
                    function(memberwisedb){
                        memberwisedb.init();
                    }
                );
        }
    });
});