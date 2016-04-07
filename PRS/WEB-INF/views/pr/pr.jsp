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
    				$("#remark").val(data.prRemark);
    				if( data.editablePrRemark == false ) {
    					$("#remark").attr("disabled", "disabled");
    				}
    				$("#createdDateStr").val(data.createdDateStr);
    				$("#createdByName").val(data.createdByName);
    				$("#status").val(data.status);
    				$("#createdDate").show();
    				$("#createdBy").show();
    				$("#currentStatus").show();
					if( data.submitted ) {
						$("#submitAllDetails").hide();
					} else {
						$("#submitAllDetails").show();
					}
    	    		var showAddDelete = true;
					//Which columns to show for pr items
					var colNames = ['Item Code*', 'Description*', 'Diamensions', 'Quantity Required*', 'UOM*', 'Make', 'Specifications', 'Required Date*', ''];
					var colModel = [
							        {name:'code', width:40, sortable: false, align:'left', resizable: true, editrules:{required:true}, editoptions: {maxlength: 50}},
							        {name:'description', width:80, sortable: false, align:'left', resizable: true, editrules:{required:true}, editoptions: {maxlength: 200}},
							        {name:'diamensions', width:50, sortable: false, align:'left', resizable: true, editrules:{required:false}, editoptions: {maxlength: 100}},
							        {name:'quantityRequired', width:40, sortable: false, align:'right', resizable: true, editrules:{number:true, required:true}, editoptions: {maxlength: 30}},
							        {name:'uom', width:40, sortable: false, align:'left', resizable: true, editrules:{required:true}, editoptions: {maxlength: 30}},
							        {name:'make', width:40, sortable: false, align:'left', resizable: true, editoptions: {maxlength: 40}},
							        {name:'specifications', width:50, sortable: false, align:'left', resizable: true, editoptions: {maxlength: 100}},
							        {name:'requiredByDateStr', width:50, sortable: false, align:'center', resizable: true, formatoptions: {newformat: 'dd-mm-yyyy'}, datefmt: 'dd-mm-yyyy',editoptions: { dataInit: initDate }, editrules:{date:true, required:true}},
							        {name:'priId', hidden:true}
								];
					
					if( data.editablePrItemsRemark == true ) {
						colNames = ['Item Code', 'Description', 'Diamensions', 'Quantity Required', 'UOM', 'Make', 'Specifications', 'Required Date', 'Delivery Date', 'Quantity Ordered', 'Deviation', 'Remark', ''];
						colModel = [
							        {name:'code', width:40, sortable: false, align:'left', resizable: true, editable:false},
							        {name:'description', width:80, sortable: false, align:'left', resizable: true, editable:false},
							        {name:'diamensions', width:50, sortable: false, align:'left', resizable: true, editable:false},
							        {name:'quantityRequired', width:40, sortable: false, align:'right', resizable: true, editable:false},
							        {name:'uom', width:40, sortable: false, align:'left', resizable: true, editable:false},
							        {name:'make', width:40, sortable: false, align:'left', resizable: true, editable:false},
							        {name:'specifications', width:50, sortable: false, align:'left', resizable: true, editable:false},
							        {name:'requiredByDateStr', width:50, sortable: false, align:'center', resizable: true, formatoptions: {newformat: 'dd-mm-yyyy'}, datefmt: 'dd-mm-yyyy',editoptions: { dataInit: initDate }, editable:false},
							        {name:'deliveryDateStr', width:50, sortable: false, align:'center', resizable: true, formatoptions: {newformat: 'dd-mm-yyyy'}, datefmt: 'dd-mm-yyyy',editoptions: { dataInit: initDate }, editrules:{date:true, required:false}},
							        {name:'orderedQuantity', width:30, sortable: false, align:'right', resizable: true, editoptions: {maxlength: 10}},
							        {name:'deviation', width:40, sortable: false, align:'right', resizable: true, editoptions: {maxlength: 100}},
							        {name:'remark', width:80, sortable: false, align:'left', resizable: true, editoptions: {maxlength: 200}},
							        {name:'priId', hidden:true}
								];
						showAddDelete = false;
					}
					
					initGrid(colNames, colModel, showAddDelete);
					
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
			$("#remarkDiv").hide();
    		$("#submitAllDetails").hide();
    		var colNames = ['Item Code*', 'Description*', 'Diamensions', 'Quantity Required*', 'UOM*', 'Make', 'Specifications', 'Required Date*', ''];
			var colModel = [
					        {name:'code', width:40, sortable: false, align:'left', resizable: true, editrules:{required:true}, editoptions: {maxlength: 50}},
					        {name:'description', width:80, sortable: false, align:'left', resizable: true, editrules:{required:true}, editoptions: {maxlength: 200}},
					        {name:'diamensions', width:50, sortable: false, align:'left', resizable: true, editrules:{required:false}, editoptions: {maxlength: 100}},
					        {name:'quantityRequired', width:40, sortable: false, align:'right', resizable: true, editrules:{number:true, required:true}, editoptions: {maxlength: 30}},
					        {name:'uom', width:40, sortable: false, align:'left', resizable: true, editrules:{required:true}, editoptions: {maxlength: 30}},
					        {name:'make', width:40, sortable: false, align:'left', resizable: true, editoptions: {maxlength: 40}},
					        {name:'specifications', width:50, sortable: false, align:'left', resizable: true, editoptions: {maxlength: 100}},
					        {name:'requiredByDateStr', width:50, sortable: false, align:'center', resizable: true, formatoptions: {newformat: 'dd-mm-yyyy'}, datefmt: 'dd-mm-yyyy',editoptions: { dataInit: initDate }, editrules:{date:true, required:true}},
					        {name:'priId', hidden:true}
						];
			initGrid(colNames, colModel, true);
			var parameters = {
		       rowID : "jqg1",
		       initdata : {},
		       position :"first",
		       useDefValues : false,
		       useFormatter : false
		   };
		   jQuery("#addPrItemGrid").jqGrid('addRow',parameters);
		   $("#addPrItemGrid_iladd").addClass("ui-state-disabled");
		   $("#addPrItemGrid_iledit").addClass("ui-state-disabled");
		   $("#addPrItemGrid_ilsave").removeClass("ui-state-disabled");
		   $("#addPrItemGrid_ilcancel").removeClass("ui-state-disabled");
    	}
    }
    initGrid = function(colNames, colModel, showAddDelete){
    	$("#addPrItemGrid").jqGrid({
		    datatype:'local',
		    colNames:colNames,
		    colModel:colModel,
		    width: $("#prheader").width()-30,
		    cmTemplate: {editable: true}, 
		    pager: '#addgridPager',
		    gridview: true,
		    rownumbers: true,
		    rownumWidth: 50,
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
    	$("#addPrItemGrid").jqGrid("setLabel", "rn", "S. No.");
		$("#addPrItemGrid").jqGrid('inlineNav', '#addgridPager', { edittext: "Edit",
		    addtext: "Add",
		    add: showAddDelete, 
		    savetext: "Save",
		    canceltext: "Cancel",
		    addParams: {position: "last",edit: true, del:false}
		});
		$.extend($.jgrid.inlineEdit, {
            keys: true
        });
		$("#addPrItemGrid_ilsave").click(function(){
		});
		
		if( showAddDelete ) {
			$("#addPrItemGrid").navButtonAdd('#addgridPager',{
				   buttonicon:"ui-icon-close", 
				   caption:"Delete", 
				   onClickButton: function(){ 
					   	var gr = jQuery("#addPrItemGrid").jqGrid('getGridParam','selrow');
					   	if( gr != null ){
					 		if(!($($("#addPrItemGrid").jqGrid("getInd",gr,true)).attr("editable") === "1")) {
					        	jQuery("#addPrItemGrid").jqGrid('delGridRow',gr,{reloadAfterSubmit:false});
					       	}
					   	} else{
					    	alert("Please Select Row to delete!");
					   	}
				   }, 
				   position:"last"
			});
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
	});
	</script>
</head>
<body onload="onLoad()">
    <jsp:include page="../menubar.jsp"></jsp:include>
 	<div class="form-container">
	 	<h4 class="text-center">Purchase Requisition</h4>
	 	<hr>
		<form:form method="POST" action="javascript:myFunction();" modelAttribute="purchaseRequisition" class="form-horizontal" id="purchaseRequisition">
			<div class="row">
				<div class="form-group col-md-6">
					<label class="control-lable col-md-3" for="prNo" id="prNoValue">PR No. Prefix: <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="prNo" id="prNo" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="prNo" class="help-inline"/>
						</div>
					</div>
				</div>
				<div class="form-group col-md-5" id="createdDate" style="display: none">
					<label class="control-lable col-md-3" for="prNo" id="prNoValue">Created Date:</label>
					<div class="col-md-5">
						<input type="text" id="createdDateStr" disabled="true" readonly="true" class="form-control input-sm"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<label class="col-md-3 control-lable" for="projectName">Project Name: <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="projectName" id="projectName" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="projectName" class="help-inline"/>
						</div>
					</div>
				</div>
				<div class="form-group col-md-5" id="createdBy" style="display: none">
					<label class="control-lable col-md-3" for="prNo" id="prNoValue">Created By:</label>
					<div class="col-md-5">
						<input type="text" id="createdByName" disabled="true" readonly="true" class="form-control input-sm"/>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-6">
					<label class="col-md-3 control-lable" for="projectCode">Project Code: <span style="color:red;">*</span></label>
					<div class="col-md-5">
						<form:input type="text" path="projectCode" id="projectCode" class="form-control input-sm" required="required"/>
						<div class="has-error">
							<form:errors path="projectCode" class="help-inline"/>
						</div>
					</div>
				</div>
				<div class="form-group col-md-5" id="currentStatus" style="display: none">
					<label class="control-lable col-md-3" for="status">Status:</label>
					<div class="col-md-5">
						<input type="text" id="status" disabled="true" readonly="true" class="form-control input-sm"/>
					</div>
				</div>
			</div>
			<div class="row" id="remarkDiv">
				<div class="form-group col-md-6">
					<label class="col-md-3 control-lable" for="remark">Remark:</label>
					<div class="col-md-5">
						<input type="text" id="remark" class="form-control input-sm"/>
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
	saveDetails( false );
});

$("#submitAllDetails").click(function(e){
	saveDetails( true );
});

function saveDetails( submit ) {
	var rowID = $("#addPrItemGrid").jqGrid('getGridParam', 'selrow');
	if(rowID != null){
		if($($("#addPrItemGrid").jqGrid("getInd",rowID,true)).attr("editable") === "1") {
			jQuery("#addPrItemGrid").jqGrid('saveRow',rowID, { 
				aftersavefunc: function( response ) {
	    		save( submit );
	    	} } );
		} else {
			save( submit );
		}
	} else {
		save( submit );
	}
} 

function save( submit ) {
	if(isValid()){
		var allRowsInGrid = $('#addPrItemGrid').jqGrid('getGridParam','data');
		var data;
		if( createPr == true ) {
			data = {
					prNoPrefix:$("#prNo").val(),
					projectName:$("#projectName").val(),
					projectCode:$("#projectCode").val(),
					purchaseRequisionItems : allRowsInGrid,
					submitted : submit
			};
		} else {
			data = {
					prNo:$("#prNo").val(),
					projectName:$("#projectName").val(),
					projectCode:$("#projectCode").val(),
					prRemark:$("#remark").val(),
					purchaseRequisionItems : allRowsInGrid,
					submitted : submit
			};
		}
		$.ajax({
			type:'POST',
			url: 'rest/purchaseRequest',
			data: JSON.stringify(data),
			dataType: 'json',
			contentType :"application/json",
			success: function(data, status, jqXHR ){
				alert(data.response);
				window.location.href="home";
			},
            error : function(jqXHR, status, error) {
            	if( jqXHR.status == 401 ) {
                	alert('Session Expired');            		
            	} else {
            		alert(jqXHR.responseText);
            	}
            }
		});
	} else {
		alert("Please provide valid Purchase Requisition details.")
	}
}

function isValid (){
	var allRowsInGrid = $('#addPrItemGrid').jqGrid('getGridParam','data');
	if(allRowsInGrid.length == 0){
		return false;
	}
	return true;
}
</script>
</html>