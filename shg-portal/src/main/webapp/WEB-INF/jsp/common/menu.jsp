<%@page import="com.beone.shg.net.utils.AccessUtil"%>
<%@page import="java.util.Collection"%>
<%@page import="org.json.JSONObject"%>
<html lang="en" class="no-js">
<%
	String view = (String) session.getAttribute("requestedView");
	Collection<?> userPages = (Collection<?>) session.getAttribute("userPages");

	Object[] pages = AccessUtil.getAllPages().toArray();
	String[] status = new String[pages.length];

	for (int i = 0; i < pages.length; i++) {
		if (view.equals(pages[i])) {
			status[i] = "active";
			pages[i] = "#";
			break;
		}
	}
%>
<div class="col-md-3">
	<ul class="ver-inline-menu tabbable margin-bottom-10">
		<%
			if (userPages.contains("manage")) {
		%>
		<li class="<%=status[0]%>"><a href="<%=pages[0]%>"> <i class="fa fa-edit"></i> Manage
		</a></li>
		<%
			}
			if (userPages.contains("dashboard")) {
		%>
		<li class="<%=status[1]%>"><a href="<%=pages[1]%>"> <i class="fa fa-home"></i> Dashboard
		</a></li>
		<%
			}
			if (userPages.contains("transaction")) {
		%>
		<li class="<%=status[2]%>"><a href="<%=pages[2]%>"> <i class="fa fa-tasks"></i> Transaction
		</a></li>
		<%
			}
			if (userPages.contains("account")) {
		%>
		<li class="<%=status[3]%>"><a href="<%=pages[3]%>"> <i class="fa fa-book"></i> Account
		</a></li>
		<%
			}
			if (userPages.contains("reports")) {
		%>
		<li class="<%=status[4]%>"><a href="<%=pages[4]%>"> <i class="fa fa-bar-chart-o"></i> Reports
		</a></li>
		<%
			}
			if (userPages.contains("application")) {
		%>
		<li class="<%=status[5]%>"><a href="<%=pages[5]%>"> <i class="fa fa-edit"></i> Application
		</a></li>
		<%
			}
			if (userPages.contains("bank")) {
		%>
		<li class="<%=status[6]%>"><a href="<%=pages[6]%>"> <i class="fa fa-inr"></i> Bank
		</a></li>
		<%
			}
			if (userPages.contains("purchase")) {
		%>
		<li class="<%=status[7]%>"><a href="<%=pages[7]%>"> <i class="fa fa-inr"></i> Purchase
		</a></li>
		<%
			}
			if (userPages.contains("profile")) {
		%>
		<li class="<%=status[8]%>"><a href="<%=pages[8]%>"> <i class="fa fa-user"></i> Profile
		</a></li>
		<%
			}
			if (userPages.contains("contact")) {
		%>
		<li class="<%=status[9]%>"><a href="<%=pages[9]%>"> <i class="fa fa-envelope"></i> Contact Us
		</a></li>
		<%
			}
		%>
	</ul>

	<!--              <div id="slideshow">
					<img src="resources/img/img1.jpg" width="306" height="267" class="active"  />
					<img src="resources/img/img2.jpg" width="306" height="267"/>
					<img src="resources/img/img3.jpg" width="306" height="267"/>
					<img src="resources/img/img4.jpg" width="306" height="267"/>
				  </div>
 -->
</div>
</html>