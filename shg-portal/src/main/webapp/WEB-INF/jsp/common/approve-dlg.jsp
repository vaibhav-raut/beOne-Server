<html lang="en" class="no-js">
<div id="approve" class="modal fade" tabindex="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<input hidden="true" type="text" id="approve_member_index" />
				<input hidden="true" type="text" id="approve_tx_type" />
				<input hidden="true" type="text" id="approval_req_from" />
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