<html lang="en" class="no-js">
<div id="search_stock_type_dlg" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<table class="table" id="sst_table">
					<tr>
						<td align="center" style="border-bottom: none" colspan="2"><strong class="label label-green">Search Stock Type</strong></td>
					</tr>
					<tr>
						<td><input type="text" id="sst_name" class="form-control" placeholder="Stock Type"></td>
						<td width="30"><button type="button" class="btn default" id="stbyname_btn">Search</button></td>
					</tr>
				</table>
				<table class="table" id="sst_dialog_table_body">
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn yellow aboutme" id="add_new_stype_btn">Add New</button>
				<button type="button" class="btn yellow aboutme" id="search_existing_stype_btn" style="display: none;">Search Existing</button>
				<button type="button" data-dismiss="modal" class="btn default" id="sst_dlg_btn">Cancel</button>
			</div>
		</div>
	</div>
</div>
</html>