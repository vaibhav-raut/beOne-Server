<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@page import="org.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%
	boolean m_acc_access = false, m_loan_acc_access = false, m_shg_access = false, firm_access = false;
	Map<?, ?> uiAccessCodes = (Map<?, ?>)session.getAttribute("uiAccessCodes");
	if(uiAccessCodes.containsKey("MEMBER_AC"))
		m_acc_access = true;
	if(uiAccessCodes.containsKey("MEMBER_LOAN_AC"))
		m_loan_acc_access = true;
	
	JSONObject userLoginInfo = (JSONObject)session.getAttribute("user");
	JSONObject member = userLoginInfo.getJSONObject("member");
	long parentGroupAcNo = member.getLong("parentGroupAcNo");
	long selectedGroupAcNo = userLoginInfo.getLong("selectedGroupAcNo");
	if(parentGroupAcNo == selectedGroupAcNo) {
		if(uiAccessCodes.containsKey("MANAGE_SHG"))
			m_shg_access = true;
		if(uiAccessCodes.containsKey("FIRM_GROUP_AC"))
			firm_access = true;
	}
	String[] status = new String[5];
	if (m_acc_access)
		status[0] = "active";
	else if (m_loan_acc_access)
		status[1] = "active";
	else if (m_shg_access)
		status[2] = "active";
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
<script src="resources/js/common/select-district.js"></script>
<script src="resources/js/common/doc-upload-dlg.js"></script>
<script src="resources/js/application.js"></script>
<%
	if(m_acc_access) {
%>
<script src="resources/js/application/member-reg.js"></script>
<%
	} if(m_loan_acc_access) {
%>
<script src="resources/js/application/member-fund-app.js"></script>
<script src="resources/js/application/closure.js"></script>
<%
	} if(m_shg_access) {
%>
<script src="resources/js/application/group-reg.js"></script>
<script src="resources/js/application/g-app-tracking.js"></script>
<script src="resources/js/application/g-app-activation.js"></script>
<%
	} 
%>
<script src="resources/js/application/app-tracking.js"></script>
<script src="resources/js/application/app-activation-closure.js"></script>
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
										if(m_acc_access) {
									%>
									<li class="<%=status[0]%>"><a href="#m_reg_content" data-toggle="tab" id="m_reg_tab">Member Registration</a></li>
									<%
										} if(m_loan_acc_access) {
									%>
									<li class="<%=status[1]%>"><a href="#m_fund_content" data-toggle="tab" id="m_fund_app_tab">Loan Application</a></li>
									<%
										} if(m_shg_access) {
									%>
									<li class="<%=status[2]%>"><a href="#g_reg_content" data-toggle="tab" id="g_reg_tab">Group Registration</a></li>
									<%
										} if(m_acc_access || m_loan_acc_access) {
									%>
									<li class="<%=status[3]%>"><a href="#app_track_content" data-toggle="tab" id="app_track_tab">Application Tracking</a></li>
									<%
										} if(m_loan_acc_access) {
									%>
									<li class="<%=status[4]%>"><a href="#app_closure_content" data-toggle="tab" id="closure_tab">Closure</a></li>
									<%
										}
									%>
								</ul>
								<div class="tab-content">
									<!--start-tab-pane-->
									<%
										if(m_acc_access) {
									%>
									<div class="tab-pane <%=status[0]%>" id="m_reg_content">
										<jsp:include page="application/member-reg.jsp"></jsp:include>
									</div>
									<%
										} if(m_loan_acc_access) {
									%>
									<div class="tab-pane <%=status[1]%>" id="m_fund_content">
										<jsp:include page="application/member-fund-app.jsp"></jsp:include>
									</div>
									<%
										} if(m_shg_access) {
									%>
									<div class="tab-pane <%=status[2]%>" id="g_reg_content">
										<jsp:include page="application/group-reg.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="g_app_activation_content">
										<jsp:include page="application/g-app-activation.jsp"></jsp:include>
									</div>
									<%
										} if(m_acc_access || m_loan_acc_access) {
									%>
									<div class="tab-pane <%=status[3]%>" id="app_track_content">
										<jsp:include page="application/app-tracking.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="app_activation_content">
										<jsp:include page="application/app-activation-closure.jsp"></jsp:include>
									</div>
									<%
										} if(m_loan_acc_access) {
									%>
									<div class="tab-pane <%=status[4]%>" id="app_closure_content">
										<jsp:include page="application/closure.jsp"></jsp:include>
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
	<jsp:include page="common/select-district-dlg.jsp"></jsp:include>
	<jsp:include page="common/modal-dialog.jsp"></jsp:include>
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