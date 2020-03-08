<html lang="en" class="no-js">
<%@page import="java.util.Map"%>
<%
	Map<?, ?> uiAccessCodes = (Map<?, ?>) session.getAttribute("uiAccessCodes");
	boolean write_access = ((Integer) uiAccessCodes.get("SHG_GROUP_RULES")) >= 2;
%>
<div class="row">
	<div class="col-md-12">
		<div class="portlet-body">
			<div class="table-toolbar">
				<div class="table-responsive">
					<table class="table">
						<tr>
							<td align="left" valign="middle" id="grules_msg" hidden="true"></td>
							<td style="color: red" align="left" valign="middle" id="grules_err_msg"></td>
							<%
								if (write_access) {
							%>
							<td width="10" align="right" id="grules_edit_btn" hidden="true">
								<button class="btn blue">Edit</button>
							</td>
							<td width="10" align="right" id="grules_update_btn" hidden="true">
								<button class="btn blue">Update</button>
							</td>
							<td width="10" align="right" id="grules_back_btn" hidden="true">
								<button class="btn default">Back</button>
							</td>
							<%
								}
							%>
							<td width="50" id="tools_btn">
								<div class="btn-group pull-right">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										Tools <i class="fa fa-angle-down"></i>
									</button>
									<ul class="dropdown-menu pull-right">
										<li><a href="#" id="grules_print"> Print </a></li>
										<li><a href="#" id="grules_pdf"> Save as PDF </a></li>
										<li><a href="#" id="grules_excel"> Export to Excel </a></li>
									</ul>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="col-md-12" id="grules_details">
				<div class="table-responsive">
					<table class="table" id="grules_table">
					</table>
				</div>
			</div>
			<div class="col-md-12" id="grules_edit_div">
				<div class="table-responsive">
					<table class="table" id="grules_edit_table">
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</html>