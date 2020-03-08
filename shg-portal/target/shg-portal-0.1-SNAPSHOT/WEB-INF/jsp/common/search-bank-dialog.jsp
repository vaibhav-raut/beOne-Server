<html lang="en" class="no-js">
<div id="search_bank_dialog" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<table class="table" id="bs_table">
					<tr>
						<td align="center" style="border-bottom: none" colspan="3"><strong class="label label-green">Search Bank</strong></td>
					</tr>
					<tr>
						<td style="border-bottom: none"><select class="form-control" id="bs_state">
								<option></option>
						</select></td>
						<td style="border-bottom: none"><select class="form-control" id="bs_district">
								<option></option>
						</select></td>
						<td style="border-bottom: none"></td>
					</tr>
					<tr>
						<td><input type="text" id="bs_name" class="form-control" placeholder="Bank Name"></td>
						<td><input type="text" id="bs_branch" class="form-control" placeholder="Branch (Optional)"></td>
						<td><button type="button" class="btn default" id="bsbyname_btn">Search</button></td>
					</tr>
					<tr>
						<td colspan="2"><input type="text" id="bs_ifsc" class="form-control" placeholder="IFSC Code"></td>
						<td><button type="button" class="btn default" id="bsbyifsc_btn">Search</button></td>
					</tr>
				</table>
				<table class="table" id="sb_dialog_table_body">
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn default" id="sb_dialog_btn">Cancel</button>
			</div>
		</div>
	</div>
</div>
</html>