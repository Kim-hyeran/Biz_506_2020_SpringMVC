<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${rootPath}/static/css/book-write.css?ver=2020-10-07-001">
<script>
	// Controller에서 보내준 _csrf.headerName과 _csrf.token 값을 js 파일로 전달하기 위해서 스크립트 변수를 선언하고,
	// book-write.js에서 ajax POST 전송 전에 값을 Header에 실어서 보낸다.
	// js 파일에서는 csrf_header 변수와 csrf_token 변수를 백틱으로 묶어서 사용한다.
	var csrf_header='${_csrf.headerName}'
	var csrf_token='${_csrf.token}'
</script>
<script src="${rootPath}/static/js/book-write.js?ver=2020-10-07-002"></script>
</head>

<%/*
 @SessionAttributes(), @ModelAttribute(), String form taglib를 연동한 write(입력 form) 구현
 Controller class에 @SessionAttributes("bookVO")를 설정하고, 각 method에 매개변수로 @ModelAttribute("bookVO") BookVO bookVO를 선언
 Conrtoller class의 Memeber 영역에 @ModelAttribute("bookVO") public BookVO newBookVO() method 선언
 Spring form taglib를 이용한 write form에 <form:form modelAttribute="bookVO">를 선언하여 프로젝트 구현
 id, seq 등 실제 사용자에게 입력받거나, 보여줄 필요가 없는 VO의 변수들을 <input type="hidden">으로 설정하고 Controller로 전송하던 HTML5 표준 방식을 사용하지 않아도
 	VO에 설정된 변수들을 Controller와 JSP가 서로 공유하여 사용 가능
 @SessionAttributes()에 담긴 VO 객체는 서버의 메모리에 보관되며 HTTP 프로토콜의 비연결지향(상태가 없는 통신) 통신상태에서도 데이터를 서로 자유롭게 공유하여 구현할 수 있다.
 그럼에도 경우에 따라 입력 form을 사용자에게 보여주었을 때 최종 마지막에 입력했던 데이터들이 form에 나타나서 불편한 경우가 발생
 이러한 현상을 방지하기 위해 form에 입력되었던 데이터 사용이 끝나면(insert나 update 완료 후) SessionStatus.setComplete() method를 호출하여 데이터를 clear해주어야 한다. 
*/%>

<body>

	<form:form id="books" modelAttribute="bookVO">
		<fieldset>
			<legend>도서정보입력</legend>
			<div>
				<label for="title">도서명</label> <form:input path="title" placeholder="도서명" />
				<button id="naver-search" type="button">검색</button>
			</div>

			<div>
				<label for="link">상세링크</label> <form:input path="link" placeholder="상세링크" />
			</div>

			<div>
				<label for="image">이미지</label> <form:input path="image" placeholder="이미지" />
			</div>

			<div>
				<label for="author">저자</label> <form:input path="author" placeholder="저자" />
			</div>

			<div>
				<label for="price">정가</label> <form:input path="price" placeholder="가격" />
			</div>

			<div>
				<label for="discount">할인가</label> <form:input path="discount" placeholder="할인가" />
			</div>

			<div>
				<label for="publisher">출판사</label> <form:input path="publisher" placeholder="출판사" />
			</div>

			<div>
				<label for="isbn">ISBN</label> <form:input path="isbn" placeholder="ISBN" />
			</div>

			<div>
				<label for="description">세부설명</label> <form:input path="description" placeholder="상세내용" />
			</div>

			<div>
				<label for="pubdate">출간일자</label> <form:input path="pubdate" placeholder="출간일자" />
			</div>

			<div>
				<label for="buydate">구입일자</label> <form:input path="buydate" placeholder="구입일자" />
			</div>

			<div>
				<label for="buyprice">구입가격</label> <form:input path="buyprice" placeholder="구입가격" />
			</div>

			<div>
				<label for="buystore">구입처</label> <form:input path="buystore" placeholder="구입처" />
			</div>

			<div class="btn-box">
				<button id="btn-save" type="button">저장</button>
			</div>
		</fieldset>
	</form:form>

	<section id="book-modal">
		<article id="modal-body">
			<div id="modal-header">
				<span>&times;</span>
			</div>
			<div id="search-result"></div>
		</article>
	</section>