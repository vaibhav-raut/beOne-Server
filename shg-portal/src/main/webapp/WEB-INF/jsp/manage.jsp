<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%
	boolean m_shg_access = false, g_rules_access = false, a_rights_access = false, csv_upload = false, update_group_status_access = false;
	Map<?, ?> uiAccessCodes = (Map<?, ?>)session.getAttribute("uiAccessCodes");
	JSONObject userLoginInfo = (JSONObject)session.getAttribute("user");
	JSONObject member = userLoginInfo.getJSONObject("member");
	String groupType = userLoginInfo.getString("groupType");
	long parentGroupAcNo = member.getLong("parentGroupAcNo");
	long selectedGroupAcNo = userLoginInfo.getLong("selectedGroupAcNo");
	if(uiAccessCodes.containsKey("MANAGE_SHG")) {
		m_shg_access = true;
		csv_upload = (parentGroupAcNo == selectedGroupAcNo && ((Integer)uiAccessCodes.get("MANAGE_SHG")) >= 2);
		update_group_status_access = (parentGroupAcNo != selectedGroupAcNo);
	}
	g_rules_access = ("SHG".equals(groupType) && uiAccessCodes.containsKey("SHG_GROUP_RULES"));
	a_rights_access = ((groupType.equals("SHG-One Admin") || parentGroupAcNo != selectedGroupAcNo) && uiAccessCodes.containsKey("ACCESS_RIGHTS"));
	
	String[] status = new String[5];
	if (m_shg_access)
		status[0] = "active";
	else if (g_rules_access)
		status[2] = "active";
	else if (a_rights_access)
		status[3] = "active";
%>
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta charset="utf-8" />
<title>SHG-One.net</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- BEGIN SCRIPT -->
<!--  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
<script src="resources/js/jquery.min.1.11.0.js"></script>
<script src="resources/js/common/edit-treetable.js"></script>
<script src="resources/js/common/common.js"></script>
<script src="resources/js/common/search-group.js"></script>
<script src="resources/js/common/search-member.js"></script>
<script src="resources/js/common/select-district.js"></script>
<script src="resources/js/manage.js"></script>
<%
	if(m_shg_access) {
%>
<script src="resources/js/manage/select-group.js"></script>
<%
	} if(update_group_status_access) {
%>
<script src="resources/js/manage/update-group-status.js"></script>
<%
	} if (g_rules_access) {
		if(((Integer)uiAccessCodes.get("SHG_GROUP_RULES")) >= 2) {
%>
<!-- <script src="resources/js/manage/edit-group-rules.js"></script> -->
<%
	}
%>
<script src="resources/js/manage/group-rules.js"></script>
<%
	} if (a_rights_access) {
%>
<script src="resources/js/manage/access-mgmt.js"></script>
<%
	} if (csv_upload) {
%>
<script src="resources/js/manage/csv-upload.js"></script>
<%
	}
%>
<!-- END SCRIPT -->

<jsp:include page="common/page-style.jsp"></jsp:include>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-full-width">
	<!-- BEGIN HEADER -->
	<jsp:include page="common/header.jsp"></jsp:include>
	<!-- END HEADER -->
	<div class="clearfix"></div>
	<!-- BEGIN CONTAINER -->
	<br>
	<!-- BEGIN CONTENT -->
	<div class="page-container">
		<div class="page-content-wrapper">
			<div class="page-content">
				<!-- BEGIN DASHBOARD STATS -->
				<jsp:include page="common/db-stats.jsp"></jsp:include>
				<!-- END DASHBOARD STATS -->
				<div style="margin: 0px 20px;">
					<div class="row">
						<jsp:include page="common/menu.jsp"></jsp:include>
						<div class="col-md-9">
							<div class="tabbable tabbable-custom tabbable-custom-profile">
								<ul class="nav nav-tabs">
									<%
										if(m_shg_access) {
									%>
									<li class="<%=status[0]%>"><a href="#g_select_content" data-toggle="tab" id="g_select_tab"> Select Group </a></li>
									<%
										} if(update_group_status_access) {
									%>
									<li class="<%=status[1]%>"><a href="#g_update_content" data-toggle="tab" id="g_update_tab"> Update Group Status </a></li>
									<%
										} if(g_rules_access) {
									%>
									<li class="<%=status[2]%>"><a href="#g_rules_content" data-toggle="tab" id="g_rules_tab"> Group Rules </a></li>
									<%
										} if(a_rights_access) {
									%>
									<li class="<%=status[3]%>"><a href="#a_rights_content" data-toggle="tab" id="a_rights_tab"> Access Management </a></li>
									<%
										} if(csv_upload) {
									%>
									<li class="<%=status[4]%>"><a href="#csv_upload_content" data-toggle="tab" id="csv_upload_tab"> CSV Upload </a></li>
									<%
										}
									%>
								</ul>
								<div class="tab-content">
									<%
										if(m_shg_access) {
									%>
									<div class="tab-pane <%=status[0]%>" id="g_select_content">
										<jsp:include page="manage/select-group.jsp"></jsp:include>
									</div>
									<%
										} if(update_group_status_access) {
									%>
									<div class="tab-pane <%=status[1]%>" id="g_update_content">
										<jsp:include page="manage/update-group-status.jsp"></jsp:include>
									</div>
									<%
										} if(g_rules_access) {
									%>
									<div class="tab-pane <%=status[2]%>" id="g_rules_content">
										<jsp:include page="manage/group-rules.jsp"></jsp:include>
									</div>
									<%
										} if(a_rights_access) {
									%>
									<div class="tab-pane <%=status[3]%>" id="a_rights_content">
										<jsp:include page="manage/access-mgmt.jsp"></jsp:include>
									</div>
									<%
										} if(csv_upload) {
									%>
									<div class="tab-pane <%=status[4]%>" id="csv_upload_content">
										<jsp:include page="manage/csv-upload.jsp"></jsp:include>
									</div>
									<%
										}
									%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="common/modal-dialog.jsp"></jsp:include>
	<jsp:include page="common/select-district-dlg.jsp"></jsp:include>
	<!-- END CONTENT -->
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<jsp:include page="common/footer.jsp"></jsp:include>
	<!-- END FOOTER -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<!--[if lt IE 9]>
<script src="resources/plugins/respond.min.js"></script>
<script src="resources/plugins/excanvas.min.js"></script> 
<![endif]-->
	<script src="resources/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
	<script src="resources/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="resources/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="resources/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="resources/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="resources/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="resources/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="resources/plugins/back-to-top.js"></script>

	<script src="resources/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="resources/plugins/select2/select2.min.js"></script>
	<script type="text/javascript" src="resources/plugins/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="resources/plugins/data-tables/DT_bootstrap.js"></script>
	<script type="text/javascript" src="resources/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="resources/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="resources/scripts/core/app.js"></script>
	<script src="resources/scripts/custom/table-editable.js"></script>
	<script src="resources/scripts/custom/ecommerce-orders-view.js"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN TREE TABLE SCRIPT -->
	<script type="text/javascript" src="resources/plugins/jquery.treetable-ajax-persist.js"></script>
	<script type="text/javascript" src="resources/plugins/jquery.treetable-3.0.0.js"></script>
	<script type="text/javascript" src="resources/plugins/persist-min.js"></script>
	<!-- END TREE TABLE SCRIPT -->

	<script>
		jQuery(document).ready(function() {
			App.init();
			TableEditable.init();
		});
	</script>

	<script>
		jQuery(document).ready(function() {
			App.init();
			EcommerceOrdersView.init();
		});
	</script>
</html>