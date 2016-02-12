<%@page import="org.iry.utils.SpringContextUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<body>
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
            	<li class="dropdown">
            		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=SpringContextUtil.getUser().getFirstName() %> <span class="caret"></span></a>
            		<ul class="dropdown-menu">
            			<li><a  data-toggle="modal" data-target="#myModal">Change Password</a></li>
            			<li><a href="logout">Logout</a></li>
            		</ul>
            	</li>
                <!-- <li><a href="logout">Logout</a></li> -->
            </ul>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">Change Password</h4>
        </div>
        <div class="modal-body">
        	<form id="passwordForm">
        		<input type="password" class="input-lg form-control" name="password1" id="password1" placeholder="New Password" autocomplete="off">
				<div class="row">
					<div class="col-sm-6">
					<span id="8char" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> 8 Characters Long<br>
					<span id="ucase" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> One Uppercase Letter
					</div>
					<div class="col-sm-6">
					<span id="lcase" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> One Lowercase Letter<br>
					<span id="num" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> One Number
					</div>
				</div>
				<input type="password" class="input-lg form-control" name="password2" id="password2" placeholder="Repeat Password" autocomplete="off">
				<div class="row">
					<div class="col-sm-12">
					<span id="pwmatch" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> Passwords Match
					</div>
				</div>
				<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal" id="close">Close</button>
          		<button type="button" class="btn btn-primary" id="saveChanges">Save changes</button>
        		</div>
        	</form>
        </div>
      </div>
    </div>
</div>
</body>
<script>
var id = <%=SpringContextUtil.getUser().getId() %>; 
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
<script>
$("input[type=password]").keyup(function(){
    var ucase = new RegExp("[A-Z]+");
	var lcase = new RegExp("[a-z]+");
	var num = new RegExp("[0-9]+");
	
	if($("#password1").val().length >= 8){
		$("#8char").removeClass("glyphicon-remove");
		$("#8char").addClass("glyphicon-ok");
		$("#8char").css("color","#00A41E");
	}else{
		$("#8char").removeClass("glyphicon-ok");
		$("#8char").addClass("glyphicon-remove");
		$("#8char").css("color","#FF0004");
	}
	
	if(ucase.test($("#password1").val())){
		$("#ucase").removeClass("glyphicon-remove");
		$("#ucase").addClass("glyphicon-ok");
		$("#ucase").css("color","#00A41E");
	}else{
		$("#ucase").removeClass("glyphicon-ok");
		$("#ucase").addClass("glyphicon-remove");
		$("#ucase").css("color","#FF0004");
	}
	
	if(lcase.test($("#password1").val())){
		$("#lcase").removeClass("glyphicon-remove");
		$("#lcase").addClass("glyphicon-ok");
		$("#lcase").css("color","#00A41E");
	}else{
		$("#lcase").removeClass("glyphicon-ok");
		$("#lcase").addClass("glyphicon-remove");
		$("#lcase").css("color","#FF0004");
	}
	
	if(num.test($("#password1").val())){
		$("#num").removeClass("glyphicon-remove");
		$("#num").addClass("glyphicon-ok");
		$("#num").css("color","#00A41E");
	}else{
		$("#num").removeClass("glyphicon-ok");
		$("#num").addClass("glyphicon-remove");
		$("#num").css("color","#FF0004");
	}
	
	if($("#password1").val() == $("#password2").val()){
		$("#pwmatch").removeClass("glyphicon-remove");
		$("#pwmatch").addClass("glyphicon-ok");
		$("#pwmatch").css("color","#00A41E");
	}else{
		$("#pwmatch").removeClass("glyphicon-ok");
		$("#pwmatch").addClass("glyphicon-remove");
		$("#pwmatch").css("color","#FF0004");
	}
});
$("#saveChanges").click(function(){
	if($("#pwmatch").hasClass('glyphicon-ok') && $("#8char").hasClass('glyphicon-ok') && 
	   $("#ucase").hasClass('glyphicon-ok') && $("#ucase").hasClass('glyphicon-ok') &&
	   $("#lcase").hasClass('glyphicon-ok')){
		var data = {
			'id':	id,
			'newPassword': $('#password1').val()
		};
		$.ajax({
			type:'POST',
			url: 'rest/user/changepassword',
			data: JSON.stringify(data),
			contentType :"application/json; charset=utf-8",
			success: function(data, status, jqXHR ){
				alert("Password changed successfully!!");
				$('#close').click();
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
});

</script>