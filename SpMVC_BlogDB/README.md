# SpMVC_BlogDB : Oracle, MyBatis 연동 Blog

### DB Table을 사용하여 CRUD 모두 구현하기

### Oracle, MyBatis 연동을 위한 Dependency 설정
 * spring-jdbc
 * mybatis
 * mybatis-spring
 * ojdbc
 * commons-dbcp2
 
### SQL Injection 공격
 * tbl_blogs table의 PK 값은 NUMBER형으로 숫자 데이터 타입이다.
 * http://localhost:8080/blog/view?seq=22 형식으로 사용하도록 코드를 작성하였을 때, seq=22 or 1=1이라는 형식의 문자열로 query 전송  
	- 1=1은 모든 경우가 true이므로 어떠한 형식의 조건문이라도 상관 없이 모든 값이 true  
	- 위와 같은 query를 전송해 delete 명령 등을 수행하여 모든 데이터를 삭제시킬 수 있다.  
 * 현재 사용되는 Mybatis, JDBC에서는 이러한 query 명령문이 전송되면 오류가 출력되도록 설계되어 있다.  
	- 과거 버전의 JDBC에서는 검증 절차 없이 명령이 수행되는 경우가 있었다.  
	- 이러한 허점을 노리는 공격을 **SQL 삽입공격(SQL Injection 공격)**이라고 한다.  

### 블로그 글 수정하기
1. view에서 수정 버튼을 클릭
2. 해당하는 seq의 글을 select 수행
3. wirte 화면에서 해당하는 글의 내용 출력
4. 내용 변경
5. 저장 버튼 클릭 시 update를 호출하여 데이터 변경
6. 변경된 내용 화면 출력

### write.jsp 파일 한 개로 insert, update 모두 사용하기
 * form tag에 action은 데이터를 저장하기 위한 Controller의 Mapper
 * action을 삭제하면 form화면을 호출할 때 사용한 RequestMapping이 자동으로 부여
 * input GET으로 write.jsp를 열고 저장을 하면 input POST로 Controller에 데이터가 전송
 * update GET으로 write.jsp를 열고 저장하면 update POST로 Controller에 데이터 전송
 
### HTTP 상태코드 400
 * Controller에서 VO를 매개변수로 설정하여 form에서 전달된 데이터를 받을 때 많이 발생하는 문제(오류코드)
 * BlogVO의 bl_seq는 type이 long형인데, 블로그 게시물을 새로 작성하기 위해 열린 write.jsp에 input type=hidden으로 bl_seq 항복 존재
	- 이 항목에 아무런 값이 채워지지 않은 상태로 Controller로 전달되면 " "형의 값을 long형으로 타입 변환을 시도하다가 내부적으로 NumberFormatException이 발생하여 나타나는 오류