<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="add_txn_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="add_txn_err_msg"></td>
							<td align="right" width="240">
								<button id="addTxBtn" type="button" class="btn blue">Add Transaction</button>
								<button id="resetAddTxBtn" type="button" class="btn default">Reset</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12">
		<div class="portlet-body">
			<table class="table">
				<tr>
					<td width="220">Transaction With *</td>
					<td colspan="4"><label> <input type="radio" name="txwith" value="radio" id="txwith_member"> Member &nbsp;
					</label> <label> <input type="radio" name="txwith" value="radio" id="txwith_group"> Group &nbsp;
					</label> <label> <input type="radio" name="txwith" value="radio" id="txwith_bank"> Bank
					</label></td>
				</tr>
				<tr>
					<td>Transaction Category *</td>
					<td><select class="form-control" id="txn_category">
							<option></option>
					</select></td>
					<td width="30">&nbsp;</td>
					<td>Transaction Type *</td>
					<td><select id="txn_type" class="form-control">
							<option></option>
					</select></td>
				</tr>
				<tr>
					<td>Member Name *</td>
					<td><input type="text" class="form-control" id="m_name" placeholder=""></td>
					<td><a data-toggle="modal" href="#modal_dialog" id="m_name_search" class="btn default"><i class="fa fa-search"></i></a></td>
					<td>Member Account</td>
					<td id="m_acc_num"></td>
				</tr>
				<tr>
					<td>Saving/Loan/Investment Ac *</td>
					<td><select id="accounts" class="form-control">
							<option></option>
					</select></td>
					<td>&nbsp;</td>
					<td>Amount *</td>
					<td><input type="text" id="txn_amt" class="form-control"></td>
				</tr>
				<tr>
					<td>Payment Mode *</td>
					<td><select id="txn_paymode" class="form-control">
							<option></option>
					</select></td>
					<td>&nbsp;</td>
					<td>Cheque No.</td>
					<td><input type="text" id="txn_chequenum" class="form-control" disabled="disabled"></td>
				</tr>
				<tr>
					<td>Slip No. *</td>
					<td><input type="text" id="txn_slipnum" class="form-control"></td>
					<td>&nbsp;</td>
					<td>Payment Date *</td>
					<td><input class="form-control form-control-inline date-picker" size="18" type="text" id="txn_paydate" placeholder="eg. 05/27/2014" /></td>
				</tr>
				<tr>
					<td>SHG Bank Ac No. *</td>
					<td><select id="txn_g_bank_acc" class="form-control" disabled="disabled">
							<option></option>
					</select></td>
					<td>&nbsp;</td>
					<td>Member Bank Ac No.</td>
					<td><select id="txn_m_bank_acc" class="form-control" disabled="disabled">
							<option></option>
					</select></td>
				</tr>
				<tr>
					<td>Remarks</td>
					<td colspan="4"><textarea id="txn_desc" cols="30" rows="3" class="form-control" style="max-width: 600px; max-height: 74px;"></textarea></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="col-md-12" id="txn_attachment_div" hidden="true">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Attachments</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table" style="width:;">
						<tr>
							<td align="right"><a class='btn default' href='#doc_upload_dlg' data-toggle="modal" id="txn_upload_file_btn">Upload Document</a></td>
						</tr>
					</table>
					<table class="table" id="txn_attachments_table" style="width:;">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>