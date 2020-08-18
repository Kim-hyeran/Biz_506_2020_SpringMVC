<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf"%>
<style>
menu {
	width: 80%;
	padding: 10px 0;
	text-align: right;
}

menu a {
	display: inline-block;
	padding: 8px 16px;
	margin: 5px;
	border-radius: 5px;
}

menu a:nth-child(1) {
	background-color: honeydew;
}

menu a:nth-child(2) {
	background-color: #FFAAAA;
}

menu a:nth-child(3) {
	background-color: #ddd;
}

menu a:nth-child(4) {
	background-color: rgb(185, 238, 255);'
}

menu a:hover {
	background-color: teal;
	color: white;
}
</style>
<script>
$(function() {
	/*
	 선택자
	  - $(this) : 이벤트 핸들러에서 사용하는 시스템 객체
	  - $("tag") : tag 선택자
	  - $("#id") : id 선택자
	  - $(".class") : class 선택자, 같은 class가 지정된 모든 tag
	*/
	$("#delete").click(function() {
		if(confirm("삭제하시겠어요?")) {
			document.location.href="${rootPath}/blog/delete?seq=${BLOG.bl_seq}"
		}
	})
	
	$("#update").click(function() {
		document.location.href="${rootPath}/blog/update?seq=${BLOG.bl_seq}"
	})
	
	$("#dummy").click(function() {
		alert("실제 존재하지 않는 id에 event를 설정하여도 오류가 발생하진 않는다")
	})
})
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jspf"%>

	<section id="main">
		<article id="blog_body">
			<section class="blog_title">
				<h3>${BLOG.bl_title}</h3>
				<h5>작성자 : ${BLOG.bl_user}</h5>
				<h5>작성일자 : ${BLOG.bl_date}</h5>
				<h5>작성시간 : ${BLOG.bl_time}</h5>
			</section>
			<section class="blog_text">
				<p>${BLOG.bl_contents}</p>
			</section>
		</article>
		<menu>
			<a href="${rootPath}/">처음으로</a>
			<a href="${rootPath}/blog/list">리스트</a>
			<a href="javascript:void(0)" id="update">수정</a>
			<a href="javascript:void(0)" id="delete">삭제</a>
		</menu>
	</section>
	<%@ include file="/WEB-INF/views/include/include-footer.jspf"%>
</body>
</html>