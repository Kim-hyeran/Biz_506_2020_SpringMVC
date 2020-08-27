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
 * <'a href="http://localhost:8080/shop/">서버 Home</a>  
	- Tomcat WAS에게 shop이라는 context를 가진 프로젝트가 작동되고 있는지 묻는 Request
 * <'a href="http://localhost:8080/shop/product/list">상품리스트</a>  
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

***

# Spring Project 빛나리 쇼핑몰 V3

### 상품관리, 거래처관리 이후 회원가입과 로그인 구현

### Session
 * Hyper Text() : Anchor Tag로 구성된 Text
 * Anchor Tag : Hyper Text를 Web Browser에서 클릭했을 때 해당 단어가 설명하는 문서가 열리는 구조
 * HTML(Hyper Text Markup Language) : Hyper Text 기능을 구현하는 데 사용되는 마크업 언어
 * HTTP(Hyper Text Transfer Protocol)  
	- Hyper Text로 구성된 문서를 Web Browser에서 보여줌  
	- 사용자가 마우스로 Anchor Tag 단어를 클릭하면 그에 따른 문서를 연속해서 보여주는 용도로 최적화된 인터넷 프로토콜  
 * HTTP의 특징 : 단방향, 비연결지향
	1. web client에서 서버에 request를 보냈을 때만 서버에서 response를 할 수 있다.  
	2. 한 번 request - response가 이루어지면 그 연결은 끊어진다.  
 * 서버에 어떤 연산을 요청할 때, 요청하는 정보가 누구나 봐선 안 되는 정보를 가정  
	- 요청 시 정보 열람 권한을 가지고 있다는 사실을 알려야 함 : 로그인 절차(ID, Password)  
	- 서버는 client request에서 사용자의 로그인 정보가 포함되어 있으면 그 정보를 확인하여 정상적인 로그인 정보인지 판별 : 인증  
	- 확인이 완료되면 요청받은 정보를 client에서 response  
	- HTTP는 즉시 client로부터 받은 모든 정보(ID, Password 포함)를 삭제  
	- 이후 또 다시 client로부터 같은 정보를 요청할 필요가 있을 경우 ID와 Password를 request에 실어서 재전송해야 한다.  
	- 서버로부터 request해야 할 정보가 여러 page에 있을 경우 매 page마다 ID와 Password를 보내고, 인증 후 response하는 절차 반복  
 * 위의 불편함을 해결하기 위해 HTTPSession이라는 보조 프로토콜 마련

### HTTPSession(연결 통로 생성)
 1. client에서 로그인 시도, ID와 Password를 먼저 request
 2. 서버에서는 사용자가 보낸 ID와 Password를 확인하여 정상 정보인지 검사 : 인증절차
 3. 서버는 정상적인 사용자로 확인되면 서버 메모리 어딘가에 HTTPSession 정보를 저장할 공간 마련
 4. 저장 공간에는 HTTPSession 규격에 따라 서버가 데이터를 보관
 5. 저장 공간에 Session ID라고 하는 식별자(PK)를 생성
 6. 서버가 client Response를 할 때 Response 정보가 생성된 SessionID를 같이 부착하여 전송
 7. Web Browser에서는 서버로부터 전달된 Response에 HTTPSession 규격에 해당하는 SessionID가 있을 경우 Browser의 임시 저장소에 보관
 8. 이후 client가 서버에 Request하면, Browser는 이 SessionID를 Request에 같이 부착하여 서버에 전송
 9. 서버에서 Request를 수신했을 때 HTTPSession 규격에 맞는 SessionID가 있으면 서버가 정상적으로 발행(Response에 부착하여 보낸)한 SessionID이고, 유효기간이 정상적이라면 이 요청은 "인가된" client로부터 전달된 것임을 확인하고 이후 response를 수행