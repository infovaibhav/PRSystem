<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>Registration Successful</title>
	<jsp:include page="header.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="menubar.jsp"></jsp:include>
	<div class="form-container">
		<div class="success">
			Confirmation message : ${success}
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>