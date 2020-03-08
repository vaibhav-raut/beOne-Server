<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-responsive">
				<table class="table">
					<tr>
						<td align="right" valign="middle" width="100">From:</td>
						<td align="left" valign="middle" width="225"><div class="input-group margin-bottom-5">
								<input type="text" class="form-control form-control-inline date-picker" placeholder="eg. 01/01/2015" id="ab_start_date">
							</div></td>
						<td align="right" valign="middle" width="125">To:</td>
						<td align="left" valign="middle" width="200"><select id="ab_end_month" class="form-control">
								<option>As On Date</option>
						</select></td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td align="right" valign="middle">Book Type</td>
						<td align="left" valign="middle">
							<label>	<input type="radio" name="bookType" value="radio" id="ab_cash_radio" checked="checked"> Cash &nbsp; </label>
							<label>	<input type="radio" name="bookType" value="radio" id="ab_bank_radio"> Bank &nbsp; </label>
							<label>	<input type="radio" name="bookType" value="radio" id="ab_joint_radio"> Joint </label>
						</td>
						<td align="right" valign="middle">Bank Account</td>
						<td align="left" valign="middle"><select id="ab_bankacc_num" class="form-control" disabled="disabled">
								<option>All</option>
						</select></td>
						<td align="left" valign="middle"><button type="button" data-dismiss="modal" class="btn blue" id="ab_load">Load Book</button></td>
						<td>
							<div class="btn-group pull-right">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="ab_print"> Print </a></li>
									<li><a href="#" id="ab_pdf"> Save as PDF </a></li>
									<li><a href="#" id="ab_excel"> Export to Excel </a></li>
								</ul>
							</div>
						</td>
					</tr>
					<tr id="ab_err_msg" style="color: red">
					</tr>
				</table>
			</div>
			<div class="row" id="ab_detail">
				<div class="col-md-12">
					<div class="table-responsive">
						<table class="table" id="ab_table">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>