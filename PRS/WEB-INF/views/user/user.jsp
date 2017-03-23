<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>User</title>
	<jsp:include page="../header.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="../menubar.jsp"></jsp:include>
 	<div class="form-container">
	 	<h1>User Form</h1>
		<form:form method="POST" modelAttribute="user" class="form-horizontal">
	
			<form:input type="hidden" path="id" id="id" class="form-control input-sm"/>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-lable" for="firstName">First Name <span style="color:red;">*</span></label>
					<div class="col-md-3">
						<form:input type="text" path="firstName" id="firstName" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="firstName" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-lable" for="lastName">Last Name <span style="color:red;">*</span></label>
					<div class="col-md-3">
						<form:input type="text" path="lastName" id="lastName" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="lastName" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-lable" for="ssoId">User Name <span style="color:red;">*</span></label>
					<div class="col-md-3">
						<form:input type="text" path="ssoId" id="ssoId" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="ssoId" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group required col-md-12">
					<label class="col-md-2 control-lable" for="email">Email <span style="color:red;">*</span></label>
					<div class="col-md-3">
						<form:input type="email" path="email" id="email" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="email" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group required col-md-12">
					<label class="col-md-2 control-lable" for="emailNotification">Subscribe for Email Notification </label>
					<div class="col-md-3">
						<form:checkbox  path="emailNotification" id="emailNotification"/>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-lable" for="authorizedTransactionLimit">Authorized Transaction Limit</label>
					<div class="col-md-3">
						<form:input type="number" path="authorizedTransactionLimit" id="authorizedTransactionLimit" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="authorizedTransactionLimit" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-2 control-lable" for="reportingTo">Reporting To <span style="color:red;">*</span></label>
					<div class="col-md-3">
						<form:select path="reportingTo" items="${users}" itemValue="id" itemLabel="ssoId" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="reportingTo" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group required col-md-12">
					<label class="col-md-2 control-lable" for="userProfiles">Roles <span style="color:red;">*</span></label>
					<div class="col-md-3">
						<form:select path="userProfiles" items="${roles}" multiple="true" itemValue="id" itemLabel="type" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="userProfiles" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-actions col-md-5">
					<div class="floatRight">
						<input type="submit" value="Save" class="btn btn-primary btn-sm"> &nbsp; <a href="home"><input type="button" value="Cancel" class="btn btn-primary btn-sm"></a>
					</div>
				</div>
			</div>
		</form:form>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>