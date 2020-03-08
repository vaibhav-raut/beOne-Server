<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
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
<script src="resources/js/common/select-district.js"></script>
<script src="resources/js/common/search-manufacturer.js"></script>
<script src="resources/js/common/search-brand.js"></script>
<script src="resources/js/common/search-stock-type.js"></script>
<script src="resources/js/common/sel-product-category.js"></script>
<script src="resources/js/purchase.js"></script>
<script src="resources/js/purchase/csv-upload.js"></script>
<script src="resources/js/purchase/purchase-details.js"></script>
<script src="resources/js/purchase/manufacturer-reg.js"></script>
<script src="resources/js/purchase/brand-stype-reg.js"></script>
<script src="resources/js/purchase/item-tag.js"></script>
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
									<li class="active"><a href="#csv_upload_content" data-toggle="tab" id="csv_upload_tab"> CSV Upload </a></li>
									<li><a href="#purchase_details_content" data-toggle="tab" id="purchase_details_tab"> Purchase </a></li>
									<li><a href="#manufacturer_reg_content" data-toggle="tab" id="manufacturer_reg_tab"> Manufacturer Registration </a></li>
									<li><a href="#brand_stype_reg_content" data-toggle="tab" id="brand_stype_reg_tab"> Brand & Stock Type Registration </a></li>
									<li><a href="#item_tag_content" data-toggle="tab" id="item_tag_tab"> Item Tags </a></li>
								</ul>
								<div class="tab-content">
									<div class="tab-pane active" id="csv_upload_content">
										<jsp:include page="purchase/csv-upload.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="purchase_details_content">
										<jsp:include page="purchase/purchase-details.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="manufacturer_reg_content">
										<jsp:include page="purchase/manufacturer-reg.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="brand_stype_reg_content">
										<jsp:include page="purchase/brand-stype-reg.jsp"></jsp:include>
									</div>
									<div class="tab-pane" id="item_tag_content">
										<jsp:include page="purchase/item-tag.jsp"></jsp:include>
									</div>
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
	<jsp:include page="common/search-manufacturer-dlg.jsp"></jsp:include>
	<jsp:include page="common/search-brand-dlg.jsp"></jsp:include>
	<jsp:include page="common/search-stock-type-dlg.jsp"></jsp:include>
	<jsp:include page="common/sel-product-category-dlg.jsp"></jsp:include>
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