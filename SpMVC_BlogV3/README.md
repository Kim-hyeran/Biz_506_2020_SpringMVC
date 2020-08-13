# SpMVC_BlogV3

### WAS를 만들면서 외부의 css 파일을 link하여 jsp에 부착할 때
 * 외부에 css파일을 만들고 style을 지정한 다음 jsp 파일에서 link하여 사용할 때, css 파일을 변경하고 저장하였음에도 반영되지 않는 경우 발생
 * 서버 Application을 Run As Server로 실행하면 tomcat이 jsp 파일을 _jsp.java 파일로 변환하고 class 파일로 컴파일  
	- jsp 파일을 변경하면 그 때마다 jsp 를 _jsp.java로 변환해야 변경된 jsp화면이 사용자에게 노출됨  
	- css와 같은 파일은 tomcat이 변경된 것을 인지하지 못하고 Run As Server를 수행했을 때 서버에 올라가 있는 css파일을 갱신하지 않음  
	- 따라서 css 파일의 내용을 변경하고 view를 새로고침하여도 변경된 css가 반영되지 않음
 * 문제 해결 트릭 : css 파일에 의미 없는 문자열을 부착하여 tomcat에게 파일의 변화를 알릴 수 있다.
 	- 예시 : <link href="파일.css?ver=0001" />와 같은 형식으로 link 코드 작성