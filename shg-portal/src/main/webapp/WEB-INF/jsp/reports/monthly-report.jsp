<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-responsive">
				<table class="table">
					<tr>
						<td align="left" valign="middle" width="110">Report Type</td>
						<td align="left" valign="middle" width="230"><select id="m_r_names" class="form-control">
								<option></option>
						</select></td>
						<td align="right" valign="middle" width="80">Till Date</td>
						<td align="left" valign="middle" width="200"><select id="m_r_till_date" class="form-control">
								<option>As On Date</option>
						</select></td>
						<td align="left" valign="middle"><button type="button" data-dismiss="modal" class="btn blue" id="load_m_report">Load Report</button></td>
						<td>
							<div class="btn-group pull-right">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="m_r_pdf"> Save as PDF </a></li>
									<li><a href="#" id="m_r_excel"> Export to Excel </a></li>
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="row" id="m_r_table">
				<div class="col-md-6">
					<div class="table-responsive">
						<table class="table" id="m_r_table_0">
						</table>
					</div>
				</div>
				<div class="col-md-6">
					<div class="table-responsive">
						<table class="table" id="m_r_table_1">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>