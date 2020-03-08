<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="activate_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="activate_err_msg"></td>
							<td align="right" width="80" id="activate_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="m_activate_btn" disabled="disabled">Activate</button>
							</td>
							<td align="right" width="80" id="disburse_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="l_disburse_btn" disabled="disabled">Disburse</button>
							</td>
							<td align="right" width="50" id="activate_back_btn"><a class='btn default' href='#app_track_content' data-toggle="tab" id="back2apptrac_btn3">Back</a></td>
							<td align="right" width="80" id="m_closure_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="m_closure_btn" disabled="disabled">Close Member Account</button>
							</td>
							<td align="right" width="80" id="l_closure_btns" hidden="true">
								<button type="button" class="btn green aboutme" id="l_closure_btn" disabled="disabled">Close Loan Account</button>
							</td>
							<td align="right" width="50" id="closure_back_btn" hidden="true"><a class='btn default' href='#app_closure_content' data-toggle="tab" id="back2closure_btn">Back</a></td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="app_print"> Print </a></li>
										<li><a href="#" id="app_pdf"> Save as PDF </a></li>
										<li><a href="#" id="app_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="app_details">
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Application Detail</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<div class="table-responsive">
							<table class="table">
								<tbody id="app_detail_body">
									<tr>
										<td>Invalid Application Data</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Transactions</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<div class="table-responsive">
							<div id="tx2activate_msg"></div>
							<table class="table" id="tx2activate_table" hidden="true">
								<tr>
									<td>Slip No.</td>
									<td><input type="text" id="slipnum" class="form-control"></td>
									<td width="60">&nbsp;</td>
									<td>Payment Date</td>
									<td><input type="text" class="form-control form-control-inline date-picker" placeholder="eg. 01/01/2015" id="pay_date"></td>
								</tr>
								<tr id="bankinfo" hidden="true">
									<td>SHG Bank Ac No.</td>
									<td><select class="form-control" id="shgbankacc"><option></option></select></td>
									<td>&nbsp;</td>
									<td>Member Bank Ac No.</td>
									<td><select class="form-control" id="mbankacc"><option></option></select></td>
								</tr>
							</table>

							<table class="table">
								<tr id="m2g_title" hidden="true">
									<td colspan="8" align="center" style="color: blue">Member To Group</td>
								</tr>
								<tr id="m2g_tx_0" hidden="true">
									<td width="50"><button class="btn btn-sm" id="m2g_check_0" value="0" title="Click to select/deselect this transaction">
										<i class="fa fa-check"></i></button></td>
									<td id="m2g_txtype_0"></td>
									<td align="right">Rs.</td>
									<td width="125"><input type="text" class="form-control" id="m2g_amt_0"></td>
									<td align="right">Payment Mode</td>
									<td><select class="form-control" id="m2g_paymode_0"><option></option></select></td>
									<td align="right">Cheque No.</td>
									<td width="125"><input type="text" class="form-control" id="m2g_cheque_0" disabled="disabled"></td>
								</tr>
								<tr id="m2g_tx_1" hidden="true">
									<td width="50"><button class="btn btn-sm" id="m2g_check_1" value="1" title="Click to select/deselect this transaction">
										<i class="fa fa-check"></i></button></td>
									<td id="m2g_txtype_1"></td>
									<td align="right">Rs.</td>
									<td><input type="text" class="form-control" id="m2g_amt_1"></td>
									<td align="right">Payment Mode</td>
									<td><select class="form-control" id="m2g_paymode_1"><option></option></select></td>
									<td align="right">Cheque No.</td>
									<td><input type="text" class="form-control" id="m2g_cheque_1" disabled="disabled"></td>
								</tr>
								<tr id="m2g_tx_2" hidden="true">
									<td width="50"><button class="btn btn-sm" id="m2g_check_2" value="2" title="Click to select/deselect this transaction">
										<i class="fa fa-check"></i></button></td>
									<td id="m2g_txtype_2"></td>
									<td align="right">Rs.</td>
									<td><input type="text" class="form-control" id="m2g_amt_2"></td>
									<td align="right">Payment Mode</td>
									<td><select class="form-control" id="m2g_paymode_2"><option></option></select></td>
									<td align="right">Cheque No.</td>
									<td><input type="text" class="form-control" id="m2g_cheque_2" disabled="disabled"></td>
								</tr>
								<tr id="m2g_tx_3" hidden="true">
									<td width="50"><button class="btn btn-sm" id="m2g_check_3" value="3" title="Click to select/deselect this transaction">
										<i class="fa fa-check"></i></button></td>
									<td id="m2g_txtype_3"></td>
									<td align="right">Rs.</td>
									<td><input type="text" class="form-control" id="m2g_amt_3"></td>
									<td align="right">Payment Mode</td>
									<td><select class="form-control" id="m2g_paymode_3"><option></option></select></td>
									<td align="right">Cheque No.</td>
									<td><input type="text" class="form-control" id="m2g_cheque_3" disabled="disabled"></td>
								</tr>
								<tr id="g2m_title" hidden="true">
									<td colspan="8" align="center" style="color: blue">Group To Member</td>
								</tr>
								<tr id="g2m_tx_0" hidden="true">
									<td width="50"><button class="btn btn-sm" id="g2m_check_0" value="0" title="Click to select/deselect this transaction">
										<i class="fa fa-check"></i></button></td>
									<td id="g2m_txtype_0"></td>
									<td align="right">Rs.</td>
									<td><input type="text" class="form-control" id="g2m_amt_0"></td>
									<td align="right">Payment Mode</td>
									<td><select class="form-control" id="g2m_paymode_0"><option></option></select></td>
									<td align="right">Cheque No.</td>
									<td><input type="text" class="form-control" id="g2m_cheque_0" disabled="disabled"></td>
								</tr>
								<tr id="g2m_tx_d0" hidden="true">
									<td></td>
									<td>Description</td>
									<td colspan="6" align="left" id="g2m_desc_0"></td>
								</tr>
								<tr id="g2m_tx_1" hidden="true">
									<td width="50"><button class="btn btn-sm" id="g2m_check_1" value="1" title="Click to select/deselect this transaction">
										<i class="fa fa-check"></i></button></td>
									<td id="g2m_txtype_1"></td>
									<td align="right">Rs.</td>
									<td><input type="text" class="form-control" id="g2m_amt_1"></td>
									<td align="right">Payment Mode</td>
									<td><select class="form-control" id="g2m_paymode_1"><option></option></select></td>
									<td align="right">Cheque No.</td>
									<td><input type="text" class="form-control" id="g2m_cheque_1" disabled="disabled"></td>
								</tr>
								<tr id="g2m_tx_d1" hidden="true">
									<td></td>
									<td>Description</td>
									<td colspan="6" align="left" id="g2m_desc_1"></td>
								</tr>
								<tr id="g2m_tx_net" hidden="true">
									<td colspan="8" align="center" style="color: blue" id="g2m_net_payable"></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>