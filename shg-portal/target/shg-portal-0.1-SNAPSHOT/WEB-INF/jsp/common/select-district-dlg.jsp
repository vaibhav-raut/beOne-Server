<html lang="en" class="no-js">
<div id="select_district_dlg" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<table class="table" id="sd_table">
					<tr>
						<td align="center" style="border-bottom: none" colspan="2"><strong class="label label-green">Select District</strong></td>
					</tr>
					<tr>
						<td style="border-bottom: none" width="250"><select class="form-control" id="sd_state">
								<option></option>
						</select></td>
						<td style="border-bottom: none" width="250"><select class="form-control" id="sd_district">
								<option></option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2" align="center" id="sd_msg"></td>
						<td colspan="2" align="center" id="sd_err_msg" hidden="true" style="color: red;"></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn default" id="select_district_btn">OK</button>
				<button type="button" data-dismiss="modal" class="btn default">Cancel</button>
			</div>
		</div>
	</div>
</div>
</html>