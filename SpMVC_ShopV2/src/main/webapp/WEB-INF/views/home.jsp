<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://kit.fontawesome.com/c2f525edad.js" crossorigin="anonymous"></script>
<title>빛나리 쇼핑몰 V1</title>
<style>
* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

header {
	background-color: #3F97AF;
	color: white;
	text-align: center;
	padding: 1.5rem;
}
</style>
<link rel="stylesheet" type="text/css" href="${rootPath}/static/css/input.css?ver=2020-08-24" />
</head>
<body>
	<header>
		<h3>빛나리 쇼핑몰 V1</h3>
		<p>빛나리 쇼핑몰 2020 V1</p>
	</header>
	<%@ include file="/WEB-INF/views/include/include-nav.jspf"%>
	<section>
		<c:choose>
			<c:when test="${BODY == 'PRO_WRITE'}">
				<%@ include file="/WEB-INF/views/component/product/product_writeV2.jspf"%>
			</c:when>
			<c:when test="${BODY == 'PRO_HOME'}">
				<%@ include file="/WEB-INF/views/component/product/product_list.jspf"%>
			</c:when>
			<c:when test="${BODY == 'PRO_DETAIL'}">
				<%@ include file="/WEB-INF/views/component/product/product_detail.jspf"%>
			</c:when>
			<c:when test="${BODY == 'DEPT_LIST'}">
				<%@ include file="/WEB-INF/views/component/dept/dept_list.jspf"%>
			</c:when>
			<c:when test="${BODY == 'DEPT_WRITE'}">
				<%@ include file="/WEB-INF/views/component/dept/dept_write.jspf"%>
			</c:when>
			<c:when test="${BODY == 'DEPT_DETAIL'}">
				<%@ include file="/WEB-INF/views/component/dept/dept_detail.jspf"%>
			</c:when>
			<c:otherwise>
				<%@ include file="/WEB-INF/views/include/include-main.jspf"%>
			</c:otherwise>
		</c:choose>
	</section>
</body>