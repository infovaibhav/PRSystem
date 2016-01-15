<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>User Registration</title>
	<jsp:include page="header.jsp"></jsp:include>
	<link rel="stylesheet" href="static/css/bootstrap-datepicker.css"></link>
	<script src="static/js/bootstrap/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
	$(function () {
		$("#editgrid").jqGrid({
		    datatype:'local',
		    colNames:['USER ID', 'USER NAME', 'FIRST NAME', 'LAST NAME', 'EMAIL', 'AUTHORIZED TXN LIMIT', 'REPORTING TO','ROLES','STATUS'],
		    colModel:[
		        {name:'id', width:30, sortable: false, align:'center', resizable: true},
		        {name:'ssoId', width:40, sortable: false, align:'left', resizable: true},
		        {name:'firstName', width:40, sortable: false, align:'left', resizable: true},
		        {name:'lastName', width:40, sortable: false, align:'left', resizable: true},
		        {name:'email', width:60, sortable: false, align:'left', resizable: true},
		        {name:'authorizedTransactionLimit', width:40, sortable: true, align:'right', resizable: true},
		        {name:'reportingTo', width:60, sortable: false, align:'left', resizable: true},
		        {name:'roles', width:80, sortable: false, align:'left', resizable: true},
		        {name:'status', width:30, sortable: false, align:'center', resizable: true}
			],
		    width: $("#usersHeader").width()-30,
		    height: "400",
		    scroll : true,
		    gridview : true,
		    loadtext: 'building list...',
		    jsonReader: {
		        repeatitems: false,
		    },
		    loadError: function(jqXHR, status, error) {
		    	if( jqXHR.status == 401 ) {
		        	jQuery("#editgrid").html('<div style="height: 205px">Session Expired</div>');            		
		    	} else if ( jqXHR.responseText.length == 0 ) {
		    		jQuery("#editgrid").html('<div style="height: 205px">Service Unavailable</div>');
		    	} else {
		        	jQuery("#editgrid").html('<div style="height: 205px">' + jqXHR.statusText + '</div>');
		    	}
		    },
		    rownumbers: true
		});
	});
	</script>
</head>
<body>
    <jsp:include page="menubar.jsp"></jsp:include>
 	<div class="form-container">
 		<h2 class="text-center">IRY Engineering Pvt Ltd</h2>
	 	<h3 class="text-center">Purchase Requisition</h3>
		<form:form method="POST" modelAttribute="purchaseRequisition" class="form-horizontal">
			<div class="row">
				<div class="form-group col-md-6">
					<label class="control-lable col-md-3" for="prNo">PR No. <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="prNo" id="prNo" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="prNo" class="help-inline"/>
						</div>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="control-lable col-md-3" for="createdDate">Date <span style="color:red;">*</span></label>
					<div class='input-group col-md-4 date' style='padding-left:15px;' id= "datepicker">
	                    <form:input type="text" path="createdDate" id="createdDate" class="form-control input-sm" required="required"/>
	                    <div class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
						</div>
	                    <div class="has-error">
							<form:errors path="createdDate" class="help-inline"/>
						</div>
                	</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<label class="col-md-3 control-lable" for="projectName">Project Name <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="projectName" id="projectName" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="projectName" class="help-inline"/>
						</div>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="control-lable col-md-3" for="rev">Rev <span style="color:red;">*</span></label>
					<div class="col-md-4">
						<form:input type="text" path="rev" id="rev" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="rev" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<label class="col-md-3 control-lable" for="projectCode">Project Code <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="projectCode" id="projectCode" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="projectCode" class="help-inline"/>
						</div>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="control-lable col-md-3" for="ponumber">PO No. </label>
					<div class="col-md-4">
						<input type="text" id="ponumber" class="form-control input-sm" required="required"/>
					</div>
				</div>
			</div>
		</form:form>
		<div id="prheader" style="width:100%;position:relative;z-index:3;"> 
            <table id="editgrid">
            </table>
        </div> 
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
$('#datepicker').datepicker({
    format: 'dd/mm/yyyy',
    autoclose:true
});
</script>
</html>