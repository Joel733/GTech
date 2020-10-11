<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<script type="text/javascript" src="/Psikometri/resources/js/jquery-3.4.1.min.js"></script>
<script>
function loginUser() {
	if($("#userName").val()=="" && $("#userPassword").val()==""){
		var x = document.getElementById("error1");
		var y = document.getElementById("error2");
		x.style.display="block";
		y.style.display="none";
	}else{
		console.log("masuk script");
		$.ajax({
		    type: "GET",
		    url: "/Psikometri/ajaxLogin/?userName="+$("#userName").val()+"&userPassword="+$("#userPassword").val(), 
		    success: function(result)
		    {
		    	console.log(result);
		    	if(result==1){
		    		
		    		$("#loginForm").submit();
		    	}else{
		    		var x = document.getElementById("error1");
		    		var y = document.getElementById("error2");
		    		x.style.display="none";
		    		y.style.display="block";
		    	}
		    	
		    },
			error: function(result){}
		  });
	}
	
}
</script>
<head>
<meta charset="ISO-8859-1">
<title>Psikometri : Login</title>
</head>
<body>
	<form:form id="loginForm" method="POST" action="${action}">
		<fieldset style="margin: auto; text-align: center; width: 400px; border: 0px;">Psikometri</fieldset>
		<br>
		<fieldset style="margin: auto; text-align: center; width: 400px;">
			<table>
				<tr>
					<td>Username</td>
					<td>:</td>
					<td> <input type="text" name="userName" id="userName"> </td>
				</tr>
				<tr>
					<td>Password</td>
					<td>:</td>
					<td> <input type="password" name="userPassword" id="userPassword"> </td>
				</tr>
				<tr>
					<td> <a href="">Forgot Password</a> </td>
					<td> </td>
					<td> <input type="button" name="loginBtn" id="loginBtn" value="Login" onclick="loginUser()"> </td>
				</tr>
			</table>
			<div id="error1" style="display: none; text-align: left;"><label style="color: red;">*Username and password must be filled</label> </div>
			<div id="error2" style="display: none; text-align: left;"><label style="color: red;">*Username or password is incorrect</label> </div>
		</fieldset>
		
	</form:form>
</body>
</html>







