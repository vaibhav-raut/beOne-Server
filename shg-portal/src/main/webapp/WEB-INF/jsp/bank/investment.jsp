<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="b_invest_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="b_invest_err_msg"></td>
							<td align="right" width="240" id="b_invest_default_btns">
								<button type="button" class="btn blue aboutme" id="b_invest_apply_btn">Apply</button>
								<button type="button" class="btn default" id="b_invest_reset_btn">Reset</button>
							</td>
							<td align="right" width="240" id="b_invest_approval_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="b_invest_approve_btn">Approve</button>
								<button type="button" class="btn yellow aboutme" id="b_invest_reject_btn">Reject</button>
							</td>
							<td width="80" id="b_invest_activate_btn" hidden="true"><a class="btn yellow aboutme" href="#bank_app_activation_content" data-toggle="tab" id="go2activate_btn2">Go to Activate</a></td>
							<td width="80" id="b_invest_closure_btn" hidden="true"><a class="btn yellow aboutme" href="#bank_app_activation_content" data-toggle="tab" id="go2close_btn2">Go to Close</a></td>
							<td width="50" id="b_invest_back_btn" hidden="true"><a class="btn default" href="#bank_app_track_content" data-toggle="tab" id="back2accTrac_btn2">Back</a></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="b_invest_details">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Investment Type</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Investment Type</td>
								<td width="252"><select id="b_invest_type" class="form-control">
										<option>Project Development</option>
										<option>Fix Deposit</option>
										<option>Investment Account</option>
								</select></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Bank Account Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#search_bank_dialog" id="b_invest_bank_search">Search Bank</a>
									<input type="text" id="b_invest_bank_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td width="232">Bank Name</td>
								<td width="252" id="b_invest_bank_name"> -- </td>
							</tr>
							<tr>
								<td>Branch Name</td>
								<td id="b_invest_branch_name"> -- </td>
							</tr>
							<tr>
								<td>IFSC Code</td>
								<td id="b_invest_ifsc_code"> -- </td>
							</tr>
							<tr>
								<td colspan="2" align="center">
									<a class="btn default" data-toggle="modal" href="#sel_add_acc_dlg" id="b_invest_acc_search">Search Investment Account</a>
									<input type="text" id="b_invest_b_acc_id" hidden="true">
								</td>
							</tr>
							<tr>
								<td>Account Number</td>
								<td id="b_invest_b_acc_num"> -- </td>
							</tr>
							<tr>
								<td>Account Name</td>
								<td id="b_invest_b_acc_name"> -- </td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Investment Information</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tbody id="b_invest_prj_dev_content">
								<tr>
									<td width="232">Investment Account Name</td>
									<td width="252"><input type="text" class="form-control" id="b_invest_acc_name1" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Investment Amount</td>
									<td><input type="text" id="b_invest_amt1" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Expected Earning</td>
									<td><input type="text" id="b_invest_ex_earning" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Requested Date</td>
									<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="b_invest_req_date1" disabled="disabled" /></td>
								</tr>
								<tr>
									<td>Expected Date of Maturity</td>
									<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="b_invest_end_date1" disabled="disabled" /></td>
								</tr>
								<tr>
									<td>Projected Rate of Interest</td>
									<td><input type="text" id="b_invest_roi1" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td width="232">Development Plan</td>
									<td width="252"><textarea id="b_invest_dev_plan" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200"></textarea></td>
								</tr>
								<tr>
									<td>Remarks</td>
									<td width="252"><textarea id="b_invest_remark" cols="30" rows="3" class="form-control" style="width: 252px; max-width: 252px; max-height: 74px;" maxlength="200"></textarea></td>
								</tr>
							</tbody>
							<tbody id="b_invest_deposit_content" hidden="true">
								<tr>
									<td>Investment Amount</td>
									<td><input type="text" id="b_invest_amt2" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Rate of Interest</td>
									<td><input type="text" id="b_invest_roi2" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Requested Date</td>
									<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="b_invest_req_date2" disabled="disabled" /></td>
								</tr>
								<tr>
									<td>Investment Duration (in months)</td>
									<td><input type="text" id="b_invest_months" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td colspan="2" align="center"><button type="button" class="btn default" id="b_invest_plan_btn">Plan Investment</button></td>
								</tr>
								<tr>
									<td width="232">Investment Account Name</td>
									<td width="252"><input type="text" class="form-control" id="b_invest_acc_name2" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Projected Interest Amount</td>
									<td><input type="text" id="b_invest_proj_int_amt" class="form-control" disabled="disabled"></td>
								</tr>
								<tr>
									<td>Date of Maturity</td>
									<td><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="b_invest_end_date2" disabled="disabled" /></td>
								</tr>
								<tr>
									<td>Other Bank Charges (in Rs.)</td>
									<td><input type="text" class="form-control" id="b_invest_oth_charges" disabled="disabled"></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" id="b_invest_attachment_div" hidden="true">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Attachments</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" style="width:;">
							<tr>
								<td align="right"><a class='btn default' href='#doc_upload_dlg' data-toggle="modal" id="b_invest_upload_file_btn">Upload Document</a></td>
							</tr>
						</table>
						<table class="table" id="b_invest_attachments_table" style="width:;">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>