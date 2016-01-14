<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>Users</title>
	<jsp:include page="header.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="menubar.jsp"></jsp:include>
	<div class="form-container">
        <div style="font-size: 13px;margin: 5px;color: #000080;font-weight: bold;">Users</div>
        <div id="usersHeader" style="width:100%;position:relative;z-index:3;">
            <table id="usersTable">
            </table>
        </div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	
<script>
$(function () {
        $("#usersTable").jqGrid({
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
                	jQuery("#usersTable").html('<div style="height: 205px">Session Expired</div>');            		
            	} else if ( jqXHR.responseText.length == 0 ) {
            		jQuery("#usersTable").html('<div style="height: 205px">Service Unavailable</div>');
            	} else {
                	jQuery("#usersTable").html('<div style="height: 205px">' + jqXHR.statusText + '</div>');
            	}
            },
            rownumbers: true
        });
    	
        var newUrlUsersTable = "rest/users";
        $("#usersTable").jqGrid().setGridParam({
    		url : newUrlUsersTable, 
    		page : 1, 
    		mtype:'GET',
    		datatype : "json"
    	});
         $("#usersTable").jqGrid().trigger('reloadGrid');
   });
</script>
</body>
</html>