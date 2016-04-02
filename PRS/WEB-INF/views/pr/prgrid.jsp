    <link rel="stylesheet" href="static/css/jash.css"></link>
    <div style="font-size: 13px;margin: 5px;color: #000080;font-weight: bold;">Purchase Requisitions : <span id='progress' style='display: none;' ><img src='static/images/progress_bar.gif'/></span></div>
    <div id="prHeader" style="width:100%;position:relative;z-index:3;">
        <table id="prTable"></table>
    </div>
    <style>
    	.myLink { text-decoration: underline; cursor: pointer; }
    </style>
	<script>
		$(function () {
			var lastsel;
	        $("#prTable").jqGrid({
	            datatype:'local',
	            colNames:['PR No', 'Project Name', 'Project Code', 'Prepared By', 'Prepared Date', 'Last Updated By', 'Last Updated Date', 'Status', 'Action','','','','','','','',''],
	            colModel:[
	                {name:'prNo', index:'prNo',key:true, width:80, sortable: false, align:'left', resizable: true, search:true/* , formatter: 'showlink', formatoptions: { baseLinkUrl: '', showAction: "editPR", idName:"prNo"}  */},
	                {name:'projectName', width:80, sortable: false, align:'left', resizable: true, search:false},
	                {name:'projectCode', width:60, sortable: false, align:'left', resizable: true, search:false},
	                {name:'createdByName', width:80, sortable: false, align:'left', resizable: true, search:false},
	                {name:'createdDateStr', width:100, sortable: false, align:'center', resizable: true, search:false},
	                {name:'lastUpdatedByName', width:80, sortable: false, align:'left', resizable: true, search:false},
	                {name:'lastUpdatedDateStr', width:100, sortable: false, align:'center', resizable: true, search:false},
	                {name:'status', index:'status', width:80, sortable: false, align:'center', resizable: true, search:false,editable: false},
	                {name:'select', index:'select',width:80, sortable: false, align:'center', resizable: true, search:false},
	                {name:'authorizedDateStr',  hidden:true},
	                {name:'authorizedByName',  hidden:true},
	                {name:'approvedDateStr',  hidden:true},
	                {name:'approvedByName',  hidden:true},
	                {name:'acknowledgedDateStr',  hidden:true},
	                {name:'acknowledgedByName',  hidden:true},
	                {name:'editable',  hidden:true},
	                {name:'allowedStatusChangesStr',  hidden:true}
				],
				width: $("#prHeader").width()-30,
	            height: "360",
	            rowNum : defaultPageSize,
	            scroll : true,
	            gridview : true,
	            loadtext: 'building list...',
	            jsonReader: {
	                repeatitems: false,
	            },
	            gridComplete: function(){ 
	                var ids = jQuery("#prTable").getDataIDs(); 
	                for(var i=0;i<ids.length;i++){ 
	                    var cl = ids[i]; 
	                    
	                    var rowData = jQuery("#prTable").getRowData(cl);
	                    var edit = "";
	                    if(rowData['editable'] == "true"){
	                    	edit = "<a id='edit' style='cursor:pointer;' href='editPR?prNo="+cl+"'><u>Edit</u></a> | ";
	                    }
	                    be = edit + "<a id='download' style='cursor:pointer;' href='rest/purchaseRequest/"+cl+"/download' download><u>Download</u></a> "; 
	                    jQuery("#prTable").setRowData(ids[i],{select:be}) 
	                } 
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
	            	var priSubgridTableId = subgridId + "_pri";
	            	var priSubgridAuditTableId = subgridId + "_aud";
	            	
	            	var prNo = rowData['prNo'];
	            	var authorizedDateStr = rowData['authorizedDateStr'];
	            	var authorizedByName = rowData['authorizedByName'];
	            	var approvedDateStr = rowData['approvedDateStr'];
	            	var approvedByName = rowData['approvedByName'];
	            	var acknowledgedDateStr = rowData['acknowledgedDateStr'];
	            	var acknowledgedByName = rowData['acknowledgedByName'];
	            	
	            	var emptyDivHtml = "<div>&nbsp</div>";
	            	
    		        var updateStatusHtml = "";
    		        var allowedStatusChanges = rowData['allowedStatusChangesStr'];
    		        allowedStatusChanges = allowedStatusChanges.replace("[","");
    		        allowedStatusChanges = allowedStatusChanges.replace("]","");
    		        if(allowedStatusChanges != ""){
	    		        var arrayofStatus = [];
	    		        arrayofStatus = allowedStatusChanges.split(",");
		            	var options = "<option id='default' value=''> -- Select -- </option>";
		            	for(var i = 0; i < arrayofStatus.length ;i++ ){
		            		var optionsValue = [];
		            		optionValue = arrayofStatus[i].split(":");
		            		options = options + "<option value="+optionValue[0]+">"+optionValue[1]+"</option>";
		            	}
		            	var dropDown = "<select id='statusDropDown_"+prNo+"'>"+ options+"</select>";
		            	updateStatusHtml = emptyDivHtml + "<div><span style='margin: 5px;color: #000080;font-weight: bold;'>Status : </span><span>"+dropDown+ " </span>&nbsp;&nbsp;";
		            	updateStatusHtml += "<span><span style='margin: 5px;color: #000080;font-weight: bold;'>Remark : </span><input type='text' id='remark_"+prNo+"'/>";
		            	updateStatusHtml += "<span><input type='button' style='margin-left: 10px;margin-top:-3px' id='updateStatus_"+prNo+"' class='btn btn-primary' value='Update'/></div>";
	            	}
    		        
	            	auditHtml = emptyDivHtml + "<table id='" + priSubgridAuditTableId + "'></table>";
	            	var audColNames = [];
	            	var audColModel = [];
	            	var audValues = {};
	            	if( authorizedByName.length > 0 && authorizedDateStr.length > 0 ) {
	            		audColNames.push("Authorized By", "Authorized Date");
	            		audColModel.push({name:'authorizedByName', width:80, sortable: false, align:'center', resizable: true});
	            		audColModel.push({name:'authorizedDateStr', width:80, sortable: false, align:'center', resizable: true});
	            		audValues['authorizedByName'] = authorizedByName;
	            		audValues['authorizedDateStr'] = authorizedDateStr;
	            	}
	            	if( approvedByName.length > 0 && approvedDateStr.length > 0 ) {
	            		audColNames.push("Approved By", "Approved Date");
	            		audColModel.push({name:'approvedByName', width:80, sortable: false, align:'center', resizable: true});
	            		audColModel.push({name:'approvedDateStr', width:80, sortable: false, align:'center', resizable: true});
	            		audValues['approvedByName'] = approvedByName;
	            		audValues['approvedDateStr'] = approvedDateStr;
	            	}
	            	if( acknowledgedByName.length > 0 && acknowledgedDateStr.length > 0 ) {
	            		audColNames.push("Acknowledged By", "Acknowledged Date");
	            		audColModel.push({name:'acknowledgedByName', width:80, sortable: false, align:'center', resizable: true});
	            		audColModel.push({name:'acknowledgedDateStr', width:80, sortable: false, align:'center', resizable: true});
	            		audValues['acknowledgedByName'] = acknowledgedByName;
	            		audValues['acknowledgedDateStr'] = acknowledgedDateStr;
	            	}
	            	
	            	var subGridDivHtml = emptyDivHtml + "<div style='margin: 5px;color: #000080;font-weight: bold;'>Items - </div><table id='" + priSubgridTableId + "'></table>"+ emptyDivHtml;
            		
	            	var finalHtml = updateStatusHtml;
	            	if( audColNames.length > 0 ) {
	            		finalHtml += auditHtml;
	            	}
	            	finalHtml += subGridDivHtml
	            	$("#" + subgridId).html( finalHtml );
            		
            		$("#updateStatus_"+prNo).click(function (e) {
            			changeStatus( $("#statusDropDown_"+prNo), prNo, $("#remark_"+prNo) );
        	        });
            		
            		$("#" + priSubgridAuditTableId).jqGrid({
            			datatype: "local",
            		   	colNames: audColNames,
            		   	colModel: audColModel,
        	            scroll : false,
        	            gridview : true,
        	            width: $("#prHeader").width()-250,
	                    height: '100%'
            		});
            		
            		$("#" + priSubgridAuditTableId).jqGrid('addRowData', 1, audValues);
            		
	                $("#" + priSubgridTableId).jqGrid({
	                	datatype:'local',
	                	mtype: 'GET',
	                	colNames:['Description*', 'Total Qty required*', 'Qty In stock', 'Qty to be Purchased*', 'UOM', 'Unit Value', 'Approx. Total Value','Make','Cat No.','Required by date','Preferred Supplier'],
	        		    colModel:[
	        		        {name:'description', width:80, sortable: false, align:'left', resizable: true},
	        		        {name:'totalQuantityRequired', width:40, sortable: false, align:'right', resizable: true},
	        		        {name:'quantityInStock', width:40, sortable: false, align:'right', resizable: true},
	        		        {name:'quantityTobePurchased', width:40, sortable: false, align:'right', resizable: true},
	        		        {name:'uom', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'unitCost', width:30, sortable: false, align:'right', resizable: true},
	        		        {name:'approxTotalCost', width:40, sortable: false, align:'right', resizable: true},
	        		        {name:'make', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'catNo', width:40, sortable: false, align:'left', resizable: true},
	        		        {name:'requiredByDateStr', width:50, sortable: false, align:'center', resizable: true},
	        		        {name:'preferredSupplier', width:40, sortable: false, align:'left', resizable: true}
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
	        String.prototype.replaceAll = function(search, replacement) {
	            var target = this;
	            return target.replace(new RegExp(search, 'g'), replacement);
	        };
	        
	        function changeStatus(target, prNo, remarkTarget) {
	        	var newStatus = target.val();
	        	var data = {
	        			remark : remarkTarget.val()
	        	};
	        	if( newStatus == '' ) {
	        		alert("Please select from the dropdown.");
	        		return;
	        	}
     			if( confirm('Do you want to change the status to ' + newStatus + ' ?') ) {
        			$('#progress').show();
	        		$.ajax({
	        			type:'PUT',
	        			url: 'rest/purchaseRequest/'+prNo+'/updatestatus/'+newStatus,
	        			data: JSON.stringify(data),
	    				dataType: 'json',
	    				contentType :"application/json",
	        			success: function(data, status, jqXHR ){
	        				$('#progress').hide();
        					alert("Updated Successfully!!");
        					$("#prTable").jqGrid().trigger('reloadGrid');
	        			},
	                    error : function(jqXHR, status, error) {
	                    	$('#progress').hide();
	                    	if( jqXHR.status == 401 ) {
	                        	alert('Session Expired');            		
	                    	} else {
	                    		alert(jqXHR.statusText);
	                    	}
	                    }
       				});
     			}
	        };

			loadData();
	        
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
					if( $('#createdBy').val().trim().length > 0 ) {
						postData['createdBy'] = [$('#createdBy').val()];
					}
					if( $('#status').val().trim().length > 0 ) {
						postData['statuses'] = [$('#status').val()];
					}else{
						postData['statuses'] = [];
					}
					postData['fromTimeStr'] = $('#fromDate').val();
					postData['toTimeStr'] = $('#toDate').val();
				    return JSON.stringify(postData);
				}
	    	});
			$("#prTable").jqGrid().trigger('reloadGrid');
		}
		
	</script>