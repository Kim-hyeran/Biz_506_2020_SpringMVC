<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	document.addEventListener("DOMContentLoaded", function() {
		document.querySelector("#bbs-write").addEventListener("click", function() {
			document.location.href="${rootPath}/bbs/write"
		})
		
		/*
		 이벤트 버블링(event bubbling)
		 - tag와 tag들이 서로 감싸는 관계로 layout이 만들어져 있을 때 tag들에 click event 핸들링이 설정되어 있으면 어떤 특정 tag를 click했을 때 원하지 않는 event가 발생
		 - tag box의 가장 중간 부분에 있는 tag를 클릭하면 안쪽부터 바깥쪽으로 계속해서 이벤트가 전해지는 현상
		 - 이러한 현상을 막기 위해서 event.stopPropagation()과 같은 함수를 사용한다.
		 - 버블링 역이용하기
		 	td를 감싸고 있는 table에 click event를 설정하고, td의 click event를 제거
		 	td를 click했을 때 해야할 일을 table의 click event에서 통합 관리 가능
		 	복잡한 event 코드일 경우 td에서 처리하는 것이 효과적일 수 있으나 짧은 코드를 반복하지 않고 처리하기 위해 table로 위임 : event 위임
		 */
		
		document.querySelector("table").addEventListener("click", function (event) {
			let tag_name = event.target.tagName
			if(tag_name === "TD") {
				// let seq = event.target.dataset.seq		// 제목이 저장된 TD tag에서 
				// td tag가 클릭되었으면 현재 클릭된 td tag와 가장 인접한 tr tag를 참조하겠다는 뜻
				// 클릭된 TD를 기준으로 TR tag에서 seq값 추출
				let seq=event.target.closest("TR").dataset.seq
				if(seq) {
					// alert(seq)
					document.location.href = "${rootPath}/bbs/detail/" + seq
				}
			}
		})
	})
</script>
<style>
	td.bbs-tr {
		cursor: pointer;
	}
	
	td.bbs-tr:hover {
		background-color: #ccc;
	}
</style>
<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>NO</th>
			<th>작성일자</th>
			<th>작성시각</th>
			<th>작성자</th>
			<th>제목</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty BBS_LIST}">
		  <tr><td colspan="6">데이터가 없습니다</td></tr>
		</c:if>
		<c:forEach items="${BBS_LIST}" var="vo" varStatus="i">
		<tr class="bbs-tr" data-seq="${vo.b_seq}">
			<td>${i.count}</td>
			<td>${vo.b_date}</td>
			<td>${vo.b_time}</td>
			<td>${vo.b_writer}</td>
			<td data-seq="${vo.b_seq}" class="bbs-subject">
				${vo.b_subject}
				<img src="${rootPath}/upload/${vo.b_file}" width="50px">
			</td>
			<td>${vo.b_count}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<button id="bbs-write">글쓰기</button>