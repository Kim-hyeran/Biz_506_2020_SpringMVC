# AJAX(Async JavaScript and XML)
 * web client와 web server 간에 request와 response를 교환하는 방식 중 하나
 * 일반적인 req, res는 client에서 req를 요청한 후 서버로부터 res(결과)가 도착할 때까지 client는 하던 일을 멈추고 기다려야 한다.  
	- 이 방식은 화면 전체를 다시 새로 rendering할 때는 상관 없으나 사소한 데이터 몇 개만 서버로부터 요청하여 받고자 할 때는 부담
 * 서버와 통신을 수행할 때 web client에 내장된 XMLHttpRequest라는 프로토콜을 사용하여 req, res를 수행하면 web client가 하던 일을 중단하지 않고도 연속적인 업무 수행 가능