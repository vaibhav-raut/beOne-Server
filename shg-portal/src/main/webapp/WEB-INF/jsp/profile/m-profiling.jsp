<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="mp_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="mp_err_msg"></td>
							<td width="100" align="right"><button class="btn blue aboutme" id="mp_update_btn" disabled="disabled">Update</button></td>
							<td width="10" align="right" id="mp_back2mprofile_btn"><a class="btn default" data-toggle="tab" href="#profile_content">Back</a></td>
							<td width="50">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="mp_print"> Print </a></li>
										<li><a href="#" id="mp_pdf"> Save as PDF </a></li>
										<li><a href="#" id="mp_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="mprofiling_details">
		<div class="col-md-12">
			<div class="portlet-body">
				<table class="table">
					<tr>
						<td>Member Acc Number</td>
						<td id="mp_acc_num" class='bluetext'></td>
					</tr>
					<tr>
						<td>Member Name</td>
						<td id="mp_name" class='bluetext'></td>
					</tr>
					<tr>
						<td>Member Role</td>
						<td id="mp_role" class='bluetext'></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="col-md-12">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Profiling Data</h3>
				</div>
				<div class="panel-body">
					<div class="portlet-body">
						<table class="table" id="mprofiling_table">
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>