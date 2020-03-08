<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<table class="table" style="width:;">
				<tr>
					<td align="center">Please search <b>Manufacturer</b>, for which <b>Brand</b> and/or <b>Stock Type</b> is required to be added.
					</td>
				</tr>
				<tr>
					<td align="center"><a class="btn default" data-toggle="modal" href="#search_manufacturer_dlg" id="br_search_manufacturer_btn">Search Manufacturer</a> <input type="text" id="br_manufacturer_acc_num" hidden="true"></td>
				</tr>
				<tr>
					<td align="center" colspan="2" id="br_manufacturer_name" style="font-weight: bold;">--</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Brand Registration</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table_new" style="width:;">
						<tr>
							<td colspan="2" align="center" valign="middle" id="br_reg_msg" hidden="true"></td>
							<td colspan="2" style="color: red" align="center" valign="middle" id="br_reg_err_msg"></td>
						</tr>
						<tr>
							<td width="232">Brand Name *</td>
							<td width="252"><input type="text" id="br_name" class="form-control" maxlength="200"></td>
						</tr>
						<tr>
							<td>Description</td>
							<td><input type="text" id="br_desc" class="form-control" maxlength="200"></td>
						</tr>
						<tr>
							<td>Properties</td>
							<td><input type="text" id="br_prop" class="form-control"></td>
						</tr>
						<tr>
							<td>Link</td>
							<td><input type="text" id="br_link" class="form-control"></td>
						</tr>
						<tr>
							<td colspan="2" style="border-bottom: none" align="center">
								<button class="btn blue abount" id="br_add_btn">Add Brand</button>
								<button class="btn default" id="br_reset_btn">Reset</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-6">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Stock Type Registration</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table_new" style="width:;">
						<tr>
							<td colspan="2" align="center" valign="middle" id="st_reg_msg" hidden="true"></td>
							<td colspan="2" style="color: red" align="center" valign="middle" id="st_reg_err_msg"></td>
						</tr>
						<tr>
							<td width="232">Brand Name *</td>
							<td width="252" id="st_brand_name">--</td>
						</tr>
						<tr>
							<td align="center" colspan="2"><a class="btn default" data-toggle="modal" href="#search_brand_dlg" id="st_search_brand_btn">Search Brand</a> <input type="text" id="st_brand_id" hidden="true"></td>
						</tr>
						<tr>
							<td>Stock Type Name *</td>
							<td><input type="text" id="st_name" class="form-control" maxlength="200"></td>
						</tr>
						<tr>
							<td>Product Category *</td>
							<td><input type="text" id="st_prod_category" class="form-control" maxlength="200" disabled="disabled"></td>
						</tr>
						<tr>
							<td align="center" colspan="2"><a class="btn default" data-toggle="modal" href="#select_prod_category_dlg" id="st_sel_prod_category_btn">Select Category</a></td>
						</tr>
						<tr>
							<td>Description</td>
							<td><input type="text" id="st_desc" class="form-control" maxlength="200"></td>
						</tr>
						<tr>
							<td>Properties</td>
							<td><input type="text" id="st_prop" class="form-control"></td>
						</tr>
						<tr>
							<td>Link</td>
							<td><input type="text" id="st_link" class="form-control"></td>
						</tr>
						<tr>
							<td colspan="2" style="border-bottom: none" align="center">
								<button class="btn blue aboutme" id="st_add_btn">Add Stock Type</button>
								<button class="btn default" id="st_reset_btn">Reset</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>