<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<table class="table">
					<tr>
						<td width="140">Search by Name:</td>
						<td width="225"><input type="text" class="form-control" id="mwd_input"></td>
						<td width="50"><a href="#" id="mwd_search" class="btn default"><i class="fa fa-search"></i></a></td>
						<td width="30"><a href="#" id="mwd_clear_search" hidden="true"><i class="fa fa-times"></i></a></td>
						<td><div id="mwd_filter"></div></td>
						<td>
							<div class="btn-group pull-right">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									Tools <i class="fa fa-angle-down"></i>
								</button>
								<ul class="dropdown-menu pull-right">
									<li><a href="#" id="mwd_print_m_info"> Print Member Info </a></li>
									<li><a href="#" id="mwd_print_m_acc_overview"> Print Member Account Overview </a></li>
									<li><a href="#" id="mwd_print_m_saving_details"> Print Member Saving Details </a></li>
									<li><a href="#" id="mwd_print_m_loan_details"> Print Member Loan Details </a></li>
									<li><a href="#" id="mwd_excel"> Export to Excel </a></li>
								</ul>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div style="width: auto; overflow-x: auto; overflow-y: hidden;" id="mwd_table">
				<table class="table table-striped table-hover table-bordered">
					<thead id="mwd_table_header">
					</thead>
					<tbody id="mwd_table_body">
						<tr>
							<td>Please wait... <br>All Member dashboard is loading... <img src="resources/img/ajax-loader.gif" alt="" />
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</html>