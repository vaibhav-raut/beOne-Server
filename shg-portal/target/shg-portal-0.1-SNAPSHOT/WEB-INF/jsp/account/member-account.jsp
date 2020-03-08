<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<br>
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td width="75">Member:</td>
							<td width="225"><input type="text" class="form-control" id="member_name"><input type="hidden" id="member_num"></td>
							<td width="50"><a data-toggle="modal" href="#modal_dialog" id="m_name_search" class="btn default"><i class="fa fa-search"></i></a></td>
							<td align="right" width="50">From:</td>
							<td width="150"><select id="m_acc_start_month" class="form-control">
								<option>As On Date</option>
							</select></td>
							<td align="right" width="50">Till:</td>
							<td width="150"><select id="m_acc_end_month" class="form-control">
								<option>As On Date</option>
							</select></td>
							<td align="right" width="80">
								<button type="button" class="btn blue aboutme" id="m_acc_load_data">Go</button>
							</td>
							<td>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="m_acc_print"> Print </a></li>
										<li><a href="#" id="m_acc_pdf"> Save as PDF </a></li>
										<li><a href="#" id="m_acc_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div style="width: auto; overflow-x: auto; overflow-y: hidden;">
				<table class="table table-striped table-hover table-bordered" id="m_acc_table">
					<thead id="m_acc_table_header">
					</thead>
					<tbody id="m_acc_table_body">
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
</html>