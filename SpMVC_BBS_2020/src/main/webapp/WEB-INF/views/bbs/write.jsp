<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	$(function() {
		var toolbar = [
			['style',['bold','italic','underline'] ],
			['fontsize',['fontsize']],
			['font Style',['fontname']],
			['color',['color']],
			['para',['ul','ol','paragraph']],
			['height',['height']],
			['table',['table']],
			['insert',['link','hr','picture']],
			['view',['fullscreen','codeview']]
			
		]
		$("#b_content").summernote({lang:"ko-KR", width:"80%", height:"200px", margin: "auto", toolbar:toolbar})
	})
</script>
<style>
	form#write-form {
		width: 100%;
		margin: 10px auto;
	}
	
	form#write-form fieldset{
		border: 2px solid white;
		border-radius: 5px;
	}
	
	form#write-form legend {
		margin: 5px;
		padding: 5px;
	}
	
	form#write-form label {
		display: inline-block;
		width: 18%;
		margin: 4px;
		padding: 4px;
		text-align: right;
	}
	
	form#write-form input {
		display: inline-block;
		width: 70%;
		margin: 4px;
		padding: 4px;
		outline: none;
		border: none;
		border-bottom: 1px solid #333;
	}
	
	form#write-form textarea {
		width: 70%;
	}
	
	form#write-form div.button-box {
		text-align: center;
	}
	
	form#write-form .button-box button {
		background-color: white;
		padding: 10px;
		outline: none;
		margin: 5px;
		border: none;
		border-radius: 5px;
	}
	
	form#write-form .button-box button#save {
		font-weight: bold;
		
	}
	
	form#write-form .button-box button:hover {
		background-color: lavender;
	}
</style>
<form id="write-form" method="POST">
	<fieldset>
		<legend>글쓰기</legend>
		<div>
			<label>작성일자</label>
			<input name="b_date">
		</div>
		<div>
			<label>작성시각</label>
			<input name="b_time">
		</div>
		<div>
			<label>작성자</label>
			<input name="b_writer">
		</div>
		<div>
			<label>제목</label>
			<input name="b_subject">
		</div>
		<div>
			<label></label>
			<textarea id="b_content" rows="5" cols="20" name="b_content"></textarea>
		</div>
		<div class="button-box">
			<button type="button" id="list">목록</button>
			<button type="submit" id="save">저장</button>
		</div>
	</fieldset>
</form>