<!DOCTYPE html>
<%@page import="java.util.Map"%>
<%
	boolean g_db_access = false, m_db_access = false, mw_db_access = false;
	Map<?, ?> uiAccessCodes = (Map<?, ?>)session.getAttribute("uiAccessCodes");
	if (uiAccessCodes.containsKey("GROUP_DASHBOARD"))
		g_db_access = true;
	if (uiAccessCodes.containsKey("MEMBER_DASHBOARD"))
		m_db_access = true;
	if (uiAccessCodes.containsKey("MEMBER_WISE_DASHBOARD"))
		mw_db_access = true;

	String[] status = new String[3];
	if (g_db_access)
		status[0] = "active";
	else if (m_db_access)
		status[1] = "active";
	else if (mw_db_access)
		status[2] = "active";
%>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8" />
<title>SHG-One.net</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />

<!-- BEGIN SCRIPT -->
<script data-main="resources/js/dashboard/Main" src="resources/js/require-jquery.js"></script>
<script src="resources/js/jquery.min.1.11.0.js"></script>
<script src="resources/js/common/common.js"></script>
<!-- END SCRIPT -->

<jsp:include page="common/page-style.jsp"></jsp:include>
</head>
<body class="page-header-fixed page-full-width">
	<input hidden="true" type="text" id="gdbAccess" value="<%=g_db_access%>">
	<input hidden="true" type="text" id="mdbAccess" value="<%=m_db_access%>">
	<input hidden="true" type="text" id="mwdbAccess" value="<%=mw_db_access%>">
	<jsp:include page="common/header.jsp"></jsp:include>
	<div class="clearfix"></div>
	<br>
	<div class="page-container">
		<div class="page-content-wrapper">
			<div class="page-content">
				<jsp:include page="common/db-stats.jsp"></jsp:include>
				<div style="margin: 0px 20px;">
					<div class="row">
						<jsp:include page="common/menu.jsp"></jsp:include>
						<div class="col-md-9">
							<div class="tabbable tabbable-custom tabbable-custom-profile">
								<ul class="nav nav-tabs">
									<%
										if (g_db_access) {
									%>
									<li class="<%=status[0]%>"><a href="#db_content" data-toggle="tab" id="db_tab"> SHG </a></li>
									<%
										}
										if (m_db_access) {
									%>
									<li class="<%=status[1]%>"><a href="#m_db_content" data-toggle="tab" id="mdb_tab"> Member </a></li>
									<%
										}
										if (mw_db_access) {
									%>
									<li class="<%=status[2]%>"><a href="#mw_db_content" data-toggle="tab" id="mwdb_tab"> All Members </a></li>
									<%
										}
									%>
								</ul>
								<div class="tab-content">
									<%
										if (g_db_access) {
									%>
									<div class="tab-pane <%=status[0]%>" id="db_content">
										<jsp:include page="dashboard/group-db.jsp"></jsp:include>
									</div>
									<%
										}
										if (m_db_access) {
									%>
									<div class="tab-pane <%=status[1]%>" id="m_db_content">
										<jsp:include page="dashboard/member-db.jsp"></jsp:include>
									</div>
									<%
										}
										if (mw_db_access) {
									%>
									<div class="tab-pane <%=status[2]%>" id="mw_db_content">
										<jsp:include page="dashboard/memberwise-db.jsp"></jsp:include>
									</div>
									<%
										}
									%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="common/footer.jsp"></jsp:include>
</html>