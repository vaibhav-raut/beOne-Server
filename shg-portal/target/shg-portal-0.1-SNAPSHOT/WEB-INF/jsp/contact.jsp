<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@page import="java.util.Map"%>
<%
	boolean db_access = false;
	Map<?, ?> uiAccessCodes = (Map<?, ?>)session.getAttribute("uiAccessCodes");
	if(uiAccessCodes.containsKey("GROUP_DASHBOARD"))
		db_access = true;

	String[] status = new String[3];
	if (db_access)
		status[0] = "active";
	else
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
<script src="resources/js/contact.js"></script>
<%
	if (db_access) {
%>
<script src="resources/js/contact/shg-contact.js"></script>
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
										if(db_access) {
									%>
									<li class="<%=status[0]%>"><a href="#shg_content" data-toggle="tab">Self Help Group</a></li>
									<%-- <li class="<%=status[1]%>"><a href="#auditor_content" data-toggle="tab">Auditor</a></li> --%>
									<%
										}
									%>
									<%-- <li class="<%=status[2]%>"><a href="#net_content" data-toggle="tab">BachatGat.Net</a></li> --%>
								</ul>
								<div class="tab-content">
									<%
										if(db_access) {
									%>
									<!--tab-pane-1-->
									<div class="tab-pane <%=status[0]%>" id="shg_content">
										<jsp:include page="contact/shg-contact.jsp"></jsp:include>
									</div>
									<!--tab-pane-2-->
 									<%-- <div class="tab-pane <%=status[1]%>" id="auditor_content">
										<div class="scroller" data-height="400px" data-always-visible="1" data-rail-visible1="1">
											<div class="row">
												<div class="col-md-12">
													<div class="portlet-body">Coming Soon.....</div>
												</div>
											</div>
										</div>
									</div> --%>
									<%
										}
									%>
									<!--tab-pane-3-->
									<%-- <div class="tab-pane <%=status[2]%>" id="net_content">
										<div class="scroller" data-height="400px" data-always-visible="1" data-rail-visible1="1">
											<div class="row">
												<div class="col-md-12">
													<div class="portlet-body">Coming Soon 3.....</div>
												</div>
											</div>
										</div>
									</div> --%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
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