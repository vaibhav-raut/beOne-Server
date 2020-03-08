<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="gp_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="gp_err_msg"></td>
							<td align="right" width="50">
								<a class='btn blue aboutme' href='#gedit_profile_content' data-toggle="tab" id="gp_edit_btn">Edit</a>
							</td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="gp_print"> Print </a></li>
										<li><a href="#" id="gp_pdf"> Save as PDF </a></li>
										<li><a href="#" id="gp_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="gp_details" hidden="true">
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
								<td width="252" id="gp_group_type"></td>
							</tr>
							<tr>
								<td>Date of Establishment</td>
								<td id="gp_doe"></td>
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
							<tr>
								<td width="232">Group Name</td>
								<td width="252" id="gp_group_name"></td>
							</tr>
							<tr>
								<td>Vision</td>
								<td id="gp_group_vision"></td>
							</tr>
							<tr>
								<td>Description</td>
								<td id="gp_group_desc"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="gp_bank_acc_div">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Account Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Account Number</td>
								<td width="252" id="gp_acc_num"></td>
							</tr>
							<tr>
								<td>Account Name</td>
								<td id="gp_acc_name"></td>
							</tr>
							<tr>
								<td>Bank Account Type</td>
								<td id="gp_acc_type"></td>
							</tr>
							<tr>
								<td>Bank Name </td>
								<td id="gp_bank_name"></td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="gp_branch_name"></td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="gp_ifsc_code"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary" id="gp_bprofile_div" hidden="true">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Bank Name</td>
								<td width="252" id="gp_bp_name"></td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="gp_bp_branch_name"></td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="gp_bp_ifsc_code"></td>
							</tr>
							<tr>
								<td>Bank Rating</td>
								<td id="gp_bp_rating"></td>
							</tr>
							<tr>
								<td>Fixed Deposit Interest Rate</td>
								<td id="gp_bp_fd_rate"></td>
							</tr>
							<tr>
								<td>Loan Interest Rate</td>
								<td id="gp_bp_loan_rate"></td>
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
								<td width="232">Address</td>
								<td width="252" id="gp_address"></td>
							</tr>
							<tr>
								<td>Village</td>
								<td id="gp_village"></td>
							</tr>
							<tr>
								<td>Gram Panchayat</td>
								<td id="gp_panchayat"></td>
							</tr>
							<tr>
								<td>Tehsil</td>
								<td id="gp_tehsil"></td>
							</tr>
							<tr>
								<td>Pin Code</td>
								<td id="gp_pin_code"></td>
							</tr>
							<tr>
								<td>District</td>
								<td id="gp_district"></td>
							</tr>
							<tr>
								<td>State</td>
								<td id="gp_state"></td>
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
								<td width="252" id="gp_pri_mobile"></td>
							</tr>
							<tr>
								<td>Secondary Mobile</td>
								<td id="gp_sec_mobile"></td>
							</tr>
							<tr>
								<td>Phone</td>
								<td id="gp_phone"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td id="gp_email"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" id="g_attachment_div">
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