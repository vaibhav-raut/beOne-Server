<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="g_reg_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="g_reg_err_msg"></td>
							<td align="right" width="180" id="g_reg_default_btns">
								<button type="button" class="btn blue aboutme" id="g_submit_btn">Submit</button>
								<button type="button" class="btn default" id="g_reset_btn">Reset</button>
							</td>
							<td align="right" width="240" id="g_reg_approval_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="g_reg_approve_btn">Approve</button>
								<button type="button" class="btn yellow aboutme" id="g_reg_reject_btn">Reject</button>
								<button type="button" class="btn blue aboutme" id="g_reg_hold_btn">Hold</button>
							</td>
							<td width="80" id="g_reg_activate_btn" hidden="true"><a class='btn yellow aboutme' href='#g_app_activation_content' data-toggle="tab" id="g_go2activate_btn">Go to Activate</a></td>
							<td width="50" id="g_reg_back_btn" hidden="true"><a class='btn default' href='#app_track_content' data-toggle="tab" id="g_back2apptrac_btn1">Back</a></td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="g_reg_print"> Print </a></li>
										<li><a href="#" id="g_reg_pdf"> Save as PDF </a></li>
										<li><a href="#" id="g_reg_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="g_reg_details">
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
								<td width="252"><select class="form-control" id="group_types">
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
								<td width="252"><textarea id="group_name" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200"></textarea></td>
							</tr>
							<tr>
								<td width="232">Vision</td>
								<td width="252"><textarea id="group_vision" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="500"></textarea></td>
							</tr>
							<tr>
								<td>Description</td>
								<td><textarea id="group_desc" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="500"></textarea></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="group_rules_div">
				<div class="panel-heading">
					<h3 class="panel-title">Group Rules</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Planned Monthly Saving *</td>
								<td width="252"><input type="text" id="plan_m_saving" class="form-control"></td>
							</tr>
							<tr>
								<td>Allow Associate Member</td>
								<td><select class="form-control" id="allow_associate">
										<option value="0">No</option>
										<option value="1">Yes</option>
								</select></td>
							</tr>
							<tr>
								<td>Interest Rate on Saving *</td>
								<td><input type="text" id="s_interest" class="form-control"></td>
							</tr>
							<tr>
								<td>Interest Rate on Loan *</td>
								<td><input type="text" id="l_interest" class="form-control"></td>
							</tr>
							<tr>
								<td>Loan Processing Fee Percent *</td>
								<td><input type="text" id="l_fee_percent" class="form-control"></td>
							</tr>
							<tr>
								<td>Registration Fee *</td>
								<td><input type="text" id="reg_fee" class="form-control"></td>
							</tr>
							<tr>
								<td>Late Payment Fee *</td>
								<td><input type="text" id="late_fee" class="form-control"></td>
							</tr>
							<tr>
								<td>Cheque Bouncing Penalty *</td>
								<td><input type="text" id="cheque_b_fee" class="form-control"></td>
							</tr>
							<tr>
								<td>Application Fee *</td>
								<td><input type="text" id="app_fee" class="form-control"></td>
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
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#select_district_dlg" id="g_sel_district_btn">Select District</a>
								</td>
							</tr>
							<tr>
								<td>District</td>
								<td><input class="form-control" id="g_district" disabled="disabled" placeholder="District"></td>
							</tr>
							<tr>
								<td>State</td>
								<td><input class="form-control" id="g_state" disabled="disabled" placeholder="State"></td>
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
									<a class="btn default" data-toggle="modal" href="#search_bank_dialog" id="greg_bank_search">Search Bank</a>
									<input type="text" id="greg_bank_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td>Bank Name</td>
								<td id="g_bank_name"> -- </td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="g_branch_name"> -- </td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="g_ifsc_code"> -- </td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-12" id="g_attachment_div" hidden="true">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Attachments</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" style="width:;">
							<tr>
								<td align="right"><a class='btn default' href='#doc_upload_dlg' data-toggle="modal" id="g_upload_file_btn">Upload Document</a></td>
							</tr>
						</table>
						<table class="table" id="g_attachments_table" style="width:;">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>