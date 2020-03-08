<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="invoice_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="invoice_err_msg"></td>
							<td align="right" width="180" id="invoice_default_btns">
								<button type="button" class="btn blue aboutme" id="invoice_submit_btn">Submit</button>
								<button type="button" class="btn default" id="invoice_reset_btn">Reset</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Invoice</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table" style="width:;">
						<tr>
							<td>Manufacturer Name</td>
							<td colspan="5" id="manufacturer_name">--</td>
							<td align="right">
								<a class="btn default" data-toggle="modal" href="#search_manufacturer_dlg" id="search_manufacturer_btn">Search Manufacturer</a>
								<input type="text" id="manufacturer_acc_num" hidden="true">
							</td>
						</tr>
						<tr>
							<td width="175">Invoice Number *</td>
							<td width="275" colspan="2"><input type="text" class="form-control" id="invoice_number"></td>
							<td width="50">&nbsp;</td>
							<td width="175">Invoice Status</td>
							<td width="275" colspan="2"><select id="invoice_status" class="form-control" disabled="disabled">
									<option>Draft</option>
							</select></td>
						</tr>
						<tr>
							<td>Description</td>
							<td colspan=6><input type="text" id="description" class="form-control" maxlength="100"></td>
						</tr>
						<tr>
							<td>No Of Lots *</td>
							<td colspan="2"><input type="number" class="form-control" id="num_of_lots"></td>
							<td width="30">&nbsp;</td>
							<td>No Of Items *</td>
							<td colspan="2"><input type="number" class="form-control" id="num_of_items"></td>
						</tr>
						<tr>
							<td>Total Amount *</td>
							<td colspan="2"><input type="number" class="form-control" id="total_amt"></td>
							<td width="30">&nbsp;</td>
							<td>VAT</td>
							<td width="130"><input type="number" class="form-control" id="vat_percent" placeholder="VAT %"></td>
							<td id="vat">Rs. --</td>
						</tr>
						<tr>
							<td>Discount</td>
							<td width="130"><input type="number" class="form-control" id="discount_percent" placeholder="Discount %"></td>
							<td id="discount">Rs. --</td>
							<td width="30">&nbsp;</td>
							<td>Other Taxes</td>
							<td colspan="2"><input type="number" class="form-control" id="other_taxes"></td>
						</tr>
						<tr>
							<td>GrossTotal</td>
							<td id="gross_total" colspan="2">Rs. --</td>
							<td width="30">&nbsp;</td>
							<td>Net Total Calculated</td>
							<td id="net_total" colspan="2">Rs. --</td>
						</tr>
					</table>
					<table class="table" id="lot_table" style="width:;">
						<tr>
							<td align="center">No lots added to invoice</td>
						</tr>
					</table>
					<table class="table" style="width:;">
						<tr>
							<td colspan="5" align="center"><strong>Fill lot details and click on Add Lot to add it to invoice.</strong></td>
						</tr>
						<tr>
							<td>Stock Type</td>
							<td colspan="3" id="stock_type">--</td>
							<td align="right">
								<a class="btn default" data-toggle="modal" href="#search_stock_type_dlg" id="search_stock_type_btn">Search Stock Type</a>
								<input type="text" id="stock_type_id" hidden="true">
							</td>
						</tr>
						<tr>
							<td width="175">Number per set *</td>
							<td width="275"><input type="number" class="form-control" id="number_per_set" /></td>
							<td width="50">&nbsp;</td>
							<td width="175">Number of set *</td>
							<td width="275"><input type="number" class="form-control" id="number_of_set" /></td>
						</tr>
						<tr>
							<td>Item Price *</td>
							<td><input type="number" class="form-control" id="item_price" /></td>
							<td width="30">&nbsp;</td>
							<td>Lot Total</td>
							<td id="lot_total">Rs. --</td>
						</tr>
						<tr>
							<td colspan="5" align="center">
								<button class="btn default" id="add_lot_btn">Add Lot to Invoice</button>
							</td>
						</tr>
						<tr>
							<td colspan="5" style="color: red" align="center" valign="middle" id="add_lot_err_msg"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>