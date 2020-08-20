# Spring Project 빛나리 쇼핑몰 V1
 * 상품관리, 거래처 관리, 회원가입, 로그인 포함
 * 반응형 메뉴와 메인화면 구현
 * DataBase : Oracle

### Project 시작
 * Java Version 1.8
 * Spring Framework 5.2.8
 * lombok, logback
 * views/home.jsp파일 삭제 후 재생성
 * Run Service 수행하여 home 화면 출력

### DB 연동 설정(pom.xml)
 * spring.jdbc
 * mybatis
 * mybatis-spring
 * commons-dbcp2
 * ojdbc6
 
 * spring/appServlet/mybatis-context.xml 파일 생성 및 작성
 
### URL(Uniform Resource Location)
 * 파일 식별자

### URI(Uniform Resource Identifier)
 * 통합 자원 식별자

#### View단에서 사용하는 URL
 * <a href="http://localhost:8080/shop/">서버 Home</a>  
	- Tomcat WAS에게 shop이라는 context를 가진 프로젝트가 작동되고 있는지 묻는 Request
 * <a href="http://localhost:8080/shop/product/list">상품리스트</a>  
	- shop context의 Dispatcher에게 product/list를 수행할 수 있는 Controller method가 존재하는지 묻는 Request  
 * 이 HTML 코드를 화면에서 만나면 Hyper Text(anchor 문자열)를 클릭했을 때 서버에 Request한다.  
	- 이 때 수행하는 Request는 method=RequestMethod.GET으로 설정된 함수에서 처리한다.  
 * href : Hyper Text Reference, URL 주소라 생각하면 된다.
 
### HTML 코드에서 GET method로 Request를 요청하는 곳들
 * anchor tag : <'a href="주소">텍스트</a>
 * script에서 임의로 변경 : document.location.href="주소"
 * script에서 임의로 변경 : document.location.replace="주소"
 * css 가져오기 : <'link rel="stylesheet" href="주소">
 * script 가져오기 : <'script src="주소"></script>
 * 이미지 보이기 : <'img src="주소"/>
 * 배경이미지 : background-imgae : url("주소")