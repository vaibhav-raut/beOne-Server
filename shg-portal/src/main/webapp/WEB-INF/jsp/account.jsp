<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@page import="java.util.Map"%>
<%
	boolean m_book_access = false, g_book_access = false, acc_track_access = false, txn_approve_access = false;
	Map<?, ?> uiAccessCodes = (Map<?, ?>)session.getAttribute("uiAccessCodes");
	int addTxnAccessVal = 0;
	if(uiAccessCodes.containsKey("ADD_TRANSACTION")) {
		addTxnAccessVal = (Integer) uiAccessCodes.get("ADD_TRANSACTION");
		txn_approve_access = (addTxnAccessVal >= 3);
	}
	if(uiAccessCodes.containsKey("MEMBER_ACCOUNT_BOOK"))
		m_book_access = true;
	if(uiAccessCodes.containsKey("GROUP_ACCOUNT_BOOK"))
		g_book_access = true;
//	if(uiAccessCodes.containsKey("GROUP_ACCOUNTS_TRACKING"))
//		acc_track_access = true;

	String[] status = new String[4];
	if (m_book_access)
		status[0] = "active";
	else if (g_book_access)
		status[1] = "active";
	else if (acc_track_access)
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
<script src="resources/js/common/common.js"></script>
<script src="resources/js/common/search-member.js"></script>
<script src="resources/js/account.js"></script>
<%
	if(m_book_access) {
%>
<script src="resources/js/account/member-account.js"></script>
<%
	} if(g_book_access) {
%>
<script src="resources/js/account/account-book.js"></script>
<script src="resources/js/account/account-statement.js"></script>
<%
	} if(txn_approve_access) {
%>
<script src="resources/js/common/approve-dlg.js"></script>
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
	<input hidden="true" type="text" id="addTxnAccessVal" value="<%=addTxnAccessVal%>">
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
										if(m_book_access) {
									%>
									<li class="<%=status[0]%>"><a href="#m_acc_content" data-toggle="tab" id="m_acc_tab"> Member Account </a></li>
									<%
										} if(g_book_access) {
									%>
									<li class="<%=status[1]%>"><a href="#accbook_content" data-toggle="tab" id="accbook_tab"> Account Book </a></li>
									<li class="<%=status[2]%>"><a href="#accstatement_content" data-toggle="tab" id="accstatement_tab"> Account Statement </a></li>
									<%
										} if(acc_track_access) {
									%>
									<li class="<%=status[3]%>"><a href="#bad_acc_content" data-toggle="tab" id="bad_acc_tab"> Bad Accounts </a></li>
									<%
										}
									%>
								</ul>
								<div class="tab-content">
									<%
										if(m_book_access) {
									%>
									<!--tab-pane-1-->
									<div class="tab-pane <%=status[0]%>" id="m_acc_content">
										<jsp:include page="account/member-account.jsp"></jsp:include>
									</div>
									<%
										} if(g_book_access) {
									%>
									<!--tab-pane-2-->
									<div class="tab-pane <%=status[1]%>" id="accbook_content">
										<jsp:include page="account/account-book.jsp"></jsp:include>
									</div>
									<!--tab-pane-3-->
									<div class="tab-pane <%=status[2]%>" id="accstatement_content">
										<jsp:include page="account/account-statement.jsp"></jsp:include>
									</div>
									<%
										} if(acc_track_access) {
									%>
									<!--tab-pane-4-->
									<div class="tab-pane <%=status[3]%>" id="bad_acc_content">
										<div class="portlet-body">
											<br>
											<div class="table-toolbar">
												<div class="table-responsive">Coming Soon....</div>
											</div>
										</div>
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
	<%
		if(txn_approve_access) {
	%>
	<jsp:include page="common/approve-dlg.jsp"></jsp:include>
	<%
		}
	%>
	<jsp:include page="common/modal-dialog.jsp"></jsp:include>
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