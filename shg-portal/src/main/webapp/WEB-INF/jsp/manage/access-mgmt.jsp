<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td colspan="3" id="am_msg"></td>
							<td colspan="3" id="am_err_msg" hidden="true" style="color: red;"></td>
						</tr>
						<tr>
							<td width="200">Search Member by Name</td>
							<td width="250">
								<div class="input-icon right">
									<a data-toggle="modal" href="#modal_dialog" id="am_member_search"><i class="fa fa-search"></i></a> 
									<input type="text" class="form-control" id="member_name" placeholder="Member Name">
								</div>
							</td>
							<td align="right" valign="middle"><a href="#" id="clear_search" hidden="true">Clear</a></td>
						</tr>
					</table>
					<table class="table" hidden="true" id="member_parent_access_table">
						<tr>
							<td>Member Name</td>
							<td><strong id="cur_member_name"> -- </strong></td>
							<td>Account Number</td>
							<td colspan="2"><strong id="cur_member_acc_num"> -- </strong></td>
						</tr>
						<tr>
							<td>Member Role</td>
							<td><strong id="cur_mrole"> -- </strong></td>
							<td>Member Status</td>
							<td colspan="2"><strong id="cur_member_status"> -- </strong></td>
						</tr>
						<tr>
							<td>New Member Role</td>
							<td>
								<select class="form-control" id="new_mrole"><option></option></select>
							</td>
							<td>New Member Status</td>
							<td>
								<select class="form-control" id="new_member_status"><option></option></select>
							</td>
							<td align="right">
								<button id="update_member_parent_access_btn" type="button" class="btn blue">Update Access</button>
							</td>
						</tr>
					</table>
					<table class="table" hidden="true" id="search_group_table">
						<tr><td colspan="4"></td></tr>
						<tr>
							<td width="260">Search Group for Member Access</td>
							<td width="250">
								<div class="input-group">
									<input type="text" class="form-control" id="dcode_am" placeholder="MH01" style="width: 75px; margin-right: 10px;">
									<input type="text" class="form-control" id="groupid_am" placeholder="10001" style="width: 100px; margin-right: 10px; margin-left: 25px;">
									<label style="width: 10px;"> or </label>
								</div>
							</td>
							<td>
								<div class="input-group">
									<input type="text" class="form-control" id="groupname_am" placeholder="Group Name" style="width: 225px; margin-right: 10px;">
									<a class="btn default" data-toggle="modal" href="#modal_dialog" id="s_grp_am_btn">Search Group</a>
								</div>
							</td>
							<td align="right" valign="middle"><a href="#" id="clear_group_search" hidden="true">Clear</a></td>
						</tr>
					</table>
					<table class="table" hidden="true" id="member_access_to_group_table">
						<tr>
							<td>Group Name</td>
							<td><strong id="cur_group_name"> -- </strong></td>
							<td>Account Number</td>
							<td colspan="2"><strong id="cur_group_acc_num"> -- </strong></td>
						</tr>
						<tr>
							<td>Group Type</td>
							<td><strong id="cur_group_type"> -- </strong></td>
							<td>Group Status</td>
							<td colspan="2"><strong id="cur_group_status"> -- </strong></td>
						</tr>
						<tr>
							<td>Member Role</td>
							<td colspan="4"><strong id="cur_mrole_to_group"> -- </strong></td>
						</tr>
						<tr>
							<td>New Member Role</td>
							<td>
								<select class="form-control" id="new_mrole_to_group"><option></option></select>
							</td>
							<td align="right">
								<button id="add_member_access_to_group_btn" type="button" class="btn blue" disabled="disabled">Add Access</button>
							</td>
							<td align="right" width="100">
								<button id="update_member_access_to_group_btn" type="button" class="btn blue" disabled="disabled">Update Access</button>
							</td>
							<td align="right" width="100">
								<button id="remove_member_access_to_group_btn" type="button" class="btn blue" disabled="disabled">Remove Access</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="am_details" hidden="true">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Selected Groups</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table table-striped table-bordered table-hover" id="grp_names_table">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>