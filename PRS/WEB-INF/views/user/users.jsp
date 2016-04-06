<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>Users</title>
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
        <div style="font-size: 13px;margin: 5px;color: #000080;font-weight: bold;">Users</div>
        <div id="usersHeader" style="width:100%;position:relative;z-index:3;">
            <table id="usersTable">
            </table>
        </div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
	
<script>
$(function () {
        $("#usersTable").jqGrid({
            datatype:'local',
            colNames:['User Id', 'User Name', 'First Name', 'Last Name', 'Email Address', 'Authorized Transaction Limit', 'Reporting To','Roles','Action',''],
            colModel:[
                {name:'id', width:25, sortable: false, align:'center', resizable: true, formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "user", idName:"id"}},
                {name:'ssoId', width:50, sortable: false, align:'left', resizable: true, formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "user", idName:"id"}},
                {name:'firstName', width:40, sortable: false, align:'left', resizable: true, formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "user", idName:"id"}},
                {name:'lastName', width:40, sortable: false, align:'left', resizable: true, formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "user", idName:"id"}},
                {name:'email', width:80, sortable: false, align:'left', resizable: true, formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "user", idName:"id"}},
                {name:'authorizedTransactionLimit', width:30, sortable: false, align:'right', resizable: true},
                {name:'reportingTo', width:60, sortable: false, align:'left', resizable: true},
                {name:'roles', width:80, sortable: false, align:'left', resizable: true},
                {name:'action', width:55, sortable: false, align:'center', resizable: true},
                {name:'status', hidden:true}
			],
            width: $("#usersHeader").width()-30,
            height: "400",
            scroll : true,
            gridview : true,
            loadtext: 'building list...',
            gridComplete: function(){ 
                var ids = jQuery("#usersTable").getDataIDs(); 
                for(var i=0;i<ids.length;i++){ 
                	var selRowData = jQuery("#usersTable").getRowData(ids[i]);
                	var statusAction = "Activate";
                	var resetPassLink = "";
                	//alert( selRowData.status );
                	if( selRowData.status == "true" ) {
                		statusAction = "Deactivate";
                		var colModel = {
            				id		: selRowData.id,
                			ssoId	: selRowData.ssoId
	                	};
                		resetPassLink = "<a id='resetPassword' style='cursor:pointer;' onclick='resetPassword("+JSON.stringify(colModel)+")' ><u>Reset</u></a>";
                	}
                	var actionLinks = "<a id='changeStatus' style='cursor:pointer;' onclick='changeStatus("+selRowData.id+")' ><u>"+ statusAction +"</u></a>"; 
                	if( resetPassLink.length > 0 ) {
                		actionLinks = actionLinks + " | " + resetPassLink;
                	}
                	jQuery("#usersTable").setRowData(ids[i],{action:actionLinks});
                }
            },
            jsonReader: {
                repeatitems: false,
            },
            loadError: function(jqXHR, status, error) {
            	if( jqXHR.status == 401 ) {
                	jQuery("#usersTable").html('<div style="height: 205px">Session Expired</div>');            		
            	} else if ( jqXHR.responseText.length == 0 ) {
            		jQuery("#usersTable").html('<div style="height: 205px">Service Unavailable</div>');
            	} else {
                	jQuery("#usersTable").html('<div style="height: 205px">' + jqXHR.statusText + '</div>');
            	}
            },
            rownumbers: true
        });
    	
        loadUsers();
   });
   
   function loadUsers() {
	   var newUrlUsersTable = "rest/user/_search";
       $("#usersTable").jqGrid().setGridParam({
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
        $("#usersTable").jqGrid().trigger('reloadGrid');
   }
   
   function changeStatus( userId ) {
	   if(confirm('Do you want to change the status?')){
	    	$.ajax({
	            url: 'rest/user/'+userId+'/changestatus',
	            type: 'PUT',
	            success: function(data, textStatus, jqXHR) { 
	                alert("Status Changed Successfully.");
	                loadUsers();
	            },
	            error: function(jqXHR, textStatus, errorThrown) { 
	            	if( jqXHR.status == 401 ) {
	            		alert("Unsuccessful - Session Expired.");
	            	} else if( jqXHR.responseText.length == 0 ) {
	            		alert("Service Unavailable");
	            	} else {
	                    alert(jqXHR.statusText); 	
	            	}
	            }
	        });
	    }
   }
   
   function resetPassword( user ) {
	   if(confirm('Do you want to reset password for user ' + user.ssoId + ' ?')){
	    	$.ajax({
	            url: 'rest/user/'+user.id+'/resetpassword',
	            type: 'PUT',
	            success: function(data, textStatus, jqXHR) { 
	                alert("Password reset Successfully.");
	            },
	            error: function(jqXHR, textStatus, errorThrown) { 
	            	if( jqXHR.status == 401 ) {
	            		alert("Unsuccessful - Session Expired.");
	            	} else if( jqXHR.responseText.length == 0 ) {
	            		alert("Service Unavailable");
	            	} else {
	                    alert(jqXHR.statusText); 	
	            	}
	            }
	        });
	    }
   }
</script>
</body>
</html>