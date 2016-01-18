<%@page import="org.iry.utils.SpringContextUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar" aria-expanded="true" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="home"><%=SpringContextUtil.getFullName() %></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse" aria-expanded="true" style="">
            <ul class="nav navbar-nav">
                <li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Purchase Requisition<span class="caret"></span></a>
	            	<ul class="dropdown-menu">
	                	<li><a href="newPR">Create</a></li>
	                	<li><a href="myPR">My PR</a></li>
	                	<li><a href="searchPR">Search</a></li>
	              	</ul>
                </li>
                <li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Purchase Orders<span class="caret"></span></a>
	            	<ul class="dropdown-menu">
	                	<li><a href="#newPo">Create</a></li>
	                	<li><a href="#">My PO</a></li>
	                	<li><a href="#">PO History</a></li>
	              	</ul>
                </li>
                <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Admin <span class="caret"></span></a>
	              <ul class="dropdown-menu">
	                <li><a href="newUser">Create User</a></li>
	                <li><a href="users">View Users</a></li>
	              </ul>
	          	</li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('body').scrollspy({ target: '.navbar-example' })
        $('a[href*=#]:not([href=#])').click(function () {
            if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
                var target = $(this.hash);
                target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
                if (target.length) {
                    $('html,body').animate({
                        scrollTop: target.offset().top
                    }, 1000);
                    return false;
                }
            }
        });
    });
    
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();
</script>