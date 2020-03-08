<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="fund_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="fund_err_msg"></td>
							<td align="right" width="240" id="fund_default_btns">
								<button type="button" class="btn blue aboutme" id="fund_plan_btn">Plan</button>
								<button type="button" class="btn blue aboutme" id="fund_apply_btn">Apply</button>
								<button type="button" class="btn default" id="fund_reset_btn">Reset</button>
							</td>
							<td align="right" width="240" id="fund_approval_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="fund_approve_btn">Approve</button>
								<button type="button" class="btn yellow aboutme" id="fund_reject_btn">Reject</button>
								<button type="button" class="btn blue aboutme" id="fund_hold_btn">Hold</button>
							</td>
							<td width="80" id="fund_disburse_btn" hidden="true"><a class='btn yellow aboutme' href='#app_activation_content' data-toggle="tab" id="go2disburse_btn">Go to Disburse</a></td>
							<td width="50" id="fund_back_btn" hidden="true"><a class='btn default' href='#app_track_content' data-toggle="tab" id="back2apptrac_btn2">Back</a></td>
							<td width="80" id="go2lclosure_btns" hidden="true"><a class='btn yellow aboutme' href='#app_activation_content' data-toggle="tab" id="go2lclosure_btn">Go to Close Account</a></td>
							<td width="50" id="lclosure_back_btn" hidden="true"><a class='btn default' href='#app_closure_content' data-toggle="tab" id="back2closure_btn2">Back</a></td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="fund_print"> Print </a></li>
										<li><a href="#" id="fund_pdf"> Save as PDF </a></li>
										<li><a href="#" id="fund_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="fund_details">
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Loan Application</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;">
							<tr>
								<td width="232">Member Name</td>
								<td><input type="text" class="form-control" id="member_name"></td>
								<td width="50"><a data-toggle="modal" href="#modal_dialog" id="fundapp_m_search" class="btn default"><i class="fa fa-search"></i></a></td>
							</tr>
							<tr>
								<td>Member Role</td>
								<td width="252" colspan="2"><input type="text" class="form-control" id="m_role" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Member Total Saving</td>
								<td colspan="2"><input type="text" class="form-control" id="m_saving" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Member Credit Rating</td>
								<td colspan="2"><input type="text" class="form-control" id="m_rating" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Member Outstanding Loan</td>
								<td colspan="2"><input type="text" class="form-control" id="m_pending_loan" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Loan Type</td>
								<td colspan="2"><select id="fund_type" class="form-control" disabled="disabled">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>Loan Calculation Type</td>
								<td colspan="2"><select id="loan_calc_type" class="form-control" disabled="disabled">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>Recovery Period</td>
								<td colspan="2"><select id="recovery_period" class="form-control" disabled="disabled">
										<option></option>
								</select></td>
							</tr>
							<tr>
								<td>Principle Amount</td>
								<td colspan="2"><input type="text" id="principle" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Rate of Interest</td>
								<td colspan="2"><input type="text" id="roi" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>Start Date</td>
								<td colspan="2"><input class="form-control form-control-inline date-picker" size="18" type="text" placeholder="eg. 01/01/2015" id="fund_s_date" disabled="disabled" /></td>
							</tr>
							<tr>
								<td>Fixed EMI</td>
								<td colspan="2"><input type="text" id="fixed_emi" class="form-control" disabled="disabled"></td>
							</tr>
							<tr>
								<td>No. of EMIs</td>
								<td colspan="2"><input type="text" id="num_of_emi" class="form-control" disabled="disabled"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Loan Planning Summary</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table_new" style="width:;" id="fund_planning_msg">
							<tr>
								<td>Please fill the details in the 'Fund Details' form and click 'Plan' to see the member fund planning here.</td>
							</tr>
						</table>
						<table class="table" style="width:;" id="fund_planning_table"></table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" id="fund_attachment_div" hidden="true">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Attachments</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" style="width:;">
							<tr>
								<td align="right"><a class='btn default' href='#doc_upload_dlg' data-toggle="modal" id="fund_upload_file_btn">Upload Document</a></td>
							</tr>
						</table>
						<table class="table" id="fund_attachments_table" style="width:;">
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12" id="fund_emi_breakdown_div" hidden="true">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Monthly EMI Breakdown Sheet</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" style="width:;" id="emi_breakdown_msg">
							<tr>
								<td>Monthly EMI breakdown will be shown here.</td>
								<td align="right"><button class="btn default" id="fund_display_emi_btn">Display Monthly EMI Breakdown</button></td>
							</tr>
						</table>
						<table class="table" style="width:;" id="emi_breakdown_table"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>