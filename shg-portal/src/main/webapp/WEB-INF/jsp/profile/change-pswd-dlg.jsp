<html lang="en" class="no-js">
<div id="pswd_dialog" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<table class="table">
					<thead>
						<tr>
							<td colspan="2" align="center" style="border-bottom: none"><strong class="label label-green">Change Password</strong></td>
						</tr>
						<tr>
							<td colspan="2" id="msg" align="center"></td>
							<td colspan="2" id="err_msg" align="center" style="color: red;" hidden="true"></td>
						</tr>
					</thead>
					<tbody id="pswd_dialog_body">
							<tr>
								<td>Old Password</td>
								<td><input type="password" class="form-control" id="old_pswd" placeholder="Old Password" maxlength="20"></td>
							</tr>
							<tr>
								<td>New Password</td>
								<td width="400"><input type="password" class="form-control" id="new_pswd" placeholder="New Password" maxlength="20"></td>
							</tr>
							<tr>
								<td>Repeat Password</td>
								<td><input type="password" class="form-control" id="repeat_pswd" placeholder="Repeat Password" maxlength="20"></td>
							</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn green aboutme" id="change_pswd_btn">Change Password</button>
				<button type="button" class="btn default" id="reset_pswd_form_btn">Reset</button>
				<button type="button" data-dismiss="modal" id="okcancel_pswd_btn" class="btn default">Cancel</button>
			</div>
		</div>
	</div>
</div>
</html>