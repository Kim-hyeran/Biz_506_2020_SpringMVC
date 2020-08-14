<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jspf"%>

	<section id="main">
		<article id="button">
			<button>
				<a href="${rootPath}/blog/input">글쓰기</a>
			</button>
		</article>
		<article id="blog_body">
		<!-- BLOGS : Controller로부터 받음 / BLOG : 직접 설정한 변수 -->
			<c:forEach items="${BLOGS}" var="BLOG">
				<section class="blog_title">
					<!-- VO에서 선언한 변수명 입력 -->
					<h3>${BLOG.bl_title} | <span>${BLOG.bl_user}</span>
					</h3>
				</section>
				<section class="blog_text">
					<h5>${BLOG.bl_contents}</h5>
				</section>
			</c:forEach>
		</article>
	</section>
	<%@ include file="/WEB-INF/views/include/include-footer.jspf"%>
</body>
</html>