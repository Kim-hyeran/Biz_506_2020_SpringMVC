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
				<a href="${rootPath}/blog/list">블로그</a>
			</button>
		</article>
		<article id="blog_body">
			<section class="blog_title">
				<h3>나희덕, 푸른 밤</h3>
			</section>
			<section class="blog_text">
				<h5>너에게로 가지 않으려고 미친 듯 걸었던</h5>
				<h5>그 무수한 길도</h5>
				<h5>실은 네게로 향한 것이었다.</h5>
				<br>
				<h5>까마득한 밤길을 혼자 걸어갈 때에도</h5>
				<h5>내 응시에 날아간 별은</h5>
				<h5>네 머리 위에서 반짝였을 것이고</h5>
				<h5>내 한숨과 입김에 꽃들은</h5>
				<h5>네게로 몸을 기울여 흔들렸을 것이다.</h5>
				<br>
				<h5>사랑에서 치욕으로,</h5>
				<h5>다시 치욕에서 사랑으로,</h5>
				<h5>하루에도 몇번씩 네게로 드리웠던 두레박</h5>
				<br>
				<h5>그러나 매양 퍼올린 것은</h5>
				<h5>수만 갈래의 길이었을 따름이다</h5>
				<h5>은하수의 한 별이 또 하나의 별을 찾아가는</h5>
				<h5>그 수만의 길을 나는 걷고 있는 것이다.</h5>
				<br>
				<h5>나의 생애는</h5>
				<h5>모든 지름길을 돌아서</h5>
				<h5>네게로 난 단 하나의 에움길이었다.</h5>
			</section>
		</article>
	</section>
	<%@ include file="/WEB-INF/views/include/include-footer.jspf"%>
</body>
</html>