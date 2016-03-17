<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>

		<%
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat format = new SimpleDateFormat( "dd-MM-yyyy" );
		    String toDate = format.format(cal.getTime());
		    cal.add(Calendar.DAY_OF_YEAR, -60);
		    String fromDate = format.format(cal.getTime());
		%>

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
		<script>
			$(function () {
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
		</script>