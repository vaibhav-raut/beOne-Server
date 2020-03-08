<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="medit_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="medit_err_msg"></td>
							<td align="right" width="200" id="p_edit_btns">
								<button type="button" class="btn green aboutme" id="p_update_btn" disabled="disabled">Update</button>
								<a class='btn default' href='#profile_content' data-toggle="tab" id="p_discard_btn">Back</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="medit_details">
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
								<td width="252" id="medit_role" colspan="2"></td>
							</tr>
							<tr>
								<td>Recommended By Member</td>
								<td><input type="text" class="form-control" id="recommend_by_m_name"> <input type="hidden" id="recommend_by_m_num"></td>
								<td><a data-toggle="modal" href="#modal_dialog" id="m_name_search" class="btn default"><i class="fa fa-search"></i></a></td>
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
									<a class="btn default" data-toggle="modal" href="#search_bank_dialog" id="medit_bank_search">Search Bank</a>
									<input type="text" id="medit_bank_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td>Bank Name</td>
								<td id="medit_bank_name"> -- </td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="medit_branch_name"> -- </td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="medit_ifsc_code"> -- </td>
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
								<td width="252"><input type="text" id="pri_mobile" class="form-control"></td>
							</tr>
							<tr>
								<td>Secondary Mobile</td>
								<td><input type="text" id="sec_mobile" class="form-control"></td>
							</tr>
							<tr>
								<td>Phone</td>
								<td><input type="text" id="phone" class="form-control"></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type="text" id="email" class="form-control"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>