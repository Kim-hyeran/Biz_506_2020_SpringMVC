<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 웹서비스</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-nav.jsp" %>
	<h4>나는 home</h4>
	<P>서버의 현재 시각 : ${serverTime}.</P>
</body>
</html>