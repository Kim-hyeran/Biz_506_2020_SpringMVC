<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>나의 홈페이지</title>
<style>
	* {
	    box-sizing: border-box;
	    margin: 0;
	    padding: 0;
	    font-family: 'Times New Roman', Times, serif;
	    color: #555;
	    background: linear-gradient(#EDEED2, #629E5E) fixed;
	}
	
	section#login_body {
	    margin: 0 auto;
	    padding: 0;
	    width: 30%;
	    padding: 30px;
	    text-align: center;
	}
	
	form input,
	form button {
	    width: 80%;
	    margin: 10px auto;
	    padding: 5px;
	    border: none;
	    outline: none;
	}
	
	form input {
	    display: block;
	    border-bottom: 1px solid #333;
	}
	
	form button {
	    background-color: #EDEED2;
	    padding: 8px 0;
	    margin: 5px;
	    border-radius: 5px;
	    transition: 0.3s linear;
	}
	
	form button:hover {
	    color: #629E5E;
	}
</style>
    
<section id="login_body">
	<form method="POST" action="${rootPath}/login">
		<h2>Login</h2>
		
		<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
			<h4 id="login-fail">${SPRING_SECURITY_LAST_EXCEPTION.message}</h4>
			<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session"/>
		</c:if>
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<input name="username" placeholder="User ID" /> 
		<input name="password" type="password" placeholder="Password" />
		<button>Login</button>
		<button type="button">Create an Account</button>
	</form>
</section>
</html>