<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="static/css/app.css"></link>
<link rel="stylesheet" href="static/css/bootstrap.css"></link>
<link rel="stylesheet" href="static/css/bootstrap-theme.css"></link>
<link rel="stylesheet" href="static/css/font-awesome.css"></link>
<link rel="stylesheet" href="static/css/ui.jqgrid.css"></link>
<link rel="stylesheet" href="static/css/jquery-ui-1.9.2.custom.css"></link>
<link rel="stylesheet" href="static/css/jash.css"></link>
<script src="static/js/jquery/jquery-1.11.2.min.js"></script>
<script src="static/js/bootstrap/bootstrap.min.js"></script>
<script src="static/js/jquery/grid.locale-en.js"></script>
<script src="static/js/jquery/jquery.jqGrid.min.js"></script>
<script>
var defaultPageSize = 20;
$(function () {
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader('${_csrf.headerName}', '${_csrf.token}');
    });
});
$body = $("body");
$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
     ajaxStop: function() { $body.removeClass("loading"); }    
});
</script>
