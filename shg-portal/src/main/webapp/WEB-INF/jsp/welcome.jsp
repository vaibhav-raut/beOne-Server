<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
	<%
		Object requestedView = session.getAttribute("requestedView");
		if (requestedView == null)
			response.sendRedirect("/shg-portal");
		else
			response.sendRedirect(requestedView.toString());
	%>

	<h3>${message}</h3>

	Click here to
	<a href="login">login</a>.
</body>
</html>