<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-responsive">
				<table class="table">
					<tr>
						<td style="color: red" align="left" valign="middle" id="sel_grp_err_msg">${errMsg}</td>
					</tr>
				</table>
			</div>
			<div id="sel_grp_form">
				<form name="selGrpForm" method="post">
					<table class="table_new" style="width:;">
						<tr>
							<td align="right">Group Account Number</td>
							<td width="225" align="right">
								<div class="input-group">
									<input type="text" class="form-control" name="district" id="sel_grp_district" placeholder="MH01" style="width: 75px; margin-right: 10px;" value="${district}">
									<input type="text" class="form-control" name="groupid" id="sel_grp_groupid" placeholder="10001" style="width: 100px; margin-right: 10px;" value="${groupid}">
									<label style="width: 10px;"> or </label>
								</div>
							</td>
							<td rowspan="3" colspan="3" style="padding:0px;">
								<table class="table_new">
									<tr>
										<td width="250"><input type="text" class="form-control" id="sel_grp_name" placeholder="Group Name"></td>
										<td><a class="btn blue" data-toggle="modal" href="#modal_dialog" id="sel_grp_search_btn">Search</a></td>
										<td></td>
									</tr>
									<tr id="s_grp_header" hidden="true">
										<td align="center"><strong>Account No.</strong></td>
										<td><strong>Group Name</strong></td>
										<td><strong>Place</strong></td>
									</tr>
									<tr>
										<td id="s_grp_num" align="center">Search group to verify</td>
										<td id="s_grp_name"></td>
										<td id="s_grp_place"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">Password</td>
							<td><input type="password" class="form-control" name="password" id="sel_grp_password" placeholder="********"></td>
						</tr>
						<tr>
							<td></td>
							<td><button type="button" id="sel_grp_btn" class="btn blue">Select Group</button>
								<button type="reset" id="sel_grp_reset_btn" class="btn theme-btn pull-right">Reset</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
</html>