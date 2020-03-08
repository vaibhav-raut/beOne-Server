<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12" id="district_app_div" hidden="true">
		<table class="table">
			<tr>
				<td width="100"><a class="btn default" data-toggle="modal" href="#select_district_dlg" id="g_app_sel_district_btn">Select District</a></td>
				<td width="200"><input class="form-control" id="g_app_state" placeholder="State" disabled="disabled"></td>
				<td width="200"><input class="form-control" id="g_app_district" placeholder="District" disabled="disabled"></td>
				<td><button type="button" class="btn default" id="g_app_search_btn">Search District Applications</button></td>
			</tr>
		</table>
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">District Applications</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<div class="table-responsive" style="width: auto; overflow-x: auto; overflow-y: hidden;">
						<table class="table table-striped table-bordered table-hover">
							<thead id="g_app_table_header"></thead>
							<tbody id="g_app_table_body">
								<tr>
									<td>Select district and click Search Applications.</td>
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
				<h3 class="panel-title">Member Applications</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover">
							<thead id="m_app_table_header"></thead>
							<tbody id="m_app_table_body">
								<tr>
									<td>Please wait... <br>Member applications are loading... <img src="resources/img/ajax-loader.gif" alt="" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-12" id="m_loan_app_div" hidden="true">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Member Loan Applications</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<div class="table-responsive" style="width: auto; overflow-x: auto; overflow-y: hidden;">
						<table class="table table-striped table-bordered table-hover">
							<thead id="loan_app_table_header"></thead>
							<tbody id="loan_app_table_body">
								<tr>
									<td>Please wait... <br>Loan applications are loading... <img src="resources/img/ajax-loader.gif" alt="" />
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>