<html lang="en" class="no-js">
<div id="doc_upload_dlg" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<input hidden="true" type="text" id="upload_doc_for">
				<table class="table" id="doc_upload_dlg_content">
					<tr>
						<td colspan="2" align="center" style="border-bottom: none"><strong class="label label-green" id="doc_upload_dlg_header">Upload Document</strong></td>
					</tr>
					<tr>
						<td colspan="2" align="center">Select appropriate document category, type and choose file to upload.</td>
					</tr>
					<tr>
						<td><select class="form-control" id="doc_upload_category">
								<option></option>
						</select></td>
						<td><select class="form-control" id="doc_upload_type">
								<option></option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2"><input type="file" id="doc_upload_file" class="form-control"></td>
					</tr>
				</table>
				<table class="table">
					<tr id="doc_upload_msg_row" hidden="true">
						<td colspan="2" align="center"><strong id="doc_upload_msg"></strong></td>
					</tr>
					<tr id="doc_upload_errmsg_row" hidden="true">
						<td colspan="2" align="center" id="doc_upload_errmsg" style="color: red;"></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" id="doc_upload_btn" class="btn default">Upload</button>
				<button type="button" id="doc_upload_btn1" class="btn default" hidden="true">Upload</button>
				<button type="button" data-dismiss="modal" class="btn default" id="doc_upload_dlg_cancel_btn">Cancel</button>
			</div>
		</div>
	</div>
</div>
</html>