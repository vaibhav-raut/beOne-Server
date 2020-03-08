<html lang="en" class="no-js">
<div class="portlet-body">
	<br>
	<div class="table-toolbar">
		<div class="table-responsive">
			<table class="table">
				<tr>
					<td>From</td>
					<td><input type="text" class="form-control form-control-inline date-picker" placeholder="eg. 01/01/2015" id="track_sdate"></td>
					<td width="60">&nbsp;</td>
					<td>To</td>
					<td><input type="text" class="form-control form-control-inline date-picker" placeholder="eg. 31/01/2015" id="track_edate"></td>
				</tr>
				<tr>
					<td>Member Name</td>
					<td><input type="text" class="form-control" id="m_name1"><input hidden="true" type="text" id="m_num1"></td>
					<td><a data-toggle="modal" href="#modal_dialog" id="m_name_search1" class="btn default"> <i class="fa fa-search"></i></a></td>
					<td>Transaction Type</td>
					<td><select id="track_txtype" class="form-control">
							<option>All</option>
					</select></td>
				</tr>
				<tr>
					<td>Done By Member Name</td>
					<td><input type="text" class="form-control" id="m_name2"><input hidden="true" type="text" id="m_num2"></td>
					<td><a data-toggle="modal" href="#modal_dialog" id="m_name_search2" class="btn default"> <i class="fa fa-search"></i></a></td>
					<td>Transaction Status</td>
					<td><select id="track_txstatus" class="form-control">
							<option>All</option>
					</select></td>
				</tr>
				<tr>
					<td>Approve By Member Name</td>
					<td><input type="text" class="form-control" id="m_name3"><input hidden="true" type="text" id="m_num3"></td>
					<td><a data-toggle="modal" href="#modal_dialog" id="m_name_search3" class="btn default"> <i class="fa fa-search"></i></a></td>
					<td>Payment Mode</td>
					<td><select id="track_paymode" class="form-control">
							<option>All</option>
					</select></td>
				</tr>
				<tr>
					<td colspan="2"><button id="searchTxBtn" type="button" class="btn blue">Search Transactions</button>
					<td>&nbsp;</td>
					<td colspan="2">
						<div class="btn-group pull-right">
							<button class="btn dropdown-toggle" data-toggle="dropdown">
								Tools <i class="fa fa-angle-down"></i>
							</button>

							<ul class="dropdown-menu pull-right">
								<li><a href="#" id="txn_print"> Print </a></li>
								<li><a href="#" id="txn_pdf"> Save as PDF </a></li>
								<li><a href="#" id="txn_excel"> Export to Excel </a></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width: auto; overflow-x: auto; overflow-y: hidden;">
		<div class="table-responsive" id="txn_table">
			<table class="table table-striped table-bordered table-hover">
				<thead id="txn_table_header">
				</thead>
				<tbody id="txn_table_body">
				</tbody>
			</table>
		</div>
	</div>
</div>
</html>