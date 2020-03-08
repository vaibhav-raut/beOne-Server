<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<%@page import="java.util.Map"%>
<%
	Map<?, ?> uiAccessCodes = (Map<?, ?>) session.getAttribute("uiAccessCodes");
	int addTxnAccessVal = (Integer) uiAccessCodes.get("ADD_TRANSACTION");
	boolean txn_update_access = (addTxnAccessVal >= 2);
	boolean txn_approve_access = (addTxnAccessVal >= 3);
	String[] status = new String[2];
	if (txn_update_access)
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
<script src="resources/js/common/doc-upload-dlg.js"></script>
<script src="resources/js/transaction.js"></script>
<%
	if(txn_update_access) {
%>
<script src="resources/js/transaction/add-txn.js"></script>
<script src="resources/js/common/todo-undo-dlg.js"></script>
<%
	} if(txn_approve_access) {
%>
<script src="resources/js/common/approve-dlg.js"></script>
<%
	}
%>
<script src="resources/js/transaction/saving-collection.js"></script>
<script src="resources/js/transaction/fund-collection.js"></script>
<script src="resources/js/transaction/txn-tracking.js"></script>
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
										if(txn_update_access) {
									%>
									<li class="<%=status[0]%>"><a href="#add_txn_content" data-toggle="tab" id="add_txn_tab"> Add Transaction </a></li>
									<%
										}
									%>
									<li class="<%=status[1]%>"><a href="#sc_content" data-toggle="tab" id="sc_tab"> Saving Collection </a></li>
									<li><a href="#fc_content" data-toggle="tab" id="loan_tab"> Loan Collection </a></li>
									<!-- <li><a href="#bank_payment_content" data-toggle="tab" id="bank_payment_tab"> Bank Payments </a></li> -->
									<li><a href="#txn_tracking_content" data-toggle="tab" id="track_txn_tab"> Transaction Tracking </a></li>
								</ul>
								<div class="tab-content">
									<%
										if(txn_update_access) {
									%>
									<div class="tab-pane <%=status[0]%>" id="add_txn_content">
										<jsp:include page="transaction/add-txn.jsp"></jsp:include>
									</div>
									<%
										}
									%>
									<!--tab-pane-2-->
									<div class="tab-pane <%=status[1]%>" id="sc_content">
										<div class="tab-pane active">
											<jsp:include page="transaction/saving-collection.jsp"></jsp:include>
										</div>
									</div>
									<!--tab-pane-3-->
									<div class="tab-pane" id="fc_content">
										<div class="tab-pane active">
											<jsp:include page="transaction/fund-collection.jsp"></jsp:include>
										</div>
									</div>
									<!--tab-pane-4-->
									<%-- <div class="tab-pane" id="bank_payment_content">
										<div class="tab-pane active">
											<jsp:include page="transaction/bank-payments.jsp"></jsp:include>
										</div>
									</div> --%>
									<!--tab-pane-5-->
									<div class="tab-pane" id="txn_tracking_content">
										<div class="tab-pane active">
											<jsp:include page="transaction/txn-tracking.jsp"></jsp:include>
										</div>
									</div>
									<!--tab-pane-end-->
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%
		if(txn_update_access) {
	%>
	<jsp:include page="common/todo-undo-dlg.jsp"></jsp:include>
	<%
		} if(txn_approve_access) {
	%>
	<jsp:include page="common/approve-dlg.jsp"></jsp:include>
	<%
		}
	%>
	<jsp:include page="common/modal-dialog.jsp"></jsp:include>
	<jsp:include page="common/doc-upload-dlg.jsp"></jsp:include>
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