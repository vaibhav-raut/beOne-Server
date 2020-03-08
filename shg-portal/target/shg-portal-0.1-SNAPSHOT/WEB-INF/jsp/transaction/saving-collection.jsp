<html lang="en" class="no-js">
<div class="portlet-body">
	<br>
	<div class="table-toolbar">
		<div class="table-responsive">
			<table class="table">
				<tr>
					<td width="140">Search by Name:</td>
					<td width="225"><input type="text" class="form-control" id="sc_input"></td>
					<td width="50"><a href="#" id="sc_search" class="btn default"><i class="fa fa-search"></i></a></td>
					<td width="30"><a href="#" id="sc_clear_search" hidden="true"><i class="fa fa-times"></i></a></td>
					<td id="sc_filter"></td>
					<td width="50" align="right">Status:</td>
					<td width="150"><select id="sc_status" class="form-control">
							<option>All</option>
					</select></td>
					<td width="50" align="right" id="sc_tx_count"></td>
					<td>
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>
							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="sc_print"> Print </a></li>
								<li><a href="#" id="sc_pdf"> Save as PDF </a></li>
								<li><a href="#" id="sc_excel"> Export to Excel </a></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="table-responsive" id="sc_table">
		<table class="table" id="sc_top_table_data"></table>
		<table class="table table-striped table-bordered table-hover">
			<thead id="sc_table_header">
			</thead>
			<tbody id="sc_table_body">
				<tr>
					<td>Please wait... <br>Saving Collection table is loading... <img src="resources/img/ajax-loader.gif" alt="" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</html>