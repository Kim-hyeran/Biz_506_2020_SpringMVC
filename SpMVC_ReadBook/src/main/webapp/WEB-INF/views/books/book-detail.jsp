<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<style>
	table#book-detail {
		width: 70%;
		margin: 20px auto;
	}
	
	table#book-detail .title td {
		padding: 0.5rem 1rem;
		background-color: #c8de4b;
		text-align: center;
	}
	
	table#book-detail .info td:first-child {
		display: flex;
		justify-content: center;
		align-item: center;
		padding: 10px;
	}
	
	table#book-detail table th {
		color: #a29696;
		padding: 8px 16px;
		text-align: right;
	}
	
	table#book-detail .dummy {
		padding: 30px;
	}
	
	table#book-detail .desc-title th {
		text-align: left;
		padding-left: 30px;
		color: #544b4b;
		background-color: #c8de4b;
		padding: 10px;
	}
	
	table#book-detail .desc td {
		padding: 15px;
	}
	
	table#book-detail .link th {
		color: #544b4b;
		padding: 10px;
		text-align: center;
	}
	
	table#book-detail .link th a {
		color: #544b4b;
		text-decoration: none;
	}
	
	table#book-detail .link th a:hover {
		color: #c8de4b;
	}
	
</style>
</head>
<body>
<table id="book-detail">
	<tr class="title"><td colspan="2"><h3>${BOOKVO.title}</h3></td></tr>
	<tr class="info">
		<td><img src="${BOOKVO.image}"></td>
		<td>
			<table>
				<tr class="author"><th>저자</th><td>${BOOKVO.author}</td><td>${BOOKVO.pubdate}</td></tr>
				<tr class="price"><th>가격</th><td>${BOOKVO.price}</td><td>${BOOKVO.discount}</td></tr>
				<tr class="pub"><th>출판사</th><td>${BOOKVO.publisher}</td><td>${BOOKVO.isbn}</td></tr>
				<tr class="link"><th colspan="3"><a href="${BOOKVO.link}" target=_new>네이버 도서정보</a></th></tr>
			</table>
		</td>
	</tr>
	<tr class="desc-title"><th colspan="2">책 소개</th></tr>
	<tr class="desc"><td colspan="2">${BOOKVO.description}</td></tr>
	
	<tr class="buy">
		<th colspan="2">
			<p>구입일 : ${BOOKVO.buydate}
			 | 구입가 : ${BOOKVO.buyprice}
			 | 구입처 : ${BOOKVO.buystore}</p>
		</th>
	</tr>
</table>