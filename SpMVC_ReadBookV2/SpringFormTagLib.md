# spring form taglib
 * spring project에서 사용할 수 있는 JSP 확장 tag library이다.
 * form taglib를 사용하면 Controller의 model과 연동하여 input 관련 form을 비교적 쉽게 구현할 수 있도록 한다.

## form taglib 프로젝트 작성 순서
0. http://www.springframework.org/tags/form을 taglib로 설정
1. form에서 input box를 구현 : html5 tag로
2. input box의 name 값과 같은 이름의 필드변수를 갖는 VO 클래스 작성
3. form의 form tag를 form:form으로, input tag를 form:input으로 변경
4. input tag의 id 속성을 모두 제거, name 속성을 path로 변경  
	자동으로 name과 id값이 path에 지정한 값으로 설정된다.
5. input tag는 반드시 self closing <form:input path="" />
6. form:form tag method를 제거해도 된다. : 기본값이 method="POST"로 설정된다.
7. action을 필요에 따라 설정
8. modelAddAttribute에 Controller의 model에 실어서 보내는 VO이름 작성

## 양방향 암호화, 단방향 암호화

#### 양방향 암호화
 * 한 가지 도구를 사용하여 평문을 암호화하고, 다시 암호문을 평문으로 복호화하는 기능을 포함하는 암호화 기법

#### 단방향 암호화
 * 평문을 암호화할 수 있지만, 암호문을 평문으로 복호화하는 도구를 제공하지 않는 암호화 기법
 * 비밀번호를 암호화하여 저장하였을 경우 사용자가 입력한 평문을 암호화하여 저장된 암호문과 비교하여 일치하는지 알아낸다.