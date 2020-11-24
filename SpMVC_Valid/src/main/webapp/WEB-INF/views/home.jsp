<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>나의 홈페이지</title>
<style>
	form.main-form {
		width: 80%;
		margin: 10px auto;
	}
	
	form.main-form input {
		width: 80%;
		padding: 8px;
		margin: 4px;
	}
	
	.valid-error {
		display: inline-block;
		margin-left: 10px;
		font-size: 12px;
		color: red;
	}
</style>
</head>
<body>
<h3>나의 홈페이지 방문을 환영합니다</h3>
<form:form class="main-form" modelAttribute="userVO">
	<div>
		<form:input path="name" placeholder="이름" />
		<form:errors path="name" class="valid-error"></form:errors>
	</div>
	<div>
		<form:input path="email" placeholder="이메일" />
		<form:errors path="email" class="valid-error"></form:errors>
	</div>
	<div>
		<form:input path="age" placeholder="나이" />
		<form:errors path="age" class="valid-error"></form:errors>
	</div>
	<div>
		<button>저장</button>
	</div>
</form:form>
</body>
</html>