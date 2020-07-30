<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 홈페이지</title>
</head>
<body>
	<% 
	//jsp 코드가 들어가는 영역
	//scriptlet, java 문법의 스크립트를 html 파일에 끼워넣는 곳
	String name="홍길동";
	%>
	<%@ include file="/WEB-INF/views/include/include-header.jspf" %>
	<%@ include file="/WEB-INF/views/include/include-nav.jspf" %>
	
	<%
	/*
	Semantics Tag
	- HTML5에서 div tag를 사용한 layout을 대체하는 새로운 규격의 tag 틀
	- 보통 <div id="header"></div>와 같은 element로 layout 생성
	- tag들이 여러 번 반복적으로 포함되는 복잡한 layout 구조에서는 가독성을 심각하게 해침
		-> 유지보수, 기능 추가 및 변경 등의 업무 수행에 애로사항 발생
	- 불편한 상황을 대체하고자 HTML5에 새롭게 도입된 tag
	- 이 tag들은 단어가 내포하는 의미를 잘 이해하고 사용하면 layout의 구조를 파악하는 데 도움이 된다.
	- 기술적으로 이 tag들은 성질(용도)이 모두 div와 유사하다.
	*/
	%>
	<section>
		<h3>반갑습니다</h3>
		<p>나는 ${name}입니다.</p>
	</section>
	<section>
		<article>
			<section>
				<article>
				</article>
			</section>
		</article>
	</section>
	
	<div id="section">
		<div id="article">
			<div id="sub_section">
				<div id="article_01">
				</div>
			</div>
		</div>
	</div>
</body>
</html>