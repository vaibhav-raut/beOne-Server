<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="reg_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="reg_err_msg"></td>
							<td align="right" width="180" id="reg_default_btns">
								<button type="button" class="btn blue aboutme" id="submit_btn">Submit</button>
								<button type="button" class="btn default" id="reset_btn">Reset</button>
							</td>
							<td align="right" width="240" id="reg_approval_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="reg_approve_btn">Approve</button>
								<button type="button" class="btn yellow aboutme" id="reg_reject_btn">Reject</button>
								<button type="button" class="btn blue aboutme" id="reg_hold_btn">Hold</button>
							</td>
							<td width="80" id="reg_activate_btn" hidden="true"><a class='btn yellow aboutme' href='#app_activation_content' data-toggle="tab" id="go2activate_btn">Go to Activate</a></td>
							<td width="50" id="reg_back_btn" hidden="true"><a class='btn default' href='#app_track_content' data-toggle="tab" id="back2apptrac_btn1">Back</a></td>
							<td width="80" id="go2mclosure_btns" hidden="true"><a class='btn yellow aboutme' href='#app_activation_content' data-toggle="tab" id="go2mclosure_btn">Go to Close Account</a></td>
							<td width="50" id="mclosure_back_btn" hidden="true"><a class='btn default' href='#app_closure_content' data-toggle="tab" id="back2closure_btn1">Back</a></td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="m_reg_print"> Print </a></li>
										<li><a href="#" id="m_reg_pdf"> Save as PDF </a></li>
										<li><a href="#" id="m_reg_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="m_reg_details">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">General Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Role *</td>
								<td width="252" colspan="2"><select class="form-control" id="roles">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>Recommended By Member</td>
								<td><input type="text" class="form-control" id="recommend_by_m_name"> <input type="hidden" id="recommend_by_m_num"></td>
								<td width="50"><a data-toggle="modal" href="#modal_dialog" id="m_name_search" class="btn default"><i class="fa fa-search"></i></a></td>
							</tr>
							<tr>
								<td>Gender *</td>
								<td colspan="2"><select class="form-control" id="gender">
										<option>Male</option>
										<option>Female</option>
								</select></td>
							</tr>
							<tr>
								<td>Date of Enrollment *</td>
								<td colspan="2"><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="doe" /></td>
							</tr>
							<tr>
								<td>Date of Birth</td>
								<td colspan="2"><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/1980" id="dob" /></td>
							</tr>
							<tr>
								<td>Date of Anniversary</td>
								<td colspan="2"><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2000" id="doa" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="saving_info_div" hidden="true">
				<div class="panel-heading">
					<h3 class="panel-title">Saving Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Monthly Saving *</td>
								<td width="252"><input type="text" id="monthly_saving" class="form-control"></td>
							</tr>
							<tr>
								<td>No. of Planned Installment</td>
								<td><input type="text" id="planned_inst_num" class="form-control"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="bank_info_div" hidden="true">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Account Information (Optional)</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Account Number *</td>
								<td width="252"><input type="text" id="acc_num" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Account Name *</td>
								<td><input type="text" id="acc_name" class="form-control" maxlength="50"></td>
							</tr>
							<tr>
								<td>Bank Account Type</td>
								<td><select id="acc_type" class="form-control">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#search_bank_dialog" id="mreg_bank_search">Search Bank</a>
									<input type="text" id="mreg_bank_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td>Bank Name</td>
								<td id="mreg_bank_name"> -- </td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="mreg_branch_name"> -- </td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="mreg_ifsc_code"> -- </td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Personal Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Title</td>
								<td width="252"><select class="form-control" id="name_title">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>First Name *</td>
								<td><input type="text" id="first_name" class="form-control" maxlength="20"></td>
							</tr>
							<tr>
								<td>Middle Name</td>
								<td><input type="text" id="middle_name" class="form-control" maxlength="20"></td>
							</tr>
							<tr>
								<td>Last Name *</td>
								<td><input type="text" id="last_name" class="form-control" maxlength="20"></td>
							</tr>
							<tr>
								<td>Address *</td>
								<td><textarea id="address" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="100"></textarea></td>
							</tr>
							<tr>
								<td>Village</td>
								<td><input type="text" id="village" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Gram Panchayat</td>
								<td><input type="text" id="panchayat" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Tehsil</td>
								<td><input type="text" id="tehsil" class="form-control" maxlength="25"></td>
							</tr>
							<tr>
								<td>Pin Code</td>
								<td><input type="text" id="pin_code" class="form-control" maxlength="8"></td>
							</tr>
							<tr>
								<td>District</td>
								<td><select class="form-control" id="district">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>State</td>
								<td><select class="form-control" id="state">
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
								<td width="232">Primary Mobile</td>
								<td width="252"><input type="text" id="pri_mobile" class="form-control" maxlength="15"></td>
							</tr>
							<tr>
								<td>Secondary Mobile</td>
								<td><input type="text" id="sec_mobile" class="form-control" maxlength="15"></td>
							</tr>
							<tr>
								<td>Phone</td>
								<td><input type="text" id="phone" class="form-control" maxlength="15"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type="text" id="email" class="form-control" maxlength="40"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-12" id="m_attachment_div" hidden="true">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Attachments</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" style="width:;">
							<tr>
								<td align="right"><a class='btn default' href='#doc_upload_dlg' data-toggle="modal" id="m_upload_file_btn">Upload Document</a></td>
							</tr>
						</table>
						<table class="table" id="m_attachments_table" style="width:;">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>