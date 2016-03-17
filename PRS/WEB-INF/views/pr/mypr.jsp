<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="org.iry.utils.SpringContextUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>My Purchase Requisitions</title>
	<jsp:include page="../header.jsp"></jsp:include>
	<link rel="stylesheet" href="static/css/bootstrap-datepicker.css"></link>
	<script src="static/js/bootstrap/bootstrap-datepicker.js"></script>
	<style type="text/css" media="screen">
	    th.ui-th-column div{
	        white-space:normal !important;
	        height:auto !important;
	        padding:2px;
	    }
	    .ui-jqgrid .ui-jqgrid-resize {height:100% !important;}
    </style>
</head>
<%
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat( "dd-MM-yyyy" );
    String toDate = format.format(cal.getTime());
    cal.add(Calendar.DAY_OF_YEAR, -60);
    String fromDate = format.format(cal.getTime());
%>
<body>
    <jsp:include page="../menubar.jsp"></jsp:include>
	<div class="form-container">
		<input type="hidden" id="createdBy" value="<%=SpringContextUtil.getUserId()%>"/>
        <div class="row">
			<div class="form-group col-md-3">
				<label class="col-md-4 control-lable" for="prNo" style="padding-top: 5px">Pr No:</label>
				<div class="col-md-7">
					<input type="text" id="prNo" class="form-control input-sm"/>
				</div>
			</div>
			<div class="form-group col-md-3">
				<label class="col-md-5 control-lable" for="projectCode" style="padding-top: 5px">Project Code:</label>
				<div class="col-md-7">
					<input type="text" id="projectCode" class="form-control input-sm"/>
				</div>
			</div>
			<div class="form-group col-md-3">
				<label class="col-md-5 control-lable" for="projectName" style="padding-top: 5px">Project Name:</label>
				<div class="col-md-7">
					<input type="text" id="projectName" class="form-control input-sm"/>
				</div>
        	</div>
			<div class="form-group col-md-3">
				<label class="col-md-5 control-lable" for="exactMatch" style="padding-top: 5px">Exact Match:</label>
				<div class="col-md-2">
					<input type="checkbox" id="exactMatch" value="true"/>
				</div>
			</div>
		</div>
        <div class="row">
			<div class="form-group col-md-3">
				<label class="col-md-4 control-lable" for="fromDate" style="padding-top: 5px">From Date:</label>
				<div class="col-md-7">
					<input type="text" id="fromDate" readonly="readonly" class="form-control input-sm" value="<%=fromDate %>"/>
				</div>
			</div>
			<div class="form-group col-md-3">
				<label class="col-md-5 control-lable" for="toDate" style="padding-top: 5px">To Date:</label>
				<div class="col-md-7">
					<input type="text" id="toDate" readonly="readonly" class="form-control input-sm" value="<%=toDate %>"/>
				</div>
			</div>
			<div class="form-group col-md-3">
				<div class="col-md-4">
        			<input type="button" id="search" class="btn btn-primary" value="Search" onclick="loadData();"/>
        		</div>
        	</div>
		</div>
		<jsp:include page="prgrid.jsp"></jsp:include>
	</div>
	<script>
		$(function () {
			loadData();
	        $("#prTable").jqGrid().trigger('reloadGrid');
         	$('#fromDate').datepicker({
            	format: 'dd-mm-yyyy',
                autoSize: false,
                changeYear: true,
                changeMonth: true,
                showWeek: true,
                showButtonPanel: true,
                autoclose: true,
                minDate: 0
            });
         	$('#toDate').datepicker({
                format: 'dd-mm-yyyy',
                autoSize: false,
                changeYear: true,
                changeMonth: true,
                showWeek: true,
                showButtonPanel: true,
                autoclose: true,
                minDate: 0
            });
	   	});
		
		function loadData() {
			var newUrlUsersTable = "rest/purchaseRequest/_search";
			$("#prTable").jqGrid().setGridParam({
	    		url : newUrlUsersTable, 
	    		page : 1, 
	    		mtype:'POST',
	    		datatype : "json",
				ajaxGridOptions: { 
					type :'POST',
					contentType :"application/json; charset=utf-8"
				},
				serializeGridData: function(postData) {
					postData['pageSize'] = defaultPageSize;
					postData['exactMatch'] = $('#exactMatch').is(':checked');
					postData['prNo'] = $('#prNo').val();
					postData['projectName'] = $('#projectName').val();
					postData['projectCode'] = $('#projectCode').val();
					postData['createdBy'] = [$('#createdBy').val()];
					postData['fromTimeStr'] = $('#fromDate').val();
					postData['toTimeStr'] = $('#toDate').val();
				    return JSON.stringify(postData);
				}
	    	});
			$("#prTable").jqGrid().trigger('reloadGrid');
		}
	</script>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>