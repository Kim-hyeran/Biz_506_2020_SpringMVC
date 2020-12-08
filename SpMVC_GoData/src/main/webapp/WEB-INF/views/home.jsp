<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	*{
		box-sizing: border-box;
		margin: 0;
		padding: 0;
	}

	header {
		padding: 2rem;
		background-color: navy;
		color: white;
		text-align: center;
		margin-bottom: 0;
	}
	
	nav.main-nav ul {
		display: flex;
		list-style: none;
		background-color: navy;
		color: white;
	}
	
	nav.main-nav li {
		cursor: pointer;
		padding: 0.5rem;
		margin: 0 20px;
	}
	
	nav.main-nav input {
		outline: none;
		border: none;
		padding: 5px;
	}
	
	nav.main-nav li:nth-child(2),
	nav.main-nav li:last-child {
		margin-left: auto;
	}
	
	nav.main-nav li:hover {
		text-decoration: line-through;
	}
</style>
<script>
	document.addEventListner("DOMContentLoaded", function() {
		const menu_station = document.querySelector("nav.main-nav .get-station")
		menu_station.onClick = function() {
			document.location.href="${rootPath}/bis/station"
		}
	})
</script>
</head>
<body>
<header>
	<h2>공공 DB 활용하기</h2>
</header>
<nav class="main-nav">
	<ul>
		<li>Home</li>
		<li><input/></li>
		<li>
			<form>
				<input name="hosp" type="search" placeholder="동물병원 이름을 입력하세요" />
			</form>
		</li>
		<li class="get-station">노선정보</li>
	</ul>
</nav>
<h2>나의 홈페이지</h2>
<style>
	table.hosp-list {
		border-collapse: collapse;
		border-spacing: 0;
		width: 90%;
		border: 1px solid #ccc;
		margin: 20px auto;
	}
	
	table.hosp-list th {
		text-align: left
	}
	
	table.hosp-list tr {
		border: 1px solid #ccc;
		cursor: pointer;
	}
	
	table.hosp-list tr#dong-list:nth-child(even) {
		background-color: #eee;
	}
	
	table.hosp-list tr#dong-list:nth-child(odd) {
		background-color: white;
	}
	
	table.hosp-list tr#dong-list:hover {
		background-color: lavender;
	}
	
	table.hosp-list td, table.hosp-list th {
		padding: 8px;
		vertical-align: top;
		white-space: nowrap; /* table의 text를 줄바꿈하지 말라 */
	}
</style>
<section>
	<table class="hosp-list">
		<tr>
			<th>병원이름</th>
			<th>도로명주소</th>
			<th>지번주소</th>
			<th>전화번호</th>
			<th>위도</th>
			<th>경도</th>
			<th>데이터기준일</th>
		</tr>
		<c:choose>
			<c:when test="${empty H_LIST}">
				<tr><td colspan="7">데이터가 존재하지 않습니다.</td></tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${H_LIST}" var="hs">
					<tr id="dong-list">
						<td>${hs.apiDongName}</td>
						<td>${hs.apiNewAddress}</td>
						<td>${hs.apiOldAddress}</td>
						<td>${hs.apiTel}</td>
						<td>${hs.apiLat}</td>
						<td>${hs.apiLng}</td>
						<td>${hs.apiRegDate}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</section>
</body>
</html>