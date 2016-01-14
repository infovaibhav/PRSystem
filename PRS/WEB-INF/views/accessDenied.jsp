<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>Access Denied</title>
	<jsp:include page="header.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="menubar.jsp"></jsp:include>
	<div class="form-container">
		Dear <strong><%=SpringContextUtil.getName() %></strong>, You are not authorized to access this page.
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>