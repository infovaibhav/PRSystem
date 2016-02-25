<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<% String prNo = request.getParameter("prNo"); %>
<% if( prNo == null) { %>
	<title>New Purchase Requisition</title>
	<script type="text/javascript">
		var createPr = true;
	</script>
<% } else {	%>
	<title>Edit Purchase Requisition</title>
	<script type="text/javascript">
		var createPr = false;
	</script>
<% } %>
	<jsp:include page="../header.jsp"></jsp:include>
	<link rel="stylesheet" href="static/css/jash.css"></link>
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
    <script type="text/javascript">
    function myFunction(){
    	
    }
    function onLoad(){
    	var path = window.location.href.substr(window.location.href.lastIndexOf("/")+1);
    	if(createPr == false){
    		$("#Download").show();
    		$("#submitAllDetails").show();
    		$("#prNoValue").html("PR No.  <span style='color:red;'>*</span>");
    		prno = path.substr(path.lastIndexOf("=")+1);
    		$("#Download").attr('href','rest/purchaseRequest/'+prno+'/download');
    		$.ajax({
    			type:'GET',
    			url: 'rest/purchaseRequest/'+prno,
    			dataType: 'json',
    			contentType :"application/json",
    			success: function(data, status, jqXHR ){
    				$("#prNo").val(data.prNo);
    				$("#prNo").attr("disabled", "disabled");
    				$("#projectName").val(data.projectName);
    				$("#projectName").attr("disabled", "disabled");
    				$("#projectCode").val(data.projectCode);
    				$("#projectCode").attr("disabled", "disabled");
    				$('#addPrItemGrid').jqGrid('setGridParam', {data: data.purchaseRequisionItems}).trigger('reloadGrid');
    			},
                error : function(jqXHR, status, error) {
                	if( jqXHR.status == 401 ) {
                    	jQuery("#addPrItemGrid").html('<div style="height: 205px">Session Expired</div>');            		
                	} else if ( jqXHR.responseText.length == 0 ) {
                		jQuery("#addPrItemGrid").html('<div style="height: 205px">Service Unavailable</div>');
                	} else {
                    	jQuery("#addPrItemGrid").html('<div style="height: 205px">' + jqXHR.statusText + '</div>');
                	}
                }
    		});
    	} else {
    		$("#submitAllDetails").hide();
    	}
    }
    
    </script>
	<script type="text/javascript">
	$(function () {
		initDate = function (elem) {
            setTimeout(function () {
                $(elem).datepicker({
                    format: 'dd-mm-yyyy',
                    autoSize: false,
                    changeYear: true,
                    changeMonth: true,
                    showWeek: true,
                    showButtonPanel: true,
                    autoclose: true,
                    minDate: 0
                });
            }, 100);
		}
		$("#addPrItemGrid").jqGrid({
		    datatype:'local',
		    colNames:['Description*', 'Total Qty required*', 'Qty In stock', 'Qty to be Purchased*', 'UOM', 'Unit Value', 'Approx. Total Value','Make','Cat No.','Required by date','Preferred Supplier',''],
		    colModel:[
		        {name:'description', width:80, sortable: false, align:'center', resizable: true, editrules:{required:true}},
		        {name:'totalQuantityRequired', width:40, sortable: false, align:'center', resizable: true, editrules:{number:true, required:true}},
		        {name:'quantityInStock', width:40, sortable: false, align:'left', resizable: true, editrules:{number:true}},
		        {name:'quantityTobePurchased', width:40, sortable: false, align:'left', resizable: true, editrules:{number:true, required:true}},
		        {name:'uom', width:40, sortable: false, align:'left', resizable: true},
		        {name:'unitCost', width:40, sortable: true, align:'right', resizable: true, editrules:{number:true}},
		        {name:'approxTotalCost', width:40, sortable: false, align:'left', resizable: true, editrules:{number:true}},
		        {name:'make', width:40, sortable: false, align:'left', resizable: true},
		        {name:'catNo', width:30, sortable: false, align:'center', resizable: true},
		        {name:'requiredByDateStr', width:50, sortable: false, align:'center', resizable: true, formatoptions: {newformat: 'dd-mm-yyyy'}, datefmt: 'dd-mm-yyyy',editoptions: { dataInit: initDate }, editrules:{date:true}},
		        {name:'preferredSupplier', width:30, sortable: false, align:'center', resizable: true},
		        {name:'priId', hidden:true}
			],
		    width: $("#prheader").width()-30,
		    cmTemplate: {editable: true}, 
		    pager: '#addgridPager',
		    gridview: true,
		    rownumbers: true,
		    pgbuttons : false,
		    pginput : false,
		    editurl: "clientArray",
		    jsonReader: {
		        repeatitems: false,
		    },
		    loadError: function(jqXHR, status, error) {
		    	if( jqXHR.status == 401 ) {
		        	jQuery("#addPrItemGrid").html('<div style="height: 205px">Session Expired</div>');            		
		    	} else if ( jqXHR.responseText.length == 0 ) {
		    		jQuery("#addPrItemGrid").html('<div style="height: 205px">Service Unavailable</div>');
		    	} else {
		        	jQuery("#addPrItemGrid").html('<div style="height: 205px">' + jqXHR.statusText + '</div>');
		    	}
		    }
		});
		$("#addPrItemGrid").jqGrid('inlineNav', '#addgridPager', { edittext: "Edit",
		    addtext: "Add",
		    savetext: "Save",
		    canceltext: "Cancel",
		    addParams: {position: "last",edit: true, del:false}
		});
		$.extend($.jgrid.inlineEdit, {
            keys: true
        });
		$("#addPrItemGrid_ilsave").click(function(){
		});
		$("#addPrItemGrid").navButtonAdd('#addgridPager',{
			   buttonicon:"ui-icon-close", 
			   caption:"Delete", 
			   onClickButton: function(){ 
				   var gr = jQuery("#addPrItemGrid").jqGrid('getGridParam','selrow');
					if( gr != null ) jQuery("#addPrItemGrid").jqGrid('delGridRow',gr,{reloadAfterSubmit:false});
					else alert("Please Select Row to delete!");
			   }, 
			   position:"last"
			});
	});
	</script>
</head>
<body onload="onLoad()">
    <jsp:include page="../menubar.jsp"></jsp:include>
 	<div class="form-container">
 		<h2 class="text-center">IRY Engineering Pvt Ltd</h2>
	 	<h3 class="text-center">Purchase Requisition</h3>
		<form:form method="POST" action="javascript:myFunction();" modelAttribute="purchaseRequisition" class="form-horizontal" id="purchaseRequisition">
			<div class="row">
				<div class="form-group col-md-6">
					<label class="control-lable col-md-3" for="prNo" id="prNoValue">PR No. Prefix <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="prNo" id="prNo" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="prNo" class="help-inline"/>
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
			</div>
			<div id="prheader" style="width:100%;position:relative;z-index:3;"> 
	            <table id="addPrItemGrid"></table>
	            <div id="addgridPager"></div>
        		<a style="margin-top:5px;margin-right:30px;float:right; display:none;" id="Download"  class="btn btn-primary btn-sm" >Download</a>
	            <input type="SUBMIT" style="margin-top:5px;margin-right:30px;float:right;" id="saveAllDetails" class="btn btn-primary btn-sm" value="Save" />
	            <input type="SUBMIT" style="margin-top:5px;margin-right:30px;float:right;" id="submitAllDetails" class="btn btn-primary btn-sm" value="Submit" />
	        </div> 
        </form:form>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script>
$("#saveAllDetails").click(function(e){
	if(isValid()){
		var allRowsInGrid = $('#addPrItemGrid').jqGrid('getGridParam','data');
		var data;
		if( createPr == true ) {
			data = {
					projectName:$("#projectName").val(),
					projectCode:$("#projectCode").val(),
					prNoPrefix:$("#prNo").val(),
					purchaseRequisionItems : allRowsInGrid
			};
		} else {
			data = {
					prNo:$("#prNo").val(),
					projectName:$("#projectName").val(),
					projectCode:$("#projectCode").val(),
					purchaseRequisionItems : allRowsInGrid
			};
		}
		$.ajax({
			type:'POST',
			url: 'rest/purchaseRequest',
			data: JSON.stringify(data),
			dataType: 'json',
			contentType :"application/json",
			success: function(data, status, jqXHR ){
				if(createPr){
					alert("Purchase Requisition created Successfully.");
				} else {
					alert("Purchase Requisition updated Successfully.");
				}
				
				window.location.href="myPR";
			},
            error : function(jqXHR, status, error) {
            	if( jqXHR.status == 401 ) {
                	alert('Session Expired');            		
            	} else {
            		alert(jqXHR.statusText);
            	}
            }
		});
	} else {
		alert("Please provide valid Purchase Requisition details.")
	}
});

$("#submitAllDetails").click(function(e){
	if(isValid()){
		if(confirm("Do you want to Submit?")){
			var allRowsInGrid = $('#addPrItemGrid').jqGrid('getGridParam','data');
			var data;
			if( createPr == true ) {
				data = {
						projectName:$("#projectName").val(),
						projectCode:$("#projectCode").val(),
						prNoPrefix:$("#prNo").val(),
						submitted : true,
						purchaseRequisionItems : allRowsInGrid
				};
			} else {
				data = {
						prNo:$("#prNo").val(),
						projectName:$("#projectName").val(),
						projectCode:$("#projectCode").val(),
						submitted : true,
						purchaseRequisionItems : allRowsInGrid
				};
			}
			$.ajax({
				type:'POST',
				url: 'rest/purchaseRequest',
				data: JSON.stringify(data),
				dataType: 'json',
				contentType :"application/json",
				success: function(data, status, jqXHR ){
					if(createPr){
						alert("Purchase Requisition Submitted Successfully.");
					} else {
						alert("Purchase Requisition updated Successfully.");
					}
					
					window.location.href="myPR";
				},
	            error : function(jqXHR, status, error) {
	            	if( jqXHR.status == 401 ) {
	                	alert('Session Expired');            		
	            	} else {
	            		alert(jqXHR.statusText);
	            	}
	            }
			});
		}
	} else {
		alert("Please provide valid Purchase Requisition details.")
	}
});

function isValid (){
	var allRowsInGrid = $('#addPrItemGrid').jqGrid('getGridParam','data');
	if(allRowsInGrid.length == 0){
		return false;
	}
	return true;
}
</script>
</html>