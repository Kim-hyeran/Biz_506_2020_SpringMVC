# spring web tiles project
 * spring에서 전통적으로 많이 사용하던 layout 관리자
 * 최근 4~5년 전부터 인기가 시들해졌으나 전자정부 프레임워크에서는 지금도 표준 layout처럼 사용되고 있다.

### tiles dependency 가져오기
 * tiles-core, tiles-extras

### tiles-layout.xml 설정하기
 * jsp를 이용해서 화면 layout을 꾸미는 방법을 정의하는 파일
 * tiles-definition tag 사이에 layout을 꾸미는 방법 기술
 * xml 파일의 tag를 정의할 때 tag 이름이 복수형으로 되어있다는 것은 tag 내부에 단수형으로 여러 개의 설정 내용이 정의된다.
 * 정의 방법
	1. layout.jsp 파일을 default라는 이름으로 정의
	2. layout.jsp attribute로 등록한 이름들을 tiles-layout.xml에 put-attribute로 등록
	3. views 폴더에 layout 폴더를 만들고, header.jsp, menu.jsp, content.jsp, footer.jsp 생성

### tiles-context.xml 파일의 UrlBaseResolver bean을 설정할 때 유의사항
 * 일반적으로 spring에서는 bean에 bean을 주입할 때 bean을 미리 만들고 id를 통해서 주입
 * tiles에서 TilesView 클래스를 UrlBaseResolver에 bean을 주입할 때 일반적인 방식으로는 안된다.
	- 반드시 class 이름(TilesView)을 직접 주입하는 방식을 사용하여야 한다.
 * TilesView class를 value 값에 문자열로 지정하는데 자동완성이 되지 않는다.
	- 이 경우 임시로 bean을 하나 생성해 class 값에 TilesView를 입력 후 자동완성된 문자열을 복사하여 사용한다.
 * order property의 value 값을 반드시 1로 설정해주어야 한다.

### tiles-context.xml 설정 후 servlet-context.xml의 변경사항 추가
 * InternalResouceViewResolver의 order 속성을 반드시 2 이상의 값으로 지정해주어야 한다.
 * TilesView를 사용하는 UrlBaseResolver를 우선순위 1, Internal*Resolver를 우선순위 2로 변경