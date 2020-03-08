<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-responsive">
				<table class="table">
					<tr>
						<td align="right" valign="middle" width="130">From:</td>
						<td align="left" valign="middle" width="225"><div class="input-group margin-bottom-5">
								<input type="text" class="form-control form-control-inline date-picker" placeholder="eg. 01/01/2015" id="as_start_date">
							</div></td>
						<td align="right" valign="middle" width="125">To:</td>
						<td align="left" valign="middle" width="200"><select id="as_end_month" class="form-control">
								<option>As On Date</option>
						</select></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td align="right" valign="middle">Statement Type</td>
						<td align="left" valign="middle"><label> <input type="radio" name="statementType" value="radio" id="as_cash_radio" checked="checked"> Cash &nbsp;
						</label> <label> <input type="radio" name="statementType" value="radio" id="as_bank_radio"> Bank &nbsp;
						</label> <label> <input type="radio" name="statementType" value="radio" id="as_joint_radio"> Joint
						</label></td>
						<td align="right" valign="middle">Bank Account</td>
						<td align="left" valign="middle"><select id="as_bankacc_num" class="form-control" disabled="disabled">
								<option>All</option>
						</select></td>
						<td align="left" valign="middle"><button type="button" data-dismiss="modal" class="btn blue" id="as_load">Load Statement</button></td>
						<td>
							<div class="btn-group pull-right">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="as_print"> Print </a></li>
									<li><a href="#" id="as_pdf"> Save as PDF </a></li>
									<li><a href="#" id="as_excel"> Export to Excel </a></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr id="as_err_msg" style="color: red">
					</tr>
				</table>
			</div>
			<div class="row" id="as_detail">
				<div class="col-md-12">
					<div class="table-responsive">
						<table class="table" id="as_table_top"></table>
						<table class="table table-striped table-hover table-bordered">
							<thead id="as_table_header">
							</thead>
							<tbody id="as_table_body">
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>