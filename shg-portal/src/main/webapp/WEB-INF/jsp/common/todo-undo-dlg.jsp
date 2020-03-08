<html lang="en" class="no-js">
<div id="done" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<input hidden="true" type="text" id="done_member_index"> <input hidden="true" type="text" id="done_tx_type">
				<table class="table">
					<tr>
						<td colspan="2"><strong class="label label-green" id="done_dialog_name"></strong></td>
						<td colspan="3" align="right"><strong id="done_m_name"></strong></td>
					</tr>
					<tr>
						<td colspan="5" align="center" style="border-bottom: none" id="done_msg"></td>
					</tr>
				</table>
				<table class="table" id="done_form">
					<tr id="done_loan_principle_interest_row" hidden="true">
						<td><strong>Principle Component </strong></td>
						<td><input type="text" id="done_principle" class="form-control" data-width="100px"></td>
						<td width="20">&nbsp;</td>
						<td><strong>Interest Component </strong></td>
						<td><input type="text" id="done_interest" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Amount </strong></td>
						<td><input type="text" id="done_amt" class="form-control" disabled="disabled" data-width="100px"></td>
						<td width="20">&nbsp;</td>
						<td><strong>Slip No.</strong></td>
						<td><input type="text" id="done_slip_num" class="form-control" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Payment Date </strong></td>
						<td><input class="form-control form-control-inline date-picker" size="18" type="text" id="done_date" placeholder="eg. 05/27/2014" /></td>
						<td>&nbsp;</td>
						<td><strong>Due Date </strong></td>
						<td><input type="text" id="done_due_date" class="form-control" data-width="100px" disabled="disabled">
					</tr>
					<tr>
						<td><strong>Payment Mode </strong></td>
						<td><select class="bs-select form-control" id="done_pay_mode" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Cheque No. </strong></td>
						<td><input type="text" id="done_cheque_num" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>SHG Bank Ac No. </strong></td>
						<td><select id="done_g_bank_acc" class="bs-select form-control" disabled="disabled" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Member Bank Ac No. </strong></td>
						<td><select id="done_m_bank_acc" class="bs-select form-control" disabled="disabled" data-width="100px">
						</select></td>
					</tr>
					<tr>
						<td><strong>Apply Penalty</strong></td>
						<td><button class="btn btn-sm" id="done_penalty_check">
								<i class="fa fa-check"></i>
							</button></td>
						<td>&nbsp;</td>
						<td><strong>Penalty Type </strong></td>
						<td><select id="done_penalty_type" class="bs-select form-control" disabled="disabled" data-width="100px">
								<option>Late Fee</option>
						</select></td>
					</tr>
					<tr>
						<td><button class="btn btn-sm" id="done_paid">
								<i class="fa fa-check"></i>
							</button> <strong>Paid</strong>
						</td>
						<td colspan="2">
							<button class="btn btn-sm" id="done_pending">
								<i class="fa fa-times"></i>
							</button> <strong>Outstanding</strong>
						</td>
						<td><strong>Penalty</strong></td>
						<td><input type="text" id="done_penalty" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Description</strong></td>
						<td colspan="4"><textarea id="done_desc" cols="30" rows="2" class="form-control" maxlength="100" style="max-width: 425px; max-height: 60px;"></textarea></td>
					</tr>
					<tr>
						<td colspan="5" align="center" style="color: red; border-bottom: none" id="done_err_msg"></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" id="done_btn" class="btn green">Done</button>
				<button type="button" data-dismiss="modal" id="done_cancel" class="btn default">Cancel</button>
			</div>
		</div>
	</div>
</div>
<div id="undo" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<input hidden="true" type="text" id="undo_member_index"> <input hidden="true" type="text" id="undo_tx_type">
				<table class="table">
					<tr>
						<td colspan="2"><strong class="label label-blue" id="undo_dialog_name"></strong></td>
						<td colspan="3" align="right"><strong id="undo_m_name"></strong></td>
					</tr>
					<tr>
						<td colspan="5" align="center" style="border-bottom: none" id="undo_msg"></td>
					</tr>
				</table>
				<table class="table" id="undo_form">
					<tr id="undo_loan_principle_interest_row" hidden="true">
						<td><strong>Principle Component </strong></td>
						<td><input type="text" id="undo_principle" class="form-control" disabled="disabled" data-width="100px"></td>
						<td width="20">&nbsp;</td>
						<td><strong>Interest Component </strong></td>
						<td><input type="text" id="undo_interest" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Amount </strong></td>
						<td><input type="text" class="form-control" id="undo_amt" disabled="disabled" data-width="100px"></td>
						<td width="20">&nbsp;</td>
						<td><strong>Slip No. </strong></td>
						<td><input type="text" class="form-control" id="undo_slip_num" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Payment Date </strong></td>
						<td><input class="form-control form-control-inline date-picker" size="18" type="text" id="undo_date" disabled="disabled" placeholder="eg. 05/27/2014" /></td>
						<td>&nbsp;</td>
						<td><strong>Due Date </strong></td>
						<td><input type="text" id="undo_due_date" class="form-control" data-width="100px" disabled="disabled">
					</tr>
					<tr>
						<td><strong>Payment Mode </strong></td>
						<td><select class="bs-select form-control" id="undo_pay_mode" disabled="disabled" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Cheque No. </strong></td>
						<td><input type="text" id="undo_cheque_num" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>SHG Bank Ac No. </strong></td>
						<td><select class="bs-select form-control" id="undo_g_bank_acc" disabled="disabled" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Member Bank Ac No. </strong></td>
						<td><select class="bs-select form-control" id="undo_m_bank_acc" disabled="disabled" data-width="100px">
						</select></td>
					</tr>
					<tr>
						<td><strong>Penalty Type </strong></td>
						<td><select class="bs-select form-control" id="undo_penalty_type" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Penalty</strong></td>
						<td><input type="text" class="form-control" id="undo_penalty" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Reason</strong></td>
						<td colspan="4"><select class="bs-select form-control" id="undo_reason" data-width="100px">
								<option>Invalid Entry</option>
						</select></td>
					</tr>
					<tr>
						<td><strong>Description</strong></td>
						<td colspan="4"><textarea id="undo_desc" cols="30" rows="2" class="form-control" maxlength="100" style="max-width: 425px; max-height: 60px;" disabled="disabled"></textarea></td>
					</tr>
					<tr>
						<td colspan="5" align="center" style="color: red; border-bottom: none" id="undo_err_msg"></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn blue aboutme" id="undo_btn">Undo</button>
				<button type="button" data-dismiss="modal" id="undo_cancel" class="btn default">Cancel</button>
			</div>
		</div>
	</div>
</div>
<div id="approve" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<input hidden="true" type="text" id="approve_member_index" /> <input hidden="true" type="text" id="approve_tx_type" />
				<table class="table">
					<tr>
						<td colspan="2"><strong class="label label-yellow" id="approve_dialog_name"></strong></td>
						<td colspan="3" align="right"><strong id="approve_m_name"></strong></td>
					</tr>
					<tr>
						<td colspan="5" align="center" style="border-bottom: none" id="approve_msg"></td>
					</tr>
				</table>
				<table class="table" id="approve_form">
					<tr id="approve_loan_principle_interest_row" hidden="true">
						<td><strong>Principle Component </strong></td>
						<td><input type="text" id="approve_principle" class="form-control" disabled="disabled" data-width="100px"></td>
						<td width="20">&nbsp;</td>
						<td><strong>Interest Component </strong></td>
						<td><input type="text" id="approve_interest" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Amount </strong></td>
						<td><input type="text" class="form-control" id="approve_amt" disabled="disabled" data-width="100px"></td>
						<td width="20">&nbsp;</td>
						<td><strong>Slip No. </strong></td>
						<td><input type="text" class="form-control" id="approve_slip_num" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Payment Date </strong></td>
						<td><input class="form-control form-control-inline date-picker" size="18" type="text" id="approve_date" placeholder="eg. 05/27/2014" /></td>
						<td>&nbsp;</td>
						<td><strong>Due Date </strong></td>
						<td><input type="text" id="approve_due_date" class="form-control" data-width="100px" disabled="disabled">
					</tr>
					<tr>
						<td><strong>Payment Mode </strong></td>
						<td><select class="bs-select form-control" id="approve_pay_mode" disabled="disabled" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Cheque No. </strong></td>
						<td><input type="text" id="approve_cheque_num" class="form-control" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>SHG Bank Ac No. </strong></td>
						<td><select class="bs-select form-control" id="approve_g_bank_acc" data-width="100px">
						</select></td>
						<td>&nbsp;</td>
						<td><strong>Member Bank Ac No. </strong></td>
						<td><select class="bs-select form-control" id="approve_m_bank_acc" data-width="100px">
						</select></td>
					</tr>
					<tr>
						<td><strong>Apply Penalty</strong></td>
						<td><button class="btn btn-sm" id="approve_penalty_check" disabled="disabled">
								<i class="fa fa-check"></i>
							</button></td>
						<td>&nbsp;</td>
						<td><strong>Penalty Type </strong></td>
						<td><select class="bs-select form-control" id="approve_penalty_type" disabled="disabled" data-width="100px">
								<option>Late Fee</option>
								<option>Cheque Bouncing Penalty</option>
								<option>Auto Debit Failure Penalty</option>
						</select></td>
					</tr>
					<tr>
						<td><button class="btn btn-sm" id="approve_paid_check" disabled="disabled">
								<i class="fa fa-times"></i>
							</button> <strong>Paid</strong>
						</td>
						<td colspan="2">
							<button class="btn btn-sm" id="approve_pending_check" disabled="disabled">
								<i class="fa fa-times"></i>
							</button> <strong>Outstanding</strong>
						</td>
						<td><strong>Penalty</strong></td>
						<td><input type="text" class="form-control" id="approve_penalty" disabled="disabled" data-width="100px"></td>
					</tr>
					<tr>
						<td><strong>Description</strong></td>
						<td colspan="4"><textarea id="approve_desc" cols="30" rows="2" class="form-control" maxlength="100" style="max-width: 425px; max-height: 60px;" disabled="disabled"></textarea></td>
					</tr>
					<tr>
						<td colspan="5" align="center" style="color: red; border-bottom: none" id="approve_err_msg"></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn yellow aboutme" id="approve_btn">Approve</button>
				<button type="button" class="btn red aboutme" id="reject_btn">Reject</button>
				<button type="button" data-dismiss="modal" id="approve_cancel" class="btn default">Cancel</button>
			</div>
		</div>
	</div>
</div>
</html>