<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name='viewport' content='width=device-width, initial-scale=1'>
<title>나의 홈페이지</title>

<style>
	* {
		box-sizing: border-box;
		padding: 0;
		margin: 0;
	}
	
	html, body {
		width: 100%;
		height: 100%;
	}
	
	body {
		overflow: auto;
	}
	
	form#books {
		width: 50%;
		margin: 10px auto;
	}
	
	form#books input {
		/*
		 만약 input box와 button 등 다른 tag를 한 줄에 나란히 놓으면서, input box의 width를 임의로 설정하고 싶으면 display를 inline-block으로 설정
		 block으로 설정할 경우 width는 임의로 설정할 수 있지만 다른 tag를 오른쪽에 배치할 수 없게 된다.
		 기본 값인 inline이면 width를 설정할 수 없다.
		 */
		display: inline-block;
		width: 90%;
		border: 1px solid #ddd;
		margin: 10px;
		padding: 8px;
		border-radius: 5px;
	}
	
	form#books fieldset {
		border: 2px solid rgb(255, 193, 181);
		border-radius: 10px;
	}
	
	form#books #title {
		width: 83%;
	}
	
	form#books div.btn-box {
		width: 92%;
		text-align: right;
	}
	
	form#books button{
		border: none;
		outline: none;
		padding: 0.5rem 12px;
		border-radius: 5px;
		background-color: rgb(255, 193, 181);
	}
	
	form#books button:hover {
		background-color: lavender;
	}
	
	/* ------------------- Modal 설정 구간 ------------------- */
	section#book-modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		/*
		 !impartant
		  - 색상을 지정했을 때 다른 css와 충돌하여 색상 지정이 원하는 대로 되지 않는 경우 발생
		  - 이 때 !important를 사용하면 앞에서 지정한 색상을 무시하고 지금 지정한 값으로 강제 지정할 수 있다.
		 */
		background-color: rgba(255, 193, 181, 0.3) !important;
	}
	
	article#modal-body {
		position: absolute;
		top: 45%;
		left: 60%;
		width: 65%;
		height: 50%;
		transform: translate(-50%, -50%);
		display: flex;
		flex-flow: column nowrap;
	}
	
	div#modal-header {
		flex: 1;
		width: 60%;
		text-align: right;
	}
	
	div#modal-header span{
		font-size: 30px;
		font-weight: 500;
		color: #333;
		cursor: pointer;
		margin: 15px;
	}
	
	div#modal-header span:hover{
		color: red;
	}
	
	div#search-result {
		flex: 7;
		width: 60%;
		padding: 30px;
		overflow: auto;
		background-color: white;
	}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<script>
	$(function() {
		$("#naver-search").click(function() {
			let title=$("#title").val()
			if(title==="") {
				alert("도서명을 입력해주세요")
				$("#title").focus()
				return false
			}
			
			// ajax를 사용하여 서버에 도서 검색 요청
			$.ajax({
				// ajax로 서버의 /naver/search URL에 POST로 요청하면서 search_text 내 title 변수에 담긴 값을 전달
				url : "${rootPath}/naver/search",
				method : "POST",
				data : {"search_text" : title},
				// 서버가 데이터 조회를 수행한 후, view(HTML)코드를 return하면 그 결과를 #search-result div box에 채워 출력 요청
				success : function(result) {
					$("#search-result").html(result)
				},
				error : function(error) {
					alert("서버 통신 오류!")
				}
			})
			
			$("#book-modal").css("display", "block")
		})
		
		// X 표시를 클릭했을 때 modal창 닫기
		$("div#modal-header span").click(function() {
			$("#book-modal").css("display", "none")	
		})
		
		/*
		 동적으로 구현된 HTML에 event 핸들링 구현하기
		  - 현재 document(HTML 문서)가 생성되는 동안 없던 tag를 JS(JQ) 코드에서 동적으로 생성했을 경우 화면에 그려지는 것은 아무런 문제가 없다.
		  - JS에서 event 핸들러를 설정할 때 아직 화면에 없는 tag에 연결하면 무시해버리고 없던 일로 만들어버린다.
		  - 사후에(HTML 문서가 완성된 후) JS 코드로 생성할 tag(id, class)에 event를 설정하려면 자체적으로 설정하지 않고, 가장 상위 객체인 document에 on() 함수를 사용하여 event를 설정한다.
		 $(document).on("event", "대상", function(){})
		 
		 주의사항
		 $(selector).click(function(){})
		  - 만약 기존 selector에 click event가 설정되어 있으면 기존의 event를 덮어쓴다.
		 $(document).on("event", "selector")
		  - 만약 기존 selector에 대한 click event가 설정되어 있더라도 중복 정의된다.
		  - 동적으로 여는 곳에서는 $(document).on()을 사용하여 event 핸들러를 설정하고, 동적으로 열리는 곳에서는 절대 $(document).on을 사용하여서는 안 된다.
		  - 동적으로 열리는 곳에서는 $(selector).click()과 같은 event 핸들러를 설정한다.
		 */
		$(document).on("click", "div.book-select", function() {
			let isbn = $(this).data("isbn")
			
			// 13자리 isbn 추출하기
			// 코드의 오른쪽에서부터 13자리 잘라내기
			let length = isbn.length
			isbn = isbn.substring(length - 13)
			
			// ajax 2.x 이상에서 권장하는 코드
			$.ajax({
				url : "${rootPath}/api/isbn",
				method : "POST",
				data : {"search_text" : isbn}
			})
			.done(function(bookVO) {
				// alert(JSON.stringify(bookVO))
				$("#seq").val(bookVO.seq);
				$("#title").val(bookVO.title);
				$("#link").val(bookVO.link);
				$("#image").val(bookVO.image);
				$("#author").val(bookVO.author);
				$("#price").val(bookVO.price);
				$("#discount").val(bookVO.discount);
				$("#publisher").val(bookVO.publisher);
				$("#isbn").val(bookVO.isbn);
				$("#description").val(bookVO.description);
				$("#pubdate").val(bookVO.pubdate);
				$("#buydate").val(bookVO.buydate);
				$("#buyprice").val(bookVO.buyprice);
				$("#buystore").val(bookVO.buystore);
				$("section#book-modal").css("display", "none")
			})
			.fail(function(xhr, textStatus, error) {
				alert("서버 통신 오류!")
			})
		})
		
		$("section#book-modal").css("display", "none")
	})
</script>

</head>

<body>

<h3>도서정보 등록</h3>

<form method="POST" id="books">
	<fieldset>
		<legend>도서정보입력</legend>
		<input name="seq" id="seq" placeholder="일련번호" />
		<input name="title" id="title" placeholder="도서명" />
		<button id="naver-search" type="button">검색</button>
		<input name="link" id="link" placeholder="상세링크" />
		<input name="image" id="image" placeholder="이미지" />
		<input name="author" id="author" placeholder="저자" />
		<input name="price" id="price" placeholder="가격" />
		<input name="discount" id="discount" placeholder="할인가" />
		<input name="publisher" id="publisher" placeholder="출판사" />
		<input name="isbn" id="isbn" placeholder="ISBN" />
		<input name="description" id="description" placeholder="상세내용" />
		<input name="pubdate" id="pubdate" placeholder="출간일자" />
		<input name="buydate" id="buydate" placeholder="구입일자" />
		<input name="buyprice" id="buyprice" placeholder="구입가격" />
		<input name="buystore" id="buystore" placeholder="구입처" />
		<div class="btn-box">
			<button id="btn-save" type="button">저장</button>
		</div>
	</fieldset>
</form>

<section id="book-modal">
	<article id="modal-body">
		<div id="modal-header">
			<span>&times;</span>
		</div>
		<div id="search-result"></div>
	</article>
</section>

</body>
</html>