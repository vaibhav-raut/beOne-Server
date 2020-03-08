<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%
	boolean my_acc_access = false, m_acc_access = false, g_acc_access = false;
	Map<?, ?> uiAccessCodes = (Map<?, ?>)session.getAttribute("uiAccessCodes");
	if(uiAccessCodes.containsKey("MEMBER_AC"))
		m_acc_access = true;
	if(uiAccessCodes.containsKey("SHG_GROUP_AC"))
		g_acc_access = true;

	JSONObject userLoginInfo = (JSONObject)session.getAttribute("user");
	JSONObject member = userLoginInfo.getJSONObject("member");
	long parentGroupAcNo = member.getLong("parentGroupAcNo");
	long selectedGroupAcNo = userLoginInfo.getLong("selectedGroupAcNo");
	
	// Don't show my profile if parent group is != to selected group.
	my_acc_access = m_acc_access && (parentGroupAcNo == selectedGroupAcNo);
	
	String[] status = new String[3];
	if(my_acc_access)
		status[0] = "active";
	else
		status[1] = "active";
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
<script src="resources/js/common/common.js"></script>
<script src="resources/js/common/search-member.js"></script>
<script src="resources/js/common/search-bank.js"></script>
<script src="resources/js/common/doc-upload-dlg.js"></script>
<script src="resources/js/profile.js"></script>
<script src="resources/js/profile/m-edit-profile.js"></script>
<script src="resources/js/profile/m-profiling.js"></script>
<%
	if (my_acc_access) {
%>
<script src="resources/js/profile/my-profile.js"></script>
<%
	} if (m_acc_access) {
%>
<script src="resources/js/profile/m-profile.js"></script>
<%
	} if (g_acc_access) {
%>
<script src="resources/js/profile/g-profile.js"></script>
<script src="resources/js/profile/g-edit-profile.js"></script>
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
										if(my_acc_access) {
									%>
									<li class="<%=status[0]%>"><a href="#profile_content" data-toggle="tab" id="my_profile_tab"> My Profile </a></li>
									<%
										} if(m_acc_access) {
									%>
									<li class="<%=status[1]%>"><a href="#profile_content" data-toggle="tab" id="m_profile_tab"> Member Profile </a></li>
									<%
										} if(g_acc_access) {
									%>
									<li class="<%=status[2]%>"><a href="#g_profile_content" data-toggle="tab" id="g_profile_tab"> Group Profile </a></li>
									<%
										}
									%>
								</ul>
								<div class="tab-content">
									<!--tab-pane-1-and-2-->
									<div class="tab-pane active" id="profile_content">
										<jsp:include page="profile/m-profile.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="edit_profile_content">
										<jsp:include page="profile/m-edit-profile.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="mprofiling_content">
										<jsp:include page="profile/m-profiling.jsp"></jsp:include>
									</div>
									<%
										if(g_acc_access) {
									%>
									<!--tab-pane-3-->
									<div class="tab-pane <%=status[2]%>" id="g_profile_content">
										<jsp:include page="profile/g-profile.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="gedit_profile_content">
										<jsp:include page="profile/g-edit-profile.jsp"></jsp:include>
									</div>
									<%
										}
									%>
									<!--end-tab-pane-->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="common/doc-upload-dlg.jsp"></jsp:include>
	<jsp:include page="common/modal-dialog.jsp"></jsp:include>
	<jsp:include page="profile/change-pswd-dlg.jsp"></jsp:include>
	<jsp:include page="common/search-bank-dialog.jsp"></jsp:include>
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