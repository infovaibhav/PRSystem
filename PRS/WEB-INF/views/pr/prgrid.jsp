    <div style="font-size: 13px;margin: 5px;color: #000080;font-weight: bold;">Purchase Requisitions</div>
    <div id="prHeader" style="width:100%;position:relative;z-index:3;">
        <table id="prTable"></table>
    </div>
    <style>
    .myLink { text-decoration: underline; cursor: pointer; }
    </style>
	<script>
		$(function () {
	        $("#prTable").jqGrid({
	            datatype:'local',
	            colNames:['PR No', 'Project Name', 'Project Code', 'Rev', 'Prepared Date', 'Prepared By', 'Status', 'Action'],
	            colModel:[
	                {name:'prNo', index:'prNo',key:true, width:80, sortable: false, align:'center', resizable: true, formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "editPR", idName:"prNo"} },
	                {name:'projectName', width:80, sortable: false, align:'left', resizable: true},
	                {name:'projectCode', width:80, sortable: false, align:'left', resizable: true},
	                {name:'rev', width:80, sortable: false, align:'left', resizable: true},
	                {name:'createdDateStr', width:80, sortable: false, align:'left', resizable: true},
	                {name:'createdByName', width:80, sortable: false, align:'left', resizable: true},
	                {name:'status', width:80, sortable: false, align:'center', resizable: true},
	                {name:'', width:80, sortable: false, align:'center', resizable: true}
				],
				width: $("#prHeader").width()-30,
	            height: "400",
	            scroll : true,
	            gridview : true,
	            loadtext: 'building list...',
	            jsonReader: {
	                repeatitems: false,
	            },
	            subGrid : true,
	            subGridOptions: { 
	            	"plusicon" : "ui-icon-triangle-1-e",
			     	"minusicon" :"ui-icon-triangle-1-s",
	                "openicon" : "ui-icon-arrowreturn-1-e",
	                "reloadOnExpand" : false,
	                "selectOnExpand" : true
	            },
	            subGridRowExpanded: function (subgridId, rowid) {
	            	
	            	var rowData = jQuery("#prTable").getRowData(rowid);
	            	var prNo = rowData['prNo'];
	            	var html = "";
	            	var priSubgridTableId = subgridId + "_pri";
            		$("#" + subgridId).html(html + "<div>&nbsp</div><div style='margin: 5px;color: #000080;font-weight: bold;'>Items - </div><table id='" + priSubgridTableId + "'></table><div>&nbsp</div>");
	                
	                $("#" + priSubgridTableId).jqGrid({
	                	datatype:'local',
	                	mtype: 'GET',
	                	colNames:['Description*', 'Total Qty required*', 'Qty In stock', 'Qty to be Purchased*', 'UOM', 'Unit Value', 'Approx. Total Value','Make','Cat No.','Required by date','Preferred Supplier'],
	        		    colModel:[
	        		        {name:'description', width:80, sortable: false, align:'center', resizable: true},
	        		        {name:'totalQuantityRequired', width:40, sortable: false, align:'center', resizable: true},
	        		        {name:'quantityInStock', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'quantityTobePurchased', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'uom', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'unitCost', width:30, sortable: false, align:'right', resizable: true},
	        		        {name:'approxTotalCost', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'make', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'catNo', width:40, sortable: false, align:'center', resizable: true},
	        		        {name:'requiredByDateStr', width:50, sortable: false, align:'center', resizable: true},
	        		        {name:'preferredSupplier', width:40, sortable: false, align:'center', resizable: true}
	        			],
	    				width: $("#prHeader").width()-111,
	                    height: '100%',
	                    loadtext: 'building list...',
	                    jsonReader: {
	                        repeatitems: false,
	                    },
	                    idPrefix: "sd_" + rowid + "_",
	                    jsonReader: {
	                        repeatitems: false,
	                    },
	                    loadError: function(jqXHR, status, error) {
	                    	if( jqXHR.status == 401 ) {
	                        	alert('Session Expired');            		
	                    	} else if( jqXHR.responseText.length == 0 ) {
	                    		alert('Service Unavailable');
	                    	} else {
	                        	alert(jqXHR.statusText);
	                    	}
	                    }
	                });
	                
	                $(".ui-jqgrid .subgrid-data .ui-th-column").each(function() {
	                	var HeaderFontColor = "#48b8e5";
	                	this.style.color = HeaderFontColor;
	               	});
	                
	            	var getPrItemsUrl =  "rest/purchaseRequest/"+prNo+"/items";
	            	$("#"+priSubgridTableId).jqGrid().setGridParam({url : getPrItemsUrl, page : 1, datatype : "json"});
	                $("#"+priSubgridTableId).jqGrid().trigger('reloadGrid');
	                
	            },
	            loadError: function(jqXHR, status, error) {
	            	if( jqXHR.status == 401 ) {
	                	jQuery("#prTable").html('<div style="height: 205px">Session Expired</div>');            		
	            	} else if ( jqXHR.responseText.length == 0 ) {
	            		jQuery("#prTable").html('<div style="height: 205px">Service Unavailable</div>');
	            	} else {
	                	jQuery("#prTable").html('<div style="height: 205px">' + jqXHR.statusText + '</div>');
	            	}
	            },
	            rownumbers: true
	        });
	    	
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
		function Link(id) {
			alert("test");
		    var row = id.split("=");
		    var row_ID = row[1];
		    var sitename= $("#users_grid").getCell(row_ID, 'Site_Name');
		    var url = "http://"+sitename; // sitename will be like google.com or yahoo.com

		    window.open(url);


		}
	</script>