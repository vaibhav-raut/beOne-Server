<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="gedit_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="gedit_err_msg"></td>
							<td align="right" width="200" id="gedit_btns">
								<button type="button" class="btn green aboutme" id="gedit_update_btn" disabled="disabled">Update</button>
								<a class='btn default' href='#g_profile_content' data-toggle="tab" id="gedit_discard_btn">Back</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="gedit_details">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">General Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Group Type</td>
								<td width="252"><select class="form-control" id="g_types" disabled="disabled">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>Date of Establishment *</td>
								<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="g_doe" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Group Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr id="grp_name_row">
								<td width="232">Group Name *</td>
								<td width="252"><textarea id="g_name" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200"></textarea></td>
							</tr>
							<tr>
								<td>Vision</td>
								<td><textarea id="g_vision" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="500"></textarea></td>
							</tr>
							<tr>
								<td>Description</td>
								<td><textarea id="g_desc" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="500"></textarea></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="g_bank_acc_div">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Account Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Account Number *</td>
								<td width="252"><input type="text" id="g_acc_num" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Account Name *</td>
								<td><input type="text" id="g_acc_name" class="form-control" maxlength="50"></td>
							</tr>
							<tr>
								<td>Bank Account Type</td>
								<td><select id="g_acc_type" class="form-control">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#search_bank_dialog" id="gedit_bank_search">Search Bank</a>
									<input type="text" id="gedit_bank_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td>Bank Name</td>
								<td id="gedit_bank_name"> -- </td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="gedit_branch_name"> -- </td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="gedit_ifsc_code"> -- </td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="g_bprofile_div" hidden="true">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Bank Name *</td>
								<td width="252"><input type="text" id="gbp_name" class="form-control" maxlength="50"></td>
							</tr>
							<tr>
								<td>Branch Name *</td>
								<td><input type="text" id="gbp_branch_name" class="form-control" maxlength="50"></td>
							</tr>
							<tr>
								<td>IFSC Code *</td>
								<td><input type="text" id="gbp_ifsc_code" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Bank Rating</td>
								<td><input type="text" id="gbp_rating" class="form-control" maxlength="50"></td>
							</tr>
							<tr>
								<td>Fixed Deposit Interest Rate</td>
								<td><input type="text" id="gbp_fd_rate" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Loan Interest Rate</td>
								<td><input type="text" id="gbp_loan_rate" class="form-control" maxlength="50"></td>
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
								<td width="252"><textarea id="g_address" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="100"></textarea></td>
							</tr>
							<tr>
								<td>Village</td>
								<td><input type="text" id="g_village" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Gram Panchayat</td>
								<td><input type="text" id="g_panchayat" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Tehsil</td>
								<td><input type="text" id="g_tehsil" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Pin Code *</td>
								<td><input type="text" id="g_pin_code" class="form-control" maxlength="8"></td>
							</tr>
							<tr>
								<td>District</td>
								<td><select class="form-control" id="g_district">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>State</td>
								<td><select class="form-control" id="g_state">
										<option>Maharashtra</option>
								</select></td>
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
								<td width="252"><input type="text" id="g_pri_mobile" class="form-control" maxlength="15"></td>
							</tr>
							<tr>
								<td>Secondary Mobile</td>
								<td><input type="text" id="g_sec_mobile" class="form-control" maxlength="15"></td>
							</tr>
							<tr>
								<td>Phone</td>
								<td><input type="text" id="g_phone" class="form-control" maxlength="15"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type="text" id="g_email" class="form-control" maxlength="40"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>