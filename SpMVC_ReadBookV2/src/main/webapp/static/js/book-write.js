$(function () {
	$("section#book-modal").css("display", "none")

	$("#btn-save").click(function () {
		// naver로부터 조회한 도서정보를 저장하기 때문에 유효성 검사는 일단 생략
		$("form").submit();
	})

	$("#naver-search").click(function () {
		let title = $("#title").val()
		if (title === "") {
			alert("도서명을 입력해주세요")
			$("#title").focus()
			return false
		}

		// ajax를 사용하여 서버에 도서 검색 요청
		// spring security 프로젝트에서 POST로 전송할 경우, csrf 관련 값을 같이 보내주어야 정상적으로 서버에서 데이터를 받아들인다
		// jsp 파일에서 spring form을 사용하면 관련된 부분을 자동으로 설정해주지만 ajax를 사용해서 POST로 전송할 경우는 자동 설정이 되지 않기 때문에 임의로 값을 지정해주어야 한다.
		$.ajax({
			// ajax로 서버의 /naver/search URL에 POST로 요청하면서 search_text 내 title 변수에 담긴 값을 전달
			url: `${rootPath}/naver/search`,
			method: "POST",
			beforeSend: function (ax) {
				ax.setRequestHeader(
					`${csrf_header}`, `${csrf_token}`
				)
			},
			data: {
				"search_text": title
			},
			// 서버가 데이터 조회를 수행한 후, view(HTML)코드를 return하면 그 결과를 #search-result div box에 채워 출력 요청
			success: function (result) {
				$("#search-result").html(result)
			},
			error: function (error) {
				alert("서버 통신 오류!")
			}
		})

		$("#book-modal").css("display", "block")
	})

	// X 표시를 클릭했을 때 modal창 닫기
	$("div#modal-header span").click(function () {
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
	$(document).on("click", "div.book-select", function () {
		let isbn = $(this).data("isbn")

		// 13자리 isbn 추출하기
		// 코드의 오른쪽에서부터 13자리 잘라내기
		let length = isbn.length
		isbn = isbn.substring(length - 13)

		// ajax 2.x 이상에서 권장하는 코드
		$.ajax({
			url: `${rootPath}/api/isbn`,
			method: "POST",
			beforeSend: function (ax) {
				ax.setRequestHeader(
					`${csrf_header}`, `${csrf_token}`
				)
			},
			data: {
				"search_text": isbn
			}
		}).done(function (bookVO) {
			// alert(JSON.stringify(bookVO))
			$("#seq").val(bookVO.seq);
			$("#title").val(bookVO.title);
			$("#link").val(bookVO.link);
			$("#image").val(bookVO.image);
			$("#author").val(bookVO.author);
			$("#price").val(bookVO.price);
			$("#discount").val(bookVO.discount);
			$("#publisher").val(bookVO.publisher);

			let isbn = bookVO.isbn;
			// isbn 변수에 들어있는 문자열 중에서 HTML tag 구조를 가진 단어가 있으면 tag를 무조건 제거하라
			isbn = isbn.replace(/(<([^>]+)>)/gi, "");
			// isbn = isbn.substring(isbn.length - 17, isbn.length - 4);	원하는 값이 도출되지만 isbn 형식이 변경되는 경우 값이 달라질 수 있다.
			isbn = isbn.substring(isbn.length - 13);
			$("#isbn").val(isbn);
			$("#description").val(bookVO.description);
			$("#pubdate").val(bookVO.pubdate);
			$("section#book-modal").css("display", "none")
		}).fail(function (xhr, textStatus, error) {
			alert("서버 통신 오류!")
		})
	})

})