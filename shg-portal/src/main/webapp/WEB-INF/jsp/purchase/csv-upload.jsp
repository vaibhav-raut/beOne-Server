<html lang="en" class="no-js">
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Import Data</h3>
			</div>
			<div class="panel-body">
				<div class="portlet-body">
					<table class="table">
						<tr>
							<td colspan="5" align="center">Please select a CSV file type and upload CSV file to import data.</td>
						</tr>
						<tr>
							<td><select class="form-control" id="csv_type">
									<option></option>
							</select></td>
							<td><input type="file" id="csv_file" class="form-control"></td>
							<td width="50"><button id="upload_csv_btn" class="btn blue">Upload</button></td>
							<td width="50"><button id="reset_csv_btn" class="btn default">Reset</button></td>
							<td>
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="m_csv_print"> Print </a></li>
										<li><a href="#" id="m_csv_pdf"> Save as PDF </a></li>
										<li><a href="#" id="m_csv_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="5" align="center" id="csv_upload_msg"></td>
						</tr>
					</table>
				</div>
				<div id="csv_upload_details">
					<table class="table table-striped table-bordered table-hover" id="csv_upload_result">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>