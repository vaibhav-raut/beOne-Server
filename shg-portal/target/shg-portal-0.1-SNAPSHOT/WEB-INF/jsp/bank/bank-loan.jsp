<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="b_loan_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="b_loan_err_msg"></td>
							<td align="right" width="240" id="b_loan_default_btns">
								<button type="button" class="btn blue aboutme" id="b_loan_apply_btn">Apply</button>
								<button type="button" class="btn default" id="b_loan_reset_btn">Reset</button>
							</td>
							<td align="right" width="240" id="b_loan_approval_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="b_loan_approve_btn">Approve</button>
								<button type="button" class="btn yellow aboutme" id="b_loan_reject_btn">Reject</button>
							</td>
							<td width="80" id="b_loan_activate_btn" hidden="true"><a class="btn yellow aboutme" href="#bank_app_activation_content" data-toggle="tab" id="go2activate_btn1">Go to Activate</a></td>
							<td width="80" id="b_loan_closure_btn" hidden="true"><a class="btn yellow aboutme" href="#bank_app_activation_content" data-toggle="tab" id="go2close_btn1">Go to Close</a></td>
							<td width="50" id="b_loan_back_btn" hidden="true"><a class="btn default" href="#bank_app_track_content" data-toggle="tab" id="back2accTrac_btn1">Back</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="b_loan_details">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Account Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#search_bank_dialog" id="b_loan_bank_search">Search Bank</a>
									<input type="text" id="b_loan_bank_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td width="232">Bank Name</td>
								<td width="252" id="b_loan_bank_name"> -- </td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="b_loan_branch_name"> -- </td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="b_loan_ifsc_code"> -- </td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#sel_add_acc_dlg" id="b_loan_acc_search">Search Loan Account</a>
									<input type="text" id="b_loan_b_acc_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td>Account Number</td>
								<td id="b_loan_b_acc_num"> -- </td>
							</tr>
							<tr>
								<td>Account Name</td>
								<td id="b_loan_b_acc_name"> -- </td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Plan</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Development Plan</td>
								<td width="252"><textarea id="b_loan_dev_plan" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200" disabled="disabled"></textarea></td>
							</tr>
							<tr>
								<td>Remarks</td>
								<td width="252"><textarea id="b_loan_remark" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200" disabled="disabled"></textarea></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Loan Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td>Loan Amount</td>
								<td><input type="text" id="b_loan_amt" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Rate of Interest</td>
								<td><input type="text" id="b_loan_roi" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Loan Computation Type</td>
								<td><select id="b_loan_calc_type" class="form-control" disabled="disabled">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>No. of EMIs</td>
								<td><input type="text" id="b_loan_num_of_emi" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Fixed EMI</td>
								<td><input type="text" id="b_loan_fixed_emi" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Loan Processing Fee Percent</td>
								<td><input type="text" class="form-control" id="b_loan_proc_fee_percent" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Requested Date</td>
								<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="b_loan_req_date" disabled="disabled" /></td>
							</tr>
							<tr>
								<td>EMI Start Date</td>
								<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="b_loan_s_date" disabled="disabled" /></td>
							</tr>
							<tr>
								<td colspan="2" align="center"><button type="button" class="btn default" id="b_loan_plan_btn">Plan Loan</button></td>
							</tr>
							<tr>
								<td width="232">Loan Account Name</td>
								<td width="252"><input type="text" class="form-control" id="b_loan_acc_name" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Loan Type</td>
								<td><select id="b_loan_fund_type" class="form-control" disabled="disabled">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>Loan Processing Fee</td>
								<td><input type="text" class="form-control" id="b_loan_proc_fee" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Other Bank Charges (in Rs.)</td>
								<td><input type="text" class="form-control" id="b_loan_oth_charges" disabled="disabled"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" id="b_loan_attachment_div" hidden="true">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Attachments</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" style="width:;">
							<tr>
								<td align="right"><a class='btn default' href='#doc_upload_dlg' data-toggle="modal" id="b_loan_upload_file_btn">Upload Document</a></td>
							</tr>
						</table>
						<table class="table" id="b_loan_attachments_table" style="width:;">
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Monthly EMI Breakdown Sheet</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;" id="emi_plan_msg">
							<tr>
								<td>Monthly EMI breakdown will be shown here.</td>
							</tr>
						</table>
						<table class="table" style="width:;" id="emi_plan_table"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>