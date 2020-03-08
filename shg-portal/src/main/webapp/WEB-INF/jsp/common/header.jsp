<%@page import="org.json.JSONObject"%>
<html lang="en" class="no-js">
<style type="text/css">
#selGroupNameAcc {
	display: inline-block; margin-top: 24px; color:#098dc7; font-weight:bold; font-size: 16px;
}
</style>
<%
	JSONObject userLoginInfo = (JSONObject) session.getAttribute("user");
	JSONObject member = userLoginInfo.getJSONObject("member");
	JSONObject contact = member.getJSONArray("contacts").getJSONObject(0);
	String lang = contact.getString("lang");
	String fullName = contact.getString("firstName") + " " + contact.getString("lastName");
	String mrole = member.getString("mrole");
	String roleCategory = member.getString("roleCategory");
	long parentGroupAcNo = member.getLong("parentGroupAcNo");
	long currentServerTime = userLoginInfo.getLong("currentServerTime");
	long memberAcNo = userLoginInfo.getLong("memberAcNo");
	String memberDistrictCode = userLoginInfo.getString("memberDistrictCode");
	int memberDistrictId = userLoginInfo.getInt("memberDistrictId");
	long selectedGroupAcNo = userLoginInfo.getLong("selectedGroupAcNo");
	String groupName = userLoginInfo.getString("groupName");
	String groupType = userLoginInfo.getString("groupType");
	String groupDistrictCode = userLoginInfo.getString("groupDistrictCode");
	int groupDistrictId = userLoginInfo.getInt("groupDistrictId");
	String authToken = userLoginInfo.getString("authToken");
	
	String accStr = Long.toString(selectedGroupAcNo);
	int size = accStr.length();
	String formattedGrpAccNum = groupDistrictCode + "-" + accStr.substring(size - 5, size);
%>
<input hidden="true" type="text" id="currentServerTime" value="<%=currentServerTime%>">
<input hidden="true" type="text" id="memberAcNo" value="<%=memberAcNo%>">
<input hidden="true" type="text" id="mdCode" value="<%=memberDistrictCode%>">
<input hidden="true" type="text" id="mdId" value="<%=memberDistrictId%>">
<input hidden="true" type="text" id="groupAcNo" value="<%=selectedGroupAcNo%>">
<input hidden="true" type="text" id="gType" value="<%=groupType%>">
<input hidden="true" type="text" id="gdCode" value="<%=groupDistrictCode%>">
<input hidden="true" type="text" id="gdId" value="<%=groupDistrictId%>">
<input hidden="true" type="text" id="curLang" value="<%=lang%>">
<input hidden="true" type="text" id="mRole" value="<%=mrole%>">
<input hidden="true" type="text" id="roleCategory" value="<%=roleCategory%>">
<input hidden="true" type="text" id="authToken" value="<%=authToken%>">

<div class="header navbar navbar-fixed-top mega-menu">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="header-inner">
		<!-- BEGIN LOGO -->
		<a class="navbar-brand" href="/shg-portal"> <img src="resources/img/shg-one.png" alt="logo" class="img-responsive" width="150" height="50" />
		</a>
		<!-- END LOGO -->
		<!-- BEGIN GROUP NAME -->
		<div id="selGroupNameAcc"><%=formattedGrpAccNum%> - <%=groupName%></div>
		<!-- END GROUP NAME -->
		<!-- BEGIN RESPONSIVE MENU TOGGLER -->
		<a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <img src="resources/img/menu-toggler.png" alt="" />
		</a>
		<!-- END RESPONSIVE MENU TOGGLER -->
		<!-- BEGIN TOP NAVIGATION MENU -->
		<ul class="nav navbar-nav pull-right">
			<li class="">
				<div class="col-md-9" style="padding-top: 15px;">
					<div class="form-group">
						<label class="control-label col-md-9"></label>
						<!-- Hiding language drop down for now -->
						<div class="col-md-3" style="padding-left: 3px;" hidden="true">
							<select id="lang" class="form-control" style="width: 100px;">
								<option>English</option>
								<option>Marathi</option>
								<option>Hindi</option>
							</select>
						</div>
					</div>
				</div>
			</li>
			<!-- BEGIN USER LOGIN DROPDOWN -->
			<li class="dropdown user"><a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
			<!-- Hiding profile photo for now -->
			<img src="resources/img/avatar.png" alt="" width="30" height="30" hidden="true" />
			<span class="username hidden-1024" id="userFullName"><%=fullName%></span> <i class="fa fa-angle-down"></i>
			</a>
				<ul class="dropdown-menu">
<%
	// Don't show my profile if parent group is != to selected group.
	if(parentGroupAcNo == selectedGroupAcNo) {
%>
					<li><a href="profile"> <i class="fa fa-user"></i>My Profile
					</a></li>
<%
	}
%>
					<!-- <li><a href="#"> <i class="fa fa-gear"></i>Change Settings
					</a></li> -->
					<li><a href="logout"> <i class="fa fa-key"></i>Log Out
					</a></li>
				</ul></li>
			<!-- END USER LOGIN DROPDOWN -->
		</ul>
		<!-- END TOP NAVIGATION MENU -->
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
</html>