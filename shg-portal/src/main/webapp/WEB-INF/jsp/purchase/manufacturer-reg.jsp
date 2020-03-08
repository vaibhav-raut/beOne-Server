<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="m_reg_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="m_reg_err_msg"></td>
							<td align="right" width="180" id="m_reg_default_btns">
								<button type="button" class="btn blue aboutme" id="m_submit_btn">Submit</button>
								<button type="button" class="btn default" id="m_reset_btn">Reset</button>
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
				<h3 class="panel-title">Manufacturer Information</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table_new" style="width:;">
						<tr>
							<td width="232">Manufacturer Name *</td>
							<td width="252"><textarea id="m_name" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200"></textarea></td>
						</tr>
						<tr>
							<td>Description</td>
							<td><textarea id="m_desc" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="500"></textarea></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Contact Information</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table_new" style="width:;">
						<tr>
							<td width="232">Primary Mobile *</td>
							<td width="252"><input type="text" id="m_pri_mobile" class="form-control" maxlength="15"></td>
						</tr>
						<tr>
							<td>Secondary Mobile</td>
							<td><input type="text" id="m_sec_mobile" class="form-control" maxlength="15"></td>
						</tr>
						<tr>
							<td>Phone</td>
							<td><input type="text" id="m_phone" class="form-control" maxlength="15"></td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input type="text" id="m_email" class="form-control" maxlength="40"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="col-md-6">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Address</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table_new" style="width:;">
						<tr>
							<td width="232">Address *</td>
							<td width="252"><textarea id="m_address" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="100"></textarea></td>
						</tr>
						<tr>
							<td>Village</td>
							<td><input type="text" id="m_village" class="form-control" maxlength="25"></td>
						</tr>
						<tr>
							<td>Gram Panchayat</td>
							<td><input type="text" id="m_panchayat" class="form-control" maxlength="25"></td>
						</tr>
						<tr>
							<td>Tehsil</td>
							<td><input type="text" id="m_tehsil" class="form-control" maxlength="25"></td>
						</tr>
						<tr>
							<td>Pin Code *</td>
							<td><input type="number" id="m_pin_code" class="form-control" maxlength="8"></td>
						</tr>
						<tr>
							<td colspan="2" align="center"><a class="btn default" data-toggle="modal" href="#select_district_dlg" id="m_sel_district_btn">Select District</a></td>
						</tr>
						<tr>
							<td>District</td>
							<td><input class="form-control" id="m_district" disabled="disabled" placeholder="District"></td>
						</tr>
						<tr>
							<td>State</td>
							<td><input class="form-control" id="m_state" disabled="disabled" placeholder="State"></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>