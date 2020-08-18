<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/include-head.jspf"%>
<link rel="stylesheet" type="text/css" href="${rootPath}/static/css/input.css?ver=2020-08-13" />
<script>
$(function() {
	/*
	 저장 버튼을 type="button"으로 설정하여 submit 기능을 무력화하고 jquery에서 버튼이 클릭되었을 때
	 실행할 event handler를 만들어 input box에 값이 존재하는지 검사하여 없으면 alert를 보여주고 입력받는 명령 수행
	*/
	$("#save").click(function() {
		//input tag에 입력된 값 추출
		var user=$("#bl_user").val()
		var title=$("#bl_title").val()
		var contents=$("#bl_contents").val()
		
		if(user=="") {
			alert("이름을 입력해주세요!")
			$("#bl_user").focus()
			return false;
		}
		
		if(title=="") {
			alert("제목을 입력해주세요!")
			$("#bl_title").focus()
			return false;
		}
		
		if(contents=="") {
			alert("내용을 입력해주세요!")
			$("#bl_contents").focus()
			return false;
		}
		
		//form(input)에 입력딘 데이터를 서버로 전송
		$("form").submit()
	})
	
	$("#goHome").click(function() {
		document.location.href="${rootPath}/blog/list"
	})
})
</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jspf"%>

	<section id="main">
		<form method="POST">
			<input name="bl_seq" value="${BLOG.bl_seq}" type="hidden" />
			<input name="bl_seq" value="${BLOG.bl_date}" type="hidden" />
			<input name="bl_seq" value="${BLOG.bl_time}" type="hidden" />
			<div>
				<input name="bl_user" id="bl_user" value="${BLOG.bl_user}" placeholder="사용자를 입력하세요" />
			</div>
			<div>
				<input name="bl_title" id="bl_title" value="${BLOG.bl_title}" placeholder="제목을 입력하세요" />
			</div>
			<div>
				<input name="bl_contents" id="bl_contents" value="${BLOG.bl_contents}" placeholder="내용을 입력하세요" />
			</div>
			<div>
				<button type="button" id="goHome">처음으로</button>
				<button type="button" id="save">저장</button>
			</div>
		</form>
	</section>
	<%@ include file="/WEB-INF/views/include/include-footer.jspf"%>
</body>
</html>