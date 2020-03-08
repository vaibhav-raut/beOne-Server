<html lang="en" class="no-js">
<div class="portlet-body">
	<div class="row">
		<div class="col-md-12">
			<div class="table-responsive">
				<table class="table">
					<tbody>
						<tr>
							<td id="db_msg">Please wait. Dashboard is loading... <img src="resources/img/ajax-loader.gif" /></td>
							<td id="db_err_msg" style="color: red;" hidden="true"></td>
							<td>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="db_print"> Print </a></li>
										<li><a href="#" id="db_pdf"> Save as PDF </a></li>
										<li><a href="#" id="db_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div id="shg_db_details">
			<div class="col-md-12">
				<div class="table-responsive">
					<table class="table" id="db_header_table">
					</table>
				</div>
			</div>
			<div class="col-md-6">
				<div class="table-responsive">
					<table class="table" id="db_table_1">
					</table>
				</div>
			</div>
			<div class="col-md-6">
				<div class="table-responsive">
					<table class="table" id="db_table_2">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>