<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>My Purchase Requisitions</title>
	<jsp:include page="../header.jsp"></jsp:include>
	<style type="text/css" media="screen">
	    th.ui-th-column div{
	        white-space:normal !important;
	        height:auto !important;
	        padding:2px;
	    }
	    .ui-jqgrid .ui-jqgrid-resize {height:100% !important;}
    </style>
</head>
<body>
    <jsp:include page="../menubar.jsp"></jsp:include>
	<div class="form-container">
        <!-- <div class="row">
			<div class="form-group col-md-4">
				<label class="col-md-3 control-lable" for="fromDate" style="padding-top: 5px">From Date:</label>
				<div class="col-md-5">
					<input type="text" path="projectName" id="fromDate" class="form-control input-sm"/>
				</div>
			</div>
			<div class="form-group col-md-4">
				<label class="col-md-3 control-lable" for="toDate" style="padding-top: 5px">To Date:</label>
				<div class="col-md-5">
					<input type="text" id="toDate" class="form-control input-sm"/>
				</div>
			</div>
		</div> -->
		
		<jsp:include page="prgrid.jsp"></jsp:include>
	
	</div>
	<script>
		$(function () {
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
					postData['pageSize'] =  defaultPageSize;
				    return JSON.stringify(postData);
				}
	    	});
	         $("#prTable").jqGrid().trigger('reloadGrid');
	   });
	</script>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>