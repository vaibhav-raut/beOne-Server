<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td id="p_msg" hidden="true"></td>
							<td style="color: red" id="p_err_msg"></td>
							<td width="80" align="right">
								<a class="btn default" data-toggle="tab" href="#mprofiling_content" id="p_mprofiling_btn">Detailed Profile</a>
							</td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="p_print"> Print </a></li>
										<li><a href="#" id="p_pdf"> Save as PDF </a></li>
										<li><a href="#" id="p_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
					<table class="table">
						<tr>
							<td id="p_member_info"></td>
							<td align="right" width="250">
								<input type="text" class="form-control" id="member_name" placeholder="Member Name" hidden="true">
							</td>
							<td width="50" id="m_profile_search" hidden="true">
								<a data-toggle="modal" href="#modal_dialog" class="btn default"><i class="fa fa-search"></i></a>
							</td>
							<td width="5"><a href="#" id="clear_search" hidden="true"><i class="fa fa-times"></i></a></td>
							<td width="50" align="right">
								<a class='btn blue aboutme' href='#edit_profile_content' data-toggle="tab" id="p_edit_btn">Edit</a>
							</td>
							<td width="80" align="right">
								<a class="btn default" data-toggle="modal" href="#pswd_dialog" id="p_change_pswd_btn" hidden="true">Change Password</a>
								<button class="btn default" disabled="disabled" id="p_reset_pswd_btn" hidden="true">Reset Password</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="p_details" hidden="true">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">General Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Role</td>
								<td width="252" id="p_role"></td>
							</tr>
							<tr>
								<td>Recommended By Member</td>
								<td id="p_r_by_m_name"></td>
							</tr>
							<tr>
								<td>Gender</td>
								<td id="p_gender"></td>
							</tr>
							<tr>
								<td>Date of Enrollment</td>
								<td id="p_doe"></td>
							</tr>
							<tr>
								<td>Date of Birth</td>
								<td id="p_dob"></td>
							</tr>
							<tr>
								<td>Date of Anniversary</td>
								<td id="p_doa"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="p_saving_info_div" hidden="true">
				<div class="panel-heading">
					<h3 class="panel-title">Saving Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Monthly Saving</td>
								<td width="252" id="p_monthly_saving"></td>
							</tr>
							<tr>
								<td>No. of Planned Installment</td>
								<td id="p_planned_inst_num"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="p_bank_info_div" hidden="true">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Account Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Account Number</td>
								<td width="252" id="p_acc_num"></td>
							</tr>
							<tr>
								<td>Account Name</td>
								<td id="p_acc_name"></td>
							</tr>
							<tr>
								<td>Bank Account Type</td>
								<td id="p_acc_type"></td>
							</tr>
							<tr>
								<td>Bank Name</td>
								<td id="p_bank_name"></td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="p_branch_name"></td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="p_ifsc_code"></td>
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
								<td width="252" id="p_name_title"></td>
							</tr>
							<tr>
								<td>First Name</td>
								<td id="p_first_name"></td>
							</tr>
							<tr>
								<td>Middle Name</td>
								<td id="p_middle_name"></td>
							</tr>
							<tr>
								<td>Last Name</td>
								<td id="p_last_name"></td>
							</tr>
							<tr>
								<td>Address</td>
								<td id="p_address"></td>
							</tr>
							<tr>
								<td>Village</td>
								<td id="p_village"></td>
							</tr>
							<tr>
								<td>Gram Panchayat</td>
								<td id="p_panchayat"></td>
							</tr>
							<tr>
								<td>Tehsil</td>
								<td id="p_tehsil"></td>
							</tr>
							<tr>
								<td>Pin Code</td>
								<td id="p_pin_code"></td>
							</tr>
							<tr>
								<td>District</td>
								<td id="p_district"></td>
							</tr>
							<tr>
								<td>State</td>
								<td id="p_state"></td>
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
								<td width="252" id="p_pri_mobile"></td>
							</tr>
							<tr>
								<td>Secondary Mobile</td>
								<td id="p_sec_mobile"></td>
							</tr>
							<tr>
								<td>Phone</td>
								<td id="p_phone"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td id="p_email"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" id="m_attachment_div">
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