<%@page import="org.iry.utils.SpringContextUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
	<title>Search Purchase Requisitions</title>
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
<body>
    <jsp:include page="../menubar.jsp"></jsp:include>
	<div class="form-container">
		<input type="hidden" id="createdBy" value=""/>
		<jsp:include page="searchform.jsp"></jsp:include>
		<jsp:include page="prgrid.jsp"></jsp:include>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>