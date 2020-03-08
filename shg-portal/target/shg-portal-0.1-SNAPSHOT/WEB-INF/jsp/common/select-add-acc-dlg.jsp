<html lang="en" class="no-js">
<div id="sel_add_acc_dlg" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<table class="table" id="sel_acc_content">
					<tr>
						<td align="center" style="border-bottom: none" colspan="2"><strong class="label label-green">Select Account</strong></td>
					</tr>
					<tr>
						<td width="232">Account Number</td>
						<td><select class="form-control" id="sel_acc_account">
								<option></option>
						</select></td>
					</tr>
				</table>
				<table class="table" id="add_acc_content" hidden="true">
					<tr>
						<td align="center" style="border-bottom: none" colspan="2"><strong class="label label-green">Add New Account</strong></td>
					</tr>
					<tr>
						<td width="232">Account Number</td>
						<td><input type="text" id="add_acc_num" class="form-control" maxlength="25"></td>
					</tr>
					<tr>
						<td>Account Name</td>
						<td><input type="text" id="add_acc_name" class="form-control" maxlength="50"></td>
					</tr>
					<tr>
						<td>Bank Account Type</td>
						<td id="sel_add_acc_type"> -- </td>
					</tr>
					<tr>
						<td>Bank Name
							<input type="text" id="add_acc_bank_id" hidden="true">
						</td>
						<td id="add_acc_bank_name">--</td>
					</tr>
					<tr>
						<td>Branch Name</td>
						<td id="add_acc_branch_name">--</td>
					</tr>
					<tr>
						<td>IFSC Code</td>
						<td id="add_acc_ifsc_code">--</td>
					</tr>
				</table>
				<table class="table" id="sel_acc_dialog_table_body">
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn blue aboutme" id="sel_acc_btn">Select</button>
				<button type="button" class="btn yellow aboutme" id="show_add_acc_content">Add New</button>
				<button type="button" class="btn blue aboutme" id="add_acc_btn" hidden="true">Add</button>
				<button type="button" class="btn yellow aboutme" id="show_sel_acc_content" hidden="true">Select Existing</button>
				<button type="button" data-dismiss="modal" class="btn default" id="sel_add_ok_btn">OK</button>
			</div>
		</div>
	</div>
</div>
</html>