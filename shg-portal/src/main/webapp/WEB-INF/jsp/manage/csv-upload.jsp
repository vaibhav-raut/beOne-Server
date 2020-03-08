<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Add Group</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table">
						<tr>
							<td><a class="btn default" data-toggle="modal" href="#select_district_dlg" id="csv_sel_district_btn">Select District</a></td>
							<td width="200"><input class="form-control" id="state_name" placeholder="State" disabled="disabled"></td>
							<td width="200"><input class="form-control" id="district_name" placeholder="District" disabled="disabled"></td>
							<td width="300"><input type="file" id="group_csv_file" class="form-control"></td>
							<td width="50"><button id="upload_grp_csv_btn" class="btn blue">Upload</button></td>
							<td><button id="reset_grp_csv_btn" class="btn default">Reset</button></td>
						</tr>
						<tr>
							<td colspan="6" align="center" id="g_csv_msg">Please select district and upload group CSV file to create group.</td>
						</tr>
					</table>
					<table class="table table-striped table-bordered table-hover" id="grp_upload_result">
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Import Group Data</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table">
						<tr>
							<td width="250" align="right">
								<div class="input-group">
									<input type="text" class="form-control" id="dcode_csv" placeholder="MH01" style="width: 75px; margin-right: 10px;"> <input type="text" class="form-control" id="groupid_csv" placeholder="10001" style="width: 100px; margin-right: 10px;"> <label
										style="width: 10px;"> or </label>
								</div>
							</td>
							<td width="300"><input type="text" class="form-control" id="groupname_csv" placeholder="Group Name"></td>
							<td><a class="btn blue" data-toggle="modal" href="#modal_dialog" id="s_grp_csv_btn">Search</a></td>
							<td colspan="2" id="group_detail_csv" align="center">No group selected</td>
						</tr>
						<tr>
							<td><select class="form-control" id="csv_update_type">
									<option></option>
							</select></td>
							<td><input type="file" id="member_csv_file" class="form-control"></td>
							<td width="50"><button id="upload_member_csv_btn" class="btn blue">Upload</button></td>
							<td width="50"><button id="reset_member_csv_btn" class="btn default">Reset</button></td>
							<td>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="m_csv_print"> Print </a></li>
										<li><a href="#" id="m_csv_pdf"> Save as PDF </a></li>
										<li><a href="#" id="m_csv_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="5" align="center" id="m_csv_msg">Please search a group, select CSV file type and upload CSV file to import group data.</td>
						</tr>
					</table>
				</div>
				<div id="m_csv_details">
					<table class="table table-striped table-bordered table-hover" id="member_upload_result">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>